package bb.ij.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

/**
 * Created by eim on 8/15/2017.
 */
public class BBPsiElement extends ASTWrapperPsiElement { //TODO: Can extend PsiElementBase as well
    public BBPsiElement(@NotNull ASTNode astNode) {
        super(astNode);
    }
}
