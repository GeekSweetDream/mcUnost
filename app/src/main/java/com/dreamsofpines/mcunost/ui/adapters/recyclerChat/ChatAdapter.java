package com.dreamsofpines.mcunost.ui.adapters.recyclerChat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamsofpines.mcunost.R;
import com.dreamsofpines.mcunost.data.network.api.RequestSender;
import com.dreamsofpines.mcunost.data.storage.help.menu.Message;


import java.io.File;
import java.util.List;


/**
 * Created by ThePupsick on 17.01.2018.
 */

public class ChatAdapter extends RecyclerView.Adapter {


    List<Message> mMessages;

    private Context mContext;

    public static int ITEM_TEXT_VIEW = 0;
    public static int ITEM_DOCUMENT_VIEW = 1;
    public static OnClickDownloadListener listener;
    interface  OnClickDownloadListener{
        void onClicked(String name);
    }

    public static void setOnItemListener(OnClickDownloadListener listener){
        ChatAdapter.listener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TEXT_VIEW) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_text_view, parent, false);
            return new ViewTextHolder(view);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_document_view, parent, false);
        return new ViewDocHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int itemType = getItemViewType(position);
        if(itemType == ITEM_TEXT_VIEW){
            ((ViewTextHolder) holder).bindView(mMessages.get(position).getMess(),mMessages.get(position).getStringDate());
        }else if(itemType == ITEM_DOCUMENT_VIEW){
            ((ViewDocHolder) holder).bindView(mMessages.get(position).getMess(),mMessages.get(position).getStringDate());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mMessages.get(position).isDoc()){
            return ITEM_DOCUMENT_VIEW;
        }
        return ITEM_TEXT_VIEW;
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public List<Message> getMessages() {
        return mMessages;
    }

    public void setMessages(List<Message> messages) {
        mMessages = messages;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    private class ViewTextHolder extends RecyclerView.ViewHolder {

        private TextView text, date;

        public ViewTextHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.message_txt);
            date = (TextView) itemView.findViewById(R.id.message_date);
        }

        public void bindView(String mess, String time) {
            text.setText(mess);
            date.setText(time);
        }

    }

    private class ViewDocHolder extends RecyclerView.ViewHolder {

        private TextView text, date;
        private String t;

        public ViewDocHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.message_doc_txt);
            date = (TextView) itemView.findViewById(R.id.message_doc_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(Html.fromHtml("<font color='#1565C0'>Загрузка файла</font>"));
                    builder.setMessage(Html.fromHtml("<font color='#1E88E5'> Скчать файл "+t+".docx?</font>"));
                    builder.setPositiveButton("Скачать", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Task task = new Task();
                            task.execute();
                        }
                    });
                    builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {}
                    });
                    builder.setCancelable(true);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    dialog.getWindow().setBackgroundDrawableResource(R.color.md_white_1000);
                    Button positiveButton = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                    positiveButton.setTextColor(getContext().getResources().getColor(R.color.md_blue_600));
                    positiveButton.invalidate();
                    Button negativeButton = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
                    negativeButton.setTextColor(getContext().getResources().getColor(R.color.md_blue_600));
                    negativeButton.invalidate();
                }
            });
        }

        public void bindView(String mess, String time) {
            text.setText(mess);
            date.setText(time);
            t = text.getText().toString();
        }

        private class Task extends AsyncTask<Void,Void,Boolean>{
            @Override
            protected Boolean doInBackground(Void... voids) {
                boolean succes = true;
                try {
                    RequestSender.downloadFile(getContext(), t);
                }catch (Exception e){
                    e.printStackTrace();
                    succes = false;
                }
                return succes;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if(success){
                    Toast.makeText(getContext(),"Файл был скачан", Toast.LENGTH_LONG)
                            .show();
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/"+t+".docx");

                    Log.i("ChatAdapter","File is exist:"+file.exists());
                    Log.i("ChatAdapter","File - path:"+Environment.getExternalStorageDirectory() + "/Download/"+t+".docx");
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    String mimeType= MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/"+t+".docx"));
                    intent.setDataAndType(Uri.fromFile(file), mimeType);
                    getContext().startActivity(Intent.createChooser(intent, "Открыть с помощью"));
//                    Uri fileUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/Download/"+name+".docx"));
//                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//                    intent.setData(fileUri);
//                    intent.setType("application/msword");
//                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    getContext().startActivity(Intent.createChooser(intent, "Открыть файл:"));
                }else{
                    Toast.makeText(getContext(),"Ошибка!",Toast.LENGTH_LONG)
                            .show();
                }
            }
        }

    }

}

