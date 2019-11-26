package amp.triples

import android.util.Log
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL


class ForecastSpaceDataService(private val service: Service) : ServiceCommand {

    var serviceParam: ForecastSpaceDataParam? = null

    override fun url(): String {

        val requestUrl = StringBuilder().apply {

            append(service.serviceUrl)
            append(service.serviceName)
            append("?serviceKey=${service.serviceKey}")
            append("&base_date=${serviceParam!!.base_date}")
            append("&base_time=${serviceParam!!.base_time}")
            append("&nx=${serviceParam!!.nx}")
            append("&ny=${serviceParam!!.ny}")
            append("&numOfRows=${serviceParam!!.numOfRows}")
            append("&pageNo=${serviceParam!!.pageNo}")
            append("&type=${serviceParam!!.type}")

        }.toString()

        Log.i("url", requestUrl)

        try {

            val requestThread = Thread(Runnable{

                val url = URL(requestUrl)
                val inputStream = BufferedReader(InputStreamReader(url.openStream(), "UTF-8"))
                var buffer = ""

                while (true) {

                    val line: String? = inputStream.readLine() ?: break
                    buffer += line

                }


                val list = ParseForcastSpace.parseData(JSONObject(buffer))
                Log.i("parseTest",list.get(0).date)
                Log.i("parseTest",list.get(0).time)
                Log.i("parseTest",list.get(0).SKY)
                Log.i("parseTest",ParseForcastSpace.getData("20191126","1800")?.SKY)
                val jsonObjectRoot = JSONObject(buffer).getJSONObject("response")
                val jsonReulstCode = jsonObjectRoot.getJSONObject("header").getString("resultCode")

                when (jsonReulstCode) {

//                    in "0001" -> TODO()
//                    else -> TODO()

                }

                val jsonObjectSub = jsonObjectRoot.getJSONObject("body").getJSONObject("items").getJSONArray("item")

                for (i in 0 until jsonObjectSub.length()) {

                    var a = jsonObjectSub.getJSONObject(i).getString("baseDate")
                    Log.i("test", "$i: $a")

                }

            })

            requestThread.start()

        } catch (e: Exception) {

            e.printStackTrace()

        }

        return "abc"

    }

}