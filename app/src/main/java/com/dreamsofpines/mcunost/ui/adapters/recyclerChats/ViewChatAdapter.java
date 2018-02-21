package com.dreamsofpines.mcunost.ui.adapters.recyclerChats;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.help.menu.ChatDialog;

import java.util.List;

/**
 * Created by ThePupsick on 17.01.2018.
 */

public class ViewChatAdapter extends RecyclerView.Adapter<ViewChatHolder> {

    private Activity mActivity;
    private List<ChatDialog> chats;

    public static OnItemClickListener listener;


    public interface OnItemClickListener{
        void onItemClick(View itemView, int position);
    }

    public static void setOnItemListener(OnItemClickListener listener) {
        ViewChatAdapter.listener = listener;
    }


    public Activity getActivity() {
        return mActivity;
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    public List<ChatDialog> getChats() {
        return chats;
    }

    public void setChats(List<ChatDialog> chats) {
        this.chats = chats;
    }

    @Override
    public ViewChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mActivity);
        View view = layoutInflater.inflate(R.layout.item_chat_document,parent,false);
        return new ViewChatHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewChatHolder holder, int position) {
        ChatDialog chat = chats.get(position);
        holder.bindView(chat);
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }
}
