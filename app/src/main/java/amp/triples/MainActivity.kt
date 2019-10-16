package amp.triples

import android.os.Bundle
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

        val restPull = ForecastSpace(service, serviceKey)

        restPull.restPull(queue)

    }
}
