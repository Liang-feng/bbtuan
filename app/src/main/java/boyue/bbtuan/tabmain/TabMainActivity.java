package boyue.bbtuan.tabmain;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import boyue.bbtuan.bean.USER;
import boyue.bbtuan.bean.UpdateBean;
import boyue.bbtuan.db.SharedPreferenceDb;
import boyue.bbtuan.radarcity.CityListActivity;
import boyue.bbtuan.update.UpdateService;
import boyue.bbtuan.utils.AppUtils;
import boyue.bbtuan.utils.HttpConstants;

public class TabMainActivity extends AppCompatActivity implements View.OnClickListener {
    //向手机内存储存数据
    private SharedPreferenceDb sharedPreferenceDb;
    private HomeFragment mHomeFragment;
    private MeFragment mMeFragment;
    private SettingsFragment mSettingsFragment;
    private FragmentManager mFragmentManager;
    private FragmentTransaction transaction;
    private FrameLayout mFrameLayout;
    private RelativeLayout home_reLlayout;
    private RelativeLayout me_reLlayout;
    private RelativeLayout settings_reLlayout;
    private ImageView home_image;
    private ImageView me_image;
    private ImageView settings_image;
    private TextView home_text;
    private TextView settings_text;
    private TextView me_text;
    private TextView top_text;
    private TextView cityNameTv;
    private ImageView cityIndictorIv; private String oldVersion;
    private UpdateBean updateBean;
    //忽略版本信息
    private Boolean verFlag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabmain);
        getSupportActionBar().hide();
        sharedPreferenceDb=new SharedPreferenceDb(this);
        initView();
        showUpdate();
        if (USER.getChangeCity()!=""){
            cityNameTv.setText(USER.getChangeCity());
        }
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction();
        replaceHomeFragment(mFragmentManager.beginTransaction());

    }

    private void initView() {
        cityIndictorIv=(ImageView)findViewById(R.id.iv_city_indictor);
        cityNameTv=(TextView)findViewById(R.id.tv_city_name);
        top_text = (TextView) findViewById(R.id.tv_top_text);
        home_image = (ImageView) findViewById(R.id.home_image);
        me_image = (ImageView) findViewById(R.id.me_image);
        settings_image = (ImageView) findViewById(R.id.setting_image);
        home_text = (TextView) findViewById(R.id.home_text);
        me_text = (TextView) findViewById(R.id.me_text);
        settings_text = (TextView) findViewById(R.id.setting_text);
        home_reLlayout = (RelativeLayout) findViewById(R.id.home_layout);
        me_reLlayout = (RelativeLayout) findViewById(R.id.me_layout);
        settings_reLlayout = (RelativeLayout) findViewById(R.id.setting_layout);
        me_reLlayout.setOnClickListener(TabMainActivity.this);
        settings_reLlayout.setOnClickListener(TabMainActivity.this);
        home_reLlayout.setOnClickListener(TabMainActivity.this);
    }
    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        clearCioce();
        switch (v.getId()) {
            case R.id.home_layout:
                replaceHomeFragment(transaction);
            break;
            case R.id.me_layout:
                replaceMeFragment(transaction);
                break;
            case R.id.setting_layout:
                replaceSettingsFragment(transaction);
                break;

        }

    }

    private void replaceHomeFragment(FragmentTransaction transaction) {
        home_image.setImageResource(R.mipmap.ic_homepage_press);
        home_text.setTextColor(getResources().getColor(R.color.bg_main));
        if (AppUtils.countLength(USER.getChangeCity())>10){
            top_text.setText("");
        }else {
            top_text.setText("首页");
        }
        cityNameTv.setVisibility(View.VISIBLE);
        cityNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TabMainActivity.this, CityListActivity.class));
            }
        });
        if((USER.getCity()==""||USER.getCity()==null)&&(USER.getChangeCity()==null||USER.getChangeCity()=="")&&(sharedPreferenceDb.getUserCity()=="")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("无法获取当前城市信息！手动选择城市");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    Intent intent=new Intent(TabMainActivity.this,CityListActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }else if (USER.getChangeCity()!=""){
            cityNameTv.setText(USER.getChangeCity());
        }
        cityIndictorIv.setVisibility(View.VISIBLE);
        if (mHomeFragment == null) mHomeFragment = new HomeFragment();
        transaction.replace(R.id.tab_content, mHomeFragment);
        transaction.commit();
    }

    private void replaceMeFragment(FragmentTransaction transaction) {
        me_image.setImageResource(R.mipmap.ic_own_press);
        me_text.setTextColor(getResources().getColor(R.color.bg_main));
        top_text.setText("个人中心");
        cityNameTv.setVisibility(View.GONE);
        cityIndictorIv.setVisibility(View.GONE);
        if (mMeFragment == null) mMeFragment = new MeFragment();
        transaction.replace(R.id.tab_content, mMeFragment);
        transaction.commit();
    }

    private void replaceSettingsFragment(FragmentTransaction transaction) {
        settings_image.setImageResource(R.mipmap.ic_set_press);
        settings_text.setTextColor(getResources().getColor(R.color.bg_main));
        top_text.setText("设置");
        cityNameTv.setVisibility(View.GONE);
        cityIndictorIv.setVisibility(View.GONE);
        if (mSettingsFragment == null) mSettingsFragment = new SettingsFragment();
        transaction.replace(R.id.tab_content, mSettingsFragment);
        transaction.commit();
    }
    private void clearCioce() {
        home_image.setImageResource(R.mipmap.ic_homepage_normal);
        home_text.setTextColor(getResources().getColor(R.color.gray_bar_text));
        me_image.setImageResource(R.mipmap.ic_own_normal);
        me_text.setTextColor(getResources().getColor(R.color.gray_bar_text));
        settings_image.setImageResource(R.mipmap.ic_set_normal);
        settings_text.setTextColor(getResources().getColor(R.color.gray_bar_text));
    }
    private void showUpdate() {

        List<NameValuePair> allP=new ArrayList<NameValuePair>();
        try {
            oldVersion=""+getPackageManager().getPackageInfo(getPackageName(),0).versionName;
        }catch (Exception e){

        }
        allP.add(new BasicNameValuePair("oldversion", oldVersion));
        RequestParams requestParams=new RequestParams();
        requestParams.addBodyParameter(allP);
        HttpUtils httpUtils=new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.POST, HttpConstants.HTTP_VERSION_UPDATE, requestParams, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result.toString();
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result);
                    updateBean = JSON.parseObject(jsonObject.getString("result"), UpdateBean.class);
                    sharedPreferenceDb.setDeviseOldVersion(oldVersion);
                    sharedPreferenceDb.setDeviseNewVersion(updateBean.getNewVersion());
                    sharedPreferenceDb.setDeviseNewVertime(updateBean.getNewVerTime());
                    sharedPreferenceDb.setDeviseNewVercap(updateBean.getNewVerCap());
                    sharedPreferenceDb.setDeviseNewVermessage(updateBean.getNewVerMessage());
                    sharedPreferenceDb.setDeviseNewVerapkpath(updateBean.getNewApkPath());
                } catch (Exception e) {
                }
               if (!(sharedPreferenceDb.getDeviseNeglectVersion()).equals(sharedPreferenceDb.getDeviseNewVersion())) {
                   showCustomDialog();
               }
            }
            @Override
            public void onFailure(HttpException e, String s) {
            }
        });
    }
    private void showCustomDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View view =inflater.inflate(R.layout.update,null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setInverseBackgroundForced(true);
        builder.setView(view);
        TextView tvTimeMessage = (TextView) view
                .findViewById(R.id.tv_update_VerMsg);
        EditText tvUpdateMessage=(EditText)view.findViewById(R.id.tvUpdateMessage);
        final  TextView forgetVerTv=(TextView)view.findViewById(R.id.tv_forget_ver);
        forgetVerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verFlag == false) {
                    Drawable acprotocol = getResources().getDrawable(R.mipmap.accept);
                    acprotocol.setBounds(0, 0, acprotocol.getMinimumWidth(), acprotocol.getMinimumHeight());
                    forgetVerTv.setCompoundDrawables(acprotocol, null, null, null);
                    verFlag = true;

                } else {
                    Drawable acprotocol1 = getResources().getDrawable(R.mipmap.unaccept);
                    acprotocol1.setBounds(0, 0, acprotocol1.getMinimumWidth(), acprotocol1.getMinimumHeight());
                    forgetVerTv.setCompoundDrawables(acprotocol1, null, null, null);
                    verFlag = false;
                }

            }
        });

        tvTimeMessage.setText("版本:" + updateBean.getNewVersion() + "更新日期:" + updateBean.getNewVerTime() + "软件大小:" + updateBean.getNewVerCap());
        tvUpdateMessage.setText("更新内容:" + updateBean.getNewVerMessage());
        builder.setView(view);
        builder.create();
        builder.setCancelable(true);
        builder.setPositiveButton("更新升级", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent updateIntent = new Intent(TabMainActivity.this, UpdateService.class);
                updateIntent.putExtra("titleId", R.string.app_name);
                updateIntent.putExtra("apkpath", HttpConstants.HTTP_REQUEST+updateBean.getNewApkPath());
                startService(updateIntent);
                dialog.dismiss();
            }

        });
        builder.setNegativeButton("稍后升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (verFlag == true) {
                    sharedPreferenceDb.setDeviseNeglectVersion(updateBean.getNewVersion());
                }
                dialog.dismiss();
            }
        });
        builder.show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
