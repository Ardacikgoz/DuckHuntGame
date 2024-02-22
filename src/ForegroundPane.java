import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

class ForegroundPane{
    private List<Image> foregroundImages;
    protected ImageView foregroundImageView;
    private int currentforegroundIndex;
    private String path = DuckHunt.class.getProtectionDomain().getCodeSource().getLocation().getPath();

    public ForegroundPane() throws FileNotFoundException {
        this.foregroundImages = new ArrayList<>();
        this.currentforegroundIndex = 0;

        // Add the background images to the list
        FileInputStream fileInputStream1 = new FileInputStream(path + File.separator + "assets/foreground/1.png");
        FileInputStream fileInputStream2 = new FileInputStream(path + File.separator + "assets/foreground/2.png");
        FileInputStream fileInputStream3 = new FileInputStream(path + File.separator + "assets/foreground/3.png");
        FileInputStream fileInputStream4 = new FileInputStream(path + File.separator + "assets/foreground/4.png");
        FileInputStream fileInputStream5 = new FileInputStream(path + File.separator + "assets/foreground/5.png");
        FileInputStream fileInputStream6 = new FileInputStream(path + File.separator + "assets/foreground/6.png");
        foregroundImages.add(new Image(fileInputStream1));
        foregroundImages.add(new Image(fileInputStream2));
        foregroundImages.add(new Image(fileInputStream3));
        foregroundImages.add(new Image(fileInputStream4));
        foregroundImages.add(new Image(fileInputStream5));
        foregroundImages.add(new Image(fileInputStream6));


        // Create the ImageView with the initial background image
        foregroundImageView = new ImageView(foregroundImages.get(currentforegroundIndex));
        foregroundImageView.setFitWidth(foregroundImages.get(0).getWidth() * DuckHunt.getScale());
        foregroundImageView.setFitHeight(foregroundImages.get(0).getHeight() * DuckHunt.getScale());

        // Add the background ImageView to the Pane

    }

    public void changeToNextBackground() {
        currentforegroundIndex++;
        if (currentforegroundIndex >= foregroundImages.size()) {
            currentforegroundIndex = 0;
        }
        foregroundImageView.setImage(foregroundImages.get(currentforegroundIndex));
    }

    public void changeToPreviousBackground() {
        currentforegroundIndex--;
        if (currentforegroundIndex < 0) {
            currentforegroundIndex = foregroundImages.size() - 1;
        }
        foregroundImageView.setImage(foregroundImages.get(currentforegroundIndex));
    }
}