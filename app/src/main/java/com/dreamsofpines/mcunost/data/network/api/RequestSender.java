package com.dreamsofpines.mcunost.data.network.api;

import android.content.Context;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import com.dreamsofpines.mcunost.data.storage.help.menu.InformExcursion;
import com.dreamsofpines.mcunost.data.storage.preference.GlobalPreferences;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for send request on API
 */

public class RequestSender{

    public static String POST(Context context, String url, JSONObject js, boolean hasAuthHeader){
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new StringHttpMessageConverter());
        HttpEntity<String> request = new HttpEntity<String>(js.toString(), getAuthHeader(context,hasAuthHeader));
        String answer = null;
        try {
            answer = template.postForObject(url,request,String.class);  // Что должен вернуть ?
        }catch (RestClientException e){
            Log.i("RequestSender","Error POST send! Error message: "+e.getMessage());
        }
        return answer;
    }

    public static String GET(String url){
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new StringHttpMessageConverter());
        String answer = "";
        try {
            answer = template.getForObject(url,String.class);
        }catch (RestClientException e){
            Log.i("RequestSender","Error GET send! Error message: "+e.getMessage());
        }
        return answer;
    }


    public static String GetPackExcur(String idRegion, String idCategory){
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new StringHttpMessageConverter());
        String response = "";
        try {
            response = template.getForObject(Constans.URL.TOUR.GET_PACK_EXCUR, String.class,idRegion,idCategory);
        }catch (RestClientException e){
            Log.i("RequestSender","Error GET_PACK_EXCUR send! Error message: "+e.getMessage());
        }
        return response;
    }


    public static String GetAllOrdersByLogin(Context context){
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new StringHttpMessageConverter());
        HttpEntity<String> request = new HttpEntity<String>(getAuthHeader(context,true));
        HttpEntity<String> response = null;
        try {
             response = template.exchange(Constans.URL.ORDER.GET_ALL_ORDER_USER_BY_LOGIN, HttpMethod.GET,request,String.class,
                     GlobalPreferences.getPrefUserEmail(context));
        }catch (RestClientException e){
            Log.i("RequestSender","Error get all order by login send! Error message: "+e.getMessage());
        }
        if (response == null) return null;

        return response.getBody();
    }

    public static String GetAllOrdersById(Context context){
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new StringHttpMessageConverter());
        HttpEntity<String> request = new HttpEntity<String>(getAuthHeader(context,true));
        HttpEntity<String> response = null;
        try {
             response = template.exchange(Constans.URL.ORDER.GET_ALL_ORDER_USER_BY_ID, HttpMethod.GET,request,String.class,
                     GlobalPreferences.getPrefIdUser(context));
        }catch (RestClientException e){
            Log.i("RequestSender","Error get all order by id send! Error message: "+e.getMessage());
        }
        if (response == null) return null;
        return response.getBody();
    }

    public static String GetDialogById(Context context){
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new StringHttpMessageConverter());
        HttpEntity<String> request = new HttpEntity<String>(getAuthHeader(context,true));
        HttpEntity<String> response = null;
        try {
             response = template.exchange(Constans.URL.MESSAGE.GET_DIALOG, HttpMethod.GET,request,String.class,
                     GlobalPreferences.getPrefIdUser(context));
        }catch (RestClientException e){
            Log.i("RequestSender","Error get dialogs by id send! Error message: "+e.getMessage());
        }
        if (response == null) return null;
        return response.getBody();
    }

    public static String SetWasReadById(Context context,int idOrder){
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new StringHttpMessageConverter());
        HttpEntity<String> request = new HttpEntity<String>(getAuthHeader(context,true));
        HttpEntity<String> response = null;
        try {
             response = template.exchange(Constans.URL.MESSAGE.SET_WAS_READ, HttpMethod.GET,request,String.class,
                     idOrder);
        }catch (RestClientException e){
            Log.i("RequestSender","Error get dialogs by id send! Error message: "+e.getMessage());
        }
        if (response == null) return null;
        return response.getBody();
    }

    public static String pushToken(Context context, String token){
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new StringHttpMessageConverter());
        JSONObject js = new JSONObject();
        try {
            js.put("token", token);
            js.put("id",GlobalPreferences.getPrefIdUser(context));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        HttpEntity<String> request = new HttpEntity<String>(js.toString(),getAuthHeader(context,true));
        HttpEntity<String> response = null;
        try {
            response = template.exchange(Constans.URL.USER.SET_TOKEN_USER, HttpMethod.POST,request,String.class);
        }catch (RestClientException e){
            Log.i("RequestSender","Error get dialogs by id send! Error message: "+e.getMessage());
        }
        if (response == null) return null;
        return response.getBody();

    }

    public static String GetMessages(Context context, int order){
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new StringHttpMessageConverter());
        HttpEntity<String> request = new HttpEntity<String>(getAuthHeader(context,true));
        HttpEntity<String> response = null;
        try {
            response = template.exchange(Constans.URL.MESSAGE.GET_MESSAGES, HttpMethod.GET,request,String.class,
                    GlobalPreferences.getPrefIdUser(context),order);
        }catch (RestClientException e){
            Log.i("RequestSender","Error get all order by id send! Error message: "+e.getMessage());
        }
        if (response == null) return null;
        return response.getBody();

    }

    public static String GetUserInformation(String email, String phone){
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new StringHttpMessageConverter());
        HttpEntity<String> request = new HttpEntity<String>(getAuthHeader(email,phone));
        HttpEntity<String> response = null;
        email = new String(Base64.encode(email.getBytes(),Base64.NO_WRAP));
        try {
            response = template.exchange(Constans.URL.USER.GET_USER_BY_LOGIN, HttpMethod.GET,request,
                    String.class, email);
        }catch (RestClientException e){
            Log.i("RequestSender","Error get user info send! Error message: "+e.getMessage());
        }
        if (response == null) return null;
        return response.getBody();
    }

