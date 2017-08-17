package bb.ij.lang;

import com.intellij.lang.Language;
import com.intellij.psi.templateLanguages.TemplateLanguage;

/**
 * Created by eim on 8/14/2017.
 */
public class BBLanguage extends Language implements TemplateLanguage {
    public static final BBLanguage INSTANCE = new BBLanguage();

    private BBLanguage() {
        super("Barebones");
    }
}
