import mongoose from "mongoose";
import config from "./config/config"


mongoose.connect(config.mongoURL,{useMongoClient: true}, async (error) => {
    if (error) {
        console.error('Please make sure Mongodb is installed and running!');
        throw error;
    }
    console.log("dkjwfkwowegrviuoe");
});
