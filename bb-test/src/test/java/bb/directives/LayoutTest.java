package bb.directives;

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
}
