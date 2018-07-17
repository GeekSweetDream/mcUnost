package com.dreamsofpines.mcunost.ui.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.squareup.picasso.Picasso;

public class ViewItemConstructor extends LinearLayout {

    public ViewItemConstructor(Context context) {
        super(context);
        init(context);
    }

    public ViewItemConstructor(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        setTitleFromAttr(attrs);
    }

    public ViewItemConstructor(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        setTitleFromAttr(attrs);
    }

    private void setTitleFromAttr(AttributeSet attr){
        TypedArray typedArray = getContext().obtainStyledAttributes(attr,R.styleable.ViewItemConstructor);
        setTitle(typedArray.getString(R.styleable.ViewItemConstructor_title));
        setVisibleIcon(typedArray.getBoolean(R.styleable.ViewItemConstructor_withIcon,true));
        setIcon(typedArray.getString(R.styleable.ViewItemConstructor_iconPath));
        typedArray.recycle();
    }

    private TextView title;
    private TextView text;
    private ImageView icon;
    private CardView cardView;
    private String pathToIcon;
    private final int ORANGE_COLOR = getResources().getColor(R.color.md_orange_500);
    private final int BLACK_COLOR = getResources().getColor(R.color.md_black_1000);

    private void init(Context context){
        inflate(context, R.layout.view_item_constructor,this);
        title = (TextView) findViewById(R.id.title);
        text = (TextView) findViewById(R.id.text);
        icon = (ImageView) findViewById(R.id.icon);
        cardView = (CardView) findViewById(R.id.lin);
    }

    public void setText(String text){
        this.text.setText(text);
        this.text.setTextColor(ORANGE_COLOR);
    }

    public void cleanText(){
        text.setText("Выбрать");
        text.setTextColor(BLACK_COLOR);
    }

    public void setTitle(String text){
        this.title.setText(text);
    }

    public void setIcon(String path){
        if(path != null) {
            pathToIcon = path;
            icon.setVisibility(VISIBLE);
            icon.setImageResource(getDrawableIdFromFileName(getContext(), path));
        }else{
            icon.setVisibility(GONE);
        }
    }

    public String getTitle(){
        return title.getText().toString();
    }

    public String getText(){
        return text.getText().toString();
    }

    public String getPathToIcon(){
        return pathToIcon;
    }

    public void setVisibleIcon(boolean visible){
        icon.setVisibility(visible?VISIBLE:GONE);
    }

    public void setMargins(int left, int top, int right, int bottom){
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) cardView.getLayoutParams();
        params.setMargins(left,top, right,bottom);
        cardView.setLayoutParams(params);
    }

    public void setUseCompatPadding(boolean fl){
        cardView.setUseCompatPadding(fl);
    }

    private int getDrawableIdFromFileName(Context context, String nameOfDrawable) {
        return context.getResources().getIdentifier(nameOfDrawable, "mipmap", context.getPackageName());
    }

}
