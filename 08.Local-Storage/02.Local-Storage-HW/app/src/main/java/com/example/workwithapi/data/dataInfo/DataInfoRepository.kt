package com.example.workwithapi.data.dataInfo

import androidx.lifecycle.LiveData

data class DataInfoRepository(private val dataInfoDao: DataInfoDao){

    fun getLast(): LiveData<DataInfo> {
        return dataInfoDao.getLast()
    }

    suspend fun addDataInfo(dataInfo: DataInfo){
        dataInfoDao.addDataInfo(dataInfo)
    }

}