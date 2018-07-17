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

public class HelloView extends RelativeLayout {
    public HelloView(Context context) {
        super(context);
        init(context);
    }

    public HelloView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HelloView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private TextView name;
    private TextView quantityNotification;
    private ImageView avatar;
    private int state;
    private int quantityNot = 0;

    private void init(Context context){
        if(GlobalPreferences.getPrefAddUser(context) == 1) {
            state = 0;
            inflate(context, R.layout.hello_view_with_icon,this);
            bindView();
            settup(context);
        }else{
            state = 1;
            inflate(context, R.layout.hello_view,this);
        }
    }

    private void bindView(){
        name = (TextView) findViewById(R.id.name);
        quantityNotification = (TextView) findViewById(R.id.mess);
        avatar = (ImageView) findViewById(R.id.icon);
    }

    private void settup(Context context){
        name.setText(GlobalPreferences.getPrefUserName(context));
        Picasso.with(context).load("file:///android_asset/" +
                ImageUtils.getNameAvatars(GlobalPreferences.getPrefStateAvatar(context))).into(avatar);
    }

    public void setQuantityNotification(int quantity){
        if(state == 0) quantityNotification.setText(String.valueOf(quantity));
    }

}
