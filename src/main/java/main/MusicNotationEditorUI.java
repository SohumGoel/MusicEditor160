package main;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.Color;
import java.awt.Point;
import java.util.*;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MusicNotationEditorUI extends JFrame {
    // Staff lines
    private JPanel staffPanel;

    // Control buttons
    private JButton playPauseButton;
    private JButton stopButton;

    // Music symbols
    private JPanel symbolPanel;
    private MusicSymbol selectedSymbol;


    public MusicNotationEditorUI() {
        super("Simple Music Notation Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 800));

        // Initialize components
        initComponents();

        // Arrange components using layout manager
        arrangeComponents();

        // Set up action listeners
        setUpActionListeners();

        // Display the UI
        pack();
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private void initComponents() {
        // Create and add staff panels
        staffPanel = new JPanel(new GridLayout(0, 1));
        staffPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        for (int i = 0; i < 2; i++) { // Example: 2 pairs of staffs
            staffPanel.add(new StaffPanel());
        }

        // Initialize control buttons
        playPauseButton = new JButton("Play/Pause");
        stopButton = new JButton("Stop");

        // Initialize symbol panel
        symbolPanel = new JPanel();
        symbolPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        symbolPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add musical symbols to symbol panel
        symbolPanel.add(new TrebleClefSymbol());
        symbolPanel.add(new WholeNoteSymbol());
        symbolPanel.add(new HalfNoteSymbol());
        symbolPanel.add(new QuarterNoteSymbol());
        symbolPanel.add(new EighthNoteSymbol());

        // Add mouse listener to symbol panel
        symbolPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                Component comp = symbolPanel.getComponentAt(e.getX(), e.getY());
                if (comp instanceof MusicSymbol) {
                    MusicSymbol symbol = (MusicSymbol) comp;
                    selectedSymbol = symbol.clone(); // Store the selected symbol
                }
            }
        });
        
        // Add mouse listener to staff panel
        staffPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedSymbol != null) {
                    // Determine which StaffPanel is clicked
                    Component comp = staffPanel.getComponentAt(e.getX(), e.getY());
                    if (comp instanceof StaffPanel) {
                        StaffPanel staff = (StaffPanel) comp;
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

    private void arrangeComponents() {
        setLayout(new BorderLayout());

        // Add symbol panel to the top
        add(symbolPanel, BorderLayout.NORTH);

        // Add staff lines panel to the center
        add(staffPanel, BorderLayout.CENTER);

        // Create control panel for buttons
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.add(playPauseButton);
        controlPanel.add(stopButton);

        // Add control panel to the bottom
        add(controlPanel, BorderLayout.SOUTH);
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

    // Inner class for representing a staff panel
    private class StaffPanel extends JPanel {
        // Constants for staff dimensions and positions
        private static final int LINE_GAP = 20;     // Vertical gap between staff lines
        private static final int NUM_LINES = 5;     // Number of lines per staff
        private static final int STAFF_WIDTH = 730; // Width of each staff
        private static final int STAFF_HEIGHT = NUM_LINES * LINE_GAP; // Height of each staff
        private static final int STAFF_MARGIN = 20; // Margin around each staff
        private static final int PANEL_WIDTH = 2 * (STAFF_MARGIN + STAFF_WIDTH);    // Total panel width
        private static final int PANEL_HEIGHT = 2 * (STAFF_MARGIN + STAFF_HEIGHT);  // Total panel height

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

            // Draw Treble clef staff
            drawStaff(g, STAFF_MARGIN, STAFF_MARGIN, STAFF_WIDTH, STAFF_HEIGHT);
            
            // Draw Bass clef staff
            drawStaff(g, STAFF_MARGIN, 2 * STAFF_MARGIN + STAFF_HEIGHT, STAFF_WIDTH, STAFF_HEIGHT);

            for (MusicSymbol symbol : symbols) {
                symbol.drawSymbol(g);
            }
        }
        
        // Method to draw a single staff
        private void drawStaff(Graphics g, int x, int y, int width, int height) {
            int lineY = y; // Starting y-coordinate for the lines
            
            // Draw the staff lines
            for (int i = 0; i < NUM_LINES; i++) {
                g.drawLine(x, lineY, x + width, lineY);
                lineY += LINE_GAP; // Move to the next line position
            }
        }
        
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
        }
    }

////////////////////////////////////////////////////// Symbols //////////////////////////////////////////////////////

public abstract class MusicSymbol extends JPanel {
    protected int type;
    protected int xOffset;
    protected int yOffset;

    protected Point position = new Point(); // Added to track symbol position

    public MusicSymbol(int type) {
        this.type = type;
        setPreferredSize(new Dimension(50, 50)); // Adjust size as needed
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawSymbol(g);
    }

    public void setPosition(int x, int y) {
        this.position.setLocation(x, y);
    }

    public Point getPosition() {
        return this.position;
    }

    protected abstract void drawSymbol(Graphics g);

    // For when it is placed on the staff
    protected abstract MusicSymbol clone();
}

class TrebleClefSymbol extends MusicSymbol {
    public TrebleClefSymbol() {
        super(1);
    }

    @Override
    protected void drawSymbol(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        GeneralPath path = new GeneralPath();
        
        g2d.draw(path);
    }

    @Override
    protected MusicSymbol clone() {
        return new TrebleClefSymbol();
    }
}


class WholeNoteSymbol extends MusicSymbol {
    public WholeNoteSymbol() {
        super(2);
    }

    @Override
    protected void drawSymbol(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
       // Use symbol's position for drawing
        int drawX = this.position.x ; // Adjust the 10 to position the note correctly relative to its selection point
        int drawY = this.position.y ; // Adjust the 10 to position the note correctly on the staff line

        // Drawing the whole note based on the updated position
        g2d.fill(new Ellipse2D.Double(drawX, drawY, 20, 15));
        g2d.setColor(Color.WHITE);
        g2d.fill(new Ellipse2D.Double(drawX + 2, drawY + 2, 16, 11));

        g2d.dispose(); // Dispose of the graphics context copy

    }

    @Override
    protected MusicSymbol clone() {
        WholeNoteSymbol clonedSymbol = new WholeNoteSymbol();
        clonedSymbol.setPosition(this.position.x, this.position.y); // Copy position
        return clonedSymbol;
    }
}

class HalfNoteSymbol extends MusicSymbol {
    public HalfNoteSymbol() {
        super(3);
    }

    @Override
    protected void drawSymbol(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.draw(new Ellipse2D.Double(10, 10, 20, 15));
        g2d.drawLine(30, 17, 30, -20);
    }

    @Override
    protected MusicSymbol clone() {
        return new HalfNoteSymbol();
    }
}

class QuarterNoteSymbol extends MusicSymbol {
    public QuarterNoteSymbol() {
        super(4);
    }

    @Override
    protected void drawSymbol(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.fill(new Ellipse2D.Double(10, 10, 20, 15));
        g2d.drawLine(30, 17, 30, -20);
    }

    @Override
    protected MusicSymbol clone() {
        return new QuarterNoteSymbol();
    }
}

class EighthNoteSymbol extends MusicSymbol {
    public EighthNoteSymbol() {
        super(5);
    }

    @Override
    protected void drawSymbol(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.fill(new Ellipse2D.Double(10, 10, 20, 15));
        g2d.drawLine(30, 17, 30, -20);
        // Draw the flag for the eighth note
        g2d.setStroke(new BasicStroke(4));
        g2d.drawLine(30,1,45,2);
    }

    @Override
    protected MusicSymbol clone() {
        return new EighthNoteSymbol();
    }
}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MusicNotationEditorUI());
    }
}

