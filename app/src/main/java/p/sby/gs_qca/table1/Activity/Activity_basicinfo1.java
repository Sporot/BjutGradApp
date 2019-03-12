package p.sby.gs_qca.table1.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import p.sby.gs_qca.Main.Activity.global_variance;
import p.sby.gs_qca.R;

public class Activity_basicinfo1 extends AppCompatActivity {
    private Button t1_confirm;
    private String department;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t1basicinfo);

        /*****上方功能栏****/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_t2bi); //主页上方功能条
        toolbar.setTitle("研究生考试试卷规范性评价表");

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
        t1_confirm=(Button) findViewById(R.id.t2_confirm);

        //添加监听事件
        t1_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交确认信息

                //跳转到评分页面
                String sessionid;
                global_variance myssession = ((global_variance)getApplicationContext());
                sessionid = myssession.getSessionid();
                System.out.println(sessionid);

                Thread loginRunnable = new Thread(){

                    @Override
                    public void run() {
                        super.run();
                        OkHttpClient client = new OkHttpClient();
//                        FormBody body = new FormBody.Builder()
//                                .add("id", "100").build();
                        FormBody body = new FormBody.Builder().build();
                        Request request1 = new Request.Builder()
                                .addHeader("cookie", sessionid)
                                .url("http://117.121.38.95:9817/mobile/form/coursedata/getdep.ht")
                                .post(body).build();
                        Call call2 = client.newCall(request1);

                        try {
                            Response response2 = call2.execute();
                            System.out.println(response2);
                            String responseData2 = response2.body().string();
                            System.out.println(responseData2);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                loginRunnable.start();




//                startActivity(new Intent(Activity_basicinfo1.this,Activity_t1class.class));
            }
        });
    }




}