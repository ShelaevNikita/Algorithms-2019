package lesson5

import lesson5.impl.GraphBuilder
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

abstract class AbstractGraphTests {

    private fun Graph.Edge.isNeighbour(other: Graph.Edge): Boolean {
        return begin == other.begin || end == other.end || begin == other.end || end == other.begin
    }

    private fun List<Graph.Edge>.assert(shouldExist: Boolean, graph: Graph) {
        val edges = graph.edges
        if (shouldExist) {
            assertEquals(edges.size, size, "Euler loop should traverse all edges")
        } else {
            assertTrue(isEmpty(), "Euler loop should not exist")
        }
        for (edge in this) {
            assertTrue(edge in edges, "Edge $edge is not inside graph")
        }
        for (i in 0 until size - 1) {
            assertTrue(this[i].isNeighbour(this[i + 1]), "Edges ${this[i]} & ${this[i + 1]} are not incident")
        }
        if (size > 1) {
            assertTrue(this[0].isNeighbour(this[size - 1]), "Edges ${this[0]} & ${this[size - 1]} are not incident")
        }
    }

    fun findEulerLoop(findEulerLoop: Graph.() -> List<Graph.Edge>) {
        val emptyGraph = GraphBuilder().build()
        val emptyLoop = emptyGraph.findEulerLoop()
        assertTrue(emptyLoop.isEmpty(), "Euler loop should be empty for the empty graph")
        val simpleGraph = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            addConnection(a, b)
        }.build()
        val simpleLoop = simpleGraph.findEulerLoop()
        simpleLoop.assert(shouldExist = false, graph = simpleGraph)
        val unconnected = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            val d = addVertex("D")
            addConnection(a, b)
            addConnection(c, d)
        }.build()
        val unconnectedLoop = unconnected.findEulerLoop()
        unconnectedLoop.assert(shouldExist = false, graph = unconnected)
        val graph = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            addConnection(a, b)
            addConnection(b, c)
            addConnection(a, c)
        }.build()
        val loop = graph.findEulerLoop()
        loop.assert(shouldExist = true, graph = graph)
        val graph2 = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            val d = addVertex("D")
            val e = addVertex("E")
            val f = addVertex("F")
            val g = addVertex("G")
            val h = addVertex("H")
            val i = addVertex("I")
            val j = addVertex("J")
            val k = addVertex("K")
            addConnection(a, b)
            addConnection(b, c)
            addConnection(c, d)
            addConnection(a, e)
            addConnection(d, k)
            addConnection(e, j)
            addConnection(j, k)
            addConnection(b, f)
            addConnection(c, i)
            addConnection(f, i)
            addConnection(b, g)
            addConnection(g, h)
            addConnection(h, c)
        }.build()
        val loop2 = graph2.findEulerLoop()
        loop2.assert(shouldExist = true, graph = graph2)
        // Seven bridges of Koenigsberg
        //    A1 -- A2 ---
        //    |      |    |
        //    B1 -- B2 -- C
        //    |     |     |
        //    D1 -- D2 ---
        val graph3 = GraphBuilder().apply {
            val a1 = addVertex("A1")
            val a2 = addVertex("A2")
            val b1 = addVertex("B1")
            val b2 = addVertex("B2")
            val c = addVertex("C")
            val d1 = addVertex("D1")
            val d2 = addVertex("D2")
            addConnection(a1, a2)
            addConnection(b1, b2)
            addConnection(d1, d2)
            addConnection(a1, b1)
            addConnection(b1, d1)
            addConnection(a2, b2)
            addConnection(b2, d2)
            addConnection(a2, c)
            addConnection(b2, c)
            addConnection(d2, c)
        }.build()
        val loop3 = graph3.findEulerLoop()
        loop3.assert(shouldExist = false, graph = graph3)
        val graph4 = GraphBuilder().apply {
            val number = 75
            val vertices = mutableListOf<Graph.Vertex>()
            for (i in 0 until number)
                vertices += addVertex("$i")
            for (i in 0 until number - 1)
                for (j in i + 1 until number)
                    addConnection(vertices[i], vertices[j])
        }.build()
        val loop4 = graph4.findEulerLoop()
        loop4.assert(shouldExist = true, graph = graph4)
        val graph5 = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            val d = addVertex("D")
            val e = addVertex("E")
            val f = addVertex("F")
            addConnection(a, b)
            addConnection(b, c)
            addConnection(c, d)
            addConnection(d, e)
            addConnection(e, f)
            addConnection(f, d)
            addConnection(d, b)
            addConnection(b, e)
            addConnection(e, c)
            addConnection(c, a)
        }.build()
        val loop5 = graph5.findEulerLoop()
        loop5.assert(shouldExist = true, graph = graph5)
        val graph6 = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            val d = addVertex("D")
            val e = addVertex("E")
            val f = addVertex("F")
            addConnection(a, b)
            addConnection(b, c)
            addConnection(c, a)
            addConnection(a, d)
            addConnection(c, d)
            addConnection(d, e)
            addConnection(c, f)
            addConnection(a, e)
            addConnection(d, f)
        }.build()
        val loop6 = graph6.findEulerLoop()
        loop6.assert(shouldExist = true, graph = graph6)
        val graph7 = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            val d = addVertex("D")
            val e = addVertex("E")
            val f = addVertex("F")
            addConnection(a, b)
            addConnection(b, c)
            addConnection(c, a)
            addConnection(a, d)
            addConnection(c, d)
            addConnection(d, e)
            addConnection(c, f)
            addConnection(a, e)
        }.build()
        val loop7 = graph7.findEulerLoop()
        loop7.assert(shouldExist = false, graph = graph7)
    }

    fun minimumSpanningTree(minimumSpanningTree: Graph.() -> Graph) {
        val emptyGraph = GraphBuilder().build()
        assertTrue(emptyGraph.minimumSpanningTree().edges.isEmpty())
        val graph = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            addConnection(a, b)
            addConnection(b, c)
            addConnection(a, c)
        }.build()
        val tree = graph.minimumSpanningTree()
        assertEquals(2, tree.edges.size)
        assertEquals(2, tree.findBridges().size)
        val graph2 = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            val d = addVertex("D")
            val e = addVertex("E")
            val f = addVertex("F")
            val g = addVertex("G")
            val h = addVertex("H")
            val i = addVertex("I")
            val j = addVertex("J")
            val k = addVertex("K")
            addConnection(a, b)
            addConnection(b, c)
            addConnection(c, d)
            addConnection(a, e)
            addConnection(d, k)
            addConnection(e, j)
            addConnection(j, k)
            addConnection(b, f)
            addConnection(c, i)
            addConnection(f, i)
            addConnection(b, g)
            addConnection(g, h)
            addConnection(h, c)
        }.build()
        val tree2 = graph2.minimumSpanningTree()
        assertEquals(10, tree2.edges.size)
        assertEquals(10, tree2.findBridges().size)
        // Cross
        val graph3 = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            val d = addVertex("D")
            val e = addVertex("E")
            addConnection(a, e)
            addConnection(b, e)
            addConnection(c, e)
            addConnection(d, e)
        }.build()
        val tree3 = graph3.minimumSpanningTree()
        assertEquals(4, tree3.edges.size)
        assertEquals(4, tree3.findBridges().size)
        // Cross
        val graph4 = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            val d = addVertex("D")
            val e = addVertex("E")
            val f = addVertex("F")
            addConnection(a, b)
            addConnection(b, c)
            addConnection(c, d)
            addConnection(d, e)
            addConnection(e, a)
            addConnection(e, f)
            addConnection(f, a)
            addConnection(f, b)
            addConnection(d, f)
        }.build()
        val tree4 = graph4.minimumSpanningTree()
        assertEquals(5, tree4.edges.size)
        assertEquals(5, tree4.findBridges().size)
        val graph5 = GraphBuilder().apply {
            val vertices = mutableListOf<Graph.Vertex>()
            for (i in 0..50) {
                vertices += addVertex("$i")
            }
            for (i in 0..40) {
                for (j in i + 5..45) {
                    addConnection(vertices[i], vertices[j - 1])
                    addConnection(vertices[i], vertices[j])
                    addConnection(vertices[i], vertices[j + 1])
                    addConnection(vertices[i], vertices[j - 3])
                    addConnection(vertices[i], vertices[j - 4])
                    addConnection(vertices[i + 1], vertices[j - 3])
                    addConnection(vertices[i + 1], vertices[j - 2])
                    addConnection(vertices[i + 1], vertices[j])
                }
            }
        }.build()
        val tree5 = graph5.minimumSpanningTree()
        assertEquals(46, tree5.edges.size)
        assertEquals(46, tree5.findBridges().size)
    }

    fun largestIndependentVertexSet(largestIndependentVertexSet: Graph.() -> Set<Graph.Vertex>) {
        val emptyGraph = GraphBuilder().build()
        assertTrue(emptyGraph.largestIndependentVertexSet().isEmpty())
        val simpleGraph = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            addConnection(a, b)
        }.build()
        assertEquals(
            setOf(simpleGraph["A"]),
            simpleGraph.largestIndependentVertexSet()
        )
        val unconnected = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            val d = addVertex("D")
            val e = addVertex("E")
            addConnection(a, b)
            addConnection(c, d)
            addConnection(d, e)
        }.build()
        assertEquals(
            setOf(unconnected["A"], unconnected["C"], unconnected["E"]),
            unconnected.largestIndependentVertexSet()
        )
        val graph = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            val d = addVertex("D")
            val e = addVertex("E")
            val f = addVertex("F")
            val g = addVertex("G")
            val h = addVertex("H")
            val i = addVertex("I")
            val j = addVertex("J")
            addConnection(a, b)
            addConnection(a, c)
            addConnection(b, d)
            addConnection(c, e)
            addConnection(c, f)
            addConnection(b, g)
            addConnection(d, i)
            addConnection(g, h)
            addConnection(h, j)
        }.build()
        assertEquals(
            setOf(graph["A"], graph["D"], graph["E"], graph["F"], graph["G"], graph["J"]),
            graph.largestIndependentVertexSet()
        )
        val cross = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            val d = addVertex("D")
            val e = addVertex("E")
            addConnection(a, e)
            addConnection(b, e)
            addConnection(c, e)
            addConnection(d, e)
        }.build()
        assertEquals(
            setOf(cross["A"], cross["B"], cross["C"], cross["D"]),
            cross.largestIndependentVertexSet()
        )
        val cross2 = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            val d = addVertex("D")
            val e = addVertex("E")
            addConnection(a, b)
            addConnection(b, c)
            addConnection(c, d)
            addConnection(d, e)
            addConnection(a, e)
        }.build()
        assertThrows<IllegalArgumentException> { cross2.largestIndependentVertexSet() }
        val cross3 = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            val d = addVertex("D")
            val e = addVertex("E")
            val f = addVertex("F")
            val g = addVertex("G")
            val h = addVertex("H")
            val i = addVertex("I")
            val j = addVertex("J")
            addVertex("R")
            addConnection(a, b)
            addConnection(a, c)
            addConnection(b, d)
            addConnection(c, e)
            addConnection(c, f)
            addConnection(b, g)
            addConnection(d, i)
            addConnection(g, h)
            addConnection(h, j)
        }.build()
        assertEquals(
            setOf(cross3["A"], cross3["D"], cross3["E"], cross3["F"], cross3["G"], cross3["J"], cross3["R"]),
            cross3.largestIndependentVertexSet()
        )
    }

    fun longestSimplePath(longestSimplePath: Graph.() -> Path) {
        val emptyGraph = GraphBuilder().build()
        assertEquals(0, emptyGraph.longestSimplePath().length)

        val unconnected = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            val d = addVertex("D")
            val e = addVertex("E")
            addConnection(a, b)
            addConnection(c, d)
            addConnection(d, e)
        }.build()
        val longestUnconnectedPath = unconnected.longestSimplePath()
        assertEquals(2, longestUnconnectedPath.length)

        val graph = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            addConnection(a, b)
            addConnection(b, c)
            addConnection(a, c)
        }.build()
        val longestPath = graph.longestSimplePath()
        assertEquals(2, longestPath.length)

        val graph2 = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            val d = addVertex("D")
            val e = addVertex("E")
            val f = addVertex("F")
            val g = addVertex("G")
            val h = addVertex("H")
            val i = addVertex("I")
            val j = addVertex("J")
            val k = addVertex("K")
            addConnection(a, b)
            addConnection(b, c)
            addConnection(c, d)
            addConnection(a, e)
            addConnection(d, k)
            addConnection(e, j)
            addConnection(j, k)
            addConnection(b, f)
            addConnection(c, i)
            addConnection(f, i)
            addConnection(b, g)
            addConnection(g, h)
            addConnection(h, c)
        }.build()
        val longestPath2 = graph2.longestSimplePath()
        assertEquals(10, longestPath2.length)
        // Seven bridges of Koenigsberg
        //    A1 -- A2 ---
        //    |      |    |
        //    B1 -- B2 -- C
        //    |     |     |
        //    D1 -- D2 ---
        val graph3 = GraphBuilder().apply {
            val a1 = addVertex("A1")
            val a2 = addVertex("A2")
            val b1 = addVertex("B1")
            val b2 = addVertex("B2")
            val c = addVertex("C")
            val d1 = addVertex("D1")
            val d2 = addVertex("D2")
            addConnection(a1, a2)
            addConnection(b1, b2)
            addConnection(d1, d2)
            addConnection(a1, b1)
            addConnection(b1, d1)
            addConnection(a2, b2)
            addConnection(b2, d2)
            addConnection(a2, c)
            addConnection(b2, c)
            addConnection(d2, c)
        }.build()
        val longestPath3 = graph3.longestSimplePath()
        assertEquals(6, longestPath3.length)
        val graph1 = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            val d = addVertex("D")
            val e = addVertex("E")
            addConnection(a, b)
            addConnection(b, c)
            addConnection(c, d)
            addConnection(d, e)
            addConnection(e, a)
        }.build()
        val longestLoopPath = graph1.longestSimplePath()
        assertEquals(4, longestLoopPath.length)
        val disconnected = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            addVertex("D")
            addConnection(a, b)
            addConnection(b, c)
            addConnection(c, a)
        }.build()
        val longestPath4 = disconnected.longestSimplePath()
        assertEquals(2, longestPath4.length)
        val graph4 = GraphBuilder().apply {
            val vertices = mutableListOf<Graph.Vertex>()
            for (i in 0..8) {
                vertices += addVertex("$i")
            }
            for (i in 0..7) {
                for (j in i + 1..8) {
                    addConnection(vertices[i], vertices[j])
                }
            }
        }.build()
        val graph4Path = graph4.longestSimplePath()
        assertEquals(8, graph4Path.length)
    }

}