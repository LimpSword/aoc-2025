package day9

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State
import println
import readInput

@State(Scope.Benchmark)
class Day09 {
    var inputLines = listOf<String>()

    @Setup
    fun setUp() {
        inputLines = readInput("day9/Day09")
    }

    @Benchmark
    fun part1(): Any {
        var maxArea = 0L
        for (i in inputLines.indices) {
            for (j in i + 1 until inputLines.size) {
                val (x1, y1) = inputLines[i].split(",").map { it.toLong() }
                val (x2, y2) = inputLines[j].split(",").map { it.toLong() }
                val area = (kotlin.math.abs(x1 - x2) + 1) * (kotlin.math.abs(y1 - y2) + 1)
                if (area > maxArea) {
                    maxArea = area
                }
            }
        }
        return maxArea
    }

    @Benchmark
    fun part2(): Any {
        val points = createPolygon(inputLines)

        var maxArea = 0L
        for (i in points.indices) {
            for (j in i + 1 until points.size) {
                val (x1, y1) = Pair(points[i].x, points[i].y)
                val (x2, y2) = Pair(points[j].x, points[j].y)

                // Check that the entire rectangle is inside the polygon
                if (!isRectangleInside(x1, y1, x2, y2, points)) continue

                val area = (kotlin.math.abs(x1 - x2) + 1) * (kotlin.math.abs(y1 - y2) + 1)
                if (area > maxArea) {
                    maxArea = area
                }
            }
        }
        return maxArea
    }

    fun isRectangleInside(x1: Long, y1: Long, x2: Long, y2: Long, polygon: List<Point>): Boolean {
        val minX = minOf(x1, x2)
        val maxX = maxOf(x1, x2)
        val minY = minOf(y1, y2)
        val maxY = maxOf(y1, y2)

        // If any polygon edge cuts through the interior of the rectangle, it's invalid
        for (i in polygon.indices) {
            // Edge (p1, p2)
            val p1 = polygon[i]
            val p2 = polygon[(i + 1) % polygon.size]

            if (p1.x == p2.x) { // vertical edge
                val minEdgeY = minOf(p1.y, p2.y)
                val maxEdgeY = maxOf(p1.y, p2.y)

                if (p1.x in (minX + 1)..<maxX) {
                    if (minEdgeY < maxY && maxEdgeY > minY) {
                        // The edge is contained in the rectangle (edge x is inside the rectangle and y overlaps)
                        return false
                    }
                }
            } else if (p1.y == p2.y) { // horizontal edge
                val minEdgeX = minOf(p1.x, p2.x)
                val maxEdgeX = maxOf(p1.x, p2.x)

                if (p1.y in (minY + 1)..<maxY) {
                    if (minEdgeX < maxX && maxEdgeX > minX) {
                        // The edge is contained in the rectangle (edge y is inside the rectangle and x overlaps)
                        return false
                    }
                }
            }
        }

        return true
    }

    fun createPolygon(inputLines: List<String>): List<Point> = inputLines.map {
        val (x, y) = it.split(",").map { it.toLong() }
        Point(x, y)
    }
}

class Point(val x: Long, val y: Long)

fun main() {
    val day8 = Day09()

    var input = readInput("day9/Day09_test")
    day8.inputLines = input
    day8.part1().println()
    day8.part2().println()

    input = readInput("day9/Day09")
    day8.inputLines = input
    day8.part1().println()
    day8.part2().println()
}
