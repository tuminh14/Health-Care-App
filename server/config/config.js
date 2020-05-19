const serverPort = 3001; // The port server will running
const dbHost = process.env.MONGO_HOST||'localhost';
const databaseName = process.env.MONGO_DATABASE_NAME||'health-care-app';
const dbPort = process.env.MONGO_PORT||27017;
const dbUser = process.env.USER_ADMIN||"admin";
const dbPassword = process.env.PASS_ADMIN||"admin";
const config = {
    mongoURL: process.env.MONGO_URL || `mongodb://${dbUser}:${dbPassword}@${dbHost}:${dbPort}/${databaseName}`,
    port: process.env.PORT || 8001,
    dbPort,
    databaseName,
    serverHost: process.env.SERVER_HOST || 'localhost',
    serverPort,
    //jwt
    jwtSecret: 'abc',
    //rabbitmq
    rabbitMQ: {
        url: process.env.RABBIT_URL || 'amqp://localhost'
    },
    CORS_OPTIONS : {
        // Find and fill your options here: https://github.com/expressjs/cors#configuration-options
        origin: process.env.SERVER_ORIGIN || '*',
        methods: 'GET,PUT,POST,DELETE',
        allowedHeaders: 'Origin,X-Requested-With,Content-Type,Accept,Authorization,Accept-Language',
    }
};
 export default config;

