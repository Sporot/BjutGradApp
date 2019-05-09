package p.sby.gs_qca.table5.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import p.sby.gs_qca.R;

public class Activity_t5preview extends AppCompatActivity {
    private TextView t5_department;
    private TextView t5_major;
    private TextView t5_studentname;
    private TextView t5_type;
    private TextView t5_teachername;
    private TextView t5_place;
    private TextView t5_time;
    private TextView t5_experts;

    private TextView t5_score1;
    private TextView t5_comment1;
    private TextView t5_comment2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t5_preview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_t5preview); //主页上方功能条
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

        t5_department=(TextView)findViewById(R.id.t5_department);
        t5_major=(TextView)findViewById(R.id.t5_major);
        t5_studentname=(TextView)findViewById(R.id.t5_studentname);
        t5_teachername=(TextView)findViewById(R.id.t5_teachername);
        t5_type=(TextView)findViewById(R.id.t5_type);
        t5_place=(TextView)findViewById(R.id.t5_place);
        t5_time=(TextView)findViewById(R.id.t5_time);
        t5_experts=(TextView)findViewById(R.id.t5_experts);

        t5_score1=(TextView)findViewById(R.id.t5_score1);
        t5_comment1=(TextView)findViewById(R.id.t5_comment1);
        t5_comment2=(TextView)findViewById(R.id.t5_comment2);

        Intent intent=getIntent();
        if(intent.getStringExtra("sendfrom").equals("basic"))
        {
            t5_department.setText(intent.getStringExtra("institute"));
            t5_major.setText(intent.getStringExtra("major"));
            t5_studentname.setText(intent.getStringExtra("student"));
            t5_type.setText(intent.getStringExtra("type"));
            t5_teachername.setText(intent.getStringExtra("teacher"));
            t5_place.setText(intent.getStringExtra("classroom"));
            t5_time.setText(intent.getStringExtra("year")+"年"+intent.getStringExtra("month")+"月"+intent.getStringExtra("day")+"日");
            t5_experts.setText(intent.getStringExtra("expert"));

            t5_score1.setText(intent.getStringExtra("score"));
            t5_comment1.setText(intent.getStringExtra("comment1"));
            t5_comment2.setText(intent.getStringExtra("comment2"));
        }

        else if(intent.getStringExtra("sendfrom").equals("search")){
            t5_department.setText(intent.getStringExtra("department"));
            t5_major.setText(intent.getStringExtra("major"));
            t5_studentname.setText(intent.getStringExtra("studentname"));
            t5_type.setText(intent.getStringExtra("type"));
            t5_teachername.setText(intent.getStringExtra("teachername"));
            t5_place.setText(intent.getStringExtra("room"));
            t5_time.setText(intent.getStringExtra("time"));
            t5_experts.setText(intent.getStringExtra("experts"));

            t5_score1.setText(intent.getStringExtra("score1"));
            t5_comment1.setText(intent.getStringExtra("comment1"));
            t5_comment2.setText(intent.getStringExtra("comment2"));
        }


    }
}
