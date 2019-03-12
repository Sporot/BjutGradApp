package p.sby.gs_qca.table1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import p.sby.gs_qca.Main.Activity.global_variance;
import p.sby.gs_qca.R;

public class Activity_basicinfo1 extends AppCompatActivity {
    private Button t1_confirm;
    private Spinner t2_institute;
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

        /*******所在院系选择********/
        List<String> listdata_institute = null;
        listdata_institute = new ArrayList<>();
        listdata_institute.add("计算机学院");
        listdata_institute.add("软件学院");
        listdata_institute.add("生命学院");
        listdata_institute.add("物理学院");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Activity_basicinfo1.this, android.R.layout.simple_spinner_item, listdata_institute);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        t2_institute=(Spinner)findViewById(R.id.t2_institute);

       t2_institute.setAdapter(arrayAdapter);



       t2_institute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Toast.makeText(Activity_basicinfo1.this,"点击",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
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
                        FormBody body = new FormBody.Builder()
                                .add("id", "100").build();

                        Request request1 = new Request.Builder()
                                .addHeader("cookie", sessionid)
                                .url("http://117.121.38.95:9817/mobile/form/coursedata/get.ht")
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




               startActivity(new Intent(Activity_basicinfo1.this,Activity_t1class.class));
            }
        });
    }











}