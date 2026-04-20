package application;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.GridPane;

import javafx.geometry.Pos;
import javafx.geometry.Insets;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.event.Event;

public class SpielFeld extends BorderPane
{     
      /* Klasse, die auf einen Zug reagiert und diesen auswertet */
       private Spiel spiel;
	
	// Layout-Container zur Aufnahme der Spielanzeige	
       private HBox labelBox ;
       /* Layout-Container zur Aufnahme der Spaltenauswahl-Knoepfe 
          und der 42 Spielsteine */
       private GridPane gridPanel;
       // Layout-Container zur Aufnahme des "NeuenSpiel"-Knopfes
       private HBox buttonBox;
        
       // Feld zur Aufnahme der 7 SpaltenAuswahl-Knoepfe
       private Button[] button = new Button[7];
       // Feld zur Aufnahme der 42 Spielsteine
       private Label[] field = new Label[42];
       
       // Spielanzeige-Label
       private Label display;
       // "NeuesSpiel"-Knopf
       private Button neuesSpiel;
       
       /* Zusatzaufgabe: Hilfe-Knopf */
       
       private Button help;
        
       // Image f r den Spaltenauswahl-Knopf
       private Image user = new Image(getClass().getResourceAsStream("/recources/user.png"));
       
       // Images f r die Steine gruen, rot und unbesetzt
       private Image green = new Image(getClass().getResourceAsStream("/recources/green.png"));
       private Image red =  new Image(getClass().getResourceAsStream("/recources/red.png"));
       private Image grey = new Image(getClass().getResourceAsStream("/recources/grau.png"));
       
       // Farbkonstanten
       private static final int ROT=1;
       private static final int GRUEN=2;
        
