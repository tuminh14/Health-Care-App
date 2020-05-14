import User from '../models/users.model';
import globalConstant from "../config/globalConstants";
export async function dummyUser(){
    try{
        await User.create({
            email: "duongtrantuminh14@gmail.com",
            fullName: "Dương Trần Tử Minh",
            passWord: "kOp1kpas",
            phoneNumber: "0943686018",
            gender: globalConstant.gender.MALE,
            birthDay: Date.parse('14/06/1999'),
            role: globalConstant.role.ROOT,
            weight: 10,
            height: 36
        })
        console.log("dummy data success")
    }catch(err){
        console.error(err)
    }
}
