@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson7

import lesson5.Graph
import lesson5.Path
import lesson6.knapsack.Fill
import lesson6.knapsack.Item
import lesson7.heuristicKnapsack.AnnealingKnapsack
import lesson7.heuristicKnapsack.HeuristicsKnapsack

// Примечание: в этом уроке достаточно решить одну задачу

/**
 * Решить задачу о ранце (см. урок 6) любым эвристическим методом
 *
 * Очень сложная
 *
 * load - общая вместимость ранца, items - список предметов
 *
 * Используйте parameters для передачи дополнительных параметров алгоритма
 * (не забудьте изменить тесты так, чтобы они передавали эти параметры)
 *
 * Если я не ошибаюсь, то:
 * Трудоёмкость: O(items.size * chromosomeNumber * generationNumber);
 * Ресурсоёмкость: O(items.size * chromosomeNumber)
 * Вероятность найти лучший случай >= 90 %
 */
fun fillKnapsackHeuristicsGenetic(
    load: Int, items: List<Item>,
    chromosomeNumber: Int, generationNumber: Int
): Fill = HeuristicsKnapsack(load, items, chromosomeNumber, generationNumber).findTheBest()


/**
 * Трудоёмкость: O(items.size * iterationNumber);
 * Ресурсоёмкость: O(2 * items.size) = O(items.size)
 * Вероятность найти лучший случай >= 80 %
 */
fun fillKnapsackHeuristicsAnnealing(
    load: Int, items: List<Item>,
    startTemperature: Int,
    iterationNumber: Int
): Fill = AnnealingKnapsack(load, items, startTemperature, iterationNumber).findTheBest()


/**
 * Решить задачу коммивояжёра (см. урок 5) методом колонии муравьёв
 * или любым другим эвристическим методом, кроме генетического и имитации отжига
 * (этими двумя методами задача уже решена в под-пакетах annealing & genetic).
 *
 * Очень сложная
 *
 * Граф передаётся через получатель метода
 *
 * Используйте parameters для передачи дополнительных параметров алгоритма
 * (не забудьте изменить тесты так, чтобы они передавали эти параметры)
 */
fun Graph.findVoyagingPathHeuristics(vararg parameters: Any): Path {
    TODO()
}

