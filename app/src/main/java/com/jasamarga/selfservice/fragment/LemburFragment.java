package com.jasamarga.selfservice.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jasamarga.selfservice.R;
import com.jasamarga.selfservice.customwiget.CustomTextView;
import com.jasamarga.selfservice.dagger.DaggerInjection;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by apridosandyasa on 12/12/16.
 */

public class LemburFragment extends Fragment {

    @InjectView(R.id.tvTestUpload)
    CustomTextView tvTestUpload;

    private Context context;
    private View view;

    public LemburFragment() {

    }

    @SuppressLint("ValidFragment")
    public LemburFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        this.view = inflater.inflate(R.layout.content_lembur, container, false);

        return this.view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.inject(this, view);
        DaggerInjection.get().inject(this);
    }

    @OnClick(R.id.tvTestUpload)
    public void setTvTestUpload() {
//        new AsyncTask<String, Void, String>() {
//            @Override
//            protected String doInBackground(String... params) {
//                final ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//                nameValuePairs.add(new BasicNameValuePair("file_content", convertBitmapToString(R.drawable.icon_profil)));
//                nameValuePairs.add(new BasicNameValuePair("file_name", "test1.png"));
//                nameValuePairs.add(new BasicNameValuePair("api_key", "58574582e287c0.23999102"));
//                try {
//                    HttpClient httpclient = new DefaultHttpClient();
//                    HttpPost httppost = new HttpPost("http://ekamant.sales1crm.com/api/crm-task/upload");
//                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
//                    HttpResponse response = httpclient.execute(httppost);
//                    String responseStr = EntityUtils.toString(response.getEntity());
//                    Log.i("TAG", "doFileUpload Response : " + responseStr);
//                } catch (Exception e) {
//                    System.out.println("Error in http connection " + e.toString());
//                }
//
//                return null;
//            }
//        }.execute("");
    }

    public String convertBitmapToString(int res){
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(),
                res);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        String encodedImage = Base64.encodeToString(b, 0, b.length, Base64.NO_WRAP);

        return encodedImage;
    }


}
