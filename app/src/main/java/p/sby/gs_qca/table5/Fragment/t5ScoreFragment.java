package p.sby.gs_qca.table5.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
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
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import java.util.ArrayList;

import p.sby.gs_qca.R;
import p.sby.gs_qca.table1.Fragment.t1CommentsFragment;
import p.sby.gs_qca.table3.Activity.Activity_t3score;
import p.sby.gs_qca.table5.Activity.Activity_t5submit;
import p.sby.gs_qca.table5.Activity.Activity_t5score;
import p.sby.gs_qca.widget.NumRangeInputFilter100;

public class t5ScoreFragment extends Fragment {
    private View mRootView;
    public TextView totalScore;
    private EditText t5c_text;
    private EditText t5c_text2;
    private ImageView t5c_mic;
    private ImageView t5c_mic2;
    private Button t5_nextpage;

    private String institute="";
    private String major="";
    private String teacher="";
    private String student="";
    private String type="";
    private String year="";
    private String month="";
    private String day="";
    private String classroom="";
    private String expert="";
    private String reportid="";
    private String time="";


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

        initView();

        if(((Activity_t5score)getActivity()).sendfrom.equals("drafts")){
            totalScore.setText(((Activity_t5score)getActivity()).t5score);
            t5c_text.setText(((Activity_t5score)getActivity()).comment1);
            t5c_text2.setText(((Activity_t5score)getActivity()).comment2);
        }
        setFilter();

        onValue();

        t5c_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSpeech(t5c_text);
            }//调用语音函数
        });

        t5c_mic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSpeech(t5c_text2);
            }
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

        t5c_text2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    ((Activity_t5score)getActivity()).comment2=t5c_text2.getText().toString();
                }
                else{
                    ((Activity_t5score)getActivity()).comment2=t5c_text2.getText().toString();
                }
            }
        });

        t5_nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((Activity_t1class)getActivity()).comment=t1c_text.getText().toString();
                // System.out.println(t1c_text.getText().toString());
                setValue();
                Intent intent=new Intent(getActivity(),Activity_t5submit.class);
                intent.putExtra("sendfrom",(((Activity_t5score) getActivity()).sendfrom));
                intent.putExtra("institute",institute);
                intent.putExtra("major",major);
                intent.putExtra("id",((Activity_t5score)getActivity()).id);
                intent.putExtra("teacher",teacher);
                intent.putExtra("student",student);
                intent.putExtra("type",type);
                intent.putExtra("year",year);
                intent.putExtra("month",month);
                intent.putExtra("day",day);
                intent.putExtra("time",time);
                intent.putExtra("classroom",classroom);
                intent.putExtra("expert",expert);
                intent.putExtra("reportid",reportid);

                intent.putExtra("comment1",((Activity_t5score)getActivity()).comment1=t5c_text.getText().toString());
                intent.putExtra("comment2",((Activity_t5score)getActivity()).comment2=t5c_text2.getText().toString());
                intent.putExtra("score",((Activity_t5score)getActivity()).t5score=totalScore.getText().toString());

//                intent.putExtra("formid",formid);
//                intent.putExtra("option",option);


                startActivity(intent);


            }
        });

        return mRootView;

    }


    void onValue(){
        totalScore.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Log.i("score", "onFocus: t5score"+totalScore.getText().toString());

                    ((Activity_t5score)getActivity()).t5score=totalScore.getText().toString();
                }
                else {
                    Log.i("score", "outFouces:t11_score1 "+totalScore.getText().toString());
                    ((Activity_t5score)getActivity()).t5score=totalScore.getText().toString();
                }
            }
        });






    }

    private void setValue(){

        institute=((Activity_t5score)getActivity()).institute;
        major=((Activity_t5score)getActivity()).major;
        teacher=((Activity_t5score)getActivity()).teacher;
        student=((Activity_t5score)getActivity()).student;
        type=((Activity_t5score)getActivity()).type;
        year=((Activity_t5score)getActivity()).year;
        month=((Activity_t5score)getActivity()).month;
        day=((Activity_t5score)getActivity()).day;
        classroom=((Activity_t5score)getActivity()).classroom;
        expert=((Activity_t5score)getActivity()).expert;
        reportid=((Activity_t5score)getActivity()).reportid;
        time=((Activity_t5score)getActivity()).time;


    }

    private void setFilter() {
        totalScore.setFilters(new InputFilter[]{new NumRangeInputFilter100()});
    }

    private void initView() {
        totalScore = mRootView.findViewById(R.id.t5_totalscore);
        t5c_mic =mRootView.findViewById(R.id.t5c_mic);
        t5c_mic2=mRootView.findViewById(R.id.t5_mic2);
        t5c_text = mRootView.findViewById(R.id.t5c_text1);
        t5c_text2=mRootView.findViewById(R.id.t5c_text2);
        t5_nextpage=mRootView.findViewById(R.id.t5c_ButtonNext);

    }

    /**************语音函数*************************/

    private void initSpeech(TextView t5c) {
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
                    t5c .append(result);
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



