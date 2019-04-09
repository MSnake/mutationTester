package ru.mai.diplom.tester.db.types;

/**
 * Пользовательский тип для поддержки postgresql типа jsonb.
 **/
public class JsonStringUserType extends OtherStringUserType {

    public JsonStringUserType() {
    }

    public int[] sqlTypes() {
        return new int[]{2000};
    }
}

