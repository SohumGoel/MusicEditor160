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
    private PitchCalculator pitchCalculator;

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
        this.pitchCalculator = new PitchCalculator();

        staffPanel = new JPanel(new GridLayout(0, 1));
        staffPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        for (int i = 0; i < 3; i++) { // Example: 2 pairs of staffs
            staffPanel.add(new Phrase(pitchCalculator));
        }
        addStaffPanelListener();
    }

    // TO DO: refactor to be of less lines
    // private void addStaffPanelListener() {
    //     staffPanel.addMouseListener(new MouseAdapter() {
    //         @Override
    //         public void mouseClicked(MouseEvent e) {
    //             if (SwingUtilities.isRightMouseButton(e)) {
    //                 Component comp = staffPanel.getComponentAt(e.getX(), e.getY());
    //                 if (comp instanceof Phrase) {
    //                     Phrase phrase = (Phrase) comp;
    //                     int x = e.getX() - comp.getX(); // Adjust X coordinate relative to staff
    //                     int y = e.getY() - comp.getY();
    //                     for (MusicSymbol symbol : phrase.getSymbols()) {
    //                         if (Math.abs(x - symbol.getPosition().x) <= 24 &&
    //                             Math.abs(y - symbol.getPosition().y) <= 10) {
    //                             phrase.removeSymbol(symbol);
    //                             break;
    //                         }
    //                     }
    //                 }
    //             } else if (selectedSymbol != null && SwingUtilities.isLeftMouseButton(e)) {
    //                 Component comp = staffPanel.getComponentAt(e.getX(), e.getY());
    //                 if (comp instanceof Phrase) {
    //                     Phrase staff = (Phrase) comp;
    //                     int x = e.getX() - comp.getX(); // Adjust X coordinate relative to staff
    //                     int y = e.getY() - comp.getY(); // Adjust Y coordinate relative to staff

    //                     staff.addSymbol(selectedSymbol.clone(), x, y); // Clone again to keep original
    //                     selectedSymbol = null; // Reset selected symbol
    //                 }
    //             }
    //         }

    //         @Override
    //         public void mousePressed(MouseEvent e) {
    //             // Identify if a symbol is clicked for dragging
    //             if (SwingUtilities.isLeftMouseButton(e)) {
    //                 for (Component comp : staffPanel.getComponents()) {
    //                     if (comp instanceof Phrase) {
    //                         Phrase phrase = (Phrase) comp;
    //                         int x = e.getX() - comp.getX(); // Adjust X coordinate relative to staff
    //         int y = e.getY() - comp.getY();
    //                         for (MusicSymbol symbol : phrase.getSymbols()) {
    //                             if (Math.abs(x - symbol.getPosition().x) <= 24 &&
    //                                 Math.abs(y - symbol.getPosition().y) <= 10) {
    //                                 draggingSymbol = symbol;
    //                                 draggingPhrase = phrase;
    //                                 isDragging = true;
    //                                 break;
    //                             }
    //                         }
    //                     }
    //                 }
    //             }
    //         }

    //         @Override
    //         public void mouseReleased(MouseEvent e) {
    //             if (isDragging && draggingSymbol != null) {
    //                 // Finalize drag
    //                 int x = e.getX() - draggingPhrase.getX();
    //                 int y = e.getY() - draggingPhrase.getY();
    //                 draggingSymbol.setPosition(x, y);

    //                 recalculateSymbolPitch(draggingSymbol); // this i public for now

    //                 draggingPhrase.repaint();
    //                 isDragging = false;
    //                 draggingSymbol = null;
    //                 draggingPhrase = null;
    //             }
    //         }
    //     });

    //     staffPanel.addMouseMotionListener(new MouseAdapter() {
    //         @Override
    //         public void mouseDragged(MouseEvent e) {
    //             if (isDragging && draggingSymbol != null) {
    //                 int x = e.getX() - draggingPhrase.getX();
    //                 int y = e.getY() - draggingPhrase.getY();
    //                 draggingSymbol.setPosition(x, y);
    //                 draggingPhrase.repaint();
    //             }
    //         }
    //     });
    // }

    private void addStaffPanelListener() {
        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    removeSymbolAt(e);
                } else if (selectedSymbol != null && SwingUtilities.isLeftMouseButton(e)) {
                    addSelectedSymbolAt(e);
                }
            }
    
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    startDraggingSymbol(e);
                }
            }
    
            @Override
            public void mouseReleased(MouseEvent e) {
                if (isDragging) {
                    finalizeDrag(e);
                }
            }
    
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDragging) {
                    updateDragPosition(e);
                }
            }
        };
    
        staffPanel.addMouseListener(mouseHandler);
        staffPanel.addMouseMotionListener(mouseHandler);
    }
    
    private void removeSymbolAt(MouseEvent e) {
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
    }
    
    private void addSelectedSymbolAt(MouseEvent e) {
        Component comp = staffPanel.getComponentAt(e.getX(), e.getY());
        if (comp instanceof Phrase) {
            Phrase staff = (Phrase) comp;
            int x = e.getX() - comp.getX(); // Adjust X coordinate relative to staff
            int y = e.getY() - comp.getY(); // Adjust Y coordinate relative to staff
            staff.addSymbol(selectedSymbol.clone(), x, y); // Clone again to keep original
            selectedSymbol = null; // Reset selected symbol
        }
    }
    
    private void startDraggingSymbol(MouseEvent e) {
        // initiate drag
        // Identify if a symbol is clicked for dragging
        for (Component comp : staffPanel.getComponents()) {
            if (comp instanceof Phrase) {
                Phrase phrase = (Phrase) comp;
                int x = e.getX() - comp.getX(); // Adjust X coordinate relative to staff
                int y = e.getY() - comp.getY();
                for (MusicSymbol symbol : phrase.getSymbols()) {
                    if (Math.abs(x - symbol.getPosition().x) <= 24 &&
                        Math.abs(y - symbol.getPosition().y) <= 10) {
                        draggingSymbol = symbol;
                        draggingPhrase = phrase;
                        isDragging = true;
                        break;
                    }
                }
            }
        }
    }
    
    private void finalizeDrag(MouseEvent e) {
       // finalize drag and recalculate pitch
        int x = e.getX() - draggingPhrase.getX();
        int y = e.getY() - draggingPhrase.getY();
        draggingSymbol.setPosition(x, y);
        recalculateSymbolPitch(draggingSymbol);
        draggingPhrase.repaint();

        isDragging = false;
        draggingSymbol = null;
        draggingPhrase = null;
    }
    
    private void updateDragPosition(MouseEvent e) {
        // Update position during drag
        int x = e.getX() - draggingPhrase.getX();
        int y = e.getY() - draggingPhrase.getY();
        draggingSymbol.setPosition(x, y);
        draggingPhrase.repaint();
    }
    

    public void recalculateSymbolPitch(MusicSymbol symbol) {
        int pitch = pitchCalculator.calculatePitch(symbol.getPosition().y);
        symbol.setMidiPitch(pitch);        
        System.out.println("position after drag: y = " + symbol.getPosition().y + " with MIDI pitch: " + symbol.getMidiPitch());
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
                int volume = getVolume();
                if (!isPlaying) {
                    isPlaying = true;
                    for (Component comp : staffPanel.getComponents()) {
                        if (comp instanceof Phrase) {
                            ((Phrase) comp).playSymbols(volume);
                        }
                    }
                    isPlaying = false;
                    // playPauseButton.setText("Pause");
                } else {
                    //isPlaying = false;
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
