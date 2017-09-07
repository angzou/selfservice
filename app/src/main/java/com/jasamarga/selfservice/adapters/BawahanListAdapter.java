package com.jasamarga.selfservice.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jasamarga.selfservice.R;
import com.jasamarga.selfservice.callback.BawahanListAdapterCallback;
import com.jasamarga.selfservice.customwiget.CustomTextView;
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
public class BawahanListAdapter extends RecyclerView.Adapter<BawahanListAdapter.BawahanListViewHolder> {

    private Context context;
    private List<PersonalInfoDao> bawahanList;
    private BawahanListAdapterCallback callback;

    public BawahanListAdapter(Context context, List<PersonalInfoDao> objects, BawahanListAdapterCallback listener) {
        this.context = context;
        this.bawahanList = objects;
        this.callback = listener;
    }

    @Override
    public BawahanListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_bawahan, parent, false);
        BawahanListViewHolder holder = new BawahanListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final BawahanListViewHolder holder, final int position) {

        Picasso.with(this.context)
                .load(Config.URL_IMAGE + bawahanList.get(position).getUrlfoto())
                .placeholder(R.drawable.placeholder)
                .into(holder.loadedBitmap);

        holder.tvRowNamaBawahan.setText(this.bawahanList.get(position).getNama());
        holder.rlRowContainerBawahan.setOnClickListener(new ActionClick(position));
    }

    @Override
    public int getItemCount() {
        return this.bawahanList.size();
    }

    public void addData(List<PersonalInfoDao> objects) {
        this.bawahanList.addAll(objects);
        notifyDataSetChanged();
    }

    public void clearData(){
        this.bawahanList.clear();
        notifyDataSetChanged();
    }

    private class ActionClick implements View.OnClickListener {
        private int mPosition;

        public ActionClick(int position) {
            this.mPosition = position;
        }

        @Override
        public void onClick(View v) {
            callback.onBawahanListAdapterCallback(mPosition);
        }
    }

    public static class BawahanListViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.rlRowContainerBawahan)
        RelativeLayout rlRowContainerBawahan;

        @InjectView(R.id.sivRowProfilBawahan)
        ShapedImageView sivRowProfilBawahan;

        @InjectView(R.id.tvRowNamaBawahan)
        CustomTextView tvRowNamaBawahan;

        private Target loadedBitmap;

        BawahanListViewHolder(View view) {
            super(view);

            ButterKnife.inject(this, view);
            if (this.loadedBitmap == null) this.loadedBitmap = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    sivRowProfilBawahan.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, width, (height < width) ? height : width));
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            this.sivRowProfilBawahan.setTag(this.loadedBitmap);

        }
    }
}
