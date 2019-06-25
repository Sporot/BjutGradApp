package p.sby.gs_qca.table5.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import p.sby.gs_qca.table3.Activity.Activity_t3submit;
import p.sby.gs_qca.util.RequestUtil;
import p.sby.gs_qca.widget.LoadingDialog;

public class Activity_t5submit extends AppCompatActivity {
    private LoadingDialog mLoadingDialog; //显示正在加载的对话框
    private TextView t5pre_intitute;
    private TextView t5pre_major;
    private TextView t5pre_teacher;
    private TextView t5pre_student;
    private TextView t5pre_type;
    private TextView t5pre_classroom;
//    private TextView t5pre_year;
//    private TextView t5pre_month;
//    private TextView t5pre_day;
    private TextView t5pre_time;
    private TextView t5pre_expert;
    private TextView t5pre_totalScrore;
    private TextView t5pre_comment1;
    private TextView t5pre_comment2;
    private Button t5pre_submit;
    private Button t5pre_save;
    private String result;
    String sessionid;
    private String temp2;
    private String temp1;
    private String temp;
    private String urladd="http://117.121.38.95:9817/mobile/form/lwdb/add.ht";
    private String urledit="http://117.121.38.95:9817/mobile/form/buff/editlwdb.ht";
    private String urlsave="http://117.121.38.95:9817/mobile/form/buff/addlwdb.ht";
    private String pre_score;
    private String pre_comment1;
    private String pre_comment2;
    private String pre_reportid;
    private String option;
    private int flag=0;
    private int flagsave=0;
    private String id;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t5_submit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_t5preview); //主页上方功能条
        toolbar.setTitle("预览");

        toolbar.setTitleTextColor(getResources().getColor(R.color.white)); //设置标题颜色
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initView();
        getValue();

        t5pre_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(  pre_score.equals("")||pre_comment1.equals("")|| pre_comment2.equals("")){
                    flag=2;
                }

                if(flag==2){
                    Toasty.warning(Activity_t5submit.this,"您的表格未完整填写，请检查！",Toasty.LENGTH_LONG).show();
                }
                else {
                    submit();
                }


            }
        });

        t5pre_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("t3save", "onClick: "+option);
                if (option.equals("basic"))
                {
                    save2draft(flagsave);
                }

                else if (option.equals("drafts")){
                    modifydraft(flagsave);
                }

            }
        });





