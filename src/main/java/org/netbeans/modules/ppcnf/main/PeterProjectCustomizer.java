package org.netbeans.modules.ppcnf.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.spi.project.ui.support.ProjectCustomizer;
import org.openide.awt.Mnemonics;
import org.openide.util.Lookup;

public class PeterProjectCustomizer implements ProjectCustomizer.CompositeCategoryProvider {

    @ProjectCustomizer.CompositeCategoryProvider.Registrations({
        @ProjectCustomizer.CompositeCategoryProvider.Registration(
                projectType = "org-netbeans-modules-java-j2seproject", // J2SE project type
                position = 10
        )
    })
    public static PeterProjectCustomizer createCustomizer() {
        return new PeterProjectCustomizer();
    }

    @Override
    public ProjectCustomizer.Category createCategory(Lookup context) {
        return ProjectCustomizer.Category.create("peter", "Peter", null);
    }

    @Override
    public JComponent createComponent(ProjectCustomizer.Category category, Lookup context) {
        Project project = context.lookup(Project.class);
        final Preferences prefs = ProjectUtils.getPreferences(project, PeterProjectCustomizer.class, true);
        JPanel panel = new JPanel();
        final JCheckBox compileOnSave = new JCheckBox((String) null,
                "true".equals(prefs.get("configureMyNode", null)));
        Mnemonics.setLocalizedText(compileOnSave, "&Configure my node");
        panel.add(compileOnSave);
        category.setStoreListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (compileOnSave.isSelected()) {
                    prefs.put("configureMyNode", "true");
                } else {
                    prefs.remove("configureMyNode");
                }
            }
        });
        return panel;
    }

}