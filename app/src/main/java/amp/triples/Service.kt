package amp.triples

import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject

abstract class Service(
    open val serviceName: String,
    open val serviceKey: String
) {

    abstract var uri: String

    fun restPull(queue: RequestQueue) {

        val request = StringRequest(
            Request.Method.GET,
            uri,
            Response.Listener {

                var strResp = it.toString()
                val jsonArray = JSONObject(strResp).getJSONObject("response")
                    .getJSONObject("body")
                    .getJSONObject("items")
                    .getJSONArray("item")

                for (i in 0 until jsonArray.length()) {

                    Log.i("test", jsonArray.getString(i))

                }

            },
            Response.ErrorListener { }
        )

        queue.add(request)

    }

}