//    public static String PostUpdateUserInfo(Context context, JSONObject user){
//        RestTemplate template = new RestTemplate();
//        template.getMessageConverters().add(new StringHttpMessageConverter());
//        HttpEntity<String> request = new HttpEntity<String>(user.toString(), getAuthHeader(context));
//        String response = null;
//        try {
//            response = template.postForObject(Constans.URL.USER.UPDATE_USER_INFO,request,String.class);
//            Log.i("RequestSender"," answer message: "+ response);
//        }catch (RestClientException e) {
//            Log.i("RequestSender", "Error post update user info send! Error message: " + e.getMessage());
//        }
//        return response;
//    }

    public static void downloadFile(Context context,String name) throws IOException {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new ByteArrayHttpMessageConverter());
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getAuthHeader(context,true));
        ResponseEntity<byte[]> response = restTemplate.exchange(
                Constans.URL.DOWNLOAD.GET_DOC,
                HttpMethod.GET,request,byte[].class, name);
        if(response.getStatusCode() == HttpStatus.OK){
            FileOutputStream output = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/Download/"+name+".docx"));
            Log.i("RequestSender","File - path:" + Environment.getExternalStorageDirectory() + "/Download/"+name+".docx" );
            IOUtils.write(response.getBody(), output);
        }

    }


    private static HttpHeaders getAuthHeader(Context context, boolean hasAuthHeader){
        HttpHeaders headers = new HttpHeaders();
        if(hasAuthHeader) {
            String email = GlobalPreferences.getPrefUserEmail(context);
            String password = GlobalPreferences.getPrefUserEmail(context) + GlobalPreferences.getPrefUserNumber(context);
            HttpAuthentication authHeader = new HttpBasicAuthentication(email, password);
            headers.setAuthorization(authHeader);
        }
        headers.add("Accept-Language","ru,en;q=0.8");
        headers.add("Content-Type","application/json; charset=utf-8");
        return headers;
    }

    private static HttpHeaders getAuthHeader(String email, String phone){
        HttpHeaders headers = new HttpHeaders();
        phone = email + phone;
        HttpAuthentication authHeader = new HttpBasicAuthentication(email, phone);
        headers.setAuthorization(authHeader);
        headers.add("Accept-Language","ru,en;q=0.8");
        headers.add("Content-Type","application/json; charset=utf-8");
        return headers;
    }

}
