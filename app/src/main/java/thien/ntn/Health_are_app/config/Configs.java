package thien.ntn.Health_are_app.config;

public class Configs {
    public static final String SERVER_URL = "http://voidbraw.com/";

    public interface API_URL {
        String LOGIN = SERVER_URL + "api/user/login";
        String SEND_VERIFY_PHONE_NUMBER = SERVER_URL + "api/user/sendVerifyPhoneNum";
        String VERIFY_PHONE_NUMBER = SERVER_URL + "api/user/verifyPhoneNum";
    }
}
