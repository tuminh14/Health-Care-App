import AMPQ from '../../ampq/ampq'

export function createWorkers() {
    AMPQ.initChannel().then(() => {
        // Import worker here
        console.log('AMPQ worker is running...');
    }).catch(error => {
        console.error('AMPQ: createWorkers initChannel error:');
        console.error(error);
    });
}
