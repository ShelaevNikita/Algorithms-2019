@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson5

import lesson5.impl.GraphBuilder
import java.util.*

/**
 * Эйлеров цикл.
 * Средняя
 *
 * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
 * Если в графе нет Эйлеровых циклов, вернуть пустой список.
 * Соседние дуги в списке-результате должны быть инцидентны друг другу,
 * а первая дуга в списке инцидентна последней.
 * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
 * Веса дуг никак не учитываются.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
 *
 * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
 * связного графа ровно по одному разу
 *
 * Трудоёмкость: O(Edge);
 * Ресурсоёмкость: O(Edge) = O(Vertex)
 */
fun Graph.findEulerLoop(): List<Graph.Edge> {
    if ((edges.isEmpty()) || (vertices.any { getConnections(it).size % 2 != 0 })
        || (edges.size < vertices.size - 1)
    ) return emptyList()
    val visitedEdge = mutableListOf<Graph.Edge>()
    val resultCycle = mutableListOf<Graph.Vertex>()

    fun createCycle(vertex: Graph.Vertex): List<Graph.Vertex> {
        val cycle = mutableListOf<Graph.Vertex>()

        fun findCycle(startVertex: Graph.Vertex) {
            for ((neighborVertex, neighborEdge) in getConnections(startVertex)) {
                if (neighborEdge !in visitedEdge) {
                    if (neighborVertex != vertex) {
                        cycle += neighborVertex
                        visitedEdge += neighborEdge
                        findCycle(neighborVertex)
                        break
                    } else {
                        cycle += neighborVertex
                        visitedEdge += neighborEdge
                        return
                    }
                }
            }
        }

        findCycle(vertex)
        return if (cycle.size != 1) {
            for (ver in cycle) {
                resultCycle += ver
                createCycle(ver)
            }
            resultCycle
        } else emptyList()
    }

    val first = vertices.random()
    createCycle(first)
    if (resultCycle.size < vertices.size) return emptyList()
    resultCycle += resultCycle.first()
    val result = mutableListOf<Graph.Edge>()
    for (vertex in 0 until resultCycle.size - 1)
        result.add(getConnection(resultCycle[vertex], resultCycle[vertex + 1])!!)
    return result
}

/**
 * Минимальное остовное дерево.
 * Средняя
 *
 * Дан граф (получатель). Найти по нему минимальное остовное дерево.
 * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
 * вернуть любое из них. Веса дуг не учитывать.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Ответ:
 *
 *      G    H
 *      |    |
 * A -- B -- C -- D
 * |    |    |
 * E    F    I
 * |
 * J ------------ K
 *
 * Трудоёмкость: O(Vertex * Edge);
 * Ресурсоёмкость: O(Vertex + Edge)
 */
fun Graph.minimumSpanningTree(): Graph {
    if (vertices.isEmpty()) return GraphBuilder().build()
    val vertexSet = mutableSetOf<Graph.Vertex>()
    val edgeList = mutableListOf<Graph.Edge>()
    val vertexRandom = vertices.random()
    vertexSet += vertexRandom

    fun connect(vertex: Graph.Vertex) {
        for ((ver, edge) in getConnections(vertex)) {
            if (ver !in vertexSet) {
                vertexSet += ver
                edgeList += edge
                connect(ver)
            }
        }
    }

    connect(vertexRandom)
    return GraphBuilder().apply {
        addVertex(vertexRandom.name)
        if (edgeList[0].begin == vertexRandom)
            for (edges in edgeList) {
                addVertex(edges.end.name)
                addConnection(edges.begin, edges.end)
            }
        else
            for (edges in edgeList) {
                addVertex(edges.begin.name)
                addConnection(edges.begin, edges.end)
            }
    }.build()
}

/**
 * Максимальное независимое множество вершин в графе без циклов.
 * Сложная
 *
 * Дан граф без циклов (получатель), например
 *
 *      G -- H -- J
 *      |
 * A -- B -- D
 * |         |
 * C -- F    I
 * |
 * E
 *
 * Найти в нём самое большое независимое множество вершин и вернуть его.
 * Никакая пара вершин в независимом множестве не должна быть связана ребром.
 *
 * Если самых больших множеств несколько, приоритет имеет то из них,
 * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
 *
 * В данном случае ответ (A, E, F, D, G, J)
 *
 * Если на входе граф с циклами, бросить IllegalArgumentException
 *
 * Эта задача может быть зачтена за пятый и шестой урок одновременно
 *
 * Трудоёмкость: O(Vertex + Edge);
 * Ресурсоёмкость: O(Vertex)
 */
fun Graph.largestIndependentVertexSet(): Set<Graph.Vertex> {
    if (this.vertices.isEmpty()) return emptySet()
    val graphSet = createGraphSet(this)
    for (components in graphSet)
        require(checkCycle(components))
    val firstVertex = graphSet.map { it.vertices.first() }
    val result = mutableSetOf<Graph.Vertex>()
    for (vertex in firstVertex)
        result += findLargestIndependentVertexSet(this, vertex)
    return result
}

