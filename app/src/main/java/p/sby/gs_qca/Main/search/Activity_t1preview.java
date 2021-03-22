package p.sby.gs_qca.Main.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import p.sby.gs_qca.Main.Activity.Activity_list;
import p.sby.gs_qca.Main.Activity.global_variance;
import p.sby.gs_qca.R;
import p.sby.gs_qca.widget.LoadingDialog;

public class Activity_t1preview extends AppCompatActivity {
    String sessionid;
    private String result;
    private LoadingDialog mLoadingDialog; //显示正在加载的对话框
    private String urlsave="http://116.213.144.72:9817/mobile/form/buff/addjxzl.ht";
    private String url="http://116.213.144.72:9817/mobile/form/jxzl/add.ht";
    private TextView t1pre_intitute;
    private TextView t1pre_coursename;
    private TextView t1pre_comment;
    private TextView t1pre_actualnum;
    private TextView t1pre_teachtheme;
    private TextView t1pre_classnum;
   // private TextView t1pre_otherinfo;
    private TextView t1pre_teacher;
    private TextView t1pre_classroom;
    private TextView t1pre_time;
    private TextView t1pre_latenum;
    private TextView t1pre_shouldnum;
    private TextView t1pre_classid;
    private TextView t1pre_type;
    private TextView t1pre_object;

    private TextView t1pre_status;


    private TextView t1pre_score1;
    private TextView t1pre_score2;
    private TextView t1pre_score3;
    private TextView t1pre_score4;
    private TextView t1pre_score5;
    private TextView t1pre_score6;
    private TextView t1pre_score7;
    private TextView t1pre_score8;
    private TextView t1pre_score9;
    private TextView t1pre_score10;
    private TextView t1total;


    private Button t1pre_submit;
    private Button t1pre_save;



/*****提交数据*****/
    private String comment="";
    private String institute="";
    private String coursename="";
    private String latenum="";
    private String teachtheme="";
    private String classnum="";
    private String teacher="";
    private String classroom="";
    private String time="";
    private String actualnum="";
    private String shouldnum="";
    private String courseid="";
    private String classid="";
    private String classtype="";
    private String classobject="";
    private String status="";

    private String t1_score1="";
    private String t1_score2="";
    private String t1_score3="";
    private String t1_score4="";
    private String t1_score5="";
    private String t1_score6="";
    private String t1_score7="";
    private String t1_score8="";
    private String t1_score9="";
    private String t1_score10="";
    private String t1_total="";


