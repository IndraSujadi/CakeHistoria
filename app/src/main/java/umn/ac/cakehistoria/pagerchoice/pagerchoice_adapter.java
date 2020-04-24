package umn.ac.cakehistoria.pagerchoice;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import umn.ac.cakehistoria.PagerView_1;
import umn.ac.cakehistoria.PagerView_2;

public class pagerchoice_adapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 7;

    public pagerchoice_adapter(FragmentManager fragmentManager) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    // Returns total number of pages.
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for a particular page.
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new pagerchoice_1();
            case 1:
                return new pagerchoice_2();
            case 2:
                return new pagerchoice_3();
            case 3:
                return new pagerchoice_4();
            case 4:
                return new pagerchoice_5();
            case 5:
                return new pagerchoice_6();
            case 6:
                return new pagerchoice_7();
            default:
                return null;
        }
    }
}
