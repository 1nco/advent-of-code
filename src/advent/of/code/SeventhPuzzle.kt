package advent.of.code

import java.util.*

class SeventhPuzzle {
    companion object {

        private val day = "7";

        private var input: MutableList<String> = arrayListOf();

        private var result = 0L;

        private var resultSecond = 0L;

        var hands = mutableListOf<Hand>();
        var chars = arrayOf("A", "K", "Q", "T", "9", "8", "7", "6", "5", "4", "3", "2", "J");
        var types = arrayOf("five of a kind","four of a kind", "full house", "three of a kind", "two pair", "one pair", "high card")

        val comparator = object : Comparator<Hand> {
            override fun compare(o1: Hand?, o2: Hand?): Int {
                if (o1 != null && o2 != null) {
                    if (types.indexOf(o1.type) < types.indexOf(o2.type)) {
                        return -1;
                    }

                    if (types.indexOf(o1.type) > types.indexOf(o2.type)) {
                        return 1;
                    }

                    if (types.indexOf(o1.type) == types.indexOf(o2.type)) {
                        for (i in 0..5) {
                            if (o1.value[i] != o2.value[i]) {
                                if (chars.indexOf(o1.value[i].toString()) < chars.indexOf(o2.value[i].toString())) {
                                    return -1;
                                }
                                if (chars.indexOf(o1.value[i].toString()) > chars.indexOf(o2.value[i].toString())) {
                                    return 1;
                                }
                            }
                        }
                        return 0;
                    }
                    return 0;
                } else {
                    return 0;
                }
            }
        }

        fun solve() {
            System.out.println(Date());
            input.addAll(Reader.readInput(day));

            var lineNum = 0;
            input.forEach { line ->
                var hand = line.split(" ")[0];
                hands.add(Hand(line.split(" ")[0], "", line.split(" ")[1].trim().toLong()));
                lineNum++;
            }

            hands.forEach{ hand ->
                setHandType(hand, 5, "five of a kind");
                setHandType(hand, 4, "four of a kind");
                setFullHouseType(hand);
                setHandType(hand, 3, "three of a kind");
                setTwoOrOnePairType(hand);

                if (hand.type == "") {
                    hand.type = "high card";
                }
            }

            var sortedHands = hands.sortedWith(comparator);
            var i = sortedHands.size.toLong();
            sortedHands.forEach{hand ->
                result += hand.bid * i;
                i--;
            }
            System.out.println(result)



        }


        private fun setHandType(hand: Hand, count: Int, type: String) {
            chars.forEach { char ->
                var handValue = hand.value.replace("J", char);
                if (handValue.count { it.toString() == char } == count && hand.type == "") {
                    hand.type = type;
                }
            }
        }

        private fun setTwoOrOnePairType(hand: Hand) {
            var hasOnePair = false
            chars.forEach { char ->
                var handValue = if (!hasOnePair) hand.value.replace("J", char) else hand.value;
                if (handValue.count { it.toString() == char } == 2 && hand.type == "") {
                    if (hasOnePair) {
                        hand.type = "two pair";
                    } else {
                        hasOnePair = true;
                    }
                }
            }
            if (hasOnePair && hand.type == "") {
                hand.type = "one pair";
            }
        }

        private fun setFullHouseType(hand: Hand) {
            var hasThreePair = false;
            var threePairChar = "";
            chars.forEach { char ->
                var handValue = hand.value.replace("J", char);
                if (handValue.count { it.toString() == char } == 3 && hand.type == "") {
                    hasThreePair = true;
                    threePairChar = char;
                }
            }

            chars.forEach { char ->
                if (hand.value.count { it.toString() == char } == 2 && hand.type == "" && char != threePairChar && char != "J") {
                    if (hasThreePair) {
                        hand.type = "full house";
                    }
                }
            }
        }
    }
}

class Hand(value: String, type: String, bid: Long) {
    var value: String;
    var type: String;
    var bid: Long;

    init {
        this.value = value;
        this.type = type;
        this.bid = bid;
    }
}