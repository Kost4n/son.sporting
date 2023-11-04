package com.bets.sons.porting.plug.act

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bets.sons.porting.databinding.ActivityRecsBinding
import com.bets.sons.porting.plug.room.Rec
import com.bets.sons.porting.plug.room.RecAdapter
import com.bets.sons.porting.plug.room.RecDB
import com.bets.sons.porting.plug.room.RecycleItemDecoration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecsActivity: AppCompatActivity() {
    private lateinit var binding: ActivityRecsBinding
    private lateinit var recsAdapter: RecAdapter
    private val recDao by lazy {
        RecDB.getDatabase(this.applicationContext).getRecDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecsBinding.inflate(layoutInflater)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        createRecycler()
    }

    private fun createRecycler() {
        var recycler = binding.recyclerRecs
        val recClick = object : RecAdapter.OnRecordClickListener {
            override fun onRecordClick(record: Rec, position: Int) {

            }
        }
        recsAdapter = RecAdapter(recClick)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)
        recycler.addItemDecoration(RecycleItemDecoration(4))

        recycler.adapter = recsAdapter
        observ()
    }

    private fun observ() {
        lifecycleScope.launch(Dispatchers.IO) {
            recDao.getRecs().collect() { flow ->
                if (flow.isNotEmpty()) {
                    recsAdapter.update(flow.toMutableList())
                }
            }
        }
    }
}