package p.sby.gs_qca.table4.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import p.sby.gs_qca.R;

public class Activity_basicinfo4 extends AppCompatActivity {
    private Button t4_next;

    private TextView t4_department;
    private TextView t4_major;
    private TextView t4_studentname;
    private TextView t4_teachername;
    private TextView type;
    private EditText t4_place;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t4basicinfo);

        /*****上方功能栏****/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_t4report); //主页上方功能条
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


        //隐藏软键盘，不让其弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initView();


        setContent();

        /*****提交按钮点击事件*******/


        //添加监听事件
        t4_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交确认信息

                //跳转到评分页面
                startActivity(new Intent(Activity_basicinfo4.this,Activity_t4score.class));
            }
        });
    }

    private void setContent() {
        Intent intent=getIntent();
        t4_department.setText(intent.getStringExtra("department"));
        t4_major.setText(intent.getStringExtra("major"));
        t4_studentname.setText(intent.getStringExtra("studentname"));
        t4_teachername.setText(intent.getStringExtra("teachername"));
        type.setText(intent.getStringExtra("type"));
    }

    private void initView() {
        //绑定按钮

        t4_next=(Button) findViewById(R.id.t4_next);
        t4_department=(TextView)findViewById(R.id.t4_institute);
        t4_major=(TextView)findViewById(R.id.t4_major);
        t4_studentname=(TextView)findViewById(R.id.t4_studentname);
        t4_teachername=(TextView)findViewById(R.id.t4_teachername);
        type=(TextView)findViewById(R.id.t4_type) ;
    }

}
