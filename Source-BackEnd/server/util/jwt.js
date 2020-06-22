import jwt from 'jsonwebtoken';
import config from '../config/config' ;
import globalConstant from '../config/globalConstants';

module.exports = {
    issue: (payload) => {
        return jwt.sign(
            payload,
            config.jwtSecret,
            {
                algorithm: 'HS256',
                expiresIn: globalConstant.tokenLife.ONE_YEAR
            }
        );
    },
    issueEmail: (payload) => {
        return jwt.sign(
            payload,
            config.jwtSecret,
            {
                algorithm: 'HS256',
                expiresIn: globalConstant.tokenLife.ONE_MINUTE * 2,
            }
        );
    },
    verify: (token, secretKey) => {
        return jwt.verify(token, secretKey);
    }
}
