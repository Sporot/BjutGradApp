package p.sby.gs_qca.table4.Activity;

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
import p.sby.gs_qca.table1.Activity.Activity_t1submit;
import p.sby.gs_qca.util.RequestUtil;
import p.sby.gs_qca.widget.LoadingDialog;

public class Activity_t4submit extends AppCompatActivity {
    private TextView t4_department;
    private TextView t4_major;
    private TextView t4_studentname;
    private TextView t4_type;
    private TextView t4_teachername;
    private TextView t4_place;
    private TextView t4_time;
    private TextView t4_experts;

    private TextView t4_score1;
    private TextView t4_comment1;
    private TextView t4_comment2;

    private Button t4_submit;
    private Button t4_save;
    private String temp;

    private String department;
    private String major;
    private String type;
    private String studentname;
    private String teachername;
    private String room;
    private String time;
    private String experts;
    private String score1="";
    private String comment1;
    private String comment2;
    private String reportid;
    private String option;
    private String id;

    private int flag=0;
    private int flagsave=0;

    private LoadingDialog mLoadingDialog; //显示正在加载的对话框
    String sessionid;
    private String result;
    private String temp1;
    private String temp2;

    private String urladd="http://116.213.144.72:9817/mobile/form/zqkh/add.ht";
    private String urledit="http://116.213.144.72:9817/mobile/form/buff/editzqkh.ht";
    private String urlsave="http://116.213.144.72:9817/mobile/form/buff/addzqkh.ht";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t4_submit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_t4submit); //主页上方功能条
        toolbar.setTitle("提交");

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

        putValue();

        t4_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(  score1.equals("")|| comment1.equals("")|| comment2.equals("")){
                    flag=2;
                }

                if(flag==2){
                    Toasty.warning(Activity_t4submit.this,"您的表格未完整填写，请检查！",Toasty.LENGTH_LONG).show();
                }
                else {
                    submit();
                }
            }
        });

        t4_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("t4save", "onClick: "+option);
                    if (option.equals("basic"))
                    {
                        save2draft(flagsave);
                    }

                    else if (option.equals("drafts")){
                        modifydraft(flagsave);
                    }


            }
        });
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

                global_variance mysession=(global_variance)(Activity_t4submit.this.getApplication());
                sessionid=mysession.getSessionid();


                HashMap<String,String> paramsMap=new HashMap<>();
                paramsMap.put("reportid",reportid);
                paramsMap.put("standardid","100");
                paramsMap.put("score1",score1);
                paramsMap.put("comment1",comment1);
                paramsMap.put("comment2",comment2);
                FormBody.Builder builder = new FormBody.Builder();
                Log.i("t4save", "run: "+paramsMap);
                for (String key : paramsMap.keySet()) {
                    //追加表单信息
                    builder.add(key, paramsMap.get(key));
                }
                temp2=RequestUtil.get().MapSend(urlsave,sessionid,paramsMap);


                try {
                    JSONObject userJSON =new JSONObject(temp2);
                    result=userJSON.getString("result");
                    Log.i("t4save", "run: "+result);
                    if(result.equals("100")){
                        Activity_t4submit.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toasty.success(Activity_t4submit.this,"成功保存到草稿箱！",Toasty.LENGTH_SHORT).show();
                                startActivity(new Intent(Activity_t4submit.this,Activity_list.class));
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
                global_variance mysession=(global_variance)(Activity_t4submit.this.getApplication());
                sessionid=mysession.getSessionid();

                //System.out.println("在提交的时候打印courseid:"+courseid);
                //添加请求信息
                HashMap<String,String> paramsMap=new HashMap<>();
                paramsMap.put("reportid",reportid);
                paramsMap.put("id",id);
                paramsMap.put("standardid","100");
                paramsMap.put("score1",score1);
                paramsMap.put("comment1",comment1);
                paramsMap.put("comment2",comment2);
                FormBody.Builder builder = new FormBody.Builder();
                Log.i("t4drafts", "submitmodify "+paramsMap);
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
                        Activity_t4submit.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toasty.success(Activity_t4submit.this,"成功修改草稿！",Toasty.LENGTH_SHORT).show();
                                startActivity(new Intent(Activity_t4submit.this,Activity_list.class));
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
                global_variance mysession=(global_variance)(Activity_t4submit.this.getApplication());
                sessionid=mysession.getSessionid();

                //添加请求信息
                HashMap<String,String> paramsMap=new HashMap<>();
                paramsMap.put("reportid",reportid);
                paramsMap.put("standardid","100");
                paramsMap.put("score1",score1);
                paramsMap.put("comment1",comment1);
                paramsMap.put("comment2",comment2);
                Log.i("t4submit", "fromdrafts "+paramsMap);
                FormBody.Builder builder = new FormBody.Builder();
                for (String key : paramsMap.keySet()) {
                    //追加表单信息
                    builder.add(key, paramsMap.get(key));
                }
                temp=RequestUtil.get().MapSend(urladd,sessionid,paramsMap);

                try {
                    JSONObject userJSON =new JSONObject(temp);
                    result=userJSON.getString("result");
                    Log.i("t4add", "run: "+result);
                    if(result.equals("100")){
                        Activity_t4submit.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toasty.success(Activity_t4submit.this,"提交成功！",Toasty.LENGTH_SHORT).show();
                                startActivity(new Intent(Activity_t4submit.this,Activity_list.class));
                            }
                        });
                    }
                    else if(result.equals("101")){

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // System.out.println("输入原始密码错误");
                                Toasty.error(Activity_t4submit.this,"抱歉，您所评课程已被评价两次，请您评价其他课程",Toasty.LENGTH_LONG).show();
                            }
                        });

                    }
                    else if(result.equals("102")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toasty.warning(Activity_t4submit.this,"抱歉，您重复提交了！",Toasty.LENGTH_LONG).show();
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

    private void putValue() {
        Intent intent=getIntent();
        option=intent.getStringExtra("sendfrom");
        department=intent.getStringExtra("department");
        t4_department.setText(department);

        major=intent.getStringExtra("major");
        t4_major.setText(intent.getStringExtra("major"));

        studentname=intent.getStringExtra("studentname");
        t4_studentname.setText(intent.getStringExtra("studentname"));

        type=intent.getStringExtra("type");
        t4_type.setText(intent.getStringExtra("type"));

        teachername=intent.getStringExtra("teachername");
        t4_teachername.setText(intent.getStringExtra("teachername"));

        room=intent.getStringExtra("room");
        t4_place.setText(intent.getStringExtra("room"));

        time=intent.getStringExtra("time");
        t4_time.setText(intent.getStringExtra("time"));

        experts=intent.getStringExtra("experts");
        t4_experts.setText(intent.getStringExtra("experts"));

        score1=intent.getStringExtra("score1");
        t4_score1.setText(intent.getStringExtra("score1"));

        comment1=intent.getStringExtra("comment1");
        t4_comment1.setText(intent.getStringExtra("comment1"));

        comment2=intent.getStringExtra("comment2");
        t4_comment2.setText(intent.getStringExtra("comment2"));

        reportid=intent.getStringExtra("reportid");
        id=intent.getStringExtra("id");

    }

    private void initView() {
        t4_department=(TextView)findViewById(R.id.t4_department);
        t4_major=(TextView)findViewById(R.id.t4_major);
        t4_studentname=(TextView)findViewById(R.id.t4_studentname);
        t4_teachername=(TextView)findViewById(R.id.t4_teachername);
        t4_type=(TextView)findViewById(R.id.t4_type);
        t4_place=(TextView)findViewById(R.id.t4_place);
        t4_time=(TextView)findViewById(R.id.t4_time);
        t4_experts=(TextView)findViewById(R.id.t4_experts);

        t4_score1=(TextView)findViewById(R.id.t4_score1);
        t4_comment1=(TextView)findViewById(R.id.t4_comment1);
        t4_comment2=(TextView)findViewById(R.id.t4_comment2);

        t4_submit=(Button)findViewById(R.id.t4_submit);
        t4_save=(Button)findViewById(R.id.t4_save);
    }

    /**加载进度框**/
    public void showLoading () {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(Activity_t4submit.this, getString(R.string.loading), false);
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
