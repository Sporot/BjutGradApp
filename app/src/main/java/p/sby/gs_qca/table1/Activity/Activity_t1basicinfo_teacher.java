package p.sby.gs_qca.table1.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import p.sby.gs_qca.Main.Activity.global_variance;
import p.sby.gs_qca.R;
import p.sby.gs_qca.util.RequestUtil;


public class Activity_t1basicinfo_teacher extends AppCompatActivity {
    private Button t1_confirm;
    private Spinner t1_teacher;
    private Spinner t1_coursename;
    private TextView t1_classtime;
    private Spinner t1_status;


    private String courseid="";
    private String sendfrom="basic";
    private String teacher="";
    private String classroom="";
    private String time="";
    private String coursename="";
    private String shouldnum="";
    private String classid="";
    private String classtype="";
    private String classobject="";
    private String status="";
    private String classname="";
    private String classidCN="";
    private String classtime="";
    private String department="";

    private final String teacherUrl = "http://116.213.144.72:9817/mobile/form/coursedata/getteacher.ht";
    private final String courseNameUrl = "http://116.213.144.72:9817/mobile/form/coursedata/getteachercourse.ht";
    private final String classurl = "http://116.213.144.72:9817/mobile/form/coursedata/getcourseclass.ht";
    //private final String timeUrl = "http://116.213.144.72:9817/mobile/form/coursedata/getcoursetime.ht";
    private final String leftInfoUrl = "http://116.213.144.72:9817/mobile/form/coursedata/getleftcourseinfo.ht";
    private final String statusurl="http://116.213.144.72:9817/mobile/form/coursedata/getsf.ht"; //请求status信息地址


    private String temp;
    private String temps;
    private String data;

    private String statusnum="";


    private TextView t1_institute;
    private TextView t1_classroom;
    private TextView t1_type;
    private Spinner t1_classid;
    private TextView t1_object;



