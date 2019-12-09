package amp.triples

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_weather.view.*

class WeatherFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_weather, container, false)

        val location = GpsTracker(MainActivity.instance).location
        val gu = MainActivity.instance.getCurrentAddress(location.latitude, location.longitude).split(" ")[2]
        val forecastSpaceData = MyData.parseData!![0]
        val forecastGrib = MyData.parseData!![2]
        val forecastTimeData = MyData.parseData!![3]
        val nowSky = forecastTimeData.getJSONObject(DateTime.now().substring(0, 8)).getJSONObject(DateTime.now().substring(8)).getInt("SKY")
        val temp = forecastGrib.getInt("T1H")
        Log.i("test", "$forecastSpaceData")

        //  위치 업데이트
        view.location.text = gu

        //  구름 업데이트
        with (view.status) {

            text = when (nowSky) {

                1 -> "맑음"
                3 -> "구름많음"
                else -> "흐림"

            }

        }

        view.temperature.text = "$temp°"

        return view //super.onCreateView(inflater, container, savedInstanceState)
    }
}