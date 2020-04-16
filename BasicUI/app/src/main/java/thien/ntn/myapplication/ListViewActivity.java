package thien.ntn.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import myadapter.ListViewAdapter;

public class ListViewActivity extends AppCompatActivity {

    private ListView listView;
    String[] maintitle = {
            "Product 1", "Product 2", "Product 3", "Product 4", "Product 5"
    };
    String[] subtitle = {
            "Subtitle 1", "Subtitle 2", "Subtitle 3", "Subtitle 4", "Subtitle 5"
    };
    Integer[] imgid = {
            R.drawable.clock, R.drawable.fire,
            R.drawable.bookmark, R.drawable.destination,
            R.drawable.mail,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        ListViewAdapter adapter = new ListViewAdapter(this, maintitle, subtitle, imgid);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }
}
