package amp.triples

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

object ParseForecastData {

    fun parseForecastSpace(JSONData: JSONObject) : JSONObject {

        var data : JSONObject = JSONObject()
        var fcstDate : JSONObject = JSONObject()
        var fcstTime : JSONObject = JSONObject()
        try {
            //val jO: JSONObject = JSONObject(JSONData)
            val jA: JSONArray = JSONData.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item")
            var nowTime = jA.getJSONObject(0).getString("fcstTime")
            var nowDate = jA.getJSONObject(0).getString("fcstDate")


            for(i in 0 until jA.length()){
                if(nowDate!=jA.getJSONObject(i).getString("fcstDate")){
                    fcstDate.put(nowTime,fcstTime)
                    data.put(nowDate,fcstDate)
                    fcstTime = JSONObject()
                    fcstDate = JSONObject()
                    nowDate = jA.getJSONObject(i).getString("fcstDate")
                    nowTime = jA.getJSONObject(i).getString("fcstTime")
                    Log.i("Parse",nowDate)
                }
                else if(nowTime != jA.getJSONObject(i).getString("fcstTime")){
                    fcstDate.put(nowTime,fcstTime)
                    fcstTime = JSONObject()
                    nowTime = jA.getJSONObject(i).getString("fcstTime")
                }
                fcstTime.put(jA.getJSONObject(i).getString("category"),jA.getJSONObject(i).getString("fcstValue"))
            }
            fcstDate.put(nowTime,fcstTime)
            data.put(nowDate,fcstDate)
        }catch ( e : JSONException){
            Log.i("JSON Error", "No value1")
        }
        return data
    }


    fun parseForecastGrip(JSONData: JSONObject) : JSONObject{
        var data : JSONObject = JSONObject()
        try {
            //val jO: JSONObject = JSONObject(JSONData)
            val jA: JSONArray = JSONData.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item")
            data.put("baseDate",jA.getJSONObject(0).getString("baseDate"))
            data.put("baseTime",jA.getJSONObject(0).getString("baseTime"))

            for(i in 0 until jA.length()){
                data.put(jA.getJSONObject(i).getString("category"),jA.getJSONObject(i).getString("obsrValue"))
            }
        }catch ( e : JSONException){
            Log.i("JSON Error", "No value1")
        }
        return data
    }



}