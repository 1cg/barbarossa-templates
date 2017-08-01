package bb.manifold;

import bb.codegen.BBTemplateGen;
import manifold.api.fs.IFile;
import manifold.api.sourceprod.AbstractSingleFileModel;
import manifold.internal.javac.IIssue;
import manifold.internal.javac.SourceJavaFileObject;
import manifold.util.JavacDiagnostic;
import manifold.util.StreamUtil;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

class BBModel extends AbstractSingleFileModel {
    private String _source;
    private BBIssueContainer _issues;

    BBModel(String fqn, Set<IFile> files) {
        super(fqn, files);
        init();
    }

    private void init() {
        IFile file = getFile();
        try {
            String templateSource = StreamUtil.getContent(new InputStreamReader(file.openInputStream()));
            BBTemplateGen generator = new BBTemplateGen();
            _source = generator.generateCode(getFqn(), templateSource);
            _issues = generator.getIssues();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateFile(IFile file) {
        super.updateFile(file);
        init();
    }

    String getSource() {
        return _source;
    }
    void report(DiagnosticListener errorHandler) {
        if (_issues.isEmpty() || errorHandler == null) {
            return;
        }

        JavaFileObject file = new SourceJavaFileObject(getFile().toURI());
        for (IIssue issue : _issues.getIssues()) {
            Diagnostic.Kind kind = issue.getKind() == IIssue.Kind.Error ? Diagnostic.Kind.ERROR : Diagnostic.Kind.WARNING;
            errorHandler.report(new JavacDiagnostic(file, kind, issue.getStartOffset(), issue.getLine(), issue.getColumn(), issue.getMessage()));
        }
    }

}
