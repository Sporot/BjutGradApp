package p.sby.gs_qca.Main.search;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import p.sby.gs_qca.Main.Activity.global_variance;
import p.sby.gs_qca.Main.Adapters.T1searchAdapter;
import p.sby.gs_qca.R;
import p.sby.gs_qca.util.Inventory;
import p.sby.gs_qca.widget.DividerListItemDecoration;

public class Activity_searcht1 extends AppCompatActivity {
    private ArrayList<String> datas;
   // private Searcht1Adapter adapter;
    private T1searchAdapter adapter;
    private List<Inventory> mInventories; //存放查询列表数据
    private RecyclerView recyclerView;
    private int flag = 0;
    private Button search;
    private String result;
    String sessionid;
    private String url="http://117.121.38.95:9817/mobile/form/jxzl/userlist.ht";

    /*******定义相关参数******************/
    private String formid;


    private String coursename="";
    private String institute="";
    private String latenum="";
    private String classnum="";
    private String room="";
    private String teacher="";
    private String time1="";
    private String courseid="";
    private String actualnum="";
    private String shouldnum="";
    private String comment="";
    private String classid="";
    private String classroom="";
    private String teachtheme="";


    private String score1="";
    private String score2="";
    private String score3="";
    private String score4="";
    private String score5="";
    private String score6="";
    private String score7="";
    private String score8="";
    private String score9="";

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
//        adapter = new Searcht1Adapter(Activity_searcht1.this,mInventories); //旧版本的Adapter
        adapter=new T1searchAdapter(Activity_searcht1.this,mInventories);
        recyclerView.setAdapter(adapter);

        //设置Layoutmanager
        recyclerView.setLayoutManager(new LinearLayoutManager(Activity_searcht1.this, LinearLayoutManager.VERTICAL, false));

        //添加Recyclerview的分割线
        recyclerView.addItemDecoration(new DividerListItemDecoration(Activity_searcht1.this, DividerListItemDecoration.VERTICAL_LIST));


