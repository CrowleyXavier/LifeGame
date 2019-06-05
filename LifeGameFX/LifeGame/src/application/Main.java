package application;

import java.io.IOException;

//import game.BoardModel;
//import game.ModelPrinter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Form.fxml"));
			Scene scene = new Scene(root,300,300);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
/*		BoardModel model = new BoardModel(10,10);//(列,行)=(x,y)
		model.addListener(new ModelPrinter());
		model.changeCellState(1, 1);
		model.changeCellState(2, 2);
		model.changeCellState(0, 3);
		model.changeCellState(1, 3);
		model.changeCellState(2, 3);
		model.changeCellState(4, 4);
		model.changeCellState(4, 4);

		for(int i = 0;i<12;i++) {
			model.next();
		}

		while(model.isUndoable()) {
			model.undo();
			model.fireUpdate();
			}
*/
	}

}
