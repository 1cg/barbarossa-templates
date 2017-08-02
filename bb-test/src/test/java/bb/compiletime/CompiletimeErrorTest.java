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

        assertEquals(expectedMessages.size(), generator.getIssues().getIssues().size());
        for(int i = 0; i < expectedMessages.size(); i += 1) {
            assertEquals(generator.getIssues().getIssues().get(i).getMessage(), expectedMessages.get(i));
        }

    }

    @Test
    public void ParamsErrorTest() {
        BBTemplateGen generator = new BBTemplateGen();
        generator.generateCode("testing.tester",
                "<%@ params(String name, int age) %><%@ params(String name, int age) %>",
                "tester.bb.html");
        generator.generateCode("testing.tester",
                "<%@ section mySection %><%@ params(String name, int age) %><%@ end section %>",
                "tester.bb.html");

        List<String> expectedMessages = new ArrayList<>();
        expectedMessages.add("Invalid Params Directive: class cannot have 2 params directives");
        expectedMessages.add("Invalid Params Directive: class cannot have param directive within section");

        assertEquals(expectedMessages.size(), generator.getIssues().getIssues().size());
        for(int i = 0; i < expectedMessages.size(); i += 1) {
            assertEquals(generator.getIssues().getIssues().get(i).getMessage(), expectedMessages.get(i));
        }

    }

    @Test
    public void SectionErrorTest() {
        BBTemplateGen generator = new BBTemplateGen();
        generator.generateCode("testing.tester",
                "<%@ section mySection %><%@ extends bb.directives.ExtendsTesterTemplate %><%@ end section %>",
                "tester.bb.html");
        generator.generateCode("testing.tester",
                "<%@ section mySection %><%@ params(String name, int age) %><%@ end section %>",
                "tester.bb.html");
        generator.generateCode("testing.tester",
                "<%@ end section %>",
                "tester.bb.html");
//        generator.generateCode("testing.tester",
//                "<%@ section MySection %>",
//                "tester.bb.html");

        List<String> expectedMessages = new ArrayList<>();
        expectedMessages.add("Invalid Extends Directive: class cannot extend within section");
        expectedMessages.add("Invalid Params Directive: class cannot have param directive within section");
        expectedMessages.add("Invalid End Section Directive: section declaration does not exist");
        //expectedMessages.add("Reached end of file before parsing section: MySection");


        assertEquals(expectedMessages.size(), generator.getIssues().getIssues().size());
        for(int i = 0; i < expectedMessages.size(); i += 1) {
            assertEquals(generator.getIssues().getIssues().get(i).getMessage(), expectedMessages.get(i));
        }

    }

    @Test
    public void IsLayoutErrorTest() {
        BBTemplateGen generator = new BBTemplateGen();
        generator.generateCode("testing.tester",
                "<%@ content %><%@ content %>",
                "tester.bb.html");
        generator.generateCode("testing.tester",
                "<%@ section mySection %><%@ content %><%@ end section %>",
                "tester.bb.html");

        List<String> expectedMessages = new ArrayList<>();
        expectedMessages.add("Invalid Layout Instantiation: cannot have two layout instantiations");
        expectedMessages.add("Invalid Layout Instantiation: cannot instantiate layout within section");

        assertEquals(expectedMessages.size(), generator.getIssues().getIssues().size());
        for(int i = 0; i < expectedMessages.size(); i += 1) {
            assertEquals(generator.getIssues().getIssues().get(i).getMessage(), expectedMessages.get(i));
        }

    }

    @Test
    public void HasLayoutErrorTest() {
        BBTemplateGen generator = new BBTemplateGen();
        generator.generateCode("testing.tester",
                "<%@ layout directives.layouts.IsLayout%><%@ layout directives.layouts.IsLayout%>",
                "tester.bb.html");
        generator.generateCode("testing.tester",
                "<%@ section mySection %><%@ layout directives.layouts.IsLayout %><%@ end section %>",
                "tester.bb.html");

        List<String> expectedMessages = new ArrayList<>();
        expectedMessages.add("Invalid Layout Declaration: cannot have two layout declarations");
        expectedMessages.add("Invalid Layout Declaration: cannot declare layout within section");

        assertEquals(expectedMessages.size(), generator.getIssues().getIssues().size());
        for(int i = 0; i < expectedMessages.size(); i += 1) {
            assertEquals(generator.getIssues().getIssues().get(i).getMessage(), expectedMessages.get(i));
        }

    }

}
