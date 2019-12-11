package lesson7.geneticKnapsack

import lesson6.knapsack.Fill
import lesson6.knapsack.Item
import kotlin.random.Random

class HeuristicsKnapsack(
    private val load: Int,
    private val items: List<Item>,
    private val chromosomeNumber: Int,
    private val generationNumber: Int
) {

    private val size = items.size

    private val random = Random

    private fun generateChromosomes(number: Int): List<Chromosome> {
        val result = mutableListOf<Chromosome>()
        for (i in 0 until number) {
            result += Chromosome(size, random)
        }
        return result
    }

    private fun Chromosome.otbor(): Int {
        var list = 0
        for (x in this.gen.indices)
            if (this.gen[x] == 1) list += items[x].weight
        if (list > load) list = 0 - list
        return list
    }

    private fun Chromosome.evaluation(): Int {
        var result = 0
        for (x in this.gen.indices)
            if (this.gen[x] == 1) result += items[x].cost
        return result
    }

    private fun List<Chromosome>.generateCrossBreeds(): List<Chromosome> {
        val result = mutableListOf<Chromosome>()
        for (i in 0 until size) {
            val first = this[random.nextInt(size)]
            val second = this[random.nextInt(size)]
            result += if (first === second) first else first.crossBreed(second, random)
        }
        return result
    }

    fun findTheBest(): Fill {
        var chromosomes = generateChromosomes(chromosomeNumber)
        for (generation in 0 until generationNumber) {
            val crossBreeds = chromosomes.generateCrossBreeds()
            val crossBreedsAfterMutation =
                crossBreeds.map { if (random.nextDouble() < 0.1) it.mutate(size, random) else it }
            val chromosomeAfterOtbor =
                (chromosomes + crossBreedsAfterMutation).filter { it.otbor() > 0 }
            if (chromosomeAfterOtbor.isEmpty()) findTheBest()
            val chromosomeAfterEvaluation =
                chromosomeAfterOtbor.sortedByDescending { it.evaluation() }
            chromosomes = if (chromosomeAfterEvaluation.size >= chromosomeNumber)
                chromosomeAfterEvaluation.subList(0, chromosomeNumber)
            else chromosomeAfterEvaluation + generateChromosomes(
                chromosomeNumber - chromosomeAfterEvaluation.size
            )
        }
        val result = chromosomes.first()
        val listResult = mutableSetOf<Item>()
        for (x in result.gen.indices)
            if (result.gen[x] == 1) listResult += items[x]
        return Fill(result.evaluation(), listResult)
    }
}