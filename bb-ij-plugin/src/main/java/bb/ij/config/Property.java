package bb.ij.config;

import com.intellij.lang.html.HTMLLanguage;
import org.jetbrains.annotations.NotNull;

/**
 * Formalizes the properties which we will persist using {@link com.intellij.ide.util.PropertiesComponent}
 */
enum Property {
  AUTO_GENERATE_CLOSE_TAG {
    @NotNull
    @Override
    public String getStringName() {
      return "BbAutoGenerateCloseTag";
    }

    @NotNull
    @Override
    public String getDefault() {
      return ENABLED;
    }
  },

  AUTOCOMPLETE_MUSTACHES {
    @NotNull
    @Override
    public String getStringName() {
      return "BbAutocompleteMustaches";
    }

    @NotNull
    @Override
    public String getDefault() {
      return ENABLED;
    }
  },

  FORMATTER {
    @NotNull
    @Override
    public String getStringName() {
      return "BbFormatter";
    }

    @NotNull
    @Override
    public String getDefault() {
      return ENABLED;
    }
  },

  AUTO_COLLAPSE_BLOCKS {
    @NotNull
    @Override
    public String getStringName() {
      return "BbAutoCollapseBlocks";
    }

    @NotNull
    @Override
    public String getDefault() {
      return DISABLED;
    }
  },

  COMMENTER_LANGUAGE_ID {
    @NotNull
    @Override
    public String getStringName() {
      return "BbCommenterLanguageId";
    }

    @NotNull
    @Override
    public String getDefault() {
      return HTMLLanguage.INSTANCE.getID();
    }

  },

  SHOULD_OPEN_HTML {
    @NotNull
    @Override
    public String getStringName() {
      return "BbShouldOpenHtmlAsHb";
    }

    @NotNull
    @Override
    public String getDefault() {
      return "";
    }
  };



  public static final String ENABLED = "enabled";
  public static final String DISABLED = "disabled";

  /**
   * The String which will actually be persisted in a user's properties using {@link com.intellij.ide.util.PropertiesComponent}.
   * <p/>
   * This value must be unique amongst Property entries
   * <p/>
   * IMPORTANT: these should probably never change so that we don't lose a user's preferences between releases.
   */
  @NotNull
  public abstract String getStringName();

  /**
   * The default/initial value for a user
   */
  @NotNull
  public abstract String getDefault();
}
