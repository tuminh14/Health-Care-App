import nodemailer from 'nodemailer';
import config from "../../config/config";

export async function sendMail(user) {
    const transporter = nodemailer.createTransport({
        host: "mail.privateemail.com",
        port: 587,
        secure: false,
        auth: {
            user: config.mail.USER,
            pass: config.mail.PASSWORD
        }
    });
    const mailOptions = {
        from: 'hyperroll@voidbraw.com',
        to: user.email,
        subject: 'Verify Your Account',
        html: '<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/1999/REC-html401-19991224/strict.dtd"><html> <head> <title>Xác nhận tại khoản Heal Care App</title> <meta http-equiv="Content-Type" content="text/html; charset=utf-8"> </head> <body> <p>Xin chào ' + user.fullName  + ',</p> <p>Chào mừng đến với Heal-Care-App,</p> <p>Vui lòng xác nhận tài khoản của bạn bằng cách click vào "Xác nhận tài khoản". </p> <a href="' + config.serverHost + '/confirm?tokenConfirm=' + user.token + '&cuid=' + user.cuid + '"> “Xác nhận tài khoản” </a> <p>Hoặc copy liên kết bên dưới vào trình duyệt của bạn và nhấn "Enter":</p> <p>' + config.serverHost + '/confirm?tokenConfirm=' + user.token + '&cuid=' + user.cuid + '</p> </body> </html>'

}
    transporter.verify((err,succes)=>{
        if (err) {
            console.log(err);
        } else {
            transporter.sendMail(mailOptions,(err)=>{
            })
        }
    })

}
