package com.example.velvethills;

import com.example.velvethills.Utils.Config;
import com.example.velvethills.Utils.captcha.CaptchaGenerator;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CaptchaController {
    LoginController root = new LoginController();

    @FXML
    private Canvas canvas;

    @FXML
    private TextField textInputField;

    CaptchaGenerator cg;
    String captchaText = "";

    public void getParentController(LoginController root) {
        this.root = root;
    }

    @FXML
    public void initialize() {
        cg = new CaptchaGenerator(canvas);
        captchaText = cg.generate(Config.captchaLength);
    }

    @FXML
    private void check() {
        if (!captchaText.equals(textInputField.getText())) {
            root.lock(true, 10);
        }
        Stage stage = (Stage) textInputField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void reset() {
        captchaText = cg.generate(Config.captchaLength);
    }
}
