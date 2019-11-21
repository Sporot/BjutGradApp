package p.sby.gs_qca.table2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import p.sby.gs_qca.R;


public class Activity_t2preview extends AppCompatActivity {

    private TextView t2pre_department;
    private TextView t2pre_course;
    private TextView t2pre_teacher;
    private TextView t2pre_class;
    private TextView t2pre_papernum;

    private TextView t2pre_score1;
    private TextView t2pre_score2;
    private TextView t2pre_score3;
    private TextView t2pre_score4;
    private TextView t2pre_score5;
    private TextView t2pre_score6;
    private TextView t2pre_score7;
    private TextView t2pre_score8;
    private TextView t2pre_total;
    private TextView t2pre_comment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t2_preview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_t2preview); //主页上方功能条
        toolbar.setTitle("返回");

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

        t2pre_department=(TextView)findViewById(R.id.t2pre_institute);
        t2pre_course=(TextView)findViewById(R.id.t2pre_coursename);
        t2pre_teacher=(TextView)findViewById(R.id.t2pre_teacher);
        t2pre_class=(TextView)findViewById(R.id.t2pre_class);
        t2pre_papernum=(TextView)findViewById(R.id.t2pre_papernumber);
        t2pre_score1=(TextView)findViewById(R.id.t2pre_score1);
        t2pre_score2=(TextView)findViewById(R.id.t2pre_score2);
        t2pre_score3=(TextView)findViewById(R.id.t2pre_score3);
        t2pre_score4=(TextView)findViewById(R.id.t2pre_score4);
        t2pre_score5=(TextView)findViewById(R.id.t2pre_score5);
        t2pre_score6=(TextView)findViewById(R.id.t2pre_score6);
        t2pre_score7=(TextView)findViewById(R.id.t2pre_score7);
        t2pre_score8=(TextView)findViewById(R.id.t2pre_score8);
        t2pre_total=(TextView)findViewById(R.id.t2pre_total);
        t2pre_comment=(TextView)findViewById(R.id.t2pre_comment);



//
//
//        t3_score1=(TextView)findViewById(R.id.t3_score1);
//        t3_comment1=(TextView)findViewById(R.id.t3_comment1);
//        t3_comment2=(TextView)findViewById(R.id.t3_comment2);
//
        Intent intent=getIntent();
        t2pre_department.setText(intent.getStringExtra("institute"));
        t2pre_course.setText(intent.getStringExtra("coursename"));
        t2pre_teacher.setText(intent.getStringExtra("teacher"));
        t2pre_class.setText(intent.getStringExtra("classroom"));
        t2pre_papernum.setText(intent.getStringExtra("papernum"));
        t2pre_score1.setText(intent.getStringExtra("score1"));
        t2pre_score2.setText(intent.getStringExtra("score2"));
        t2pre_score3.setText(intent.getStringExtra("score3"));
        t2pre_score4.setText(intent.getStringExtra("score4"));
        t2pre_score5.setText(intent.getStringExtra("score5"));
        t2pre_score6.setText(intent.getStringExtra("score6"));
        t2pre_score7.setText(intent.getStringExtra("score7"));
        t2pre_score8.setText(intent.getStringExtra("score8"));
        t2pre_total.setText(intent.getStringExtra("total"));
        t2pre_comment.setText(intent.getStringExtra("comment1"));




    }


}
