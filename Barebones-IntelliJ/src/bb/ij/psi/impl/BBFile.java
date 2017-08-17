package bb.ij.psi.impl;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.impl.PsiFileEx;
import org.jetbrains.annotations.NotNull;

/**
 * Created by eim on 8/15/2017.
 */
public interface BBFile extends PsiFileEx {
    @NotNull
    @Override
    FileType getFileType();
}