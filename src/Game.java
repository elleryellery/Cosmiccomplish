import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.*; 
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

//dani successfully downloaded VS Code on her home computer!

public class Game  extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener{

	/*
	   _       __    ___   _    __    ___   _     ____      ___   ____  __    _      __    ___    __   _____  _   ___   _     
	  \ \  /  / /\  | |_) | |  / /\  | |_) | |   | |_      | | \ | |_  / /`  | |    / /\  | |_)  / /\   | |  | | / / \ | |\ | 
 	   \_\/  /_/--\ |_| \ |_| /_/--\ |_|_) |_|__ |_|__     |_|_/ |_|__ \_\_, |_|__ /_/--\ |_| \ /_/--\  |_|  |_| \_\_/ |_| \| 
 	   
 	   TODO Variable Declaration
	 */

	//Management Variables
	private BufferedImage back; 
	private File file;
	
	//Objects
	private CharacterObject astronaut = new CharacterObject(300, 300, (int)(121/2), (int)(176/2), new ImageIcon("Astronaut Facing Right Lifting None.png"));
	private CharacterObject myPlanet = new CharacterObject(centerXPosition(200), centerYPosition(200)-20, (int)(200), (int)(200), new ImageIcon("Default Planet.png"));
	private CharacterObject miniSpaceship = new CharacterObject(300, 300, 100, 100, new ImageIcon(rotate(imageIconToBufferedImage(new ImageIcon("Default Spaceship.png")), (long)90)),new ImageIcon(rotate(imageIconToBufferedImage(new ImageIcon("Default Spaceship.png")), (long)90)));
	private CharacterObject blankPopUpBox = new CharacterObject(centerXPosition(750),centerYPosition(400)-20,750,400, new ImageIcon ("BlankPopUpBox.png"));
	private Button startButton;
	private Button invisibleButton;
	
	private Button aboutButton;
	private Button storeButton;
	private Button homeButton;
	private Button taskButton;
	private Button finishedInputtingTask;
	private Button XButton;
	private Button forwardButton, backwardButton, rightArrowButton, leftArrowButton, selectButton, removeButton;
	private Button viewTasksButton;
	private Button finishedTimerInput, invisibleButton2;
	private Button yesButton, noButton;
	private Button claimRewardButton, selectNewTaskButton, setNewTimerButton;
	private Button backButton,purchaseButton,addButton, editButton;
	private Button backToTimerButton;
	
	private TextBox taskNameInput, taskDateInput, taskRewardInput, taskPositionInput;
	private TextBox currentInputBox;
	private TextBox hoursInput, minutesInput;

	private Task newtask;

	Scanner scan;
	
	//Integers
	private int key;
	private int mvmfactor;
	private int taskIteratePos = 0;
	private int storeIteratePos = 0;
	private int setHours;
	private int setMinutes;
	private int setSeconds;
	private int hours;
	private int minutes;
	private int seconds, b;
	private double testX = centerXPosition(40);
	private double testY = centerYPosition(40);
	private double diameter = 200;
	private double angle = 0.0;
	private static double angleSum = 0.0;
	private double ratio;
	private int deleteStop;
	private int threadRunTime;
	private int coins = 0;
	
	//Other Numbers
	private long starttime;
	private long initialTime;
	private long begintime = 0;
	private long threadRunTimeHelper;
	private long threadRunTimeHelper2 =0;
	
	//Boolean
	private boolean moving;
	private boolean recentright;
	private boolean called;
	private boolean astronautNeeded;
	private boolean make;
	private boolean finishEnteringTaskNotif = false;
	private boolean taskAdded = false;
	private boolean inputStat;
	private boolean stupidvscode;
	private boolean timerDoneNotif = false;
	private boolean timer;
	private boolean confirmTaskDeletionNotif = false;
	private boolean addToCoins = false;
	private boolean dragNDrop = false;
	
	//Strings
	//private String screenstatus = "Start Up";
	private String screenstatus = "Start Up";
	private String screenComingFrom;
	private String temporaryTaskName = new String("");
	private String temporaryTaskDate = new String("");
	private String temporaryTaskReward = new String("");
	private String temporaryTaskPositionInQueue = new String("");
	private String currentInput = new String("");
	private String iteratedDate, iteratedName, iteratedReward, iteratedPosition;
	private String output = new String("");

	//Array Lists
	private ArrayList <Task> tasks = new ArrayList();
	private ArrayList <Animation> animRand = new ArrayList();
	private ArrayList <CharacterObject> storeOptions = new ArrayList();
	
	private Animation thisAnim = null;
	private int thisLength = 0;
	private long thisStart = 0;
	
	/*
	  _       __    _       __    __    ____  _      ____  _     _____ 
	 | |\/|  / /\  | |\ |  / /\  / /`_ | |_  | |\/| | |_  | |\ |  | |  
	 |_|  | /_/--\ |_| \| /_/--\ \_\_/ |_|__ |_|  | |_|__ |_| \|  |_|  
	 
	 TODO Management
	 */

	public Game() {
		
		//Thread Setup
		new Thread(this).start();	
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		mvmfactor = 5;
		called = false;
		astronautNeeded = false;
		
		startButton = new Button(20, 0, 1400, 725, new ImageIcon("Spaceship Icon.png"));
		invisibleButton = new Button(527, 155, 262, 357, new ImageIcon("nan.png"));

		editButton = new Button(20,20,75,75,new ImageIcon("Edit Icon.png"));
		
		aboutButton = new Button (350,300,140,56, new ImageIcon ("AboutButtonn2.png"));
		aboutButton = new Button (350,300,140,56, new ImageIcon ("AboutButtonn2.png"));
		storeButton = new Button(900, 300,140,56, new ImageIcon ("StoreButton2.png"));
		homeButton = new Button (centerXPosition(140), 25, 140, 70, new ImageIcon("HomeButton2.png"));
		taskButton = new Button(centerXPosition(250),centerYPosition(80),250,80, new ImageIcon("NewTaskButton.png"));
		finishedInputtingTask = new Button(1100,200,100,100, new ImageIcon("Checkmark2.png"));
		XButton = new Button(1000,180,40,40, new ImageIcon("X Button.png"));
		forwardButton = new Button(150,550,335,70, new ImageIcon("Move Forward Button.png"));
		backwardButton = new Button(forwardButton.getX()+forwardButton.getW()+30, 550, 335,70, new ImageIcon("MoveBackwardButton.png"));
		rightArrowButton = new Button(centerXPosition(100)+600, centerYPosition(130)-50, 100,130, new ImageIcon ("RightArrowButton.png"));
		leftArrowButton = new Button(centerXPosition(100)-600, centerYPosition(130)-50, 100,130, new ImageIcon ("LeftArrowButton.png"));
		selectButton = new Button (centerXPosition(170)-110, 550, 170,70, new ImageIcon("SelectButton.png"));
		removeButton = new Button(centerXPosition(190)+110, 550, 190,70, new ImageIcon ("RemoveButton.png"));
		purchaseButton = new Button (centerXPosition(200)-110, 550, 200,70, new ImageIcon("PurchaseButton.png"));
		addButton = new Button(centerXPosition(190)+110, 550, 190,70, new ImageIcon ("AddButton.png"));
		viewTasksButton = new Button(centerXPosition(280), centerYPosition(80)+120, 280,80, new ImageIcon("NewViewTaskButton.png"));

		finishedTimerInput = new Button(0, -50, 1400, 725, new ImageIcon("Still Frame Timer Input Button.png"));
		invisibleButton2 = new Button(659, 584, 109, 70, new ImageIcon("nan.png"));
		yesButton = new Button(centerXPosition(200)-120, centerYPosition(100)+80, 200,100, new ImageIcon("YesButton.png"));
		noButton = new Button(centerXPosition(200)+130, centerYPosition(100)+80, 200,100, new ImageIcon ("NoButton.png"));
		claimRewardButton = new Button(centerXPosition(500),centerYPosition(60)-100,500,60, new ImageIcon("ClaimReward.png"));
		selectNewTaskButton = new Button(centerXPosition(650), centerYPosition(70),650,70, new ImageIcon ("SelectNewTask.png"));
		setNewTimerButton = new Button(centerXPosition(550), centerYPosition(65), 550,65, new ImageIcon ("SetNewTimerButton.png"));
		backButton = new Button(centerXPosition(150), 600, 150,70, new ImageIcon ("BackButton.png"));
		backToTimerButton = new Button(950, 20, 400,70, new ImageIcon ("BackToTimerButton.png"));

		taskNameInput = new TextBox(centerXPosition(600) + 30,centerYPosition(600)+100, 560, 90, 7, false, "Task: ");
		taskDateInput = new TextBox(centerXPosition(600) + 30,centerYPosition(600)+230, 560, 30, 11, false, "Due Date: ");
		taskRewardInput = new TextBox(centerXPosition(600)+ 30, centerYPosition(600)+360, 560,60,9,false, "Reward: ");
		taskPositionInput = new TextBox(centerXPosition(600)+ 30, centerYPosition(600)+490, 560,30, 10, false, "Position: ");
		
		hoursInput = new TextBox(426, 240, 125,170, 0, false, "");
		minutesInput = new TextBox(720, 240, 450,150, 0, false, "");

	}

