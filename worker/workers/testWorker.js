import AMPQ from '../../ampq/ampq';

AMPQ.consumeData('test', (msg,channel)=>{
    console.log(msg.content.toString())
});