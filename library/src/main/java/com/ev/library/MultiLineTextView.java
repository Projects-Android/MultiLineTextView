package com.ev.library;


import android.content.Context;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * created by EV on 2019/10/28
 * a TextView supports every truncation in multi line mode
 * just like:
 * This is a sentence just
 * for test i... multi lines.
 */
public class MultiLineTextView extends AppCompatTextView {

    public MultiLineTextView(Context context) {
        super(context);
    }

    public MultiLineTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MultiLineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (!TextUtils.isEmpty(getText())) {
            int lineWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
            if (lineWidth <= 0) {
                return;
            }
            String text = ellipsize(getPaint(), getText(), getMaxLines(), lineWidth);
            if (null != text) {
                setText(text);
            }
        }
    }

    private String ellipsize(TextPaint tp, CharSequence cs, int line, int lineWidth) {
        StaticLayout layout = new StaticLayout(
                cs,
                tp,
                lineWidth,
                Layout.Alignment.ALIGN_NORMAL,
                1.0f,
                0.0f,
                true);

        int count = layout.getLineCount();
        if (count > line) {
            StringBuilder sb = new StringBuilder();
            int start = layout.getLineStart(line - 1);
            sb.append(cs.subSequence(0, start));
            CharSequence lastLine = TextUtils.ellipsize(cs.subSequence(start, cs.length()), tp, lineWidth, getEllipsize(), false, null);
            sb.append(lastLine);
            return sb.toString();
        }

        return null;
    }
}
