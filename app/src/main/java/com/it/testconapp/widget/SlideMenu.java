package com.it.testconapp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by 骚涛 on 2016/7/7.
 */
public class SlideMenu extends ViewGroup {
    private Scroller scroller;
    private int drawX;//获取当前按下的x轴坐标
    private int touchSlop;
    private final int SCREEN_MENU = 0; // 菜单界面
    private final int SCREEN_MAIN = 1; // 主界面
    private int currentScreen = SCREEN_MAIN; // 当前屏幕显示的界面, 默认为: 主界面

    public SlideMenu(Context context) {
        super(context);
        init();
    }



    public SlideMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init() {
         scroller = new Scroller(getContext());
        touchSlop = ViewConfiguration.get(getContext()).getTouchSlop();
    }

    /**
     *
     * @param b
     * @param i 左边=0
     * @param i1 上边=0
     * @param i2 右边=屏幕的宽度
     * @param i3 下边=屏幕的高度
     */
    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        //主界面的位置放置在屏幕的左上角
        View mainView = getChildAt(1);
        int mainViewWidth = mainView.getMeasuredWidth();
        int mainViewHeight = mainView.getMeasuredHeight();
        mainView.layout(i,i1,i2,i3);
        View menuView = getChildAt(0);
        int menuHeight = menuView.getMeasuredHeight();
        int menuWidth = menuView.getMeasuredWidth();
        menuView.layout(-menuWidth,i1,0,i3);
    }

    /**
     *
     * @param widthMeasureSpec 填充屏幕的宽
     * @param heightMeasureSpec 填充屏幕的高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
      /*  // 测量菜单的宽和高. 宽: 240dip, 高: 填充屏幕
        View menuView = getChildAt(0); // 获取菜单对象
        menuView.measure(menuView.getLayoutParams().width, heightMeasureSpec);

        // 测量主界面的宽和高. 宽: 填充屏幕, 高: 填充屏幕
        View mainView = getChildAt(1);
        mainView.measure(widthMeasureSpec, heightMeasureSpec);*/
        //上面代码可以一句代码代替
        measureChildren(widthMeasureSpec,heightMeasureSpec);
    }

    /**
     * 事件分发机制
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //只有横着滑动时候才拦截
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                drawX= (int) ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
               int moveX= (int) ev.getX();
                int diff= Math.abs(drawX-moveX);
                if (diff>touchSlop){
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int scrollX;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
               drawX= (int)event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX= (int)event.getX();
                int deltaX = drawX - moveX;
                //判断给定当前的增量移动后, 是否能够超出边界.
                scrollX = getScrollX()+deltaX;
                if(scrollX<-getChildAt(0).getMeasuredWidth()){
                    //当前超出了左边界, 应该设置为在菜单的左边界位置上.
                    scrollTo(-getChildAt(0).getMeasuredWidth(),0);
                }else if(scrollX>0){
                    // 当前超出了右边界, 应该设置为0
                    scrollTo(0, 0);
                }else{
                    scrollBy(deltaX,0);
                }
                drawX=moveX;
                break;
            case MotionEvent.ACTION_UP:
                // 获取菜单宽度的一半
                int center = -getChildAt(0).getMeasuredWidth() / 2;
                scrollX = getScrollX(); // 当前屏幕左上角的值

                if(scrollX > center) {
                    System.out.println("当前切换到主界面");
                    currentScreen = SCREEN_MAIN;
                } else {
                    System.out.println("当前切换到菜单界面");
                    currentScreen = SCREEN_MENU;
                }
                switchScreen();
                break;
        }
        return true;
    }

    private void switchScreen() {
        int startX=getScrollX();//开始的位置
        int dx=0;//增量值
        if(currentScreen==SCREEN_MAIN){
            //scrollTo(0,0); 太快了 设置切换状态的动画
            dx=0-startX;
        }else if(currentScreen == SCREEN_MENU) {
            //scrollTo(-getChildAt(0).getMeasuredWidth(), 0);
            dx = -getChildAt(0).getMeasuredWidth() - startX;
        }
        int duration = Math.abs(dx) * 10;
        if(duration > 1000) {
            duration = 1000;
        }
        scroller.startScroll(startX, 0, dx, 0, duration);
        // 刷新当前控件, 会引起onDraw方法的调用.
        invalidate();
    }

    @Override
    public void computeScroll() {
        //当scroller数据模拟完毕时,不应该继续递归
        //反之,如果正在进行数据模拟,继续递归
        if(scroller.computeScrollOffset()){//当前还是正在模拟数据中
            //把当前scroller正在模拟的数据取出来,使用scrollTo方法切换屏幕
            int currX = scroller.getCurrX();

            scrollTo(currX,0);
            invalidate();// 在触发当前方法, 相当于递归.
        }
    }

    /**
     * 是否显示菜单
     * @return true 显示菜单, false 不显示
     */
    public boolean isShowMenu(){
        return currentScreen==SCREEN_MENU;
    }
    /**
     * 隐藏菜单
     */
    public void hideMenu(){
        currentScreen=SCREEN_MAIN;
        switchScreen();
    }
    /**
     * 显示菜单
     */
    public void showMenu() {
        currentScreen = SCREEN_MENU;
        switchScreen();
    }
}
