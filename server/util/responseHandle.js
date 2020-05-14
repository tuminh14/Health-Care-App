export default class ResponseHandler {
    constructor(res) {
        this.res = res;
    }

    success(data) {
        if (!data || typeof data !== "object") {
            throw new Error('Data return must be object.')
        } else {
            return this.res.json({
                success : true,
                payload : data
            })
        }
    }

    error(error) {
        const statusCode = (typeof error.status !== "undefined") ? error.status : 500
        return this.res.status(statusCode).json({
            error : error
        })
    }
}