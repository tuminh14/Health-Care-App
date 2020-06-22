package thien.ntn.Health_are_app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import thien.ntn.Health_are_app.config.Constants;
import thien.ntn.Health_are_app.worker.LoginWorker;
import thien.ntn.Health_are_app.worker.VerifyPhoneNumberWorker;
import thien.ntn.myapplication.R;

public class VerifyPhoneNumberActivity extends AppCompatActivity {

    EditText editVerifyCode;
    TextView textViewPhoneNumber;
    Button btnContinue, btnSkip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);
        getSupportActionBar().hide();
        AnhXa();

        SharedPreferences sp = getSharedPreferences(Constants.SHARE_PREFERENCES_NAME.LOGIN_PHONE_NUMBER_VERIFY, Context.MODE_PRIVATE);
        //Đọc dữ liệu
        String phoneNumber = sp.getString("phoneNumber", "");
        textViewPhoneNumber.setText(phoneNumber);

        btnContinue.setOnClickListener(view -> {
            if (editVerifyCode.getText().toString().isEmpty() ) {
                Toast.makeText(VerifyPhoneNumberActivity.this, "Bạn chưa điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
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

        btnSkip.setOnClickListener(view -> {
            Intent sub1 = new Intent(VerifyPhoneNumberActivity.this, LoginPhoneNumberActivity.class);
            startActivity(sub1);
        });
    }

    public void AnhXa(){
        editVerifyCode=(EditText)findViewById(R.id.edit_text_verify_code);
        textViewPhoneNumber = (TextView)findViewById(R.id.textView_phone_number);
        btnContinue=(Button)findViewById(R.id.btn_continue);
        btnSkip=(Button)findViewById(R.id.btn_skip);
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
            String verifyCode = editVerifyCode.getText().toString();
            String phoneNumber = textViewPhoneNumber.getText().toString();
            requestJson = new JSONObject();
            requestJson.put("phoneNumber", phoneNumber);
            requestJson.put("verifyCode", verifyCode);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), Constants.ERROR_MSG.UNKNOWN_ERROR, Toast.LENGTH_SHORT).show();
            setShowButton();
        }

        Data requestData = new Data.Builder().putString("request", requestJson.toString()).build();
        // Create worker
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(VerifyPhoneNumberWorker.class)
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
                        Toast.makeText(VerifyPhoneNumberActivity.this, msgError, Toast.LENGTH_SHORT).show();
                        setShowButton();
                    } else {
                        JSONObject payloadObject = request.getJSONObject("payload");
                        JSONObject jsonObjectUser = payloadObject.getJSONObject("user");
                        // Luu thong tin hien thi profile
                        String strIsSuccess = String.valueOf(isSuccess);
                        String fullName = jsonObjectUser.getString("fullName");
                        String gender = jsonObjectUser.getString("gender");
                        String email = jsonObjectUser.getString("email");
                        String phoneNumber = jsonObjectUser.getString("phoneNumber");
                        String birthDay = jsonObjectUser.getString("birthDay");
                        String weight = jsonObjectUser.getString("weight");
                        String height = jsonObjectUser.getString("height");
                        String token = jsonObjectUser.getString("token");

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
                        editor.putString("token", "Bearer " + token);

                        editor.putString("success", strIsSuccess);
                        //Hoàn thành
                        editor.commit();
                        Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent sub1 = new Intent(VerifyPhoneNumberActivity.this, MainActivity.class);
                        startActivity(sub1);
                        setShowButton();
                    }

                } else {
                    if (workStatus.getState() == WorkInfo.State.FAILED) {
                        Data data = workStatus.getOutputData();
                        String msgError = data.getString("result");
                        Toast.makeText(VerifyPhoneNumberActivity.this, msgError, Toast.LENGTH_SHORT).show();
                        setShowButton();
                    }
                }
            } catch (JSONException e) {
                Toast.makeText(VerifyPhoneNumberActivity.this, Constants.ERROR_MSG.UNKNOWN_ERROR, Toast.LENGTH_SHORT).show();
                setShowButton();
            }

        });
    }
}
