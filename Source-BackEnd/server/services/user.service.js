import globalConstants from '../config/globalConstants';
import userModel from '../models/users.model';
import StepByDay from '../models/stepByDay.model';
import mongoose from 'mongoose'
import hashPassword from '../util/helper/hashPassword';
import JWT from '../util/jwt';
import config from '../config/config';
import twilio from 'twilio';
import AMPQ from "../../ampq/ampq";
import cuid from 'cuid';
export async function registry(user) {
    const data = {
        cuid: cuid(),
        email: user.email,
        fullName: user.fullName,
        phoneNumber: user.phoneNumber,
        birthDay: user.birthDay,
        role: globalConstants.role.USER,
        activeMail: globalConstants.activate.ACTIVATED,
        online: false,
        weight: user.weight,
        height: user.height
    }
    try {
        let existUser = await userModel.findOne({
            $or: [
                { email: data.email.toString() },
                { phoneNumber: data.phoneNumber.toString() }
            ]
        });
        if (existUser) {
            return Promise.reject({ status: 403, error: 'email/phone number already created.' });
        }

        data.passWord = await hashPassword.hash(user.passWord);
        if (user.gender === 'male') {
            data.gender = globalConstants.gender.MALE;
        } else {
            if (user.gender === 'female') {
                data.gender = globalConstants.gender.FEMALE;
            } else {
                data.gender = globalConstants.gender.OTHER;
            }
        }
        let userInsert = await userModel.create(data);
        return userInsert.toJSON();

    } catch (error) {
        console.error('error userRegistry: ', error);
        return Promise.reject(
            {
                status: error.status || 500,
                error: error.message || error.errors || 'Server Internal Error'
            }
        )
    }
}

export async function login(user) {
    const data = {
        email: user.email,
        passWord: user.passWord
    }
    try {
        let existUser = await userModel.findOne({ email: data.email.toString() });

        if (!existUser) {
            return Promise.reject({ status: 403, error: 'Incorrect email/password.' });
        }

        let result = await hashPassword.compare(data.passWord, existUser.passWord);
        if (result) {
            if (existUser.activeMail === globalConstants.activate.ACTIVATED) {
                await userModel.updateOne({
                    _id: existUser._id
                }, {
                    $set: {
                        online: true
                    }
                });
                let token = await JWT.issue({ _id: existUser.cuid }, config.jwtSecret);
                return {
                    user: existUser,
                    token: token
                }
            }
            return Promise.reject({ status: 401, error: 'Account must be activated.' });

        }
        return Promise.reject({ status: 403, error: 'Incorrect email/password.' });


    } catch (error) {
        console.error('error userLogin: ', error);
        return Promise.reject(
            {
                status: error.status || 500,
                error: error.message || error.errors || 'Server Internal Error'
            }
        )
    }
}

export async function sendVerifyPhoneNum(user) {
    try {
        let existUser = await userModel.findOne({ phoneNumber: user.phoneNumber.toString()} );

        if (!existUser) {
            return Promise.reject({ status: 403, error: 'Incorrect phone number.' });
        }
        const data = {
            existUser: existUser,
            user: user
        }
        AMPQ.sendDataToRabbit(globalConstants.jobName.SEND_OTP_PHONE,data)
        return {
            to: user.phoneNumber,
            status: globalConstants.twilioVerifyStatus.PENDING
        }
    } catch (error) {
        console.error('error user send verify phone number: ', error);
        return Promise.reject(
            {
                status: error.status || 500,
                error: error.message || error.errors || 'Server Internal Error'
            }
        )
    }
}

export async function verifyPhoneNum(user) {
    const data = {
        phoneNumber: `${globalConstants.countryCode.VIET_NAM}${user.phoneNumber}`,
        verifyCode: user.verifyCode,
        channel: globalConstants.channel.SMS
    }

    try {
        let existUser = await userModel.findOne({ phoneNumber: user.phoneNumber.toString()} );

        if (!existUser) {
            return Promise.reject({ status: 403, error: 'Incorrect phone number.' });
        }
        const twilioClient = await twilio(config.twilio.accountSID, config.twilio.authToken);
        const payload = await twilioClient
            .verify
            .services(config.twilio.serviceID)
            .verificationChecks
            .create({
                to: data.phoneNumber,
                code: data.verifyCode
            });

        if (payload.status === globalConstants.twilioVerifyStatus.PENDING) {
            return Promise.reject({ status: 401, error: 'Unauthorized' });
        }

        if (payload.status === globalConstants.twilioVerifyStatus.APPROVED) {
            userModel.updateOne({
                _id: existUser._id
            }, {
                $set: {
                    activePhone: globalConstants.activate.ACTIVATED
                }
            })

            await userModel.updateOne({
                _id: existUser._id
            }, {
                $set: {
                    online: true
                }
            });
            let token = await JWT.issue({ _id: existUser._id }, config.jwtSecret);
            return {
                user: existUser,
                token: token
            }
        }

        return Promise.reject({ status: 401, error: 'Unauthorized' });

    } catch (error) {
        console.error('error user verify phone number: ', error);
        if (error.status === 404) {
            error.message = 'Phone number was not requested';
        }
        return Promise.reject(
            {
                status: error.status || 500,
                error: error.message || error.errors || 'Server Internal Error'
            }
        )
    }
}

