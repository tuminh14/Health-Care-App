package thien.ntn.Health_are_app.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import thien.ntn.myapplication.R;

public class TestReportActivity extends AppCompatActivity {
    private TextView txtStepWalked, txtAvgSpeed, txtCaloriesBurnt, txtTime, txtStepsPerMinute;
    boolean actiityRunning;

    private Intent intentMainactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_report);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        buttonClickListener();

        AnhXa();

        Bundle extras = getIntent().getExtras();
        String[] value = extras.getStringArray("report");

        DecimalFormat decim = new DecimalFormat("0.00");

        txtStepWalked.setText(value[1]);
        txtTime.setText(value[3]);
        txtAvgSpeed.setText(String.format("%.2f",(Double.parseDouble(value[2])/Double.parseDouble(value[3]))* 0.00508 ));


//        txtStepWalked.setText(value[1]+"steps");
//        txtTime.setText(value[3]+"min");
//        txtAvgSpeed.setText(String.format("%.2f",(Double.parseDouble(value[2])/Double.parseDouble(value[3]))* 0.00508 )+"m/sec");

        //Calculating calories burnt
        double conversationFactor = Double.parseDouble(value[2])/63;           //(5280/10) ==> (1 mile in feet / 0.8333 feet one step
        double CaloriesBurned = Double.parseDouble(value[1]) * conversationFactor;
        txtCaloriesBurnt.setText(String.format("%.2f",CaloriesBurned));

        txtStepsPerMinute.setText(String.format("%.2f",(Double.parseDouble(value[1])/Double.parseDouble(value[3]))));

    }

    private void buttonClickListener() {
        Button back = (Button) findViewById(R.id.button_back);
        //Handling back button click
        //Going back from current StepDetector Activity to Main Activity
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentMainactivity = new Intent(TestReportActivity.this, StepDetectorActivity.class);
                intentMainactivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentMainactivity);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        AnhXa();

        Bundle extras = getIntent().getExtras();
        String[] value = extras.getStringArray("report");

        txtStepWalked.setText(value[1]);
        //txtTime.setText(value[3]);
        txtTime.setText(String.format("%.2f",(Double.parseDouble(value[3]))));
        txtAvgSpeed.setText(String.format("%.2f",(Double.parseDouble(value[2])/Double.parseDouble(value[3]))* 0.00508 ));


//        txtStepWalked.setText(value[1]+"steps");
//        txtTime.setText(value[3]+"min");
//        txtAvgSpeed.setText(String.format("%.2f",(Double.parseDouble(value[2])/Double.parseDouble(value[3]))* 0.00508 )+"m/sec");

        //Calculating calories burnt
        double conversationFactor = Double.parseDouble(value[2])/63;           //(5280/10) ==> (1 mile in feet / 0.8333 feet one step
        double CaloriesBurned = Double.parseDouble(value[1]) * conversationFactor;
        txtCaloriesBurnt.setText(String.format("%.2f",CaloriesBurned));

        txtStepsPerMinute.setText(String.format("%.2f",(Double.parseDouble(value[1])/Double.parseDouble(value[3]))));
    }

    private void AnhXa(){
        txtStepWalked = (TextView)findViewById(R.id.txt_step_walked);
        txtAvgSpeed = (TextView)findViewById(R.id.txt_avg_speed);
        txtCaloriesBurnt = (TextView)findViewById(R.id.txt_calories_burnt);
        txtTime = (TextView)findViewById(R.id.txt_time);
        txtStepsPerMinute = (TextView)findViewById(R.id.txt_steps_per_minute);
    }
}
