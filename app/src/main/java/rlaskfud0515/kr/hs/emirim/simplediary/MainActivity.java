package rlaskfud0515.kr.hs.emirim.simplediary;

import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    DatePicker date;
    EditText edit;
    Button but;
    String filename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date = (DatePicker)findViewById(R.id.date_pick);
        edit = (EditText)findViewById(R.id.edit1);
        but = (Button)findViewById(R.id.but1);

        Calendar cal= Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day =cal.get(Calendar.DATE);

        date.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                filename=year+"_"+(month+1)+" "+day+".txt";
                String readData=readDiary(filename);
                edit.setText(readData);
                but.setEnabled(true);

            }
        }); //변경되었을때 핸들러
    }
    public String readDiary(String filename){
        String diaryStr=null;
        try{
            FileInputStream fin=openFileInput(filename);
            byte[] buf = new byte[500];
            fin.read(buf);
            diaryStr=new String(buf);
            but.setText("수정");
        }catch (FileNotFoundException e){
            edit.setText("일기가 존재하지 않습니다.");
            but.setText("새로저장");
        }catch (IOException e){

        }

        return diaryStr;
    }
}
