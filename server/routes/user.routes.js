import {Router} from 'express';
import * as userController from '../controllers/user.controller';
import * as userValidation from '../validation/user.validation';

const router = new Router();

router.route('/user/registry')
    .post(
        userValidation.registry,
        userController.registry
    )

export default router;