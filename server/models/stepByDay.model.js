import mongoose from "mongoose"
import User from "./users.model"
import globalConstant from "../config/globalConstants";
const StepByDaySchema = mongoose.Schema({
    userId: {type: mongoose.Types.ObjectId, ref: "User", required: true, unique: true},
    date: {type: String, required:true},
    h0: [{type: Object}],
    h1: [{type: Object}],
    h2: [{type: Object}],
    h3: [{type: Object}],
    h4: [{type: Object}],
    h5: [{type: Object}],
    h6: [{type: Object}],
    h7: [{type: Object}],
    h8: [{type: Object}],
    h9: [{type: Object}],
    h10: [{type: Object}],
    h11: [{type: Object}],
    h12: [{type: Object}],
    h13: [{type: Object}],
    h14: [{type: Object}],
    h15: [{type: Object}],
    h16: [{type: Object}],
    h17: [{type: Object}],
    h18: [{type: Object}],
    h19: [{type: Object}],
    h20: [{type: Object}],
    h21: [{type: Object}],
    h22: [{type: Object}],
    h23: [{type: Object}]
},{timestamp: true});

StepByDaySchema.set("toJSON",{
    transform(doc, ret, options){
        delete ret.__v;
    }
});
export default mongoose.model("Step", StepByDaySchema);
