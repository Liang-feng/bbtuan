package boyue.bbtuan.superroom;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import boyue.bbtuan.R;
import boyue.bbtuan.view.RoundImageView;
import boyue.bbtuan.xlist.XListView;

public class NearlyStuActivity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener,XListView.IXListViewListener{
    private Button topBackButt;//返回键
    private TextView topTitleTv;//界面主题
    private XListView nearStuLv;//刷新列表
    private NearStuAdapter nearStuAdapter;
    private ViewHolder holder = null;
    private Handler mHandler;//刷新回调函数
    private ArrayList<HashMap<String, Object>> lstNearStuItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_nearlystu);
        initView();
        nearStuAdapter=new NearStuAdapter(this);
        nearStuLv.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
        nearStuLv.setPullRefreshEnable(false);
        nearStuLv.setXListViewListener(this);
        lstNearStuItem = new ArrayList<HashMap<String, Object>>();
        nearStuLv.setOnItemClickListener(this);
        mHandler = new Handler();
        getData();
        nearStuLv.setAdapter(nearStuAdapter);
    }

    private void initView() {
        topBackButt=(Button)this.findViewById(R.id.btn_top_back);
        topBackButt.setOnClickListener(this);
        topTitleTv=(TextView)this.findViewById(R.id.tv_top_btntext);
        topTitleTv.setText("周边学霸");
        nearStuLv=(XListView)this.findViewById(R.id.lv_nearly_stu);
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

    }
    private ArrayList<HashMap<String, Object>> getData() {

        HashMap<String, Object> map;
        for(int i=1;i<5;i++)
        {
            map= new HashMap<String, Object>();
            map.put("nearitemImgIv", R.mipmap.ic_ceshi_head);
            map.put("nearitemNameTv", "学霸就是我");
            map.put("nearitemLabelTv", "学霸");
            map.put("nearitemDistTv", "0.9km");
            map.put("nearitemSexTv", "女");
            map.put("nearitemAgeTv", "20岁");
            map.put("nearitemCollegeTv", "电子工程与自动化");
            map.put("nearitemGradeTv", "2013级");
            map.put("nearitemSignTv", "想远离挂科吗。。。那就来找我吧！哈哈哈哈哈");
            lstNearStuItem.add(map);
        }
        return lstNearStuItem;
    }
    //ViewHolder静态类
    public static class ViewHolder    {
        //头像
        public RoundImageView nearitemImgIv;
        //姓名
        public TextView nearitemNameTv;
        //标签
        public TextView nearitemLabelTv;
        //距离
        public TextView nearitemDistTv;
        //性别
        public TextView nearitemSexTv;
        //年龄
        public TextView nearitemAgeTv;
        //学院
        public TextView nearitemCollegeTv;
        //年级
        public TextView nearitemGradeTv;
        //签名
        public TextView nearitemSignTv;
    }
    public class NearStuAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;
        private NearStuAdapter(Context context)
        {
            //根据context上下文加载布局，即this
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            // 在此适配器中所代表的数据集中的条目数
            return lstNearStuItem.size();
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
                convertView = mInflater.inflate(R.layout.item_nearly_stu,null);
                holder.nearitemImgIv = (RoundImageView)convertView.findViewById(R.id.iv_nearitem_img);
                holder.nearitemNameTv = (TextView)convertView.findViewById(R.id.tv_nearitem_name);
                holder.nearitemLabelTv = (TextView)convertView.findViewById(R.id.tv_nearitem_label);
                holder.nearitemDistTv = (TextView)convertView.findViewById(R.id.tv_nearitem_dist);
                holder.nearitemSexTv = (TextView)convertView.findViewById(R.id.tv_nearitem_sex);
                holder.nearitemAgeTv = (TextView)convertView.findViewById(R.id.tv_nearitem_age);
                holder.nearitemCollegeTv = (TextView)convertView.findViewById(R.id.tv_nearitem_college);
                holder.nearitemGradeTv = (TextView)convertView.findViewById(R.id.tv_nearitem_grade);
                holder.nearitemSignTv = (TextView)convertView.findViewById(R.id.tv_nearitem_sign);
                //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
                convertView.setTag(holder);
            }
            else{
                holder = (ViewHolder)convertView.getTag();
            }
            holder.nearitemImgIv.setImageResource((Integer) lstNearStuItem.get(position).get("nearitemImgIv"));
            holder.nearitemNameTv.setText((String) lstNearStuItem.get(position).get("nearitemNameTv"));
            holder.nearitemLabelTv.setText((String) lstNearStuItem.get(position).get("nearitemLabelTv"));
            holder.nearitemDistTv.setText((String) lstNearStuItem.get(position).get("nearitemDistTv"));
            holder.nearitemSexTv.setText((String) lstNearStuItem.get(position).get("nearitemSexTv"));
            holder.nearitemAgeTv.setText((String) lstNearStuItem.get(position).get("nearitemAgeTv"));
            holder.nearitemCollegeTv.setText((String) lstNearStuItem.get(position).get("nearitemCollegeTv"));
            holder.nearitemGradeTv.setText((String) lstNearStuItem.get(position).get("nearitemGradeTv"));
            holder.nearitemSignTv.setText((String) lstNearStuItem.get(position).get("nearitemSignTv"));
            return convertView;
        }
    }
    /** 停止刷新， */
    private void onLoad() {
        nearStuLv.stopRefresh();
        nearStuLv.stopLoadMore();
        nearStuLv.setRefreshTime("刚刚");
    }

    // 刷新
    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
                nearStuLv.setAdapter(nearStuAdapter);
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
                nearStuAdapter.notifyDataSetChanged();
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
