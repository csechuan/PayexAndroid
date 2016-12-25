package io.payex.android.ui.sale;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;
import io.payex.android.R;
import io.payex.android.ui.BaseActivity;
import io.payex.android.ui.common.StateFragment;

public class CardReaderActivity extends BaseActivity
        implements AbstractCardReaderFragment.OnScanListener ,
        StateFragment.OnFragmentInteractionListener
{

    @BindView(R.id.tv_primary) AppCompatTextView mPrimaryText;
    @BindView(R.id.rv_logo) RecyclerView mLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_reader);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        setBackButton();

        // get amount from previous page
        String amount = "RM0.00";
        if (getIntent() != null) {
            String temp = getIntent().getStringExtra("AMOUNT");
            if (!TextUtils.isEmpty(temp)) {
                amount = temp;
            }
        }
        mPrimaryText.setText(amount);

        setupLogo(this);

        if (savedInstanceState == null) {
            addFragment(R.id.fragment_container, CardReaderFragment.newInstance());
        }
    }

    private void setupLogo(Context context) {
        // todo size of columns need more research. max now is 4 on my tiny phone
        List<IFlexible> logos = getLogos();
        mLogo.setLayoutManager(new GridLayoutManager(context, logos.size()));
        mLogo.setHasFixedSize(true);
        mLogo.setAdapter(new FlexibleAdapter<>(logos));
    }

    private List<IFlexible> getLogos() {
        List<IFlexible> list = new ArrayList<>();

        int max = 3;
        for (int i = 0 ; i < max ; i++) {

            Drawable d = VectorDrawableCompat.create(getResources(), R.drawable.ic_mastercard_40dp, null);
            d = DrawableCompat.wrap(d);

            list.add(new SaleLogoItem(i + 1 + "", d));
        }
        return list;
    }

    @Override
    public void onSuccess() {
        changeFragment(R.id.fragment_container, StateFragment.newInstance(
                R.drawable.ic_mood_black_72dp, R.string.state_title_loading, 0));
    }

    @Override
    public void onFailed() {

    }

    @Override
    public void onDoneLoading() {
        startActivity(EmailSlipActivity.class, true);
    }
}
