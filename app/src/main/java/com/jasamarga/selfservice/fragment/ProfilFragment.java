package com.jasamarga.selfservice.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jasamarga.selfservice.R;
import com.jasamarga.selfservice.callback.ProfilFragmentCallback;
import com.jasamarga.selfservice.customwiget.CustomTextView;
import com.jasamarga.selfservice.dagger.DaggerInjection;
import com.jasamarga.selfservice.models.LoginDao;
import com.jasamarga.selfservice.utility.Config;
import com.jasamarga.selfservice.utility.Utility;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * Created by apridosandyasa on 12/4/16.
 */

public class ProfilFragment extends Fragment {

    @InjectView(R.id.ll_title_personalinfo)
    LinearLayout ll_title_personalinfo;

    @InjectView(R.id.tv_header_personalinfo)
    CustomTextView tv_header_personalinfo;

    @InjectView(R.id.tvNameProfil)
    CustomTextView tvNameProfil;

    @InjectView(R.id.tvJabatanProfil)
    CustomTextView tvJabatanProfil;

    @InjectView(R.id.tvStatusPeg)
    CustomTextView tvStatusPeg;

    @InjectView(R.id.tvAlamatKantor)
    CustomTextView tvAlamatKantor;

    @InjectView(R.id.tvTmptLahir)
    CustomTextView tvTmptLahir;

    @InjectView(R.id.tvGender)
    CustomTextView tvGender;

    @InjectView(R.id.tvAlamat)
    CustomTextView tvAlamat;

    @InjectView(R.id.tvKontak)
    CustomTextView tvKontak;

    @InjectView(R.id.sivProfil)
    ShapedImageView sivProfil;

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.7f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final float PERCENTAGE_TO_HIDE_FLOAT_BUTTONS     = 0.1f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;
    private boolean mIsTheFloatingButtonsVisible = true;

    private Context context;
    private View view;
    private ProfilFragmentCallback callback;
    private Bundle userData;
    private LoginDao loginDao;
    private Target loadedBitmap;

    public ProfilFragment() {

    }

    @SuppressLint("ValidFragment")
    public ProfilFragment(Context context, ProfilFragmentCallback listener, Bundle bundle) {
        this.context = context;
        this.callback = listener;
        this.userData = bundle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.content_profil, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.inject(this, view);
        DaggerInjection.get().inject(this);

        loginDao = (LoginDao) userData.getSerializable("loginData");

        float radius = tv_header_personalinfo.getTextSize() / 18;
        BlurMaskFilter filter = new BlurMaskFilter(radius, BlurMaskFilter.Blur.SOLID);
        tv_header_personalinfo.getPaint().setMaskFilter(filter);

        tvNameProfil.setText(loginDao.getNama());
        tvJabatanProfil.setText(loginDao.getJabatan());
        tvStatusPeg.setText(loginDao.getStatus_desc() + ", " + loginDao.getUnit_desc());
        tvAlamatKantor.setText(loginDao.getKantor_alamat());
        tvTmptLahir.setText(loginDao.getTempatlahir() + ", "+ loginDao.getTanggallahir());
        tvGender.setText(loginDao.getAgama() + ", " + (loginDao.getJenis_kelamin().equals("M") ? "Laki-laki" : "Perempuan"));
        tvAlamat.setText(loginDao.getAlamat());
        tvKontak.setText(((loginDao.getTelp1() != null) ? loginDao.getTelp1() : "-") + ", " + ((loginDao.getTelp2() != null) ? loginDao.getTelp2() : "-"));

        if (loadedBitmap == null) loadedBitmap = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                sivProfil.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, width, (height < width) ? height : width));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        sivProfil.setTag(loadedBitmap);
        Picasso.with(this.context)
                .load(Config.URL_IMAGE + loginDao.getUrlfoto())
                .placeholder(R.drawable.placeholder)
                .into(this.loadedBitmap);
    }
}
