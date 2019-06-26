package p.sby.gs_qca.table5.Activity;

import android.content.Context;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.FormBody;
import p.sby.gs_qca.Main.Activity.Activity_list;
import p.sby.gs_qca.Main.Activity.global_variance;
import p.sby.gs_qca.R;
import p.sby.gs_qca.table1.Activity.Activity_t1submit;
import p.sby.gs_qca.table3.Activity.Activity_t3score;
import p.sby.gs_qca.table5.Fragment.t5CommentsFragment;
import p.sby.gs_qca.table5.Fragment.t5ScoreFragment;
import p.sby.gs_qca.util.RequestUtil;
import p.sby.gs_qca.widget.LoadingDialog;

public class Activity_t5score extends AppCompatActivity {
    String sessionid;
    private String temp1;
    private String temp2;
    private String result;
    private LoadingDialog mLoadingDialog; //显示正在加载的对话框
    private FragmentTabHost mTabHost;
    private ViewPager mViewPager;
    private List<Fragment> mFragmentList;
    private Class mClass[] = {t5ScoreFragment.class};
    private Fragment mFragment[] = {new t5ScoreFragment()};
    private String mTitles[] = {"评分项目"};
    private int mImages[] = {
            R.drawable.tab_score,
            R.drawable.tab_comments
    };
    private String urledit="http://117.121.38.95:9817/mobile/form/buff/editlwdb.ht";
    private String urlsave="http://117.121.38.95:9817/mobile/form/buff/addlwdb.ht";
    private String temp;
    public String comment1="";
    public String comment2="";
    public String institute;
    public String major;
    public String teacher;
    public String student;
    public String time;
    public String year;
    public String month;
    public String day;
    public String expert;
    public String classroom;
    public String type;
    public String t5score;
    public String reportid;
    public String sendfrom;
    public String id;
    private int flagsave=0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t5_main);

        //科大讯飞的语音识别模块
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5c860000");
//
        Toolbar toolbar = (Toolbar) findViewById(R.id.t5_main_toolbar);
        toolbar.setTitle("答辩评价");
        setSupportActionBar(toolbar);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Intent intent=getIntent();
        sendfrom=intent.getStringExtra("sendfrom");
        Log.i("t5drafts", "sendfrom: "+sendfrom);
        if(sendfrom.equals("basic")){
            institute= intent.getStringExtra("institute");
            major=intent.getStringExtra("major");
            teacher=intent.getStringExtra("teacher");
            student=intent.getStringExtra("student");
            type=intent.getStringExtra("type");
            classroom=intent.getStringExtra("classroom");
            time=intent.getStringExtra("time");
            year=intent.getStringExtra("year");
            month=intent.getStringExtra("month");
            day=intent.getStringExtra("day");
            expert=intent.getStringExtra("experts");
            reportid=intent.getStringExtra("reportid");
            System.out.println("在课堂信息页打印reportid:"+reportid);
        }

        else if(sendfrom.equals("drafts")){
            institute= intent.getStringExtra("department");
            Log.i("t5drafts", "score department: "+institute);
            major=intent.getStringExtra("major");
            teacher=intent.getStringExtra("teachername");
            student=intent.getStringExtra("studentname");
            type=intent.getStringExtra("type");
            classroom=intent.getStringExtra("room");
            time=intent.getStringExtra("time");
            year=intent.getStringExtra("year");
            month=intent.getStringExtra("month");
            day=intent.getStringExtra("day");
            expert=intent.getStringExtra("experts");
            reportid=intent.getStringExtra("reportid");

            t5score=intent.getStringExtra("score1");
            id=intent.getStringExtra("id");
            comment1=intent.getStringExtra("comment1");
            comment2=intent.getStringExtra("comment2");
        }


        init();

    }

    private void init() {
        initView();
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
            Intent intent=new Intent(Activity_t5score.this,Activity_t5preview.class);

            intent.putExtra("sendfrom","basic");
            intent.putExtra("institute",institute);
            intent.putExtra("major",major);
            intent.putExtra("teacher",teacher);
            intent.putExtra("student",student);
            intent.putExtra("type",type);
            intent.putExtra("time",time);
            intent.putExtra("year",year);
            intent.putExtra("month",month);
            intent.putExtra("day",day);
            intent.putExtra("expert",expert);
            intent.putExtra("classroom",classroom);
            intent.putExtra("score",t5score);
            intent.putExtra("comment1",comment1);
            intent.putExtra("comment2",comment2);



            startActivity(intent);
        }

        else if (id == R.id.tb2_save) {
            if (sendfrom.equals("basic"))
            {
                Log.i("t3save", "onOptionsItemSelected: "+sendfrom);
                save2draft(flagsave);
            }

            else if (sendfrom.equals("drafts")){
                Log.i("t3modify", "enter into modifydraft ");
                modifydraft(flagsave);
            }

        }

        else if (id == R.id.tb2_quit) {
            startActivity(new Intent(this,Activity_list.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void save2draft(int flagsave) {
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

                global_variance mysession=(global_variance)(Activity_t5score.this.getApplication());
                sessionid=mysession.getSessionid();


                HashMap<String,String> paramsMap=new HashMap<>();
                paramsMap.put("reportid",reportid);
                paramsMap.put("standardid","100");
                paramsMap.put("score1",t5score);
                paramsMap.put("comment1",comment1);
                paramsMap.put("comment2",comment2);
                FormBody.Builder builder = new FormBody.Builder();
                Log.i("t3save", "run: "+paramsMap);
                for (String key : paramsMap.keySet()) {
                    //追加表单信息
                    builder.add(key, paramsMap.get(key));
                }
                temp2=RequestUtil.get().MapSend(urlsave,sessionid,paramsMap);


                try {
                    JSONObject userJSON =new JSONObject(temp2);
                    result=userJSON.getString("result");
                    Log.i("t3save", "run: "+result);
                    if(result.equals("100")){
                        Activity_t5score.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toasty.success(Activity_t5score.this,"成功保存到草稿箱！",Toasty.LENGTH_SHORT).show();
                                startActivity(new Intent(Activity_t5score.this,Activity_list.class));
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideLoading();//隐藏加载框
            }
        };
        saveRunnable.start();
    }

    private void modifydraft(int flagsave) {
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
                global_variance mysession=(global_variance)(Activity_t5score.this.getApplication());
                sessionid=mysession.getSessionid();

                HashMap<String,String> paramsMap=new HashMap<>();
                paramsMap.put("reportid",reportid);
                paramsMap.put("id",id);
                paramsMap.put("standardid","100");
                paramsMap.put("score1",t5score);
                paramsMap.put("comment1",comment1);
                paramsMap.put("comment2",comment2);
                FormBody.Builder builder = new FormBody.Builder();
                Log.i("t3modify", "modify: "+paramsMap);
                for (String key : paramsMap.keySet()) {
                    //追加表单信息
                    builder.add(key, paramsMap.get(key));
                }
                temp1=RequestUtil.get().MapSend(urledit,sessionid,paramsMap);
                Log.i("t3modify", "temp1:"+temp1);
                try {
                    JSONObject userJSON =new JSONObject(temp1);
                    result=userJSON.getString("result");
                    System.out.println(result);
                    if(result.equals("100")){
                        Activity_t5score.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toasty.success(Activity_t5score.this,"成功修改草稿！",Toasty.LENGTH_SHORT).show();
                                startActivity(new Intent(Activity_t5score.this,Activity_list.class));
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                hideLoading();//隐藏加载框
            }
        };

        modifyRunnable.start();
    }


    public void showLoading () {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(Activity_t5score.this, getString(R.string.loading), false);
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


