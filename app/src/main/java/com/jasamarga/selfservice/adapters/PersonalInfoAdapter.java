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
import com.jasamarga.selfservice.callback.PersonalInfoAdapterCallback;
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
 * Created by apridosandyasa on 12/29/16.
 */

public class PersonalInfoAdapter extends RecyclerView.Adapter<PersonalInfoAdapter.PersonalInfoViewHolder> {

    private Context context;
    private List<PersonalInfoDao> personalInfoDaoList;
    private PersonalInfoAdapterCallback callback;

    public PersonalInfoAdapter(Context context, List<PersonalInfoDao> objects, PersonalInfoAdapterCallback listener) {
        this.context = context;
        this.personalInfoDaoList = objects;
        this.callback = listener;
    }

    @Override
    public PersonalInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_personalinfo, parent, false);
        PersonalInfoViewHolder holder = new PersonalInfoViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final PersonalInfoViewHolder holder, final int position) {
        Picasso.with(this.context)
                .load(Config.URL_IMAGE + personalInfoDaoList.get(position).getUrlfoto())
                .placeholder(R.drawable.placeholder)
                .into(holder.loadedBitmap);

        holder.tvRowNamaPersonalInfo.setText(personalInfoDaoList.get(position).getNama());
        holder.tvRowJabatanPersonalInfo.setText(personalInfoDaoList.get(position).getJabatan());
        holder.rlRowContainerPersonalInfo.setOnClickListener(new ShowDetailView(personalInfoDaoList.get(position).getNpp()));
    }

    @Override
    public int getItemCount() {
        return this.personalInfoDaoList.size();
    }

    public void addData(List<PersonalInfoDao> objects) {
        this.personalInfoDaoList.addAll(objects);
        notifyDataSetChanged();
    }

    public void clearData(){
        this.personalInfoDaoList.clear();
        notifyDataSetChanged();
    }

    private class ShowDetailView implements View.OnClickListener {

        private String personNpp;

        public ShowDetailView(String npp) {
            this.personNpp = npp;
        }

        @Override
        public void onClick(View v) {
            callback.ShowDetailPersonalInfo(this.personNpp);
        }
    }

    static class PersonalInfoViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.rlRowContainerPersonalInfo)
        RelativeLayout rlRowContainerPersonalInfo;

        @InjectView(R.id.sivRowProfilPersonalInfo)
        ShapedImageView sivRowProfilPersonalInfo;

        @InjectView(R.id.tvRowNamaPersonalInfo)
        CustomTextView tvRowNamaPersonalInfo;

        @InjectView(R.id.tvRowJabatanPersonalInfo)
        CustomTextView tvRowJabatanPersonalInfo;

        private Target loadedBitmap;

        PersonalInfoViewHolder(View view) {
            super(view);

            ButterKnife.inject(this, view);

            if (this.loadedBitmap == null) this.loadedBitmap = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    sivRowProfilPersonalInfo.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, width, (height < width) ? height : width));
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            this.sivRowProfilPersonalInfo.setTag(this.loadedBitmap);
        }
    }

}
