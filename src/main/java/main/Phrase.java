package main;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.swing.JPanel;

public class Phrase extends JPanel{

    private static final int LINE_GAP = 15;
    private static final int NUM_LINES = 5;
    private static final int MARGIN = 20;
    private static final int WIDTH = 720;
    private static final int STAFF_HEIGHT = (NUM_LINES - 1) * LINE_GAP;
    private static final int HEIGHT = (2 * STAFF_HEIGHT) + MARGIN;
    private static final int MEASURE_WIDTH = WIDTH / 4;

    private PitchCalculator pitchCalculator;
    private List<MusicSymbol> symbols = new ArrayList<>();
    private int volume = 80;

    public Phrase(PitchCalculator pitchCalculator) {
        this.pitchCalculator = pitchCalculator;
    }

    
    public void addSymbol(MusicSymbol symbol, int x, int y) {
        symbol.setPosition(x, y);
        int pitch = pitchCalculator.calculatePitch(y);
        symbol.setMidiPitch(pitch);
        symbols.add(symbol);
        this.repaint();
        System.out.println("Added symbol at y=" + y + " with MIDI pitch: " + symbol.getMidiPitch());
    }
    
    public List<MusicSymbol> getSymbols() {
        return symbols;
    }

    public void removeSymbol(MusicSymbol symbolToRemove) {
        symbols.remove(symbolToRemove);
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPhrase(g);
        for (MusicSymbol symbol : symbols) {
            symbol.drawSymbol(g);
        }
    }

    private void drawPhrase(Graphics g) {
        drawStaff(g, MARGIN, MARGIN, WIDTH);
        drawStaff(g, MARGIN, HEIGHT, WIDTH);
        drawMeasure(g, MARGIN, MARGIN, STAFF_HEIGHT);
        drawMeasure(g, MARGIN, HEIGHT, STAFF_HEIGHT);
        drawEndLines(g, MARGIN, MARGIN, WIDTH, HEIGHT);
    }

    private void drawStaff(Graphics g, int x, int y, int width) {
        int lineY = y;
        for (int i = 0; i < NUM_LINES; i++) {
            g.drawLine(x, lineY, x + width, lineY);
            lineY += LINE_GAP;
        }
        drawStart(g, x, y);
    }

    private void drawStart(Graphics g, int x, int y) {
        drawTimeSignature(g, x, y);
        drawClefs(g,x,y);
    }

    private void drawClefs(Graphics g, int x, int y) {
        Image trebleClefImage = Toolkit.getDefaultToolkit().getImage("src\\main\\java\\main\\treble_clef.png");
        Image bassClefImage = Toolkit.getDefaultToolkit().getImage("src\\main\\java\\main\\bass_clef.png");
        g.drawImage(trebleClefImage, 25, 15, this);
        g.drawImage(bassClefImage, 25, 148, this);
    }

    private void drawTimeSignature(Graphics g, int x, int y) {
        Font originalFont = g.getFont();
        Font largerFont = originalFont.deriveFont(Font.BOLD, 30f);
        g.setFont(largerFont);
        g.drawString("4", x + 45, y + 27); 
        g.drawString("4", x + 45, y + 55);
    }

    private void drawMeasure(Graphics g, int x, int y, int height) {
        int lineX = x;
        for (int i = 0; i < 5; i++) {
            g.drawLine(lineX, y, lineX, y + height);
            lineX += MEASURE_WIDTH;
        }
    }

    private void drawEndLines(Graphics g, int x, int y, int width, int height) {
        g.drawLine(x, y, x, y + height);
        g.drawLine(x + width, y, x + width, y + height);
    }


    public void playSymbols(int dynamic) {
        volume = dynamic;
        try {
            Synthesizer synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            final MidiChannel[] channels = synthesizer.getChannels();
            symbols.sort((s1, s2) -> Integer.compare(s1.getPosition().x, s2.getPosition().x)); 
            Thread leftHandThread = new Thread(() -> playLeftNotes(symbols, channels));
            Thread rightHandThread = new Thread(() -> playRightNotes(symbols, channels));
            leftHandThread.start();
            rightHandThread.start();
            leftHandThread.join();
            rightHandThread.join();
            synthesizer.close();
        } catch (MidiUnavailableException e) {
            System.err.println("MIDI device not available");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Playback threads interrupted");
        }
    }

    private void playLeftNotes(List<MusicSymbol> symbols, MidiChannel[] channels) {
        for (MusicSymbol symbol : symbols) {
            if (symbol.getPosition().y > 105 && symbol.getPosition().y < 214){
                int pitch = symbol.getMidiPitch();
                double duration = symbol.getDuration();
                
                System.out.println("Playing " + symbol.getClass().getSimpleName() + 
                    ": Pitch " + pitch + ", Duration " + duration + " beats");

                System.out.println("x = " + symbol.getPosition().x);
                if (pitch != 0) {
                    channels[0].noteOn(pitch, volume);
                    System.out.println("Volume is at " + volume);
                }
                try {
                    Thread.sleep((long) (duration * 500));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Playback thread interrupted");
                }
                channels[0].noteOff(pitch);
            } else {
                continue;
            }
        }     
    }

    private void playRightNotes(List<MusicSymbol> symbols, MidiChannel[] channels) {
        for (MusicSymbol symbol : symbols) {
            if (symbol.getPosition().y > 0 && symbol.getPosition().y < 95){
                int pitch = symbol.getMidiPitch();
                double duration = symbol.getDuration();
                
                System.out.println("Playing " + symbol.getClass().getSimpleName() + 
                                  ": Pitch " + pitch + ", Duration " + duration + " beats");
                System.out.println("x = " + symbol.getPosition().x);

                if (pitch != 0) {
                    channels[0].noteOn(pitch, volume);
                    System.out.println("Volume is at " + volume);
                }
                try {
                    Thread.sleep((long) (duration * 500));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Playback thread interrupted");
                }
                channels[0].noteOff(pitch);
            } else {
                continue;
            }
        }
            
    }
}