package lesson1;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     * <p>
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     * <p>
     * Пример:
     * <p>
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     * <p>
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     * <p>
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) {
        // Трудоемкость - О(n * log(n)) ; Ресурсоемкость - О(n)
        class Time implements Comparable<Time> {
            private int h, m, s;

            public Time(String str) throws IllegalArgumentException {
                String[] strTime = str.split("[: ]");
                try {
                    h = Integer.parseInt(strTime[0]);
                    m = Integer.parseInt(strTime[1]);
                    s = Integer.parseInt(strTime[2]);
                    if (h > 12 || m > 59 || s > 59 || h < 0 || m < 0 || s < 0 || (!strTime[3].equals("PM") && !strTime[3].equals("AM"))) {
                        throw new IllegalArgumentException();
                    }
                    h += (strTime[3].equals("PM") ? 12 : 0);
                    if (h % 12 == 0) {
                        h -= 12;
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException();
                }
            }

            @Override
            public int compareTo(@NotNull Time date) {
                if (h != date.h) {
                    return h - date.h;
                } else if (m != date.m) {
                    return m - date.m;
                } else {
                    return s - date.s;
                }
            }

            @Override
            public String toString() {
                return String.format("%02d:%02d:%02d ", h % 12 == 0 ? 12 : h % 12, m, s) + (h < 12 ? "AM" : "PM");
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(inputName)))) {
            try (PrintWriter writer = new PrintWriter(new File(outputName))) {
                for (Time time : reader.lines().map(Time::new).sorted().collect(Collectors.toList())) {
                    writer.println(time);
                }
            } catch (IOException e) {
            }
        } catch (IOException e) {
        }
    }

    /**
     * Сортировка адресов
     * <p>
     * Средняя
     * <p>
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     * <p>
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     * <p>
     * Людей в городе может быть до миллиона.
     * <p>
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     * <p>
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка температур
     * <p>
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     * <p>
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     * <p>
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     * <p>
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    static public void sortTemperatures(String inputName, String outputName) {
        // Трудоемкость - О(n) ; Ресурсоемкость - О(HIGH_TEMPERATURE - LOW_TEMPERATURE)
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(inputName)))) {
            try (PrintWriter writer = new PrintWriter(new File(outputName))) {
                float LOW_TEMPERATURE = -273.0f;
                float HIGH_TEMPERATURE = 500.0f;
                int[] count = new int[(int) (HIGH_TEMPERATURE - LOW_TEMPERATURE) * 10 + 1];
                reader.lines().forEach(s -> count[Math.round((Float.parseFloat(s) - LOW_TEMPERATURE) * 10)]++);
                for (int i = 0; i < count.length; i++) {
                    for (int j = 0; j < count[i]; j++) {
                        writer.println(String.format("%.1f", i / 10.0 + LOW_TEMPERATURE).replace(",", "."));
                    }
                }
            } catch (IOException e) {
            }
        } catch (IOException e) {
        }
    }

    /**
     * Сортировка последовательности
     * <p>
     * Средняя
     * (Задача взята с сайта acmp.ru)
     * <p>
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     * <p>
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     * <p>
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     * <p>
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Соединить два отсортированных массива в один
     * <p>
     * Простая
     * <p>
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     * <p>
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     * <p>
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }
}
