package org.netbeans.modules.ppcnf.main;

import java.awt.event.ActionEvent;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.spi.project.ui.CustomizerProvider2;
import org.netbeans.spi.project.ui.support.NodeFactory;
import org.netbeans.spi.project.ui.support.NodeFactorySupport;
import org.netbeans.spi.project.ui.support.NodeList;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

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
