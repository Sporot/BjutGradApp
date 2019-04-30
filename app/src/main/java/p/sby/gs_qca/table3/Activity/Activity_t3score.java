package p.sby.gs_qca.table3.Activity;

import android.Manifest;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

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

import p.sby.gs_qca.table3.Fragment.t3CommentsFragment;
import p.sby.gs_qca.table3.Fragment.t3ScoreFragment;
import p.sby.gs_qca.table4.Activity.Activity_t4preview;
import p.sby.gs_qca.table4.Activity.Activity_t4score;
import p.sby.gs_qca.util.FileDisplayActivity;
import p.sby.gs_qca.util.RequestUtil;
import p.sby.gs_qca.widget.LoadingDialog;
import pub.devrel.easypermissions.EasyPermissions;

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

    private LoadingDialog mLoadingDialog; //显示正在加载的对话框
    public String department;
    public String major;
    public String studentname;
    public String teachername;
    public String type;
    public String room;
    public String reportid;
    private String id;
    public String experts;
    public String score1;
    public String comment1="";
    public String comment2="";
    public String time;
    public String sendfrom;

    public String filePath;
    private int flagsave=0;

    private String sessionid;
    private String temp1;
    private String temp2;

    private String urledit="http://117.121.38.95:9817/mobile/form/buff/editktbg.ht";
    private String urlsave="http://117.121.38.95:9817/mobile/form/buff/addktbg.ht";

    private String result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t3_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.t3_main_toolbar);
        toolbar.setTitle("培养环节质量评价-开题");
        setSupportActionBar(toolbar);

        init();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Intent intent=getIntent();
        sendfrom=intent.getStringExtra("sendfrom");
        if(sendfrom.equals("basic")) {
            department = intent.getStringExtra("department");
            major = intent.getStringExtra("major");
            studentname = intent.getStringExtra("studentname");
            teachername = intent.getStringExtra("teachername");
            type = intent.getStringExtra("type");
            room = intent.getStringExtra("room");
            reportid = intent.getStringExtra("reportid");
            id=intent.getStringExtra("id");
            time = intent.getStringExtra("time");
            experts = intent.getStringExtra("experts");
        }

        else if(sendfrom.equals("drafts")) {
            department = intent.getStringExtra("department");
            major = intent.getStringExtra("major");
            studentname = intent.getStringExtra("studentname");
            teachername = intent.getStringExtra("teachername");
            type = intent.getStringExtra("type");
            room = intent.getStringExtra("room");
            reportid = intent.getStringExtra("reportid");
            id=intent.getStringExtra("id");
            time = intent.getStringExtra("time");
            experts = intent.getStringExtra("experts");
            score1=intent.getStringExtra("score1");
            comment1=intent.getStringExtra("comment1");
            comment2=intent.getStringExtra("comment2");
            Log.i("t3modify", "id :"+reportid);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_t4list, menu);
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
            Intent intent =new Intent(Activity_t3score.this,Activity_t3preview.class);
            intent.putExtra("department",department);
            intent.putExtra("major",major);
            intent.putExtra("studentname",studentname);
            intent.putExtra("type",type);
            intent.putExtra("teachername",teachername);
            intent.putExtra("room",room);
            intent.putExtra("time",time);
            intent.putExtra("experts",experts);

            intent.putExtra("score1",score1);
            intent.putExtra("comment1",comment1);
            intent.putExtra("comment2",comment2);
            startActivity(intent);
        }

        else if (id == R.id.tb4_openfile) {
            String[] perms = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
            filePath = getFilePath(1);
            Log.i("FileDisplayActivity", "点击"+"点击下载");
            Log.i("FileDisplayActivity", "filepath"+filePath);
            if (!EasyPermissions.hasPermissions(Activity_t3score.this, perms)) {
                EasyPermissions.requestPermissions(Activity_t3score.this, "需要访问手机存储权限！", 10086, perms);
            } else {
                FileDisplayActivity.show(Activity_t3score.this, filePath);
            }
        }

        else if(id==R.id.tb2_save){
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

                global_variance mysession=(global_variance)(Activity_t3score.this.getApplication());
                sessionid=mysession.getSessionid();


                HashMap<String,String> paramsMap=new HashMap<>();
                paramsMap.put("id",reportid);
                paramsMap.put("standardid","100");
                paramsMap.put("score1",score1);
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
                        Activity_t3score.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toasty.success(Activity_t3score.this,"成功保存到草稿箱！",Toasty.LENGTH_SHORT).show();
                                startActivity(new Intent(Activity_t3score.this,Activity_list.class));
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
                global_variance mysession=(global_variance)(Activity_t3score.this.getApplication());
                sessionid=mysession.getSessionid();

                HashMap<String,String> paramsMap=new HashMap<>();
                paramsMap.put("id",reportid);
                paramsMap.put("standardid","100");
                paramsMap.put("score1",score1);
                paramsMap.put("comment1",comment1);
                paramsMap.put("comment2",comment2);
                FormBody.Builder builder = new FormBody.Builder();
                Log.i("t4modify", "modify: "+paramsMap);
                for (String key : paramsMap.keySet()) {
                    //追加表单信息
                    builder.add(key, paramsMap.get(key));
                }
                temp1=RequestUtil.get().MapSend(urledit,sessionid,paramsMap);
                Log.i("t4modify", "temp1:"+temp1);
                try {
                    JSONObject userJSON =new JSONObject(temp1);
                    result=userJSON.getString("result");
                    System.out.println(result);
                    if(result.equals("100")){
                        Activity_t3score.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toasty.success(Activity_t3score.this,"成功修改草稿！",Toasty.LENGTH_SHORT).show();
                                startActivity(new Intent(Activity_t3score.this,Activity_list.class));
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


    private String getFilePath(int position) {
        String path = null;
        switch (position) {
            case 0:
                path = "http://117.121.38.95:9817/platform/file/filemessage/download.ht";
                break;
            case 1:
                path =  "/storage/emulated/0/Download/hello.doc";
                break;
            case 2:
                path = "/storage/emulated/0/test.txt";
                break;
            case 3:
                path = "/storage/emulated/0/test.xlsx";
                break;
            case 4:
                path = "/storage/emulated/0/test.pptx";
                break;

            case 5:
                path = "/storage/emulated/0/test.pdf";
                break;
        }
        return path;
    }



    /**加载进度框**/
    public void showLoading () {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(Activity_t3score.this, getString(R.string.loading), false);
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

