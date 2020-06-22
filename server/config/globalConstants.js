const globalConstant = {
  role: {
    USER: "user",
    ROOT: "root"
  },
  gender: {
    FEMALE: "female",
    MALE: "male",
    OTHER: "other"
  },
  activate: {
    ACTIVATED: 1,
    DEACTIVATED: 0,
    PENDING: 2
  },
  tokenLife: {
    ONE_MINUTE: 60,
    ONE_HOUR : 3600,
    ONE_DAY : 3600 * 24,
    ONE_MONTH : 3600 * 24 * 30,
    ONE_YEAR : 3600 * 24 * 30 * 365,
  },
  regex: {
    PHONE_NUMBER: /^0[0-9]{9}/,
    FULL_NAME: /[\b]*[A-Za-z]+[\b]*/,
    EMAIL: /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  },
  channel: {
    SMS: 'sms',
    CALL: 'call'
  },
  countryCode: {
    VIET_NAM: '+84'
  },
  twilioVerifyStatus: {
    PENDING: 'pending',
    APPROVED: 'approved'
  },
  jobName: {
    SEND_OTP_PHONE: 'send_otp_phone',
    SEND_EMAIL: 'send_email'
  },
  mail: {
    USER: process.env.MAIL_USER || '',
    PASSWORD: process.env.MAIL_PASSWORD || ''
  }

};
export default globalConstant;
