import ampq from 'ampqlib'
import config from '../server/config/config.js'
 export default class AMPQ {
    static channel = null;
    static queues = {};

    static initChannel(){
        return new Promise((resolve,reject)=>{
            let channel = AMPQ.channel;
            if(channel){
                resolve(channel);
            }
            // Connect to RabbitMQ
            ampq.connect(config.rabbitMQ.url).then(async conn =>{
                channel = await conn.createChannel();
                AMPQ.channel = channel;
                return resolve(channel);
            }).catch(err=>{
                console.error("ampq connection failed, please check it carefully");
                console.error(err);
                return reject(err);
            });
        })
    }

    static getChannel(){
        return AMPQ.channel;
    }
    static initQueue(queueName, durable=true){
        let channel;
        try{
            channel = AMPQ.getChannel();
        }catch (err) {
            console.error('innit queue error');
            console.error(err);
            throw err;
        }
        if(!AMPQ.queues[queueName]){
            AMPQ.queues[queueName] = channel.assertQueue(queueName, {durable:durable})
        }
        return AMPQ.queues[queueName];
    }
    static sendDataToRabbit(queueName,data){
        if(!data || !(typeof data ==='object') || !(typeof data === 'string')) {
           throw Error('Data must be string or object');
        }
        if(typeof data ==='object'){
            data.JSON.stringify(data);
        }
        try{
            AMPQ.channel.sendToQueue(queueName,Buffer.from(data));
        }catch(err){
            console.error('send data to rabbit error');
            console.error(err);
            throw err;
        }
    }

    static consumeData(queueName, callback, options){
        if(!queueName){
            throw new Error('you must implement queueName in consomer child');
        }
        let noAck = options ? options.noAck : undefined;
        if(typeof noAck === 'undefined'){
            noAck = true;
        }
        AMPQ.channel.consume(queueName,(msg)=>{
            callback(msg,AMPQ.channel);
        }, {
            noAck: noAck,
        });
    }
 }
