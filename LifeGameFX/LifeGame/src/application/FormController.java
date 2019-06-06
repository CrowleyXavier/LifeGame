package application;

import java.net.URL;
import java.util.ResourceBundle;

import game.BoardModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class FormController implements Initializable{
	@FXML Canvas canvas;
	@FXML private Button button_Next;
	@FXML private Button button_Undo;
	@FXML private Button button_NewGame;
    GraphicsContext gc;
    BoardModel model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	model = new BoardModel(15,15);//(列,行)=(x,y)
        gc = canvas.getGraphicsContext2D();
        draw();
    }

    @FXML
    public void onNextClicked() {
    	model.next();
        draw();

    }

    @FXML
    public void onUndoClicked() {
    	model.undo();
        draw();
    }

    @FXML
    public void onNewGameClicked() {


    }

















    void draw(){
        gc.setStroke(Color.RED);
        //gc.strokeLine(50,100,350,200);

		for(int c = 0;c<=model.getCols();c++) {//colsは列
			gc.strokeLine(10+10*c,20,10+10*c,20+model.getRows()*10);//縦棒
		}

		for(int r = 0;r<=model.getRows();r++) {//rowsは行
			gc.strokeLine(10, 20+r*10, 10+10*model.getCols(), 20+r*10);//横棒
		}
    }
}
