package thien.ntn.Health_are_app.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import thien.ntn.Health_are_app.config.Constants;
import thien.ntn.Health_are_app.util.FileStream;
import thien.ntn.Health_are_app.worker.GetStepByDayWorker;
import thien.ntn.Health_are_app.worker.SaveStepByDayWorker;
import thien.ntn.myapplication.R;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TestFragmentActivity extends Fragment {
    private static final int REQUEST_WRITE_STORAGE = 112;

    //Reading/Writing the steps related history on to/from a local storage file


    private Intent intentMainactivity;

    String[] lineDetail = new String[4];
    ArrayList<String> lines = new ArrayList<String>(); //Array list to store each line from the file
    ArrayList<Date> dates = new ArrayList<Date>();

    boolean isPushSuccess = false;
    int iterFile = 0;
    int lengthFile = 0;
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
                String token = "";
                //get file
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(Constants.STEP_COUNTER_FILE.FILE_TEMP), "Cp1252"), 100); //Open the file to read
                    String line;
                    ArrayList<String> lineDetails = new ArrayList<String>();
                    while ((line = br.readLine()) != null) { //Read each Line from the file

                        lineDetail = line.split("\t");
                        lineDetails.add(line);
                        //Split the line by tab and store in a string array
                        lines.add(lineDetail[0] + "\t" + lineDetail[1] + "\t" + lineDetail[2] + "\t" + lineDetail[3]);
                        //String interm = lineDetail[0].split(" ")[0];
                        String dateAndTime = lineDetail[0];

                        SimpleDateFormat formatter3 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date date3=formatter3.parse(dateAndTime);
                        dates.add(date3);
                    }
                    br.close();//Close the Buffer reader
                    lengthFile = lineDetails.size();
                    for (int i = 0; i< lineDetails.size(); i++ ) {
                        lineDetail = lineDetails.get(i).split("\t");
                        String dateAndTime = lineDetail[0];
                        String dateStart = dateAndTime.split(" ")[0];
                        String timeStart = dateAndTime.split(" ")[1];
                        String step = lineDetail[1];
                        String distance= lineDetail[2];
                        String totalTime = lineDetail[3];
                        JSONObject requestJson = new JSONObject();
                        requestJson.put("date",dateStart);
                        requestJson.put("time", timeStart);
                        requestJson.put("step", step);
                        requestJson.put("totalDistance", distance);
                        requestJson.put("totalTime", totalTime);
                        SharedPreferences sp = getActivity().getSharedPreferences(Constants.SHARE_PREFERENCES_NAME.LOGIN_PROFILE, Context.MODE_PRIVATE);
                        //Đọc dữ liệu
                        token = sp.getString("token", "");
                        postData(requestJson, token, lineDetail);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    isPushSuccess = true;
                    try {
                        SharedPreferences sp = getActivity().getSharedPreferences(Constants.SHARE_PREFERENCES_NAME.LOGIN_PROFILE, Context.MODE_PRIVATE);
                        token = sp.getString("token", "");
                        getData(token);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

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
    private void postData(JSONObject requestJson, String token, String[] fileText) {
        Data.Builder requestData = new Data.Builder();
        requestData.putString("request", requestJson.toString());
        requestData.putString("token", token);
        // Create worker
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(SaveStepByDayWorker.class)
                .setInputData(requestData.build())
                .build();

        // Push worker to queue
        WorkManager.getInstance().enqueue(oneTimeWorkRequest);
        WorkManager.getInstance().getWorkInfoByIdLiveData(oneTimeWorkRequest.getId()).observe(this, workStatus -> {
            try {

                if (workStatus.getState() == WorkInfo.State.SUCCEEDED) {
                    iterFile +=1;
                    Data data = workStatus.getOutputData();
                    String result = data.getString("result");

                    JSONObject request = new JSONObject(result);

                    Boolean isSuccess = request.getBoolean("success");

                    if (!isSuccess) {
                        JSONObject error = request.getJSONObject("error");
                        String msgError;
                        try {
                            // Get order error message
                            msgError = error.getString("error");
                        } catch (JSONException e) {
                            // get validate message
                            String errorString = error.toString().replaceAll("[\"{}]","");
                            String[] ListError = errorString.split("[,:]");
                            // select first validate message error
                            msgError = ListError[1];
                        }
                        Toast.makeText(getActivity(), msgError, Toast.LENGTH_SHORT).show();
                        FileStream.Save(Constants.STEP_COUNTER_FILE.FILE_TEMP_ERROR, Constants.STEP_COUNTER_FILE.DIR_TEMP_ERROR, fileText);
                        if (iterFile >= lengthFile) {
                            saveAndLoadFile();
                        }
//                        setShowButton();
                    } else {
                        FileStream.Save(Constants.STEP_COUNTER_FILE.FILE,Constants.STEP_COUNTER_FILE.DIR ,  fileText);
                        if (iterFile >= lengthFile) {
                            saveAndLoadFile();
                        }
//                        setShowButton();
                    }

                } else {
                    if (workStatus.getState() == WorkInfo.State.FAILED) {
                        iterFile +=1;
                        Data data = workStatus.getOutputData();
                        String msgError = data.getString("result");
                        Toast.makeText(getActivity(), msgError, Toast.LENGTH_SHORT).show();
                        FileStream.Save(Constants.STEP_COUNTER_FILE.FILE_TEMP_ERROR, Constants.STEP_COUNTER_FILE.DIR_TEMP_ERROR,fileText);
                        if (iterFile >= lengthFile) {
                            getData(token);
                        }
//                        setShowButton();
                    }
                }
            } catch (JSONException | IOException e) {
                Toast.makeText(getActivity(), Constants.ERROR_MSG.UNKNOWN_ERROR, Toast.LENGTH_SHORT).show();
                try {
                    FileStream.Save(Constants.STEP_COUNTER_FILE.FILE_TEMP_ERROR, Constants.STEP_COUNTER_FILE.DIR_TEMP_ERROR,fileText);
                    if (iterFile >= lengthFile) {
                        saveAndLoadFile();
                    }
                } catch (IOException er) {
                }
//                setShowButton();
            }

        });
    }

    public void getData(String token) throws IOException {
        Data.Builder requestData = new Data.Builder();
        requestData.putString("token", token);
        // Create worker
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(GetStepByDayWorker.class)
                .setInputData(requestData.build())
                .build();

        // Push worker to queue
        WorkManager.getInstance().enqueue(oneTimeWorkRequest);
        WorkManager.getInstance().getWorkInfoByIdLiveData(oneTimeWorkRequest.getId()).observe(this, workStatus -> {
            try {

                if (workStatus.getState() == WorkInfo.State.SUCCEEDED) {
                    Data data = workStatus.getOutputData();
                    String result = data.getString("result");

                    JSONObject request = new JSONObject(result);

                    Boolean isSuccess = request.getBoolean("success");

                    if (!isSuccess) {
                        JSONObject error = request.getJSONObject("error");
                        String msgError;
                        try {
                            // Get order error message
                            msgError = error.getString("error");
                        } catch (JSONException e) {
                            // get validate message
                            String errorString = error.toString().replaceAll("[\"{}]","");
                            String[] ListError = errorString.split("[,:]");
                            // select first validate message error
                            msgError = ListError[1];
                        }
                        Toast.makeText(getActivity(), msgError, Toast.LENGTH_SHORT).show();


//                        setShowButton();
                    } else {
                        try {
                            Constants.STEP_COUNTER_FILE.FILE.delete();
                            JSONArray payloadArray = request.getJSONArray("payload");
                            for (int i = 0; i <= payloadArray.length(); i++) {
                                JSONObject curDate = payloadArray.getJSONObject(i);

                                JSONArray h0 = curDate.getJSONArray("h0");
                                JSONArray h1 = curDate.getJSONArray("h1");
                                JSONArray h2 = curDate.getJSONArray("h2");
                                JSONArray h3 = curDate.getJSONArray("h3");
                                JSONArray h4 = curDate.getJSONArray("h4");
                                JSONArray h5 = curDate.getJSONArray("h5");
                                JSONArray h6 = curDate.getJSONArray("h6");
                                JSONArray h7 = curDate.getJSONArray("h7");
                                JSONArray h8 = curDate.getJSONArray("h8");
                                JSONArray h9 = curDate.getJSONArray("h9");
                                JSONArray h10 = curDate.getJSONArray("h10");
                                JSONArray h11 = curDate.getJSONArray("h11");
                                JSONArray h12 = curDate.getJSONArray("h12");
                                JSONArray h13 = curDate.getJSONArray("h13");
                                JSONArray h14 = curDate.getJSONArray("h14");
                                JSONArray h15 = curDate.getJSONArray("h15");
                                JSONArray h16 = curDate.getJSONArray("h16");
                                JSONArray h17 = curDate.getJSONArray("h17");
                                JSONArray h18 = curDate.getJSONArray("h18");
                                JSONArray h19 = curDate.getJSONArray("h19");
                                JSONArray h20 = curDate.getJSONArray("h20");
                                JSONArray h21 = curDate.getJSONArray("h21");
                                JSONArray h22 = curDate.getJSONArray("h22");
                                JSONArray h23 = curDate.getJSONArray("h23");
                                loadHourData(h0);
                                loadHourData(h1);
                                loadHourData(h2);
                                loadHourData(h3);
                                loadHourData(h4);
                                loadHourData(h5);
                                loadHourData(h6);
                                loadHourData(h7);
                                loadHourData(h8);
                                loadHourData(h9);
                                loadHourData(h10);
                                loadHourData(h11);
                                loadHourData(h12);
                                loadHourData(h13);
                                loadHourData(h14);
                                loadHourData(h15);
                                loadHourData(h16);
                                loadHourData(h17);
                                loadHourData(h18);
                                loadHourData(h19);
                                loadHourData(h20);
                                loadHourData(h21);
                                loadHourData(h22);
                                loadHourData(h23);
                                saveAndLoadFile();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                            saveAndLoadFile();
                        }
                    }
                    } else {
                    if (workStatus.getState() == WorkInfo.State.FAILED) {
                        Data data = workStatus.getOutputData();
                        String msgError = data.getString("result");
                        Toast.makeText(getActivity(), msgError, Toast.LENGTH_SHORT).show();
                        saveAndLoadFile();
//                        setShowButton();
                    }
                }
            } catch (JSONException | IOException e) {
                Toast.makeText(getActivity(), Constants.ERROR_MSG.UNKNOWN_ERROR, Toast.LENGTH_SHORT).show();
                try {
                    saveAndLoadFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
//                setShowButton();
            }

        });
    }

    private void loadHourData(JSONArray hour) throws JSONException, IOException {
            for (int j= 0; j < hour.length(); j++) {
                JSONObject curHour = hour.getJSONObject(j);
                String time = curHour.getString("time");
                String date = curHour.getString("date");
                String step = curHour.getString("step");
                String totalTime = curHour.getString("totalTime");
                String totalDistance = curHour.getString("totalDistance");

                String[] line = {date+ " " + time,step,totalTime,totalDistance};
                FileStream.Save(Constants.STEP_COUNTER_FILE.FILE, Constants.STEP_COUNTER_FILE.DIR, line);
            }
        }

    private void saveAndLoadFile() throws IOException {
            if (Constants.STEP_COUNTER_FILE.FILE_TEMP_ERROR.exists()) {
                if (Constants.STEP_COUNTER_FILE.FILE_TEMP.exists()) {
                    FileStream.copyFileUsingStream(Constants.STEP_COUNTER_FILE.FILE_TEMP_ERROR, Constants.STEP_COUNTER_FILE.FILE_TEMP);
                    Constants.STEP_COUNTER_FILE.FILE_TEMP_ERROR.delete();
                }

            } else {
                if (Constants.STEP_COUNTER_FILE.FILE_TEMP.exists()) {
                    Constants.STEP_COUNTER_FILE.FILE_TEMP.delete();
                }
            }

        Intent intentScore = new Intent(getActivity(), TrackStepsActivity.class);
        intentScore.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intentScore);

    }

}
