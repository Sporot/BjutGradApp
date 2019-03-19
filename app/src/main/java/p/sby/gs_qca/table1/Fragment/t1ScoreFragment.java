package p.sby.gs_qca.table1.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import es.dmoral.toasty.Toasty;
import p.sby.gs_qca.R;
import p.sby.gs_qca.table1.Activity.Activity_t1class;

public class t1ScoreFragment extends Fragment {
    private View mRootView;
    private EditText t1_score1;
    private EditText t1_score2;
    private EditText t1_score3;
    private EditText t1_score4;
    private EditText t1_score5;
    private EditText t1_score6;
    private EditText t1_score7;
    private EditText t1_score8;
    private EditText t1_score9;

    private Button t1_prescore;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null){
            Log.e("666","显示专家评语");
            mRootView = inflater.inflate(R.layout.t1scorefragment,container,false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null){
            parent.removeView(mRootView);
        }

        t1_score1=mRootView.findViewById(R.id.t1_score1);
        t1_score2=mRootView.findViewById(R.id.t1_score2);
        t1_score3=mRootView.findViewById(R.id.t1_score3);
        t1_score4=mRootView.findViewById(R.id.t1_score4);
        t1_score5=mRootView.findViewById(R.id.t1_score5);
        t1_score6=mRootView.findViewById(R.id.t1_score6);
        t1_score7=mRootView.findViewById(R.id.t1_score7);
        t1_score8=mRootView.findViewById(R.id.t1_score8);
        t1_score9=mRootView.findViewById(R.id.t1_score9);

        t1_prescore=mRootView.findViewById(R.id.t1_prescore);

        t1_prescore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity_t1class)getActivity()).t1_score1=t1_score1.getText().toString();
                ((Activity_t1class)getActivity()).t1_score2=t1_score2.getText().toString();
                ((Activity_t1class)getActivity()).t1_score3=t1_score3.getText().toString();
                ((Activity_t1class)getActivity()).t1_score4=t1_score4.getText().toString();
                ((Activity_t1class)getActivity()).t1_score5=t1_score5.getText().toString();
                ((Activity_t1class)getActivity()).t1_score6=t1_score6.getText().toString();
                ((Activity_t1class)getActivity()).t1_score7=t1_score7.getText().toString();
                ((Activity_t1class)getActivity()).t1_score8=t1_score8.getText().toString();
                ((Activity_t1class)getActivity()).t1_score9=t1_score9.getText().toString();
                Toasty.info(getActivity(),"成功将评分信息添加到预览！",Toasty.LENGTH_LONG).show();
            }
        });





        return mRootView;
    }
}
