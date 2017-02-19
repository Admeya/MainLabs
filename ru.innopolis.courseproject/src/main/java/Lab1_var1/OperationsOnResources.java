package Lab1_var1;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * Выполняются операции по подсчету суммы, проверки на корректность полученных значений,
 * проверки на четность и неотрицательность *
 */
public class OperationsOnResources {
    public static volatile Map<String, Integer> map = new ConcurrentHashMap<String, Integer>();
    static Logger logger = Logger.getLogger(OperationsOnResources.class);
    static Object lock = new Object();
    File file = null;

    public OperationsOnResources(File file) {
        this.file = file;
        readSymbolAndCountSymbols();
    }

    /**
     * Проверка переданного значения на валидность
     *
     * @param number Передается считанный из входного потока набор символов
     * @return true если значение подходит указанной маске (положительные и отрицательные целые числа)
     */
    public static boolean isWordCorrect(String number) {
        Pattern p = Pattern.compile("[^A-Za-z]+");
        if (!p.matcher(number).matches()) {
            Main_lab1.isInterrupt = true;
        } else
            return true;

        return false;
    }

    public static void countSameWords(String nextWord) {
        int val = 0;
        if (!nextWord.equals("")) {
            if (map.containsKey(nextWord)) {
                val = map.get(nextWord);
                ++val;
            } else {
                val = 1;
            }
            map.put(nextWord, val);
            logger.trace(nextWord + "---" + val);
        }
    }

    /**
     * Проверка условия "Число, которое войдет в сумму должно быть четным и положительным"
     *
     * @param word передается строка, которая проверяется, состоит ли из русских символов
     * @return пустая строка, если строка не подходит под условие. Либо если это слово из кириллицы, то оно возвращается
     */
    public static String isSuitable(String word) {
        Pattern p = Pattern.compile("[А-Яа-я]+");
        if (p.matcher(word).matches()) {
            return word;
        }
        return "";
    }

    @Override
    public String toString() {
        return "OperationsOnResources{" + ", file=" + file + '}';
    }

    /**
     * Подсчет суммы чисел со всех потоков согласно условию
     *
     * @throws InterruptedException если число не пройдено на валидность
     */
    private void readSymbolAndCountSymbols() {
        logger.trace(this.toString());
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                synchronized (lock) {
                    if (Main_lab1.isInterrupt)
                        break;
                    else {
                        if (sc.hasNext()) {
                            String wordForAnalisys = sc.next();
                            if (isWordCorrect(wordForAnalisys)) {
                                String nextWord = isSuitable(wordForAnalisys);
                                countSameWords(nextWord);
                            } else {
                                try {
                                    Main_lab1.isInterrupt = true;
                                    throw new InterruptedException("This is not correct word '" + wordForAnalisys + "'");
                                } catch (InterruptedException e) {
                                    logger.error(e);
                                }
                            }
                        }
                    }
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}