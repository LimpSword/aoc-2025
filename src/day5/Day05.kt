package day5

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State
import println
import readInput
import readInputString
import kotlin.math.pow

@State(Scope.Benchmark)
class Day05 {
    var inputString = ""

    @Setup
    fun setUp() {
        inputString = readInputString("day5/Day05")
    }

    @Benchmark
    fun part1(): Int {
        val lines = inputString.lines()
        var startChecking = false
        val ranges = mutableListOf<LongRange>()

        var freshIngredients = 0
        lines.forEach { line ->
            if (line.trim().isBlank()) {
                startChecking = true
                return@forEach
            }

            if (startChecking) {
                val ingredient = line.toLong()
                if (ranges.any { ingredient in it }) freshIngredients++
            } else {
                val (start, end) = line.split("-").map { it.toLong() }
                ranges.add(start..end)
            }
        }
        return freshIngredients
    }

    @Benchmark
    fun part2(): Long {
        val lines = inputString.lines()
        var startChecking = false
        val ranges = mutableListOf<LongRange>()

        lines.forEach { line ->
            if (line.trim().isBlank()) {
                // Combine ranges
                ranges.sortWith(compareBy({ it.first }, { it.last }))
                var i = 0
                while (i < ranges.size - 1) {
                    val nextRange = ranges[i + 1]
                    if (nextRange.first <= ranges[i].last || ranges[i].last + 1 == nextRange.first) {
                        ranges[i] = ranges[i].first..maxOf(ranges[i].last, nextRange.last)
                        ranges.removeAt(i + 1)
                    } else {
                        i += 1
                    }
                }

                startChecking = true
                return@forEach
            }

            if (startChecking) {
                return@forEach
            } else {
                val (start, end) = line.split("-").map { it.toLong() }
                ranges.add(start..end)
            }
        }

        var rangesTotal = 0L
        ranges.forEach { rangesTotal += (it.last - it.first + 1) }
        return rangesTotal
    }
}

fun main() {
    val day5 = Day05()

    var input = readInputString("day5/Day05_test")
    day5.inputString = input
    day5.part1().println()
    day5.part2().println()

    input = readInputString("day5/Day05")
    day5.inputString = input
    day5.part1().println()
    day5.part2().println()
}
