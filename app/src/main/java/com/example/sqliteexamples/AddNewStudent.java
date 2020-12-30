package com.example.sqliteexamples;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AddNewStudent extends Activity {

    EditText mssv;
    EditText hoTen;
    EditText ngaySinh;
    EditText email;
    EditText diaChi;
    SQLiteDatabase db;
    Button troVe;
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
        troVe=findViewById(R.id.btn_done);
        Intent myLocalIntent = getIntent();
        troVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String smssv=mssv.getText().toString();
                String shoten=hoTen.getText().toString();
                String sngaysinh=ngaySinh.getText().toString();
                String semail=email.getText().toString();
                String sdiachi=diaChi.getText().toString();


                db.beginTransaction();
                try {

                    String sql = String.format("insert into sinhvien(mssv, hoten, ngaysinh, email, diachi) " +
                            "values('%s', '%s', '%s', '%s', '%s')", smssv, shoten, sngaysinh, semail, sdiachi);
                    db.execSQL(sql);
                    Toast.makeText(getBaseContext(), "Insert Success", Toast.LENGTH_LONG).show();
                    db.setTransactionSuccessful();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    db.endTransaction();
                }
                Log.v("Sucess: ", "Insert into");
                finish();
            }
        });




// return sending an OK signal to calling activity
        setResult(Activity.RESULT_OK, myLocalIntent);


    }//onCreate

    protected void onStop() {
        db.close();
        super.onStop();
    }
}
