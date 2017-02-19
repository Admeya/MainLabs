package Lab1_var3;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.FileNotFoundException;


/**
 * @author Быкова Ирина
 * @version 0.25
 *          <p>
 *          Необходимо разработать программу, которая получает на вход список ресурсов,
 *          содержащих набор чисел и считает сумму всех положительных четных.
 *          Каждый ресурс должен быть обработан в отдельном потоке, набор должен содержать лишь числа,
 *          унарный оператор "-" и пробелы. Общая сумма должна отображаться на экране и изменяться в
 *          режиме реального времени. Все ошибки должны быть корректно обработаны, все API покрыто модульными тестами
 *          <p>
 *          По результатам вычислений полученная сумма должна равняться 1432
 */
public class Main_lab1_var3 {
    public static volatile boolean isInterrupt = false;

    static Logger logger = Logger.getLogger(Main_lab1_var3.class);
    static {
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
    }

    public static void main(final String args[]) throws InterruptedException, FileNotFoundException {
        final File[] setOfFile = getResourcesNamesAndCheck(args);
        if (!isInterrupt) {

            for (int i = 0; i < args.length; i++) {
                final int finalI = i;
                Thread thr = new Thread(new Runnable() {
                    public void run() {
                        logger.trace(new OperationsOnResources(setOfFile[finalI]));
                    }
                });
                System.out.println(thr);
                thr.start();
            }

        }
    }

    /**
     * На этапе извлечения переданных параметров осуществляется проверка на правильность пути к файлу в файловой системе
     *
     * @param args передаются полученные ресурсы
     * @throws FileNotFoundException При получении списка ресурсов сразу проверяется файл на существование
     */
    public static File[] getResourcesNamesAndCheck(String args[]) {
        String fileNameIn = null;
        File[] setOfFiles = new File[args.length];

        for (int i = 0; i < args.length; i++) {
            fileNameIn = args[i];
            File file = new File(fileNameIn);
            try {
                if (!file.exists()) {
                    setOfFiles = null;
                    isInterrupt = true;
                    throw new FileNotFoundException("Please, check input parameters, this file is not found " + file.getAbsolutePath());

                } else {
                    setOfFiles[i] = file;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (isInterrupt) {
                    break;
                }
            }
        }
        return setOfFiles;
    }
}