package p.sby.gs_qca.table4.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import p.sby.gs_qca.R;

public class Activity_t4preview extends AppCompatActivity {
    private TextView t4_department;
    private TextView t4_major;
    private TextView t4_studentname;
    private TextView t4_type;
    private TextView t4_teachername;
    private TextView t4_place;
    private TextView t4_time;
    private TextView t4_experts;

    private TextView t4_score1;
    private TextView t4_comment1;
    private TextView t4_comment2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t4_preview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_t4preview); //主页上方功能条
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

        t4_department=(TextView)findViewById(R.id.t4_department);
        t4_major=(TextView)findViewById(R.id.t4_major);
        t4_studentname=(TextView)findViewById(R.id.t4_studentname);
        t4_teachername=(TextView)findViewById(R.id.t4_teachername);
        t4_type=(TextView)findViewById(R.id.t4_type);
        t4_place=(TextView)findViewById(R.id.t4_place);
        t4_time=(TextView)findViewById(R.id.t4_time);
        t4_experts=(TextView)findViewById(R.id.t4_experts);

        t4_score1=(TextView)findViewById(R.id.t4_score1);
        t4_comment1=(TextView)findViewById(R.id.t4_comment1);
        t4_comment2=(TextView)findViewById(R.id.t4_comment2);

        Intent intent=getIntent();
        t4_department.setText(intent.getStringExtra("department"));
        t4_major.setText(intent.getStringExtra("major"));
        t4_studentname.setText(intent.getStringExtra("studentname"));
        t4_type.setText(intent.getStringExtra("type"));
        t4_teachername.setText(intent.getStringExtra("teachername"));
        t4_place.setText(intent.getStringExtra("room"));
        t4_time.setText(intent.getStringExtra("time"));
        t4_experts.setText(intent.getStringExtra("experts"));

        t4_score1.setText(intent.getStringExtra("score1"));
        t4_comment1.setText(intent.getStringExtra("comment1"));
        t4_comment2.setText(intent.getStringExtra("comment2"));

    }
}
