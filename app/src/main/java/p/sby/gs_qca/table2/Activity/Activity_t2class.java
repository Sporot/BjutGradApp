package p.sby.gs_qca.table2.Activity;

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
import android.widget.Toast;

import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.google.gson.*;

import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;
import okhttp3.FormBody;
import p.sby.gs_qca.Main.Activity.Activity_list;
import p.sby.gs_qca.Main.Activity.global_variance;
import p.sby.gs_qca.R;
import p.sby.gs_qca.table2.Fragment.t2CommentsFragment;
import p.sby.gs_qca.table2.Fragment.t2ScoreFragment;
import p.sby.gs_qca.util.RequestUtil;
import p.sby.gs_qca.widget.LoadingDialog;

public class Activity_t2class extends AppCompatActivity {
    private FragmentTabHost mTabHost;
    private LoadingDialog mLoadingDialog;
    private ViewPager mViewPager;
    private List<Fragment> mFragmentList;
    private Class mClass[] = {t2ScoreFragment.class,t2CommentsFragment.class};
    private Fragment mFragment[] = {new t2ScoreFragment(),new t2CommentsFragment()};
    private String mTitles[] = {"评分项目","专家评语"};
    private int mImages[] = {
            R.drawable.tab_score,
            R.drawable.tab_comments
    };
    private String urledit="http://116.213.144.72:9817/mobile/form/buff/editsjgf.ht";
    private String urladd="http://116.213.144.72:9817/mobile/form/buff/addsjgf.ht";
    private int flagsave=0;
    private String temp;
    private String temp1;
    private String sendfrom="";

    public String option="";
    public String institute="";
    public String coursename="";
    public String teacher="";
    public String classroom="";
    public String papernum="";
    public String courseid="";

    public String t2_score1="";
    public String t2_score2="";
    public String t2_score3="";
    public String t2_score4="";
    public String t2_score5="";
    public String t2_score6="";
    public String t2_score7="";
    public String t2_score8="";
    public String t2_total="";
    public String t2_comment="";
    public String formid="";

