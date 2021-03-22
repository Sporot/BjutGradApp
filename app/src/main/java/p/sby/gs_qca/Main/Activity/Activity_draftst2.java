package p.sby.gs_qca.Main.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import p.sby.gs_qca.Main.Adapters.InventoryAdapter;
import p.sby.gs_qca.R;
import p.sby.gs_qca.table1.Activity.Activity_t1class;
import p.sby.gs_qca.util.Inventory;
import p.sby.gs_qca.util.RequestUtil;
import p.sby.gs_qca.util.SlideRecyclerView;
import p.sby.gs_qca.widget.LoadingDialog;

public class Activity_draftst2 extends AppCompatActivity {
    String sessionid;
    private SlideRecyclerView recycler_view_list;
    private List<Inventory> mInventories;
    private InventoryAdapter mInventoryAdapter;
    private LoadingDialog mLoadingDialog; //显示正在加载的对话框
    /******需要传的数据*********/
    private String sendfrom="drafts";
    private String department;
    private String major;
    private String type;
    private String studentname;
    private String teachername;
    private String room;
    private String time;
    private String experts;
    private String score1="";
    private String comment1;
    private String comment2;
    private String reportid;
    private String option;


    private String formidget;//想要获取具体信息的formid
    private String formiddel;//想要删除的formid
    private String listurl="http://116.213.144.72:9817/mobile/form/buff/get.ht";
    private String deleteurl="http://116.213.144.72:9817/mobile/form/buff/delzqkh.ht";
    private String temp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drafts);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_draft); //主页上方功能条
        toolbar.setTitle("我的草稿箱"); //settings
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


        recycler_view_list = (SlideRecyclerView) findViewById(R.id.recycler_view_list);
        recycler_view_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_inset));
        recycler_view_list.addItemDecoration(itemDecoration);

        Inventory inventory;


        initData();
        mInventoryAdapter = new InventoryAdapter(this, mInventories);
        recycler_view_list.setAdapter(mInventoryAdapter);

        /*******点击跳转到相应的草稿界面，并传递已保存数据************/
