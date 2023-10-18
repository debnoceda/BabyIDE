/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packages.baby.components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author liaminakigillamac
 */
public class TabbedPane extends JTabbedPane{

    public TabbedPane() {
    }
    
     // Method to remove the currently selected tab
    public void removeSelectedTab() {
        int tabIndex = getSelectedIndex();
        if (tabIndex != -1) {
            remove(tabIndex);
        }
    }

    // Method to create a tab panel with a close button
    private JPanel createTabPanel(String title, int tabIndex) {
        
        JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
//        tabPanel.setPreferredSize(new Dimension(60, 10)); // Set the preferred size
        tabPanel.setOpaque(false); // Make the panel transparent

        JLabel titleLabel = new JLabel(title);
        tabPanel.add(titleLabel); // Add the title label to the panel

        JButton closeButton = new JButton("X");
        closeButton.setOpaque(false);
//        closeButton.setPreferredSize(new Dimension(20, 20)); // Set the preferred size
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(tabIndex); // Remove the tab when the close button is clicked
            }
        });
        tabPanel.add(closeButton); // Add the close button to the panel

        return tabPanel;
    }

    // Method to add a new tab with a given title and content component
    public void addTabWithCloseButton(String title, Component component) {
        int tabIndex = getTabCount();
        addTab("", component); // Set an empty title for the tab

        JPanel tabPanel = createTabPanel(title, tabIndex);
        setTabComponentAt(tabIndex, tabPanel); // Set the panel as the tab component
    }
  
}
