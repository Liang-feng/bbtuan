package boyue.bbtuan.superroom;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import boyue.bbtuan.R;
import boyue.bbtuan.utils.AppUtils;
import boyue.bbtuan.utils.HttpConstants;

public class FindStuActivity extends Activity implements View.OnClickListener{
    private Button topBackButt;//返回键
    private TextView topTitleTv;//界面主题
    private EditText findstuNameEdit;//个人姓名
    private EditText findstuCollegeEdit;//个人学院
    private TextView findstuGradeTv;//个人年级
    private ImageView findstuGradeIv;//年级选择框
    private EditText findstuPhoneEdit;//个人联系方式
    private EditText findstuwSubEdit;//想学科目
    private RadioButton findstuwNanRb;//男老师
    private RadioButton findstuwNvRb;//女老师
    private RadioButton findstuwElseRb;//性别不限
    private RadioButton findstuwTchRb;//老师上门
    private RadioButton findstuwStuRb;//学生上门
    private RadioButton findstuwLocRb;//协商地点
    private EditText findstuwPriceEdit;//家阿哥区间
    private EditText findstuwIntroEdit;//具体描述
    private Button findstuPutButt;//提交订单
    private Dialog loadbar = null;//加载条
    private String sex="男";
    private String classStyle="学生上门";
    private int mSingleChoiceID0;
    private String[] signGradeLabel={"大一","大二","大三","大四"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_findstu);
        initView();

    }

    private void initView() {
        topBackButt=(Button)this.findViewById(R.id.btn_top_back);
        topBackButt.setOnClickListener(this);
        topTitleTv=(TextView)this.findViewById(R.id.tv_top_btntext);
        topTitleTv.setText("找学霸");
        findstuNameEdit=(EditText)this.findViewById(R.id.edit_findstu_name);
        findstuCollegeEdit=(EditText)this.findViewById(R.id.edit_findstu_college);
        findstuPhoneEdit=(EditText)this.findViewById(R.id.edit_findstu_phone);
        findstuwSubEdit=(EditText)this.findViewById(R.id.edit_findstuw_sub);
        findstuwPriceEdit=(EditText)this.findViewById(R.id.edit_findstuw_price);
        findstuwIntroEdit=(EditText)this.findViewById(R.id.edit_findstuw_intro);
        findstuGradeTv=(TextView)this.findViewById(R.id.tv_findstu_grade);
        findstuGradeIv=(ImageView)this.findViewById(R.id.iv_findstu_grade);
        findstuGradeIv.setOnClickListener(this);
        findstuwNanRb=(RadioButton)this.findViewById(R.id.rb_findstuw_nan);
        findstuwNvRb=(RadioButton)this.findViewById(R.id.rb_findstuw_nv);
        findstuwElseRb=(RadioButton)this.findViewById(R.id.rb_findstuw_else);
        findstuwTchRb=(RadioButton)this.findViewById(R.id.rb_findstuw_tch);
        findstuwStuRb=(RadioButton)this.findViewById(R.id.rb_findstuw_stu);
        findstuwLocRb=(RadioButton)this.findViewById(R.id.rb_findstuw_loc);
        findstuPutButt=(Button)this.findViewById(R.id.btn_findstu_put);
        findstuPutButt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_top_back:
                    finish();
                    break;
                case R.id.iv_findstu_grade:
                    showSignGrade();
                    break;
                case R.id.btn_findstu_put:
                    if (findstuwNanRb.isChecked()==true){
                        sex="男";
                    }else if (findstuwNvRb.isChecked()==true){
                        sex="女";
                    }else if (findstuwElseRb.isChecked()==true){
                        sex="不限";
                    }
                    if (findstuwTchRb.isChecked()==true){
                        classStyle="老师上门";
                    }else if (findstuwStuRb.isChecked()==true){
                        classStyle="学生上门";
                    }else if (findstuwLocRb.isChecked()==true){
                        classStyle="协商地点";
                    }
                    String findstuNameString=findstuNameEdit.getText().toString().trim();
                    String findstuCollegeString=findstuCollegeEdit.getText().toString().trim();
                    String findstuPhoneString=findstuPhoneEdit.getText().toString().trim();
                    String findstuwSubString=findstuwSubEdit.getText().toString().trim();
                    String findstuwPriceString=findstuwPriceEdit.getText().toString().trim();
                    String findstuwIntroString=findstuwIntroEdit.getText().toString().trim();
                    if (TextUtils.isEmpty(findstuNameString)){
                        showToast("姓名尚未填写!");
                        findstuNameEdit.setFocusable(true);
                        findstuNameEdit.requestFocus();
                    }else if (TextUtils.isEmpty(findstuCollegeString)){
                        showToast("学院尚未填写!");
                        findstuCollegeEdit.setFocusable(true);
                        findstuCollegeEdit.requestFocus();
                    }else if (TextUtils.isEmpty(findstuPhoneString)){
                        showToast("联系方式尚未填写!");
                        findstuPhoneEdit.setFocusable(true);
                        findstuPhoneEdit.requestFocus();
                    }else if (TextUtils.isEmpty(findstuwSubString)){
                        showToast("想学科目尚未填写!");
                        findstuwSubEdit.setFocusable(true);
                        findstuwSubEdit.requestFocus();
                    }else if (TextUtils.isEmpty(findstuwPriceString)){
                        showToast("价格区间尚未填写!");
                        findstuwPriceEdit.setFocusable(true);
                        findstuwPriceEdit.requestFocus();
                    }else if (TextUtils.isEmpty(findstuwIntroString)){
                        showToast("课程描述尚未填写!");
                        findstuwPriceEdit.setFocusable(true);
                        findstuwPriceEdit.requestFocus();
                    }else if (AppUtils.isNetworkConnected(FindStuActivity.this)==true){
                        initProgressDialog();
                        List<NameValuePair> allP=new ArrayList<NameValuePair>();
                        allP.add(new BasicNameValuePair("findName",findstuNameString));
                        allP.add(new BasicNameValuePair("findCollege",findstuCollegeString));
                        allP.add(new BasicNameValuePair("findPhone",findstuPhoneString));
                        allP.add(new BasicNameValuePair("findSub",findstuwSubString));
                        allP.add(new BasicNameValuePair("findSex",sex));
                        allP.add(new BasicNameValuePair("findClassStyle",classStyle));
                        allP.add(new BasicNameValuePair("findPrice",findstuwPriceString));
                        allP.add(new BasicNameValuePair("findIntro",findstuwIntroString));
                        RequestParams params=new RequestParams();
                        params.addBodyParameter(allP);
                        HttpUtils httpUtils=new HttpUtils();
                        httpUtils.send(HttpRequest.HttpMethod.POST, HttpConstants.HTTP_FIND_STU, params,
                                new RequestCallBack<String>() {
                                    @Override
                                    public void onStart() {
                                        super.onStart();
                                    }
                                    @Override
                                    public void onSuccess(ResponseInfo<String> responseInfo) {
                                                close();
                                    }
                                    @Override
                                    public void onFailure(HttpException e, String s) {
                                        showToast("服务器连接失败");
                                        close();
                                    }
                                });
                    }else {
                        showToast("请连接网络!");
                    }
                    break;
                default:
                    break;
            }
    }
    private void showSignGrade() {
        mSingleChoiceID0=-1;
        AlertDialog.Builder builder1=new AlertDialog.Builder(FindStuActivity.this);
        builder1.setTitle("学生年级");
        builder1.setSingleChoiceItems(signGradeLabel, 0,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        mSingleChoiceID0 = whichButton;
                    }
                });
        builder1.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        if (mSingleChoiceID0 >= 0) {
                            findstuGradeTv.setText(signGradeLabel[mSingleChoiceID0]);
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
    private void showToast(String str){
        Toast.makeText(FindStuActivity.this, str, Toast.LENGTH_LONG).show();
    }
}
