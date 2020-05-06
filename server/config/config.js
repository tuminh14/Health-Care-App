const serverPort = 8001; // The port server will running
const dbHost = 'localhost';
const databaseName = 'healthCareApp';
const dbPort = 27017;
const dbUser = "admin";
const dbPassword = "admin";
const config = {
    mongoURL: process.env.MONGO_URL || `mongodb://${dbUser}:${dbPassword}@${dbHost}:${dbPort}/${databaseName}`,
    port: process.env.PORT || 8001,
    dbPort,
    databaseName,
    serverPort,
    //jwt
    jwtSecret: '',
    //rabbitmq
    rabbitMQ: {
        url: process.env.RABBIT_URL || 'amqp://localhost'
    },
};
 export default config;

