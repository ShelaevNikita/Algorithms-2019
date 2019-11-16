package lesson3

import org.junit.jupiter.api.Tag
import kotlin.test.*

class TrieTest {

    @Test
    @Tag("Example")
    fun generalTest() {
        val trie = Trie()
        assertEquals(0, trie.size)
        assertFalse("Some" in trie)
        trie.add("abcdefg")
        assertTrue("abcdefg" in trie)
        assertFalse("abcdef" in trie)
        assertFalse("a" in trie)
        assertFalse("g" in trie)
        trie.add("zyx")
        trie.add("zwv")
        trie.add("zyt")
        trie.add("abcde")
        assertEquals(5, trie.size)
        assertTrue("abcdefg" in trie)
        assertFalse("abcdef" in trie)
        assertTrue("abcde" in trie)
        assertTrue("zyx" in trie)
        assertTrue("zyt" in trie)
        assertTrue("zwv" in trie)
        assertFalse("zy" in trie)
        assertFalse("zv" in trie)
        trie.remove("zwv")
        trie.remove("zy")
        assertEquals(4, trie.size)
        assertTrue("zyt" in trie)
        assertFalse("zwv" in trie)
        trie.clear()
        assertEquals(0, trie.size)
        assertFalse("zyx" in trie)
    }

    @Test
    @Tag("Hard")
    fun rudeIteratorTest1() {
        val trie = Trie()
        assertEquals(setOf<String>(), trie)
        trie.add("abcdefg")
        trie.add("zyx")
        trie.add("zwv")
        trie.add("zyt")
        trie.add("abcde")
        assertEquals(setOf("abcdefg", "zyx", "zwv", "zyt", "abcde"), trie)
        val trie1 = Trie()
        assertEquals(0, trie1.size)
        assertFalse("Some" in trie1)
        assertEquals(setOf<String>(), trie1)
        trie1.add("abcdefg")
        trie1.add("zyx")
        trie1.add("zwv")
        trie1.add("zyt")
        trie1.add("abcde")
        assertEquals(setOf("abcdefg", "zyx", "zwv", "zyt", "abcde"), trie1)
        val iterator = trie1.iterator()
        iterator.next()
        iterator.remove()
        iterator.next()
        iterator.remove()
        iterator.next()
        iterator.remove()
        iterator.next()
        iterator.remove()
        assertEquals(1, trie1.size)
    }

    @Test
    @Tag("Hard")
    fun rudeIteratorTest2() {
        val trie = Trie()
        assertEquals(setOf<String>(), trie)
        trie.add("error")
        trie.add("throw throw throw throw throw throw throw throw")
        trie.add("end ")
        trie.add("five")
        trie.add("ena")
        val iterator = trie.iterator()
        iterator.next()
        iterator.remove()
        assertEquals(4, trie.size)
        iterator.next()
        iterator.remove()
        assertEquals(3, trie.size)
        iterator.next()
        iterator.remove()
        assertEquals(2, trie.size)
        iterator.next()
        iterator.remove()
        assertEquals(1, trie.size)
    }
}