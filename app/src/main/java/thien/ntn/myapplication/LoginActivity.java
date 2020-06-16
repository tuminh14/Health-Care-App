package thien.ntn.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
                    Toast.makeText(LoginActivity.this, "Bạn chưa điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    postData();
                    SharedPreferences sp = getSharedPreferences("Save", MODE_PRIVATE);
                    //Đọc dữ liệu
                    String resIsSuccess = sp.getString("success", null);
                    if (resIsSuccess.equals("false")){
                        Toast.makeText(getApplicationContext(),"Email hoặc mật khẩu không hợp lệ",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                        Intent sub1 = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(sub1);
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

    public void postData() {
        RequestQueue requestQueue=Volley.newRequestQueue(LoginActivity.this);
        String url="http://165.22.107.58/api/user/login";
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
            object.put("email",editEmail.getText().toString());
            object.put("passWord",editPassword.getText().toString());

//            object.put("email","duongtrantuminh14@gmail.com");
//            object.put("passWord","adminroot");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObjectPayload = response.getJSONObject("payload");
                            JSONObject jsonObjectUser = jsonObjectPayload.getJSONObject("user");

                            // Luu thong tin check dang nhap
                            Boolean isSuccess = response.getBoolean("success");
                            String strIsSuccess = String.valueOf(isSuccess);

                            // Luu thong tin hien thi profile
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

//                            writeFile(fullName, strFileName);
//                            writeFile(gender, strFileGender);
//                            writeFile(email, strFileEmail);
//                            writeFile(phoneNumber, strFilePhoneNumber);
//                            writeFile(birthDay, strFileBirthDay);
//                            writeFile(weight, strFileWeight);
//                            writeFile(height, strFileHeight);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Error getting response", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

}
