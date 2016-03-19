package boyue.bbtuan.superroom;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import boyue.bbtuan.R;
import boyue.bbtuan.view.RoundImageView;
import boyue.bbtuan.xlist.XListView;

public class MoreCourseActivity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener,XListView.IXListViewListener{
    private Button topBackButt;//返回键
    private TextView topTitleTv;//界面主题
    private XListView moreCourseLv;//刷新列表
    private MoreCourseAdapter moreCourseAdapter;
    private ViewHolder holder = null;
    private Handler mHandler;//刷新回调函数
    private ArrayList<HashMap<String, Object>> lstTeachItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morecourse);
        initView();
        moreCourseAdapter=new MoreCourseAdapter(this);
        moreCourseLv.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
        moreCourseLv.setPullRefreshEnable(false);
        moreCourseLv.setXListViewListener(this);
        lstTeachItem = new ArrayList<HashMap<String, Object>>();
        moreCourseLv.setOnItemClickListener(this);
        mHandler = new Handler();
        getData();
        moreCourseLv.setAdapter(moreCourseAdapter);
    }

    private void initView() {
        topBackButt=(Button)this.findViewById(R.id.btn_top_back);
        topBackButt.setOnClickListener(this);
        topTitleTv=(TextView)this.findViewById(R.id.tv_top_btntext);
        topTitleTv.setText("更多精彩课程");
        moreCourseLv=(XListView)this.findViewById(R.id.lv_more_course);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_top_back:
                finish();
                break;
            default:
                break;
        }
    }
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3)
    {
        startActivity(new Intent(MoreCourseActivity.this, SuperTeacherinfoActivity.class));
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
    // 刷新
    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
                moreCourseLv.setAdapter(moreCourseAdapter);
                onLoad();
            }
        }, 2000);
    }
    // 加载更多
    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                getData();
                moreCourseAdapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
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
    public class MoreCourseAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;
        private MoreCourseAdapter(Context context)
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
        moreCourseLv.stopRefresh();
        moreCourseLv.stopLoadMore();
        moreCourseLv.setRefreshTime("刚刚");
    }
}
