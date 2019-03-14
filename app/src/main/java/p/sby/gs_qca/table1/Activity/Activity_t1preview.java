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
    private TextView t1pre_text;
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
        t1pre_text=(TextView)findViewById(R.id.t1pre_text);

        Intent intent=getIntent();
       t1pre_intitute.setText(intent.getStringExtra("institute"));
       t1pre_coursename.setText(intent.getStringExtra("coursename"));
       t1pre_text.setText(intent.getStringExtra("comment"));


    }
}
