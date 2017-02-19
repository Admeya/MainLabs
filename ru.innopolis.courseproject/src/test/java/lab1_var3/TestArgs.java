package lab1_var3;


import Lab1_var3.Main_lab1_var3;
import Lab1_var3.OperationsOnResources;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * Created by Ирина on 09.02.2017.
 */
public class TestArgs {

    @Test
    public void allArgumentsIsTrue() {
        String[] trueMas = {"src/main/resources/inp4.txt", "src/main/resources/inp5.txt", "src/main/resources/inp6.txt", "src/main/resources/inp1.txt", "src/main/resources/inp2.txt", "src/main/resources/inp6.txt"};
        File[] fileOfMas = Main_lab1_var3.getResourcesNamesAndCheck(trueMas);

        Assert.assertNotNull(fileOfMas);
    }

    @Test
    public void oneArgumentsIsFalse() {
        String[] trueMas = {"src/main/resources/inp4.txt", "/src/main/resources/inp5.txt", "src/main/resources/inp6.txt", "src/main/resources/inp1.txt", "src/main/resources/inp2.txt", "src/main/resources/inp6.txt"};
        File[] fileOfMas = Main_lab1_var3.getResourcesNamesAndCheck(trueMas);

        Assert.assertNull(fileOfMas);
    }

    @Test
    public void countSumOddAndPositive() {
        String[] str = {"2", "4", "16", "20", "1200"};
        for (int i = 0; i < str.length; i++) {
            Assert.assertEquals(Integer.parseInt(str[i]), OperationsOnResources.isSuitable(str[i]));
        }
    }

    @Test
    public void countSumNonSuitable() {
        String[] str = {"1", "-5", "9", "11", "95"};
        for (int i = 0; i < str.length; i++) {
            Assert.assertNotEquals(Integer.parseInt(str[i]), OperationsOnResources.isSuitable(str[i]));
        }
    }

    @Test
    public void TrueSum() {
        Assert.assertEquals(OperationsOnResources.countingSum(12, 1000), 1012);
    }

    @Test
    public void isCorrectNumberFalseTest() {
        Assert.assertFalse(OperationsOnResources.isNumberCorrect("--12"));
        Assert.assertFalse(OperationsOnResources.isNumberCorrect("12--"));
        Assert.assertFalse(OperationsOnResources.isNumberCorrect("12/1"));
        Assert.assertFalse(OperationsOnResources.isNumberCorrect("1.2"));
        Assert.assertFalse(OperationsOnResources.isNumberCorrect("12."));
        Assert.assertFalse(OperationsOnResources.isNumberCorrect("12 000"));
    }

    @Test
    public void isCorrectNumberTrueTest() {
        Assert.assertTrue("IncorrectNumber 12", OperationsOnResources.isNumberCorrect("12"));
        Assert.assertTrue("IncorrectNumber -15", OperationsOnResources.isNumberCorrect("-15"));
        Assert.assertTrue("IncorrectNumber 10000", OperationsOnResources.isNumberCorrect("10000"));
        Assert.assertTrue("IncorrectNumber 000", OperationsOnResources.isNumberCorrect("000"));
    }

    @Test(expected = FileNotFoundException.class)
    public void throwsFileNotFoundExceptionWithMessage() {
        String[] args = {"/src/main/resources/inp5.txt"};
        Throwable thrown = catchThrowable(() -> {
            Main_lab1_var3.getResourcesNamesAndCheck(args);
        });

        assertThat(thrown).isInstanceOf(FileNotFoundException.class);
        assertThat(thrown.getMessage()).isNotBlank();
        assertThat(thrown.getMessage()).contains("Please, check input parameters, this file is not found " + new File(args[0]).getAbsolutePath());
    }

// JUNIT5
// @Test
//    public void testCreateTempFile() throws IOException {
//        Path tmpDir = Files.createTempDirectory("tmp");
//        tmpDir.toFile().delete();
//        Throwable thrown = assertThrows(IOException.class, () -> {
//            Files.createTempFile(tmpDir, "test", ".txt");
//        });
//        assertNotNull(thrown.getMessage());
//        // дальше идёт какой-то другой код
//        // в нём тоже может появиться неожиданный IOException
//        // если это случится -- тест упадёт
//    }

}
