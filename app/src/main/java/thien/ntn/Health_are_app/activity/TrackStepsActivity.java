package thien.ntn.Health_are_app.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import thien.ntn.Health_are_app.config.Constants;
import thien.ntn.Health_are_app.worker.DeleteAllStepWorker;
import thien.ntn.Health_are_app.worker.GetStepByDayWorker;
import thien.ntn.myapplication.R;

public class TrackStepsActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<ListviewContent> listviewContents;
    private static ListviewAdap adapter, adapterTemp;

    private Intent intentMainactivity;
    //Nome temp file
    String[] lineDetail = new String[4];
    ArrayList<String> lines = new ArrayList<String>(); //Array list to store each line from the file
    ArrayList<Date> dates = new ArrayList<Date>();

    //Temp file
    String[] lineDetailTemp = new String[4];
    ArrayList<String> linesTemp = new ArrayList<String>(); //Array list to store each line from the file
    ArrayList<Date> datesTemp = new ArrayList<Date>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_steps);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        buttonClickListener();

        GraphView graph = (GraphView) findViewById(R.id.graph);
        GridLabelRenderer glr = graph.getGridLabelRenderer();
        glr.setPadding(60);
        graph.getViewport().setScrollable(true);

        // Get data to from temp file
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(Constants.STEP_COUNTER_FILE.FILE), "Cp1252"), 100); //Open the file to read
            String line;

            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
            while ((line = br.readLine()) != null) { //Read each Line from the file

                lineDetail = line.split("\t"); //Split the line by tab and store in a string array
                lines.add(lineDetail[0] + "\t" + lineDetail[1] + "\t" + lineDetail[2] + "\t" + lineDetail[3]);
                //String interm = lineDetail[0].split(" ")[0];
                String interm1 = lineDetail[0];
                SimpleDateFormat formatter3 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date3=formatter3.parse(interm1);
                dates.add(date3);

               // dates.add(new Date(interm1));
            }

            //<Ve bieu do, khong can co the xoa
            Iterator it1 = lines.iterator();
            Iterator it2 = dates.iterator();
            DataPoint[] dp = new DataPoint[25];
            while (it1.hasNext() && it2.hasNext()) {
                Date a = (Date)it2.next();
                Integer b = Integer.parseInt(it1.next().toString().split("\t")[1]);
                series.appendData(//new DataPoint(4, 6)
                        new DataPoint(a, b),true,100 //new DataPoint(new Date(2018, 04, 16), 40),
//                        new DataPoint(a, b),true,100 //new DataPoint(new Date(2018, 04, 16), 40),
                         );
            }
            //series.appendData(new DataPoint(new Date(2018,04,16),20),true,100);
            graph.addSeries(series);

            // set date label formatter
            glr.setLabelFormatter(new DateAsXAxisLabelFormatter(graph.getContext()));
            glr.setNumHorizontalLabels(3);

            graph.getViewport().setXAxisBoundsManual(true);

            graph.getGridLabelRenderer().setHumanRounding(false);
            // />

            br.close();//Close the Buffer reader
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Get data to from temp file
        try {
            BufferedReader brTemp = new BufferedReader(new InputStreamReader(new FileInputStream(Constants.STEP_COUNTER_FILE.FILE_TEMP), "Cp1252"), 100); //Open the file to read
            String lineTemp;

            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
            while ((lineTemp = brTemp.readLine()) != null) { //Read each Line from the file

                lineDetailTemp = lineTemp.split("\t"); //Split the line by tab and store in a string array
                linesTemp.add(lineDetailTemp[0] + "\t" + lineDetailTemp[1] + "\t" + lineDetailTemp[2] + "\t" + lineDetailTemp[3]);
                //String interm = lineDetail[0].split(" ")[0];
                String interm1Temp = lineDetailTemp[0];
                SimpleDateFormat formatter3 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date3Temp= formatter3.parse(interm1Temp);
                datesTemp.add(date3Temp);

            }

            //<Ve bieu do, khong can co the xoa
            Iterator it1 = lines.iterator();
            Iterator it2 = dates.iterator();
            DataPoint[] dp = new DataPoint[25];
            while (it1.hasNext() && it2.hasNext()) {

                Date a = (Date)it2.next();
                Integer b = Integer.parseInt(it1.next().toString().split("\t")[1]);
                series.appendData(//new DataPoint(4, 6)
                        new DataPoint(a, b),true,100 //new DataPoint(new Date(2018, 04, 16), 40),

//                        new DataPoint(a, b),true,100 //new DataPoint(new Date(2018, 04, 16), 40),
                );
            }
            //series.appendData(new DataPoint(new Date(2018,04,16),20),true,100);
            graph.addSeries(series);

            // set date label formatter
            glr.setLabelFormatter(new DateAsXAxisLabelFormatter(graph.getContext()));
            glr.setNumHorizontalLabels(3);

            graph.getViewport().setXAxisBoundsManual(true);

            graph.getGridLabelRenderer().setHumanRounding(false);
            // />

            brTemp.close();//Close the Buffer reader
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Khoi tao list view
        listView=(ListView)findViewById(R.id.list);

        // Khoi tao data de do vao listview
        listviewContents = new ArrayList<>();
        Iterator iter = lines.iterator();
        int i =0;

        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        // Lay data tu arraylits da tao do vao listview none temp
        while(iter.hasNext()){
            String str = iter.next().toString();
            double distance = Double.parseDouble(str.split("\t")[2]);
            listviewContents.add(new ListviewContent(str.split("\t")[0], str.split("\t")[1]+" steps", decimalFormat.format(distance).toString()+" feets",str.split("\t")[3],str.split("\t")[3]+ " mins"));
        }

        adapter= new ListviewAdap(listviewContents,getApplicationContext());
        listView.setAdapter(adapter);

        Iterator iterTemp = linesTemp.iterator();
        int iTemp = 0;

        // Lay data tu arraylits da tao do vao listview temp
        while(iterTemp.hasNext()){
            String strTemp = iterTemp.next().toString();
            double distanceTemp = Double.parseDouble(strTemp.split("\t")[2]);
            listviewContents.add(new ListviewContent(strTemp.split("\t")[0], strTemp.split("\t")[1]+" steps", decimalFormat.format(distanceTemp).toString()+" feets",strTemp.split("\t")[3],strTemp.split("\t")[3]+ " mins"));
        }

        adapterTemp= new ListviewAdap(listviewContents,getApplicationContext());
        listView.setAdapter(adapterTemp);
    }

    private void buttonClickListener() {
        Button clear = (Button) findViewById(R.id.clear_data);
        Button back = (Button) findViewById(R.id.button_back);
        //Play Button onclick listener
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                SharedPreferences sp = getSharedPreferences(Constants.SHARE_PREFERENCES_NAME.LOGIN_PROFILE, Context.MODE_PRIVATE);
                String token = sp.getString("token", "");
                try {
                    getData(token);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        //Handling back button click
        //Going back from current StepDetector Activity to Main Activity
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentMainactivity = new Intent(TrackStepsActivity.this, MainActivity.class);
                intentMainactivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentMainactivity);
            }
        });
    }

    public void getData(String token) throws IOException {
        Data.Builder requestData = new Data.Builder();
        requestData.putString("token", token);
        // Create worker
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(DeleteAllStepWorker.class)
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
                        Toast.makeText(getApplicationContext(), msgError, Toast.LENGTH_SHORT).show();


//                        setShowButton();
                    } else {
                        if (Constants.STEP_COUNTER_FILE.FILE.exists()) {
                            Constants.STEP_COUNTER_FILE.FILE.delete();
                        }
                        if (Constants.STEP_COUNTER_FILE.FILE_TEMP.exists()) {
                            Constants.STEP_COUNTER_FILE.FILE_TEMP.delete();
                        }
                        String msg = "Data cleared";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        intentMainactivity = new Intent(TrackStepsActivity.this, TrackStepsActivity.class);
                        intentMainactivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intentMainactivity);

                    }
                } else {
                    if (workStatus.getState() == WorkInfo.State.FAILED) {
                        Data data = workStatus.getOutputData();
                        String msgError = data.getString("result");
                        Toast.makeText(getApplicationContext(), msgError, Toast.LENGTH_SHORT).show();
//                        setShowButton();
                    }
                }
            } catch (JSONException e ) {
                Toast.makeText(getApplicationContext(), Constants.ERROR_MSG.UNKNOWN_ERROR, Toast.LENGTH_SHORT).show();

            }

        });
    }
}
