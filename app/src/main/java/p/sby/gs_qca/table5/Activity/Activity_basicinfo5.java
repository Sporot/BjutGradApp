package p.sby.gs_qca.table5.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import p.sby.gs_qca.Main.Activity.global_variance;
import p.sby.gs_qca.R;
import p.sby.gs_qca.table1.Activity.Activity_basicinfo1;
import p.sby.gs_qca.util.RequestUtil;

public class Activity_basicinfo5 extends AppCompatActivity {
    private Button t5_confirminfo;
    private Spinner t5_grade;
    private Spinner t5_institute;
    private Spinner t5_major;
    private Spinner t5_teacher;
    private Spinner t5_student;
    private String sendfrom="basic";
    private String depurl="http://116.213.144.72:9817/mobile/form/reportlwdb/getdep.ht";
    private String majurl="http://116.213.144.72:9817/mobile/form/reportlwdb/getmajor.ht";
    private String teacherurl="http://116.213.144.72:9817/mobile/form/reportlwdb/getteacher.ht";
    private String studenturl="http://116.213.144.72:9817/mobile/form/reportlwdb/getstudent.ht";
    private String reporturl="http://116.213.144.72:9817/mobile/form/reportlwdb/getreport.ht";
    private String temp;
    private String data;
    private String maj;
    private String tea;
    private String stu;
    private String classroom="";
    private String type="";
    private String experts="";
    private String time="";
    private int year;
    private int month;
    private int day;
    private String reportid;

    private TextView t5_type;
    private TextView t5_classroom;
    private TextView t5_experts;
    private TextView t5_date;
    private TextView t5_year;
    private TextView t5_month;
    private TextView t5_day;

    String sessionid;                   //存储登录时cookie的字符串
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t5basicinfo);

        /*****上方功能栏****/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_t5bi); //主页上方功能条
        toolbar.setTitle("学位论文答辩评价");

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
        global_variance myssession = ((global_variance)getApplicationContext());   //声明全局变量类
        sessionid =myssession.getSessionid(); //获取本次登陆中的会话cookie

        t5_type=(TextView)findViewById(R.id.t5_grade);
        t5_experts=(TextView)findViewById(R.id.t5_group);
        t5_classroom=(TextView)findViewById(R.id.t5_place);
        t5_date=(TextView)findViewById(R.id.t5_date);
