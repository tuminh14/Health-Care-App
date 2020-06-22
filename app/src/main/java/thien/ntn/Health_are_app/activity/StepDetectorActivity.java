package thien.ntn.Health_are_app.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import thien.ntn.Health_are_app.config.Constants;
import thien.ntn.Health_are_app.util.FileStream;
import thien.ntn.myapplication.R;

public class StepDetectorActivity extends AppCompatActivity {

    EditText inchStep;
    TextView counter;

    Button start;
    Button stop;
    Button back;

    long startTime;
    long stopTime;
    String countedStep;
    String DetectedStep;
    static final String State_Count = "Counter";
    static final String State_Detect = "Detector";

    boolean isServiceStopped;

    //Reading/Writing the steps related history on to a local storage file



    private Intent intent,intentMainactivity;
    private static final String TAG = "SensorEvent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_detector);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        intent = new Intent(this, StepCountingService.class);

        viewInit(); // Call view initialisation method.

        stop.setVisibility(View.GONE);
    }

    /*string array to capture
    * a. start date/time,
    * b. step count
    * c. Distance walked
    * d. Total time taken*/
    String [] saveText = new String[4];

    // Initialise step_detector layout view
    //Author: Abhilash Gudasi
    public void viewInit() {

        isServiceStopped = true; //Current Service state

        start = (Button)findViewById(R.id.button_start);
        inchStep = (EditText) findViewById(R.id.edittext_inchstep);
        counter = (TextView) findViewById(R.id.counter);
        inchStep.setText("10");
        counter.setText("0");
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                back.setVisibility(View.GONE);

                start.setVisibility(View.GONE);
                stop.setVisibility(View.VISIBLE);

                String msg = "Step Counter Started";
                Toast toast = Toast.makeText(StepDetectorActivity.this,msg,Toast.LENGTH_SHORT);
                toast.show();

                //Recording date and time
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String format = sdf.format(Calendar.getInstance().getTime());
                //saveText[0] =  DateFormat.getDateTimeInstance().format(new Date());
                saveText[0] =  format;
                startTime = Calendar.getInstance().getTimeInMillis();

                // start Service.
                startService(new Intent(getBaseContext(), StepCountingService.class));

                // register BroadcastReceiver
                registerReceiver(broadcastReceiver, new IntentFilter(StepCountingService.BROADCAST_ACTION));
                isServiceStopped = false;
            }
        });

        //To stop step detector service
        stop = (Button)findViewById(R.id.button_stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isServiceStopped) {
                    start.setVisibility(View.VISIBLE);
                    stop.setVisibility(View.GONE);
                    String msg = "Step counter stopped";
                    Toast toast = Toast.makeText(StepDetectorActivity.this,msg,Toast.LENGTH_SHORT);
                    toast.show();
                    //unregisterReceiver
                    unregisterReceiver(broadcastReceiver);
                    isServiceStopped = true;

                    stopTime = Calendar.getInstance().getTimeInMillis();
                    String abc = String.valueOf(stopTime);
                    //Steps walked
                    saveText[1] = counter.getText().toString();
                    //Distance Walked in feets
                    saveText[2] = String.valueOf(0.0833 * Double.parseDouble(counter.getText().toString()) * Double.parseDouble(inchStep.getText().toString()));
                    //Total time taken in min
                    saveText[3] = String.valueOf(((stopTime - startTime)/60000)+"."+((stopTime - startTime)%60000));
                    // stop Service.
                    stopService(new Intent(getBaseContext(), StepCountingService.class));

                    //To save the current step count results on to local file

                    try {
                        FileStream.Save(Constants.STEP_COUNTER_FILE.FILE_TEMP,Constants.STEP_COUNTER_FILE.DIR_TEMP,saveText);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    //test
//                    try {
//                        BufferedReader br = new BufferedReader(new FileReader(file));
//                        String line;
//
//                        while ((line = br.readLine()) != null) {
//                            text.append(line);
//                            text.append('\n');
//                        }
//                        br.close();
//                    }
//                    catch (IOException e) {
//                        //You'll need to add proper error handling here
//                    }

                    back.setVisibility(View.VISIBLE);

                    Intent intentReportactivity = new Intent(StepDetectorActivity.this, TestReportActivity.class);
                    intentReportactivity.putExtra("report",saveText);
                    intentReportactivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intentReportactivity,1);
                }
            }
        });

        //Handling back button click
        //Going back from current StepDetector Activity to Main Activity
        back = (Button)findViewById(R.id.button_back);
        back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intentMainactivity = new Intent(StepDetectorActivity.this, MainActivity.class);
                    intentMainactivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intentMainactivity);


                }
            });

        counter = (TextView)findViewById(R.id.counter);
    }


    protected void onPause() {
        super.onPause();
    }

    // OnResume resetting to previously left state of step_detector layout
    // Author: Paras Bansal
    protected void onResume() {
        super.onResume();
        intent = new Intent(this, StepCountingService.class);
        viewInit();
    }


    // BroadcastReceiver to receive the message from the Step Detector Service
    // Author: Paras Bansal
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateViews(intent);
        }
    };

    // Retrieve and set data of counter
    // Author: Paras Bansal
    private void updateViews(Intent intent) {
        countedStep = intent.getStringExtra("Counted_Step");
        DetectedStep = intent.getStringExtra("Detected_Step");
        Log.d(TAG, String.valueOf(countedStep));
        Log.d(TAG, String.valueOf(DetectedStep));

        counter.setText(String.valueOf(DetectedStep));
    }
}