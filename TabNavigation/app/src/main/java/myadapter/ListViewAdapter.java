package myadapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import thien.ntn.myapplication.R;

public class ListViewAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] maintitle;
    private final String[] subtitle;
    private final Integer[] imgid;

    public ListViewAdapter(Activity context, String[] maintitle, String[] subtitle, Integer[] imgid){
        super(context, R.layout.ui_list_view, maintitle);
        this.context = context;
        this.maintitle = maintitle;
        this.subtitle = subtitle;
        this.imgid = imgid;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View row = inflater.inflate(R.layout.ui_list_view, null, true);
        TextView title = (TextView) row.findViewById(R.id.lv_title);
        TextView sub_title = (TextView) row.findViewById(R.id.lv_subtitle);
        ImageView img = (ImageView) row.findViewById(R.id.lv_img);

        title.setText(maintitle[position]);
        sub_title.setText(subtitle[position]);
        img.setImageResource(imgid[position]);
        return row;
    }
}
