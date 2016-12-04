package io.payex.android.ui.login;

import android.support.design.widget.Snackbar;

import android.os.Bundle;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.payex.android.R;
import io.payex.android.ui.BaseActivity;
import io.payex.android.ui.MainActivity;
import io.payex.android.ui.common.TutorialFragment;
import io.payex.android.ui.common.TutorialSubFragment;
import io.payex.android.ui.register.RegisterActivity;

public class LoginActivity extends BaseActivity
        implements LoginFragment.OnFragmentInteractionListener, LoginHelperFragment.OnFragmentInteractionListener,
SetupFragment.OnFragmentInteractionListener, TutorialSubFragment.OnFragmentInteractionListener
{

    @BindView(R.id.root_container) View mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            addFragment(R.id.fragment_container, LoginFragment.newInstance());
        }
    }

    @Override
    public void onLoginHelpButtonPressed() {
        changeFragment(R.id.fragment_container, LoginHelperFragment.newInstance());
    }

    @Override
    public void onRegisterButtonPressed() {
//        Snackbar.make(mRootView, "Register page under construction", Snackbar.LENGTH_LONG).show();
        startActivity(RegisterActivity.class);
    }

    @Override
    public void onLoginButtonPressed() {
        changeFragment(R.id.fragment_container, TutorialFragment.newInstance());
    }

    @Override
    public void onPasswordReset() {
        Snackbar.make(mRootView, "Resetting password", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onSetupCompleted() {
        startActivity(MainActivity.class, true);
    }

    @Override
    public void onDonePressed() {
        changeFragment(R.id.fragment_container, SetupFragment.newInstance());
    }
}

