package io.payex.android.ui.sale;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import io.payex.android.R;
import io.payex.android.ui.BaseActivity;
import io.payex.android.ui.MainActivity;

public class EmailSlipActivity extends BaseActivity
implements EmailSlipFragment.OnFragmentInteractionListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_slip);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setBackButton();
    }

    @Override
    public void onSendButtonPressed() {
        // fixme warning. anti-pattern. - single task on main activity?
        startActivity(MainActivity.class, true);
    }
}
