package amp.triples

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ParseForcastSpace {

    val forcastSpaces = arrayListOf<ForcastSpace>()

    fun parseData(JSONData: JSONObject) : ArrayList<ForcastSpace> {
        this.forcastSpaces.clear()
        forcastSpaces.add(ForcastSpace())
        try {
            //val jO: JSONObject = JSONObject(JSONData)
            val jA: JSONArray = JSONData.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item")
            var nowTime = jA.getJSONObject(0).getString("fcstTime")
            var nowDate = jA.getJSONObject(0).getString("fcstDate")
            var nowIndex = 0
            for (i in 0 until jA.length()) {
                if(nowTime != jA.getJSONObject(i).getString("fcstTime") || nowDate != jA.getJSONObject(i).getString("fcstDate")) {
                    nowTime = jA.getJSONObject(i).getString("fcstTime")
                    nowDate = jA.getJSONObject(i).getString("fcstDate")
                    forcastSpaces.add(ForcastSpace())
                    nowIndex++
                    forcastSpaces.get(nowIndex).time = nowTime
                    forcastSpaces.get(nowIndex).date = nowDate
                }
                forcastSpaces.get(nowIndex).parseData(jA.getJSONObject(i))
            }
        }catch ( e : JSONException){
            Log.i("JSON Error", "No value1")
        }
        return forcastSpaces
    }



}