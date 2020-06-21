package thien.ntn.Health_are_app.config;

public class Configs {
    public static final String SERVER_URL = "http://voidbraw.com/";

    public interface API_URL {
        String LOGIN = SERVER_URL + "api/user/login";
        String SEND_VERIFY_PHONE_NUMBER = SERVER_URL + "api/user/sendVerifyPhoneNum";
        String VERIFY_PHONE_NUMBER = SERVER_URL + "api/user/verifyPhoneNum";
        String REGISTRY = SERVER_URL + "api/user/registry";
        String SAVE_STEP_BY_DAY = SERVER_URL + "api/user/saveStepByDay";
        String GET_STEP_BY_DAY = SERVER_URL +"api/user/getStepByDay";
        String DELETE_ALL_STEP = SERVER_URL + "api/user/deleteAllStep";
    }
}
