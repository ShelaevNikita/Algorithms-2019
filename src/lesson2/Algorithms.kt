@file:Suppress("UNUSED_PARAMETER")

package lesson2

import java.io.File
import java.io.IOException
import java.util.*
import kotlin.math.log
import kotlin.math.pow
/**
 * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
 * Простая
 *
 * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
 * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
 *
 * 201
 * 196
 * 190
 * 198
 * 187
 * 194
 * 193
 * 185
 *
 * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
 * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
 * Вернуть пару из двух моментов.
 * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
 * Например, для приведённого выше файла результат должен быть Pair(3, 4)
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun optimizeBuyAndSell(inputName: String): Pair<Int, Int> {
    /**
     * Ресурсоёмкость: O(N);
     * Трудоёмкость: O(N)
     */
    val list = mutableListOf<Int>()
    try {
        val file = File(inputName).bufferedReader().readLines()
        var sum = 0
        var max = 0
        var result = Pair(0, 0)
        for (string in file)
            list += string.toInt()
        val delta = mutableListOf<Int>()
        for (string in list.indices) {
            if (string != list.size - 1) delta += list[string + 1] - list[string]
        }
        var y = 0
        for (x in 0 until delta.size) {
            val change = max
            sum += delta[x]
            max = maxOf(sum, max)
            if (change != max) y = x + 1
            sum = maxOf(sum, 0)
        }
        for (x in 0 until list.size) {
            if (list[y] - list[x] == max) {
                result = Pair(x + 1, y + 1)
                break
            }
        }
        return result
    } catch (e: NumberFormatException) {
        throw NumberFormatException("Неверный формат")
    }
}

/**
 * Задача Иосифа Флафия.
 * Простая
 *
 * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
 *
 * 1 2 3
 * 8   4
 * 7 6 5
 *
 * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
 * Человек, на котором остановился счёт, выбывает.
 *
 * 1 2 3
 * 8   4
 * 7 6 х
 *
 * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
 * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
 *
 * 1 х 3
 * 8   4
 * 7 6 Х
 *
 * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
 *
 * 1 Х 3
 * х   4
 * 7 6 Х
 *
 * 1 Х 3
 * Х   4
 * х 6 Х
 *
 * х Х 3
 * Х   4
 * Х 6 Х
 *
 * Х Х 3
 * Х   х
 * Х 6 Х
 *
 * Х Х 3
 * Х   Х
 * Х х Х
 *
 * Общий комментарий: решение из Википедии для этой задачи принимается,
 * но приветствуется попытка решить её самостоятельно.
 */
fun josephTask(menNumber: Int, choiceInterval: Int): Int {
    /**
     * Ресурсоёмкость: O(N);
     * Трудоёмкость: O(N * logN), в худшем случае: O(N ^ 2)
     */
    val list1 = mutableListOf<Int>()
    if (menNumber == 1) return 1
    if (choiceInterval == 2)
        return 2 * (menNumber - 2.0.pow(log(menNumber.toDouble(), 2.0))).toInt() + 1
    for (x in 1..menNumber) {
        if (x % choiceInterval != 0) list1 += x
        if ((x == menNumber) && (list1.size == 0)) return menNumber
    }
    if (list1.size == 1) return list1[0]
    var list2 = list1.toMutableList()
    var number = menNumber % choiceInterval
    list1.clear()
    while (list1.size != 1) {
        for (x in list2) {
            number++
            if (number != choiceInterval) list1 += x else number = 0
        }
        if (list1.size == 1) break
        list2 = list1.toMutableList()
        list1.clear()
    }
    return list1[0]
}

/**
 * Наибольшая общая подстрока.
 * Средняя
 *
 * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
 * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
 * Если общих подстрок нет, вернуть пустую строку.
 * При сравнении подстрок, регистр символов *имеет* значение.
 * Если имеется несколько самых длинных общих подстрок одной длины,
 * вернуть ту из них, которая встречается раньше в строке first.
 */
fun longestCommonSubstring(first: String, second: String): String {
    /**
     * Ресурсоёмкость: O(3 * S) = O(S);
     * Трудоёмкость: O(F * S)
     */
    val listResult = MutableList(second.length) { 0 }
    var index = 0
    var max = 0
    for (char1 in first.indices) {
        val listSecond = listResult.toList()
        val list = mutableListOf<Int>()
        val check = max
        for (char2 in second)
            list += if (first[char1] == char2) 1 else 0
        for (element in 0 until list.size)
            if (list[element] == 1)
                if (element >= 1)
                    listResult[element] = listSecond[element - 1] + 1 else listResult[element] = 1
            else listResult[element] = 0
        max = maxOf(max, listResult.max()!!)
        if (check != max) index = char1 + 1
    }
    return first.substring(index - max, index)
}

