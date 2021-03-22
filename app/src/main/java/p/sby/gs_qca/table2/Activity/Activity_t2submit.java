package p.sby.gs_qca.table2.Activity;

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

public class Activity_t2submit extends AppCompatActivity {
    private Button t2sub_save;
    private Button t2sub_submit;
    private LoadingDialog mLoadingDialog; //显示正在加载的对话框
    private TextView t2sub_institute;
    private TextView t2sub_classname;
    private TextView t2sub_teacher;
    private TextView t2sub_classroom;
    private TextView t2sub_papernum;
    private TextView t2sub_score1;
    private TextView t2sub_score2;
    private TextView t2sub_score3;
    private TextView t2sub_score4;
    private TextView t2sub_score5;
    private TextView t2sub_score6;
    private TextView t2sub_score7;
    private TextView t2sub_score8;
    private TextView t2sub_total;
    private TextView t2sub_comment;

    private String urladd="http://116.213.144.72:9817/mobile/form/sjgf/add.ht";
    private String urlsave="http://116.213.144.72:9817/mobile/form/buff/addsjgf.ht";
    private String urledit="http://116.213.144.72:9817/mobile/form/buff/editsjgf.ht";
//    提交数据
    private String courseid="";
    private String option="";
    private int flagsave=0;
    private String teacher="";
    private String classroom="";
    private String coursename="";
    private String institute="";
    private String papernumber="";
    private String score1="";
    private String score2="";
    private String score3="";
    private String score4="";
    private String score5="";
    private String score6="";
    private String score7="";
    private String score8="";
    private String total="";
    private String comment="";

