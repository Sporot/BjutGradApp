package p.sby.gs_qca.table2.Fragment;

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

public class t2CommentsFragment extends Fragment {
    private View mRootView;
    private ImageView t2c_mic;
    private EditText t2c_text;
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

        t2c_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSpeech();
            }
        });
        return mRootView;

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
