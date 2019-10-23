@file:Suppress("UNUSED_PARAMETER")

package lesson1

import java.io.File

/**
 * Сортировка времён
 *
 * Простая
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
 * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
 *
 * Пример:
 *
 * 01:15:19 PM
 * 07:26:57 AM
 * 10:00:03 AM
 * 07:56:14 PM
 * 01:15:19 PM
 * 12:40:31 AM
 *
 * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
 * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
 *
 * 12:40:31 AM
 * 07:26:57 AM
 * 10:00:03 AM
 * 01:15:19 PM
 * 01:15:19 PM
 * 07:56:14 PM
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */

fun sortTimes(inputName: String, outputName: String) {
    /**
     * Ресурсоёмкость: O(2 * N) = O(N);
     * Трудоёмкость: O(N * logN)
     */
    val listsort = mutableListOf<Int>()
    val timeMap = mutableMapOf<Int, String>()
    try {
        val file = File(inputName).bufferedReader().readLines()
        File(outputName).bufferedWriter().use {
            for (line in file) {
                val element = line.split(" ")
                val timelist = element[0].split(":")
                var hours = timelist[0].toInt()
                val minutes = timelist[1].toInt()
                val seconds = timelist[2].toInt()
                require(!((hours !in 1..12) || (minutes !in 0..59) || (seconds !in 0..59)))
                { "Неверный формат времени" }
                if (hours == 12) hours = 0
                if (element[1] == "PM") hours += 12
                val time = hours * 3600 + minutes * 60 + seconds
                listsort += time
                timeMap += Pair(time, line)
            }
            for (time in listsort.sorted()) {
                it.write(timeMap[time]!!)
                it.newLine()
            }
        }
    } catch (e: NumberFormatException) {
        throw NumberFormatException("Неверный формат")
    }
}

/**
 * Сортировка адресов
 *
 * Средняя
 *
 * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
 * где они прописаны. Пример:
 *
 * Петров Иван - Железнодорожная 3
 * Сидоров Петр - Садовая 5
 * Иванов Алексей - Железнодорожная 7
 * Сидорова Мария - Садовая 5
 * Иванов Михаил - Железнодорожная 7
 *
 * Людей в городе может быть до миллиона.
 *
 * Вывести записи в выходной файл outputName,
 * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
 * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
 *
 * Железнодорожная 3 - Петров Иван
 * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
 * Садовая 5 - Сидоров Петр, Сидорова Мария
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortAddresses(inputName: String, outputName: String) {
    /**
     * Ресурсоёмкость: O(3 * N) = O(N);
     * Трудоёмкость: O(N ^ 2)
     */
    val listMap = mutableListOf<Pair<String, String>>()
    val addresses = mutableSetOf<Pair<String, Int>>()
    try {
        val file = File(inputName).bufferedReader().readLines()
        File(outputName).bufferedWriter().use {
            for (line in file) {
                val para = line.split(" - ")
                listMap += Pair(para[0], para[1])
                val number = para[1].split(" ")
                addresses += Pair(number[0], number[1].toInt())
            }
            val addressSort = addresses.sortedWith(Comparator { p1, p2 ->
                when {
                    p1.first > p2.first -> 1
                    ((p1.first == p2.first) && (p1.second > p2.second)) -> 1
                    ((p1.first == p2.first) && (p1.second == p2.second)) -> 0
                    ((p1.first == p2.first) && (p1.second < p2.second)) -> -1
                    else -> -1
                }
            })
            for (address in addressSort) {
                val address1 = "${address.first} ${address.second}"
                val listNames = mutableListOf<String>()
                for ((name, adr) in listMap)
                    if (adr == address1) listNames += name
                val nameString = StringBuilder()
                val sort = listNames.sorted()
                for (x in sort.indices) {
                    nameString.append(sort[x])
                    if (x != sort.size - 1) nameString.append(", ")
                }
                it.write("$address1 - $nameString")
                it.newLine()
            }
        }
    } catch (e: NumberFormatException) {
        throw NumberFormatException("Неверный формат")
    }
}

