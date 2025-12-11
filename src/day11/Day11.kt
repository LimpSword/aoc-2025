package day11

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State
import println
import readInput

@State(Scope.Benchmark)
class Day11 {
    var inputLines = listOf<String>()

    var cache = mutableMapOf<Pair<String, Set<String>>, Long>()

    @Setup
    fun setUp() {
        inputLines = readInput("day11/Day11")
    }

    @Benchmark
    fun part1(): Any {
        val neighbours = mutableMapOf<String, List<String>>()
        inputLines.forEach { line ->
            val node = line.split(":").first()
            val neighboursList = line.split(":").last().trim().split(" ").map { it.trim() }
            neighbours[node] = neighboursList
        }
        return dfs1("you", mutableSetOf(), neighbours)
    }

    fun dfs1(node: String, visited: MutableSet<String>, neighbours: Map<String, List<String>>): Int {
        if (node == "out") return 1

        visited.add(node)

        val count = neighbours[node]?.sumOf { neighbor ->
            if (neighbor !in visited) {
                dfs1(neighbor, visited, neighbours)
            } else 0
        } ?: 0

        visited.remove(node)

        return count
    }

    @Benchmark
    fun part2(): Any {
        val neighbours = mutableMapOf<String, List<String>>()
        inputLines.forEach { line ->
            val node = line.split(":").first()
            val neighboursList = line.split(":").last().trim().split(" ").map { it.trim() }
            neighbours[node] = neighboursList
        }

        cache = mutableMapOf() // reset cache
        return dfs2("svr", mutableSetOf(), emptySet(), neighbours)
    }

    fun dfs2(
        node: String,
        visited: MutableSet<String>,
        hitCheckpoints: Set<String>,
        neighbours: Map<String, List<String>>
    ): Long {
        val key = node to hitCheckpoints
        cache[key]?.let { return it }

        val newHitCheckpoints = if (node in setOf("fft", "dac")) hitCheckpoints + node else hitCheckpoints

        if (node == "out") {
            return if (newHitCheckpoints == setOf("fft", "dac")) 1 else 0
        }

        visited.add(node)

        val count = neighbours[node]?.sumOf { neighbor ->
            if (neighbor !in visited) {
                dfs2(neighbor, visited, newHitCheckpoints, neighbours)
            } else 0
        } ?: 0L

        visited.remove(node)

        return count.also { cache[key] = it }
    }
}

fun main() {
    val day8 = Day11()

    var input = readInput("day11/Day11_test")
    day8.inputLines = input
    day8.part1().println()
    day8.part2().println()

    input = readInput("day11/Day11")
    day8.inputLines = input
    day8.part1().println()
    day8.part2().println()
}
