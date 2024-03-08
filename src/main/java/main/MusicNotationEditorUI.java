package main;
import java.awt.BorderLayout;
import javax.swing.*; 
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

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
                if (selectedSymbol != null) {
                    // Determine which StaffPanel is clicked
                    Component comp = staffPanel.getComponentAt(e.getX(), e.getY());
                    if (comp instanceof Phrase) {
                        Phrase staff = (Phrase) comp;
                        int x = e.getX() - comp.getX(); // Adjust X coordinate relative to staff
                        int y = e.getY() - comp.getY(); // Adjust Y coordinate relative to staff
                        staff.addSymbol(selectedSymbol.clone(), x, y); // Clone again to keep original
                        staff.repaint();
                        selectedSymbol = null; // Reset selected symbol
                    }
                }
            }
        });
    }

    private void initSymbolPanel() {
        symbolPanel = new JPanel();
        symbolPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        symbolPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        symbolPanel.setPreferredSize(new Dimension(2000, 100)); // You can adjust the width and height as needed

        addToSymbolPanel();
        addSymbolPanelListener();


        

    }

    private void addToSymbolPanel() {
        symbolPanel.add(new TrebleClefSymbol());
        symbolPanel.add(new WholeNoteSymbol());
        symbolPanel.add(new HalfNoteSymbol());
        symbolPanel.add(new QuarterNoteSymbol());
        symbolPanel.add(new EighthNoteSymbol());
    }

    // TO DO: refactor to be of less lines
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
        private static final int MEASURE_WIDTH = WIDTH / 5;

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
        }

        private void drawMeasure(Graphics g, int x, int y, int height) {
            int lineX = x;
            for (int i = 0; i < 6; i++) {
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
