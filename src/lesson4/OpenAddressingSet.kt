package lesson4

@Suppress("UNCHECKED_CAST")
class OpenAddressingSet<T : Any>(private val bits: Int) : AbstractMutableSet<T>() {

    init {
        require(bits in 2..31)
    }

    private val capacity = 1 shl bits

    private val storage = Array<Any?>(capacity) { null }

    override var size: Int = 0

    private fun T.startingIndex(): Int {
        return hashCode() and (0x7FFFFFFF shr (31 - bits))
    }

    override fun contains(element: T): Boolean {
        var index = element.startingIndex()
        var current = storage[index]
        while (current != null) {
            if (current == element) {
                return true
            }
            index = (index + 1) % capacity
            current = storage[index]
        }
        return false
    }

    override fun add(element: T): Boolean {
        val startingIndex = element.startingIndex()
        var index = startingIndex
        var current = storage[index]
        while (current != null) {
            if (current == element) return false
            index = (index + 1) % capacity
            check(index != startingIndex) { "Table is full" }
            current = storage[index]
        }
        storage[index] = element
        size++
        return true
    }

    /**
     * Для этой задачи пока нет тестов, но вы можете попробовать привести решение и добавить к нему тесты
     *
     * Ресурсоёмкость: O(1)
     * Трудоёмкость: O(capacity)
     */
    override fun remove(element: T): Boolean {
        val startingIndex = element.startingIndex()
        var index = startingIndex
        var current = storage[index]
        while (current != null) {
            if (current == element) {
                storage[index] = null
                size--
            }
            index = (index + 1) % capacity
            if (index == startingIndex) return false
            current = storage[index]
        }
        return true
    }

    /**
     * Для этой задачи пока нет тестов, но вы можете попробовать привести решение и добавить к нему тесты
     */
    override fun iterator(): MutableIterator<T> {
        return OpenAddressingSetIterator()
    }

    private inner class OpenAddressingSetIterator internal constructor() : MutableIterator<T> {

        private var nextIndex = 0

        private var nowIndex = 0

        private var now: T? = null

        private var next: T? = findNext()

        /**
         *  Ресурсоёмкость: O(1)
         *  Трудоёмкость: O(1)
         */
        override fun hasNext(): Boolean {
            return next != null
        }

        /**
         *  Ресурсоёмкость: O(1)
         *  Трудоёмкость: O(capacity)
         */
        override fun next(): T {
            if (!hasNext()) throw NoSuchElementException()
            now = next
            nowIndex = nextIndex
            next = findNext()
            return now!!
        }

        /**
         *  Ресурсоёмкость: O(1)
         *  Трудоёмкость: O(1)
         */
        override fun remove() {
            requireNotNull(now)
            storage[nowIndex] = null
            size--
            now = null
        }

        private fun findNext(): T? {
            if (size == 0) return null
            else {
                var current: Any?
                do {
                    nextIndex++
                    if (nextIndex == capacity) return null
                    current = storage[nextIndex]
                } while (current == null)
                return current as T?
            }
        }
    }
}