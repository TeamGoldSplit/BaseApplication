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
            case 0: return KMeansImageFragment.newInstance(1, "KMeansImageFragment", "original_image.jpg");
            case 1: return KMeansImageFragment.newInstance(2, "KMeansImageFragment", "kmeans_output.jpg");
            case 2: return ProminentColorFragment.newInstance(3,"ProminentColorFragment", "color0.png");
            case 3: return PaletteFragment.newInstance(4,"PaletteFragment", calculatedColors);
            case 4: return GeneratedPaletteFragment.newInstance(5,"GeneratedPaletteFragment, Instance 1",  5);
            case 5: return GeneratedPaletteFragment.newInstance(6,"GeneratedPaletteFragment, Instance 2",  4);
            case 6: return GeneratedPaletteFragment.newInstance(7,"GeneratedPaletteFragment, Instance 3",  3);
            case 7: return GeneratedPaletteFragment.newInstance(8,"GeneratedPaletteFragment, Instance 4",  2);
            case 8: return GeneratedPaletteFragment.newInstance(9,"GeneratedPaletteFragment, Instance 5",  1);
            case 9: return GeneratedPaletteFragment.newInstance(10,"GeneratedPaletteFragment, Instance 6",  0);
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
        return 10;
    }

    @Override
    public CharSequence getPageTitle(int position){
        switch (position){
            case 0: return "Original Image (Resized)";
            case 1: return "Result of Decomposition";
            case 2: return "Most Prominent Color";
            case 3: return "Six Most Prominent Colors";
            case 4: return "Palette from Color 1";
            case 5: return "Palette from Color 2";
            case 6: return "Palette from Color 3";
            case 7: return "Palette from Color 4";
            case 8: return "Palette from Color 5";
            case 9: return "Palette from Color 6";
        }
        return "OBJECT" + (position + 1);
    }
}
