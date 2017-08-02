package bb.runtime;

import org.junit.Test;

import runtime.*;
/**
 * Created by eim on 8/1/2017.
 */
public class RuntimeErrorTest {
    @Test
    public void basicErrorWorks() {
        BasicRuntimeError.render();
    }

    @Test
    public void layoutErrorWorks() {
        HasLayoutRuntimeError.render();
    }

}
