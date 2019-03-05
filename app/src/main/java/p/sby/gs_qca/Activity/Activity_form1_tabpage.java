package p.sby.gs_qca.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import p.sby.gs_qca.R;

public class Activity_form1_tabpage extends FragmentActivity implements OnClickListener {
    // 底部菜单4个Linearlayout
    private LinearLayout ll_detail;
    private LinearLayout ll_score;
    private LinearLayout ll_comment;


    // 底部菜单4个ImageView
    private ImageView iv_detail;
    private ImageView iv_score;
    private ImageView iv_comment;

    // 底部菜单4个菜单标题
    private TextView tv_detail;
    private TextView tv_score;
    private TextView tv_comment;

    // 4个Fragment
    private Fragment detailFragment;
    private Fragment scoreFragment;
    private Fragment commentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_tabmain);

        // 初始化控件
        initView();
        // 初始化底部按钮事件
        initEvent();
        // 初始化并设置当前Fragment
        initFragment(0);

    }

    private void initFragment(int index) {
        // 由于是引用了V4包下的Fragment，所以这里的管理器要用getSupportFragmentManager获取
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 开启事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 隐藏所有Fragment
        hideFragment(transaction);
        switch (index) {
            case 0:
                if (detailFragment == null) {
                    detailFragment = new form1_DetailFragment();
                    transaction.add(R.id.fl_content, detailFragment);
                } else {
                    transaction.show(detailFragment);
                }
                break;
            case 1:
                if (scoreFragment == null) {
                    scoreFragment = new form1_GradeFragment();
                    transaction.add(R.id.fl_content, scoreFragment);
                } else {
                    transaction.show(scoreFragment);
                }

                break;
            case 2:
                if (commentFragment == null) {
                    commentFragment = new form1_CommentFragment();
                    transaction.add(R.id.fl_content, commentFragment);
                } else {
                    transaction.show(commentFragment);
                }

                break;

            default:
                break;
        }

        // 提交事务
        transaction.commit();

    }

    //隐藏Fragment
    private void hideFragment(FragmentTransaction transaction) {
        if (detailFragment != null) {
            transaction.hide(detailFragment);
        }
        if (scoreFragment != null) {
            transaction.hide(scoreFragment);
        }
        if (commentFragment != null) {
            transaction.hide(commentFragment);
        }


    }

    private void initEvent() {
        // 设置按钮监听
        ll_detail.setOnClickListener(this);
        ll_score.setOnClickListener(this);
        ll_comment.setOnClickListener(this);


    }

    private void initView() {

        // 底部菜单4个Linearlayout
        this.ll_detail = (LinearLayout) findViewById(R.id.ll_detail);
        this.ll_score = (LinearLayout) findViewById(R.id.ll_score);
        this.ll_comment = (LinearLayout) findViewById(R.id.ll_comment);


        // 底部菜单4个ImageView
        this.iv_detail = (ImageView) findViewById(R.id.iv_detail);
        this.iv_score = (ImageView) findViewById(R.id.iv_score);
        this.iv_comment = (ImageView) findViewById(R.id.iv_comment);


        // 底部菜单4个菜单标题
        this.tv_detail = (TextView) findViewById(R.id.tv_detail);
        this.tv_score = (TextView) findViewById(R.id.tv_score);
        this.tv_comment = (TextView) findViewById(R.id.tv_comment);


    }

    @Override
    public void onClick(View v) {

        // 在每次点击后将所有的底部按钮(ImageView,TextView)颜色改为灰色，然后根据点击着色
        restartBotton();
        // ImageView和TetxView置为绿色，页面随之跳转
        switch (v.getId()) {
            case R.id.ll_detail:
                iv_comment.setImageResource(R.drawable.tb_score);
                tv_comment.setTextColor(0xff1B940A);
                initFragment(0);
                break;
            case R.id.ll_score:
                iv_score.setImageResource(R.drawable.tb_score);
                tv_score.setTextColor(0xff1B940A);
                initFragment(1);
                break;
            case R.id.ll_comment:
                iv_comment.setImageResource(R.drawable.tab_comments);
                tv_comment.setTextColor(0xff1B940A);
                initFragment(2);
                break;

            default:
                break;
        }

    }

    private void restartBotton() {
        // ImageView置为灰色
        iv_detail.setImageResource(R.drawable.tb_score);
        iv_score.setImageResource(R.drawable.tb_score);
        iv_comment.setImageResource(R.drawable.tab_comments);
        // TextView置为白色
        tv_detail.setTextColor(0xffffffff);
        tv_score.setTextColor(0xffffffff);
        tv_comment.setTextColor(0xffffffff);
//        tv_setting.setTextColor(0xffffffff);
    }

}
