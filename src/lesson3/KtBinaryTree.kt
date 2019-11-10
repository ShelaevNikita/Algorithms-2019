package lesson3

import java.util.*
import kotlin.NoSuchElementException
import kotlin.math.max

// Attention: comparable supported but comparator is not
class KtBinaryTree<T : Comparable<T>> : AbstractMutableSet<T>(), CheckableSortedSet<T> {

    private var root: Node<T>? = null

    override var size = 0
        private set

    private class Node<T>(val value: T) {

        var left: Node<T>? = null

        var right: Node<T>? = null

        var parentNode: Node<T>? = null
    }

    override fun add(element: T): Boolean {
        val closest = find(element)
        val comparison = if (closest == null) -1 else element.compareTo(closest.value)
        if (comparison == 0) {
            return false
        }
        val newNode = Node(element)
        when {
            closest == null -> root = newNode
            comparison < 0 -> {
                assert(closest.left == null)
                closest.left = newNode
            }
            else -> {
                assert(closest.right == null)
                closest.right = newNode
            }
        }
        size++
        return true
    }

    override fun checkInvariant(): Boolean =
        root?.let { checkInvariant(it) } ?: true

    override fun height(): Int = height(root)

    private fun checkInvariant(node: Node<T>): Boolean {
        val left = node.left
        if (left != null && (left.value >= node.value || !checkInvariant(left))) return false
        val right = node.right
        return right == null || right.value > node.value && checkInvariant(right)
    }

    private fun height(node: Node<T>?): Int {
        if (node == null) return 0
        return 1 + max(height(node.left), height(node.right))
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    override fun remove(element: T): Boolean {
        val closest = find(element) ?: return false
        closest.parentNode = findParent(closest)
        if (closest.left != null && closest.right != null) {
            var minNode = closest.right!!
            while (minNode.left != null) minNode = minNode.left!!
            minNode.parentNode = findParent(minNode)
            if (minNode.parentNode?.right == minNode) minNode.parentNode?.right = null
            else minNode.parentNode?.left = null
            minNode.left = closest.left
            minNode.right = closest.right
            when (closest) {
                root -> root = minNode
                closest.parentNode?.left -> closest.parentNode?.left = minNode
                else -> closest.parentNode?.right = minNode
            }
        } else if (closest.parentNode?.right == closest) closest.parentNode?.right = closest.right
        else closest.parentNode?.left = closest.left
        size--
        return true
    }

    override operator fun contains(element: T): Boolean {
        val closest = find(element)
        return closest != null && element.compareTo(closest.value) == 0
    }

    private fun find(value: T): Node<T>? =
        root?.let { find(it, value) }

    private fun find(start: Node<T>, value: T): Node<T> {
        val comparison = value.compareTo(start.value)
        return when {
            comparison == 0 -> start
            comparison < 0 -> start.left?.let { find(it, value) } ?: start
            else -> start.right?.let { find(it, value) } ?: start
        }
    }

    private fun findParent(node: Node<T>?): Node<T>? {
        val queue = ArrayDeque<Node<T>>()
        var top = root ?: return null
        while ((top.right != node) && (top.left != node)) {
            if (top.left != null) queue.add(top.left!!)
            if (top.right != null) queue.add(top.right!!)
            if (queue.isNotEmpty()) top = queue.poll() else return null
        }
        return top
    }

    inner class BinaryTreeIterator internal constructor() : MutableIterator<T> {

        private val stack = ArrayDeque<Node<T>>()

        init {
            var element = root
            while (element != null) {
                stack.push(element)
                element = element.left
            }
        }

        /**
         * Проверка наличия следующего элемента
         * Средняя
         */
        override fun hasNext() = stack.isNotEmpty()


        /**
         * Поиск следующего элемента
         * Средняя
         */
        override fun next(): T {
            if (!hasNext()) throw NotImplementedError()
            var element = stack.pop()
            val result = element
            if (element.right != null) {
                element = element.right!!
                while (element != null) {
                    stack.push(element)
                    element = element.left
                }
            }
            return result!!.value
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        override fun remove() {
            val element = next()
            remove(element)
        }
    }

    override fun iterator(): MutableIterator<T> = BinaryTreeIterator()

    override fun comparator(): Comparator<in T>? = null

    /**
     * Найти множество всех элементов в диапазоне [fromElement, toElement)
     * Очень сложная
     */
    override fun subSet(fromElement: T, toElement: T): SortedSet<T> {
        val set = mutableSetOf<T>()
        val queue = ArrayDeque<Node<T>>()
        var top = root ?: return set.toSortedSet()
        queue.add(top)
        while (queue.isNotEmpty()) {
            if ((top.value < toElement) && (top.value >= fromElement)) set += top.value
            if (top.left != null) queue.add(top.left!!)
            if (top.right != null) queue.add(top.right!!)
            if (queue.isNotEmpty()) top = queue.poll()
        }
        return set.toSortedSet()
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    override fun headSet(toElement: T): SortedSet<T> {
        val set = mutableSetOf<T>()
        val queue = ArrayDeque<Node<T>>()
        var top = root ?: return set.toSortedSet()
        queue.add(top)
        while (queue.isNotEmpty()) {
            if (top.value < toElement) set += top.value
            if (top.left != null) queue.add(top.left!!)
            if (top.right != null) queue.add(top.right!!)
            if (queue.isNotEmpty()) top = queue.poll()
        }
        return set.toSortedSet()
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    override fun tailSet(fromElement: T): SortedSet<T> {
        val set = mutableSetOf<T>()
        val queue = ArrayDeque<Node<T>>()
        var top = root ?: return set.toSortedSet()
        queue.add(top)
        while (queue.isNotEmpty()) {
            if (top.value >= fromElement) set += top.value
            if (top.left != null) queue.add(top.left!!)
            if (top.right != null) queue.add(top.right!!)
            if (queue.isNotEmpty()) top = queue.poll()
        }
        return set.toSortedSet()
    }

    override fun first(): T {
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.left != null) {
            current = current.left!!
        }
        return current.value
    }

    override fun last(): T {
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.right != null) {
            current = current.right!!
        }
        return current.value
    }
}
