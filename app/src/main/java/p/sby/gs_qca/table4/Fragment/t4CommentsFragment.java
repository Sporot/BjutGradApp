package p.sby.gs_qca.table4.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import java.util.ArrayList;

import p.sby.gs_qca.R;
import p.sby.gs_qca.table1.Activity.Activity_t1class;
import p.sby.gs_qca.table1.Fragment.t1CommentsFragment;
import p.sby.gs_qca.table4.Activity.Activity_t4score;
import p.sby.gs_qca.table4.Activity.Activity_t4submit;

public class t4CommentsFragment extends Fragment {
    private View mRootView;
    private ImageView micro1;
    private ImageView micro2;
    private Button next;

    private EditText t4_comment1;
    private EditText t4_comment2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null){
            Log.e("666","显示专家评语");
            mRootView = inflater.inflate(R.layout.t4_commentfragment,container,false);

            micro1=mRootView.findViewById(R.id.t4_micro1);
            micro2=mRootView.findViewById(R.id.t4_micro2);
            next=mRootView.findViewById(R.id.t4_next);
            t4_comment1=mRootView.findViewById(R.id.t4_comment1);
            t4_comment2=mRootView.findViewById(R.id.t4_comment2);

            if(((Activity_t4score)getActivity()).sendfrom.equals("drafts")){
                t4_comment1.setText(((Activity_t4score)getActivity()).comment1);
                t4_comment2.setText(((Activity_t4score)getActivity()).comment2);
            }
            SpeechUtility.createUtility(getActivity(), SpeechConstant.APPID + "=5c860000");//初始化语音函数

            micro1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initSpeech(t4_comment1);
                }
            });

            micro2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initSpeech(t4_comment2);
                }
            });

            t4_comment1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus){
                        ((Activity_t4score)getActivity()).comment1=t4_comment1.getText().toString();
                    }
                    else{
                        ((Activity_t4score)getActivity()).comment1=t4_comment1.getText().toString();
                    }
                }
            });

            t4_comment2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus){
                        ((Activity_t4score)getActivity()).comment2=t4_comment2.getText().toString();
                    }
                    else{
                        ((Activity_t4score)getActivity()).comment2=t4_comment2.getText().toString();
                    }
                }
            });


            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity_t4score)getActivity()).comment1=t4_comment1.getText().toString();
                    ((Activity_t4score)getActivity()).comment2=t4_comment2.getText().toString();
                    Intent intent=new Intent(getActivity(),Activity_t4submit.class);
                    intent.putExtra("sendfrom",((Activity_t4score)getActivity()).sendfrom);
                    intent.putExtra("department",((Activity_t4score)getActivity()).department);
                    intent.putExtra("major",((Activity_t4score)getActivity()).major);
                    intent.putExtra("studentname",((Activity_t4score)getActivity()).studentname);
                    intent.putExtra("type",((Activity_t4score)getActivity()).type);
                    intent.putExtra("teachername",((Activity_t4score)getActivity()).teachername);
                    intent.putExtra("room",((Activity_t4score)getActivity()).room);
                    intent.putExtra("time",((Activity_t4score)getActivity()).time);
                    intent.putExtra("experts",((Activity_t4score)getActivity()).experts);

                    intent.putExtra("reportid",((Activity_t4score)getActivity()).reportid);
                    intent.putExtra("score1",((Activity_t4score)getActivity()).score1);
                    intent.putExtra("comment1",((Activity_t4score)getActivity()).comment1);
                    intent.putExtra("comment2",((Activity_t4score)getActivity()).comment2);

                    startActivity(intent);
                }
            });

        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null){
            parent.removeView(mRootView);
        }
        return mRootView;
    }




    /**************语音函数*************************/

    private void initSpeech(TextView t1){
        //1、初始化窗口
        RecognizerDialog dialog=new RecognizerDialog(getContext(),null);
        //2、设置听写参数，详见官方文档
        //识别中文听写可设置为"zh_cn",此处为设置听写
        dialog.setParameter(SpeechConstant.LANGUAGE,"zh_cn");
        dialog.setParameter(SpeechConstant.ACCENT,"mandarin");
        //3、开始听写
        dialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                if(!b){
                    String result=parseVoice(recognizerResult.getResultString());
                    t1.append(result);
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
        Gson gson =  new Gson();
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
