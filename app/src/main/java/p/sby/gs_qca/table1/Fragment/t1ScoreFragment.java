package p.sby.gs_qca.table1.Fragment;

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
import android.widget.TextView;

import es.dmoral.toasty.Toasty;
import p.sby.gs_qca.R;
import p.sby.gs_qca.table1.Activity.Activity_t1class;
import p.sby.gs_qca.widget.NumRangeInputFilter10;
import p.sby.gs_qca.widget.NumRangeInputFilter15;
import p.sby.gs_qca.widget.NumRangeInputFilter20;
import p.sby.gs_qca.widget.NumRangeInputFilter5;

public class t1ScoreFragment extends Fragment {
    private View mRootView;
    private TextView t1_total;
    private EditText t1_score1;
    private EditText t1_score2;
    private EditText t1_score3;
    private EditText t1_score4;
    private EditText t1_score5;
    private EditText t1_score6;
    private EditText t1_score7;
    private EditText t1_score8;
    private EditText t1_score9;
    private EditText t1_score10;

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
    private float total10=0;
    private float total=0;

    private Button t1_prescore;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null){
            Log.e("666","显示评分项目");
            mRootView = inflater.inflate(R.layout.t1scorefragment,container,false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null){
            parent.removeView(mRootView);
        }

        initView();

        setFilter();

        onValue();

        setTotal();



        if(((Activity_t1class)getActivity()).option.equals("drafts")){
            t1_score1.setText(((Activity_t1class)getActivity()).t1_score1);
            t1_score2.setText(((Activity_t1class)getActivity()).t1_score2);
            t1_score3.setText(((Activity_t1class)getActivity()).t1_score3);
            t1_score4.setText(((Activity_t1class)getActivity()).t1_score4);
            t1_score5.setText(((Activity_t1class)getActivity()).t1_score5);
            t1_score6.setText(((Activity_t1class)getActivity()).t1_score6);
            t1_score7.setText(((Activity_t1class)getActivity()).t1_score7);
            t1_score8.setText(((Activity_t1class)getActivity()).t1_score8);
            t1_score9.setText(((Activity_t1class)getActivity()).t1_score9);
            t1_score10.setText(((Activity_t1class)getActivity()).t1_score10);

        }

        return mRootView;
    }

    private void onValue() {

        t1_score1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Log.i("score", "onFocus: t1_score1"+t1_score1.getText().toString());

                    ((Activity_t1class)getActivity()).t1_score1=t1_score1.getText().toString();
                }
                else {
                    Log.i("score", "outFouces:t11_score1 "+t1_score1.getText().toString());
                    ((Activity_t1class)getActivity()).t1_score1=t1_score1.getText().toString();
                }
            }
        });

        t1_score2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Log.i("score", "onFocus: t1_score2"+t1_score2.getText().toString());
                    ((Activity_t1class)getActivity()).t1_score2=t1_score2.getText().toString();
                }
                else {
                    Log.i("score", "outFocus: t1_score2"+t1_score2.getText().toString());
                    ((Activity_t1class)getActivity()).t1_score2=t1_score2.getText().toString();
                }
            }
        });

        t1_score3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Log.i("score", "onFocus: t1_score3"+t1_score3.getText().toString());
                    ((Activity_t1class)getActivity()).t1_score3=t1_score3.getText().toString();
                }
                else {
                    ((Activity_t1class)getActivity()).t1_score3=t1_score3.getText().toString();
                }
            }
        });

        t1_score4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Log.i("score", "onFocus: t1_score4"+t1_score4.getText().toString());
                    ((Activity_t1class)getActivity()).t1_score4=t1_score4.getText().toString();
                }
                else {
                    ((Activity_t1class)getActivity()).t1_score4=t1_score4.getText().toString();
                }
            }
        });

        t1_score5.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Log.i("score", "onFocus: t1_score5"+t1_score5.getText().toString());
                    ((Activity_t1class)getActivity()).t1_score5=t1_score5.getText().toString();
                }
                else {
                    ((Activity_t1class)getActivity()).t1_score5=t1_score5.getText().toString();
                }
            }
        });

        t1_score6.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Log.i("score", "onFocus: t1_score6"+t1_score6.getText().toString());
                    ((Activity_t1class)getActivity()).t1_score6=t1_score6.getText().toString();
                }
                else {
                    ((Activity_t1class)getActivity()).t1_score6=t1_score6.getText().toString();
                }
            }
        });

        t1_score7.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Log.i("score", "onFocus: t1_score7"+t1_score7.getText().toString());
                    ((Activity_t1class)getActivity()).t1_score7=t1_score7.getText().toString();
                }
                else {
                    ((Activity_t1class)getActivity()).t1_score7=t1_score7.getText().toString();
                }
            }
        });

        t1_score8.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Log.i("score", "onFocus: t1_score8"+t1_score8.getText().toString());
                    ((Activity_t1class)getActivity()).t1_score8=t1_score8.getText().toString();
                }
                else {
                    ((Activity_t1class)getActivity()).t1_score8=t1_score8.getText().toString();
                }
            }
        });

        t1_score9.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Log.i("score", "onFocus: t1_score9"+t1_score9.getText().toString());
                    ((Activity_t1class)getActivity()).t1_score9=t1_score9.getText().toString();
                }
                else {
                    ((Activity_t1class)getActivity()).t1_score9=t1_score9.getText().toString();
                }
            }
        });

        t1_score10.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Log.i("score", "onFocus: t1_score10"+t1_score10.getText().toString());
                    ((Activity_t1class)getActivity()).t1_score10=t1_score10.getText().toString();
                }
                else {
                    ((Activity_t1class)getActivity()).t1_score10=t1_score10.getText().toString();
                }
            }
        });
