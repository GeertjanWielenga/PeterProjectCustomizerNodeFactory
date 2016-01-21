package org.netbeans.modules.ppcnf.main;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;
import javax.swing.event.ChangeListener;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.spi.project.ui.support.NodeFactory;
import org.netbeans.spi.project.ui.support.NodeList;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.ChangeSupport;

@NodeFactory.Registration(projectType = "org-netbeans-modules-java-j2seproject")
public class PeterNodeFactory implements NodeFactory {

    private AbstractNode node;

    @Override
    public NodeList createNodes(final Project project) {
        return new PeterNodeList(project);
    }

    private class PeterNodeList implements NodeList<Project>, PreferenceChangeListener {
        private final Project project;
        private final Preferences prefs;
        private List<Project> keys;
        private final ChangeSupport cs;
        public PeterNodeList(Project prjct) {
            this.project = prjct;
            this.cs = new ChangeSupport(this);
            this.prefs = ProjectUtils.getPreferences(project, PeterProjectCustomizer.class, true);
            prefs.addPreferenceChangeListener(this);
        }
        @Override
        public List<Project> keys() {
            fireChange();
            if ("true".equals(prefs.get("showMyNode", null))) {
                this.keys = Arrays.asList(project);
            } else {
                this.keys = Collections.EMPTY_LIST;
            }
            return keys;
        }
        @Override
        public Node node(final Project project) {
            AbstractNode node = new AbstractNode(Children.LEAF);
            node.setDisplayName("Special");
            return node;
        }
        @Override
        public void addChangeListener(ChangeListener cl) {
            cs.addChangeListener(cl);
        }
        @Override
        public void removeChangeListener(ChangeListener cl) {
            cs.removeChangeListener(cl);
        }
        private void fireChange() {
            cs.fireChange();
        }
        @Override
        public void addNotify() {
        }
        @Override
        public void removeNotify() {
        }
        @Override
        public void preferenceChange(PreferenceChangeEvent evt) {
            if (evt.getKey().equals("showMyNode")) {
                fireChange();
            }
        }
    }

}
