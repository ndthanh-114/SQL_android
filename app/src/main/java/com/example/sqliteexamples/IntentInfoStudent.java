package com.example.sqliteexamples;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class IntentInfoStudent extends Activity{
    EditText dataReceived;
    Button btnDone;
    TextView mssv;
    TextView hoTen;
    TextView ngaySinh;
    TextView email;
    TextView diaChi;
    SQLiteDatabase db;
    Button troVe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_student);

        String dataPath = getFilesDir() + "/student_data";
        db = SQLiteDatabase.openDatabase(dataPath, null, SQLiteDatabase.OPEN_READONLY);
        mssv=findViewById(R.id.text_mssv);
        hoTen=findViewById(R.id.text_hoten);
        ngaySinh=findViewById(R.id.text_ngay_sinh);
        email=findViewById(R.id.text_email);
        diaChi=findViewById(R.id.text_dia_chi);
        troVe=findViewById(R.id.btn_troVe);
        troVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent myLocalIntent = getIntent();

        Bundle myBundle =  myLocalIntent.getExtras();
        String mssvIntent = myBundle.getString("mssv");

        String sql= "select * from sinhvien where mssv="+ mssvIntent;
        Cursor cs=db.rawQuery(sql, null);
        cs.moveToPosition(0);

        mssv.setText("MSSV: "+cs.getString(0));
        hoTen.setText("Họ tên: "+cs.getString(1));
        ngaySinh.setText("Ngày sinh: "+cs.getString(2));
        email.setText("Email: "+cs.getString(3));
        diaChi.setText("Địa chỉ: "+cs.getString(4));
// return sending an OK signal to calling activity
        setResult(Activity.RESULT_OK, myLocalIntent);


    }//onCreate

    protected void onStop() {
        db.close();
        super.onStop();
    }
}


