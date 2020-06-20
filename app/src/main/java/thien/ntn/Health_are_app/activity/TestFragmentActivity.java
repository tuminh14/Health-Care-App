package thien.ntn.Health_are_app.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thien.ntn.Health_are_app.config.Constants;
import thien.ntn.myapplication.R;



import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

import thien.ntn.myapplication.R;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class TestFragmentActivity extends Fragment {
    private static final int REQUEST_WRITE_STORAGE = 112;

    //Reading/Writing the steps related history on to/from a local storage file


    private Intent intentMainactivity;

    String[] lineDetail = new String[4];
    ArrayList<String> lines = new ArrayList<String>(); //Array list to store each line from the file
    ArrayList<Date> dates = new ArrayList<Date>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_test_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Checking for writing permission
        boolean hasPermission = (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        //If write permission is not allowed request user to allow
        if (!hasPermission) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //On screen button listener
        buttonClickListener();
    }

    private void buttonClickListener(){

        Button start = (Button) getView().findViewById(R.id.button_start);
        Button track = (Button) getView().findViewById(R.id.button_track);
        Button help = (Button) getView().findViewById(R.id.button_help);
        Button exit = (Button) getView().findViewById(R.id.button_exit);

        //Start count Button onclick listener
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0){
                Intent intentPlay = new Intent(getActivity(), StepDetectorActivity.class);
                intentPlay.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intentPlay);
            }
        });

        //Track steps Button onclick listener
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0){

                //get file
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(Constants.STEP_COUNTER_FILE.FILE_TEMP), "Cp1252"), 100); //Open the file to read
                    String line;

                    while ((line = br.readLine()) != null) { //Read each Line from the file

                        lineDetail = line.split("\t"); //Split the line by tab and store in a string array
                        lines.add(lineDetail[0] + "\t" + lineDetail[1] + "\t" + lineDetail[2] + "\t" + lineDetail[3]);
                        //String interm = lineDetail[0].split(" ")[0];
                        String interm1 = lineDetail[0];
                        SimpleDateFormat formatter3 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
                        Date date3=formatter3.parse(interm1);
                        dates.add(date3);
                    }

                    br.close();//Close the Buffer reader
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent intentScore = new Intent(getActivity(), TrackStepsActivity.class);
                intentScore.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intentScore);
            }
        });

        //Help Button onclick listener
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0){
                Intent intentHelp = new Intent(getActivity(), HelpActivity.class);
                intentHelp.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intentHelp);
            }
        });

        //Exit App Button onclick listener
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0){
                Intent intentExit = new Intent(Intent.ACTION_MAIN);
                intentExit.addCategory(Intent.CATEGORY_HOME);
                intentExit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentExit);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //reload my activity with permission granted or use the features what required the permission
                    Toast.makeText(getActivity(), "The app was allowed to access storage", Toast.LENGTH_LONG).show();
                } else
                {
                    Toast.makeText(getActivity(), "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

}
