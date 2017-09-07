package com.jasamarga.selfservice.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jasamarga.selfservice.R;
import com.jasamarga.selfservice.callback.RekanListAdapterCallback;
import com.jasamarga.selfservice.models.PersonalInfoDao;
import com.jasamarga.selfservice.utility.Config;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * Created by apridosandyasa on 8/13/16.
 */
public class RekanListAdapter extends RecyclerView.Adapter<RekanListAdapter.RekanListViewHolder> {

    private final static String TAG = RekanListAdapter.class.getSimpleName();
    private Context context;
    private List<PersonalInfoDao> rekanList;
    private RekanListAdapterCallback callback;

    public RekanListAdapter(Context context, List<PersonalInfoDao> objects, RekanListAdapterCallback listener) {
        this.context = context;
        this.rekanList = objects;
        this.callback = listener;
    }

    @Override
    public RekanListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_peer, parent, false);
        RekanListViewHolder holder = new RekanListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RekanListViewHolder holder, final int position) {
        Picasso.with(this.context)
                .load(Config.URL_IMAGE + rekanList.get(position).getUrlfoto())
                .placeholder(R.drawable.placeholder)
                .into(holder.loadedBitmap);

        holder.llRowContainerPeer.setOnClickListener(new ActionClick(position));
    }

    @Override
    public int getItemCount() {
        return this.rekanList.size();
    }

    public void addData(List<PersonalInfoDao> objects) {
        this.rekanList.addAll(objects);
        notifyDataSetChanged();
    }

    public void clearData(){
        this.rekanList.clear();
        notifyDataSetChanged();
    }

    private class ActionClick implements View.OnClickListener {
        private int mPosition;

        public ActionClick(int position) {
            this.mPosition = position;
        }
        @Override
        public void onClick(View v) {
            callback.onRekanListAdapterCallback(mPosition);
        }
    }

    public static class RekanListViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.llRowContainerPeer)
        LinearLayout llRowContainerPeer;

        @InjectView(R.id.sivRowProfilPeer)
        ShapedImageView sivRowProfilPeer;

        private Target loadedBitmap;

        RekanListViewHolder(View view) {
            super(view);

            ButterKnife.inject(this, view);

            if (this.loadedBitmap == null) this.loadedBitmap = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    sivRowProfilPeer.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, width, (height < width) ? height : width));
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            this.sivRowProfilPeer.setTag(this.loadedBitmap);

        }

    }
}
