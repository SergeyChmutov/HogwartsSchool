package ru.hogwarts.school.constants;

import ru.hogwarts.school.model.Student;

public class Constants {
    public static final String EMPTY_STRING = "";
    public static final String BLANK_STRING = " ";

    public static final String ID_FIELD = "id";
    public static final String NAME_FIELD = "name";
    public static final String AGE_FIELD = "age";
    public static final String COLOR_FIELD = "color";

    public static final String STUDENT_NAME_RON = "Ron";
    public static final String STUDENT_NAME_GARRY = "Garry";
    public static final String STUDENT_NAME_HERMIONE = "Hermione";

    public static final String FACULTY_NAME_GRYFFINDOR = "Gryffindor";
    public static final String FACULTY_NAME_HUFFLEPUFF = "Hufflepuff";
    public static final String FACULTY_NAME_RAVENCLAW = "Ravenclaw";
    public static final String FACULTY_NAME_SLYTHERIN = "Slytherin";

    public static final String COLOR_RED = "Red";
    public static final String COLOR_GOLD = "Gold";
    public static final String COLOR_GREEN = "Green";

    public static final int AGE_NEGATIVE = -1;
    public static final int AGE_ZERO = 0;
    public static final int AGE_NINE = 9;
    public static final int AGE_TEN = 10;
    public static final int AGE_TWELVE = 12;
    public static final int AGE_THIRTEEN = 13;

    public static final Long ID_NEGATIVE = -1L;
    public static final Long ID_ZERO = 0L;
    public static final Long ID_ONE = 1L;
    public static final Long ID_TWO = 2L;

    public static final Student STUDENT_RON = new Student(ID_ONE, STUDENT_NAME_RON, AGE_TEN);
}
