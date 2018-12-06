package ssu.groupname.baseapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ColorPagerAdapter extends FragmentPagerAdapter {
    ColorPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int i){
        String[] calculatedColors = {"color0.png","color1.png","color2.png","color3.png","color4.png","color5.png"};
        switch (i) {
            case 0: return KMeansImageFragment.newInstance(1, "KMeansImageFragment", "kmeans_output.png");
            case 1: return ProminentColorFragment.newInstance(2,"ProminentColorFragment", "color5.png");
            case 2: return PaletteFragment.newInstance(3,"PaletteFragment", calculatedColors);
            case 3: return GeneratedPaletteFragment.newInstance(4,"GeneratedPaletteFragment", calculatedColors);
            case 4: return ProminentColorFragment.newInstance(4,"ProminentColorFragment, Instance 4", "color0.png");
            default: return ProminentColorFragment.newInstance(0, "ProminentColorFragment, Default", "color0.png");
        }
//        Fragment fragment = new ProminentColorFragment();
//        Bundle args = new Bundle();
//        args.putInt(ProminentColorFragment.ARG_OBJECT, i + 1);
//        fragment.setArguments(args);
//        return fragment;
    }

    @Override
    public int getCount(){
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position){
        switch (position){
            case 0: return "Result of Decomposition";
            case 1: return "Most Common Color";
            case 2: return "Six Most Common Colors";
            case 3: return "Palette from Random Common Color";
        }
        return "OBJECT" + (position + 1);
    }
}
