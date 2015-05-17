package com.jack.android.tutorial.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jack.android.tutorial.R;

/**
 * A simple implementation for Scratch Card
 */
public class ScratchCard extends View {
    private volatile boolean isComplete;

    private Canvas canvas;

    private Paint outerPaint;
    private Path path;
    private int strokeWidth;
    private Bitmap bitmapForeground;
    private Drawable drawableForeground;

    private Paint innerPaint;
    private Rect textBound;
    private String text;
    private int textSize;
    private int textColor;

    private int lastX;
    private int lastY;

    private CalculateAreaRunnable calculateAreaRunnable;

    private Handler clientHandler;

    private OnScratchCompleteListener onScratchCompleteListener;

    public ScratchCard(Context context) {
        this(context, null);
    }

    public ScratchCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScratchCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScratchCard, defStyleAttr, 0);
        text = typedArray.getString(R.styleable.ScratchCard_text);
        textSize = typedArray.getDimensionPixelSize(R.styleable.ScratchCard_textSize, 16);
        textColor = typedArray.getColor(R.styleable.ScratchCard_textColor, Color.BLACK);
        drawableForeground = typedArray.getDrawable(R.styleable.ScratchCard_foreground);
        typedArray.recycle();

        initScratchCard();
    }

    public void setOnScratchCompleteListener(OnScratchCompleteListener onScratchCompleteListener) {
        clientHandler = new Handler();
        this.onScratchCompleteListener = onScratchCompleteListener;
    }

    public String getText() {
        return text;
    }

    private void initScratchCard() {
        isComplete = false;

        outerPaint = new Paint();
        path = new Path();
        strokeWidth = 32;

        innerPaint = new Paint();
        textBound = new Rect();

        calculateAreaRunnable = new CalculateAreaRunnable();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        bitmapForeground = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmapForeground);

        drawableForeground.setBounds(0, 0, width, height);
        drawableForeground.draw(canvas);

        setupOuterPaint();
        setupInnerPaint();
    }

    private void setupOuterPaint() {
        outerPaint.setColor(Color.BLACK);
        outerPaint.setAntiAlias(true);
        outerPaint.setDither(true);
        outerPaint.setStrokeJoin(Paint.Join.ROUND);
        outerPaint.setStrokeCap(Paint.Cap.ROUND);
        outerPaint.setStyle(Paint.Style.STROKE);
        outerPaint.setStrokeWidth(strokeWidth);
    }

    private void setupInnerPaint() {
        innerPaint.setStyle(Paint.Style.FILL);
        innerPaint.setTextSize(textSize);
        innerPaint.setColor(textColor);
        innerPaint.getTextBounds(text, 0, text.length(), textBound);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                path.moveTo(lastX, lastY);
                break;

            case MotionEvent.ACTION_MOVE:
                int dx = Math.abs(x - lastX);
                int dy = Math.abs(y - lastY);

                if (dx > 3 && dy > 3) {
                    path.lineTo(x, y);
                }

                lastX = x;
                lastY = y;
                break;

            case MotionEvent.ACTION_UP:
                if (!isComplete) {
                    new Thread(calculateAreaRunnable).start();
                }
                break;

            default:
                break;
        }

        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawText(text, getWidth() / 2 - textBound.width() / 2, getHeight() / 2 + textBound.height() / 2, innerPaint);
        if (!isComplete) {
            drawPath();
            canvas.drawBitmap(bitmapForeground, 0, 0, null);
        }
    }

    private void drawPath() {
        outerPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        canvas.drawPath(path, outerPaint);
    }

    public interface OnScratchCompleteListener {
        void onScratchComplete();
    }

    private class CalculateAreaRunnable implements Runnable {
        @Override
        public void run() {
            int width = getWidth();
            int height = getHeight();

            float wipeArea = 0;
            float area = width * height;

            int[] pixels = new int[width * height];
            bitmapForeground.getPixels(pixels, 0, width, 0, 0, width, height);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int index = i + j * width;
                    if (pixels[index] == 0) {
                        wipeArea++;
                    }
                }
            }

            if (wipeArea > 0 && area > 0) {
                double percent = 1.0 * wipeArea / area;
                if (percent > 0.6 && !isComplete) {
                    isComplete = true;
                    postInvalidate();
                    if (onScratchCompleteListener != null) {
                        clientHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onScratchCompleteListener.onScratchComplete();
                            }
                        });
                    }
                }
            }
        }
    }
}
