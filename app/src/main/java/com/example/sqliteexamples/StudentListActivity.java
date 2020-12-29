package com.example.sqliteexamples;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.bloco.faker.Faker;

public class StudentListActivity extends AppCompatActivity {
    List<ItemModel> items;
    ListView listView;
    SQLiteDatabase db;
    StudentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("SQLite");
        actionBar.setDisplayShowCustomEnabled(true);


        String dataPath = getFilesDir() + "/student_data";
        db = SQLiteDatabase.openDatabase(dataPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);

        items=new ArrayList<>();

        String sql = "select * from sinhvien";
        Cursor c1 = db.rawQuery(sql, null);
        c1.moveToPosition(-1);
        while ( c1.moveToNext() ){
            String  mssv= c1.getString(0);
            String hoTen = c1.getString(1);
            String email = c1.getString(c1.getColumnIndex("email"));

            items.add(new ItemModel(mssv, hoTen, email));
        }

         listView = findViewById(R.id.list_students);
         adapter = new StudentAdapter(this, db, items);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        listView.setLongClickable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Intent myIntentInfoStudent = new Intent(StudentListActivity.this,
                        IntentInfoStudent.class);

                Bundle myDataBundle = new Bundle();
                ItemModel itemModel = items.get(position);
                Log.v("MSSV: ", itemModel.mssv);
                myDataBundle.putString("mssv", itemModel.mssv);

                myIntentInfoStudent.putExtras(myDataBundle);

                startActivityForResult(myIntentInfoStudent, 101);

            }
        });

    }
    public void setCurrentAdapter(StudentAdapter adapter){

        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        listView.setLongClickable(true);
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
//
//        Button buttonAdd =(Button) menu.findItem(R.id.action_add);
//        buttonAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent myAddStudent = new Intent(StudentListActivity.this,
//                        IntentAddStudent.class);
//
//                startActivityForResult(myAddStudent, 102);
//            }
//        });

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String sql="select * from sinhvien where mssv like '%"+query+"%'"+"or hoten like '%"+query+"%'";
               try{
                   Cursor cs=db.rawQuery(sql, null);
                   cs.moveToPosition(-1);

                   while ( cs.moveToNext() ){
                       String  mssv= cs.getString(0);
                       String hoTen = cs.getString(1);
                       String email = cs.getString(cs.getColumnIndex("email"));

                       items.add(new ItemModel(mssv, hoTen, email));
                   }
                   adapter=new StudentAdapter(getBaseContext(), db, items);
                   setCurrentAdapter(adapter);
               }catch(Exception e){
                   Log.v("Error: ", e.getMessage());
               }
            return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                String sql="select * from sinhvien where mssv like '%"+newText+"%'"+"or hoten like '%"+newText+"%'";
                try{
                    Cursor cs=db.rawQuery(sql, null);
                    if(cs.getCount() > 0){
                        cs.moveToPosition(-1);
                        items.clear();
                        while ( cs.moveToNext() ){
                            String  mssv= cs.getString(0);
                            String hoTen = cs.getString(1);
                            String email = cs.getString(cs.getColumnIndex("email"));

                            items.add(new ItemModel(mssv, hoTen, email));
                        }
                    }else{

                        items.set(0, new ItemModel("Khong co", "KHONG CO", "KHONG CO"));
                    }
                    adapter=new StudentAdapter(getBaseContext(), db, items);
                    setCurrentAdapter(adapter);
                }catch (Exception e){
                    Log.v("Error: ", e.getMessage());
                }

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    private void createRandomData() {
        db.beginTransaction();
        try {
            db.execSQL("create table sinhvien(" +
                    "mssv char(8) primary key," +
                    "hoten text," +
                    "ngaysinh date," +
                    "email text," +
                    "diachi text);");

            Faker faker = new Faker();
            for (int i = 0; i < 50; i++) {
                String mssv = "2017" + faker.number.number(4);
                String hoten = faker.name.name();
                String ngaysinh = faker.date.birthday(18, 22).toString();
                String email = faker.internet.email();
                String diachi = faker.address.city() + ", " + faker.address.country();
                String sql = String.format("insert into sinhvien(mssv, hoten, ngaysinh, email, diachi) " +
                        "values('%s', '%s', '%s', '%s', '%s')", mssv, hoten, ngaysinh, email, diachi);

                db.execSQL(sql);
            }

            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.endTransaction();
        }

    }
    private int getNumber(){
        Cursor cs;
        cs = db.rawQuery("select * from sinhvien", null);
        return cs.getCount();
    }
    @Override
    protected void onStop() {
        db.close();
        super.onStop();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if ((requestCode == 101 ) && (resultCode == Activity.RESULT_OK)){
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }//onActivityResult
}