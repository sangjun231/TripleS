package amp.triples

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.startActivity

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

        //  test code
        forecastSpace!!.serviceParam = ForecastSpaceDataParam(DateTime.date(), DateTime.time(), "60", "127", "9", "1", "json")
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

}
