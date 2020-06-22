import globalConstants from "../../server/config/globalConstants";
import userModel from "../../server/models/users.model";
import config from "../../server/config/config";
import twilio from 'twilio';
import AMPQ from "../../ampq/ampq";


AMPQ.consumeData(globalConstants.jobName.SEND_OTP_PHONE,async  (msg, channel)=>{
    try{
        const twilioClient = await twilio(config.twilio.accountSID, config.twilio.authToken);
        const userInfo  = JSON.parse(msg.content.toString());
        const user = userInfo.user;
        const existUser = userInfo.existUser;
        const data = {
            phoneNumber: `${globalConstants.countryCode.VIET_NAM}${user.phoneNumber}`,
            channel: globalConstants.channel.SMS
        };
        const payload = await twilioClient
            .verify
            .services(config.twilio.serviceID)
            .verifications
            .create({
                to: data.phoneNumber,
                channel: data.channel
            });

        if (payload.status === globalConstants.twilioVerifyStatus.PENDING ) {
            userModel.updateOne({
                _id: existUser._id
            }, {
                $set: {
                    activePhone: globalConstants.activate.PENDING
                }
            });
        }
        return true;
    }catch(err){
        console.log(`${globalConstants.jobName.SEND_OTP_PHONE} fail`);
        console.error(err);
        return true;
    }
})

