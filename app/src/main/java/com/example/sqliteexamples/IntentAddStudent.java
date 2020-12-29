package com.example.sqliteexamples;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.EditText;

import io.bloco.faker.Faker;

public class IntentAddStudent extends  Activity{

    Button btnDone;
    EditText mssv;
    EditText hoTen;
    EditText ngaySinh;
    EditText email;
    EditText diaChi;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student);

        String dataPath = getFilesDir() + "/student_data";
        db = SQLiteDatabase.openDatabase(dataPath, null, SQLiteDatabase.OPEN_READWRITE);
        mssv=findViewById(R.id.edit_mssv);
        hoTen=findViewById(R.id.edit_hoten);
        ngaySinh=findViewById(R.id.edit_nam_sinh);
        email=findViewById(R.id.edit_email);
        diaChi=findViewById(R.id.edit_dia_chi);
        btnDone=findViewById(R.id.btn_done);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.beginTransaction();
                try {
                    String sql = String.format("insert into sinhvien(mssv, hoten, ngaysinh, email, diachi) " +
                            "values('%s', '%s', '%s', '%s', '%s')", mssv.getText().toString(), hoTen.getText().toString(), ngaySinh.getText().toString(), email.getText().toString(), diaChi.getText().toString());
                    db.execSQL(sql);

                    db.setTransactionSuccessful();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    db.endTransaction();
                    finish();
                }
            }
        });


//        String sql= "select * from sinhvien where mssv="+ mssvIntent;
//        Cursor cs=db.rawQuery(sql, null);
//        cs.moveToPosition(0);
//
//        mssv.setText("MSSV: "+cs.getString(0));
//        hoTen.setText("Họ tên: "+cs.getString(1));
//        ngaySinh.setText("Ngày sinh: "+cs.getString(2));
//        email.setText("Email: "+cs.getString(3));
//        diaChi.setText("Địa chỉ: "+cs.getString(4));
// return sending an OK signal to calling activity
            Intent myLocalIntent=getIntent();
        setResult(Activity.RESULT_OK, myLocalIntent);


    }//onCreate

    protected void onStop() {
        db.close();
        super.onStop();
    }
}
