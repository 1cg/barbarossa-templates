package bb.runtime;

import bb.BBTemplates;
import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

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

    protected ILayout getExplicitLayout() {
        return _explicitLayout;
    }

    protected void beforeRender(Appendable buffer, ILayout override, boolean outerTemplate) throws IOException {
        if (outerTemplate) {
            ILayout templateLayout = override == null ? getTemplateLayout() : override;
            templateLayout.header(buffer);
        }
    }

    protected void afterRender(Appendable buffer, ILayout override, boolean outerTemplate, long renderTime) throws IOException {
        if (outerTemplate) {
            ILayout templateLayout = override == null ? getTemplateLayout() : override;
            templateLayout.footer(buffer);
        }
        BBTemplates.getTracer().trace(this.getClass(), renderTime);
    }

    protected void handleException(Exception e, String fileName, int lineStart, int[] bbLineNumbers) {
        int lineNumber = e.getStackTrace()[0].getLineNumber();
        int javaLineNum = lineNumber - lineStart;
        StackTraceElement[] a = e.getStackTrace();
        String declaringClass = a[1].getClassName();
        String methodName = a[1].getMethodName();
        StackTraceElement b = new StackTraceElement(declaringClass, methodName, fileName + ".bb.txt", bbLineNumbers[javaLineNum]);
        a[1] = b;
        e.setStackTrace(Arrays.copyOfRange(a, 1, a.length));
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            Unsafe unsafe = (Unsafe) theUnsafe.get(null);
            unsafe.throwException(e);
        } catch (NoSuchFieldException|IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

}
