import {body, query , validationResult} from 'express-validator';
import globalConstants from '../config/globalConstants';

const validate = validations => {
    return async (req, res, next) =>{
        await Promise.all(validations.map(validation => validation.run(req)));

        const errors = validationResult(req);
        if (errors.isEmpty()) {
            next();
        } else {
            res.status(403);
            
            let msgErrors = {};
            errors.array().forEach(error => {
                msgErrors[error.param] = error.msg;
            })
            res.RH.successError(msgErrors);
        }
    }
}


export const registry = validate([
    // body('email', 'Invalid does not Empty').not().isEmpty(),
    body('email', 'Invalid email').isEmail(),
    body('birthDay', 'Invalid birthday').trim().isISO8601('yyyy-mm-dd'),
    body('passWord', 'password more than 6 digits').trim().isLength({ min: 6 }),
    body('phoneNumber', 'Invalid phone number').matches(globalConstants.regex.PHONE_NUMBER),
    body('fullName', 'Invalid full name').trim().isLength({ min: 2, max: 100 }).matches(globalConstants.regex.FULL_NAME),
    body('weight', 'Invalid weight').trim().isNumeric(),
    body('height', 'Invalid height').trim().isNumeric(),
    
]);

export const login = validate([
    body('email', 'Invalid email').isEmail(),
    body('passWord', 'password more than 6 digits').trim().isLength({ min: 6 }),
]);

export const sendVerifyPhoneNum = validate([
    query('phoneNumber', 'Invalid phone number').matches(globalConstants.regex.PHONE_NUMBER),
]);
export const verifyPhoneNum = validate([
    query('phoneNumber', 'Invalid phone number').matches(globalConstants.regex.PHONE_NUMBER),
]);

