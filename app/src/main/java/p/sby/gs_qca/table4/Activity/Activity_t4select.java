package p.sby.gs_qca.table4.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import p.sby.gs_qca.R;

public class Activity_t4select extends AppCompatActivity {
    private Button t4_select;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t4_select);


        /*****上方功能栏****/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_t4select); //主页上方功能条
        toolbar.setTitle("培养环节质量评价-中期");

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
        t4_select=(Button) findViewById(R.id.t4_confirm);

        //添加监听事件
        t4_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交确认信息

                //跳转到查看报告的页面
                startActivity(new Intent(Activity_t4select.this,Activity_t4reportlist.class));
            }
        });


    }
}
