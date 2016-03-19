package boyue.bbtuan.tabmain;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.WriterException;

import boyue.bbtuan.R;
import boyue.bbtuan.db.SharedPreferenceDb;
import boyue.bbtuan.person.AboutUsActivity;
import boyue.bbtuan.person.CommonSetActivity;
import boyue.bbtuan.person.FeedBackActivity;
import boyue.bbtuan.person.QCodeActivity;
import boyue.bbtuan.update.UpdateService;
import boyue.bbtuan.utils.HttpConstants;
import boyue.bbtuan.utils.QrcodeUtil;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class SettingsFragment extends Fragment implements View.OnClickListener{
    private SharedPreferenceDb sharedPreferenceDb;
    private RelativeLayout setFeedRelLay,setUpdateRelLay,setAppcodeRelLay,setAppinfoRelLay,setShareRelLay,setCommonRelLay;
    private Resources mResources;
    private ImageView newUpdateImgIv;
    private static final int CREATE_QRCODE = 1001;
    private static final int CREATE_LOGO_QRCODE = 1002;
    private static final int CREATE_NORMAL_QRCODE = 1003;
    private Boolean verFlag=false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        initView(view);
        mResources = getResources();
        sharedPreferenceDb=new SharedPreferenceDb(getActivity());
        if (!sharedPreferenceDb.getDeviseOldVersion().equals(sharedPreferenceDb.getDeviseNewVersion())){
            newUpdateImgIv.setVisibility(View.VISIBLE);
        }
        return view;
    }

    private void initView(View view) {
        newUpdateImgIv=(ImageView)view.findViewById(R.id.iv_new_update_img);
        setFeedRelLay=(RelativeLayout)view.findViewById(R.id.relLay_set_feed);
        setUpdateRelLay=(RelativeLayout)view.findViewById(R.id.relLay_set_update);
        setAppcodeRelLay=(RelativeLayout)view.findViewById(R.id.relLay_set_appcode);
        setAppinfoRelLay=(RelativeLayout)view.findViewById(R.id.relLay_set_appinfo);
        setShareRelLay=(RelativeLayout)view.findViewById(R.id.relLay_set_share);
        setCommonRelLay=(RelativeLayout)view.findViewById(R.id.relLay_set_common);
        setFeedRelLay.setOnClickListener(this);
        setUpdateRelLay.setOnClickListener(this);
        setAppcodeRelLay.setOnClickListener(this);
        setAppinfoRelLay.setOnClickListener(this);
        setShareRelLay.setOnClickListener(this);
        setCommonRelLay.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relLay_set_feed:
                startActivity(new Intent(getActivity(), FeedBackActivity.class));
                break;
            case R.id.relLay_set_update:
                    showUpdate();
                break;
            case R.id.relLay_set_appcode:
                String content = "http://www.apk.anzhi.com/data3/apk/201509/25/cd297bf49f3e15721e800f1ae071c124_25470100.apk";
                try {
                    Bitmap map_erweima = QrcodeUtil.onCreateLogoQrcode(mResources, content);

                } catch (WriterException e) {
                    e.printStackTrace();

                }
                startActivity(new Intent(getActivity(), QCodeActivity.class));
                break;
            case R.id.relLay_set_appinfo:
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
            case R.id.relLay_set_share:
                showShare();
                break;
            case R.id.relLay_set_common:
                startActivity(new Intent(getActivity(), CommonSetActivity.class));
                break;
            default:
                break;
        }
    }

    private void showUpdate() {
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.update,null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setInverseBackgroundForced(true);
        builder.setView(view);
        TextView tvTimeMessage = (TextView) view
                .findViewById(R.id.tv_update_VerMsg);
        EditText tvUpdateMessage=(EditText)view.findViewById(R.id.tvUpdateMessage);
        final  TextView forgetVerTv=(TextView)view.findViewById(R.id.tv_forget_ver);
        forgetVerTv.setVisibility(View.GONE);
        tvTimeMessage.setText("版本:" + sharedPreferenceDb.getDeviseNewVersion() + "更新日期:" + sharedPreferenceDb.getDeviseNewVertime() + "软件大小:" + sharedPreferenceDb.getDeviseNewVercap());
        tvUpdateMessage.setText(sharedPreferenceDb.getDeviseNewVermessage());
        builder.setView(view);
        builder.create();
        builder.setCancelable(true);
        builder.setPositiveButton("更新升级", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent updateIntent = new Intent(getActivity(), UpdateService.class);
                updateIntent.putExtra("titleId", R.string.app_name);
                updateIntent.putExtra("apkpath", HttpConstants.HTTP_REQUEST+sharedPreferenceDb.getDeviseNewVerapkpath());
                getActivity().startService(updateIntent);
                dialog.dismiss();
            }

        });
        builder.setNegativeButton("稍后升级",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void showShare() {
        ShareSDK.initSDK(getActivity());
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("https://123.sogou.com/");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("桂林抱抱团科技有限公司招新啦！");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/text.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("https://123.sogou.com/");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("这款社交软件非常好用");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        //oks.setSilent(true);
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("https://123.sogou.com/");
        oks.show(getActivity());

    }
}
