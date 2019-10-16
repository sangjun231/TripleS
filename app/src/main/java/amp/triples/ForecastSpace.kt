package amp.triples

class ForecastSpace(
    service: String, serviceKey: String
) : RestPull(service, serviceKey) {

    private var nx: String = "60"
    private var ny: String = "127"
    private var numOfRows: String = "9"
    private var pageNo: String = "1"
    private val type: String = "json"

    override var uri: String? = "$service?ServiceKey=$serviceKey&base_date=${DateTime.date()}&base_time=${DateTime.time()}&nx=$nx&ny=$ny&numOfRows=$numOfRows&pageNo=$pageNo&type=$type"

}