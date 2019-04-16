package p.sby.gs_qca.table4.Activity;

import android.Manifest;
import android.content.Intent;
import android.media.Image;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    private ImageView open;
    private LoadingDialog mLoadingDialog; //显示正在加载的对话框
    private ProgressBar progressBar;
    private int hasDownload=0;

    private TextView t4_department;
    private TextView t4_major;
    private TextView t4_filepath;
    private TextView t4_studentname;
    private TextView t4_teachername;
    private TextView t4_type;

    private String filePath;
    private String department;
    private String major;
    private String studentname;
    private String teachername;
    private String type;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t4_reportdetail);


        /*****上方功能栏****/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_t4report); //主页上方功能条
        toolbar.setTitle("报告详情");

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

        Intent intent=getIntent();
        department=intent.getStringExtra("department");
        major=intent.getStringExtra("major");
        studentname=intent.getStringExtra("studentname");
        teachername=intent.getStringExtra("teachername");
        type=intent.getStringExtra("type");

        initView();
        t4_teachername.setText(teachername);
        t4_studentname.setText(studentname);
        t4_department.setText(department);
        t4_major.setText(major);
        t4_type.setText(type);


        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("download", "onClick: "+"点击下载");
                if(hasDownload==0)
                {
                    showLoading();
                    getFile();
                    hasDownload=1;
                }
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

                Intent intent =new Intent(Activity_t4reportdetail.this,Activity_basicinfo4.class);
                intent.putExtra("department",department);
                intent.putExtra("major",major);
                intent.putExtra("studentname",studentname);
                intent.putExtra("type",type);
                intent.putExtra("teachername",teachername);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        //绑定按钮
        t4_department=(TextView)findViewById(R.id.t4_institute);
        t4_major=(TextView)findViewById(R.id.t4_major);
        t4_studentname=(TextView)findViewById(R.id.t4_studentname);
        t4_teachername=(TextView)findViewById(R.id.t4_teachername) ;
        t4_type=(TextView)findViewById(R.id.t4_type);

        t4_startfill=(Button) findViewById(R.id.t4_startfill);
        open=(ImageView) findViewById(R.id.open);
      //  progressBar=(ProgressBar)findViewById(R.id.progress_bar);
    }

    public void getFile(){
        global_variance myssession = ((global_variance)getApplicationContext());
        sessionid =myssession.getSessionid();
        Thread Getfile = new Thread(){

            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                DownloadUtil.get().download("http://117.121.38.95:9817/platform/file/filemessage/download.ht", sessionid,"download", new DownloadUtil.OnDownloadListener() {
                            @Override
                            public void onDownloadSuccess() {
                                Log.i("download", "onDownloadSuccess:    "+"下载成功");
                            }
                            @Override
                            public void onDownloading(int progress) {
                              // progressBar.setProgress(progress);
                            } @Override
                            public void onDownloadFailed() {
                                 Log.i("download", "onDownloadFailed:   "+"下载失败");
                            } }
                );
                hideLoading();
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
