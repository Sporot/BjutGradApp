package p.sby.gs_qca.Main.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import p.sby.gs_qca.R;

public class Activity_t1specific extends AppCompatActivity {
    private TextView t1pre_intitute;
    private TextView t1pre_coursename;
    private TextView t1pre_comment;
    private TextView t1pre_actualnum;
    private TextView t1pre_teachtheme;
    private TextView t1pre_classnum;
    private TextView t1pre_classroom;


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






    }
}
