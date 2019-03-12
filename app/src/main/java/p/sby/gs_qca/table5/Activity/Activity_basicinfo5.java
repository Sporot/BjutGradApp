package p.sby.gs_qca.table5.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import p.sby.gs_qca.R;

public class Activity_basicinfo5 extends AppCompatActivity {
    private Button t5_confirminfo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t5basicinfo);

        /*****上方功能栏****/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_t5bi); //主页上方功能条
        toolbar.setTitle("研究生学位论文答辩评价");

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



        /*****提交按钮点击事件*******/
        //绑定按钮
        t5_confirminfo=(Button) findViewById(R.id.t5_confirminfo);

        //添加监听事件
        t5_confirminfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交确认信息

                //跳转到评分页面
                startActivity(new Intent(Activity_basicinfo5.this,Activity_t5score.class));
            }
        });
    }




}