       /* Konstruktor: erzeugt das Spielfeld */     
        public SpielFeld()
       {
        	spiel = new Spiel(this);
        	
        	// Layout-Klasse HBox erzeugen und Spielanzeige-Label einsetzen
        	display = new Label("Rot beginnt");
        	labelBox = new HBox();
        	// Hintergrundfarbe der HBox setzen
                labelBox.setStyle("-fx-background-color: red;");
                // Label zur HBox hinzzufuegen
                labelBox.getChildren().add(display);
                // zentrale Anordnung des Labels auf HBox
                labelBox.setAlignment(Pos.CENTER);
                /* Erzeugung eines leeren Randes um das Label,
                   Insets definiert die 4 Abstaende im Uhrzeigersinn: oben, rechts, unten, links */
                labelBox.setMargin( display, new Insets(6.0, 6.0 , 6.0, 6.0));
                // HBox wird in die TopRegion des BorderPanes gesetzt
                this.setTop(labelBox);
                
                /*Layout-Klasse GridPane zur Aufnahme der SpaltenAuswahl-Kn pfe
                  und der Spielsteine erzeugen */
                gridPanel = new GridPane();
                gridPanel.setStyle("-fx-background-color: black;");
                for (int i = 0; i < 7; i++) {
                    ColumnConstraints colConst = new ColumnConstraints();
                    colConst.setPercentWidth(100.0 / 7); // Each column takes equal percentage
                    colConst.setFillWidth(true);
                    gridPanel.getColumnConstraints().add(colConst);
                }
                for (int i = 0; i < 7; i++) {
                    RowConstraints rowConst = new RowConstraints();
                    rowConst.setPercentHeight(100.0 / 7); // Each row takes equal percentage
                    rowConst.setFillHeight(true);
                    gridPanel.getRowConstraints().add(rowConst);
                }
                //  Spaltenauswahl-Kn pfe f r die 1. Zeile
                // i entspricht SpaltenNummer
                for(int i = 0; i < 7; i++)
                {
                    ImageView userView = new ImageView(user);
                    userView.setFitWidth(50);
                    userView.setFitHeight(50);
                    userView.setPreserveRatio(true);
                    
                    button[i] = new Button("", userView);
                    button[i].setOnAction( e -> spiel.waehleSpalte(e));
                    button[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                    button[i].setPrefSize(80, 80);
                    gridPanel.add(button[i], i, 0);
                }
                
                // Spielsteine 
                int index =0; // Index des Spielsteines field
                for(int zeile = 1; zeile < 7; zeile++)   
                { 	
                    for(int spalte = 0; spalte < 7; spalte++)   
                    {
                        ImageView greyView = new ImageView(grey);
                        greyView.setFitWidth(50);
                        greyView.setFitHeight(50);
                        greyView.setPreserveRatio(true);
                        
                        field[index] = new Label("", greyView);
                        field[index].setStyle("-fx-border-color: white;");
                        field[index].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                        field[index].setPrefSize(80, 80);
                        gridPanel.add(field[index], spalte, zeile);
                        index++;
                    }
                }
                gridPanel.setAlignment(Pos.CENTER); 
                this.setCenter(gridPanel);
                
                // Layout-Klasse HBox erzeugen und Kn pfe "Neues Spiel" und "Hilfe" einsetzen
               neuesSpiel = new Button("  Neues Spiel  ");
               
               neuesSpiel.setOnAction( e -> {
               		        // Spiel neu initialisieren
               	                spiel.clean();
                		
                 	} );
                	
                
               /* Zusatzaufgabe Hilfe-Knopf */
               
               help = new Button("    Hilfe    ");
               help.setOnAction( e -> {
               	 // Neues Dialogfenster erzeugen	       
                 HilfeDialog dialog = new HilfeDialog();  
                 // Dialogfenster anzeigen und Hauptfenster (this) blockieren
                 dialog.showAndWait();
             });

               
                buttonBox = new HBox();
                buttonBox.getChildren().addAll(neuesSpiel, help);
                buttonBox.setAlignment(Pos.CENTER);
                buttonBox.setMargin( neuesSpiel, new Insets(6.0, 6.0 , 6.0, 6.0));
                buttonBox.setMargin( help, new Insets(6.0, 6.0 , 6.0, 6.0));
                this.setBottom(buttonBox);
                
       }
        private void loadImages() {
            try {
                user = new Image(getClass().getResourceAsStream("/recources/user.png"));
                green = new Image(getClass().getResourceAsStream("/recources/green.png"));
                red = new Image(getClass().getResourceAsStream("/recources/red.png"));
                grey = new Image(getClass().getResourceAsStream("/recources/grau.png"));
                
                // Debug output to verify images loaded
                System.out.println("Images loaded successfully");
            } catch (Exception e) {
                System.out.println("Error loading images: " + e.getMessage());
                e.printStackTrace();
            }
        }
       // Gibt Button der i-ten Spalte zurueck
       public Button getButton(int i)
       {
       	       return button[i];
       }
       
       // setzt i-ten Spielstein in ausgewaehlter Farbe
       public void setGraphic(int i, int farbe)
       {
           ImageView view;
           if(farbe == GRUEN) {
               view = new ImageView(green);
           } else if(farbe == ROT) {
               view = new ImageView(red);
           } else {
               return;
           }
           
           view.setFitWidth(50);
           view.setFitHeight(50);
           view.setPreserveRatio(true);
           field[i].setGraphic(view);
       }
       
       // Setzen des Spieleanzeige-Labels und der Hintergrundfarbe des Containers
       public void amZug(int farbe)
       {
       	       if (farbe == ROT)
       	       {
       	       	  display.setText(" Rot am Zug ");
       	       	  labelBox.setStyle("-fx-background-color: red;");
       	       }
       	       if (farbe == GRUEN)
       	       {
       	       	  display.setText(" Gr n am Zug ");
       	       	  labelBox.setStyle("-fx-background-color: green;");    
       	       }
       }
       
       // Fehlermeldung ins Spielanzeige-Label setzen
       public void meldeFehler(String fehlermeldung)
       {
       	      display.setText(fehlermeldung); 
       }
       
       // Spielanzeige-Label und Hintergrund initialisieren
       public void neuesSpiel()
       {
       	   display.setText("  Rot beginnt  ");
	   labelBox.setStyle("-fx-background-color: red;");    
	   
	   for(int i=0;  i <7; i++)
           {
             // Auswahlkn pfe freigeben
             button[i].setDisable(false);
           }
	   for(int i =0; i <42; i++)
       { 
           ImageView greyView = new ImageView(grey);
           greyView.setFitWidth(50);
           greyView.setFitHeight(50);
           greyView.setPreserveRatio(true);
           field[i].setGraphic(greyView);
       }
       }
       
       // Gewinn anzeigen 
       public void gewonnen(int farbe)
       {
        //gelber Hintergrund, ... hat gewonnen und Gewinnnachricht ausgeben
	
	labelBox.setStyle("-fx-background-color: yellow;");    
	if(farbe== ROT)
	    display.setText("Rot hat gewonnen");
	if(farbe== GRUEN)
	    display.setText("Gr n hat gewonnen"); 
         
       // Auswahlkn pfe verriegeln
        for(int i=0; i <7; i++)
       	    {
       	      button[i].setDisable(true);
       	    }
    
       }

}
