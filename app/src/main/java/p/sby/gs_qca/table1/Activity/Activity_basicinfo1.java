package p.sby.gs_qca.table1.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

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
    private Spinner t1_institute;
    private Spinner t1_coursename;

    private String courseid="";
    private String teacher="";
    private String classroom="";
    private String time="";
    private String coursename="";
    private String shouldnum="";
    private String classid="";

    private String temp;
    private String data;
    private TextView t1_teacher;
    private TextView t1_classroom;
    private TextView t1_classtime;
    private TextView t1_classid;


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

        JSONArray department;
        t1_teacher=(TextView)findViewById(R.id.t1_teacher);
        t1_classroom=(TextView)findViewById(R.id.t1_classroom);
        t1_classtime=(TextView)findViewById(R.id.t1_time);
        t1_classid=(TextView)findViewById(R.id.t1_classid);
        String sessionid;
        global_variance myssession = ((global_variance)getApplicationContext());
        sessionid =myssession.getSessionid();



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
                    String responseData2 = response2.body().string();
                    temp=responseData2.substring(responseData2.indexOf("{"),responseData2.lastIndexOf("}")+1);
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
            loginRunnable.join(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        department = myssession.getDepartment();

        System.out.println(department);

        Thread GetDetail=new Thread(){
            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                OkHttpClient clientDetail = new OkHttpClient();
                System.out.println(myssession.getCourseid());
                FormBody body = new FormBody.Builder().add("id",myssession.getCourseid()).build();
                Request request = new Request.Builder()
                        .addHeader("cookie", sessionid)
                        .url("http://117.121.38.95:9817/mobile/form/coursedata/get.ht")
                        .post(body).build();
                Call call3 = clientDetail.newCall(request);
                Response response = null;
                try {
                    response = call3.execute();
                    String responseData = response.body().string();
                    temp=responseData.substring(responseData.indexOf("{"),responseData.lastIndexOf("}")+1);
                    System.out.println(temp);
                    try {
                        JSONObject CourseData=new JSONObject(temp);
                        JSONObject CourseDetail=new JSONObject(CourseData.get("coursedata").toString());
                         teacher=CourseDetail.get("teacher").toString();
                         classroom=CourseDetail.get("room").toString();
                         time=CourseDetail.get("time1").toString();
                         courseid=CourseDetail.get("id").toString();
                         shouldnum=CourseDetail.get("studentnumber").toString();
                         classid=CourseDetail.get("classid").toString();
                         System.out.print(time);
                        System.out.println(teacher);

                        System.out.println("请打印一下id"+courseid);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                t1_classtime.setText(time);
                                t1_teacher.setText(teacher);
                                t1_classroom.setText(classroom);
                                t1_classid.setText(classid);

                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        };


        Thread getCourse =new Thread(){
            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                OkHttpClient clientCourse = new OkHttpClient();
                FormBody body = new FormBody.Builder().add("department",data).build();
                Request request = new Request.Builder()
                        .addHeader("cookie", sessionid)
                        .url("http://117.121.38.95:9817/mobile/form/coursedata/getcourse.ht")
                        .post(body).build();
                Call call = clientCourse.newCall(request);
                try {
                    Response response = call.execute();
                    String responseData = response.body().string();
                    temp=responseData.substring(responseData.indexOf("{"),responseData.lastIndexOf("}")+1);
                    System.out.println("temp:  "+temp);
                    try {
                        JSONObject courselist = new JSONObject(temp);
//                        System.out.println(courselist.getJSONArray("coursedata"));
                        myssession.setCourse(courselist.getJSONArray("coursedata"));


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                JSONArray Course=myssession.getCourse();
//                                ArrayList<String> strArray = new ArrayList<> ();
                                List<String> listdata_coursename=new ArrayList<>();
                                for(int i=0;i<Course.length();i++){
                                    try {
//                                        strArray.add(Course.getJSONObject(i).get("id").toString()+Course.getJSONObject(i).get("course").toString());
                                        listdata_coursename.add(Course.getJSONObject(i).get("course").toString());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
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



                                            try {
                                                if(coursename.equals(Course.getJSONObject(pos).get("course").toString())) {
                                                    System.out.println(pos);
                                                    System.out.println("id=" + Course.getJSONObject(pos).get("id").toString());
                                                    myssession.setCourseid(Course.getJSONObject(pos).get("id").toString());
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        Thread t1=new Thread(GetDetail);
                                        t1.start();

                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        };

        List<String> listdata_institute = null;
        listdata_institute = new ArrayList<>();
        System.out.println(department.length());
        for(int i=0;i<department.length();i++){
            try {
                listdata_institute.add(department.getJSONObject(i).get("department").toString());
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

                 data=(String)t1_institute.getSelectedItem();
                System.out.println(data);
                Thread t=new Thread(getCourse);
                t.start();

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

                Intent intent=new Intent(Activity_basicinfo1.this,Activity_t1class.class);
                intent.putExtra("institute",data);
                intent.putExtra("coursename",coursename);
                intent.putExtra("teacher",teacher);
                intent.putExtra("classroom",classroom);
                intent.putExtra("time",time);
                intent.putExtra("courseid",courseid);
                intent.putExtra("shouldnum",shouldnum);
                intent.putExtra("classid",classid);
                startActivity(intent);
            }
        });
    }


}