	//Run Method
	public void run()
	   {
	   	try
	   	{
	   		while(true)
	   		{
	   		   //Thread.currentThread().sleep(5);
			   Thread.currentThread().sleep(5);
	            repaint();
	         }
	      }
	   		catch(Exception e) {
	   			
	      }
	  	}
	
	/*
 	 _       __    _   _          ____  ___   _  _____  ___   ___  
	| |\/|  / /\  | | | |\ |     | |_  | | \ | |  | |  / / \ | |_) 
	|_|  | /_/--\ |_| |_| \|     |_|__ |_|_/ |_|  |_|  \_\_/ |_| \ 
	
	TODO Main Editor
	 */
	public void paint(Graphics g){
		
		Graphics2D twoDgraph = (Graphics2D) g; 
		if( back ==null)
			back=(BufferedImage)( (createImage(getWidth(), getHeight()))); 
				
		//Back Graphics and Setup
		Graphics g2d = back.createGraphics();
		g2d.clearRect(0,0,getSize().width, getSize().height);
		g2d.setFont( new Font("Courier New", Font.BOLD, 20));
		g2d.setColor(Color.WHITE);
		((Graphics2D) g2d).setStroke(new BasicStroke(10));
		
		//System.out.println(coins);
		//Start Screen
		if(screenstatus.equals("Start Up")) {
			StartUpScreen(g2d);
		} else if(screenstatus.equals("Start")) {
			StartScreen(g2d);
		} else if(screenstatus.equals("Play")) {
			PlayScreen(g2d);
		} else if(screenstatus.equals("About")) {
			AboutScreen(g2d);
		} else if(screenstatus.equals("Store")) {
			StoreScreen(g2d);
		} else if(screenstatus.equals("Input")){
			InputScreen(g2d);
		} else if(screenstatus.equals("Reorder Tasks")){
			ReorderTasksScreen(g2d);
		} else if(screenstatus.equals("Timer")){
			TimerScreen(g2d);
		} else if(screenstatus.equals("Countdown")){
			CountdownScreen(g2d);
		} else if (screenstatus.equals("Choose Reward")){
			ChooseRewardScreen(g2d);
		} else if (screenstatus.equals("Set New Timer")){
			SetNewTimerScreen(g2d);
		} else if (screenstatus.equals("Claim Reward")){
			RewardScreen(g2d);
		} else if(screenstatus.equals("Interior")) {
			InteriorScreen(g2d);
		}
		
		textTimer();

		//Pop-Up Notifications
		if(finishEnteringTaskNotif && screenstatus.equals("Input")){
			drawScreen(g2d,new ImageIcon("White Filter.png"));
			g2d.drawImage(new ImageIcon("Finish Filling Out Box.png").getImage(),centerXPosition(800),centerYPosition(800),800,800, this);
			drawButton(g2d, XButton);
		}

		if(taskAdded && screenstatus.equals("Input")){
			drawScreen(g2d,new ImageIcon("White Filter.png"));
			g2d.drawImage(new ImageIcon("Task Added Notification.png").getImage(),centerXPosition(800),centerYPosition(800)-15,800,800, this);
			drawButton(g2d, XButton);
			seconds = setSeconds+1;
		}

		if(timerDoneNotif && screenstatus.equals("Countdown")){
			drawScreen(g2d, new ImageIcon("White Filter.png"));
			g2d.drawImage(new ImageIcon ("TimerCompleteNotif.png").getImage(), centerXPosition(750),centerYPosition(400)-20,750,400, this);
			drawButton(g2d, yesButton);
			drawButton(g2d, noButton);
		}

		if(confirmTaskDeletionNotif && screenstatus.equals("Reorder Tasks")){
			drawScreen(g2d, new ImageIcon("White Filter.png"));
			g2d.drawImage(new ImageIcon ("ConfirmTaskDeletionNotif.png").getImage(), centerXPosition(750),centerYPosition(400)-40,750,400, this);
			drawButton(g2d, yesButton);
			drawButton(g2d, noButton);
		}

		//Astronaut
		if(astronautNeeded) {
			g2d.drawImage(astronaut.getImg().getImage(), (int)astronaut.getX(), (int)astronaut.getY(), (int)astronaut.getW(), (int)astronaut.getH(), this);
		
			if(recentright) {
				if(moving) {
					if(((int)(System.currentTimeMillis()/200))%2==0) {
						astronaut.setImg(new ImageIcon("Astronaut Facing Right Lifting Right.png"));
					} else {
						astronaut.setImg(new ImageIcon("Astronaut Facing Right Lifting Left.png"));
					}
				} else {
					astronaut.setImg(new ImageIcon("Astronaut Facing Right Lifting None.png"));
				}
			} else {
				if(moving) {
					if(((int)(System.currentTimeMillis()/200))%2==0) {
						astronaut.setImg(new ImageIcon("Astronaut Facing Left Lifting Right.png"));
					} else {
						astronaut.setImg(new ImageIcon("Astronaut Facing Left Lifting Left.png"));
					}
				} else {
					astronaut.setImg(new ImageIcon("Astronaut Facing Left Lifting None.png"));
				}
			}
		}
		if(screenstatus.equals("Testing Room")){
			
			drawScreen(g2d, new ImageIcon("void.png"));
			circularMotion(g2d,astronaut,100,true,10.0);

			ImageIcon test1 = new ImageIcon("Astronaut Facing Left Lifting None.png");
			BufferedImage test = new BufferedImage(
				test1.getIconWidth(),
				test1.getIconHeight(),
				BufferedImage.TYPE_INT_RGB
			);
			//astronaut.setImg(new ImageIcon(rotate(test,20.0)));
		}

		twoDgraph.drawImage(back, null, 0, 0);
}
	
	/*
 	 ___   ___    __    _           _      ____ _____  _     ___   ___   __  
	| | \ | |_)  / /\  \ \    /    | |\/| | |_   | |  | |_| / / \ | | \ ( (` 
	|_|_/ |_| \ /_/--\  \_\/\/     |_|  | |_|__  |_|  |_| | \_\_/ |_|_/ _)_) 
	
	TODO Draw Methods
	 */

	private void drawScreen(Graphics g2d, ImageIcon drawnImage) {
		g2d.drawImage(drawnImage.getImage(), 0, 0, getWidth(), getHeight(), this);
	}
	
	private void drawButton(Graphics g2d, Button button) {
		g2d.drawImage(button.getImg().getImage(), button.getX(), button.getY(), button.getW(), button.getH(), this);
	}

	private void drawObject(Graphics g2d, CharacterObject a){
		g2d.drawImage(a.getImg().getImage(), (int)a.getX(), (int)a.getY(), (int)a.getW(), (int)a.getH(), this);

	}

	public int centerXPosition(int objectWidth){
		int temp;
		temp = (1400/2)-(objectWidth/2);
		return temp;
	}

	public int centerYPosition(int objectHeight){
		int temp;
		temp = (725/2)-(objectHeight/2);
		return temp;
	}
	
	/*
 	 __   __    ___   ____  ____  _          _      ____ _____  _     ___   ___   __  
	( (` / /`  | |_) | |_  | |_  | |\ |     | |\/| | |_   | |  | |_| / / \ | | \ ( (` 
	_)_) \_\_, |_| \ |_|__ |_|__ |_| \|     |_|  | |_|__  |_|  |_| | \_\_/ |_|_/ _)_) 
	
	TODO Screen Methods
	 */
	
	private void StartUpScreen(Graphics g2d) {
		g2d.drawImage(new ImageIcon("Loading Frame.png").getImage(), 0,0,getWidth(),getHeight(),this);
		if(!called) {
			generateAll();
			starttime = System.currentTimeMillis();
			called = true;
		}
		g2d.drawImage(new ImageIcon("Start Screen GIF.gif").getImage(), 0,0,getWidth(),getHeight(),this);
		if(System.currentTimeMillis()-starttime >= 7500) {
			g2d.drawImage(new ImageIcon("End Frame.png").getImage(), 0,0,getWidth(),getHeight(),this);
			screenstatus = "Start";
		}
	}
	
