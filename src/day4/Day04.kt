package day4

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State
import println
import readInput
import readInputString
import kotlin.math.pow

@State(Scope.Benchmark)
class Day04 {
    var inputString = ""

    @Setup
    fun setUp() {
        inputString = readInputString("day4/Day04")
    }

    @Benchmark
    fun part1(): Int {
        val directions = setOf(1 to 0, 0 to 1, -1 to 0, 0 to -1, 1 to 1, 1 to -1, -1 to 1, -1 to -1)
        val lineCount = inputString.lines().size
        val columnCount = inputString.lines().first().length

        val lines = inputString.lines()

        var accessible = 0
        for (i in 0 until lineCount) {
            for (j in 0 until columnCount) {
                if (lines[i][j] != '@') continue

                var adjacent = 0
                for ((dx, dy) in directions) {
                    if (i + dy !in 0..<lineCount || j + dx < 0 || j + dx >= columnCount) continue
                    if (lines[i + dy][j + dx] == '@') adjacent++

                    if (adjacent >= 4) break
                }
                if (adjacent < 4) {
                    accessible++
                }
            }
        }
        return accessible
    }

    @Benchmark
    fun part2(): Int {
        val directions = setOf(1 to 0, 0 to 1, -1 to 0, 0 to -1, 1 to 1, 1 to -1, -1 to 1, -1 to -1)
        val lineCount = inputString.lines().size
        val columnCount = inputString.lines().first().length

        val charArray = inputString.lines().map { it.toCharArray() }.toTypedArray()

        var prevAccessible = -1
        var accessible = 0
        while (accessible != prevAccessible) {
            prevAccessible = accessible
            for (i in 0 until lineCount) {
                for (j in 0 until columnCount) {
                    if (charArray[i][j] != '@') continue

                    var adjacent = 0
                    for ((dx, dy) in directions) {
                        if (i + dy !in 0..<lineCount || j + dx < 0 || j + dx >= columnCount) continue
                        if (charArray[i + dy][j + dx] == '@') adjacent++

                        if (adjacent >= 4) break
                    }
                    if (adjacent < 4) {
                        charArray[i][j] = '.'
                        accessible++
                    }
                }
            }
        }
        return accessible
    }
}

fun main() {
    val day4 = Day04()

    var input = readInputString("day4/Day04_test")
    day4.inputString = input
    day4.part1().println()
    day4.part2().println()

    input = readInputString("day4/Day04")
    day4.inputString = input
    day4.part1().println()
    day4.part2().println()
}