export async function sendVerifyEmail(user){
    try{
        let existUser = await userModel.findOne({email: user.email});
        if(existUser) {
            if(existUser.activeMail === globalConstants.activate.ACTIVATED)
            {
                return Promise.reject({status: 403, error: 'Account already activated'})
            }
            let token = await JWT.issueEmail({ email: user.email, cuid: existUser.cuid, fullName: existUser.fullName }, config.jwtSecret);
            const data = {
                email: user.email,
                fullName: existUser.fullName,
                cuid: existUser.cuid,
                token: token
            }
            AMPQ.sendDataToRabbit(globalConstants.jobName.SEND_EMAIL,data);
            return {
                fullName: existUser.fullName,
                email: user.email
            }
        }
        return Promise.reject({ status: 403, error: 'Incorrect email.' })

    }catch(err){
        console.error('error user send verify email: ', err);
        return Promise.reject(
            {
                status: err.status || 500,
                err: err.message || err.errors || 'Server Internal Error'
            }
        )
    }
}

export async function saveStepByDay(data){
    try{
        let existUser = await userModel.findOne({cuid: data._id});
        if(!existUser){
            return Promise.reject(
                {
                    status: 403,
                    err: 'User dont existed'
                }
            )
        }
        let time = {
            hour: data.time.split(':')[0],
            minute: data.time.split(':')[1],
            seccond: data.time.split(':')[2]
        }
        let existDate = await StepByDay.findOne({date: data.date.toString(), userId: data._id});
        if(!existDate){
            let stepDay = {
                userId: data._id,
                date: data.date,
            }
            let key = 'h' +  time.hour.toString();
            stepDay[key] = [{
                time: data.time.toString(),
                date: data.date.toString(),
                step: data.step,
                totalTime: data.totalTime,
                totalDistance: data.totalDistance
            }]
            let dateInsert = await StepByDay.create(stepDay);
            return dateInsert.toJSON();
        }
        let key = 'h' +  time.hour.toString();
        let stepDay = {
            time: data.time.toString(),
            date: data.date.toString(),
            step: data.step,
            totalTime: data.totalTime,
            totalDistance: data.totalDistance
        };
        await existDate[key].push(stepDay);
        await existDate.save();
        return existDate.toJSON();
    }catch(err) {
        console.error('error save step by day', err);
        return Promise.reject(
            {
                status: err.status || 500,
                err: err.message || error.errors || 'Server Internal Error'
            }
        )
    }
}
export async function getStepByDay(data){
    try{
        let existDate = await StepByDay.find({userId: data._id.toString()}).lean();
        if(!existDate){
            return Promise.reject(
                { 
                    status: 403,
                    err: 'Invalid user'
                }
            )
        }
        return existDate;
        // let days = await StepByDay.find({}).lean();
        // let returnDays = [];
        // let startDayCondition = parseInt(data.startDay.split('/')[0], 10);
        // let startMonthCondition = parseInt(data.startDay.split('/')[1], 10);
        // let startYearCondition =  parseInt(data.startDay.split('/')[2], 10);
        // days.filter((day)=>{
        //     let dd = parseInt(day.date.split('/')[0], 10);
        //     let mm = parseInt(day.date.split('/')[1], 10);
        //     let yyyy = parseInt(day.date.split('/')[2], 10);
        //     if(dd < startDayCondition && mm < startMonthCondition && yyyy < startYearCondition){
        //         return false;
        //     }
        //     return true;
        // }).map((day)=>{
        //     returnDays.push(day)
        // })
        // return returnDays
    }catch(err) {
        console.error('error get step by date', err);
        return Promise.reject(
            {
                status: err.status || 500,
                err: err.message || error.errors || 'Server Internal Error'
            }
        )
    }
}

export async function deleteAllStep(data){
    try{
        let existDate = await StepByDay.find({userId: data._id.toString()});
        if(!existDate){
            return Promise.reject(
                { 
                    status: 403,
                    err: 'Invalid user'
                }
            )
        }
        await StepByDay.deleteMany({userId: data._id.toString()})
        return ({})
    }catch(err) {
        console.error('error delete all step by day', err);
        return Promise.reject(
            {
                status: err.status || 500,
                err: err.message || error.errors || 'Server Internal Error'
            }
        )
    }
}