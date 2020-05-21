import {Router} from 'express';
import * as userController from '../controllers/user.controller';
import * as userValidation from '../validation/user.validation';

const router = new Router();
/**
 * @swagger
 * /user/registry:
 *  post:
 *    summary: Users Registry
 *    tags:
 *      - Users
 *    parameters:
 *      - in: body
 *        name: body
 *        required: true
 *        type: object
 *        properties:
 *          email:
 *            type: string
 *          phoneNumber:
 *            type: string
 *          passWord:
 *            type: string
 *          fullName:
 *            type: string
 *          gender:
 *            type: string
 *          weight:
 *            type: int
 *          height:
 *            type: int
 *          birthDay:
 *            type: Date
 *        example: {
 *          "email": "duongtrantuminh14@gmail.com",
 *          "passWord": "adminRoot",
 *          "phoneNumber": "0943686018",
 *          "fullName": "Duong Tran Tu Minh",
 *          "gender": "male",
 *          "weight": 10,
 *          "height": 20,
 *          "birthDay": "1999-06-14"
 *        }
 *    responses:
 *       200:
 *         description: The response details
 *         schema:
 *           type: object
 *           properties:
 *             success:
 *               type: boolean
 *             payload:
 *               type: string
 *               description: Data result
 *           example: {
 *   "success": true,
 *   "payload": {
        "gender": "male",
        "role": "user",
        "activeMail": 0,
        "activePhone": 0,
        "online": false,
        "_id": "5ec3d3843836f835b0fcc88d",
        "email": "duongtrantuminh14@gmail.com",
        "fullName": "Duong Tran Tu Minh",
        "phoneNumber": "0943686018",
        "birthDay": "1999-06-14T00:00:00.000Z",
        "weight": 10,
        "height": 20
 *   }
}
 *       401:
 *         description: Not permission
 *       404:
 *         description: Dat not found
 *       422:
 *         description: Unprocessable Entity, the data is not valid
 *       500:
 *         description: When got server exception
 * */
router.route('/user/registry')
    .post(
        userValidation.registry,
        userController.registry
    );
/**
 * @swagger
 * /user/login:
 *  post:
 *    summary: Users Login
 *    tags:
 *      - Users
 *    parameters:
 *      - in: body
 *        name: body
 *        required: true
 *        type: object
 *        properties:
 *          email:
 *            type: string
 *          passWord:
 *            type: string
 *        example: {
 *          "email": "duongtrantuminh14@gmail.com",
 *          "passWord": "adminRoot"
 *        }
 *    responses:
 *       200:
 *         description: The response details
 *         schema:
 *           type: object
 *           properties:
 *             success:
 *               type: boolean
 *             payload:
 *               type: string
 *               description: Data result
 *           example: {
 *   "success": true,
 *  "payload": {
 *      "user": {
            "gender": "male",
            "role": "user",
            "activeMail": 1,
            "activePhone": 1,
            "online": false,
            "_id": "5ec3d3843836f835b0fcc88d",
            "email": "duongtrantuminh14@gmail.com",
            "fullName": "Duong Tran Tu Minh",
            "phoneNumber": "0943686018",
            "birthDay": "1999-06-14T00:00:00.000Z",
            "weight": 10,
            "height": 20
*        },
*       "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZWMzZDM4NDM4MzZmODM1YjBmY2M4OGQiLCJpYXQiOjE1ODk4OTIxNDAsImV4cCI6MjUzNTk3MjE0MH0.XE2tpk3jSM4ptgRdjFTwKhmwtfchqCZoHFK8Qak4obU"
*    }
}
 *       401:
 *         description: Not permission
 *       404:
 *         description: Dat not found
 *       422:
 *         description: Unprocessable Entity, the data is not valid
 *       500:
 *         description: When got server exception
 * */
router.route('/user/login')
    .post(
        userValidation.login,
        userController.login
    );

/**
 * @swagger
 * /user/sendVerifyPhoneNum:
 *  get:
 *    summary: Users send verify phone number
 *    tags:
 *      - Users
 *    parameters:
 *      - in: body
 *        name: body
 *        required: true
 *        type: object
 *        properties:
 *          phoneNumber:
 *            type: string
 *        example: {
 *          "phoneNumber": "0943686018"
 *        }
 *    responses:
 *       200:
 *         description: The response details
 *         schema:
 *           type: object
 *           properties:
 *             success:
 *               type: boolean
 *             payload:
 *               type: string
 *               description: Data result
 *           example: {
*    "success": true,
*    "payload": {
*        "to": "+84326152565",
*        "status": "pending"
*    }
*}
 *       401:
 *         description: Not permission
 *       404:
 *         description: Dat not found
 *       422:
 *         description: Unprocessable Entity, the data is not valid
 *       500:
 *         description: When got server exception
 * */
router.route('/user/sendVerifyPhoneNum')
    .get(
        userValidation.sendVerifyPhoneNum,
        userController.sendVerifyPhoneNum
    );

/**
 * @swagger
 * /user/verifyPhoneNum:
 *  get:
 *    summary: Users verify phone number
 *    tags:
 *      - Users
 *    parameters:
 *      - in: body
 *        name: body
 *        required: true
 *        type: object
 *        properties:
 *          phoneNumber:
 *            type: string
 *        example: {
 *          "phoneNumber": "0943686018",
 *          "verifyCode": 902461
 *        }
 *    responses:
 *       200:
 *         description: The response details
 *         schema:
 *           type: object
 *           properties:
 *             success:
 *               type: boolean
 *             payload:
 *               type: string
 *               description: Data result
 *           example: {
 *   "success": true,
 *   "payload": {
 *      "to": "+84326152565",
 *      "status": "approved"
 *   }
 * }
 *       401:
 *         description: Not permission
 *       404:
 *         description: Dat not found
 *       422:
 *         description: Unprocessable Entity, the data is not valid
 *       500:
 *         description: When got server exception
 * */    
router.route('/user/verifyPhoneNum')
    .get(
        userValidation.verifyPhoneNum,
        userController.verifyPhoneNum
    );


export default router;
