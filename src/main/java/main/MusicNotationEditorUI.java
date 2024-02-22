package main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


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
        setPreferredSize(new Dimension(800, 600));

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
        staffPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        for (int i = 0; i < 4; i++) { // Example: 4 staffs
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
        symbolPanel.add(new ClefSymbol());
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
                    selectedSymbol = symbol.clone();
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
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Example: Draw staff lines
            int startX = 50;
            int startY = 50;
            int endX = getWidth() - 50;
            int endY = 50;
            for (int i = 0; i < 5; i++) {
                g.drawLine(startX, startY + i * 20, endX, endY + i * 20);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(600, 100); // Example size for the staff panel
        }
    }

////////////////////////////////////////////////////// Symbols //////////////////////////////////////////////////////

public abstract class MusicSymbol extends JPanel {
    protected int type;
    protected int xOffset;
    protected int yOffset;

    public MusicSymbol(int type) {
        this.type = type;
        setPreferredSize(new Dimension(50, 50)); // Adjust size as needed
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawSymbol(g);
    }

    protected abstract void drawSymbol(Graphics g);

    protected abstract MusicSymbol clone();
}

class ClefSymbol extends MusicSymbol {
    public ClefSymbol() {
        super(1);
    }

    @Override
    protected void drawSymbol(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.BLACK);

        // Draw clef symbol
          // This is a very simplified version of a treble clef.
          g2d.drawArc(20, 20, 10, 40, 70, 180);
          g2d.drawArc(20, 30, 20, 40, 180, -180);
          g2d.drawArc(10, 60, 40, 20, 180, 180);
    }

    @Override
        protected MusicSymbol clone() {
            return new ClefSymbol();
        }
}


class WholeNoteSymbol extends MusicSymbol {
    public WholeNoteSymbol() {
        super(2);
    }

    @Override
    protected void drawSymbol(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Draw the outer oval for the whole note
        g2d.fill(new Ellipse2D.Double(10, 10, 20, 15));
        g2d.setColor(getBackground());
        // Draw the inner oval to create the 'donut' effect
        g2d.fill(new Ellipse2D.Double(12, 12, 16, 11));
    }

    @Override
        protected MusicSymbol clone() {
            return new WholeNoteSymbol();
        }
}

class HalfNoteSymbol extends MusicSymbol {
    public HalfNoteSymbol() {
        super(3);
    }

    @Override
    protected void drawSymbol(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        // Draw the oval for the half note
        g2d.draw(new Ellipse2D.Double(10, 10, 20, 15));
        // Draw the stem for the half note
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
        // Draw the oval for the quarter note
        g2d.fill(new Ellipse2D.Double(10, 10, 20, 15));
        // Draw the stem for the quarter note
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
        // Draw the oval for the eighth note
        g2d.fill(new Ellipse2D.Double(10, 10, 20, 15));
        // Draw the stem for the eighth note
        g2d.drawLine(30, 17, 30, -20);
        // Draw the flag for the eighth note
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(30, -20, 45, -10);
    }

    @Override
        protected MusicSymbol clone() {
            return new EighthNoteSymbol();
        }
}

// class WholeNoteSymbol extends MusicSymbol {
//     public WholeNoteSymbol() {
//         super(2);
//     }

//     @Override
//     protected void drawSymbol(Graphics g) {
//         Graphics2D g2d = (Graphics2D) g;
//         g2d.setStroke(new BasicStroke(2));
//         g2d.setColor(Color.BLACK);

//         // Draw whole note symbol (example)
//         g2d.drawOval(10, 10, 30, 30);
//         g2d.fillOval(25, 25, 5, 5); // Note head
//     }
// }

// class HalfNoteSymbol extends MusicSymbol {
//     public HalfNoteSymbol() {
//         super(3);
//     }

//     @Override
//     protected void drawSymbol(Graphics g) {
//         Graphics2D g2d = (Graphics2D) g;
//         g2d.setStroke(new BasicStroke(2));
//         g2d.setColor(Color.BLACK);

//         // Draw half note symbol (example)
//         g2d.drawOval(10, 10, 30, 30);
//         g2d.drawLine(40, 25, 25, 45); // Stem
//     }
// }

// class QuarterNoteSymbol extends MusicSymbol {
//     public QuarterNoteSymbol() {
//         super(4);
//     }

//     @Override
//     protected void drawSymbol(Graphics g) {
//         Graphics2D g2d = (Graphics2D) g;
//         g2d.setStroke(new BasicStroke(2));
//         g2d.setColor(Color.BLACK);

//         // Draw quarter note symbol (example)
//         g2d.drawOval(10, 10, 30, 30);
//         g2d.drawLine(40, 25, 25, 45); // Stem
//         g2d.fillRect(22, 20, 5, 15); // Note head
//     }
// }

// class EighthNoteSymbol extends MusicSymbol {
//     public EighthNoteSymbol() {
//         super(5);
//     }

//     @Override
//     protected void drawSymbol(Graphics g) {
//         Graphics2D g2d = (Graphics2D) g;
//         g2d.setStroke(new BasicStroke(2));
//         g2d.setColor(Color.BLACK);

//         // Draw eighth note symbol (example)
//         g2d.drawOval(10, 10, 30, 30);
//         g2d.drawLine(40, 25, 25, 45); // Stem
//         g2d.fillOval(22, 20, 5, 5); // Note head
//     }
// }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MusicNotationEditorUI());
    }
}
