import User from '../models/users.model';
import globalConstant from "../config/globalConstants";
import cuid from 'cuid'

export async function dummyUser() {
    try {
        let existedUser = await User.findOne({ email: "duongtrantuminh14@gmail.com" }).lean()
        if (!existedUser) {
            await User.create({
                email: "duongtrantuminh14@gmail.com",
                fullName: "Dương Trần Tử Minh",
                passWord: "kOp1kpas",
                cuid: cuid(),
                phoneNumber: "0943686018",
                gender: globalConstant.gender.MALE,
                birthDay: Date.now(),
                role: globalConstant.role.ROOT,
                weight: 10,
                height: 36
            })
        }

    } catch (err) {
        console.error(err)
    }
}