/**
 * Сортировка температур
 *
 * Средняя
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
 * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
 * Например:
 *
 * 24.7
 * -12.6
 * 121.3
 * -98.4
 * 99.5
 * -12.6
 * 11.0
 *
 * Количество строк в файле может достигать ста миллионов.
 * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
 * Повторяющиеся строки сохранить. Например:
 *
 * -98.4
 * -12.6
 * -12.6
 * 11.0
 * 24.7
 * 99.5
 * 121.3
 */
fun sortTemperatures(inputName: String, outputName: String) {
    /**
     * Ресурсоёмкость: O(N);
     * Трудоёмкость: O(N * logN)
     */
    val map = mutableMapOf<Double, Int>()
    try {
        val file = File(inputName).bufferedReader().readLines()
        File(outputName).bufferedWriter().use {
            for (line in file) {
                val temp = line.toDouble()
                require(temp in -273.0..500.0) { "Перебор" }
                if (temp in map.keys) {
                    val h = map[temp]!! + 1
                    map[temp] = h
                } else map[temp] = 1
            }
            while (map.isNotEmpty()) {
                val min = map.keys.min()!!
                for (x in 1..map[min]!!) {
                    it.write(min.toString())
                    it.newLine()
                }
                map.remove(min)
            }
        }
    } catch (e: NumberFormatException) {
        throw NumberFormatException("Неверный формат")
    }
}

/**
 * Сортировка последовательности
 *
 * Средняя
 * (Задача взята с сайта acmp.ru)
 *
 * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
 *
 * 1
 * 2
 * 3
 * 2
 * 3
 * 1
 * 2
 *
 * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
 * а если таких чисел несколько, то найти минимальное из них,
 * и после этого переместить все такие числа в конец заданной последовательности.
 * Порядок расположения остальных чисел должен остаться без изменения.
 *
 * 1
 * 3
 * 3
 * 1
 * 2
 * 2
 * 2
 */
fun sortSequence(inputName: String, outputName: String) {
    /**
     * Ресурсоёмкость: O(N);
     * Трудоёмкость: O(N)
     */
    val listMap = mutableMapOf<Int, Int>()
    val list = mutableListOf<Int>()
    try {
        val file = File(inputName).bufferedReader().readLines()
        File(outputName).bufferedWriter().use {
            for (element in file) {
                val first = element.toInt()
                list += first
                if (first in listMap.keys) {
                    val t = listMap[first]!! + 1
                    listMap[first] = t
                } else listMap[first] = 1
            }
            val max = listMap.values.max() ?: 0
            var maxPair = Pair(Int.MAX_VALUE, 0)
            for ((first, second) in listMap)
                if ((second == max) && (first <= maxPair.first))
                    maxPair = Pair(first, max)
            for (line in list)
                if (line != maxPair.first) {
                    it.write(line.toString())
                    it.newLine()
                }
            for (n in 0 until maxPair.second) {
                it.write(maxPair.first.toString())
                it.newLine()
            }
        }
    } catch (e: NumberFormatException) {
        throw NumberFormatException("Неверный формат")
    }
}

/**
 * Соединить два отсортированных массива в один
 *
 * Простая
 *
 * Задан отсортированный массив first и второй массив second,
 * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
 * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
 *
 * first = [4 9 15 20 28]
 * second = [null null null null null 1 3 9 13 18 23]
 *
 * Результат: second = [1 3 4 9 9 13 15 20 23 28]
 */
fun <T : Comparable<T>> mergeArrays(first: Array<T>, second: Array<T?>) {
    /**
     * Ресурсоёмкость: O(S);
     * Трудоёмкость: O(S)
     */
    val list = mutableListOf<T>()
    val size = second.size
    var li = 0
    var ri = first.size
    for (i in 0 until size) {
        if (li < first.size && (ri == size || first[li] <= second[ri]!!))
            list.add(first[li++])
        else list.add(second[ri++]!!)
    }
    for (element in 0 until list.size)
        second[element] = list[element]
}

