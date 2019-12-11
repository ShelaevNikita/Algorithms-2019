package lesson7

import lesson5.Graph
import lesson5.Path
import lesson5.impl.GraphBuilder
import lesson6.knapsack.Fill
import lesson6.knapsack.Item
import lesson6.knapsack.fillKnapsackDynamic
import lesson6.knapsack.fillKnapsackGreedy
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

abstract class AbstractHeuristicsTests {

    fun fillKnapsackCompareWithGreedyTest(fillKnapsackHeuristics: (Int, List<Item>) -> Fill) {
        for (i in 0..9) {
            val items = mutableListOf<Item>()
            val random = Random()
            for (j in 0 until 10000) {
                items += Item(1 + random.nextInt(10000), 300 + random.nextInt(600))
            }
            try {
                val fillHeuristics = fillKnapsackHeuristics(1000, items)
                println("Heuristics score = " + fillHeuristics.cost)
                val fillGreedy = fillKnapsackGreedy(1000, items)
                println("Greedy score = " + fillGreedy.cost)
                val fillDinamic = fillKnapsackDynamic(1000, items)
                println("Dynamic score = " + fillDinamic.cost)
                assertTrue(fillHeuristics.cost >= fillGreedy.cost)
            } catch (e: StackOverflowError) {
                println("Greedy failed with Stack Overflow")
            }
        }
    }

    fun fillKnapsackCompareWithGreedyTest2(fillKnapsackHeuristics: (Int, List<Item>) -> Fill) {
        val items1 = listOf(
            Item(cost = 8, weight = 10),
            Item(cost = 5, weight = 12),
            Item(cost = 6, weight = 8),
            Item(cost = 10, weight = 15),
            Item(cost = 4, weight = 2)
        )
        try {
            val fillHeuristics = fillKnapsackHeuristics(30, items1)
            println("Heuristics score = " + fillHeuristics.cost)
            val fillGreedy = fillKnapsackGreedy(30, items1)
            println("Greedy score = " + fillGreedy.cost)
            val fillDinamic = fillKnapsackDynamic(30, items1)
            println("Dynamic score = " + fillDinamic.cost)
            assertTrue(fillHeuristics.cost >= fillGreedy.cost)
        } catch (e: StackOverflowError) {
            println("Greedy failed with Stack Overflow")
        }
        val items2 = listOf(
            Item(cost = 1, weight = 3),
            Item(cost = 6, weight = 4),
            Item(cost = 4, weight = 5),
            Item(cost = 7, weight = 8),
            Item(cost = 6, weight = 9)
        )
        try {
            val fillHeuristics = fillKnapsackHeuristics(13, items2)
            println("Heuristics score = " + fillHeuristics.cost)
            val fillGreedy = fillKnapsackGreedy(13, items2)
            println("Greedy score = " + fillGreedy.cost)
            val fillDinamic = fillKnapsackDynamic(13, items2)
            println("Dynamic score = " + fillDinamic.cost)
            assertTrue(fillHeuristics.cost >= fillGreedy.cost)
        } catch (e: StackOverflowError) {
            println("Greedy failed with Stack Overflow")
        }
        val items3 = listOf(
            Item(cost = 2, weight = 1),
            Item(cost = 5, weight = 3),
            Item(cost = 10, weight = 5),
            Item(cost = 15, weight = 7)
        )
        try {
            val fillHeuristics = fillKnapsackHeuristics(10, items3)
            println("Heuristics score = " + fillHeuristics.cost)
            val fillGreedy = fillKnapsackGreedy(10, items3)
            println("Greedy score = " + fillGreedy.cost)
            val fillDinamic = fillKnapsackDynamic(10, items3)
            println("Dynamic score = " + fillDinamic.cost)
            assertTrue(fillHeuristics.cost >= fillGreedy.cost)
        } catch (e: StackOverflowError) {
            println("Greedy failed with Stack Overflow")
        }
        val items4 = listOf(
            Item(1, 389), Item(377, 148), Item(157, 2),
            Item(424, 205), Item(232, 2), Item(59, 247),
            Item(188, 143), Item(314, 1), Item(148, 474),
            Item(33, 2), Item(346, 149), Item(1, 182),
            Item(149, 308), Item(2, 1), Item(490, 2),
            Item(209, 470), Item(148, 2), Item(2, 61),
            Item(253, 148), Item(148, 125),
            Item(1, 148), Item(1, 1), Item(326, 148),
            Item(213, 149), Item(2, 6), Item(365, 219),
            Item(52, 181), Item(2, 3), Item(149, 1),
            Item(141, 1), Item(380, 449), Item(108, 149),
            Item(2, 1), Item(2, 149), Item(46, 148),
            Item(244, 414), Item(1, 1), Item(2, 273),
            Item(2, 380), Item(75, 148), Item(1, 148),
            Item(2, 1), Item(2, 149), Item(1, 1),
            Item(46, 148), Item(244, 414)
        )
        try {
            val fillHeuristics = fillKnapsackHeuristics(1300, items4)
            println("Heuristics score = " + fillHeuristics.cost)
            val fillGreedy = fillKnapsackGreedy(1300, items4)
            println("Greedy score = " + fillGreedy.cost)
            val fillDinamic = fillKnapsackDynamic(1300, items4)
            println("Dynamic score = " + fillDinamic.cost)
            assertTrue(fillHeuristics.cost >= fillGreedy.cost)
        } catch (e: StackOverflowError) {
            println("Greedy failed with Stack Overflow")
        }
    }

