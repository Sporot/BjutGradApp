package p.sby.gs_qca.table2.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import p.sby.gs_qca.Main.Activity.Activity_login;
import p.sby.gs_qca.R;
import p.sby.gs_qca.table2.Activity.Activity_testvoice;
import p.sby.gs_qca.widget.NumRangeInputFilter10;
import p.sby.gs_qca.widget.NumRangeInputFilter15;
import p.sby.gs_qca.widget.NumRangeInputFilter20;
import p.sby.gs_qca.widget.NumRangeInputFilter5;

public class t2ScoreFragment extends Fragment  {
    private View mRootView;
    private TextView t2_total;
    private EditText t2_score1;
    private EditText t2_score2;
    private EditText t2_score3;
    private EditText t2_score4;
    private EditText t2_score5;
    private EditText t2_score6;
    private EditText t2_score7;
    private EditText t2_score8;
    private EditText t2_score9;

    /********total变量*********/
    private float total1=0;
    private float total2=0;
    private float total3=0;
    private float total4=0;
    private float total5=0;
    private float total6=0;
    private float total7=0;
    private float total8=0;
    private float total9=0;
    private float total=0;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null){
            Log.e("666","显示评分项目");
            mRootView = inflater.inflate(R.layout.t2_scorefragment,container,false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null){
            parent.removeView(mRootView);
        }

        initView();

        setFilter();






        return mRootView;
    }
    /**
     * 限定用户输入分数大小
     */
    private void setFilter() {
        t2_score1.setFilters(new InputFilter[]{new NumRangeInputFilter15()});
        t2_score2.setFilters(new InputFilter[]{new NumRangeInputFilter10()});
        t2_score3.setFilters(new InputFilter[]{new NumRangeInputFilter20()});
        t2_score4.setFilters(new InputFilter[]{new NumRangeInputFilter10()});
        t2_score5.setFilters(new InputFilter[]{new NumRangeInputFilter10()});
        t2_score6.setFilters(new InputFilter[]{new NumRangeInputFilter15()});
        t2_score7.setFilters(new InputFilter[]{new NumRangeInputFilter10()});
        t2_score8.setFilters(new InputFilter[]{new NumRangeInputFilter10()});

    }

    private void initView() {
        t2_total=mRootView.findViewById(R.id.t1_scoretotal);
        t2_score1=mRootView.findViewById(R.id.t2_score1);
        t2_score2=mRootView.findViewById(R.id.t2_score2);
        t2_score3=mRootView.findViewById(R.id.t2_score3);
        t2_score4=mRootView.findViewById(R.id.t2_score4);
        t2_score5=mRootView.findViewById(R.id.t2_score5);
        t2_score6=mRootView.findViewById(R.id.t2_score6);
        t2_score7=mRootView.findViewById(R.id.t2_score7);
        t2_score8=mRootView.findViewById(R.id.t2_score8);

    }





}