    String sessionid;
    private String result;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t2_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.t2_main_toolbar);
        toolbar.setTitle("研究生考试试卷规范性评价表");
        setSupportActionBar(toolbar);

        init();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Intent intent=getIntent();
        sendfrom=intent.getStringExtra("sendfrom");
        if(sendfrom.equals("basic")) {
            option = "basic";
            institute = intent.getStringExtra("institute");
            coursename = intent.getStringExtra("coursename");
            teacher = intent.getStringExtra("teacher");
            classroom = intent.getStringExtra("classroom");
//        time=intent.getStringExtra("time");
            courseid = intent.getStringExtra("courseid");
            papernum = intent.getStringExtra("papernum");
//            courseid = intent.getStringExtra("courseid");
            System.out.println("在课堂信息页打印id:" + courseid);
            System.out.println("在课堂信息页打印papernum:" + papernum);
        }

         else if(sendfrom.equals("drafts")) {

             System.out.println("收到判别为从草稿页转入");
             option = "drafts";
             institute = intent.getStringExtra("institute");
             System.out.println("***************收到草稿页传来的数据***************");
             System.out.println("institute:   " + institute);
             coursename = intent.getStringExtra("coursename");
             System.out.println("coursename:  " + coursename);
             teacher = intent.getStringExtra("teacher");
             papernum = intent.getStringExtra("papernumber");
             classroom = intent.getStringExtra("classid");
             System.out.println("classroom:  " + classroom);
             courseid = intent.getStringExtra("courseid");


             formid = intent.getStringExtra("formid");

             System.out.println("在课堂信息页打印papernum:" + papernum);
             t2_score1 = intent.getStringExtra("score1");
             if (t2_score1.equals("-1")) {
                 t2_score1 = "";

             }
             t2_score2 = intent.getStringExtra("score2");
             if (t2_score2.equals("-1")) {
                 t2_score2 = "";
             }
             t2_score3 = intent.getStringExtra("score3");
             if (t2_score3.equals("-1")) {
                 t2_score3 = "";
             }
             t2_score4 = intent.getStringExtra("score4");
             if (t2_score4.equals("-1")) {
                 t2_score4 = "";
             }
             t2_score5 = intent.getStringExtra("score5");
             if (t2_score5.equals("-1")) {
                 t2_score5 = "";
             }
             t2_score6 = intent.getStringExtra("score6");
             if (t2_score6.equals("-1")) {
                 t2_score6 = "";
             }
             t2_score7 = intent.getStringExtra("score7");
             if (t2_score7.equals("-1")) {
                 t2_score7 = "";
             }
             t2_score8 = intent.getStringExtra("score8");
             if (t2_score8.equals("-1")) {
                 t2_score8 = "";
             }
            t2_comment=intent.getStringExtra("comment1");
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
            System.out.println(t2_score1);
            System.out.println(t2_score2);
            System.out.println(t2_score3);
            System.out.println(t2_comment);

            Intent intent=new Intent(Activity_t2class.this,Activity_t2preview.class);
            intent.putExtra("option",option);
            intent.putExtra("institute",institute);
            intent.putExtra("coursename",coursename);
            intent.putExtra("teacher",teacher);
            intent.putExtra("classroom",classroom);
            intent.putExtra("papernum",papernum);
            intent.putExtra("courseid",courseid);

            intent.putExtra("score1",t2_score1);
            intent.putExtra("score2",t2_score2);
            intent.putExtra("score3",t2_score3);
            intent.putExtra("score4",t2_score4);
            intent.putExtra("score5",t2_score5);
            intent.putExtra("score6",t2_score6);
            intent.putExtra("score7",t2_score7);
            intent.putExtra("score8",t2_score8);
            intent.putExtra("total",t2_total);
            intent.putExtra("comment1",t2_comment);

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
//                modifydraft(flagsave);
            }


        }


        else if (id == R.id.tb2_quit) {
            startActivity(new Intent(this,Activity_list.class));
        }

        return super.onOptionsItemSelected(item);
    }




    /***********语音听写模块函数*************/

    public void initSpeech() {
        //1.创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(Activity_t2class.this, null);
        //2.设置accent、language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        //3.设置回调接口
        mDialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                if (!isLast) {
                    //解析语音
                    //返回的result为识别后的汉字,直接赋值到TextView上即可
                    String result = parseVoice(recognizerResult.getResultString());

                }
            }

            @Override
            public void onError(SpeechError speechError) {

            }
        });
        //4.显示dialog，接收语音输入
        mDialog.show();

    }

    /**
     * 解析语音json
     */
    public String parseVoice(String resultString) {
        Gson gson = new Gson();
        Voice voiceBean = gson.fromJson(resultString, Voice.class);

        StringBuffer sb = new StringBuffer();
        ArrayList<Voice.WSBean> ws = voiceBean.ws;
        for (Voice.WSBean wsBean : ws) {
            String word = wsBean.cw.get(0).w;
            sb.append(word);
        }
        return sb.toString();
    }
    /**
     * 语音对象封装
     */
    public class Voice {

        public ArrayList<WSBean> ws;

        public class WSBean {
            public ArrayList<CWBean> cw;
        }

        public class CWBean {
            public String w;
        }
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

                global_variance mysession=(global_variance)(Activity_t2class.this.getApplication());
                sessionid=mysession.getSessionid();

                //System.out.println("在提交的时候打印courseid:"+courseid);
                //添加请求信息
                HashMap<String,String> paramsMap=new HashMap<>();

//                if(flag2==0) {


                //    }
                paramsMap.put("id",formid);

                paramsMap.put("courseid",courseid);
                paramsMap.put("course",coursename);
                paramsMap.put("department",institute);
                paramsMap.put("standardid","100");
                paramsMap.put("room",classroom);
                paramsMap.put("papernum",papernum);

                paramsMap.put("teacher",teacher);
                paramsMap.put("comment",t2_comment);
                paramsMap.put("score1",t2_score1);
                paramsMap.put("score2",t2_score2);
                paramsMap.put("score3",t2_score3);
                paramsMap.put("score4",t2_score4);
                paramsMap.put("score5",t2_score5);
                paramsMap.put("score6",t2_score6);
                paramsMap.put("score7",t2_score7);
                paramsMap.put("score8",t2_score8);
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
                        Activity_t2class.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toasty.success(Activity_t2class.this,"成功修改草稿！",Toasty.LENGTH_SHORT).show();
                                startActivity(new Intent(Activity_t2class.this,Activity_list.class));
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

                global_variance mysession=(global_variance)(Activity_t2class.this.getApplication());
                sessionid=mysession.getSessionid();

                //System.out.println("在提交的时候打印courseid:"+courseid);
                //添加请求信息
                HashMap<String,String> paramsMap=new HashMap<>();

//                if(flag1==0) {


                // }
                paramsMap.put("courseid",courseid);
                paramsMap.put("course",coursename);
                paramsMap.put("department",institute);
                paramsMap.put("standardid","100");
                paramsMap.put("room",classroom);
                paramsMap.put("papernumber",papernum);

                paramsMap.put("teacher",teacher);
                paramsMap.put("comment1",t2_comment);
                paramsMap.put("score1",t2_score1);
                paramsMap.put("score2",t2_score2);
                paramsMap.put("score3",t2_score3);
                paramsMap.put("score4",t2_score4);
                paramsMap.put("score5",t2_score5);
                paramsMap.put("score6",t2_score6);
                paramsMap.put("score7",t2_score7);
                paramsMap.put("score8",t2_score8);
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
                        Activity_t2class.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toasty.success(Activity_t2class.this,"成功保存到草稿箱！",Toasty.LENGTH_SHORT).show();
                                startActivity(new Intent(Activity_t2class.this,Activity_list.class));
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



    public void showLoading () {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(Activity_t2class.this, getString(R.string.loading), false);
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
