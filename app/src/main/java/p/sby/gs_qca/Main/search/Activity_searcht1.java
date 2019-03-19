package p.sby.gs_qca.Main.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

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
        search = (Button) findViewById(R.id.btn_t1search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //      initData(2);

            }
        });


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
        datas = new ArrayList<>();
        datas.add("暂无查询结果");

    }


}

