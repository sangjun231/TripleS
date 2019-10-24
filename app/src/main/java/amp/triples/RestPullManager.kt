package amp.triples

import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

object RestPullManager {

    var service: Service? = null

}

//abstract class RestPullManager(open val service: String? = null, open val serviceKey: String? = null) {
//
//    abstract var uri: String?
//
//    fun restPull(queue: RequestQueue) {
//
//        val request = StringRequest(
//            Request.Method.GET,
//            uri,
//            Response.Listener { response -> Log.i("test", "$response") },
//            Response.ErrorListener {}
//        )
//
//        queue.add(request)
//
//    }
//
//}