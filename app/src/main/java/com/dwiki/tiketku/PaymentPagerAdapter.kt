package com.dwiki.tiketku

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dwiki.tiketku.view.detail.DetailPenerbanganPulangFragment
import com.dwiki.tiketku.view.detail.DetailPergiFragment
import com.dwiki.tiketku.view.payment.PaymentRDepartureFragment
import com.dwiki.tiketku.view.payment.PaymentRReturnragment

class PaymentPagerAdapter(activity: AppCompatActivity):FragmentStateAdapter(activity) {

    override fun getItemCount(): Int  = 2

    override fun createFragment(position: Int): Fragment {
        var fragment:Fragment? = null
        when(position){
            0 -> fragment = PaymentRDepartureFragment()
            1 -> fragment = PaymentRReturnragment()
        }
        return fragment as Fragment
    }
}