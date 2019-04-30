package p.sby.gs_qca.table3.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import p.sby.gs_qca.R;
import p.sby.gs_qca.table3.Activity.Activity_t3score;
import p.sby.gs_qca.table4.Activity.Activity_t4score;
import p.sby.gs_qca.widget.NumRangeInputFilter100;

public class t3ScoreFragment extends Fragment {
    private View mRootView;
    private EditText t3_score1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null){
            Log.e("666","显示评分项目");
            mRootView = inflater.inflate(R.layout.t3_scorefragment,container,false);
            t3_score1=(EditText)mRootView.findViewById(R.id.t3_score1);
            t3_score1.setFilters(new InputFilter[]{new NumRangeInputFilter100()});

            if(((Activity_t3score)getActivity()).sendfrom.equals("drafts")){
                t3_score1.setText(((Activity_t3score)getActivity()).score1);
            }
            t3_score1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus){
                        Log.i("t4score", "onFocus: t1_score1"+t3_score1.getText().toString());

                        ((Activity_t3score)getActivity()).score1=t3_score1.getText().toString();
                    }
                    else {
                        Log.i("t4score", "outFouces:t11_score1 "+t3_score1.getText().toString());
                        ((Activity_t3score)getActivity()).score1=t3_score1.getText().toString();
                    }
                }
            });
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null){
            parent.removeView(mRootView);
        }
        return mRootView;
    }
}
