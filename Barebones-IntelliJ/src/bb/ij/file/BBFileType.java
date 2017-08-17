package bb.ij.file;

import bb.ij.lang.BBIcons;
import bb.ij.lang.BBLanguage;
import bb.ij.lang.Barebones;
import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by eim on 8/14/2017.
 */
public class BBFileType extends LanguageFileType {
    public static final BBFileType INSTANCE = new BBFileType();

    static final String DEFAULT_EXTENTION = "bb";

    private BBFileType() {
        super(BBLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return Barebones.LanguageName;
    }

    @NotNull
    @Override
    public String getDescription() {
        return Barebones.LanguageDescription;
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return DEFAULT_EXTENTION;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return BBIcons.FILE;
    }
}
