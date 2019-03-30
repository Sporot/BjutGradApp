package p.sby.gs_qca.table1.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import p.sby.gs_qca.Main.Activity.Activity_list;
import p.sby.gs_qca.Main.Activity.global_variance;
import p.sby.gs_qca.R;
import p.sby.gs_qca.table1.Activity.Activity_t1class;
import p.sby.gs_qca.table1.Activity.Activity_t1preview;
import p.sby.gs_qca.widget.LoadingDialog;

import static android.content.ContentValues.TAG;

public class t1CommentsFragment extends Fragment{
    private View mRootView;
    private ImageView t1c_mic;
    private EditText t1c_text;
    private Button t1_submit;
    private Button t1c_save;
    private String result;
    private int flag=0;
    String sessionid;
    private LoadingDialog mLoadingDialog; //显示正在加载的对话框
    private String url="http://117.121.38.95:9817/mobile/form/jxzl/add.ht";
    /********表单需要提交的数据***********/
    private String comment="";
    private String option="";
    private String institute="";
    private String coursename="";
    private String otherinfo="";
    private String latenum="";
    private String teachtheme="";
    private String classnum="";
    private String teacher="";
    private String classroom="";
    private String time="";
    private String actualnum="";
    private String courseid="";
    private String shouldnum="";
    private String classid="";
    private String formid="";

    private String t1_score1="";
    private String t1_score2="";
    private String t1_score3="";
    private String t1_score4="";
    private String t1_score5="";
    private String t1_score6="";
    private String t1_score7="";
    private String t1_score8="";
    private String t1_score9="";

    private static final String TAG = "t1CommentsFragment";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null){
            Log.e("666","显示评分项目");
            mRootView = inflater.inflate(R.layout.t1commentfragment,container,false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null){
            parent.removeView(mRootView);
        }

        SpeechUtility.createUtility(getActivity(), SpeechConstant.APPID + "=5c860000");//初始化语音函数
        t1c_mic=mRootView.findViewById(R.id.t1c_mic);
        t1c_text=mRootView.findViewById(R.id.t1c_text);
        t1c_save=mRootView.findViewById(R.id.t1_save);

        if(((Activity_t1class)getActivity()).option.equals("drafts")){
            t1c_text.setText(((Activity_t1class)getActivity()).comment);
        }


        t1c_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSpeech();
            }//调用语音函数
        });

        t1c_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((Activity_t1class)getActivity()).comment=s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        t1c_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // System.out.println(t1c_text.getText().toString());
                setValue();
                Intent intent=new Intent(getActivity(),Activity_t1preview.class);
                intent.putExtra("institute",institute);
                intent.putExtra("formid",formid);
                intent.putExtra("option",option);
                intent.putExtra("coursename",coursename);
                intent.putExtra("comment",comment);
                intent.putExtra("latenum",latenum);
                intent.putExtra("teachtheme",teachtheme);
                intent.putExtra("classnum",classnum);
                intent.putExtra("actualnum",actualnum);
                intent.putExtra("shouldnum",shouldnum);
                intent.putExtra("teacher",teacher);
                intent.putExtra("classroom",classroom);
                intent.putExtra("time",time);
                intent.putExtra("courseid",courseid);
                intent.putExtra("classid",classid);

                intent.putExtra("score1",t1_score1);
                intent.putExtra("score2",t1_score2);
                intent.putExtra("score3",t1_score3);
                intent.putExtra("score4",t1_score4);
                intent.putExtra("score5",t1_score5);
                intent.putExtra("score6",t1_score6);
                intent.putExtra("score7",t1_score7);
                intent.putExtra("score8",t1_score8);
                intent.putExtra("score9",t1_score9);

                startActivity(intent);


            }
        });





        return mRootView;
    }


    private void setValue(){
        institute=((Activity_t1class)getActivity()).institute;
        formid=((Activity_t1class)getActivity()).formid;
        coursename=((Activity_t1class)getActivity()).coursename;
        teacher=((Activity_t1class)getActivity()).teacher;
        classroom=((Activity_t1class)getActivity()).classroom;
        time=((Activity_t1class)getActivity()).time;
        actualnum=((Activity_t1class)getActivity()).actualnum;
        shouldnum=((Activity_t1class)getActivity()).shouldnum;
        courseid=((Activity_t1class)getActivity()).courseid;
        classid=((Activity_t1class)getActivity()).classid;
        option=((Activity_t1class)getActivity()).option;
        latenum=((Activity_t1class)getActivity()).latenum;
        teachtheme=((Activity_t1class)getActivity()).teachtheme;
        classnum=((Activity_t1class)getActivity()).classnum;
        comment=  ((Activity_t1class)getActivity()).comment;
        t1_score1=((Activity_t1class)getActivity()).t1_score1;
        t1_score2=((Activity_t1class)getActivity()).t1_score2;
        t1_score3=((Activity_t1class)getActivity()).t1_score3;
        t1_score4=((Activity_t1class)getActivity()).t1_score4;
        t1_score5=((Activity_t1class)getActivity()).t1_score5;
        t1_score6=((Activity_t1class)getActivity()).t1_score6;
        t1_score7=((Activity_t1class)getActivity()).t1_score7;
        t1_score8=((Activity_t1class)getActivity()).t1_score8;
        t1_score9=((Activity_t1class)getActivity()).t1_score9;
    }


    /**************语音函数*************************/

    private void initSpeech(){
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
                    t1c_text.append(result);
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
