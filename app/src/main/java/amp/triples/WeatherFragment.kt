package amp.triples

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_weather.view.*
import java.time.DayOfWeek
import java.time.LocalDateTime


class WeatherFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_weather, container, false)

        val location = GpsTracker(MainActivity.instance).location
        val gu = MainActivity.instance.getCurrentAddress(
            location.latitude,
            location.longitude
        ).split(" ")[2]
        val forecastSpaceData = MyData.parseData!![0]
        val forecastGrib = MyData.parseData!![2]
        val forecastTimeData = MyData.parseData!![3]
        Log.i("test", forecastTimeData.toString())
        val temp = forecastGrib.getInt("T1H")

        //  위치 업데이트
        view.location.text = gu

        //  구름 업데이트
        with(view.status) {

            text = when (forecastTimeData.getJSONObject(DateTime.now().substring(0, 8))
                .getJSONObject(DateTime.now().substring(8)).getInt("PTY")) {

                0 ->
                    when (forecastTimeData.getJSONObject(DateTime.now().substring(0, 8))
                        .getJSONObject(DateTime.now().substring(8)).getInt("SKY")) {

                        1 -> "맑음"
                        3 -> "구름많음"
                        else -> "흐림"

                    }

                1, 2 -> "비"
                3 -> "눈"
                else -> "소나기"

            }

        }

        view.temperature.text = "$temp°"

        //  하루간 날씨 표시
        for (i in 1..9) {

            val linearLayout = LinearLayout(MainActivity.instance)

            linearLayout.orientation = LinearLayout.VERTICAL
            linearLayout.layoutParams = LinearLayout.LayoutParams(
                150,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            linearLayout.gravity = Gravity.CENTER

            val timeUnitTime = TextView(MainActivity.instance)
            timeUnitTime.text = "${DateTime.now2(i).substring(8, 10)}시"
            timeUnitTime.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            timeUnitTime.gravity = Gravity.CENTER
            linearLayout.addView(timeUnitTime)

            val timeUnitImage = ImageView(MainActivity.instance)
            timeUnitImage.layoutParams = LinearLayout.LayoutParams(
                100,
                100
            )

            when (forecastSpaceData.getJSONObject(
                DateTime.now2(i).substring(
                    0,
                    8
                )
            ).getJSONObject(
                DateTime.now2(i).substring(8)
            ).getInt("PTY")) {

                0 ->
                    when (forecastSpaceData.getJSONObject(
                        DateTime.now2(i).substring(
                            0,
                            8
                        )
                    ).getJSONObject(DateTime.now2(i).substring(8)).getInt("SKY")) {

                        1 -> timeUnitImage.setImageResource(R.drawable.weather_sunny)
                        3 -> timeUnitImage.setImageResource(R.drawable.weather_cloudandsun)
                        4 -> timeUnitImage.setImageResource(R.drawable.weather_cloudy)

                    }

                1, 2 -> timeUnitImage.setImageResource(R.drawable.weather_rain)
                3 -> timeUnitImage.setImageResource(R.drawable.weather_snow)
                4 -> timeUnitImage.setImageResource(R.drawable.weather_umbrella)

            }

            linearLayout.addView(timeUnitImage)

            val timeUnitTemp = TextView(MainActivity.instance)
            timeUnitTemp.text =
                "${forecastSpaceData.getJSONObject(
                    DateTime.now2(i).substring(
                        0,
                        8
                    )
                ).getJSONObject(
                    DateTime.now2(i).substring(8)
                ).getInt("T3H")}°"
            timeUnitTemp.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            timeUnitTemp.gravity = Gravity.CENTER
            linearLayout.addView(timeUnitTemp)

            view.timeUnit.addView(linearLayout)

        }

        with (view.dayOfTheWeek1) {

            text = when (LocalDateTime.now().dayOfWeek) {

                DayOfWeek.SUNDAY -> "일요일"
                DayOfWeek.MONDAY -> "월요일"
                DayOfWeek.TUESDAY -> "화요일"
                DayOfWeek.WEDNESDAY -> "수요일"
                DayOfWeek.THURSDAY -> "목요일"
                DayOfWeek.FRIDAY -> "금요일"
                DayOfWeek.SATURDAY -> "토요일"

            }

        }

        return view //super.onCreateView(inflater, container, savedInstanceState)

    }
}