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
        val root = MyData.parseData!![2]
//        val nowSky = root.getJSONObject(DateTime.baseTime2().substring(0, 8)).getJSONObject(DateTime.baseTime2().substring(8)).getInt("SKY")
Log.i("test", root.toString())
        //  위치 업데이트
        view.location.text = gu

        //  구름 업데이트
//        with (view.status) {
//
//            text = when (nowSky) {
//
//                1 -> "맑음"
//                3 -> "구름많음"
//                else -> "흐림"
//
//            }
//
//        }


        return view //super.onCreateView(inflater, container, savedInstanceState)
    }
}