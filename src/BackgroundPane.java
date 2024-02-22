import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

class BackgroundPane extends ImageView{
    private List<Image> backgroundImages;
    private ImageView backgroundImageView;
    private int currentBackgroundIndex;
    private String path = DuckHunt.class.getProtectionDomain().getCodeSource().getLocation().getPath();

    public BackgroundPane(Pane pane) throws FileNotFoundException {
        this.backgroundImages = new ArrayList<>();
        this.currentBackgroundIndex = 0;

        // Add the background images to the list
        FileInputStream fileInputStream1 = new FileInputStream(path + File.separator + "assets/background/1.png");
        FileInputStream fileInputStream2 = new FileInputStream(path + File.separator + "assets/background/2.png");
        FileInputStream fileInputStream3 = new FileInputStream(path + File.separator + "assets/background/3.png");
        FileInputStream fileInputStream4 = new FileInputStream(path + File.separator + "assets/background/4.png");
        FileInputStream fileInputStream5 = new FileInputStream(path + File.separator + "assets/background/5.png");
        FileInputStream fileInputStream6 = new FileInputStream(path + File.separator + "assets/background/6.png");
        backgroundImages.add(new Image(fileInputStream1));
        backgroundImages.add(new Image(fileInputStream2));
        backgroundImages.add(new Image(fileInputStream3));
        backgroundImages.add(new Image(fileInputStream4));
        backgroundImages.add(new Image(fileInputStream5));
        backgroundImages.add(new Image(fileInputStream6));


        // Create the ImageView with the initial background image
        backgroundImageView = new ImageView(backgroundImages.get(currentBackgroundIndex));
        backgroundImageView.setFitWidth(backgroundImages.get(0).getWidth() * DuckHunt.getScale());
        backgroundImageView.setFitHeight(backgroundImages.get(0).getHeight() * DuckHunt.getScale());

        // Add the background ImageView to the Pane
        pane.getChildren().add(backgroundImageView);
    }

    public void changeToNextBackground() {
        currentBackgroundIndex++;
        if (currentBackgroundIndex >= backgroundImages.size()) {
            currentBackgroundIndex = 0;
        }
        backgroundImageView.setImage(backgroundImages.get(currentBackgroundIndex));
    }

    public void changeToPreviousBackground() {
        currentBackgroundIndex--;
        if (currentBackgroundIndex < 0) {
            currentBackgroundIndex = backgroundImages.size() - 1;
        }
        backgroundImageView.setImage(backgroundImages.get(currentBackgroundIndex));
    }
}