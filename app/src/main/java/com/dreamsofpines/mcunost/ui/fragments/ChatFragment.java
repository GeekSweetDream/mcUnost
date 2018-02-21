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
import android.widget.TextView;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.network.api.RequestSender;
import com.dreamsofpines.mcunost.data.storage.help.menu.Message;
import com.dreamsofpines.mcunost.ui.adapters.recyclerChat.ChatAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThePupsick on 17.01.2018.
 */

public class ChatFragment extends Fragment {

    private RecyclerView rec;
    private int idOrder;
    private Bundle mBundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat,container,false);
        TextView title = (TextView) getActivity().findViewById(R.id.title_tour);
        title.setText("Чат");
        rec = (RecyclerView) view.findViewById(R.id.recycler_chat);
        mBundle = getArguments();
        idOrder = mBundle.getInt("idOrder");
        MessTask messTask = new MessTask();
        messTask.execute();
        return view;
    }


    private void updateUI(List<Message> lists){
        if(null == rec.getLayoutManager()) {
            LinearLayoutManager lr = new LinearLayoutManager(getActivity());
            rec.setLayoutManager(lr);
        }
        ChatAdapter mAdapter = new ChatAdapter();
        mAdapter.setContext(getContext());
        mAdapter.setMessages(lists);
        mAdapter.notifyDataSetChanged();
        rec.setAdapter(mAdapter);
    }


    private class MessTask extends AsyncTask<Void,Void,Boolean> {
        private List<Message> mMessageList;

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean success = true;
            mMessageList = new ArrayList<>();
            String responce = RequestSender.GetMessages(getContext(), idOrder);
            try {
                JSONObject js = new JSONObject(responce);
                String resultResponce = js.getString("result");
                if (resultResponce.equalsIgnoreCase("success")) {
                    JSONArray messJsonArray = js.getJSONArray("data");
                    int length = messJsonArray.length();
                    for (int i = 0; i < length; ++i) {
                        JSONObject jsMess = messJsonArray.getJSONObject(i);
                        Message mess = new Message();
                        mess.setMess(jsMess.getString("text"));
                        mess.setLongDate(jsMess.getLong("date"));
                        mess.setDoc(jsMess.getInt("isDoc") == 1);
                        if (mess.isDoc()) mess.setUrl(jsMess.getString("url"));
                        mMessageList.add(mess);
                    }
                } else {
                    success = false;
                    Log.i("MessFrag:", "Bad answer! Error message:" + js.getString("mess"));
                    /* error answer (add log.i())*/
                }
            } catch (JSONException e) {
                success = false;
                Log.i("MessFrag:", "JsonExeption from parsing json pack_excur! Error message:" + e.getMessage());
            } catch (Exception e) {
                success = false;
                Log.i("MessFrag:", " Error! Error message:" + e.getMessage());
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                if (mMessageList.size() == 0) {
                    // то не видно recycler
                } else {
                    rec.setVisibility(View.VISIBLE);
                    updateUI(mMessageList);
                }
            }
        }

    }
}
