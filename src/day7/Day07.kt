package day7

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State
import println
import readInput

@State(Scope.Benchmark)
class Day07 {
    var inputLines = listOf<String>()

    @Setup
    fun setUp() {
        inputLines = readInput("day7/Day07")
    }

    @Benchmark
    fun part1(): Any {
        var result = 0
        val activeBeams = mutableSetOf(inputLines.first().indexOf('S')) // The first beam is where 'S' is
        inputLines.subList(1, inputLines.size).forEach { line ->
            val splitters = line.mapIndexedNotNull { index, c -> if (c == '^') index else null }
            // Split the beams that are coming onto splitters
            splitters.filter { it in activeBeams }.forEach { splitterIndex ->
                activeBeams.remove(splitterIndex)
                activeBeams.add(splitterIndex - 1)
                activeBeams.add(splitterIndex + 1)
                result += 1
            }
        }
        return result
    }

    @Benchmark
    fun part2(): Any {
        // Column to number of possible outputs
        val topSplitters = mutableMapOf<Int, Long>()
        // We start from the last line splitters (all can give 1 output)
        inputLines.takeLast(2).first().mapIndexedNotNull { index, c -> if (c == '^') index else null }.forEach {
            topSplitters[it] = 2
        }
        // We climb each line upwards and try to send beams from splitters (to the splitters below)
        inputLines.reversed().subList(2, inputLines.size - 1).forEach { line ->
            val splitters = line.mapIndexedNotNull { index, c -> if (c == '^') index else null }
            splitters.forEach { splitterIndex ->
                // Is there a splitter we can reach on the left or right of this splitter?
                val left = topSplitters[splitterIndex - 1] ?: 1
                val right = topSplitters[splitterIndex + 1] ?: 1
                topSplitters[splitterIndex] = left + right
            }
        }
        return topSplitters[inputLines.first().indexOf('S')]!!
    }
}

fun main() {
    val day7 = Day07()

    var input = readInput("day7/Day07_test")
    day7.inputLines = input
    day7.part1().println()
    day7.part2().println()

    input = readInput("day7/Day07")
    day7.inputLines = input
    day7.part1().println()
    day7.part2().println()
}
