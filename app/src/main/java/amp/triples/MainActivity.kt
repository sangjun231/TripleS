package amp.triples

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private lateinit var forecastSpaceData: ForecastSpaceDataService
    private lateinit var getCtprvnMesureSidoLIst: GetCtprvnMesureSidoLIstService
    private lateinit var myData: MyData

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
        if (networkCheck()) {

            Log.i("NET", "connected")
            networkOnInit()

        } else {

            Log.i("NET", "disconnected")
            networkOffInit()

        }

    }

    private fun myDataInit() {

        val sharedPref = getSharedPreferences(getString(R.string.myData_filename), Context.MODE_PRIVATE)

        val gpsEnabled = sharedPref.getBoolean(getString(R.string.myData_key_gpsEnabled), true)
        val ox = sharedPref.getFloat(getString(R.string.myData_key_ox), 60f)
        val oy = sharedPref.getFloat(getString(R.string.myData_key_oy), 127f)
        val location = sharedPref.getString(getString(R.string.myData_key_location), "서울")!!

        myData = MyData(gpsEnabled, ox, oy, location)

    }

    override fun onStop() {

        super.onStop()

        val sharedPref = getSharedPreferences(getString(R.string.myData_filename), Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        editor.putBoolean(getString(R.string.myData_key_gpsEnabled), myData.gpsEnabled)
        editor.putFloat(getString(R.string.myData_key_ox), myData.ox)
        editor.putFloat(getString(R.string.myData_key_oy), myData.oy)
        editor.putString(getString(R.string.myData_key_location), myData.location)

        editor.apply()

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

        RestPullManager.serviceAdd(forecastSpaceData)
        RestPullManager.serviceAdd(getCtprvnMesureSidoLIst)

    }

    private fun networkCheck() = (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo?.isConnected ?: false

    private fun networkOnInit() {

        //  GPS 설정을 하지 않은 경우 myData 에 저장된 위치로 request 한다.
        if (!myData.gpsEnabled) {

            forecastSpaceData.serviceParam = ForecastSpaceDataParam(
                grid = Grid(myData.ox, myData.oy)
            )

            getCtprvnMesureSidoLIst.serviceParam = GetCtprvnMesureSidoLIstParam(
                sidoName = myData.location
            )

        }

        for (i in 0 until RestPullManager.size) {

            val url = RestPullManager.url(i)
            val contents = RestPullManager.request(url)
            Log.i("test", contents)
//            val list = forecastSpaceData.parse(contents)

        }

//        TODO()

    }

    private fun networkOffInit() {

        longToast("네트워크 연결 필요")
        finish()

    }

}