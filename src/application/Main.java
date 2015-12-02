package application;


import java.awt.image.renderable.RenderContext;
import java.util.ArrayList;

import javax.sound.sampled.LineEvent;

import org.w3c.dom.css.Rect;

import com.sun.javafx.scene.BoundsAccessor;

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
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Main extends Application {

	public int idObjIndex = 0;
	public int idLineIndex = 0;
	int x1, x2, y1, y2;
	double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    int btnMode;
    private static ArrayList<ObjPane> objPanList = new ArrayList<ObjPane>();
    private static ArrayList<MyLine> lineList = new ArrayList<MyLine>();
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
			CheckMenuItem groupOpt = new CheckMenuItem("Group");
			CheckMenuItem unGroupOpt = new CheckMenuItem("UnGroup");
			CheckMenuItem renameOpt = new CheckMenuItem("Change Object Name");
			CheckMenuItem exitOpt =  new CheckMenuItem("Exit");
			menuFile.getItems().add(exitOpt);
			
			exitOpt.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
				
			});
			renameOpt.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					BorderPane renamePane = new BorderPane();
					renamePane.setPadding(new Insets(20,10,20,10));
					TextArea renameText = new TextArea();
					renameText.setPrefSize(200, 20);
					HBox btnHbox = new HBox(120);
					Button confirmBtn = new Button("OK");
					Button cancelBtn = new Button("Cancel");
					btnHbox.setPadding(new Insets(5,0,0,0));
					btnHbox.getChildren().addAll(confirmBtn,cancelBtn);
					renamePane.setCenter(renameText);
					renamePane.setBottom(btnHbox);;


					Scene renameScene = new Scene(renamePane);
					Stage renameWindow = new Stage();
					renameWindow.setScene(renameScene);
					renameWindow.show();
					
					cancelBtn.setOnMouseClicked(e ->{
						renameWindow.hide();
					});

				}
				
			});
			
			menuEdit.getItems().addAll(groupOpt,unGroupOpt,renameOpt);
			menuBar.getMenus().addAll(menuFile,menuEdit);
			pane.add(menuBar, 0, 0);
		
			//System.out.println(groupOpt.get);
			MyPane canvas = new MyPane(WIDTH,HEIGHT);
			canvas.setStyle("-fx-background-color: #9D9D9D");
	
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
					System.out.println("press event ! ");
					mousePress.handle(e2);

					if(e2.getTarget() instanceof Main.ObjPane){
						
						ObjPane p = (ObjPane)(e2.getTarget());
						
						System.out.println("size : "+p.getChildren().size());
						
						if(canvas.getChildren().size()>0){
							for(int j=0;j<canvas.getChildren().size();j++){
								if(canvas.getChildren().get(j) instanceof Main.ObjPane){
									ObjPane delp = (ObjPane)(canvas.getChildren().get(j));
									if(delp.getChildren().size()==7){
										int deli=4;
										while(deli!=0){
											delp.getChildren().remove(3);
											deli--;
										}
									}
									else if(delp.getChildren().size()==5){
										int i = 4;
										while(i!=0){
										delp.getChildren().remove(1);
										i--;
										}
									}
									
								}
							}
						}
						

						canvas.getChildren().clear();
						
						for(int i=0;i<objPanList.size();i++){
							canvas.getChildren().add(objPanList.get(i));
						}
						for(int i=0;i<lineList.size();i++){
							canvas.getChildren().add(lineList.get(i));
						}
						

						double x1 = p.getLayoutX();
						double y1 = p.getLayoutY();
						int obj = checkObj(x1, y1);
						//System.out.println("###"+obj);
						objPanList.get(obj).setSelect(true);
						
						//set other false
						for(int i =0;i<objPanList.size();i++){
							if(i!=obj){
								objPanList.get(i).setSelect(false);
								//System.out.println("in atleas : "+i);
							}
						}
						
						if(objPanList.get(obj).getSelect()==true){
							Rectangle rcp1 = new Rectangle(p.getcp1x(),p.getcp1y(),4,4);
							Rectangle rcp2 = new Rectangle(p.getcp2x(),p.getcp2y(),4,4);
							Rectangle rcp3 = new Rectangle(p.getcp3x(),p.getcp3y(),4,4);
							Rectangle rcp4 = new Rectangle(p.getcp4x(),p.getcp4y(),4,4);
	
							rcp1.setFill(Color.BLUE);
							rcp2.setFill(Color.BLUE);
							rcp3.setFill(Color.BLUE);
							rcp4.setFill(Color.BLUE);
	
							p.getChildren().addAll(rcp1,rcp2,rcp3,rcp4);
						}

					}
					
					//if press on the balnk space
					if(e2.getTarget() instanceof Main.MyPane){
						
						//set all false
						for(int i =0;i<objPanList.size();i++){
							objPanList.get(i).setSelect(false);
							//System.out.println("in atleas : "+i);
							
						}
						

						if(canvas.getChildren().size()>0){
							for(int j=0;j<canvas.getChildren().size();j++){
								if(canvas.getChildren().get(j) instanceof Main.ObjPane){
									ObjPane delp = (ObjPane)(canvas.getChildren().get(j));
									if(delp.getChildren().size()==7){
										int deli=4;
										while(deli!=0){
											delp.getChildren().remove(3);
											deli--;
										}
									}
									else if(delp.getChildren().size()==5){
										int i = 4;
										while(i!=0){
										delp.getChildren().remove(1);
										i--;
										}
									}
									
								}
							}
						}
						
						
						canvas.getChildren().clear();
						
						for(int i=0;i<objPanList.size();i++){
							canvas.getChildren().add(objPanList.get(i));
						}
						for(int i=0;i<lineList.size();i++){
							canvas.getChildren().add(lineList.get(i));
						}
					}
				});
				
				canvas.setOnMouseDragged(e3 ->{
					mouseDragged.handle(e3);

				});
				
				canvas.setOnMouseReleased(e1 ->{

					mouseRelease.handle(e1);
					//canvas.getChildren().clear();
					
					if(e1.getTarget() instanceof Main.ObjPane){
						ObjPane p = (ObjPane)(e1.getTarget());
						/*
						Rectangle rcp1 = new Rectangle(p.getcp1x(),p.getcp1y(),4,4);
						Rectangle rcp2 = new Rectangle(p.getcp2x(),p.getcp2y(),4,4);
						Rectangle rcp3 = new Rectangle(p.getcp3x(),p.getcp3y(),4,4);
						Rectangle rcp4 = new Rectangle(p.getcp4x(),p.getcp4y(),4,4);

						rcp1.setFill(Color.BLUE);
						rcp2.setFill(Color.BLUE);
						rcp3.setFill(Color.BLUE);
						rcp4.setFill(Color.BLUE);

						p.getChildren().addAll(rcp1,rcp2,rcp3,rcp4);
						*/
						if(p.getCp1Linked()==true){
							/*
							 * fuck this idLineIndex
							 */
							if(p.getCp1LinkedSE()== false){
								int thisLineId = p.getCp1LineId();
	
								MyLine l = (MyLine) (lineList.get(thisLineId));
															
								lineList.remove(thisLineId);
								MyLine newLine = new MyLine(p.getcp1x(), p.getcp1y());
								System.out.println("------"+thisLineId);
	
								lineList.add(thisLineId, newLine);
								lineList.get(thisLineId).setStartX(p.cp1x+p.getX1());
								lineList.get(thisLineId).setStartY(p.cp1y+p.getY1());
								lineList.get(thisLineId).setEndX(l.lx2);
								lineList.get(thisLineId).setEndY(l.ly2);
							
								lineList.get(thisLineId).setX1(p.cp1x+p.getX1());
								lineList.get(thisLineId).setY1(p.cp1y+p.getY1());
								lineList.get(thisLineId).setX2(l.lx2);
								lineList.get(thisLineId).setY2(l.ly2);
								//System.out.print("!!!!!!!!! "+l.lx2);
								//System.out.print("!!!!!!!!! "+l.ly2);
								canvas.getChildren().clear();
								for(int i=0;i<objPanList.size();i++){
									canvas.getChildren().add(objPanList.get(i));
								}
								for(int i=0;i<lineList.size();i++){
									canvas.getChildren().add(lineList.get(i));
								}
								//System.out.println(lineList.get(thisLineId).lx1);
								//System.out.println(lineList.get(thisLineId).ly1);
								
								
								//System.out.println("----1----");
							}
							else{
								int thisLineId = p.getCp1LineId();
								
								MyLine l = (MyLine) (lineList.get(thisLineId));
															
								lineList.remove(thisLineId);
								MyLine newLine = new MyLine(p.getcp1x(), p.getcp1y());
								System.out.println("------"+thisLineId);	
									
								lineList.add(thisLineId, newLine);
								lineList.get(thisLineId).setEndX(p.cp1x+p.getX1());
								lineList.get(thisLineId).setEndY(p.cp1y+p.getY1());
								lineList.get(thisLineId).setStartX(l.lx1);
								lineList.get(thisLineId).setStartY(l.ly1);
							
								lineList.get(thisLineId).setX2(p.cp1x+p.getX1());
								lineList.get(thisLineId).setY2(p.cp1y+p.getY1());
								lineList.get(thisLineId).setX1(l.lx1);
								lineList.get(thisLineId).setY1(l.ly1);
								
								canvas.getChildren().clear();
								for(int i=0;i<objPanList.size();i++){
									canvas.getChildren().add(objPanList.get(i));
								}
								for(int i=0;i<lineList.size();i++){
									canvas.getChildren().add(lineList.get(i));
								}
								//System.out.println(lineList.get(thisLineId).lx1);
								//System.out.println(lineList.get(thisLineId).ly1);
								
								
								//System.out.println("----1----");
							}
						}
						if(p.getCp2Linked()==true){
							/*
							 * fuck this idLineIndex-1 
							 */
							if(p.getCp2LinkedSE()==false){
								int thisLineId = p.getCp2LineId();
								System.out.println("---"+p.getCp2LineId());
	
								MyLine l = (MyLine) (lineList.get(thisLineId));
															
								lineList.remove(thisLineId);
								MyLine newLine = new MyLine(p.getcp2x(), p.getcp2y());
	
								lineList.add(thisLineId, newLine);
								lineList.get(thisLineId).setStartX(p.cp2x+p.getX1());
								lineList.get(thisLineId).setStartY(p.cp2y+p.getY1());
								lineList.get(thisLineId).setEndX(l.lx2);
								lineList.get(thisLineId).setEndY(l.ly2);
							
								lineList.get(thisLineId).setX1(p.cp2x+p.getX1());
								lineList.get(thisLineId).setY1(p.cp2y+p.getY1());
								lineList.get(thisLineId).setX2(l.lx2);
								lineList.get(thisLineId).setY2(l.ly2);
								
								canvas.getChildren().clear();
								for(int i=0;i<objPanList.size();i++){
									canvas.getChildren().add(objPanList.get(i));
								}
								for(int i=0;i<lineList.size();i++){
									canvas.getChildren().add(lineList.get(i));
								}
								//System.out.println(lineList.get(thisLineId).lx1);
								//System.out.println(lineList.get(thisLineId).ly1);
								
								//System.out.println("----2----");
							}
							else{
								int thisLineId = p.getCp2LineId();
								System.out.println("---"+p.getCp2LineId());

								MyLine l = (MyLine) (lineList.get(thisLineId));
															
								lineList.remove(thisLineId);
								MyLine newLine = new MyLine(p.getcp2x(), p.getcp2y());

								lineList.add(thisLineId, newLine);
								lineList.get(thisLineId).setEndX(p.cp2x+p.getX1());
								lineList.get(thisLineId).setEndY(p.cp2y+p.getY1());
								lineList.get(thisLineId).setStartX(l.lx1);
								lineList.get(thisLineId).setStartY(l.ly1);
							
								lineList.get(thisLineId).setX2(p.cp2x+p.getX1());
								lineList.get(thisLineId).setY2(p.cp2y+p.getY1());
								lineList.get(thisLineId).setX1(l.lx1);
								lineList.get(thisLineId).setY1(l.ly1);
								
								canvas.getChildren().clear();
								for(int i=0;i<objPanList.size();i++){
									canvas.getChildren().add(objPanList.get(i));
								}
								for(int i=0;i<lineList.size();i++){
									canvas.getChildren().add(lineList.get(i));
								}
								//System.out.println(lineList.get(thisLineId).lx1);
								//System.out.println(lineList.get(thisLineId).ly1);
								
								System.out.println("----2----");
							}
						}
						if(p.getCp3Linked()==true){
							/*
							 * fuck this idLineIndex-1 
							 */
							if(p.getCp3LinkedSE()==false){
								int thisLineId = p.getCp3LineId();
								System.out.println("---"+p.getCp3LineId());
	
								MyLine l = (MyLine) (lineList.get(thisLineId));
															
								lineList.remove(thisLineId);
								MyLine newLine = new MyLine(p.getcp3x(), p.getcp3y());
								System.out.println("------"+thisLineId);
	
								lineList.add(thisLineId, newLine);
								lineList.get(thisLineId).setStartX(p.cp3x+p.getX1());
								lineList.get(thisLineId).setStartY(p.cp3y+p.getY1());
								lineList.get(thisLineId).setEndX(l.lx2);
								lineList.get(thisLineId).setEndY(l.ly2);
							
								lineList.get(thisLineId).setX1(p.cp3x+p.getX1());
								lineList.get(thisLineId).setY1(p.cp3y+p.getY1());
								lineList.get(thisLineId).setX2(l.lx2);
								lineList.get(thisLineId).setY2(l.ly2);
								
								canvas.getChildren().clear();
								for(int i=0;i<objPanList.size();i++){
									canvas.getChildren().add(objPanList.get(i));
								}
								for(int i=0;i<lineList.size();i++){
									canvas.getChildren().add(lineList.get(i));
								}
								//System.out.println(lineList.get(thisLineId).lx1);
								//System.out.println(lineList.get(thisLineId).ly1);
								
								//System.out.println("----3----");
							}
							else{
								int thisLineId = p.getCp3LineId();
								System.out.println("---"+p.getCp3LineId());
	
								MyLine l = (MyLine) (lineList.get(thisLineId));
															
								lineList.remove(thisLineId);
								MyLine newLine = new MyLine(p.getcp3x(), p.getcp3y());
								System.out.println("------"+thisLineId);
	
								lineList.add(thisLineId, newLine);
								lineList.get(thisLineId).setEndX(p.cp3x+p.getX1());
								lineList.get(thisLineId).setEndY(p.cp3y+p.getY1());
								lineList.get(thisLineId).setStartX(l.lx1);
								lineList.get(thisLineId).setStartY(l.ly1);
							
								lineList.get(thisLineId).setX2(p.cp3x+p.getX1());
								lineList.get(thisLineId).setY2(p.cp3y+p.getY1());
								lineList.get(thisLineId).setX1(l.lx1);
								lineList.get(thisLineId).setY1(l.ly1);
								
								canvas.getChildren().clear();
								for(int i=0;i<objPanList.size();i++){
									canvas.getChildren().add(objPanList.get(i));
								}
								for(int i=0;i<lineList.size();i++){
									canvas.getChildren().add(lineList.get(i));
								}
								//System.out.println(lineList.get(thisLineId).lx1);
								//System.out.println(lineList.get(thisLineId).ly1);
								
								//System.out.println("----3----");
							}
						}
						if(p.getCp4Linked()==true){
							/*
							 * fuck this idLineIndex-1 
							 */
							if(p.getCp4LinkedSE()==false){
								int thisLineId = p.getCp4LineId();
								System.out.println("---"+p.getCp4LineId());
	
								MyLine l = (MyLine) (lineList.get(thisLineId));
															
								lineList.remove(thisLineId);
								MyLine newLine = new MyLine(p.getcp4x(), p.getcp4y());
								System.out.println("------"+thisLineId);
	
								lineList.add(thisLineId, newLine);
								lineList.get(thisLineId).setStartX(p.cp4x+p.getX1());
								lineList.get(thisLineId).setStartY(p.cp4y+p.getY1());
								lineList.get(thisLineId).setEndX(l.lx2);
								lineList.get(thisLineId).setEndY(l.ly2);
							
								lineList.get(thisLineId).setX1(p.cp4x+p.getX1());
								lineList.get(thisLineId).setY1(p.cp4y+p.getY1());
								lineList.get(thisLineId).setX2(l.lx2);
								lineList.get(thisLineId).setY2(l.ly2);
								
								canvas.getChildren().clear();
								for(int i=0;i<objPanList.size();i++){
									canvas.getChildren().add(objPanList.get(i));
								}
								for(int i=0;i<lineList.size();i++){
									canvas.getChildren().add(lineList.get(i));
								}
								//System.out.println(lineList.get(thisLineId).lx1);
								//System.out.println(lineList.get(thisLineId).ly1);
								
								System.out.println("----4----");
							}
							else{
								int thisLineId = p.getCp4LineId();
								System.out.println("---"+p.getCp4LineId());
	
								MyLine l = (MyLine) (lineList.get(thisLineId));
															
								lineList.remove(thisLineId);
								MyLine newLine = new MyLine(p.getcp4x(), p.getcp4y());
								System.out.println("------"+thisLineId);
	
								lineList.add(thisLineId, newLine);
								lineList.get(thisLineId).setEndX(p.cp4x+p.getX1());
								lineList.get(thisLineId).setEndY(p.cp4y+p.getY1());
								lineList.get(thisLineId).setStartX(l.lx1);
								lineList.get(thisLineId).setStartY(l.ly1);
							
								lineList.get(thisLineId).setX2(p.cp4x+p.getX1());
								lineList.get(thisLineId).setY2(p.cp4y+p.getY1());
								lineList.get(thisLineId).setX1(l.lx1);
								lineList.get(thisLineId).setY1(l.ly1);
								
								canvas.getChildren().clear();
								for(int i=0;i<objPanList.size();i++){
									canvas.getChildren().add(objPanList.get(i));
								}
								for(int i=0;i<lineList.size();i++){
									canvas.getChildren().add(lineList.get(i));
								}
								//System.out.println(lineList.get(thisLineId).lx1);
								//System.out.println(lineList.get(thisLineId).ly1);
								
								System.out.println("----4----");
							}
						}
					}
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


				if(canvas.getChildren().size()>0){
					for(int j=0;j<canvas.getChildren().size();j++){
						if(canvas.getChildren().get(j) instanceof Main.ObjPane){
							ObjPane delp = (ObjPane)(canvas.getChildren().get(j));
							if(delp.getChildren().size()==7){
								int deli=4;
								while(deli!=0){
									delp.getChildren().remove(3);
									deli--;
								}
							}
							else if(delp.getChildren().size()==5){
								int i = 4;
								while(i!=0){
								delp.getChildren().remove(1);
								i--;
								}
							}
							
						}
					}
				}
				
				canvas.getChildren().clear();
				
				for(int i=0;i<objPanList.size();i++){
					canvas.getChildren().add(objPanList.get(i));
				}
				for(int i=0;i<lineList.size();i++){
					canvas.getChildren().add(lineList.get(i));
				}
				/*
				 * draw line 
				 */
				System.out.println("Mode is : "+btnMode);
				canvas.setOnMousePressed(e2 ->{
					
					if(e2.getTarget() instanceof Main.ObjPane){
						ObjPane p = (ObjPane)(e2.getTarget());
						System.out.println("press get target : "+e2.getTarget());

						x1 = mousePress.getX(e2);
						y1 = mousePress.getY(e2);
						
						System.out.println("x1 : " +x1+" y1 :"+y1);
						//System.out.println(checkObj(x1, y1));
						System.out.println("xp1 : " +p.getcp1x()+" yp1 :"+p.getcp1y());

						double distcp1 = Math.hypot((p.getcp1x()+p.getX1())-x1,(p.getcp1y()+p.getY1())-y1);
						double distcp2 = Math.hypot((p.getcp2x()+p.getX1())-x1,(p.getcp2y()+p.getY1())-y1);
						double distcp3 = Math.hypot((p.getcp3x()+p.getX1())-x1,(p.getcp3y()+p.getY1())-y1);
						double distcp4 = Math.hypot((p.getcp4x()+p.getX1())-x1,(p.getcp4y()+p.getY1())-y1);
						double dtemp = Math.min(distcp1, Math.min(distcp2, Math.min(distcp3,distcp4)));
						
						if (dtemp == distcp1){
							canvas.drawLine(p.getcp1x()+p.getX1(),p.getcp1y()+p.getY1());
							p.setCp1Linked(true);
							p.setCp1LineId(canvas.getTheLineId());
							p.setCp1LinedSE(false);
						}else if(dtemp == distcp2){
							canvas.drawLine(p.getcp2x()+p.getX1(),p.getcp2y()+p.getY1());
							p.setCp2Linked(true);
							p.setCp2LineId(canvas.getTheLineId());
							p.setCp2LinedSE(false);

						}else if(dtemp == distcp3){
							canvas.drawLine(p.getcp3x()+p.getX1(),p.getcp3y()+p.getY1());
							p.setCp3Linked(true);
							p.setCp3LineId(canvas.getTheLineId());
							p.setCp3LinedSE(false);

						}else if(dtemp == distcp4){
							canvas.drawLine(p.getcp4x()+p.getX1(),p.getcp4y()+p.getY1());
							p.setCp4Linked(true);
							p.setCp4LineId(canvas.getTheLineId());
							p.setCp4LinedSE(false);

						}
					}

				});
				canvas.setOnMouseDragged(e3 ->{
						x2 = mouseDragged.getX(e3);
						y2 = mouseDragged.getY(e3);
						canvas.dragTuneLine(x2,y2);
	
				});
				canvas.setOnMouseReleased(e1 ->{
					System.out.println("release get target : "+e1.getTarget());
						
					x2 = mouseRelease.getX(e1);
					y2 = mouseRelease.getY(e1);
						
					
					int objno = checkObj(x2, y2);
					System.out.println("@@@@@@@@@@@@@@@@@@"+objno);
					ObjPane p = (ObjPane)(objPanList.get(objno));

					if(objno>0){
						
						double distcp1 = Math.hypot((p.getcp1x()+p.getX1())-x2,(p.getcp1y()+p.getY1())-y2);
						double distcp2 = Math.hypot((p.getcp2x()+p.getX1())-x2,(p.getcp2y()+p.getY1())-y2);
						double distcp3 = Math.hypot((p.getcp3x()+p.getX1())-x2,(p.getcp3y()+p.getY1())-y2);
						double distcp4 = Math.hypot((p.getcp4x()+p.getX1())-x2,(p.getcp4y()+p.getY1())-y2);
		
						double dtemp = Math.min(distcp1, Math.min(distcp2, Math.min(distcp3,distcp4)));
							
						if (dtemp == distcp1){
							canvas.dragTuneLine(p.getcp1x()+p.getX1(),p.getcp1y()+p.getY1());
							p.setCp1Linked(true);
							p.setCp1LineId(canvas.getTheLineId());
							p.setCp1LinedSE(true);
	
						}else if(dtemp == distcp2){
							canvas.dragTuneLine(p.getcp2x()+p.getX1(),p.getcp2y()+p.getY1());
							p.setCp2Linked(true);
							p.setCp2LineId(canvas.getTheLineId());
							p.setCp2LinedSE(true);
						}else if(dtemp == distcp3){
							canvas.dragTuneLine(p.getcp3x()+p.getX1(),p.getcp3y()+p.getY1());
							p.setCp3Linked(true);
							p.setCp3LineId(canvas.getTheLineId());
							p.setCp3LinedSE(true);
						}else if(dtemp == distcp4){
							canvas.dragTuneLine(p.getcp4x()+p.getX1(),p.getcp4y()+p.getY1());
							p.setCp4Linked(true);
							p.setCp4LineId(canvas.getTheLineId());
							p.setCp4LinedSE(true);
						}
					}
					else{
						//canvas.delLine(canvas.getTheLineId());
					}
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


				if(canvas.getChildren().size()>0){
					for(int j=0;j<canvas.getChildren().size();j++){
						if(canvas.getChildren().get(j) instanceof Main.ObjPane){
							ObjPane delp = (ObjPane)(canvas.getChildren().get(j));
							if(delp.getChildren().size()==7){
								int deli=4;
								while(deli!=0){
									delp.getChildren().remove(3);
									deli--;
								}
							}
							else if(delp.getChildren().size()==5){
								int i = 4;
								while(i!=0){
								delp.getChildren().remove(1);
								i--;
								}
							}
							
						}
					}
				}
				
				canvas.getChildren().clear();
				
				for(int i=0;i<objPanList.size();i++){
					canvas.getChildren().add(objPanList.get(i));
				}
				for(int i=0;i<lineList.size();i++){
					canvas.getChildren().add(lineList.get(i));
				}
				/*
				 * draw line 
				 */
				System.out.println("Mode is : "+btnMode);
				canvas.setOnMousePressed(e2 ->{
					if(e2.getTarget() instanceof Main.ObjPane){
						ObjPane p = (ObjPane)(e2.getTarget());
						System.out.println("press get target : "+e2.getTarget());

						x1 = mousePress.getX(e2);
						y1 = mousePress.getY(e2);
						
						System.out.println("x1 : " +x1+" y1 :"+y1);
						//System.out.println(checkObj(x1, y1));
						System.out.println("xp1 : " +p.getcp1x()+" yp1 :"+p.getcp1y());

						double distcp1 = Math.hypot((p.getcp1x()+p.getX1())-x1,(p.getcp1y()+p.getY1())-y1);
						double distcp2 = Math.hypot((p.getcp2x()+p.getX1())-x1,(p.getcp2y()+p.getY1())-y1);
						double distcp3 = Math.hypot((p.getcp3x()+p.getX1())-x1,(p.getcp3y()+p.getY1())-y1);
						double distcp4 = Math.hypot((p.getcp4x()+p.getX1())-x1,(p.getcp4y()+p.getY1())-y1);
						double dtemp = Math.min(distcp1, Math.min(distcp2, Math.min(distcp3,distcp4)));
						
						if (dtemp == distcp1){
							canvas.drawLine(p.getcp1x()+p.getX1(),p.getcp1y()+p.getY1());
							p.setCp1Linked(true);
							p.setCp1LineId(canvas.getTheLineId());
							p.setCp1LinedSE(false);
						}else if(dtemp == distcp2){
							canvas.drawLine(p.getcp2x()+p.getX1(),p.getcp2y()+p.getY1());
							p.setCp2Linked(true);
							p.setCp2LineId(canvas.getTheLineId());
							p.setCp2LinedSE(false);

						}else if(dtemp == distcp3){
							canvas.drawLine(p.getcp3x()+p.getX1(),p.getcp3y()+p.getY1());
							p.setCp3Linked(true);
							p.setCp3LineId(canvas.getTheLineId());
							p.setCp3LinedSE(false);

						}else if(dtemp == distcp4){
							canvas.drawLine(p.getcp4x()+p.getX1(),p.getcp4y()+p.getY1());
							p.setCp4Linked(true);
							p.setCp4LineId(canvas.getTheLineId());
							p.setCp4LinedSE(false);

						}
					}

				});
				canvas.setOnMouseDragged(e3 ->{
						x2 = mouseDragged.getX(e3);
						y2 = mouseDragged.getY(e3);
						canvas.dragTuneLine(x2,y2);
	
				});
				canvas.setOnMouseReleased(e1 ->{
					System.out.println("release get target : "+e1.getTarget());
						
					x2 = mouseRelease.getX(e1);
					y2 = mouseRelease.getY(e1);
						
					int objno = checkObj(x2, y2);
					ObjPane p = (ObjPane)(objPanList.get(objno));
  
					double distcp1 = Math.hypot((p.getcp1x()+p.getX1())-x2,(p.getcp1y()+p.getY1())-y2);
					double distcp2 = Math.hypot((p.getcp2x()+p.getX1())-x2,(p.getcp2y()+p.getY1())-y2);
					double distcp3 = Math.hypot((p.getcp3x()+p.getX1())-x2,(p.getcp3y()+p.getY1())-y2);
					double distcp4 = Math.hypot((p.getcp4x()+p.getX1())-x2,(p.getcp4y()+p.getY1())-y2);
	
					double dtemp = Math.min(distcp1, Math.min(distcp2, Math.min(distcp3,distcp4)));
						
					if (dtemp == distcp1){
						canvas.dragTuneLine(p.getcp1x()+p.getX1(),p.getcp1y()+p.getY1());
						p.setCp1Linked(true);
						p.setCp1LineId(canvas.getTheLineId());
						p.setCp1LinedSE(true);

					}else if(dtemp == distcp2){
						canvas.dragTuneLine(p.getcp2x()+p.getX1(),p.getcp2y()+p.getY1());
						p.setCp2Linked(true);
						p.setCp2LineId(canvas.getTheLineId());
						p.setCp2LinedSE(true);
					}else if(dtemp == distcp3){
						canvas.dragTuneLine(p.getcp3x()+p.getX1(),p.getcp3y()+p.getY1());
						p.setCp3Linked(true);
						p.setCp3LineId(canvas.getTheLineId());
						p.setCp3LinedSE(true);
					}else if(dtemp == distcp4){
						canvas.dragTuneLine(p.getcp4x()+p.getX1(),p.getcp4y()+p.getY1());
						p.setCp4Linked(true);
						p.setCp4LineId(canvas.getTheLineId());
						p.setCp4LinedSE(true);
					}
				});
						
				System.out.println("Mode is : "+btnMode);
			});
			
			btnCompositionLine.setOnMousePressed(e -> {
				btnMode=4;
				//System.out.println("asdfasdfasdfas : "+lineList.size());

				btnSelect.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("1.png"))));
				btnAssociationLine.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("2.png"))));
				btnGeneraliztionLine.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("3.png"))));
				btnCompositionLine.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("4_1.png"))));
				btnClass.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("5.png"))));
				btnUseCase.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("6.png"))));


				if(canvas.getChildren().size()>0){
					for(int j=0;j<canvas.getChildren().size();j++){
						if(canvas.getChildren().get(j) instanceof Main.ObjPane){
							ObjPane delp = (ObjPane)(canvas.getChildren().get(j));
							if(delp.getChildren().size()==7){
								int deli=4;
								while(deli!=0){
									delp.getChildren().remove(3);
									deli--;
								}
							}
							else if(delp.getChildren().size()==5){
								int i = 4;
								while(i!=0){
								delp.getChildren().remove(1);
								i--;
								}
							}
							
						}
					}
				}
				
				canvas.getChildren().clear();
				
				for(int i=0;i<objPanList.size();i++){
					canvas.getChildren().add(objPanList.get(i));
				}
				for(int i=0;i<lineList.size();i++){
					canvas.getChildren().add(lineList.get(i));
				}
				/*
				 * draw line 
				 */
				System.out.println("Mode is : "+btnMode);
				canvas.setOnMousePressed(e2 ->{
					if(e2.getTarget() instanceof Main.ObjPane){
						ObjPane p = (ObjPane)(e2.getTarget());
						System.out.println("press get target : "+e2.getTarget());

						x1 = mousePress.getX(e2);
						y1 = mousePress.getY(e2);
						
						System.out.println("x1 : " +x1+" y1 :"+y1);
						//System.out.println(checkObj(x1, y1));
						System.out.println("xp1 : " +p.getcp1x()+" yp1 :"+p.getcp1y());

						double distcp1 = Math.hypot((p.getcp1x()+p.getX1())-x1,(p.getcp1y()+p.getY1())-y1);
						double distcp2 = Math.hypot((p.getcp2x()+p.getX1())-x1,(p.getcp2y()+p.getY1())-y1);
						double distcp3 = Math.hypot((p.getcp3x()+p.getX1())-x1,(p.getcp3y()+p.getY1())-y1);
						double distcp4 = Math.hypot((p.getcp4x()+p.getX1())-x1,(p.getcp4y()+p.getY1())-y1);
						double dtemp = Math.min(distcp1, Math.min(distcp2, Math.min(distcp3,distcp4)));
						
						if (dtemp == distcp1){
							canvas.drawLine(p.getcp1x()+p.getX1(),p.getcp1y()+p.getY1());
							p.setCp1Linked(true);
							p.setCp1LineId(canvas.getTheLineId());
							p.setCp1LinedSE(false);
						}else if(dtemp == distcp2){
							canvas.drawLine(p.getcp2x()+p.getX1(),p.getcp2y()+p.getY1());
							p.setCp2Linked(true);
							p.setCp2LineId(canvas.getTheLineId());
							p.setCp2LinedSE(false);

						}else if(dtemp == distcp3){
							canvas.drawLine(p.getcp3x()+p.getX1(),p.getcp3y()+p.getY1());
							p.setCp3Linked(true);
							p.setCp3LineId(canvas.getTheLineId());
							p.setCp3LinedSE(false);

						}else if(dtemp == distcp4){
							canvas.drawLine(p.getcp4x()+p.getX1(),p.getcp4y()+p.getY1());
							p.setCp4Linked(true);
							p.setCp4LineId(canvas.getTheLineId());
							p.setCp4LinedSE(false);

						}
					}

				});
				canvas.setOnMouseDragged(e3 ->{
						x2 = mouseDragged.getX(e3);
						y2 = mouseDragged.getY(e3);
						canvas.dragTuneLine(x2,y2);
	
				});
				canvas.setOnMouseReleased(e1 ->{
					System.out.println("release get target : "+e1.getTarget());
						
					x2 = mouseRelease.getX(e1);
					y2 = mouseRelease.getY(e1);
						
					int objno = checkObj(x2, y2);
					ObjPane p = (ObjPane)(objPanList.get(objno));
  
					double distcp1 = Math.hypot((p.getcp1x()+p.getX1())-x2,(p.getcp1y()+p.getY1())-y2);
					double distcp2 = Math.hypot((p.getcp2x()+p.getX1())-x2,(p.getcp2y()+p.getY1())-y2);
					double distcp3 = Math.hypot((p.getcp3x()+p.getX1())-x2,(p.getcp3y()+p.getY1())-y2);
					double distcp4 = Math.hypot((p.getcp4x()+p.getX1())-x2,(p.getcp4y()+p.getY1())-y2);
	
					double dtemp = Math.min(distcp1, Math.min(distcp2, Math.min(distcp3,distcp4)));
						
					if (dtemp == distcp1){
						canvas.dragTuneLine(p.getcp1x()+p.getX1(),p.getcp1y()+p.getY1());
						p.setCp1Linked(true);
						p.setCp1LineId(canvas.getTheLineId());
						p.setCp1LinedSE(true);

					}else if(dtemp == distcp2){
						canvas.dragTuneLine(p.getcp2x()+p.getX1(),p.getcp2y()+p.getY1());
						p.setCp2Linked(true);
						p.setCp2LineId(canvas.getTheLineId());
						p.setCp2LinedSE(true);
					}else if(dtemp == distcp3){
						canvas.dragTuneLine(p.getcp3x()+p.getX1(),p.getcp3y()+p.getY1());
						p.setCp3Linked(true);
						p.setCp3LineId(canvas.getTheLineId());
						p.setCp3LinedSE(true);
					}else if(dtemp == distcp4){
						canvas.dragTuneLine(p.getcp4x()+p.getX1(),p.getcp4y()+p.getY1());
						p.setCp4Linked(true);
						p.setCp4LineId(canvas.getTheLineId());
						p.setCp4LinedSE(true);
					}
				});
						
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


				if(canvas.getChildren().size()>0){
					for(int j=0;j<canvas.getChildren().size();j++){
						if(canvas.getChildren().get(j) instanceof Main.ObjPane){
							ObjPane delp = (ObjPane)(canvas.getChildren().get(j));
							if(delp.getChildren().size()==7){
								int deli=4;
								while(deli!=0){
									delp.getChildren().remove(3);
									deli--;
								}
							}
							else if(delp.getChildren().size()==5){
								int i = 4;
								while(i!=0){
								delp.getChildren().remove(1);
								i--;
								}
							}
							
						}
					}
				}
				
				canvas.getChildren().clear();
				
				for(int i=0;i<objPanList.size();i++){
					canvas.getChildren().add(objPanList.get(i));
				}
				for(int i=0;i<lineList.size();i++){
					canvas.getChildren().add(lineList.get(i));
				}
				
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


				if(canvas.getChildren().size()>0){
					for(int j=0;j<canvas.getChildren().size();j++){
						if(canvas.getChildren().get(j) instanceof Main.ObjPane){
							ObjPane delp = (ObjPane)(canvas.getChildren().get(j));
							if(delp.getChildren().size()==7){
								int deli=4;
								while(deli!=0){
									delp.getChildren().remove(3);
									deli--;
								}
							}
							else if(delp.getChildren().size()==5){
								int i = 4;
								while(i!=0){
								delp.getChildren().remove(1);
								i--;
								}
							}
							
						}
					}
				}
				
				canvas.getChildren().clear();
				
				for(int i=0;i<objPanList.size();i++){
					canvas.getChildren().add(objPanList.get(i));
				}
				for(int i=0;i<lineList.size();i++){
					canvas.getChildren().add(lineList.get(i));
				}
				
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
	
	private int checkObj(double x, double y) {
		for (int i = 0; i < objPanList.size(); i++) {
			if (x >= objPanList.get(i).px1 && x <= objPanList.get(i).px2)
				if (y >= objPanList.get(i).py1 && y <= objPanList.get(i).py2)
					return i;

		}
		return -1;
	}
	
	class MyClass extends Rectangle{
		//protected Line line = this.line;
		int rx1=x1;
		int ry1=y1;
		int rx2=x1+100;
		int ry2=y1+120;
		public MyClass(){
	    	this.setStroke(Color.BLACK);
	    	this.setFill(Color.TRANSPARENT);
	    	this.setX(0);
	    	this.setY(0);
	    	this.setWidth(100);
	    	this.setHeight(120);
		}
		int getX1(){
			return rx1;
		}
		int getY1(){
			return ry1;
		}
		int getX2(){
			return rx2;
		}
		int getY2(){
			return ry2;
		}
		
	}
	
	class MyCase extends Ellipse{
		int ex1=x1;
		int ey1=y1;
		int ex2;
		int ey2;
		int rdx=60;
		int rdy=40;
		int id;

		public MyCase(){
	    	this.setStroke(Color.BLACK);
	    	this.setFill(Color.TRANSPARENT);
	    	this.setCenterX(60);
	    	this.setCenterY(40);
	    	this.setRadiusX(60);
	    	this.setRadiusY(40);
		}
		int getCenterX1(){
			return ex1;
		}
		int getCenterY1(){
			return ey1;
		}
		int getRadiusX1(){
			return 60;
		}
		int getRadiusY1(){
			return 40;
		}
		public void setX1(int x){
			ex1=x;
		}
		public void setY1(int y){
			ey1=y;
		}
		public void setX2(int x){
			ex2=x;
		}
		public void setY2(int y){
			ey2=y;
		}
		void setidLineIndex(int index) {
			id = index;
		}
		int getLineId(){
			return id;
		}
	}
	
	class MyLine extends Line{
		int lx1;
		int ly1;
		int lx2;
		int ly2;
		int id;

		public MyLine(int cpx1,int cpy1){
	    	this.setStrokeWidth(2);
	    	this.setStartX(cpx1);
	    	this.setStartY(cpy1);
	    	this.setEndX(cpx1);
	    	this.setEndY(cpy1);
	    	this.setX1(cpx1);
	    	this.setY1(cpy1);
	    	//this.setDisable(true);
		}
		int getX1(){
			return lx1;
		}
		int getY1(){
			return ly1;
		}
		int getX2(){
			return lx2;
		}
		int getY2(){
			return ly2;
		}
		
		public void setX1(int x){
			lx1=x;
		}
		public void setY1(int y){
			ly1=y;
		}
		public void setX2(int x){
			lx2=x;
		}
		public void setY2(int y){
			ly2=y;
		}
		void setidLineIndex(int index) {
			id = index;
		}
		int getLineId(){
			return id;
		}
	}

	class ObjPane extends Pane{
		int px1 ;
		int py1 ;
		int px2 ;
		int py2 ;
		int id;

		boolean select = false;
		
		boolean cp1Linked = false;
		boolean cp2Linked = false;
		boolean cp3Linked = false;
		boolean cp4Linked = false;
		/*
		 * 0-start
		 * 1-end
		 */
		boolean cp1LindSE;
		boolean cp2LindSE;
		boolean cp3LindSE;
		boolean cp4LindSE;
		
		int cp1LineId;
		int cp2LineId;
		int cp3LineId;
		int cp4LineId;
		
		int cp1x,cp1y,cp2x,cp2y,cp3x,cp3y,cp4x,cp4y;
	
		public ObjPane(){
		}
		public void setX1(int x){
			px1=x;
			cp1x = (((px1+px2)/2)-px1);
			cp1y =  py1-py1;
			cp2x = 	px2-px1;
			cp2y = ((py1+py2)/2)-py1;
			cp3x = ((px1+px2)/2)-px1;
			cp3y =	py2-py1;
			cp4x = 	px1-px1;
			cp4y = ((py1+py2)/2)-py1;
			
		}
		public void setY1(int y){
			py1=y;
			cp1x = (((px1+px2)/2)-px1);
			cp1y =  py1-py1;
			cp2x = 	px2-px1;
			cp2y = ((py1+py2)/2)-py1;
			cp3x = ((px1+px2)/2)-px1;
			cp3y =	py2-py1;
			cp4x = 	px1-px1;
			cp4y = ((py1+py2)/2)-py1;
			
		}
		public void setX2(int x){
			px2=x;
			cp1x = (((px1+px2)/2)-px1);
			cp1y =  py1-py1;
			cp2x = 	px2-px1;
			cp2y = ((py1+py2)/2)-py1;
			cp3x = ((px1+px2)/2)-px1;
			cp3y =	py2-py1;
			cp4x = 	px1-px1;
			cp4y = ((py1+py2)/2)-py1;
			
		}
		public void setY2(int y){
			py2=y;
			cp1x = (((px1+px2)/2)-px1);
			cp1y =  py1-py1;
			cp2x = 	px2-px1;
			cp2y = ((py1+py2)/2)-py1;
			cp3x = ((px1+px2)/2)-px1;
			cp3y =	py2-py1;
			cp4x = 	px1-px1;
			cp4y = ((py1+py2)/2)-py1;
			
		}
		
		void setidObjIndex(int index) {
			id = index;
		}
		
		boolean getSelect(){
			return select;
		}
		
	    int getidObjIndex() {
			return id;
		}
		
		int getX1(){
			return px1;
		}
		int getY1(){
			return py1;
		}
		int getX2(){
			return px2;
		}
		int getY2(){
			return py2;
		}
		
		int getcp1x(){
			return cp1x;
		}
		int getcp1y(){
			return cp1y;
		}
		int getcp2x(){
			return cp2x;
		}
		int getcp2y(){
			return cp2y;
		}
		int getcp3x(){
			return cp3x;
		}
		int getcp3y(){
			return cp3y;
		}
		int getcp4x(){
			return cp4x;
		}
		int getcp4y(){
			return cp4y;
		}
		void setSelect(boolean setSelect){
			select = setSelect;
		}
		
		void setCp1Linked(boolean set){
			cp1Linked = set;
		}
		void setCp2Linked(boolean set){
			cp2Linked = set;
		}
		void setCp3Linked(boolean set){
			cp3Linked = set;
		}
		void setCp4Linked(boolean set){
			cp4Linked = set;
		}
		boolean getCp1Linked(){
			return cp1Linked;
		}
		boolean getCp2Linked(){
			return cp2Linked;
		}
		boolean getCp3Linked(){
			return cp3Linked;
		}
		boolean getCp4Linked(){
			return cp4Linked;
		}
		void setCp1LineId(int id){
			cp1LineId = id;
		}
		void setCp2LineId(int id){
			cp2LineId = id;
		}
		void setCp3LineId(int id){
			cp3LineId = id;
		}
		void setCp4LineId(int id){
			cp4LineId = id;
		}
		int getCp1LineId(){
			return cp1LineId;
		}
		int getCp2LineId(){
			return cp2LineId;
		}
		int getCp3LineId(){
			return cp3LineId;
		}
		int getCp4LineId(){
			return cp4LineId;
		}
		boolean getCp1LinkedSE(){
			return cp1LindSE;
		}
		boolean getCp2LinkedSE(){
			return cp2LindSE;
		}
		boolean getCp3LinkedSE(){
			return cp3LindSE;
		}
		boolean getCp4LinkedSE(){
			return cp4LindSE;
		}
		void setCp1LinedSE(boolean se){
			cp1LindSE=se;
		}
		void setCp2LinedSE(boolean se){
			cp2LindSE=se;
		}
		void setCp3LinedSE(boolean se){
			cp3LindSE=se;
		}
		void setCp4LinedSE(boolean se){
			cp4LindSE=se;
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
		protected ObjPane objPane;
		protected MyLine myComLine;
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
	    
	    protected void drawLine(int cpx1,int cpy1){
	    	myLine = new MyLine(cpx1,cpy1);
	    	myLine.setidLineIndex(idLineIndex);
	    	idLineIndex++;
	    	lineList.add(myLine);
	    	super.getChildren().add(myLine);
	    }
	    protected void dragTuneLine(int cpx2,int cpy2){
	    	myLine.setEndX(cpx2);
	    	myLine.setEndY(cpy2);
	    	myLine.setX2(cpx2);
	    	myLine.setY2(cpy2);
	    }
	    protected void delLine(int index){
	    	lineList.remove(index);
	    	
	    }
	    int getTheLineId(){
	    	return myLine.getLineId();
	    }
	    /*
	    protected void tuneLineStart(int cpx2,int cpy2){
	    	myLine.setEndX(cpx2);
	    	myLine.setEndY(cpy2);
	    }
	    protected void tuneLineEnd(int cpx2,int cpy2){
	    	myLine.setEndX(cpx2);
	    	myLine.setEndY(cpy2);
	    }*/
	    
	    protected void drawRect(){
			myClass = new MyClass();
			objPane = new ObjPane();
			
			objPane.setidObjIndex(idObjIndex);
			idObjIndex++;
			
			objPane.setLayoutX(myClass.getX1());
			objPane.setLayoutY(myClass.getY1());
			
			objPane.setX1(myClass.getX1());
			objPane.setY1(myClass.getY1());
			objPane.setX2(myClass.getX1()+100);
			objPane.setY2(myClass.getY1()+120);		
			
			Line midline1 = new Line();
			midline1.setStrokeWidth(2);
			midline1.setStroke(Color.BLACK);
			
			midline1.setStartX(0);
			midline1.setStartY(40);
			midline1.setEndX(100);
			midline1.setEndY(40);
			
			Line midline2 = new Line();
			midline2.setStrokeWidth(2);
			midline2.setStroke(Color.BLACK);
			
			midline2.setStartX(0);
			midline2.setStartY(80);
			midline2.setEndX(100);
			midline2.setEndY(80);
			
			//objPane.setStyle("-fx-background-color: black;");
			
			midline1.setDisable(true);
			midline2.setDisable(true);
			myClass.setDisable(true);
			objPane.getChildren().add(myClass);
			objPane.getChildren().add(midline1);
			objPane.getChildren().add(midline2);
		
			objPanList.add(objPane);

	    	super.getChildren().add(objPane);
	    }
	    protected void drawCase(){
	    	myCase = new MyCase();
			objPane = new ObjPane();
			objPane.setidObjIndex(idObjIndex);
			idObjIndex++;
			//objPane.setStyle("-fx-background-color: black;");
			objPane.setLayoutX(myCase.getCenterX1());
			objPane.setLayoutY(myCase.getCenterY1());
			
			objPane.setX1(myCase.getCenterX1()-myCase.getRadiusX1());
			objPane.setY1(myCase.getCenterY1()-myCase.getRadiusY1());
			objPane.setX2(myCase.getCenterX1()+myCase.getRadiusX1());
			objPane.setY2(myCase.getCenterY1()+myCase.getRadiusY1());
			
			myCase.setDisable(true);
			objPane.getChildren().add(myCase);
			objPanList.add(objPane);

	    	super.getChildren().add(objPane);

	    }
	    protected void drawComLine(int cpx1,int cpy1){
	    	myComLine = new MyLine(cpx1,cpy1);
	    	myComLine.setidLineIndex(idLineIndex);
	    	idLineIndex++;
	    	lineList.add(myComLine);
	    	super.getChildren().add(myComLine);
	    }
	    protected void dragTuneComLine(int cpx2,int cpy2){
	    	myComLine.setEndX(cpx2);
	    	myComLine.setEndY(cpy2);
	    	myComLine.setX2(cpx2);
	    	myComLine.setY2(cpy2);
	    }
	    protected void releaseComLine(int cpx2,int cpy2){
	    	myComLine.setEndX(cpx2);
	    	myComLine.setEndY(cpy2);
	    	myComLine.setX2(cpx2);
	    	myComLine.setY2(cpy2);
	    }

	}
	
	class mousePress implements EventHandler<MouseEvent>{
;
		@Override
		public void handle(MouseEvent event){
		
            // only move objpane

			if(event.getTarget()instanceof Main.ObjPane){
	          	orgSceneX = event.getX();
	  	        orgSceneY = event.getY();
	  	        //int disx = (int) ((event.getSceneX())-(event.getSceneX()));
	  	       // int disy = (int) ((event.getSceneY())-(event.getSceneY()));

	  	        System.out.println(orgSceneX);
	  	        System.out.println(orgSceneY);
	  	        System.out.println("x "+event.getX());
	  	        System.out.println("y "+event.getY());

	  	        ObjPane p = ((ObjPane) (event.getTarget()));
	  	        
	  	        orgTranslateX = p.getLayoutX();
	  	        orgTranslateY = p.getLayoutY();  
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
			
            double offsetX = event.getX() - orgSceneX;
            double offsetY = event.getY() - orgSceneY;

            //double newTranslateX = orgTranslateX + offsetX;
            //double newTranslateY = orgTranslateY + offsetY;
          
            // only move objpane
			if(event.getTarget()instanceof Main.ObjPane){
				
				ObjPane p = ((ObjPane) (event.getTarget()));

				p.setX1((int)orgTranslateX+(int)offsetX);
				p.setY1((int)orgTranslateY+(int)offsetY);
				p.setX2((int)(orgTranslateX+(int)offsetX+p.getWidth()-4));
				p.setY2((int)(orgTranslateY+(int)offsetY+p.getHeight()-4));
				//p.setStyle("-fx-background-color: black;");
				
			
		    	p.setLayoutX(orgTranslateX+offsetX);
		    	p.setLayoutY(orgTranslateY+offsetY);
				//p.setTranslateX(newTranslateX);
            	//p.setTranslateY(newTranslateY);
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