package p.sby.gs_qca.table1.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
        latenum=mRootView.findViewById(R.id.t1_latenumm);
        teachtheme=mRootView.findViewById(R.id.t1_teachtheme);
        classnum=mRootView.findViewById(R.id.t1_classnum);
        actualnum=mRootView.findViewById(R.id.t1_actualnum);
        shouldnum=mRootView.findViewById(R.id.t1_shouldnum);
        t1_predetail=mRootView.findViewById(R.id.t1_predetail);


        shouldnum.setText(((Activity_t1class)getActivity()).shouldnum);

        if(((Activity_t1class)getActivity()).option.equals("drafts")){
            shouldnum.setText(((Activity_t1class)getActivity()).shouldnum);
            latenum.setText(((Activity_t1class)getActivity()).latenum);
            actualnum.setText(((Activity_t1class)getActivity()).actualnum);
            teachtheme.setText(((Activity_t1class)getActivity()).teachtheme);
            classnum.setText(((Activity_t1class)getActivity()).classnum);
        }

        t1_predetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity_t1class)getActivity()).latenum=latenum.getText().toString();
                ((Activity_t1class)getActivity()).teachtheme=teachtheme.getText().toString();
                ((Activity_t1class)getActivity()).classnum=classnum.getText().toString();
                ((Activity_t1class)getActivity()).actualnum=actualnum.getText().toString();
                Toasty.info(getActivity(),"成功保存您所填写的课堂信息",Toasty.LENGTH_LONG).show();
            }
        });
        return mRootView;
    }



}
