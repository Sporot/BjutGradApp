package p.sby.gs_qca.Main.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import p.sby.gs_qca.Main.Adapters.SearchListAdapter;
import p.sby.gs_qca.Main.Adapters.TableListAdapter;
import p.sby.gs_qca.Main.search.Activity_searcht1;
import p.sby.gs_qca.Main.search.Activity_searcht4;
import p.sby.gs_qca.R;
import p.sby.gs_qca.widget.DividerListItemDecoration;

public class Activity_draftlist extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<String> datas;
    private SearchListAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draftlist);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_draftlist); //主页上方功能条
        toolbar.setTitle("查询");

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


        recyclerView=(RecyclerView)findViewById(R.id.searchlist);
        //准备数据集合，展示列表
        datas=new ArrayList<>();
        datas.add("研究生课堂教学质量评价表");
        datas.add("研究生考试试卷规范性评价表");
        datas.add("研究生培养环节质量评价表-开题报告");
        datas.add("研究生培养环节质量评价表-中期考核");
        datas.add("研究生学位论文答辩情况评价表");

        //设置recyclerview的适配器
        adapter=new SearchListAdapter(Activity_draftlist.this,datas);
        recyclerView.setAdapter(adapter);

        //设置Layoutmanager
        recyclerView.setLayoutManager(new LinearLayoutManager(Activity_draftlist.this,LinearLayoutManager.VERTICAL,false));

        //添加Recyclerview的分割线
        recyclerView.addItemDecoration(new DividerListItemDecoration(Activity_draftlist.this,DividerListItemDecoration.VERTICAL_LIST));

        adapter.setOnItemClickListener(new TableListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String content) {
                String id = content;
                if(content=="研究生课堂教学质量评价表"){
                 startActivity(new Intent(Activity_draftlist.this,Activity_draftst1.class));
                }

                if(content=="研究生培养环节质量评价表-中期考核"){
                    startActivity(new Intent(Activity_draftlist.this,Activity_searcht4.class));
                }
            }
        });
    }
}
