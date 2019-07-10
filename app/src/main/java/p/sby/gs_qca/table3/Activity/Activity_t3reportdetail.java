package p.sby.gs_qca.table3.Activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
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
import p.sby.gs_qca.table4.Activity.Activity_t4score;
import p.sby.gs_qca.util.DownloadUtil;
import p.sby.gs_qca.util.FileDisplayActivity;
import p.sby.gs_qca.widget.LoadingDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class Activity_t3reportdetail extends AppCompatActivity {
    private Button t3_startfill;
    String sessionid;
    private Button download;
    private ImageView open;
    private LoadingDialog mLoadingDialog; //显示正在加载的对话框
    private ProgressBar progressBar;
    private int hasDownload=0;

    private TextView t3_department;
    private TextView t3_major;
    private TextView t3_filepath;
    private TextView t3_studentname;
    private TextView t3_teachername;
    private TextView t3_type;
    private TextView t3_room;
    private TextView t3_experts;
    private TextView t3_time;

    private String filePath;
    private String department;
    private String major;
    private String studentname;
    private String teachername;
    private String type;
    private String reportid;
    private String time;
    private String room;
    private String experts;
    private String sendfrom="basic";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t3_reportdetail);


        /*****上方功能栏****/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_t3report); //主页上方功能条
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
        reportid=intent.getStringExtra("reportid");
        time=intent.getStringExtra("time");
        room=intent.getStringExtra("room");
        experts=intent.getStringExtra("experts");

        initView();
        t3_teachername.setText(teachername);
        t3_studentname.setText(studentname);
        t3_department.setText(department);
        t3_major.setText(major);
        t3_type.setText(type);
        t3_experts.setText(experts);
        t3_time.setText(time);
        t3_room.setText(room);


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
                if (!EasyPermissions.hasPermissions(Activity_t3reportdetail.this, perms)) {
                    EasyPermissions.requestPermissions(Activity_t3reportdetail.this, "需要访问手机存储权限！", 10086, perms);
                } else {
                    FileDisplayActivity.show(Activity_t3reportdetail.this, filePath);
                }
            }
        });


        /*****提交按钮点击事件*******/
        t3_startfill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交确认信息

                Intent intent =new Intent(Activity_t3reportdetail.this,Activity_t3score.class);
                intent.putExtra("department",department);
                intent.putExtra("major",major);
                intent.putExtra("studentname",studentname);
                intent.putExtra("type",type);
                intent.putExtra("reportid",reportid);
                intent.putExtra("teachername",teachername);
                intent.putExtra("time",time);
                intent.putExtra("experts",experts);
                intent.putExtra("room",room);
                intent.putExtra("sendfrom",sendfrom);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        //绑定按钮
        t3_department=(TextView)findViewById(R.id.t3_institute);
        t3_major=(TextView)findViewById(R.id.t3_major);
        t3_studentname=(TextView)findViewById(R.id.t3_studentname);
        t3_teachername=(TextView)findViewById(R.id.t3_teachername) ;
        t3_type=(TextView)findViewById(R.id.t3_type);
        t3_room=(TextView)findViewById(R.id.t3_place);
        t3_experts=(TextView)findViewById(R.id.t3_experts);
        t3_time=(TextView)findViewById(R.id.t3_time);

        t3_startfill=(Button) findViewById(R.id.t3_startfill);
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
                DownloadUtil.get().download("http://117.121.38.95:9817/mobile/form/ktbg/download.ht?id="+reportid, sessionid,"download", new DownloadUtil.OnDownloadListener() {
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
            mLoadingDialog = new LoadingDialog(Activity_t3reportdetail.this, getString(R.string.loading), false);
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
