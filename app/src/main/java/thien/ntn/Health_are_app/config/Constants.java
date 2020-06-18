package thien.ntn.Health_are_app.config;

public class Constants {
    public interface ERROR_MSG {
        String UNKNOWN_ERROR = "Unknown Error !";
        String NO_NETWORK_CONNECTION = "No network connection ";
        String TIMEOUT = "Time out !";
    }

    public static final int REQUEST_TIMEOUT = 60;

    public interface SHARE_PREFERENCES_NAME {
        String LOGIN_PROFILE = "LOGIN_PROFILE";
        String LOGIN_PHONE_NUMBER_VERIFY = "LOGIN_PHONE_NUMBER_VERIFY";
    }
}
