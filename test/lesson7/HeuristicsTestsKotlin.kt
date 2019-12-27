package lesson7

import org.junit.Ignore
import org.junit.jupiter.api.Tag
import kotlin.test.Test

class HeuristicsTestsKotlin : AbstractHeuristicsTests() {

    @Ignore
    @Test
    @Tag("Impossible")
    fun testFillKnapsackCompareWithGreedyTest() {
        fillKnapsackCompareWithGreedyTest { load, items ->
            fillKnapsackHeuristicsGenetic(load, items, 50, 100)
        }
    }

    @Test
    @Tag("Impossible")
    fun testFillKnapsackCompareWithGreedyTest2() {
        fillKnapsackCompareWithGreedyTest2 { load, items ->
            fillKnapsackHeuristicsGenetic(load, items, 500, 500)
        }
    }

    @Test
    @Tag("Impossible")
    fun testFillKnapsackCompareWithDinamicTest() {
        fillKnapsackCompareWithDimanicTest { load, items ->
            fillKnapsackHeuristicsGenetic(load, items, 500, 1000)
        }
    }

    @Ignore
    @Test
    @Tag("Impossible")
    fun testFillKnapsackCompareWithGreedyTest3() {
        fillKnapsackCompareWithGreedyTest3 { load, items ->
            fillKnapsackHeuristicsAnnealing(load, items, 500000, 100000)
        }
    }

    @Test
    @Tag("Impossible")
    fun testFillKnapsackCompareWithGreedyTest4() {
        fillKnapsackCompareWithGreedyTest4 { load, items ->
            fillKnapsackHeuristicsAnnealing(load, items, 5000000, 200000)
        }
    }

    @Test
    @Tag("Impossible")
    fun testFillKnapsackCompareWithDinamicTest2() {
        fillKnapsackCompareWithDimanicTest2 { load, items ->
            fillKnapsackHeuristicsAnnealing(load, items, 5000000, 200000)
        }
    }

    @Test
    @Tag("Impossible")
    fun testFindVoyagingPathHeuristics() {
        findVoyagingPathHeuristics { findVoyagingPathHeuristics() }
    }
}