import {Router} from 'express';
import * as userController from '../controllers/user.controller';
import * as userValidation from '../validation/user.validation';
import {isUser} from '../middleware/auth.middleware'

const router = new Router();

router.route('/user/registry')
    .post(
        userValidation.registry,
        userController.registry
    );

router.route('/user/login')
    .post(
        userValidation.login,
        userController.login
    );


router.route('/user/sendVerifyPhoneNum')
    .get(
        userValidation.sendVerifyPhoneNum,
        userController.sendVerifyPhoneNum
    );


router.route('/user/verifyPhoneNum')
    .get(
        userValidation.verifyPhoneNum,
        userController.verifyPhoneNum
    );

router.route('/user/sendVerifyEmail')
    .get(
        userValidation.sendverifyMail,
        userController.sendVerifyEmail
    )
router.route('/user/saveStepByDay')
    .post(
        isUser,
        userValidation.saveStepByDay,
        userController.saveStepByDay,
    )

router.route('/user/getStepByDay')
    .get(
        isUser,
        userController.getStepByDay,
    )

router.route('/user/deleteAllStep')
    .delete(
        isUser,
        userController.deleteAllStep,
    )
export default router;
