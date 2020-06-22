export default (error, req, res, next) => {
    if (!error.status) {
        console.error(error.stack);
    }
    
    const statusCode =(typeof error.status !== "undefined") ? error.status : 500;
    const payload = statusCode === 500 ? error.message : 'Internal server error';
    
    if (typeof payload === 'string') {
        res.status(statusCode).send(payload);
    } else {
        res.status(statusCode).json({
            success: false,
            error: payload
        });
    }
}