package p.sby.gs_qca.Main.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import p.sby.gs_qca.R;
import p.sby.gs_qca.widget.LoadingDialog;

public class Activity_changepw extends AppCompatActivity implements View.OnClickListener {
    private EditText cgp_op;
    private EditText cgp_np;
    private EditText cgp_cp;
    private String result;
    private String npword;
    private String cnpword;
    private CheckBox cgp_see;
    String sessionid;
    private String url = "http://117.121.38.95:9817/mobile/system/user/modifyPwd.ht";
    private LoadingDialog mLoadingDialog; //显示正在加载的对话框

    private Button confirm;
    private static final String TAG = "Activity_changepw";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepw);


        /****toolbar功能条**********/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_cpw); //主页上方功能条
        toolbar.setTitle("修改密码");

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


        /*********修改密码功能区****************/
        initViews();
        setupEvent();
    }

    /**
     * 设置点击事件
     */
    private void setupEvent() {
        cgp_see.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }


    /*****注册控件*******/
    private void initViews() {
        cgp_op = (EditText) findViewById(R.id.cgp_op);
        cgp_np = (EditText) findViewById(R.id.cgp_np);
        cgp_cp = (EditText) findViewById(R.id.cgp_cp);
        confirm = (Button) findViewById(R.id.cgp_confirm);
        cgp_see=(CheckBox) findViewById(R.id.cgp_see);
        cgp_op.setTransformationMethod(PasswordTransformationMethod.getInstance());
        cgp_np.setTransformationMethod(PasswordTransformationMethod.getInstance());
        cgp_cp.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }


    /**
     * 判断哪个点击事件
     * @param v 当前View对象
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cgp_confirm:
             if(check_right())   {
                 change();
             }
             else{
                 Toasty.error(this,"新密码与确认新密码不一致！",Toasty.LENGTH_SHORT).show();
             }

                break;

            case R.id.cgp_see:
                setPasswordVisibility();    //改变图片并设置输入框的文本可见或不可见
                break;

        }
    }

    private void toast(String s){
        Toast.makeText(getApplication(),s,Toast.LENGTH_SHORT).show();
    }

    /**
     * 判断输入新密码和确认新密码是否一致
     */
    private boolean check_right() {

        if(cgp_np.getText().toString().equals(cgp_cp.getText().toString())){
            return true;
        }
        return false;
    }


    /*****修改密码函数******/
    private void change() {
        showLoading(); //显示加载框


        Thread changepwRunnable = new Thread() {
            public void run() {
                super.run();
                setChangeBtnClickable(false);//点击确认后，设置确认按钮不可点击状态

                //睡眠3秒
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                global_variance mysession=(global_variance)(getApplication());
                sessionid=mysession.getSessionid();

                //添加请求信息
                HashMap<String,String> paramsMap=new HashMap<>();
                paramsMap.put("primitivePassword",cgp_op.getText().toString().trim());
                paramsMap.put("newPassword",cgp_np.getText().toString().trim());
                FormBody.Builder builder = new FormBody.Builder();
                for (String key : paramsMap.keySet()) {
                    //追加表单信息
                    builder.add(key, paramsMap.get(key));
                }

                OkHttpClient okHttpClient=new OkHttpClient();
                RequestBody formBody = builder.build();
                Request request = new Request.Builder()
                        .addHeader("cookie", sessionid)
                        .url(url)
                        .post(formBody).build();
                Call call = okHttpClient.newCall(request);

                try {
                    Response response = call.execute();
                    String responseData = response.body().string();
                    System.out.println(responseData);

                    String  temp=responseData.substring(responseData.indexOf("{"),responseData.lastIndexOf("}") + 1);
                    System.out.println(temp);


                    try {
                        JSONObject userJSON =new JSONObject(temp);
                        result=userJSON.getString("result");
                        System.out.println(result);
                        if(result.equals("100")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toasty.success(Activity_changepw.this,"密码修改成功！",Toasty.LENGTH_SHORT).show();
                                    startActivity(new Intent (Activity_changepw.this,Activity_list.class));
                                }
                            });
                        }

                        else if(result.equals("101")){

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    System.out.println("输入原始密码错误");
                                    Toasty.error(Activity_changepw.this,"输入原始密码错误！",Toasty.LENGTH_LONG).show();
                                }
                            });

                        }
                        else if(result.equals("102")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toasty.info(Activity_changepw.this,"输入新密码与原始密码相同！",Toasty.LENGTH_LONG).show();
                                }
                            });

                        }
                        else if(result.equals("103")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toasty.warning(Activity_changepw.this,"修改异常，请重新操作！",Toasty.LENGTH_LONG).show();
                                }
                            });


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
                setChangeBtnClickable(true);  //这里解放确定按钮，设置为可以点击
                hideLoading();//隐藏加载框
            }
        };

        changepwRunnable.start();
    }


         /**加载进度框**/
        public void showLoading () {
            if (mLoadingDialog == null) {
                mLoadingDialog = new LoadingDialog(this, getString(R.string.loading), false);
            }
            mLoadingDialog.show();
        }

        /**设置按钮不可点击**/
        public void setChangeBtnClickable ( boolean clickable){
            confirm.setClickable(clickable);
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




    /**
     * 设置密码可见和不可见的相互转换
     */
    private void setPasswordVisibility() {
        if (cgp_see.isChecked()) {
            cgp_op.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            cgp_np.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            cgp_op.setTransformationMethod(PasswordTransformationMethod.getInstance());
            cgp_np.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

