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

public class DuckHorizontal {
    protected ImageView imageView;
    protected List<Image> duckImages;
    private int currentDuckImageIndex;
    double imageWidth, imageHeight;
    private double xDirection = 1; // 1 for moving right, -1 for moving left
    protected Timeline timelineMoving;
    protected Timeline timelineTurn;
    private final double birdSpeedX = (1.5) * DuckHunt.getScale();
    private String path = DuckHunt.class.getProtectionDomain().getCodeSource().getLocation().getPath();

    public DuckHorizontal(String color) throws FileNotFoundException {
        this.duckImages = new ArrayList<>();
        this.currentDuckImageIndex = 0;

        FileInputStream fileInputStream = new FileInputStream(path+ File.separator + "assets/duck_"+color+"/4.png");
        FileInputStream fileInputStream1 = new FileInputStream(path+ File.separator + "assets/duck_"+color+"/5.png");
        FileInputStream fileInputStream2 = new FileInputStream(path+ File.separator + "assets/duck_"+color+"/6.png");

        duckImages.add(new Image(fileInputStream));
        duckImages.add(new Image(fileInputStream1));
        duckImages.add(new Image(fileInputStream2));
        imageWidth = duckImages.get(0).getWidth();
        imageHeight = duckImages.get(0).getHeight();

        imageView = new ImageView(duckImages.get(currentDuckImageIndex));
        imageView.setFitWidth(DuckHunt.getScale() * imageWidth);
        imageView.setFitHeight(DuckHunt.getScale() * imageHeight);
        imageView.setTranslateY(-65*DuckHunt.getScale());

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
    private void moveImage() throws NullPointerException{
        // Get the current position and bounds of the image
        double currentX = imageView.getTranslateX();
        double currentY = imageView.getTranslateY();

        // Check if the image hits the window boundaries
        if (currentX >= imageView.getScene().getWidth()/2) {
            // Hit the right boundary, change direction to move left
            xDirection = -1;
            flipImageHorizontally(true);
        } else if (currentX <= (-125)*DuckHunt.getScale()) {
            // Hit the left boundary, change direction to move right
            xDirection = 1;
            flipImageHorizontally(false);
        }

        // Update the image position based on the direction
        imageView.setTranslateX(currentX + (birdSpeedX * xDirection));
        imageView.setTranslateY(currentY);
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
