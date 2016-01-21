package org.netbeans.modules.ppcnf.main;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.event.ChangeListener;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.spi.project.SubprojectProvider;
import org.netbeans.spi.project.ui.CustomizerProvider2;
import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.netbeans.spi.project.ui.support.NodeFactory;
import org.netbeans.spi.project.ui.support.NodeFactorySupport;
import org.netbeans.spi.project.ui.support.NodeList;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;

@NodeFactory.Registration(projectType = "org-netbeans-modules-java-j2seproject")
public class PeterNodeFactory implements NodeFactory, PreferenceChangeListener {

    private AbstractNode node;
    private Preferences prefs;

    @StaticResource
    private static final String GREEN = "org/netbeans/modules/ppcnf/main/green.png";
    @StaticResource
    private static final String RED = "org/netbeans/modules/ppcnf/main/red.png";

    @Override
    public NodeList createNodes(final Project project) {
        prefs = ProjectUtils.getPreferences(project, PeterProjectCustomizer.class, true);
        prefs.addPreferenceChangeListener(this);
        node = new AbstractNode(Children.LEAF){
            @Override
            public Action getPreferredAction() {
                return new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //first string is category, second string is subcategory
                        //in this example, we have a category called "peter",
                        //without a subcategory, hence the second string is empty
                        project.getLookup().lookup(CustomizerProvider2.class).showCustomizer("peter","");
                    }
                };
            };
        };
        node.setDisplayName("Double-click to configure");
        node.setIconBaseWithExtension(RED);
        return NodeFactorySupport.fixedNodeList(node);
    }
    
    //Currently unused, but the start of change listener support to add/remove
    private class PeterNodeList implements NodeList<Project> {
        private final Project project;
        public PeterNodeList(Project prjct) {
            this.project = prjct;
        }
        @Override
        public List<Project> keys() {
            return Arrays.asList(project);
        }
        @Override
        public Node node(final Project project) {
            AbstractNode node = new AbstractNode(Children.LEAF);
            node.setDisplayName("Peter");
            return node;
        }
        @Override
        public void addChangeListener(ChangeListener cl) {
        }
        @Override
        public void removeChangeListener(ChangeListener cl) {
        }
        @Override
        public void addNotify() {
        }
        @Override
        public void removeNotify() {
        }
    }

    @Override
    public void preferenceChange(PreferenceChangeEvent evt) {
        if (evt.getKey().equals("configureMyNode")) {
            if ("true".equals(prefs.get("configureMyNode", null))) {
                node.setDisplayName("Configured");
                node.setIconBaseWithExtension(GREEN);
            } else {
                node.setDisplayName("Double-click to configure");
                node.setIconBaseWithExtension(RED);
            }
        }
    }

}