//        ((Activity_t1class)getActivity()).t1_total=t1_total.getText().toString();
    }

    /**
     * 设置总分大小
     */
    private void setTotal() {

        t1_score1.addTextChangedListener(new TextWatcher() {
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
                total=total1+total2+total3+total4+total5+total6+total7+total8+total9+total10;
                t1_total.setText(String.valueOf(total));
                ((Activity_t1class)getActivity()).t1_total=t1_total.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        t1_score2.addTextChangedListener(new TextWatcher() {
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

                total=total1+total2+total3+total4+total5+total6+total7+total8+total9+total10;
                t1_total.setText(String.valueOf(total));
                ((Activity_t1class)getActivity()).t1_total=t1_total.getText().toString();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        t1_score3.addTextChangedListener(new TextWatcher() {
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
                total=total1+total2+total3+total4+total5+total6+total7+total8+total9+total10;
                t1_total.setText(String.valueOf(total));
                ((Activity_t1class)getActivity()).t1_total=t1_total.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        t1_score4.addTextChangedListener(new TextWatcher() {
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
                total=total1+total2+total3+total4+total5+total6+total7+total8+total9+total10;
                t1_total.setText(String.valueOf(total));
                ((Activity_t1class)getActivity()).t1_total=t1_total.getText().toString();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        t1_score5.addTextChangedListener(new TextWatcher() {
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
                total=total1+total2+total3+total4+total5+total6+total7+total8+total9+total10;
                t1_total.setText(String.valueOf(total));
                ((Activity_t1class)getActivity()).t1_total=t1_total.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        t1_score6.addTextChangedListener(new TextWatcher() {
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

                total=total1+total2+total3+total4+total5+total6+total7+total8+total9+total10;
                t1_total.setText(String.valueOf(total));
                ((Activity_t1class)getActivity()).t1_total=t1_total.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        t1_score7.addTextChangedListener(new TextWatcher() {
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
                total=total1+total2+total3+total4+total5+total6+total7+total8+total9+total10;
                t1_total.setText(String.valueOf(total));
                ((Activity_t1class)getActivity()).t1_total=t1_total.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        t1_score8.addTextChangedListener(new TextWatcher() {
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

                total=total1+total2+total3+total4+total5+total6+total7+total8+total9+total10;
                t1_total.setText(String.valueOf(total));
                ((Activity_t1class)getActivity()).t1_total=t1_total.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        t1_score9.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString() != null && !s.toString().equals("") && !s.toString().equals("null")) {
                    total9 = Float.parseFloat(s.toString());
                }
                else {
                    total9=0;
                }
                System.out.println(s.toString().length());
                total=total1+total2+total3+total4+total5+total6+total7+total8+total9+total10;
                t1_total.setText(String.valueOf(total));
                ((Activity_t1class)getActivity()).t1_total=t1_total.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        t1_score10.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString() != null && !s.toString().equals("") && !s.toString().equals("null")) {
                    total10 = Float.parseFloat(s.toString());
                }
                else {
                    total10=0;
                }
                System.out.println(s.toString().length());
                total=total1+total2+total3+total4+total5+total6+total7+total8+total9+total10;
                t1_total.setText(String.valueOf(total));
                ((Activity_t1class)getActivity()).t1_total=t1_total.getText().toString();
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
        t1_score1.setFilters(new InputFilter[]{new NumRangeInputFilter10()});
        t1_score2.setFilters(new InputFilter[]{new NumRangeInputFilter10()});
        t1_score3.setFilters(new InputFilter[]{new NumRangeInputFilter10()});
        t1_score4.setFilters(new InputFilter[]{new NumRangeInputFilter10()});
        t1_score5.setFilters(new InputFilter[]{new NumRangeInputFilter10()});
        t1_score6.setFilters(new InputFilter[]{new NumRangeInputFilter10()});
        t1_score7.setFilters(new InputFilter[]{new NumRangeInputFilter10()});
        t1_score8.setFilters(new InputFilter[]{new NumRangeInputFilter10()});
        t1_score9.setFilters(new InputFilter[]{new NumRangeInputFilter10()});
        t1_score10.setFilters(new InputFilter[]{new NumRangeInputFilter10()});
    }

    /**
     * 绑定控件
     */
    private void initView() {
        t1_total=mRootView.findViewById(R.id.t1_scoretotal);
        t1_score1=mRootView.findViewById(R.id.t1_score1);
        t1_score2=mRootView.findViewById(R.id.t1_score2);
        t1_score3=mRootView.findViewById(R.id.t1_score3);
        t1_score4=mRootView.findViewById(R.id.t1_score4);
        t1_score5=mRootView.findViewById(R.id.t1_score5);
        t1_score6=mRootView.findViewById(R.id.t1_score6);
        t1_score7=mRootView.findViewById(R.id.t1_score7);
        t1_score8=mRootView.findViewById(R.id.t1_score8);
        t1_score9=mRootView.findViewById(R.id.t1_score9);
        t1_score10=mRootView.findViewById(R.id.t1_score10);
    }


}
