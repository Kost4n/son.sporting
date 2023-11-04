package com.bets.sons.porting.plug.act

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.bets.sons.porting.plug.game.GameView

class GamActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(GameView(this))
    }
}