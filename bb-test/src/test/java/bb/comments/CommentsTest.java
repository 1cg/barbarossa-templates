package bb.comments;

import bb.BBTemplates;
import org.junit.Test;
import comments.*;

import static org.junit.Assert.assertEquals;

public class CommentsTest {

    @Test
    public void basicCommentsWork() {
        BBTemplates.trace();
        assertEquals("", SimpleComment.render());
    }

    @Test
    public void commentsIgnoreSyntax() {
        assertEquals("", ExpressionInsideComment1.render());
        assertEquals("", ExpressionInsideComment2.render());
        assertEquals("", StatementInsideComment.render());
        assertEquals("", DirectiveInsideComment.render());

    }

}