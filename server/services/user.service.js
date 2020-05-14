import globalConstants from '../config/globalConstants';
import userModel from '../models/users.model';
import hashPassword from '../util/helper/hashPassword';

export async function registry(user) {
    const data = {
        email: user.email,
        fullname: user.fullname,
        phoneNumber: user.phoneNumber,
        gender: user.gender,
        birthday: user.birthday,
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
        
        data.passWord = hashPassword.hash(user.passWord);
        let userInsert = await userModel.create(user);
        return userInsert.toJSON();

    } catch (error) {
        console.error('error userRegistry: ', error);
        return Promise.reject(
            { 
                status: erorr.status || 500,
                error: error.message || error.errors. ||'Server Internal Error'
            }
        )
    }
}