    private String result;
    private String sessionid;
    private String temp;
    private String temp1;
    private String temp2;
    private int flag=0;
    private String formid="";


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t2_submit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_t2preview);
        toolbar.setTitle("考试试卷规范性评价");
        setSupportActionBar(toolbar);

        initView();

        getValue();

        t2sub_save.setOnClickListener(new View.OnClickListener() {
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


        t2sub_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(    score1.equals("") || score2.equals("") || score3.equals("") || score4.equals("") || score5.equals("") || score6.equals("") || score7.equals("") || score8.equals("")){
                    flag=2;
                }

                if(flag==2){
                    Toasty.warning(Activity_t2submit.this,"您的表格未完整填写，请检查！",Toasty.LENGTH_LONG).show();
                }
                else {
                    submit();
                }

            }
        });

    }

    public void initView() {
        t2sub_save=(Button)findViewById(R.id.t2sub_save);
        t2sub_submit=(Button)findViewById(R.id.t2sub_submit);


        t2sub_institute=(TextView)findViewById(R.id.t2sub_institute);
        t2sub_classname=(TextView) findViewById(R.id.t2sub_coursename);
        t2sub_teacher=(TextView) findViewById(R.id.t2sub_teacher);
        t2sub_papernum=(TextView)findViewById(R.id.t2sub_papernumber);
        t2sub_classroom=(TextView)findViewById(R.id.t2sub_class);


        t2sub_score1=(TextView)findViewById(R.id.t2sub_score1);
        t2sub_score2=(TextView)findViewById(R.id.t2sub_score2);
        t2sub_score3=(TextView)findViewById(R.id.t2sub_score3);
        t2sub_score4=(TextView)findViewById(R.id.t2sub_score4);
        t2sub_score5=(TextView)findViewById(R.id.t2sub_score5);
        t2sub_score6=(TextView)findViewById(R.id.t2sub_score6);
        t2sub_score7=(TextView)findViewById(R.id.t2sub_score7);
        t2sub_score8=(TextView)findViewById(R.id.t2sub_score8);
        t2sub_total=(TextView)findViewById(R.id.t2sub_total);
        t2sub_comment=(TextView)findViewById(R.id.t2sub_comment);


    }

    public void getValue(){
        Intent intent=getIntent();
//        formid=intent.getStringExtra("formid");
//        option=intent.getStringExtra("option");
        System.out.println("********option值*******");
//        System.out.println(option);

        courseid=intent.getStringExtra("courseid");
        System.out.println("在预览页打印courseid:"+courseid);
        option=intent.getStringExtra("option");

        t2sub_institute.setText(intent.getStringExtra("institute"));
        t2sub_classname.setText(intent.getStringExtra("coursename"));
        t2sub_teacher.setText(intent.getStringExtra("teacher"));
        t2sub_classroom.setText(intent.getStringExtra("classroom"));
        papernumber=(intent.getStringExtra("papernum"));
        t2sub_papernum.setText(papernumber);

        institute=intent.getStringExtra("institute");
        coursename=intent.getStringExtra("coursename");
        teacher=intent.getStringExtra("teacher");
        classroom=intent.getStringExtra("classroom");
        score1=intent.getStringExtra("score1");
        score2=intent.getStringExtra("score2");
        score3=intent.getStringExtra("score3");
        score4=intent.getStringExtra("score4");
        score5=intent.getStringExtra("score5");
        score6=intent.getStringExtra("score6");
        score7=intent.getStringExtra("score7");
        score8=intent.getStringExtra("score8");
        total=intent.getStringExtra("total");
        comment=intent.getStringExtra("comment1");
        t2sub_score1.setText(score1);
        t2sub_score2.setText(score2);
        t2sub_score3.setText(score3);
        t2sub_score4.setText(score4);
        t2sub_score5.setText(score5);
        t2sub_score6.setText(score6);
        t2sub_score7.setText(score7);
        t2sub_score8.setText(score8);
        t2sub_total.setText(total);
        t2sub_comment.setText(comment);
//        courseid=intent.getStringExtra("courseid");


//

    }

    private void submit() {
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

                global_variance mysession = (global_variance) (Activity_t2submit.this.getApplication());
                sessionid = mysession.getSessionid();

                System.out.println("在提交的时候打印courseid:" + courseid);
                //添加请求信息
                HashMap<String, String> paramsMap = new HashMap<>();
                paramsMap.put("courseid", courseid);
                paramsMap.put("standardid", "100");
                paramsMap.put("papernumber", papernumber);

                paramsMap.put("comment1", comment);
                paramsMap.put("score1", score1);
                paramsMap.put("score2", score2);
                paramsMap.put("score3", score3);
                paramsMap.put("score4", score4);
                paramsMap.put("score5", score5);
                paramsMap.put("score6", score6);
                paramsMap.put("score7", score7);
                paramsMap.put("score8", score8);

                System.out.println(paramsMap);

                FormBody.Builder builder = new FormBody.Builder();
                for (String key : paramsMap.keySet()) {
                    //追加表单信息
                    builder.add(key, paramsMap.get(key));
                }
                temp = RequestUtil.get().MapSend(urladd, sessionid, paramsMap);

                try {
                    JSONObject userJSON = new JSONObject(temp);
                    result = userJSON.getString("result");
                    System.out.println(result);
                    if (result.equals("100")) {
                        Activity_t2submit.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toasty.success(Activity_t2submit.this, "提交成功！", Toasty.LENGTH_SHORT).show();
                                startActivity(new Intent(Activity_t2submit.this, Activity_list.class));
                            }
                        });


                    } else if (result.equals("101")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // System.out.println("输入原始密码错误");
                                Toasty.error(Activity_t2submit.this, "抱歉，您所评课程已被评价两次，请您评价其他课程", Toasty.LENGTH_LONG).show();
                            }
                        });

                    } else if (result.equals("102")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toasty.warning(Activity_t2submit.this, "抱歉，您重复提交了！", Toasty.LENGTH_LONG).show();
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

                global_variance mysession=(global_variance)(Activity_t2submit.this.getApplication());
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
                paramsMap.put("papernum",papernumber);

                paramsMap.put("teacher",teacher);
                paramsMap.put("comment1",comment);
                paramsMap.put("score1",score1);
                paramsMap.put("score2",score2);
                paramsMap.put("score3",score3);
                paramsMap.put("score4",score4);
                paramsMap.put("score5",score5);
                paramsMap.put("score6",score6);
                paramsMap.put("score7",score7);
                paramsMap.put("score8",score8);
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
                        Activity_t2submit.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toasty.success(Activity_t2submit.this,"成功修改草稿！",Toasty.LENGTH_SHORT).show();
                                startActivity(new Intent(Activity_t2submit.this,Activity_list.class));
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

                global_variance mysession=(global_variance)(Activity_t2submit.this.getApplication());
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
                paramsMap.put("papernum",papernumber);

                paramsMap.put("teacher",teacher);
                paramsMap.put("comment1",comment);
                paramsMap.put("score1",score1);
                paramsMap.put("score2",score2);
                paramsMap.put("score3",score3);
                paramsMap.put("score4",score4);
                paramsMap.put("score5",score5);
                paramsMap.put("score6",score6);
                paramsMap.put("score7",score7);
                paramsMap.put("score8",score8);
                System.out.println(paramsMap);

                FormBody.Builder builder = new FormBody.Builder();
                for (String key : paramsMap.keySet()) {
                    //追加表单信息
                    builder.add(key, paramsMap.get(key));
                }

                temp=RequestUtil.get().MapSend(urlsave,sessionid,paramsMap);


                try {
                    JSONObject userJSON =new JSONObject(temp);
                    result=userJSON.getString("result");
                    System.out.println(result);
                    if(result.equals("100")){
                        Activity_t2submit.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toasty.success(Activity_t2submit.this,"成功保存到草稿箱！",Toasty.LENGTH_SHORT).show();
                                startActivity(new Intent(Activity_t2submit.this,Activity_list.class));
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
            mLoadingDialog = new LoadingDialog(Activity_t2submit.this, getString(R.string.loading), false);
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
