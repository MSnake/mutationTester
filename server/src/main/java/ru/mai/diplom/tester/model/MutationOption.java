package ru.mai.diplom.tester.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * Настройки мутационных преобразований
 */
@Getter
@Setter
@NoArgsConstructor
public class MutationOption {

    private MutationType type;

    private Map<String, String> options;

}
