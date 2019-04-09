package ru.mai.diplom.tester.service;

import liquibase.util.MD5Util;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mai.diplom.tester.db.model.MutationData;
import ru.mai.diplom.tester.db.model.SourceCodeData;
import ru.mai.diplom.tester.BaseSpringBootApplicationTest;
import ru.mai.diplom.tester.db.model.TestCodeData;
import ru.mai.diplom.tester.utils.DigestUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Тестирование сервиса для работы с информацией о исходном коде
 */
public class SourceCodeServiceTest extends BaseSpringBootApplicationTest{

    @Autowired
    SourceCodeService service;

    @Test
    public void saveTest(){
        SourceCodeData data = service.save(createSourceCodeDataTest());
        Assert.assertNotNull(data);
    }

    private SourceCodeData createSourceCodeDataTest(){
        SourceCodeData result = new SourceCodeData();
        result.setCodeText("public class Multiply {\n" +
                "\n" +
                "    public Long multiply(Long a, Long b) {\n" +
                "        return  a*b;\n" +
                "    }\n" +
                "}");
        result.setMd5Data(DigestUtils.getMd5(result.getCodeText()));
        result.addMutationData(createMutationDataTest());
        result.addTestCodeData(createTestCodeDataTest());
        return result;
    }

    private TestCodeData createTestCodeDataTest() {
        TestCodeData result = new TestCodeData();
        result.setCodeText("public class MultiplyTest {\n" +
                "    @Test\n" +
                "    public void multiply() throws Exception {\n" +
                "        Multiply multiply = new Multiply();\n" +
                "        Long result = multiply.multiply(1l, 2l);\n" +
                "        Assert.assertEquals(2l, result.longValue());\n" +
                "    }\n" +
                "\n" +
                "}");
        result.setMd5Data(DigestUtils.getMd5(result.getCodeText()));
        return result;
    }

    private MutationData createMutationDataTest() {
        MutationData result = new MutationData();
        result.setCodeText("public class Multiply {\n" +
                "\n" +
                "    public Long multiply(Long a, Long b) {\n" +
                "        return  a*b;\n" +
                "    }\n" +
                "}");
        result.setMd5Data(DigestUtils.getMd5(result.getCodeText()));
        result.setJsonData("{\"isMutation\": true, \"link\": \"http://jsonview.com\"}");
        return result;
    }
}
