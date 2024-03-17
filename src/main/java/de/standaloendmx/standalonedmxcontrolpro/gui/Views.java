package de.standaloendmx.standalonedmxcontrolpro.gui;

public enum Views {


    /*
                Parent contentArea = FXMLLoader.load(getClass().getResource("/gui/main/ContentAreaView.fxml"));
            Parent sideBar = FXMLLoader.load(getClass().getResource("/gui/main/SideBarView.fxml"));
            Parent menuBar = FXMLLoader.load(getClass().getResource("/gui/main/MenuBarView.fxml"));
            Parent bottomBar = FXMLLoader.load(getClass().getResource("/gui/main/BottomBarView.fxml"));
     */

    BOTTOM_BAR("/gui/main/BottomBarView.fxml"), CONTENT_AREA("/gui/main/ContentAreaView.fxml"),
    MAIN("/gui/main/MainView.fxml"), MENU_BAR("/gui/main/MenuBarView.fxml"),
    SIDE_BAR("/gui/main/SideBarView.fxml"), PATCH_DIRECTORY("/gui/patch/PatchDirectoryView.fxml"),
    PATCH_GRID("/gui/patch/PatchGridView.fxml"), EDIT_VIEW("/gui/edit/EditView.fxml");

    private String path;

    Views(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
