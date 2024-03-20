package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StaffPanelListener extends MouseAdapter{
    private final MusicNotationEditorUI editorUI;

    public StaffPanelListener(MusicNotationEditorUI editorUI) {
        this.editorUI = editorUI;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        editorUI.handleMouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        editorUI.handleMousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        editorUI.handleMouseReleased(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        editorUI.handleMouseDragged(e);
    }
}
