import swaggerJSDoc from 'swagger-jsdoc';
import config from './config/config';

const swaggerDefinition = {
    info: {
        title: 'Health Care App V1',
        version: '1.0.0',
        description: 'Health Care App API Docs V1',
    },
    host: `localhost:${config.serverPort}`,
    basePath: '/api',
    produces: ['application/json'],
    consumes: ['application/json'],
    securityDefinitions: {
        jwt: {
            type: 'apiKey',
            name: 'Authorization',
            in: 'header',
        },
    },
    security: [{ jwt: [] }],
};
const options = {
    swaggerDefinition,
    apis: [
        'server/routes/*.js',
        'server/models/*.js'
    ],
};

const swaggerSpec = swaggerJSDoc(options);

export default swaggerSpec;

