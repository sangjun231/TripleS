package amp.triples

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {

    private val queue: RequestQueue by lazy { Volley.newRequestQueue(this) }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service =
            "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastSpaceData"
        val serviceKey =
            "8xaGWSV1v6FOopHsShsxWQ3QA424faF4ScjhtFMtY2M8HB%2BSxSmeDQgVEb6awrerBy0AbdOF1%2Fxumb59L47%2BeQ%3D%3D"


        val sample : String = "{\"response\":{\"header\":{\"resultCode\":\"0000\",\"resultMsg\":\"OK\"},\"body\":{\"items\":{\"item\":[\n" +
                "{\"baseDate\":20151021,\"baseTime\":\"0500\",\"category\":\"T3H\",\"fcstDate\":20151021,\"fcstTime\":\"0900\",\"fcstValue\":-50,\"nx\":1,\"ny\":1},\n" +
                "{\"baseDate\":20151021,\"baseTime\":\"0500\",\"category\":\"UUU\",\"fcstDate\":20151021,\"fcstTime\":\"0900\",\"fcstValue\":-5,\"nx\":1,\"ny\":1},\n" +
                "{\"baseDate\":20151021,\"baseTime\":\"0500\",\"category\":\"VVV\",\"fcstDate\":20151021,\"fcstTime\":\"0900\",\"fcstValue\":-1,\"nx\":1,\"ny\":1},\n" +
                "{\"baseDate\":20151021,\"baseTime\":\"0500\",\"category\":\"POP\",\"fcstDate\":20151021,\"fcstTime\":\"0900\",\"fcstValue\":-1,\"nx\":1,\"ny\":1},\n" +
                "{\"baseDate\":20151021,\"baseTime\":\"0500\",\"category\":\"REH\",\"fcstDate\":20151021,\"fcstTime\":\"0900\",\"fcstValue\":-1,\"nx\":1,\"ny\":1},\n" +
                "{\"baseDate\":20151021,\"baseTime\":\"0500\",\"category\":\"PTY\",\"fcstDate\":20151021,\"fcstTime\":\"0900\",\"fcstValue\":0,\"nx\":1,\"ny\":1},\n" +
                "{\"baseDate\":20151021,\"baseTime\":\"0500\",\"category\":\"R06\",\"fcstDate\":20151021,\"fcstTime\":\"0900\",\"fcstValue\":0,\"nx\":1,\"ny\":1},\n" +
                "{\"baseDate\":20151021,\"baseTime\":\"0500\",\"category\":\"S06\",\"fcstDate\":20151021,\"fcstTime\":\"0900\",\"fcstValue\":0,\"nx\":1,\"ny\":1},\n" +
                "{\"baseDate\":20151021,\"baseTime\":\"0500\",\"category\":\"TMN\",\"fcstDate\":20151021,\"fcstTime\":\"0900\",\"fcstValue\":0,\"nx\":1,\"ny\":1},\n" +
                "{\"baseDate\":20151021,\"baseTime\":\"0500\",\"category\":\"TMX\",\"fcstDate\":20151021,\"fcstTime\":\"0900\",\"fcstValue\":0,\"nx\":1,\"ny\":1},\n" +
                "{\"baseDate\":20151021,\"baseTime\":\"0500\",\"category\":\"SKY\",\"fcstDate\":20151021,\"fcstTime\":\"0900\",\"fcstValue\":1,\"nx\":1,\"ny\":1},\n" +
                "{\"baseDate\":20151021,\"baseTime\":\"0500\",\"category\":\"WAV\",\"fcstDate\":20151021,\"fcstTime\":\"0900\",\"fcstValue\":1,\"nx\":1,\"ny\":1},\n" +
                "{\"baseDate\":20151021,\"baseTime\":\"0500\",\"category\":\"WSD\",\"fcstDate\":20151021,\"fcstTime\":\"0900\",\"fcstValue\":5,\"nx\":1,\"ny\":1},\n" +
                "{\"baseDate\":20151021,\"baseTime\":\"0500\",\"category\":\"VEC\",\"fcstDate\":20151021,\"fcstTime\":\"0900\",\"fcstValue\":74,\"nx\":1,\"ny\":1}]\n" +
                ",\"numOfRows\":308,\"pageNo\":1,\"totalCount\":308}}}}"
        Log.i("test2", WetherInfo(sample).map["POP"].toString())
        val restPull = ForecastSpace(service, serviceKey)

        restPull.restPull(queue)

    }
}
