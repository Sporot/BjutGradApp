package p.sby.gs_qca.table2.Fragment;

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
import android.widget.Toast;

import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import p.sby.gs_qca.R;
import p.sby.gs_qca.table2.Activity.Activity_t2score;
import p.sby.gs_qca.table2.Activity.Activity_t2submit;

public class t2CommentsFragment extends Fragment {
    private View mRootView;
    private ImageView t2c_mic;
    private EditText t2c_text;
    private Button t2c_submit;
    private String institute="";
    private String coursename="";
    private String teacher="";
    private String classroom="";
    private String papernum="";
    private String courseid="";

    private String t2_score1="";
    private String t2_score2="";
    private String t2_score3="";
    private String t2_score4="";
    private String t2_score5="";
    private String t2_score6="";
    private String t2_score7="";
    private String t2_score8="";
    private String t2_comment="";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mRootView == null){
            Log.e("666","显示专家评语");
            mRootView = inflater.inflate(R.layout.t2_commentfragment,container,false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null){
            parent.removeView(mRootView);
        }

        SpeechUtility.createUtility(getActivity(), SpeechConstant.APPID + "=5c860000");


        t2c_mic=mRootView.findViewById(R.id.t2c_mic);
        t2c_text=mRootView.findViewById(R.id.t2c_text);
        t2c_submit=mRootView.findViewById(R.id.t2_submit);

        t2c_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSpeech();
            }
        });


        t2c_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    ((Activity_t2score)getActivity()).t2_comment=t2c_text.getText().toString();
                }
                else{
                    ((Activity_t2score)getActivity()).t2_comment=t2c_text.getText().toString();
                }
            }
        });


        t2c_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity_t2score)getActivity()).t2_comment=t2c_text.getText().toString();
                // System.out.println(t1c_text.getText().toString());
                setValue();
                Intent intent=new Intent(getActivity(),Activity_t2submit.class);
                intent.putExtra("institute",institute);
                intent.putExtra("coursename",coursename);
                intent.putExtra("teacher",teacher);
                intent.putExtra("classroom",classroom);
                intent.putExtra("papernum",papernum);
                intent.putExtra("courseid",courseid);


                intent.putExtra("score1",t2_score1);
                intent.putExtra("score2",t2_score2);
                intent.putExtra("score3",t2_score3);
                intent.putExtra("score4",t2_score4);
                intent.putExtra("score5",t2_score5);
                intent.putExtra("score6",t2_score6);
                intent.putExtra("score7",t2_score7);
                intent.putExtra("score8",t2_score8);
                intent.putExtra("comment",t2_comment);

                System.out.println(t2_score1);

                startActivity(intent);


            }
        });

        return mRootView;

    }

    private void setValue(){
        courseid=((Activity_t2score)getActivity()).courseid;
        institute=((Activity_t2score)getActivity()).institute;
        coursename=((Activity_t2score)getActivity()).coursename;
        teacher=((Activity_t2score)getActivity()).teacher;
        classroom=((Activity_t2score)getActivity()).classroom;
        papernum=((Activity_t2score)getActivity()).papernum;
        t2_comment= ((Activity_t2score)getActivity()).t2_comment;
        t2_score1=((Activity_t2score)getActivity()).t2_score1;
        t2_score2=((Activity_t2score)getActivity()).t2_score2;
        t2_score3=((Activity_t2score)getActivity()).t2_score3;
        t2_score4=((Activity_t2score)getActivity()).t2_score4;
        t2_score5=((Activity_t2score)getActivity()).t2_score5;
        t2_score6=((Activity_t2score)getActivity()).t2_score6;
        t2_score7=((Activity_t2score)getActivity()).t2_score7;
        t2_score8=((Activity_t2score)getActivity()).t2_score8;
    }



    private void initSpeech(){
        //1、初始化窗口
        RecognizerDialog dialog=new RecognizerDialog(getActivity(),null);
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
                    t2c_text.append(result);
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
        Voice voiceBean = gson.fromJson(resultString, Voice.class);

        StringBuffer sb = new StringBuffer();
        ArrayList<Voice.WSBean> ws = voiceBean.ws;
        for (Voice.WSBean wsBean : ws) {
            String word = wsBean.cw.get(0).w;
            sb.append(word);
        }
        return sb.toString();
    }

    /**
     * 语音对象封装
     */
    public class Voice {

        public ArrayList<WSBean> ws;

        public class WSBean {
            public ArrayList<CWBean> cw;
        }

        public class CWBean {
            public String w;
        }
    }

}
