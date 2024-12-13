package advent.of.code

import java.io.File
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


class Reader {

    companion object {

        private val appPath = "src/advent/of/code/" //script path

        private fun downloadInput(year: String, day: String) {
            val client = HttpClient.newBuilder().build();
            val request = HttpRequest.newBuilder()
                    .uri(URI.create("https://adventofcode.com/$year/day/$day/input"))
                    .header("Cookie", File(appPath + "SessionCookie.txt").readText())
                    .build();
            val response = client.send(request, HttpResponse.BodyHandlers.ofString())
            val file = File(getInputFileNameByDay(year, day))
            file.writeText(response.body());
        }

        fun readInput(year: String, day: String): List<String> {
            var file = File(getInputFileNameByDay(year, day));
            if (!file.exists()) {
                downloadInput(year, day);
            }
            return file.useLines { it.toList() }
        }

        fun readInputAsString(year: String, day: String): String {
            var file = File(getInputFileNameByDay(year, day));
            if (!file.exists()) {
                downloadInput(year, day);
            }
            return file.readText().trim();
        }

        private fun getInputFileNameByDay(year: String, day: String): String {
            return appPath + "inputs/$year-$day.txt";
        }
    }

}