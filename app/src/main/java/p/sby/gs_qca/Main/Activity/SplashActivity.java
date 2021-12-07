package p.sby.gs_qca.Main.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import p.sby.gs_qca.Main.Adapters.TableListAdapter;
import p.sby.gs_qca.R;
import p.sby.gs_qca.table2.Activity.Activity_t2basicinfo_teacher;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //设置此界面竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();

    }

    public String getVersion() {
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
        String http_versionName = "2.0";
        TextView tv_version = findViewById(R.id.tv_version);
//        try {
//            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
//            tv_version.setText("version:" + packageInfo.versionName);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//            tv_version.setText("version");
//        }
        //利用timer让此界面延迟3秒后跳转，timer有一个线程，该线程不断执行task

//        if current_versionName ==
//        http_versionName = "1.0";
        local_versionName = getVersion();
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
