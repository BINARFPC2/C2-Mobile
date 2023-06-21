package com.dwiki.tiketku.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dwiki.tiketku.view.detail.DetailPenerbanganPulangFragment
import com.dwiki.tiketku.view.detail.DetailPergiFragment

class SectionPagerAdapter(activity: AppCompatActivity):FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment:Fragment? = null
        when(position){
            0 -> fragment = DetailPergiFragment()
            1 -> fragment = DetailPenerbanganPulangFragment()
        }
        return fragment as Fragment
    }
}