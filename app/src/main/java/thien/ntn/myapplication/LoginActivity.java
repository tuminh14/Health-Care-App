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

public class LoginActivity extends AppCompatActivity {

    EditText editEmail, editPassword;
    Button btnSignIn, btnRegister;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        AnhXa();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editEmail.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"enter email address",Toast.LENGTH_SHORT).show();
                }else {
                    if (editEmail.getText().toString().trim().matches(emailPattern)) {
                        Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (editEmailRegister.getText().toString().equals("") || editPasswordRegister.getText().toString().equals("")){
//                    Toast.makeText(LoginActivity.this, "Bạn chưa điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Intent sub1 = new Intent(LoginActivity.this, RegisterActivity.class);
//                    startActivity(sub1);
//                }
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

}