//        mInventoryAdapter.setOnItemClickListener(new InventoryAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(RecyclerView.Adapter adapter, View v, int position) {
//                TextView textView= (TextView) v.findViewById(R.id.t1_draftname);
//
//                     System.out.println("***********打印点击的content*************");
//                     System.out.println(textView.getText());
//
//                     formidget=mInventories.get(position).getItemCode();
//                System.out.println("***********打印点击的content的formid*************");
//                System.out.println(formidget);
//
//                showLoading();
//                global_variance mysession=(global_variance)(getApplication());
//                sessionid=mysession.getSessionid();
//                Thread getDraftdetailRunnable = new Thread() {
//                    public void run() {
//                        super.run();
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//
//                        HashMap<String,String> paramsMap=new HashMap<>();
//                        paramsMap.put("id",formidget);
//                        temp=RequestUtil.get().MapSend(drafturl,sessionid,paramsMap);
//                            try {
//
//                                JSONObject Draftdata=new JSONObject(temp);
//                                JSONObject DraftDetail=new JSONObject(Draftdata.get("JxzlInfo").toString());
//                                System.out.println("*****************打印DraftDetail*******************");
//                                System.out.println(DraftDetail);
//
//                                /*******获取到存在草稿箱中的数值********/
//                                coursename=DraftDetail.get("course").toString();
//                                classid=DraftDetail.get("classid").toString();
//                                institute=DraftDetail.get("department").toString();
//                                latenum=DraftDetail.get("latenumber").toString();
//                                shouldnum=DraftDetail.get("studentnumber").toString();
//                                actualnum=DraftDetail.get("presentnumber").toString();
//                                comment=DraftDetail.get("comment").toString();
//                                time1=DraftDetail.get("time1").toString();
//                                classroom=DraftDetail.get("room").toString();
//                                classnum=DraftDetail.get("listentime").toString();
//                                courseid=DraftDetail.get("courseid").toString();
//                                teachtheme=DraftDetail.get("topic").toString();
//                                teacher=DraftDetail.get("teacher").toString();
//
//                                score1=DraftDetail.get("score1").toString();
//                                score2=DraftDetail.get("score2").toString();
//                                score3=DraftDetail.get("score3").toString();
//                                score4=DraftDetail.get("score4").toString();
//                                score5=DraftDetail.get("score5").toString();
//                                score6=DraftDetail.get("score6").toString();
//                                score7=DraftDetail.get("score7").toString();
//                                score8=DraftDetail.get("score8").toString();
//                                score9=DraftDetail.get("score9").toString();
//
//
//                                System.out.println("*********************打印获取到的课程名称**************************");
//                                System.out.println(actualnum);
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                    }
//                };
//
//                getDraftdetailRunnable.start();
//                try {
//                    getDraftdetailRunnable.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//
//
//
//
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            Intent intent = new Intent(Activity_draftst2.this, Activity_t1class.class);
//                            intent.putExtra("sendfrom",sendfrom);
//                            intent.putExtra("courseid",courseid);
//                            intent.putExtra("institute",institute);
//                            intent.putExtra("classid",classid);
//                            intent.putExtra("teacher",teacher);
//                            intent.putExtra("classroom",classroom);
//                            intent.putExtra("time1",time1);
//                            intent.putExtra("coursename",coursename);
//                            intent.putExtra("formid",formidget);
//
//
//                            intent.putExtra("shouldnum",shouldnum);
//                            intent.putExtra("actualnum",actualnum);
//                            intent.putExtra("latenum",latenum);
//                            intent.putExtra("teachtheme",teachtheme);
//                            intent.putExtra("classnum",classnum);
//
//
//                            intent.putExtra("score1",score1);
//                            intent.putExtra("score2",score2);
//                            intent.putExtra("score3",score3);
//                            intent.putExtra("score4",score4);
//                            intent.putExtra("score5",score5);
//                            intent.putExtra("score6",score6);
//                            intent.putExtra("score7",score7);
//                            intent.putExtra("score8",score8);
//                            intent.putExtra("score9",score9);
//
//                            intent.putExtra("comment",comment);
//                            System.out.println("***************打印从草稿传递到编辑页的数据***********************");
//                            System.out.println("actualnum:   "+actualnum);
//                            System.out.println("teacher:   "+teacher);
//                            startActivity(intent);
//                        }
//
//                    },720);
//
//                hideLoading();//隐藏加载框
//            }
//        });

        /*************删除一条草稿记录********************/
        mInventoryAdapter.setOnDeleteClickListener(new InventoryAdapter.OnDeleteClickLister() {
            @Override
            public void onDeleteClick(View view, int position) {
                formiddel=mInventories.get(position).getItemCode();
                System.out.println("*********打印要删除的id************");
                System.out.println(formiddel);


                /********************向服务器提交id*****************************/

                global_variance mysession=(global_variance)(getApplication());
                sessionid=mysession.getSessionid();
                Thread DraftDeleteRunnable = new Thread() {
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        HashMap<String,String> paramsMap=new HashMap<>();
                        paramsMap.put("id",formiddel);
                        FormBody.Builder builder = new FormBody.Builder();
                        for (String key : paramsMap.keySet()) {
                            //追加表单信息
                            builder.add(key, paramsMap.get(key));
                        }

                        OkHttpClient client = new OkHttpClient();
                        RequestBody body = builder.build();
                        Request request1 = new Request.Builder()
                                .addHeader("cookie", sessionid)
                                .url("http://116.213.144.72:9817/mobile/form/buff/deljxzl.ht")
                                .post(body).build();
                        Call call = client.newCall(request1);
                        try {
                            Response response = call.execute();
                            String responseData = response.body().string();
                            System.out.println(responseData);

                            String  temp=responseData.substring(responseData.indexOf("{"),responseData.lastIndexOf("}") + 1);
                            System.out.println(temp);
                            try {

                                JSONObject Draftdel=new JSONObject(temp);
                                String delinfo=Draftdel.getString("result");
                                System.out.println(delinfo);
                                if(delinfo.equals("100")){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toasty.success(Activity_draftst2.this,"删除成功！",Toasty.LENGTH_SHORT).show();
                                        }
                                    });
                                }
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

                DraftDeleteRunnable.start();
                try {
                    DraftDeleteRunnable.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                mInventories.remove(position);
                mInventoryAdapter.notifyDataSetChanged();
                recycler_view_list.closeMenu();
            }
        });
    }


    /**加载进度框**/
    public void showLoading () {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this, getString(R.string.loading), false);
        }
        mLoadingDialog.show();
    }

    /**隐藏进度框**/
    public void hideLoading() {
        if (mLoadingDialog != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLoadingDialog.hide();
                }
            });

        }
    }

    /****************加载数据**********/
    private void initData() {
       Inventory inventory;
        mInventories = new ArrayList<>();

        global_variance mysession=(global_variance)(getApplication());
        Thread historyListRunnable = new Thread() {
            public void run() {
                super.run();
                sessionid=mysession.getSessionid();
                OkHttpClient client = new OkHttpClient();
                FormBody body = new FormBody.Builder().build();
                Request request1 = new Request.Builder()
                        .addHeader("cookie", sessionid)
                        .url(listurl)
                        .post(body).build();
                Call call = client.newCall(request1);
                try {
                    Response response = call.execute();
                    String responseData = response.body().string();
                    Log.i("t4drafts", "run: "+responseData);

                    String  temp=responseData.substring(responseData.indexOf("{"),responseData.lastIndexOf("}") + 1);
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
            inventory = new Inventory();
            inventory.setItemDesc("暂无草稿" );
            inventory.setItemCode("");
            inventory.setDate("");
            mInventories.add(inventory);
        }
        else{
            for(int i=0;i<draftData.length();i++){
                try {
                    String draftname=draftData.getJSONObject(i).get("topic1").toString();
                    String date=draftData.getJSONObject(i).get("time").toString();
                    String formid=draftData.getJSONObject(i).get("formid").toString();
                    Log.i("dra", "initData: "+draftname);
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
