package model;

public class UserInformation {
    private String name;
    private String phone;
    private String gender;
    private String birthDay;
    private String weight;
    private String height;
    private String email;
    private String passWord;

    public UserInformation (String strName, String strPhone, String strGender,
                            String strBirthDay, String strWeight, String strHeight,
                            String strEmail, String strPassWord) {
        this.setName(strName);
        this.setPhone(strPhone);
        this.setGender(strGender);
        this.setBirthDay(strBirthDay);
        this.setWeight(strWeight);
        this.setHeight(strHeight);
        this.setEmail(strEmail);
        this.setPassWord(strPassWord);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }


}
