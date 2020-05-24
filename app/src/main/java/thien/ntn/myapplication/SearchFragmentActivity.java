package thien.ntn.myapplication;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class SearchFragmentActivity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_search_fragment, container, false);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readFile();
    }
    public void readFile() {
        try {


//            //reading text from the file
//            FileInputStream fileIn = getActivity().openFileInput("FragmentFile.txt");
//            //write text to file
//            try {
//                FileOutputStream fileout = getActivity().openFileOutput("FragmentFile.txt", MODE_PRIVATE);
//                OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
//                outputWriter.write(response.toString());
//                outputWriter.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }}


//            displayText.setText("aa");
            FileInputStream fileInputStream = getActivity().openFileInput("TutorialFile.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();

            String lines;
            while ((lines = bufferedReader.readLine()) != null) {
                stringBuffer.append(lines + "\n");
            }
            Toast.makeText(getActivity(), stringBuffer.toString(), Toast.LENGTH_SHORT).show();
            Log.d("tets: ", stringBuffer.toString());
//            displayText.setText(stringBuffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

