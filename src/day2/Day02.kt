package day2

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State
import println
import readInputWithComma

@State(Scope.Benchmark)
class Day02 {
    var input: List<String> = listOf()

    @Setup
    fun setUp() {
        input = readInputWithComma("day2/Day02")
    }

    @Benchmark
    fun part1(): Long {
        var result = 0L
        input.forEach {
            val id1 = it.substringBefore("-").toLong()
            val id2 = it.substringAfter("-").toLong()

            for (i in id1..id2) {
                val firstHalf = i.toString().take(i.toString().length / 2)
                val secondHalf = i.toString().takeLast(i.toString().length / 2)
                if (i.toString().length % 2 == 0 && firstHalf == secondHalf) {
                    result += i
                }
            }
        }
        return result
    }

    @Benchmark
    fun part2(): Long {
        var result = 0L
        input.forEach {
            val id1 = it.substringBefore("-").toLong()
            val id2 = it.substringAfter("-").toLong()

            for (i in id1..id2) {
                if (i.toString().isMadeOfRepeatedSubstring()) result += i
            }
        }
        return result
    }
}

fun main() {
    val day1 = Day02()

    var input = readInputWithComma("day2/Day02_test")
    day1.input = input
    day1.part1().println() // 1227775554
    day1.part2().println() // 4174379265

    input = readInputWithComma("day2/Day02")
    day1.input = input
    day1.part1().println()
    day1.part2().println()
}

fun String.isMadeOfRepeatedSubstring(): Boolean =
    (1..length / 2).any { len ->
        length % len == 0 && this == substring(0, len).repeat(length / len)
    }
