package ru.ancap.commons.iterable;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class IterableToArrayTest {

    @Test
    public void test() {
        assertArrayEquals(
            IterableToArray.deepRecursiveReflective(List.of(
                List.of(500, 400, 300),
                List.of(
                    123,
                    List.of(888, 888)
                ),
                8
            )),
            IterableToArray.deepRecursiveReflective(new ArrayList<>() {{
                add(new ArrayList<>() {{ add(500); add(400); add(300); }});
                add(new ArrayList<>() {{
                    add(123);
                    add(new ArrayList<>() {{
                        add(888);
                        add(888);
                    }});
                }});
                add(8);
            }})
        );
    }

}
