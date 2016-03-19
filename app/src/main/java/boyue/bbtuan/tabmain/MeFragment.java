package boyue.bbtuan.tabmain;

import android.app.Dialog;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.loopj.android.image.WebImage;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import boyue.bbtuan.R;
import boyue.bbtuan.bean.ResultBean;
import boyue.bbtuan.bean.USER;
import boyue.bbtuan.customeffect.Effectstype;
import boyue.bbtuan.customeffect.NiftyDialogBuilder;
import boyue.bbtuan.db.SharedPreferenceDb;
import boyue.bbtuan.person.MeDataAvtivity;
import boyue.bbtuan.person.MyCollectsActivity;
import boyue.bbtuan.person.MyGoodsActivity;
import boyue.bbtuan.person.MyMessageActivity;
import boyue.bbtuan.view.CircleImageView;
import boyue.bbtuan.utils.HttpConstants;

public class MeFragment extends Fragment implements View.OnClickListener{
    //private static final String TAG =DISPLAY_SERVICE ;
    //向手机内存写入数据
    private SharedPreferenceDb sharedPreferenceDb;
    private RelativeLayout personOwnRelLay,personGoodsRelLay,personCollectRelLay,personMessageRelLay;
    private TextView personalUserTv,personalAgeTv;
    private LinearLayout personalSexLinLay;
    private CircleImageView personalHeadimg;
    private WebImage webImage;
    private Dialog loadbar = null;
    //对话框类型
    private Effectstype effect;
    //自定义对话框
    private NiftyDialogBuilder dialogBuilder;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferenceDb=new SharedPreferenceDb(getActivity());
        dialogBuilder= NiftyDialogBuilder.getInstance(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        personalUserTv=(TextView)view.findViewById(R.id.tv_personal_user);
        personalUserTv.setText(sharedPreferenceDb.getName());
        personalSexLinLay=(LinearLayout)view.findViewById(R.id.linLay_personal_sex);
        personalHeadimg=(CircleImageView)view.findViewById(R.id.img_personal_head);
        personalHeadimg.setImageUrl(HttpConstants.HTTP_REQUEST + sharedPreferenceDb.getImgPath());
        personalHeadimg.setOnClickListener(this);
        personalAgeTv=(TextView)view.findViewById(R.id.tv_personal_age);
        personalAgeTv.setText(sharedPreferenceDb.getAge());
        if (sharedPreferenceDb.getSex().equals("man")){
            personalSexLinLay.setBackgroundResource(R.drawable.ic_personalinfo_male);
        }else if (sharedPreferenceDb.getSex().equals("woman")){
            personalSexLinLay.setBackgroundResource(R.drawable.ic_personalinfo_female);
        }
        personOwnRelLay=(RelativeLayout)view.findViewById(R.id.relLay_person_own);
        personOwnRelLay.setOnClickListener(this);
        personGoodsRelLay=(RelativeLayout)view.findViewById(R.id.relLay_person_goods);
        personGoodsRelLay.setOnClickListener(this);
        personCollectRelLay=(RelativeLayout)view.findViewById(R.id.relLay_person_collect);
        personCollectRelLay.setOnClickListener(this);
        personMessageRelLay=(RelativeLayout)view.findViewById(R.id.relLay_person_message);
        personMessageRelLay.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relLay_person_own:
                startActivity(new Intent(getActivity(), MeDataAvtivity.class));
                break;
            case R.id.relLay_person_goods:
                startActivity(new Intent(getActivity(), MyGoodsActivity.class));
                break;
            case R.id.relLay_person_collect:
                startActivity(new Intent(getActivity(), MyCollectsActivity.class));
                break;
            case R.id.relLay_person_message:
                startActivity(new Intent(getActivity(), MyMessageActivity.class));
                break;
            case R.id.img_personal_head:
                //头像获取
                effect= Effectstype.SlideBottom;
                ShowPickDialog(v);
                break;
            default:
                break;
        }
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
    public void onActivityResult(int requestCode, int resultCode, Intent data)
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
    public static void simulateKey(final int KeyCode) {
        new Thread() {   public void run() {
            try {
                Instrumentation inst = new Instrumentation();
                inst.sendKeyDownUpSync(KeyCode);
            } catch (Exception e) {
                Log.e("ExceptionsendKey", e.toString());
            }   }  }.start(); }
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
            } catch (IOException e)
            {
            }
            File file = new File("/mnt/sdcard/BoYue/boyuehead.png");
            if (!file.exists()){
                return;
            }
            initProgressDialog();
            List<NameValuePair> allP=new ArrayList<NameValuePair>();
            allP.add(new BasicNameValuePair("phone", sharedPreferenceDb.getPhoneNum()));
            allP.add(new BasicNameValuePair("changeImgPath","1"));
            RequestParams params=new RequestParams();
            params.addBodyParameter(allP);
            params.addBodyParameter("imgPath", file);
            HttpUtils httpUtils=new HttpUtils();
            httpUtils.send(HttpRequest.HttpMethod.POST, HttpConstants.HTTP_UPDATE_USERICON, params,
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
                                if (!b.getData().equals("")) {
                                    sharedPreferenceDb.setImgPath(b.getData());
                                    personalHeadimg.destroyDrawingCache();
                                    webImage=new WebImage(HttpConstants.HTTP_REQUEST + b.getData());
                                    webImage.removeFromCache(HttpConstants.HTTP_REQUEST + b.getData());
                                    personalHeadimg.setImageUrl(HttpConstants.HTTP_REQUEST + b.getData());
                                    dialogBuilder.cancel();
                                    showToast("图片修改成功!");
                                    close();
                                }
                                if (b.getData().equals("0")) {
                                    showToast("图片修改失败!");
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

        }
    }
    private void showToast(String str){
        Toast.makeText(getActivity(), str, Toast.LENGTH_LONG).show();
    }
    private void initProgressDialog() {

        if (loadbar == null) {
            loadbar = new Dialog(getActivity(), R.style.load_dialog);
            LayoutInflater mInflater = getActivity().getLayoutInflater();
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
