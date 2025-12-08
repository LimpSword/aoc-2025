package day8

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State
import println
import readInput
import java.util.PriorityQueue
import kotlin.math.abs

@State(Scope.Benchmark)
class Day08 {
    var inputLines = listOf<String>()

    @Setup
    fun setUp() {
        inputLines = readInput("day8/Day08")
    }

    @Benchmark
    fun part1(): Any {
        val distanceQueue = PriorityQueue<TwoNodesDistance>()
        for (i in inputLines.indices) {
            val line = inputLines[i]
            for (j in i + 1 until inputLines.size) {
                val otherLine = inputLines[j]
                val distance = distance(line, otherLine)
                distanceQueue.add(TwoNodesDistance(line, otherLine, distance))
            }
        }

        val repeat = if (inputLines.size == 1000) 1000 else 10 // hardcode for part 1 because it depends on the input
        val circuits = PriorityQueue<Circuit>()
        repeat(repeat) {
            val (node1, node2, _) = distanceQueue.poll()
            // Find the circuits containing any of the two nodes
            val possibleCircuits = circuits.filter { node1 in it.nodes || node2 in it.nodes }
            if (possibleCircuits.isEmpty()) {
                circuits.add(Circuit(mutableSetOf(node1, node2)))
            } else if (possibleCircuits.size == 1) {
                possibleCircuits.first().nodes.add(node1)
                possibleCircuits.first().nodes.add(node2)
            } else if (possibleCircuits.size == 2) {
                val newCircuit = possibleCircuits.first().nodes.union(possibleCircuits.last().nodes).toMutableSet()
                circuits.remove(possibleCircuits.first())
                circuits.remove(possibleCircuits.last())
                circuits.add(Circuit(newCircuit))
            } else {
                error("Too many circuits found")
            }
        }

        return circuits.poll().nodes.size * circuits.poll().nodes.size * circuits.poll().nodes.size
    }

    @Benchmark
    fun part2(): Any {
        val distanceQueue = PriorityQueue<TwoNodesDistance>()
        for (i in inputLines.indices) {
            val line = inputLines[i]
            for (j in i + 1 until inputLines.size) {
                val otherLine = inputLines[j]
                val distance = distance(line, otherLine)
                distanceQueue.add(TwoNodesDistance(line, otherLine, distance))
            }
        }

        val circuits = PriorityQueue<Circuit>()
        while (distanceQueue.isNotEmpty()) {
            val (node1, node2, _) = distanceQueue.poll()
            // Find the circuits containing any of the two nodes
            val possibleCircuits = circuits.filter { node1 in it.nodes || node2 in it.nodes }
            if (possibleCircuits.isEmpty()) {
                circuits.add(Circuit(mutableSetOf(node1, node2)))
            } else if (possibleCircuits.size == 1) {
                possibleCircuits.first().nodes.add(node1)
                possibleCircuits.first().nodes.add(node2)
            } else if (possibleCircuits.size == 2) {
                val newCircuit = possibleCircuits.first().nodes.union(possibleCircuits.last().nodes).toMutableSet()
                circuits.remove(possibleCircuits.first())
                circuits.remove(possibleCircuits.last())
                circuits.add(Circuit(newCircuit))
            } else {
                error("Too many circuits found")
            }

            if (circuits.size == 1 && circuits.first().nodes.size == inputLines.size) {
                val (x, y, z) = node1.split(",").map { it.toLong() }
                val (x2, y2, z2) = node2.split(",").map { it.toLong() }
                return x * x2
            }
        }

        return circuits.poll().nodes.size * circuits.poll().nodes.size * circuits.poll().nodes.size
    }

    fun distance(node1: String, node2: String): Long {
        val (x, y, z) = node1.split(",").map { it.toLong() }
        val (x2, y2, z2) = node2.split(",").map { it.toLong() }
        return (x - x2) * (x - x2) + (y - y2) * (y - y2) + (z - z2) * (z - z2)
    }
}

class Circuit(val nodes: MutableSet<String>) : Comparable<Circuit> {
    override fun compareTo(other: Circuit): Int = -(nodes.size - other.nodes.size)

    override fun toString(): String {
        return "Circuit(nodes=$nodes,size=${nodes.size})"
    }
}

class TwoNodesDistance(val node1: String, val node2: String, val distance: Long) : Comparable<TwoNodesDistance> {
    override fun compareTo(other: TwoNodesDistance): Int = distance.compareTo(other.distance)

    operator fun component1() = node1
    operator fun component2() = node2
    operator fun component3() = distance
}

fun main() {
    val day8 = Day08()

    var input = readInput("day8/Day08_test")
    day8.inputLines = input
    day8.part1().println()
    day8.part2().println()

    input = readInput("day8/Day08")
    day8.inputLines = input
    day8.part1().println()
    day8.part2().println()
}
