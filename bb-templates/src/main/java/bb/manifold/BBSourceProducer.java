package bb.manifold;

import bb.codegen.BBTemplateGen;
import manifold.api.fs.IFile;
import manifold.api.host.ITypeLoader;
import manifold.api.sourceprod.JavaSourceProducer;
import manifold.util.StreamUtil;

import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.InputStreamReader;

public class BBSourceProducer extends JavaSourceProducer<BBModel> {

    public void init( ITypeLoader typeLoader )
    {
        init(typeLoader, BBModel::new);
    }

    @Override
    protected String aliasFqn(String fqn, IFile file) {
        return fqn.endsWith( "_bb" ) ? fqn.substring( 0, fqn.length()-3 ) : fqn;
    }

    @Override
    protected boolean isInnerType(String topLevelFqn, String relativeInner) {
        return true;
    }

    @Override
    public boolean handlesFileExtension(String fileExtension) {
        return true;
    }

    @Override
    public boolean handlesFile(IFile file) {
        return file.getBaseName().endsWith(".bb") || file.getExtension().equals("bb");
    }

    @Override
    protected String produce(String topLevelFqn, String existing, BBModel model, DiagnosticListener<JavaFileObject> errorHandler) {
        String source = model.getSource();
        model.report( errorHandler );
        return source;
    }

}
