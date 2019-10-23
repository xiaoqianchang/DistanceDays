package com.adups.distancedays.view;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * 自动缩小字体的TextView
 * <p>
 * Created by Chang.Xiao on 2019/10/23.
 *
 * @version 1.0
 */
public class ScalableTextView extends AppCompatTextView {

    private Paint paint;
    private float minSize;
    private float maxSize; // 字体默认设置的大小

    public ScalableTextView(Context context) {
        super(context);
        init();
    }

    public ScalableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScalableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.set(getPaint());
        maxSize = getTextSize();
        if (maxSize < 11.0f) {
            maxSize = 20.0f;
        }
        this.minSize = 5.0f;
    }

    private void changeFontSize(String text, int textWidth, int textHeight) {
        float trySize = maxSize;
        if (textWidth > 0) {
            int availableWidth = (textWidth - getPaddingLeft()) - getPaddingRight();
            this.paint.setTextSize(trySize);
            while (true) {
                if (trySize <= minSize || this.paint.measureText(text) <= ((float) availableWidth)) {
                    break;
                }
                trySize --;
                if (trySize <= minSize) {
                    trySize = minSize;
                    break;
                }
                this.paint.setTextSize(trySize);
            }
            super.setTextSize(0, trySize);
        }
        if (textHeight > 0) {
            int availableHeight = (textHeight - getPaddingTop()) - getPaddingBottom();
            Paint.FontMetrics fm = this.paint.getFontMetrics();
            while (true) {
                if (trySize <= minSize || Math.ceil((double) (fm.descent - fm.ascent)) <= ((double) availableHeight)) {
                    break;
                }
                trySize -= 1.0f;
                if (trySize <= minSize) {
                    trySize = minSize;
                    break;
                } else {
                    this.paint.setTextSize(trySize);
                    fm = this.paint.getFontMetrics();
                }
            }
            super.setTextSize(0, trySize);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw) {
            changeFontSize(getText().toString(), w, h);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        changeFontSize(text.toString(), getMeasuredWidth(), getMeasuredHeight());
    }

    public float getMinTextSize() {
        return minSize;
    }

    public void setMinTextSize(int minTextSize) {
        this.minSize = (float) minTextSize;
    }

    public float getMaxTextSize() {
        return maxSize;
    }

    public void setMaxTextSize(int minTextSize) {
        this.maxSize = (float) minTextSize;
    }
}
