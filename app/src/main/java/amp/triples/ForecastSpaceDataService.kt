package amp.triples


class ForecastSpaceDataService(private val service: Service) : ServiceCommand {

    var serviceParam: ForecastSpaceDataParam? = null

    override fun url(): String {

        return StringBuilder().apply {

            append(service.serviceUrl)
            append(service.serviceName)
            append("?serviceKey=${service.serviceKey}")
            append("&base_date=${serviceParam!!.base_date}")
            append("&base_time=${serviceParam!!.base_time}")
            append("&nx=${serviceParam!!.nx}")
            append("&ny=${serviceParam!!.ny}")
            append("&numOfRows=${serviceParam!!.numOfRow}")
            append("&pageNo=${serviceParam!!.pageNo}")
            append("&type=${serviceParam!!.type}")

        }.toString()

    }

}