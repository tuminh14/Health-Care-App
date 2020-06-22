import jwt from '../util/jwt';
import globalConstant from '../config/globalConstants';
import config from '../config/config';

export function isUser(req, res, next) {
    const authorization = req.header('Authorization');
    
    if (typeof authorization != 'string') {
        return next(Promise.reject({
            status: 401,
            error: 'Unauthorized'
        }));
    }
    const authorizationArray = authorization.split(' ');
    
    if (authorizationArray[0] === 'Bearer') {
        const token = authorizationArray[1];
        let userData;

        try {
            userData = jwt.verify(token,config.jwtSecret);
            if (!userData) {
                return next(Promise.reject({
                    status: 401,
                    error: 'Unauthorized'
                }));
            } else {
                req.JWTDecoded = userData;
                return next();
            }
        } catch (err) {
            return next(Promise.reject({
                status: 401,
                error: 'Unauthorized'
            }))
        }
    }
    return next(Promise.reject({
        status: 401,
        error: 'Unauthorized'
    }))
}