    private int flag=0;
    private int flagsave=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t1_submit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_t1detail); //主页上方功能条
        toolbar.setTitle(coursename);

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


        initView();
    //    setValue();
        getValue();




    }

    public void initView() {
        t1pre_save=(Button)findViewById(R.id.t1pre_save);
        t1pre_submit=(Button)findViewById(R.id.t1pre_submit);


        t1pre_intitute=(TextView)findViewById(R.id.t1pre_institute);
        t1pre_coursename=(TextView) findViewById(R.id.t1pre_coursename);
        t1pre_comment=(TextView)findViewById(R.id.t1pre_comment);
        t1pre_actualnum=(TextView)findViewById(R.id.t1pre_actualnum);
        t1pre_teachtheme=(TextView)findViewById(R.id.t1pre_teachtheme);
        t1pre_classnum=(TextView)findViewById(R.id.t1pre_classnum);
        t1pre_classid=(TextView)findViewById(R.id.t1pre_classid);
        t1pre_status=(TextView)findViewById(R.id.t1pre_status);

        t1pre_teacher=(TextView)findViewById(R.id.t1pre_teacher);
        t1pre_classroom=(TextView)findViewById(R.id.t1pre_classroom);
        t1pre_latenum=(TextView)findViewById(R.id.t1pre_latenum);
        t1pre_time=(TextView)findViewById(R.id.t1pre_time);
        t1pre_shouldnum=(TextView)findViewById(R.id.t1pre_shouldnum);
        // t1pre_otherinfo=(TextView)findViewById(R.id.t1pre_otherinfo);

        t1pre_type=(TextView)findViewById(R.id.t1pre_type);
        t1pre_object=(TextView)findViewById(R.id.t1pre_object);

//        t1pre_score2=(TextView)findViewById(R.id.t1pre_score2);
        t1pre_score1=(TextView)findViewById(R.id.t1pre_score1);
        t1pre_score2=(TextView)findViewById(R.id.t1pre_score2);
        t1pre_score3=(TextView)findViewById(R.id.t1pre_score3);
        t1pre_score4=(TextView)findViewById(R.id.t1pre_score4);
        t1pre_score5=(TextView)findViewById(R.id.t1pre_score5);
        t1pre_score6=(TextView)findViewById(R.id.t1pre_score6);
        t1pre_score7=(TextView)findViewById(R.id.t1pre_score7);
        t1pre_score8=(TextView)findViewById(R.id.t1pre_score8);
        t1pre_score9=(TextView)findViewById(R.id.t1pre_score9);
        t1pre_score10=(TextView)findViewById(R.id.t1pre_score10);
        t1total=(TextView)findViewById(R.id.t1pre_total);

    }


    public void getValue(){
        Intent intent=getIntent();

        courseid=intent.getStringExtra("courseid");
        System.out.println("***********在查询详情页打印courseid:******************");
        System.out.println("courseid: "+courseid);

        t1pre_coursename.setText(intent.getStringExtra("coursename"));
        coursename= intent.getStringExtra("coursename");

        t1pre_classid.setText(intent.getStringExtra("classid"));
        classid= intent.getStringExtra("classid");

        t1pre_comment.setText(intent.getStringExtra("comment"));
        comment=intent.getStringExtra("comment");

        t1pre_teacher.setText(intent.getStringExtra("teacher"));
       teacher=intent.getStringExtra("teacher");
        System.out.println(teacher);

        t1pre_latenum.setText(intent.getStringExtra("latenum"));
        latenum=intent.getStringExtra("latenum");

        t1pre_teachtheme.setText(intent.getStringExtra("teachtheme"));
        teachtheme=intent.getStringExtra("teachtheme");
        System.out.println(teachtheme);

        t1pre_intitute.setText(intent.getStringExtra("institute"));
        institute=intent.getStringExtra("institute");
        System.out.println(intent.getStringExtra("institute"));

        t1pre_classnum.setText(intent.getStringExtra("classnum"));
        classnum=intent.getStringExtra("classnum");


        t1pre_time.setText(intent.getStringExtra("time1"));
        time=intent.getStringExtra("time1");

        t1pre_status.setText(intent.getStringExtra("status"));
        status=intent.getStringExtra("status");

        t1pre_classroom.setText(intent.getStringExtra("classroom"));
        classroom=intent.getStringExtra("classroom");

        t1pre_actualnum.setText(intent.getStringExtra("actualnum"));
        actualnum=intent.getStringExtra("actualnum");

        t1pre_shouldnum.setText(intent.getStringExtra("shouldnum"));
        shouldnum=intent.getStringExtra("shouldnum");


        t1pre_type.setText(intent.getStringExtra("type"));
        classtype=intent.getStringExtra("type");

        t1pre_object.setText(intent.getStringExtra("extend"));
        classobject=intent.getStringExtra("extend");

        t1pre_score1.setText(intent.getStringExtra("score1"));
        t1_score1=intent.getStringExtra("score1");

        t1pre_score2.setText(intent.getStringExtra("score2"));
        t1_score2=intent.getStringExtra("score2");

        System.out.println(t1_score2);

        t1pre_score3.setText(intent.getStringExtra("score3"));
        t1_score3=intent.getStringExtra("score3");

        t1pre_score4.setText(intent.getStringExtra("score4"));
        t1_score4=intent.getStringExtra("score4");

        t1pre_score5.setText(intent.getStringExtra("score5"));
        t1_score5=intent.getStringExtra("score5");

        t1pre_score6.setText(intent.getStringExtra("score6"));
        t1_score6=intent.getStringExtra("score6");

        t1pre_score7.setText(intent.getStringExtra("score7"));
        t1_score7=intent.getStringExtra("score7");

        t1pre_score8.setText(intent.getStringExtra("score8"));
        t1_score8=intent.getStringExtra("score8");

        t1pre_score9.setText(intent.getStringExtra("score9"));
        t1_score9=intent.getStringExtra("score9");

        t1pre_score10.setText(intent.getStringExtra("score10"));
        t1_score10=intent.getStringExtra("score10");

        t1total.setText(intent.getStringExtra("totalscore"));
//        t1_total=intent.getStringExtra("score9");


    }


}
