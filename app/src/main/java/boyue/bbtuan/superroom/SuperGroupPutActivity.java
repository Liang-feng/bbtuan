package boyue.bbtuan.superroom;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import boyue.bbtuan.R;
import boyue.bbtuan.jifen.JifenProActivity;

public class SuperGroupPutActivity extends Activity implements View.OnClickListener{
    private TextView groupSignobjTv;//科目名称
    private TextView groupSignnumTv;//报名数量
    private TextView groupSignopriceTv;//课程原价
    private TextView groupSignpriceTv;//课程团购价
    private TextView groupSignoprice0Tv;//课程单价
    private TextView groupSignpriceline;//分隔线
    private TextView groupSignnumLine;//分隔线
    private EditText groupSignnameEdit;//用户名输入
    private EditText groupSignidEdit;//用户学号输入
    private TextView groupSigngradeEdit;//用户年级选择
    private TextView topBtntextTv;//团队报名标题
    private TextView guakeTv;//团队报名标题
    private EditText groupSignphoneEdit;//用户手机号输入
    private EditText groupSignmlocEdit;//用户自填辅导地点
    private ImageView groupSigngradeIv;//用户年级选择按钮
    private LinearLayout groupSignreduceLinLay;//团购人数减
    private LinearLayout groupSignaddLinLay;//团购人数加
    private Button groupSignputButt;//订单提交
    private Button topBackButt;//团队报名返回
    private LinearLayout groupSignnumLinLay;//团购线性布局
    private LinearLayout groupSignpriceLinLay;//课程团购价线性布局
    private int num=0;
    private String signWay="";
    private int mSingleChoiceID0;
    private Dialog loadbar = null;//加载对话框
    private String[] signGradeLabel={"大一","大二","大三","大四"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.item_teacher_group);
        final Intent intent = getIntent();
        signWay=intent.getStringExtra("signway");
        initView();
        divisionWay(signWay);
    }

    private void divisionWay(String signWay) {
        if (signWay.equals("group")){
            topBtntextTv.setText("团队报名");
        }else if (signWay.equals("person")){
            topBtntextTv.setText("个人报名");
            groupSignpriceline.setVisibility(View.GONE);
            groupSignnumLine.setVisibility(View.GONE);
            groupSignnumLinLay.setVisibility(View.GONE);
            groupSignpriceLinLay.setVisibility(View.GONE);
        }
    }

    private void initView() {
        guakeTv=(TextView)this.findViewById(R.id.tv_guake);
        guakeTv.setOnClickListener(this);
        groupSignobjTv=(TextView)this.findViewById(R.id.tv_group_sign_obj);
        groupSignnumTv=(TextView)this.findViewById(R.id.tv_group_signnum);
        groupSignopriceTv=(TextView)this.findViewById(R.id.tv_group_signoprice);
        groupSignpriceTv=(TextView)this.findViewById(R.id.tv_group_signprice);
        groupSignoprice0Tv=(TextView)this.findViewById(R.id.tv_group_signoprice0);
        topBtntextTv=(TextView)this.findViewById(R.id.tv_top_btntext);
        groupSignpriceline=(TextView)this.findViewById(R.id.line_group_signprice);
        groupSignnumLine=(TextView)this.findViewById(R.id.line_group_signnum);
        groupSignnameEdit=(EditText)this.findViewById(R.id.edit_group_signname);
        groupSignidEdit=(EditText)this.findViewById(R.id.edit_group_signid);
        groupSigngradeEdit=(TextView)this.findViewById(R.id.edit_group_signgrade);
        groupSignphoneEdit=(EditText)this.findViewById(R.id.edit_group_signphone);
        groupSigngradeIv=(ImageView)this.findViewById(R.id.iv_group_signgrade);
        groupSigngradeIv.setOnClickListener(this);
        groupSignreduceLinLay=(LinearLayout)this.findViewById(R.id.linLay_group_signreduce);
        groupSignaddLinLay=(LinearLayout)this.findViewById(R.id.linLay_group_signadd);
        groupSignnumLinLay=(LinearLayout)this.findViewById(R.id.linLay_group_signnum);
        groupSignpriceLinLay=(LinearLayout)this.findViewById(R.id.linLay_group_signprice);
        groupSignputButt=(Button)this.findViewById(R.id.btn_group_signput);
        topBackButt=(Button)this.findViewById(R.id.btn_top_back);
        topBackButt.setOnClickListener(this);
        groupSignreduceLinLay.setOnClickListener(new OnLinLayClickListener());
        groupSignaddLinLay.setOnClickListener(new OnLinLayClickListener());
        groupSignputButt.setOnClickListener(this);
    }
    class OnLinLayClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            if (v.getId()==R.id.linLay_group_signadd){
                num++;
                groupSignnumTv.setText(""+num);
            }
            if (v.getId()==R.id.linLay_group_signreduce){
                if (num==0){

                }else if(num>=1){
                    num--;
                    groupSignnumTv.setText(""+num);
                }
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_group_signgrade:
                showSignGrade();
                break;
            case R.id.btn_group_signput:
                signPutInfo();
                break;
            case R.id.btn_top_back:
                finish();
                break;
            case R.id.tv_guake:
                Intent intent1=new Intent(SuperGroupPutActivity.this,JifenProActivity.class);
                intent1.putExtra("protcl","guake");
                startActivity(intent1);
                break;
            default:
                break;
        }

    }

    private void signPutInfo() {
        startActivity(new Intent(SuperGroupPutActivity.this,FinalPayActivity.class));
//        String groupSignname=groupSignnameEdit.getText().toString().trim();
//        String groupSignid=groupSignidEdit.getText().toString().trim();
//        String groupSignphone=groupSignphoneEdit.getText().toString().trim();
//        if (signWay.equals("group")&&(groupSignnumTv.equals("0")||groupSignnumTv.equals("1"))){
//            showToast("团队报名至少为两人以上!");
//        }else if (groupSignstageRb.isChecked()||groupSigndownRb.isChecked()){
//            showToast("暂不支持分期付款和线下支付!");
//        }else if (TextUtils.isEmpty(groupSignname)){
//            showToast("请输入姓名!");
//            groupSignnameEdit.setFocusable(true);
//            groupSignnameEdit.requestFocus();
//        }else if (TextUtils.isEmpty(groupSignid)){
//            showToast("请输入学号!");
//            groupSignnameEdit.setFocusable(true);
//            groupSignnameEdit.requestFocus();
//        }else if (TextUtils.isEmpty(groupSignphone)){
//            showToast("请输入手机号码!");
//            groupSignphoneEdit.setFocusable(true);
//            groupSignphoneEdit.requestFocus();
//        }else if (groupSignmlocRb.isChecked()&&TextUtils.isEmpty(groupSignmlocEdit.getText().toString().trim())){
//            showToast("请输入辅导地点!");
//            groupSignmlocEdit.setFocusable(true);
//            groupSignmlocEdit.requestFocus();
//        }else if (AppUtils.isNetworkConnected(SuperGroupPutActivity.this)){
//
//        }else {
//            showToast("无网络连接!请链接网络");
//        }
    }
    private void showToast(String str){
        Toast.makeText(SuperGroupPutActivity.this, str, Toast.LENGTH_SHORT).show();
    }
    private void showSignGrade() {
        mSingleChoiceID0=-1;
        AlertDialog.Builder builder1=new AlertDialog.Builder(SuperGroupPutActivity.this);
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
                            groupSigngradeEdit.setText(signGradeLabel[mSingleChoiceID0]);
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
