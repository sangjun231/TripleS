package amp.triples

object RestPullManager {

    private val serviceManager = mutableListOf<ServiceCommand>()

    fun serviceAdd(service: ServiceCommand) = serviceManager.add(service)
    fun serviceRemove(service: ServiceCommand) = serviceManager.remove(service)
    fun url(index: Int) = serviceManager[index].url()

}