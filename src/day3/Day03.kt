package day3

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State
import println
import readInput
import kotlin.math.pow

@State(Scope.Benchmark)
class Day03 {
    var input: List<String> = listOf()

    @Setup
    fun setUp() {
        input = readInput("day3/Day03")
    }

    @Benchmark
    fun part1(): Long {
        var joltageSum = 0L
        input.forEach { line ->
            var currentMaxIndex = 0
            var joltage = 0L
            for (i in 2 downTo 1) {
                // Find the max value in the (length - i) remaining values
                // At each digit, we need the max possible we can find
                line.substring(currentMaxIndex, line.length - (i - 1)).map { c -> c.digitToInt() }.withIndex()
                    .maxBy { it.value }.let { (index, value) ->
                        joltage += 10.0.pow(i - 1).toLong() * value
                        currentMaxIndex += index + 1
                    }
            }
            joltageSum += joltage
        }
        return joltageSum
    }

    @Benchmark
    fun part2(): Long {
        var joltageSum = 0L
        input.forEach { line ->
            var currentMaxIndex = 0
            var joltage = 0L
            for (i in 12 downTo 1) {
                line.substring(currentMaxIndex, line.length - (i - 1)).map { c -> c.digitToInt() }.withIndex()
                    .maxBy { it.value }.let { (index, value) ->
                        joltage += 10.0.pow(i - 1).toLong() * value
                        currentMaxIndex += index + 1
                    }
            }
            joltageSum += joltage
        }
        return joltageSum
    }
}

fun main() {
    val day1 = Day03()

    var input = readInput("day3/Day03_test")
    day1.input = input
    day1.part1().println()
    day1.part2().println()

    input = readInput("day3/Day03")
    day1.input = input
    day1.part1().println()
    day1.part2().println()
}
