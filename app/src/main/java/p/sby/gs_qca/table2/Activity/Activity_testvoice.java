package p.sby.gs_qca.table2.Activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import java.util.ArrayList;

import p.sby.gs_qca.R;


public class Activity_testvoice extends AppCompatActivity {

    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testvoice);

        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5c860000");
        SpeechRecognizer.createRecognizer(Activity_testvoice.this,null);
        button = (Button) findViewById(R.id.testvoice);
        editText = (EditText) findViewById(R.id.voicetext);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                initSpeech();
            }
        });
    }


    private void initSpeech(){
        //1、初始化窗口
        RecognizerDialog dialog=new RecognizerDialog(Activity_testvoice.this,null);
        //2、设置听写参数，详见官方文档
        //识别中文听写可设置为"zh_cn",此处为设置英文听写
        dialog.setParameter(SpeechConstant.LANGUAGE,"zh_cn");
        dialog.setParameter(SpeechConstant.ACCENT,"mandarin");
        //3、开始听写
        dialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                if(!b){
                    String result=parseVoice(recognizerResult.getResultString());
                    editText.append(result);
                    Toast.makeText(Activity_testvoice.this,result,Toast.LENGTH_SHORT).show();
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

