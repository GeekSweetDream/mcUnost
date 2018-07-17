package com.dreamsofpines.mcunost.ui.customView;

import android.animation.StateListAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;

public class SettingFieldView extends LinearLayout {

    public SettingFieldView(Context context) {
        super(context);
        init(context);
    }

    public SettingFieldView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        setTitleFromAttrs(attrs);
    }

    public SettingFieldView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        setTitleFromAttrs(attrs);
    }

    public SettingFieldView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
        setTitleFromAttrs(attrs);
    }

    private void setTitleFromAttrs(AttributeSet attrs){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SettingFieldView);
        setTitle(typedArray.getString(R.styleable.SettingFieldView_title));
        typedArray.recycle();
    }

    private TextView title;
    private EditText field;
    private String textBefore;
    private OnChangeTextListener listener;
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!textBefore.equals(field.getText().toString())){
                if(listener!=null) listener.change(true);
            }else{
                if(listener!=null) listener.change(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public interface OnChangeTextListener{
        void change(boolean change);
    }

    public void setListener(OnChangeTextListener listener) {
        this.listener = listener;
    }

    private void init(Context context){
        inflate(context, R.layout.view_setting_field,this);
        title = (TextView) findViewById(R.id.title);
        field = (EditText) findViewById(R.id.edit_text);
        setup();
    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    public void setTextField(String text){
        textBefore = text;
        field.setText(textBefore);
    }

    public String getTextFromField(){
        return textBefore;
    }


    private void setup(){
        field.addTextChangedListener(mTextWatcher);
        field.setOnFocusChangeListener((view,state)->{
            if(!state && field.getText().toString().equals("")){
                field.setText(textBefore);
            }
        });
    }



}
