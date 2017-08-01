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
        if (e.getClass().equals(BBRuntimeException.class)) {
            try {
                Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                theUnsafe.setAccessible(true);
                Unsafe unsafe = (Unsafe) theUnsafe.get(null);
                unsafe.throwException(e);
            } catch (NoSuchFieldException|IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }

        StackTraceElement[] currentStack = e.getStackTrace();

        int lineNumber = currentStack[0].getLineNumber();
        int javaLineNum = lineNumber - lineStart;
        String declaringClass = currentStack[1].getClassName();
        String methodName = currentStack[1].getMethodName();

        StackTraceElement b = new StackTraceElement(declaringClass, methodName, fileName, bbLineNumbers[javaLineNum]);
        currentStack[1] = b;

        e.setStackTrace(Arrays.copyOfRange(currentStack, 1, currentStack.length));
        BBRuntimeException exceptionToThrow = new BBRuntimeException(e);
        exceptionToThrow.setStackTrace(Arrays.copyOfRange(currentStack, 1, currentStack.length));

        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            Unsafe unsafe = (Unsafe) theUnsafe.get(null);
            unsafe.throwException(exceptionToThrow);
        } catch (NoSuchFieldException|IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

}
