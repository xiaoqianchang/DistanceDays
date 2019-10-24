package com.adups.distancedays.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.Toast;

import com.adups.distancedays.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 1.EditText输入框禁止输入Emoji表情符;
 * 2.输入长度限制;
 * 3.特殊字符输入限制.
 * <p>
 * Created by Chang.Xiao on 2016/9/22.
 *
 * @version 1.0
 */
public class ForbidInputEmojiEditText extends EditText {

    // 输入表情前的光标位置
    private int cursorPosition;
    // 输入表情前EditText中的文本
    private String inputAfterText;
    // 是否重置了EditText的内容
    private boolean resetText;
    // 限制输入的最大长度
    private int mMaxLenth;
    // toast文案
    private String mMaxLengthToast;

    private Context mContext;

    public ForbidInputEmojiEditText(Context context) {
        super(context);
        this.mContext = context;
        initEditText();
    }

    public ForbidInputEmojiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initialize(context, attrs);
        initEditText();
    }

    public ForbidInputEmojiEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initialize(context, attrs);
        initEditText();
    }

    private void initialize(Context context, AttributeSet attrs) {
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.ForbidInputEmojiEditText);

        // 获取自定义属性和默认值
        mMaxLenth = mTypedArray.getInteger(R.styleable.ForbidInputEmojiEditText_maxLength, 16);
        mMaxLengthToast = mTypedArray.getString(R.styleable.ForbidInputEmojiEditText_maxLengthToast);

        mTypedArray.recycle();
    }

    /**
     * 初始化edittext 控件
     */
    private void initEditText() {
        addTextChangedListener(new TextWatcher() {

            private int cou = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {
                if (!resetText) {
                    cursorPosition = getSelectionEnd();
                    //注意事项
                    // 这里用s.toString()而不直接用s是因为如果用s，
                    // 那么，inputAfterText和s在内存中指向的是同一个地址，s改变了，
                    // inputAfterText也就改变了，那么表情过滤就失败了
                    inputAfterText = s.toString();
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (!resetText) {
                        // Emoji表情符判断
                        if (count >= 2) {//表情符号的字符长度最小为2
                            int _start = cursorPosition;
                            int _end = cursorPosition + count;
                            if (s.length() >= _end && _start < _end) {
                                CharSequence input = s.subSequence(cursorPosition, cursorPosition + count);
                                if (containsEmoji(input.toString())) {
                                    resetText = true;
                                    Toast.makeText(mContext, "不支持输入Emoji表情符号，请重新输入", Toast.LENGTH_SHORT).show();
                                    //是表情符号就将文本还原为输入表情符号之前的内容
                                    setText(inputAfterText);
                                    CharSequence text = getText();
                                    if (text instanceof Spannable) {
                                        Spannable spanText = (Spannable) text;
                                        Selection.setSelection(spanText, text.length());
                                    }
                                }
                            }
                        } else if (count > 0) {
                            CharSequence input = s.subSequence(cursorPosition, cursorPosition + count);
                            String str = stringFilter(input.toString());
                            if (!input.toString().equals(str)) { // 包含特殊字符
                                resetText = true;
                                setText(inputAfterText);
                            }
                        }
                        // 考虑最大限制
                        cou = length();
                        if (cou > mMaxLenth) {
                            resetText = true;
                            setText(inputAfterText);
                            if (!TextUtils.isEmpty(mMaxLengthToast)) {
                                Toast.makeText(mContext, mMaxLengthToast, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, "最大只能" + mMaxLenth + "个字符", Toast.LENGTH_SHORT).show();
                            }
                        }
                        setSelection(length());
                    } else {
                        resetText = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { // 如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }

    /**
     * 字符串过滤
     *
     * @param str
     * @return
     */
    public String stringFilter(String str) {
        String regEx = "[/\\:*?<>|\"\n\t]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }
}
