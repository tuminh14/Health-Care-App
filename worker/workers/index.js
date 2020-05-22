import AMPQ from '../../ampq/ampq'
import globalConstant from "../../server/config/globalConstants";

export async function createQueue(){
    try {
        await AMPQ.initChannel();
        // Import worker
        AMPQ.initQueue('test');
        AMPQ.initQueue(globalConstant.jobName.SEND_OTP_PHONE);
    }catch (error){
        console.error('AMPQ: createWorkers initChannel error: ');
        console.error(error);
    }
}
export function createWorkers() {
    AMPQ.initChannel().then(() => {
        // Import worker here
        require('./testWorker')
        require('./PhoneWorker')
        console.log('AMPQ worker is running...');
    }).catch(error => {
        console.error('AMPQ: createWorkers initChannel error:');
        console.error(error);
    });
}
