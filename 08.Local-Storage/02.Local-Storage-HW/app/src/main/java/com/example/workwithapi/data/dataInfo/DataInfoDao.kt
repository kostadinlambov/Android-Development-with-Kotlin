package com.example.workwithapi.data.dataInfo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DataInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDataInfo(dataInfo: DataInfo)

    @Query("SELECT * FROM data_info as d ORDER BY d.update_time DESC LIMIT 1")
    fun getLast(): LiveData<DataInfo>

}