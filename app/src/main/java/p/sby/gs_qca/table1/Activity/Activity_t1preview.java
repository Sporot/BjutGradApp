package p.sby.gs_qca.table1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import p.sby.gs_qca.R;

public class Activity_t1preview extends AppCompatActivity {
    private TextView t1pre_intitute;
    private TextView t1pre_coursename;
    private TextView t1pre_comment;
    private TextView t1pre_actualnum;
    private TextView t1pre_teachtheme;
    private TextView t1pre_classnum;
    private TextView t1pre_otherinfo;


    private TextView t1pre_score1;
    private TextView t1pre_score2;
    private TextView t1pre_score3;
    private TextView t1pre_score4;
    private TextView t1pre_score5;
    private TextView t1pre_score6;
    private TextView t1pre_score7;
    private TextView t1pre_score8;
    private TextView t1pre_score9;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t1_preview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_t1preview); //主页上方功能条
        toolbar.setTitle("预览");

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


        t1pre_intitute=(TextView)findViewById(R.id.t1pre_institute);
        t1pre_coursename=(TextView) findViewById(R.id.t1pre_coursename);
        t1pre_comment=(TextView)findViewById(R.id.t1pre_comment);
        t1pre_actualnum=(TextView)findViewById(R.id.t1pre_actualnum);
        t1pre_teachtheme=(TextView)findViewById(R.id.t1pre_teachtheme);
        t1pre_classnum=(TextView)findViewById(R.id.t1pre_classnum);
        t1pre_otherinfo=(TextView)findViewById(R.id.t1pre_otherinfo);

        t1pre_score1=(TextView)findViewById(R.id.t1pre_score1);
        t1pre_score2=(TextView)findViewById(R.id.t1pre_score2);
        t1pre_score3=(TextView)findViewById(R.id.t1pre_score3);
        t1pre_score4=(TextView)findViewById(R.id.t1pre_score4);
        t1pre_score5=(TextView)findViewById(R.id.t1pre_score5);
        t1pre_score6=(TextView)findViewById(R.id.t1pre_score6);
        t1pre_score7=(TextView)findViewById(R.id.t1pre_score7);
        t1pre_score8=(TextView)findViewById(R.id.t1pre_score8);
        t1pre_score9=(TextView)findViewById(R.id.t1pre_score9);




        Intent intent=getIntent();
       t1pre_intitute.setText(intent.getStringExtra("institute"));
       t1pre_coursename.setText(intent.getStringExtra("coursename"));
       t1pre_comment.setText(intent.getStringExtra("comment"));
       t1pre_otherinfo.setText(intent.getStringExtra("otherinfo"));
       t1pre_actualnum.setText(intent.getStringExtra("latenum"));
       t1pre_teachtheme.setText(intent.getStringExtra("teachtheme"));
       t1pre_classnum.setText(intent.getStringExtra("classnum"));

        t1pre_score1.setText(intent.getStringExtra("score1"));
        t1pre_score2.setText(intent.getStringExtra("score2"));
        t1pre_score3.setText(intent.getStringExtra("score3"));
        t1pre_score4.setText(intent.getStringExtra("score4"));
        t1pre_score5.setText(intent.getStringExtra("score5"));
        t1pre_score6.setText(intent.getStringExtra("score6"));
        t1pre_score7.setText(intent.getStringExtra("score7"));
        t1pre_score8.setText(intent.getStringExtra("score8"));
        t1pre_score9.setText(intent.getStringExtra("score9"));



    }
}