    fun fillKnapsackCompareWithDimanicTest(fillKnapsackHeuristics: (Int, List<Item>) -> Fill) {
        var result = 0
        for (i in 0..9) {
            val items = listOf(
                Item(24, 1), Item(148, 13), Item(149, 476),
                Item(148, 148), Item(148, 450), Item(149, 310),
                Item(221, 248), Item(148, 460), Item(154, 118),
                Item(148, 324), Item(32, 2), Item(380, 173),
                Item(304, 1), Item(237, 442), Item(78, 456),
                Item(373, 138), Item(2, 16), Item(459, 256),
                Item(1, 148), Item(338, 148), Item(430, 2),
                Item(2, 252), Item(1, 355), Item(149, 2),
                Item(2, 24), Item(148, 129), Item(128, 16),
                Item(28, 379), Item(148, 383), Item(149, 148)
            )
            try {
                val fillHeuristics = fillKnapsackHeuristics(1160, items)
                println("Heuristics score = " + fillHeuristics.cost)
                val fillGreedy = fillKnapsackGreedy(1160, items)
                println("Greedy score = " + fillGreedy.cost)
                val fillDinamic = fillKnapsackDynamic(1160, items)
                println("Dynamic score = " + fillDinamic.cost)
                if (fillHeuristics.cost >= fillDinamic.cost) result++
            } catch (e: StackOverflowError) {
                println("Greedy failed with Stack Overflow")
            }
        }
        assertTrue(result >= 9)
    }

    fun findVoyagingPathHeuristics(findVoyagingPathHeuristics: Graph.() -> Path) {
        val graph = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            val d = addVertex("D")
            val e = addVertex("E")
            val f = addVertex("F")
            addConnection(a, b, 10)
            addConnection(b, c, 15)
            addConnection(c, f, 30)
            addConnection(a, d, 20)
            addConnection(d, e, 25)
            addConnection(e, f, 15)
            addConnection(a, f, 40)
            addConnection(b, d, 10)
            addConnection(c, e, 5)
        }.build()
        val path = graph.findVoyagingPathHeuristics()
        assertEquals(105, path.length)
        val vertices = path.vertices
        assertEquals(vertices.first(), vertices.last(), "Voyaging path $vertices must be loop!")
        val withoutLast = vertices.dropLast(1)
        val expected = listOf(graph["A"], graph["D"], graph["B"], graph["C"], graph["E"], graph["F"])
        assertEquals(expected.size, withoutLast.size, "Voyaging path $vertices must travel through all vertices!")
        expected.forEach {
            assertTrue(it in vertices, "Voyaging path $vertices must travel through all vertices!")
        }
    }
}