export default (error, req, res, next) => {
    if (!error.status) {
        console.error(error.stack);
    }
    
    const status = error.status|| 500;
    const payload = status === 500 ? error.message : 'Internal server error';
    
    res.status(status).json(payload);

}