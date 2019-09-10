package p.sby.gs_qca.table1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import okhttp3.FormBody;
import p.sby.gs_qca.Main.Activity.Activity_list;
import p.sby.gs_qca.Main.Activity.global_variance;
import p.sby.gs_qca.R;
import p.sby.gs_qca.util.RequestUtil;
import p.sby.gs_qca.widget.LoadingDialog;

public class Activity_t1submit extends AppCompatActivity {
    String sessionid;
    private String result;
    private LoadingDialog mLoadingDialog; //显示正在加载的对话框
    private String urlsave="http://117.121.38.95:9817/mobile/form/buff/addjxzl.ht";
    private String urladd="http://117.121.38.95:9817/mobile/form/jxzl/add.ht";
    private String urledit="http://117.121.38.95:9817/mobile/form/buff/editjxzl.ht";
    private TextView t1pre_intitute;
    private TextView t1pre_coursename;
    private TextView t1pre_comment;
    private TextView t1pre_actualnum;
    private TextView t1pre_teachtheme;
    private TextView t1pre_classnum;
    private TextView t1pre_teacher;
    private TextView t1pre_classroom;
    private TextView t1pre_time;
    private TextView t1pre_latenum;
    private TextView t1pre_shouldnum;
    private TextView t1pre_classid;
    private TextView t1pre_type;
    private TextView t1pre_object;


    private TextView t1pre_score1;
    private TextView t1pre_score2;
    private TextView t1pre_score3;
    private TextView t1pre_score4;
    private TextView t1pre_score5;
    private TextView t1pre_score6;
    private TextView t1pre_score7;
    private TextView t1pre_score8;
    private TextView t1pre_score9;
    private TextView t1pre_totalscore;

    private Button t1pre_submit;
    private Button t1pre_save;



/*****提交数据*****/
    private String formid="";
    private String option="";
    private String comment="";
    private String institute="";
    private String coursename="";
    private String latenum="";
    private String teachtheme="";
    private String classnum="";
    private String teacher="";
    private String classroom="";
    private String time="";
    private String actualnum="";
    private String shouldnum="";
    private String courseid="";
    private String classid="";
    private String classtype="";
    private String classobject="";


    private String t1_score1="";
    private String t1_score2="";
    private String t1_score3="";
    private String t1_score4="";
    private String t1_score5="";
    private String t1_score6="";
    private String t1_score7="";
    private String t1_score8="";
    private String t1_score9="";
    private String t1_totalscore="";


    private int flag=0;
    private int flagsave=0;

    private String temp2;
    private String temp1;
    private String temp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t1_preview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_t1preview); //主页上方功能条
        toolbar.setTitle("预览");

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


        initView();

        getValue();
        t1pre_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("******保存的option********");
                System.out.println(option);
                if (option.equals("basic"))
                {
                    save2draft(flagsave);
                }

                else if (option.equals("drafts")){
                    modifydraft(flagsave);
                }

            }
        });



        t1pre_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               if(  actualnum.equals("")|| latenum.equals("")|| teachtheme.equals("") || classnum.equals("")|| comment.equals("") || t1_score1.equals("") || t1_score2.equals("") || t1_score3.equals("") || t1_score4.equals("") || t1_score5.equals("") || t1_score6.equals("") || t1_score7.equals("") || t1_score8.equals("") ||t1_score9.equals("")){
                    flag=2;
                }

                if(flag==2){
                    Toasty.warning(Activity_t1submit.this,"您的表格未完整填写，请检查！",Toasty.LENGTH_LONG).show();
                }
                else {
                    submit();
                }

            }
        });
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

                global_variance mysession=(global_variance)(Activity_t1submit.this.getApplication());
                sessionid=mysession.getSessionid();

                //System.out.println("在提交的时候打印courseid:"+courseid);
                //添加请求信息
                HashMap<String,String> paramsMap=new HashMap<>();

