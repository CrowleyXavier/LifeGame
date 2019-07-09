package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

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
	@FXML private Button button_Auto;
	@FXML private Button button_Stop;

	Timer timer ;
	private boolean auto_mode = false;
	private int mouse_x;
	private int mouse_y;
    GraphicsContext gc;
    BoardModel model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	model = new BoardModel(15,15);//(列,行)=(x,y)
        gc = canvas.getGraphicsContext2D();

        //セル部分をクリックするとその部分のChangeCellStateを呼び出す
        canvas.setOnMousePressed(ev ->{
        	mouse_x=(int)ev.getSceneX()-50;
        	mouse_y=(int)ev.getSceneY();

        	for(int r=0;r<model.getRows();r++) {
        		for(int c=0;c<model.getCols();c++) {
        			if(10+10*c<mouse_x&&mouse_x<20+10*c&&20+10*r<mouse_y&&mouse_y<30+10*r) {
        				model.changeCellState(c, r);
        			}
        		}
        	}
        	draw();
        });

        //セル部分をドラッグするとその部分のセルの状態をtrueにする
        canvas.setOnMouseDragged(ev ->{
        	mouse_x=(int)ev.getSceneX()-50;
        	mouse_y=(int)ev.getSceneY();

        	for(int r=0;r<model.getRows();r++) {
        		for(int c=0;c<model.getCols();c++) {
        			if(10+10*c<mouse_x&&mouse_x<20+10*c&&20+10*r<mouse_y&&mouse_y<30+10*r) {
        				model.trueCellState(c, r);
        			}
        		}
        	}
        	draw();
        });
        draw();
    }

    //Nextボタンを押した時の処理
    @FXML
    public void onNextClicked() {
    	if(auto_mode==false) {
    		model.next();
        	draw();
    	}
    }

    //Undoボタンを押した時の処理
    @FXML
    public void onUndoClicked() {
    	if(auto_mode==false) {
    		model.undo();
    		draw();
    	}
    }

    //NewGameボタンを押した時の処理
    @FXML
    public void onNewGameClicked() {
    	if(auto_mode==false) {
    		model.NewGame();
    		draw();
    	}
    }

    //Autoボタンを押した時の処理
    //Autoボタンを押すとStopボタンを押すまでほかのボタンは押しても作動しない
    @FXML
    public void onAutoClicked() {
    	auto_mode = true;

    	if(timer==null) {
        	timer = new Timer();
        	TimerTask task = new TimerTask() {
        		public void run() {
        			model.next();
        			draw();
        		}
        	};
        //Autoボタンを押した後から500msごとにtaskを呼び出す
		timer.scheduleAtFixedRate(task,0,500);
    	}
    }

    //Stopボタンを押した時の処理
    //timerを停止させ、nullを格納する
    public void onStopClicked() {
    	if(timer!=null){
    		timer.cancel();
    		timer=null;
    		auto_mode=false;
    	}
    }

    //盤面を作成し、生存しているセルに色を付ける
    void draw(){
    	//盤面の色を設定し、盤面を作成
        gc.setStroke(Color.BLACK);

        //縦棒作成
		for(int c = 0;c<=model.getCols();c++) {//colsは列
			gc.strokeLine(10+10*c,20,10+10*c,20+model.getRows()*10);
		}
		//横棒作成
		for(int r = 0;r<=model.getRows();r++) {//rowsは行
			gc.strokeLine(10, 20+r*10, 10+10*model.getCols(), 20+r*10);
		}

		//CellStateがtrueのセルに青色をつける
		for(int r=0;r<model.getRows();r++) {
    		for(int c=0;c<model.getCols();c++) {
    			if(model.CellState(c, r)==true) {
    				gc.setFill(Color.BLUE);
    				gc.fillRect(10+c*10,20+r*10,10,10);
    			}else {
    				gc.setFill(Color.WHITE);
    				gc.fillRect(10.1+c*10,20.1+r*10,9.9,9.9);
    			}
    		}
    	}
    }
}
