package day1

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State
import println
import readInput
import kotlin.math.abs

@State(Scope.Benchmark)
class Day01 {
    var input: List<String> = listOf()

    @Setup
    fun setUp() {
        input = readInput("day1/Day01")
    }

    @Benchmark
    fun part1(): Int {
        var dial = 50
        var countAtZero = 0
        input.forEach {
            dial = if (it[0] == 'L') (dial - it.substring(1).toInt()) % 100
            else (dial + it.substring(1).toInt()) % 100
            if (dial == 0) countAtZero++
        }
        return countAtZero
    }

    @Benchmark
    fun part2(): Int {
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
}

fun main() {
    val day1 = Day01()

    var input  = readInput("day1/Day01_test")
    day1.input = input
    day1.part1().println()
    day1.part2().println()

    input = readInput("day1/Day01")
    day1.input = input
    day1.part1().println()
    day1.part2().println()
}
