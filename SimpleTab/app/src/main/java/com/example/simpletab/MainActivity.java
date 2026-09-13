package com.example.simpletab;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TextView tvTabMine;
    private TextView tvTabDiscover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager    = findViewById(R.id.viewPager);
        tvTabMine    = findViewById(R.id.tvTabMine);
        tvTabDiscover = findViewById(R.id.tvTabDiscover);

        // 禁止左右滑动切换（点击 tab 才切换）
        viewPager.setUserInputEnabled(false);

        // 设置 ViewPager2 适配器
        viewPager.setAdapter(new TabPagerAdapter(this));

        // 默认选中第一个 tab
        selectTab(0);

        // 点击"我的"
        tvTabMine.setOnClickListener(v -> {
            viewPager.setCurrentItem(0, false);
            selectTab(0);
        });

        // 点击"发现"
        tvTabDiscover.setOnClickListener(v -> {
            viewPager.setCurrentItem(1, false);
            selectTab(1);
        });
    }

    /**
     * 更新 Tab 选中状态样式
     * @param index 选中的 tab 索引（0=我的，1=发现）
     */
    private void selectTab(int index) {
        if (index == 0) {
            // "我的" 选中
            tvTabMine.setBackgroundResource(R.drawable.bg_tab_selected);
            tvTabMine.setTextColor(getResources().getColor(R.color.tab_selected_text, null));
            // "发现" 未选中
            tvTabDiscover.setBackgroundResource(R.drawable.bg_tab_unselected);
            tvTabDiscover.setTextColor(getResources().getColor(R.color.tab_unselected_text, null));
        } else {
            // "发现" 选中
            tvTabDiscover.setBackgroundResource(R.drawable.bg_tab_selected);
            tvTabDiscover.setTextColor(getResources().getColor(R.color.tab_selected_text, null));
            // "我的" 未选中
            tvTabMine.setBackgroundResource(R.drawable.bg_tab_unselected);
            tvTabMine.setTextColor(getResources().getColor(R.color.tab_unselected_text, null));
        }
    }

    // -------------------------------------------------------
    // ViewPager2 适配器（内部类）
    // -------------------------------------------------------
    private static class TabPagerAdapter extends FragmentStateAdapter {

        public TabPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return new Tab1Fragment();
            } else {
                return new Tab2Fragment();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}
