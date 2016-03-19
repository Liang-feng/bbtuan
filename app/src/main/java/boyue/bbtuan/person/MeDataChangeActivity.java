package boyue.bbtuan.person;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import boyue.bbtuan.utils.HttpConstants;

public class MeDataChangeActivity extends Activity implements View.OnClickListener {
    public static final String action = "jason.broadcast.action";
    private Button meDataChangeBackButt;
    private ImageButton meDataChangePut;
    private TextView meDataChangeTitleTv,meDataChangeNumTv;
    private EditText meDataChangeInputEdit;
    private String titleName;
    private int inputNum;
    private String inputhInt;
    private Dialog loadbar = null;
    private SharedPreferenceDb sharedPreferenceDb;
    //提交用户数据
    private List<NameValuePair> allP=new ArrayList<NameValuePair>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity__medata_change);
        sharedPreferenceDb=new SharedPreferenceDb(this);
        Bundle bundle=getIntent().getExtras();
        titleName=bundle.getString("titlename");
        inputhInt=bundle.getString("inputhint");
        inputNum=bundle.getInt("inputnum");
        initView();
        meDataChangeInputEdit.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            private int selectionStart;
            private int selectionEnd;


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp = s;

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                int number = inputNum - s.length();
                meDataChangeNumTv.setText("" + number);
                selectionStart = meDataChangeInputEdit.getSelectionStart();
                selectionEnd = meDataChangeInputEdit.getSelectionEnd();
                if (temp.length() > inputNum) {
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    meDataChangeInputEdit.setText(s);
                    meDataChangeInputEdit.setSelection(tempSelection);
                }

            }
        });
    }

    private void initView() {
        meDataChangePut=(ImageButton)this.findViewById(R.id.btn_top_put);
        meDataChangePut.setOnClickListener(this);
        meDataChangePut.setVisibility(View.VISIBLE);
        meDataChangeBackButt=(Button)this.findViewById(R.id.btn_top_back);
        meDataChangeBackButt.setOnClickListener(this);
        meDataChangeTitleTv=(TextView)this.findViewById(R.id.tv_top_btntext);
        meDataChangeTitleTv.setText(titleName);
        meDataChangeInputEdit=(EditText)this.findViewById(R.id.edit_medatachange_input);
        meDataChangeInputEdit.setHint(inputhInt);
        meDataChangeNumTv=(TextView)this.findViewById(R.id.tv_medatachange_num);
        meDataChangeNumTv.setText("" + inputNum);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_top_back:
                finish();
                break;
            case R.id.btn_top_put:
                switch (titleName){
                    case "显示昵称":
                        allP.add(new BasicNameValuePair("phone",sharedPreferenceDb.getPhoneNum()));
                        allP.add(new BasicNameValuePair("name", meDataChangeInputEdit.getText().toString().trim()));
                        showChangeDialog();
                        break;
                    case "个人签名":
                        allP.add(new BasicNameValuePair("phone",sharedPreferenceDb.getPhoneNum()));
                        allP.add(new BasicNameValuePair("nameSignature", meDataChangeInputEdit.getText().toString().trim()));
                        showChangeDialog();
                        break;
                    case "最喜欢的书籍":
                        allP.add(new BasicNameValuePair("phone",sharedPreferenceDb.getPhoneNum()));
                        allP.add(new BasicNameValuePair("nameBooks", meDataChangeInputEdit.getText().toString().trim()));
                        showChangeDialog();
                        break;
                    case "最喜欢的影视":
                        allP.add(new BasicNameValuePair("phone",sharedPreferenceDb.getPhoneNum()));
                        allP.add(new BasicNameValuePair("nameVideos", meDataChangeInputEdit.getText().toString().trim()));
                        showChangeDialog();
                        break;
                    case "个人说明":
                        allP.add(new BasicNameValuePair("phone",sharedPreferenceDb.getPhoneNum()));
                        allP.add(new BasicNameValuePair("nameIllustrate", meDataChangeInputEdit.getText().toString().trim()));
                        showChangeDialog();
                        break;
                    default:
                        break;
                }
        }
    }

    private void showChangeDialog() {
        initProgressDialog();
        RequestParams params=new RequestParams();
        params.addBodyParameter(allP);
        HttpUtils httpUtils=new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.POST, HttpConstants.HTTP_UPDATE_USERDATA, params,
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
                            ResultBean b= JSON.parseObject(jsonObject.getString("result"), ResultBean.class);
                                switch (b.getData()){
                                    case "1":
                                        sharedPreferenceDb.setName(meDataChangeInputEdit.getText().toString().trim());
                                        Intent intent1 = new Intent(action);
                                        intent1.putExtra("name",meDataChangeInputEdit.getText().toString().trim());
                                        sendBroadcast(intent1);
                                        showToast("资料修改成功!");
                                        close();
                                        finish();
                                        break;
                                    case "8":
                                        sharedPreferenceDb.setNameSignature(meDataChangeInputEdit.getText().toString().trim());
                                        Intent intent2 = new Intent(action);
                                        intent2.putExtra("nameSignature",meDataChangeInputEdit.getText().toString().trim());
                                        sendBroadcast(intent2);
                                        showToast("资料修改成功!");
                                        close();
                                        finish();
                                        break;
                                    case "9":
                                        sharedPreferenceDb.setNameBooks(meDataChangeInputEdit.getText().toString().trim());
                                        Intent intent3 = new Intent(action);
                                        intent3.putExtra("nameBooks",meDataChangeInputEdit.getText().toString().trim());
                                        sendBroadcast(intent3);
                                        showToast("资料修改成功!");
                                        close();
                                        finish();
                                        break;
                                    case "10":
                                        sharedPreferenceDb.setNameVideos(meDataChangeInputEdit.getText().toString().trim());
                                        Intent intent4 = new Intent(action);
                                        intent4.putExtra("nameVideos",meDataChangeInputEdit.getText().toString().trim());
                                        sendBroadcast(intent4);
                                        showToast("资料修改成功!");
                                        close();
                                        finish();
                                        break;
                                    case "11":
                                        sharedPreferenceDb.setNameIllustrate(meDataChangeInputEdit.getText().toString().trim());
                                        Intent intent5 = new Intent(action);
                                        intent5.putExtra("nameIllustrate", meDataChangeInputEdit.getText().toString().trim());
                                        sendBroadcast(intent5);
                                        showToast("资料修改成功!");
                                        close();
                                        finish();
                                        break;
                                    case "2":
                                        showToast("用户名称已存在!");
                                        finish();
                                        close();
                                        break;
                                    case "0":
                                        showToast("资料修改失败!");
                                        close();
                                        break;
                                    default:
                                        break;
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
    }
    private void showToast(String str){
        Toast.makeText(MeDataChangeActivity.this, str, Toast.LENGTH_LONG).show();
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
        startActivity(new Intent(MeDataChangeActivity.this, MeDataAvtivity.class));
        finish();
    }
}
