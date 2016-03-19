package boyue.bbtuan.person;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import boyue.bbtuan.db.SharedPreferenceDb;
import boyue.bbtuan.utils.AppUtils;
import boyue.bbtuan.utils.HttpConstants;

public class ChangePwdActivity extends Activity implements View.OnClickListener{
    //向手机内存写入数据
    private SharedPreferenceDb sharedPreferenceDb;
    private Button changePassBackButt,editpwdPutButt;
    private TextView changePassTitleTv;
    private EditText editpwdPassedEdit,editpwdPassedEdit1,editpwdPassedEdit2;
    private ImageView editpwdShIv,editpwdShIv1,editpwdShIv2;
    private Dialog loadbar = null;
    private Boolean isHidden=true;
    private Boolean isHidden1=true;
    private Boolean isHidden2=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_changepass);
        sharedPreferenceDb=new SharedPreferenceDb(this);
        initView();
    }

    private void initView() {
        changePassBackButt=(Button)this.findViewById(R.id.btn_top_back);
        changePassBackButt.setOnClickListener(this);
        changePassTitleTv=(TextView)this.findViewById(R.id.tv_top_btntext);
        changePassTitleTv.setText("修改密码");
        editpwdPutButt=(Button)this.findViewById(R.id.btn_editpwd_put);
        editpwdPutButt.setOnClickListener(this);
        editpwdPassedEdit=(EditText)this.findViewById(R.id.edit_editpwd_passed);
        editpwdPassedEdit1=(EditText)this.findViewById(R.id.edit_editpwd_passed1);
        editpwdPassedEdit2=(EditText)this.findViewById(R.id.edit_editpwd_passed2);
        editpwdShIv=(ImageView)this.findViewById(R.id.iv_editpwd_sh);
        editpwdShIv1=(ImageView)this.findViewById(R.id.iv_editpwd_sh1);
        editpwdShIv2=(ImageView)this.findViewById(R.id.iv_editpwd_sh2);
        editpwdShIv.setOnClickListener(this);
        editpwdShIv1.setOnClickListener(this);
        editpwdShIv2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_editpwd_sh:
                if (isHidden) {
                    //设置EditText文本为可见的
                    editpwdShIv.setBackgroundResource(R.mipmap.password_show);
                    editpwdPassedEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    editpwdShIv.setBackgroundResource(R.mipmap.password_dide);
                    editpwdPassedEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHidden = !isHidden;
                editpwdPassedEdit.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence =editpwdPassedEdit.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
            case R.id.iv_editpwd_sh1:
                if (isHidden1) {
                    //设置EditText文本为可见的
                    editpwdShIv1.setBackgroundResource(R.mipmap.password_show);
                    editpwdPassedEdit1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    editpwdShIv1.setBackgroundResource(R.mipmap.password_dide);
                    editpwdPassedEdit1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHidden1 = !isHidden1;
                editpwdPassedEdit1.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence1 =editpwdPassedEdit1.getText();
                if (charSequence1 instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence1;
                    Selection.setSelection(spanText, charSequence1.length());
                }
                break;
            case R.id.iv_editpwd_sh2:
                if (isHidden2) {
                    //设置EditText文本为可见的
                    editpwdShIv2.setBackgroundResource(R.mipmap.password_show);
                    editpwdPassedEdit2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    editpwdShIv2.setBackgroundResource(R.mipmap.password_dide);
                    editpwdPassedEdit2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHidden2 = !isHidden2;
                editpwdPassedEdit2.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence2 =editpwdPassedEdit1.getText();
                if (charSequence2 instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence2;
                    Selection.setSelection(spanText, charSequence2.length());
                }
                break;
            case R.id.btn_top_back:
                finish();
                break;
            case R.id.btn_editpwd_put:
                String pwdOrigin=editpwdPassedEdit.getText().toString().trim();
                String pwdAgain=editpwdPassedEdit1.getText().toString().trim();
                String pwdAgain1=editpwdPassedEdit1.getText().toString().trim();
                if (TextUtils.isEmpty(pwdOrigin)){
                    showToast("您的初始密码未填写!");
                    editpwdPassedEdit.setFocusable(true);
                    editpwdPassedEdit.requestFocus();
                    return;
                }else if (TextUtils.isEmpty(pwdAgain)){
                    showToast("您的修改密码未填写!");
                    editpwdPassedEdit1.setFocusable(true);
                    editpwdPassedEdit1.requestFocus();
                    return;
                }else if (TextUtils.isEmpty(pwdAgain1)) {
                    showToast("您的修改密码未填写!");
                    editpwdPassedEdit2.setFocusable(true);
                    editpwdPassedEdit2.requestFocus();
                    return;
                }else if (!pwdAgain.equals(pwdAgain1)){
                    showToast("您两次输入的修改密码不一致!");
                    return;
                }else if (pwdOrigin.equals(pwdAgain)){
                    showToast("初始密码与原始密码不能相同!");
                    return;
                } else if (AppUtils.isNetworkConnected(ChangePwdActivity.this)){
                    initProgressDialog();
                    List<NameValuePair> allP=new ArrayList<NameValuePair>();
                    allP.add(new BasicNameValuePair("phone",sharedPreferenceDb.getPhoneNum()));
                    allP.add(new BasicNameValuePair("oldPassword", pwdOrigin));
                    allP.add(new BasicNameValuePair("newPassword", pwdAgain));
                    RequestParams params=new RequestParams();
                    params.addBodyParameter(allP);
                    HttpUtils httpUtils=new HttpUtils();
                    httpUtils.send(HttpRequest.HttpMethod.POST, HttpConstants.HTTP_CHANGE_USERPWD, params,
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
                                        if (b.getData().equals("1")) {
                                            showToast("密码修改成功!请重新登录");
                                            close();
                                            startActivity(new Intent(ChangePwdActivity.this,LoginActivity.class));
                                            finish();
                                        }
                                        if (b.getData().equals("0")){
                                            showToast("密码修改失败!");
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
                    showToast("手机无网络连接!");
                }
                break;
            default:
                break;
        }
    }
    private void showToast(String str){
        Toast.makeText(ChangePwdActivity.this, str, Toast.LENGTH_LONG).show();
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
