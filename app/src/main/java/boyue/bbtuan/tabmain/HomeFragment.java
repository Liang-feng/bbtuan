package boyue.bbtuan.tabmain;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import boyue.bbtuan.R;
import boyue.bbtuan.db.SharedPreferenceDb;
import boyue.bbtuan.jifen.JiFenActivity;
import boyue.bbtuan.library.LibraryActivity;
import boyue.bbtuan.signin.SigninActivity;
import boyue.bbtuan.superroom.MoreCourseActivity;
import boyue.bbtuan.superroom.SuperClassActivity;
import boyue.bbtuan.view.RoundImageView;
import boyue.bbtuan.view.ScrollListView;
import boyue.bbtuan.xlist.XListView;

public class HomeFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener,XListView.IXListViewListener,ScrollListView.OnGetBottomListener{
    //向手机内存写入数据
    private SharedPreferenceDb sharedPreferenceDb;
    private ViewFlow mViewFlow;
    private FrameLayout framelayout;
    private CircleFlowIndicator mFlowIndicator;
    private ArrayList<String> imageUrlList = new ArrayList<String>();
    private ArrayList<String> linkUrlArray = new ArrayList<String>();
    private ArrayList<String> titleList = new ArrayList<String>();
    private ArrayList<HashMap<String, Object>> lstTeachItem;
    private Handler mHandler;//刷新回调函数
    private ViewHolder holder = null;
    private superHomeAdapter superHomeAdapter;
    private XListView homeLv;
    private ScrollListView homeScroll;
    private LinearLayout homeLinLay;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 刷新
    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
                homeLv.setAdapter(superHomeAdapter);
                onLoad();
            }
        }, 2000);
    }
    @Override
    public void onBottom() {
        // TODO Auto-generated method stub
        homeLv.setBottomFlag(true);

    }
    // 加载更多
    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                getData();
                superHomeAdapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mViewFlow = (ViewFlow) view.findViewById(R.id.viewflow);
        mFlowIndicator = (CircleFlowIndicator) view.findViewById(R.id.viewflowindic);
        if (imageUrlList.size() == 0) initDate();
        initBanner(imageUrlList);
        initView(view);
        sharedPreferenceDb=new SharedPreferenceDb(getActivity());

        return view;
    }
    private void initView(View view){
        homeLinLay= (LinearLayout)view.findViewById(R.id.linLay_home_list);
        homeScroll=(ScrollListView)view.findViewById(R.id.scroll_homecalss);
        homeLv=(XListView)view.findViewById(R.id.lv_home_list);
        framelayout=(FrameLayout)view.findViewById(R.id.framelayout);
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int height = displayMetrics.heightPixels;      //获取手机高度
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height-100);
        homeLinLay.setLayoutParams(lp);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height-(int)(2*height/3));
        framelayout.setLayoutParams(lp);
        homeScroll.smoothScrollTo(0, 0);
        homeScroll.setBottomListener(this);
        homeLv.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
       homeLv.setPullRefreshEnable(false);
        homeLv.setXListViewListener(this);
        lstTeachItem = new ArrayList<HashMap<String, Object>>();
        homeLv.setOnItemClickListener(this);
        superHomeAdapter= new superHomeAdapter(getContext());
        mHandler = new Handler();
        getData();
        homeLv.setAdapter(superHomeAdapter);
        view.findViewById(R.id.tv_home_more_course).setOnClickListener(this);
        view.findViewById(R.id.iv_home_baodatui).setOnClickListener(this);
        view.findViewById(R.id.iv_home_library).setOnClickListener(this);
        view.findViewById(R.id.iv_home_jifen).setOnClickListener(this);
        view.findViewById(R.id.iv_home_qiandao).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_home_baodatui:
                startActivity(new Intent(getActivity(), SuperClassActivity.class));
                break;
            case R.id.iv_home_library:
                startActivity(new Intent(getActivity(), LibraryActivity.class));
                break;
            case R.id.iv_home_jifen:
                startActivity(new Intent(getActivity(), JiFenActivity.class));
                break;
            case R.id.iv_home_qiandao:
                startActivity(new Intent(getActivity(), SigninActivity.class));
                break;
            case R.id.tv_home_more_course:
                startActivity(new Intent(getActivity(),MoreCourseActivity.class));
                break;
            default:
                break;
        }


    }
    private void initBanner(ArrayList<String> imageUrlList) {

        mViewFlow.setAdapter(new ImagePagerAdapter(getActivity(), imageUrlList,
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
    private ArrayList<HashMap<String, Object>> getData() {

        HashMap<String, Object> map;
        for(int i=1;i<5;i++)
        {
            map= new HashMap<String, Object>();
            map.put("superRoomteachImgIv", R.mipmap.ic_math_temp);
            map.put("superRoomteachNameTv", "高等数学第六版上册辅导班");
            map.put("superRoomteachLocTv", "东区9教214");
            map.put("superRoomteachTimeTv", "星期三、六 19:00-21:30");
            map.put("superRoomteachClassTTv", "57节");
            map.put("superRoomteachNumTv", "12/15");
            map.put("superRoomteachPriceTv", "¥260");
            lstTeachItem.add(map);
        }
        return lstTeachItem;
    }
    //ViewHolder静态类
    public static class ViewHolder    {
        //课程图片
        public RoundImageView superRoomteachImgIv;
        //课程科目
        public TextView superRoomteachNameTv;
        //课程地址
        public TextView superRoomteachLocTv;
        //课程时间
        public TextView superRoomteachTimeTv;
        //课程课时
        public TextView superRoomteachClassTTv;
        //课程报名数
        public TextView superRoomteachNumTv;
        //课程单价
        public TextView superRoomteachPriceTv;
    }
    public class superHomeAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;
        private superHomeAdapter(Context context)
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
                convertView = mInflater.inflate(R.layout.item_home_list,null);
                holder.superRoomteachImgIv = (RoundImageView)convertView.findViewById(R.id.thitem_img_photo);
                holder.superRoomteachNameTv = (TextView)convertView.findViewById(R.id.thitem_tv_name);
                holder.superRoomteachLocTv = (TextView)convertView.findViewById(R.id.thitem_tv_loc);
                holder.superRoomteachTimeTv = (TextView)convertView.findViewById(R.id.thitem_tv_time);
                holder.superRoomteachClassTTv = (TextView)convertView.findViewById(R.id.thitem_tv_classt);
                holder.superRoomteachNumTv = (TextView)convertView.findViewById(R.id.thitem_tv_num);
                holder.superRoomteachPriceTv = (TextView)convertView.findViewById(R.id.thitem_tv_prive);
                //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
                convertView.setTag(holder);
            }
            else{
                holder = (ViewHolder)convertView.getTag();
            }
            holder.superRoomteachImgIv.setImageResource((Integer) lstTeachItem.get(position).get("superRoomteachImgIv"));
            holder.superRoomteachNameTv.setText((String) lstTeachItem.get(position).get("superRoomteachNameTv"));
            holder.superRoomteachLocTv.setText((String) lstTeachItem.get(position).get("superRoomteachLocTv"));
            holder.superRoomteachTimeTv.setText((String) lstTeachItem.get(position).get("superRoomteachTimeTv"));
            holder.superRoomteachClassTTv.setText((String) lstTeachItem.get(position).get("superRoomteachClassTTv"));
            holder.superRoomteachNumTv.setText((String) lstTeachItem.get(position).get("superRoomteachNumTv"));
            holder.superRoomteachPriceTv.setText((String) lstTeachItem.get(position).get("superRoomteachPriceTv"));
            return convertView;
        }
    }
    /** 停止刷新， */
    private void onLoad() {
        homeLv.stopRefresh();
        homeLv.stopLoadMore();
        homeLv.setRefreshTime("刚刚");
    }
}
