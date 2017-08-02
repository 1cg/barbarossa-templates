package bb.compiletime;

import bb.codegen.BBTemplateGen;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by hkalidhindi on 8/2/2017.
 */
public class CompiletimeErrorTest {

    @Test
    public void ExtendsErrorTest() {
        BBTemplateGen generator = new BBTemplateGen();
        generator.generateCode("testing.tester",
                "<%@ extends bb.directives.ExtendsTesterTemplate %><%@ extends bb.directives.ExtendsTesterTemplate %>",
                "tester.bb.html");
        generator.generateCode("testing.tester",
                "<%@ section mySection %><%@ extends bb.directives.ExtendsTesterTemplate %><%@ extends bb.directives.ExtendsTesterTemplate %><%@ end section %>",
                "tester.bb.html");

        List<String> expectedMessages = new ArrayList<>();
        expectedMessages.add("Invalid Extends Directive: class cannot extend 2 classes");
        expectedMessages.add("Invalid Extends Directive: class cannot extend within section");
        expectedMessages.add("Invalid Extends Directive: class cannot extend within section");

        assertEquals(generator.getIssues().getIssues().size(), expectedMessages.size());
        for(int i = 0; i < expectedMessages.size(); i += 1) {
            assertEquals(generator.getIssues().getIssues().get(i).getMessage(), expectedMessages.get(i));
        }

    }
}
