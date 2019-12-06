package amp.triples

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

data class ForcastSpace (
    var POP : String? = "default",
    var PTY : String? = "default",
    var R06 : String? = "default",
    var REH : String? = "default",
    var S06 : String? = "default",
    var SKY : String? = "default",
    var T3H : String? = "default",
    var TMN : String? = "default",
    var TMX : String? = "default",
    var UUU : String? = "default",
    var VVV : String? = "default",
    var WAV : String? = "default",
    var VEC : String? = "default",
    var WSD : String? = "default",
    var time : String? = "default",
    var date : String? = "default"){
    fun parseData(JSONData: JSONObject) {
        try{

        }catch ( e : JSONException){
            Log.i("JSON Error", "No value2")
        }

    }
<<<<<<< HEAD

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

=======
>>>>>>> c2f6a1fd0d45c6445d5e7a81f59d055394c961e2
}