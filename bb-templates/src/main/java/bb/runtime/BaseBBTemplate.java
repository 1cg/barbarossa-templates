package bb.runtime;

import bb.BBTemplates;

import java.io.IOException;

public class BaseBBTemplate {

    private ILayout _explicitLayout = null;

    public String toS(Object o) {
        return o == null ? "" : o.toString();
    }

    protected void setLayout(ILayout layout) {
        _explicitLayout = layout;
    }

    protected ILayout getTemplateLayout() {
        if (_explicitLayout != null) {
            return _explicitLayout;
        } else {
            return BBTemplates.getDefaultTemplate(this.getClass().getName());
        }
    }

    protected void beforeRender(Appendable buffer, boolean outerTemplate, ILayout override) throws IOException {
        if (outerTemplate) {
            ILayout templateLayout = override == null ? getTemplateLayout() : override;
            templateLayout.header(buffer);
        }
    }

    protected void afterRender(Appendable buffer, boolean outerTemplate, long renderTime, ILayout override) throws IOException {
        if (outerTemplate) {
            ILayout templateLayout = override == null ? getTemplateLayout() : override;
            templateLayout.footer(buffer);
        }
        BBTemplates.getTracer().trace(this.getClass(), renderTime);
    }

}
