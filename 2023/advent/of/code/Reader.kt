package advent.of.code

import java.io.File
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


class Reader {

    companion object {

        private val appPath = "out/production/advent-of-code/advent/of/code/" // IntelliJ IDEA path
//        private val appPath = "advent/of/code/" //script path

        private fun downloadInput(day: String) {
            val client = HttpClient.newBuilder().build();
            val request = HttpRequest.newBuilder()
                    .uri(URI.create("https://adventofcode.com/2023/day/" + day + "/input"))
                    .header("Cookie", File(appPath + "SessionCookie.txt").readText())
                    .build();
            val response = client.send(request, HttpResponse.BodyHandlers.ofString())
            val file = File(getInputFileNameByDay(day))
            file.writeText(response.body());
        }

        fun readInput(day: String): List<String> {
            var file = File(getInputFileNameByDay(day));
            if (!file.exists()) {
                downloadInput(day);
            }
            return file.useLines { it.toList() }
        }

        private fun getInputFileNameByDay(day: String): String {
            return appPath + "inputs/" + day + ".txt";
        }
    }

}