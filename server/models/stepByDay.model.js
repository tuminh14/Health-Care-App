import mongooge from "mongoose"
import Step from "./step.model"
import globalConstant from "../config/globalConstants";
const StepByDaySchema = mongooge.Schema({
    dayId: {type: mongooge.Types.ObjectId, ref: "Step"},
    h01: {type: Number, default: 0},
    h02: {type: Number, default: 0},
    h03: {type: Number, default: 0},
    h04: {type: Number, default: 0},
    h05: {type: Number, default: 0},
    h06: {type: Number, default: 0},
    h07: {type: Number, default: 0},
    h08: {type: Number, default: 0},
    h09: {type: Number, default: 0},
    h10: {type: Number, default: 0},
    h11: {type: Number, default: 0},
    h12: {type: Number, default: 0},
    h13: {type: Number, default: 0},
    h14: {type: Number, default: 0},
    h15: {type: Number, default: 0},
    h16: {type: Number, default: 0},
    h17: {type: Number, default: 0},
    h18: {type: Number, default: 0},
    h19: {type: Number, default: 0},
    h20: {type: Number, default: 0},
    h21: {type: Number, default: 0},
    h22: {type: Number, default: 0},
    h23: {type: Number, default: 0},
    h24: {type: Number, default: 0},
},{timestamp: true});
StepSchema.set("toJSON",{
    transform(doc, ret, options){
        delete ret.__v;
    }
});
export default mongooge.model("Step", StepByDaySchema);
