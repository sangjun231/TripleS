package amp.triples

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_finedust.view.*
import kotlinx.android.synthetic.main.fragment_weather.view.location
import org.jetbrains.anko.sdk25.coroutines.onTouch

class FineDustFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_finedust, container, false)

        val location = GpsTracker(MainActivity.instance).location
        val gu = MainActivity.instance.getCurrentAddress(
            location.latitude,
            location.longitude
        ).split(" ")[2]
        val forecastFineDust = MyData.parseData!![1]
        var now10Pm = Integer.parseInt(forecastFineDust.getJSONObject(gu).getString("pm10Value"))
        var now25Pm = Integer.parseInt(forecastFineDust.getJSONObject(gu).getString("pm25Value"))
        Log.i("test", forecastFineDust.toString())

        //  위치 업데이트
        view.location.text = gu


        Log.i("DataTest",now10Pm.toString())
        //  구름 업데이트
        with(view) {

             when (now10Pm) {
                in 0..30 -> {
                    pm10Status.text = "좋음"
                    now10Image.setImageResource(R.drawable.emoticon_eight_level_big_2)
                }
                in 31..80 -> {
                    pm10Status.text = "보통"
                    now10Image.setImageResource(R.drawable.emoticon_eight_level_big_4)
                }
                in 81..100 -> {
                    pm10Status.text = "나쁨"
                    now10Image.setImageResource(R.drawable.emoticon_eight_level_big_5)
                }
                else -> {
                    pm10Status.text = "매우나쁨"
                    now10Image.setImageResource(R.drawable.emoticon_eight_level_big_7)
                }
            }
            when (now25Pm) {
            in 0..15 -> {
                pm25Status.text = "좋음"
                now25Image.setImageResource(R.drawable.emoticon_eight_level_big_2)
            }
            in 16..35 -> {
                pm25Status.text = "보통"
                now25Image.setImageResource(R.drawable.emoticon_eight_level_big_4)
            }
            in 36..75 -> {
                pm25Status.text = "나쁨"
                now25Image.setImageResource(R.drawable.emoticon_eight_level_big_5)
            }
            else -> {
                pm25Status.text = "매우나쁨"
                now25Image.setImageResource(R.drawable.emoticon_eight_level_big_7)
            }
        }
        }
        with(view.pm10Bar){
            if(now10Pm>200)
                progress = 100
            else
                progress = now10Pm/2
            setOnTouchListener(View.OnTouchListener { v, event -> true })

        }
        with(view.pm25Bar){
            if(now25Pm>100)
                progress = 100
            else
                progress = now25Pm
            setOnTouchListener(View.OnTouchListener { v, event -> true })
        }
        view.now10Text.text = now10Pm.toString() + "μg/m³"
        view.now25Text.text = now25Pm.toString() + "μg/m³"


        return view //super.onCreateView(inflater, container, savedInstanceState)

    }
}