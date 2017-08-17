package bb.ij.file;

import com.intellij.lang.Language;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.FileViewProviderFactory;
import com.intellij.psi.PsiManager;

/**
 * Created by eim on 8/14/2017.
 */
public class BBFileViewProviderFactory implements FileViewProviderFactory {
    @Override
    public FileViewProvider createFileViewProvider(VirtualFile virtualFile, Language language, PsiManager psiManager, boolean physical) {
        return new BBFileViewProvider(psiManager, virtualFile, physical);
    }
}