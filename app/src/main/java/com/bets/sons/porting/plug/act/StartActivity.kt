package com.bets.sons.porting.plug.act

import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.bets.sons.porting.databinding.ActivityStartBinding

class StartActivity: AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        binding.imageStart.setOnClickListener {
            startActivity(Intent(this, GamActivity::class.java))
        }

        binding.imageRecord.setOnClickListener {
            startActivity(Intent(this, RecsActivity::class.java))
        }
    }
}