package amp.triples

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

object ParseForcastSpace {

    val forcastSpaces = arrayListOf<ForcastSpace>()

    fun getData(fcstDate: String, fcstTime: String) : ForcastSpace?{
        for(forcastSpace in forcastSpaces){
            if(forcastSpace.date == fcstDate && forcastSpace.time == fcstTime){
                return forcastSpace
            }

        }
        return null
    }
    fun parseData(JSONData: JSONObject) : ArrayList<ForcastSpace> {
        this.forcastSpaces.clear()
        forcastSpaces.add(ForcastSpace())
        try {
            //val jO: JSONObject = JSONObject(JSONData)
            val jA: JSONArray = JSONData.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item")
            var nowTime = jA.getJSONObject(0).getString("fcstTime")
            var nowDate = jA.getJSONObject(0).getString("fcstDate")
            var nowIndex = 0
            var nowObject = forcastSpaces.get(nowIndex)
            nowObject.date = nowDate
            nowObject.time = nowTime
            for (i in 0 until jA.length()) {
                if(nowTime != jA.getJSONObject(i).getString("fcstTime") || nowDate != jA.getJSONObject(i).getString("fcstDate")) {
                    nowTime = jA.getJSONObject(i).getString("fcstTime")
                    nowDate = jA.getJSONObject(i).getString("fcstDate")
                    forcastSpaces.add(ForcastSpace())
                    nowObject = forcastSpaces.get(nowIndex)
                    nowIndex++
                    nowObject.time = nowTime
                    nowObject.date = nowDate
                }

                when(jA.getJSONObject(i).getString("category")){
                    "POP" -> nowObject.POP = jA.getJSONObject(i).getString("fcstValue")
                    "PTY" -> nowObject.PTY = jA.getJSONObject(i).getString("fcstValue")
                    "R06" -> nowObject.R06 = jA.getJSONObject(i).getString("fcstValue")
                    "REH" -> nowObject.REH = jA.getJSONObject(i).getString("fcstValue")
                    "S06" -> nowObject.S06 = jA.getJSONObject(i).getString("fcstValue")
                    "SKY" -> nowObject.SKY = jA.getJSONObject(i).getString("fcstValue")
                    "T3H" -> nowObject.T3H = jA.getJSONObject(i).getString("fcstValue")
                    "TMN" -> nowObject.TMN = jA.getJSONObject(i).getString("fcstValue")
                    "TMX" -> nowObject.TMX = jA.getJSONObject(i).getString("fcstValue")
                    "UUU" -> nowObject.UUU = jA.getJSONObject(i).getString("fcstValue")
                    "VVV" -> nowObject.VVV = jA.getJSONObject(i).getString("fcstValue")
                    "WAV" -> nowObject.WAV = jA.getJSONObject(i).getString("fcstValue")
                    "VEC" -> nowObject.VEC = jA.getJSONObject(i).getString("fcstValue")
                    "WSD" -> nowObject.WSD = jA.getJSONObject(i).getString("fcstValue")
                    else -> {Log.i("JSONError","category is not valid")}
                }
            }
        }catch ( e : JSONException){
            Log.i("JSON Error", "No value1")
        }
        return forcastSpaces
    }



}