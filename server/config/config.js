const serverPort = 8001; // The port server will running
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
    serverPort,
    //jwt
    jwtSecret: 'abc',
    //rabbitmq
    rabbitMQ: {
        url: process.env.RABBIT_URL || 'amqp://localhost'
    },
};
 export default config;

