package thien.ntn.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText editName, editGender, editWeight, editHeight, editPhoneNumber, editBirthDay, editEmail, editPassword;
    Button btnCreate;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        AnhXa();

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editName.getText().toString().isEmpty() || editGender.getText().toString().isEmpty() ||
                        editWeight.getText().toString().isEmpty() || editHeight.getText().toString().isEmpty() ||
                        editPhoneNumber.getText().toString().isEmpty() || editBirthDay.getText().toString().isEmpty() ||
                        editEmail.getText().toString().isEmpty() || editPassword.getText().toString().isEmpty()
                ){
                    Toast.makeText(RegisterActivity.this, "Bạn chưa điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (editEmail.getText().toString().trim().matches(emailPattern)) {
                        Toast.makeText(getApplicationContext(),"Email hợp lệ",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"Email không hợp lệ", Toast.LENGTH_SHORT).show();
                        postData();
                        Toast.makeText(RegisterActivity.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                        Intent sub1 = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(sub1);
                    }
                }
            }
        });
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
        String url="http://www.voidbraw.com/api/user/registry";
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
//            object.put("parameter","value");
//            object.put("parameter","value");
            object.put("email",editEmail.getText().toString());
            object.put("passWord",editPassword.getText().toString());
            object.put("phoneNumber",editPhoneNumber.getText().toString());
            object.put("fullName",editName.getText().toString());
            object.put("gender",editGender.getText().toString());
            object.put("weight",editWeight.getText().toString());
            object.put("height",editHeight.getText().toString());
            object.put("birthDay",editBirthDay.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObj = response.getJSONObject("payload");
                            String gender = jsonObj.getString("gender");
                            Toast.makeText(RegisterActivity.this, "String Response : " + gender, Toast.LENGTH_SHORT).show();
//                            result_textView.setText("String Response : " + gender);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "Error getting response", Toast.LENGTH_SHORT).show();
//                result_textView.setText("Error getting response");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

}