	private void StartScreen(Graphics g2d) {
		g2d.drawImage(new ImageIcon("End Frame.png").getImage(), 0,0,getWidth(),getHeight(),this);
		g2d.drawImage(new ImageIcon("Cosmiccomplish Title.png").getImage(), getWidth()/2 - (674/2), getHeight()/2 - (114/2)-150, 674, 114,this);
		drawButton(g2d, startButton);
		drawButton(g2d, aboutButton);
		drawButton(g2d, storeButton);
	}

	private void PlayScreen(Graphics g2d) {
		drawScreen(g2d, new ImageIcon("End Frame.png"));
		drawButton(g2d, taskButton);

		if(!tasks.isEmpty()){
			drawButton(g2d, viewTasksButton);
		}
	}
	
	private void AboutScreen(Graphics g2d) {
		drawScreen(g2d, new ImageIcon("End Frame.png"));
		g2d.setColor(Color.white);
		g2d.drawImage(new ImageIcon("Cosmiccomplish About Note.png").getImage(), centerXPosition(800), centerYPosition(500), 800,500,this);
		drawButton(g2d, homeButton);
	}
	
	private void StoreScreen(Graphics g2d) {
		drawScreen(g2d, new ImageIcon("End Frame.png"));
		drawButton(g2d, homeButton);

		drawButton(g2d, rightArrowButton);
		drawButton(g2d, leftArrowButton);
		drawButton(g2d, purchaseButton);
		drawButton(g2d, addButton);

		if(storeOptions.get(storeIteratePos).getUnlocked()){
			purchaseButton.setImg(new ImageIcon ("PurchasedButton.png"));
		}

		if(!storeOptions.isEmpty()){			
			CharacterObject current = storeOptions.get(storeIteratePos);
			displayTaskElement(g2d, current.getName(),175);

			//coconuts the two lines below this are temporary until you add the new graphics and a way to show when something is unlocked/added
			displayTaskElement(g2d, "My Coins: " + coins,500);
			displayTaskElement(g2d, "Cost: " + current.getPrice(),125);

			g2d.drawImage(current.getImg().getImage(),centerXPosition((int)current.getW()*3),centerYPosition((int)current.getH()*3),(int)current.getW()*3,(int)current.getH()*3,this);
		} else {
			typeToFontNOBOX(g2d,"No Tasks to show!",centerXPosition(getWidthForText("No Tasks to show!",20)),centerYPosition(20)-40,500,50,0,20);
		}
	}

	private void InputScreen(Graphics g2d){
		g2d.setColor(Color.white);
		inputStat=true;
		if(!tasks.isEmpty()){
			g2d.drawString("Task:"+tasks.get(0).getTaskName(), 100,100);
		}
		drawScreen(g2d, new ImageIcon("Cork Board Background.png"));
		g2d.drawImage(new ImageIcon("Sticky Note.png").getImage(),centerXPosition(600), centerYPosition(600),600,600,this);		
		//Task Name Text Box
		typeTextBoxToFont(g2d,taskNameInput,30);
		typeTextBoxToFont(g2d,taskDateInput,30);
		typeTextBoxToFont(g2d, taskRewardInput, 30);
		//typeTextBoxToFont(g2d, taskPositionInput, 30);

		drawButton(g2d, finishedInputtingTask);
	}

	private void ReorderTasksScreen(Graphics g2d){
		g2d.setColor(Color.white);
		drawScreen(g2d, new ImageIcon("ReorderingTasksBackground.png"));
		//drawButton(g2d, forwardButton);
		//drawButton(g2d, backwardButton);
		drawButton(g2d, rightArrowButton);
		drawButton(g2d, leftArrowButton);
		drawButton(g2d, selectButton);
		drawButton(g2d, removeButton);

		if(!tasks.isEmpty()){			
			displayTaskElement(g2d, tasks.get(taskIteratePos).getTaskName(),175);
			displayTaskElement(g2d, "Due Date: " + tasks.get(taskIteratePos).getDueDate(),275);
			displayTaskElement(g2d, "Reward: " + tasks.get(taskIteratePos).getRewardValue(),375);
			displayTaskElement(g2d, "" + (taskIteratePos + 1),465);

		} else {
			typeToFontNOBOX(g2d,"No Tasks to show!",centerXPosition(getWidthForText("No Tasks to show!",20)),centerYPosition(20)-40,500,50,0,20);
		}
	}

	private void TimerScreen(Graphics g2d){
		
		drawScreen(g2d, new ImageIcon("Timer Input Background.png"));
		drawScreen(g2d, new ImageIcon("Hours and Minutes Boxes.png"));

		typeTextBoxToFont(g2d,hoursInput,150);
		typeTextBoxToFont(g2d,minutesInput,150);
		drawButton(g2d, finishedTimerInput);

		if(begintime !=0){
			if(System.currentTimeMillis()-begintime>=1600){
				screenstatus = "Countdown";
				initialTime= 0;

				begintime = 0;
			}
		}


	}

