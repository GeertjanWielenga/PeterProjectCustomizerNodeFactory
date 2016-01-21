package org.netbeans.modules.ppcnf.main;

import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.spi.project.ui.support.NodeFactory;
import org.netbeans.spi.project.ui.support.NodeFactorySupport;
import org.netbeans.spi.project.ui.support.NodeList;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

@NodeFactory.Registration(projectType = "org-netbeans-modules-java-j2seproject")
public class PeterNodeFactory implements NodeFactory, PreferenceChangeListener {

    private Node node;
    private Preferences prefs;

    @Override
    public NodeList createNodes(Project project) {
        prefs = ProjectUtils.getPreferences(project, PeterProjectCustomizer.class, true);
        prefs.addPreferenceChangeListener(this);
        node = new AbstractNode(Children.LEAF);
        node.setDisplayName("Not Configured");
        return NodeFactorySupport.fixedNodeList(node);
    }

    @Override
    public void preferenceChange(PreferenceChangeEvent evt) {
        if (evt.getKey().equals("configureMyNode")) {
            if ("true".equals(prefs.get("configureMyNode", null))) {
                node.setDisplayName("Configured");
            } else {
                node.setDisplayName("Not configured");
            }
        }
    }

}
