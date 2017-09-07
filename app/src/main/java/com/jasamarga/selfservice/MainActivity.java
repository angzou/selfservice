package com.jasamarga.selfservice;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jasamarga.selfservice.callback.LogoutFragmentCallback;
import com.jasamarga.selfservice.callback.ProfilFragmentCallback;
import com.jasamarga.selfservice.callback.SlipGajiFragmentCallback;
import com.jasamarga.selfservice.dagger.DaggerInjection;
import com.jasamarga.selfservice.events.ErrorEvent;
import com.jasamarga.selfservice.fragment.CutiFragment;
import com.jasamarga.selfservice.fragment.LemburFragment;
import com.jasamarga.selfservice.fragment.LogoutFragment;
import com.jasamarga.selfservice.fragment.PersonalInfoFragment;
import com.jasamarga.selfservice.fragment.ProfilFragment;
import com.jasamarga.selfservice.fragment.SPPDFragment;
import com.jasamarga.selfservice.fragment.SlipGajiFragment;
import com.jasamarga.selfservice.utility.PreferenceUtility;
import com.jasamarga.selfservice.utility.Utility;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener, ProfilFragmentCallback, SlipGajiFragmentCallback, LogoutFragmentCallback {

    @InjectView(R.id.container)
    FrameLayout container;

    @InjectView(R.id.fab_close)
    FloatingActionButton fab_close;

    @InjectView(R.id.fab)
    FloatingActionButton fab;

    private static final int ALPHA_ANIMATIONS_DURATION = 200;
    private int menuIndex = 1;
    private MenuParams menuParams;
    private Bundle userData;
    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;
    private ProfilFragment profilFragment;
    private SlipGajiFragment slipGajiFragment;
    private CutiFragment cutiFragment;
    private LemburFragment lemburFragment;
    private SPPDFragment sppdFragment;
    private PersonalInfoFragment personalInfoFragment;
    private LogoutFragment logoutFragment;
    private AlertDialog alertDialog;
    private AlertDialog.Builder alertDialogBuilder;

    private String[] navigationTitles = {"", "Profil", "Slip Gaji", "Cuti", "Absensi", "SPPD", "Personal Info"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        DaggerInjection.get().inject(this);

        userData = getIntent().getBundleExtra("bundleLogin");

        fragmentManager = getSupportFragmentManager();

        initContextMenu();
        initFloatingButtonIcons();

        profilFragment = new ProfilFragment(MainActivity.this, this, userData);
        slipGajiFragment = new SlipGajiFragment(MainActivity.this, this);
        cutiFragment = new CutiFragment(MainActivity.this);
        lemburFragment = new LemburFragment(MainActivity.this);
        sppdFragment = new SPPDFragment(MainActivity.this);
        personalInfoFragment = new PersonalInfoFragment(MainActivity.this);
        addFragment(profilFragment, R.id.container);
    }

    @Override
    public void onBackPressed() {
        if (menuIndex != 1) {
            addFragment(profilFragment, R.id.container);
        }else{
            ShowLogoutDialog("Keluar dari aplikasi?", 1);
//            ShowAlertDialog("Keluar dari aplikasi?", 1);
        }
        menuIndex = 1;
        changeMenuColor(R.color.colorWhite);
        setupFloatingCloseButtons(R.drawable.icon_logout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        if (position > 1) {
            menuIndex = position;
            setupFloatingCloseButtons(R.drawable.icon_back);
            if (position == 2) {
                addFragment(slipGajiFragment, R.id.container);
                changeMenuColor(R.color.colorRedAP);
            }else if (position == 6) {
                addFragment(personalInfoFragment, R.id.container);
                changeMenuColor(R.color.colorRedAP);
            }else if (position == 3) {
                addFragment(cutiFragment, R.id.container);
                changeMenuColor(R.color.colorGreenA700);
            }else if (position == 4) {
                addFragment(lemburFragment, R.id.container);
                changeMenuColor(R.color.colorOrange400);
            }else if (position == 5) {
                addFragment(sppdFragment, R.id.container);
                changeMenuColor(R.color.colorBrown300);
            }
        }else if (position == 1) {
            menuIndex = position;
            setupFloatingCloseButtons(R.drawable.icon_logout);
            addFragment(profilFragment, R.id.container);
            changeMenuColor(R.color.colorWhite);
        }
    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {
    }

    @OnClick(R.id.fab)
    public void ShowContextMenu() {
        if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
            mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
        }
    }

    @OnClick(R.id.fab_close)
    public void Logout() {
        if (menuIndex != 1) {
            addFragment(profilFragment, R.id.container);
        }else{
            ShowLogoutDialog("Keluar dari akun?", 0);
//            ShowAlertDialog("Keluar dari akun?", 0);
        }
        menuIndex = 1;
        changeMenuColor(R.color.colorWhite);
        setupFloatingCloseButtons(R.drawable.icon_logout);
        //Snackbar.make(fab_close, "Logout", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void LogoutFromAccount() {
        PreferenceUtility.getInstance().saveData(MainActivity.this, PreferenceUtility.API_TOKEN, "");
        PreferenceUtility.getInstance().saveData(MainActivity.this, PreferenceUtility.USERNAME, "");
        PreferenceUtility.getInstance().saveData(MainActivity.this, PreferenceUtility.PASSWORD, "");
        PreferenceUtility.getInstance().saveData(MainActivity.this, PreferenceUtility.USER_DATA, "");
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        MainActivity.this.finish();
    }

    @Override
    public void QuitApplication() {
        MainActivity.this.finish();
    }

    @Subscribe
    public void onEventThread(ErrorEvent event) {
        Snackbar.make(container, event.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    private void initContextMenu() {
        menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(true);
        menuParams.setAnimationDuration(30);
        menuParams.setAnimationDelay(10);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }

    private List<MenuObject> getMenuObjects() {
        List<MenuObject> menuObjects = new ArrayList<>();

        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.icon_slip_gaji);
        Drawable d = new BitmapDrawable(getResources(), b);
        d.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);

        Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_profil);
        Drawable d1 = new BitmapDrawable(getResources(), b1);
        d1.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);

        Bitmap b2 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_info_rekan);
        Drawable d2 = new BitmapDrawable(getResources(), b2);
        d2.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);

        Bitmap b3 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_close);
        Drawable d3 = new BitmapDrawable(getResources(), b3);
        d3.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);

        Bitmap b4 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_cuti);
        Drawable d4 = new BitmapDrawable(getResources(), b4);
        d4.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);

        Bitmap b5 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_absensi);
        Drawable d5 = new BitmapDrawable(getResources(), b5);
        d5.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);

        Bitmap b6 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_sppd);
        Drawable d6 = new BitmapDrawable(getResources(), b6);
        d6.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);

        MenuObject close = new MenuObject();
        close.setBgDrawable(d3);
        close.setScaleType(ImageView.ScaleType.FIT_CENTER);

        MenuObject send = new MenuObject("Profil");
        send.setBgDrawable(d1);
        send.setMenuTextAppearanceStyle(R.style.TextViewStyle);
        Log.d("TAG", "" + send.getMenuTextAppearanceStyle());
        send.setScaleType(ImageView.ScaleType.FIT_CENTER);

        MenuObject like = new MenuObject("Slip Gaji");
        like.setBgDrawable(d);
        like.setMenuTextAppearanceStyle(R.style.TextViewStyle);
        like.setScaleType(ImageView.ScaleType.FIT_CENTER);
        //like.setBgColor(getResources().getColor(R.color.colorAqua));

        MenuObject cuti = new MenuObject("Cuti");
        cuti.setBgDrawable(d4);
        cuti.setMenuTextAppearanceStyle(R.style.TextViewStyle1);
        cuti.setScaleType(ImageView.ScaleType.FIT_CENTER);

        MenuObject absensi = new MenuObject("Lembur");
        absensi.setBgDrawable(d5);
        absensi.setMenuTextAppearanceStyle(R.style.TextViewStyle1);
        absensi.setScaleType(ImageView.ScaleType.FIT_CENTER);

        MenuObject sppd = new MenuObject("SPPD");
        sppd.setBgDrawable(d6);
        sppd.setMenuTextAppearanceStyle(R.style.TextViewStyle1);
        sppd.setScaleType(ImageView.ScaleType.FIT_CENTER);

        MenuObject addFr = new MenuObject("Personal Info");
        addFr.setBgDrawable(d2);
        addFr.setMenuTextAppearanceStyle(R.style.TextViewStyle);
        addFr.setScaleType(ImageView.ScaleType.FIT_CENTER);

        menuObjects.add(close);
        menuObjects.add(send);
        menuObjects.add(like);
        menuObjects.add(cuti);
        menuObjects.add(absensi);
        menuObjects.add(sppd);
        menuObjects.add(addFr);

        return menuObjects;
    }

    private void initFloatingButtonIcons() {
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.icon_logout);
        Drawable d = new BitmapDrawable(getResources(), b);
        d.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);

        Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_home);
        Drawable d1 = new BitmapDrawable(getResources(), b1);
        d1.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);

        fab_close.setImageDrawable(d);
        fab.setImageDrawable(d1);
    }

    private void setupFloatingCloseButtons(int resource) {
        Bitmap b = BitmapFactory.decodeResource(getResources(), resource);
        Drawable d = new BitmapDrawable(getResources(), b);
        d.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);

        fab_close.setImageDrawable(d);
    }

    private void changeMenuColor(int color) {
        menuParams.getMenuObjects().get(0).getBgDrawable().setColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);
        menuParams.getMenuObjects().get(1).getBgDrawable().setColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);
        menuParams.getMenuObjects().get(2).getBgDrawable().setColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);
        menuParams.getMenuObjects().get(3).getBgDrawable().setColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);
        menuParams.getMenuObjects().get(4).getBgDrawable().setColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);
        menuParams.getMenuObjects().get(5).getBgDrawable().setColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);
        menuParams.getMenuObjects().get(6).getBgDrawable().setColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);
        fab.getDrawable().setColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);
        fab_close.getDrawable().setColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);
    }

    protected void addFragment(Fragment fragment, int containerId) {
        invalidateOptionsMenu();
        String backStackName = fragment.getClass().getName();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);
        if (!fragmentPopped) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(containerId, fragment, backStackName);
            transaction.commit();
        }
    }

    private void ShowLogoutDialog(String message, int mode) {
        logoutFragment = new LogoutFragment(MainActivity.this, message, mode, this);
        logoutFragment.show(getSupportFragmentManager(), "logoutFragment");
    }

    private void ShowAlertDialog(String message, final int mode) {
        this.alertDialogBuilder = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        if (mode == 1)
                            MainActivity.this.finish();
                        else {
                            PreferenceUtility.getInstance().saveData(MainActivity.this, PreferenceUtility.API_TOKEN, "");
                            PreferenceUtility.getInstance().saveData(MainActivity.this, PreferenceUtility.USERNAME, "");
                            PreferenceUtility.getInstance().saveData(MainActivity.this, PreferenceUtility.PASSWORD, "");
                            PreferenceUtility.getInstance().saveData(MainActivity.this, PreferenceUtility.USER_DATA, "");
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            MainActivity.this.finish();
                        }
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);
        this.alertDialog = this.alertDialogBuilder.create();
        this.alertDialog.show();
    }

    @Override
    public void HideFloatingButtons() {
        Utility.startAlphaAnimation(fab, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
        Utility.startAlphaAnimation(fab_close, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
    }

    @Override
    public void ShowFloatingButtons() {
        Utility.startAlphaAnimation(fab, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
        Utility.startAlphaAnimation(fab_close, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
    }

    @Override
    public void ShowProgressDialog() {
        showLoadingDialog();
    }

    @Override
    public void HideProgressDialog() {
        dismissLoadingDialog();
    }

    @Override
    public void LogoutFromInvalidToken() {
        LogoutFromAccount();
    }
}
