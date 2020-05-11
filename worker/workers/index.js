import AMPQ from '../../ampq/ampq'

export async function createQueue(){
    try {
        await AMPQ.initChannel();
        AMPQ.initQueue('test');
    }catch {
        console.log('loi con me may roi thang ngu')
    }
}
export function createWorkers() {
    AMPQ.initChannel().then(() => {
        // Import worker here
        require('./testWorker')
        console.log('AMPQ worker is running...');
    }).catch(error => {
        console.error('AMPQ: createWorkers initChannel error:');
        console.error(error);
    });
}
