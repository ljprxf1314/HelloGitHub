package com.ljp.hellogithub.ui.coordinator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ljp.hellogithub.R;
import com.ljp.hellogithub.base.BaseActivity;
import com.ljp.hellogithub.fragment.CoordinatorFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <pre>
 *     author : lijipei
 *     time   : 2018/6/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class CoordinatorLayoutActivity03 extends BaseActivity {


//    @BindView(R.id.toolbar)
//    Toolbar mToolbar;
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;

    private String mTitles[] = {
            "全部", "已通过", "未通过"};

    private ArrayList<Fragment> fragments;
    MyFragmentAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout03);
        ButterKnife.bind(this);

        for (int i = 0; i < mTitles.length; i++) {
            TabLayout.Tab tab = mTabs.newTab();
            tab.setTag(i);
            tab.setText(mTitles[i]);
            mTabs.addTab(tab);
        }
        //监听事件
        mTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewpager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        fragments = new ArrayList<>();
        fragments.add(new CoordinatorFragment());
        fragments.add(new CoordinatorFragment());
        fragments.add(new CoordinatorFragment());

        adapter = new MyFragmentAdapter(getSupportFragmentManager(), fragments);
        mViewpager.setAdapter(adapter);

//        mToolbar.setTitle("唐嫣");
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    class MyFragmentAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;
        private FragmentManager fm;


        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        public MyFragmentAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fm = fm;
            this.fragments = fragments;
        }

        /**
         * 重写此方法,返回页面标题,用于viewpagerIndicator的页签显示
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return mTabs.getTabAt(position).getText();
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            getSupportFragmentManager().beginTransaction().show(fragment).commit();
            return fragment;
        }

        public void setFragments(ArrayList<Fragment> fragments) {
            if (this.fragments != null) {
                FragmentTransaction ft = fm.beginTransaction();
                for (Fragment f : this.fragments) {
                    ft.remove(f);
                }
                ft.commit();
                ft = null;
                fm.executePendingTransactions();
            }
            this.fragments = fragments;
            notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            fragment = fragments.get(position);
            Bundle bundle = new Bundle();
            bundle.putString("id", "" + position);
            fragment.setArguments(bundle);
            return fragment;
            //			return fragments.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //            super.destroyItem(container, position, object);
            Fragment fragment = fragments.get(position);
            getSupportFragmentManager().beginTransaction().hide(fragment).commit();
        }
    }
}
