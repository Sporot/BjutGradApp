package p.sby.gs_qca.table3.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import p.sby.gs_qca.R;

public class Activity_t3preview extends AppCompatActivity {
    private TextView t3_department;
    private TextView t3_major;
    private TextView t3_studentname;
    private TextView t3_type;
    private TextView t3_teachername;
    private TextView t3_place;
    private TextView t3_time;
    private TextView t3_experts;

    private TextView t3_score1;
    private TextView t3_comment1;
    private TextView t3_comment2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t3_preview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_t3preview); //主页上方功能条
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

        t3_department=(TextView)findViewById(R.id.t3_department);
        t3_major=(TextView)findViewById(R.id.t3_major);
        t3_studentname=(TextView)findViewById(R.id.t3_studentname);
        t3_teachername=(TextView)findViewById(R.id.t3_teachername);
        t3_type=(TextView)findViewById(R.id.t3_type);
        t3_place=(TextView)findViewById(R.id.t3_place);
        t3_time=(TextView)findViewById(R.id.t3_time);
        t3_experts=(TextView)findViewById(R.id.t3_experts);

        t3_score1=(TextView)findViewById(R.id.t3_score1);
        t3_comment1=(TextView)findViewById(R.id.t3_comment1);
        t3_comment2=(TextView)findViewById(R.id.t3_comment2);

        Intent intent=getIntent();
        t3_department.setText(intent.getStringExtra("department"));
        t3_major.setText(intent.getStringExtra("major"));
        t3_studentname.setText(intent.getStringExtra("studentname"));
        t3_type.setText(intent.getStringExtra("type"));
        t3_teachername.setText(intent.getStringExtra("teachername"));
        t3_place.setText(intent.getStringExtra("room"));
        t3_time.setText(intent.getStringExtra("time"));
        t3_experts.setText(intent.getStringExtra("experts"));

        t3_score1.setText(intent.getStringExtra("score1"));
        t3_comment1.setText(intent.getStringExtra("comment1"));
        t3_comment2.setText(intent.getStringExtra("comment2"));

    }
}
