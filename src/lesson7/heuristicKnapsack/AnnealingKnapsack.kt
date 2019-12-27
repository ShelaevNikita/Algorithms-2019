package lesson7.heuristicKnapsack

import lesson6.knapsack.Fill
import lesson6.knapsack.Item
import java.util.*
import kotlin.math.exp

class AnnealingKnapsack(
    private val load: Int,
    private val items: List<Item>,
    private val startTemperature: Int,
    private val iterationNumber: Int
) {

    private val random = Random()

    private val size = items.size

    private fun List<Int>.generateNewState(): List<Int> {
        val newState = mutableListOf<Int>()
        val i = random.nextInt(size)
        val j = random.nextInt(size)
        val first = minOf(i, j)
        val second = maxOf(i, j)
        for (k in 0 until first) {
            newState += this[k]
        }
        for (k in second downTo first) {
            newState += this[k]
        }
        for (k in second + 1 until size) {
            newState += this[k]
        }
        assert(newState.size == size)
        return newState
    }

    private fun transitionProbability(evaluationIncrease: Int, temperature: Double): Double {
        return exp(-evaluationIncrease / temperature)
    }

    private fun List<Int>.evaluation(): Int {
        var list = 0
        var result = 0
        for (x in this.indices)
            if (this[x] == 1) {
                list += items[x].weight
                result += items[x].cost
            }
        if (list > load) {
            result -= list - load
        }
        return result
    }

    fun findTheBest(): Fill {
        val state = mutableListOf<Int>()
        for (x in 0 until size) state.add(random.nextInt(2))
        for (i in 1..iterationNumber) {
            val newState = state.generateNewState()
            val evaluation = state.evaluation()
            val newEvaluation = newState.evaluation()
            if (newEvaluation > evaluation) {
                state.clear()
                for (x in newState)
                    state.add(x)
                if (random.nextDouble() >
                    transitionProbability(
                        evaluation - newEvaluation,
                        startTemperature.toDouble() / i
                    )
                ) {
                    state.clear()
                    for (x in newState)
                        state.add(x)
                }
            }
        }
        val listResult = mutableSetOf<Item>()
        for (x in state.indices)
            if (state[x] == 1) listResult += items[x]
        return Fill(state.evaluation(), listResult)
    }
}