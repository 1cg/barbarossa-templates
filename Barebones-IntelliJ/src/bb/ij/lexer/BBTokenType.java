package bb.ij.lexer;

import bb.ij.lang.BBLanguage;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * Created by eim on 8/14/2017.
 */
public class BBTokenType extends IElementType {
    public BBTokenType(@NotNull @NonNls String debugName) {
        super(debugName, BBLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "[Barebones] " + super.toString();
    }
}
