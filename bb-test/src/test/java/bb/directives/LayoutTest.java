package bb.directives;

import bb.BBTemplates;
import bb.runtime.ILayout;
import org.junit.Test;
import directives.layouts.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by hkalidhindi on 7/17/2017.
 */
public class LayoutTest {

    @Test
    public void layoutTestWithoutContent() {
        assertEquals("HeaderFooter", HasLayout.render());
    }

    @Test
    public void layoutTestWithContent() {
        assertEquals("HeaderContentFooter", HasLayoutAndContent1.render());
        assertEquals("HeaderContentFooter", HasLayoutAndContent2.render());
    }

    @Test
    public void NestedLayoutTestWithContent() {
        assertEquals("HeaderH2ContentF2Footer", HasNestedLayout.render());
    }

    @Test
    public void PlainOverrideLayoutTest() {
        ILayout lo = IsLayout.asLayout();
        assertEquals("HeaderPlainFooter", PlainFile.render(lo));
    }

    @Test
    public void PlainDefaultLayoutTest() {
        ILayout lo = IsLayout.asLayout();
        BBTemplates.setDefaultTemplate(lo);
        assertEquals("HeaderPlainFooter", PlainFile.render());
        BBTemplates.setDefaultTemplate("directives", IsLayout3.asLayout());
        assertEquals("3Plain4", PlainFile.render());
    }

    @Test
    public void LayoutPrecedenceTest() {
        assertEquals("HeaderH2ContentF2Footer", HasNestedLayout.render());
    }
}
