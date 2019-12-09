package amp.triples

import android.location.Location
import android.util.Log

class ForecastSpaceDataService(private val service: Service) : ServiceCommand {

    var serviceParam: ForecastSpaceDataParam? = null

    override fun url(): String {

        val grid by lazy { gpsConvert(gpsTracker()) }

        val requestUrl = StringBuilder().apply {

            append(service.serviceUrl)
            append(service.serviceName)
            append("?serviceKey=${service.serviceKey}")
            append("&base_date=${serviceParam?.base_date ?: DateTime.baseTime().substring(0, 8)}")
            append("&base_time=${serviceParam?.base_time ?: DateTime.baseTime().substring(8)}")
            append("&nx=${serviceParam?.grid?.ox ?: grid.ox}")
            append("&ny=${serviceParam?.grid?.oy ?: grid.ox}")
            append("&numOfRows=${serviceParam?.numOfRows ?: 200}")
            append("&pageNo=${serviceParam?.pageNo ?: 1}")
            append("&type=json")

        }.toString()

        Log.i("URL", requestUrl)

        return requestUrl

    }

    private fun gpsTracker(): Location {

        val gpsTracker = GpsTracker(MainActivity.instance)
        //  GPS 좌표로 GPS를 불러옴
        return gpsTracker.getLocation()

    }

    private fun gpsConvert(location: Location): Grid {

        val gps = GpsConverter()
        //  GPS 좌표를 기상청에서 제공하는 좌표로 변환
        val latX_lngY = gps.convertGRID_GPS(GpsConverter.TO_GRID, location.latitude, location.longitude)
        //  Double 타입을 Int 타입으로 변환
        return Grid(
            latX_lngY.x.toInt(),
            latX_lngY.y.toInt()
        )

    }

}