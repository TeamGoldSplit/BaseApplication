package ssu.groupname.baseapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ColorPagerAdapter extends FragmentPagerAdapter {
    ColorPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int i){
        Fragment fragment = new ColorFragment();
        Bundle args = new Bundle();
        args.putInt(ColorFragment.ARG_OBJECT, i + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount(){
        return 100;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return "OBJECT" + (position + 1);
    }
}
