package p.sby.gs_qca.Main.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import p.sby.gs_qca.R;
import p.sby.gs_qca.table2.Activity.Activity_t2basicinfo_dep;
import p.sby.gs_qca.util.RequestUtil;


public class SplashActivity extends AppCompatActivity {
    private String temp;
    private String appUrl = "http://116.213.144.72:9817/appversion.ht";
    private String sessionid = "JSESSIONID=CA791FDBD079F6333DEABEB6E827C6D9";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //设置此界面竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();

    }

    public void get_http_Version() {


        String http_version = null;


        Thread getCourse =new Thread(){
            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                temp = RequestUtil.get().sendrequest(appUrl,sessionid,"","");
                System.out.println(temp);


            }

        };

        getCourse.start();
        try {
            getCourse.join(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



////        JSONObject httpVersion = null; //接收json对象
//
//        try {
//            httpVersion = new JSONObject(temp);
//            http_version = httpVersion.getString("android");
//        } catch (JSONException e) {
//            e.printStackTrace();
// d        }
//        System.out.println("++++++++++++++"+http_version+"+++++++++++++");
        //return http_version;
        //return http_version;
    }

    public String get_local_Version() {
        String mVersionName = "";
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            mVersionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return mVersionName;
    }


    private void init() {

        String local_versionName = "";
        String http_versionName = "1.0";
        //String http_version = get_http_Version();
        get_http_Version();
        TextView tv_version = findViewById(R.id.tv_version);

        local_versionName = get_local_Version();
        if (local_versionName != "") {

            tv_version.setText("version:" + local_versionName);
        }
        else {
            tv_version.setText("version");
        }

        if (http_versionName.equals(local_versionName)){
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    //发送intent实现页面跳转，第一个参数为当前页面的context，第二个参数为要跳转的主页
                    Intent intent = new Intent(SplashActivity.this, Activity_login.class);
                    startActivity(intent);
                    //跳转后关闭当前欢迎页面
                    SplashActivity.this.finish();
                }
            };
            //调度执行timerTask，第二个参数传入延迟时间（毫秒）
            timer.schedule(timerTask, 1000);
        }

        else{
            new AlertDialog.Builder(this)
                    .setTitle("软件更新")
                    .setMessage("已检测到更新的版本，请及时更新app")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(SplashActivity.this, Activity_login.class));
                        }
                    } )
//                    .setNegativeButton("否", null)
                    .show();

        }
    }
}
