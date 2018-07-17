package com.dreamsofpines.mcunost.ui.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;

import net.igenius.customcheckbox.CustomCheckBox;

public class ViewTextWithCheckbox extends LinearLayout {
    public ViewTextWithCheckbox(Context context) {
        super(context);
        init(context);
    }

    public ViewTextWithCheckbox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        getInfoFromAttrs(attrs);
    }

    public ViewTextWithCheckbox(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        getInfoFromAttrs(attrs);
    }

    private void getInfoFromAttrs(AttributeSet attrs){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,R.styleable.ViewTextWithCheckbox);
        setTitle(typedArray.getString(R.styleable.ViewTextWithCheckbox_title));
        setCheckBox(typedArray.getBoolean(R.styleable.ViewTextWithCheckbox_checked,false));
        typedArray.recycle();
    }


    private TextView title;
    private CustomCheckBox mCheckBox;
    private OnClickCheckBoxListener listener;

    public interface OnClickCheckBoxListener{
        void onClick(View view, boolean on);
    }

    public void setOnClickCheckBoxListener(OnClickCheckBoxListener listener){
        this.listener = listener;
    }


    private void init(Context context){
        inflate(context, R.layout.view_text_with_checkbox,this);
        title = (TextView) findViewById(R.id.title);
        mCheckBox = (CustomCheckBox) findViewById(R.id.checkBox);
        mCheckBox.setOnClickListener((view -> {
            mCheckBox.setChecked(!mCheckBox.isChecked());
            if(listener!=null) listener.onClick(this,mCheckBox.isChecked());
        }));
    }

    public void setTitle(String str){
        title.setText(str);
    }

    public String getTitle(){
        return title.getText().toString();
    }

    public void setCheckBox(boolean on){
        mCheckBox.setChecked(on);
    }

    public boolean isChecked(){
        return mCheckBox.isChecked();
    }

}
