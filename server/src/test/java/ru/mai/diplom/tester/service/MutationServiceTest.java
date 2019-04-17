package ru.mai.diplom.tester.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mai.diplom.tester.BaseSpringBootApplicationTest;
import ru.mai.diplom.tester.model.MutationOption;
import ru.mai.diplom.tester.model.MutationType;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Тестирование сервиса для работы c мутированием исходного кода
 */
public class MutationServiceTest extends BaseSpringBootApplicationTest {

    @Autowired
    MutationService service;

    private String testSourceCode = null;

    @Before
    public void setUp(){
        testSourceCode = "public void runTest(TestResultData testResultData){\n" +
                "        int a = 0;\n" +
                "        int b = 123;\n" +
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
    }

    @Test
    public void ifRevertMutationTest() {
        String result = service.revertIf(testSourceCode);
        Assert.assertNotNull(result);
    }

    @Test
    public void removeLinesTest() {
        Set<Integer> lineNumbers = new HashSet<>(Arrays.asList(16, 17));
        String result = service.removeLines(testSourceCode, lineNumbers);
        Assert.assertNotNull(result);
    }

    @Test
    public void replaceCharactersTest() {

        Map<String, String> replaceOptions = new HashMap<>();
        replaceOptions.put("1", "2");
        replaceOptions.put("2", "3");
        replaceOptions.put("3", "1");
        String result = service.replaceCharacters(testSourceCode, replaceOptions);
        Assert.assertNotNull(result);
    }

    @Test
    public void createMutationCodeFromSourceTest() {
        List<MutationOption> options = Arrays.asList(createRevertIfMO(), createReplaceCharsMO(),createRemoveRowsMO());
        String result = service.createMutationCodeFromSource(testSourceCode, options);
        Assert.assertNotNull(result);
    }

    private MutationOption createRevertIfMO(){
        MutationOption result = new MutationOption();
        result.setType(MutationType.REVERT_IF);
        return result;
    }

    private MutationOption createReplaceCharsMO(){
        MutationOption result = new MutationOption();
        result.setType(MutationType.REPLACE_CHARS);
        Map<String, String> replaceOptions = new HashMap<>();
        replaceOptions.put("1", "2");
        replaceOptions.put("2", "3");
        replaceOptions.put("3", "1");
        result.setReplaceOptions(replaceOptions);
        return result;
    }

    private MutationOption createRemoveRowsMO(){
        MutationOption result = new MutationOption();
        result.setType(MutationType.REMOVE_ROWS);
        result.setRowNumbers(new HashSet<>(Arrays.asList(16, 17)));
        return result;
    }


}
