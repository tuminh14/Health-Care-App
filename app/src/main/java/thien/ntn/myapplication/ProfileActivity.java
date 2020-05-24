package thien.ntn.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class ProfileActivity extends Fragment {
    TextView txtName, txtGender, txtWeight, txtHeight, txtPhoneNumber, txtBirthDay, txtEmail;
    String strFileName = "FileName.txt";
    String strFileGender = "FileGender.txt";
    String strFileWeight = "FileWeight.txt";
    String strFileHeight = "FileHeight.txt";
    String strFilePhoneNumber = "FilePhoneNumber.txt";
    String strFileBirthDay = "FileBirthDay.txt";
    String strFileEmail = "FileEmail.txt";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_profile, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnhXa();
        String strName = readFile(strFileName);
        txtName.setText(strName);
        String strGender = readFile(strFileGender);
        txtGender.setText(strGender);
        String strWeight = readFile(strFileWeight);
        txtWeight.setText(strWeight);
        String strHeight = readFile(strFileHeight);
        txtHeight.setText(strHeight);
        String strPhoneNumber = readFile(strFilePhoneNumber);
        txtPhoneNumber.setText(strPhoneNumber);
        String strBirthDay = readFile(strFileBirthDay);
        txtBirthDay.setText(strBirthDay);
        String strEmail = readFile(strFileEmail);
        txtEmail.setText(strEmail);
    }

    public void AnhXa(){
        txtName = (TextView) getView().findViewById(R.id.txt_name);
        txtGender = (TextView) getView().findViewById(R.id.txt_gender);
        txtWeight = (TextView) getView().findViewById(R.id.txt_weight);
        txtHeight = (TextView) getView().findViewById(R.id.txt_height);
        txtPhoneNumber = (TextView) getView().findViewById(R.id.txt_phone);
        txtBirthDay = (TextView) getView().findViewById(R.id.txt_birth_day);
        txtEmail = (TextView) getView().findViewById(R.id.txt_email);
    }

    public String readFile(String strFileInfo) {
        try {
            FileInputStream fileInputStream = getActivity().openFileInput(strFileInfo);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();

            String lines;
            while ((lines = bufferedReader.readLine()) != null) {
                stringBuffer.append(lines + "\n");
            }
            Toast.makeText(getActivity(), stringBuffer.toString(), Toast.LENGTH_SHORT).show();
            Log.d("tets: ", stringBuffer.toString());

            return stringBuffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "false1";
    }
}


