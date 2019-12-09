package lesson7.geneticKnapsack

import kotlin.random.Random

internal class Chromosome(private val gen: List<Int>) {
    constructor(size: Int, random: Random) : this(
        (0 until size).toMutableList().apply { random.nextInt(1) }
    )

    fun mutate(mutateSize: Int, random: Random): Chromosome =
        Chromosome(
            gen.toMutableList().apply {
                for (x in 0 until mutateSize) {
                    val index = random.nextInt(gen.size)
                    val byte = random.nextInt(1)
                    this[index] = byte
                }
            }
        )

    fun crossBreed(other: Chromosome, random: Random): Chromosome {
        assert(gen.size == other.gen.size)
        val result = mutableListOf<Int>()
        for (index in this.gen.indices)
            result += if (gen[index] == other.gen[index])
                gen[index]
            else random.nextInt(1)
        assert(gen.size == result.size)
        return Chromosome(result.toList())
    }
}