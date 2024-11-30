package advent.of.code.y2023
import advent.of.code.*

class FourthPuzzle {
    companion object {

        private val day = "4";

        private var input: MutableList<String> = arrayListOf();

        private var sum = 0;

        private var sumSecond = 0;
        fun solve() {
            input.addAll(Reader.readInput("2023",DAY));
            input.forEach { line ->
                processScratchCardFirst(line);
                processScratchCardSecond(line);
            }

            System.out.println("firstSum: " + sum);
            System.out.println("secondSum: " + sumSecond);
        }

        private fun processScratchCardFirst(line: String) {
            var numbers = line.split(":")[1].split("|")
            var winningNumbers = numbers[0].split(" ").filter { it -> !it.equals("") };
            var myNumbers = numbers[1].split(" ").filter { it -> !it.equals("") };
            var points = 0;
            winningNumbers.forEach { winningNumber ->
                myNumbers.forEach { myNumber ->
                    if (Integer.parseInt(winningNumber.trim()) == Integer.parseInt(myNumber.trim())) {
                        if (points == 0) {
                            points = 1;
                        } else {
                            points = points * 2
                        }
                    }
                }
            }
            sum += points;
        }

        private fun getScratchCard(num: Int): String {
            return input.get(num - 1);
        }

        private fun processScratchCardSecond(line: String) {
            var cardNum = Integer.parseInt(line.split(":")[0].replace("Card ", "").trim())
            var numbers = line.split(":")[1].split("|")
            var winningNumbers = numbers[0].split(" ").filter { it -> !it.equals("") };
            var myNumbers = numbers[1].split(" ").filter { it -> !it.equals("") };

            var winnerNumberCount = 0;

            winningNumbers.forEach { winningNumber ->
                myNumbers.forEach { myNumber ->
                    if (Integer.parseInt(winningNumber.trim()) == Integer.parseInt(myNumber.trim())) {
                        winnerNumberCount++;
                    }
                }
            }

            sumSecond++;

            var listOfWinnings = arrayListOf<String>()

            var i = 1;
            while (i <= winnerNumberCount) {
                listOfWinnings.add(getScratchCard(cardNum + i));
                i++;
            }

            if (!listOfWinnings.isEmpty()) {
                listOfWinnings.forEach { winning ->
                    processScratchCardSecond(winning);
                }
            }
        }
//        private fun processScratchCard(line: String): List<String> {
//            var cardNum = Integer.parseInt(line.split(":")[0].replace("Card ", ""))
//            var numbers = line.split(":")[1].split("|")
//            var winningNumbers = numbers[0].split(" ").filter { it -> !it.equals("") };
//            var myNumbers = numbers[1].split(" ").filter { it -> !it.equals("") };
//
//            var winnerNumberCount = 0;
//
//            winningNumbers.forEach { winningNumber ->
//                myNumbers.forEach { myNumber ->
//                    if (Integer.parseInt(winningNumber.trim()) == Integer.parseInt(myNumber.trim())) {
//                        winnerNumberCount++;
//                    }
//                }
//            }
//
//            var listOfWinnings = arrayListOf<String>()
//
//            var i = 1;
//            while (i <= winnerNumberCount) {
//                listOfWinnings.add(getScratchCard(cardNum + i));
//                i++;
//            }
//
//            sum++;
//            return listOfWinnings;
//        }
    }
}