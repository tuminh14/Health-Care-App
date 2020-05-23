import mongooge from "mongoose"
import Step from "./step.model"
import globalConstant from "../config/globalConstants";
const StepByDaySchema = mongooge.Schema({
    dayId: {type: mongooge.Types.ObjectId, ref: "Step"},
    h01: {type: Object},
    h02: {type: Object},
    h03: {type: Object},
    h04: {type: Object},
    h05: {type: Object},
    h06: {type: Object},
    h07: {type: Object},
    h08: {type: Object},
    h09: {type: Object},
    h10: {type: Object},
    h11: {type: Object},
    h12: {type: Object},
    h13: {type: Object},
    h14: {type: Object},
    h15: {type: Object},
    h16: {type: Object},
    h17: {type: Object},
    h18: {type: Object},
    h19: {type: Object},
    h20: {type: Object},
    h21: {type: Object},
    h22: {type: Object},
    h23: {type: Object},
    h24: {type: Object},
},{timestamp: true});
StepSchema.set("toJSON",{
    transform(doc, ret, options){
        delete ret.__v;
    }
});
export default mongooge.model("Step", StepByDaySchema);
