package ru.mai.diplom.tester.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

/**
 * Настройки мутационных преобразований
 */
@Getter
@Setter
@NoArgsConstructor
public class MutationOption {

    private MutationType type;

    private Set<Integer> rowNumbers;

    private Map<String, String> replaceOptions;

    public void putReplaceOption(String key, String value) {
        if (replaceOptions == null) {
            replaceOptions = new HashMap<>();
        }
        replaceOptions.put(key, value);
    }

    public void addRowNumber(Integer number) {
        if (rowNumbers == null) {
            rowNumbers = new HashSet<>();
        }
        rowNumbers.add(number);
    }

    public void addRowNumbers(List<Integer> numbers) {
        if (rowNumbers == null) {
            rowNumbers = new HashSet<>();
        }
        rowNumbers.addAll(numbers);
    }

}
