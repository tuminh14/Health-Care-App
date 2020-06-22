package thien.ntn.Health_are_app.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import thien.ntn.Health_are_app.config.Constants;
import thien.ntn.Health_are_app.service.ForegroundService;
import thien.ntn.myapplication.R;

public class MainActivity extends AppCompatActivity {

//    private ActionBar toolbar;
    private Intent intentMainactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setBackgroundDrawable(new ColorDrawable((Color.parseColor("#7b4bff"))));

        BottomNavigationView bottomNav = findViewById(R.id.nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        startService();

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new TestFragmentActivity()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public void onBackPressed()
    {
        Intent intentExit = new Intent(Intent.ACTION_MAIN);
        intentExit.addCategory(Intent.CATEGORY_HOME);
        intentExit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentExit);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            this.finish();
        }
        switch (id){
            case R.id.action_exit:

                //delete 2 files
                if (Constants.STEP_COUNTER_FILE.FILE.exists()) {
                    Constants.STEP_COUNTER_FILE.FILE.delete();
                }
                if (Constants.STEP_COUNTER_FILE.FILE_TEMP.exists()) {
                    Constants.STEP_COUNTER_FILE.FILE_TEMP.delete();
                }

                intentMainactivity = new Intent(MainActivity.this, LoginActivity.class);
                intentMainactivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentMainactivity);

                return true;
            default:
                return  super.onOptionsItemSelected(item);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_test:
                            selectedFragment = new TestFragmentActivity();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new ProfileActivity();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
    public void startService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Health app is running in background");
        ContextCompat.startForegroundService(this, serviceIntent);
    }
    public void stopService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        stopService(serviceIntent);
    }
}
