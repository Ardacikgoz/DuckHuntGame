import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class DuckX {
    protected ImageView imageView;
    private List<Image> duckImages;
    private int currentDuckImageIndex;
    double imageWidth, imageHeight;
    private double xDirection = 1; // 1 for moving right, -1 for moving left
    private double yDirection = 1;
    protected Timeline timelineMoving;
    protected Timeline timelineTurn;
    private final double birdSpeedX = (1) * DuckHunt.getScale();
    private final double birdSpeedY = (1) * DuckHunt.getScale();
    private String path = DuckHunt.class.getProtectionDomain().getCodeSource().getLocation().getPath();

    public DuckX(String color) throws FileNotFoundException {
        this.duckImages = new ArrayList<>();
        this.currentDuckImageIndex = 0;

        FileInputStream fileInputStream = new FileInputStream(path+ File.separator + "assets/duck_"+color+"/1.png");
        FileInputStream fileInputStream1 = new FileInputStream(path+ File.separator + "assets/duck_"+color+"/2.png");
        FileInputStream fileInputStream2 = new FileInputStream(path+ File.separator + "assets/duck_"+color+"/3.png");

        duckImages.add(new Image(fileInputStream));
        duckImages.add(new Image(fileInputStream1));
        duckImages.add(new Image(fileInputStream2));
        imageWidth = duckImages.get(0).getWidth();
        imageHeight = duckImages.get(0).getHeight();

        imageView = new ImageView(duckImages.get(currentDuckImageIndex));
        imageView.setFitWidth(DuckHunt.getScale() * imageWidth);
        imageView.setFitHeight(DuckHunt.getScale() * imageHeight);
        imageView.setTranslateY((-100)*DuckHunt.getScale());
        imageView.setTranslateX((-125)*DuckHunt.getScale());

        timelineMoving = new Timeline(new KeyFrame(Duration.millis(16), event -> moveImage()));
        timelineMoving.setCycleCount(Timeline.INDEFINITE);
        timelineMoving.play();

        timelineTurn = new Timeline(
                new KeyFrame(Duration.millis(500), event -> changeImage())
        );
        timelineTurn.setCycleCount(Timeline.INDEFINITE);
        timelineTurn.play();

    }
    void fallDown(Duration delayDuration){
        PauseTransition pauseTransition = new PauseTransition(delayDuration);
        pauseTransition.setOnFinished(event -> {
            TranslateTransition fallTransition = new TranslateTransition(Duration.seconds(2), imageView);
            fallTransition.setToY(500); // Fall down to Y-coordinate 500
            fallTransition.play();
        });
        pauseTransition.play();
    }
    private void flipImageHorizontally(boolean flip) {
        // Flip the image horizontally using Scale transformation
        Scale flipScale = new Scale(flip ? -1 : 1, 1);
        flipScale.setPivotX(imageView.getBoundsInParent().getWidth() / 2);
        imageView.getTransforms().setAll(flipScale);
    }
    private void moveImage() {
        // Get the current position and bounds of the image
        double currentX = imageView.getTranslateX();
        double currentY = imageView.getTranslateY();
        double imageWidth = imageView.getBoundsInLocal().getWidth();
        double imageHeight = imageView.getBoundsInLocal().getHeight();

        // Check if the image hits the window boundaries
        double maxX = (250)*DuckHunt.getScale() - imageWidth;
        double maxY = (240)*DuckHunt.getScale() - imageHeight;

        if (currentX >= maxX /2) {
            // Hit the right boundary, change direction to move left
            xDirection = -1;
            flipImageHorizontally(true);
        } else if (currentX <= (-125)*DuckHunt.getScale()) {
            // Hit the left boundary, change direction to move right
            xDirection = 1;
            flipImageHorizontally(false);
        }

        if (currentY >= maxY/2) {
            // Hit the bottom boundary, change direction to move up
            yDirection = -1;
        } else if (currentY <= (-120)*DuckHunt.getScale()) {
            // Hit the top boundary, change direction to move down
            yDirection = 1;
        }

        // Update the image position based on the directions
        double newX = currentX + (birdSpeedX * xDirection);
        double newY = currentY + (birdSpeedY * yDirection);
        imageView.setTranslateX(newX);
        imageView.setTranslateY(newY);
    }

    private void changeImage() {
        // Increment the current image index
        currentDuckImageIndex++;

        // Wrap around to the first image if the index goes beyond the array size
        if (currentDuckImageIndex >= duckImages.size()) {
            currentDuckImageIndex = 0;
        }

        // Update the ImageView with the new image
        imageView.setImage(duckImages.get(currentDuckImageIndex));
    }
}
