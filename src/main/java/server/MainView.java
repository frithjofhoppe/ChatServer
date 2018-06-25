package server;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;


public class MainView implements FxmlView<MainViewModel>, Initializable {

    @FXML
    TextField txt_port;
    @FXML
    Label lbl_ipAddress;
    @FXML
    Label lbl_status;
    @FXML
    Button btn_start;
    @FXML
    Button btn_stop;
    @FXML
    Button btn_close;
    @FXML
    ListView tbl_users;

    @InjectViewModel
    private MainViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lbl_ipAddress.textProperty().bind(viewModel.lblIpAddress);
        lbl_status.textProperty().bindBidirectional(viewModel.lblStatus);
        txt_port.disableProperty().bind(viewModel.txtPort);
        tbl_users.itemsProperty().bindBidirectional(viewModel.tblUsers);
    }

    @FXML
    public void handle_btn_start_click() {
        String port = txt_port.getText();
        if(port.matches("^\\d{5}$")) {
            viewModel.startServer(Integer.parseInt(port));
            System.out.println("Intialized ChatServer successfuly");
        }
        System.out.println("Intialized ChatServer unsuccessfully");
    }

    @FXML
    public void handle_btn_stop_click() {
        viewModel.stopServer();
    }

    @FXML
    public void handle_btn_close_click(){
        viewModel.closeApplication();
    }

    @FXML
    public void handle_lbl_ipAddress_click(){
        String content = lbl_ipAddress.getText();
        viewModel.copyToClipboard(content);
    }
}
