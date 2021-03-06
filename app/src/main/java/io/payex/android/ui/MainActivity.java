package io.payex.android.ui;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import org.chromium.customtabsclient.CustomTabsActivityHelper;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.items.IFlexible;
import icepick.State;
import io.payex.android.R;
import io.payex.android.ui.about.AboutFragment;
import io.payex.android.ui.account.MyAccountFragment;
import io.payex.android.ui.login.LoginActivity;
import io.payex.android.ui.sale.CardReaderActivity;
import io.payex.android.ui.sale.SaleFragment;
import io.payex.android.ui.sale.history.SaleHistoryFragment;
import io.payex.android.ui.sale.history.SaleHistoryItem;
import io.payex.android.ui.sale.history.SaleSlipActivity;
import io.payex.android.ui.sale.voided.VoidFragment;
import io.payex.android.ui.sale.voided.VoidItem;
import io.payex.android.ui.sale.voided.VoidSlipActivity;
import me.zhanghai.android.customtabshelper.CustomTabsHelperFragment;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        SaleHistoryFragment.OnListFragmentInteractionListener,
        VoidFragment.OnListFragmentInteractionListener,
        SaleFragment.OnFragmentInteractionListener,
        AboutFragment.OnFragmentInteractionListener
{

    private final CustomTabsActivityHelper.CustomTabsFallback mCustomTabsFallback =
            new CustomTabsActivityHelper.CustomTabsFallback() {
                @Override
                public void openUri(Activity activity, Uri uri) {
                    try {
                        activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    } catch (ActivityNotFoundException e) {
                        Log.e(getLocalClassName(), "Activity not found");
                        Snackbar.make(mDrawer, "Activity not found", Snackbar.LENGTH_LONG).show();
                    }
                }
            };

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawer;
    @BindView(R.id.nav_view) NavigationView mNavView;

    @BindColor(R.color.colorPrimary)
    int mColorPrimary;

    private CustomTabsHelperFragment mCustomTabsHelperFragment;
    private CustomTabsIntent mCustomTabsIntent;

    @State int itemId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavView.setNavigationItemSelectedListener(this);

        // select the default
        onNavigationItemSelected(mNavView.getMenu().getItem(itemId));
        mNavView.setCheckedItem(mNavView.getMenu().getItem(itemId).getItemId());
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_sale) {
            changeFragment(R.id.fragment_container, SaleFragment.newInstance(), null);
            setTitle(R.string.title_activity_sale);
            itemId = 0;
        } else if (id == R.id.nav_void_transaction) {
            changeFragment(R.id.fragment_container, VoidFragment.newInstance(), null);
            setTitle(R.string.title_activity_void);
            itemId = 1;
        } else if (id == R.id.nav_sale_history) {
            changeFragment(R.id.fragment_container, SaleHistoryFragment.newInstance(), null);
            setTitle(R.string.title_activity_sale_history);
            itemId = 2;
        } else if (id == R.id.nav_account) {
            changeFragment(R.id.fragment_container, MyAccountFragment.newInstance(), null);
            setTitle(R.string.title_activity_my_account);
            itemId = 3;
        } else if (id == R.id.nav_about) {
            changeFragment(R.id.fragment_container, AboutFragment.newInstance(), AboutFragment.TAG);
            setTitle(R.string.title_activity_about);
            itemId = 4;
        } else if (id == R.id.nav_logout) {
            // todo clear all the cache before logout
            startActivity(LoginActivity.class, true);
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(IFlexible item) {
        if (item instanceof SaleHistoryItem) {
            SaleHistoryItem saleHistoryItem = (SaleHistoryItem) item;
            Log.i(getLocalClassName(), saleHistoryItem.getPrimaryText());
            startActivity(SaleSlipActivity.class, false);
        }
    }

    private void openWithCustomTabs(final Uri uri) {
        // fixme may not be needed
        mCustomTabsHelperFragment = CustomTabsHelperFragment.attachTo(this);

        mCustomTabsHelperFragment.setConnectionCallback(
                new CustomTabsActivityHelper.ConnectionCallback() {
                    @Override
                    public void onCustomTabsConnected() {
                        mCustomTabsHelperFragment.mayLaunchUrl(uri, null, null);
                    }

                    @Override
                    public void onCustomTabsDisconnected() {
                    }
                });

        mCustomTabsIntent = new CustomTabsIntent.Builder()
                .enableUrlBarHiding()
                .setToolbarColor(mColorPrimary)
                .setShowTitle(true)
                .build();

        CustomTabsHelperFragment.open(this, mCustomTabsIntent, uri, mCustomTabsFallback);
    }

    @Override
    public void onEnterPressed(String text) {
        startActivity(CardReaderActivity.class, false);
    }

    @Override
    public void onLinkClicked(Uri uri) {
        openWithCustomTabs(uri);
    }

    @Override
    public void onTutorialClicked() {
        // todo add tutorial page
        Snackbar.make(mDrawer, "Under construction", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onVoidItemClicked(IFlexible item) {
        if (item instanceof VoidItem) {
            VoidItem voidItem = (VoidItem) item;
            Log.i(getLocalClassName(), voidItem.getPrimaryText());
            startActivity(VoidSlipActivity.class, false);
        }
    }
}
