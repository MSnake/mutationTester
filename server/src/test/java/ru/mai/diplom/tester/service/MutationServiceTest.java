package ru.mai.diplom.tester.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mai.diplom.tester.BaseSpringBootApplicationTest;
import ru.mai.diplom.tester.db.model.MutationData;
import ru.mai.diplom.tester.db.model.SourceCodeData;
import ru.mai.diplom.tester.db.model.TestCodeData;
import ru.mai.diplom.tester.utils.DigestUtils;

/**
 * Тестирование сервиса для работы c мутированием исходного кода
 */
public class MutationServiceTest extends BaseSpringBootApplicationTest{

    @Autowired
    MutationService service;

    @Test
    public void ifRevertMutationTest(){
        String testIfString = "public void runTest(TestResultData testResultData){\n" +
                "        int a = 0;\n" +
                "        int b = 1;\n" +
                "        int c = 2;\n" +
                "        if (true && (a==b)) {\n" +
                "            // что то написать;\n" +
                "            a=b+1;\n" +
                "        }\n" +
                "        if (true && (a==b)) a=b+1;\n" +
                "        if (true && (a==b) &&\n" +
                "                (c < a)) a=b+1;\n" +
                "        c=0;\n" +
                "        if (true && (a==b) &&\n" +
                "                (c < a)) {\n" +
                "            a=b+1;\n" +
                "        }\n" +
                "    }";
        String  result = service.revertIf(testIfString);
        Assert.assertNotNull(result);
    }

}
