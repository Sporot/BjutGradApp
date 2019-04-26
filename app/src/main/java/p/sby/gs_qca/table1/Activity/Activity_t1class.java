package p.sby.gs_qca.table1.Activity;

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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.FormBody;
import p.sby.gs_qca.Main.Activity.Activity_list;
import p.sby.gs_qca.Main.Activity.global_variance;
import p.sby.gs_qca.Main.search.Activity_t1preview;
import p.sby.gs_qca.R;
import p.sby.gs_qca.table1.Fragment.t1CommentsFragment;
import p.sby.gs_qca.table1.Fragment.t1DetailFragment;
import p.sby.gs_qca.table1.Fragment.t1ScoreFragment;
import p.sby.gs_qca.util.RequestUtil;
import p.sby.gs_qca.widget.LoadingDialog;


public class Activity_t1class extends AppCompatActivity {
    private FragmentTabHost mTabHost;
    private ViewPager mViewPager;
    private LoadingDialog mLoadingDialog; //显示正在加载的对话框
    private android.app.FragmentManager manager;
     private android.app.FragmentTransaction transaction;
    private List<Fragment> mFragmentList;
    private Class mClass[] = {t1DetailFragment.class,t1ScoreFragment.class,t1CommentsFragment.class};
    private Fragment mFragment[] = {new t1DetailFragment(),new t1ScoreFragment(),new t1CommentsFragment()};

    private String urledit="http://117.121.38.95:9817/mobile/form/buff/editjxzl.ht";
    private String urladd="http://117.121.38.95:9817/mobile/form/buff/addjxzl.ht";
    private String temp1;
    private String temp;

    private String mTitles[] = {"课堂信息","评分信息","专家评语"};
    private int mImages[] = {
            R.drawable.tab_score,
            R.drawable.tab_score,
            R.drawable.tab_comments
    };


    private int flagsave=0;
    private String sendfrom="";
    public String option="";
    /******需要提交的表单数据*******/
    public String formid="";
    public String institute="";
    public String institute1="";
    public String coursename="";
    public String comment="";
    public String classnum="";   //听课节次
    public String latenum="";    //迟到人数
    public String teachtheme="";
    public String actualnum=""; //实到人数
    public String shouldnum=""; //应到人数
    public String teacher="";
    public String classroom="";
    public String time="";
    public String courseid="";
    public String classid="";

    public String t1_score1="";
    public String t1_score2="";
    public String t1_score3="";
    public String t1_score4="";
    public String t1_score5="";
    public String t1_score6="";
    public String t1_score7="";
    public String t1_score8="";
    public String t1_score9="";


    String sessionid;
    private String result;
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
        sendfrom=intent.getStringExtra("sendfrom");
        System.out.println("************打印从哪个页面跳转过来*************");
        System.out.println(sendfrom);
        if(sendfrom.equals("basic")){
            option="basic";
            institute= intent.getStringExtra("institute");
            coursename=intent.getStringExtra("coursename");
            teacher=intent.getStringExtra("teacher");
            classroom=intent.getStringExtra("classroom");
            time=intent.getStringExtra("time");
            courseid=intent.getStringExtra("courseid");
            shouldnum=intent.getStringExtra("shouldnum");
            classid=intent.getStringExtra("classid");
            System.out.println("在课堂信息页打印id:"+courseid);
        }