//                if(flagsave==0) {
                    paramsMap.put("latenumber", latenum);
                    paramsMap.put("presentnumber", actualnum);

           //     }
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
                           Activity_t1submit.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toasty.success(Activity_t1submit.this,"成功修改草稿！",Toasty.LENGTH_SHORT).show();
                                    startActivity(new Intent(Activity_t1submit.this,Activity_list.class));
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

    public void initView() {
        t1pre_save=(Button)findViewById(R.id.t1pre_save);
        t1pre_submit=(Button)findViewById(R.id.t1pre_submit);


        t1pre_intitute=(TextView)findViewById(R.id.t1pre_institute);
        t1pre_coursename=(TextView) findViewById(R.id.t1pre_coursename);
        t1pre_comment=(TextView)findViewById(R.id.t1pre_comment);
        t1pre_actualnum=(TextView)findViewById(R.id.t1pre_actualnum);
        t1pre_teachtheme=(TextView)findViewById(R.id.t1pre_teachtheme);
        t1pre_classnum=(TextView)findViewById(R.id.t1pre_classnum);
        t1pre_classid=(TextView)findViewById(R.id.t1pre_classid);
        t1pre_type=(TextView)findViewById(R.id.t1pre_type);
        t1pre_object=(TextView) findViewById(R.id.t1pre_object);

        t1pre_teacher=(TextView)findViewById(R.id.t1pre_teacher);
        t1pre_classroom=(TextView)findViewById(R.id.t1pre_classroom);
        t1pre_latenum=(TextView)findViewById(R.id.t1pre_latenum);
        t1pre_time=(TextView)findViewById(R.id.t1pre_time);
        t1pre_shouldnum=(TextView)findViewById(R.id.t1pre_shouldnum);
        // t1pre_otherinfo=(TextView)findViewById(R.id.t1pre_otherinfo);

        t1pre_score1=(TextView)findViewById(R.id.t1pre_score1);
        t1pre_score2=(TextView)findViewById(R.id.t1pre_score2);
        t1pre_score3=(TextView)findViewById(R.id.t1pre_score3);
        t1pre_score4=(TextView)findViewById(R.id.t1pre_score4);
        t1pre_score5=(TextView)findViewById(R.id.t1pre_score5);
        t1pre_score6=(TextView)findViewById(R.id.t1pre_score6);
        t1pre_score7=(TextView)findViewById(R.id.t1pre_score7);
        t1pre_score8=(TextView)findViewById(R.id.t1pre_score8);
        t1pre_score9=(TextView)findViewById(R.id.t1pre_score9);
        t1pre_totalscore=(TextView)findViewById(R.id.t1_totalscore);

    }


    public void getValue(){
        Intent intent=getIntent();
        formid=intent.getStringExtra("formid");
        option=intent.getStringExtra("option");
        System.out.println("********option值*******");
        System.out.println(option);

        courseid=intent.getStringExtra("courseid");
        System.out.println("在预览页打印courseid:"+courseid);

        t1pre_coursename.setText(intent.getStringExtra("coursename"));
        coursename= intent.getStringExtra("coursename");

        t1pre_classid.setText(intent.getStringExtra("classid"));
        classid= intent.getStringExtra("classid");

        t1pre_comment.setText(intent.getStringExtra("comment"));
        comment=intent.getStringExtra("comment");

        t1pre_teacher.setText(intent.getStringExtra("teacher"));
       teacher=intent.getStringExtra("teacher");
        System.out.println(teacher);

        //  t1pre_otherinfo.setText(intent.getStringExtra("otherinfo"));
        t1pre_latenum.setText(intent.getStringExtra("latenum"));
        latenum=intent.getStringExtra("latenum");

        t1pre_teachtheme.setText(intent.getStringExtra("teachtheme"));
        teachtheme=intent.getStringExtra("teachtheme");
        System.out.println(teachtheme);

        t1pre_intitute.setText(intent.getStringExtra("institute"));
        institute=intent.getStringExtra("institute");
        System.out.println(intent.getStringExtra("institute"));

        t1pre_classnum.setText(intent.getStringExtra("classnum"));
        classnum=intent.getStringExtra("classnum");


        t1pre_time.setText(intent.getStringExtra("time"));
        time=intent.getStringExtra("time");


        t1pre_classroom.setText(intent.getStringExtra("classroom"));
        classroom=intent.getStringExtra("classroom");

        t1pre_actualnum.setText(intent.getStringExtra("actualnum"));
        actualnum=intent.getStringExtra("actualnum");

        t1pre_shouldnum.setText(intent.getStringExtra("shouldnum"));
        shouldnum=intent.getStringExtra("shouldnum");

        t1pre_type.setText(intent.getStringExtra("type"));
        classtype=intent.getStringExtra("type");

        t1pre_object.setText(intent.getStringExtra("extend"));
        classobject=intent.getStringExtra("extend");

        t1pre_score1.setText(intent.getStringExtra("score1"));
        t1_score1=intent.getStringExtra("score1");

        t1pre_score2.setText(intent.getStringExtra("score2"));
        t1_score2=intent.getStringExtra("score2");

        System.out.println(t1_score2);

        t1pre_score3.setText(intent.getStringExtra("score3"));
        t1_score3=intent.getStringExtra("score3");

        t1pre_score4.setText(intent.getStringExtra("score4"));
        t1_score4=intent.getStringExtra("score4");

        t1pre_score5.setText(intent.getStringExtra("score5"));
        t1_score5=intent.getStringExtra("score5");

        t1pre_score6.setText(intent.getStringExtra("score6"));
        t1_score6=intent.getStringExtra("score6");

        t1pre_score7.setText(intent.getStringExtra("score7"));
        t1_score7=intent.getStringExtra("score7");

        t1pre_score8.setText(intent.getStringExtra("score8"));
        t1_score8=intent.getStringExtra("score8");

        t1pre_score9.setText(intent.getStringExtra("score9"));
        t1_score9=intent.getStringExtra("score9");

        t1pre_totalscore.setText(intent.getStringExtra("t1_total"));
        t1_totalscore=intent.getStringExtra("totalscore");


    }


    private void submit(){
        showLoading(); //显示加载框


        Thread submitRunnable = new Thread() {
            public void run() {
                super.run();
                //setChangeBtnClickable(false);//点击确认后，设置确认按钮不可点击状态

                //睡眠3秒
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                global_variance mysession=(global_variance)(Activity_t1submit.this.getApplication());
                sessionid=mysession.getSessionid();

                System.out.println("在提交的时候打印courseid:"+courseid);
                //添加请求信息
                HashMap<String,String> paramsMap=new HashMap<>();
                paramsMap.put("courseid",courseid);
                paramsMap.put("classid",classid);
                paramsMap.put("course",coursename);
                paramsMap.put("department",institute);
                paramsMap.put("latenumber",latenum);
                paramsMap.put("studentnumber",shouldnum);
                paramsMap.put("standardid","100");
                paramsMap.put("room",classroom);
                paramsMap.put("time1",time);
                paramsMap.put("presentnumber",actualnum);
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
                            Activity_t1submit.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toasty.success(Activity_t1submit.this,"提交成功！",Toasty.LENGTH_SHORT).show();
                                    startActivity(new Intent(Activity_t1submit.this,Activity_list.class));
                                }
                            });



                        }
                        else if(result.equals("101")){

                           runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // System.out.println("输入原始密码错误");
                                    Toasty.error(Activity_t1submit.this,"抱歉，您所评课程已被评价两次，请您评价其他课程",Toasty.LENGTH_LONG).show();
                                }
                            });

                        }

                        else if(result.equals("102")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toasty.warning(Activity_t1submit.this,"抱歉，您重复提交了！",Toasty.LENGTH_LONG).show();
                                }
                            });


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                hideLoading();//隐藏加载框
            }
        };

        submitRunnable.start();
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

                global_variance mysession=(global_variance)(Activity_t1submit.this.getApplication());
                sessionid=mysession.getSessionid();

               //System.out.println("在提交的时候打印courseid:"+courseid);
                //添加请求信息
                HashMap<String,String> paramsMap=new HashMap<>();

//                if(flag1==0) {
                    paramsMap.put("latenumber", latenum);
                    paramsMap.put("presentnumber", actualnum);

             //   }
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
                temp2=RequestUtil.get().MapSend(urlsave,sessionid,paramsMap);


                    try {
                        JSONObject userJSON =new JSONObject(temp2);
                        result=userJSON.getString("result");
                        System.out.println(result);
                        if(result.equals("100")){
                            Activity_t1submit.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toasty.success(Activity_t1submit.this,"成功保存到草稿箱！",Toasty.LENGTH_SHORT).show();
                                    startActivity(new Intent(Activity_t1submit.this,Activity_list.class));
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
            mLoadingDialog = new LoadingDialog(Activity_t1submit.this, getString(R.string.loading), false);
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
