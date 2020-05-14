import {Router} from 'express';
import * as userController from '../controllers/user.controller'
const router = new Router();

router.route('/user/registry')
    .post(
        userController.registry
    )

export default router;