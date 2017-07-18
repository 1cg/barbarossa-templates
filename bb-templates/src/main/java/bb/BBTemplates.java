package bb;

import bb.runtime.ILayout;

/**
 * Created by eim on 7/17/2017.
 * For configuration purposes
 */
public class BBTemplates {
    public static void setDefaultTemplate(Class layout) {

    }

    public static void setDefaultTemplate(String somePackage, Class layout) {

    }

    public static ILayout getDefaultTemplate(String packageName) {
        //TODO: Search through map of templates to packageName,
        //going from most specific to least specific.
        return null;
    }

}
