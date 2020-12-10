package lesson7;

import kotlin.NotImplementedError;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
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
     */
    public static String longestCommonSubSequence(String first, String second) {
        // Трудоемкость - О(|first|*|second|), Ресурсоемкость - О(|first|*|second|)
        int[][] lcs = new int[first.length() + 1][second.length() + 1];
        //Тип 0 - дописали символ
        //Тип 1 - символ в первой строке не входит в ответ
        //Тип 2 - символ во второй строке не входит в ответ
        int[][] type = new int[first.length() + 1][second.length() + 1];
        for (int i = 1; i <= first.length(); i++) {
            for (int j = 1; j <= second.length(); j++) {
                if (first.charAt(i - 1) == second.charAt(j - 1)) {
                    lcs[i][j] = lcs[i - 1][j - 1] + 1;
                    type[i][j] = 0;
                }
                if (lcs[i][j] <= lcs[i - 1][j]) {
                    lcs[i][j] = lcs[i - 1][j];
                    type[i][j] = 1;
                }
                if (lcs[i][j] <= lcs[i][j - 1]) {
                    lcs[i][j] = lcs[i][j - 1];
                    type[i][j] = 2;
                }
            }
        }
        int x = first.length(), y = second.length();
        StringBuilder answer = new StringBuilder();
        while (x != 0 && y != 0) {
            switch (type[x][y]) {
                case 0:
                    answer.append(first.charAt(x - 1));
                    x--;
                    y--;
                    break;
                case 1:
                    x--;
                    break;
                case 2:
                    y--;
                    break;
            }
        }
        answer.reverse();
        return answer.toString();
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
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        // Трудоемкость - О(n^2), Ресурсоемкость - О(n)
        int[] dp = new int[list.size()];
        int[] last = new int[list.size()];
        int length = 0, end = -1;
        for (int i = 0; i < list.size(); i++) {
            dp[i] = 1;
            last[i] = -1;
            for (int j = 0; j < i; j++) {
                if (list.get(j) < list.get(i) && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    last[i] = j;
                }
            }
            if (dp[i] > length) {
                length = dp[i];
                end = i;
            }
        }
        List<Integer> answer = new ArrayList<>();
        while (end != -1) {
            answer.add(list.get(end));
            end = last[end];
        }
        Collections.reverse(answer);
        return answer;
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
    public static int shortestPathOnField(String inputName) {
        throw new NotImplementedError();
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
