package thien.ntn.Health_are_app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import thien.ntn.Health_are_app.config.Constants;
import thien.ntn.Health_are_app.worker.LoginWorker;
import thien.ntn.myapplication.R;

public class LoginActivity extends AppCompatActivity {

    EditText editEmail, editPassword;
    Button btnSignIn, btnRegister;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    String strFileName = "FileName.txt";
    String strFileGender = "FileGender.txt";
    String strFileWeight = "FileWeight.txt";
    String strFileHeight = "FileHeight.txt";
    String strFilePhoneNumber = "FilePhoneNumber.txt";
    String strFileBirthDay = "FileBirthDay.txt";
    String strFileEmail = "FileEmail.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        AnhXa();

        btnSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (editEmail.getText().toString().isEmpty() || editPassword.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Bạn chưa điền đ1ầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {

                    try {
                        postData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), Constants.ERROR_MSG.UNKNOWN_ERROR, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sub1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(sub1);
            }
        });
    }

    public void AnhXa(){
        editEmail=(EditText)findViewById(R.id.edit_text_email);
        editPassword=(EditText)findViewById(R.id.edit_text_password);

        btnSignIn=(Button)findViewById(R.id.btn_sign_in);
        btnRegister=(Button)findViewById(R.id.btn_register);
    }

    private void postData() throws JSONException {
        JSONObject requestJson = new JSONObject();
        try {
            String email =editEmail.getText().toString();
            String password = editPassword.getText().toString();
            requestJson = new JSONObject();
            requestJson.put("email",email);
            requestJson.put("passWord",password);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), Constants.ERROR_MSG.UNKNOWN_ERROR, Toast.LENGTH_SHORT).show();
        }

        Data requestData = new Data.Builder().putString("request", requestJson.toString()).build();
        // Create worker
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(LoginWorker.class)
                .setInputData(requestData)
                .build();

        // Push worker to queue
        WorkManager.getInstance().enqueue(oneTimeWorkRequest);
        WorkManager.getInstance().getWorkInfoByIdLiveData(oneTimeWorkRequest.getId()).observe(this, workStatus -> {
            try {
                if (workStatus.getState() == WorkInfo.State.SUCCEEDED) {
                    Data data = workStatus.getOutputData();
                    String result = data.getString("result");

                    JSONObject request = new JSONObject(result);

                    Boolean isSuccess = request.getBoolean("success");

                    if (!isSuccess) {
                        JSONObject error = request.getJSONObject("error");
                        String msgError;
                        try {
                            // Get order error message
                            msgError = error.getString("error");
                        } catch (JSONException e) {
                            // get validate message
                            String errorString = error.toString().replaceAll("[\"{}]","");
                            String[] ListError = errorString.split("[,:]");
                            // select first validate message error
                            msgError = ListError[1];
                        }
                        Toast.makeText(LoginActivity.this, msgError, Toast.LENGTH_SHORT).show();
                    } else {
                        JSONObject paloadObject = request.getJSONObject("payload");
                        JSONObject jsonObjectUser = paloadObject.getJSONObject("user");
                        // Luu thong tin hien thi profile
                        String strIsSuccess = String.valueOf(isSuccess);
                        String fullName = jsonObjectUser.getString("fullName");
                        String gender = jsonObjectUser.getString("gender");
                        String email = jsonObjectUser.getString("email");
                        String phoneNumber = jsonObjectUser.getString("phoneNumber");
                        String birthDay = jsonObjectUser.getString("birthDay");
                        String weight = jsonObjectUser.getString("weight");
                        String height = jsonObjectUser.getString("height");

                        SharedPreferences sp = getSharedPreferences("Save", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.clear();
                        //Lưu dữ liệu
                        editor.putString("fullName", fullName);
                        editor.putString("gender", gender);
                        editor.putString("email", email);
                        editor.putString("phoneNumber", phoneNumber);
                        editor.putString("birthDay", birthDay);
                        editor.putString("weight", weight);
                        editor.putString("height", height);

                        editor.putString("success", strIsSuccess);
                        //Hoàn thành
                        editor.commit();
                        Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent sub1 = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(sub1);

                    }

                } else {
                    if (workStatus.getState() == WorkInfo.State.FAILED) {
                        Data data = workStatus.getOutputData();
                        String msgError = data.getString("result");
                        Toast.makeText(LoginActivity.this, msgError, Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (JSONException e) {
                Toast.makeText(LoginActivity.this, Constants.ERROR_MSG.UNKNOWN_ERROR, Toast.LENGTH_SHORT).show();
            }

        });
    }
}
