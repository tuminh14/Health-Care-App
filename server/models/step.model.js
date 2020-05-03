import mongooge from "mongoose"
import User from "./users.model"

import globalConstant from "../config/globalConstants";
const StepSchema = mongooge.Schema({
    userId: {type: mongooge.Types.ObjectId, ref: "User"},
},{timestamp: true});
StepSchema.set("toJSON",{
    transform(doc, ret, options){
        delete ret.__v;
    }
});
export default mongooge.model("Step", StepSchema);
