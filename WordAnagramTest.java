import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

class WordAnagramTest {

    List<String> dict;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        String[] dictArray = {"go", "bat", "me", "eat",
                "goal", "boy", "run"} ;
        dict = Arrays.asList(dictArray);
    }

    @org.junit.jupiter.api.Test
    void check() {
        HashSet<Character> set = new HashSet<>();
        set.add('E');
        set.add('o');
        set.add('b');
        set.add('A');
        set.add('m');
        set.add('g');
        set.add('l');

        WordAnagram wa = new WordAnagram(dict);
        List<String> res = wa.check(set);
        assertThat(res, containsInAnyOrder("go", "goal", "me"));
    }
}