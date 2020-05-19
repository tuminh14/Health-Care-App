import Express from 'express';
import mongoose from "mongoose";
import bodyParser from 'body-parser';
import morgan from 'morgan';
import swaggerUI from 'swagger-ui-express';
import cors from 'cors';

import swaggerSpec from './docs';
import config from "./config/config"
import * as dummy from "./mongo/dummyData";
import AMPQ from "../ampq/ampq";
import { createQueue, createWorkers} from "../worker/workers";
import errorHandler from './util/helper/errorHandler';
import responseHandle from './util/responseHandle';
import {dummyUser} from './mongo/dummyData';

import users from './routes/user.routes';
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
        dummy.dummyUser();
    }
});
createQueue().then(()=>{
    createWorkers();
});

app.use(morgan(config.MORGAN_FORMAT, {
    skip: (req, res) => res.status < 400,
    stream: process.stderr,
}))
app.use(morgan(config.MORGAN_FORMAT, {
    skip: (req, res) => res.status >= 400,
    stream: process.stdout
}));
// Note: All import correquest handle use CORS must be write bellow CORS settings
app.use(cors(config.CORS_OPTIONS));

app.use(bodyParser.json({ limit: '20mb' }));
app.use(bodyParser.urlencoded({ limit: '20mb', extended: false }));
app.use((req, res, next) => {
    let langCode = req.headers.lang || req.query.lang;
    if (!langCode || langClan === 'null' || langCode === 'undefined') {
        req.headers.lang = 'en';
    }
    next();
})
app.use('/api-docs', swaggerUI.serve, swaggerUI.setup(swaggerSpec));
app.use((req, res, next) => {
    res.RH = new responseHandle(res);
    next();
})

// import api here
app.use('/api',[users]);

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
