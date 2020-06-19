package thien.ntn.Health_are_app.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import org.json.JSONException;
import org.json.JSONObject;

import thien.ntn.Health_are_app.config.Constants;
import thien.ntn.Health_are_app.model.UserInformation;
import thien.ntn.Health_are_app.validation.RegisterValidation;
import thien.ntn.Health_are_app.worker.LoginWorker;
import thien.ntn.Health_are_app.worker.RegisterWorker;
import thien.ntn.myapplication.R;

public class RegisterActivity extends AppCompatActivity {

    EditText editName, editGender, editWeight, editHeight, editPhoneNumber, editBirthDay, editEmail, editPassword;
    Button btnCreate;
    public UserInformation userInformation;

    private String test = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        AnhXa();

        btnCreate.setOnClickListener(view -> {

            if (editTextInfoIsEmpty()) {
                Toast.makeText(RegisterActivity.this, Constants.ERROR_MSG.INFO_IS_EMPTY, Toast.LENGTH_SHORT).show();
            }
            else {
               if (isValidInfo()) {
                    postData();
                }
            }
        });
    }

    private boolean isValidInfo() {
        if (!RegisterValidation.isEmail(editEmail.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), Constants.ERROR_MSG.EMAIL_INVALID,Toast.LENGTH_SHORT).show();
            return false;
        } else if (!RegisterValidation.isDate(editBirthDay.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),Constants.ERROR_MSG.BIRTH_DAY_INVALID,Toast.LENGTH_SHORT).show();
            return false;
        } else if(!RegisterValidation.isValidPassword(editPassword.getText().toString().trim())){
            Toast.makeText(getApplicationContext(), Constants.ERROR_MSG.PASSWORD_LIMITED,Toast.LENGTH_SHORT).show();
            return false;
        } else if (!RegisterValidation.isValidName(editName.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), Constants.ERROR_MSG.NAME_LIMITED,Toast.LENGTH_SHORT).show();
            return false;
        } else if (!RegisterValidation.isNumeric(editWeight.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),Constants.ERROR_MSG.WEIGHT_INVALID,Toast.LENGTH_SHORT).show();
            return false;
        } else if (!RegisterValidation.isNumeric(editHeight.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),Constants.ERROR_MSG.HEIGHT_INVALID,Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    
    private boolean editTextInfoIsEmpty() {
        return editName.getText().toString().isEmpty() ||
                editGender.getText().toString().isEmpty() ||
                editWeight.getText().toString().isEmpty() ||
                editHeight.getText().toString().isEmpty() ||
                editPhoneNumber.getText().toString().isEmpty() ||
                editBirthDay.getText().toString().isEmpty() ||
                editEmail.getText().toString().isEmpty() ||
                editPassword.getText().toString().isEmpty() ;

    }

    public void AnhXa(){
        editName=(EditText)findViewById(R.id.edit_text_name);
        editGender=(EditText)findViewById(R.id.edit_text_gender);
        editWeight=(EditText)findViewById(R.id.edit_text_weight);
        editHeight=(EditText)findViewById(R.id.edit_text_height);
        editPhoneNumber=(EditText)findViewById(R.id.edit_text_verify_code);
        editBirthDay=(EditText)findViewById(R.id.edit_text_birthDay);
        editEmail=(EditText)findViewById(R.id.edit_text_email);
        editPassword=(EditText)findViewById(R.id.edit_text_password);

        btnCreate=(Button)findViewById(R.id.btn_create);
    }
    private void setShowButton() {
        btnCreate.setVisibility(View.VISIBLE);
        btnCreate.setEnabled(true);
    }

    private void setHideButton() {
        btnCreate.setEnabled(false);
        btnCreate.setVisibility(View.INVISIBLE);
    }
    public void postData() {
        setHideButton();
        JSONObject requestJson = new JSONObject();
        try {
            //input your API parameters
            requestJson.put("email",editEmail.getText().toString().trim());
            requestJson.put("passWord",editPassword.getText().toString().trim());
            requestJson.put("phoneNumber",editPhoneNumber.getText().toString().trim());
            requestJson.put("fullName",editName.getText().toString().trim());
            requestJson.put("gender",editGender.getText().toString().trim());
            requestJson.put("weight",editWeight.getText().toString().trim());
            requestJson.put("height",editHeight.getText().toString().trim());
            requestJson.put("birthDay",editBirthDay.getText().toString().trim());

//            requestJson.put("email","thien123123@gmail.com");
//            requestJson.put("passWord","121212");
//            requestJson.put("phoneNumber","0918290203");
//            requestJson.put("fullName","Thien");
//            requestJson.put("gender","Nam");
//            requestJson.put("weight","12");
//            requestJson.put("height","12");
//            requestJson.put("birthDay","2020-02-02");

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), Constants.ERROR_MSG.UNKNOWN_ERROR, Toast.LENGTH_SHORT).show();
            setShowButton();
        }
        Data requestData = new Data.Builder().putString("request", requestJson.toString()).build();
        // Create worker
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(RegisterWorker.class)
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
                        Toast.makeText(RegisterActivity.this, msgError, Toast.LENGTH_SHORT).show();
                        setShowButton();
                    } else {
                        JSONObject paloadObject = request.getJSONObject("payload");
                        String email = paloadObject.getString("email");


                        SharedPreferences sp = getSharedPreferences(Constants.SHARE_PREFERENCES_NAME.REGISTRY_INFO, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.clear();
                        //Lưu dữ liệu
                        editor.putString("email", email);
                        editor.putString("password",editPassword.getText().toString().trim());
                        //Hoàn thành
                        editor.commit();
                        Toast.makeText(getApplicationContext(), Constants.NOTIFICATION.REGISTRY.SUCCESSFUL, Toast.LENGTH_SHORT).show();
                        Intent sub1 = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(sub1);
                        setShowButton();
                    }

                } else {
                    if (workStatus.getState() == WorkInfo.State.FAILED) {
                        Data data = workStatus.getOutputData();
                        String msgError = data.getString("result");
                        Toast.makeText(RegisterActivity.this, msgError, Toast.LENGTH_SHORT).show();
                        setShowButton();
                    }
                }
            } catch (JSONException e) {
                Toast.makeText(RegisterActivity.this, Constants.NOTIFICATION.REGISTRY.UNSUCESSFUL, Toast.LENGTH_SHORT).show();
                setShowButton();
            }

        });
    }
}
