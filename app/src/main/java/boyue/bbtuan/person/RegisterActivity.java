package boyue.bbtuan.person;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Instrumentation;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import boyue.bbtuan.GuideActivity;
import boyue.bbtuan.R;
import boyue.bbtuan.bean.ResultBean;
import boyue.bbtuan.bean.USER;
import boyue.bbtuan.customeffect.Effectstype;
import boyue.bbtuan.customeffect.NiftyDialogBuilder;
import boyue.bbtuan.customwheel.NumericWheelAdapter;
import boyue.bbtuan.customwheel.OnWheelScrollListener;
import boyue.bbtuan.customwheel.WheelView;
import boyue.bbtuan.utils.AppUtils;
import boyue.bbtuan.utils.HttpConstants;
import boyue.bbtuan.view.CircleImageView;

public class RegisterActivity extends Activity implements View.OnClickListener{
    private static final String TAG =DISPLAY_SERVICE ;
    private TextView registerBirthEdit,topBtntextTv;
    private Button topBackButt,registerButt;
    private ImageView mrregshIv,mrregshIv1;
    private RadioButton registerManRadio,registerWomanRadio;
    private static TextView registerAccountTv,registeractproTv,registerproTv,registerLabelTv;
    private EditText registerPsEdit,registerPsEdit1,registerNameEdit;
    private CircleImageView registerHeadCiv;
    private boolean isHidden = true;
    private boolean isHidden1 = true;
    private boolean isChangeHead=false;
    private static  boolean acnum = true; //用户服务协议
    private Dialog upLoadBar = null;//上传对话框
    private Effectstype effect;//对话框类型
    private NiftyDialogBuilder dialogBuilder;//自定义对话框
    private String sex="man";
    private boolean sexWarn=false;
    //定义显示时间控件
    private Calendar calendar; //通过Calendar获取系统时间
    private String[] registerLabel={"考神","学霸","学酥","学弱"};
    private int[] registerLabelImg={R.mipmap.ic_person_label0,R.mipmap.ic_person_label1,R.mipmap.ic_person_label2,R.mipmap.ic_person_label3};
    private String[] tmp;
    private WheelView year;
    private WheelView month;
    private WheelView day;
    private int mYear=1996;
    private int mMonth=0;
    private int mDay=1;
    private int cYear;
    private int cMonth;
    private int cDay;
    private int sumDay;
    private int age;
    private String birthday;
    private int mSingleChoiceID2;
    private Dialog loadbar = null;
    private AlertDialog.Builder builder1;
    private boolean textFlag=true;//禁止昵称输入表情
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        initView();
        dialogBuilder= NiftyDialogBuilder.getInstance(this);
        calendar = Calendar.getInstance();
        builder1= new AlertDialog.Builder(RegisterActivity.this);
        showForbidEmoj();
    }


    private void initView() {
        topBtntextTv=(TextView)this.findViewById(R.id.tv_top_btntext);
        registerAccountTv=(TextView)this.findViewById(R.id.tv_register_account);
        registerAccountTv.setText(USER.numberPhone);
        registerBirthEdit=(TextView)this.findViewById(R.id.edit_register_birth);
        registerBirthEdit.setOnClickListener(this);
        registerNameEdit=(EditText)this.findViewById(R.id.edit_register_name);
        registerManRadio=(RadioButton)this.findViewById(R.id.rb_register_man);
        registerManRadio.setOnClickListener(this);
        registerWomanRadio=(RadioButton)this.findViewById(R.id.rb_register_woman);
        registerWomanRadio.setOnClickListener(this);
        mrregshIv=(ImageView)this.findViewById(R.id.iv_mrreg_sh);
        mrregshIv.setOnClickListener(this);
        mrregshIv1=(ImageView)this.findViewById(R.id.iv_mrreg_sh1);
        mrregshIv1.setOnClickListener(this);
        registerPsEdit=(EditText)this.findViewById(R.id.edit_register_ps);
        registerPsEdit1=(EditText)this.findViewById(R.id.edit_register_ps1);
        registeractproTv=(TextView)this.findViewById(R.id.tv_registeractpro);
        registeractproTv.setOnClickListener(this);
        registerproTv=(TextView)this.findViewById(R.id.tv_registerpro);
        registerproTv.setOnClickListener(this);
        registerHeadCiv=(CircleImageView)this.findViewById(R.id.iv_register_head);
        registerHeadCiv.setOnClickListener(this);
        topBackButt=(Button)this.findViewById(R.id.btn_top_back);
        topBackButt.setOnClickListener(this);
        registerLabelTv=(TextView)this.findViewById(R.id.tv_register_label);
        registerLabelTv.setOnClickListener(this);
        registerButt=(Button)this.findViewById(R.id.btn_register);
        registerButt.setOnClickListener(this);
    }

    private void showForbidEmoj() {
        registerNameEdit.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(textFlag)
                {
                    textFlag=false;
                    String text = registerNameEdit.getText().toString();
                    registerNameEdit.setText(getTextValue(text));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                textFlag=true;
                if(s instanceof Spannable)
                {
                    Spannable spanText = (Spannable)s;
                    Selection.setSelection(spanText, s.length());
                }
            }
        });
    }
    private String getTextValue(String str)
    {
        String s=new String();
        for(int i=0;i<str.length();i++)
        {
            String text=str.substring(i, i+1);
            Log.i("text_info", text);

            Pattern p = Pattern.compile("[0-9]*");
            Matcher m = p.matcher(text);
            if (m.matches())
            {
                s+=text;
            }
            p = Pattern.compile("[a-zA-Z]");
            m = p.matcher(text);
            if (m.matches())
            {
                s+=text;
            }
            p = Pattern.compile("[\u4e00-\u9fa5]");
            m = p.matcher(text);
            if (m.matches())
            {
                s+=text;
            }
        }
        return s.trim();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_register_man:
                if (sexWarn==false){
                    showSexWarn();
                    sexWarn=true;
                }
                break;
            case R.id.rb_register_woman:
                if (sexWarn==false){
                    showSexWarn();
                    sexWarn=true;
                }
                break;
            case R.id.edit_register_birth:
                choiseBirth();
                break;
            case R.id.btn_top_back:
                Intent intent=new Intent(this, GuideActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_mrreg_sh:
                if (isHidden)
                {
                    // 设置EditText文本为可见的
                    mrregshIv.setBackgroundResource(R.mipmap.password_show);
                    registerPsEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else
                {
                    // 设置EditText文本为隐藏的
                    mrregshIv.setBackgroundResource(R.mipmap.password_dide);
                    registerPsEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHidden = !isHidden;
                registerPsEdit.postInvalidate();
                // 切换后将EditText光标置于末尾
                CharSequence charSequence = registerPsEdit.getText();
                if (charSequence instanceof Spannable)
                {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
            case R.id.iv_mrreg_sh1:
                if (isHidden1)
                {
                    // 设置EditText文本为可见的
                    mrregshIv1.setBackgroundResource(R.mipmap.password_show);
                    registerPsEdit1.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());
                } else
                {
                    // 设置EditText文本为隐藏的
                    mrregshIv1.setBackgroundResource(R.mipmap.password_dide);
                    registerPsEdit1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHidden1 = !isHidden1;
                registerPsEdit1.postInvalidate();
                // 切换后将EditText光标置于末尾
                CharSequence charSequence1 = registerPsEdit1.getText();
                if (charSequence1 instanceof Spannable)
                {
                    Spannable spanText = (Spannable) charSequence1;
                    Selection.setSelection(spanText, charSequence1.length());
                }
                break;
            case R.id.tv_registeractpro:
                if (acnum == true)
                {
                    Drawable acprotocol = getResources().getDrawable(
                            R.mipmap.unaccept);
                    acprotocol.setBounds(0, 0, acprotocol.getMinimumWidth(),
                            acprotocol.getMinimumHeight());
                    registeractproTv.setCompoundDrawables(acprotocol, null, null,
                            null);
                    acnum = false;
                } else
                {
                    Drawable unacprotocol = getResources().getDrawable(
                            R.mipmap.accept);
                    unacprotocol.setBounds(0, 0,
                            unacprotocol.getMinimumWidth(),
                            unacprotocol.getMinimumHeight());
                    registeractproTv.setCompoundDrawables(unacprotocol, null,
                            null, null);
                    acnum = true;
                }
                break;
            case R.id.tv_registerpro:
                //用户阅读协议
                Intent intent1=new Intent(RegisterActivity.this,MyLicenseActivity.class);
                startActivity(intent1);
                break;
            case R.id.iv_register_head:
                //头像获取
                effect= Effectstype.SlideBottom;
                ShowPickDialog(v);
                break;
            case R.id.tv_register_label:
                //学习标签选择
                    showDialogLabel();
                break;
            case R.id.btn_register:
                if (registerManRadio.isChecked()){
                    sex="man";
                }else if (registerWomanRadio.isChecked()){
                    sex="woman";
                }
                if (AppUtils.isNetworkConnected(RegisterActivity.this)==true){
                            if (TextUtils.isEmpty(registerPsEdit.getText().toString().trim())){
                                showToast("请输入密码!");
                                registerPsEdit.setFocusable(true);
                                registerPsEdit.requestFocus();
                                return;
                            }
                            if (!TextUtils.isEmpty(registerPsEdit.getText().toString().trim())&&TextUtils.isEmpty(registerPsEdit1.getText().toString().trim())){
                                showToast("请再次输入密码!");
                                registerPsEdit.setFocusable(true);
                                registerPsEdit.requestFocus();
                                return;
                            }
                            if (TextUtils.isEmpty(registerNameEdit.getText().toString().trim())){
                                showToast("请输入用户昵称!");
                                registerNameEdit.setFocusable(true);
                                registerNameEdit.requestFocus();
                                return;
                            }
                            if (TextUtils.isEmpty(registerLabelTv.getText().toString().trim())){
                                showToast("请输入学习类型!");
                                registerLabelTv.setFocusable(true);
                                registerLabelTv.requestFocus();
                                return;
                            }
                            if (TextUtils.isEmpty(registerBirthEdit.getText().toString().trim())){
                                showToast("请输入年龄!");
                                registerBirthEdit.setFocusable(true);
                                registerBirthEdit.requestFocus();
                                return;
                            }
                            if (isChangeHead==false){
                                showToast("请选择头像");
                                return;
                            }
                                if (registerPsEdit.getText().toString().trim().equals(registerPsEdit1.getText().toString().trim())){
                                    initProgressDialog();
                                    File file = new File("/mnt/sdcard/BoYue/boyuehead.png");
                                    if (!file.exists()){
                                        showToast("请选择头像");
                                         close();
                                        return;
                                    }
                                    List<NameValuePair> allP=new ArrayList<NameValuePair>();
                                    allP.add(new BasicNameValuePair("phone",registerAccountTv.getText().toString().trim()));
                                    allP.add(new BasicNameValuePair("name", registerNameEdit.getText().toString().trim()));
                                    allP.add(new BasicNameValuePair("sex", sex));
                                    allP.add(new BasicNameValuePair("age", registerBirthEdit.getText().toString().trim()));
                                    allP.add(new BasicNameValuePair("nameLabel", registerLabelTv.getText().toString().trim()));
                                    allP.add(new BasicNameValuePair("password", registerPsEdit.getText().toString().trim()));
                                    RequestParams params=new RequestParams();
                                    params.addBodyParameter(allP);
                                    params.addBodyParameter("imgPath", file);
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
                                                        if (b.getData().equals("13")) {
                                                            showToast("用户注册成功!");
                                                            close();
                                                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                                            finish();
                                                        }
                                                        if (b.getData().equals("0")) {
                                                            showToast("反馈提交失败!");
                                                            close();
                                                        }
                                                    } catch (Exception e) {
                                                    }
                                                }

                                                @Override
                                                public void onLoading(long total, long current, boolean isUploading) {
                                                    super.onLoading(total, current, isUploading);
                                                }

                                                @Override
                                                public void onFailure(HttpException e, String s) {
                                                    showToast("服务器连接失败");
                                                    close();
                                                }
                                            });

                            }else {
                                showToast("两次输入密码不相符!");
                            }
                }else {
                    showToast("当前无网络连接!");
                }
                break;
            default:
                break;
        }
    }
    private void choiseBirth(){
        //通过自定义控件AlertDialog实现
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        View view = (LinearLayout) getLayoutInflater().inflate(R.layout.dlg_datepicker, null);
        //初始化当前日期
        calendar.setTimeInMillis(System.currentTimeMillis());
        cYear=calendar.get(Calendar.YEAR);
        cMonth=calendar.get(Calendar.MONTH)+1;
        cDay=calendar.get(Calendar.DAY_OF_MONTH);
        year = (WheelView) view.findViewById(R.id.year);
        year.setAdapter(new NumericWheelAdapter(1950, cYear));
        year.setLabel("年");
        year.setCyclic(true);
        year.addScrollingListener(scrollListener);

        month = (WheelView) view.findViewById(R.id.month);
        month.setAdapter(new NumericWheelAdapter(1, 12, "%02d"));
        month.setLabel("月");
        month.setCyclic(true);
        month.addScrollingListener(scrollListener);

        day = (WheelView) view.findViewById(R.id.day);
        initDay(cYear,cMonth);
        day.setLabel("日");
        day.setCyclic(true);

        year.setCurrentItem(cYear - 1950);
        month.setCurrentItem(cMonth - 1);
        day.setCurrentItem(cDay - 1);
        //设置date布局
        builder.setView(view);
        builder.setTitle("生日日期");
        builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //日期格式
                //sb.append(String.format("%d-%02d-%02d", datePicker.getYear(), datePicker.getMonth()+1, datePicker.getDayOfMonth()));
                registerBirthEdit.setText(""+calculateDatePoor(birthday));
                //赋值后面闹钟使用
                dialog.cancel();
            }
        });
        builder.setNegativeButton("取  消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    private void showDialogLabel() {
        mSingleChoiceID2=-1;
        builder1.setTitle("学习类型");
        builder1.setSingleChoiceItems(registerLabel, 0,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        mSingleChoiceID2 = whichButton;
                    }
                });
        builder1.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        if (mSingleChoiceID2 >= 0) {
                            registerLabelTv.setText(registerLabel[mSingleChoiceID2]);
                        }
                    }
                });
        builder1.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        dialog.cancel();
                    }
                });
        builder1.create().show();
    }
    private void ShowPickDialog(View v)
    {
        dialogBuilder
                .withTitle( "图片选择")
                .withTitleColor("#FFFFFFFF")
                .withDividerColor("#11000000")
                .withMessage("")
                .withMessageColor("#FFFFFFFF")
                .withDialogColor("#990cadff")
                .withIcon(getResources().getDrawable(R.mipmap.ic_launcher))
                .isCancelableOnTouchOutside(true)
                .withDuration(700)
                .withEffect(effect)
                .withButton1Text("拍照")
                .withButton2Text("相册")
                .setCustomView(R.layout.dlg_register_view, v.getContext())
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        // 下面这句指定调用相机拍照后的照片存储的路径
                        File photoFile = new File(Environment
                                .getExternalStorageDirectory() + USER.IMAGEPATH);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                        startActivityForResult(intent, 2);
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                "image/*");
                        startActivityForResult(intent, 1);
                    }
                })
                .show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            // 如果是直接从相册获取
            case 1:
                startPhotoZoom(data.getData());
                break;
            // 如果是调用相机拍照时
            case 2:
                // File temp;
                File temp = new File(Environment.getExternalStorageDirectory()
                        +USER.IMAGEPATH);
                startPhotoZoom(Uri.fromFile(temp));
                break;
            // 取得裁剪后的图片
            case 3:
                if (data != null)
                {
                    setPicToView(data);
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void startPhotoZoom(Uri uri)
    {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX",80);
        intent.putExtra("outputY",80);
        intent.putExtra("return-data", true);
        simulateKey(KeyEvent.KEYCODE_BACK);
        startActivityForResult(intent, 3);
    }
    private void setPicToView(Intent picdata)
    {
        Bundle extras = picdata.getExtras();
        if (extras != null)
        {
            Bitmap photo = extras.getParcelable("data");
            String fileName = Environment.getExternalStorageDirectory()
                    + USER.IMAGEPATH;
            try
            {
                BufferedOutputStream bos = new BufferedOutputStream(
                        new FileOutputStream(fileName));
                photo.compress(Bitmap.CompressFormat.PNG, 80, bos);
                bos.flush();
                bos.close();
            } catch (FileNotFoundException e)
            {
                Log.e(TAG,"图片读取失败");
            } catch (IOException e)
            {
                Log.e(TAG, "图片读取失败");
            }
            isChangeHead=true;
            registerHeadCiv.setImageBitmap(photo);
            dialogBuilder.cancel();
        }
    }

    public static void simulateKey(final int KeyCode) {
        new Thread() {   public void run() {
            try {
                Instrumentation inst = new Instrumentation();
                inst.sendKeyDownUpSync(KeyCode);
            } catch (Exception e) {
                Log.e("ExceptionsendKey", e.toString());
            }   }  }.start(); }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent=new Intent(this,GuideActivity.class);
        startActivity(intent);
        finish();
    }
    private void showToast(String str){
        Toast.makeText(RegisterActivity.this, str, Toast.LENGTH_LONG).show();
    }
    OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
        @Override
        public void onScrollingStarted(WheelView wheel) {

        }

        @Override
        public void onScrollingFinished(WheelView wheel) {
            int n_year = year.getCurrentItem() + 1950;//
            int n_month = month.getCurrentItem() + 1;//
            initDay(n_year,n_month);
             birthday=new StringBuilder().append((year.getCurrentItem()+1950)).append("-").append((month.getCurrentItem() + 1) < 10 ? "0" + (month.getCurrentItem() + 1) : (month.getCurrentItem() + 1)).append("-").append(((day.getCurrentItem()+1) < 10) ? "0" + (day.getCurrentItem()+1) : (day.getCurrentItem()+1)).toString();
           // tv.setText("年龄             "+calculateDatePoor(birthday)+"岁");
        }
    };

    /**
     *
     * @param year
     * @param month
     * @return
     */
    private int getDay(int year, int month) {
        int day = 30;
        boolean flag = false;
        switch (year % 4) {
            case 0:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                day = flag ? 29 : 28;
                break;
            default:
                day = 30;
                break;
        }
        return day;
    }

    /**
     */
    private void initDay(int arg1, int arg2) {
        day.setAdapter(new NumericWheelAdapter(1, getDay(arg1, arg2), "%02d"));
    }


    public static final String calculateDatePoor(String birthday) {
        try {
            if (TextUtils.isEmpty(birthday))
                return "0";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birthdayDate = sdf.parse(birthday);
            String currTimeStr = sdf.format(new Date());
            Date currDate = sdf.parse(currTimeStr);
            if (birthdayDate.getTime() > currDate.getTime()) {
                return "0";
            }
            long age = (currDate.getTime() - birthdayDate.getTime())
                    / (24 * 60 * 60 * 1000) + 1;
            String year = new DecimalFormat("0.00").format(age / 365f);
            if (TextUtils.isEmpty(year))
                return "0";
            return String.valueOf(new Double(year).intValue());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "0";
    }
    private void showSexWarn(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("性别确定后将无法变更!");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
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
