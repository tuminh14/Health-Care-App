import * as userService from '../services/user.service';

export async function registry(req, res) {
    try {
        let options = req.body;
        let payload = await userService.registry(options);
        res.RH.success(payload);
    } catch (error) {
        return res.RH.successError(error);
    }
}
export async function login(req, res) {
    try {
        let options = req.body;
        let payload = await userService.login(options);
        res.RH.success(payload);
    } catch (error) {
        return res.RH.successError(error);
    }
}

export async function sendVerifyPhoneNum(req, res) {
    try {
        let options = await req.query;
        let payload = await userService.sendVerifyPhoneNum(options);
        res.RH.success(payload);
    } catch (error) {
        return res.RH.successError(error);
    }
}

export async function verifyPhoneNum(req, res) {
    try {
        let options = await req.query;
        let payload = await userService.verifyPhoneNum(options);
        res.RH.success(payload);
    } catch (error) {
        return res.RH.successError(error);
    }
}