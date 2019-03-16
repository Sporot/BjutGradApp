package p.sby.gs_qca.table1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import p.sby.gs_qca.Main.Activity.global_variance;
import p.sby.gs_qca.R;

public class Activity_basicinfo1 extends AppCompatActivity {
    private Button t1_confirm;
    private String department;
    private Spinner t1_institute;
    private Spinner t1_coursename;
    private String coursename;
    private String jsonstring;
    private String temp;
    private String institute;
    private String teacher;
    private String classroom;
    private TextView t1_classroom;
    private TextView t1_teacher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t1basicinfo);

        /*****上方功能栏****/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_t1bi); //主页上方功能条
        toolbar.setTitle("研究生教学质量评价表");

        toolbar.setTitleTextColor(getResources().getColor(R.color.white)); //设置标题颜色
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //返回上级页面
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//返回
            }
        });


        /******班级填写*******/
        t1_classroom=(TextView)findViewById(R.id.t1_classroom);


        JSONArray department;
        String sessionid;
        global_variance myssession = ((global_variance)getApplicationContext());
        sessionid =myssession.getSessionid();
        System.out.println(sessionid);



        Thread loginRunnable = new Thread(){

            @Override
            public void run() {
                super.run();
                OkHttpClient client = new OkHttpClient();
                FormBody body = new FormBody.Builder().build();
                Request request1 = new Request.Builder()
                        .addHeader("cookie", sessionid)
                        .url("http://117.121.38.95:9817/mobile/form/coursedata/getdep.ht")
                        .post(body).build();
                Call call2 = client.newCall(request1);

                try {
                    Response response2 = call2.execute();
//                    System.out.println(response2);
                    String responseData2 = response2.body().string();
//                    System.out.println(responseData2);
                    temp=responseData2.substring(responseData2.indexOf("{"),responseData2.lastIndexOf("}")+1);
//                    System.out.println(temp);
                    try {
                        JSONObject departmentlist = new JSONObject(temp);
                        myssession.setDepartment(departmentlist.getJSONArray("coursedata"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        loginRunnable.start();
        try {
            loginRunnable.join(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        department = myssession.getDepartment();
        System.out.println(department);

        List<String> listdata_institute = null;
        listdata_institute = new ArrayList<>();
//        listdata_institute.add("计算机学院");
//        listdata_institute.add("软件学院");
//        listdata_institute.add("生命学院");
//        listdata_institute.add("物理学院");
        System.out.println(department.length());
        for(int i=0;i<department.length();i++){
            try {
                listdata_institute.add(department.getJSONObject(i).get("department").toString());
//                System.out.println(department.getJSONObject(i).get("department").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        /*******所在院系选择********/

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Activity_basicinfo1.this, android.R.layout.simple_spinner_item, listdata_institute);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        t1_institute=(Spinner)findViewById(R.id.t1_institute);
        t1_institute.setAdapter(arrayAdapter);
        t1_institute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
           //     Toast.makeText(Activity_basicinfo1.this,"点击",Toast.LENGTH_SHORT).show();

                institute=(String)t1_institute.getSelectedItem();
               // System.out.println(institute);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        /*******课程名称设置********/
        List<String> listdata_coursename = null;
        listdata_coursename = new ArrayList<>();
        listdata_coursename.add("数据挖掘");
        listdata_coursename.add("算法设计");
        listdata_coursename.add("人工智能");
        listdata_coursename.add("数据库");
        ArrayAdapter<String> arrayAdapter_course = new ArrayAdapter<>(Activity_basicinfo1.this, android.R.layout.simple_spinner_item, listdata_coursename);
        arrayAdapter_course.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        t1_coursename=(Spinner)findViewById(R.id.t1_coursename);
        t1_coursename.setAdapter(arrayAdapter_course);
        t1_coursename.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
               // Toast.makeText(Activity_basicinfo1.this,"点击",Toast.LENGTH_SHORT).show();

                coursename=(String)t1_coursename.getSelectedItem();
                System.out.println(coursename);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        /*****提交按钮点击事件*******/
        //绑定按钮
        t1_confirm=(Button) findViewById(R.id.t1_confirm);

        //添加监听事件
        t1_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                classroom=t1_classroom.getText().toString();
                Intent intent=new Intent(Activity_basicinfo1.this,Activity_t1class.class);
                intent.putExtra("institute",institute);
                intent.putExtra("coursename",coursename);
                intent.putExtra("classroom",classroom);
                startActivity(intent);

            }
        });
    }











}