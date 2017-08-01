package bb.manifold;

import manifold.api.fs.IFile;
import manifold.internal.javac.IIssue;
import manifold.internal.javac.IIssueContainer;

import java.util.Collections;
import java.util.List;

public class BBIssueContainer implements IIssueContainer {
    private final List<BBIssue> _issues;

    public BBIssueContainer() {
        _issues = Collections.emptyList();
    }

    public BBIssueContainer(List<BBIssue> issues) {
        _issues = issues;
    }

    @Override
    public List<IIssue> getIssues() {
        return (List) _issues;
    }

    @Override
    public List<IIssue> getWarnings() {
        return Collections.emptyList();
    }

    @Override
    public List<IIssue> getErrors() {
        return getIssues();
    }

    @Override
    public boolean isEmpty() {
        return _issues.isEmpty();
    }
}
