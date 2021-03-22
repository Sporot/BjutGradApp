package p.sby.gs_qca.table3.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.FormBody;
import p.sby.gs_qca.Main.Activity.global_variance;
import p.sby.gs_qca.Main.Adapters.InventoryAdapter;
import p.sby.gs_qca.R;
import p.sby.gs_qca.table4.Activity.Activity_t4reportdetail;
import p.sby.gs_qca.table4.Adapter.t4listAdapter;
import p.sby.gs_qca.util.Inventory;
import p.sby.gs_qca.util.RequestUtil;
import p.sby.gs_qca.widget.DividerListItemDecoration;
import p.sby.gs_qca.widget.LoadingDialog;

public class Activity_t3reportlist extends AppCompatActivity {
    private RecyclerView recyclerView;
    private t4listAdapter adapter;


    private JsonArray result;

    String sessionid;
    private String temp1;
    private String getreporturl = "http://116.213.144.72:9817/mobile/form/reportktbg/getreport.ht";
    private List<Inventory> mInventories;
    private InventoryAdapter mInventoryAdapter;
    private LoadingDialog mLoadingDialog; //显示正在加载的对话框

    /********报告信息*********/
    private String department;
    private String major;
    private String experts;
    private String filepath;
    private String id;
    private String type;
    private String studentname;
    private String teachername;
    private String topic;
    private String room;
    private String time;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t3_reportlist);

        /*****上方功能栏****/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_t3reportlist); //主页上方功能条
        toolbar.setTitle("开题报告列表");

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

        Intent intent = getIntent();
        department = intent.getStringExtra("department");
        major = intent.getStringExtra("major");
        type=intent.getStringExtra("type");

        /********列表显示部分******************/
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_list_t3);
        //准备数据集合
        initData();

        adapter=new t4listAdapter(Activity_t3reportlist.this,mInventories);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(Activity_t3reportlist.this, LinearLayoutManager.VERTICAL, false));

        //添加Recyclerview的分割线
        recyclerView.addItemDecoration(new DividerListItemDecoration(Activity_t3reportlist.this, DividerListItemDecoration.VERTICAL_LIST));


        adapter.setOnItemClickListener(new InventoryAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(RecyclerView.Adapter adapter, View v, int position) {

               studentname= mInventories.get(position).getDate();
               teachername=mInventories.get(position).getItemCode();
               topic=mInventories.get(position).getItemDesc();
               filepath=mInventories.get(position).getFilepath();
               experts=mInventories.get(position).getExperts();
               id=mInventories.get(position).getId();
               time=mInventories.get(position).getTime();
               room=mInventories.get(position).getRoom();

               Intent intent=new Intent(Activity_t3reportlist.this,Activity_t3reportdetail.class);
               intent.putExtra("department",department);
               intent.putExtra("major",major);
               intent.putExtra("studentname",studentname);
               intent.putExtra("teachername",teachername);
               intent.putExtra("type",type);
               intent.putExtra("reportid",id);
               intent.putExtra("room",room);
               intent.putExtra("experts",experts);
               intent.putExtra("time",time);
               startActivity(intent);
            }
        });


    }

    /**
     * 获取报告题目列表
     ******/
    private void initData() {
        global_variance mysession = (global_variance) (Activity_t3reportlist.this.getApplication());
        sessionid = mysession.getSessionid();

        Inventory inventory;
        mInventories = new ArrayList<>();

        Thread getReportListRunnable = new Thread() {
            public void run() {
                super.run();

                //System.out.println("在提交的时候打印courseid:"+courseid);
                //添加请求信息
                HashMap<String, String> paramsMap = new HashMap<>();

                paramsMap.put("department", department);
                paramsMap.put("major", major);
                paramsMap.put("type",type);


                Log.i("t4info", "学院 " + department + " 专业：" + major);

                FormBody.Builder builder = new FormBody.Builder();
                for (String key : paramsMap.keySet()) {
                    //追加表单信息
                    builder.add(key, paramsMap.get(key));
                }
                temp1 = RequestUtil.get().MapSend(getreporturl, sessionid, paramsMap);


                try {
                    JSONObject report=new JSONObject(temp1);
                    JSONArray reportlist=new JSONArray(report.get("ReportKtbg").toString());
                    Log.i("t3info", "reportlist"+reportlist);
                    mysession.setReportlist(reportlist);
                    Log.i("t3info", "run: "+mysession.getReportlist());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        getReportListRunnable.start();

        try {
            getReportListRunnable.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JSONArray reportinfo=mysession.getReportlist();
        Log.i("t3info", "initData: "+"*********************************************************");
        Log.i("t3info", "reportinfo"+reportinfo);
        if (reportinfo.length()==0){
            inventory = new Inventory();
            inventory.setItemDesc("暂无数据" );
            //    inventory.setQuantity(random.nextInt(100000));
            inventory.setItemCode("");
            inventory.setDate("");
            //   inventory.setVolume(random.nextFloat());
            mInventories.add(inventory);
        }

        else{
            for(int i=0;i<reportinfo.length();i++){
                try {
                    topic=reportinfo.getJSONObject(i).get("topic").toString();
                    studentname=reportinfo.getJSONObject(i).get("studentname").toString();
                    teachername=reportinfo.getJSONObject(i).get("teachername").toString();
//                    experts=reportinfo.getJSONObject(i).get("experts").toString();
//                    filepath=reportinfo.getJSONObject(i).get("filepath").toString();
                    id=reportinfo.getJSONObject(i).get("id").toString();
                    type=reportinfo.getJSONObject(i).get("type").toString();
//                    room=reportinfo.getJSONObject(i).get("room").toString();
                    time=reportinfo.getJSONObject(i).get("time").toString();
                    System.out.println("**************打印具体信息**************");

                    Log.i("t3info", "initData: "+topic+" "+" "+studentname+" "+teachername);
                    inventory = new Inventory();
                    inventory.setItemDesc(topic);
                    inventory.setItemCode(teachername);
                    inventory.setDate(studentname);
                    inventory.setId(id);
                    inventory.setExperts(experts);
                    inventory.setFilepath(filepath);
                    inventory.setType(type);
                    inventory.setRoom(room);
                    inventory.setTime(time);
                    mInventories.add(inventory);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }

        }

    }
}