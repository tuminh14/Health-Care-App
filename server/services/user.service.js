import globalConstants from '../config/globalConstants';
import userModel from '../models/users.model';
import hashPassword from '../util/helper/hashPassword';
import JWT from '../util/jwt';
import config from '../config/config';

export async function registry(user) {
    const data = {
        email: user.email,
        fullName: user.fullName,
        phoneNumber: user.phoneNumber,
        birthDay: user.birthDay,
        role: globalConstants.role.USER,
        active: globalConstants.activate.DEACTIVATED,
        online: false,
        weight: user.weight,
        height: user.height
    }
    try {
        let existUser = await userModel.findOne({ $or: [
                                                        { email: data.email.toString() },
                                                        { phoneNumber: data.phoneNumber.toString()}
                                                    ]});
        if (existUser) {
            return Promise.reject({status: 403, error: 'email/phone number already created.'});
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
                error: error.message || error.errors||'Server Internal Error'
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
        let existUser = await userModel.findOne({ email: data.email.toString()});

        if (!existUser) {
            return Promise.reject({status: 403, error: 'Incorrect email/password.'});
        }

        let result = await hashPassword.compare(data.passWord, existUser.passWord);
        if (result)
        {
            if (existUser.activeMail === globalConstants.activate.ACTIVATED) {
                await userModel.updateOne( {
                    _id: existUser._id
                }, {
                    $set: {
                        online: true
                    }
                });
                let token = await JWT.issue({_id: existUser._id}, config.jwtSecret);
                return {
                    user: existUser,
                    token: token
                }
            }
            return Promise.reject({status: 403, error: 'Account must be activated.'});

        }
        return Promise.reject({status: 403, error: 'Incorrect email/password.'});


    } catch (error) {
        console.error('error userLogin: ', error);
        return Promise.reject(
            {
                status: error.status || 500,
                error: error.message || error.errors||'Server Internal Error'
            }
        )
    }
}
