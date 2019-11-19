package amp.triples

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ForcastSpace {
    var POP : String? = null
    var PTY : String? = null
    var R06 : String? = null
    var REH : String? = null
    var S06 : String? = null
    var SKY : String? = null
    var T3H : String? = null
    var TMN : String? = null
    var TMX : String? = null
    var UUU : String? = null
    var VVV : String? = null
    var WAV : String? = null
    var VEC : String? = null
    var WSD : String? = null
    fun parseData(JSONData: JSONObject) {
        try {
            findCategory(JSONData.getString("category"),JSONData.getString("fcstvalue"))

        }catch ( e : JSONException){
            Log.i("JSON Error", "No value")
        }

    }

    fun findCategory(key : String, value : String){
        when(key){
            "POP" -> POP = value
            "PTY" -> PTY = value
            "R06" -> R06 = value
            "REH" -> REH = value
            "S06" -> S06 = value
            "SKY" -> SKY = value
            "T3H" -> T3H = value
            "TMN" -> TMN = value
            "TMX" -> TMX = value
            "UUU" -> UUU = value
            "VVV" -> VVV = value
            "WAV" -> WAV = value
            "VEC" -> VEC = value
            "WSD" -> WSD = value
            else -> {Log.i("JSONError","category is not valid")}
        }
    }



}