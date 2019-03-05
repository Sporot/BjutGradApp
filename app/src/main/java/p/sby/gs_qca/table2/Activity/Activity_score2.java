package p.sby.gs_qca.table2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import p.sby.gs_qca.Activity.Activity_list;
import p.sby.gs_qca.R;

public class Activity_score2 extends AppCompatActivity {
    private Button t2_confirm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t2score);

        /*****上方功能栏****/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_t2score); //主页上方功能条
        toolbar.setTitle("研究生考试试卷规范性评价表");

        toolbar.setTitleTextColor(getResources().getColor(R.color.white)); //设置标题颜色
        setSupportActionBar(toolbar);


    }


    /**
     *创建菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_list,menu); //通过getMenuInflater()方法得到MenuInflater对象，再调用它的inflate()方法就可以给当前活动创建菜单了，第一个参数：用于指定我们通过哪一个资源文件来创建菜单；第二个参数：用于指定我们的菜单项将添加到哪一个Menu对象当中。
        return true; // true：允许创建的菜单显示出来，false：创建的菜单将无法显示。
    }


    /**
     *菜单的点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.tb2_preview:
                Toast.makeText(this, "你点击了 预览按钮！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tb2_save:
                Toast.makeText(this, "你点击了 保存按钮！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tb2_quit:
                startActivity(new Intent(this,Activity_list.class));  //测试调用同一个menu,但写不同的代码会有有区别吗？
            default:
                break;
        }

        return true;
    }





}
