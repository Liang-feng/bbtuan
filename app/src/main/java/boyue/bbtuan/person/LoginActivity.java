package boyue.bbtuan.person;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import boyue.bbtuan.GuideActivity;
import boyue.bbtuan.R;
import boyue.bbtuan.bean.USER;
import boyue.bbtuan.bean.UserBean;
import boyue.bbtuan.db.SharedPreferenceDb;
import boyue.bbtuan.tabmain.TabMainActivity;
import boyue.bbtuan.utils.AESEncryptor;
import boyue.bbtuan.view.CircleImageView;

public class LoginActivity extends Activity implements View.OnClickListener{
    //向手机内存写入数据
    private SharedPreferenceDb sharedPreferenceDb;
    private SharedPreferences preferences;
    //设置密码可见性
    private ImageView loginpsshIv;
    //用户登陆
    private AutoCompleteTextView uerEdit = null;
    private boolean isHidden=true;
    private EditText uerPassed = null;
    private TextView forgetPasswdTv,topBtntextTv;
    private Button loginPutButt,topBackButt;
    private ImageButton loginQQImgBtn,loginWeiXinImgBtn,loginSinaImgBtn;
    private CircleImageView loginHeadCiv;
    private String userPhone;
    private String userPassword;
    private Dialog loadbar = null;
    private UserBean u=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        sharedPreferenceDb=new SharedPreferenceDb(this);
        // 读取SharedPreferences中需要的数据
        preferences = getSharedPreferences("baobaotuan", MODE_WORLD_READABLE);
        initView();
        //忘记密码添加下划线
        forgetPasswdTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        topBtntextTv.setText("登陆");
        //账号自动补全
        accountAuto();
    }
    private void initView() {
        loginHeadCiv=(CircleImageView)this.findViewById(R.id.civ_login_head);
        loginpsshIv=(ImageView)this.findViewById(R.id.iv_login_ps_sh);
        loginpsshIv.setOnClickListener(this);
        uerEdit = (AutoCompleteTextView)this.findViewById(R.id.edit_login_user);
        uerPassed = (EditText)this.findViewById(R.id.edit_login_passed);
        forgetPasswdTv=(TextView)this.findViewById(R.id.tv_forget_passwd);
        forgetPasswdTv.setOnClickListener(this);
        loginPutButt=(Button)this.findViewById(R.id.btn_login_put);
        loginPutButt.setOnClickListener(this);
        topBackButt=(Button)this.findViewById(R.id.btn_top_back);
        topBackButt.setOnClickListener(this);
        topBtntextTv=(TextView)this.findViewById(R.id.tv_top_btntext);
        loginQQImgBtn=(ImageButton)this.findViewById(R.id.imgbtn_login_qq);
        loginQQImgBtn.setOnClickListener(this);
        loginWeiXinImgBtn=(ImageButton)this.findViewById(R.id.imgbtn_login_weixin);
        loginWeiXinImgBtn.setOnClickListener(this);
        loginSinaImgBtn=(ImageButton)this.findViewById(R.id.imgbtn_login_sina);
        loginSinaImgBtn.setOnClickListener(this);
    }

    /**
     * 账号自动补全
     */
    private void accountAuto() {
        uerEdit.setThreshold(1);
        //用户输入自动提示
        uerEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // sp.getAll().size()返回的是有多少个键值对
                String[] allUserName = new String[preferences.getAll().size()];
                allUserName = preferences.getAll().keySet().toArray(new String[0]);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line,
                        allUserName);
                uerEdit.setAdapter(adapter);// 设置数据适配器
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                userPassword = preferences.getString(uerEdit.getText().toString().trim(), "");
                try {
                    userPassword = AESEncryptor.decrypt("41227677", userPassword);
                } catch (Exception e) {
                    userPassword = "";
                }
                uerPassed.setText(userPassword);
            }
        });
//        uerEdit.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.length()==11){
//                    if (AppUtils.isNetworkConnected(LoginActivity.this)==true){
//                        if (AppUtils.isMobileNO(uerEdit.getText().toString().trim())==true&&AppUtils.isNetworkConnected(LoginActivity.this)==true){
//                            initProgressDialog();
//                            List<NameValuePair> allP=new ArrayList<NameValuePair>();
//                            allP.add(new BasicNameValuePair("phone", uerEdit.getText().toString().trim()));
//                            allP.add(new BasicNameValuePair("password", ""));
//                            RequestParams params=new RequestParams();
//                            params.addBodyParameter(allP);
//                            HttpUtils httpUtils=new HttpUtils();
//                            httpUtils.send(HttpRequest.HttpMethod.POST, HttpConstants.HTTP_LOGIN, params,
//                                    new RequestCallBack<String>() {
//                                        @Override
//                                        public void onStart() {
//                                            super.onStart();
//                                        }
//
//                                        @Override
//                                        public void onSuccess(ResponseInfo<String> responseInfo) {
//                                            String returnString=responseInfo.result.toString();
//                                            try {
//                                                JSONObject jsonObject = new JSONObject(returnString);
//                                                ResultBean b= JSON.parseObject(jsonObject.getString("result"), ResultBean.class);
//                                                if (!b.getData().equals("0")) {
//                                                    loginHeadCiv.setImageUrl(HttpConstants.HTTP_REQUEST+b.getData());
//                                                    uerPassed.setFocusable(true);
//                                                    uerPassed.requestFocus();
//                                                    close();
//                                                }
//                                                if (b.getData().equals("0")){
//                                                    showToast("用户名不存在!");
//                                                    uerEdit.setText("");
//                                                    close();
//                                                }
//                                            }catch (Exception e){
//                                            }
//                                        }
//                                        @Override
//                                        public void onFailure(HttpException e, String s) {
//                                            showToast("服务器连接失败");
//                                            close();
//                                        }
//                                    });
//                        }else {
//                            showToast("手机号码格式错误!请重新输入");
//                        }
//                    }else {
//                        showToast("当前无网络连接!");
//                    }
//                }
//
//            }
//        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login_put:
                startActivity(new Intent(LoginActivity.this, TabMainActivity.class));
