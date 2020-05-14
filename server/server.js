import Express from 'express';
import mongoose from "mongoose";
import bodyParser from 'body-parser';
import config from "./config/config"
import {dummyUser} from "./mongo/dummyData";
import AMPQ from "../ampq/ampq"
import { createQueue, createWorkers} from "../worker/workers";
import User from "./models/users.model"
import errorHandler from './util/helper/errorHandler';
import responseHandle from './util/responseHandle'
// Initialize the Express app
var app = new Express();

mongoose.Promise = global.Promise
// Mongodb connection
mongoose.connect(config.mongoURL,{ useNewUrlParser: true, useUnifiedTopology: true}, async (error) => {
    if (error) {
        console.error('Please make sure Mongodb is installed and running!');
        throw error;
    } else {
        console.log('Mongodb connected!');
        await dummyUser();
    }
});
createQueue().then(()=>{
    createWorkers();
});
console.log(config.mongoURL)
app.use(bodyParser.json({ limit: '20mb' }));
app.use(bodyParser.urlencoded({ limit: '20mb', extended: false }));
app.use((req, res, next) => {
    let langCode = req.headers.lang || req.query.lang;
    if (!langCode || langClan === 'null' || langCode === 'undefined') {
        req.headeha.lang = 'en';
    }
    return next();
})
app.use((req, res, next) => {
    res.RH = new responseHandle;
})

// import api here
app.use(errorHandler);

app.listen(config.port, (error) => {
    if (error) {
        console.error(`Cannot start backend services: `);
        console.error(error);
    } else {
        console.log(`Backend service is running on port: ${config.port}`);
    }
});

export default app;
