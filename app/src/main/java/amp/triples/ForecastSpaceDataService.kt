package amp.triples

import android.util.Log
import org.json.JSONObject

class ForecastSpaceDataService(private val service: Service) : ServiceCommand {

    var serviceParam: ForecastSpaceDataParam? = null

    override fun url(): String {

        val requestUrl = StringBuilder().apply {

            append(service.serviceUrl)
            append(service.serviceName)
            append("?serviceKey=${service.serviceKey}")
            append("&base_date=${serviceParam?.base_date ?: DateTime.date()}")
            append("&base_time=${serviceParam?.base_time ?: DateTime.time()}")
            append("&nx=${serviceParam?.grid?.xo ?: 60 /*?: GPS.x*/}")
            append("&ny=${serviceParam?.grid?.yo ?: 127 /*?: GPS.y*/}")
            append("&numOfRows=${serviceParam?.numOfRows ?: 10}")
            append("&pageNo=${serviceParam?.pageNo ?: 1}")
            append("&type=json")

        }.toString()

        Log.i("URL", requestUrl)

        return requestUrl

    }

    fun parse(contents: String): ArrayList<ForcastSpace> {

        val jsonObjectRoot = JSONObject(contents).getJSONObject("response")
        val jsonReulstCode = jsonObjectRoot.getJSONObject("header").getString("resultCode")

        when (jsonReulstCode) {

//            in "0001" -> TODO()
//            else -> TODO()

        }

//        val jsonObjectSub = jsonObjectRoot.getJSONObject("body").getJSONObject("items").getJSONArray("item")

        return ParseForcastSpace.parseData(JSONObject(contents))

    }
}