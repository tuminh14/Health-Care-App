package thien.ntn.Health_are_app.activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import thien.ntn.myapplication.R;


public class HomeActivity extends Fragment {

    ListView listView;
    ArrayList<ListviewContent> listviewContents;
    private static ListviewAdap adapter;

    //Reading/Writing the steps related history on to/from a local storage file
    public String path  = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Step Counter";
    File myDirs = new File(path);
    File file =new File(path+"/stepCountHistory.txt");

    private Intent intentMainactivity;

    String[] lineDetail = new String[4];
    ArrayList<String> lines = new ArrayList<String>(); //Array list to store each line from the file
    ArrayList<Date> dates = new ArrayList<Date>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home, container, false);



    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GraphView graph = (GraphView)getView().findViewById(R.id.graph);
        GridLabelRenderer glr = graph.getGridLabelRenderer();
        glr.setPadding(60);
        graph.getViewport().setScrollable(true);
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Cp1252"), 100); //Open the file to read
            String line;

            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
            while ((line = br.readLine()) != null) { //Read each Line from the file

                lineDetail = line.split("\t"); //Split the line by tab and store in a string array
                lines.add(lineDetail[0] + "\t" + lineDetail[1] + "\t" + lineDetail[2] + "\t" + lineDetail[3]);
                //String interm = lineDetail[0].split(" ")[0];
                String interm1 = lineDetail[0];
                SimpleDateFormat formatter3 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
                Date date3=formatter3.parse(interm1);
                dates.add(date3);

                // dates.add(new Date(interm1));
            }

            Iterator it1 = lines.iterator();
            Iterator it2 = dates.iterator();
            DataPoint[] dp = new DataPoint[25];
            while (it1.hasNext() && it2.hasNext()) {
                Date a = (Date)it2.next();
                Integer b = Integer.parseInt(it1.next().toString().split("\t")[1]);
                series.appendData(//new DataPoint(4, 6)
                        new DataPoint(a, b),true,100 //new DataPoint(new Date(2018, 04, 16), 40),
                );
            }
            //series.appendData(new DataPoint(new Date(2018,04,16),20),true,100);
            graph.addSeries(series);

            // set date label formatter
            glr.setLabelFormatter(new DateAsXAxisLabelFormatter(graph.getContext()));
            glr.setNumHorizontalLabels(3);

            graph.getViewport().setXAxisBoundsManual(true);

            graph.getGridLabelRenderer().setHumanRounding(false);


            br.close();//Close the Buffer reader
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

}
