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
*               "success": true,
*               "payload": {
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
*               }
*           }
*       403:
*           description: the data is already created
*           schema:
*               type: object
*               properties:
*                   error:
*                       type: object
*                       properties:
*                           status:
*                               type: int
*                           error:
*                               type: string
*                               description: error messages
*               example: {
*                   "error": {
*                       "status": 403,
*                       "error": "email/phone number already created."
*                   }
*               } 
*       422:
*           description: Unprocessable Entity, the data is not valid
*           schema:
*               type: object
*               properties:
*                   success:
*                       type: boolean
*                       properties:
*                   error:
*                       type: string
*                       description: error messages
*               example: {
*                       "success": false,
*                       "error": {
                            "phoneNumber": "Invalid phone number"
                        }
*               } 
*       500:
*         description: When got server exception
* */

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
*               "success": true,
*               "payload": {
                   "user": {
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
                   },
                   "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZWMzZDM4NDM4MzZmODM1YjBmY2M4OGQiLCJpYXQiOjE1ODk4OTIxNDAsImV4cCI6MjUzNTk3MjE0MH0.XE2tpk3jSM4ptgRdjFTwKhmwtfchqCZoHFK8Qak4obU"
*               }
*           }
*       401:
*           description: Not permission
*           schema:
*               type: object
*               properties:
*                   error:
*                       type: object
*                       properties:
*                           status:
*                               type: int
*                           error:
*                               type: string
*                               description: error messages
*               example: {
*                   "error": {
*                       "status": 401,
*                       "error": "Account must be activated."
*                   }
*               } 
*       403:
*           description: user not found
*           schema:
*               type: object
*               properties:
*                   error:
*                       type: object
*                       properties:
*                           status:
*                               type: int
*                           error:
*                               type: string
*                               description: error messages
*               example: {
*                   "error": {
*                       "status": 403,
*                       "error": "Incorrect email/password."
*                   }
*               } 
*       422:
*           description: Unprocessable Entity, the data is not valid
*           schema:
*               type: object
*               properties:
*                   success:
*                       type: boolean
*                       properties:
*                   error:
*                       type: string
*                       description: error messages
*               example: {
*                       "success": false,
*                       "error": {
                            "email": "Invalid email"
                        }
*               } 
*       500:
*         description: When got server exception
* */

/**
* @swagger
* /user/sendVerifyPhoneNum:
*  get:
*    summary: Users send verify phone number
*    tags:
*      - Users
*    parameters:
*      - in: query
*        name: phoneNumber
*        required: true
*        type: string
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
*               "success": true,
*               "payload": {
*                   "to": "+84326152565",
*                   "status": "pending"
*               }
*           }
*       403:
*           description: user not found
*           schema:
*               type: object
*               properties:
*                   error:
*                       type: object
*                       properties:
*                           status:
*                               type: int
*                           error:
*                               type: string
*                               description: error messages
*               example: {
*                   "error": {
*                       "status": 403,
*                       "error": "Incorrect phone number."
*                   }
*               } 
*       422:
*           description: Unprocessable Entity, the data is not valid
*           schema:
*               type: object
*               properties:
*                   success:
*                       type: boolean
*                       properties:
*                   error:
*                       type: string
*                       description: error messages
*               example: {
*                       "success": false,
*                       "error": {
                            "phoneNumber": "Invalid phone number"
                        }
*               } 
*       500:
*         description: When got server exception
* */

/**
* @swagger
* /user/verifyPhoneNum:
*  get:
*    summary: verify phone number
*    tags:
*      - Users
*    parameters:
*      - in: query
*        name: phoneNumber
*        required: true
*        type: string
*      - in: query
*        name: verifyCode
*        required: true
*        type: string
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
*               "success": true,
*               "payload": {
                   "user": {
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
                   },
                   "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZWMzZDM4NDM4MzZmODM1YjBmY2M4OGQiLCJpYXQiOjE1ODk4OTIxNDAsImV4cCI6MjUzNTk3MjE0MH0.XE2tpk3jSM4ptgRdjFTwKhmwtfchqCZoHFK8Qak4obU"
*               }
*           }
*       401:
*           description: Unauthorized
*           schema:
*               type: object
*               properties:
*                   error:
*                       type: object
*                       properties:
*                           status:
*                               type: int
*                           error:
*                               type: string
*                               description: error messages
*               example: {
*                   "error": {
*                       "status": 401,
*                       "error": "Unauthorized"
*                   }
*               } 
*       403:
*           description: user not found
*           schema:
*               type: object
*               properties:
*                   error:
*                       type: object
*                       properties:
*                           status:
*                               type: int
*                           error:
*                               type: string
*                               description: error messages
*               example: {
*                   "error": {
*                       "status": 403,
*                       "error": "Incorrect phone number."
*                   }
*               } 
*       429:
*           description: too many request
*           schema:
*               type: object
*               properties:
*                   error:
*                       type: object
*                       properties:
*                           status:
*                               type: int
*                           error:
*                               type: string
*                               description: error messages
*               example: {
*                   "error": {
*                       "status": 429,
*                       "error": "Max send attempts reached"
*                   }
*               } 
*       422:
*           description: Unprocessable Entity, the data is not valid
*           schema:
*               type: object
*               properties:
*                   success:
*                       type: boolean
*                       properties:
*                   error:
*                       type: string
*                       description: error messages
*               example: {
*                       "success": false,
*                       "error": {
                            "phoneNumber": "Invalid phone number"
                        }
*               } 
*       500:
*         description: When got server exception
* */