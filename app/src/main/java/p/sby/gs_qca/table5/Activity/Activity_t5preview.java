package p.sby.gs_qca.table5.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import okhttp3.FormBody;
import p.sby.gs_qca.Main.Activity.global_variance;
import p.sby.gs_qca.R;
import p.sby.gs_qca.util.RequestUtil;
import p.sby.gs_qca.widget.LoadingDialog;

public class Activity_t5preview extends AppCompatActivity {
    private LoadingDialog mLoadingDialog; //显示正在加载的对话框
    private TextView t5pre_intitute;
    private TextView t5pre_major;
    private TextView t5pre_teacher;
    private TextView t5pre_student;
    private TextView t5pre_type;
    private TextView t5pre_classroom;
    private TextView t5pre_year;
    private TextView t5pre_month;
    private TextView t5pre_day;
    private TextView t5pre_expert;
    private TextView t5pre_totalScrore;
    private TextView t5pre_comment1;
    private TextView t5pre_comment2;
    private Button t5pre_submit;
    private String result;
    String sessionid;
    private String temp;
    private String urladd="http://117.121.38.95:9817/mobile/form/lwdb/add.ht";
    private String pre_score;
    private String pre_comment1;
    private String pre_comment2;
    private String pre_reportid;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t5_preview);

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
                submit();


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
        t5pre_month=(TextView)findViewById(R.id.t5pre_month);
        t5pre_year=(TextView)findViewById(R.id.t5pre_year);
        t5pre_day=(TextView)findViewById(R.id.t5pre_day);
        t5pre_expert=(TextView)findViewById(R.id.t5pre_group);
        t5pre_totalScrore=(TextView)findViewById(R.id.t5pre_score);
        t5pre_comment1=(TextView)findViewById(R.id.t5pre_comment1);
        t5pre_comment2=(TextView)findViewById(R.id.t5pre_comment2);
        t5pre_submit=(Button)findViewById(R.id.t5pre_submit);

    }

    public void getValue(){
        Intent intent=getIntent();
//            formid=intent.getStringExtra("formid");
//            option=intent.getStringExtra("option");
//            System.out.println("********option值*******");
//            System.out.println(option);

//        institute=intent.getStringExtra("expert");
//        System.out.println("在预览页打印courseid:"+institute);
//        t5pre_intitute.setText(intent.getStringExtra("institute"));
        t5pre_intitute.setText(intent.getStringExtra("institute"));

        t5pre_major.setText(intent.getStringExtra("major"));
//
        t5pre_teacher.setText(intent.getStringExtra("teacher"));

//
        t5pre_student.setText(intent.getStringExtra("student"));

        t5pre_type.setText(intent.getStringExtra("type"));
        t5pre_year.setText(intent.getStringExtra("year"));
        t5pre_month.setText(intent.getStringExtra("month"));
        t5pre_day.setText(intent.getStringExtra("day"));
        t5pre_classroom.setText(intent.getStringExtra("classroom"));

        t5pre_expert.setText(intent.getStringExtra("expert"));
        pre_score=intent.getStringExtra("score");
        t5pre_totalScrore.setText(pre_score);
        pre_comment1=intent.getStringExtra("comment1");
        pre_comment2=intent.getStringExtra("comment2");
        t5pre_comment1.setText(pre_comment1);
        t5pre_comment2.setText(pre_comment2);
        pre_reportid=intent.getStringExtra("reportid");




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

                global_variance mysession=(global_variance)(Activity_t5preview.this.getApplication());
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
                        Activity_t5preview.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toasty.success(Activity_t5preview.this,"提交成功！",Toasty.LENGTH_SHORT).show();
//                                startActivity(new Intent(Activity_t1submit.this,Activity_list.class));
                            }
                        });



                    }
                    else if(result.equals("101")){

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // System.out.println("输入原始密码错误");
                                Toasty.error(Activity_t5preview.this,"抱歉，您所评课程已被评价两次，请您评价其他课程",Toasty.LENGTH_LONG).show();
                            }
                        });

                    }

                    else if(result.equals("102")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toasty.warning(Activity_t5preview.this,"抱歉，您重复提交了！",Toasty.LENGTH_LONG).show();
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
            mLoadingDialog = new LoadingDialog(Activity_t5preview.this, getString(R.string.loading), false);
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
