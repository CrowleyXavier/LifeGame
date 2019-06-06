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
	private int x;
	private int y;
    GraphicsContext gc;
    BoardModel model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	model = new BoardModel(15,15);//(列,行)=(x,y)
        gc = canvas.getGraphicsContext2D();

        canvas.setOnMousePressed(ev ->{
        	double xtemp=ev.getSceneX()-50;
        	double ytemp=ev.getSceneY();
        	x=(int)xtemp;
        	y=(int)ytemp;
        	System.out.println((x/10)*10);
/*
        	if(10<x&&x<20&&20<y&&y<30) {
        		gc.fillRect(30, 20, 10, 10);
        	}
*/
        	for(int r=0;r<model.getRows();r++) {
        		for(int c=0;c<model.getCols();c++) {
        			if(10+10*c<x&&x<20+10*c&&20+10*r<y&&y<30+10*r) {
        				model.changeCellState(c, r);
        			//	gc.fillRect((x/10)*10,(y/10)*10,10,10);
        			}
        		}
        	}
        	draw();
        });

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
    	model.NewGame();
    	draw();
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

		for(int r=0;r<model.getRows();r++) {
    		for(int c=0;c<model.getCols();c++) {
    			if(model.CellState(c, r)==true) {
    				gc.setFill(Color.BLUE);
    				gc.fillRect(10+c*10,20+r*10,10,10);
    			}else {
    				gc.setFill(Color.WHITE);
    				gc.fillRect(10.1+c*10,20.1+r*10,9.9,9.9);
    			}


    			if(10+10*c<x&&x<20+10*c&&20+10*r<y&&y<30+10*r) {


    			}
    		}
    	}


    }
}
