import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Cross {
    protected List<Image> crosshairImages;
    protected ImageView crosshairImageView;

    private int currentcrosshairIndex;
    private String path = DuckHunt.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    public Cross() throws FileNotFoundException {
        crosshairImages = new ArrayList<>();

        FileInputStream fileInputStream1 = new FileInputStream(path+ File.separator + "assets/crosshair/1.png");
        FileInputStream fileInputStream2 = new FileInputStream(path+ File.separator + "assets/crosshair/2.png");
        FileInputStream fileInputStream3 = new FileInputStream(path+ File.separator + "assets/crosshair/3.png");
        FileInputStream fileInputStream4 = new FileInputStream(path+ File.separator + "assets/crosshair/4.png");
        FileInputStream fileInputStream5 = new FileInputStream(path+ File.separator + "assets/crosshair/5.png");
        FileInputStream fileInputStream6 = new FileInputStream(path+ File.separator + "assets/crosshair/6.png");
        FileInputStream fileInputStream7 = new FileInputStream(path+ File.separator + "assets/crosshair/7.png");

        crosshairImages.add(new Image(fileInputStream1));
        crosshairImages.add(new Image(fileInputStream2));
        crosshairImages.add(new Image(fileInputStream3));
        crosshairImages.add(new Image(fileInputStream4));
        crosshairImages.add(new Image(fileInputStream5));
        crosshairImages.add(new Image(fileInputStream6));
        crosshairImages.add(new Image(fileInputStream7));

        crosshairImageView = new ImageView(crosshairImages.get(currentcrosshairIndex));
        crosshairImageView.setFitWidth(crosshairImages.get(0).getWidth() * DuckHunt.getScale());
        crosshairImageView.setFitHeight(crosshairImages.get(0).getHeight() * DuckHunt.getScale());
    }
    public void changeToNextBackground() {
        currentcrosshairIndex++;
        if (currentcrosshairIndex >= crosshairImages.size()) {
            currentcrosshairIndex = 0;
        }
        crosshairImageView.setImage(crosshairImages.get(currentcrosshairIndex));
    }
    public void changeToPreviousBackground() {
        currentcrosshairIndex--;
        if (currentcrosshairIndex < 0) {
            currentcrosshairIndex = crosshairImages.size() - 1;
        }
        crosshairImageView.setImage(crosshairImages.get(currentcrosshairIndex));
    }
    private void handleMouseMoved(MouseEvent event) {
        crosshairImageView.setX(event.getX() - crosshairImageView.getFitWidth() / 2);
        crosshairImageView.setY(event.getY() - crosshairImageView.getFitHeight() / 2);
    }
}