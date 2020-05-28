// The location of the javaFX is PATH_TO_FIX

// importing all the required packages
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
public class ConnectFour extends Application 
{
	Button restartButton = new Button("RESTART"); // Creating a button for restarting the game
	Label gameStatus = new Label("RED TURN");
  // Indicate which player has a turn, initially it is the Red player
	boolean hasWon;
	int z = 0;  // initializing z to 0  required for altering the colors
	boolean isPlayer1Turn = (z%2==0); // Create a boolean for altering the colors
  // Create and initialize new Circle array
  private final Circle[][] circles =  new Circle[6][7];
  @Override // Override the start method in the Application class
  public void start(final Stage primaryStage) 
  {
		restartButton.setOnAction( e-> // Handling a mouse click when Restart Button is pressed to restart the new game
		{
			primaryStage.close();  // Close the current stage
			Platform.runLater( () -> new ConnectFour().start( new Stage() ) ); //  Starting the new Stage
		} );
    GridPane pane = new GridPane();  // creating a new grid pane to hold circles
    for (int i = 0; i < 6; i++)
      for (int j = 0; j < 7; j++)
      {
    	  Rectangle rec = new Rectangle(30,30,40,40 );  //Create a rectangle of given appropriate figure
    	  rec.setFill(Color.BLACK); // set the inside color to black of rectangle
          rec.setStroke(Color.BLACK); // set the outline color to black of rectangle
          pane.add(rec, j, i);    //Adding rectangle to the pane	 
          Circle circle = new Circle(); // creating new circle 
          pane.add(circles[i][j] = circle, j, i); // adding the circle to given i and j as rows and columns
          circle.setRadius(19); // setting the radius of circle 
          circle.setStroke(Color.BLACK); // setting an outline of circle to black
          circle.setFill( Color.WHITE); // setting the inside color to white
          final int row = i;
          final int column = j;
          circles[i][j].setOnMouseClicked(e-> {   // handling the mouse clicked event and alternating the red and yellow colors
              if (checkLocation(row, column) && !hasWon&& already(row,column))  //Checking if the bottom cell is occupied  and occupied cell is not again replaced and nobody has won 
              {
            	  circles[row][column].setFill((z%2==0) ? Color.RED : Color.YELLOW); // Alternating the color as Red and Yellow
            	  if (z%2==0)
            	  {
            		  gameStatus.setText("YELLOW TURN"); // Making Yellow Turn to show at the bottom if its yellow turn
            	  }
            	  	else
            	  	{
            		  gameStatus.setText("RED TURN"); //Making Red Turn to show at the bottom if its red turn
            	  	}
            	 z++;
                 if (isConsecutiveFour())
                  {
                      hasWon = true;
                  }   
              }
          });
      }
             BorderPane borderPane = new BorderPane(); // creating a new border pane
    	{
    		borderPane.setCenter(pane);  // adding grid pane pane to border pane and setting to center
    		borderPane.setBottom(gameStatus);// Adding whose player's turn is it at the bottom of the game board
    		borderPane.setTop(restartButton);// Adding restart button at the top 	
    	}
    	Scene scene = new Scene(borderPane, 287, 290);  // Creating a scene and place it in the stage
    	primaryStage.setTitle("ConnectFour");       // Setting the stage title
    	primaryStage.setScene(scene);                  // Placing the scene in the stage
    	primaryStage.show();                                  // Displaying the stage   
  }
  	private boolean checkLocation(int row, int column) // method to check the location to drop the disk in last row and column
  	{   
      // if at the bottom row , the circle is white return true else false
  			if (row == circles.length - 1)
      	{
  			return circles[row][column].getFill() == Color.WHITE;
      	}
      // if circle below the circle which is clicked is not white return true
      else
      {
    	  return (circles[row + 1][column].getFill() != Color.WHITE);
      }
    }
  		public boolean isConsecutiveFour()  // Checking the winners with the new method isConsecutiveFour
  	{
	//Checking the horizontal win by checking the same color is present or not in the row
		for (int i = 0;i<=5;i++)
		{
			for (int j =0;j<4;j++)
			{
				if ( circles[i][j].getFill() == circles[i][j+1].getFill() && circles[i][j].getFill() == circles[i][j+2].getFill() && circles[i][j].getFill() == circles[i][j+3].getFill() && circles[i][j].getFill()!=Color.WHITE && circles[i][j+1].getFill() != Color.WHITE && circles[i][j+2] .getFill()!= Color.WHITE && circles[i][j+3].getFill()!= Color.WHITE)
				{
					// Assigning the winning circles to a,b,c,d for flashing the winning circles.
					Circle a  = circles[i][j];
					Circle b = circles[i][j+1];
					Circle c  = circles[i][j+2];
					Circle d  = circles[i][j+3];
					// For flashing the circles
				     Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), e-> flashCircles( a,b,c,d)));
			     timeline.setCycleCount(Timeline.INDEFINITE); //Changing the color to indefinite time
			     timeline.play();
					if (circles[i][j].getFill()==Color.RED)
					{
						gameStatus.setText( "RED has Won the game"); 
					// Setting the text as shown if the horizontal consecutive four has red color
					}
					if (circles[i][j].getFill()==Color.YELLOW) 
						{
							gameStatus.setText( "YELLOW has Won the game"); //Setting the text as shown if the horizontal consecutive four do not have red color
						}
						return true;
					}
			}
		}
		//Checking the vertical win by checking the same color is present or not in the column
		for (int i = 0;i<=6;i++)
		{
			for (int j =0;j<3;j++)
			{
				if (circles[j][i].getFill() == circles[j+1][i].getFill() && circles[j][i].getFill() == circles[j+2][i].getFill() && circles[j][i].getFill() == circles[j+3][i].getFill() && circles[j][i] .getFill()!=Color.WHITE && circles[j+1][i].getFill()!= Color.WHITE && circles[j+2][i].getFill() != Color.WHITE&& circles[j+3][i].getFill() != Color.WHITE)
					{ 
					// Assigning the winning circles to a,b,c,d for flashing the winning circles.
					Circle a  = circles[j][i];
					Circle b = circles[j+1][i];
					Circle c  = circles[j+2][i];
					Circle d  = circles[j+3][i];
				     Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), e->flashCircles( a,b,c,d)));
				     timeline.setCycleCount(Timeline.INDEFINITE);
				     timeline.play();
					
					if (circles[j][i].getFill()==Color.RED)  
						{ 
							gameStatus.setText( "RED has won the game"); // Setting the text as shown if the vertical consecutive four has red color
						}
					if (circles[j][i].getFill()==Color.YELLOW)   
						{
							gameStatus.setText( "YELLOW has won the game"); // Setting the text as shown if the vertical consecutive four do not have red color
						}
						return true;
					}
			}
		}
		
		//Checking the diagonal win from one side by checking the same color is present or not
		
		for (int j = 0;j<=2;j++)
		{
			for (int i =0;i<=3;i++)
			{
				if (circles[j][i].getFill() == circles[j+1][i+1] .getFill()&& circles[j][i].getFill() == circles[j+2][i+2].getFill() && circles[j][i].getFill() == circles[j+3][i+3].getFill() && circles[j][i].getFill() != Color.WHITE && circles[j+1][i+1].getFill() != Color.WHITE&& circles[j+2][i+2].getFill() != Color.WHITE && circles[j+3][i+3].getFill() != Color.WHITE)
					{ 
					// Assigning the winning circles to a,b,c,d for flashing the winning circles.
						Circle a  = circles[j][i];
						Circle b = circles[j+1][i+1];
						Circle c  = circles[j+2][i+2];
						Circle d  = circles[j+3][i+3];
					
						Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), e->flashCircles( a,b,c,d)));
						timeline.setCycleCount(Timeline.INDEFINITE);
						timeline.play();
					if (circles[j][i].getFill()==Color.RED)    //Checking if the winning cell has red color
						{ 						
							gameStatus.setText( "RED has won the game"); // Setting the text as shown if the diagonal consecutive four  has red color
						}
					if (circles[j][i].getFill()==Color.YELLOW)
						{
							gameStatus.setText( "YELLOW has won the game");// Setting the text as show if the diagonal consecutive four do not have red color
						}
						return true;
					}
			}
		}
		
		//Checking the diagonal win from another side by checking the same color is present or not
		
		for (int j = 0;j<=2;j++)
		{
			for (int i =6;i>=3;i--)
			{
				if (circles[j][i].getFill() == circles[j+1][i-1].getFill() && circles[j][i].getFill()== circles[j+2][i-2].getFill() && circles[j][i].getFill() == circles[j+3][i-3].getFill()&& circles[j][i].getFill() != Color.WHITE && circles[j+1][i-1].getFill() != Color.WHITE && circles[j+2][i-2].getFill() != Color.WHITE && circles[j+3][i-3].getFill() != Color.WHITE)
					{ 
					// Assigning the winning circles to a,b,c,d for flashing the winning circles.
					Circle a  = circles[j][i];
					Circle b = circles[j+1][i-1];
					Circle c  = circles[j+2][i-2];
					Circle d  = circles[j+3][i-3];
					
					Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), e->  flashCircles( a,b,c,d))); 
					timeline.setCycleCount(Timeline.INDEFINITE);
					timeline.play();
					
					if (circles[j][i].getFill()==Color.RED) {
						gameStatus.setText( "RED has won the game"); // Setting the text as shown if the diagonal consecutive four has red color

					}
					if (circles[j][i].getFill()==Color.YELLOW) 
						{
							gameStatus.setText( "YELLOW has won the game"); //Setting the text as shown if the diagonal consecutive four do not have red color
						}
							return true;
					}
			}
		
		}
		//Checking if its draw or not by increasing the count by 1 if the Color of the dropping cell is White and return false. If count is equal to 0 then its a draw and return true
		int count = 0; 
		
		for ( int i = 0; i<6;i++) //For row
		{
			for (int j = 0; j<7; j++) // For column
			{
				if (circles[i][j].getFill() == Color.WHITE)  
				{
					count++; // increasing the counter by 1 if the color of the dropping cell is White.
				}
			}
		}
		if (count ==0)
			{
			gameStatus.setText( "NO WINNER"); //Setting the bottom text  as shown if its a draw
				return true;
			}
			else
				{
					return false;
				}		 
}
  
