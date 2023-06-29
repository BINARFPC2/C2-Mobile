package com.dwiki.tiketku.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dwiki.tiketku.model.penumpang.PenumpangData


class TestViewModel:ViewModel() {
//    val dataList= MutableLiveData<ArrayList<PenumpangData>>(arrayListOf())
    private val dataList: MutableList<PenumpangData> = mutableListOf()

    fun addData(data:PenumpangData) {
        dataList.add(data)
    }
//     val list = dataList.value
//        if (list != null) {
//            list.add(data)
//            dataList.value = list!!
//        } else{
//            Log.d("TestViewModel","Tidak ada data")
//        }
//    }

    fun removeData(): List<PenumpangData> {
        dataList.clear()
        return dataList
    }
    fun getDataList(): List<PenumpangData> {
        return dataList
    }

//    fun getDataList(): MutableLiveData<ArrayList<PenumpangData>> {
//        return dataList
//    }


}