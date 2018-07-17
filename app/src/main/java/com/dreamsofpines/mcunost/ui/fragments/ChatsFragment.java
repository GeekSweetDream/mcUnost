package com.dreamsofpines.mcunost.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.storage.mItemRecyclerView;
import com.dreamsofpines.mcunost.data.storage.models.DateItem;
import com.dreamsofpines.mcunost.data.storage.models.MessageTextItem;
import com.dreamsofpines.mcunost.ui.adapters.AdapterMessages;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatsFragment extends Fragment {

    private static ChatsFragment sChatsFragment;

    public static ChatsFragment getInstance() {
        if(sChatsFragment == null){
            sChatsFragment = new ChatsFragment();
        }
        return sChatsFragment;
    }

    private View view;
    private RecyclerView mRecyclerView;
    private AdapterMessages mAdapterMessages;
    private View toolbar;
    private TextView textToolbar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chats,container,false);
        bindView();
        init();
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        mAdapterMessages = new AdapterMessages(getActivity());
        mAdapterMessages.setItems(getItemsMess());
        mRecyclerView.setAdapter(mAdapterMessages);
        mRecyclerView.setLayoutManager(getLinearLayoutManager());
    }

    private LinearLayoutManager getLinearLayoutManager(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        return layoutManager;
    }


    private List<mItemRecyclerView> getItemsMess() {
        List<mItemRecyclerView> list = new ArrayList<>();
        list.add(getDateItem(new Date()));
        list.add(getMessageTextItem("Здравствуйте, ваш тур зарегистрирован, ожидайте, наш менеджер свяжется с вами!",new Date(),false,"",""));
        list.add(getMessageTextItem("Вот файл",new Date(),true,"Экскурсии","asdf"));
        list.add(getMessageTextItem("Ваш тур готов",new Date(),false,"",""));
        list.add(getMessageTextItem("Мы скоро вам позвоним, будьте на связи",new Date(),false,"",""));
        list.add(getMessageTextItem("Удачной поездки",new Date(),false,"",""));
        return list;
    }

    private DateItem getDateItem(Date date){
        return new DateItem().setDate(date);
    }

    private MessageTextItem getMessageTextItem(String mess,Date date,boolean isDock,String nameFile,String uri){
        return new MessageTextItem().setDate(date)
                .setMess(mess)
                .setDock(isDock)
                .setNameFile(nameFile)
                .setUri(uri);
    }


    private void init() {
        textToolbar.setText("Уведомления");
    }

    private void bindView() {
        toolbar = (View) view.findViewById(R.id.view_toolbar);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.mess_recycler);
        textToolbar = (TextView) toolbar.findViewById(R.id.text);
    }
}
