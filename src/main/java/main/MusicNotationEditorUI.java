package main;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MusicNotationEditorUI extends JFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Music Notation Editor");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600); // Set your preferred size

            Score score = new Score();
            Phrase phrase = new Phrase();
            phrase.newMeasure();
            phrase.newMeasure();

            frame.add(score); // Add score to the frame
            frame.setVisible(true); // Make the frame visible
        });
    }
}

/*
 * Class: To keep track of all the Phrase
 * objects on the page and update where the
 * next Phrase will be created
 */
class Score extends JPanel {
    protected int xCoor = 50;
    protected int yCoor = 50;
    protected int yMarker = 50;

    void update() {
        yMarker += 120;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}


/*
 * Class: Phrase keeps all the Measure objects
 * and makes sure there isn't too many in one
 * row as well as keeping the xCoor updated for
 * future new measures
 */
class Phrase extends Score {
    final int totalMeasures = 6;
    int currMeasures;
    List<Measure> measures;
    int xCoor;
    int yCoor;

    Phrase() {
        currMeasures = 0;
        xCoor = super.xCoor;
        yCoor = super.yMarker;
        measures = new ArrayList<>();
        super.update();
    }

    void newMeasure() {
        if (currMeasures != totalMeasures) {
            measures.add(new Measure());
            currMeasures++;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}

/*
 * Class: A measure of four four time that can be
 * added to a phrase in the row depending on
 * availability of space in the phrase and the coordinates
 * You can add notes onto the measure.
 */
class Measure extends Phrase {
    final int totalDuration = 4;
    int currDuration;
    //List<Note> notes;
    int xCoor;
    int yCoor;
    final int MEASURE_WIDTH = 50;
    final int MEASURE_HEIGHT = 50;


    Measure() {
        currDuration = 0;
        xCoor = super.xCoor;
        yCoor = super.yCoor;
        //paintComponent(g);
        //notes = new ArrayList<>();
        super.xCoor += MEASURE_WIDTH;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(xCoor, yCoor, xCoor + 20, yCoor + 20); // Even this is not working
    }

    private void drawStaff(Graphics g, int x, int y, int width) {
        int lineY = y;
        for (int i = 0; i < 5; i++) {
            g.drawLine(x, lineY, x + width, lineY);
            lineY += 10;
        }
    }

    @Override
    protected void drawMeasure(Graphics g) {
        g.drawLine(xCoor, yCoor, xCoor, yCoor + MEASURE_HEIGHT);
        g.drawLine(xCoor, yCoor + (MEASURE_HEIGHT * 2), xCoor, yCoor + (MEASURE_HEIGHT * 3));
        drawStaff(g, xCoor, yCoor, MEASURE_WIDTH);
        drawStaff(g, xCoor, yCoor + MEASURE_HEIGHT, MEASURE_WIDTH);

        /*for (Note note : notes) {
            // Draw the Notes at their xOffset and yOffset
        }*/
    }
}


