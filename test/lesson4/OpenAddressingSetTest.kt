package lesson4

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import kotlin.random.Random
import kotlin.test.assertFailsWith

class OpenAddressingSetTest {

    @Test
    @Tag("Example")
    fun add() {
        val set = OpenAddressingSet<String>(16)
        assertTrue(set.isEmpty())
        set.add("Alpha")
        set.add("Beta")
        set.add("Omega")
        assertSame(3, set.size)
        assertTrue("Beta" in set)
        assertFalse("Gamma" in set)
        assertTrue("Omega" in set)
    }

    @Test
    @Tag("Example")
    fun remove() {
        val set = OpenAddressingSet<String>(5)
        assertTrue(set.isEmpty())
        set.add("Alpha")
        set.add("Beta")
        set.add("Omega")
        set.add("Delta")
        set.add("Gamma")
        set.remove("Alpha")
        assertEquals(4, set.size)
        assertFalse("Alpha" in set)
        assertTrue("Beta" in set)
        assertTrue("Omega" in set)
        set.add("Alpha")
        assertTrue("Alpha" in set)
        assertTrue("Beta" in set)
        assertTrue("Omega" in set)
        assertEquals(5, set.size)
        set.remove("Alpha")
        set.remove("Beta")
        set.remove("Delta")
        assertFalse("Beta" in set)
        assertTrue("Omega" in set)
        assertEquals(2, set.size)
    }

    @Test
    @Tag("Example")
    fun iterator() {
        val set = OpenAddressingSet<String>(5)
        assertTrue(set.isEmpty())
        set.add("Alpha")
        set.add("Beta")
        set.add("Omega")
        set.add("Delta")
        set.add("Gamma")
        assertEquals(setOf("Alpha", "Beta", "Omega", "Delta", "Gamma"), set)
        val iterator = set.iterator()
        iterator.next()
        iterator.remove()
        assertEquals(4, set.size)
        iterator.next()
        iterator.remove()
        assertEquals(3, set.size)
        iterator.next()
        iterator.remove()
        assertEquals(2, set.size)
        iterator.next()
        iterator.remove()
        assertEquals(1, set.size)
        iterator.next()
        iterator.remove()
        assertEquals(0, set.size)
        assertEquals(false, iterator.hasNext())
        assertFailsWith<IllegalArgumentException> { iterator.next() }
        assertFalse(set.contains("Alpha"))
        assertFalse(set.contains("Beta"))
        assertFalse(set.contains("Omega"))
        assertFalse(set.contains("Delta"))
        assertFalse(set.contains("Gamma"))
        set.add("Alpha")
        assertEquals(1, set.size)
    }
}