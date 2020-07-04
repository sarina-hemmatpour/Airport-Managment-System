package com.company.Controller;

import javafx.scene.control.TabPane;

public class RemoveTabInTabPane {

    public void removeTab(TabPane tabPane , int indexOfTab)
    {
        tabPane.getTabs().remove(indexOfTab);
    }
}
