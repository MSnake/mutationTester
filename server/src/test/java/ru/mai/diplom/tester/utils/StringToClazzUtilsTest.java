package ru.mai.diplom.tester.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.IOException;

public class StringToClazzUtilsTest {

    @Test
    public void loadClassFromStringTest(){
        try {
            Class newClass = StringToClazzUtils.load("package ru.mai.diplom.tester.utils;\n" +
                    "\n" +
                    "/**\n" +
                    " * Created by Alex on 17.02.2019.\n" +
                    " */\n" +
                    "public class Multiply {\n" +
                    "\n" +
                    "    public Long multiply(Long a, Long b) {\n" +
                    "        Long result = null;\n" +
                    "        if (a < b) result = a * b;\n" +
                    "        if (a > b) result = a + b;\n" +
                    "        return result;\n" +
                    "    }\n" +
                    "}");
            Assert.assertNotNull(newClass);
            Class testClass = StringToClazzUtils.load("package ru.mai.diplom.tester.utils;\n" +
                    "\n" +
                    "import org.junit.Assert;\n" +
                    "import org.junit.Test;\n" +
                    "\n" +
                    "/**\n" +
                    " * Created by Alex on 17.02.2019.\n" +
                    " */\n" +
                    "public class MultiplyTest {\n" +
                    "    @Test\n" +
                    "    public void multiply() throws Exception {\n" +
                    "        Multiply multiply = new Multiply();\n" +
                    "        Long result = multiply.multiply(55l, 2l);\n" +
                    "        Assert.assertEquals(2l, result.longValue());\n" +
                    "    }\n" +
                    "\n" +
                    "\n" +
                    "}");
            Assert.assertNotNull(newClass);

            Result result = JUnitCore.runClasses(testClass);
            if (!result.getFailures().isEmpty()) {
                for (Failure failure : result.getFailures()) {
                    System.out.println("Тест не пройден. Детали: " + failure.toString());
                }
            } else System.out.println("Тест успешно пройден.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
