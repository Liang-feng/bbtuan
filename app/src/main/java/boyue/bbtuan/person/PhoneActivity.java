package boyue.bbtuan.person;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import boyue.bbtuan.GuideActivity;
import boyue.bbtuan.R;
import boyue.bbtuan.bean.ResultBean;
import boyue.bbtuan.bean.USER;
import boyue.bbtuan.utils.AppUtils;
import boyue.bbtuan.utils.HttpConstants;
import boyue.bbtuan.view.SendValidateButton;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static cn.smssdk.framework.utils.R.getStringRes;
public class PhoneActivity extends Activity implements View.OnClickListener{
    private Button phonebBackButt,phoneNumButt;
    private SendValidateButton phoneGetButt;
    private RelativeLayout phonePwdRelLay;
    private View line1,line2;
    private EditText phoneAccountEdit,phoneNumEdit;
    // 填写从短信SDK应用后台注册得到的APPKEY
    private static String APPKEY = "ab44622b1c70";
    // 填写从短信SDK应用后台注册得到的APPSECRET
    private static String APPSECRET = "80b5f362fb9346f9598e5df785480668";
    public String phString;
    private Dialog loadbar = null;
    private TextView topBtntextTv,phonePwdTv;
    private String phonePwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_phone);
        topBtntextTv=(TextView)this.findViewById(R.id.tv_top_btntext);
        if (USER.isForgetPwd==false){
            topBtntextTv.setText("手机验证");
        }else {
            topBtntextTv.setText("密码找回");
        }
        phonebBackButt=(Button)this.findViewById(R.id.btn_top_back);
        phoneGetButt=(SendValidateButton)this.findViewById(R.id.btn_phone_get);
        phoneNumButt=(Button)this.findViewById(R.id.btn_phone_num);
        phoneAccountEdit=(EditText)this.findViewById(R.id.edit_phone_account);
        phoneNumEdit=(EditText)this.findViewById(R.id.edit_phone_num);
        phonebBackButt.setOnClickListener(this);
        phoneGetButt.setOnClickListener(this);
        phoneNumButt.setOnClickListener(this);
        phonePwdTv=(TextView)this.findViewById(R.id.tv_phone_pwd);
        phonePwdRelLay=(RelativeLayout)this.findViewById(R.id.relLay_phone_pwd);
        line1=(View)this.findViewById(R.id.line1);
        line2=(View)this.findViewById(R.id.line2);
        if (USER.isForgetPwd==true){
            line1.setVisibility(View.VISIBLE);
            line2.setVisibility(View.VISIBLE);
            phonePwdRelLay.setVisibility(View.VISIBLE);

        }else {
            line1.setVisibility(View.GONE);
            line2.setVisibility(View.GONE);
            phonePwdRelLay.setVisibility(View.GONE);
        }
        SMSSDK.initSDK(this, APPKEY, APPSECRET);
        EventHandler eh=new EventHandler(){

            @Override
            public void afterEvent(int event, int result, Object data) {

                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }

        };
        SMSSDK.registerEventHandler(eh);
        phoneAccountEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 11) {
                    initProgressDialog();
                    List<NameValuePair> allP=new ArrayList<NameValuePair>();
                    allP.add(new BasicNameValuePair("phoneVerifyNum", phoneAccountEdit.getText().toString().trim()));
                    allP.add(new BasicNameValuePair("phoneVerify", "1"));
                    RequestParams params=new RequestParams();
                    params.addBodyParameter(allP);
                    HttpUtils httpUtils=new HttpUtils();
                    httpUtils.send(HttpRequest.HttpMethod.POST, HttpConstants.HTTP_REGISTER, params,
                            new RequestCallBack<String>() {
                                @Override
                                public void onStart() {
                                    super.onStart();
                                }

                                @Override
                                public void onSuccess(ResponseInfo<String> responseInfo) {
                                    String returnString = responseInfo.result.toString();
                                    try {
                                        JSONObject jsonObject = new JSONObject(returnString);
                                        ResultBean b = JSON.parseObject(jsonObject.getString("result"), ResultBean.class);
                                        if (USER.isForgetPwd==true){
                                            if (b.getData().equals("14")) {
                                                showToast("该手机号尚未注册!");
                                                phoneAccountEdit.setText("");
                                                close();
                                            }
                                            if (!(b.getData().equals("14"))) {
                                                phonePwd=b.getData();
                                                close();
                                            }
                                        }else{
                                            if (b.getData().equals("14")) {
                                                close();
                                            }
                                            if (!b.getData().equals("14")) {
                                                showToast("手机号已注册!");
                                                phoneAccountEdit.setText("");
                                                close();
                                            }
                                        }
                                    } catch (Exception e) {
                                    }
                                }

                                @Override
                                public void onFailure(HttpException e, String s) {

                                }
                            });
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_top_back:
                if (USER.isForgetPwd==true){
                    USER.isForgetPwd=false;
                    finish();
                }else {
                    startActivity(new Intent(PhoneActivity.this, GuideActivity.class));
                    finish();
                }
                break;
            case R.id.btn_phone_get://获取验证码
                if(!TextUtils.isEmpty(phoneAccountEdit.getText().toString().trim())){
                    phoneGetButt.startTickWork();
                    SMSSDK.getVerificationCode("86",phoneAccountEdit.getText().toString());
                    phString=phoneAccountEdit.getText().toString();
                }else {
                    if (TextUtils.isEmpty(phoneAccountEdit.getText().toString().trim())){
                        Toast.makeText(this, "电话不能为空", Toast.LENGTH_LONG).show();
                    }else if (!AppUtils.isMobileNO(phoneAccountEdit.getText().toString().trim())) {
                        Toast.makeText(this, "电话号码格式错误", Toast.LENGTH_LONG).show();
                        phoneAccountEdit.setFocusable(true);
                        phoneAccountEdit.requestFocus();
                    }
                }
                break;
            case R.id.btn_phone_num://校验验证码
                startActivity(new Intent(PhoneActivity.this, RegisterActivity.class));
//                if(!TextUtils.isEmpty(phoneNumEdit.getText().toString())){
//                    SMSSDK.submitVerificationCode("86", phString, phoneNumEdit.getText().toString());
//                }else {
//                    Toast.makeText(this, "验证码不能为空",  Toast.LENGTH_LONG).show();
//                }
        break;
        default:
        break;
    }
    }
    Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.e("event", "event=" + event);
            if (result == SMSSDK.RESULT_COMPLETE) {
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
                    if (USER.isForgetPwd==true){
                        phonePwdTv.setVisibility(View.VISIBLE);
                        phonePwdTv.setText(phonePwd);
                    }else{
                        startActivity(new Intent(PhoneActivity.this, RegisterActivity.class));
                        USER.isCheckPhone = true;
                        USER.setNumberPhone(phoneAccountEdit.getText().toString().trim());
                        finish();
                    }
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    showToast("验证码已经发送");
                }
            } else {
                ((Throwable) data).printStackTrace();
                int resId = getStringRes(PhoneActivity.this, "smssdk_network_error");
                showToast("验证码错误");
                if (resId > 0) {
                    Toast.makeText(PhoneActivity.this, resId, Toast.LENGTH_SHORT).show();
                }
            }

        }

    };
    private void showToast(String str){
        Toast.makeText(PhoneActivity.this, str, Toast.LENGTH_LONG).show();
    }
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
    private void initProgressDialog() {

        if (loadbar == null) {
            loadbar = new Dialog(this, R.style.load_dialog);
            LayoutInflater mInflater = this.getLayoutInflater();
            View v = mInflater.inflate(R.layout.anim_load, null);
            loadbar.setContentView(v);
            loadbar.setCancelable(false);
            loadbar.show();
        } else {
            loadbar.show();
        }
    }
    private void close() {
        if (loadbar != null) {
            if (loadbar.isShowing()) {
                loadbar.dismiss();
            }
        }
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        USER.isForgetPwd=false;
        finish();
    }
}
