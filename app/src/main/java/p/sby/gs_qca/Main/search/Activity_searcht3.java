package p.sby.gs_qca.Main.search;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import p.sby.gs_qca.Main.Activity.global_variance;
import p.sby.gs_qca.Main.Adapters.T1searchAdapter;
import p.sby.gs_qca.R;
import p.sby.gs_qca.table4.Activity.Activity_t4preview;
import p.sby.gs_qca.util.Inventory;
import p.sby.gs_qca.util.RequestUtil;
import p.sby.gs_qca.widget.DividerListItemDecoration;

public class Activity_searcht3 extends AppCompatActivity {
    private T1searchAdapter adapter;
    private List<Inventory> mInventories; //存放查询列表数据
    private RecyclerView recyclerView;
    String sessionid;
    private String formid;
    private String temp;
    private String url="http://117.121.38.95:9817/mobile/form/ktbg/userlist.ht";
    private String searchurl="http://117.121.38.95:9817/mobile/form/ktbg/get.ht";

    private String deparment;
    private String major;
    private String studentname;
    private String teachername;
    private String room;
    private String time;
    private String experts;
    private String type;

    private String comment1;
    private String comment2;
    private String score1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searcht4);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_searcht4); //主页上方功能条
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


        /********列表显示部分******************/
        recyclerView = (RecyclerView) findViewById(R.id.searcht4);

        initData();


        //设置recyclerview的适配器
//        adapter = new Searcht1Adapter(Activity_searcht1.this,mInventories); //旧版本的Adapter
        adapter=new T1searchAdapter(Activity_searcht3.this,mInventories);
        recyclerView.setAdapter(adapter);

        //设置Layoutmanager
        recyclerView.setLayoutManager(new LinearLayoutManager(Activity_searcht3.this, LinearLayoutManager.VERTICAL, false));

        //添加Recyclerview的分割线
        recyclerView.addItemDecoration(new DividerListItemDecoration(Activity_searcht3.this, DividerListItemDecoration.VERTICAL_LIST));


        //设计item点击事件
        adapter.setOnItemClickListener(new T1searchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.Adapter adapter, View v, int position) {

                formid=mInventories.get(position).getItemCode();
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
                        temp=RequestUtil.get().MapSend(searchurl,sessionid,paramsMap);

                        try {

                            JSONObject Searchdata=new JSONObject(temp);
                            JSONObject Searchinfo=new JSONObject(Searchdata.get("KtbgInfo").toString());
                            JSONObject Searchinfoplus=new JSONObject(Searchdata.get("KtbgInfoPlus").toString());
                            Log.i("t4search", "run: "+Searchinfoplus);

                            /*******获取到存在草稿箱中的数值********/
                            deparment=Searchinfo.get("department").toString();
                            major=Searchinfo.get("major").toString();
                            studentname=Searchinfo.get("studentname").toString();
                            time=Searchinfo.get("time").toString();
                            teachername=Searchinfo.get("teachername").toString();
                            type=Searchinfo.get("type").toString();
                            room=Searchinfo.get("room").toString();
                            experts=Searchinfo.get("experts").toString();

                            comment1=Searchinfoplus.get("comment1").toString();
                            comment2=Searchinfoplus.get("comment2").toString();
                            score1=Searchinfoplus.get("score1").toString();

                            Log.i("t4search", "run: "+comment1);

                        } catch (JSONException e) {
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
                        Intent intent = new Intent(Activity_searcht3.this, Activity_t4preview.class);
                        intent.putExtra("department",deparment);
                        intent.putExtra("major",major);
                        intent.putExtra("studentname",studentname);
                        intent.putExtra("type",type);
                        intent.putExtra("teachername",teachername);
                        intent.putExtra("room",room);
                        intent.putExtra("time",time);
                        intent.putExtra("experts",experts);

                        intent.putExtra("score1",score1);
                        intent.putExtra("comment1",comment1);
                        intent.putExtra("comment2",comment2);
                        startActivity(intent);
                    }

                },650);


            }
        });


    }

    private void initData() {
        Inventory inventory;
        mInventories = new ArrayList<>();

        global_variance mysession=(global_variance)(getApplication());
        Thread searchListRunnable = new Thread() {
            public void run() {
                super.run();
                sessionid=mysession.getSessionid();

                temp=RequestUtil.get().sendrequest(url,sessionid,"","");

                try {
                    JSONObject Search=new JSONObject(temp);
                    JSONArray draftlist=new JSONArray(Search.get("KtbgInfo").toString());
                    Log.i("t4search", "run: "+draftlist);
                    mysession.setSearchlist(draftlist);

                } catch (JSONException e) {
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
        JSONArray searchData=mysession.getSearchlist();
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
                    String topic=searchData.getJSONObject(i).get("topic").toString();
                    String time=searchData.getJSONObject(i).get("uptime").toString();
                    String formid=searchData.getJSONObject(i).get("id").toString();
                    Log.i("t4search", "initData: "+topic+" "+time+" "+formid);
                    inventory = new Inventory();
                    inventory.setItemDesc(topic);
                    inventory.setItemCode(formid);
                    inventory.setDate(time);
                    mInventories.add(inventory);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
