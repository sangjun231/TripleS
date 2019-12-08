package amp.triples

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_weather.view.*
import org.jetbrains.anko.backgroundColor


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

        //  하루간 날씨 표시
        for (i in 1..7) {

            val linearLayout = LinearLayout(MainActivity.instance)

            linearLayout.orientation = LinearLayout.VERTICAL
            linearLayout.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                1f
            )

            val timeUnitTime = TextView(MainActivity.instance)
            timeUnitTime.text = "${DateTime.now2(i).substring(8, 10)}"
            timeUnitTime.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            timeUnitTime.gravity = Gravity.CENTER
            linearLayout.addView(timeUnitTime)

            val timeUnitTemp = TextView(MainActivity.instance)
            timeUnitTemp.text = "${forecastSpaceData.getJSONObject(DateTime.now2(i).substring(0, 8)).getJSONObject(DateTime.now2(i).substring(8)).getInt("T3H")}°"
            timeUnitTemp.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            timeUnitTemp.gravity = Gravity.CENTER
            linearLayout.addView(timeUnitTemp)

            view.timeUnit.addView(linearLayout)

        }

        return view //super.onCreateView(inflater, container, savedInstanceState)
    }
}