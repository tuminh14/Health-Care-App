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
                postData();
                SharedPreferences sp = getSharedPreferences("Save", MODE_PRIVATE);
                //Đọc dữ liệu

                String ssss = sp.getString("fullName", null);
                Toast.makeText(LoginActivity.this, "aa + " + ssss, Toast.LENGTH_SHORT).show();
//                if (editEmail.getText().toString().isEmpty() || editPassword.getText().toString().isEmpty()) {
//                    Toast.makeText(LoginActivity.this, "Bạn chưa điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    if (!editEmail.getText().toString().trim().matches(emailPattern)) {
//                        Toast.makeText(getApplicationContext(),"Email không hợp lệ",Toast.LENGTH_SHORT).show();
//                    } else if(editPassword.getText().toString().trim().length() < 6){
//                        Toast.makeText(getApplicationContext(),"Mật khẩu nhiều hơn 6 ký tự",Toast.LENGTH_SHORT).show();
//                    } else {
//                        postData();
//                    }
//                }
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

    public static boolean isNumeric(String str) {
        return str.matches("[-+]?\\d*\\.?\\d+");
    }

    public static boolean validateJavaDate(String strDate)
    {
        /*
         * Set preferred date format,
         * For example MM-dd-yyyy, MM.dd.yyyy,dd.MM.yyyy etc.*/
        SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
        sdfrmt.setLenient(false);
        /* Create Date object
         * parse the string into date
         */
        try
        {
            Date javaDate = sdfrmt.parse(strDate);
            System.out.println(strDate+" is valid date format");
        }
        /* Date format is invalid */
        catch (ParseException e)
        {
            System.out.println(strDate+" is Invalid Date format");
            return false;
        }
        /* Return true if date format is valid */
        return true;
    }

    public void AnhXa(){
        editEmail=(EditText)findViewById(R.id.edit_text_email);
        editPassword=(EditText)findViewById(R.id.edit_text_password);

        btnSignIn=(Button)findViewById(R.id.btn_sign_in);
        btnRegister=(Button)findViewById(R.id.btn_register);
    }

    public void writeFile(String strText, String strFileInfo) {
        String textToSave = "text to save";


        try {
            FileOutputStream fileOutputStream = openFileOutput(strFileInfo, MODE_PRIVATE);
            fileOutputStream.write(strText.getBytes());
            fileOutputStream.close();

            Toast.makeText(getApplicationContext(), "Text Saved", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void postData() {
        RequestQueue requestQueue=Volley.newRequestQueue(LoginActivity.this);
        String url="http://165.22.107.58/api/user/login";
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
            //input your API parameters

//            object.put("email",editEmail.getText().toString());
//            object.put("passWord",editPassword.getText().toString());

            object.put("email","duongtrantuminh14@gmail.com");
            object.put("passWord","adminroot");

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
                            //Hoàn thành
                            editor.commit();

//                            writeFile(fullName, strFileName);
//                            writeFile(gender, strFileGender);
//                            writeFile(email, strFileEmail);
//                            writeFile(phoneNumber, strFilePhoneNumber);
//                            writeFile(birthDay, strFileBirthDay);
//                            writeFile(weight, strFileWeight);
//                            writeFile(height, strFileHeight);

                            Intent sub1 = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(sub1);
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