      //设计item点击事件
        adapter.setOnItemClickListener(new T1searchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.Adapter adapter, View v, int position) {

                TextView textView= (TextView) v.findViewById(R.id.t1_draftname);

                System.out.println("***********打印点击的content*************");
                System.out.println(textView.getText());

                formid=mInventories.get(position).getItemCode();
                System.out.println("***********打印点击的content的formid*************");
                System.out.println(formid);

                global_variance mysession=(global_variance)(getApplication());
                sessionid=mysession.getSessionid();
                Thread getSearchdetailRunnable = new Thread() {
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
                                .url("http://117.121.38.95:9817/mobile/form/jxzl/get.ht")
                                .post(body).build();
                        Call call = client.newCall(request1);
                        try {
                            Response response = call.execute();
                            String responseData = response.body().string();
                            System.out.println(responseData);

                            String  temp=responseData.substring(responseData.indexOf("{"),responseData.lastIndexOf("}") + 1);
                            System.out.println(temp);
                            try {

                                JSONObject Searchdata=new JSONObject(temp);
                                JSONObject Searchinfo=new JSONObject(Searchdata.get("JxzlInfo").toString());
                                System.out.println("*****************打印Searchinfo*******************");
                                System.out.println(Searchinfo);

                                JSONObject Searchinfoplus=new JSONObject(Searchdata.get("JxzlInfoPlus").toString());
                                System.out.println("*****************打印Searchinfoplus*******************");
                                System.out.println(Searchinfoplus);


                                /*******获取到存在草稿箱中的数值********/
                                coursename=Searchinfo.get("course").toString();
                                classid=Searchinfo.get("classid").toString();
                                institute=Searchinfo.get("department").toString();
                                latenum=Searchinfo.get("latenumber").toString();
                                shouldnum=Searchinfo.get("studentnumber").toString();
                                actualnum=Searchinfo.get("presentnumber").toString();
                                time1=Searchinfo.get("time1").toString();
                                classroom=Searchinfo.get("room").toString();
                                classnum=Searchinfo.get("listentime").toString();
                                courseid=Searchinfo.get("courseid").toString();
                                teachtheme=Searchinfo.get("topic").toString();
                                teacher=Searchinfo.get("teacher").toString();

                                comment=Searchinfoplus.get("comment").toString();
                                score1=Searchinfoplus.get("score1").toString();
                                score2=Searchinfoplus.get("score2").toString();
                                score3=Searchinfoplus.get("score3").toString();
                                score4=Searchinfoplus.get("score4").toString();
                                score5=Searchinfoplus.get("score5").toString();
                                score6=Searchinfoplus.get("score6").toString();
                                score7=Searchinfoplus.get("score7").toString();
                                score8=Searchinfoplus.get("score8").toString();
                                score9=Searchinfoplus.get("score9").toString();


                                System.out.println("*********************打印获取到的课程名称**************************");
                                System.out.println("actualnum: "+actualnum);
                                System.out.println("comment:  "+comment);
                                System.out.println("score1:  "+score1);
//
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                };

                getSearchdetailRunnable.start();
                try {
                    getSearchdetailRunnable.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                /*******进行页面跳转，并传递数据**********/
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Activity_searcht1.this, Activity_t1submit.class);
                        intent.putExtra("courseid",courseid);
                        intent.putExtra("institute",institute);
                        intent.putExtra("classid",classid);
                        intent.putExtra("teacher",teacher);
                        intent.putExtra("classroom",classroom);
                        intent.putExtra("time1",time1);
                        intent.putExtra("coursename",coursename);


                        intent.putExtra("shouldnum",shouldnum);
                        intent.putExtra("actualnum",actualnum);
                        intent.putExtra("latenum",latenum);
                        intent.putExtra("teachtheme",teachtheme);
                        intent.putExtra("classnum",classnum);

                        intent.putExtra("score1",score1);
                        intent.putExtra("score2",score2);
                        intent.putExtra("score3",score3);
                        intent.putExtra("score4",score4);
                        intent.putExtra("score5",score5);
                        intent.putExtra("score6",score6);
                        intent.putExtra("score7",score7);
                        intent.putExtra("score8",score8);
                        intent.putExtra("score9",score9);

                        intent.putExtra("comment",comment);
                        System.out.println("***************打印从草稿传递到编辑页的数据***********************");
                        System.out.println("actualnum:   "+actualnum);
                        System.out.println("teacher:   "+teacher);
                        startActivity(intent);
                    }

                },650);


            }
        });

    }

    /**
     * 获取查询列表信息
     ******/
    private void initData() {
        Inventory inventory;
        mInventories = new ArrayList<>();

        global_variance mysession=(global_variance)(getApplication());
        Thread searchListRunnable = new Thread() {
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
                        JSONArray draftlist=new JSONArray(Search.get("JxzlInfo").toString());
                        System.out.println(draftlist);
                        mysession.setDraftlist(draftlist);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        };

        searchListRunnable.start();
        try {
            searchListRunnable.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONArray searchData=mysession.getDraftlist();
        System.out.println(1111);
        System.out.println(searchData);
        if(searchData.length()==0)
        {
            inventory = new Inventory();
            inventory.setItemDesc("暂无查询结果" );
            //    inventory.setQuantity(random.nextInt(100000));
            inventory.setItemCode("");
            inventory.setDate("");
            //   inventory.setVolume(random.nextFloat());
            mInventories.add(inventory);
        }
        else{
            for(int i=0;i<searchData.length();i++){
                try {
                    String draftname=searchData.getJSONObject(i).get("course").toString();
                    String date=searchData.getJSONObject(i).get("uptime").toString();
                    String formid=searchData.getJSONObject(i).get("id").toString();
                    System.out.println("**************打印具体查询列表信息**************");
                    System.out.println(draftname+"  "+date+"  "+formid);
                    inventory = new Inventory();
                    inventory.setItemDesc(draftname);
                    inventory.setItemCode(formid);
                    inventory.setDate(date);
                    mInventories.add(inventory);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }



    }

}

