import mongooge from "mongoose"
import globalConstant from "../config/globalConstants";
const UserSchema = mongooge.Schema({
    email: { type: String, required: true, unique: true },
    fullName: { type: String, required: true },
    passWord: { type: String, required: true },
    phoneNumber: { type: String, unique: true},
    gender: {type: String, default: globalConstant.gender.OTHER},
    birthDay: {type: Date},
    role: {type: String,  default: globalConstant.role.USER},
    active: {type: Number, default: globalConstant.activate.DEACTIVATED},
    online:{type: Boolean, default: false},
    weight: {type: Number, required: true},
    height: {type: Number, required: true}
},{timestamp: true});
UserSchema.set("toJSON",{
    transform(doc, ret, options){
        delete ret.__v;
        delete ret.passWord;
    }
});
export default mongooge.model("User", UserSchema);
