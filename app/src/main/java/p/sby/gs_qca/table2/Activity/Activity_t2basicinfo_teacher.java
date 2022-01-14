package p.sby.gs_qca.table2.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import p.sby.gs_qca.Main.Activity.global_variance;
import p.sby.gs_qca.R;
import p.sby.gs_qca.table1.Activity.Activity_t1basicinfo_teacher;
import p.sby.gs_qca.table1.Activity.Activity_t1class;
import p.sby.gs_qca.table2.Activity.Activity_t2searchselect;
import p.sby.gs_qca.util.RequestUtil;
import p.sby.gs_qca.widget.NumRangeInputFilter100;

public class Activity_t2basicinfo_teacher extends AppCompatActivity {
    private TextView t2_institute;   //学院下拉菜单
    private Spinner t2_class;
    private Spinner t2_course;
    private Spinner t2_teacher;

    private Button t2_confirm;

    private String sendfrom="basic";
    private String temp;


    private String teacherUrl="http://116.213.144.72:9817/mobile/form/coursedata/getteacher.ht";
    //    private String courseurl="http://116.213.144.72:9817/mobile/form/coursedata/getallcourse.ht";
    private String courseurl="http://116.213.144.72:9817/mobile/form/coursedata/getteachercourse.ht";
    private String classurl="http://116.213.144.72:9817/mobile/form/coursedata/getcourseclass.ht";
    private String detailurl="http://116.213.144.72:9817/mobile/form/coursedata/getleftcourseinfo.ht";

    private String data;
    private String papernum="";
    private String coursename="";
    private String courseid="";
    private String department;
    private String classroom="";
    private String classidCN;


    //    private TextView t2_classroom;
    private EditText t2_papernum;

    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.t2basicinfo_teacher);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_t2teacher);
        toolbar.setTitle("考试试卷规范性评价");

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

        t2_teacher = (Spinner) findViewById(R.id.t2_teacher);
        t2_course = (Spinner) findViewById(R.id.t2_course);
        t2_institute=(TextView)findViewById(R.id.t2_institute);
        t2_class = (Spinner) findViewById(R.id.t2_class);
        t2_papernum=(EditText)findViewById(R.id.t2_papernum);
        t2_papernum.setFilters(new InputFilter[]{new NumRangeInputFilter100()});
        papernum=t2_papernum.getText().toString();


        String sessionid;
        global_variance myssession = ((global_variance)getApplicationContext());
        sessionid = myssession.getSessionid();



        /**学院信息网络请求线程，获取学院信息，使用okhttp包**/
        Thread loginRunnable = new Thread(){

            @Override
            public void run() {
                super.run();
                temp=RequestUtil.get().sendrequest(teacherUrl,sessionid,"","");
                try {
                    JSONObject teacherlist = new JSONObject(temp); //接收json对象
                    System.out.println(temp);
                    myssession.setExam_teacher(teacherlist.getJSONArray("teacherdata")); //从json对象中提取出相应数组
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
                HashMap<String, String> paramsMap = new HashMap<>();
                paramsMap.put("teacher", data);
                paramsMap.put("course", coursename);
                paramsMap.put("classid",classidCN);
                temp=RequestUtil.get().MapSend(detailurl,sessionid,paramsMap);
                //temp=RequestUtil.get().sendrequest(detailurl,sessionid,"id",myssession.getCourseid());

                try {
                    JSONObject CourseData=new JSONObject(temp);
                    myssession.setExam_detail(CourseData.getJSONArray("courseinfo"));
//                    JSONObject CourseDetail=new JSONObject(CourseData.get("courseinfo").toString());
//                    System.out.println("*************打印CourseDetail***************");
//                    System.out.println(CourseDetail);
//                    department=CourseDetail.get("department").toString();
//                    classroom=CourseDetail.get("classid").toString();
//                    courseid=CourseDetail.get("id").toString();


//                    System.out.println("请打印一下id"+courseid);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONArray detail=myssession.getExam_detail();
                            try {
                                department = detail.getJSONObject(0).get("department").toString();
                                classroom = detail.getJSONObject(0).get("classid").toString();
                                courseid = detail.getJSONObject(0).get("id").toString();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            t2_institute.setText(department);
                            //t2_class.setText(classroom);
//                            t2_classroom.setText(classroom);

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
                            ArrayAdapter<String> arrayAdapter_course = new ArrayAdapter<>(Activity_t2basicinfo_teacher.this, android.R.layout.simple_spinner_item, listdata_classes);
                            arrayAdapter_course.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                            t2_class=(Spinner)findViewById(R.id.t2_class);
                            t2_class.setAdapter(arrayAdapter_course);

                            t2_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                                    System.out.println(coursename);
                                    classidCN=(String)t2_class.getSelectedItem();

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



        Thread getCourse =new Thread(){
            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



                temp=RequestUtil.get().sendrequest(courseurl,sessionid,"teacher",data);

                //System.out.println(java.net.URLEncoder.encode(data, "utf-8"));

                try {
                    JSONObject courselist = new JSONObject(temp);
                    myssession.setExam_course(courselist.getJSONArray("coursename"));

                    /**动态显示课程信息*/
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONArray Course=myssession.getExam_course();
                            List<String> listdata_coursename=new ArrayList<>();
                            for(int i=0;i<Course.length();i++){
                                try {
                                    listdata_coursename.add(Course.getJSONObject(i).get("course").toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            ArrayAdapter<String> arrayAdapter_course = new ArrayAdapter<>(Activity_t2basicinfo_teacher.this, android.R.layout.simple_spinner_item, listdata_coursename);
                            arrayAdapter_course.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                            t2_course=(Spinner)findViewById(R.id.t2_course);
                            t2_course.setAdapter(arrayAdapter_course);
                            t2_course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                    coursename=(String)t2_course.getSelectedItem();
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
//
//                                    Thread t1=new Thread(GetDetail);
                                    Thread t1=new Thread(getClasses);
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


        JSONArray teacher;
        teacher = myssession.getExam_teacher();


        /***初始化所在院系***/
        List<String> listdata_teacher = null;
        listdata_teacher = new ArrayList<>();
        if(teacher!=null) {
            for (int i = 0; i < teacher.length(); i++) {
                try {
                    if (teacher.getJSONObject(i).get("teacher").toString() != null){
                        listdata_teacher.add(teacher.getJSONObject(i).get("teacher").toString());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            System.out.println("no internet connection");
            showNormalDialog();
        }
        /*******选择老师********/

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Activity_t2basicinfo_teacher.this, android.R.layout.simple_spinner_item, listdata_teacher);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        t2_teacher.setAdapter(arrayAdapter);
        t2_teacher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                data=(String)t2_teacher.getSelectedItem();
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



        t2_confirm = (Button) findViewById(R.id.t2_confirm);
        t2_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                papernum=t2_papernum.getText().toString();
                Intent intent = new Intent(Activity_t2basicinfo_teacher.this, Activity_t2class.class);
                intent.putExtra("sendfrom", sendfrom);
                intent.putExtra("institute", department);
                intent.putExtra("coursename", coursename);
                intent.putExtra("teacher", data);
                intent.putExtra("classroom", classroom);
                intent.putExtra("papernum", papernum);
                intent.putExtra("courseid", courseid);
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
                new AlertDialog.Builder(Activity_t2basicinfo_teacher.this);
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
