package ca.warp7.tables.controller.main;

import ca.warp7.tables.controller.Misc;
import javafx.fxml.FXML;

public class ViewsController {

    @FXML
    void onNewViewAction() {
        Misc.openWindow("stages/new_view.fxml", "New View", getClass());
    }
}
