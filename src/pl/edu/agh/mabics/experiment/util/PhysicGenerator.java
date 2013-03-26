package pl.edu.agh.mabics.experiment.util;

import pl.edu.agh.mabics.platform.model.Vector;
import pl.edu.agh.mabics.ui.datamodel.util.Coordinates;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 26.03.13
 * Time: 12:19
 */
public class PhysicGenerator {

    public static final int MAX_ACCELERATION = 1;
    public static final int MAX_SPEED = 2;

    public static void generatePhysic(String physicFileName, String boardFilePath) {
        List<Coordinates> allPoints = new ArrayList<Coordinates>();
        try {
            allPoints = getAllExistingPoints(boardFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Coordinates point : allPoints) {
            generatePossibleMoves(point); //move to specialized class
        }

    }

    private static void generatePossibleMoves(Coordinates point) {
        for (int speed = 0; speed <= MAX_SPEED; speed++) {
            System.out.print("Current state:((" + point.getX() + "," + point.getY() + ")" + "," + speed + ") ");
            System.out.print("Possible moves: ");
            for (PossibleMove move : generatePossibleMoves(point, speed)) {
                System.out.print(move.toString() + " ");
            }
            System.out.println();

        }

    }

    //generates all possible movements for point with speed
    private static List<PossibleMove> generatePossibleMoves(Coordinates point, int speed) {
        List<PossibleMove> possibleMoves = new ArrayList<PossibleMove>();
        //moves left
        for (int acc = -MAX_ACCELERATION; acc <= MAX_ACCELERATION; acc++)
            if (speed + acc <= MAX_SPEED && speed + acc >= 0)
                possibleMoves.add(new PossibleMove(point.getX() + speed, point.getY(), speed + acc));
        //moves right
        for (int acc = -MAX_ACCELERATION; acc <= MAX_ACCELERATION; acc++)
            if (speed + acc <= MAX_SPEED && speed + acc >= 0)
                possibleMoves.add(new PossibleMove(point.getX() - speed, point.getY(), speed + acc));
        //moves up
        for (int acc = -MAX_ACCELERATION; acc <= MAX_ACCELERATION; acc++)
            if (speed + acc <= MAX_SPEED && speed + acc >= 0)
                possibleMoves.add(new PossibleMove(point.getX(), point.getY() - speed, speed + acc));
        //moves down
        for (int acc = -MAX_ACCELERATION; acc <= MAX_ACCELERATION; acc++)
            if (speed + acc <= MAX_SPEED && speed + acc >= 0)
                possibleMoves.add(new PossibleMove(point.getX(), point.getY() + speed, speed + acc));
        return possibleMoves;

    }

    private static List<Coordinates> getAllExistingPoints(String boardFilePath) throws IOException {
        List<Coordinates> allPoints = new ArrayList<Coordinates>();
        URL url = new File(boardFilePath).toURI().toURL();
        BufferedImage img = ImageIO.read(url);
        int[] pixels = img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth());
        for (int h = 0; h < img.getHeight(); h++) {
            for (int w = 0; w < img.getWidth(); w++) {
                int index = h * img.getHeight() + w;
                if (pixels[index] == -1) {
                    allPoints.add(new Coordinates(w, h)); //check if it shouldn't be img.height - h
                }
            }
        }
        return allPoints;
    }

    public static void main(String[] args) {
        generatePhysic("", "C:\\Users\\Mateusz\\Desktop\\nauka\\Studia\\IX semestr\\magisterka\\myzael-Sterowanie-pojazdami-w-przestrzeni-dyskretnej-e36555b\\src\\board\\small.bmp");
    }

    private static class PossibleMove {
        int x;
        int y;
        int speed;
        Vector velocity = new Vector(0, 0);

        public PossibleMove(Integer x, int y, int speed) {
            this.x = x;
            this.y = y;
            this.speed = speed;
        }

        @Override
        public String toString() {
            return "((" + x + "," + y + ")" + "," + "(" + velocity.getX() + "," + velocity.getY() + ")" + "," + speed + ")";
        }
    }
}
