package com.neo1125.numberkeyview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class NumberKeyView extends LinearLayout implements View.OnClickListener {

    private static int rows = 4;
    private static int cols = 3;

    private List<NumberKeyButton> keys = new ArrayList<>();
    private NumberKeyOnClickListener linstener;
    private float keyScale = 1;
    private String keyTextFontFamily = "";
    private int keyBackgroundColor = Color.TRANSPARENT;
    private int keyHighlightColor = keyBackgroundColor;
    private int keyTextColor = Color.WHITE;
    private int keyTextSize = 0;
    private int keyBorderColor = Color.TRANSPARENT;
    private int keyBorderSize = 0;
    private int drawableLeft = 0;
    private int keyClearBackgroundColor = keyBackgroundColor;
    private int keyEmptyBackgroundColor = keyBackgroundColor;
    private KeyStyle keyStyle = KeyStyle.square;
    private KeyClearPosition keyClearPosition;

    public enum KeyStyle {
        square, circle
    }

    public enum KeyClearPosition {
        left, right
    }

    public enum Key {
        key0(0), key1(1), key2(2), key3(3), key4(4), key5(5), key6(6), key7(7), key8(8), key9(9),
        custom(99), clear(-1), empty(-2);

        private int value;

        Key(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            if (this.getValue() != 99 && this.getValue() < 0) {
                if (this.getValue() == -1 || this.getValue() == -2)
                    return "";
                else
                    return "cus";
            }
            return String.format("%d", this.getValue());
        }
    }

    public NumberKeyView(Context context) {
        super(context);
        init(context, null);
    }

    public NumberKeyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NumberKeyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setOnNumberKeyOnClickListener(NumberKeyOnClickListener listener) {
        this.linstener = listener;
    }

    @Override
    public void onClick(View view) {
        NumberKeyButton button = (NumberKeyButton) view;
        if (linstener != null && button != null) {
            linstener.onClick(button.getKey());
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        updateKeys();
    }

    private void init(Context context, AttributeSet attrs) {

        setOrientation(VERTICAL);

        TypedArray typed = context.obtainStyledAttributes(attrs, R.styleable.NumberKeyView, 0, 0);
        int style = typed.getInt(R.styleable.NumberKeyView_keyStyle, 1);
        keyScale = typed.getFloat(R.styleable.NumberKeyView_keyScale, keyScale);
        keyTextFontFamily = typed.getString(R.styleable.NumberKeyView_keyTextFontFamily);
        keyBackgroundColor = typed.getColor(R.styleable.NumberKeyView_keyBackgroundColor, keyBackgroundColor);
        keyHighlightColor = typed.getColor(R.styleable.NumberKeyView_keyHighlightColor, keyHighlightColor);
        keyTextColor = typed.getColor(R.styleable.NumberKeyView_keyTextColor, keyTextColor);
        keyTextSize = typed.getDimensionPixelSize(R.styleable.NumberKeyView_keyTextSize, keyTextSize);
        keyBorderColor = typed.getColor(R.styleable.NumberKeyView_keyBorderColor, keyBorderColor);
        keyBorderSize = typed.getDimensionPixelSize(R.styleable.NumberKeyView_keyBorderSize, keyBorderSize);
        drawableLeft = typed.getResourceId(R.styleable.NumberKeyView_keyClearIcon, drawableLeft);
        keyClearBackgroundColor = typed.getColor(R.styleable.NumberKeyView_keyClearBackgroundColor, keyBackgroundColor);
        int clearPosition = typed.getInt(R.styleable.NumberKeyView_keyClearPosition, 1);
        keyEmptyBackgroundColor = typed.getColor(R.styleable.NumberKeyView_keyEmptyBackgroundColor, keyBackgroundColor);
        keyStyle = (style == 1) ? KeyStyle.square : KeyStyle.circle;
        keyClearPosition = (clearPosition == 1) ? KeyClearPosition.left : KeyClearPosition.right;

        int numberKey = 1;
        for (int row = 0; row < rows; row++) {
            LinearLayout layout = new LinearLayout(context, attrs);
            layout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
            layout.setBackgroundColor(Color.TRANSPARENT);
            layout.setOrientation(HORIZONTAL);
            addView(layout);

            for (int col = 0; col < cols; col++) {
                NumberKeyButton button = new NumberKeyButton(context, attrs);
                button.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
                button.setScale(keyScale);
                button.setTextFontface(keyTextFontFamily);
                button.setOnClickListener(this);
                if (row == 3) {
                    if (col == 0)
                        button.setKey(Key.empty);
                    if (col == 1)
                        button.setKey(Key.key0);
                    if (col == 2) {
                        button.setKey(Key.clear);
                        if (drawableLeft > 0) {
                            button.setIcon(drawableLeft);
                        }
                    }
                } else {
                    button.setKey(getKey(numberKey));
                }

                layout.addView(button);
                keys.add(button);
                numberKey++;
            }
        }
    }

    private Key getKey(int num) {
        switch (num) {
            case 1: return Key.key1;
            case 2: return Key.key2;
            case 3: return Key.key3;
            case 4: return Key.key4;
            case 5: return Key.key5;
            case 6: return Key.key6;
            case 7: return Key.key7;
            case 8: return Key.key8;
            case 9: return Key.key9;
            case 0: return Key.key0;
            case -1: return Key.clear;
            case -2: return Key.empty;
            default: return Key.custom;
        }
    }

    private void updateKeys() {

        for (NumberKeyButton button : keys) {
            button.setCircle((keyStyle == KeyStyle.circle));
            button.setBackgroundColor(keyBackgroundColor);
            button.setPressedColor(keyHighlightColor);
            button.setBorderColor(keyBorderColor);
            button.setBorderSize(keyBorderSize);
            button.setTextColor(keyTextColor);
            button.setTextSize(keyTextSize);

            if (button.getKey() == Key.clear) {
                button.setBackgroundColor(keyClearBackgroundColor);
            }

            if (button.getKey() == Key.empty) {
                button.setBackgroundColor(keyEmptyBackgroundColor);
            }
        }

        if (keyClearPosition == KeyClearPosition.left) {
            NumberKeyButton clearKey = keys.get(9);
            clearKey.setKey(Key.clear);

            if (drawableLeft > 0) {
                clearKey.setIcon(drawableLeft);
            }
            NumberKeyButton emptyKey = keys.get(11);
            emptyKey.setKey(Key.empty);
            emptyKey.setIcon(0);
        } else {
            NumberKeyButton clearKey = keys.get(9);
            clearKey.setKey(Key.empty);

            NumberKeyButton emptyKey = keys.get(11);
            emptyKey.setKey(Key.clear);
            if (drawableLeft > 0) {
                emptyKey.setIcon(drawableLeft);
            }
        }
    }

    public void setKeyStyle(KeyStyle style) {
        keyStyle = style;
    }

    public void setKeyScale(float scale) {
        keyScale = scale;
    }

    public void setKeyBackgroundColor(int color) {
        keyBackgroundColor = color;
    }

    public void setKeyHighlightColor(int color) {
        keyHighlightColor = color;
    }

    public void setKeyClearPosition(KeyClearPosition position) {
        keyClearPosition = position;
    }

    public void setKeyTextColor(int color) {
        keyTextColor = color;
    }

    public void setKeyTextSize(int size) {
        keyTextSize = size;
    }

    public void setKeyBorderColor(int color) {
        keyBorderColor = color;
    }

    public void setKeyBorderSize(int size) {
        keyBorderSize = size;
    }

    public void setKeyClearIcon(int icon) {
        drawableLeft = icon;
    }

    public void setKeyClearBackgroundColor(int color) {
        keyClearBackgroundColor = color;
    }

    public void setKeyEmptyBackgroundColor(int color) {
        keyEmptyBackgroundColor = color;
    }

}