package main;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

    private boolean isDragging = false;
    private MusicSymbol draggingSymbol = null;
    private Phrase draggingPhrase = null;

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
        PitchCalculator pitchCalculator = new PitchCalculator();

        staffPanel = new JPanel(new GridLayout(0, 1));
        staffPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        for (int i = 0; i < 3; i++) { // Example: 2 pairs of staffs
            staffPanel.add(new Phrase(pitchCalculator));
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
                        int x = e.getX() - comp.getX(); // Adjust X coordinate relative to staff
                        int y = e.getY() - comp.getY();
                        for (MusicSymbol symbol : phrase.getSymbols()) {
                            if (Math.abs(x - symbol.getPosition().x) <= 24 &&
                                Math.abs(y - symbol.getPosition().y) <= 10) {
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

            @Override
            public void mousePressed(MouseEvent e) {
                // Identify if a symbol is clicked for dragging
                if (SwingUtilities.isLeftMouseButton(e)) {
                    for (Component comp : staffPanel.getComponents()) {
                        if (comp instanceof Phrase) {
                            Phrase phrase = (Phrase) comp;
                            for (MusicSymbol symbol : phrase.getSymbols()) {
                                if (symbol.getBounds().contains(e.getPoint())) {
                                    draggingSymbol = symbol;
                                    draggingPhrase = phrase;
                                    isDragging = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (isDragging && draggingSymbol != null) {
                    // Finalize drag
                    int x = e.getX() - draggingPhrase.getX();
                    int y = e.getY() - draggingPhrase.getY();
                    draggingSymbol.setPosition(x, y);

                    draggingPhrase.recalculateSymbolPitch(draggingSymbol); // this i public for now

                    draggingPhrase.repaint();
                    isDragging = false;
                    draggingSymbol = null;
                    draggingPhrase = null;
                }
            }
        });

        staffPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDragging && draggingSymbol != null) {
                    int x = e.getX() - draggingPhrase.getX();
                    int y = e.getY() - draggingPhrase.getY();
                    draggingSymbol.setPosition(x, y);
                    draggingPhrase.repaint();
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
        addNotesToPanel();
        addDynamicsToPanel();
        symbolPanel.add(new WholeRestSymbol(10,25));
        symbolPanel.add(new HalfRestSymbol(10,25));
    }

    private void addNotesToPanel() {
        symbolPanel.add(new WholeNoteSymbol(10,10));
        symbolPanel.add(new HalfNoteSymbol(10,15));
        symbolPanel.add(new QuarterNoteSymbol(10,15));
        symbolPanel.add(new EighthNoteSymbol(10,15));
    }

    private void addDynamicsToPanel() {
        symbolPanel.add(new PianoSymbol(15,25));
        symbolPanel.add(new ForteSymbol(17,25));
        symbolPanel.add(new MPSymbol(5,25));
        symbolPanel.add(new MFSymbol(5,25));
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
        playPauseButton = new JButton("Play");
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
                isPlaying = !isPlaying;
                int volume = getVolume();
                if (isPlaying) {
                    for (Component comp : staffPanel.getComponents()) {
                        if (comp instanceof Phrase) {
                            ((Phrase) comp).playSymbols(volume);
                        }
                    }
                    // playPauseButton.setText("Pause");
                } else {
                    playPauseButton.setText("Play");
                }
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private int getVolume() {
        int volume = 80;
        boolean dynamicFound = false;
        for (Component comp : staffPanel.getComponents()) {
            if (comp instanceof Phrase) {
                for (MusicSymbol symbol : ((Phrase) comp).getSymbols()) {
                    if (symbol instanceof PianoSymbol || symbol instanceof ForteSymbol ||
                            symbol instanceof MFSymbol || symbol instanceof MPSymbol) {
                        volume = checkDynamic(symbol);
                        dynamicFound = true;
                        break;
                    }
                }
                if (dynamicFound) break;
            }
        }
        return volume;
    }

    private int checkDynamic(MusicSymbol symbol) {
        if (symbol instanceof PianoSymbol) {
            return 40;
        } else if (symbol instanceof ForteSymbol) {
            return 120;
        } else if (symbol instanceof MFSymbol) {
            return 100;
        } else if (symbol instanceof MPSymbol) {
            return 60;
        }
        return 80;
    }

}
