package lesson7

import org.junit.Ignore
import org.junit.jupiter.api.Tag
import kotlin.test.Test

class HeuristicsTestsKotlin : AbstractHeuristicsTests() {

    @Ignore
    @Test
    // This test is too long for "kotoed" - 9 минут
    @Tag("Impossible")
    fun testFillKnapsackCompareWithGreedyTest() {
        fillKnapsackCompareWithGreedyTest { load, items ->
            fillKnapsackHeuristics(load, items, 20, 1000)
        }
    }

    @Test
    @Tag("Impossible")
    fun testFillKnapsackCompareWithGreedyTest2() {
        fillKnapsackCompareWithGreedyTest2 { load, items ->
            fillKnapsackHeuristics(load, items, 20, 20)
        }
    }

    @Test
    @Tag("Impossible")
    fun testFillKnapsackCompareWithGreedyTest3() {
        fillKnapsackCompareWithGreedyTest3 { load, items ->
            fillKnapsackHeuristics(load, items, 100, 1000)
        }
    }

    @Test
    @Tag("Impossible")
    fun testFindVoyagingPathHeuristics() {
        findVoyagingPathHeuristics { findVoyagingPathHeuristics() }
    }
}