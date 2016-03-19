package boyue.bbtuan.library;


import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import boyue.bbtuan.R;
import boyue.bbtuan.xlist.XListView;

public class LibraryActivity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener,XListView.IXListViewListener{
    private Button topBackButt;//返回键
    private TextView topTitleTv;//界面主题
    private XListView libraryStuLv;//刷新列表
    private LibraryAdapter libraryAdapter;
    private ViewHolder holder = null;
    private Handler mHandler;//刷新回调函数
    private ProgressDialog mProgressDialog= null;
    private static final int MAX_PROGRESS = 100;
    private int downloadCount=0;
    private int downloadCurrent=0;
    private ArrayList<HashMap<String, Object>> lstLibraryStuItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_library);
        initView();
        libraryAdapter=new LibraryAdapter(this);
        libraryStuLv.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
        libraryStuLv.setPullRefreshEnable(false);
        libraryStuLv.setXListViewListener(this);
        lstLibraryStuItem = new ArrayList<HashMap<String, Object>>();
        libraryStuLv.setOnItemClickListener(this);
        mHandler = new Handler();
        getData();
        libraryStuLv.setAdapter(libraryAdapter);
    }
    private void initView() {
        topBackButt=(Button)this.findViewById(R.id.btn_top_back);
        topBackButt.setOnClickListener(this);
        topTitleTv=(TextView)this.findViewById(R.id.tv_top_btntext);
        topTitleTv.setText("小文库");
        libraryStuLv=(XListView)this.findViewById(R.id.lv_library_book);
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
            map.put("libraryitemImgIv", R.mipmap.ic_download);
            map.put("libraryitemNameTv", "高等数学A1");
            map.put("libraryitemLinkTv", "www.doubi.com");
            map.put("libraryitemNumTv", "12344556已下载");
            lstLibraryStuItem.add(map);
        }
        return lstLibraryStuItem;
    }
    //ViewHolder静态类
    public static class ViewHolder    {
        //下载按钮
        public ImageView libraryitemImgIv;
        //课程名字
        public TextView libraryitemNameTv;
        //课程资料下载链接
        public TextView libraryitemLinkTv;
        //课程下载人数
        public TextView libraryitemNumTv;
    }
    public class LibraryAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;
        private LibraryAdapter(Context context)
        {
            //根据context上下文加载布局，即this
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            // 在此适配器中所代表的数据集中的条目数
            return lstLibraryStuItem.size();
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
                convertView = mInflater.inflate(R.layout.item_library_list,null);
                holder.libraryitemImgIv = (ImageView)convertView.findViewById(R.id.iv_library_down);
                holder.libraryitemNameTv = (TextView)convertView.findViewById(R.id.tv_library_obj);
                holder.libraryitemLinkTv = (TextView)convertView.findViewById(R.id.tv_library_link);
                holder.libraryitemNumTv = (TextView)convertView.findViewById(R.id.tv_library_num);

                //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
                convertView.setTag(holder);
            }
            else{
                holder = (ViewHolder)convertView.getTag();
            }
            holder.libraryitemImgIv.setImageResource((Integer) lstLibraryStuItem.get(position).get("libraryitemImgIv"));
            holder.libraryitemNameTv.setText((String) lstLibraryStuItem.get(position).get("libraryitemNameTv"));
            holder.libraryitemLinkTv.setText((String) lstLibraryStuItem.get(position).get("libraryitemLinkTv"));
            holder.libraryitemNumTv.setText((String) lstLibraryStuItem.get(position).get("libraryitemNumTv"));
            holder.libraryitemImgIv.setOnClickListener(new gViewButton(position));
            return convertView;
        }
    }
    //下载
    private class gViewButton implements View.OnClickListener{
        public int iPosition = -1;
        public gViewButton(int position)
        {
            this.iPosition = position;
        }

        @Override
        public void onClick(View v) {
            showSetDialog();
            HttpUtils httpUtils=new HttpUtils();
            HttpHandler httpHandler=httpUtils.download("http://v.meituan.net/mobile/app/Android/group-320_2-meituan_.apk?channel=meituan&subchannel=", "/sdcard/BoYue/qq.apk", true, true, new RequestCallBack<File>() {
                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    super.onLoading(total, current, isUploading);
                   // Toast.makeText(LibraryActivity.this,current + "/" + total,Toast.LENGTH_SHORT).show();
                    downloadCount=(int) (current * 100 / total);
                    while((downloadCount-downloadCurrent)/10>=1) {
                        int tempCount=(downloadCount-downloadCurrent)/10;
                        while (tempCount>0){
                            try {
                                // 更新进度条的进度,可以在子线程中更新进度条进度
                                //mProgressDialog.setProgress();
                                mProgressDialog.incrementProgressBy(10);
                                // dialog.incrementSecondaryProgressBy(10)//二级进度条更新方式

                            } catch (Exception e) {
                            }
                            tempCount--;
                        }

                        downloadCurrent+=10;
                    }
                    //downloadCurrent=(int) (current * 100 / total);
                            // 在进度条走完时删除Dialog
                            if ((int) (current/total)==1){
                                mProgressDialog.cancel();
                            }
                }

                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {

                }

                @Override
                public void onFailure(HttpException e, String s) {

                }

            });
        }
    }

    private void showSetDialog() {
        mProgressDialog= new ProgressDialog(LibraryActivity.this);
        mProgressDialog.setTitle("正在下载中。。。");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //mProgressDialog .setCanceledOnTouchOutside(false);
        mProgressDialog.setMax(MAX_PROGRESS);
        mProgressDialog.setProgress(0);
        mProgressDialog.show();
    }

    /** 停止刷新， */
    private void onLoad() {
        libraryStuLv.stopRefresh();
        libraryStuLv.stopLoadMore();
        libraryStuLv.setRefreshTime("刚刚");
    }

    // 刷新
    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
                libraryStuLv.setAdapter(libraryAdapter);
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
                libraryAdapter.notifyDataSetChanged();
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
