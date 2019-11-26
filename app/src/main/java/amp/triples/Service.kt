package amp.triples

import android.content.Intent
import android.net.NetworkInfo
import android.provider.Browser
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.*
import org.jetbrains.anko.internals.AnkoInternals
import  org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.makeCall
import kotlin.coroutines.coroutineContext

abstract class
Service(
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
                Log.i("test", it)
                val jsonArray = JSONObject(strResp).getJSONObject("response")
                    .getJSONObject("body")
                    .getJSONObject("items")
                    .getJSONArray("item")

                for (i in 0 until jsonArray.length()) {

                    Log.i("test", jsonArray.getString(i))

                }
                val testList = ParseForcastSpace().parseData(JSONObject(strResp))
                Log.i("testParse",testList.get(0).SKY)

            },
            Response.ErrorListener { }
        )

        queue.add(request)

    }

}