package main;

public class LoadCommand implements Command{
    private final MusicNotationEditorUI editorUI;

    public LoadCommand(MusicNotationEditorUI editorUI) {
        this.editorUI = editorUI;
    }

    @Override
    public void execute() {
        editorUI.loadComposition();
    }
}
