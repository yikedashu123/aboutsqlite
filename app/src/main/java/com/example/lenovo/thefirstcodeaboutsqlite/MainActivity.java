package com.example.lenovo.thefirstcodeaboutsqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mCreate, mAdd, mUpdate, mDelete,mFind;
    private  MyDatabaseHelper myDatabaseHelper;
    private TextView mText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCreate =findViewById(R.id.btn_create);
        mAdd =findViewById(R.id.btn_add);
        mUpdate =findViewById(R.id.btn_update);
        mDelete =findViewById(R.id.btn_delete);
        mFind=findViewById(R.id.btn_find);
        mText=findViewById(R.id.tv_text);
        myDatabaseHelper=new MyDatabaseHelper(MainActivity.this,"Book.db",null,1);
        mFind.setOnClickListener(this);
        mCreate.setOnClickListener(this);
        mAdd.setOnClickListener(this);
        mUpdate.setOnClickListener(this);
        mDelete.setOnClickListener(this);
        //访问Mysql并显示获取的值
        ConnectMysql connectMysql=new ConnectMysql();
        connectMysql.connect();
    }

    @Override
    public void onClick(View view) {
        SQLiteDatabase sql;
        ContentValues values;
        switch (view.getId())
        {
            case R.id.btn_create:
                myDatabaseHelper.getWritableDatabase();
                break;
            case R.id.btn_add:
                sql=myDatabaseHelper.getWritableDatabase();
                values=new ContentValues();
                values.put("name","The First Code");
                values.put("author","msms");
                values.put("pages",454);
                values.put("price",16.96);
                sql.insert("book",null,values);
                values.clear();
                break;
            case R.id.btn_delete:
                sql=myDatabaseHelper.getWritableDatabase();

                sql.delete("book","name=?",new String[]{"The First Code"});
                mText.setText("");
                break;
            case R.id.btn_find:
                sql=myDatabaseHelper.getWritableDatabase();
                Cursor cursor=sql.query("book",null,null,null,null,null,null);
                if(cursor.moveToFirst())
                {
                    do{
                        mText.setText(cursor.getString(cursor.getColumnIndex("name"))+"---"+
                        cursor.getString(cursor.getColumnIndex("author"))+"---"+
                        cursor.getInt(cursor.getColumnIndex("pages"))+"---"+
                        cursor.getFloat(cursor.getColumnIndex("price")));
                    }while (cursor.moveToNext());
                }
                cursor.close();
                break;
            case R.id.btn_update:
                sql=myDatabaseHelper.getWritableDatabase();
                values=new ContentValues();
                values.put("price",152);
                sql.update("book",values,"name=?",new String[]{"The First Code"});
                break;
        }
    }
}
