package boyue.bbtuan.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class CustomListView extends ListView {
    /**
     * pull state,pull up or pull down;PULL_UP_STATE or PULL_DOWN_STATE
     */
    int mLastMotionY ;
    boolean bottomFlag;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        //阻止父类拦截事件
        if(bottomFlag){
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        return super.onInterceptTouchEvent(ev);
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int y = (int) ev.getRawY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 首先拦截down事件,记录y坐标
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                // deltaY > 0 是向下运动,< 0是向上运动
                int deltaY = y - mLastMotionY;

                if(deltaY>0){
                    View child = getChildAt(0);
                    if(child!=null){
                        if (getFirstVisiblePosition() == 0
                                && child.getTop() == 0) {
                            bottomFlag = false;
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }

                        int top = child.getTop();
                        int padding = getPaddingTop();
                        if (getFirstVisiblePosition() == 0
                                && Math.abs(top - padding) <= 8) {//这里之前用3可以判断,但现在不行,还没找到原因
                            bottomFlag = false;
                            getParent().requestDisallowInterceptTouchEvent(false);

                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }



        return super.onTouchEvent(ev);
    }

    public void setBottomFlag(boolean flag){
        bottomFlag = flag;
    }
//        @Override
//        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//            int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
//                    View.MeasureSpec.AT_MOST);
//            super.onMeasure(widthMeasureSpec, expandSpec);
//        }

        public CustomListView(Context context) {
            super(context);
        }

        public CustomListView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public CustomListView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }


}
