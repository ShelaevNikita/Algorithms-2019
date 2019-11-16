package lesson3

class Trie : AbstractMutableSet<String>(), MutableSet<String> {

    override var size: Int = 0
        private set

    private class Node {
        val children: MutableMap<Char, Node> = linkedMapOf()
        var parent: Node? = null
    }

    private var root = Node()

    override fun clear() {
        root.children.clear()
        size = 0
    }

    private fun String.withZero() = this + 0.toChar()

    private fun findNextNode(element: String): Node? {
        var current = root
        for (char in element) {
            current = current.children[char] ?: return null
        }
        return current
    }

    override fun contains(element: String): Boolean =
        findNextNode(element.withZero()) != null

    override fun containsAll(elements: Collection<String>): Boolean {
        var change = true
        for (element in elements)
            change = contains(element)
        return change
    }

    override fun add(element: String): Boolean {
        var current = root
        var modified = false
        for (char in element.withZero()) {
            val child = current.children[char]
            if (child != null) {
                current = child
            } else {
                modified = true
                val newChild = Node()
                newChild.parent = current
                current.children[char] = newChild
                current = newChild
            }
        }
        if (modified) {
            size++
        }
        return modified
    }

    override fun toString(): String {
        val string = StringBuilder()
        for (element in this) {
            string.append(element)
            string.append("; ")
        }
        string.delete(string.length - 2, string.length)
        return string.toString()
    }


    override fun addAll(elements: Collection<String>): Boolean {
        for (element in elements)
            add(element)
        return true
    }

    override fun remove(element: String): Boolean {
        val current = findNextNode(element) ?: return false
        return removeNode(current)
    }

    private fun removeNode(current: Node): Boolean {
        if (current.children.remove(0.toChar()) != null) {
            size--
            return true
        }
        return false
    }

    override fun removeAll(elements: Collection<String>): Boolean {
        for (element in elements)
            remove(element)
        return true
    }

    /**
     * Итератор для префиксного дерева
     * Сложная
     */
    override fun iterator(): MutableIterator<String> {
        return TrieIterator()
    }

    private inner class TrieIterator internal constructor() : MutableIterator<String> {

        private var nextString = StringBuilder()

        private var currentNode: Node? = null

        private var visited = mutableSetOf<Node>()

        private var nextNode: Node? = findNextNode(root)

        /**
         *  Ресурсоёмкость: O(1)
         *  Трудоёмкость: O(1)
         */
        override fun hasNext(): Boolean {
            return nextNode != null
        }

        /**
         *  Ресурсоёмкость: O(word.lenght)
         *  Трудоёмкость: O(N)
         */
        override fun next(): String {
            if (!hasNext()) throw NotImplementedError()
            currentNode = nextNode
            nextNode = findNextNode(nextNode)
            return nextString.toString()
        }

        /**
         *  Ресурсоёмкость: O(1)
         *  Трудоёмкость: O(1)
         */
        override fun remove() {
            requireNotNull(currentNode)
            removeNode(currentNode!!)
            currentNode = null
        }

        private fun findNextNode(node: Node?): Node? {
            requireNotNull(node)
            if (node !in visited) {
                visited.add(node)
                if (node.children.containsKey(0.toChar())) return node
            }
            for ((char, child) in node.children) {
                if ((char != 0.toChar()) && (child !in visited)) {
                    nextString.append(char)
                    return findNextNode(child)
                }
            }
            if (node == root) return null
            nextString.delete(0, nextString.length)
            return findNextNode(node.parent)
        }
    }
}