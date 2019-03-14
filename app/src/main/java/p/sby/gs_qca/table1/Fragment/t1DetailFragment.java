package p.sby.gs_qca.table1.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import p.sby.gs_qca.R;

public class t1DetailFragment extends  Fragment {
    private View mRootView;
    private TextView actualnum;
    private TextView teachtheme;

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
        actualnum=mRootView.findViewById(R.id.t1_actualnumm);
        teachtheme=mRootView.findViewById(R.id.t1_teachtheme);

        return mRootView;
    }
}
