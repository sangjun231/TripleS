package amp.triples

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

object RestPullManager {

    private val serviceManager = mutableListOf<ServiceCommand>()
    val size
        get() = serviceManager.size

    fun serviceAdd(service: ServiceCommand) = serviceManager.add(service)
    fun serviceRemove(service: ServiceCommand) = serviceManager.remove(service)
    fun url(index: Int) = serviceManager[index].url()

    fun request(requestUrl: String): String {

        var buffer = ""

        try {

            val requestThread = Thread(Runnable {

                val url = URL(requestUrl)
                val inputStream = BufferedReader(InputStreamReader(url.openStream(), "UTF-8"))

                while (true) {

                    buffer += inputStream.readLine() ?: break

                }

            })

            requestThread.start()
            requestThread.join()

        } catch (e: Exception) {

            e.printStackTrace()

        }

        return buffer

    }

}