package com.bets.sons.porting.plug.act

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.AbsoluteLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bets.sons.porting.databinding.ActivityGamBinding
import com.bets.sons.porting.plug.room.MyViewModel
import com.bets.sons.porting.plug.room.Rec


class GamActivity: AppCompatActivity() {
    private lateinit var binding: ActivityGamBinding
    var score = 0
    private lateinit var locTimerGame: CountDownTimer
    val text = "Score: "

    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamBinding.inflate(layoutInflater)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        setRandomXY(binding.fall1)
        createView()
        binding.fall1.setOnClickListener { v ->
            score += 10
            binding.scorText.text = text + score
            v.visibility = View.GONE
            timer(v as ImageView)
        }
        binding.fall2.setOnClickListener { v ->
            score += 15
            binding.scorText.text = text + score
            v.visibility = View.GONE
            timer(v as ImageView)
        }
        binding.fall3.setOnClickListener { v ->
            score += 20
            binding.scorText.text = text + score
            v.visibility = View.GONE
            timer(v as ImageView)
        }
        binding.fall4.setOnClickListener { v ->
            score += 25
            binding.scorText.text = text + score
            v.visibility = View.GONE
            timer(v as ImageView)
        }
        binding.fall5.setOnClickListener { v ->
            score += 15
            binding.scorText.text = text + score
            v.visibility = View.GONE
            timer(v as ImageView)
        }
        locTimerGame = timerGame(30000)

        binding.stop.setOnClickListener {
            locTimerGame.cancel()
            binding.stopView.visibility = View.VISIBLE
            binding.start.visibility = View.VISIBLE
        }

        binding.stopView.setOnClickListener {
            binding.stopView.visibility = View.GONE
            binding.start.visibility = View.GONE
            locTimerGame = timerGame(timeLeft)
        }

        binding.start.setOnClickListener {
            binding.stopView.visibility = View.GONE
            binding.start.visibility = View.GONE
            locTimerGame = timerGame(timeLeft)
        }
        val gi = Intent(this, GamActivity::class.java)
        binding.restart.setOnClickListener {
            startActivity(gi)
        }
    }
    private var timeLeft = 0L

    private fun createView() {
        var i = 0
        val arr = arrayOf(binding.fall2, binding.fall5, binding.fall3, binding.fall4)
        object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timer(arr[i])
                i++
            }

            override fun onFinish() {
            }
        }.start()
    }

    private fun timerGame(milSec: Long): CountDownTimer {
        val timer = object : CountDownTimer(milSec, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
                Log.d("GA timer", "--------millisUntilFinished = $millisUntilFinished --------")
            }

            override fun onFinish() {
                binding.restart.visibility = View.VISIBLE
                binding.restartView.visibility = View.VISIBLE
            }
        }.start()
        return timer
    }

    private fun timer(fallObj: ImageView): CountDownTimer {
        val timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                setRandomXY(fallObj)
            }
        }.start()

        return timer
    }



    private fun setRandomXY(fallObj: ImageView) {
        fallObj.visibility = View.VISIBLE
        val absParams = fallObj.layoutParams as AbsoluteLayout.LayoutParams
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels
        absParams.x = (50..(width - 50)).random()
        absParams.y = (50..(height - 50)).random()
        fallObj.layoutParams = absParams
    }


    override fun onPause() {
        super.onPause()
        locTimerGame.cancel()
        viewModel.addRec(Rec(score))
    }
}