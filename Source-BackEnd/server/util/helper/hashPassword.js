import bcrypt from 'bcrypt';

const saltRounds = 10;

module.exports = {
    hash: (password) => {
        return bcrypt.hash(password,saltRounds);
    },
    compare: (password, hash) => {
        return bcrypt.compare(password, hash);
    }
}