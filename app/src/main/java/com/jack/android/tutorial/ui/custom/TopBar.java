package com.jack.android.tutorial.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jack.android.tutorial.R;

/**
 * A simple top bar to provide a left button, right button, and center title.
 */
public class TopBar extends RelativeLayout {

    private Button leftButton;
    private Button rightButton;
    private TextView textViewTitle;

    private TopBarOnClickListener topBarOnClickListener;

    private LayoutParams leftLayoutParams;
    private LayoutParams rightLayoutParams;
    private LayoutParams titleLayoutParams;

    private int leftTextColor;
    private Drawable leftBackground;
    private String leftText;

    private int rightTextColor;
    private Drawable rightBackground;
    private String rightText;

    private float titleTextSize;
    private int titleTextColor;
    private String titleText;

    public TopBar(Context context) {
        this(context, null);
    }

    public TopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBar);

        leftTextColor = typedArray.getColor(R.styleable.TopBar_leftTextColor, Color.BLACK);
        leftBackground = typedArray.getDrawable(R.styleable.TopBar_leftBackground);
        leftText = typedArray.getString(R.styleable.TopBar_leftText);

        rightTextColor = typedArray.getColor(R.styleable.TopBar_rightTextColor, Color.BLACK);
        rightBackground = typedArray.getDrawable(R.styleable.TopBar_rightBackground);
        rightText = typedArray.getString(R.styleable.TopBar_rightText);

        titleTextSize = typedArray.getDimension(R.styleable.TopBar_titleTextSize, 16);
        titleTextColor = typedArray.getColor(R.styleable.TopBar_titleTextColor, Color.BLACK);
        titleText = typedArray.getString(R.styleable.TopBar_titleText);

        typedArray.recycle();

        leftButton = new Button(context);
        rightButton = new Button(context);
        textViewTitle = new TextView(context);

        leftButton.setTextColor(leftTextColor);
        if (leftBackground != null)
            leftButton.setBackground(leftBackground);
        leftButton.setText(leftText);

        rightButton.setTextColor(rightTextColor);
        if (rightBackground != null)
            rightButton.setBackground(rightBackground);
        rightButton.setText(rightText);

        textViewTitle.setTextSize(titleTextSize);
        textViewTitle.setTextColor(titleTextColor);
        textViewTitle.setText(titleText);
        textViewTitle.setGravity(Gravity.CENTER);

        if (getBackground() == null) {
            setBackgroundColor(0xFFF59563);
        }

        titleLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(textViewTitle, titleLayoutParams);

        leftLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        leftLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        addView(leftButton, leftLayoutParams);

        rightLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rightLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        addView(rightButton, rightLayoutParams);

        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (topBarOnClickListener != null) {
                    topBarOnClickListener.onLeftButtonClicked();
                }
            }
        });

        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (topBarOnClickListener != null) {
                    topBarOnClickListener.onRightButtonClicked();
                }
            }
        });
    }

    public void setTopBarClickListener(TopBarOnClickListener topBarOnClickListener) {
        this.topBarOnClickListener = topBarOnClickListener;
    }

    public interface TopBarOnClickListener {
        void onLeftButtonClicked();

        void onRightButtonClicked();
    }
}
