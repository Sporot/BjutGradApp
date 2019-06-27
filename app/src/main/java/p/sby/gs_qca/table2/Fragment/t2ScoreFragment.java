package p.sby.gs_qca.table2.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
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
import p.sby.gs_qca.table2.Activity.Activity_t2score;
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


    /********total变量*********/
    private float total1=0;
    private float total2=0;
    private float total3=0;
    private float total4=0;
    private float total5=0;
    private float total6=0;
    private float total7=0;
    private float total8=0;

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

        onValue();

        setTotal();






        return mRootView;
    }

    private void onValue() {

        t2_score1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Log.i("score", "onFocus: t2_score1"+t2_score1.getText().toString());

                    ((Activity_t2score)getActivity()).t2_score1=t2_score1.getText().toString();
                }
                else {
                    Log.i("score", "outFouces:t11_score1 "+t2_score1.getText().toString());
                    ((Activity_t2score)getActivity()).t2_score1=t2_score1.getText().toString();
                }
            }
        });

        t2_score2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Log.i("score", "onFocus: t1_score2"+t2_score2.getText().toString());
                    ((Activity_t2score)getActivity()).t2_score2=t2_score2.getText().toString();
                }
                else {
                    Log.i("score", "outFocus: t1_score2"+t2_score2.getText().toString());
                    ((Activity_t2score)getActivity()).t2_score2=t2_score2.getText().toString();
                }
            }
        });

        t2_score3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Log.i("score", "onFocus: t1_score3"+t2_score3.getText().toString());
                    ((Activity_t2score)getActivity()).t2_score3=t2_score3.getText().toString();
                }
                else {
                    ((Activity_t2score)getActivity()).t2_score3=t2_score3.getText().toString();
                }
            }
        });

        t2_score4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Log.i("score", "onFocus: t1_score4"+t2_score4.getText().toString());
                    ((Activity_t2score)getActivity()).t2_score4=t2_score4.getText().toString();
                }
                else {
                    ((Activity_t2score)getActivity()).t2_score4=t2_score4.getText().toString();
                }
            }
        });

        t2_score5.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Log.i("score", "onFocus: t1_score5"+t2_score5.getText().toString());
                    ((Activity_t2score)getActivity()).t2_score5=t2_score5.getText().toString();
                }
                else {
                    ((Activity_t2score)getActivity()).t2_score5=t2_score5.getText().toString();
                }
            }
        });

        t2_score6.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Log.i("score", "onFocus: t1_score6"+t2_score6.getText().toString());
                    ((Activity_t2score)getActivity()).t2_score6=t2_score6.getText().toString();
                }
                else {
                    ((Activity_t2score)getActivity()).t2_score6=t2_score6.getText().toString();
                }
            }
        });

        t2_score7.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Log.i("score", "onFocus: t1_score7"+t2_score7.getText().toString());
                    ((Activity_t2score)getActivity()).t2_score7=t2_score7.getText().toString();
                }
                else {
                    ((Activity_t2score)getActivity()).t2_score7=t2_score7.getText().toString();
                }
            }
        });

        t2_score8.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Log.i("score", "onFocus: t1_score8"+t2_score8.getText().toString());
                    ((Activity_t2score)getActivity()).t2_score8=t2_score8.getText().toString();
                }
                else {
                    ((Activity_t2score)getActivity()).t2_score8=t2_score8.getText().toString();
                }
            }
        });


    }


    /**
     * 设置总分大小
     */
    private void setTotal() {

        t2_score1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString() != null && !s.toString().equals("") && !s.toString().equals("null")) {
                    total1 = Float.parseFloat(s.toString());
                }
                else {
                    total1=0;
                }
                total=total1+total2+total3+total4+total5+total6+total7+total8;
                t2_total.setText(String.valueOf(total));
                ((Activity_t2score)getActivity()).t2_total=t2_total.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        t2_score2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString() != null && !s.toString().equals("") && !s.toString().equals("null")) {
                    total2 = Float.parseFloat(s.toString());
                }
                else {
                    total2=0;
                }

                total=total1+total2+total3+total4+total5+total6+total7+total8;
                t2_total.setText(String.valueOf(total));
                ((Activity_t2score)getActivity()).t2_total=t2_total.getText().toString();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        t2_score3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString() != null && !s.toString().equals("") && !s.toString().equals("null")) {
                    total3 = Float.parseFloat(s.toString());
                }
                else {
                    total3=0;
                }
                total=total1+total2+total3+total4+total5+total6+total7+total8;
                t2_total.setText(String.valueOf(total));
                ((Activity_t2score)getActivity()).t2_total=t2_total.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        t2_score4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString() != null && !s.toString().equals("") && !s.toString().equals("null")) {
                    total4 = Float.parseFloat(s.toString());
                }
                else {
                    total4=0;
                }
                total=total1+total2+total3+total4+total5+total6+total7+total8;
                t2_total.setText(String.valueOf(total));
                ((Activity_t2score)getActivity()).t2_total=t2_total.getText().toString();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        t2_score5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString() != null && !s.toString().equals("") && !s.toString().equals("null")) {
                    total5 = Float.parseFloat(s.toString());
                }
                else {
                    total5=0;
                }
                total=total1+total2+total3+total4+total5+total6+total7+total8;
                t2_total.setText(String.valueOf(total));
                ((Activity_t2score)getActivity()).t2_total=t2_total.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        t2_score6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString() != null && !s.toString().equals("") && !s.toString().equals("null")) {
                    total6 = Float.parseFloat(s.toString());
                }
                else {
                    total6=0;
                }

                total=total1+total2+total3+total4+total5+total6+total7+total8;
                t2_total.setText(String.valueOf(total));
                ((Activity_t2score)getActivity()).t2_total=t2_total.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        t2_score7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString() != null && !s.toString().equals("") && !s.toString().equals("null")) {
                    total7 = Float.parseFloat(s.toString());
                }
                else {
                    total7=0;
                }
                total=total1+total2+total3+total4+total5+total6+total7+total8;
                t2_total.setText(String.valueOf(total));
                ((Activity_t2score)getActivity()).t2_total=t2_total.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        t2_score8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString() != null && !s.toString().equals("") && !s.toString().equals("null")) {
                    total8 = Float.parseFloat(s.toString());
                }
                else {
                    total8=0;
                }

                total=total1+total2+total3+total4+total5+total6+total7+total8;
                t2_total.setText(String.valueOf(total));
                ((Activity_t2score)getActivity()).t2_total=t2_total.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


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
        t2_score6.setFilters(new InputFilter[]{new NumRangeInputFilter10()});
        t2_score7.setFilters(new InputFilter[]{new NumRangeInputFilter15()});
        t2_score8.setFilters(new InputFilter[]{new NumRangeInputFilter10()});

    }



    private void initView() {
        t2_total=mRootView.findViewById(R.id.t2_socretotal);
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
