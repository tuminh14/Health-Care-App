package thien.ntn.Health_are_app.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import thien.ntn.Health_are_app.config.Constants;
import thien.ntn.myapplication.R;


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

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnhXa();
        //Nhận từ MainActivity
        SharedPreferences sp = this.getActivity().getSharedPreferences(Constants.SHARE_PREFERENCES_NAME.LOGIN_PROFILE, Context.MODE_PRIVATE);
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



}


