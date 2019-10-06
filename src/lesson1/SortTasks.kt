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
    val listsort = mutableListOf<Int>()
    val timeMap = mutableMapOf<Int, String>()
    try {
        val result = File(outputName).bufferedWriter()
        val file = File(inputName).bufferedReader().readLines()
        for (line in file) {
            val element = line.split(" ")
            val timelist = element[0].split(":")
            var hours = timelist[0].toInt()
            val minutes = timelist[1].toInt()
            val seconds = timelist[2].toInt()
            require(!((hours !in 0..12) || (minutes !in 0..59) || (seconds !in 0..59))) { "Неверный формат времени" }
            if (hours == 12) hours -= 12
            if (element[1] == "PM") hours += 12
            val time = hours * 3600 + minutes * 60 + seconds
            listsort += time
            timeMap += Pair(time, line)
        }
        for (time in listsort.sorted()) {
            result.write(timeMap[time]!!)
            result.newLine()
        }
        result.close()
    } catch (e: Exception) {
        throw IllegalArgumentException("Неверный формат")
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
    val listAdress = mutableSetOf<String>()
    val listMap = mutableListOf<Pair<String, String>>()
    try {
        val file = File(inputName).bufferedReader().readLines()
        val result = File(outputName).bufferedWriter()
        for (line in file) {
            val para = line.split(" - ")
            listAdress += para[1]
            listMap += Pair(para[0], para[1])
        }
        val street = mutableSetOf<String>()
        val spisokStreet = mutableSetOf<String>()
        for (adress in listAdress) street += adress.split(" ")[0]
        for (str in street.sorted()) {
            val numberList = mutableSetOf<Int>()
            for (line in listAdress) if (str == line.split(" ")[0])
                numberList += line.split(" ")[1].toInt()
            for (number in numberList.sorted()) {
                spisokStreet += "$str $number"
            }
        }
        for (adress in spisokStreet) {
            val listNames = mutableListOf<String>()
            for ((name, adr) in listMap) {
                if (adr == adress) listNames += name
            }
            val nameString = StringBuilder()
            val sort = listNames.sorted()
            sort.indices.forEach { x ->
                nameString.append(sort[x])
                if (x != sort.size - 1) nameString.append(", ")
            }
            result.write("$adress - $nameString")
            result.newLine()
        }
        result.close()
    } catch (e: Exception) {
        throw IllegalArgumentException("Неверный формат")
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
    val list = mutableListOf<Double>()
    try {
        val file = File(inputName).bufferedReader().readLines()
        val result = File(outputName).bufferedWriter()
        for (line in file) {
            val temp = line.toDouble()
            require(temp in -273.0..500.0) { "Перебор" }
            list += temp
        }
        for (temp in list.sorted()) {
            result.write(temp.toString())
            result.newLine()
        }
        result.close()
    } catch (e: Exception) {
        throw IllegalArgumentException("Неверный формат")
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
    val listMap = mutableMapOf<Int, Int>()
    val list = mutableListOf<Int>()
    try {
        val file = File(inputName).bufferedReader().readLines()
        val result = File(outputName).bufferedWriter()
        for (line in file) {
            val number = line.toInt()
            list += number
        }
        for (x in 0 until list.size) {
            val first = list[x]
            if (first !in listMap.keys) {
                var t = 0
                for (y in 0 until list.size) if (first == list[y]) t++
                listMap += Pair(first, t)
            }
        }
        val max = listMap.values.max() ?: 0
        var maxPair = Pair(0, 0)
        var second = Int.MAX_VALUE
        for ((first, t) in listMap) if ((t == max) && (first <= second)) {
            second = first
            maxPair = Pair(second, max)
        }
        for (line in list) if (line != maxPair.first) {
            result.write(line.toString())
            result.newLine()
        }
        for (n in 0 until maxPair.second) {
            result.write(maxPair.first.toString())
            result.newLine()
        }
        result.close()
    } catch (e: Exception) {
        throw IllegalArgumentException("Неверный формат")
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
    val size = first.size
    for (x in 0 until size) {
        if (second[x] == null) second[x] = first[x]
    }
    second.sort()
}

