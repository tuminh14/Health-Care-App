export default (error, req, res, next) => {
    if (!error.statusCode) {
        console.error(error.stack);
    }
    
    const status = error.statusCode || 500;
    const payload = status === 500 ? error.message : 'Internal server error';
    
    res.status(status).json(payload);

}