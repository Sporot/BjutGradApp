package p.sby.gs_qca.table5.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import java.util.ArrayList;

import p.sby.gs_qca.R;
import p.sby.gs_qca.table1.Fragment.t1CommentsFragment;
import p.sby.gs_qca.table5.Activity.Activity_t5score;
import p.sby.gs_qca.widget.NumRangeInputFilter100;
import p.sby.gs_qca.widget.NumRangeInputFilter5;

public class t5ScoreFragment extends Fragment {
    private View mRootView;
    private TextView totalScore;
    private EditText t5c_text;
    private ImageView t5c_mic;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            Log.e("666", "显示评分项目");
            mRootView = inflater.inflate(R.layout.t5_score, container, false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        t5c_mic =mRootView.findViewById(R.id.t5c_mic);
        t5c_text = mRootView.findViewById(R.id.t5c_text1);
        initView();
        setFilter();

        t5c_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSpeech();
            }//调用语音函数
        });

        t5c_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    ((Activity_t5score)getActivity()).comment1=t5c_text.getText().toString();
                }
                else{
                    ((Activity_t5score)getActivity()).comment1=t5c_text.getText().toString();
                }
            }
        });
        return mRootView;

    }

    private void setFilter() {
        totalScore.setFilters(new InputFilter[]{new NumRangeInputFilter100()});
    }

    private void initView() {
        totalScore = mRootView.findViewById(R.id.t5_totalscore);
    }

    /**************语音函数*************************/

    private void initSpeech() {
        //1、初始化窗口
        RecognizerDialog dialog = new RecognizerDialog(getContext(), null);
        //2、设置听写参数，详见官方文档
        //识别中文听写可设置为"zh_cn",此处为设置听写
        dialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        dialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        //3、开始听写
        dialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                if (!b) {
                    String result = parseVoice(recognizerResult.getResultString());
//                   t5c .append(result);
                    //Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(SpeechError speechError) {

            }
        });
        dialog.show();
    }

    //解析Gson对象
    public String parseVoice(String resultString) {
        Gson gson = new Gson();
        t1CommentsFragment.Voice voiceBean = gson.fromJson(resultString, t1CommentsFragment.Voice.class);

        StringBuffer sb = new StringBuffer();
        ArrayList<t1CommentsFragment.Voice.WSBean> ws = voiceBean.ws;
        for (t1CommentsFragment.Voice.WSBean wsBean : ws) {
            String word = wsBean.cw.get(0).w;
            sb.append(word);
        }
        return sb.toString();
    }

    /**
     * 语音对象封装
     */
    public class Voice {

        public ArrayList<t1CommentsFragment.Voice.WSBean> ws;

        public class WSBean {
            public ArrayList<t1CommentsFragment.Voice.CWBean> cw;
        }

        public class CWBean {
            public String w;
        }
    }

}



