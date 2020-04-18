package umn.ac.cakehistoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class Create_order extends AppCompatActivity {

    FragmentPagerAdapter adapterViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        ViewPager vpPager = (ViewPager)findViewById(R.id.pagerView);
        adapterViewPager = new PagerViewAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

    }
}
