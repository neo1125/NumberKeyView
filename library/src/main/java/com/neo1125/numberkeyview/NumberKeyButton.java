package com.neo1125.numberkeyview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


public class NumberKeyButton extends FrameLayout {

    private TextView titleTextView;
    private ImageView iconImageView;

    private boolean isCircle = false;
    private NumberKeyView.Key key;
    private Paint backgroundPaint;
    private int defaultColor = Color.TRANSPARENT;
    private int pressedColor = -1;
    private int borderColor = Color.TRANSPARENT;
    private int borderSize = 0;
    private int icon = -1;

    private int centerX;
    private int centerY;
    private int radius;
    private float scale = 1;

    public NumberKeyButton(Context context) {
        super(context);
        init(context, null);
    }

    public NumberKeyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NumberKeyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isCircle) {
            canvas.drawCircle(centerX, centerY, radius, backgroundPaint);
            if (borderSize > 0) {
                Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                borderPaint.setStyle(Paint.Style.STROKE);
                borderPaint.setColor(borderColor);
                borderPaint.setStrokeWidth(borderSize);
                canvas.drawCircle(centerX, centerY, radius, borderPaint);
            }
        } else {
            canvas.drawPaint(backgroundPaint);
            if (borderSize > 0) {
                Paint bottomPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                bottomPaint.setStyle(Paint.Style.FILL);
                bottomPaint.setColor(borderColor);
                bottomPaint.setStrokeWidth(borderSize/2);
                canvas.drawLine(0, 0, getWidth(), 0, bottomPaint);
                canvas.drawLine(0, getHeight(), getWidth(), getHeight(), bottomPaint);
                canvas.drawLine(0, 0, 0, getHeight(), bottomPaint);
                canvas.drawLine(getWidth(), 0, getWidth(), getHeight(), bottomPaint);
            }
        }

        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int offsetX = (w - (int) (w * scale));
        int offsetY = (h - (int) (h * scale));

        w = (w - offsetX);
        h = (h - offsetY);
        centerX = (w / 2) + (offsetX/2);
        centerY = (h / 2) + (offsetY/2);
        radius = Math.min(w, h) / 2;
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        if (backgroundPaint != null) {
            backgroundPaint.setColor(pressed ? pressedColor : defaultColor);
        }

        invalidate();
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor((isCircle) ? Color.TRANSPARENT : color);
        defaultColor = color;
        pressedColor = (pressedColor == -1) ? color : pressedColor;
        backgroundPaint.setColor(defaultColor);
    }

    private void init(Context context, AttributeSet attrs) {
        setFocusable(true);
        setClickable(true);

        titleTextView = new TextView(context, attrs);
        titleTextView.setBackgroundColor(Color.TRANSPARENT);
        titleTextView.setGravity(Gravity.CENTER);
        addView(titleTextView);

        iconImageView = new ImageView(context, attrs);
        iconImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        iconImageView.setVisibility(GONE);
        addView(iconImageView);

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setStyle(Paint.Style.FILL);
    }

    public void setKey(NumberKeyView.Key key) {
        this.key = key;
        titleTextView.setText(key.toString());
        setEnabled(true);
        if (key == NumberKeyView.Key.empty)
            setEnabled(false);
    }

    public NumberKeyView.Key getKey() {
        return key;
    }

    public void setCircle(boolean isCircle) {
        this.isCircle = isCircle;
    }

    public void setTextColor(int color) {
        titleTextView.setTextColor(color);
    }

    public void setTextSize(int size) {
        titleTextView.setTextSize(size);
    }

    public void setTextFontface(String familyName) {
        if (familyName != null && !familyName.equals(""))
            titleTextView.setTypeface(Typeface.create(familyName, Typeface.NORMAL));
    }

    public void setPressedColor(int color) {
        pressedColor = color;
    }

    public void setBorderColor(int color) {
        borderColor = color;
    }

    public void setBorderSize(int size) {
        borderSize = size;
    }

    public void setIcon(int drawable) {
        icon = drawable;
        iconImageView.setImageResource(drawable);
        iconImageView.setVisibility(VISIBLE);
        titleTextView.setVisibility(GONE);
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