	private void CountdownScreen(Graphics g2d){
		finishedTimerInput.setImg(new ImageIcon("Still Frame Timer Input Button.png"));
		stupidvscode = false;
		//boolean timer = true;
		setSeconds = 59;
		seconds = Integer.valueOf(String.valueOf((System.currentTimeMillis() - initialTime)/1000));
		//System.out.println(threadRunTime);
		
		drawScreen(g2d, new ImageIcon("Timer Screen Background.png"));
		drawObject(g2d, myPlanet);
		typeToFontNOBOX(g2d, output, centerXPosition(getWidthForText(output, 40)),20,300,50,0,40);
		
		if(tasks.size()>0){
			String currentTask = "Current Task: "+ tasks.get(taskIteratePos).getTaskName();
			displayTaskElement(g2d, currentTask, 610);
		}
		

		if(timer){
			long milliseconds = setSeconds*1000 + (setMinutes-1)*60000 + setHours*36000000;
		
			if(threadRunTime != 0){
				circularMotion(g2d, miniSpaceship, (int)(myPlanet.getW()+20)/2, false,(threadRunTime*360.00000/milliseconds));
				miniSpaceship.setImg(new ImageIcon(rotate(imageIconToBufferedImage(miniSpaceship.getOgImage()), (threadRunTime*360.00000/milliseconds)) ));
			}

			if(seconds>=setSeconds +1) {
				seconds = 1;
				initialTime= System.currentTimeMillis();
				minutes++;
			}
			
			if(setHours==1 && setMinutes==0){
				setMinutes=60;
				setHours=0;
			}
			if(setHours>0 && setMinutes>0){
				if(minutes>setMinutes){
					setHours--;
					setMinutes = 61;
					setSeconds = 60;
				}
			}
			if(setHours>0){
				if(setMinutes-minutes < 10 && setSeconds-seconds > 10) {
					output = String.valueOf(setHours)+":0"+String.valueOf(setMinutes-minutes) + ":" + String.valueOf(setSeconds-seconds);
				}
				if(setSeconds-seconds < 10 && setMinutes-minutes > 10) {
					output = String.valueOf(setHours)+":"+ String.valueOf(setMinutes-minutes) + ":0" + String.valueOf(setSeconds - seconds);
				}
				if(setSeconds-seconds < 10 && setMinutes-minutes <10) {
					output = String.valueOf(setHours)+":0"+String.valueOf(setMinutes-minutes) + ":0" + String.valueOf(setSeconds - seconds);
				
				}
				if(setMinutes-minutes >= 10 && setSeconds-seconds >= 10) {
					output = String.valueOf(setHours)+ ":"+ String.valueOf(setMinutes-minutes) + ":" + String.valueOf(setSeconds - seconds);
				}
				if(setMinutes-minutes <= 10 && setSeconds-seconds >= 10) {
					output = String.valueOf(setHours)+":0"+String.valueOf(setMinutes-minutes) + ":" + String.valueOf(setSeconds - seconds);
				}
			} else if(setHours==0) {
				if(setMinutes-minutes < 10 && setSeconds-seconds > 10) {
					output = "0:"+"0"+String.valueOf(setMinutes-minutes) + ":" + String.valueOf(setSeconds-seconds);
				}
				if(setSeconds-seconds < 10 && setMinutes-minutes > 10) {
					output = "0:"+String.valueOf(setMinutes-minutes) + ":0" + String.valueOf(setSeconds - seconds);
				}
				if(setSeconds-seconds < 10 && setMinutes-minutes <10) {
					output = "0:"+"0"+String.valueOf(setMinutes-minutes) + ":0" + String.valueOf(setSeconds - seconds);
				
				}
				if(setMinutes-minutes >= 10 && setSeconds-seconds >= 10) {
					output = "0:"+String.valueOf(setMinutes-minutes) + ":" + String.valueOf(setSeconds - seconds);
				}
				if(setMinutes-minutes <= 10 && setSeconds-seconds >= 10) {
					output = "0:"+"0"+String.valueOf(setMinutes-minutes) + ":" + String.valueOf(setSeconds - seconds);
				}
			}

			/*if(setMinutes-minutes < 10 && setSeconds-seconds > 10) {
				output = "0"+String.valueOf(setMinutes-minutes) + ":" + String.valueOf(setSeconds-seconds);
			}
			if(setSeconds-seconds < 10 && setMinutes-minutes > 10) {
				output = String.valueOf(setMinutes-minutes) + ":0" + String.valueOf(setSeconds - seconds);
			}
			if(setSeconds-seconds < 10 && setMinutes-minutes <10) {
				output = "0"+String.valueOf(setMinutes-minutes) + ":0" + String.valueOf(setSeconds - seconds);
			
			}
			if(setMinutes-minutes >= 10 && setSeconds-seconds >= 10) {
				output = String.valueOf(setMinutes-minutes) + ":" + String.valueOf(setSeconds - seconds);

			}*/

			if(threadRunTimeHelper != 0){
				threadRunTime = (int)(System.currentTimeMillis()-threadRunTimeHelper);
				threadRunTimeHelper = System.currentTimeMillis();
			} else {
				threadRunTimeHelper = System.currentTimeMillis();
			}

			if(setHours-hours==0 && minutes >= setMinutes && setSeconds-seconds == 0) {
				System.out.println("DONE!");
				angle = 0;
				angleSum = 0;
				output = "0:00:00";
				
				seconds = setSeconds;
				
				miniSpaceship.setImg(new ImageIcon(miniSpaceship.getOgImage().getImage()));
				timerDoneNotif = true;
				
				timer = false;
			}

			
		}
	}
	private void ChooseRewardScreen(Graphics g2d){
		//screen is used when user says they did finish their task (yes)
		timer=false;
		drawScreen(g2d, new ImageIcon("Timer Screen Background.png"));
		drawObject(g2d, blankPopUpBox);
		drawButton(g2d, claimRewardButton);
		drawButton(g2d, selectNewTaskButton);
		screenComingFrom = "Choose Reward";

	}
	private void SetNewTimerScreen(Graphics g2d){
		//screen is used when user says they did not finish their task (no)
		timer=false;
		drawScreen(g2d, new ImageIcon("Timer Screen Background.png"));
		drawObject(g2d, blankPopUpBox);
		drawButton(g2d, claimRewardButton);
		drawButton(g2d, setNewTimerButton);
		screenComingFrom = "Set New Timer";
		
		
	}
	private void RewardScreen(Graphics g2d){
		drawScreen(g2d, new ImageIcon("Timer Screen Background.png"));
		if(tasks.size()>0){
			displayTaskElement(g2d, "Reward: " + tasks.get(taskIteratePos).getRewardValue(),375);
			if(addToCoins){
				coins = coins + Integer.valueOf(tasks.get(taskIteratePos).getRewardValue());
				addToCoins = false;
			}
		} else if(tasks.size()==0){
			typeToFontNOBOX(g2d, "Enjoy your reward!", centerXPosition(700), centerYPosition(50), 700,50,0,30);
		}
		drawButton(g2d, backButton);
	 	

	}

	private void InteriorScreen(Graphics g2d){
		//drawButton(g2d, backToTimerButton);
		if(!(thisAnim == null)){
			g2d.setColor(Color.WHITE);
			g2d.drawRect(0,0,1400,725);
			g2d.drawImage(thisAnim.getGif().getImage(),0,0,getWidth(),getHeight(), this);

			if(System.currentTimeMillis() - thisStart >= thisAnim.getLength()){
				thisAnim = null;
				drawScreen(g2d, new ImageIcon("Default Frame.png"));
			}
		}
		else {
			b = (int)(Math.random()*(animRand.size()));
			thisAnim = animRand.get(b);
			thisStart = System.currentTimeMillis();
		}
		drawButton(g2d, backToTimerButton);
		drawButton(g2d, editButton);

		for(int i = storeOptions.size()-1; i>=0; i--){

			CharacterObject c = storeOptions.get(i);
			if(c.getAdded()){
				drawObject(g2d,c);

				if(b == 0){
						if(!c.getName().equals("Hanging Picture") && !c.getName().equals("Landscaped Picture") && !c.getName().equals("Friendly Picture") && !c.getName().equals("Patterned Picture") && !c.getName().equals("Motivational Poster")&& !c.getName().equals("Pendant Flag"))

						if(c.getY()>0 && System.currentTimeMillis() - thisStart >= 13000 && System.currentTimeMillis() - thisStart <= 32500){
							c.setY(c.getY()-0.5);
						} else {
							if(!c.getName().equals("Hanging Picture") && !c.getName().equals("Landscaped Picture") && !c.getName().equals("Friendly Picture") && !c.getName().equals("Patterned Picture") && !c.getName().equals("Motivational Poster")&& !c.getName().equals("Pendant Flag"))
								if(c.getX()>50 && c.getX()<600 && c.getY()<385){
									fallTo(c,385);
								} else if(c.getX()<50 && c.getY()<370){
									fallTo(c,370);
								} else if(c.getX()>600 && c.getX()<800){
									fallTo(c,355);
								} else if(c.getX()>800 && c.getX()<1100){
									fallTo(c,345);
								} else if(c.getX()>1100 && c.getX()<1200){
									fallTo(c,350);
								} else if(c.getX()>1200){
									fallTo(c,385);
								}
						}
				
				} else {
				if(!c.getName().equals("Hanging Picture") && !c.getName().equals("Landscaped Picture") && !c.getName().equals("Friendly Picture") && !c.getName().equals("Patterned Picture") && !c.getName().equals("Motivational Poster")&& !c.getName().equals("Pendant Flag"))
					if(c.getX()>50 && c.getX()<600 && c.getY()<385){
						fallTo(c,385);
					} else if(c.getX()<50 && c.getY()<370){
						fallTo(c,370);
					} else if(c.getX()>600 && c.getX()<800){
						fallTo(c,355);
					} else if(c.getX()>800 && c.getX()<1100){
						fallTo(c,345);
					} else if(c.getX()>1100 && c.getX()<1200){
						fallTo(c,350);
					} else if(c.getX()>1200){
						fallTo(c,385);
					}
				}
			}
		}
	}
	
	/*
 	 ___   _   __   ___   _      __    _    
	| | \ | | ( (` | |_) | |    / /\  \ \_/ 
	|_|_/ |_| _)_) |_|   |_|__ /_/--\  |_|  s
	
	TODO Display
	 */

	 private void fallTo(CharacterObject c, int goal){
		double current = c.getY();

		while(current<goal){
			c.setY(current + 1);
			goal = (int)current;
		}
	 }
	 private void displayTaskElement(Graphics g2d, String inputString, int yValue){
		int tempFontSize = 30;

		while(getWidthForText(inputString,tempFontSize)>750){
			tempFontSize--;
		}

		typeToFontNOBOX(g2d,inputString,centerXPosition(getWidthForText(inputString,tempFontSize)),yValue,750,100,0,tempFontSize);

	 }
	 private void typeToFontNOBOX(Graphics g2d, String inputString, int xValue, int yValue, int width, int height, int deleteStopI, int fontSize) {
		int xAddedValue = 0;
		int yAddedValue = 0;
		int yMaxI = yValue + height;
		int margin = xValue + width;
		deleteStop = deleteStopI;
		
		for(int i=0; i<inputString.length();i++) {
			Letter newLetter = new Letter(inputString.charAt(i));
			g2d.drawImage(newLetter.getAffiliatedImage().getImage(),xValue+xAddedValue,yValue+yAddedValue,fontSize,fontSize,this);

			if(newLetter.getDimension() == 'M') {
				xAddedValue += fontSize;
			} else if(newLetter.getDimension() == 'S') {
				xAddedValue += (fontSize * 4/5);
			} else {
				xAddedValue += fontSize + (fontSize * 3 / 10);
			}

			if(xValue + xAddedValue + (fontSize * 13/10) > margin) {
				xAddedValue = 0;
				yAddedValue += (fontSize +10);
			}

			if(newLetter.getAffiliatedCharacter() == '\n'){
				xAddedValue = 0;
				yAddedValue += (fontSize +10);
			}
		}
	}