//        返回上级页面
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//返回
            }
        });
    }

    public void initView(){
        t5pre_intitute=(TextView)findViewById(R.id.t5pre_institute);
        t5pre_major=(TextView)findViewById(R.id.t5pre_major);
        t5pre_teacher=(TextView)findViewById(R.id.t5pre_teacher);
        t5pre_student =(TextView)findViewById(R.id.t5pre_student);
        t5pre_type=(TextView)findViewById(R.id.t5pre_grade);
        t5pre_classroom=(TextView)findViewById(R.id.t5pre_place);
        t5pre_time=(TextView)findViewById(R.id.t5pre_time);
//        t5pre_month=(TextView)findViewById(R.id.t5pre_month);
//        t5pre_year=(TextView)findViewById(R.id.t5pre_year);
//        t5pre_day=(TextView)findViewById(R.id.t5pre_day);
        t5pre_expert=(TextView)findViewById(R.id.t5pre_group);
        t5pre_totalScrore=(TextView)findViewById(R.id.t5pre_score);
        t5pre_comment1=(TextView)findViewById(R.id.t5pre_comment1);
        t5pre_comment2=(TextView)findViewById(R.id.t5pre_comment2);
        t5pre_submit=(Button)findViewById(R.id.t5pre_submit);
        t5pre_save=(Button)findViewById(R.id.t5pre_save);

    }

    public void getValue(){
        Intent intent=getIntent();

        option=intent.getStringExtra("sendfrom");
        t5pre_intitute.setText(intent.getStringExtra("institute"));

        t5pre_major.setText(intent.getStringExtra("major"));
//
        t5pre_teacher.setText(intent.getStringExtra("teacher"));

        t5pre_student.setText(intent.getStringExtra("student"));

        t5pre_type.setText(intent.getStringExtra("type"));
//        t5pre_year.setText(intent.getStringExtra("year"));
//        t5pre_month.setText(intent.getStringExtra("month"));
//        t5pre_day.setText(intent.getStringExtra("day"));
        t5pre_time.setText(intent.getStringExtra("time"));

        t5pre_classroom.setText(intent.getStringExtra("classroom"));

        t5pre_expert.setText(intent.getStringExtra("expert"));
        pre_score=intent.getStringExtra("score");
        t5pre_totalScrore.setText(pre_score);
        pre_comment1=intent.getStringExtra("comment1");
        pre_comment2=intent.getStringExtra("comment2");
        t5pre_comment1.setText(pre_comment1);
        t5pre_comment2.setText(pre_comment2);
        pre_reportid=intent.getStringExtra("reportid");

        id=intent.getStringExtra("id");


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

                global_variance mysession=(global_variance)(Activity_t5submit.this.getApplication());
                sessionid=mysession.getSessionid();


                HashMap<String,String> paramsMap=new HashMap<>();
                paramsMap.put("reportid",pre_reportid);
                paramsMap.put("standardid","100");
                paramsMap.put("score1",pre_score);
                paramsMap.put("comment1",pre_comment1);
                paramsMap.put("comment2",pre_comment2);
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
                        Activity_t5submit.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toasty.success(Activity_t5submit.this,"成功保存到草稿箱！",Toasty.LENGTH_SHORT).show();
                                startActivity(new Intent(Activity_t5submit.this,Activity_list.class));
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
                global_variance mysession=(global_variance)(Activity_t5submit.this.getApplication());
                sessionid=mysession.getSessionid();

                //System.out.println("在提交的时候打印courseid:"+courseid);
                //添加请求信息
                HashMap<String,String> paramsMap=new HashMap<>();
                paramsMap.put("reportid",pre_reportid);
                paramsMap.put("id",id);
                paramsMap.put("standardid","100");
                paramsMap.put("score1",pre_score);
                paramsMap.put("comment1",pre_comment1);
                paramsMap.put("comment2",pre_comment2);
                FormBody.Builder builder = new FormBody.Builder();
                Log.i("t3drafts", "submitmodify "+paramsMap);
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
                        Activity_t5submit.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toasty.success(Activity_t5submit.this,"成功修改草稿！",Toasty.LENGTH_SHORT).show();
                                startActivity(new Intent(Activity_t5submit.this,Activity_list.class));
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

                global_variance mysession=(global_variance)(Activity_t5submit.this.getApplication());
                sessionid=mysession.getSessionid();

                System.out.println("在提交的时候打印reportid:"+pre_reportid);
                //添加请求信息
                HashMap<String,String> paramsMap=new HashMap<>();
                paramsMap.put("reportid",pre_reportid);
                paramsMap.put("standardid","100");
                paramsMap.put("score1",pre_score);
                paramsMap.put("comment1",pre_comment1);
                paramsMap.put("comment2",pre_comment2);
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
                        Activity_t5submit.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toasty.success(Activity_t5submit.this,"提交成功！",Toasty.LENGTH_SHORT).show();
                                startActivity(new Intent(Activity_t5submit.this,Activity_list.class));
                            }
                        });



                    }
                    else if(result.equals("101")){

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // System.out.println("输入原始密码错误");
                                Toasty.error(Activity_t5submit.this,"抱歉，您所评课程已被评价两次，请您评价其他课程",Toasty.LENGTH_LONG).show();
                            }
                        });

                    }

                    else if(result.equals("102")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toasty.warning(Activity_t5submit.this,"抱歉，您重复提交了！",Toasty.LENGTH_LONG).show();
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

    public void showLoading () {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(Activity_t5submit.this, getString(R.string.loading), false);
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
