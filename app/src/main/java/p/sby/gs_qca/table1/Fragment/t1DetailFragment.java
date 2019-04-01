package p.sby.gs_qca.table1.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import es.dmoral.toasty.Toasty;
import p.sby.gs_qca.R;
import p.sby.gs_qca.table1.Activity.Activity_t1class;

public class t1DetailFragment extends  Fragment {
    private View mRootView;
    private TextView latenum;
    private TextView teachtheme;
    private TextView shouldnum;
    private TextView actualnum;
    private TextView classnum;
    private Button t1_predetail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null){
            Log.e("666","显示专家评语");
            mRootView = inflater.inflate(R.layout.t1detailfragment,container,false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null){
            parent.removeView(mRootView);
        }

        initView();


        shouldnum.setText(((Activity_t1class)getActivity()).shouldnum);

        if(((Activity_t1class)getActivity()).option.equals("drafts")){
            shouldnum.setText(((Activity_t1class)getActivity()).shouldnum);
            latenum.setText(((Activity_t1class)getActivity()).latenum);
            actualnum.setText(((Activity_t1class)getActivity()).actualnum);
            teachtheme.setText(((Activity_t1class)getActivity()).teachtheme);
            classnum.setText(((Activity_t1class)getActivity()).classnum);
        }

        setContent();

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
                ((Activity_t1class)getActivity()).actualnum=s.toString();
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
                ((Activity_t1class)getActivity()).latenum=s.toString();
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
                ((Activity_t1class)getActivity()).teachtheme=s.toString();
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
                ((Activity_t1class)getActivity()).classnum=s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initView() {
        latenum=mRootView.findViewById(R.id.t1_latenumm);
        teachtheme=mRootView.findViewById(R.id.t1_teachtheme);
        classnum=mRootView.findViewById(R.id.t1_classnum);
        actualnum=mRootView.findViewById(R.id.t1_actualnum);
        shouldnum=mRootView.findViewById(R.id.t1_shouldnum);
       // t1_predetail=mRootView.findViewById(R.id.t1_predetail);
    }


}
