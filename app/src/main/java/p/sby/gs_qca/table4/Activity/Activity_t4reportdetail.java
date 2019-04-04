package p.sby.gs_qca.table4.Activity;

import android.Manifest;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import p.sby.gs_qca.Main.Activity.global_variance;
import p.sby.gs_qca.R;
import p.sby.gs_qca.table1.Activity.Activity_t1preview;
import p.sby.gs_qca.util.DownloadUtil;
import p.sby.gs_qca.util.FileDisplayActivity;
import p.sby.gs_qca.widget.LoadingDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class Activity_t4reportdetail extends AppCompatActivity {
    private Button t4_startfill;
    String sessionid;
    private Button download;
    private Button open;
    private LoadingDialog mLoadingDialog; //显示正在加载的对话框
    private ProgressBar progressBar;


    private String filePath;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t4_reportdetail);


        /*****上方功能栏****/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_t4report); //主页上方功能条
        toolbar.setTitle("中期报告列表");

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

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("download", "onClick: "+"点击下载");
                getFile();
            }
        });


        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] perms = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
                filePath = getFilePath(1);
                Log.i("FileDisplayActivity", "点击"+"点击下载");
                Log.i("FileDisplayActivity", "filepath"+filePath);
                if (!EasyPermissions.hasPermissions(Activity_t4reportdetail.this, perms)) {
                    EasyPermissions.requestPermissions(Activity_t4reportdetail.this, "需要访问手机存储权限！", 10086, perms);
                } else {
                    FileDisplayActivity.show(Activity_t4reportdetail.this, filePath);
                }
            }
        });


        /*****提交按钮点击事件*******/
        t4_startfill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交确认信息

                //跳转到查看报告的页面
                startActivity(new Intent(Activity_t4reportdetail.this,Activity_basicinfo4.class));
            }
        });
    }

    private void initView() {
        //绑定按钮
        t4_startfill=(Button) findViewById(R.id.t4_startfill);
        download=(Button)findViewById(R.id.download);
        open=(Button)findViewById(R.id.open);
        progressBar=(ProgressBar)findViewById(R.id.progress_bar);
    }

    public void getFile(){
        global_variance myssession = ((global_variance)getApplicationContext());
        sessionid =myssession.getSessionid();
        showLoading(); //显示加载框
        Thread Getfile = new Thread(){

            @Override
            public void run() {
                super.run();
                DownloadUtil.get().download("http://117.121.38.95:9817/platform/file/filemessage/download.ht", sessionid,"download", new DownloadUtil.OnDownloadListener() {
                            @Override
                            public void onDownloadSuccess() {
                                Log.i("download", "onDownloadSuccess:    "+"下载成功");
                            }
                            @Override
                            public void onDownloading(int progress) {
                               progressBar.setProgress(progress);
                            } @Override
                            public void onDownloadFailed() {
                                 Log.i("download", "onDownloadFailed:   "+"下载失败");
                            } }
                );
                hideLoading();//隐藏加载框
            }
        };

        Getfile.start();
    }


    /**加载进度框**/
    public void showLoading () {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(Activity_t4reportdetail.this, getString(R.string.loading), false);
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
}
