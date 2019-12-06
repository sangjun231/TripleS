package amp.triples

import android.location.Location
import android.util.Log

class GetCtprvnMesureSidoLIstService(private val service: Service) : ServiceCommand {

    var serviceParam: GetCtprvnMesureSidoLIstParam? = null

    override fun url(): String {

        val sido = addrConvert(gpsTracker())

        val requestUrl = StringBuilder().apply {

            append(service.serviceUrl)
            append(service.serviceName)
            append("?serviceKey=${service.serviceKey}")
            append("&numOfRows=${serviceParam?.numOfRows ?: 10}")
            append("&sidoName=${serviceParam?.sidoName ?: sido }")
            append("&searchCondition=${serviceParam?.searchCondition ?: "DAILY"}")
            append("&_returnType=json")

        }.toString()

        Log.i("URL", requestUrl)

        return requestUrl

    }

    private fun gpsTracker(): Location {

        val gpsTracker = GpsTracker(MainActivity.instance)
        //  GPS 좌표로 GPS를 불러옴
        return gpsTracker.getLocation()

    }

    private fun addrConvert(location: Location): String? {

        val addr = MainActivity.instance.getCurrentAddress(location.latitude, location.longitude)
        val addrCity = addr.split(" ")[1]

        for (city in MainActivity.instance.resources.getStringArray(R.array.cities)) {

            if (addrCity.contains(city)) {

                return city

            }

        }

        return null

    }

}