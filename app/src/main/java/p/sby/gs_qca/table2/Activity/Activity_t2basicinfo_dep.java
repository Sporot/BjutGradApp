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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import p.sby.gs_qca.Main.Activity.global_variance;
import p.sby.gs_qca.R;
import p.sby.gs_qca.util.RequestUtil;
import p.sby.gs_qca.widget.NumRangeInputFilter100;

public class Activity_t2basicinfo_dep extends AppCompatActivity {
    private Spinner t2_institute;   //学院下拉菜单
    private Spinner t2_course;
    private Spinner t2_class;
    private Button t2_confirm;
    private String sendfrom="basic";
    private String temp;
    private String depurl="http://116.213.144.72:9817/mobile/form/coursedata/getalldep.ht";
//    private String courseurl="http://116.213.144.72:9817/mobile/form/coursedata/getallcourse.ht";
    private String courseurl="http://116.213.144.72:9817/mobile/form/coursedata/getcourseforsj.ht";
    private String classurl="http://116.213.144.72:9817/mobile/form/coursedata/getclassforsj.ht";
    private String detailurl="http://116.213.144.72:9817/mobile/form/coursedata/get.ht";
    private String data;
    private String papernum="";
    private String coursename="";
    private String courseid="";
    private String teacher;
    private String classroom="";
    private TextView t2_teacher;
//    private TextView t2_classroom;
    private EditText t2_papernum;
//    private SQLiteHelper dbhelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t2basicinfo_dep);
//        dbhelper=new SQLiteHelper(this,"BookStore.db",null,2);

        /*****上方功能栏****/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_t2bi); //主页上方功能条
        toolbar.setTitle("研究生考试试卷规范性评价表");

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


        t2_teacher=(TextView)findViewById(R.id.t2_teacher);
//        t2_classroom=(TextView)findViewById(R.id.t2_class);
        t2_papernum=(EditText)findViewById(R.id.t2_papernum);
        t2_papernum.setFilters(new InputFilter[]{new NumRangeInputFilter100()});
        papernum=t2_papernum.getText().toString();

        String sessionid;
        global_variance myssession = ((global_variance)getApplicationContext());
        sessionid =myssession.getSessionid();


        /**学院信息网络请求线程，获取学院信息，使用okhttp包**/
        Thread loginRunnable = new Thread(){

            @Override
            public void run() {
                super.run();
                temp=RequestUtil.get().sendrequest(depurl,sessionid,"","");
                try {
                    JSONObject departmentlist = new JSONObject(temp); //接收json对象
                    System.out.println(temp);
                    myssession.setExam_deparment(departmentlist.getJSONArray("coursedata")); //从json对象中提取出相应数组
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
                temp=RequestUtil.get().sendrequest(detailurl,sessionid,"id",myssession.getCourseid());

                try {
                    JSONObject CourseData=new JSONObject(temp);
                    JSONObject CourseDetail=new JSONObject(CourseData.get("coursedata").toString());
                    System.out.println("*************打印CourseDetail***************");
                    System.out.println(CourseDetail);
                    teacher=CourseDetail.get("teacher").toString();
                    classroom=CourseDetail.get("classid").toString();
                    courseid=CourseDetail.get("id").toString();


                    System.out.println("请打印一下id"+courseid);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            t2_teacher.setText(teacher);
//                            t2_classroom.setText(classroom);

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        };

        Thread GetClass=new Thread(){
            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                HashMap<String, String> paramsMap = new HashMap<>();
                paramsMap.put("department", data);
                paramsMap.put("course", coursename);
                temp=RequestUtil.get().MapSend(classurl,sessionid,paramsMap);
                System.out.println(temp);
                try{
                    JSONObject classlist = new JSONObject(temp);
                    myssession.setExam_class(classlist.getJSONArray("coursedata"));
                    /**z动态显示课程信息*/
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONArray Class=myssession.getExam_class();
                            List<String> listdata_coursename=new ArrayList<>();
                            for(int i=0;i<Class.length();i++){
                                try {
                                    listdata_coursename.add(Class.getJSONObject(i).get("classid").toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            ArrayAdapter<String> arrayAdapter_course = new ArrayAdapter<>(Activity_t2basicinfo_dep.this, android.R.layout.simple_spinner_item, listdata_coursename);
                            arrayAdapter_course.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                            t2_class=(Spinner)findViewById(R.id.t2_class);
                            t2_class.setAdapter(arrayAdapter_course);
                            t2_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                    courseid=(String)t2_class.getSelectedItem();
                                    System.out.println(courseid);

                                    try {

                                        myssession.setCourseid(Class.getJSONObject(pos).get("id").toString());
                                        Log.i("table1", "选中id:"+Class.getJSONObject(pos).get("id").toString());

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

//                                    try {
//                                        if(coursename.equals(Class.getJSONObject(pos).get("class").toString())) {
//                                            System.out.println(pos);
//                                            System.out.println("id=" + Class.getJSONObject(pos).get("id").toString());
//                                            myssession.setCourseid(Class.getJSONObject(pos).get("id").toString());
//                                        }
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//
//                                    Thread t1=new Thread(GetDetail);
                                    Thread t2=new Thread(GetDetail);
                                    t2.start();

                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                        }
                    });
                }catch (JSONException e) {
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
                    myssession.setExam_course(courselist.getJSONArray("coursedata"));

                    /**z动态显示课程信息*/
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
                            ArrayAdapter<String> arrayAdapter_course = new ArrayAdapter<>(Activity_t2basicinfo_dep.this, android.R.layout.simple_spinner_item, listdata_coursename);
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
                                    Thread t1=new Thread(GetClass);
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


        JSONArray department;
        department = myssession.getExam_deparment();


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

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Activity_t2basicinfo_dep.this, android.R.layout.simple_spinner_item, listdata_institute);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        t2_institute=(Spinner)findViewById(R.id.t2_institute);
        t2_institute.setAdapter(arrayAdapter);
        t2_institute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                data=(String)t2_institute.getSelectedItem();
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
        t2_confirm=(Button) findViewById(R.id.t2_confirm);

        //添加监听事件
        t2_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交确认信息

                //跳转到评分页面
//                startActivity(new Intent(Activity_basicinfo2.this,Activity_t2score.class));
                papernum=t2_papernum.getText().toString();
                Intent intent=new Intent(Activity_t2basicinfo_dep.this, Activity_t2class.class);
                intent.putExtra("sendfrom",sendfrom);
                intent.putExtra("institute",data);
                intent.putExtra("coursename",coursename);
                intent.putExtra("teacher",teacher);
                intent.putExtra("classroom",classroom);
                intent.putExtra("papernum",papernum);
                intent.putExtra("courseid",courseid);
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
                new AlertDialog.Builder(Activity_t2basicinfo_dep.this);
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
