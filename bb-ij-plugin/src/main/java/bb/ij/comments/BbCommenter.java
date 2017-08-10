package bb.ij.comments;

import bb.ij.BbLanguage;
import bb.ij.config.BbConfig;
import com.intellij.lang.Commenter;
import com.intellij.lang.Language;
import com.intellij.lang.LanguageCommenters;
import org.jetbrains.annotations.Nullable;

public class BbCommenter implements Commenter {
  private static final Commenter ourBarebonesCommenter = new BarebonesCommenter();

  @Nullable
  @Override
  public String getLineCommentPrefix() {
    Commenter commenter = getCommenter();
    return commenter != null ? commenter.getLineCommentPrefix() : null;
  }

  @Nullable
  @Override
  public String getBlockCommentPrefix() {
    Commenter commenter = getCommenter();
    return commenter != null ? commenter.getBlockCommentPrefix() : null;
  }

  @Nullable
  @Override
  public String getBlockCommentSuffix() {
    Commenter commenter = getCommenter();
    return commenter != null ? commenter.getBlockCommentSuffix() : null;
  }

  @Nullable
  @Override
  public String getCommentedBlockCommentPrefix() {
    Commenter commenter = getCommenter();
    return commenter != null ? commenter.getCommentedBlockCommentPrefix() : null;
  }

  @Nullable
  @Override
  public String getCommentedBlockCommentSuffix() {
    Commenter commenter = getCommenter();
    return commenter != null ? commenter.getCommentedBlockCommentSuffix() : null;
  }

  private static Commenter getCommenter() {
    Language commenterLanguage = BbConfig.getCommenterLanguage();
    if (commenterLanguage == null) {
      commenterLanguage = BbLanguage.getDefaultTemplateLang().getLanguage();
    }
    else if (commenterLanguage.isKindOf(BbLanguage.INSTANCE)) {
      return ourBarebonesCommenter;
    }

    return LanguageCommenters.INSTANCE.forLanguage(commenterLanguage);
  }
}
