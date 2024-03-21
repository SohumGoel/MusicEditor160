package main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MusicNotationEditorUI extends JFrame{

    private JPanel staffPanel, controlPanel,symbolPanel;
    private JButton playPauseButton, stopButton, saveButton, loadButton;

    private MusicSymbol selectedSymbol;
    private MusicSymbol draggingSymbol = null;

    private boolean isDragging = false, isPlaying = false;

    private Phrase draggingPhrase = null;
    private PitchCalculator pitchCalculator;

    public MusicNotationEditorUI() {
        super("Simple Music Notation Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 850));
        initComponents();
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

        for (int i = 0; i < 3; i++) {
            staffPanel.add(new Phrase(pitchCalculator));
        }

        addStaffPanelListener();
    }

    private void addStaffPanelListener() {
        StaffPanelListener listener = new StaffPanelListener(this);
        staffPanel.addMouseListener(listener);
        staffPanel.addMouseMotionListener(listener);
    }

    public void handleMouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            removeSymbolAt(e);
        } else if (selectedSymbol != null && SwingUtilities.isLeftMouseButton(e)) {
            addSelectedSymbolAt(e);
        }
    }

    public void handleMousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            startDraggingSymbol(e);
        }
    }

    public void handleMouseReleased(MouseEvent e) {
        if (isDragging) {
            finalizeDrag(e);
        }
    }

    public void handleMouseDragged(MouseEvent e) {
        if (isDragging) {
            updateDragPosition(e);
        }
    }

    private void removeSymbolAt(MouseEvent e) {
        Component comp = staffPanel.getComponentAt(e.getX(), e.getY());
        if (comp instanceof Phrase) {
            Phrase phrase = (Phrase) comp;
            int x = e.getX() - comp.getX();
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
            int x = e.getX() - comp.getX();
            int y = e.getY() - comp.getY(); 
            staff.addSymbol(selectedSymbol.clone(), x, y);
            selectedSymbol = null;
        }
    }

    private void startDraggingSymbol(MouseEvent e) {
        for (Component comp : staffPanel.getComponents()) {
            if (comp instanceof Phrase) {
                Phrase phrase = (Phrase) comp;
                int x = e.getX() - comp.getX();
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
        int x = e.getX() - draggingPhrase.getX();
        int y = e.getY() - draggingPhrase.getY();
        draggingSymbol.setPosition(x, y);
        draggingPhrase.repaint();
    }

    public void recalculateSymbolPitch(MusicSymbol symbol) {
        int pitch = pitchCalculator.calculatePitch(symbol.getPosition().y);
        symbol.setMidiPitch(pitch);
        System.out.println(
                "position after drag: y = " + symbol.getPosition().y + " with MIDI pitch: " + symbol.getMidiPitch());
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
        addReststoPanel();
    }

    private void addNotesToPanel() {
        symbolPanel.add(new WholeNoteSymbol(10, 10));
        symbolPanel.add(new HalfNoteSymbol(10, 15));
        symbolPanel.add(new QuarterNoteSymbol(10, 15));
        symbolPanel.add(new EighthNoteSymbol(10, 15));
    }

    private void addDynamicsToPanel() {
        symbolPanel.add(new PianoSymbol(15, 25));
        symbolPanel.add(new ForteSymbol(17, 25));
        symbolPanel.add(new MPSymbol(5, 25));
        symbolPanel.add(new MFSymbol(5, 25));
    }

    private void addReststoPanel() {
        symbolPanel.add(new WholeRestSymbol(10, 25));
        symbolPanel.add(new HalfRestSymbol(10, 25));
        symbolPanel.add(new QuarterRestSymbol(20, 19));
        symbolPanel.add(new EighthRestSymbol(20, 19));
    }

    private void addSymbolPanelListener() {
        SymbolPanelListener listener = new SymbolPanelListener(this);
        symbolPanel.addMouseListener(listener);
    }

    public void handleSymbolPanelMousePressed(MouseEvent e) {
        Component comp = symbolPanel.getComponentAt(e.getX(), e.getY());
        if (comp instanceof MusicSymbol) {
            MusicSymbol symbol = (MusicSymbol) comp;
            selectedSymbol = symbol.clone();
        }
    }

    private void initControlPanel() {
        initButtons();
        controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.add(playPauseButton);
        controlPanel.add(stopButton);
        controlPanel.add(saveButton);
        controlPanel.add(loadButton);
        setUpActionListeners();
    }

    private void initButtons() {
        playPauseButton = new JButton("Play");
        stopButton = new JButton("Stop");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
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
        Command playCommand = new PlayCommand(this);
        Command saveCommand = new SaveCommand(this);
        Command loadCommand = new LoadCommand(this);
        // Command stopCommand = new StopCommand(this);
        playPauseButton.addActionListener(e -> playCommand.execute());
        saveButton.addActionListener(e -> saveCommand.execute());
        loadButton.addActionListener(e -> loadCommand.execute());
        // stopButton.addActionListener(e -> stopCommand.execute());
    }

    protected void saveComposition() {
        JFileChooser fileChooser = new JFileChooser();
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileToSave))) {
                for (Component comp : staffPanel.getComponents()) {
                    if (comp instanceof Phrase) {
                        out.writeObject(comp);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    protected void loadComposition() {
        JFileChooser fileChooser = new JFileChooser();
        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileToLoad))) {
                staffPanel.removeAll();
                while (true) {
                    try {
                        Phrase phrase = (Phrase) in.readObject();
                        staffPanel.add(phrase);
                    } catch (EOFException ex) {
                        break;
                    }
                }
                staffPanel.revalidate();
                staffPanel.repaint();
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }


    protected void togglePlayback() {
        int volume = getVolume();
        if (!isPlaying) {
            isPlaying = true;
            for (Component comp : staffPanel.getComponents()) {
                if (comp instanceof Phrase) {
                    ((Phrase) comp).playSymbols(volume);
                }
            }
            isPlaying = false;
        } else {
            playPauseButton.setText("Play");
        }
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
                if (dynamicFound)
                    break;
            }
        }
        return volume;
    }

    private int checkDynamic(MusicSymbol symbol) {
        return DYNAMIC_VALUES.getOrDefault(symbol.getClass(), 80);
    }

    private static final Map<Class<? extends MusicSymbol>, Integer> DYNAMIC_VALUES = Map.of(
        PianoSymbol.class, 40,
        ForteSymbol.class, 120,
        MFSymbol.class, 100,
        MPSymbol.class, 60
    );
}