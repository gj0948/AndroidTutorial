package com.jack.android.tutorial.ui.custom.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple implementation for flow layout.
 */
public class FlowLayout extends ViewGroup {
    private List<List<View>> allViews = new ArrayList<>();
    private List<Integer> lineHeights = new ArrayList<>();

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        // for wrap_content use
        int width = 0;
        int height = 0;

        // store width & height for each row
        int lineWidth = 0;
        int lineHeight = 0;

        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            int childWidth = childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            // new line
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                width = Math.max(width, lineWidth);
                lineWidth = childWidth;

                height += lineHeight;
                lineHeight = childHeight;
            }
            // in the same line
            else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }

            if (i == count - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }

        setMeasuredDimension(modeWidth == MeasureSpec.AT_MOST ? width + getPaddingLeft() + getPaddingRight() : sizeWidth,
                modeHeight == MeasureSpec.AT_MOST ? height + getPaddingTop() + getPaddingBottom() : sizeHeight);
    }

    @Override
    protected void onLayout(boolean b, int left, int top, int right, int bottom) {
        allViews.clear();
        lineHeights.clear();

        int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;
        int maxHeightInLine = 0;

        List<View> lineViews = new ArrayList<>();
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            int childWidth = childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            // new line
            if (lineWidth + childWidth > width - getPaddingLeft() - getPaddingRight()) {
                allViews.add(lineViews);
                lineViews = new ArrayList<>();

                lineWidth = 0;
                lineHeights.add(lineHeight);
                lineHeight += maxHeightInLine;
                maxHeightInLine = 0;
            }

            lineViews.add(childView);
            lineWidth += childWidth;

            maxHeightInLine = Math.max(maxHeightInLine, childHeight);
        }

        lineHeights.add(lineHeight);
        allViews.add(lineViews);

        // set child view position
        int lineLeft;
        int lineTop;

        int lineCount = allViews.size();
        for (int i = 0; i < lineCount; i++) {
            lineViews = allViews.get(i);

            lineLeft = getPaddingLeft();
            lineTop = lineHeights.get(i) + getPaddingTop();

            for (int j = 0; j < lineViews.size(); j++) {
                View childView = lineViews.get(j);

                if (childView.getVisibility() == View.GONE) {
                    continue;
                }

                MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
                int cl = lineLeft + lp.leftMargin;
                int ct = lineTop + lp.topMargin;
                int cr = cl + childView.getMeasuredWidth();
                int cb = ct + childView.getMeasuredHeight();

                // layout for child view
                childView.layout(cl, ct, cr, cb);

                lineLeft += childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
