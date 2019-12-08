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
            val tempArray = ArrayList<JSONObject>()
            for(i in 0 until jA.length()){
                tempArray.add(jA.getJSONObject(i))
            }
            tempArray.sortWith(comparator = object :Comparator<JSONObject>{
                override fun compare(o1: JSONObject?, o2: JSONObject?): Int {
                    if(Integer.parseInt(o1?.getString("fcstDate")) > Integer.parseInt(o2?.getString("fcstDate"))){
                        return 1;
                    }
                    else if(Integer.parseInt(o1?.getString("fcstDate")) == Integer.parseInt(o2?.getString("fcstDate"))){
                        if(Integer.parseInt(o1?.getString("fcstTime")) > Integer.parseInt(o2?.getString("fcstTime")))
                            return 1;
                        else if(Integer.parseInt(o1?.getString("fcstTime")) == Integer.parseInt(o2?.getString("fcstTime")))
                            return 0;
                    }
                    return -1
                }

            })

            var nowTime = tempArray.get(0).getString("fcstTime")
            var nowDate = tempArray.get(0).getString("fcstDate")


            for(i in 0 until jA.length()){
                if(nowDate!=tempArray.get(i).getString("fcstDate")){
                    fcstDate.put(nowTime,fcstTime)
                    data.put(nowDate,fcstDate)
                    fcstTime = JSONObject()
                    fcstDate = JSONObject()
                    nowDate = tempArray.get(i).getString("fcstDate")
                    nowTime = tempArray.get(i).getString("fcstTime")
                    Log.i("Parse",nowDate)
                }
                else if(nowTime != tempArray.get(i).getString("fcstTime")){
                    fcstDate.put(nowTime,fcstTime)
                    fcstTime = JSONObject()
                    nowTime = tempArray.get(i).getString("fcstTime")
                }
                fcstTime.put(tempArray.get(i).getString("category"),tempArray.get(i).getString("fcstValue"))
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

    fun parseFineDust(JSONData: JSONObject) : JSONObject{
        var data : JSONObject = JSONObject()
        try {
            //val jO: JSONObject = JSONObject(JSONData)
            val jA: JSONArray = JSONData.getJSONArray("list")
            var dataTime = jA.getJSONObject(0).getString("dataTime").replace("-","").replace(":","").replace(" ","")
            var nowTime = dataTime
            var nowBorough = jA.getJSONObject(0).getString("cityName")
            var time = JSONObject()
            var borough = JSONObject()
            for(i in 0 until jA.length()){
                var dataTime = jA.getJSONObject(i).getString("dataTime").replace("-","").replace(":","").replace(" ","")
                if(nowBorough != jA.getJSONObject(i).getString("cityName")){
                    time.put(nowBorough,borough)
                    nowBorough = jA.getJSONObject(i).getString("cityName")
                    borough = JSONObject()
                }
                else if(nowTime != dataTime){
                    time.put(nowBorough,borough)
                    data.put(nowTime, time)
                    nowBorough = jA.getJSONObject(i).getString("cityName")
                    borough = JSONObject()
                    nowTime = dataTime
                    time = JSONObject()
                }
                borough.put("pm10Value",jA.getJSONObject(i).getString("pm10Value"))
                borough.put("pm25Value",jA.getJSONObject(i).getString("pm25Value"))
            }

            time.put(nowBorough,borough)
            data.put(nowTime, time)
        }catch ( e : JSONException){
            Log.i("JSON Error", "No value1")
        }
        return data
    }

}