       else if(sendfrom.equals("drafts")){
            System.out.println("收到判别为从草稿页转入");
            option="drafts";
            institute= intent.getStringExtra("institute");
            System.out.println("***************收到草稿页传来的数据***************");
            System.out.println("institute:   "+institute);
            coursename=intent.getStringExtra("coursename");
            System.out.println("coursename:  "+coursename);
            teacher=intent.getStringExtra("teacher");
            classroom=intent.getStringExtra("classroom");
            time=intent.getStringExtra("time1");
            courseid=intent.getStringExtra("courseid");
            shouldnum=intent.getStringExtra("shouldnum");
            classid=intent.getStringExtra("classid");

            teachtheme=intent.getStringExtra("teachtheme");
            classnum=intent.getStringExtra("classnum");
            formid=intent.getStringExtra("formid");

            latenum=intent.getStringExtra("latenum");
            if (latenum.equals("0")){
                latenum="";
            }
            actualnum=intent.getStringExtra("actualnum");
            if(actualnum.equals("0")){
                actualnum="";
            }

            t1_score1=intent.getStringExtra("score1");
            if (t1_score1.equals("-1")){
                t1_score1="";

            }
            t1_score2=intent.getStringExtra("score2");
            if (t1_score2.equals("-1")){
                t1_score2="";
            }
            t1_score3=intent.getStringExtra("score3");
            if(t1_score3.equals("-1")){
                t1_score3="";
            }
            t1_score4=intent.getStringExtra("score4");
            if(t1_score4.equals("-1")){
                t1_score4="";
            }
            t1_score5=intent.getStringExtra("score5");
            if(t1_score5.equals("-1")){
                t1_score5="";
            }
            t1_score6=intent.getStringExtra("score6");
            if(t1_score6.equals("-1")){
                t1_score6="";
            }
            t1_score7=intent.getStringExtra("score7");
            if(t1_score7.equals("-1")){
                t1_score7="";
            }
            t1_score8=intent.getStringExtra("score8");
            if(t1_score8.equals("-1")){
                t1_score8="";
            }
            t1_score9=intent.getStringExtra("score9");
            if(t1_score9.equals("-1")){
                t1_score9="";
            }
            comment=intent.getStringExtra("comment");
        }

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

    /**
     * 添加菜单项
     * @param menu
     * @return
     */
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
            System.out.println(t1_score2);
            System.out.println(t1_score3);
            System.out.println(t1_score4);
            System.out.println(t1_score5);
            System.out.println(t1_score6);
            System.out.println(t1_score7);
            System.out.println(t1_score8);
            System.out.println(t1_score9);

            Intent intent=new Intent(Activity_t1class.this,Activity_t1preview.class);
            intent.putExtra("formid",formid);
            intent.putExtra("option",option);
            System.out.println("**********传递的option值************");
            System.out.println(option);
            intent.putExtra("institute",institute);
            intent.putExtra("coursename",coursename);
            intent.putExtra("comment",comment);
            intent.putExtra("latenum",latenum);
            intent.putExtra("teachtheme",teachtheme);
            intent.putExtra("classnum",classnum);
            intent.putExtra("actualnum",actualnum);
            intent.putExtra("shouldnum",shouldnum);
            intent.putExtra("teacher",teacher);
            intent.putExtra("classroom",classroom);
            intent.putExtra("time1",time);
            intent.putExtra("courseid",courseid);
            intent.putExtra("classid",classid);

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

        else if (id==R.id.tb2_save){
//            if(actualnum.equals("") || latenum.equals(""))
//            {
//                flagsave=1;
//            }
            if (option=="basic")
            {
                save2draft(flagsave);
            }

            else if (option=="drafts"){
                modifydraft(flagsave);
            }


        }


