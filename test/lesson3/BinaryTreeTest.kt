package lesson3

import org.junit.jupiter.api.Tag
import kotlin.test.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BinaryTreeTest {
    private fun testTree(create: () -> CheckableSortedSet<Int>) {
        val tree = create()
        assertEquals(0, tree.size)
        assertFalse(tree.contains(5))
        tree.add(10)
        tree.add(5)
        tree.add(7)
        tree.add(10)
        assertEquals(3, tree.size)
        assertTrue(tree.contains(5))
        tree.add(3)
        tree.add(1)
        tree.add(3)
        tree.add(4)
        assertEquals(6, tree.size)
        assertFalse(tree.contains(8))
        tree.addAll(listOf(8, 15, 15, 20))
        assertEquals(9, tree.size)
        assertTrue(tree.contains(8))
        assertTrue(tree.checkInvariant())
        assertEquals(1, tree.first())
        assertEquals(20, tree.last())
        tree.removeAll(listOf(8, 15, 20))
        assertFalse(tree.containsAll(listOf(8, 15, 20)))
        assertEquals(1, tree.first())
        assertEquals(10, tree.last())

        val binary = KtBinaryTree<Int>()
        binary.add(10)
        binary.add(5)
        binary.add(7)
        binary.add(10)
        assertEquals(3, binary.size)
        assertTrue(binary.contains(5))
        binary.add(3)
        binary.add(1)
        binary.add(3)
        binary.add(4)
        assertEquals(6, binary.size)
        assertFalse(binary.contains(8))
        binary.addAll(listOf(8, 15, 15, 20))
        assertEquals(setOf(10, 5, 7, 3, 1, 4, 8, 15, 20), binary)
        assertEquals("1, 3, 4, 5, 7, 8, 10, 15, 20", binary.toString())
        assertEquals(9, binary.size)
        assertTrue(binary.contains(8))
        assertTrue(binary.checkInvariant())
        assertEquals(1, binary.first())
        assertEquals(20, binary.last())
        binary.removeAll(listOf(8, 15, 20))
        assertEquals(setOf(10, 5, 7, 3, 1, 4), binary)
        assertFalse(binary.containsAll(listOf(8, 15, 20)))
    }

    @Test
    @Tag("Example")
    fun testAddKotlin() {
        testTree { createKotlinTree() }
    }

    @Test
    @Tag("Example")
    fun testAddJava() {
        testTree { createJavaTree() }
    }

    private fun <T : Comparable<T>> createJavaTree(): CheckableSortedSet<T> = BinaryTree()

    private fun <T : Comparable<T>> createKotlinTree(): CheckableSortedSet<T> = KtBinaryTree()

    private fun testRemove(create: () -> CheckableSortedSet<Int>) {
        val random = Random()
        for (iteration in 1..1000) {
            val list = mutableListOf<Int>()
            for (i in 1..2000) {
                list.add(random.nextInt(1000))
            }
            val binarySet = create()
            assertFalse(binarySet.remove(42))
            for (element in list) {
                binarySet += element
            }
            val originalHeight = binarySet.height()
            val toRemove = list[random.nextInt(list.size)]
            val oldSize = binarySet.size
            assertTrue(binarySet.remove(toRemove))
            assertEquals(oldSize - 1, binarySet.size)
            println("Removing $toRemove from $list")
            for (element in list) {
                val inn = element != toRemove
                assertEquals(
                    inn, element in binarySet,
                    "$element should be ${if (inn) "in" else "not in"} tree"
                )
            }
            assertTrue(binarySet.checkInvariant(), "Binary tree invariant is false after tree.remove()")
            assertTrue(
                binarySet.height() <= originalHeight,
                "After removal of $toRemove from $list binary tree height increased"
            )
            val binarySet1 = create()
            val list2 = listOf(50, 12, 19, 80, 75, 92, 45, 1, 8, 65, 40, 75)
            val remove = 50
            assertFalse(binarySet1.remove(42))
            binarySet1.addAll(list2)
            assertTrue(binarySet1.remove(remove))
            for (element in list2) {
                val inn = element != remove
                assertEquals(inn, element in binarySet1)
            }
        }
    }

    @Test
    @Tag("Normal")
    fun testRemoveKotlin() {
        testRemove { createKotlinTree() }
    }

    @Test
    @Tag("Normal")
    fun testRemoveJava() {
        testRemove { createJavaTree() }
    }

    private fun testIterator(create: () -> CheckableSortedSet<Int>) {
        val random = Random()
        for (iteration in 1..1000) {
            val list = mutableListOf<Int>()
            for (i in 1..200) {
                list.add(random.nextInt(1000))
            }
            val treeSet = TreeSet<Int>()
            val binarySet = create()
            assertFalse(binarySet.iterator().hasNext(), "Iterator of empty set should not have next element")
            for (element in list) {
                treeSet += element
                binarySet += element
            }
            val treeIt = treeSet.iterator()
            val binaryIt = binarySet.iterator()
            println("Traversing $list")
            while (treeIt.hasNext()) {
                assertEquals(treeIt.next(), binaryIt.next(), "Incorrect iterator state while iterating $treeSet")
            }
            val iterator1 = binarySet.iterator()
            val iterator2 = binarySet.iterator()
            println("Consistency check for hasNext $list")
            // hasNext call should not affect iterator position
            while (iterator1.hasNext()) {
                assertEquals(
                    iterator2.next(), iterator1.next(),
                    "Call of iterator.hasNext() changes its state while iterating $treeSet"
                )
            }
        }
        val random1 = Random()
        for (iteration in 1..1000) {
            val list = mutableListOf<Int>()
            for (i in 1..20000) {
                list.add(random1.nextInt(100000))
            }
            val treeSet = TreeSet<Int>()
            val binarySet = create()
            assertFalse(binarySet.iterator().hasNext())
            for (element in list) {
                treeSet += element
                binarySet += element
            }
            val treeIt = treeSet.iterator()
            val binaryIt = binarySet.iterator()
            while (treeIt.hasNext()) assertEquals(treeIt.next(), binaryIt.next())
            val iterator1 = binarySet.iterator()
            val iterator2 = binarySet.iterator()
            while (iterator1.hasNext()) {
                assertEquals(iterator2.next(), iterator1.next())
            }
        }
    }

    @Test
    @Tag("Normal")
    fun testIteratorKotlin() {
        testIterator { createKotlinTree() }
    }

    @Test
    @Tag("Normal")
    fun testIteratorJava() {
        testIterator { createJavaTree() }
    }

    private fun testIteratorRemove(create: () -> CheckableSortedSet<Int>) {
        val random = Random()
        for (iteration in 1..1000) {
            val list = mutableListOf<Int>()
            for (i in 1..2000) {
                list.add(random.nextInt(1000))
            }
            val treeSet = TreeSet<Int>()
            val binarySet = create()
            for (element in list) {
                treeSet += element
                binarySet += element
            }
            val toRemove = list[random.nextInt(list.size)]
            treeSet.remove(toRemove)
            println("Removing $toRemove from $list")
            val iterator = binarySet.iterator()
            var counter = binarySet.size
            while (iterator.hasNext()) {
                val element = iterator.next()
                counter--
                print("$element ")
                if (element == toRemove) {
                    iterator.remove()
                }
            }
            assertEquals(
                0, counter,
                "Iterator.remove() of $toRemove from $list changed iterator position: " +
                        "we've traversed a total of ${binarySet.size - counter} elements instead of ${binarySet.size}"
            )
            println()
            assertEquals<SortedSet<*>>(treeSet, binarySet, "After removal of $toRemove from $list")
            assertEquals(treeSet.size, binarySet.size, "Size is incorrect after removal of $toRemove from $list")
            for (element in list) {
                val inn = element != toRemove
                assertEquals(
                    inn, element in binarySet,
                    "$element should be ${if (inn) "in" else "not in"} tree"
                )
            }
            assertTrue(binarySet.checkInvariant(), "Binary tree invariant is false after tree.iterator().remove()")
        }
        val random1 = Random()
        for (iteration in 1..1000) {
            val list = mutableListOf<Int>()
            for (i in 1..20000) {
                list.add(random1.nextInt(10000))
            }
            val treeSet = TreeSet<Int>()
            val binarySet = create()
            for (element in list) {
                treeSet += element
                binarySet += element
            }
            val toRemove = list[random.nextInt(list.size)]
            treeSet.remove(toRemove)
            val iterator = binarySet.iterator()
            var counter = binarySet.size
            while (iterator.hasNext()) {
                val element = iterator.next()
                counter--
                if (element == toRemove) iterator.remove()
            }
            assertEquals(0, counter)
            assertEquals<SortedSet<*>>(treeSet, binarySet)
            assertEquals(treeSet.size, binarySet.size)
            for (element in list) {
                val inn = element != toRemove
                assertEquals(inn, element in binarySet)
            }
            assertTrue(binarySet.checkInvariant())
        }
    }

    @Test
    @Tag("Hard")
    fun testIteratorRemoveKotlin() {
        testIteratorRemove { createKotlinTree() }
    }

    @Test
    @Tag("Hard")
    fun testIteratorRemoveJava() {
        testIteratorRemove { createJavaTree() }
    }
}