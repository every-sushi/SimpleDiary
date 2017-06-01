package rlaskfud0515.kr.hs.emirim.simplediary;

import android.content.Context;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileOutputStream fout = openFileOutput(filename, Context.MODE_PRIVATE);
                    String str = edit.getText().toString();
                    fout.write(str.getBytes());
                    fout.close();
                    Toast.makeText(MainActivity.this, "정상적으로 "+filename+"파일이 저장되었습니다.", Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

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
        FileInputStream fin = null;
        try{
            fin=openFileInput(filename);
            byte[] buf = new byte[500];
            fin.read(buf);
            diaryStr=new String(buf);
            but.setText("수정");
            fin.close();
        }catch (FileNotFoundException e){
            edit.setText("일기가 존재하지 않습니다.");
            but.setText("새로저장");
        }catch (IOException e) {
            e.printStackTrace();
        }

        try{
            fin.close();

        }catch (IOException e){
            e.printStackTrace();
        }

        return diaryStr;
    }
}
