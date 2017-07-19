package bb.runtime;

import bb.BBTemplates;

import java.io.IOException;

public class BaseBBTemplate {
    private ILayout _explicitLayout = null;
    private ThreadLocal<ILayout> withLayout = new ThreadLocal<>();

    public String toS(Object o) {
        return o == null ? "" : o.toString();
    }

    protected void setLayout(ILayout layout) {
        _explicitLayout = layout;
    }

    public BaseBBTemplate withLayout(ILayout layout) {
        withLayout.set(layout);
        return this;
    }

    protected ILayout getTemplateLayout() {
        if (withLayout.get() != null) {
            return withLayout.get();
        } else if (_explicitLayout != null) {
            return _explicitLayout;
        } else {
            return BBTemplates.getDefaultTemplate(this.getClass().getName());
        }
    }

    protected void beforeRender(Appendable buffer, boolean outerTemplate) throws IOException {
        if (outerTemplate) {
            ILayout templateLayout = getTemplateLayout();
            templateLayout.header(buffer);
        }
    }

    protected void afterRender(Appendable buffer, boolean outerTemplate) throws IOException {
        if (outerTemplate) {
            ILayout templateLayout = getTemplateLayout();
            templateLayout.footer(buffer);
        }
    }

    protected void afterAfterRender(Appendable buffer) {
        withLayout.set(null);
    }

}
