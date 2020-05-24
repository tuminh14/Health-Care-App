package thien.ntn.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import model.UserInformation;

public class RegisterActivity extends AppCompatActivity {

    EditText editName, editGender, editWeight, editHeight, editPhoneNumber, editBirthDay, editEmail, editPassword;
    Button btnCreate;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public UserInformation userInformation;

    private String test = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        AnhXa();

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                                       postData();

//                if (editName.getText().toString().isEmpty() || editGender.getText().toString().isEmpty() ||
//                        editWeight.getText().toString().isEmpty() || editHeight.getText().toString().isEmpty() ||
//                        editPhoneNumber.getText().toString().isEmpty() || editBirthDay.getText().toString().isEmpty() ||
//                        editEmail.getText().toString().isEmpty() || editPassword.getText().toString().isEmpty()
//                ){
//                    Toast.makeText(RegisterActivity.this, "Bạn chưa điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                }
//                else {
////                     postData();
////                    new TestAsyncTask().execute();
////                    Toast.makeText(RegisterActivity.this, "test = " + test, Toast.LENGTH_SHORT).show();
//
//                    if (!editEmail.getText().toString().trim().matches(emailPattern)) {
//                        Toast.makeText(getApplicationContext(),"Email không hợp lệ",Toast.LENGTH_SHORT).show();
//                    } else if (!validateJavaDate(editBirthDay.getText().toString().trim())){
//                        Toast.makeText(getApplicationContext(),"Ngày sinh không hợp lệ",Toast.LENGTH_SHORT).show();
//                    } else if(editPassword.getText().toString().trim().length() < 6){
//                        Toast.makeText(getApplicationContext(),"Mật khẩu nhiều hơn 6 ký tự",Toast.LENGTH_SHORT).show();
//                    } else if (editName.getText().toString().trim().length() < 2 ||
//                            editName.getText().toString().trim().length() > 100) {
//                        Toast.makeText(getApplicationContext(),"Tên nhiều hơn 2 ký tự và ít hơn 100 ký tự",Toast.LENGTH_SHORT).show();
//                    } else if (isNumeric(editWeight.getText().toString().trim()) == false){
//                        Toast.makeText(getApplicationContext(),"Cân nặng phải là số",Toast.LENGTH_SHORT).show();
//                    } else if (isNumeric(editHeight.getText().toString().trim()) == false){
//                        Toast.makeText(getApplicationContext(),"Chiều cao phải là số",Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        postData();
//
//                    }
//                }
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

        editName=(EditText)findViewById(R.id.edit_text_name);
        editGender=(EditText)findViewById(R.id.edit_text_gender);
        editWeight=(EditText)findViewById(R.id.edit_text_weight);
        editHeight=(EditText)findViewById(R.id.edit_text_height);
        editPhoneNumber=(EditText)findViewById(R.id.edit_text_phoneNumber);
        editBirthDay=(EditText)findViewById(R.id.edit_text_birthDay);
        editEmail=(EditText)findViewById(R.id.edit_text_email);
        editPassword=(EditText)findViewById(R.id.edit_text_password);

        btnCreate=(Button)findViewById(R.id.btn_create);
    }

    public void postData() {
        RequestQueue requestQueue=Volley.newRequestQueue(RegisterActivity.this);
        String url="http://165.22.107.58/api/user/registry";
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
//            object.put("email",editEmail.getText().toString());
//            object.put("passWord",editPassword.getText().toString());
//            object.put("phoneNumber",editPhoneNumber.getText().toString());
//            object.put("fullName",editName.getText().toString());
//            object.put("gender",editGender.getText().toString());
//            object.put("weight",editWeight.getText().toString());
//            object.put("height",editHeight.getText().toString());
//            object.put("birthDay",editBirthDay.getText().toString());

            object.put("email","thiengmail.com");
            object.put("passWord","121212");
            object.put("phoneNumber","0918290203");
            object.put("fullName","Thien");
            object.put("gender","Nam");
            object.put("weight","12");
            object.put("height","12");
            object.put("birthDay","2020-02-02");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
//                            JSONObject jsonObj = response.getJSONObject("payload");
//                            Boolean success = Boolean.valueOf(response.getString("success"));
                            String success = response.getString("success");
                            Toast.makeText(RegisterActivity.this, success, Toast.LENGTH_SHORT).show();
//                            String statusSuccess = new String(); // Trạng thái khi invalid input
//                            statusSuccess = jsonObj.getString("gender");

//                            userInformation = new UserInformation(jsonObj.getString("email"), "passWord",
//                                    jsonObj.getString("phoneNumber"), jsonObj.getString("fullName"),
//                                    jsonObj.getString("gender"), jsonObj.getString("weight"),
//                                    jsonObj.getString("height"), jsonObj.getString("birthDay"));
//                            String user = userInformation.getName();

//                            Toast.makeText(RegisterActivity.this, "String Response : " + user, Toast.LENGTH_SHORT).show();
//                            Log.d("user: ", user);
//                            String s = response.getString("success");
//                            Toast.makeText(RegisterActivity.this, "hi", Toast.LENGTH_SHORT).show();
//                            Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_SHORT).show();
//                            Intent sub1 = new Intent(RegisterActivity.this, LoginActivity.class);
//                            startActivity(sub1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "Error getting response", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }



    private class TestAsyncTask extends AsyncTask<Void, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            postData();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

    }

}
