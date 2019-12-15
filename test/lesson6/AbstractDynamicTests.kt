package lesson6

import java.io.File
import kotlin.test.assertEquals

abstract class AbstractDynamicTests {
    fun longestCommonSubSequence(longestCommonSubSequence: (String, String) -> String) {
        assertEquals("", longestCommonSubSequence("мой мир", "я"))
        assertEquals("1", longestCommonSubSequence("1", "1"))
        assertEquals("13", longestCommonSubSequence("123", "13"))
        assertEquals("здс", longestCommonSubSequence("здравствуй мир", "мы здесь"))
        assertEquals("emt ole", longestCommonSubSequence("nematode knowledge", "empty bottle"))
        val expectedLength = "e kerwelkkd r".length
        assertEquals(
            expectedLength, longestCommonSubSequence(
                "oiweijgw kejrhwejelkrw kjhdkfjs hrk",
                "perhkhk lerkerorwetp lkjklvvd durltr"
            ).length, "Answer must have length of $expectedLength, e.g. 'e kerwelkkd r' or 'erhlkrw kjk r'"
        )
        val expectedLength2 = """ дд саы чтых,
евшнео ваа се сви дн.
        """.trimIndent().length
        assertEquals(
            expectedLength2, longestCommonSubSequence(
                """
Мой дядя самых честных правил,
Когда не в шутку занемог,
Он уважать себя заставил
И лучше выдумать не мог.
                """.trimIndent(),
                """
Так думал молодой повеса,
Летя в пыли на почтовых,
Всевышней волею Зевеса
Наследник всех своих родных.
                """.trimIndent()
            ).length, "Answer must have length of $expectedLength2"
        )
        assertEquals("ABEKA", longestCommonSubSequence("ABDEFFEKABCD", "GABCEKEEA"))
        assertEquals("HARBR", longestCommonSubSequence("HABRAHABR", "HARBOUR"))
        assertEquals(
            "Как он ме ать н, евноть зуять, ать ть",
            longestCommonSubSequence(
                "Как рано мог он лицемерить, Таить надежду, ревновать, Разуверять, заставить верить",
                "Как он умел казаться новым, Шутя невинность изумлять, Пугать отчаяньем готовым"
            )
        )
        assertEquals(
            "Ка  осет  рока оо а осно езо",
            longestCommonSubSequence(
                "Как взор его был быстр и нежен, Стыдлив и дерзок, а порой Блистал послушною слезой!",
                "Куда ж поскачет мой проказник? С кого начнет он? Все равно: Везде поспеть немудрено."
            )
        )
        assertEquals(
            "ет  оол, енны жь авальы акр ый адани уник",
            longestCommonSubSequence(
                "Какие сети им готовил! Но вы, блаженные мужья, С ним оставались вы друзья: " +
                        "Его ласкал супруг лукавый, Фобласа давний ученик",
                "Театра злой законодатель, Непостоянный обожатель Очаровательных актрис, " +
                        "Почетный гражданин кулис, Онегин полетел к театру"
            )
        )
    }

    fun longestIncreasingSubSequence(longestIncreasingSubSequence: (List<Int>) -> List<Int>) {
        assertEquals(listOf(), longestIncreasingSubSequence(listOf()))
        assertEquals(listOf(1), longestIncreasingSubSequence(listOf(1)))
        assertEquals(listOf(1, 2), longestIncreasingSubSequence(listOf(1, 2)))
        assertEquals(listOf(2), longestIncreasingSubSequence(listOf(2, 1)))
        assertEquals(
            listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
            longestIncreasingSubSequence(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
        )
        assertEquals(listOf(2, 8, 9, 12), longestIncreasingSubSequence(listOf(2, 8, 5, 9, 12, 6)))
        assertEquals(
            listOf(23, 34, 56, 87, 91, 98, 140, 349), longestIncreasingSubSequence(
                listOf(
                    23, 76, 34, 93, 123, 21, 56, 87, 91, 12, 45, 98, 140, 12, 5, 38, 349, 65, 94,
                    45, 76, 15, 99, 100, 88, 84, 35, 88
                )
            )
        )
        assertEquals(
            listOf(3, 6, 7, 8, 9, 10), lesson6.longestIncreasingSubSequence(
                listOf(3, 6, 5, 4, 7, 8, 9, 8, 7, 6, 8, 1, 0, 4, 7, 10)
            )
        )
        assertEquals(
            listOf(0, 2, 5, 6, 7, 8, 11), lesson6.longestIncreasingSubSequence(
                listOf(0, 9, 2, 5, 4, 7, 6, 9, 8, -1, -2, -5, 7, 8, 11, 5, 8, 7, 4, 7)
            )
        )
        assertEquals(
            listOf(-1, 0, 4, 5, 7, 8, 10), longestIncreasingSubSequence(
                listOf(-1, 0, -3, -4, -7, -8, 7, 8, 4, 7, 10, 4, 7, 5, 7, 5, 8, 10)
            )
        )
    }

    fun shortestPathOnField(shortestPathOnField: (String) -> Int) {
        assertEquals(10, shortestPathOnField("input/field_in2.txt"))
        assertEquals(12, shortestPathOnField("input/field_in1.txt"))
        assertEquals(43, shortestPathOnField("input/field_in3.txt"))
        assertEquals(28, shortestPathOnField("input/field_in4.txt"))
        assertEquals(222, shortestPathOnField("input/field_in5.txt"))
        assertEquals(15, shortestPathOnField("input/field_in6.txt"))
        assertEquals(0, shortestPathOnField("input/field_in7.txt"))
        assertEquals(314, shortestPathOnField("input/field_in8.txt"))
    }

}