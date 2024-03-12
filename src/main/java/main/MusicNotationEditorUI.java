package main;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MusicNotationEditorUI extends JFrame {
    private JPanel staffPanel;
    private JPanel controlPanel;
    private JPanel symbolPanel;

    private JButton playPauseButton;
    private JButton stopButton;

    private MusicSymbol selectedSymbol;

    public MusicNotationEditorUI() {
        super("Simple Music Notation Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 850));
        initComponents();
        setUpActionListeners();
    }

    private void initComponents() {
        initStaffPanel();
        initSymbolPanel();
        initControlPanel();
        displayComponents();
    }

    private void initStaffPanel() {
        staffPanel = new JPanel(new GridLayout(0, 1));
        staffPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        for (int i = 0; i < 3; i++) { // Example: 2 pairs of staffs
            staffPanel.add(new Phrase());
        }
        addStaffPanelListener();
    }

    // TO DO: refactor to be of less lines
    private void addStaffPanelListener() {
        staffPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    Component comp = staffPanel.getComponentAt(e.getX(), e.getY());
                    if (comp instanceof Phrase) {
                        Phrase phrase = (Phrase) comp;
                        for (MusicSymbol symbol : phrase.getSymbols()) {
                            if (Math.abs(e.getX() - symbol.getPosition().x) <= 21 &&
                                Math.abs(e.getY() - symbol.getPosition().y) <= 9) {
                                phrase.removeSymbol(symbol);
                                break;
                            }
                        }
                    }
                } else if (selectedSymbol != null && SwingUtilities.isLeftMouseButton(e)) {
                    Component comp = staffPanel.getComponentAt(e.getX(), e.getY());
                    if (comp instanceof Phrase) {
                        Phrase staff = (Phrase) comp;
                        int x = e.getX() - comp.getX(); // Adjust X coordinate relative to staff
                        int y = e.getY() - comp.getY(); // Adjust Y coordinate relative to staff
                        staff.addSymbol(selectedSymbol.clone(), x, y); // Clone again to keep original
                        selectedSymbol = null; // Reset selected symbol
                    }
                }
            }
        });
    }

    private void initSymbolPanel() {
        symbolPanel = new JPanel();
        symbolPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 8));
        symbolPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        addToSymbolPanel();
        addSymbolPanelListener();
    }

    private void addToSymbolPanel() {
        symbolPanel.add(new WholeNoteSymbol(10,10));
        symbolPanel.add(new HalfNoteSymbol(10,15));
        symbolPanel.add(new QuarterNoteSymbol(10,15));
        symbolPanel.add(new EighthNoteSymbol(10,15));
    }

    // TO DO: refactor to be of 5 lines
    private void addSymbolPanelListener() {
        symbolPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                Component comp = symbolPanel.getComponentAt(e.getX(), e.getY());
                if (comp instanceof MusicSymbol) {
                    MusicSymbol symbol = (MusicSymbol) comp;
                    selectedSymbol = symbol.clone();
                }
            }
        });
    }

    private void initControlPanel() {
        initButtons();
        controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.add(playPauseButton);
        controlPanel.add(stopButton);
    }

    private void initButtons() {
        playPauseButton = new JButton("Play/Pause");
        stopButton = new JButton("Stop");
    }

    private void displayComponents() {
        setLayout(new BorderLayout());
        add(symbolPanel, BorderLayout.NORTH);
        add(staffPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        displayUI();
    }
    private void displayUI() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setUpActionListeners() {
        playPauseButton.addActionListener(new ActionListener() {
            private boolean isPlaying = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                // Toggle play/pause state
                isPlaying = !isPlaying;
                if (isPlaying) {
                    // Start playing
                    // Implement playback functionality here
                    playPauseButton.setText("Pause");
                } else {
                    // Pause playback
                    // Implement pause functionality here
                    playPauseButton.setText("Play");
                }
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Stop playback
                // Implement stop functionality here
            }
        });
    }

    private class Phrase extends JPanel {
        private static final int LINE_GAP = 15;
        private static final int NUM_LINES = 5;
        private static final int MARGIN = 20;
        private static final int WIDTH = 720;
        private static final int STAFF_HEIGHT = (NUM_LINES - 1) * LINE_GAP;
        private static final int HEIGHT = (2 * STAFF_HEIGHT) + MARGIN;
        private static final int MEASURE_WIDTH = WIDTH / 4;

        private List<MusicSymbol> symbols = new ArrayList<>();

        
        public void addSymbol(MusicSymbol symbol, int x, int y) {
            
            symbol.setPosition(x, y); // Set symbol position
            symbols.add(symbol);
            // Check the coordinates on the staff to define what letter it is
            // Save that information into an array list that saves both the letter and type of note like (G2, Quarter)
            // Check that the amount of beats has not exceeded measureBeats of 4
            // Add the beats like if it is a quarter note, add measureBeats += 1
            // Draw the note
            this.repaint();
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
            drawStart(g, x, y);
            int lineY = y;
            for (int i = 0; i < NUM_LINES; i++) {
                g.drawLine(x, lineY, x + width, lineY);
                lineY += LINE_GAP;
            }
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
    }
}
