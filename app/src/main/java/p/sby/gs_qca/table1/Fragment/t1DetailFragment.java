package p.sby.gs_qca.table1.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import es.dmoral.toasty.Toasty;
import p.sby.gs_qca.R;
import p.sby.gs_qca.table1.Activity.Activity_t1class;

import static android.content.ContentValues.TAG;

public class t1DetailFragment extends  Fragment {
    private View mRootView;
    private TextView latenum;
    private TextView teachtheme;
    private TextView shouldnum;
    private TextView actualnum;
    private TextView classnum;
    private Button t1_predetail;
    private LinearLayout bg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null){
            Log.e("666","显示详细信息");
            mRootView = inflater.inflate(R.layout.t1detailfragment,container,false);
            initView();
            shouldnum.setText(((Activity_t1class)getActivity()).shouldnum);

            if(((Activity_t1class)getActivity()).option.equals("drafts")){
                shouldnum.setText(((Activity_t1class)getActivity()).shouldnum);
                latenum.setText(((Activity_t1class)getActivity()).latenum);
                actualnum.setText(((Activity_t1class)getActivity()).actualnum);
                teachtheme.setText(((Activity_t1class)getActivity()).teachtheme);
                classnum.setText(((Activity_t1class)getActivity()).classnum);
            }


            setValue();

            //  setContent();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null){
            parent.removeView(mRootView);
        }


//        t1_predetail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((Activity_t1class)getActivity()).latenum=latenum.getText().toString();
//                ((Activity_t1class)getActivity()).teachtheme=teachtheme.getText().toString();
//                ((Activity_t1class)getActivity()).classnum=classnum.getText().toString();
//                ((Activity_t1class)getActivity()).actualnum=actualnum.getText().toString();
//                Toasty.info(getActivity(),"成功保存您所填写的课堂信息",Toasty.LENGTH_LONG).show();
//            }
//        });
        return mRootView;
    }

    private void setValue() {
        latenum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    Log.e("666", "onFocusChange: "+latenum.getText().toString());
                    ((Activity_t1class)getActivity()).latenum=latenum.getText().toString();
                }
                else
                {
                    ((Activity_t1class)getActivity()).latenum=latenum.getText().toString();
                }
            }
        });

        actualnum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Log.e("666", "onFocusChange: "+actualnum.getText().toString());
                    ((Activity_t1class)getActivity()).actualnum=actualnum.getText().toString();
                }
                else
                {
                    ((Activity_t1class)getActivity()).actualnum=actualnum.getText().toString();
                }
            }
        });

        teachtheme.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    Log.e("666", "onFocusChange: "+teachtheme.getText().toString());
                    ((Activity_t1class)getActivity()).teachtheme=teachtheme.getText().toString();
                }
                else
                {
                    ((Activity_t1class)getActivity()).teachtheme=teachtheme.getText().toString();
                }
            }
        });

        classnum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    Log.e("666", "onFocusChange: "+classnum.getText().toString());
                    ((Activity_t1class)getActivity()).classnum=classnum.getText().toString();
                }
                else
                {
                    ((Activity_t1class)getActivity()).classnum=classnum.getText().toString();
                }
            }
        });
    }

    /**
     * 监听本文本框变化，传递值
     */
    private void setContent() {
        actualnum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("") && !(((Activity_t1class)getActivity()).actualnum.equals("")))
                {
                    ((Activity_t1class)getActivity()).actualnum=((Activity_t1class)getActivity()).actualnum;
                }
                else{
                    ((Activity_t1class)getActivity()).actualnum=s.toString();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });



        latenum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(s.toString().equals("") && ((Activity_t1class)getActivity()).latenum.length()>1)
//                {
//                    System.out.println("*********进来没**************真实值"+((Activity_t1class)getActivity()).latenum.length());
//                    System.out.println("*********进来没**************填写值"+s.toString().length());
//                    ((Activity_t1class)getActivity()).latenum=((Activity_t1class)getActivity()).latenum;
//                }
//               else if(s.toString().equals("") && ((Activity_t1class)getActivity()).latenum.length()==1){
//                    System.out.println("********看看有没有进来*******"+latenum.getText().toString());
//                    ((Activity_t1class)getActivity()).latenum=s.toString();
//                }
//                else{
//                    ((Activity_t1class)getActivity()).latenum=s.toString();
//                }
                ((Activity_t1class)getActivity()).latenum=s.toString();
                Log.e("666", "onTextChanged: "+"是不是又加载了一遍");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        teachtheme.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("") && !(((Activity_t1class)getActivity()).teachtheme.equals("")))
                {
                    ((Activity_t1class)getActivity()).teachtheme=((Activity_t1class)getActivity()).teachtheme;
                }
                else{
                    ((Activity_t1class)getActivity()).teachtheme=s.toString();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        classnum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("") && !(((Activity_t1class)getActivity()).classnum.equals("")))
                {
                    ((Activity_t1class)getActivity()).classnum=((Activity_t1class)getActivity()).classnum;
                }
                else{
                    ((Activity_t1class)getActivity()).classnum=s.toString();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initView() {
        Log.e("666", "initView: 看看这个运行不运行" );
        latenum=mRootView.findViewById(R.id.t1_latenumm);
        teachtheme=mRootView.findViewById(R.id.t1_teachtheme);
        classnum=mRootView.findViewById(R.id.t1_classnum);
        actualnum=mRootView.findViewById(R.id.t1_actualnum);
        shouldnum=mRootView.findViewById(R.id.t1_shouldnum);
       // t1_predetail=mRootView.findViewById(R.id.t1_predetail);
    }

//
//    public void onResume() {
//
//        super.onResume();
//        System.out.println("是不是又一次运行了Resume");
//                  ((Activity_t1class)getActivity()).latenum=latenum.getText().toString();
//                ((Activity_t1class)getActivity()).teachtheme=teachtheme.getText().toString();
//                ((Activity_t1class)getActivity()).classnum=classnum.getText().toString();
//                ((Activity_t1class)getActivity()).actualnum=actualnum.getText().toString();
//    }


}
