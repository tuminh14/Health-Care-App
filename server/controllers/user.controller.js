import * as userService from '../services/user.service';

export async function registry(req, res) {
    try {
        let options = req.body;
        let payload = await userService.registry(options);
        res.RH.success(payload);
    } catch (error) {
        return res.RH.error(error);
    }
}
export async function login(req, res) {
    try {
        let options = req.body;
        let payload = await userService.login(options);
        res.RH.success(payload);
    } catch (error) {
        return res.RH.error(error);
    }
}