package p.sby.gs_qca.table5.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import p.sby.gs_qca.R;

public class Activity_t5preview extends AppCompatActivity {
    private TextView t5pre_intitute;

    private String institute="";


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t5_preview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_t5preview); //主页上方功能条
        toolbar.setTitle("预览");

        toolbar.setTitleTextColor(getResources().getColor(R.color.white)); //设置标题颜色
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        initView();
        getValue();



//        返回上级页面
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//返回
            }
        });
    }

    public void getValue(){
        Intent intent=getIntent();
//            formid=intent.getStringExtra("formid");
//            option=intent.getStringExtra("option");
//            System.out.println("********option值*******");
//            System.out.println(option);

        institute=intent.getStringExtra("institute");
        System.out.println("在预览页打印courseid:"+institute);

//            t1pre_coursename.setText(intent.getStringExtra("coursename"));
//            coursename= intent.getStringExtra("coursename");
//
//            t1pre_classid.setText(intent.getStringExtra("classid"));
//            classid= intent.getStringExtra("classid");
//
//            t1pre_comment.setText(intent.getStringExtra("comment"));
//            comment=intent.getStringExtra("comment");
//
//            t1pre_teacher.setText(intent.getStringExtra("teacher"));
//            teacher=intent.getStringExtra("teacher");
//            System.out.println(teacher);
//
//              t1pre_otherinfo.setText(intent.getStringExtra("otherinfo"));
//            t1pre_latenum.setText(intent.getStringExtra("latenum"));
//            latenum=intent.getStringExtra("latenum");
//
//            t1pre_teachtheme.setText(intent.getStringExtra("teachtheme"));
//            teachtheme=intent.getStringExtra("teachtheme");
//            System.out.println(teachtheme);
//
//            t1pre_intitute.setText(intent.getStringExtra("institute"));
//            institute=intent.getStringExtra("institute");
//            System.out.println(intent.getStringExtra("institute"));
//
//            t1pre_classnum.setText(intent.getStringExtra("classnum"));
//            classnum=intent.getStringExtra("classnum");


//            t1pre_time.setText(intent.getStringExtra("time"));
//            time=intent.getStringExtra("time");
//
//
//            t1pre_classroom.setText(intent.getStringExtra("classroom"));
//            classroom=intent.getStringExtra("classroom");
//
//            t1pre_actualnum.setText(intent.getStringExtra("actualnum"));
//            actualnum=intent.getStringExtra("actualnum");
//
//            t1pre_shouldnum.setText(intent.getStringExtra("shouldnum"));
//            shouldnum=intent.getStringExtra("shouldnum");
//
//
//            t1pre_score1.setText(intent.getStringExtra("score1"));
//            t1_score1=intent.getStringExtra("score1");
//
//            t1pre_score2.setText(intent.getStringExtra("score2"));
//            t1_score2=intent.getStringExtra("score2");
//
//            System.out.println(t1_score2);
//
//            t1pre_score3.setText(intent.getStringExtra("score3"));
//            t1_score3=intent.getStringExtra("score3");
//
//            t1pre_score4.setText(intent.getStringExtra("score4"));
//            t1_score4=intent.getStringExtra("score4");
//
//            t1pre_score5.setText(intent.getStringExtra("score5"));
//            t1_score5=intent.getStringExtra("score5");
//
//            t1pre_score6.setText(intent.getStringExtra("score6"));
//            t1_score6=intent.getStringExtra("score6");
//
//            t1pre_score7.setText(intent.getStringExtra("score7"));
//            t1_score7=intent.getStringExtra("score7");
//
//            t1pre_score8.setText(intent.getStringExtra("score8"));
//            t1_score8=intent.getStringExtra("score8");
//
//            t1pre_score9.setText(intent.getStringExtra("score9"));
//            t1_score9=intent.getStringExtra("score9");


    }
}
