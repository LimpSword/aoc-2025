package day6

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State
import println
import readInput

@State(Scope.Benchmark)
class Day06 {
    var inputLines = listOf<String>()

    @Setup
    fun setUp() {
        inputLines = readInput("day6/Day06")
    }

    @Benchmark
    fun part1(): Long {
        var result = 0L
        val lineNumbers = mutableMapOf<Int, MutableList<Long>>()
        inputLines.forEach { line ->
            line.trim().split("\\s+".toRegex()).forEachIndexed { index, s ->
                lineNumbers.putIfAbsent(index, mutableListOf())

                if (s == "+") {
                    result += lineNumbers[index]!!.sum()
                } else if (s == "*") {
                    result += lineNumbers[index]!!.reduce { acc, i -> acc * i }
                } else {
                    lineNumbers[index]!!.add(s.toLong())
                }
            }
        }
        return result
    }

    @Benchmark
    fun part2(): Long {
        // Worst code ever
        var result = 0L

        val numbers = mutableMapOf<Int, MutableList<Int>>()
        for (i in 0 until inputLines.maxBy { it.length }.length) {
            numbers.putIfAbsent(i, mutableListOf())
        }

        inputLines.forEach { line ->
            if (line.contains("*")) return@forEach

            line.toCharArray().forEachIndexed { index, c ->
                if (c == ' ') return@forEachIndexed
                numbers[index]!!.add(c.digitToInt())
            }
        }

        val operatorIndexes =
            inputLines.last().mapIndexedNotNull { index, c -> if (c == '+' || c == '*') index else null }
        for (i in 0 until operatorIndexes.size) {
            val nextIndex =
                if (i + 1 < operatorIndexes.size) operatorIndexes[i + 1] else inputLines.maxBy { it.length }.length
            if (inputLines.last().toCharArray()[operatorIndexes[i]] == '+') {
                var res = 0L
                for (j in operatorIndexes[i] until nextIndex) {
                    val resString = numbers[j]!!.map { it.toString() }.fold("") { acc, x -> acc + x }
                    if (resString.isNotBlank()) res += resString.toLong()
                }
                result += res
            } else if (inputLines.last().toCharArray()[operatorIndexes[i]] == '*') {
                var res = 1L
                for (j in operatorIndexes[i] until nextIndex) {
                    val resString = numbers[j]!!.map { it.toString() }.fold("") { acc, x -> acc + x }
                    if (resString.isNotBlank()) res *= resString.toLong()
                }
                result += res
            }
        }
        return result
    }
}

fun main() {
    val day6 = Day06()

    var input = readInput("day6/Day06_test")
    day6.inputLines = input
    day6.part1().println()
    day6.part2().println()

    input = readInput("day6/Day06")
    day6.inputLines = input
    day6.part1().println()
    day6.part2().println()
}