    String sessionid;                   //存储登录时cookie的字符串

    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.t1basicinfo_teacher);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_t1teacher);
        toolbar.setTitle("教学质量评价");

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

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

        t1_teacher = (Spinner)findViewById(R.id.t1_teacher);
        t1_coursename = (Spinner)findViewById(R.id.t1_coursename) ;
        t1_classtime = (TextView)findViewById(R.id.t1_time);
        t1_classroom = (TextView)findViewById(R.id.t1_classroom);
        t1_institute = (TextView)findViewById(R.id.t1_institute);
        t1_classid = (Spinner)findViewById(R.id.t1_classid);
        t1_type = (TextView)findViewById(R.id.t1_type);
        t1_object = (TextView)findViewById(R.id.t1_object);
        t1_classroom = (TextView)findViewById(R.id.t1_classroom);
        t1_status = (Spinner) findViewById(R.id.t1_status);

        global_variance myssession = ((global_variance)getApplicationContext());   //声明全局变量类
        sessionid =myssession.getSessionid(); //获取本次登陆中的会话cookie


        /****根据班级，请求课程详细信息****/
        Thread GetDetail=new Thread(){
            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //System.out.println("class:  "+myssession.getClassesid());

                //temp=RequestUtil.get().sendrequest(leftInfoUrl,sessionid,"id",myssession.getClassesid());
                HashMap<String, String> paramsMap = new HashMap<>();
                paramsMap.put("teacher", data);
                paramsMap.put("course", coursename);
                paramsMap.put("classid",classidCN);
                temp=RequestUtil.get().MapSend(leftInfoUrl,sessionid,paramsMap);
                try {
                    JSONObject classdetail=new JSONObject(temp);
                    myssession.setClassDetail(classdetail.getJSONArray("courseinfo"));
//                    JSONObject CourseDetail=new JSONObject(CourseData.get("coursedata").toString());
//                    System.out.println("*************打印CourseDetail***************");
//                    System.out.println(CourseDetail);
//
//                    teacher=CourseDetail.get("teacher").toString();
//                    classroom=CourseDetail.get("room").toString();
//                    time=CourseDetail.get("time1").toString();
//                    courseid=CourseDetail.get("id").toString();
//                    shouldnum=CourseDetail.get("studentnumber").toString();
//                    classid=CourseDetail.get("classid").toString();
//                    classtype=CourseDetail.get("type").toString();
//                    classobject=CourseDetail.get("extend1").toString();
//
//                    Log.i("table1", "inform:  "+teacher+"  "+"courseid:"+courseid);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONArray detail=myssession.getClassDetail();
                            try {
                                courseid = detail.getJSONObject(0).get("id").toString();
                                classtime = detail.getJSONObject(0).get("time1").toString();
                                classroom = detail.getJSONObject(0).get("room").toString();
                                classtype = detail.getJSONObject(0).get("type").toString();
                                classobject = detail.getJSONObject(0).get("extend1").toString();
                                courseid = detail.getJSONObject(0).get("id").toString();
                                department = detail.getJSONObject(0).get("department").toString();
                                shouldnum = detail.getJSONObject(0).get("studentnumber").toString();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            t1_classtime.setText(classtime);
                            t1_classroom.setText(classroom);
                            t1_institute.setText(department);
                            t1_type.setText(classtype);
                            t1_object.setText(classobject);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };


        /***根据教师，课程请求班级*****/
        Thread getClasses=new Thread(){
            @Override
            public void run() {
                super.run();
                HashMap<String, String> paramsMap = new HashMap<>();
                paramsMap.put("teacher", data);
                paramsMap.put("course", coursename);
                temp=RequestUtil.get().MapSend(classurl,sessionid,paramsMap);
                try {
                    JSONObject classlist = new JSONObject(temp);
                    myssession.setclasses(classlist.getJSONArray("classid"));

                    /**z动态显示课程信息*/
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONArray Classes=myssession.getClasses();
                            List<String> listdata_classes=new ArrayList<>();
                            for(int i=0;i<Classes.length();i++){
                                try {
                                    listdata_classes.add(Classes.getJSONObject(i).get("classid").toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            ArrayAdapter<String> arrayAdapter_course = new ArrayAdapter<>(Activity_t1basicinfo_teacher.this, android.R.layout.simple_spinner_item, listdata_classes);
                            arrayAdapter_course.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                            t1_classid=(Spinner)findViewById(R.id.t1_classid);
                            t1_classid.setAdapter(arrayAdapter_course);

                            t1_classid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                                    System.out.println(coursename);
                                    classidCN=(String)t1_classid.getSelectedItem();

                                    if(classidCN!=null){
                                        Thread t1=new Thread(GetDetail);
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


        /*****根据教师请求课程名称******/
        Thread getCourseName =new Thread(){
            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                temp=RequestUtil.get().sendrequest(courseNameUrl,sessionid,"teacher",data);
                try {
                    JSONObject courselist = new JSONObject(temp);
                    myssession.setCourse(courselist.getJSONArray("coursename"));

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
                            ArrayAdapter<String> arrayAdapter_course = new ArrayAdapter<>(Activity_t1basicinfo_teacher.this, android.R.layout.simple_spinner_item, listdata_coursename);
                            arrayAdapter_course.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                            t1_coursename=(Spinner)findViewById(R.id.t1_coursename);
                            t1_coursename.setAdapter(arrayAdapter_course);
                            coursename=(String)t1_coursename.getSelectedItem();
                            t1_coursename.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                    coursename=(String)t1_coursename.getSelectedItem();
                                    System.out.println(coursename);

                                    if(coursename!=null){
                                        Thread t1=new Thread(getClasses);
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



        /****请求教师列表****/
        Thread GetTeacher = new Thread(){

            @Override
            public void run() {
                super.run();
                temp=RequestUtil.get().sendrequest(teacherUrl,sessionid,"","");
                try {
                    JSONObject teacherlist = new JSONObject(temp); //接收json对象
                    System.out.println("++++++++++++++"+teacherlist+"+++++++++++++");
                    myssession.setTeacherName(teacherlist.getJSONArray("teacherdata")); //从json对象中提取出相应数组
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                temps=RequestUtil.get().sendrequest(statusurl,sessionid,"","");
                try {
                    JSONObject statuslist = new JSONObject(temps); //接收json对象
                    System.out.println("----------------Status----------------");

                    statusnum=statuslist.get("expertsattribute").toString();
                    System.out.println(statusnum);
                    myssession.setStatusnum(statusnum);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        GetTeacher.start();
        try {
            GetTeacher.join(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        JSONArray teacherName;
        String stn;
        teacherName = myssession.getTeacherName();
        stn=myssession.getStatusnum();

        /****初始化教师信息*****/
        List<String> listdata_teacherName = null;
        List<String> listdata_status=null;
        listdata_teacherName = new ArrayList<>();
        listdata_status = new ArrayList<>();
        if(teacherName!=null) {
            for (int i = 0; i < teacherName.length(); i++) {
                try {
                    listdata_teacherName.add(teacherName.getJSONObject(i).get("teacher").toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if(stn.equals("2")) {
                listdata_status.add("院级专家");
            }
            if(stn.equals("1")) {
                listdata_status.add("校级专家");
            }
            if(stn.equals("0")) {
                listdata_status.add("校级专家");
                listdata_status.add("院级专家");
            }

        }
        else {
            System.out.println("no internet connection");
            showNormalDialog();
        }



        /*****选择身份****/
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(Activity_t1basicinfo_teacher.this, android.R.layout.simple_spinner_item, listdata_status);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        t1_status.setAdapter(arrayAdapter1);

        status=(String)t1_status.getSelectedItem();//新加
        t1_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                status=(String)t1_status.getSelectedItem();
                System.out.println(status);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        /*******所在院系选择********/
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Activity_t1basicinfo_teacher.this, android.R.layout.simple_spinner_item, listdata_teacherName);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        t1_teacher.setAdapter(arrayAdapter);
        t1_teacher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                data=(String)t1_teacher.getSelectedItem();

                System.out.println(data);
                if(data!=null) {
                    Thread t = new Thread(getCourseName);
                    t.start();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });




        /****点击下一步****/

        t1_confirm = (Button) findViewById(R.id.t1_confirm);
        t1_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_t1basicinfo_teacher.this, Activity_t1class.class);
                intent.putExtra("sendfrom",sendfrom);
                intent.putExtra("institute",department);
                intent.putExtra("coursename",coursename);
                intent.putExtra("teacher",data);
                intent.putExtra("classroom",classroom);
                intent.putExtra("time",classtime);
                intent.putExtra("courseid",courseid);
                intent.putExtra("shouldnum",shouldnum);
                intent.putExtra("classid",classidCN);
                intent.putExtra("type",classtype);
                intent.putExtra("extend",classobject);
                intent.putExtra("status",status);
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
                new AlertDialog.Builder(Activity_t1basicinfo_teacher.this);
//        normalDialog.setIcon(R.drawable.icon_dialog);
        normalDialog.setTitle("网络连接貌似出现了错误");
        normalDialog.setMessage("请您检查您的网络连接再重新进入");
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
