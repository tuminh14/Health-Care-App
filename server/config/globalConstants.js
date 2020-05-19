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
    ONE_HOUR : 3600,
    ONE_DAY : 3600 * 24,
    ONE_MONTH : 3600 * 24 * 30,
    ONE_YEAR : 3600 * 24 * 30 * 365,
  },
  regex: {
    PHONE_NUMBER: /(09|01[2|6|8|9])+([0-9]{8})\b/,
    FULL_NAME: /[\b]*[A-Za-z]+[\b]*/
  }

};
export default globalConstant;
