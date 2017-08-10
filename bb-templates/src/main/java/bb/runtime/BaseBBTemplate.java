package bb.runtime;

import bb.BBTemplates;
import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

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
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }
        StackTraceElement[] currentStack = e.getStackTrace();
        String templateClassName = getClass().getName();

        int elementToRemove = 0;
        while (elementToRemove < currentStack.length) {
            StackTraceElement curr = currentStack[elementToRemove];
            if (curr.getClassName().equals(templateClassName)) {
                if (curr.getMethodName().equals("renderImpl")) {
                    handleTemplateException(e, fileName, lineStart, bbLineNumbers, elementToRemove);
                } else if (curr.getMethodName().equals("footer") || curr.getMethodName().equals("header")) {
                    handleLayoutException(e, fileName, lineStart, bbLineNumbers, elementToRemove);
                }
            }
            elementToRemove++;
        }
    }

    protected void handleTemplateException(Exception e, String fileName, int lineStart, int[] bbLineNumbers, int elementToRemove) {
        StackTraceElement[] currentStack = e.getStackTrace();
        int lineNumber = currentStack[elementToRemove].getLineNumber();
        int javaLineNum = lineNumber - lineStart;

        String declaringClass = currentStack[elementToRemove + 1].getClassName();
        String methodName = currentStack[elementToRemove + 1].getMethodName();

        StackTraceElement b = new StackTraceElement(declaringClass, methodName, fileName, bbLineNumbers[javaLineNum]);
        currentStack[elementToRemove + 1] = b;

        System.arraycopy(currentStack, elementToRemove + 1, currentStack, elementToRemove, currentStack.length-1-elementToRemove);
        throwBBException(e, currentStack);
    }

    protected void handleLayoutException(Exception e, String fileName, int lineStart, int[] bbLineNumbers, int elementToReplace) {
        StackTraceElement[] currentStack = e.getStackTrace();
        int lineNumber = currentStack[elementToReplace].getLineNumber();
        int javaLineNum = lineNumber - lineStart;

        String declaringClass = currentStack[elementToReplace].getClassName();
        String methodName = currentStack[elementToReplace].getMethodName();

        StackTraceElement b = new StackTraceElement(declaringClass, methodName, fileName, bbLineNumbers[javaLineNum]);
        currentStack[elementToReplace] = b;

        throwBBException(e, currentStack);
    }

    protected void throwBBException(Exception e, StackTraceElement[] currentStack) {
        e.setStackTrace(currentStack);
        BBRuntimeException exceptionToThrow = new BBRuntimeException(e);
        exceptionToThrow.setStackTrace(currentStack);

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
