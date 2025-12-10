package day10

import com.gurobi.gurobi.GRB
import com.gurobi.gurobi.GRBEnv
import com.gurobi.gurobi.GRBLinExpr
import com.gurobi.gurobi.GRBModel
import com.gurobi.gurobi.GRBVar
import com.gurobi.gurobi.GurobiJni
import joptsimple.internal.Objects
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State
import println
import readInput
import kotlin.math.exp

@State(Scope.Benchmark)
class Day10 {
    var inputLines = listOf<String>()
    var cache = mutableMapOf<String, Int>()

    @Setup
    fun setUp() {
        inputLines = readInput("day10/Day10")
    }

    @Benchmark
    fun part1(): Any {
        var result = 0
        inputLines.forEach { line ->
            cache = mutableMapOf()

            val neededPattern = "\\[(.+)]".toRegex().find(line)!!.groups[1]!!.value
            val buttons =
                "\\((\\d+(?:,\\d+)*)\\)".toRegex().findAll(line).map { it.groups[1]!!.value.split(",") }.toList()

            result += findMinPattern(".".repeat(neededPattern.length), neededPattern, buttons.map {
                it.map { it.toInt() }
            }, listOf(".".repeat(neededPattern.length)))
        }
        return result
    }

    fun findMinPattern(
        currentPattern: String,
        neededPattern: String,
        buttons: List<List<Int>>,
        visited: List<String>
    ): Int {
        if (currentPattern in cache) {
            return cache[currentPattern]!!
        }

        if (currentPattern == neededPattern) {
            return 0
        }

        var result = Int.MAX_VALUE
        buttons.forEach { button ->
            val newPattern = switchPattern(currentPattern, button)
            if (newPattern in visited) {
                return@forEach
            }

            val subResult = findMinPattern(newPattern, neededPattern, buttons, visited + newPattern)
            if (subResult != Int.MAX_VALUE) {
                result = minOf(result, 1 + subResult)
            }
        }
        cache[currentPattern] = result
        return result
    }

    fun switchPattern(currentPattern: String, button: List<Int>): String {
        var newPattern = currentPattern
        for (i in button) {
            if (currentPattern[i] == '.') newPattern = newPattern.replaceRange(i, i + 1, "#")
            if (currentPattern[i] == '#') newPattern = newPattern.replaceRange(i, i + 1, ".")
        }
        return newPattern
    }

    @Benchmark
    fun part2(): Any {
        // No free license of Gurobi on Java API
        /*var result = 0
        inputLines.forEach { line ->
            cache = mutableMapOf()

            val targets = "\\{(.+)}".toRegex().find(line)!!.groups[1]!!.value
                .split(",").map { it.toInt() }
            val buttons =
                "\\((\\d+(?:,\\d+)*)\\)".toRegex().findAll(line).map { it.groups[1]!!.value.split(",") }.toList()
                    .map { it.map { it.toInt() } }

            result += solveWithGurobi(buttons, targets)
        }
        return result*/
        return 0
    }

    /*fun solveWithGurobi(buttons: List<List<Int>>, targets: List<Int>): Int {
        val env = GRBEnv(true)
        env.start()
        val model = GRBModel(env)

        // Create variables
        val variables = mutableListOf<GRBVar>()
        for (i in buttons.indices) {
            val variable = model.addVar(0.0, GRB.INFINITY, 1.0, GRB.INTEGER, "x_$i")
            variables.add(variable)
        }

        // Add constraints
        for (i in targets.indices) {
            val expr = GRBLinExpr()
            for (j in buttons.indices) {
                if (targets[i] in buttons[j]) {
                    expr.addTerm(1.0, variables[j])
                }
            }

            model.addConstr(expr, GRB.EQUAL, 1.0, "c_$i")
        }

        // Set objective
        val objective = GRBLinExpr()
        for (i in targets.indices) {
            objective.addTerm(1.0, variables[i])
        }
        model.setObjective(objective, GRB.MINIMIZE)

        model.optimize()

        return model.get(GRB.DoubleAttr.ObjVal).toInt()
    }*/
}

fun main() {
    val day10 = Day10()

    var input = readInput("day10/Day10_test")
    day10.inputLines = input
    day10.part1().println()
    day10.part2().println()

    input = readInput("day10/Day10")
    day10.inputLines = input
    day10.part1().println()
    day10.part2().println()
}
