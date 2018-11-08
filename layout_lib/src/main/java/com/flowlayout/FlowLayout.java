package com.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.example.flowlayout.R;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {
    
    @Documented
    @IntDef(value = {
            Gravity.CENTER,
            Gravity.LEFT,
            Gravity.RIGHT
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface FlowLayoutGravity {
    }
    
    private List<List<View>> mAllViews;
    private List<Integer> mLineHeight, mLineWidth;
    private List<View> lineViews;
    
    private int mGravity;
    
    public FlowLayout(Context context) {
        this(context, null);
    }
    
    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        int gravity = ta.getInt(R.styleable.FlowLayout_flow_gravity, 0);
        switch (gravity) {
            case -1:
                mGravity = Gravity.LEFT;
                break;
            case 0:
                mGravity = Gravity.CENTER;
                break;
            case 1:
                mGravity = Gravity.RIGHT;
                break;
        }
        ta.recycle();
    }
    
    public void setFlowGravity(@FlowLayoutGravity int gravity) {
        mGravity = gravity;
    }
    
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mAllViews == null) {
            mAllViews = new ArrayList<>();
        } else mAllViews.clear();
        if (mLineHeight == null) {
            mLineHeight = new ArrayList<>();
        } else mLineHeight.clear();
        if (lineViews == null) {
            lineViews = new ArrayList<>();
        } else lineViews.clear();
        if (mLineWidth == null) {
            mLineWidth = new ArrayList<>();
        } else mLineWidth.clear();
        
        int width = getWidth();
        
        int lineWidth = 0;
        int lineHeight = 0;
        
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (view.getVisibility() == View.GONE) {
                continue;
            }
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) view.getLayoutParams();
            int childWidth = view.getMeasuredWidth();
            int childHeight = view.getMeasuredHeight();
            
            if (childWidth + lineWidth + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin > width - getPaddingLeft() - getPaddingRight()) {
                mLineHeight.add(lineHeight);
                mAllViews.add(lineViews);
                mLineWidth.add(lineWidth);
                
                lineWidth = 0;
                lineHeight = childHeight + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
                lineViews = new ArrayList<View>();
            }
            lineWidth += childWidth + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + marginLayoutParams.topMargin
                    + marginLayoutParams.bottomMargin);
            lineViews.add(view);
        }
        mLineHeight.add(lineHeight);
        mLineWidth.add(lineWidth);
        mAllViews.add(lineViews);
        
        
        int left = getPaddingLeft();
        int top = getPaddingTop();
        
        int lineNum = mAllViews.size();
        
        
        for (int i = 0; i < lineNum; i++) {
            lineViews = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);
            
            // set gravity
            int currentLineWidth = this.mLineWidth.get(i);
            switch (this.mGravity) {
                case Gravity.LEFT:
                    left = getPaddingLeft();
                    break;
                case Gravity.CENTER:
                    left = (width - currentLineWidth) / 2 + getPaddingLeft();
                    break;
                case Gravity.RIGHT:
                    left = width - currentLineWidth + getPaddingLeft();
                    break;
            }
            
            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                
                MarginLayoutParams lp = (MarginLayoutParams) child
                        .getLayoutParams();
                
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();
                
                child.layout(lc, tc, rc, bc);
                
                left += child.getMeasuredWidth() + lp.leftMargin
                        + lp.rightMargin;
            }
            top += lineHeight;
        }
    }
    
    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }
    
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }
    
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
    
    /***
     * 测量
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        
        
        int width = 0;//总宽度
        int height = 0;//总长度
        
        int lineWidth = 0;//行宽
        int lineHeight = 0;//行高
        
        int childCount = getChildCount();
        
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            int visibility = view.getVisibility();
            //不显示
            if (visibility == View.GONE) {
                if (i == childCount - 1) {
                    //最后一个了
                    width = Math.max(lineWidth, width);
                    height += lineHeight;
                }
                //不显示的话，不做测量
                continue;
            }
            //显示的都做测量
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams layoutParams = (MarginLayoutParams) view.getLayoutParams();
            int childWidth = view.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int childHeight = view.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                width = Math.max(width, lineWidth);
                lineWidth = childWidth;
                height += lineHeight;
                lineHeight = childHeight;
            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            
            if (i == childCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }
        setMeasuredDimension(
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom()
        );
    }
}
