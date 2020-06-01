import globalConstants from "../../server/config/globalConstants";
import User from "../../server/models/users.model";
import {sendMail} from "../../server/util/helper/sendMail";
import AMPQ from "../../ampq/ampq";

AMPQ.consumeData(globalConstants.jobName.SEND_EMAIL, async (msg,channel)=>{
    try{
        const data = JSON.parse(msg.content.toString());
        const existUser = await User.findOne({email: data.email}).lean();
        if(existUser){
            if(existUser.activeMail === globalConstants.activate.DEACTIVATED) {
                const user = {
                    fullName: existUser.fullName,
                    email: existUser.email,
                    token: data.token,
                    cuid: existUser.cuid
                }
                await sendMail(user);
                return true;
            }
            const err = new Error('User already activated');
            console.log(`${globalConstants.jobName.SEND_EMAIL} fail`);
            console.error(err);
            return true;
        }
        const err = new Error('Email Wrong');
        console.log(`${globalConstants.jobName.SEND_EMAIL} fail`);
        console.error(err);
        return true;
    }catch(err){
        console.log(`${globalConstants.jobName.SEND_EMAIL} fail`);
        console.error(err);
        return true;
    }
})
