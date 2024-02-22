import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class DuckHunt extends Application{
    private static final double SCALE = 2.0 ;
    private static final double VOLUME = 0.225;
    private List<MediaPlayer> mediaPlayers;
    private final double width = 250;

    private final double height = 240;
    private BackgroundPane background;
    private ForegroundPane foreground;
    private Cross cross;
    private StackPane backPane;
    private int lvlClickCount = 3;
    private Label clickCountLabel;
    private DuckHorizontal duckHorizontalLvl1;
    private DuckHorizontal duckHorizontalLvl3A;
    private DuckHorizontal duckHorizontalLvl3B;
    private DuckHorizontal duckHorizontalLvl4;
    private DuckHorizontal duckHorizontalLevel6;

    private boolean firstTime = true;
    private int levelOneCheck = 0;
    private int levelTwoCheck = 0;
    private int levelThreeCheck = 0;
    private int levelFourCheck = 0;
    private int levelFiveCheck = 0;
    private int levelSixCheck = 0;
    private Image blackduckFallImage;
    private Image blueduckFallImage;
    private Image redduckFallImage;
    private Image blackBeforeDuckFallImage;
    private Image blueBeforeDuckFallImage;
    private Image redBeforeDuckFallImage;
    private DuckX duckLevel2;
    private DuckX duckXLevel4;
    private DuckX duckXLevel5A;
    private DuckX duckXLevel5B;
    private DuckX duckXLevel6A;
    private DuckX duckXLevel6B;
    private String path = DuckHunt.class.getProtectionDomain().getCodeSource().getLocation().getPath();


    @Override

    public void start(Stage primaryStage) throws FileNotFoundException {

        mediaPlayers = new ArrayList<>();

        addSound("assets/effects/Title.mp3");
        addSound(path + File.separator + "assets/effects/LevelCompleted.mp3");
        addSound(path + File.separator + "assets/effects/Intro.mp3");
        addSound(path + File.separator + "assets/effects/Gunshot.mp3");
        addSound(path + File.separator + "assets/effects/GameOver.mp3");
        addSound(path + File.separator + "assets/effects/GameCompleted.mp3");
        addSound(path + File.separator + "assets/effects/DuckFalls.mp3");
        mediaPlayers.get(0).setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayers.get(0).play();
        for (MediaPlayer media : mediaPlayers){
            media.setVolume(VOLUME);
        }

        FileInputStream favicon = new FileInputStream(path + File.separator + "assets/favicon/1.png");
        Image faviconImage = new Image(favicon);
        primaryStage.getIcons().add(faviconImage);

        Scene welcomeScene = new Scene(welcomePane(),width*SCALE,height*SCALE);
        primaryStage.setScene(welcomeScene);
        primaryStage.setTitle("HUBMM DUCK HUNT");
        primaryStage.show();


        FileInputStream duckFallBlack = new FileInputStream(path + File.separator + "assets/duck_black/8.png");
        blackduckFallImage = new Image(duckFallBlack);
        FileInputStream duckFallBlue = new FileInputStream(path + File.separator + "assets/duck_blue/8.png");
        blueduckFallImage = new Image(duckFallBlue);
        FileInputStream duckFallRed = new FileInputStream(path + File.separator + "assets/duck_red/8.png");
        redduckFallImage = new Image(duckFallRed);
        FileInputStream blackBeforeFall = new FileInputStream(path + File.separator + "assets/duck_black/7.png");
        blackBeforeDuckFallImage = new Image(blackBeforeFall);
        FileInputStream blueBeforeFall = new FileInputStream(path + File.separator + "assets/duck_blue/7.png");
        blueBeforeDuckFallImage = new Image(blueBeforeFall);
        FileInputStream redBeforeFall = new FileInputStream(path + File.separator + "assets/duck_red/7.png");
        redBeforeDuckFallImage = new Image(redBeforeFall);


        StackPane optionPane = new StackPane();
        StackPane lvl1Pane = new StackPane();
        StackPane lvl2Pane = new StackPane();
        StackPane lvl3Pane = new StackPane();
        StackPane lvl4Pane = new StackPane();
        StackPane lvl5Pane = new StackPane();
        StackPane lvl6Pane = new StackPane();
        Scene optionScene = new Scene(optionPane,width*SCALE,height*SCALE);
        Scene firstLevelScene = new Scene(lvl1Pane,width*SCALE,height*SCALE);
        Scene secondLevelScene = new Scene(lvl2Pane , width*SCALE, height*SCALE);
        Scene thirdLevelScene = new Scene(lvl3Pane,width*SCALE, height*SCALE);
        Scene fourthLevelScene = new Scene(lvl4Pane,width*SCALE,height*SCALE);
        Scene fifthLevelScene = new Scene(lvl5Pane,width*SCALE,height*SCALE);
        Scene sixthLevelScene = new Scene(lvl6Pane,width*SCALE,height*SCALE);

        Font font = new Font("Arial",7*SCALE);
        Color color = Color.LIGHTGREEN;
        clickCountLabel = new Label("Ammo Left:" + lvlClickCount);
        clickCountLabel.setFont(font);
        clickCountLabel.setTextFill(color);




        welcomeScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                // Change to the second screen
                try {
                    optionsPane(optionPane);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                primaryStage.setScene(optionScene);
            }
            else if (event.getCode().toString().equals("ESCAPE")) {
                // Close the application
                Platform.exit();
            }
        });
        optionScene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE){
               primaryStage.setScene(welcomeScene);
            }
            else if (event.getCode() == KeyCode.UP) {
                cross.changeToPreviousBackground();
            }

            else if (event.getCode() == KeyCode.DOWN) {
                cross.changeToNextBackground();
            }

            else if (event.getCode() == KeyCode.LEFT) {
                background.changeToPreviousBackground();
                foreground.changeToPreviousBackground();
            }

            else if (event.getCode() == KeyCode.RIGHT) {
                background.changeToNextBackground();
                foreground.changeToNextBackground();
            }

            else if(event.getCode() == KeyCode.ENTER){
                mediaPlayers.get(0).stop();
                if (firstTime){
                    mediaPlayers.get(2).play();
                    mediaPlayers.get(2).setOnEndOfMedia(() ->{
                        firstLevelMusicOrNot(primaryStage, lvl1Pane, firstLevelScene);
                        firstTime = false;
                    });
                }
                else{
                    firstLevelMusicOrNot(primaryStage, lvl1Pane, firstLevelScene);
                }
            }
        });



        firstLevelScene.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            // Play the sound
            mediaPlayers.get(3).seek(mediaPlayers.get(3).getStartTime());
            mediaPlayers.get(3).play();
        });
        firstLevelScene.setOnMouseClicked(event -> {
            lvlClickCount--;
            clickCountLabel.setText("Ammo Left: " + lvlClickCount);
            if (levelOneCheck != 1){
                if(lvlClickCount == 0){
                    mediaPlayers.get(4).play();
                    outOfBullet(primaryStage,optionPane,lvl1Pane,firstLevelScene,
                            firstLevelScene,welcomeScene,levelOneCheck,lvl1Pane);
                }


            }
            else {
                mediaPlayers.get(1).play();
                winText(lvl1Pane);
                firstLevelScene.setOnKeyPressed(event1 -> {
                    if(event1.getCode() == KeyCode.ENTER){
                        mediaPlayers.get(1).stop();
                        lvlClickCount = 3;
                        clickCountLabel.setText("Ammo Left: " + lvlClickCount);
                        levelPanes(lvl2Pane,"Level 2");
                        try {
                            duckLevel2 = new DuckX("blue");
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        lvl2Pane.getChildren().add(duckLevel2.imageView);
                        lvl2Pane.getChildren().add(foreground.foregroundImageView);
                        duckLevel2.imageView.setOnMouseClicked(this::handleDuckClickedLevel2);
                        primaryStage.setScene(secondLevelScene);
                    }
                });
            }
        });



        secondLevelScene.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            // Play the sound
            mediaPlayers.get(3).seek(mediaPlayers.get(3).getStartTime());
            mediaPlayers.get(3).play();
        });
        secondLevelScene.setOnMouseClicked(event -> {
            lvlClickCount--;
            clickCountLabel.setText("Ammo Left: " + lvlClickCount);
            if (levelTwoCheck != 1){
                if(lvlClickCount == 0){
                    mediaPlayers.get(4).play();
                    outOfBullet(primaryStage, optionPane, lvl2Pane, firstLevelScene,
                            secondLevelScene,welcomeScene,levelTwoCheck,lvl1Pane);
                    levelOneCheck = 0;}
            }
            else {
                mediaPlayers.get(1).play();
                winText(lvl2Pane);
                secondLevelScene.setOnKeyPressed(event1 -> {
                    if(event1.getCode() == KeyCode.ENTER){
                        mediaPlayers.get(1).stop();
                        lvlClickCount = 6;
                        clickCountLabel.setText("Ammo Left: " + lvlClickCount);
                        levelPanes(lvl3Pane,"Level 3");
                        try {
                            duckHorizontalLvl3A = new DuckHorizontal("blue");
                            duckHorizontalLvl3B = new DuckHorizontal("black");
                            duckHorizontalLvl3B.imageView.setTranslateY(-(33)*SCALE);

                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        lvl3Pane.getChildren().addAll(duckHorizontalLvl3A.imageView,duckHorizontalLvl3B.imageView);
                        lvl3Pane.getChildren().add(foreground.foregroundImageView);
                        duckHorizontalLvl3A.imageView.setOnMouseClicked(this::handleDuckClickedLevel3A);
                        duckHorizontalLvl3B.imageView.setOnMouseClicked(this::handleDuckClickedLevel3B);
                        levelTwoCheck = 0;
                        primaryStage.setScene(thirdLevelScene);
                    }
                });
            }
        });

        thirdLevelScene.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            // Play the sound
            mediaPlayers.get(3).seek(mediaPlayers.get(3).getStartTime());
            mediaPlayers.get(3).play();
        });
        thirdLevelScene.setOnMouseClicked(event -> {
            lvlClickCount--;
            clickCountLabel.setText("Ammo Left: " + lvlClickCount);
            if (levelThreeCheck != 2){
                if(lvlClickCount == 0){
                    mediaPlayers.get(4).play();
                    outOfBullet(primaryStage, optionPane, lvl3Pane, firstLevelScene,
                            thirdLevelScene,welcomeScene, levelThreeCheck,lvl1Pane);
                    levelOneCheck = 0;
                    levelTwoCheck = 0;}

            }
            else{
                mediaPlayers.get(1).play();
                winText(lvl3Pane);
                thirdLevelScene.setOnKeyPressed(event1 -> {
                    if(event1.getCode() == KeyCode.ENTER){
                        mediaPlayers.get(1).stop();
                        lvlClickCount = 6;
                        clickCountLabel.setText("Ammo Left: " + lvlClickCount);
                        levelPanes(lvl4Pane,"Level 4");
                        try{
                            duckXLevel4 = new DuckX("red");
                            duckHorizontalLvl4 = new DuckHorizontal("red");
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        lvl4Pane.getChildren().addAll(duckXLevel4.imageView,duckHorizontalLvl4.imageView);
                        lvl4Pane.getChildren().add(foreground.foregroundImageView);

                        duckXLevel4.imageView.setOnMouseClicked(this::handleDuckClickedLevel4X);
                        duckHorizontalLvl4.imageView.setOnMouseClicked(this::handleDuckClickedLevel4);

                        levelThreeCheck = 0;
                        primaryStage.setScene(fourthLevelScene);
                    }
                });

            }
        });
        fourthLevelScene.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            // Play the sound
            mediaPlayers.get(3).seek(mediaPlayers.get(3).getStartTime());
            mediaPlayers.get(3).play();
        });
        fourthLevelScene.setOnMouseClicked(event -> {
            lvlClickCount--;
            clickCountLabel.setText("Ammo Left: " + lvlClickCount);
            if(levelFourCheck != 2){
                if(lvlClickCount == 0){
                    mediaPlayers.get(4).play();
                    outOfBullet(primaryStage, optionPane, lvl4Pane, firstLevelScene,
                        fourthLevelScene,welcomeScene,levelFourCheck,lvl1Pane);
                    levelOneCheck = 0;
                    levelTwoCheck = 0;
                    levelThreeCheck = 0;}
            }
            else{
                winText(lvl4Pane);
                mediaPlayers.get(1).play();
                fourthLevelScene.setOnKeyPressed(event1 -> {
                    if(event1.getCode() == KeyCode.ENTER){
                        mediaPlayers.get(1).stop();
                        lvlClickCount = 6;
                        clickCountLabel.setText("Ammo Left: " + lvlClickCount);
                        levelPanes(lvl5Pane,"Level 5");
                        try {
                            duckXLevel5A = new DuckX("blue");
                            duckXLevel5B = new DuckX("black");
                            duckXLevel5B.imageView.setTranslateY(0);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        lvl5Pane.getChildren().addAll(duckXLevel5A.imageView,duckXLevel5B.imageView);
                        lvl5Pane.getChildren().add(foreground.foregroundImageView);
                        duckXLevel5A.imageView.setOnMouseClicked(this::handleDuckClickedLevel5A);
                        duckXLevel5B.imageView.setOnMouseClicked(this::handleDuckClickedLevel5B);

                        levelFourCheck = 0;
                        primaryStage.setScene(fifthLevelScene);
                    }
                });
            }
        });

        fifthLevelScene.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            // Play the sound
            mediaPlayers.get(3).seek(mediaPlayers.get(3).getStartTime());
            mediaPlayers.get(3).play();
        });
        fifthLevelScene.setOnMouseClicked(event -> {
            lvlClickCount--;
            clickCountLabel.setText("Ammo Left: " + lvlClickCount);
            if(levelFiveCheck != 2){
                if(lvlClickCount == 0){
                    mediaPlayers.get(4).play();
                outOfBullet(primaryStage, optionPane, lvl5Pane, firstLevelScene,
                        fifthLevelScene,welcomeScene,levelFiveCheck,lvl1Pane);
                levelOneCheck = 0;
                levelTwoCheck = 0;
                levelThreeCheck = 0;
                levelFourCheck = 0;}

            }
            else{
                mediaPlayers.get(1).play();
                winText(lvl5Pane);
                fifthLevelScene.setOnKeyPressed(event1 -> {
                    if(event1.getCode() == KeyCode.ENTER){
                        mediaPlayers.get(1).stop();
                        lvlClickCount = 9;
                        clickCountLabel.setText("Ammo Left: " + lvlClickCount);
                        levelPanes(lvl6Pane,"Level 6");
                        try{
                            duckXLevel6A = new DuckX("blue");
                            duckXLevel6B = new DuckX("black");
                            duckXLevel6B.imageView.setTranslateY(-(33)*SCALE);
                            duckHorizontalLevel6 = new DuckHorizontal("red");
                        }
                        catch (FileNotFoundException e ){
                            throw new RuntimeException(e);
                        }
                        lvl6Pane.getChildren().addAll(duckXLevel6A.imageView,
                                duckXLevel6B.imageView, duckHorizontalLevel6.imageView);
                        lvl6Pane.getChildren().add(foreground.foregroundImageView);

                        duckXLevel6A.imageView.setOnMouseClicked(this:: handleDuckClickedLevel6XA);
                        duckXLevel6B.imageView.setOnMouseClicked(this::handleDuckClickedLevel6XB);
                        duckHorizontalLevel6.imageView.setOnMouseClicked(this :: handleDuckClickedLevel6);

                        levelFiveCheck =0 ;
                        primaryStage.setScene(sixthLevelScene);
                    }
                });
            }
        });

        sixthLevelScene.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            // Play the sound
            mediaPlayers.get(3).seek(mediaPlayers.get(3).getStartTime());
            mediaPlayers.get(3).play();
        });
        sixthLevelScene.setOnMouseClicked(event -> {
            lvlClickCount--;
            clickCountLabel.setText("Ammo Left: " + lvlClickCount);
            if (levelSixCheck != 3) {
                if(lvlClickCount == 0){
                    mediaPlayers.get(4).play();
                    outOfBullet(primaryStage, optionPane, lvl6Pane, firstLevelScene,
                            sixthLevelScene,welcomeScene,levelSixCheck,lvl1Pane);
                    levelOneCheck = 0;
                    levelTwoCheck = 0;
                    levelThreeCheck = 0;
                    levelFourCheck = 0;
                    levelFiveCheck = 0;}
            }
            else {
                mediaPlayers.get(5).play();
                gameCompleteText(lvl6Pane);
                primaryStage.setScene(sixthLevelScene);
                sixthLevelScene.setOnKeyPressed(event1 -> {
                    if(event1.getCode() == KeyCode.ENTER){
                        lvl6Pane.getChildren().clear();
                        lvlClickCount = 3;
                        clickCountLabel.setText("Ammo Left: " + lvlClickCount);
                        levelPanes(lvl1Pane,"Level 1");
                        try {
                            duckHorizontalLvl1 = new DuckHorizontal("red");
                            lvl1Pane.getChildren().add(duckHorizontalLvl1.imageView);
                            lvl1Pane.getChildren().add(foreground.foregroundImageView);
                            duckHorizontalLvl1.imageView.setOnMouseClicked(this::handleDuckClickedHorizontalLevel1);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        levelOneCheck = 0;
                        levelTwoCheck = 0;
                        levelThreeCheck = 0;
                        levelFourCheck = 0;
                        levelFiveCheck = 0;
                        levelSixCheck = 0;
                        primaryStage.setScene(firstLevelScene);
                    }
                    else if (event1.getCode() == KeyCode.ESCAPE){
                        primaryStage.setScene(welcomeScene);
                    }
                });
            }
        });


    }

    private void firstLevelMusicOrNot(Stage primaryStage, StackPane pane, Scene levelScene) {
        levelPanes(pane,"Level 1");
        try {
            duckHorizontalLvl1 = new DuckHorizontal("red");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        pane.getChildren().add(duckHorizontalLvl1.imageView);
        pane.getChildren().add(foreground.foregroundImageView);
        duckHorizontalLvl1.imageView.setOnMouseClicked(this::handleDuckClickedHorizontalLevel1);
        primaryStage.setScene(levelScene);
    }

    private void outOfBullet(Stage primaryStage, StackPane optionPane, StackPane pane,Scene firstlevelScene,
                             Scene LevelScene,Scene welcomeScene,int levelCheck,StackPane lvl1pane) {
            levelCheck = 0;
            mediaPlayers.get(4).play();
            gameOverText(pane);
            primaryStage.setScene(LevelScene);
            lvlClickCount = 3;
            LevelScene.setOnKeyPressed(event1 ->{
                if(event1.getCode() == KeyCode.ENTER){
                    pane.getChildren().clear();
                    clickCountLabel.setText("Ammo Left: " + lvlClickCount);
                    levelPanes(lvl1pane ,"Level 1");
                    try {
                        duckHorizontalLvl1 = new DuckHorizontal("red");
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    lvl1pane.getChildren().add(duckHorizontalLvl1.imageView);
                    lvl1pane.getChildren().add(foreground.foregroundImageView);
                    duckHorizontalLvl1.imageView.setOnMouseClicked(this::handleDuckClickedHorizontalLevel1);
                    primaryStage.setScene(firstlevelScene);
                }
                else if (event1.getCode().toString().equals("ESCAPE")) {
                    optionPane.getChildren().clear();
                    lvl1pane.getChildren().clear();
                    mediaPlayers.get(0).setCycleCount(MediaPlayer.INDEFINITE);
                    mediaPlayers.get(0).play();
                    primaryStage.setScene(welcomeScene);
                }
            });
    }
    private void gameOverText(StackPane pane){
        Text gameOverText = new Text("GAME OVER!");
        Text enterOrEscText = new Text("PRESS ENTER TO START \n    PRESS ESC TO EXIT");
        textArange(pane, gameOverText, enterOrEscText);

    }

    private void textArange(StackPane pane, Text text1, Text text2) {
        FadeTransition fadeTransition = createFadeTransition(text2);
        adjustFontColor(text1);
        adjustFontColor(text2);
        fadeTransition.play();
        VBox vBox = new VBox();
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(text1,text2);
        pane.getChildren().add(vBox);
    }

    private void gameCompleteText(StackPane pane){
        Text gameCompleteText = new Text("You have completed the game!");
        Text pressEnterPressESC = new Text("Press ENTER to play again \n     Press ESC to exit");
        textArange(pane, gameCompleteText, pressEnterPressESC);
    }

    private void winText(StackPane pane) {
        Text winText = new Text("YOU WIN !");
        Text enterText = new Text("Press ENTER to play next level");
        textArange(pane,winText,enterText);

    }

    public StackPane welcomePane() throws FileNotFoundException {
        ImageView imageViewWelcome = new ImageView();
        StackPane paneWelcome = new StackPane();

        FileInputStream fileInputStream = new FileInputStream(path + File.separator + "assets/welcome/1.png");
        Image image = new Image(fileInputStream);

        imageViewWelcome.setImage(image);
        imageViewWelcome.setFitHeight(image.getHeight() * SCALE);
        imageViewWelcome.setFitWidth(image.getWidth() * SCALE);

        Text text = new Text("PRESS ENTER TO START \n    PRESS ESC TO EXIT");
        text.setFont(Font.font("Arial",15*SCALE));
        text.setFill(Color.LIGHTGREEN);

        FadeTransition fadeTransition = createFadeTransition(text);
        fadeTransition.play();

        double y = 50*SCALE;
        text.setTranslateY(y);

        paneWelcome.getChildren().addAll(imageViewWelcome,text);

        return paneWelcome;

    }
    public void optionsPane(StackPane optionsPane)throws FileNotFoundException{
        backPane = new StackPane();
        background = new BackgroundPane(backPane);
        foreground = new ForegroundPane();
        cross = new Cross();
        Text nowText = Texts("\nUSE ARROW KEYS TO NAVIGATE \n       " +
                "PRESS ENTER TO START\n          " +
                "PRESS ESC TO EXIT");
        StackPane.setAlignment(nowText, Pos.TOP_CENTER);
        optionsPane.getChildren().addAll(backPane,nowText,foreground.foregroundImageView,cross.crosshairImageView);
    }
    public void levelPanes(StackPane lvl3Pane,String level){
        Text levelText = Texts(level+"/6");
        StackPane.setAlignment(levelText, Pos.TOP_CENTER);
        StackPane.setAlignment(clickCountLabel, Pos.TOP_RIGHT);
        lvl3Pane.getChildren().addAll(backPane,levelText,clickCountLabel);
    }




    public static double getScale() {
        return SCALE;
    }
    private void handleDuckClickedHorizontalLevel1(MouseEvent event){
        mediaPlayers.get(6).play();
        duckHorizontalLvl1.timelineMoving.stop();
        duckHorizontalLvl1.timelineTurn.stop();
        changeImageWithDelay(redBeforeDuckFallImage,redduckFallImage,Duration.seconds(0.7),duckHorizontalLvl1.imageView);
        duckHorizontalLvl1.fallDown(Duration.seconds(1));
        levelOneCheck++;
    }
    private void handleDuckClickedLevel2(MouseEvent event){
        mediaPlayers.get(6).play();
        duckLevel2.timelineMoving.stop();
        duckLevel2.timelineTurn.stop();
        changeImageWithDelay(blueBeforeDuckFallImage,blueduckFallImage,Duration.seconds(0.7),duckLevel2.imageView);
        duckLevel2.fallDown(Duration.seconds(1));
        levelTwoCheck++;
    }
    private void handleDuckClickedLevel3A(MouseEvent event){
        mediaPlayers.get(6).play();
        duckHorizontalLvl3A.timelineMoving.stop();
        duckHorizontalLvl3A.timelineTurn.stop();
        changeImageWithDelay(blueBeforeDuckFallImage,blueduckFallImage,Duration.seconds(0.7),duckHorizontalLvl3A.imageView);
        duckHorizontalLvl3A.fallDown(Duration.seconds(1));
        levelThreeCheck++;
    }
    private void handleDuckClickedLevel3B(MouseEvent event){
        mediaPlayers.get(6).play();
        duckHorizontalLvl3B.timelineMoving.stop();
        duckHorizontalLvl3B.timelineTurn.stop();
        changeImageWithDelay(blackBeforeDuckFallImage,blackduckFallImage,Duration.seconds(0.7),duckHorizontalLvl3B.imageView);
        duckHorizontalLvl3B.fallDown(Duration.seconds(1));
        levelThreeCheck++;
    }
    private void handleDuckClickedLevel4(MouseEvent event){
        mediaPlayers.get(6).play();
        duckHorizontalLvl4.timelineMoving.stop();
        duckHorizontalLvl4.timelineTurn.stop();
        changeImageWithDelay(redBeforeDuckFallImage,redduckFallImage,Duration.seconds(0.7),duckHorizontalLvl4.imageView);
        duckHorizontalLvl4.fallDown(Duration.seconds(1));
        levelFourCheck++;
    }
    private void handleDuckClickedLevel4X (MouseEvent event){
        mediaPlayers.get(6).play();
        duckXLevel4.timelineMoving.stop();
        duckXLevel4.timelineTurn.stop();
        changeImageWithDelay(redBeforeDuckFallImage,redduckFallImage,Duration.seconds(0.7),duckXLevel4.imageView);
        duckXLevel4.fallDown(Duration.seconds(1));
        levelFourCheck++;
    }
    private void handleDuckClickedLevel5A (MouseEvent event){
        mediaPlayers.get(6).play();
        duckXLevel5A.timelineMoving.stop();
        duckXLevel5A.timelineTurn.stop();
        changeImageWithDelay(blueBeforeDuckFallImage,blueduckFallImage,Duration.seconds(0.7),duckXLevel5A.imageView);
        duckXLevel5A.fallDown(Duration.seconds(1));
        levelFiveCheck++;
    }
    private void handleDuckClickedLevel5B (MouseEvent event){
        mediaPlayers.get(6).play();
        duckXLevel5B.timelineMoving.stop();
        duckXLevel5B.timelineTurn.stop();
        changeImageWithDelay(redBeforeDuckFallImage,redduckFallImage,Duration.seconds(0.7),duckXLevel5B.imageView);
        duckXLevel5B.fallDown(Duration.seconds(1));
        levelFiveCheck++;
    }
    private void handleDuckClickedLevel6XA (MouseEvent event){
        mediaPlayers.get(6).play();
        duckXLevel6A.timelineMoving.stop();
        duckXLevel6A.timelineTurn.stop();
        changeImageWithDelay(blueBeforeDuckFallImage,blueduckFallImage,Duration.seconds(0.7),duckXLevel6A.imageView);
        duckXLevel6A.fallDown(Duration.seconds(1));
        levelSixCheck++;
    }
    private void handleDuckClickedLevel6XB (MouseEvent event){
        mediaPlayers.get(6).play();
        duckXLevel6B.timelineMoving.stop();
        duckXLevel6B.timelineTurn.stop();
        changeImageWithDelay(blackBeforeDuckFallImage,blackduckFallImage,Duration.seconds(0.7),duckXLevel6B.imageView);
        duckXLevel6B.fallDown(Duration.seconds(1));
        levelSixCheck++;
    }
    private void handleDuckClickedLevel6 (MouseEvent event){
        mediaPlayers.get(6).play();
        duckHorizontalLevel6.timelineMoving.stop();
        duckHorizontalLevel6.timelineTurn.stop();
        changeImageWithDelay(redBeforeDuckFallImage,redduckFallImage,Duration.seconds(0.7),duckHorizontalLevel6.imageView);
        duckHorizontalLevel6.fallDown(Duration.seconds(1));
        levelSixCheck++;
    }


    private Text Texts (String sentence){
        Text text = new Text(sentence);
        text.setFont(Font.font("Arial", 10*SCALE)); // Set the font and size of the text
        text.setFill(Color.LIGHTGREEN);
        return text;
    }
    private FadeTransition createFadeTransition(Text text) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2),text);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(FadeTransition.INDEFINITE);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();

        return fadeTransition;
    }
    private void adjustFontColor (Text text){
        text.setFont(Font.font("Arial",15*SCALE));
        text.setFill(Color.LIGHTGREEN);
    }
    private void addSound(String soundFilePath) {
        // Create a media object with the sound file
        Media sound = new Media(new File(soundFilePath).toURI().toString());

        // Create a media player with the media object
        MediaPlayer mediaPlayer = new MediaPlayer(sound);

        // Add the media player to the list
        mediaPlayers.add(mediaPlayer);
    }

    private void changeImageWithDelay(Image newImage, Image originalImage, Duration duration,ImageView imageView) {
        // Change the image to the new image
        imageView.setImage(newImage);

        // Create a PauseTransition to delay the image change back to the original image
        PauseTransition pauseTransition = new PauseTransition(duration);
        pauseTransition.setOnFinished(e -> {
            // Change the image back to the original image
            imageView.setImage(originalImage);
        });

        // Start the PauseTransition
        pauseTransition.play();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
