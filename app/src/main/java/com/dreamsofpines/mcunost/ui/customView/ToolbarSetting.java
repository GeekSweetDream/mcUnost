package com.dreamsofpines.mcunost.ui.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.utils.ImageUtils;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;
import com.squareup.picasso.Picasso;

public class ToolbarSetting extends RelativeLayout{
    public ToolbarSetting(Context context) {
        super(context);
        init(context);
    }

    public ToolbarSetting(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ToolbarSetting(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ToolbarSetting(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private ImageView avatar;
    private TextView name;
    private TextView email;
    private int state;

    private void init(Context context){
        inflate(context, R.layout.view_toolbar_setting,this);
        state = GlobalPreferences.getPrefStateAvatar(context);
        bindView();
        settingView(context);
        loadImage(context,state);
    }

    private void bindView(){
        avatar = (ImageView) findViewById(R.id.avatar);
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
    }

    public void updateUser(){
        name.setText(GlobalPreferences.getPrefAddUser(getContext()) == 1?
                GlobalPreferences.getPrefUserName(getContext())
                :"Имя");
        email.setText(GlobalPreferences.getPrefAddUser(getContext()) == 1?
                GlobalPreferences.getPrefUserEmail(getContext())
                :"Почта");
    }
    private void settingView(Context context){
        avatar.setOnClickListener((view)->{
            ++state;
            GlobalPreferences.setPrefStateAvatar(context,state);
            loadImage(context,state);
        });
    }

    private void loadImage(Context context,int state){
        Picasso.with(context).load("file:///android_asset/"+ ImageUtils.getNameAvatars(state)).into(avatar);
    }




}
