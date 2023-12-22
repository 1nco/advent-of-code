package advent.of.code

class SecondPuzzle {
    companion object {

        private val day = "2";

        private var input: MutableList<String> = arrayListOf();

        private val cubes = Game(12,13,14);

        private var sumOfIds = 0;

        private var sumOfPowers = 0;

        fun solve() {
            input.addAll(Reader.readInput(day));
            input.forEach {
                val games = getGames(it);
                if (areAllGamesPossible(games)) {
                    sumOfIds += getGameId(it);
                }

                val minGame = getMinGame(games);
                sumOfPowers += minGame.red * minGame.green * minGame.blue;
            }
            System.out.println("sumOfIds: " + sumOfIds);
            System.out.println("sumOfPowers: " + sumOfPowers);
        }

        private fun getGameId(line: String): Int {
             return Integer.parseInt(line.split(":")[0].replace("Game ", "").trim());
        }

        private fun getGames(line: String): List<Game> {
            val gamesAsString = line.split(":")[1].trim().split(";");
            return gamesAsString.map {
                val cubes = it.trim().split(",")
                Game(getCubeCountByColour(cubes, "red"), getCubeCountByColour(cubes, "green"), getCubeCountByColour(cubes, "blue"))
            }
        }

        private fun getCubeCountByColour(cubes: List<String>, colour: String): Int {
            val cube = cubes.find { it.contains(colour) }
            return if (cube != null) {
                Integer.parseInt(cube.replace(colour, "").trim())
            } else {
                0;
            }
        }

        private fun areAllGamesPossible(games: List<Game>): Boolean {
            return !games.map { isGamePossible(it) }.contains(false);
        }

        private fun isGamePossible(game: Game): Boolean {
            return game.red <= cubes.red && game.green <= cubes.green && game.blue <= cubes.blue;
        }

        private fun getMinGame(games: List<Game>): Game {
            var minRed = 0;
            var minGreen = 0;
            var minBlue = 0;

            games.forEach {
                if (it.red > minRed) {
                    minRed = it.red;
                }
                if (it.green > minGreen) {
                    minGreen = it.green;
                }
                if (it.blue > minBlue) {
                    minBlue = it.blue;
                }
            }

            return Game(minRed, minGreen, minBlue);
        }

    }
}

class Game(red: Int, green: Int, blue: Int) {
    var red: Int;
    var green: Int;
    var blue: Int;
    init {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
//    public fun getRed(): Int {
//        return this.red;
//    }
//
//    public fun setRed(value: Int) {
//        red = value;
//    }
//    public fun getGreen(): Int {
//        return this.green;
//    }
//
//    public fun setGreen(value: Int) {
//        green = value;
//    }
//    public fun getBlue(): Int {
//        return this.blue;
//    }
//
//    public fun setBlue(value: Int) {
//        blue = value;
//    }

}