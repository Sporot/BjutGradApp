package p.sby.gs_qca.table;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.IFormat;
import com.bin.david.form.data.format.grid.BaseGridFormat;
import com.bin.david.form.data.format.selected.BaseSelectFormat;
import com.bin.david.form.data.table.FormTableData;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.utils.DensityUtils;

import es.dmoral.toasty.Toasty;
import p.sby.gs_qca.Activity.Activity_list;
import p.sby.gs_qca.Activity.Activity_login;
import p.sby.gs_qca.R;

public class Activity_table01 extends AppCompatActivity {

    private SmartTable<Form> table;
    private View llBottom;
    private Button searchBtn;
    private EditText editText;
    private Form selectForm;
    private TableConfig config;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table01);

        /***Toolbar相关设置***/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_tb1); //主页上方功能条
        toolbar.setTitle("研究生课堂教学质量评价表 ");

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



        /*****table设置******/
        table = (SmartTable<Form>) findViewById(R.id.table);
        int dp5 = DensityUtils.dp2px(this,10);
        table.getConfig().setVerticalPadding(dp5)
                .setTextLeftOffset(dp5);
        table.getConfig().setShowXSequence(false);
        table.getConfig().setShowYSequence(false);
        table.getConfig().setShowTableTitle(false);


        llBottom = findViewById(R.id.ll_bottom);

        searchBtn = (Button) findViewById(R.id.tv_query);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectForm !=null){
                    selectForm.setName(editText.getText().toString());
                    table.invalidate();
                    editText.setText("");
                }
            }
        });


        editText = (EditText)findViewById(R.id.edit_query) ;
        if(selectForm!=null){
            editText.setText(selectForm.getName());
        }

        Form[][] forms = {
                {
                        new Form("课程名称", Paint.Align.LEFT), new Form(3,1,""),
                        new Form("班级", Paint.Align.LEFT), new Form(2,1,""),
                        new Form("星期", Paint.Align.LEFT),new Form(1,1,""),
                        new Form("节次", Paint.Align.LEFT), new Form(1,1,""),
                },
                {
                        new Form("授课教师", Paint.Align.LEFT), new Form(3,1,""),
                        new Form("开课院系", Paint.Align.LEFT), new Form(3, 1, ""),
                        new Form("授课教师", Paint.Align.LEFT), new Form(2, 1, "")
                },
                {
                        new Form("应到人数", Paint.Align.LEFT), new Form(3,1,""),
                        new Form(1, 1, "迟到人数", Paint.Align.LEFT),new Form(3,1,""),
                        new Form(1, 1, "实到人数",Paint.Align.LEFT),  new Form(2, 1, "")
                },
                {
                        new Form(1, 1, "授课专题", Paint.Align.LEFT), new Form(10, 1, "")

                },



                {
                        new Form(1, 1, "项目"),
                        new Form(7,1,"分项指标"),
                        new Form(1,1,"分值"),
                        new Form(2,1,"得分（可有一位小数）")
                },

                {
                        new Form(1,1,"教学常规"),
                        new Form(7,1,"1.提前到课，按时上课、下课，严格课堂管理",Paint.Align.LEFT),
                        new Form(1,1,"5", Paint.Align.LEFT),new Form(2,1,"")

                },

                {
                        new Form(1,2,"教学思想"),
                        new Form(7,1,"2.治学严谨，教学观点正确，严格执行教学安排，言论健康，遵守课堂教学规范",Paint.Align.LEFT),
                        new Form(1,1,"10", Paint.Align.LEFT),new Form(2,1,"")

                },

                {
                        new Form(7,1,"3.强调研究型学习,注重学生质疑、发现、提出和分析解决问题等创新能力的培养",Paint.Align.LEFT),
                        new Form(1,1,"10", Paint.Align.LEFT),new Form(2,1,"")

                },
                {
                        new Form(1,2,"教学内容"),
                        new Form(7,1,"4.内容新颖，注重基础性与前沿性，融知识传授、能力培养和素质教育为一体",Paint.Align.LEFT),
                        new Form(1,1,"15", Paint.Align.LEFT),new Form(2,1,"")

                },

                {
                        new Form(7,1,"5.掌握学科国内外的研究动态，能及时把学科领域最新成果和经典案例引入教学过程",Paint.Align.LEFT),
                        new Form(1,1,"10", Paint.Align.LEFT),new Form(2,1,"")

                },

                {
                        new Form(1,2,"教学方法"),
                        new Form(7,1,"6.启发式、讨论式、案例式等多种讲授方式与文献研讨相结合，注重自学能力培养",Paint.Align.LEFT),
                        new Form(1,1,"15", Paint.Align.LEFT),new Form(2,1,"")

                },

                {
                        new Form(7,1,"7.理论联系实际，条理清楚、层次分明、重点突出，激发思维，注重师生互动",Paint.Align.LEFT),
                        new Form(1,1,"10", Paint.Align.LEFT),new Form(2,1,"")

                },

                {
                        new Form(1,1,"教学手段"),
                        new Form(7,1,"8.教学手段恰当，讲解生动，语言清晰，不照本宣科，文字规范，板书工整",Paint.Align.LEFT),
                        new Form(1,1,"10", Paint.Align.LEFT),new Form(2,1,"")

                },

                {
                        new Form(1,1,"教学效果"),
                        new Form(7,1,"9.充分激发学生的学习热情，学生参与度高，课堂气氛活跃，效果良好",Paint.Align.LEFT),
                        new Form(1,1,"15", Paint.Align.LEFT),new Form(2,1,"")

                },
                {
                        new Form(9,1,"综合得分"),
                        new Form(2,1,"")
                },
                {
                        new Form(1,2,"专家评语"),
                        new Form(10,1,""),
                },
                {
                        new Form(2, 1, "专家签字"), new Form(4, 1, "")
                        , new Form(2, 1, "日期"), new Form(3, 1, "")
                }



        };
        final FormTableData<Form> tableData = FormTableData.create(table, "", 11, forms);
        tableData.setFormat(new IFormat<Form>() {
            @Override
            public String format(Form form) {
                if (form != null) {
                    return form.getName();
                } else {
                    return "";
                }
            }
        });
        table.setSelectFormat(new BaseSelectFormat());
        tableData.setOnItemClickListener(new TableData.OnItemClickListener<Form>() {
            @Override
            public void onClick(Column column, String value, Form form, int col, int row) {
                if(form !=null){
                    selectForm = form;
                    editText.setFocusable(true);
                    editText.setFocusableInTouchMode(true);
                    editText.requestFocus();
                    editText.getText().toString();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
                    editText.setText(form.getName());
            }


        });

        table.setTableData(tableData);
        table.setZoom(true,100,0);




    }
}




