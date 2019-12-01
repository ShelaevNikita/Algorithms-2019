@file:Suppress("UNUSED_PARAMETER")

package lesson6

/**
 * Наибольшая общая подпоследовательность.
 * Средняя
 *
 * Дано две строки, например "nematode knowledge" и "empty bottle".
 * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
 * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
 * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
 * Если общей подпоследовательности нет, вернуть пустую строку.
 * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
 * При сравнении подстрок, регистр символов *имеет* значение.
 *
 * Трудоёмкость: O(first.length * second.length);
 * Ресурсоёмкость: O(first.length * second.length)
 */
fun longestCommonSubSequence(first: String, second: String): String {
    var firstSize = first.length
    var secondSize = second.length
    val matrix = MutableList(firstSize) {
        MutableList(secondSize) { 0 }
    }
    for (i in 0 until firstSize) {
        for (j in 0 until secondSize) {
            if ((i != 0) && (j != 0)) {
                if (first[i] == second[j])
                    matrix[i][j] = matrix[i - 1][j - 1] + 1
                else
                    matrix[i][j] = maxOf(matrix[i][j - 1], matrix[i - 1][j])
            }
        }
    }
    val result = StringBuilder()
    firstSize--
    secondSize--
    while ((firstSize >= 0) && (secondSize >= 0)) {
        when {
            first[firstSize] == second[secondSize] -> {
                result.append(first[firstSize])
                firstSize--
                secondSize--
            }
            secondSize == 0 -> firstSize--
            firstSize == 0 -> secondSize--
            matrix[firstSize - 1][secondSize] > matrix[firstSize][secondSize - 1] -> {
                firstSize--
            }
            else -> secondSize--
        }
    }
    return result.reverse().toString()
}

/**
 * Наибольшая возрастающая подпоследовательность
 * Сложная
 *
 * Дан список целых чисел, например, [2 8 5 9 12 6].
 * Найти в нём самую длинную возрастающую подпоследовательность.
 * Элементы подпоследовательности не обязаны идти подряд,
 * но должны быть расположены в исходном списке в том же порядке.
 * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
 * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
 * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
 *
 * Трудоёмкость: O(N ^ 2);
 * Ресурсоёмкость: O(2 * N + result) = O(N)
 */
fun longestIncreasingSubSequence(list: List<Int>): List<Int> {
    if (list.isEmpty()) return emptyList()
    val parent = MutableList(list.size) { -1 }
    val value = MutableList(list.size) { 1 }
    for (i in list.indices) {
        for (j in 0 until i) {
            if ((list[j] < list[i]) && (value[i] < value[j] + 1)) {
                value[i] = value[j] + 1
                parent[i] = j
            }
        }
    }
    var current = value.indices.maxBy { value[it] } ?: -1
    val result = mutableListOf<Int>()
    result += list[current]
    while (value[current] != 1) {
        current = parent[current]
        result += list[current]
    }
    return result.reversed()
}

/**
 * Самый короткий маршрут на прямоугольном поле.
 * Средняя
 *
 * В файле с именем inputName задано прямоугольное поле:
 *
 * 0 2 3 2 4 1
 * 1 5 3 4 6 2
 * 2 6 2 5 1 3
 * 1 4 3 2 6 2
 * 4 2 3 1 5 0
 *
 * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
 * В каждой клетке записано некоторое натуральное число или нуль.
 * Необходимо попасть из верхней левой клетки в правую нижнюю.
 * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
 * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
 *
 * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
 */
fun shortestPathOnField(inputName: String): Int {
    TODO()
}

// Задачу "Максимальное независимое множество вершин в графе без циклов"
// смотрите в уроке 5