/**
 * Число простых чисел в интервале
 * Простая
 *
 * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
 * Если limit <= 1, вернуть результат 0.
 *
 * Справка: простым считается число, которое делится нацело только на 1 и на себя.
 * Единица простым числом не считается.
 */
fun calcPrimesNumber(limit: Int): Int {
    /**
     * Ресурсоёмкость: O(logN);
     * Трудоёмкость: O(N * logN)
     */
    if (limit <= 1) return 0
    val set = mutableSetOf<Int>()
    for (x in 2..limit) {
        var number = 0
        for (element in set) {
            if (element * element - 1 > x) {
                set += x
                break
            }
            if (x % element == 0) break else number++
        }
        if (number == set.size) set += x
    }
    return set.size
}

/**
 * Балда
 * Сложная
 *
 * В файле с именем inputName задана матрица из букв в следующем формате
 * (отдельные буквы в ряду разделены пробелами):
 *
 * И Т Ы Н
 * К Р А Н
 * А К В А
 *
 * В аргументе words содержится множество слов для поиска, например,
 * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
 *
 * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
 * и вернуть множество найденных слов. В данном случае:
 * ТРАВА, КРАН, АКВА, НАРТЫ
 *
 * И т Ы Н     И т ы Н
 * К р а Н     К р а н
 * А К в а     А К В А
 *
 * Все слова и буквы -- русские или английские, прописные.
 * В файле буквы разделены пробелами, строки -- переносами строк.
 * Остальные символы ни в файле, ни в словах не допускаются.
 */
fun baldaSearcher(inputName: String, words: Set<String>): Set<String> {
    /**
     * Ресурсоёмкость: O(columns * rows);
     * Трудоёмкость: O(words * columns * rows * queue)
     */
    val words1 = words.toMutableSet()
    val result = mutableSetOf<String>()
    try {
        val file = File(inputName).bufferedReader().readLines()
        val matrix = mutableListOf<MutableList<Char>>()
        for (lines in file.indices) {
            val list = mutableListOf<Char>()
            for (char in file[lines].indices) {
                require(!((file[lines][char].isDigit()) || (file[lines][char].isLowerCase()))) { "Только прописные буквы" }
                if (file[lines][char] != ' ') list += file[lines][char]
            }
            matrix += list
        }
        val columns = matrix.size
        val rows = matrix[0].size
        for (x in 0 until columns) {
            for (y in 0 until rows) {
                for (word in words1) {
                    val length = word.length
                    var number = 0
                    if (matrix[x][y] == word[number]) {
                        number++
                        val queue = ArrayDeque<Pair<Int, Int>>()
                        queue.add(Pair(x, y))
                        val visited = mutableSetOf<Pair<Int, Int>>()
                        val map = mutableMapOf<Int, Int>()
                        visited += Pair(x, y)
                        map += Pair(1, 1)
                        while (queue.isNotEmpty()) {
                            require(!((word[number].isDigit()) || (word[number].isLowerCase()))) { "Только прописные буквы" }
                            var f = 0
                            val next = queue.poll()
                            visited += next
                            if (next.first + 1 < columns) {
                                if (Pair(next.first + 1, next.second) !in visited)
                                    if (matrix[next.first + 1][next.second] == word[number]) {
                                        visited += Pair(next.first + 1, next.second)
                                        f++
                                        queue.addFirst(Pair(next.first + 1, next.second))
                                    }
                            }
                            if (next.second + 1 < rows) {
                                if (Pair(next.first, next.second + 1) !in visited)
                                    if (matrix[next.first][next.second + 1] == word[number]) {
                                        if (f == 0) visited += Pair(next.first, next.second + 1)
                                        f++
                                        queue.addFirst(Pair(next.first, next.second + 1))
                                    }
                            }
                            if (next.first - 1 >= 0) {
                                if (Pair(next.first - 1, next.second) !in visited)
                                    if (matrix[next.first - 1][next.second] == word[number]) {
                                        if (f == 0) visited += Pair(next.first - 1, next.second)
                                        f++
                                        queue.addFirst(Pair(next.first - 1, next.second))
                                    }
                            }
                            if (next.second - 1 >= 0) {
                                if (Pair(next.first, next.second - 1) !in visited)
                                    if (matrix[next.first][next.second - 1] == word[number]) {
                                        if (f == 0) visited += Pair(next.first, next.second - 1)
                                        f++
                                        queue.addFirst(Pair(next.first, next.second - 1))
                                    }
                            }
                            if (f != 0) {
                                number++
                                map[number] = f
                            } else {
                                for (r in number downTo 1)
                                    if (map[r]!! >= 2) {
                                        number = r
                                        val take = visited.take(number)
                                        visited.clear()
                                        visited += take
                                        break
                                    }
                            }
                            if (number == length) break
                        }
                    }
                    if (number == length) {
                        result += word
                        words1 -= word
                        break
                    }
                }
            }
        }
        return result
    } catch (e: IOException) {
        throw IOException("Неверный формат")
    }
}