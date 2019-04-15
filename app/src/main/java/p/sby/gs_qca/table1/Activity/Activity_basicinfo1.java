package p.sby.gs_qca.table1.Activity;


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
import p.sby.gs_qca.util.RequestUtil;

public class Activity_basicinfo1 extends AppCompatActivity {
    private Button t1_confirm;       //确认按钮
    private Spinner t1_institute;   //学院下拉菜单
    private Spinner t1_coursename;  //课程下拉菜单

    private String courseid="";
    private String sendfrom="basic";
    private String teacher="";
    private String classroom="";
    private String time="";
    private String coursename="";
    private String shouldnum="";
    private String classid="";
    private String depurl="http://117.121.38.95:9817/mobile/form/coursedata/getdep.ht";  //请求学院地址
    private String courseurl="http://117.121.38.95:9817/mobile/form/coursedata/getcourse.ht";  //请求课程地址
    private String detailurl="http://117.121.38.95:9817/mobile/form/coursedata/get.ht";  //请求细节信息地址
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



        t1_teacher=(TextView)findViewById(R.id.t1_teacher);
        t1_classroom=(TextView)findViewById(R.id.t1_classroom);
        t1_classtime=(TextView)findViewById(R.id.t1_time);
        t1_classid=(TextView)findViewById(R.id.t1_classid);
        String sessionid;                   //存储登录时cookie的字符串
        global_variance myssession = ((global_variance)getApplicationContext());   //声明全局变量类
        sessionid =myssession.getSessionid(); //获取本次登陆中的会话cookie




        /**学院信息网络请求线程，获取学院信息，使用okhttp包**/
        Thread loginRunnable = new Thread(){

            @Override
            public void run() {
                super.run();
                temp=RequestUtil.get().sendrequest(depurl,sessionid,"","");
                    try {
                        JSONObject departmentlist = new JSONObject(temp); //接收json对象
                        myssession.setDepartment(departmentlist.getJSONArray("coursedata")); //从json对象中提取出相应数组
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        };
        loginRunnable.start();
        try {
            loginRunnable.join(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JSONArray department;
        department = myssession.getDepartment();

        /**课程信息网络请求线程，获取课程细节信息，使用okhttp包**/
        Thread GetDetail=new Thread(){
            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                temp=RequestUtil.get().sendrequest(detailurl,sessionid,"id",myssession.getCourseid());

                    try {
                        JSONObject CourseData=new JSONObject(temp);
                        JSONObject CourseDetail=new JSONObject(CourseData.get("coursedata").toString());
                        System.out.println("*************打印CourseDetail***************");
                        System.out.println(CourseDetail);
                         teacher=CourseDetail.get("teacher").toString();
                         classroom=CourseDetail.get("room").toString();
                         time=CourseDetail.get("time1").toString();
                         courseid=CourseDetail.get("id").toString();
                         shouldnum=CourseDetail.get("studentnumber").toString();
                         classid=CourseDetail.get("classid").toString();

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
                temp=RequestUtil.get().sendrequest(courseurl,sessionid,"department",data);
                    try {
                        JSONObject courselist = new JSONObject(temp);
                        myssession.setCourse(courselist.getJSONArray("coursedata"));

                        /**z动态显示课程信息*/
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                JSONArray Course=myssession.getCourse();
                                List<String> listdata_coursename=new ArrayList<>();
                                for(int i=0;i<Course.length();i++){
                                    try {
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

            }

        };
        /***初始化所在院系***/
        List<String> listdata_institute = null;
        listdata_institute = new ArrayList<>();
        if(department!=null) {
            for (int i = 0; i < department.length(); i++) {
                try {
                    listdata_institute.add(department.getJSONObject(i).get("department").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            System.out.println("no internet connection");
            showNormalDialog();
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
                if(data!=null) {
                    Thread t = new Thread(getCourse);
                    t.start();
                }
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
                intent.putExtra("sendfrom",sendfrom);
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

    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(Activity_basicinfo1.this);
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