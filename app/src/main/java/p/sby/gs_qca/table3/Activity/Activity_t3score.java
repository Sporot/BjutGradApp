package p.sby.gs_qca.table3.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import p.sby.gs_qca.Main.Activity.Activity_list;
import p.sby.gs_qca.R;

import p.sby.gs_qca.table3.Fragment.t3CommentsFragment;
import p.sby.gs_qca.table3.Fragment.t3ScoreFragment;

public class Activity_t3score extends AppCompatActivity {
    private FragmentTabHost mTabHost;
    private ViewPager mViewPager;
    private List<Fragment> mFragmentList;
    private Class mClass[] = {t3ScoreFragment.class,t3CommentsFragment.class};
    private Fragment mFragment[] = {new t3ScoreFragment(),new t3CommentsFragment()};
    private String mTitles[] = {"评分项目","专家评语"};
    private int mImages[] = {
            R.drawable.tab_score,
            R.drawable.tab_comments
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t3_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.t3_main_toolbar);
        toolbar.setTitle("培养环节质量评价-开题");
        setSupportActionBar(toolbar);

        init();

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
            Toast.makeText(this, "你点击了 预览按钮！", Toast.LENGTH_SHORT).show();
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

