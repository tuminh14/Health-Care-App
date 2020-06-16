package thien.ntn.Health_are_app.activity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import thien.ntn.myapplication.R;

import  static thien.ntn.myapplication.R.layout.activity_search_fragment;

public class SearchFragmentActivity extends Fragment {
    BarChart barChart;

    public static SearchFragmentActivity newInstance() {
        SearchFragmentActivity fragment = new SearchFragmentActivity();
        return fragment;
    }

    public SearchFragmentActivity() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(activity_search_fragment, container, false);

        barChart = (BarChart)view.findViewById(R.id.barChart);
//        BarChart barChart = (BarChart)findViewById(R.id.barChart);
        // truyền cứng dl cho chart
        ArrayList<BarEntry> visitors = new ArrayList<>();
        visitors.add(new BarEntry(2014, 402));
        visitors.add(new BarEntry(2015, 475));
        visitors.add(new BarEntry(2016, 508));
        visitors.add(new BarEntry(2017, 660));
        visitors.add(new BarEntry(2018, 550));
        visitors.add(new BarEntry(2019, 630));
        visitors.add(new BarEntry(2020, 470));
        // xét màu sắc, kích cỡ chữ
        BarDataSet barDataSet = new BarDataSet(visitors, "Visitors");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Bar Chart Example");
        barChart.animateY(2000);
        return  view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}