	private int getWidthForText(String inputString, int fontSize){
		int xAddedValue = 0;		
		
		for(int i=0; i<inputString.length();i++) {
			Letter newLetter = new Letter(inputString.charAt(i));
			if(newLetter.getDimension() == 'M') {
				xAddedValue += fontSize;
			} else if(newLetter.getDimension() == 'S') {
				xAddedValue += (fontSize * 4/5);
			} else {
				xAddedValue += fontSize + (fontSize * 3 / 10);
			}
		}
		return xAddedValue;
	}
	
	 private void typeToFont(Graphics g2d, String inputString, int xValue, int yValue, int width, int height, int deleteStopI, int fontSize, TextBox a) {
		int xAddedValue = 0;
		int yAddedValue = 0;
		int yMaxI = yValue + height;
		int margin = xValue + width;
		deleteStop = deleteStopI;
			
		for(int i=0; i<inputString.length();i++) {
			Letter newLetter = new Letter(inputString.charAt(i));
			g2d.drawImage(newLetter.getAffiliatedImage().getImage(),xValue+xAddedValue,yValue+yAddedValue,fontSize,fontSize,this);

			if(newLetter.getDimension() == 'M') {
				xAddedValue += fontSize;
			} else if(newLetter.getDimension() == 'S') {
				xAddedValue += (fontSize * 4/5);
			} else {
				xAddedValue += fontSize + (fontSize * 3 / 10);
			}

			if(xValue + xAddedValue + (fontSize * 13/10) > margin) {
				xAddedValue = 0;
				yAddedValue += (fontSize +10);
			}

			if(newLetter.getAffiliatedCharacter() == '\n'){
				xAddedValue = 0;
				yAddedValue += (fontSize +10);
			}

			if(yValue + yAddedValue > yMaxI){
				a.setInputStatus(false);
				
			} else {
				a.setInputStatus(true);
			}

			if(deleteStopI>0){
				if(inputString.length()<=deleteStopI){
					a.setDeleteAllowed(false);
				} else {
					a.setDeleteAllowed(true);
				}
			} else {
				if(inputString.length()==0){
					a.setDeleteAllowed(false);
				} else {
					a.setDeleteAllowed(true);
				}
			}
		}
	}
	 
	 private void typeTextBoxToFont(Graphics g2d, TextBox a, int fontSize) {
			typeToFont(g2d, a.getAffiliatedText(),a.getX(), a.getY(), a.getW(), a.getH(), a.getDeletionRestriction(),fontSize, a);
	 }


	private void circularMotion(Graphics g2d, CharacterObject object, int radius, boolean spiral, double speed){		
		
		g2d.drawImage(object.getImg().getImage(), (int)testX, (int)testY, (int)object.getW(), (int)object.getH(), this);
		if(angle<360){
			angle += speed;
		} else {
			angle = 0.0;
		}
		

		testX = Math.cos(Math.toRadians(angle-90))*diameter + centerXPosition(200)+35;
		testY = Math.sin(Math.toRadians(angle-90))*diameter + centerYPosition(200)+25;

		if(!spiral){
		diameter = radius*2;

		} else {
			if(diameter>0){
				diameter-=0.1;
			}
			object.setW((object.getW()/1.002));
			object.setH((object.getH()/1.002));
		}
		
	}

	public static BufferedImage rotate(BufferedImage bimg, double angle) {
		int w = bimg.getWidth();
		int h = bimg.getHeight();
		BufferedImage rotated = new BufferedImage(w, h, bimg.getType());
		Graphics2D graphic = rotated.createGraphics();
		// Enable anti-aliasing
		graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		 // Use higher quality rendering
		graphic.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		angleSum += angle;

		graphic.rotate(Math.toRadians(angleSum + 45), w / 2, h / 2);
		graphic.drawRenderedImage(bimg, null);
		graphic.dispose();
		return rotated;
	}
		
		
		
	
	/*
	 __    ____  _      ____  ___    __   _____  _   ___   _     
	/ /`_ | |_  | |\ | | |_  | |_)  / /\   | |  | | / / \ | |\ | 
	\_\_/ |_|__ |_| \| |_|__ |_| \ /_/--\  |_|  |_| \_\_/ |_| \| 
	
	TODO Generation
	 */
		
	public void generateAll() {
		animRand.add(new Animation(40500,new ImageIcon("Gravity Animation.gif")));
		animRand.add(new Animation(40500,new ImageIcon("Stargazing Animation.gif")));
		animRand.add(new Animation(81000, new ImageIcon("Napping Animation.gif")));

		//storeOptions.add(new CharacterObject(x,y,w,h,image,name))
		storeOptions.add(new CharacterObject(300,400,100,100, new ImageIcon("Plant 1.png"),50,"Plant 1"));
		storeOptions.add(new CharacterObject(500,400,75,75,new ImageIcon("Plant 2.png"),50,"Plant 2"));
		storeOptions.add(new CharacterObject(0,350,100,100,new ImageIcon("Plant 3.png"),50,"Plant 3"));
		storeOptions.add(new CharacterObject(0,0,75,75,new ImageIcon("Plant 4.png"),50,"Plant 4"));
		storeOptions.add(new CharacterObject(0,0,100,100,new ImageIcon("Plant 5.png"),50,"Plant 5"));

		storeOptions.add(new CharacterObject(0,0,150,150,new ImageIcon("Geology Kit.png"),50,"Geology Kit"));
		storeOptions.add(new CharacterObject(0,0,100,100,new ImageIcon("Green Book.png"),50,"Green Book"));
		storeOptions.add(new CharacterObject(0,0,100,100,new ImageIcon("Pink Book.png"),50,"Pink Book"));
		storeOptions.add(new CharacterObject(315,275,100,100,new ImageIcon("Motivational Poster.png"),50,"Motivational Poster"));
		storeOptions.add(new CharacterObject(315,275,100,100,new ImageIcon("Hanging Picture 1.png"),50,"Hanging Picture"));
		storeOptions.add(new CharacterObject(315,275,100,100,new ImageIcon("Hanging Picture 2.png"),50,"Landscaped Picture"));
		storeOptions.add(new CharacterObject(315,275,100,100,new ImageIcon("Hanging Picture 3.png"),50,"Friendly Picture"));
		storeOptions.add(new CharacterObject(315,275,100,100,new ImageIcon("Hanging Picture 4.png"),50,"Patterned Picture"));
		storeOptions.add(new CharacterObject(315,275,100,100,new ImageIcon("Pendant Flag.png"),50,"Pendant Flag"));





	}

	public void nullThese(){
		hours = 0;
		minutes= 0;
		seconds= 0;

		threadRunTime = 0;

		testX = centerXPosition(40);
		testY = centerYPosition(40);
		diameter = 200;
		angle = 0.0;
		angleSum = 0.0;
		miniSpaceship = new CharacterObject(300, 300, 100, 100, new ImageIcon(rotate(imageIconToBufferedImage(new ImageIcon("Default Spaceship.png")), (long)0)),new ImageIcon(rotate(imageIconToBufferedImage(new ImageIcon("Default Spaceship.png")), (long)0)));

	}
	
	/*
 	 __     __    _      ____  ___   _      __    _         _      ____  __    _      __    _      _   __    __  
	/ /`_  / /\  | |\/| | |_  | |_) | |    / /\  \ \_/     | |\/| | |_  / /`  | |_|  / /\  | |\ | | | / /`  ( (` 
	\_\_/ /_/--\ |_|  | |_|__ |_|   |_|__ /_/--\  |_|      |_|  | |_|__ \_\_, |_| | /_/--\ |_| \| |_| \_\_, _)_) 
	
	TODO Gameplay Mechanics
	 */
	
	public void textTimer(){
		if(currentInputBox != null) {
			currentInput = currentInputBox.getAffiliatedText();
			if((System.currentTimeMillis()/500)%2==0 && !make) {
				make = true;
				currentInput = currentInput + "|";
				currentInputBox.setAffiliatedText(currentInput);
			} else if((System.currentTimeMillis()/500)%2 != 0 && make && currentInput.length()>0) {
				for(int i=currentInput.length(); i>0;i--) {
					if(currentInput.substring(i-1,i).equals("|")) {
						currentInput = currentInput.substring(0,i-1) + currentInput.substring(i,currentInput.length());
						currentInputBox.setAffiliatedText(currentInput);
						break;
					}
				}
				make = false;
			}
		}
	}
	


