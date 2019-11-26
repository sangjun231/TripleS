package amp.triples

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private var forecastSpaceData: ForecastSpaceDataService? = null
    private var getCtprvnMesureSidoLIst: GetCtprvnMesureSidoLIstService? = null

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
        forecastSpaceData!!.serviceParam = ForecastSpaceDataParam(
            DateTime.date(),
            DateTime.time(),
            "60",
            "127",
            "9",
            "1",
            "json"
        )
        val a = RestPullManager.url(0)
        Log.i("test", a)

        getCtprvnMesureSidoLIst!!.serviceParam = GetCtprvnMesureSidoLIstParam(
            "10",
            "1",
            "서울",
            "DAILY",
            "json"
        )
        val b = RestPullManager.url(1)
        Log.i("test", b)

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

        val service2 = Service(
            getString(R.string.service2_url),
            getString(R.string.service2_name),
            getString(R.string.key_getCtprvnMesureSidoLIst)
        )

        forecastSpaceData = ForecastSpaceDataService(service1)
        getCtprvnMesureSidoLIst = GetCtprvnMesureSidoLIstService(service2)

        RestPullManager.serviceAdd(forecastSpaceData!!)
        RestPullManager.serviceAdd(getCtprvnMesureSidoLIst!!)

    }

    private fun networkCheck() = (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo?.isConnected ?: false

}
