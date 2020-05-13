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
        return this.res.status(error.statusCode || 500).json({
            error : error
        })
    }
}