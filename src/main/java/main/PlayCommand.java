package main;

public class PlayCommand implements Command {
    private final MusicNotationEditorUI editorUI;

    public PlayCommand(MusicNotationEditorUI editorUI) {
        this.editorUI = editorUI;
    }

    @Override
    public void execute() {
        editorUI.togglePlayback();
    }
}
