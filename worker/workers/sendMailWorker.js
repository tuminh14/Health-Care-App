import globalConstants from "../../server/config/globalConstants";
import User from "../../server/models/users.model";
import {sendMail} from "../../server/util/helper/sendMail";
import AMPQ from "../../ampq/ampq";

AMPQ.consumeData(globalConstants.jobName.SEND_EMAIL, async (msg,channel)=>{
    try{
        const data = JSON.parse(msg.content.toString());
        const user = {
            fullName: data.fullName,
            email: data.email,
            token: data.token,
            cuid: data.cuid
        }
        await sendMail(user);
        return true;
    }catch(err){
        console.log(`${globalConstants.jobName.SEND_EMAIL} fail`);
        console.error(err);
        return true;
    }
})
