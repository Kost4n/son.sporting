package com.bets.sons.porting

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.bets.sons.porting.databinding.ActivityNoInetBinding

class NoInetActivity: AppCompatActivity() {
    private lateinit var binding: ActivityNoInetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoInetBinding.inflate(layoutInflater)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
    }
}