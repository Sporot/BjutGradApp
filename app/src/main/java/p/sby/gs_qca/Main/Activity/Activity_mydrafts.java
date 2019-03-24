package p.sby.gs_qca.Main.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import p.sby.gs_qca.Main.Adapters.MyDraftListAdapter;
import p.sby.gs_qca.Main.Adapters.TableListAdapter;
import p.sby.gs_qca.R;
import p.sby.gs_qca.table1.Activity.Activity_t1class;
import p.sby.gs_qca.widget.DividerListItemDecoration;

public class Activity_mydrafts extends AppCompatActivity {

    String sessionid;
    private int flag=0;

    /******需要传的数据*********/
    private String coursename="";
    private String institute="";
    private String latenum="";
    private String listentime="";
    private String room="";
    private String teacher="";
    private String time1="";
    private String courseid="";
    private String actualnum="";
    private String shouldnum="";
    private String comment="";
    private String classid="";


    private String score1="";
    private String score2="";
    private String score3="";
    private String score4="";
    private String score5="";
    private String score6="";
    private String score7="";
    private String score8="";
    private String score9="";

    private String formid;
    private RecyclerView recyclerView;
    private ArrayList<String> datas;
    private MyDraftListAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydraft);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_mydraft); //主页上方功能条
        toolbar.setTitle("我的草稿");

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



        /********列表显示部分******************/
        recyclerView=(RecyclerView)findViewById(R.id.mydraftlist);

        //准备数据集合
        initData();

        //设置recyclerview的适配器
        adapter=new MyDraftListAdapter(Activity_mydrafts.this,datas);
        recyclerView.setAdapter(adapter);


        //设置Layoutmanager
        recyclerView.setLayoutManager(new LinearLayoutManager(Activity_mydrafts.this,LinearLayoutManager.VERTICAL,false));

        //添加Recyclerview的分割线
        recyclerView.addItemDecoration(new DividerListItemDecoration(Activity_mydrafts.this,DividerListItemDecoration.VERTICAL_LIST));


        //设计item点击事件
        adapter.setOnItemClickListener(new TableListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String content) {
                // Toast.makeText(Activity_list.this,"data=="+content,Toast.LENGTH_SHORT).show();
              //  String id = content;
                //根据id值进行不同页面的跳转

                /******获取到每个草稿的formid*********/
                String regEx="[^0-9]";
                Pattern p= Pattern.compile(regEx);
                Matcher m=p.matcher(content);
                formid=m.replaceAll("").trim();

                global_variance mysession=(global_variance)(getApplication());
                sessionid=mysession.getSessionid();
                Thread getDraftdetailRunnable = new Thread() {
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        HashMap<String,String> paramsMap=new HashMap<>();
                        paramsMap.put("id",formid);
                        FormBody.Builder builder = new FormBody.Builder();
                        for (String key : paramsMap.keySet()) {
                            //追加表单信息
                            builder.add(key, paramsMap.get(key));
                        }

                        OkHttpClient client = new OkHttpClient();
                        RequestBody body = builder.build();
                        Request request1 = new Request.Builder()
                                .addHeader("cookie", sessionid)
                                .url("http://117.121.38.95:9817/mobile/form/buff/getjxzl.ht")
                                .post(body).build();
                        Call call = client.newCall(request1);
                        try {
                            Response response = call.execute();
                            String responseData = response.body().string();
                            System.out.println(responseData);

                            String  temp=responseData.substring(responseData.indexOf("{"),responseData.lastIndexOf("}") + 1);
                            System.out.println(temp);
                            try {

                                JSONObject Draftdata=new JSONObject(temp);
                                JSONObject DraftDetail=new JSONObject(Draftdata.get("JxzlInfo").toString());
                                System.out.println(DraftDetail);

                                /*******获取到存在草稿箱中的数值********/
                                coursename=DraftDetail.get("course").toString();
                                classid=DraftDetail.get("classid").toString();
                                institute=DraftDetail.get("department").toString();
                                latenum=DraftDetail.get("latenumber").toString();
                                shouldnum=DraftDetail.get("studentnumber").toString();
                                actualnum=DraftDetail.get("presentnumber").toString();
                                comment=DraftDetail.get("comment").toString();
                                time1=DraftDetail.get("time1").toString();
                                classid=DraftDetail.get("classid").toString();

                                score1=DraftDetail.get("score1").toString();
                                score2=DraftDetail.get("score2").toString();
                                score3=DraftDetail.get("score3").toString();
                                score4=DraftDetail.get("score4").toString();
                                score5=DraftDetail.get("score5").toString();
                                score6=DraftDetail.get("score6").toString();
                                score7=DraftDetail.get("score7").toString();
                                score8=DraftDetail.get("score8").toString();
                                score9=DraftDetail.get("score9").toString();


                                System.out.println("***********************************************");
                                System.out.println(coursename);

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

               getDraftdetailRunnable.start();
                try {
                   getDraftdetailRunnable.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                System.out.println(formid);

                System.out.println(datas.get(0));
                /*****跳转到编辑页面并传入数据*****/
//                if(content==datas.get(0)){
//                    Intent intent = new Intent(Activity_mydrafts.this, Activity_basicinfo1.class);
//                    intent.putExtra("id",formid);
//                    startActivity(intent);
//                }

                Intent intent=new Intent(Activity_mydrafts.this,Activity_t1class.class);

            }
        });



    }

    /**获取报告题目列表******/
    private void initData() {
        datas=new ArrayList<>();

        global_variance mysession=(global_variance)(getApplication());
        Thread historyListRunnable = new Thread() {
            public void run() {
                super.run();
                sessionid=mysession.getSessionid();
                OkHttpClient client = new OkHttpClient();
                FormBody body = new FormBody.Builder().build();
                Request request1 = new Request.Builder()
                        .addHeader("cookie", sessionid)
                        .url("http://117.121.38.95:9817/mobile/form/buff/get.ht")
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
                        JSONArray draftlist=new JSONArray(Search.get("FormBuff").toString());
                        System.out.println(draftlist);
                        mysession.setDraftlist(draftlist);

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
        JSONArray draftData=mysession.getDraftlist();
        System.out.println(1111);
        System.out.println(draftData);
        if(draftData.length()==0)
        {
            datas.add("暂无草稿");
        }
        else{
            for(int i=0;i<draftData.length();i++){
                try {
                    String temp=draftData.getJSONObject(i).get("topic1").toString()+"("+draftData.getJSONObject(i).get("id").toString()+")";
                    datas.add(temp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }




    }

}

