package p.sby.gs_qca.Main.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import p.sby.gs_qca.Main.Activity.Activity_changepw;
import p.sby.gs_qca.Main.Activity.global_variance;
import p.sby.gs_qca.Main.Adapters.Searcht1Adapter;
import p.sby.gs_qca.Main.Adapters.TableListAdapter;
import p.sby.gs_qca.R;
import p.sby.gs_qca.table4.Activity.Activity_t4report;
import p.sby.gs_qca.table4.Activity.Activity_t4reportlist;
import p.sby.gs_qca.table4.Adapter.t4listAdapter;
import p.sby.gs_qca.widget.DividerListItemDecoration;

public class Activity_searcht1 extends AppCompatActivity {
    private ArrayList<String> datas;
    private Searcht1Adapter adapter;
    private RecyclerView recyclerView;
    private int flag = 0;
    private Button search;
    private String result;
    String sessionid;
    private String url="http://117.121.38.95:9817/mobile/system/user/modifyPwd.ht";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searcht1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_searcht1); //主页上方功能条
        toolbar.setTitle("研究生课堂教学质量评价表");

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


        /*****************查询功能实现****************/
//        search = (Button) findViewById(R.id.btn_t1search);
//
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //      initData(2);
//
//            }
//        });


        /********列表显示部分******************/
        recyclerView = (RecyclerView) findViewById(R.id.searcht1);

        initData();


        //设置recyclerview的适配器
        adapter = new Searcht1Adapter(Activity_searcht1.this, datas);
        recyclerView.setAdapter(adapter);

        //设置Layoutmanager
        recyclerView.setLayoutManager(new LinearLayoutManager(Activity_searcht1.this, LinearLayoutManager.VERTICAL, false));

        //添加Recyclerview的分割线
        recyclerView.addItemDecoration(new DividerListItemDecoration(Activity_searcht1.this, DividerListItemDecoration.VERTICAL_LIST));


        //设计item点击事件
        adapter.setOnItemClickListener(new TableListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String content) {
                // Toast.makeText(Activity_list.this,"data=="+content,Toast.LENGTH_SHORT).show();
                String id = content;
                //根据id值进行不同页面的跳转

                /*****跳转到第一个报告页面*****/
                if (content == datas.get(0)) {
//                    Intent intent = new Intent(Activity_searcht1.this, Activity_t1specific.class);
//                    intent.putExtra("id", id);
//                    startActivity(intent);
                }

            }
        });


    }

    /**
     * 获取报告题目列表
     ******/

    private void initData() {
        global_variance mysession=(global_variance)(getApplication());
        Thread historyListRunnable = new Thread() {
            public void run() {
                super.run();
                sessionid=mysession.getSessionid();
                OkHttpClient client = new OkHttpClient();
                FormBody body = new FormBody.Builder().build();
                Request request1 = new Request.Builder()
                        .addHeader("cookie", sessionid)
                        .url("http://117.121.38.95:9817/mobile/form/jxzl/userlist.ht")
                        .post(body).build();
                Call call = client.newCall(request1);
                try {
                    Response response = call.execute();
                    String responseData = response.body().string();
                    System.out.println(responseData);

                    String  temp=responseData.substring(responseData.indexOf("{"),responseData.lastIndexOf("}") + 1);
//                    System.out.println(temp);
                    try {
                        JSONObject Search=new JSONObject(temp);
                        JSONArray searchlist=new JSONArray(Search.get("JxzlInfo").toString());
                        System.out.println(searchlist);
                        mysession.setSearchlist(searchlist);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }catch (IOException e){
                    e.printStackTrace();
                }
//                setChangeBtnClickable(true);  //这里解放确定按钮，设置为可以点击
//                hideLoading();//隐藏加载框
            }
        };

        historyListRunnable.start();
        try {
            historyListRunnable.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        datas = new ArrayList<>();
        JSONArray searchData=mysession.getSearchlist();
        System.out.println(1111);
        System.out.println(searchData);
//        datas.add("hello");
        for(int i=0;i<searchData.length();i++){
                            try {
                                String temp=searchData.getJSONObject(i).get("course").toString()+searchData.getJSONObject(i).get("id").toString();
                                datas.add(temp);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


    }


}

