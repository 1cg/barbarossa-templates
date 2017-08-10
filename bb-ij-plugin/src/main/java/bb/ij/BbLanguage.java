package bb.ij;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.psi.templateLanguages.TemplateLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BbLanguage extends Language implements TemplateLanguage {
    public static final BbLanguage INSTANCE = new BbLanguage();

    @SuppressWarnings("SameReturnValue")
    // ideally this would be public static, but the static inits in the tests get cranky when we do that
    public static LanguageFileType getDefaultTemplateLang() {
        return StdFileTypes.HTML;
    }

    public BbLanguage() {
        super("BareBones", "text/x-barebones-template", "text/x-barebones");
    }

    public BbLanguage(@Nullable Language baseLanguage, @NotNull @NonNls final String ID, @NotNull @NonNls final String... mimeTypes) {
        super(baseLanguage, ID, mimeTypes);
    }
}
