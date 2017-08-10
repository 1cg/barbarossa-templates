package bb.ij.file;

import com.dmarcotte.handlebars.BbLanguage;
import com.dmarcotte.handlebars.config.HbConfig;
import com.intellij.ide.highlighter.HtmlFileType;
import com.intellij.lang.Language;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.LanguageSubstitutor;
import com.intellij.testFramework.LightVirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BbLanguageSubstitutor extends LanguageSubstitutor {
  @Nullable
  @Override
  public Language getLanguage(@NotNull VirtualFile file, @NotNull Project project) {
    if (HbConfig.shouldOpenHtmlAsHandlebars(project) &&
        file.getFileType() == HtmlFileType.INSTANCE) {
      if (file instanceof LightVirtualFile) {
        return null;
      }

      return BbLanguage.INSTANCE;
    }

    return null;
  }
}
