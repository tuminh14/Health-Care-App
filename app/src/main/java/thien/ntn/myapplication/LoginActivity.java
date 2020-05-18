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

    TextView textViewSignInOrSignUp;
    EditText editEmail, editName, editCountry, editUsername, editPassword;
    Button btnSignIn, btnCreateOrRegister;

    int i=0;

    int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        AnhXa();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendGetRequest();
                Intent sub1 = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(sub1);
            }
        });

        btnCreateOrRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(i==0)
                {
                    i=1;
                    textViewSignInOrSignUp.setText("Sign Up");
                    editEmail.setVisibility(View.VISIBLE);
                    editName.setVisibility(View.VISIBLE);
                    editCountry.setVisibility(View.VISIBLE);
                    btnSignIn.setVisibility(View.GONE);
                    btnCreateOrRegister.setText("CREATE ACCOUNT");
                }
                else{
                    textViewSignInOrSignUp.setText("Sign In");
                    btnCreateOrRegister.setText("REGISTER");
                    editEmail.setVisibility(View.GONE);
                    editName.setVisibility(View.GONE);
                    editCountry.setVisibility(View.GONE);
                    btnSignIn.setVisibility(View.VISIBLE);
                    i=0;
                }

            }
        });
    }


    public void AnhXa(){
        textViewSignInOrSignUp = (TextView)findViewById(R.id.text_view_sign_in_or_sign_up);

        editEmail=(EditText)findViewById(R.id.edit_text_email);
        editName=(EditText)findViewById(R.id.edit_text_name);
        editCountry=(EditText)findViewById(R.id.edit_text_country);
        editUsername=(EditText)findViewById(R.id.edit_text_username);
        editPassword=(EditText)findViewById(R.id.edit_text_password);

        btnSignIn=(Button)findViewById(R.id.btn_sign_in);
        btnCreateOrRegister=(Button)findViewById(R.id.btn_create_or_register);
    }

//    private void postRequest() {
//        RequestQueue requestQueue=Volley.newRequestQueue(MainActivity.this);
//        String url="http://10.246.132.196/get-post-android/post_data.php";
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                //let's parse json data
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    post_response_text.setText("Data 1 : " + jsonObject.getString("data_1_post")+"\n");
//                    post_response_text.append("Data 2 : " + jsonObject.getString("data_2_post")+"\n");
//                    post_response_text.append("Data 3 : " + jsonObject.getString("data_3_post")+"\n");
//                    post_response_text.append("Data 4 : " + jsonObject.getString("data_4_post")+"\n");
//                }
//                catch (Exception e){
//                    e.printStackTrace();
//                    post_response_text.setText("POST DATA : unable to Parse Json");
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                post_response_text.setText("Post Data : Response Failed");
//            }
//        }){
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params=new HashMap<String, String>();
//                params.put("data_1_post","Value 1 Data");
//                params.put("data_2_post","Value 2 Data");
//                params.put("data_3_post","Value 3 Data");
//                params.put("data_4_post","Value 4 Data");
//                return params;
//            }
//
//            @Override
//            public Map<String,String> getHeaders() throws AuthFailureError {
//                Map<String,String> params=new HashMap<String, String>();
//                params.put("Content-Type","application/x-www-form-urlencoded");
//                return params;
//            }
//        };
//
//        requestQueue.add(stringRequest);
//
//    }

    private void sendGetRequest() {
        //get working now
        //let's try post and send some data to server
        RequestQueue queue= Volley.newRequestQueue(LoginActivity.this);
        String url="http://10.246.132.196/get-post-android/get_data.php";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                 String s = "Data : "+response;
                 Log.d("Data: ", s);
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    get_response_text.setText("Data 1 :"+jsonObject.getString("data_1")+"\n");
//                    get_response_text.append("Data 2 :"+jsonObject.getString("data_2")+"\n");
//                    get_response_text.append("Data 3 :"+jsonObject.getString("data_3")+"\n");
//                }
//                catch (Exception e){
//                    e.printStackTrace();
//                    get_response_text.setText("Failed to Parse Json");
//                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Data: ", "Response Failed");
            }
        });

        queue.add(stringRequest);
    }

}
