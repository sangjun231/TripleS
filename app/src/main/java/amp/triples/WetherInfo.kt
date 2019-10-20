package amp.triples


import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class WetherInfo  (wetherInfo : String){
    val map = emptyMap<String,String>().toMutableMap()
    init {
        try {
            val jO: JSONObject? = JSONObject(wetherInfo)

            val jA: JSONArray = jO?.getJSONObject("response")?.getJSONObject("body")?.getJSONObject("items")?.getJSONArray("item") ?: JSONArray()

            for (i in 0 until jA.length()) {
                map[jA.getJSONObject(i).getString("category")] =
                    jA.getJSONObject(i).getString("fcstValue")
            }
        }catch ( e : JSONException){
            Log.i("JSON Error", "No value")
        }
    }

}