//                if (TextUtils.isEmpty(uerEdit.getText().toString().trim())){
//                    showToast("手机号尚未填写!");
//                    uerEdit.setFocusable(true);
//                    uerEdit.requestFocus();
//                }else if (TextUtils.isEmpty(uerPassed.getText().toString().trim())){
//                    showToast("密码错误!");
//                    uerPassed.setFocusable(true);
//                    uerPassed.requestFocus();
//                }else if (AppUtils.isNetworkConnected(LoginActivity.this)==true){
//                    initProgressDialog();
//                    userPhone=uerEdit.getText().toString().trim();
//                    userPassword=uerPassed.getText().toString().trim();
//                    List<NameValuePair> allP=new ArrayList<NameValuePair>();
//                    allP.add(new BasicNameValuePair("phone", userPhone));
//                    allP.add(new BasicNameValuePair("password", userPassword));
//                    RequestParams params=new RequestParams();
//                    params.addBodyParameter(allP);
//                    HttpUtils httpUtils=new HttpUtils();
//                    httpUtils.send(HttpRequest.HttpMethod.POST, HttpConstants.HTTP_LOGIN, params,
//                            new RequestCallBack<String>() {
//                                @Override
//                                public void onStart() {
//                                    super.onStart();
//                                }
//                                @Override
//                                public void onSuccess(ResponseInfo<String> responseInfo) {
//                                    String returnString=responseInfo.result.toString();
//                                    UserBean u=null;
//                                    try {
//                                        JSONObject jsonObject = new JSONObject(returnString);
//                                        ResultBean b= JSON.parseObject(jsonObject.getString("result"), ResultBean.class);
//                                        if (!b.getData().equals("0")) {
//                                            u = JSON.parseObject(b.getData(), UserBean.class);
//                                            showToast(u.getName());
//                                            sharedPreferenceDb.setName(u.getName());
//                                            sharedPreferenceDb.setPhoneNum(u.getPhonenum());
//                                            sharedPreferenceDb.setAge(u.getAge());
//                                            sharedPreferenceDb.setNameSchool(u.getNameSchool());
//                                            sharedPreferenceDb.setNameCollege(u.getNameCollege());
//                                            sharedPreferenceDb.setNameMajor(u.getNameMajor());
//                                            sharedPreferenceDb.setNameLabel(u.getNameLabel());
//                                            sharedPreferenceDb.setNameSignature(u.getNameSignature());
//                                            sharedPreferenceDb.setNameBooks(u.getNameBooks());
//                                            sharedPreferenceDb.setNameVideos(u.getNameVideos());
//                                            sharedPreferenceDb.setSex(u.getSex());
//                                            sharedPreferenceDb.setNameIllustrate(u.getNameIllustrate());
//                                            sharedPreferenceDb.setImgPath(u.getImgPath());
//                                            showToast("登陆成功");
//                                            SharedPreferences.Editor editor = preferences.edit();
//                                            int count = preferences.getInt("count", 0);
//                                            // 存入数据
//                                            editor.putInt("count", ++count);
//                                            // 提交修改
//                                            editor.commit();
//                                            startActivity(new Intent(LoginActivity.this, TabMainActivity.class));
//                                            finish();
//                                            close();
//                                        }
//                                        if (b.getData().equals("0")){
//                                            showToast("用户名或密码错误");
//                                            close();
//                                        }
//                                    }catch (Exception e){
//
//                                    }
//                                }
//                                @Override
//                                public void onFailure(HttpException e, String s) {
//                                    showToast("服务器连接失败");
//                                    close();
//                                }
//                            });
//                }

                break;
            case R.id.btn_top_back:
                Intent intent=new Intent(this, GuideActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_login_ps_sh:
                if (isHidden) {
                    //设置EditText文本为可见的
                    loginpsshIv.setBackgroundResource(R.mipmap.password_show);
                    uerPassed.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    loginpsshIv.setBackgroundResource(R.mipmap.password_dide);
                    uerPassed.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHidden = !isHidden;
                uerPassed.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence =uerPassed.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }

                break;
            case R.id.tv_forget_passwd:
                USER.isForgetPwd=true;
                startActivity(new Intent(LoginActivity.this,PhoneActivity.class));
                break;
            case R.id.imgbtn_login_qq:

                break;
            case R.id.imgbtn_login_weixin:

                break;
            case R.id.imgbtn_login_sina:

                break;
        }

    }
    private void showToast(String str){
        Toast.makeText(LoginActivity.this, str, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
}
