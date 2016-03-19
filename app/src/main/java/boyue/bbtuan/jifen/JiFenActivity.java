package boyue.bbtuan.jifen;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import boyue.bbtuan.R;
import boyue.bbtuan.utils.AppUtils;
import boyue.bbtuan.view.CustomGridView;

public class JiFenActivity extends Activity implements View.OnClickListener{
    private TextView top_title;
    private Button btn_top_back;
    private ImageView jifenCetIv;//四六级
    private ImageView jifenIeltsIv;//雅思
    private ImageView jifenKaoyanIv;//四考研
    private ImageView jifenKeshiIv;//科试
    private ImageView jifenJishuIv;//技术
    private ImageView jifenFenleiIv;//分类
    private TextView signinProTv;//积分规则
    private CustomGridView jifenGridview;
    private ScrollView sv;//滚动加载更多
    private LinearLayout llLoad;
    private JifenAdapter adapter;
    private ViewHolder holder = null;
    private ArrayList<HashMap<String, Object>> lstImageItem;
    private Dialog loadbar = null;
    private AppUtils appUtils=new AppUtils(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_jifen);
        initView();
    }
    private void initView() {
        top_title = (TextView) findViewById(R.id.tv_top_btntext);
        btn_top_back = (Button) findViewById(R.id.btn_top_back);
        top_title.setText("积分商城");
        btn_top_back.setOnClickListener(this);
        sv = (ScrollView) findViewById(R.id.jifenoneGridViewScroll);
        llLoad = (LinearLayout) findViewById(R.id.jifenoneGridViewScrollLinear);
        jifenCetIv=(ImageView)findViewById(R.id.iv_jifen_cet);
        jifenCetIv.setOnClickListener(this);
        jifenIeltsIv=(ImageView)findViewById(R.id.iv_jifen_ielts);
        jifenIeltsIv.setOnClickListener(this);
        jifenKaoyanIv=(ImageView)findViewById(R.id.iv_jifen_kaoyan);
        jifenKaoyanIv.setOnClickListener(this);
        jifenKeshiIv=(ImageView)findViewById(R.id.iv_jifen_keshi);
        jifenKeshiIv.setOnClickListener(this);
        jifenJishuIv=(ImageView)findViewById(R.id.iv_jifen_jishu);
        jifenJishuIv.setOnClickListener(this);
        jifenFenleiIv=(ImageView)findViewById(R.id.iv_jifen_fenlei);
        jifenFenleiIv.setOnClickListener(this);
        jifenGridview=(CustomGridView)findViewById(R.id.gridview_jifen);
        signinProTv=(TextView)findViewById(R.id.tv_signin_pro);
        signinProTv.setOnClickListener(this);
        jifenGridview.setHorizontalSpacing(appUtils.dp2px(15));
        jifenGridview.setVerticalSpacing(appUtils.dp2px(15));
        lstImageItem = new ArrayList<HashMap<String, Object>>();
        adapter = new JifenAdapter(this);
        getData();
        jifenGridview.setAdapter(adapter);
        sv.setOnTouchListener(new View.OnTouchListener() {

            private int lastY = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    lastY = sv.getScrollY();
                    if (lastY == (llLoad.getHeight() - sv.getHeight())) {
                        initProgressDialog();
                        addMoreData();
                    }
                }
                return false;
            }
        });
    }
    private void addMoreData(){
        new Thread(new Runnable(){

            @Override
            public void run() {
                Bundle b = new Bundle();
                try{
                    Thread.sleep(2000);
                    b.putBoolean("addMoreData", true);
                }catch(Exception e){}finally{
                    Message msg = handler.obtainMessage();
                    msg.setData(b);
                    handler.sendMessage(msg);
                }
            }}).start();
    }
    private Handler handler = new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(Message msg) {
            try{
                if(msg.getData().containsKey("grid")){
                    adapter = new JifenAdapter(JiFenActivity.this);
                    jifenGridview.setAdapter(adapter);
                }else if(msg.getData().containsKey("addMoreData")){
                    getData();
                    adapter = new JifenAdapter(JiFenActivity.this);
                    jifenGridview.setAdapter(adapter);
                    close();
                }
            }catch(Exception e){}
        }};
    private ArrayList<HashMap<String, Object>> getData() {

        HashMap<String, Object> map;
        for(int i=0;i<8;i++)
        {
            map = new HashMap<String, Object>();
            map.put("jifenGridItemIv", R.mipmap.ic_jifen_temp);
            map.put("jifenGridItemNumTv", "200分");
            map.put("jifenGridItemNameTv", "突破口语学习的6次瓶颈期");
            map.put("jifenGridItemIntroTv", "学习就是长跑，度过艰难期，一切豁然开朗");
            lstImageItem.add(map);
        }
        return lstImageItem;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_top_back:
                finish();
                break;
            case R.id.tv_signin_pro:
                Intent intent1=new Intent(JiFenActivity.this,JifenProActivity.class);
                intent1.putExtra("protcl","jifen");
                startActivity(intent1);
                break;
            case R.id.iv_jifen_cet:
                break;
            case R.id.iv_jifen_ielts:
                break;
            case R.id.iv_jifen_kaoyan:
                break;
            case R.id.iv_jifen_keshi:
                break;
            case R.id.iv_jifen_jishu:
                break;
            case R.id.iv_jifen_fenlei:
                break;
            default:
                break;
        }
    }
    //ViewHolder静态类
    public static class ViewHolder    {
        public ImageView jifenGridItemColIv;
        private TextView jifenGridItemNameTv;
        public TextView jifenGridItemIntroTv;
        public TextView jifenGridItemNumTv;
    }
    public class JifenAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;
        private JifenAdapter(Context context)
        {
            //根据context上下文加载布局，即this
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            // 在此适配器中所代表的数据集中的条目数
            return lstImageItem.size();
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
                convertView = mInflater.inflate(R.layout.activity_jifen_grid_item,null);
                holder.jifenGridItemColIv = (ImageView)convertView.findViewById(R.id.iv_jifengriditem);
                holder.jifenGridItemIntroTv = (TextView)convertView.findViewById(R.id.tv_jifengriditemintro);
                holder.jifenGridItemNumTv = (TextView)convertView.findViewById(R.id.tv_jifengriditemnum);
                holder.jifenGridItemNameTv = (TextView)convertView.findViewById(R.id.tv_jifengriditemname);
                //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
                convertView.setTag(holder);
            }
            else{
                holder = (ViewHolder)convertView.getTag();
            }
            holder.jifenGridItemColIv.setBackgroundResource((Integer)lstImageItem.get(position).get("jifenGridItemIv"));
            holder.jifenGridItemIntroTv.setText((String) lstImageItem.get(position).get("jifenGridItemIntroTv"));
            holder.jifenGridItemNumTv.setText((String)lstImageItem.get(position).get("jifenGridItemNumTv"));
            holder.jifenGridItemNameTv.setText((String)lstImageItem.get(position).get("jifenGridItemNameTv"));
            return convertView;
        }
    }
    public void initProgressDialog() {

        if (loadbar == null) {
            loadbar = new Dialog(JiFenActivity.this, R.style.load_dialog);
            LayoutInflater mInflater = JiFenActivity.this.getLayoutInflater();
            View v = mInflater.inflate(R.layout.anim_load, null);
            loadbar.setContentView(v);
            loadbar.setCancelable(false);
            loadbar.show();
        } else {
            loadbar.show();
        }
    }
    public void close() {
        if (loadbar != null) {
            if (loadbar.isShowing()) {
                loadbar.dismiss();
            }
        }
    }
}
