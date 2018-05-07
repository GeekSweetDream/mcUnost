package com.dreamsofpines.mcunost.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.network.api.RequestSender;
import com.dreamsofpines.mcunost.data.storage.models.ChatDialog;
import com.dreamsofpines.mcunost.ui.adapters.recyclerChats.ViewChatAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by ThePupsick on 17.01.2018.
 */

public class ChatsRecyclerFragment extends Fragment {

    private RecyclerView rec;
    private RelativeLayout rel;
    private View err;
    private AVLoadingIndicatorView indView;
    private Button repeat;
    private List<ChatDialog> chats;
    public static OnClickRecyclerListener mListener;

    public interface OnClickRecyclerListener{
        void onClicked(Bundle bundle);
    }

    public static void setOnClickRecyclerListener(OnClickRecyclerListener listener){
        ChatsRecyclerFragment.mListener = listener;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_document,container,false);
        TextView title = (TextView) getActivity().findViewById(R.id.title_tour);
        title.setText("Сообщения");
        rec = (RecyclerView) view.findViewById(R.id.chat_recycler_view);
        rel = (RelativeLayout) view.findViewById(R.id.chat_recycler_empty);
        err = (View) view.findViewById(R.id.error_message);
        repeat = (Button) err.findViewById(R.id.category_resend_butt);
        indView = (AVLoadingIndicatorView) view.findViewById(R.id.chat_indicator);
        rec.setVisibility(View.GONE);
        rel.setVisibility(View.GONE);
        err.setVisibility(View.GONE);
        indView.show();
        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                indView.show();
                DialogTask dT = new DialogTask();
                dT.execute();
                err.setVisibility(View.GONE);
            }
        });
        DialogTask dT = new DialogTask();
        dT.execute();
        return view;
    }


    public void updateUI(final List<ChatDialog> chatDialogs){
        if(null == rec.getLayoutManager()) {
            LinearLayoutManager lr = new LinearLayoutManager(getActivity());
            lr.setReverseLayout(true);
            lr.setStackFromEnd(true);
            rec.setLayoutManager(lr);
        }
        final ViewChatAdapter mAdapter = new ViewChatAdapter();
        mAdapter.setChats(chatDialogs);
        mAdapter.setActivity(getActivity());
        mAdapter.setOnItemListener(new ViewChatAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Bundle mBundle = new Bundle();
                final int pos = Integer.valueOf(chatDialogs.get(position).getNumber());
                if(chatDialogs.get(position).isNewMes()) {
                    new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] objects) {
                            return RequestSender.SetWasReadById(getContext(), pos);
                        }
                    }.execute();
//                    GlobalPreferences.setMinusOneQuantityNewMess(getContext());
                }
                mBundle.putInt("idOrder",pos);
                if(mListener != null){
                    mListener.onClicked(mBundle);
                }
            }
        });
        rec.setAdapter(mAdapter);
        rec.setVisibility(View.VISIBLE);
    }

    private class DialogTask extends AsyncTask<Void, Void, Boolean> {

        private List<ChatDialog> list;

        @Override
        protected Boolean doInBackground(Void... voids) {
            Boolean success = true;
            list = new ArrayList<>();
            String response = RequestSender.GetDialogById(getContext());
            try {
                JSONObject js = new JSONObject(response);
                String resultResponce = js.getString("result");
                if (resultResponce.equalsIgnoreCase("success")) {
                    JSONArray JsonArray = js.getJSONArray("data");
                    Log.i("Myapp", "Count responce json ChatDialog " + JsonArray.length());
                    int length = JsonArray.length();
                    for(int i = 0; i < length; ++i) {
                        JSONObject ord = JsonArray.getJSONObject(i);
                        if(ord.getJSONObject("status").getString("status").equalsIgnoreCase("в обработке")
                                || ord.getJSONObject("status").getString("status").equalsIgnoreCase("Заказан")) {
                            Date date = new Date(ord.getLong("dateCreateOrder"));
                            SimpleDateFormat formatForDateNow = new SimpleDateFormat(" dd.MM.yyyy");
                            list.add(new ChatDialog(
                                    ord.getString("nameTour"),
                                    formatForDateNow.format(date),
                                    ord.getString("id"),
                                    ord.getInt("isRead")==0)
                            );
                        }
                    }
                }else{
                    success = false;
                    Log.i("ChatDialogFrag","Error answer! Error message: "+js.getString("mess"));
                }
            } catch (Exception e) {
                success = false;
                Log.i("ChatDialogFrag","Error answer! Error message: "+e.getMessage());
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if(!success){
                Toast.makeText(getContext(),"Ошибка сети, нет подключения к интернету!",Toast.LENGTH_LONG)
                        .show();
                indView.hide();
                err.setVisibility(View.VISIBLE);
                rec.setVisibility(View.GONE);
            }else {
                indView.hide();
                if(err.getVisibility()==View.VISIBLE){
                    err.setVisibility(View.GONE);
                }
                if(list.size() == 0) {
                    rel.setVisibility(View.VISIBLE);
                    rec.setVisibility(View.GONE);
                }else{
                    rel.setVisibility(View.GONE);
                    updateUI(list);
                }

            }
        }
    }

}
