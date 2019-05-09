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
import p.sby.gs_qca.table2.Activity.Activity_t2preview;
import p.sby.gs_qca.util.Inventory;
import p.sby.gs_qca.util.RequestUtil;
import p.sby.gs_qca.widget.DividerListItemDecoration;

public class Activity_searcht2 extends AppCompatActivity {

    private T1searchAdapter adapter;
    private List<Inventory> mInventories; //存放查询列表数据
    private RecyclerView recyclerView;
    String sessionid;
    private String formid;
    private String temp;
    private String url="http://117.121.38.95:9817/mobile/form/sjgf/userlist.ht";
    private String searchurl="http://117.121.38.95:9817/mobile/form/sjgf/get.ht";

    private String institute="";
    private String course="";
    private String teacher="";
    private String papernum="";
    private String room="";


    private String comment1;

    private String score1;
    private String score2;
    private String score3;
    private String score4;
    private String score5;
    private String score6;
    private String score7;
    private String score8;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searcht2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_searcht2); //主页上方功能条
        toolbar.setTitle("考试试卷规范性评价");

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

        recyclerView = (RecyclerView) findViewById(R.id.searcht2);

        initData();

        adapter=new T1searchAdapter(Activity_searcht2.this,mInventories);
        recyclerView.setAdapter(adapter);

        //设置Layoutmanager
        recyclerView.setLayoutManager(new LinearLayoutManager(Activity_searcht2.this, LinearLayoutManager.VERTICAL, false));

        //添加Recyclerview的分割线
        recyclerView.addItemDecoration(new DividerListItemDecoration(Activity_searcht2.this, DividerListItemDecoration.VERTICAL_LIST));

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
                            JSONObject Searchinfo=new JSONObject(Searchdata.get("SjgfInfo").toString());
                            JSONObject Searchinfoplus=new JSONObject(Searchdata.get("SjgfInfoPlus").toString());
                            Log.i("t2search", "run: "+Searchinfoplus);
                            System.out.println("Searchinfo"+Searchinfo);
                            System.out.println("Searchinfoplus"+Searchinfoplus);
                            /*******获取到存在草稿箱中的数值********/
                            institute=Searchinfo.get("department").toString();
                            System.out.println("111"+institute);
                            course=Searchinfo.get("course").toString();
                            System.out.println("222"+course);
                            teacher=Searchinfo.get("teacher").toString();
                            System.out.println("333"+teacher);
                            room=Searchinfo.get("room").toString();
                            System.out.println("444"+room);
                            papernum=Searchinfo.get("papernumber").toString();
                            System.out.println("555"+papernum);
//                            experts=Searchinfo.get("experts").toString();

                            comment1=Searchinfoplus.get("comment1").toString();
                            System.out.println("666"+ comment1);

                            score1=Searchinfoplus.get("score1").toString();
                            System.out.println("777"+ score1);
                            score2=Searchinfoplus.get("score2").toString();
                            score3=Searchinfoplus.get("score3").toString();
                            score4=Searchinfoplus.get("score4").toString();
                            score5=Searchinfoplus.get("score5").toString();
                            score6=Searchinfoplus.get("score6").toString();
                            score7=Searchinfoplus.get("score7").toString();
                            score8=Searchinfoplus.get("score8").toString();





//                            Log.i("t5search", "run: "+comment1);

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
                        Intent intent = new Intent(Activity_searcht2.this, Activity_t2preview.class);
//                        intent.putExtra("sendfrom","search");
//                        System.out.println("111");
//                        System.out.println(institute);
//                        System.out.println("11");
//                        System.out.println(course);
                        intent.putExtra("institute",institute);
                        intent.putExtra("coursename",course);
                        intent.putExtra("teacher",teacher);
                        intent.putExtra("classroom",room);
                        intent.putExtra("papernum",papernum);
                        intent.putExtra("score1",score1);
                        intent.putExtra("score2",score2);
                        intent.putExtra("score3",score3);
                        intent.putExtra("score4",score4);
                        intent.putExtra("score5",score5);
                        intent.putExtra("score6",score6);
                        intent.putExtra("score7",score7);
                        intent.putExtra("score8",score8);
                        intent.putExtra("comment",comment1);
//                        intent.putExtra("comment2",comment2);
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
                    JSONArray draftlist=new JSONArray(Search.get("SjgfInfo").toString());
                    Log.i("t5search", "run: "+draftlist);
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
                    String draftname=searchData.getJSONObject(i).get("course").toString();
                    String date=searchData.getJSONObject(i).get("uptime").toString();
                    String formid=searchData.getJSONObject(i).get("id").toString();
//                    Log.i("t2search", "initData: "+topic+" "+time+" "+formid);
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
