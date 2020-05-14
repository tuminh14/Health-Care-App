export async function registry(req, res) {
    try {
        let options = req.body;
        let payload = {};
        res.RH.success(payload);
    } catch (error) {
        return res.RH.error(error);
    }
}