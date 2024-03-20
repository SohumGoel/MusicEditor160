package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SymbolPanelListener extends MouseAdapter{
    private final MusicNotationEditorUI editorUI;

    public SymbolPanelListener(MusicNotationEditorUI editorUI) {
        this.editorUI = editorUI;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        editorUI.handleSymbolPanelMousePressed(e);
    }
}
