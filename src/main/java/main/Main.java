package main;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MusicNotationEditorUI());
    }
}


// fixed order of note additions
// drag feature
// refarctor pitch cal for less lines

// safe file