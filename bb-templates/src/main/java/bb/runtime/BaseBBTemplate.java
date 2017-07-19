package bb.runtime;

public class BaseBBTemplate {
    private ILayout myLayout = new DefaultLayout();

    public String toS(Object o) {
        return o == null ? "" : o.toString();
    }

    public void beforeRender(Appendable buffer) {
        //TODO: Search for layout and append the layout header
    }

    public void afterRender(Appendable buffer) {
        //TODO: Search for the layout and append the footer
    }

}
