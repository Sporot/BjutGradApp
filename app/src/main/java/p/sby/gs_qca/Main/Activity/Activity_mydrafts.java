package p.sby.gs_qca.Main.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import p.sby.gs_qca.Main.Adapters.MyDraftListAdapter;
import p.sby.gs_qca.Main.Adapters.TableListAdapter;
import p.sby.gs_qca.R;
import p.sby.gs_qca.table1.Activity.Activity_basicinfo1;
import p.sby.gs_qca.table4.Activity.Activity_t4report;
import p.sby.gs_qca.widget.DividerListItemDecoration;

public class Activity_mydrafts extends AppCompatActivity {

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
                String id = content;
                //根据id值进行不同页面的跳转

                /*****跳转到第一个报告页面*****/
                if(content==datas.get(0)){
                    Intent intent = new Intent(Activity_mydrafts.this, Activity_basicinfo1.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }

            }
        });



    }

    /**获取报告题目列表******/
    private void initData() {
        datas=new ArrayList<>();
        for (int i=0;i<5;i++)
        {
            datas.add("My draft"+i);
        }
    }
}

