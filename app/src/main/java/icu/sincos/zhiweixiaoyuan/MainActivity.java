package icu.sincos.zhiweixiaoyuan;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.net.URLEncoder;
import java.util.Map;

import icu.sincos.zhiweixiaoyuan.util.OkHttpUtils;

public class MainActivity extends AppCompatActivity {


    String wxOa = "orT8vuPhqrSfM5ylXZWLdLMzwPBU";
    String getNoAuth = "https://wx.ivxiaoyuan.com/sc/h5/officialAccountLoginManage/getUserRolesNoAuth?ciphertext=";
    String getBalance = "https://wx.ivxiaoyuan.com/sc/consume/h5/query/getMemberBalance?objectUuid=97c0098d3a4e44069c6ca7f3df94d231&t=1694329190049";

    private TextView time_textview;
    private TextView left_textview;
    private TextView name_textView;
    private TextView balance_textview;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(runnable).start();





        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String currentTime = dateFormat.format(new Date());

        time_textview = findViewById(R.id.time_textview);
        left_textview = findViewById(R.id.left_textview);
        name_textView = findViewById(R.id.name_textView);
        balance_textview = findViewById(R.id.balance_textView);

        left_textview.setText("智慧校园");
        time_textview.setText(currentTime);
        name_textView.setText("少女祈祷中...");
        balance_textview.setText("Now loading...");

        name_textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        balance_textview.setMovementMethod(ScrollingMovementMethod.getInstance());

        try {

        } catch (Exception e) {

            Log.e("Activity", String.valueOf(e));
            //name_textView.setText(exceptionAsString);
        }




    }


    Runnable runnable = new Runnable() {

        @SuppressLint("SetTextI18n")
        @Override
        public void run() {


            String url ;
            String cipherText ;
            String result ;
            String url_Balance ;
            String result_Balance;
            String result_headers;
            try {
                cipherText = icu.sincos.zhiweixiaoyuan.util.CiperTextUtil.encrypt(wxOa);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            //Aes加密后参数


            try {
                url = getNoAuth + URLEncoder.encode(cipherText, "utf-8");
                url_Balance = URLEncoder.encode(getBalance, "utf-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            try {
                String encodedUrl = URLEncoder.encode(url, "utf-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            //拼接链接

            try {
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:52.0) Gecko/20100101 Firefox/52.0");
                result = String.valueOf(icu.sincos.zhiweixiaoyuan.util.OkHttpUtils.get(url, headerMap));
                //result_headers =  String.valueOf(icu.sincos.zhiweixiaoyuan.util.OkHttpUtils.getHeaders(url, headerMap));
                //result_Balance = String.valueOf(icu.sincos.zhiweixiaoyuan.util.OkHttpUtils.get("https://wx.ivxiaoyuan.com/sc/consume/h5/query/getMemberBalance?objectUuid=97c0098d3a4e44069c6ca7f3df94d231&t=1694329190049", result_headers));
            } catch (Exception e) {
                e.printStackTrace();
                // 抛出自定义异常或返回一个默认值，具体取决于您的应用需求
                throw new RuntimeException("Failed to execute balance request: " + e.getMessage());
            }


            name_textView.setText(String.valueOf(result));
            //balance_textview.setText(String.valueOf("余额：" + result_Balance));

        }
    };

}


