package p.sby.gs_qca.table4.Activity;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import p.sby.gs_qca.Main.Activity.global_variance;
import p.sby.gs_qca.R;
import p.sby.gs_qca.util.RequestUtil;

public class Activity_t4select extends AppCompatActivity {
    private Button t4_select;
    private Spinner t4_department;
    private Spinner t4_major;
    private Spinner t4_type;

    private String data;
    private String major;

    private String temp;
    private String type;

    private String depurl="http://117.121.38.95:9817/mobile/form/reportzqkh/getdep.ht";
    private String majorurl="http://117.121.38.95:9817/mobile/form/reportzqkh/getmajor.ht";
    String sessionid;                   //存储登录时cookie的字符串
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t4_select);


        /*****上方功能栏****/
        setToolbar();

        //注册按钮
        initView();

        global_variance myssession = ((global_variance)getApplicationContext());   //声明全局变量类
        sessionid =myssession.getSessionid(); //获取本次登陆中的会话cookie
        Thread getMajor =new Thread(){
            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                temp=RequestUtil.get().sendrequest(majorurl,sessionid,"department",data);
                try {
                    JSONObject majorlist = new JSONObject(temp);
                    myssession.setCourse(majorlist.getJSONArray("ReportZqkh"));

                    /**z动态显示课程信息*/
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONArray Major=myssession.getCourse();
                            List<String> listdata_coursename=new ArrayList<>();
                            for(int i=0;i<Major.length();i++){
                                try {
                                    listdata_coursename.add(Major.getJSONObject(i).get("major").toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            ArrayAdapter<String> arrayAdapter_course = new ArrayAdapter<>(Activity_t4select.this, android.R.layout.simple_spinner_item, listdata_coursename);
                            arrayAdapter_course.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                            t4_major.setAdapter(arrayAdapter_course);
                            t4_major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                    major=(String)t4_major.getSelectedItem();

//
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

        Thread getDepartment = new Thread(){

            @Override
            public void run() {
                super.run();
                temp=RequestUtil.get().sendrequest(depurl,sessionid,"","");
                try {
                    JSONObject departmentlist = new JSONObject(temp); //接收json对象
                    myssession.setDepartment(departmentlist.getJSONArray("ReportZqkh")); //从json对象中提取出相应数组
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        getDepartment.start();
        try {
            getDepartment.join(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JSONArray department;
        department = myssession.getDepartment();
        Log.i("t4info", "表4的学院: "+department);

        /***初始化所在院系***/
        List<String> listdata_institute = null;
        listdata_institute = new ArrayList<>();
        for(int i=0;i<department.length();i++){
            try {
                listdata_institute.add(department.getJSONObject(i).get("department").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Activity_t4select.this, android.R.layout.simple_spinner_item, listdata_institute);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        t4_department.setAdapter(arrayAdapter);
        t4_department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                data=(String)t4_department.getSelectedItem();
                Log.i("t4info", "selectdepartment "+data);
                Thread t=new Thread(getMajor);
                t.start();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        List<String> listdata_type = null;
        listdata_type = new ArrayList<>();
        listdata_type.add("硕士");
        listdata_type.add("专业学位硕士");
        listdata_type.add("博士");


        ArrayAdapter<String> arrayAdapter_type = new ArrayAdapter<>(Activity_t4select.this, android.R.layout.simple_spinner_item, listdata_type);
        arrayAdapter_type.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        t4_type.setAdapter(arrayAdapter_type);
        t4_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                type=(String)t4_type.getSelectedItem();
                Log.i("t4info", "selecttype "+type);
                Thread t=new Thread(getMajor);
                t.start();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        nextStep();
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_t4select); //主页上方功能条
        toolbar.setTitle("培养环节质量评价-中期");

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
    }


    //点击下一步
    private void nextStep() {
        //添加监听事件
        t4_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交确认信息

                //跳转到查看报告的页面
                Intent intent= new Intent(Activity_t4select.this,Activity_t4reportlist.class);
                intent.putExtra("department",data);
                intent.putExtra("major",major);
                intent.putExtra("type",type);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        /*****提交按钮点击事件*******/
        //绑定按钮
        t4_department=(Spinner)findViewById(R.id.t4_institute);
        t4_major=(Spinner)findViewById(R.id.t4_major);
        t4_select=(Button) findViewById(R.id.t4_confirm);
        t4_type=(Spinner)findViewById(R.id.t4_type);
    }
}
