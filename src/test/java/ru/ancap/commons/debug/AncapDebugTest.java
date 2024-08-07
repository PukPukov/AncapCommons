package ru.ancap.commons.debug;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.ancap.commons.random.Weight;
import ru.ancap.commons.random.Weights;

import java.util.List;

import static ru.ancap.commons.debug.AncapDebug.*;

@HandTest
public class AncapDebugTest {
    
    @Test // for coverage
    public void test() {
        debug((Object) null);
        debug((Object[]) null);
        debug();
        
        debug(named("first", List.of("a", "b", 3)), new Object[]{"foo", "bar", new int[]{3, 4, 5}});
        debugArray(new Object[]{"foo", "bar", new int[]{3, 4, 5}});
        
        debug(true, false);
        debug(inline(true, false));
        
        debug(name("fizz"), true, false);
        debug(new Weights<>(List.of(new Weight<>(null, 20))));
        
        debug(name("postformat"), postformat(
            "<1> \"ru.ancap.eggs.quest.QuestController$QuestProgression{  " +
            "QuestProgression[filledItems=[TaskProgression[task=Task[grade=" +
            "ResourceGrade(id=epic, nameFormat=StyleImpl{color=null, obfu" +
            "scated=not_set, bold=not_set, strikethrough={not_set}, underlined" +
            "=not_set, italic=not_set, clickEvent=null, hoverEvent=null, " +
            "insertion=null, font=null}), material=DIAMOND], amount=56], " +
            "TaskProgression[task=Task[grade=ResourceGrade(id=epic, nameFormat=StyleImpl{color=null, " +
            "obfuscated=not_set, bold=not_set, strikethrough=not_set, underlined=not_set, italic=not_set, " +
            "clickEvent=null, hoverEvent=null, insertion=null, font=null}), material=DIAMOND], amount=59], " +
            "TaskProgression[task=Task[grade=ResourceGrade(id=rare, nameFormat=StyleImpl{color=null, obfuscated=not_set, " +
            "bold=not_set, strikethrough=not_set, underlined=not_set, italic=not_set, clickEvent=null, " +
            "hoverEvent=null, insertion=null, font=null}), material=IRON_INGOT], amount=61]], requiredItems=18]  " +
            "}\""
        ));
        
        Assertions.assertTrue(testThrough());
    }
    
    private static boolean testThrough() {
        return debugThrough(true, false);
    }
    
}