package amp.triples

abstract class RestPull(val service: String? = null, val serviceKey: String? = null) {

    abstract fun url(): String?

}

class ForecastSpace(service: String, serviceKey: String, baseDate: String = "20151201", baseTime: String = "0500", nx: String = "60", ny: String = "127", numOfRows: String = "10", pageNo: String = "1", type: String = "json") : RestPull(service, serviceKey) {

    var baseDate: String = baseDate
    var baseTime: String = baseTime
    var nx: String = nx
    var ny: String = ny
    var numOfRows: String = numOfRows
    var pageNo: String = pageNo
    var type: String = type

    override fun url(): String? = "$service?ServiceKey=$serviceKey&base_date=$baseDate&base_time=$baseTime&nx=$nx&ny=$ny&numOfRows=$numOfRows&pageNo=$pageNo&type=$type"

}