const serverPort = 8001; // The port server will running
const dbHost = 'localhost';
const databaseName = 'healthCareApp';
const dbPort = 27017;
const dbUser = "admin";
const dbPassword = "admin";
const config = {
    mongoURL: process.env.MONGO_URL || `mongodb://${dbHost}:${dbPort}/${databaseName}`,
    port: process.env.PORT || 8001,
    dbPort,
    databaseName,
    serverPort,
    jwtSecret: '',
    kue: {
        prefix: 'q',
        redis: {
            port: 6379,
            host: 'localhost',
            db: 4,
            options: {
                // see https://github.com/mranney/node_redis#rediscreateclient
            }
        }
    },
    kueUI: {
        username: '',
        password: '',
        port: 3051
    },
};
 export default config;
