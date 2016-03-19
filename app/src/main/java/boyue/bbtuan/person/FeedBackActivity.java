package boyue.bbtuan.person;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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

import boyue.bbtuan.R;
import boyue.bbtuan.bean.ResultBean;
import boyue.bbtuan.utils.AppUtils;
import boyue.bbtuan.utils.HttpConstants;

public class FeedBackActivity extends Activity implements View.OnClickListener{
    private Button feedBackSubmitButt,backButt;
    private EditText feedBackOpinionEdit,feedbackLinkEdit;
    private TextView feedBackTitleTv;
    private Dialog loadbar = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_feedback);
        initView();
    }

    private void initView() {
        backButt=(Button)this.findViewById(R.id.btn_top_back);
        backButt.setOnClickListener(this);
        feedBackSubmitButt=(Button)this.findViewById(R.id.btn_feedback_submit);
        feedBackSubmitButt.setOnClickListener(this);
        feedBackOpinionEdit=(EditText)this.findViewById(R.id.edit_feedback_opinion);
        feedbackLinkEdit=(EditText)this.findViewById(R.id.edit_feedback_link);
        feedBackTitleTv=(TextView)this.findViewById(R.id.tv_top_btntext);
        feedBackTitleTv.setText("意见反馈");
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_top_back:
                    finish();
                    break;
                case R.id.btn_feedback_submit:

                    if (TextUtils.isEmpty(feedBackOpinionEdit.getText().toString().trim())){
                        showToast("亲！宝贵意见尚未填写。。。");
                        feedBackOpinionEdit.setFocusable(true);
                        feedBackOpinionEdit.requestFocus();
                    }else if (TextUtils.isEmpty(feedbackLinkEdit.getText().toString().trim())){
                        showToast("亲！联系方式尚未填写!");
                        feedbackLinkEdit.setFocusable(true);
                        feedbackLinkEdit.requestFocus();
                    }else if (AppUtils.isNetworkConnected(FeedBackActivity.this)==true){
                        initProgressDialog();
                        List<NameValuePair> allP=new ArrayList<NameValuePair>();
                        allP.add(new BasicNameValuePair("contact",feedbackLinkEdit.getText().toString().trim()));
                        allP.add(new BasicNameValuePair("advise", feedBackOpinionEdit.getText().toString().trim()));
                        RequestParams params=new RequestParams();
                        params.addBodyParameter(allP);
                        HttpUtils httpUtils=new HttpUtils();
                        httpUtils.send(HttpRequest.HttpMethod.POST, HttpConstants.HTTP_FEEDBACK, params,
                                new RequestCallBack<String>() {
                                    @Override
                                    public void onStart() {
                                        super.onStart();
                                    }

                                    @Override
                                    public void onSuccess(ResponseInfo<String> responseInfo) {
                                        String returnString=responseInfo.result.toString();
                                        try {
                                            JSONObject jsonObject = new JSONObject(returnString);
                                            ResultBean b= JSON.parseObject(jsonObject.getString("result"), ResultBean.class);
                                            if (!b.getData().equals("0")) {
                                                showToast("反馈提交成功!");
                                                close();
                                                finish();
                                            }
                                            if (b.getData().equals("0")){
                                                showToast("反馈提交失败!");
                                                close();
                                            }
                                        }catch (Exception e){
                                        }
                                    }
                                    @Override
                                    public void onFailure(HttpException e, String s) {
                                        showToast("服务器连接失败");
                                        close();
                                    }
                                });
                    }else {
                        showToast("手机无网络连接！请检查网路配置!");
                    }
                    break;
                default:
                    break;

            }
    }
    private void showToast(String str){
        Toast.makeText(FeedBackActivity.this, str, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }
    public void initProgressDialog() {

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
    public void close() {
        if (loadbar != null) {
            if (loadbar.isShowing()) {
                loadbar.dismiss();
            }
        }
    }

}
