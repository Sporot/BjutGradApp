package p.sby.gs_qca.Main.search;

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
import p.sby.gs_qca.R;
import p.sby.gs_qca.widget.DividerListItemDecoration;

public class Activity_searchlist extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<String> datas;
    private SearchListAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchlist);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_searchlist); //主页上方功能条
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
        datas.add("课堂教学质量评价");
        datas.add("考试试卷规范性评价");
        datas.add("培养环节质量评价-开题报告");
        datas.add("培养环节质量评价-中期考核");
        datas.add("学位论文答辩情况评价");

        //设置recyclerview的适配器
        adapter=new SearchListAdapter(Activity_searchlist.this,datas);
        recyclerView.setAdapter(adapter);

        //设置Layoutmanager
        recyclerView.setLayoutManager(new LinearLayoutManager(Activity_searchlist.this,LinearLayoutManager.VERTICAL,false));

        //添加Recyclerview的分割线
        recyclerView.addItemDecoration(new DividerListItemDecoration(Activity_searchlist.this,DividerListItemDecoration.VERTICAL_LIST));

        adapter.setOnItemClickListener(new TableListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String content) {
                String id = content;
                if(content=="课堂教学质量评价"){
                 startActivity(new Intent(Activity_searchlist.this,Activity_searcht1.class));
                }

//                if(content=="课堂教学质量评价"){
//                    startActivity(new Intent(Activity_searchlist.this,Activity_searcht2.class));
//                }

                if(content=="培养环节质量评价-中期考核"){
                    startActivity(new Intent(Activity_searchlist.this,Activity_searcht4.class));
                }

                if(content=="培养环节质量评价-开题报告"){
                    startActivity(new Intent(Activity_searchlist.this,Activity_searcht3.class));
                }

                if(content=="学位论文答辩情况评价"){
                    startActivity(new Intent(Activity_searchlist.this,Activity_searcht5.class));
                }

                if(content=="考试试卷规范性评价"){
                    startActivity(new Intent(Activity_searchlist.this,Activity_searcht2.class));
                }

            }
        });
    }
}
