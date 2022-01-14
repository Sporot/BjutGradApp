package p.sby.gs_qca.table2.Activity;

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
import p.sby.gs_qca.Main.search.Activity_searcht4;
import p.sby.gs_qca.R;
import p.sby.gs_qca.table1.Activity.Activity_t1basicinfo_dep;
import p.sby.gs_qca.table1.Activity.Activity_t1searchselect;
import p.sby.gs_qca.widget.DividerListItemDecoration;

public class Activity_t2searchselect extends AppCompatActivity{
    private RecyclerView select_list_view;
    private ArrayList<String> datas;
    private SearchListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t1_searchselect);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_t1searchselect);
        toolbar.setTitle("筛选条件");

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        select_list_view = (RecyclerView)findViewById(R.id.searchlist);

        datas = new ArrayList<>();
        datas.add("按学院查询");
        datas.add("按教师查询");

        //设置适配器
        adapter = new SearchListAdapter(Activity_t2searchselect.this, datas);
        select_list_view.setAdapter(adapter);

        //设置layoutmanager
        select_list_view.setLayoutManager(new LinearLayoutManager(Activity_t2searchselect.this, LinearLayoutManager.VERTICAL, false));

        //添加分割线
        select_list_view.addItemDecoration(new DividerListItemDecoration(Activity_t2searchselect.this, DividerListItemDecoration.VERTICAL_LIST));

        adapter.setOnItemClickListener(new TableListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String content) {
                String id = content;
                if (content == "按学院查询"){
                    startActivity(new Intent(Activity_t2searchselect.this, Activity_t2basicinfo_dep.class));
                }

                if (content == "按教师查询"){
                    startActivity(new Intent(Activity_t2searchselect.this, Activity_t2basicinfo_teacher.class));
                }
            }
        });


    }
}
