package io.payex.android.ui.common;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.payex.android.R;

public class TutorialFragment extends Fragment {

    @BindView(R.id.view_pager) ViewPager mViewPager;
    @BindView(R.id.indicator)
    CirclePageIndicator mIndicator;

    public static TutorialFragment newInstance() {
        return new TutorialFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // create ContextThemeWrapper from the original Activity Context with the custom theme
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_NoActionBar);

        // clone the inflater using the ContextThemeWrapper
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);

        View view = localInflater.inflate(R.layout.fragment_tutorial, container, false);
        ButterKnife.bind(this, view);

        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(TutorialSubFragment.newInstance(
                R.string.tutorial_title_sales,
                R.string.tutorial_caption_sales,
                R.drawable.ic_mood_black_72dp,
                false));
        mFragments.add(TutorialSubFragment.newInstance(
                R.string.tutorial_title_void,
                R.string.tutorial_caption_void,
                R.drawable.ic_mood_black_72dp,
                false));
        mFragments.add(TutorialSubFragment.newInstance(
                R.string.tutorial_title_test,
                R.string.tutorial_caption_test,
                R.drawable.ic_mood_black_72dp,
                true));
        PagerAdapter adapter = new SectionsPagerAdapter(getChildFragmentManager(), mFragments);
        mViewPager.setAdapter(adapter);

        mIndicator.setViewPager(mViewPager);

        return view;
    }

    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments = new ArrayList<>();

        SectionsPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

    }
}
