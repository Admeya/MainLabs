package Lab1_var1;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * Выполняются операции по подсчету суммы, проверки на корректность полученных значений,
 * проверки на четность и неотрицательность *
 */
public class OperationsOnResources {
    static Logger logger = Logger.getLogger(OperationsOnResources.class);
    static Object lock = new Object();

    public static volatile int sum = 0;
    public static Map<String, Integer> map = new ConcurrentHashMap<String, Integer>();

    File file = null;

    public OperationsOnResources(File file) {
        this.file = file;
        readSymbolAndCountSymbols();
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
                                // int nextNumber = isSuitable(wordForAnalisys);
                                countSameWords(wordForAnalisys);
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
        for (Map.Entry entr : map.entrySet()) {
            if (entr.getKey().equals(nextWord)) {
                val = (Integer) entr.getValue();
                val++;
            } else {
                val = 1;
            }
        }
        map.put(nextWord, val);
        logger.trace(nextWord +" here in "+ val + " time ");
    }

    /**
     * Проверка условия "Число, которое войдет в сумму должно быть четным и положительным"
     *
     * @param number передается строка, преобразовывается в число и проверяется на нужное условие
     * @return num = 0 если число не подходит под условие и приведенная к типу int строка, если условие пройдено
     */
    public static int isSuitable(String number) {
        int num = 0;
        num = Integer.parseInt(number);

        if ((num % 2 == 0) && (num > 0)) {
            logger.trace("Find positive and even number! This is number " + num);
            return num;
        } else {
            logger.trace("Number " + num + " thread is unsuitable for this example ");
            num = 0;
        }
        return num;
    }
}