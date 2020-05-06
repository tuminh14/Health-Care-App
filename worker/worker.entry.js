import mongoose from 'mongoose';
import { createWorkers } from './workers';
import { MONGO_CONNECTION_STRING } from './config/config';

mongoose.Promise = global.Promise;
mongoose.connect(MONGO_CONNECTION_STRING,{ useNewUrlParser: true, useUnifiedTopology: true }, (err)=>{
    if(err){
        console.error('Please make sure Mongodb is installed and running!');
        throw err;
    }
    console.log('MongoDB connected!');
});
createWorkers();
