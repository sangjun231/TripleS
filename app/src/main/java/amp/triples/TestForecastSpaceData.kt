package amp.triples

class TestForecastSpaceData (
    override val serviceName: String,
    override val serviceKey: String
    ) : Service(serviceName, serviceKey) {

        override var uri: String = ""
        get() = "$serviceName?serviceKey=$serviceKey&base_date=$baseDate&base_time=$baseTime&nx=$nx&ny=$ny&numOfRows=$numOfRows&pageNo=$pageNo&type=$type"
        private var baseDate = "20191025"
        private var baseTime = "0250"
        private var nx: String = "60"
        private var ny: String = "127"
        private var numOfRows: String = "9"
        private var pageNo: String = "1"
        private val type: String = "json"
}