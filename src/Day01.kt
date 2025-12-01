import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        var dial = 50
        var countAtZero = 0
        input.forEach {
            dial = if (it[0] == 'L') (dial - it.substring(1).toInt()) % 100
            else (dial + it.substring(1).toInt()) % 100
            if (dial == 0) countAtZero++
        }
        return countAtZero
    }

    fun part2(input: List<String>): Int {
        var dial = 50
        var countAtZero = 0
        input.forEach {
            if (it[0] == 'L') {
                val move = it.substring(1).toInt()

                // We check if we crossed -99 <= dial <= 0
                // We skip this if we were already at 0
                if (dial != 0 && dial - move <= 0) {
                    countAtZero++
                }
                // Rest after the first one - how many hundreds would we reach?
                countAtZero += abs((dial - move)).floorDiv(100)

                dial = (dial - move).mod(100)
            } else {
                val move = it.substring(1).toInt()

                // How many hundreds would we reach?
                countAtZero += (dial + move).floorDiv(100)

                dial = (dial + move).mod(100)
            }
        }
        return countAtZero
    }

    // Read the input from the `src/Day01_test.txt` file.
    val testInput = readInput("Day01_test")
    println("TEST INPUT")
    part1(testInput).println()
    part2(testInput).println()
    check(part1(testInput) == 3)
    check(part2(testInput) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    println("INPUT")
    part1(input).println()
    part2(input).println()
}
