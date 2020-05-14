import globalConstants from '../config/globalConstants';
import userModel from '../models/users.model';
import hashPassword from '../util/helper/hashPassword';

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