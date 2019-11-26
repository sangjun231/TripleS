package amp.triples

data class GetCtprvnMesureSidoLIstParam(
    val numOfRows: String,
    val pageNo : String,
    val sidoName: String,
    val searchCondition: String,
    val type: String
)