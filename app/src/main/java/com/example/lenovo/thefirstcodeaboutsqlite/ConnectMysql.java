package com.example.lenovo.thefirstcodeaboutsqlite;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectMysql {
    public static final String NAME="root";
    public static final String PASS="1234";
    public static final String URL="jdbc:mysql://10.0.2.2:3306/orders";
    private Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle=new Bundle();
            bundle=msg.getData();
            //打印获取的值
            Log.i("ConnectMysql", "handleMessage: "+bundle.get("name"));
            Log.i("ConnectMysql", "handleMessage: "+bundle.get("pass"));
            Log.i("ConnectMysql", "handleMessage: "+bundle.get("number"));
        }
    };
    //连接数据库
    public void connect()
    {
        //开启一个新线程访问MySQL
        new Thread(new Runnable()
        {
            private Connection connection;
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    connection= DriverManager.getConnection(URL,NAME,PASS);
                    Log.d("ConnectMysql", "run: 数据库连接成功");
                    readDate(connection);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void readDate(Connection connection) {

        try {
            String sql="select * from user";
            Statement statement=connection.createStatement();
            ResultSet set=statement.executeQuery(sql);
            Bundle bundle=new Bundle();
            while (set.next())
            {
                bundle.clear();
                bundle.putString("name",set.getString(1));
                bundle.putString("pass",set.getString(2));
                bundle.putString("number",set.getString(3));
                Message message=new Message();
                message.setData(bundle);
                //用handler将数据传出
                handler.sendMessage(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //关闭数据库
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
