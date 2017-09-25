/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invincibagel;

import java.util.Iterator;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.Group;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage; // This line of code imports the Stage class 
                          // from javafx.stage package

/**
 *
 * @author Ștefan Uifălean
 */
public class InvinciBagel extends Application {

    private static final double WIDTH = 640, HEIGHT = 400;
        
    private Scene scene;
    private Group root;
    
    private boolean movingUp, movingLeft, movingDown, movingRight;
    private boolean pressingW, pressingA, pressingS, pressingD;
        
    private Image splashScreen, instructionLayer, scoresLayer, legalLayer, skyCloud;
    private Image[] iB;
    private Image iP0, iT0, iT1, iE0, iC0, iC1;
    
    private AudioClip[] iSound;
    
    private Bagel iBagel;
    private Enemy iBeagle;
    private Projectile iBullet, iCheese;
    private Prop iPR0;
    private PropH iPH0;
    private PropV iPV0;
    private PropB iPB0;
    private Treasure iTR0, iTR1;
        
    private CastingDirector castDirector;
    
    private HBox buttonContainer;
    private Insets buttonContainerPadding;
    private Button gameButton, helpButton, scoreButton, legalButton;
    
    private int gameScore = 0;
    private Text scoreText;
    private Text scoreLabel;
    private Font scoreFont;
    
    private ImageView splashScreenBackplate, splashScreenTextArea;

    private GamePlayLoop gamePlayLoop;


    @Override
    public void start(Stage primaryStage) {
        /* Basic Stage/Scene config calls */
        primaryStage.setTitle("InvinciBagel");
        root = new Group();
        scene = new Scene(root, WIDTH, HEIGHT, Color.WHITE);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        /* Game Creation Process "container" methods calls */
        
        createSceneEventHandling();
        loadAudioAssets();
        loadImageAssets();
        createGameActors();
        addGameActorNodes();
        createCastingDirection();
        createSplashScreenNodes();
        addNodesToRoot();
        createStartGameLoop();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
     /* Scene Event Handling Process - the following method "container" */
    private void createSceneEventHandling() {
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch(event.getCode()) {
                case UP:    movingUp        = true; break;
                case LEFT:  movingLeft      = true; break;
                case DOWN:  movingDown      = true; break;
                case RIGHT: movingRight     = true; break;
                case W:     pressingW       = true; break;
                case A:     pressingA       = true; break; 
                case S:     pressingS       = true; break;
                case D:     pressingD       = true; break;
                }
            });
        