        else if (id == R.id.tb2_quit) {
            startActivity(new Intent(this,Activity_list.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void modifydraft(int flag2) {
        showLoading(); //显示加载框


        Thread modifyRunnable = new Thread() {
            public void run() {
                super.run();

                //睡眠3秒
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                global_variance mysession=(global_variance)(Activity_t1class.this.getApplication());
                sessionid=mysession.getSessionid();

                //System.out.println("在提交的时候打印courseid:"+courseid);
                //添加请求信息
                HashMap<String,String> paramsMap=new HashMap<>();

//                if(flag2==0) {
                    paramsMap.put("latenumber", latenum);
                    paramsMap.put("presentnumber", actualnum);

            //    }
                paramsMap.put("id",formid);

                paramsMap.put("courseid",courseid);
                paramsMap.put("classid",classid);
                paramsMap.put("course",coursename);
                paramsMap.put("department",institute);
                paramsMap.put("studentnumber",shouldnum);
                paramsMap.put("standardid","100");
                paramsMap.put("room",classroom);
                paramsMap.put("time1",time);

                paramsMap.put("listentime",classnum);
                paramsMap.put("teacher",teacher);
                paramsMap.put("topic",teachtheme);
                paramsMap.put("comment",comment);
                paramsMap.put("score1",t1_score1);
                paramsMap.put("score2",t1_score2);
                paramsMap.put("score3",t1_score3);
                paramsMap.put("score4",t1_score4);
                paramsMap.put("score5",t1_score5);
                paramsMap.put("score6",t1_score6);
                paramsMap.put("score7",t1_score7);
                paramsMap.put("score8",t1_score8);
                paramsMap.put("score9",t1_score9);
                System.out.println(paramsMap);

                FormBody.Builder builder = new FormBody.Builder();
                for (String key : paramsMap.keySet()) {
                    //追加表单信息
                    builder.add(key, paramsMap.get(key));
                }
                temp1=RequestUtil.get().MapSend(urledit,sessionid,paramsMap);


                    try {
                        JSONObject userJSON =new JSONObject(temp1);
                        result=userJSON.getString("result");
                        System.out.println(result);
                        if(result.equals("100")){
                            Activity_t1class.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toasty.success(Activity_t1class.this,"成功修改草稿！",Toasty.LENGTH_SHORT).show();
                                    startActivity(new Intent(Activity_t1class.this,Activity_list.class));
                                }
                            });



                        }
//


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                hideLoading();//隐藏加载框
            }
        };

        modifyRunnable.start();
    }



    private void save2draft(int flag1){

        showLoading(); //显示加载框


        Thread saveRunnable = new Thread() {
            public void run() {
                super.run();

                //睡眠3秒
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                global_variance mysession=(global_variance)(Activity_t1class.this.getApplication());
                sessionid=mysession.getSessionid();

                //System.out.println("在提交的时候打印courseid:"+courseid);
                //添加请求信息
                HashMap<String,String> paramsMap=new HashMap<>();

//                if(flag1==0) {
                    paramsMap.put("latenumber", latenum);
                    paramsMap.put("presentnumber", actualnum);

               // }
                paramsMap.put("courseid",courseid);
                paramsMap.put("classid",classid);
                paramsMap.put("course",coursename);
                paramsMap.put("department",institute);
                paramsMap.put("studentnumber",shouldnum);
                paramsMap.put("standardid","100");
                paramsMap.put("room",classroom);
                paramsMap.put("time1",time);

                paramsMap.put("listentime",classnum);
                paramsMap.put("teacher",teacher);
                paramsMap.put("topic",teachtheme);
                paramsMap.put("comment",comment);
                paramsMap.put("score1",t1_score1);
                paramsMap.put("score2",t1_score2);
                paramsMap.put("score3",t1_score3);
                paramsMap.put("score4",t1_score4);
                paramsMap.put("score5",t1_score5);
                paramsMap.put("score6",t1_score6);
                paramsMap.put("score7",t1_score7);
                paramsMap.put("score8",t1_score8);
                paramsMap.put("score9",t1_score9);
                System.out.println(paramsMap);

                FormBody.Builder builder = new FormBody.Builder();
                for (String key : paramsMap.keySet()) {
                    //追加表单信息
                    builder.add(key, paramsMap.get(key));
                }

                temp=RequestUtil.get().MapSend(urladd,sessionid,paramsMap);


                    try {
                        JSONObject userJSON =new JSONObject(temp);
                        result=userJSON.getString("result");
                        System.out.println(result);
                        if(result.equals("100")){
                            Activity_t1class.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toasty.success(Activity_t1class.this,"成功保存到草稿箱！",Toasty.LENGTH_SHORT).show();
                                    startActivity(new Intent(Activity_t1class.this,Activity_list.class));
                            }
                            });



                        }
//


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                hideLoading();//隐藏加载框
            }
        };

        saveRunnable.start();

    }

    /**加载进度框**/
    public void showLoading () {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(Activity_t1class.this, getString(R.string.loading), false);
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

}
