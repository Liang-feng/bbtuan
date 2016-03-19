package boyue.bbtuan.person;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import boyue.bbtuan.R;
import boyue.bbtuan.bean.ResultBean;
import boyue.bbtuan.customwheel.NumericWheelAdapter;
import boyue.bbtuan.customwheel.OnWheelScrollListener;
import boyue.bbtuan.customwheel.WheelView;
import boyue.bbtuan.db.SharedPreferenceDb;
import boyue.bbtuan.utils.AppUtils;
import boyue.bbtuan.utils.HttpConstants;


public class MeDataAvtivity extends Activity implements View.OnClickListener{
    private RelativeLayout meDataPassRelLay,meDataNameRelLay,meDataBirthRelLay,meDataSchoolRelLay,meDataCollegeRelLay,meDataMajorRelLay,meDataLabelRelLay,meDataSignRelLay,meDataFavoriteBooksRelLay,meDataFavoriteVideosRelLay,meDatallustrationRelLay;
    //向手机内存储存数据
    private SharedPreferenceDb sharedPreferenceDb;
    private Button meDataBackButt;
    private ImageView meDataRefreshIv;
    private TextView medataTitleTv,meDataNameTv,meDataBirthTv,meDataSchoolTv,meDataCollegeTv,meDataMajorTv,meDataLabelTv,meDataSignTv,meDataFavoriteBooksTv,meDataFavoriteVideosTv,meDatallustrationTv;
    private String[] meDataCollege=new String[]{"机电工程学院","信息与通信学院","计算机科学与工程学院","艺术与设计学院","商学院","外国语学院","数学与计算科学学院","电子工程与自动化学院","材料科学与工程学院","生命与环境科学学院","建筑与交通工程学院","法学院","国际学院","海洋信息工程学院","公共事务学院"};
    private String[] meDataMajor1={"车辆工程专业","机械电子工程专业","电子封装技术专业","电气工程及其自动化专业","机械设计制造及其自动化专业"};
    private String[] meDataMajor2={"通信工程专业","电子信息工程专业","电子科学与技术专业","微电子科学与工程专业","信息对抗专业"};
    private String[] meDataMajor3={"计算机科学与技术专业","数字媒体技术专业","信息管理与信息系统专业","软件工程专业","信息安全专业","网络工程专业","物联网工程专业"};
    private String[] meDataMajor4={"工业设计专业","产品设计专业","环境设计专业","视觉传达设计专业","动画专业","服装与服饰设计专业","书法学专业","数字媒体技术专业"};
    private String[] meDataMajor5={"会计学专业","市场营销专业","电子商务专业","工业工程专业","工商管理专业","财务管理专业","国际经济与贸易专业","物流管理专业"};
    private  String[] meDataMajor6={"英语专业","日语专业"};
    private String[] meDataMajor7={"信息与计算科学专业","统计学专业","数学与应用数学专业"};
    private String[] meDataMajor8={"自动化专业","测控技术与仪器专业","电子信息科学与工程专业","光电信息科学与工程专业","智能科学与技术专业"};
    private String[] meDataMajor9={"材料科学与工程专业","材料成型及控制工程专业","应用物理学专业","高分子材料与工程专业"};
    private String[] meDataMajor10={"生物医学工程专业","生物工程专业","环境工程专业"};
    private String[] meDataMajor11={"交通工程专业","建筑环境与能源应用工程专业","土木工程专业","建筑电气与智能化专业"};
    private String[] meDataMajor12={"法学专业","行政管理专业","知识产权专业"};
    private String[] meDataMajor13={"汉语国际教育专业","对外汉语专业"};
    private String[] meDataMajor14={"机械设计制造及其自动化专业","通信工程专业","电子信息工程专业","计算机科学与技术专业","工业设计专业","物流管理专业","交通工程专业"};
    private String[] meDataMajor15={"公共事业管理专业"};
    private String[] meDataLabel={"考神","学霸","学酥","学弱"};
    private int[] meDataLabelImg={R.mipmap.ic_person_label0,R.mipmap.ic_person_label1,R.mipmap.ic_person_label2,R.mipmap.ic_person_label3};
    //定义显示时间控件
    private Calendar calendar; //通过Calendar获取系统时间
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
    private String[] tmp;
    private int mSingleChoiceID;
    private int mSingleChoiceID1;
    private int mSingleChoiceID2;
    private AlertDialog.Builder builder1;
    private Dialog loadbar = null;
    //提交用户数据
    private List<NameValuePair> allP=new ArrayList<NameValuePair>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_medata);
        sharedPreferenceDb=new SharedPreferenceDb(this);
        initView();
        calendar = Calendar.getInstance();
        builder1= new AlertDialog.Builder(MeDataAvtivity.this);
        IntentFilter filter = new IntentFilter(MeDataChangeActivity.action);
        registerReceiver(broadcastReceiver, filter);
    }
    private void initView() {
        //标题控件
        meDataBackButt=(Button)this.findViewById(R.id.btn_top_back);
        meDataBackButt.setOnClickListener(this);
        medataTitleTv=(TextView)this.findViewById(R.id.tv_top_btntext);
        medataTitleTv.setText("个人资料");
        meDataRefreshIv=(ImageView)this.findViewById(R.id.btn_top_put);
        meDataRefreshIv.setVisibility(View.VISIBLE);
        meDataRefreshIv.setOnClickListener(this);
        meDataRefreshIv.setBackgroundResource(R.drawable.ic_default_ptr_rotate);
        //资料显示控件
        meDataNameTv=(TextView)this.findViewById(R.id.tv_medata_name);
        meDataNameTv.setText(sharedPreferenceDb.getName());
        meDataBirthTv=(TextView)this.findViewById(R.id.tv_medata_birth);
        meDataBirthTv.setText(sharedPreferenceDb.getAge());
        meDataSchoolTv=(TextView)this.findViewById(R.id.tv_medata_school);
        meDataSchoolTv.setText(sharedPreferenceDb.getNameSchool());
        meDataCollegeTv=(TextView)this.findViewById(R.id.tv_medata_college);
        meDataCollegeTv.setText(sharedPreferenceDb.getNameCollege());
        meDataMajorTv=(TextView)this.findViewById(R.id.tv_medata_major);
        meDataMajorTv.setText(sharedPreferenceDb.getNameMajor());
        meDataLabelTv=(TextView)this.findViewById(R.id.tv_medata_label);
        meDataLabelTv.setText(sharedPreferenceDb.getNameLabel());
        meDataSignTv=(TextView)this.findViewById(R.id.tv_medata_sign);
        meDataSignTv.setText(sharedPreferenceDb.getNameSignature());
        meDataFavoriteBooksTv=(TextView)this.findViewById(R.id.tv_medata_favoritebooks);
        meDataFavoriteBooksTv.setText(sharedPreferenceDb.getNameBooks());
        meDataFavoriteVideosTv=(TextView)this.findViewById(R.id.tv_medata_favoritevideos);
        meDataFavoriteVideosTv.setText(sharedPreferenceDb.getNameVideos());
        meDatallustrationTv=(TextView)this.findViewById(R.id.tv_medata_llustration);
        meDatallustrationTv.setText(sharedPreferenceDb.getNameIllustrate());
        //资料点击控件
        meDataPassRelLay=(RelativeLayout)this.findViewById(R.id.relLay_medata_pass);
        meDataNameRelLay=(RelativeLayout)this.findViewById(R.id.relLay_medata_name);
        meDataBirthRelLay=(RelativeLayout)this.findViewById(R.id.relLay_medata_birth);
        meDataSchoolRelLay=(RelativeLayout)this.findViewById(R.id.relLay_medata_school);
        meDataCollegeRelLay=(RelativeLayout)this.findViewById(R.id.relLay_medata_college);
        meDataMajorRelLay=(RelativeLayout)this.findViewById(R.id.relLay_medata_major);
        meDataLabelRelLay=(RelativeLayout)this.findViewById(R.id.relLay_medata_label);
        meDataSignRelLay=(RelativeLayout)this.findViewById(R.id.relLay_medata_sign);
        meDataFavoriteBooksRelLay=(RelativeLayout)this.findViewById(R.id.relLay_medata_favoritebooks);
        meDataFavoriteVideosRelLay=(RelativeLayout)this.findViewById(R.id.relLay_medata_favoritevideos);
        meDatallustrationRelLay=(RelativeLayout)this.findViewById(R.id.relLay_medata_llustration);
        meDataPassRelLay.setOnClickListener(this);
        meDataNameRelLay.setOnClickListener(this);
        meDataBirthRelLay.setOnClickListener(this);
        meDataSchoolRelLay.setOnClickListener(this);
        meDataCollegeRelLay.setOnClickListener(this);
        meDataMajorRelLay.setOnClickListener(this);
        meDataLabelRelLay.setOnClickListener(this);
        meDataSignRelLay.setOnClickListener(this);
        meDataFavoriteBooksRelLay.setOnClickListener(this);
        meDataFavoriteVideosRelLay.setOnClickListener(this);
        meDatallustrationRelLay.setOnClickListener(this);
    }
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!(intent.getExtras().getString("name")==null)){
                meDataNameTv.setText(intent.getExtras().getString("name"));
            }if (!(intent.getExtras().getString("nameSignature")==null)){
                meDataSignTv.setText(intent.getExtras().getString("nameSignature"));
            }if (!(intent.getExtras().getString("nameBooks")==null)){
                meDataFavoriteBooksTv.setText(intent.getExtras().getString("nameBooks"));
            }if (!(intent.getExtras().getString("nameVideos")==null)){
                meDataFavoriteVideosTv.setText(intent.getExtras().getString("nameVideos"));
            }if (!(intent.getExtras().getString("nameIllustrate")==null)){
                meDatallustrationTv.setText(intent.getExtras().getString("nameIllustrate"));
            }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relLay_medata_pass:
                if (AppUtils.isNetworkConnected(MeDataAvtivity.this)==true){
                    startActivity(new Intent(MeDataAvtivity.this,ChangePwdActivity.class));
                }else{
                    showToast("当前无网络连接!");
                }
                break;
            case R.id.relLay_medata_name:
                Intent intent1=new Intent(MeDataAvtivity.this,MeDataChangeActivity.class);
                intent1.putExtra("titlename","显示昵称");
                intent1.putExtra("inputnum", 12);
                intent1.putExtra("inputhint","请输入你的昵称");
                startActivity(intent1);
                break;
            case R.id.relLay_medata_birth:
                //通过自定义控件AlertDialog实现
                AlertDialog.Builder builder = new AlertDialog.Builder(MeDataAvtivity.this);
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
                        allP.add(new BasicNameValuePair("phone",sharedPreferenceDb.getPhoneNum()));
                        allP.add(new BasicNameValuePair("age", "" + calculateDatePoor(birthday)));
                        showChangeDialog();

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
                break;
            case R.id.relLay_medata_school:
                meDataSchoolTv.setText("桂林电子科技大学");
                break;
            case R.id.relLay_medata_college:
                if (AppUtils.isNetworkConnected(MeDataAvtivity.this)==true){
                mSingleChoiceID = -1;
                builder1.setTitle("学院选择");
                builder1.setSingleChoiceItems(meDataCollege, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                mSingleChoiceID = whichButton;
                            }
                        });
                builder1.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                if (mSingleChoiceID >= 0) {
                                    allP.add(new BasicNameValuePair("phone", sharedPreferenceDb.getPhoneNum()));
                                    allP.add(new BasicNameValuePair("nameCollege", meDataCollege[mSingleChoiceID]));
                                    showChangeDialog();

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
                builder1.create().show();}
                else{
                    showToast("当前无网络连接!");
                }
                break;
            case R.id.relLay_medata_major:
                if (!TextUtils.isEmpty(meDataCollegeTv.getText().toString().trim())){
                    switch (meDataCollegeTv.getText().toString().trim()){
                        case "机电工程学院":
                            tmp=meDataMajor1;
                            break;
                        case "信息与通信学院":
                            tmp=meDataMajor2;
                            break;
                        case "计算机科学与工程学院":
                            tmp=meDataMajor3;
                            break;
                        case "艺术与设计学院":
                            tmp=meDataMajor4;
                            break;
                        case "商学院":
                            tmp=meDataMajor5;
                            break;
                        case "外国语学院":
                            tmp=meDataMajor6;
                            break;
                        case "数学与计算科学学院":
                            tmp=meDataMajor7;
                            break;
                        case "电子工程与自动化学院":
                            tmp=meDataMajor8;
                            break;
                        case "材料科学与工程学院":
                            tmp=meDataMajor9;
                            break;
                        case "生命与环境科学学院":
                            tmp=meDataMajor10;
                            break;
                        case "建筑与交通工程学院":
                            tmp=meDataMajor11;
                            break;
                        case "法学院":
                            tmp=meDataMajor12;
                            break;
                        case "国际学院":
                            tmp=meDataMajor13;
                            break;
                        case "海洋信息工程学院":
                            tmp=meDataMajor14;
                            break;
                        case "公共事务学院":
                            tmp=meDataMajor15;
                            break;
                        default:

                            break;
                    }
                    showDialogMajor();
                }else{
                    showToast("学院尚未选择!");
                }

                break;
            case R.id.relLay_medata_label:
                showDialogLabel();
                break;
            case R.id.relLay_medata_sign:
                Intent intent2=new Intent(MeDataAvtivity.this,MeDataChangeActivity.class);
                intent2.putExtra("titlename","个人签名");
                intent2.putExtra("inputnum", 200);
                intent2.putExtra("inputhint", "请输入你的个人签名");
                startActivity(intent2);
                break;
            case R.id.relLay_medata_favoritebooks:
                Intent intent3=new Intent(MeDataAvtivity.this,MeDataChangeActivity.class);
                intent3.putExtra("titlename","最喜欢的书籍");
                intent3.putExtra("inputnum", 200);
                intent3.putExtra("inputhint", "请输入你最喜欢的书籍");
                startActivity(intent3);
                break;
            case R.id.relLay_medata_favoritevideos:
                Intent intent4=new Intent(MeDataAvtivity.this,MeDataChangeActivity.class);
                intent4.putExtra("titlename","最喜欢的影视");
                intent4.putExtra("inputnum", 200);
                intent4.putExtra("inputhint", "请输入你最喜欢的影视");
                startActivity(intent4);
                break;
            case R.id.relLay_medata_llustration:
                Intent intent5=new Intent(MeDataAvtivity.this,MeDataChangeActivity.class);
                intent5.putExtra("titlename","个人说明");
                intent5.putExtra("inputnum", 200);
                intent5.putExtra("inputhint", "请输入你的个人说明");
                startActivity(intent5);
                break;
            case R.id.btn_top_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void showChangeDialog() {
        initProgressDialog();
        RequestParams params=new RequestParams();
        params.addBodyParameter(allP);
        HttpUtils httpUtils=new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.POST, HttpConstants.HTTP_UPDATE_USERDATA,params,new RequestCallBack<String>() {
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
                    switch (b.getData()) {
                        case "3":
                            //年龄修改
                            meDataBirthTv.setText("" + calculateDatePoor(birthday));
                            sharedPreferenceDb.setAge("" + calculateDatePoor(birthday));
                            close();
                            break;
                        case "5":
                            //学院修改
                            meDataCollegeTv.setText(meDataCollege[mSingleChoiceID]);
                            meDataMajorTv.setText("");
                            sharedPreferenceDb.setNameCollege(meDataCollege[mSingleChoiceID]);
                            close();
                            break;
                        case "6":
                            //专业修改
                            meDataMajorTv.setText(tmp[mSingleChoiceID1]);
                            sharedPreferenceDb.setNameMajor(tmp[mSingleChoiceID1]);
                            close();
                            break;
                        case "7":
                            //学习修改
                            meDataLabelTv.setText(meDataLabel[mSingleChoiceID2]);
                            sharedPreferenceDb.setNameLabel(meDataLabel[mSingleChoiceID2]);
                            close();
                            break;
                    }
                }catch (Exception e){
            }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                showToast("资料修改失败!");
                close();
            }
        });
    }

    private void showDialogLabel() {
        mSingleChoiceID2=-1;
        builder1.setTitle("学习类型");
        builder1.setSingleChoiceItems(meDataLabel, 0,
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
                            allP.add(new BasicNameValuePair("phone",sharedPreferenceDb.getPhoneNum()));
                            allP.add(new BasicNameValuePair("nameLabel", meDataLabel[mSingleChoiceID2]));
                            showChangeDialog();

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
    private void showDialogMajor() {
        mSingleChoiceID1=-1;
        builder1.setTitle("专业选择");
        builder1.setSingleChoiceItems(tmp, 0,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        mSingleChoiceID1 = whichButton;
                    }
                });
        builder1.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        if (mSingleChoiceID1 >= 0) {
                            allP.add(new BasicNameValuePair("phone",sharedPreferenceDb.getPhoneNum()));
                            allP.add(new BasicNameValuePair("nameMajor",tmp[mSingleChoiceID1]));
                            showChangeDialog();

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
    private void showToast(String str){
        Toast.makeText(MeDataAvtivity.this, str, Toast.LENGTH_LONG).show();
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
