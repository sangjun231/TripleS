package amp.triples

import android.util.Log
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL


class GetCtprvnMesureSidoLIstService(private val service: Service) : ServiceCommand {

    var serviceParam: GetCtprvnMesureSidoLIstParam? = null

    override fun url(): String {

        val requestUrl = StringBuilder().apply {

            append(service.serviceUrl)
            append(service.serviceName)
            append("?serviceKey=${service.serviceKey}")
            append("&numOfRows=${serviceParam!!.numOfRows}")
            append("&sidoName=${serviceParam!!.sidoName}")
            append("&searchCondition=${serviceParam!!.searchCondition}")
            append("&_returnType=${serviceParam!!.type}")

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

//                val jsonObjectRoot = JSONObject(buffer).getJSONObject("list")
//                val jsonReulstCode = jsonObjectRoot.getJSONObject("header").getString("resultCode")

//                when (jsonReulstCode) {

//                    in "0001" -> TODO()
//                    else -> TODO()

//                }

//                val jsonObjectSub = jsonObjectRoot.getJSONObject("body").getJSONObject("items").getJSONArray("item")

//                for (i in 0 until jsonObjectSub.length()) {

//                    var a = jsonObjectSub.getJSONObject(i).getString("baseDate")
//                    Log.i("test", "$i: $a")

//                }

            })

            requestThread.start()

        } catch (e: Exception) {

            e.printStackTrace()

        }

        return "abc"

    }

}