package p.sby.gs_qca.Main.Activity;

/*****test the commit****/
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import p.sby.gs_qca.Main.Adapters.TableListAdapter;
import p.sby.gs_qca.Main.search.Activity_searchlist;
import p.sby.gs_qca.R;
import p.sby.gs_qca.table1.Activity.Activity_t1basicinfo_dep;
import p.sby.gs_qca.table1.Activity.Activity_t1searchselect;
import p.sby.gs_qca.table2.Activity.Activity_t2basicinfo_dep;
import p.sby.gs_qca.table2.Activity.Activity_t2searchselect;
import p.sby.gs_qca.table3.Activity.Activity_t3select;
import p.sby.gs_qca.table4.Activity.Activity_t4select;
import p.sby.gs_qca.table5.Activity.Activity_basicinfo5;
import p.sby.gs_qca.util.RequestUtil;
import p.sby.gs_qca.util.SharedPreferencesUtils;
import p.sby.gs_qca.widget.DividerListItemDecoration;
import p.sby.gs_qca.widget.LoadingDialog;

public class Activity_list extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView table_list;
    private ArrayList<String> datas;
    private LoadingDialog mLoadingDialog;
    static long lastTimeMillis;
    private final long MIN_CLICK_INTERVAL=2000;
    private String name;
    private String versionurl="http://116.213.144.72:9817/appversion.ht";
    private String version="ad1121";
    private String temp;
    private String appversion="";
    String sessionid;
//    private TextView fullname;

    protected boolean isTimeEnable(){
        long currentTimeMillis = System.currentTimeMillis();
        if((currentTimeMillis-lastTimeMillis)>MIN_CLICK_INTERVAL){
            lastTimeMillis=currentTimeMillis;
            return true;
        }
        return false;
    }

    private TableListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); //主页上方功能条
        toolbar.setTitle("北京工业大学研究生培养");
        setSupportActionBar(toolbar);

        //表格列表展示
        table_list=(RecyclerView)findViewById(R.id.table_list);
        //准备数据集合，展示列表
        datas=new ArrayList<>();
        datas.add("课堂教学质量评价");
        datas.add("考试试卷规范性评价");
        datas.add("培养环节质量评价-开题报告");
        datas.add("培养环节质量评价-中期考核");
        datas.add("学位论文答辩情况评价");

        //设置recycler适配器
        adapter=new TableListAdapter(Activity_list.this,datas);
        table_list.setAdapter(adapter);

        global_variance myssession = ((global_variance)getApplicationContext());   //声明全局变量类
        sessionid =myssession.getSessionid(); //获取本次登陆中的会话cookie


        //设置Layoutmanager
        table_list.setLayoutManager(new LinearLayoutManager(Activity_list.this,LinearLayoutManager.VERTICAL,false));

        //添加Recyclerview的分割线
        table_list.addItemDecoration(new DividerListItemDecoration(Activity_list.this,DividerListItemDecoration.VERTICAL_LIST));

        //设计item点击事件
        adapter.setOnItemClickListener(new TableListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String content) {
                //Toast.makeText(Activity_list.this,"data=="+content,Toast.LENGTH_SHORT).show();
                String id = content;
                //根据id值进行不同页面的跳转

                /*****跳转到研究生课堂教学质量评价表*****/
                if(content=="课堂教学质量评价"){
                    if(isTimeEnable()) {

                        Intent intent = new Intent(Activity_list.this, Activity_t1searchselect.class);
                        intent.putExtra("id", id);
                        startActivity(intent);

                    }

                }

                if(content=="考试试卷规范性评价"){
                    if(isTimeEnable()) {
                        Intent intent = new Intent(Activity_list.this, Activity_t2searchselect.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                }

                if(content=="培养环节质量评价-开题报告"){
                    if(isTimeEnable()) {
                        Intent intent = new Intent(Activity_list.this, Activity_t3select.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                }

                if(content=="培养环节质量评价-中期考核"){
                    if(isTimeEnable()) {
                        Intent intent = new Intent(Activity_list.this, Activity_t4select.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                }

                if(content=="学位论文答辩情况评价"){
                    if(isTimeEnable()) {
                        Intent intent = new Intent(Activity_list.this, Activity_basicinfo5.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                }

            }

        });

        Thread Getversion=new Thread(){
            @Override
            public void run() {
                super.run();


                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("hello22");
                temp=RequestUtil.get().sendrequest(versionurl,sessionid,"","");
                System.out.print(temp);
                try {
                    JSONObject version1=new JSONObject(temp);
                    System.out.print(version1);
                    appversion=version1.get("android").toString();
//                    System.out.println("*************打印CourseDetail***************");
//                    System.out.println(appversion);

                    if(appversion.equals(version)==false){
                        System.out.println("your version is not the newest");
                        showToastwarning("您现在的版本并非最新版本，请您根据需求重新下载");
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
//        System.out.println("hello11");
        Getversion.start();



        //抽屉面板功能区
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
//        global_variance myssession = ((global_variance)getApplicationContext());
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView=navigationView.getHeaderView(0);
        TextView textview =(TextView)headerView.findViewById(R.id.fullname);;
        name=myssession.getUsername();
        textview.setText(name);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





    @Override

    //面板上按钮功能实现区
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_draft) {
           //跳转至草稿页面
            startActivity(new Intent(this,Activity_draftst1.class));
        }

        else if(id==R.id.nav_search){
            //跳转至搜索页面
            startActivity(new Intent(this,Activity_searchlist.class));

        }
        else if(id==R.id.nav_settings){
            //跳转至设置界面
            startActivity(new Intent(this,Activity_changepw.class));
        }




         else if (id == R.id.nav_manage) {
            SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
            //创建记住密码和自动登录是默认不选,密码为空
            helper.putValues(new SharedPreferencesUtils.ContentValue("autoLogin", false));
            startActivity(new Intent(this, Activity_login.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showToastwarning(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toasty.warning(Activity_list.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
