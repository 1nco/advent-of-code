package advent.of.code

import java.util.*
import kotlin.collections.ArrayList

class FifthPuzzle {
    companion object {
        private val day = "5";

        private var input: MutableList<String> = arrayListOf();

        private var sum = 0;

        private var sumSecond = 0;

        private var seeds = arrayListOf<Long>();
        private var seedsRange = arrayListOf<Long>();

        private var seedToSoilMap = arrayListOf<SourceDestination>();
        private var soilToFertilizerMap = arrayListOf<SourceDestination>()
        private var fertilizerToWaterMap = arrayListOf<SourceDestination>()
        private var waterToLightMap = arrayListOf<SourceDestination>()
        private var lightToTemperatureMap = arrayListOf<SourceDestination>()
        private var temperatureToHumidityMap = arrayListOf<SourceDestination>()
        private var humidityToLocationMap = arrayListOf<SourceDestination>()

        private var locationNums = arrayListOf<Long>();

        fun solve() {
            System.out.println(Date());
            input.addAll(Reader.readInput(day));
            var lineNum = 0;
            input.forEach { line ->
                if (lineNum == 0) {
                    var lin = line.split(":")[1];
                    seeds.addAll(lin.split(" ").filter { !it.equals("") }.map { num -> num.toLong() });
                }

                if (lineNum > 2 && lineNum < 31 && line != "") {
                    seedToSoilMap.add(createMap(line.split(" ")[0].toLong(), line.split(" ")[1].toLong(), line.split(" ")[2].toLong()));
                }

                if (lineNum > 31 && lineNum < 53 && line != "") {
                    soilToFertilizerMap.add(createMap((line.split(" ")[0].toLong()), (line.split(" ")[1].toLong()), (line.split(" ")[2].toLong())));
                }

                if (lineNum > 53 && lineNum < 103 && line != "") {
                    fertilizerToWaterMap.add(createMap((line.split(" ")[0].toLong()), (line.split(" ")[1].toLong()), (line.split(" ")[2].toLong())));
                }

                if (lineNum > 103 && lineNum < 147 && line != "") {
                    waterToLightMap.add(createMap((line.split(" ")[0].toLong()), (line.split(" ")[1].toLong()), (line.split(" ")[2].toLong())));
                }

                if (lineNum > 147 && lineNum < 173 && line != "") {
                    lightToTemperatureMap.add(createMap((line.split(" ")[0].toLong()), (line.split(" ")[1].toLong()), (line.split(" ")[2].toLong())));
                }

                if (lineNum > 173 && lineNum < 200 && line != "") {
                    temperatureToHumidityMap.add(createMap((line.split(" ")[0].toLong()), (line.split(" ")[1].toLong()), (line.split(" ")[2].toLong())));
                }
                if (lineNum > 200 && line != "") {
                    humidityToLocationMap.add(createMap((line.split(" ")[0].toLong()), (line.split(" ")[1].toLong()), (line.split(" ")[2].toLong())));
                }



//                if (lineNum > 2 && lineNum < 6 && line != "") {
//                    seedToSoilMap.add(createMap(line.split(" ")[0].toLong(), line.split(" ")[1].toLong(), line.split(" ")[2].toLong()));
//                }
//
//                if (lineNum > 6 && lineNum < 11 && line != "") {
//                    soilToFertilizerMap.add(createMap((line.split(" ")[0].toLong()), (line.split(" ")[1].toLong()), (line.split(" ")[2].toLong())));
//                }
//
//                if (lineNum > 11 && lineNum < 17 && line != "") {
//                    fertilizerToWaterMap.add(createMap((line.split(" ")[0].toLong()), (line.split(" ")[1].toLong()), (line.split(" ")[2].toLong())));
//                }
//
//                if (lineNum > 17 && lineNum < 21 && line != "") {
//                    waterToLightMap.add(createMap((line.split(" ")[0].toLong()), (line.split(" ")[1].toLong()), (line.split(" ")[2].toLong())));
//                }
//
//                if (lineNum > 21 && lineNum < 26 && line != "") {
//                    lightToTemperatureMap.add(createMap((line.split(" ")[0].toLong()), (line.split(" ")[1].toLong()), (line.split(" ")[2].toLong())));
//                }
//
//                if (lineNum > 26 && lineNum < 30 && line != "") {
//                    temperatureToHumidityMap.add(createMap((line.split(" ")[0].toLong()), (line.split(" ")[1].toLong()), (line.split(" ")[2].toLong())));
//                }
//                if (lineNum > 30 && line != "") {
//                    humidityToLocationMap.add(createMap((line.split(" ")[0].toLong()), (line.split(" ")[1].toLong()), (line.split(" ")[2].toLong())));
//                }


                lineNum++;
            }

            var minlocation = getDestinationFromSource(
                humidityToLocationMap, getDestinationFromSource(
                    temperatureToHumidityMap, getDestinationFromSource(
                        lightToTemperatureMap, getDestinationFromSource(
                            waterToLightMap, getDestinationFromSource(
                                fertilizerToWaterMap, getDestinationFromSource(
                                    soilToFertilizerMap, getDestinationFromSource(seedToSoilMap, seeds[0].toLong())
                                )
                            )
                        )
                    )
                )
            );

            seeds.forEach{ seed ->

                if (getDestinationFromSource(
                        humidityToLocationMap, getDestinationFromSource(
                            temperatureToHumidityMap, getDestinationFromSource(
                                lightToTemperatureMap, getDestinationFromSource(
                                    waterToLightMap, getDestinationFromSource(
                                        fertilizerToWaterMap, getDestinationFromSource(
                                            soilToFertilizerMap, getDestinationFromSource(seedToSoilMap, seed.toLong())
                                        )
                                    )
                                )
                            )
                        )
                    ) < minlocation) {
                    minlocation = getDestinationFromSource(
                        humidityToLocationMap, getDestinationFromSource(
                            temperatureToHumidityMap, getDestinationFromSource(
                                lightToTemperatureMap, getDestinationFromSource(
                                    waterToLightMap, getDestinationFromSource(
                                        fertilizerToWaterMap, getDestinationFromSource(
                                            soilToFertilizerMap, getDestinationFromSource(
                                                seedToSoilMap, seed.toLong())
                                        )
                                    )
                                )
                            )
                        )
                    );
                }


            }

            System.out.println("first:"  + minlocation);

            var minLocationSecond = getDestinationFromSource(
                humidityToLocationMap, getDestinationFromSource(
                    temperatureToHumidityMap, getDestinationFromSource(
                        lightToTemperatureMap, getDestinationFromSource(
                            waterToLightMap, getDestinationFromSource(
                                fertilizerToWaterMap, getDestinationFromSource(
                                    soilToFertilizerMap, getDestinationFromSource(seedToSoilMap, seeds[0].toLong())
                                )
                            )
                        )
                    )
                )
            );

            var seedNum = 0;
            var million = 0L;
            seeds.forEach{ seed ->
                if (seedNum % 2 == 0) {
                    var i = 0;
                    var range = seeds[seedNum + 1];
                    while (i < range) {
                        if (million % 1000000 == 0L) {
                            System.out.println(million);
                        }
                        if (getDestinationFromSource(
                                humidityToLocationMap, getDestinationFromSource(
                                    temperatureToHumidityMap, getDestinationFromSource(
                                        lightToTemperatureMap, getDestinationFromSource(
                                            waterToLightMap, getDestinationFromSource(
                                                fertilizerToWaterMap, getDestinationFromSource(
                                                    soilToFertilizerMap, getDestinationFromSource(seedToSoilMap, seed + i)
                                                )
                                            )
                                        )
                                    )
                                )
                            ) < minLocationSecond) {
                            minLocationSecond = getDestinationFromSource(
                                humidityToLocationMap, getDestinationFromSource(
                                    temperatureToHumidityMap, getDestinationFromSource(
                                        lightToTemperatureMap, getDestinationFromSource(
                                            waterToLightMap, getDestinationFromSource(
                                                fertilizerToWaterMap, getDestinationFromSource(
                                                    soilToFertilizerMap, getDestinationFromSource(seedToSoilMap, seed + i)
                                                )
                                            )
                                        )
                                    )
                                )
                            );
                        }
                        i++
                        million++;
                    }
                }
                seedNum++;
            }


            System.out.println("second:"  + minLocationSecond);
            System.out.println(Date());

        }

        private fun createMap(destination: Long, source: Long, range: Long): SourceDestination {
            return SourceDestination(source, destination, range);
        }

        private fun getDestinationFromSource(list: ArrayList<SourceDestination>, source: Long): Long {
            list.forEach{ item ->
                if (source >= item.source && source <= item.source + item.range) {
                    return item.destination + (source - item.source);
                }
            }
            return source;
        }
    }
}

class SourceDestination(source: Long, destination: Long, range: Long) {
    var source: Long;
    var destination: Long;
    var range: Long;

    init {
        this.source = source;
        this.destination = destination;
        this.range = range;
    }
}