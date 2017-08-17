package bb.ij.psi.impl;

import bb.ij.file.BBFileType;
import bb.ij.lang.BBLanguage;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

/**
 * Created by eim on 8/15/2017.
 */
public class BBFileImpl extends PsiFileBase implements BBFile {
    public BBFileImpl(FileViewProvider viewProvider) {
        super(viewProvider, BBLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return BBFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "BBFile: " + getName();
    }
}


