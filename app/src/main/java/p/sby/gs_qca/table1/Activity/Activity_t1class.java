package p.sby.gs_qca.table1.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import p.sby.gs_qca.Main.Activity.Activity_list;
import p.sby.gs_qca.R;
import p.sby.gs_qca.table1.Fragment.t1CommentsFragment;
import p.sby.gs_qca.table1.Fragment.t1DetailFragment;
import p.sby.gs_qca.table1.Fragment.t1ScoreFragment;


public class Activity_t1class extends AppCompatActivity {
    private FragmentTabHost mTabHost;
    private ViewPager mViewPager;
    private android.app.FragmentManager manager;
     private android.app.FragmentTransaction transaction;
    private List<Fragment> mFragmentList;
    private Class mClass[] = {t1DetailFragment.class,t1ScoreFragment.class,t1CommentsFragment.class};
    private Fragment mFragment[] = {new t1DetailFragment(),new t1ScoreFragment(),new t1CommentsFragment()};

    private String mTitles[] = {"课堂信息","评分信息","专家评语"};
    private int mImages[] = {
            R.drawable.tab_score,
            R.drawable.tab_score,
            R.drawable.tab_comments
    };


    /******需要提交的表单数据*******/
    public String institute="";
    public String coursename="";
    public String comment="";
    public String classnum="";   //听课节次
    public String latenum="";    //迟到人数
    public String teachtheme="";
    public String otherinfo="";
    public String actualnum; //实到人数
    public String shouldnum; //应到人数

    public String t1_score1="";
    public String t1_score2="";
    public String t1_score3="";
    public String t1_score4="";
    public String t1_score5="";
    public String t1_score6="";
    public String t1_score7="";
    public String t1_score8="";
    public String t1_score9="";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t1_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.t1_main_toolbar);
        toolbar.setTitle("研究生课堂教学质量评价表");
        setSupportActionBar(toolbar);

        init();

        //隐藏软键盘，不让其弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Intent intent=getIntent();
        institute= intent.getStringExtra("institute");
        coursename=intent.getStringExtra("coursename");
        otherinfo=intent.getStringExtra("otherinfo");



    }



    private void init() {

        initView();

        initEvent();
    }

    private void initView() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mFragmentList = new ArrayList<Fragment>();

        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        mTabHost.getTabWidget().setDividerDrawable(null);

        for (int i = 0;i < mFragment.length;i++){
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTitles[i]).setIndicator(getTabView(i));
            mTabHost.addTab(tabSpec,mClass[i],null);
            mFragmentList.add(mFragment[i]);
            mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.WHITE);
        }

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        });
    }

    private View getTabView(int index) {
        View view = LayoutInflater.from(this).inflate(R.layout.t2_tabitem, null);

        ImageView image = (ImageView) view.findViewById(R.id.t2c_image);
        TextView title = (TextView) view.findViewById(R.id.t2s_title);

        image.setImageResource(mImages[index]);
        title.setText(mTitles[index]);

        return view;
    }

    private void initEvent() {

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                mViewPager.setCurrentItem(mTabHost.getCurrentTab());
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabHost.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.tb2_preview) {
          //  Toast.makeText(this, "你点击了 预览按钮！", Toast.LENGTH_SHORT).show();
         //   startActivity(new Intent(this,Activity_preview.class));

            System.out.println(t1_score1);

            Intent intent=new Intent(Activity_t1class.this,Activity_t1preview.class);
            intent.putExtra("institute",institute);
            intent.putExtra("coursename",coursename);
            intent.putExtra("comment",comment);
            intent.putExtra("latenum",latenum);
            intent.putExtra("teachtheme",teachtheme);
            intent.putExtra("otherinfo",otherinfo);
            intent.putExtra("classnum",classnum);
            intent.putExtra("actualnum",actualnum);
            intent.putExtra("shouldnum",actualnum);

            intent.putExtra("score1",t1_score1);
            intent.putExtra("score2",t1_score2);
            intent.putExtra("score3",t1_score3);
            intent.putExtra("score4",t1_score4);
            intent.putExtra("score5",t1_score5);
            intent.putExtra("score6",t1_score6);
            intent.putExtra("score7",t1_score7);
            intent.putExtra("score8",t1_score8);
            intent.putExtra("score9",t1_score9);

            startActivity(intent);

        }

        else if (id == R.id.tb2_save) {
            Toast.makeText(this, "你点击了 保存按钮！", Toast.LENGTH_SHORT).show();
        }

        else if (id == R.id.tb2_quit) {
            startActivity(new Intent(this,Activity_list.class));
        }

        return super.onOptionsItemSelected(item);
    }




}
