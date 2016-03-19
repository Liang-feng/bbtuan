package boyue.bbtuan.superroom;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import boyue.bbtuan.R;
import boyue.bbtuan.tabmain.CircleFlowIndicator;
import boyue.bbtuan.tabmain.ImagePagerAdapter;
import boyue.bbtuan.tabmain.ViewFlow;
import boyue.bbtuan.view.RoundImageView;
import boyue.bbtuan.view.ScrollListView;
import boyue.bbtuan.xlist.XListView;

public class SuperClassActivity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener,XListView.IXListViewListener,ScrollListView.OnGetBottomListener {
    private Button topBackButt;//返回键
    private TextView topTitleTv;//界面主题
    private ViewFlow mViewFlow;
    private FrameLayout framelayout;
    private CircleFlowIndicator mFlowIndicator;
    private ArrayList<String> imageUrlList = new ArrayList<String>();
    private ArrayList<String> linkUrlArray = new ArrayList<String>();
    private ArrayList<String> titleList = new ArrayList<String>();
    private ImageView superSearchIv;
    private Handler mHandler;//刷新回调函数
    private ArrayList<HashMap<String, Object>> lstTeachItem;
    private ViewHolder holder = null;
    private superRoomAdapter superRoomAdapter;
    private XListView  superClassHomeLv;
    private ScrollListView supercalssScroll;
    private LinearLayout superclassHomeLinLay;
    private ImageView nearlyStuIv;//周围学霸
    private ImageView findStuIv;//找学霸
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_superclass);
        initView();
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int height = displayMetrics.heightPixels;      //获取手机高度
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height-100);
        superclassHomeLinLay.setLayoutParams(lp);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height-(int)(2*height/3));
        framelayout.setLayoutParams(lp);
        supercalssScroll.smoothScrollTo(0, 0);
        supercalssScroll.setBottomListener(this);
        superClassHomeLv.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
        superClassHomeLv.setPullRefreshEnable(false);
        superClassHomeLv.setXListViewListener(this);
        lstTeachItem = new ArrayList<HashMap<String, Object>>();
        superClassHomeLv.setOnItemClickListener(this);
        superRoomAdapter= new superRoomAdapter(this);
        mHandler = new Handler();
        getData();
        superClassHomeLv.setAdapter(superRoomAdapter);
}
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3)
    {
        startActivity(new Intent(SuperClassActivity.this, SuperTeacherinfoActivity.class));
    }
    private void initView() {
        topBackButt=(Button)this.findViewById(R.id.btn_supertop_back);
        topBackButt.setOnClickListener(this);
        topTitleTv=(TextView)this.findViewById(R.id.tv_school_name);
        topTitleTv.setText("超级课堂");
        nearlyStuIv=(ImageView)this.findViewById(R.id.iv_nearly_stu);
        nearlyStuIv.setOnClickListener(this);
        findStuIv=(ImageView)this.findViewById(R.id.iv_find_stu);
        findStuIv.setOnClickListener(this);
        framelayout=(FrameLayout)this.findViewById(R.id.framelayout);
        superclassHomeLinLay= (LinearLayout)findViewById(R.id.linLay_superclass_home);
        supercalssScroll=(ScrollListView)this.findViewById(R.id.scroll_supercalss);
        superClassHomeLv=(XListView)this.findViewById(R.id.lv_superclass_home);
        mViewFlow = (ViewFlow) this.findViewById(R.id.viewflow);
        mFlowIndicator = (CircleFlowIndicator)this.findViewById(R.id.viewflowindic);
        if (imageUrlList.size() == 0) initDate();
        initBanner(imageUrlList);
        superSearchIv=(ImageView)this.findViewById(R.id.iv_super_search);
        superSearchIv.setOnClickListener(this);
    }
    private ArrayList<HashMap<String, Object>> getData() {

        HashMap<String, Object> map;
        for(int i=1;i<5;i++)
        {
            map= new HashMap<String, Object>();
            map.put("superRoomteachImgIv", R.mipmap.ic_ceshi_head);
            map.put("superRoomteachStarIv", R.mipmap.img_star_four);
            map.put("superRoomteachSubTv", "高等数学A1");
            map.put("superRoomteachNameTv", "陈志良");
            map.put("superRoomteachCollegeTv", "电子工程与自动化学院");
            map.put("superRoomteachPriceTv", "80~200");
            map.put("superRoomteachPhoneTv", "13000000000");
            lstTeachItem.add(map);
        }
        return lstTeachItem;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_supertop_back:
                finish();
                break;
            case R.id.iv_super_search:
                startActivity(new Intent(SuperClassActivity.this,SuperSearchActivity.class));
                break;
            case R.id.iv_nearly_stu:
                startActivity(new Intent(SuperClassActivity.this,NearlyStuActivity.class));
                break;
            case R.id.iv_find_stu:
                startActivity(new Intent(SuperClassActivity.this,FindStuActivity.class));
                break;

            default:
                break;
        }
    }
    private void showToast(String str){
        Toast.makeText(SuperClassActivity.this, str, Toast.LENGTH_LONG).show();
    }
    private void initBanner(ArrayList<String> imageUrlList) {

        mViewFlow.setAdapter(new ImagePagerAdapter(SuperClassActivity.this, imageUrlList,
                linkUrlArray, titleList).setInfiniteLoop(true));
        mViewFlow.setmSideBuffer(imageUrlList.size()); // 实际图片张数，
        // 我的ImageAdapter实际图片张数为3

        mViewFlow.setFlowIndicator(mFlowIndicator);
        mViewFlow.setTimeSpan(4500);
        mViewFlow.setSelection(imageUrlList.size() * 1000); // 设置初始位置
        mViewFlow.startAutoFlowTimer(); // 启动自动播放
    }
    private void initDate() {
        imageUrlList.add("http://i2.qhimg.com/dr/200__/t01fd9e7806947aec45.jpg");
        imageUrlList.add("http://i9.qhimg.com/dr/200__/t0117980c71e890a2da.jpg");
        imageUrlList.add("http://i2.qhimg.com/dr/200__/t01de2e90f7f972921f.jpg");
        imageUrlList.add("http://pic4.nipic.com/20090922/2585201_101948877141_2.jpg");
        linkUrlArray.add("");
        linkUrlArray.add("");
        linkUrlArray.add("");
        linkUrlArray.add("");
        titleList.add("");
        titleList.add("");
        titleList.add("");
        titleList.add("");
    }
    //ViewHolder静态类
    public static class ViewHolder    {
        //老师头像
        public RoundImageView superRoomteachImgIv;
        //老师评价星数
        public ImageView  superRoomteachStarIv;
        //老师授课科目
        public TextView superRoomteachSubTv;
        //老师名字
        public TextView superRoomteachNameTv;
        //老师辅导学院
        public TextView superRoomteachCollegeTv;
        //老师授课一位学生单价
        public TextView superRoomteachPriceTv;
        //老师联系方式
        public TextView superRoomteachPhoneTv;
    }
    public class superRoomAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;
        private superRoomAdapter(Context context)
        {
            //根据context上下文加载布局，即this
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            // 在此适配器中所代表的数据集中的条目数
            return lstTeachItem.size();
        }
        @Override
        public Object getItem(int position) {
            // 获取数据集中与指定索引对应的数据项
            return position;
        }
        @Override
        public long getItemId(int position) {
            // 获取在列表中与指定索引对应的行id
            return position;
        }

        //获取一个在数据集中指定索引的视图来显示数据
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            //如果缓存convertView为空，则需要创建View
            if(convertView == null)            {
                holder = new ViewHolder();
                //根据自定义的Item布局加载布局
                convertView = mInflater.inflate(R.layout.item_teacher_room,null);
                holder.superRoomteachImgIv = (RoundImageView)convertView.findViewById(R.id.thitem_img_teacher);
                holder.superRoomteachStarIv = (ImageView)convertView.findViewById(R.id.thitemth_img_star);
                holder.superRoomteachSubTv = (TextView)convertView.findViewById(R.id.thitem_tv_subject);
                holder.superRoomteachNameTv = (TextView)convertView.findViewById(R.id.thitem_tv_name);
                holder.superRoomteachCollegeTv = (TextView)convertView.findViewById(R.id.thitem_tv_college_name);
                holder.superRoomteachPriceTv = (TextView)convertView.findViewById(R.id.thitem_tv_price_name);
                holder.superRoomteachPhoneTv = (TextView)convertView.findViewById(R.id.thitem_tv_phone_name);
                //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
                convertView.setTag(holder);
            }
            else{
                holder = (ViewHolder)convertView.getTag();
            }
            holder.superRoomteachImgIv.setImageResource((Integer) lstTeachItem.get(position).get("superRoomteachImgIv"));
            holder.superRoomteachStarIv.setImageResource((Integer) lstTeachItem.get(position).get("superRoomteachStarIv"));
            holder.superRoomteachSubTv.setText((String) lstTeachItem.get(position).get("superRoomteachSubTv"));
            holder.superRoomteachNameTv.setText((String) lstTeachItem.get(position).get("superRoomteachNameTv"));
            holder.superRoomteachCollegeTv.setText((String) lstTeachItem.get(position).get("superRoomteachCollegeTv"));
            holder.superRoomteachPriceTv.setText((String) lstTeachItem.get(position).get("superRoomteachPriceTv"));
            holder.superRoomteachPhoneTv.setText((String) lstTeachItem.get(position).get("superRoomteachPhoneTv"));
            return convertView;
        }
    }
    /** 停止刷新， */
    private void onLoad() {
        superClassHomeLv.stopRefresh();
        superClassHomeLv.stopLoadMore();
        superClassHomeLv.setRefreshTime("刚刚");
    }

    // 刷新
    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
                superClassHomeLv.setAdapter(superRoomAdapter);
                onLoad();
            }
        }, 2000);
    }
    @Override
    public void onBottom() {
        // TODO Auto-generated method stub
        superClassHomeLv.setBottomFlag(true);

    }
    // 加载更多
    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                getData();
                superRoomAdapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            this.finish();
        }
        return false;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