	public static BufferedImage imageIconToBufferedImage(ImageIcon icon) {
        BufferedImage bufferedImage = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = bufferedImage.createGraphics();
        icon.paintIcon(null, graphics, 0, 0);
        graphics.dispose();//from   w  ww.j a  va  2  s.  co m
        return bufferedImage;
    }

	public static ImageIcon bufferedImageToImageIcon(BufferedImage bimg){
		ImageIcon icon = new ImageIcon(bimg);
		return icon;
	}

	



	
	/*
 	 _     ____  _         _     _   __  _____  ____  _      ____  ___       _      ____ _____  _     ___   ___   __  
	| |_/ | |_  \ \_/     | |   | | ( (`  | |  | |_  | |\ | | |_  | |_)     | |\/| | |_   | |  | |_| / / \ | | \ ( (` 
	|_| \ |_|__  |_|      |_|__ |_| _)_)  |_|  |_|__ |_| \| |_|__ |_| \     |_|  | |_|__  |_|  |_| | \_\_/ |_|_/ _)_) 
	
	TODO Key Listener Methods
	 */
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		key= e.getKeyCode();
		//System.out.println(key+ " - " + e.getKeyChar());
	
		if(currentInputBox == null){
		if(key == 65 || key == 37) { // A or <-
			astronaut.move(-mvmfactor, 0);
			moving = true;
			recentright = false;
		}
		if(key == 87 || key == 38) { // W or ^
			astronaut.move(0, -mvmfactor);
			moving = true;
		}
		if(key == 68 || key == 39) { // D or ->
			astronaut.move(mvmfactor, 0);
			moving = true;
			recentright = true;
		}
		if(key == 83 || key == 40) { // S or down
			astronaut.move(0, mvmfactor);
			moving = true;
		}
		if(key == 32) { // Space
			screenstatus = "Interior";
		}
		
		if(key==72) { // H
			screenstatus = ("Start");
		}
		
		if(key==73){ // I
			if(b!=0){
				b=0;
			}
			else {
				b=1;
			}
		}

		if(key==82){ // R
			screenstatus = ("Reorder Tasks");
		}

		if(key==39){ // ->
			if(taskIteratePos<tasks.size()-1){
				taskIteratePos++;
			}
		}

		if(key==37){
			if(taskIteratePos>0){
				taskIteratePos--;
			}
		}

		if(key==70){
			screenstatus = "Testing Room";
			screenstatus = "Testing Room";
			testX = centerXPosition(40);
			testY = centerYPosition(40);
			diameter = 200;
			angle = 0.0;

			astronaut.setW((int)(121/2));
			astronaut.setH((int)(176/2));

			//miniSpaceship.setImg(rotatedImageIcon(miniSpaceship.getImg(), 20.0));

		}

		if(key == 84){ //T
			screenstatus = "Timer";
						initialTime = System.currentTimeMillis();

		}

		if(key == 16){ //shift key for testing timer 
			timerDoneNotif = true;
		}
		}

		
		if(!(currentInputBox==null)) {
			char character = e.getKeyChar();
			
			currentInput = currentInputBox.getAffiliatedText();
									
			if(key!=16 && key!=8 && currentInputBox.getInputStatus()){
				for(int i=currentInput.length(); i>0;i--) {
					if(currentInput.substring(i-1,i).equals("|")) {
						currentInput = currentInput.substring(0,i-1) + currentInput.substring(i,currentInput.length());
						break;
					}
				}
				currentInput = currentInput + character;
			}  else if(key == 8 && currentInputBox.getDeleteAllowed()){
				if(currentInput.length()==1 || currentInput.length()==0) {
					currentInput = "";
				} else {
					currentInput = currentInput.substring(0,currentInput.length()-2);
				}
			}
			
			currentInputBox.setAffiliatedText(currentInput);

			if(key==10) {
			}
		}
	}

	//Key Release
	@Override
	public void keyReleased(KeyEvent e) {
		
		key = e.getKeyCode();
		
		if(key == 65 || key == 37) { // A or <-
			moving = false;
		}
		if(key == 87 || key == 38) { // W or ^
			moving = false;
		}
		if(key == 68 || key == 39) { // D or ->
			moving = false;
		}
		if(key == 83 || key == 40) { // D or ->
			moving = false;
		}
		
		
	}


	/*
 	 _      ___   _     __   ____      _     _   __  _____  ____  _      ____  ___       _      ____ _____  _     ___   ___   __  
	| |\/| / / \ | | | ( (` | |_      | |   | | ( (`  | |  | |_  | |\ | | |_  | |_)     | |\/| | |_   | |  | |_| / / \ | | \ ( (` 
	|_|  | \_\_/ \_\_/ _)_) |_|__     |_|__ |_| _)_)  |_|  |_|__ |_| \| |_|__ |_| \     |_|  | |_|__  |_|  |_| | \_\_/ |_|_/ _)_) 
	
	TODO Mouse Listener Methods
	 */
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(screenstatus.equals("Interior")){
			if(dragNDrop){
			for(CharacterObject c: storeOptions){
				if(c.getAdded()&&c.hover(e.getX(),e.getY())){
					c.setX(e.getX()-c.getW()/2);
					c.setY(e.getY()-c.getH()/2);
					System.out.println(c.getY());
					break;
				}
			}
		}
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		
		if(screenstatus.equals("Start")) {
			if(invisibleButton.hover(e.getX(), e.getY())){
				startButton.setImg(new ImageIcon("Spaceship Animation.gif"));
			} else {
				startButton.setImg(new ImageIcon("Spaceship Icon.png"));
			}	
			
			
			if(aboutButton.hover(e.getX(), e.getY())) {
				aboutButton.setImg(new ImageIcon ("AboutHover2.png"));
			} else {
				aboutButton.setImg(new ImageIcon("AboutButtonn2.png"));
			}
			
			if(storeButton.hover(e.getX(), e.getY())) {
				storeButton.setImg(new ImageIcon ("StoreHover2.png"));
			} else {
				storeButton.setImg(new ImageIcon("StoreButton2.png"));
			}
		}
		
		
		if(homeButton.hover(e.getX(), e.getY())) {
			homeButton.setImg(new ImageIcon ("HomeHover2.png"));
		} else {
			homeButton.setImg(new ImageIcon("HomeButton2.png"));
		}
		
		if(screenstatus.equals("Play")){
			if(taskButton.hover(e.getX(), e.getY())){
				taskButton.setImg(new ImageIcon("NewTaskHover.png"));
			}else{
				taskButton.setImg(new ImageIcon("NewTaskButton.png"));
			}

			if(viewTasksButton.hover(e.getX(), e.getY())){
				viewTasksButton.setImg(new ImageIcon ("NewViewTaskHover.png"));
			}else{
				viewTasksButton.setImg(new ImageIcon ("NewViewTaskButton.png"));
			}
		}

		if(screenstatus.equals("Input")){
			if(finishedInputtingTask.hover(e.getX(), e.getY())){
				finishedInputtingTask.setImg(new ImageIcon ("CheckmarkHover.png"));
			}else{
				finishedInputtingTask.setImg(new ImageIcon("Checkmark2.png"));
			}
			if(finishEnteringTaskNotif || taskAdded){
				if(XButton.hover(e.getX(),e.getY())){
					XButton.setImg(new ImageIcon("X Button Highlighted.png"));
				} else {
					XButton.setImg(new ImageIcon("X Button.png"));
				}
			}
		}

		if(screenstatus.equals("Reorder Tasks")){
			if(forwardButton.hover(e.getX(), e.getY())){
				forwardButton.setImg(new ImageIcon ("MoveForwardHover.png"));
			} else{
				forwardButton.setImg(new ImageIcon("Move Forward Button.png"));
			}

			if(backwardButton.hover(e.getX(), e.getY())){
				backwardButton.setImg(new ImageIcon ("MoveBackwardHover.png"));
			} else{
				backwardButton.setImg(new ImageIcon ("MoveBackwardButton.png"));
			}

			if(rightArrowButton.hover(e.getX(), e.getY())){
				rightArrowButton.setImg(new ImageIcon ("RightArrowHover.png"));
			} else{
				rightArrowButton.setImg(new ImageIcon ("RightArrowButton.png"));
			}

			if(leftArrowButton.hover(e.getX(), e.getY())){
				leftArrowButton.setImg(new ImageIcon ("LeftArrowHover.png"));
			} else{
				leftArrowButton.setImg(new ImageIcon ("LeftArrowButton.png"));
			}

			if(selectButton.hover(e.getX(), e.getY())){
				selectButton.setImg(new ImageIcon ("SelectHover.png"));
			} else{
				selectButton.setImg(new ImageIcon ("SelectButton.png"));
			}
			if(removeButton.hover(e.getX(), e.getY())){
				removeButton.setImg(new ImageIcon("RemoveHover.png"));
			} else{
				removeButton.setImg(new ImageIcon("RemoveButton.png"));
			}

			if(confirmTaskDeletionNotif){
				if(yesButton.hover(e.getX(), e.getY())){
					yesButton.setImg(new ImageIcon ("YesButtonHover.png"));
				} else{
					yesButton.setImg(new ImageIcon ("YesButton.png"));
				}
	
				if(noButton.hover(e.getX(), e.getY())){
					noButton.setImg(new ImageIcon ("NoButtonHover.png"));
				} else{
					noButton.setImg(new ImageIcon ("NoButton.png"));
				}
			}
		}

		if(screenstatus.equals("Timer")){

			if(!stupidvscode){
				if(invisibleButton2.hover(e.getX(), e.getY())){
					finishedTimerInput.setImg(new ImageIcon ("Still Frame Timer Input Button Hover.png"));
				} else{
					finishedTimerInput.setImg(new ImageIcon("Still Frame Timer Input Button.png"));
				}
			}
		}

		if(timerDoneNotif){
			if(yesButton.hover(e.getX(), e.getY())){
				yesButton.setImg(new ImageIcon ("YesButtonHover.png"));
			} else{
				yesButton.setImg(new ImageIcon ("YesButton.png"));
			}

			if(noButton.hover(e.getX(), e.getY())){
				noButton.setImg(new ImageIcon ("NoButtonHover.png"));
			} else{
				noButton.setImg(new ImageIcon ("NoButton.png"));
			}
		}

		if(screenstatus.equals("Choose Reward")){
			if(claimRewardButton.hover(e.getX(), e.getY())){
				claimRewardButton.setImg(new ImageIcon ("ClaimRewardHover.png"));
			} else{
				claimRewardButton.setImg(new ImageIcon ("ClaimReward.png"));
			}
			if(selectNewTaskButton.hover(e.getX(), e.getY())){
				selectNewTaskButton.setImg(new ImageIcon("SelectNewTaskHover.png"));
			}else{
				selectNewTaskButton.setImg(new ImageIcon("SelectNewTask.png"));
			}
		}
		if(screenstatus.equals("Set New Timer")){
			if(claimRewardButton.hover(e.getX(), e.getY())){
				claimRewardButton.setImg(new ImageIcon ("ClaimRewardHover.png"));
			} else{
				claimRewardButton.setImg(new ImageIcon ("ClaimReward.png"));
			}
			if(setNewTimerButton.hover(e.getX(), e.getY())){
				setNewTimerButton.setImg(new ImageIcon ("SetNewTimerHover.png"));
			} else{
				setNewTimerButton.setImg(new ImageIcon ("SetNewTimerButton.png"));
			}
		}

		if(screenstatus.equals("Claim Reward")){
			if(backButton.hover(e.getX(), e.getY())){
				backButton.setImg(new ImageIcon ("BackHover.png"));
			} else{
				backButton.setImg(new ImageIcon ("BackButton.png"));
			}
		}

		if(screenstatus.equals("Store")){
			if(rightArrowButton.hover(e.getX(), e.getY())){
				rightArrowButton.setImg(new ImageIcon ("RightArrowHover.png"));
			} else{
				rightArrowButton.setImg(new ImageIcon ("RightArrowButton.png"));
			}

			if(leftArrowButton.hover(e.getX(), e.getY())){
				leftArrowButton.setImg(new ImageIcon ("LeftArrowHover.png"));
			} else{
				leftArrowButton.setImg(new ImageIcon ("LeftArrowButton.png"));
			}

			//coconuts
			if(purchaseButton.hover(e.getX(), e.getY())){
				purchaseButton.setImg(new ImageIcon ("PurchaseButtonHover.png"));
				if(storeOptions.get(storeIteratePos).getUnlocked()){
					purchaseButton.setImg(new ImageIcon ("PurchasedButton.png"));
				}
			} else{
				purchaseButton.setImg(new ImageIcon ("PurchaseButton.png"));
				if(storeOptions.get(storeIteratePos).getUnlocked()){
					purchaseButton.setImg(new ImageIcon ("PurchasedButton.png"));
				}
			}

			if(addButton.hover(e.getX(), e.getY())){
				addButton.setImg(new ImageIcon ("AddButtonHover.png"));
				if(storeOptions.get(storeIteratePos).getAdded()){
					addButton.setImg(new ImageIcon ("AddedButton.png"));
				}
			} else{
				addButton.setImg(new ImageIcon ("AddButton.png"));
				if(storeOptions.get(storeIteratePos).getAdded()){
					addButton.setImg(new ImageIcon ("AddedButton.png"));
				}
			}
		}
		if(screenstatus.equals("Interior")){
			if(backToTimerButton.hover(e.getX(), e.getY())){
				backToTimerButton.setImg(new ImageIcon ("BackToTimerHover.png"));
			} else{
				backToTimerButton.setImg(new ImageIcon ("BackToTimerButton.png"));
			}

			if(dragNDrop){
				if(editButton.hover(e.getX(), e.getY())){
					editButton.setImg(new ImageIcon ("Edit Icon.png"));
				} else{
					editButton.setImg(new ImageIcon ("Edit Icon Light.png"));
				}
			} else {
				if(editButton.hover(e.getX(), e.getY())){
					editButton.setImg(new ImageIcon ("Edit Icon Light.png"));
				} else{
					editButton.setImg(new ImageIcon ("Edit Icon.png"));
				}
			}
		}
		
		
	}
	
	public void operateTextBox(TextBox a) {
		if(!currentInput.equals("")){
		if(currentInput.substring(currentInput.length()-1,currentInput.length()).equals("|")) {
			currentInput = currentInput.substring(0,currentInput.length()-1);
			a.setAffiliatedText(currentInput);
		}
	}	
		a.setInputStatus(true);
		currentInputBox = a;
		currentInput = a.getAffiliatedText();
		make = true;
		currentInput = currentInput + "|";
		a.setAffiliatedText(currentInput);
	}

	public void doNotOperateTextBox(TextBox a) {
		currentInput = a.getAffiliatedText();
		if(currentInput.length() != 0){
			if(currentInput.substring(currentInput.length()-1,currentInput.length()).equals("|")) {
				currentInput = currentInput.substring(0,currentInput.length()-1);
				a.setAffiliatedText(currentInput);
			}
		}
		a = null;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {		

		//System.out.println(e.getX() + ", " + e.getY());
		
		if(screenstatus.equals("Start")) {
			if(invisibleButton.hover(e.getX(), e.getY())){
				screenstatus = "Play";
				
			} else if(aboutButton.hover(e.getX(), e.getY())){
				screenstatus = "About";
			} else if(storeButton.hover(e.getX(), e.getY())) {
				screenstatus = "Store";
			}
		}
		
		else if(screenstatus.equals("About")) {
			if(homeButton.hover(e.getX(), e.getY())) {
				screenstatus = "Start";
			}
		}
		
		else if(screenstatus.equals("Store")) {
			if(homeButton.hover(e.getX(), e.getY())) {
				screenstatus = "Start";
			}

			if(rightArrowButton.hover(e.getX(), e.getY())){
				if(storeIteratePos<storeOptions.size()-1){
					storeIteratePos++;
				}
			}
	
			 else if(leftArrowButton.hover(e.getX(), e.getY())){
				if(storeIteratePos>0){
					storeIteratePos--;
				}

			}

			if(purchaseButton.hover(e.getX(), e.getY())){
				//coconuts add an if statement for if you have enough coins and make a warning pop up if you don't
				storeOptions.get(storeIteratePos).setUnlocked(true);
				if(coins>storeOptions.get(storeIteratePos).getPrice()){
					storeOptions.get(storeIteratePos).setUnlocked(true);
					coins = coins-(storeOptions.get(storeIteratePos).getPrice());
				}
				
			}

			if(addButton.hover(e.getX(), e.getY()) && storeOptions.get(storeIteratePos).getUnlocked()){

				if(storeOptions.get(storeIteratePos).getAdded() == false){
					storeOptions.get(storeIteratePos).setAdded(true);
				} else {
					storeOptions.get(storeIteratePos).setAdded(false);
				}
			}
		}
		
		else if(screenstatus.equals("Play")) {
			if(taskButton.hover(e.getX(), e.getY())){
				screenstatus = "Input";
			}else if(!tasks.isEmpty()){
				if(viewTasksButton.hover(e.getX(), e.getY())){
					screenstatus = "Reorder Tasks";
				}
			}
		}
		
		
		else if(screenstatus.equals("Input")){
			boolean tempb = false;
			if(finishEnteringTaskNotif || taskAdded){
				if(XButton.hover(e.getX(),e.getY())){
					
					if(finishEnteringTaskNotif){
					finishEnteringTaskNotif = false;
					} else if(taskAdded){
						taskAdded = false;
						screenstatus = "Start";
					}
				}
			} else {
			if(taskNameInput.hover(e.getX(), e.getY())) {
				operateTextBox(taskNameInput);
				tempb = true;
			} else {
				doNotOperateTextBox(taskNameInput);
			}
			if(taskDateInput.hover(e.getX(), e.getY())) {
				operateTextBox(taskDateInput);
				tempb = true;
			} else {
				doNotOperateTextBox(taskDateInput);
			}
			if(taskRewardInput.hover(e.getX(), e.getY())) {
				operateTextBox(taskRewardInput);
				tempb = true;
			} else {
				doNotOperateTextBox(taskRewardInput);
			}
			/*if(taskPositionInput.hover(e.getX(), e.getY())){
				operateTextBox(taskPositionInput);
				tempb=true;
			} else{
				doNotOperateTextBox(taskPositionInput);
			}*/

			if(!tempb){
				currentInputBox = null;
			}
			
			if(!(currentInput == null)){
				if(finishedInputtingTask.hover(e.getX(), e.getY())){

					temporaryTaskReward = taskRewardInput.getAffiliatedText();
					temporaryTaskName = taskNameInput.getAffiliatedText();
					temporaryTaskDate = taskDateInput.getAffiliatedText();
					temporaryTaskPositionInQueue = taskPositionInput.getAffiliatedText();

					iteratedReward = temporaryTaskReward.substring(8,temporaryTaskReward.length());
					iteratedName = temporaryTaskName.substring(6,temporaryTaskName.length());
					iteratedDate = temporaryTaskDate.substring(10,temporaryTaskDate.length());
					iteratedPosition = temporaryTaskPositionInQueue.substring(9,temporaryTaskPositionInQueue.length());

					if((!iteratedReward.equals("")) && !iteratedName.equals("")&& !iteratedDate.equals("") && !iteratedPosition.equals("")){
						tasks.add(new Task(iteratedReward, iteratedName,iteratedDate,iteratedPosition));
						System.out.println(tasks.get(0).getTaskName());
						taskAdded = true;

						taskNameInput.setAffiliatedText("Task: ");
						taskDateInput.setAffiliatedText("Due Date: ");
						taskRewardInput.setAffiliatedText("Reward: ");
						//taskPositionInput.setAffiliatedText("Position: ");
					} else {
						finishEnteringTaskNotif = true;
					}

					

				}
			} }}

		else if(screenstatus.equals("Reorder Tasks")){
			if(rightArrowButton.hover(e.getX(), e.getY())){
				if(taskIteratePos<tasks.size()-1){
					taskIteratePos++;
				}
			}
	
			if(leftArrowButton.hover(e.getX(), e.getY())){
				if(taskIteratePos>0){
					taskIteratePos--;
				}
			}
			
			if(!tasks.isEmpty()){
				if(selectButton.hover(e.getX(), e.getY())){
					screenstatus = "Timer";
				}
				if(removeButton.hover(e.getX(), e.getY())){
					confirmTaskDeletionNotif = true;
				}
				if(confirmTaskDeletionNotif){
					if(yesButton.hover(e.getX(), e.getY())){
						tasks.remove(taskIteratePos);
						confirmTaskDeletionNotif = false;
					}
					if(noButton.hover(e.getX(), e.getY())){
						confirmTaskDeletionNotif = false;
					}
				}
			}
		}
		
		else if(screenstatus.equals("Timer")){
			boolean tempb = false;
			if(hoursInput.hover(e.getX(), e.getY())) {
				operateTextBox(hoursInput);
				tempb = true;
			} else {
				doNotOperateTextBox(hoursInput);
			}

			if(minutesInput.hover(e.getX(), e.getY())) {
				operateTextBox(minutesInput);
				tempb = true;
			} else {
				doNotOperateTextBox(minutesInput);
			}

			
			if(invisibleButton2.hover(e.getX(), e.getY())){

				if(!minutesInput.getAffiliatedText().isEmpty()){
						
					if(!hoursInput.getAffiliatedText().isEmpty()){
							setHours = Integer.valueOf(hoursInput.getAffiliatedText());
					} else {
							setHours = 0;
					}
					setMinutes = Integer.valueOf(minutesInput.getAffiliatedText());
					//System.out.println(setMinutes);
					nullThese();
					begintime = System.currentTimeMillis();
					finishedTimerInput.setImg(new ImageIcon ("Timer Input GIF.gif"));
					stupidvscode = true;
					timer = true;

						//System.out.println(setHours + ":" + setMinutes);
					} else {
						stupidvscode = false;
					}
				} 
			if(!tempb){
				currentInputBox = null;
			}
		}

		else if(screenstatus.equals("Countdown")){
			if(timerDoneNotif){
				if(yesButton.hover(e.getX(), e.getY())){
					screenstatus = "Choose Reward";
				}
				if(noButton.hover(e.getX(), e.getY())){
					screenstatus = "Set New Timer";
				}
			} else {
				Button spaceshipBu;
				spaceshipBu = new Button((int)testX,(int)testY,(int)miniSpaceship.getW(),(int)miniSpaceship.getH(),new ImageIcon(""));

				if(spaceshipBu.hover(e.getX(),e.getY())){
					screenstatus = "Interior";
				} else {
					System.out.println(spaceshipBu.getX() + " and " + spaceshipBu.getY());
				}
			}
		}
		if(screenstatus.equals("Interior")){
			if(backToTimerButton.hover(e.getX(), e.getY())){
				screenstatus = "Countdown";
			} 
			if(editButton.hover(e.getX(), e.getY())){
				dragNDrop = !dragNDrop;
			} 
		}

		else if(screenstatus.equals("Choose Reward")){
			if(claimRewardButton.hover(e.getX(), e.getY())){
				screenstatus = "Claim Reward";
				addToCoins = true;
			}
			if(selectNewTaskButton.hover(e.getX(), e.getY())){
				screenstatus = "Reorder Tasks";
				if(tasks.size()>0){
					tasks.remove(taskIteratePos);
				}
			}
		}

		else if(screenstatus.equals("Set New Timer")){
			if(claimRewardButton.hover(e.getX(), e.getY())){
				screenstatus = "Claim Reward";
				addToCoins = true;
			}
			if(setNewTimerButton.hover(e.getX(), e.getY())){
				screenstatus = "Timer";
				timerDoneNotif = false;
				setMinutes=0;
				setHours=0;
				if(!minutesInput.getAffiliatedText().isEmpty()){
					minutesInput.setAffiliatedText("");
				}
				if(!hoursInput.getAffiliatedText().isEmpty()){
					hoursInput.setAffiliatedText("");
				}
				
			}
		}

		else if(screenstatus.equals("Claim Reward")){
			if(screenComingFrom.equals("Choose Reward")){
				if(backButton.hover(e.getX(), e.getY())){
					screenstatus = "Choose Reward";
				} 
			}
			if(screenComingFrom.equals("Set New Timer")){
				if(backButton.hover(e.getX(), e.getY())){
					screenstatus = "Set New Timer";
				} 
			}
				
		}

		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}



	@Override
	public void mouseExited(MouseEvent e) {
		
	}



	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	
	/*
 	 ____  _   _     ____      _      ____ _____  _     ___   ___   __  
	| |_  | | | |   | |_      | |\/| | |_   | |  | |_| / / \ | | \ ( (` 
	|_|   |_| |_|__ |_|__     |_|  | |_|__  |_|  |_| | \_\_/ |_|_/ _)_) 
	
	TODO File Methods
	 */
	
		//Create File
		public void createFile() {
			try {
				if(file.createNewFile()) {
					System.out.println("File Created");
				}
				else
					System.out.println("File Already Exists");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//Read File
		public void readFromFile() {
			try {
				Scanner sc = new Scanner(file);
				//while(sc.hasNextLine()) {
					//System.out.println(sc.nextLine());
				//}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		
		//Save File
		public void saveFile() {
			try {
				FileWriter myWriter = new FileWriter(file);

				myWriter.write("Your cool file!");
		
				myWriter.close();
				System.out.println("Successfully wrote to file");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Error writing to file");
			}
		}
		
		//Refresh File
		public void refreshFromFile() {
			
		}
		
}