        scene.setOnKeyReleased((KeyEvent event) -> {
            switch(event.getCode()) {
                case UP:    movingUp       = false; break;
                case LEFT:  movingLeft     = false; break;
                case DOWN:  movingDown     = false; break;
                case RIGHT: movingRight    = false; break;
                case W:     pressingW      = false; break;
                case A:     pressingA      = false; break; 
                case S:     pressingS      = false; break;
                case D:     pressingD      = false; break;
            }
        });
    }
    
        /* Game Design Process - the following 4 method "containers" */
    private void loadAudioAssets() {
        iSound = new AudioClip[6];
        for (byte i = 0; i < iSound.length; ++i) {
            iSound[i] = new AudioClip(getClass().
                        getResource("/assets/iAudio" + i + ".wav").toString());
        }
    }
    
    private void loadImageAssets() {
        splashScreen = new Image("/assets/invincibagelsplash.png", 640, 400,
                                                          true, false, true);
        instructionLayer = new Image("/assets/invincibagelinstruct.png",
                                            640, 400, true, false, true);
        scoresLayer = new Image("/assets/invincibagelscores.png",
                                            640, 400, true, false, true);        
        legalLayer = new Image("/assets/invincibagelcreds.png", 640, 400,
                                                       true, false, true);
        skyCloud = new Image("/assets/skycloud.png", 640, 400,
                                                       true, false, true);
        
        iB = new Image[9];
        for (byte i=0; i<iB.length; ++i) {
            iB[i] = new Image( "/assets/sprite" + i + ".png", 81, 81, 
                                                 true, false, true );
        }
        iE0 = new Image("/assets/enemy.png", 116, 70, true, false, true);
        iC0 = new Image("/assets/bullet.png", 64, 24, true, false, true);
        iC1 = new Image("/assets/cheese.png", 32, 29, true, false, true);
        
        iP0 = new Image("/assets/prop0.png", 72, 32, true, false, true);
        iT0 = new Image("/assets/treasure0.png", 64, 64, true, false, true);
        iT1 = new Image("/assets/treasure1.png", 64, 64, true, false, true);
    }
    
    private void createGameActors() {
        iBagel = new Bagel(this, 
"M 56,4 L 48,24 29,28 29,41 18,41 18,44 28,56 37,57 35,73 39,81 43,81 45,55 56,40 64,42 73,28 Z",
                           WIDTH/2 - 81/2 , HEIGHT/2 - 81/2, iB);
        iBeagle = new Enemy(this,
                "M 88,0 L 116,0  116,46  94,70  70,70  70,52  0,52  0,0 Z",
                1000, 1000, iE0);
        iBullet = new Projectile(0.2, "M 0,0 L 0,24 64,24 64,0 Z", 1000, 1000, iC0);
        iCheese = new Projectile(0.1, "M 0,0 L 0,29 32,29 32,0 Z", 1000, 1000, iC1);
        
        iPR0 = new Prop("M 0,0 L 0,32 72,32 72,0 Z", 30, 48, iP0);
        iPH0 = new PropH("M 0,0 L 0,32 72,32 72,0 Z", 172, 248, iP0);
        iPV0 = new PropV("M 0,0 L 0,32 72,32 72,0 Z", 396, 116, iP0);
        iPB0 = new PropB("M 0,0 L 0,32 72,32 72,0 Z", 516, 316, iP0);
        iTR0 = new Treasure("M 0,0 L 0,64 64,64 64,0 Z", 50, 105, iT0);
        iTR1 = new Treasure("M 0,0 L 0,64 64,64 64,0 Z", 533, 206, iT1);
    }    
    
    private void addGameActorNodes() {
        root.getChildren().add(iPR0.spriteFrame);
        root.getChildren().add(iPH0.spriteFrame);
        root.getChildren().add(iPV0.spriteFrame);
        root.getChildren().add(iPB0.spriteFrame);
        root.getChildren().add(iTR0.spriteFrame);
        root.getChildren().add(iTR1.spriteFrame);
        root.getChildren().add(iBagel.spriteFrame);
        root.getChildren().add(iBeagle.spriteFrame);
        root.getChildren().add(iBullet.spriteFrame);
        root.getChildren().add(iCheese.spriteFrame);
    }
    
    private void createCastingDirection() {
        castDirector = new CastingDirector();
        castDirector.addCurrentCast(iPR0, iPH0, iPV0, iPB0, iTR0, iTR1, 
                                    iBullet, iCheese, iBeagle);
    }
    
