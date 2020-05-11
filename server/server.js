import mongoose from "mongoose";
import config from "./config/config"
import AMPQ from "../ampq/ampq"
import { createQueue,
createWorkers} from "../worker/workers";
import User from "./models/users.model"

mongoose.connect(config.mongoURL,{ useNewUrlParser: true, useUnifiedTopology: true}, async (error) => {
    if (error) {
        console.error('Please make sure Mongodb is installed and running!');
        throw error;
    }
});
createQueue().then(()=>{
    createWorkers();
})




