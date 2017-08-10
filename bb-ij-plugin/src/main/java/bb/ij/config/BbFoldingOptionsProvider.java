package bb.ij.config;

import com.intellij.application.options.editor.CodeFoldingOptionsProvider;
import com.intellij.openapi.options.BeanConfigurable;

public class BbFoldingOptionsProvider
  extends BeanConfigurable<BbFoldingOptionsProvider.BbCodeFoldingOptionsBean> implements CodeFoldingOptionsProvider {

  public static class BbCodeFoldingOptionsBean {
    public boolean isAutoCollapseBlocks() {
      return BbConfig.isAutoCollapseBlocksEnabled();
    }
    public void setAutoCollapseBlocks(boolean value) {
      BbConfig.setAutoCollapseBlocks(value);
    }
  }

  public BbFoldingOptionsProvider() {
    super(new BbCodeFoldingOptionsBean());
//    HbCodeFoldingOptionsBean settings = getInstance();
//    checkBox(HbBundle.message("hb.pages.folding.auto.collapse.blocks"), settings::isAutoCollapseBlocks, settings::setAutoCollapseBlocks);
  }
}
