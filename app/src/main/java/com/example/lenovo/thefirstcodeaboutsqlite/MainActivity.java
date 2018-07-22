package com.example.lenovo.thefirstcodeaboutsqlite;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button,add,updata,delete;
    private  MyDatabaseHelper myDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.btn_create);
        add=findViewById(R.id.btn_add);
        updata=findViewById(R.id.btn_update);
        delete=findViewById(R.id.btn_delete);
        myDatabaseHelper=new MyDatabaseHelper(MainActivity.this,"Book.db",null,1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myDatabaseHelper.getWritableDatabase();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase sql=myDatabaseHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("name","The First Code");
                values.put("author","msms");
                values.put("pages",454);
                values.put("price",16.96);
                sql.insert("book",null,values);
                values.clear();
            }
        });
        updata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase sql=myDatabaseHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("price",152);
                sql.update("book",values,"name=?",new String[]{"The First Code"});
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase sql=myDatabaseHelper.getWritableDatabase();

                sql.delete("book","name=?",new String[]{"The First Code"});
            }
        });
    }
}