/* Top-Level UI(Main Menu) Design process-the following 2 method "containers" */
    private void createSplashScreenNodes() {

        
        buttonContainer = new HBox(12);
        buttonContainer.setLayoutY(365);
        buttonContainerPadding = new Insets(0, 0, 10, 16);
        buttonContainer.setPadding(buttonContainerPadding);

        gameButton = new Button();
        gameButton.setText("PLAY GAME");
        gameButton.setOnAction((ActionEvent event) -> {
            splashScreenBackplate.setImage(skyCloud);
            splashScreenBackplate.toBack();
            splashScreenTextArea.setVisible(false);
        });
        
        helpButton = new Button();
        helpButton.setText("INSTRUCTIONS");
        helpButton.setOnAction((ActionEvent event) -> {
            splashScreenBackplate.setImage(splashScreen);
            splashScreenBackplate.toFront();
            splashScreenTextArea.setVisible(true);
            splashScreenTextArea.toFront();
            splashScreenTextArea.setImage(instructionLayer);
            buttonContainer.toFront();
        });
        
        scoreButton = new Button();
        scoreButton.setText("HIGH SCORES");
        scoreButton.setOnAction((ActionEvent event) -> {
            splashScreenBackplate.setImage(splashScreen);
            splashScreenBackplate.toFront();
            splashScreenTextArea.setVisible(true);
            splashScreenTextArea.toFront();
            splashScreenTextArea.setImage(scoresLayer);
            buttonContainer.toFront();
        });
        
        
        legalButton = new Button();
        legalButton.setText("LEGAL & CREDITS");
        legalButton.setOnAction((ActionEvent event) -> {
            splashScreenBackplate.setImage(splashScreen);
            splashScreenBackplate.toFront();
            splashScreenTextArea.setVisible(true);
            splashScreenTextArea.toFront();
            splashScreenTextArea.setImage(legalLayer);
            buttonContainer.toFront();
        });
        

        buttonContainer.getChildren().addAll(gameButton, helpButton,
                                           scoreButton, legalButton);
        
        scoreText = new Text();
        scoreText.setText(String.valueOf(gameScore));
        scoreText.setLayoutY(390);
        scoreText.setLayoutX(610);
        scoreFont = new Font("Verdana", 20);
        scoreText.setFont(scoreFont);
        scoreText.setFill(Color.BLUE);
        scoreLabel = new Text();
        scoreLabel.setText("SCORE:");
        scoreLabel.setLayoutY(390);
        scoreLabel.setLayoutX(530);
        scoreLabel.setFont(scoreFont);
        scoreLabel.setFill(Color.BLACK);
        
        splashScreenBackplate = new ImageView();
        splashScreenBackplate.setImage(splashScreen);

        splashScreenTextArea = new ImageView();
        splashScreenTextArea.setImage(instructionLayer);
    }

    private void addNodesToRoot() {
        root.getChildren().add(splashScreenBackplate);
        root.getChildren().add(splashScreenTextArea);
        root.getChildren().add(buttonContainer);
        root.getChildren().add(scoreText);
        root.getChildren().add(scoreLabel);
    }

  /* GamePlay Loop Design Process - this following last method "container" */
    private void createStartGameLoop() {
        gamePlayLoop = new GamePlayLoop(this);
        gamePlayLoop.start();
    }

    
    
                   /* Helper & Data-Request Methods */
    public static double getWIDTH() {
        return WIDTH;
    }

    public static double getHEIGHT() {
        return HEIGHT;
    }

    public int getGameScore() {
        return gameScore;
    }

    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }
    
    public Text getScoreText() {
        return scoreText;
    }
    
    public Group getRoot() {
        return root;
    }
    
    public boolean isMovingUp() {
        return movingUp;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public boolean isMovingDown() {
        return movingDown;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public boolean isPressingW() {
        return pressingW;
    }

    public boolean isPressingA() {
        return pressingA;
    }

    public boolean isPressingS() {
        return pressingS;
    }

    public boolean isPressingD() {
        return pressingD;
    }

    public void playiSound(int index) {
        this.iSound[index].play();
    }
    
    public Bagel getiBagel() {
        return iBagel;
    }

    public Enemy getiBeagle() {
        return iBeagle;
    }

    public Projectile getiBullet() {
        return iBullet;
    }

    public Projectile getiCheese() {
        return iCheese;
    }
    
    public CastingDirector getCastDirector() {
        return castDirector;
    }
    
    /**
     * (re)loads from memory the object whom object parameter references 
     * by adding it (back) to the Casting Director and Scene Graph
     * @param <T> a class that extends Actor
     * @param object reference to an Actor object loaded in memory 
     */
    public <T extends Actor> void loadNotDuplicate(T object) {
        for (Iterator iterator = castDirector.getCurrentCast().iterator(); 
                iterator.hasNext();) {
            T next = (T)iterator.next();
            // if instance of that object exists do NOT duplicate
            if(next.equals(object)) {
                return;
            }
        }
        // otherwise load object from memory
        this.castDirector.addCurrentCast(object);
        this.root.getChildren().add(object.spriteFrame);
    }
}