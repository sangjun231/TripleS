package amp.triples

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    private var forecastSpace: ForecastSpaceDataService? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        //  splash image 시작
        startActivity<SplashActivity>()

        //  my data 초기화
        myDataInit()

        //  service 초기화
        serviceInit()

        //  network 확인
        val networkStatus = networkCheck()

        if (networkStatus) {

            Log.i("test", "connected")
//            TODO()

        } else {

            Log.i("test", "disconnected")
//            TODO()

        }

        //  test code
        forecastSpace!!.serviceParam = ForecastSpaceDataParam(
            DateTime.date(),
            DateTime.time(),
            "60",
            "127",
            "9",
            "1",
            "json"
        )

        //test view update
        val testSKYValue : String = "3"
        viewUpdate(testSKYValue)

        val a = RestPullManager.url(0)
        Log.i("test", a)

    }

    private fun myDataInit() {

        //  data file 있으면 이어 쓰고, 없으면 생성한다.
        try {

            val myData = openFileOutput("myData.dat", Context.MODE_APPEND)

        } catch (e: Exception) {

            e.printStackTrace()

        }

    }

    private fun serviceInit() {

        val service1 = Service(
            getString(R.string.service1_url),
            getString(R.string.service1_name),
            getString(R.string.key_ForecastSpaceData)
        )

        forecastSpace = ForecastSpaceDataService(service1)

        RestPullManager.serviceAdd(forecastSpace!!)

    }

    private fun networkCheck() = (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo?.isConnected ?: false

    //son9son9
    private fun viewUpdate (testSKYValue: String) {
        when (testSKYValue) {
            "1" -> { statusImage1.setImageResource(R.drawable.weather_sunny)
                        status.setText("맑음")}
            "3" -> { statusImage1.setImageResource(R.drawable.weather_cloudandsun)
                status.setText("구름 많음")}
            "4" -> { statusImage1.setImageResource(R.drawable.weather_cloudy)
                status.setText("흐림")}
            else -> toast("SKY value is invalid")

        }
    }

}