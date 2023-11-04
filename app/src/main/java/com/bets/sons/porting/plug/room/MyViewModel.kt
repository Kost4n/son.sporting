package com.bets.sons.porting.plug.room

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bets.sons.porting.plug.room.Rec
import com.bets.sons.porting.plug.room.RecDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel(application: Application): AndroidViewModel(application) {
    private val recDao by lazy {
        RecDB.getDatabase(application.applicationContext).getRecDao()
    }

    fun addRec(rec: Rec) = viewModelScope.launch(Dispatchers.IO) {
        recDao.addRec(rec)
    }
}