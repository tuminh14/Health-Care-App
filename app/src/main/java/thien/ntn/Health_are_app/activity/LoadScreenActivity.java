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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import thien.ntn.Health_are_app.config.Constants;
import thien.ntn.Health_are_app.worker.LoginWorker;
import thien.ntn.Health_are_app.worker.SaveStepByDayWorker;
import thien.ntn.myapplication.R;

public class LoadScreenActivity extends AppCompatActivity {

    private String token = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);
        getSupportActionBar().hide();
        SharedPreferences sp = getSharedPreferences(Constants.SHARE_PREFERENCES_NAME.LOGIN_PROFILE, Context.MODE_PRIVATE);
        //Đọc dữ liệu
        token = sp.getString("token", "");

        if (token != "") {
            Intent intentMainactivity= new Intent(this, MainActivity.class);
            intentMainactivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentMainactivity);
        } else {
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.apply();
            SharedPreferences spLogin = getSharedPreferences(Constants.SHARE_PREFERENCES_NAME.REGISTRY_INFO, Context.MODE_PRIVATE);
            SharedPreferences.Editor editLogin = spLogin.edit();
            editLogin.clear();
            editLogin.apply();
            Intent intentMainactivity= new Intent(this, LoginActivity.class);
            intentMainactivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentMainactivity);
        }


    }


}