//Method which takes the winning circles and change the circle to different colors to make it flash indefinitely 
 private void flashCircles(Circle circle, Circle circle2, Circle circle3, Circle circle4)  
 {
	     Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e->  // Changing the color of wining circle to Red and Yellow alternately
	     { 
	    	 circle.setFill(Color.RED);
	         circle2.setFill(Color.YELLOW);
	         circle3.setFill(Color.RED);
	         circle4.setFill(Color.YELLOW); 
	         }));
	     timeline.setCycleCount(Timeline.INDEFINITE);
	     timeline.play();
	     Timeline time = new Timeline(new KeyFrame(Duration.millis(200), e-> 
	     {
	    	 // Changing the color of wining circles to Orange
	    	 circle.setFill(Color.ORANGE); 
	    	 circle2.setFill(Color.ORANGE);
	    	 circle3.setFill(Color.ORANGE);
	    	 circle4.setFill(Color.ORANGE);
	     }));
	     	time.setCycleCount(Timeline.INDEFINITE);
	     	time.play();
     }
private boolean already(int row,int column)  //Method to check if the cell is already occupied or not. 
 {
	  if (circles[row][column].getFill() == Color.RED || circles[row][column].getFill() == Color.YELLOW ) // If the cell has either red or yellow color already filled return false else true.
	  {
		  return false;
	  }
	  	else
	  		{
	  			return true;
	  		}  
 }
      public static void main(String[] args)  
      	{
          	Application.launch(args);  //launching an application
      	}
}