//        t5_year=(TextView)findViewById(R.id.t5_year);
//        t5_month=(TextView)findViewById(R.id.t5_month);
//        t5_day=(TextView)findViewById(R.id.t5_day);

        Thread getdet=new Thread(){
            public void run() {
                super.run();
                HashMap<String, String> paramsMap = new HashMap<>();
                paramsMap.put("department", data);
                paramsMap.put("major", maj);
                paramsMap.put("teacher", tea);
                paramsMap.put("student", stu);
                temp = RequestUtil.get().MapSend(reporturl, sessionid, paramsMap);
                try {
                    System.out.println(temp);
                    JSONObject reportlist = new JSONObject(temp); //接收json对象
                    JSONArray detail=reportlist.getJSONArray("ReportLwdb");
                    type=detail.getJSONObject(0).get("type").toString();
                    classroom=detail.getJSONObject(0).get("room").toString();
                    time=detail.getJSONObject(0).get("time").toString();
                    reportid=detail.getJSONObject(0).get("id").toString();
                    experts=detail.getJSONObject(0).get("experts").toString();
                    Date time1;
                    Calendar c=Calendar.getInstance();

//                    try {
//                        time1=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(time);
//                        System.out.println(time1);
//                        c.setTime(time1);
//                        year=c.get(Calendar.YEAR);
//                        month=c.get(Calendar.MONTH);
//                        day=c.get(Calendar.DATE);
//                        System.out.println(year);
//                        System.out.println(month);
//                        System.out.println(day);
//
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    新修改注释掉此处
//                    myssession.setGrad_student(studentlist.getJSONArray("ReportLwdb")); //从json对象中提取出相应数组
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        t5_type.setText(type);
                        t5_classroom.setText(classroom);
                        t5_experts.setText(experts);
                        t5_date.setText(time);

                    }
                });
            }


        };

        Thread getStudent=new Thread(){
            @Override
            public void run() {
                super.run();
                HashMap<String, String> paramsMap = new HashMap<>();
                paramsMap.put("department", data);
                paramsMap.put("major", maj);
                paramsMap.put("teacher", tea);
                temp = RequestUtil.get().MapSend(studenturl, sessionid, paramsMap);
                try {
                    System.out.println(temp);
                    JSONObject studentlist = new JSONObject(temp); //接收json对象
//                    System.out.print(teacherlist);
                    myssession.setGrad_student(studentlist.getJSONArray("ReportLwdb")); //从json对象中提取出相应数组
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONArray Student = myssession.getGrad_student();
                        List<String> listdata_students = new ArrayList<>();
                        System.out.println(Student);
                        for (int i = 0; i < Student.length(); i++) {
                            try {
                                listdata_students.add(Student.getJSONObject(i).get("studentname").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<String> arrayAdapter_student = new ArrayAdapter<>(Activity_basicinfo5.this, android.R.layout.simple_spinner_item, listdata_students);
                        arrayAdapter_student.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                        t5_student = (Spinner) findViewById(R.id.t5_student);
                        t5_student.setAdapter(arrayAdapter_student);
                        t5_student.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                                stu=(String)t5_student.getSelectedItem();
                                System.out.println(stu);
                                if(stu!=null) {
                                    Thread t3 = new Thread(getdet);
                                    t3.start();
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    }
                    });
            }

        };

        Thread getTeacher=new Thread(){
            @Override
            public void run() {
                super.run();
                HashMap<String,String> paramsMap=new HashMap<>();
                paramsMap.put("department", data);
                paramsMap.put("major", maj);
                temp=RequestUtil.get().MapSend(teacherurl,sessionid,paramsMap);
                try {
                    System.out.println(temp);
                    JSONObject teacherlist = new JSONObject(temp); //接收json对象
//                    System.out.print(teacherlist);
                    myssession.setGrad_teacher(teacherlist.getJSONArray("ReportLwdb")); //从json对象中提取出相应数组
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONArray teacher=myssession.getGrad_teacher();
                        List<String> listdata_teacher=new ArrayList<>();
                        System.out.println(teacher);
                        for(int i=0;i<teacher.length();i++){
                            try {
                                listdata_teacher.add(teacher.getJSONObject(i).get("teachername").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<String> arrayAdapter_teacher = new ArrayAdapter<>(Activity_basicinfo5.this, android.R.layout.simple_spinner_item, listdata_teacher);
                        arrayAdapter_teacher.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                        t5_teacher=(Spinner)findViewById(R.id.t5_teacher);
                        t5_teacher.setAdapter(arrayAdapter_teacher);
                        t5_teacher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                                tea=(String)t5_teacher.getSelectedItem();
                                System.out.println(tea);
                                if(tea!=null) {
                                    Thread t2 = new Thread(getStudent);
                                    t2.start();
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

                    }
                });
            }
        };

        Thread getDep = new Thread(){
            @Override
            public void run() {
                super.run();
                temp=RequestUtil.get().sendrequest(depurl,sessionid,"","");
                try {
                    System.out.println(temp);
                    JSONObject departmentlist = new JSONObject(temp); //接收json对象

                    myssession.setGrad_department(departmentlist.getJSONArray("ReportLwdb")); //从json对象中提取出相应数组
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        getDep.start();

        try {
            getDep.join(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JSONArray dep;
        dep = myssession.getGrad_department();


        List<String> listdata_institute = null;
        listdata_institute = new ArrayList<>();

        if(dep!=null) {
            for (int i = 0; i < dep.length(); i++) {
                try {
                    listdata_institute.add(dep.getJSONObject(i).get("department").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            System.out.println("no internet connection");
            showNormalDialog();
        }

        Thread getMajor =new Thread(){
            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                temp=RequestUtil.get().sendrequest(majurl,sessionid,"department",data);
                try {
                    JSONObject courselist = new JSONObject(temp);
                    System.out.println(courselist);
//                    System.out.println(courselist.getJSONArray("ReportLwdb"));
                    myssession.setGrad_major(courselist.getJSONArray("ReportLwdb"));
                    /**z动态显示课程信息*/
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONArray Major=myssession.getGrad_major();
                            List<String> listdata_major=new ArrayList<>();
                            System.out.println(Major);
                            for(int i=0;i<Major.length();i++){
                                try {
                                    listdata_major.add(Major.getJSONObject(i).get("major").toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            ArrayAdapter<String> arrayAdapter_major = new ArrayAdapter<>(Activity_basicinfo5.this, android.R.layout.simple_spinner_item, listdata_major);
                            arrayAdapter_major.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                            t5_major=(Spinner)findViewById(R.id.t5_major);
                            t5_major.setAdapter(arrayAdapter_major);
                            maj=(String)t5_major.getSelectedItem();
                            t5_major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                                    maj=(String)t5_major.getSelectedItem();
                                    System.out.println(maj);
                                    if(data!=null) {
                                        Thread t1 = new Thread(getTeacher);
                                        t1.start();
                                    }
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

            }

        };


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Activity_basicinfo5.this, android.R.layout.simple_spinner_item, listdata_institute);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        t5_institute=(Spinner)findViewById(R.id.t5_institute);
        t5_institute.setAdapter(arrayAdapter);
        t5_institute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                data=(String)t5_institute.getSelectedItem();
                System.out.println(data);
                if(data!=null) {
                    Thread t = new Thread(getMajor);
                    t.start();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });




        /*****提交按钮点击事件*******/
        //绑定按钮
        t5_confirminfo=(Button) findViewById(R.id.t5_confirminfo);

        //添加监听事件
        t5_confirminfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交确认信息

                //跳转到评分页面
                Intent intent= new Intent(Activity_basicinfo5.this,Activity_t5score.class);
                System.out.println("the report id is "+reportid);
                intent.putExtra("sendfrom",sendfrom);
                intent.putExtra("institute",data);
                intent.putExtra("major",maj);
                intent.putExtra("teacher",tea);
                intent.putExtra("student",stu);
                intent.putExtra("type",type);
                intent.putExtra("classroom",classroom);
                intent.putExtra("year",""+year);
                intent.putExtra("month",""+month);
                intent.putExtra("day",""+day);
                intent.putExtra("time",time);
                intent.putExtra("experts",experts);
                intent.putExtra("reportid",reportid);
                startActivity(intent);
            }
        });
    }

    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(Activity_basicinfo5.this);
//        normalDialog.setIcon(R.drawable.icon_dialog);
        normalDialog.setTitle("网络连接貌似出现了错误");
        normalDialog.setMessage("请您检查您的网络连接再重新再重新进入");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        finish();
                    }
                });

        // 显示
        normalDialog.show();
    }




}
