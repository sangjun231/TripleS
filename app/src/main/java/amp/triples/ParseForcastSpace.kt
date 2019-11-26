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
            var nowtime = jA.getJSONObject(0).getString("baseTime")
            var nowIndex = 0
            for (i in 0 until jA.length()) {
                if(nowtime != jA.getJSONObject(i).getString("baseTime")) {
                    nowtime = jA.getJSONObject(i).getString("baseTime")
                    forcastSpaces.add(ForcastSpace())
                    nowIndex++
                    forcastSpaces.get(nowIndex).time = nowtime
                }
                forcastSpaces.get(nowIndex).parseData(jA.getJSONObject(i))
            }
        }catch ( e : JSONException){
            Log.i("JSON Error", "No value1")
        }
        return forcastSpaces
    }



}