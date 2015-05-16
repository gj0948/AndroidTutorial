package com.jack.android.tutorial.ui.custom.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.jack.android.tutorial.R;

import java.util.ArrayList;

/**
 * A simple arc menu implementation.
 */
public class ArcMenu extends ViewGroup implements View.OnClickListener {
    private static final int ANIMATION_DURATION = 300;
    private Position position;
    private boolean isMenuOpen;
    private float radius;
    private int childCount;
    private View mainMenu;
    private RotateAnimation rotateAnimation;
    private AnimationSet scaleUpAnimation;
    private AnimationSet scaleDownAnimation;
    private ArrayList<TranslateAnimation> openTranslateAnimations;
    private OnMenuItemClickListener onMenuItemClickListener;

    public ArcMenu(Context context) {
        this(context, null);
    }

    public ArcMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ArcMenu, defStyleAttr, 0);
        position = Position.values()[typedArray.getInt(R.styleable.ArcMenu_position, Position.BOTTOM_RIGHT.ordinal())];
        radius = typedArray.getDimension(R.styleable.ArcMenu_radius, radius);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        childCount = getChildCount();
        if (childCount > 0) {
            mainMenu = getChildAt(0);
        }

        isMenuOpen = false;
        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(ANIMATION_DURATION);

        scaleDownAnimation = new AnimationSet(true);
        scaleDownAnimation.addAnimation(new ScaleAnimation(1.0f, 0f, 1.0f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f));
        scaleDownAnimation.addAnimation(new AlphaAnimation(1.0f, 0f));
        scaleDownAnimation.setDuration(ANIMATION_DURATION);

        scaleUpAnimation = new AnimationSet(true);
        scaleUpAnimation.addAnimation(new ScaleAnimation(1.0f, 3.0f, 1.0f, 3.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f));
        scaleUpAnimation.addAnimation(new AlphaAnimation(1.0f, 0f));
        scaleUpAnimation.setDuration(ANIMATION_DURATION);

        openTranslateAnimations = new ArrayList<>();
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            if (childCount > 0) {
                layoutMainMenu();
            }

            if (childCount > 1) {
                openTranslateAnimations.clear();
                openTranslateAnimations.add(new TranslateAnimation(0, 0, 0, 0));
                double deltaAngle = Math.PI / 2 / (childCount - 2);
                for (int i = 1; i < childCount; i++) {
                    View childView = getChildAt(i);
                    childView.setTag(i);
                    childView.setOnClickListener(this);

                    childView.setVisibility(isMenuOpen ? VISIBLE : GONE);
                    int deltaLeft = (int) (radius * Math.sin(deltaAngle * (i - 1)));
                    int deltaTop = (int) (radius * Math.cos(deltaAngle * (i - 1)));
                    layoutMenuItem(childView, deltaLeft, deltaTop);

                    TranslateAnimation animation = new TranslateAnimation(mainMenu.getX() - childView.getX(), 0, mainMenu.getY() - childView.getY(), 0);
                    animation.setDuration(ANIMATION_DURATION);
                    openTranslateAnimations.add(animation);
                }
            }
        }
    }

    private void layoutMainMenu() {
        mainMenu = getChildAt(0);
        mainMenu.setOnClickListener(this);

        int left = 0;
        int top = 0;
        int width = mainMenu.getMeasuredWidth();
        int height = mainMenu.getMeasuredHeight();

        switch (position) {
            case TOP_LEFT:
                left = getPaddingLeft();
                top = getPaddingTop();
                break;

            case TOP_RIGHT:
                left = getMeasuredWidth() - getPaddingRight() - width;
                top = getPaddingTop();
                break;

            case BOTTOM_LEFT:
                left = getPaddingLeft();
                top = getMeasuredHeight() - getPaddingBottom() - height;
                break;

            case BOTTOM_RIGHT:
                left = getMeasuredWidth() - getPaddingRight() - width;
                top = getMeasuredHeight() - getPaddingBottom() - height;
                break;

            default:
                break;
        }

        mainMenu.layout(left, top, left + width, top + height);
    }

    private void layoutMenuItem(View childView, int deltaLeft, int deltaTop) {
        if (childView == null) {
            return;
        }

        int left = 0;
        int top = 0;
        int width = childView.getMeasuredWidth();
        int height = childView.getMeasuredHeight();

        switch (position) {
            case TOP_LEFT:
                left = getPaddingLeft() + deltaLeft;
                top = getPaddingTop() + deltaTop;
                break;

            case TOP_RIGHT:
                left = getMeasuredWidth() - getPaddingRight() - width - deltaLeft;
                top = getPaddingTop() + deltaTop;
                break;

            case BOTTOM_LEFT:
                left = getPaddingLeft() + deltaLeft;
                top = getMeasuredHeight() - getPaddingBottom() - height - deltaTop;
                break;

            case BOTTOM_RIGHT:
                left = getMeasuredWidth() - getPaddingRight() - width - deltaLeft;
                top = getMeasuredHeight() - getPaddingBottom() - height - deltaTop;
                break;

            default:
                break;
        }

        childView.layout(left, top, left + width, top + height);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        toggleMenu(v);
        if (v != getChildAt(0) && onMenuItemClickListener != null) {
            onMenuItemClickListener.onMenuItemClick(v, (int) v.getTag());
        }
    }

    public void toggleMenu(View clickedMenu) {
        isMenuOpen = !isMenuOpen;
        rotateMainMenu();
        if (isMenuOpen) {
            translateMenuItems();
        } else {
            scaleMenuItems(clickedMenu);
        }
    }

    private void rotateMainMenu() {
        if (mainMenu != null) {
            mainMenu.startAnimation(rotateAnimation);
        }
    }

    private void translateMenuItems() {
        for (int i = 1; i < childCount; i++) {
            View childView = getChildAt(i);
            childView.setVisibility(VISIBLE);
            childView.startAnimation(openTranslateAnimations.get(i));
        }
    }

    private void scaleMenuItems(View clickedMenu) {
        for (int i = 1; i < childCount; i++) {
            View childView = getChildAt(i);
            if (clickedMenu == childView) {
                childView.startAnimation(scaleUpAnimation);
            } else {
                childView.startAnimation(scaleDownAnimation);
            }
            childView.setVisibility(GONE);
        }
    }

    public enum Position {
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
    }

    public interface OnMenuItemClickListener {
        void onMenuItemClick(View view, int position);
    }
}
