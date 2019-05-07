package p.sby.gs_qca.table2.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import p.sby.gs_qca.Main.Activity.Activity_login;
import p.sby.gs_qca.R;
import p.sby.gs_qca.table2.Activity.Activity_testvoice;

public class t2ScoreFragment extends Fragment  {
    private View mRootView;
//    public TextView t2_score8;


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





        return mRootView;
    }





}
