package lesson3

import java.util.*
import kotlin.NoSuchElementException
import kotlin.math.max

// Attention: comparable supported but comparator is not
open class KtBinaryTree<T : Comparable<T>> : AbstractMutableSet<T>(), CheckableSortedSet<T> {

    private var root: Node<T>? = null

    override var size = 0

    private class Node<T>(val value: T) {

        var left: Node<T>? = null

        var right: Node<T>? = null

        var parentNode: Node<T>? = null

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if ((other == null) || (other.javaClass != this.javaClass)) return false
            else other as Node<*>
            return this.value == other.value && this.left == other.left &&
                    this.right == other.right && this.parentNode == other.parentNode
        }

        override fun hashCode(): Int {
            return Objects.hash(value, left, right, parentNode)
        }
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
                newNode.parentNode = closest
                closest.left = newNode
            }
            else -> {
                assert(closest.right == null)
                newNode.parentNode = closest
                closest.right = newNode
            }
        }
        size++
        return true
    }

    override fun addAll(elements: Collection<T>): Boolean {
        for (element in elements)
            add(element)
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
     *
     * Трудоёмкость: O(height)
     * Ресурсоёмкость: O(height)
     */
    override fun remove(element: T): Boolean {
        val closest = find(element) ?: return false
        when {
            closest.left == null -> change(closest, closest.right)
            closest.right == null -> change(closest, closest.left)
            else -> {
                var minNode = closest.right!!
                while (minNode.left != null) minNode = minNode.left!!
                if (minNode.parentNode != closest) {
                    change(minNode, minNode.right)
                    minNode.right = closest.right
                    minNode.right?.parentNode = minNode
                }
                change(closest, minNode)
                minNode.left = closest.left
                minNode.left?.parentNode = minNode
            }
        }
        size--
        return true
    }

    private fun change(from: Node<T>?, to: Node<T>?) {
        when {
            from?.parentNode == null -> root = to
            from.parentNode!!.left == from -> from.parentNode!!.left = to
            else -> from.parentNode!!.right = to
        }
        if (from != null) to?.parentNode = from.parentNode
    }

    private fun removeNode(node: Node<T>?) = remove(node?.value)

    override fun removeAll(elements: Collection<T>): Boolean {
        for (element in elements)
            remove(element)
        return true
    }

    override fun clear() {
        for (element in this) remove(element)
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        var change = true
        for (element in elements)
            change = contains(element)
        return change
    }

    override fun toString(): String {
        val string = StringBuilder()
        for (element in this) {
            string.append(element)
            string.append(", ")
        }
        string.delete(string.length - 2, string.length)
        return string.toString()
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

    open inner class BinaryTreeIterator<U> internal constructor() : MutableIterator<T> {

        private var node: Node<T>? = null

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
         *
         * Трудоёмкость: O(height)
         * Ресурсоёмкость: O(height)
         */
        override fun hasNext() = stack.isNotEmpty()

        /**
         * Поиск следующего элемента
         * Средняя
         *
         * Трудоёмкость: O(height)
         * Ресурсоёмкость: O(height)
         */
        override fun next(): T {
            if (!hasNext()) throw NoSuchElementException()
            var element = stack.pop()
            val result = element
            if (element.right != null) {
                element = element.right
                while (element != null) {
                    if (element !in stack) stack.push(element)
                    element = element.left
                }
            }
            node = result
            return node!!.value
        }

        /**
         * Удаление следующего элемента
         * Сложная
         *
         * Трудоёмкость: O(height)
         * Ресурсоёмкость: O(height)
         */
        override fun remove() {
            removeNode(node ?: return)
        }
    }

    override fun iterator(): MutableIterator<T> = BinaryTreeIterator<Any>()

    override fun comparator(): Comparator<in T>? = null

    /**
     * Найти множество всех элементов в диапазоне [fromElement, toElement)
     * Очень сложная
     *
     * Трудоёмкость: O(1)
     * Ресурсоёмкость: O(1)
     */
    override fun subSet(fromElement: T, toElement: T): SortedSet<T> {
        require(fromElement < toElement) { "Неверный интервал" }
        return CreateSubSet(this, fromElement, toElement)
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     *
     * Трудоёмкость: O(1)
     * Ресурсоёмкость: O(1)
     */
    override fun headSet(toElement: T): SortedSet<T> = CreateSubSet(this, null, toElement)


    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     *
     * Трудоёмкость: O(1)
     * Ресурсоёмкость: O(1)
     */
    override fun tailSet(fromElement: T): SortedSet<T> = CreateSubSet(this, fromElement, null)


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

    private inner class CreateSubSet(parentSet: KtBinaryTree<T>, startSubSet: T?, finishSubSet: T?) :
        KtBinaryTree<T>() {

        private val start = startSubSet

        private val finish = finishSubSet

        private val parent = parentSet

        private var sizeSubSet = 0

        private fun T.addSubSet() = ((start == null || this >= start)
                && (finish == null || this < finish))

        override fun add(element: T): Boolean {
            require(element.addSubSet())
            return parent.add(element)
        }

        override operator fun contains(element: T): Boolean {
            return parent.contains(element) && element.addSubSet()
        }

        override var size: Int
            get() = parent.count { it.addSubSet() }
            set(value) {
                sizeSubSet = value
            }

        override fun remove(element: T): Boolean {
            require(element.addSubSet())
            return parent.remove(element)
        }

        override fun iterator(): MutableIterator<T> = BinaryTreeIteratorSubSet(start, finish)

        private inner class BinaryTreeIteratorSubSet internal constructor(start: T?, finish: T?) :
            BinaryTreeIterator<T>() {

            private var node: Node<T>? = null

            private val stack = ArrayDeque<Node<T>>()

            init {
                var element = root
                while (element != null) {
                    if ((start == null || element.value >= start)
                        && (finish == null || element.value < finish)
                    ) {
                        stack.push(element)
                        element = element.left
                    }
                }
            }

            override fun hasNext() = stack.isNotEmpty()

            override fun next(): T {
                if (!hasNext()) throw NotImplementedError()
                var element = stack.pop()
                val result = element
                if (element.right != null) {
                    element = element.right
                    while (element != null) {
                        if (element !in stack) stack.push(element)
                        element = element.left
                    }
                }
                node = result
                return node!!.value
            }

            override fun remove() {
                removeNode(node ?: return)
            }
        }
    }
}
