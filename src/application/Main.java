package application;


import javax.sound.sampled.LineEvent;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Main extends Application {

	int x1, x2, y1, y2;
	double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    int btnMode;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private mousePress mousePress = new mousePress();
	private mouseReleased mouseRelease = new mouseReleased();
	private mouseDragged mouseDragged = new mouseDragged();

	@Override
	public void start(Stage primaryStage) {
		try {
			Group root = new Group();
			GridPane pane = new GridPane();
			
			pane.setAlignment(Pos.TOP_LEFT);
			pane.setHgap(5);
			pane.setVgap(5);
			pane.setGridLinesVisible(true);
			
			MenuBar menuBar = new MenuBar();
			Menu menuFile = new Menu("File");
			Menu menuEdit = new Menu("Edit");
			menuBar.getMenus().addAll(menuFile,menuEdit);
			pane.add(menuBar, 0, 0);
		
			MyPane canvas = new MyPane(WIDTH,HEIGHT);
			canvas.setStyle("-fx-background-color: #2f4f4f");
	
			Button btnSelect = new Button("", new ImageView(new Image(getClass().getResourceAsStream("1.png"))));
			Button btnAssociationLine = new Button("",
					new ImageView(new Image(getClass().getResourceAsStream("2.png"))));
			Button btnGeneraliztionLine = new Button("",
					new ImageView(new Image(getClass().getResourceAsStream("3.png"))));
			Button btnCompositionLine = new Button("",
					new ImageView(new Image(getClass().getResourceAsStream("4.png"))));
			Button btnClass = new Button("", new ImageView(new Image(getClass().getResourceAsStream("5.png"))));
			Button btnUseCase = new Button("", new ImageView(new Image(getClass().getResourceAsStream("6.png"))));

			btnSelect.setOnMouseClicked(e -> {
				btnMode=1;
				btnSelect.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("1_1.png"))));
				btnAssociationLine.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("2.png"))));
				btnGeneraliztionLine.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("3.png"))));
				btnCompositionLine.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("4.png"))));
				btnClass.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("5.png"))));
				btnUseCase.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("6.png"))));

				System.out.println("Mode is : "+btnMode);
				canvas.setOnMousePressed(e2 ->{
					mousePress.handle(e2);
					//if(e2.getTarget() instanceof Rectangle){
						//Circle c1 = new Circle();
						
				       // Node p = ((Node) (e2.getTarget()));

				      //  System.out.println(e2.getSceneX());
				      //  System.out.println(e2.getSceneY());

						
					//}
					System.out.println(e2.getTarget());
				});
				canvas.setOnMouseDragged(e3 ->{
					mouseDragged.handle(e3);
			       // Node p = ((Node) (e3.getTarget()));

					//System.out.println(p.getTranslateX());
					//System.out.println(p.getTranslateY());

				});
				
			});

			btnAssociationLine.setOnMouseClicked(e -> {
				btnMode=2;
				btnSelect.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("1.png"))));
				btnAssociationLine.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("2_1.png"))));
				btnGeneraliztionLine.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("3.png"))));
				btnCompositionLine.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("4.png"))));
				btnClass.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("5.png"))));
				btnUseCase.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("6.png"))));

				/*
				 * draw line 
				 */
				System.out.println("Mode is : "+btnMode);
				canvas.setOnMousePressed(e2 ->{
					//if(e2.getTarget() instanceof Pane){
						
					//}else{
						x1 = mousePress.getX(e2);
						y1 = mousePress.getY(e2);
						canvas.drawLine();
					//}
				});
				canvas.setOnMouseDragged(e3 ->{
					//if(e3.getTarget() instanceof Pane){
						
					//}
					//else{
						x2 = mouseDragged.getX(e3);
						y2 = mouseDragged.getY(e3);
						canvas.tuneLine();
					//}
				});
				canvas.setOnMouseReleased(e1 ->{
					x2 = mouseRelease.getX(e1);
					y2 = mouseRelease.getY(e1);
				});
			});
			
			btnGeneraliztionLine.setOnMouseClicked(e -> {
				btnMode=3;
				btnSelect.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("1.png"))));
				btnAssociationLine.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("2.png"))));
				btnGeneraliztionLine.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("3_1.png"))));
				btnCompositionLine.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("4.png"))));
				btnClass.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("5.png"))));
				btnUseCase.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("6.png"))));

				System.out.println("Mode is : "+btnMode);
			});
			
			btnCompositionLine.setOnMousePressed(e -> {
				btnMode=4;
				btnSelect.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("1.png"))));
				btnAssociationLine.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("2.png"))));
				btnGeneraliztionLine.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("3.png"))));
				btnCompositionLine.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("4_1.png"))));
				btnClass.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("5.png"))));
				btnUseCase.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("6.png"))));

				System.out.println("Mode is : "+btnMode);
			});
			
			btnClass.setOnMouseClicked(e -> {
				btnMode=5;
				btnSelect.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("1.png"))));
				btnAssociationLine.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("2.png"))));
				btnGeneraliztionLine.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("3.png"))));
				btnCompositionLine.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("4.png"))));
				btnClass.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("5_1.png"))));
				btnUseCase.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("6.png"))));

				System.out.println("Mode is : "+btnMode);
				canvas.setOnMousePressed(e1 ->{
					x1 = mousePress.getX(e1);
					y1 = mousePress.getY(e1);
					canvas.drawRect();
				});
				canvas.setOnMouseDragged(e3 ->{
					x2 = mouseDragged.getX(e3);
					y2 = mouseDragged.getY(e3);
				});
				canvas.setOnMouseReleased(e2 ->{
					x2 = mouseRelease.getX(e2);
					y2 = mouseRelease.getY(e2);
				});
			});
			
			btnUseCase.setOnMouseClicked(e -> {
				btnMode=6;
				btnSelect.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("1.png"))));
				btnAssociationLine.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("2.png"))));
				btnGeneraliztionLine.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("3.png"))));
				btnCompositionLine.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("4.png"))));
				btnClass.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("5.png"))));
				btnUseCase.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("6_1.png"))));

				System.out.println("Mode is : "+btnMode);
				canvas.setOnMousePressed(e1 ->{
					x1 = mousePress.getX(e1);
					y1 = mousePress.getY(e1);
					canvas.drawCase();
				});
				canvas.setOnMouseDragged(e3 ->{
					x2 = mouseDragged.getX(e3);
					y2 = mouseDragged.getY(e3);
				});
				canvas.setOnMouseReleased(e2 ->{
					x2 = mouseRelease.getX(e2);
					y2 = mouseRelease.getY(e2);
				});
			});
			
				
			
			pane.add(canvas,1,1);
	
			VBox vBox = new VBox(20);
			vBox.setPadding(new Insets(15, 15, 15, 15));
			vBox.getChildren().addAll(btnSelect, btnAssociationLine, btnGeneraliztionLine, btnCompositionLine, btnClass,
					btnUseCase);
			pane.add(vBox,0,1);
			
			Text coordinate = new Text(10,10,"");
			pane.add(coordinate, 0, 2);
			pane.setOnMouseMoved(e ->{
				coordinate.setText(" x : "+String.valueOf((int)e.getX())+"  y : "+String.valueOf((int)e.getY()));
			});
			root.getChildren().add(pane);
			Scene scene = new Scene(root, WIDTH+150,HEIGHT+50);
			
			

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	class MyClass extends Rectangle{
		//protected Line line = this.line;
	    
		public MyClass(){
	    	this.setStroke(Color.BLACK);
	    	this.setFill(Color.RED);
	    	this.setX(x1);
	    	this.setY(y1);
	    	this.setWidth(100);
	    	this.setHeight(120);
		}
		int getX1(){
			return x1;
		}
		int getY1(){
			return y1;
		}
		int getX2(){
			return x2;
		}
		int getY2(){
			return y2;
		}
		
	}
	
	class MyCase extends Ellipse{
		
		public MyCase(){
	    	this.setStroke(Color.BLACK);
	    	this.setFill(Color.RED);
	    	this.setCenterX(x1);
	    	this.setCenterY(y1);
	    	this.setRadiusX(60);
	    	this.setRadiusY(40);
		}
		int getCenterX1(){
			return x1;
		}
		int getCenterY1(){
			return y1;
		}
		int getRadiusX1(){
			return 60;
		}
		int getRadiusY1(){
			return 40;
		}

	}
	
	class MyLine extends Line{
		
		public MyLine(){
	    	this.setStrokeWidth(2);
	    	this.setStartX(x1);
	    	this.setStartY(y1);
	    	this.setEndX(x1);
	    	this.setEndY(y1);
		}
		int getX1(){
			return x1;
		}
		int getY1(){
			return y1;
		}
		int getX2(){
			return x2;
		}
		int getY2(){
			return y2;
		}

	}

	
	class MyPane extends Pane {
	    /*
		protected Line line;
	    protected Rectangle rect;
	    protected Ellipse ellipse;
	    protected Pane ptemp ;
	    */
		protected MyClass myClass ;
		protected MyCase myCase;
		protected MyLine myLine;
	   // public Line getLine(){
	   // 	return this.line;
	   // }
	    
	   // public void setLine(Line line){
	   /// 	this.line = line;
	   // }
	    
	    public MyPane(double width, double height){
	    	super.setPrefSize(width, height);
	    	//super.setStyle("-fx-background-color: #2f4f4f");
	    }
	    
	    protected void drawLine(){
	    	myLine = new MyLine();
	    	/*
	    	line = new Line();
	    	line.setStrokeWidth(2);
	    	line.setStartX(x1);
	    	line.setStartY(y1);
	    	line.setEndX(x1);
	    	line.setEndY(y1);
	    	*/
	    	
	    	super.getChildren().add(myLine);
	    }
	    protected void tuneLine(){
	    	myLine.setEndX(x2);
	    	myLine.setEndY(y2);
	    }
	    
	    protected void drawRect(){
			myClass = new MyClass();
	    	//ptemp = new Pane();
	    	//ptemp.setStyle("-fx-background-color: black;");\
	    	/*
	    	rect = new Rectangle();
	    	rect.setStroke(Color.BLACK);
	    	rect.setFill(Color.RED);
	    	rect.setX(x1);
	    	rect.setY(y1);
	    	rect.setWidth(100);
	    	rect.setHeight(120);
	    	*/
	    	//ptemp.setLayoutX(x1);
	    	//ptemp.setLayoutY(y1);
	    	//ptemp.getChildren().add(rect);
	    	super.getChildren().add(myClass);
	    }
	    protected void drawCase(){
	    	myCase = new MyCase();
	    	//ptemp = new Pane();
	    	//ptemp.setStyle("-fx-background-color: black;");
	    	/*
	    	ellipse = new Ellipse();
	    	ellipse.setStroke(Color.BLACK);
	    	ellipse.setFill(Color.RED);
	    	ellipse.setCenterX(x1);
	    	ellipse.setCenterY(y1);
	    	ellipse.setRadiusX(60);
	    	ellipse.setRadiusY(40);
	    	*/
	    	//ptemp.setLayoutX(x1);
	    	//ptemp.setLayoutY(y1);
	    	//ptemp.getChildren().add(ellipse);
	    	super.getChildren().add(myCase);

	    }
	}
	
	class mousePress implements EventHandler<MouseEvent>{
;
		@Override
		public void handle(MouseEvent event){
          if(event.getTarget()instanceof Pane){
          }else{

        	orgSceneX = event.getSceneX();
	        orgSceneY = event.getSceneY();
	            
	        Node p = ((Node) (event.getTarget()));
	
	        orgTranslateX = p.getTranslateX();
	        orgTranslateY = p.getTranslateY();
            
            
          }
		}
		
		int getX(MouseEvent event){
			return (int) event.getX();
		}
		
		int getY(MouseEvent event){
			return (int) event.getY();
		}
	}
	class mouseDragged implements EventHandler<MouseEvent>{

		@Override
		public void handle(MouseEvent event) {
			// TODO Auto-generated method stub

            double offsetX = event.getSceneX() - orgSceneX;
            double offsetY = event.getSceneY() - orgSceneY;

            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;
          //  Pane p = ((Pane) (event.getSource()));
  
            if(event.getTarget()instanceof Pane){
            	
            }
            else{
            	Node p = ((Node) (event.getTarget()));

            	p.setTranslateX(newTranslateX);
            	p.setTranslateY(newTranslateY);
            }
            
		}
		int getX(MouseEvent event){
			return (int) event.getX();
		}
		
		int getY(MouseEvent event){
			return (int) event.getY();
		}
	}
	class mouseReleased implements EventHandler<MouseEvent>{

		@Override
		public void handle(MouseEvent event) {
			// TODO Auto-generated method stub

		}
		
		int getX(MouseEvent event){
			return (int) event.getX();
		}
		
		int getY(MouseEvent event){
			return (int) event.getY();
		}
	}


	public static void main(String[] args) {
		launch(args);
	}
}
