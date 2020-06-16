package thien.ntn.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class ProfileActivity extends Fragment {
    TextView txtName, txtGender, txtWeight, txtHeight, txtPhoneNumber, txtBirthDay, txtEmail;
    String strFileName = "FileName.txt";
    String strFileGender = "FileGender.txt";
    String strFileWeight = "FileWeight.txt";
    String strFileHeight = "FileHeight.txt";
    String strFilePhoneNumber = "FilePhoneNumber.txt";
    String strFileBirthDay = "FileBirthDay.txt";
    String strFileEmail = "FileEmail.txt";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_profile, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postData();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnhXa();
//        String strName = readFile(strFileName);
//        txtName.setText(strName);
//        String strGender = readFile(strFileGender);
//        txtGender.setText(strGender);
//        String strWeight = readFile(strFileWeight);
//        txtWeight.setText(strWeight);
//        String strHeight = readFile(strFileHeight);
//        txtHeight.setText(strHeight);
//        String strPhoneNumber = readFile(strFilePhoneNumber);
//        txtPhoneNumber.setText(strPhoneNumber);
//        String strBirthDay = readFile(strFileBirthDay);
//        txtBirthDay.setText(strBirthDay);
//        String strEmail = readFile(strFileEmail);
//        txtEmail.setText(strEmail);

        //Nhận từ MainActivity
        SharedPreferences sp = this.getActivity().getSharedPreferences("Save", Context.MODE_PRIVATE);
        //Đọc dữ liệu
        txtName.setText(sp.getString("fullName", null));
        txtGender.setText(sp.getString("gender", null));
        txtEmail.setText(sp.getString("email", null));
        txtPhoneNumber.setText(sp.getString("phoneNumber", null));
        txtBirthDay.setText(sp.getString("birthDay", null));
        txtWeight.setText(sp.getString("weight", null));
        txtHeight.setText(sp.getString("height", null));

    }

    public void AnhXa(){
        txtName = (TextView) getView().findViewById(R.id.txt_name);
        txtGender = (TextView) getView().findViewById(R.id.txt_gender);
        txtWeight = (TextView) getView().findViewById(R.id.txt_weight);
        txtHeight = (TextView) getView().findViewById(R.id.txt_height);
        txtPhoneNumber = (TextView) getView().findViewById(R.id.txt_phone);
        txtBirthDay = (TextView) getView().findViewById(R.id.txt_birth_day);
        txtEmail = (TextView) getView().findViewById(R.id.txt_email);
    }

    public void postData() {
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity().getApplicationContext());
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
                            String gender = jsonObjectUser.getString("gender");

                            String fullName = jsonObjectUser.getString("fullName");
                            Toast.makeText(getActivity(), fullName + gender, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error getting response", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public String readFile(String strFileInfo) {
        try {
            FileInputStream fileInputStream = getActivity().openFileInput(strFileInfo);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();

            String lines;
            while ((lines = bufferedReader.readLine()) != null) {
                stringBuffer.append(lines + "\n");
            }
            Toast.makeText(getActivity(), stringBuffer.toString(), Toast.LENGTH_SHORT).show();
            Log.d("tets: ", stringBuffer.toString());

            return stringBuffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "false1";
    }
}


