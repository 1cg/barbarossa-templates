package bb.ij.file;

import bb.ij.BbLanguage;
import com.intellij.lang.Language;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.highlighter.EditorHighlighter;
import com.intellij.openapi.fileTypes.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.templateLanguages.TemplateDataLanguageMappings;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.nio.charset.Charset;

public class BbFileType extends LanguageFileType implements TemplateLanguageFileType {
  public static final LanguageFileType INSTANCE = new BbFileType();

  @NonNls
  public static final String DEFAULT_EXTENSION = "barebones;bb";

  private BbFileType() {
    this(BbLanguage.INSTANCE);
  }

  protected BbFileType(Language lang) {
    super(lang);

    FileTypeEditorHighlighterProviders.INSTANCE.addExplicitExtension(this, new EditorHighlighterProvider() {
      public EditorHighlighter getEditorHighlighter(@Nullable Project project,
                                                    @NotNull FileType fileType,
                                                    @Nullable VirtualFile virtualFile,
                                                    @NotNull EditorColorsScheme editorColorsScheme) {
        return new HbTemplateHighlighter(project, virtualFile, editorColorsScheme);
      }
    });
  }

  @NotNull
  public String getName() {
    return "BareBones";
  }

  @NotNull
  public String getDescription() {
    return BbBundle.message("bb.files.file.type.description");
  }

  @NotNull
  public String getDefaultExtension() {
    return DEFAULT_EXTENSION;
  }

  public Icon getIcon() {
    return HandlebarsIcons.Handlebars_icon;
  }

  public Charset extractCharsetFromFileContent(@Nullable final Project project,
                                               @Nullable final VirtualFile file,
                                               @NotNull final CharSequence content) {
    LanguageFileType associatedFileType = getAssociatedFileType(file, project);

    if (associatedFileType == null) {
      return null;
    }

    return CharsetUtil.extractCharsetFromFileContent(project, file, associatedFileType, content);
  }

  private static LanguageFileType getAssociatedFileType(VirtualFile file, Project project) {
    if (project == null) {
      return null;
    }
    Language language = TemplateDataLanguageMappings.getInstance(project).getMapping(file);

    LanguageFileType associatedFileType = null;
    if (language != null) {
      associatedFileType = language.getAssociatedFileType();
    }

    if (language == null || associatedFileType == null) {
      associatedFileType = BbLanguage.getDefaultTemplateLang();
    }
    return associatedFileType;
  }
}
