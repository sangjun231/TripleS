package amp.triples

import android.util.Log

class GetCtprvnMesureSidoLIstService(private val service: Service) : ServiceCommand {

    var serviceParam: GetCtprvnMesureSidoLIstParam? = null

    override fun url(): String {

        val requestUrl = StringBuilder().apply {

            append(service.serviceUrl)
            append(service.serviceName)
            append("?serviceKey=${service.serviceKey}")
            append("&numOfRows=${serviceParam?.numOfRows ?: 10}")
            append("&sidoName=${serviceParam?.sidoName ?: "서울" /*?: GPS.sido*/}")
            append("&searchCondition=${serviceParam?.searchCondition ?: "DAILY"}")
            append("&_returnType=json")

        }.toString()

        Log.i("URL", requestUrl)

        return requestUrl

    }

}