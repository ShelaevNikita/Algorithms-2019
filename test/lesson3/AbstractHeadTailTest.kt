package lesson3

import java.util.*
import kotlin.test.*

abstract class AbstractHeadTailTest {
    private lateinit var tree: SortedSet<Int>
    private lateinit var randomTree: SortedSet<Int>
    private val randomTreeSize = 1000
    private val randomValues = mutableListOf<Int>()

    protected fun fillTree(create: () -> SortedSet<Int>) {
        this.tree = create()
        //В произвольном порядке добавим числа от 1 до 10
        tree.add(5)
        tree.add(1)
        tree.add(2)
        tree.add(7)
        tree.add(9)
        tree.add(10)
        tree.add(8)
        tree.add(4)
        tree.add(3)
        tree.add(6)

        this.randomTree = create()
        val random = Random()
        for (i in 0 until randomTreeSize) {
            val randomValue = random.nextInt(randomTreeSize) + 1
            if (randomTree.add(randomValue)) {
                randomValues.add(randomValue)
            }
        }
    }


    protected fun doHeadSetTest() {
        var set: SortedSet<Int> = tree.headSet(5)
        assertEquals(true, set.contains(1))
        assertEquals(true, set.contains(2))
        assertEquals(true, set.contains(3))
        assertEquals(true, set.contains(4))
        assertEquals(false, set.contains(5))
        assertEquals(false, set.contains(6))
        assertEquals(false, set.contains(7))
        assertEquals(false, set.contains(8))
        assertEquals(false, set.contains(9))
        assertEquals(false, set.contains(10))


        set = tree.headSet(7)
        for (i in 1..6)
            assertEquals(true, set.contains(i))
        for (i in 8..10)
            assertEquals(false, set.contains(i))
    }

    protected fun doTailSetTest() {
        var set: SortedSet<Int> = tree.tailSet(5)
        assertEquals(false, set.contains(1))
        assertEquals(false, set.contains(2))
        assertEquals(false, set.contains(3))
        assertEquals(false, set.contains(4))
        assertEquals(true, set.contains(5))
        assertEquals(true, set.contains(6))
        assertEquals(true, set.contains(7))
        assertEquals(true, set.contains(8))
        assertEquals(true, set.contains(9))
        assertEquals(true, set.contains(10))

        set = tree.tailSet(2)
        for (i in 3..10)
            assertEquals(true, set.contains(i))
        for (i in 0..1)
            assertEquals(false, set.contains(i))

    }

    protected fun doHeadSetRelationTest() {
        val set: SortedSet<Int> = tree.headSet(7)
        assertEquals(6, set.size)
        assertEquals(10, tree.size)
        tree.add(0)
        assertTrue(set.contains(0))
        set.add(-2)
        assertTrue(tree.contains(-2))
        tree.add(12)
        assertFalse(set.contains(12))
        assertFailsWith<IllegalArgumentException> { set.add(8) }
        assertEquals(8, set.size)
        assertEquals(13, tree.size)
        assertFailsWith<IllegalArgumentException> { set.remove(7) }
        tree.remove(3)
        assertFalse(set.contains(3))
        set.remove(-2)
        assertFalse(tree.contains(-2))
        assertEquals(6, set.size)
        assertEquals(11, tree.size)
        tree.addAll(listOf(75, 84, 7, 3, -2, -1))
        assertTrue(set.containsAll(listOf(-1, -2, 3)))
        assertFalse(set.contains(75))
        assertFalse(set.contains(84))
        assertFalse(set.contains(7))
    }

    protected fun doTailSetRelationTest() {
        val set: SortedSet<Int> = tree.tailSet(4)
        assertEquals(7, set.size)
        assertEquals(10, tree.size)
        tree.add(12)
        assertTrue(set.contains(12))
        set.add(42)
        assertTrue(tree.contains(42))
        tree.add(0)
        assertFalse(set.contains(0))
        assertFailsWith<IllegalArgumentException> { set.add(-2) }
        assertEquals(9, set.size)
        assertEquals(13, tree.size)
        assertFailsWith<IllegalArgumentException> { set.remove(1) }
        tree.remove(12)
        assertFalse(set.contains(12))
        set.remove(42)
        assertFalse(tree.contains(42))
        assertEquals(7, set.size)
        assertEquals(11, tree.size)
        tree.addAll(listOf(75, 84, 0, 14, 15, -1))
        assertTrue(set.containsAll(listOf(75, 84, 14, 15)))
        assertFalse(set.contains(0))
        assertFalse(set.contains(-1))
    }

    protected fun doSubSetTest() {
        val smallSet: SortedSet<Int> = tree.subSet(3, 8)
        assertEquals(false, smallSet.contains(1))
        assertEquals(false, smallSet.contains(2))
        assertEquals(true, smallSet.contains(3))
        assertEquals(true, smallSet.contains(4))
        assertEquals(true, smallSet.contains(5))
        assertEquals(true, smallSet.contains(6))
        assertEquals(true, smallSet.contains(7))
        assertEquals(false, smallSet.contains(8))
        assertEquals(false, smallSet.contains(9))
        assertEquals(false, smallSet.contains(10))

        assertFailsWith<IllegalArgumentException> { smallSet.add(2) }
        assertFailsWith<IllegalArgumentException> { smallSet.add(9) }
        assertFailsWith<IllegalArgumentException> { smallSet.remove(9) }
        assertFailsWith<IllegalArgumentException> { smallSet.remove(1) }

        val allSet = tree.subSet(1, 11)
        for (i in 1..10)
            assertEquals(true, allSet.contains(i))
        assertEquals(false, allSet.contains(11))
        val random = Random()
        val toElement = random.nextInt(randomTreeSize) + 1
        val fromElement = random.nextInt(toElement - 1) + 1

        val randomSubset = randomTree.subSet(fromElement, toElement)
        randomValues.forEach { element ->
            assertEquals(element in fromElement until toElement, randomSubset.contains(element))
        }
    }

    protected fun doSubSetRelationTest() {
        val set: SortedSet<Int> = tree.subSet(2, 15)
        assertEquals(9, set.size)
        assertEquals(10, tree.size)
        tree.add(11)
        assertTrue(set.contains(11))
        set.add(14)
        assertTrue(tree.contains(14))
        tree.add(0)
        assertFalse(set.contains(0))
        tree.add(15)
        assertFalse(set.contains(15))
        assertFailsWith<IllegalArgumentException> { set.add(1) }
        assertFailsWith<IllegalArgumentException> { set.add(20) }
        assertEquals(11, set.size)
        assertEquals(14, tree.size)
        assertFailsWith<IllegalArgumentException> { set.remove(1) }
        tree.remove(11)
        assertFalse(set.contains(11))
        set.remove(14)
        assertFalse(tree.contains(14))
        assertEquals(9, set.size)
        assertEquals(12, tree.size)
        tree.addAll(listOf(5, 6, 9, 10, 75, 84, 0, 2, 4, 7, 14, 15))
        assertTrue(set.containsAll(listOf(5, 6, 9, 10, 0, 2, 4, 14, 7)))
        tree.removeAll(listOf(9, 10, 75, 2, 4, 14))
        assertTrue(set.containsAll(listOf(5, 6, 0, 7)))
        assertFalse(set.contains(14))
    }

}