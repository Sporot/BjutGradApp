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

import java.util.ArrayList;

import p.sby.gs_qca.Main.Adapters.TableListAdapter;
import p.sby.gs_qca.Main.search.Activity_searchlist;
import p.sby.gs_qca.R;
import p.sby.gs_qca.table1.Activity.Activity_basicinfo1;
import p.sby.gs_qca.table2.Activity.Activity_basicinfo2;
import p.sby.gs_qca.table3.Activity.Activity_t3select;
import p.sby.gs_qca.table4.Activity.Activity_t4select;
import p.sby.gs_qca.table5.Activity.Activity_basicinfo5;
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
        datas.add("研究生课堂教学质量评价表");
        datas.add("研究生考试试卷规范性评价表");
        datas.add("研究生培养环节质量评价表-开题报告");
        datas.add("研究生培养环节质量评价表-中期考核");
        datas.add("研究生学位论文答辩情况评价表");

        //设置recycler适配器
        adapter=new TableListAdapter(Activity_list.this,datas);
        table_list.setAdapter(adapter);


        //设置Layoutmanager
        table_list.setLayoutManager(new LinearLayoutManager(Activity_list.this,LinearLayoutManager.VERTICAL,false));

        //添加Recyclerview的分割线
        table_list.addItemDecoration(new DividerListItemDecoration(Activity_list.this,DividerListItemDecoration.VERTICAL_LIST));

        //设计item点击事件
        adapter.setOnItemClickListener(new TableListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String content) {
                Toast.makeText(Activity_list.this,"data=="+content,Toast.LENGTH_SHORT).show();
                String id = content;
                //根据id值进行不同页面的跳转

                /*****跳转到研究生课堂教学质量评价表*****/
                if(content=="研究生课堂教学质量评价表"){
                    if(isTimeEnable()) {

                        Intent intent = new Intent(Activity_list.this, Activity_basicinfo1.class);
                        intent.putExtra("id", id);
                        startActivity(intent);

                    }

                }

                if(content=="研究生考试试卷规范性评价表"){
                    if(isTimeEnable()) {
                        Intent intent = new Intent(Activity_list.this, Activity_basicinfo2.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                }

                if(content=="研究生培养环节质量评价表-开题报告"){
                    if(isTimeEnable()) {
                        Intent intent = new Intent(Activity_list.this, Activity_t3select.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                }

                if(content=="研究生培养环节质量评价表-中期考核"){
                    if(isTimeEnable()) {
                        Intent intent = new Intent(Activity_list.this, Activity_t4select.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                }

                if(content=="研究生学位论文答辩情况评价表"){
                    if(isTimeEnable()) {
                        Intent intent = new Intent(Activity_list.this, Activity_basicinfo5.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                }

            }
        });




        //抽屉面板功能区
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        global_variance myssession = ((global_variance)getApplicationContext());
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
            startActivity(new Intent(this,Activity_drafts.class));
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

}
