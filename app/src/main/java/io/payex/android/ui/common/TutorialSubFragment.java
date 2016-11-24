package io.payex.android.ui.common;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.payex.android.R;
import io.payex.android.util.HtmlCompat;

public class TutorialSubFragment extends Fragment {

    @BindView(R.id.image)
    ImageView mImageView;
    @BindView(R.id.tv_title)
    AppCompatTextView mTitleTextView;
    @BindView(R.id.tv_caption)
    AppCompatTextView mCaptionTextView;
    @BindView(R.id.btn_done)
    AppCompatButton mDoneButton;

    private int mTitleRes;
    private int mCaptionRes;
    private int mDrawableRes;
    private boolean mShow;

    public static TutorialSubFragment newInstance(@StringRes int titleRes, @StringRes int captionRes,
                                                  @DrawableRes int drawableRes, boolean show) {
        TutorialSubFragment f = new TutorialSubFragment();
        Bundle args = new Bundle();
        args.putInt("titleRes", titleRes);
        args.putInt("captionRes", captionRes);
        args.putInt("drawableRes", drawableRes);
        args.putBoolean("show", show);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitleRes = getArguments().getInt("titleRes", 0);
        mCaptionRes = getArguments().getInt("captionRes", 0);
        mDrawableRes = getArguments().getInt("drawableRes", 0);
        mShow = getArguments().getBoolean("show", false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tutorial_sub, container, false);
        ButterKnife.bind(this, view);

        mImageView.setImageResource(mDrawableRes);
        HtmlCompat.setSpannedText(mTitleTextView, getString(mTitleRes));
        HtmlCompat.setSpannedText(mCaptionTextView, getString(mCaptionRes));
        mDoneButton.setVisibility(mShow ? View.VISIBLE : View.INVISIBLE);

        return view;
    }

    protected OnFragmentInteractionListener mListener;

    public interface OnFragmentInteractionListener {
        void onDonePressed();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick(R.id.btn_done)
    public void done() {
        mListener.onDonePressed();
    }
}
