package com.dreamsofpines.mcunost.ui.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;

public class InformTextView extends LinearLayout {
    public InformTextView(Context context) {
        super(context);
        init(context);
    }

    public InformTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        setTitleFromAttrs(attrs);
    }

    public InformTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        setTitleFromAttrs(attrs);
    }

    private void setTitleFromAttrs(AttributeSet attrs){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.InformTextView);
        setTitle(typedArray.getString(R.styleable.InformTextView_title));
        typedArray.recycle();
    }


    private TextView title;
    private TextView text;

    private void init(Context context){
        inflate(context, R.layout.view_inform_text,this);
        bindView();
    }


    private void bindView(){
        title = (TextView) findViewById(R.id.title);
        text = (TextView) findViewById(R.id.text);
    }

    public void setTitle(String str){
        title.setText(str);
    }

    public void setText(String str){
        text.setText(str);
    }


}
