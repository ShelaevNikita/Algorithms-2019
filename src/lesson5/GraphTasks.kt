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
 */
fun Graph.findEulerLoop(): List<Graph.Edge> {
    TODO()
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
 * Трудоёмкость: O(Vertex);
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
 */
fun Graph.largestIndependentVertexSet(): Set<Graph.Vertex> {
    TODO()
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
 * Трудоёмкость: O(queue * Neighbors), в худшем случае: O(Vertex * Edge * maxLenght);
 * Ресурсоёмкость: O(queue) ~= O(Edge * maxLenght)
 */
fun Graph.longestSimplePath(): Path {
    var maxLenght = -1
    var longestPath = Path()
    val queue = ArrayDeque<Path>()
    for (vertex in vertices)
        queue.add(Path(vertex))
    while (queue.isNotEmpty()) {
        val path = queue.poll()
        if (path.length > maxLenght) {
            maxLenght = path.length
            longestPath = path
            if (maxLenght == vertices.size) break
        }
        for (next in getNeighbors(path.vertices.last())) {
            if (next !in path)
                queue.add(Path(path, this, next))
        }
    }
    return longestPath
}