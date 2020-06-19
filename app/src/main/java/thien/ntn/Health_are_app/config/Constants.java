package thien.ntn.Health_are_app.config;

public class Constants {
    public interface ERROR_MSG {
        String UNKNOWN_ERROR = "Unknown Error !";
        String NO_NETWORK_CONNECTION = "No network connection ";
        String TIMEOUT = "Time out !";
        String ERROR_GETTING_RESPONSE = "Error getting response";

        String INFO_IS_EMPTY = "Bạn chưa điền đầy đủ thông tin";
        String EMAIL_INVALID = "Email không hợp lệ";
        String BIRTH_DAY_INVALID = "Ngày sinh không hợp lệ";
        String PASSWORD_LIMITED = "Mật khẩu nhiều hơn 6 ký tự";
        String NAME_LIMITED = "Tên nhiều hơn 2 ký tự và ít hơn 100 ký tự";
        String WEIGHT_INVALID = "Cân nặng phải là số";
        String HEIGHT_INVALID = "Chiều cao phải là số";

    }
    public interface NOTIFICATION {
        interface REGISTRY {
            String  SUCCESSFUL = "Đăng kí thành công";
            String  UNSUCESSFUL = "Đăng kí không thành công";
        }
        interface LOGIN {
            String SUCCESSFUL = "Đăng nhập thành công";
        }
    }

    public static final int REQUEST_TIMEOUT = 60;

    public interface SHARE_PREFERENCES_NAME {
        String LOGIN_PROFILE = "LOGIN_PROFILE";
        String LOGIN_PHONE_NUMBER_VERIFY = "LOGIN_PHONE_NUMBER_VERIFY";
        String REGISTRY_INFO = "REGISTRY_INFO";
    }

    public interface REGEX {
        String EMAIL = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String NUMERIC = "[-+]?\\d*\\.?\\d+";
    }

    public static final String DATE_FORMAT = "yyyy-MM-dd";
}
