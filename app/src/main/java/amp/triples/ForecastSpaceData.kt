package amp.triples

class ForecastSpaceData(
    override val serviceName: String,
    override val serviceKey: String
    ) : Service(serviceName, serviceKey) {

    override var uri: String = ""
        get() = "$serviceName?serviceKey=$serviceKey&base_date=$baseDate&base_time=$baseTime&nx=$nx&ny=$ny&numOfRows=$numOfRows&pageNo=$pageNo&type=$type"
    private var baseDate = DateTime.date()
    private var baseTime = DateTime.time()
    private var nx: String = "60"
    private var ny: String = "127"
    private var numOfRows: String = "9"
    private var pageNo: String = "1"
    private val type: String = "json"

}