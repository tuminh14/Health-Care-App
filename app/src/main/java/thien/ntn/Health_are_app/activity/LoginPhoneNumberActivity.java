package thien.ntn.Health_are_app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import thien.ntn.Health_are_app.config.Configs;
import thien.ntn.Health_are_app.config.Constants;
import thien.ntn.Health_are_app.worker.SendVerifyPhoneNumberWorker;
import thien.ntn.myapplication.R;

public class LoginPhoneNumberActivity extends AppCompatActivity {
    EditText editPhoneNumber;
    Button btnContinue, btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone_number);
        getSupportActionBar().hide();
        AnhXa();

        btnContinue.setOnClickListener(view -> {
            if (editPhoneNumber.getText().toString().isEmpty()) {
                Toast.makeText(LoginPhoneNumberActivity.this, "Vui lòng nhập số điện thoại !", Toast.LENGTH_SHORT).show();
            }
            else {

                try {
                    postData();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), Constants.ERROR_MSG.UNKNOWN_ERROR, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sub1 = new Intent(LoginPhoneNumberActivity.this, RegisterActivity.class);
                startActivity(sub1);
            }
        });
    }

    public void AnhXa(){
        editPhoneNumber=(EditText)findViewById(R.id.edit_text_verify_code);

        btnContinue=(Button)findViewById(R.id.btn_continue);
        btnRegister=(Button)findViewById(R.id.btn_resend);
    }

    private void setShowButton() {
        btnContinue.setVisibility(View.VISIBLE);
        btnContinue.setEnabled(true);
    }

    private void setHideButton() {
        btnContinue.setEnabled(false);
        btnContinue.setVisibility(View.INVISIBLE);
    }

    private void postData() throws JSONException {
        setHideButton();
        JSONObject requestJson = new JSONObject();
        try {
            String phoneNumber =editPhoneNumber.getText().toString();
            requestJson = new JSONObject();
            requestJson.put("phoneNumber",phoneNumber);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), Constants.ERROR_MSG.UNKNOWN_ERROR, Toast.LENGTH_SHORT).show();
            setShowButton();
        }

        Data requestData = new Data.Builder().putString("request", requestJson.toString()).build();
        // Create worker
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(SendVerifyPhoneNumberWorker.class)
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
                        Toast.makeText(LoginPhoneNumberActivity.this, msgError, Toast.LENGTH_SHORT).show();
                        setShowButton();
                    } else {

                        SharedPreferences sp = getSharedPreferences(Constants.SHARE_PREFERENCES_NAME.LOGIN_PHONE_NUMBER_VERIFY, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.clear();
                        //Lưu dữ liệu
                        String phoneNumber = editPhoneNumber.getText().toString();
                        editor.putString("phoneNumber", phoneNumber );
                        editor.commit();
                        Intent sub1 = new Intent(LoginPhoneNumberActivity.this, VerifyPhoneNumberActivity.class);
                        startActivity(sub1);
                        setShowButton();
                    }

                } else {
                    if (workStatus.getState() == WorkInfo.State.FAILED) {
                        Data data = workStatus.getOutputData();
                        String msgError = data.getString("result");
                        Toast.makeText(LoginPhoneNumberActivity.this, msgError, Toast.LENGTH_SHORT).show();
                        setShowButton();
                    }
                }
            } catch (JSONException e) {
                Toast.makeText(LoginPhoneNumberActivity.this, Constants.ERROR_MSG.UNKNOWN_ERROR, Toast.LENGTH_SHORT).show();
                setShowButton();
            }

        });
    }
}