private fun findLargestIndependentVertexSet(graph: Graph, vertex: Graph.Vertex): Set<Graph.Vertex> {
    val vertexValue = mutableMapOf<Graph.Vertex, Int>()
    val visited = mutableSetOf<Graph.Vertex>()
    var flag = false

    fun independentVertexSet(vertex: Graph.Vertex): Int {
        if (vertexValue[vertex] != null) return vertexValue[vertex]!!
        visited += vertex
        var childrenSum = 0
        var grandChildrenSum = 0
        for (child in graph.getNeighbors(vertex))
            if (child !in visited)
                childrenSum += independentVertexSet(child)
        val grandChildren = mutableSetOf<Graph.Vertex>()
        for (child in graph.getNeighbors(vertex))
            if (child !in visited)
                for (grandChild in graph.getNeighbors(child))
                    grandChildren += grandChild
        for (grandChild in grandChildren)
            grandChildrenSum += independentVertexSet(grandChild)
        if (childrenSum <= grandChildrenSum + 1) {
            vertexValue[vertex] = grandChildrenSum
            flag = false
        } else {
            vertexValue[vertex] = childrenSum
            flag = true
        }
        return vertexValue[vertex]!!
    }

    independentVertexSet(vertex)
    val largestIndependentVertexSet = mutableSetOf<Graph.Vertex>()
    visited.clear()

    fun createLargestIndependentVertexSet(vertices: Set<Graph.Vertex>) {
        for (ver in vertices) {
            val grandChildren = mutableSetOf<Graph.Vertex>()
            for (child in graph.getNeighbors(ver)) {
                visited += child
                for (grandChild in graph.getNeighbors(child)) {
                    if (grandChild !in visited) {
                        visited += grandChild
                        grandChildren += grandChild
                    }
                }
            }
            largestIndependentVertexSet += grandChildren
            createLargestIndependentVertexSet(grandChildren)
        }
    }

    if (flag) {
        val children = graph.getNeighbors(vertex)
        visited += children
        largestIndependentVertexSet += children
        createLargestIndependentVertexSet(children)
    } else {
        visited += vertex
        largestIndependentVertexSet += vertex
        createLargestIndependentVertexSet(setOf(vertex))
    }
    return largestIndependentVertexSet
}

private fun createGraphSet(graph: Graph): Set<Graph> {
    val graphSet = mutableSetOf<Graph>()
    var vertexList = listOf<Graph.Vertex>()
    while (vertexList.size != graph.vertices.size) {
        val first = graph.vertices.first { it !in vertexList }
        val connectedComponent = graphOfVertex(first, graph)
        graphSet += connectedComponent
        vertexList = graphSet.flatMap { it.vertices }
    }
    return graphSet
}

private fun graphOfVertex(vertex: Graph.Vertex, graph: Graph): Graph {
    val vertexSet = mutableSetOf<Graph.Vertex>()
    val edgeSet = mutableSetOf<Graph.Edge>()

    fun findConnected(ver: Graph.Vertex) {
        vertexSet += ver
        for ((v, edge) in graph.getConnections(ver)) {
            if (edge !in edgeSet) {
                edgeSet += edge
                vertexSet += v
                findConnected(v)
            }
        }
    }

    findConnected(vertex)
    return GraphBuilder().apply {
        for (vertices in vertexSet)
            addVertex(vertices.name)
        for (edges in edgeSet)
            addConnection(edges.begin, edges.end)
    }.build()
}

private fun checkCycle(graph: Graph): Boolean {
    val visitedVertex = mutableSetOf<Graph.Vertex>()
    val visitedEdge = mutableSetOf<Graph.Edge>()

    fun dfs(vertex: Graph.Vertex): Boolean {
        if (vertex in visitedVertex) return false
        visitedVertex += vertex
        for ((ver, edge) in graph.getConnections(vertex)) {
            if (edge !in visitedEdge) {
                visitedEdge += edge
                if (!dfs(ver)) return false
            }
        }
        return true
    }
    return dfs(graph.vertices.random())
}

/**
 * Наидлиннейший простой путь.
 * Сложная
 *
 * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
 * Простым считается путь, вершины в котором не повторяются.
 * Если таких путей несколько, вернуть любой из них.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Ответ: A, E, J, K, D, C, H, G, B, F, I
 *
 * maxLength - длина наидлинейшего простого пути
 * Трудоёмкость: O(maxLength!);
 * Ресурсоёмкость: O(maxLength!)
 */
fun Graph.longestSimplePath(): Path {
    var maxLength = -1
    var longestPath = Path()
    val queue = ArrayDeque<Path>()
    for (vertex in vertices)
        queue.add(Path(vertex))
    while (queue.isNotEmpty()) {
        val path = queue.poll()
        if (path.length > maxLength) {
            maxLength = path.length
            longestPath = path
            if (maxLength == vertices.size) break
        }
        for (next in getNeighbors(path.vertices.last())) {
            if (next !in path)
                queue.add(Path(path, this, next))
        }
    }
    return longestPath
}