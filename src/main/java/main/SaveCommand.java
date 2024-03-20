package main;

public class SaveCommand implements Command{
    private final MusicNotationEditorUI editorUI;

    public SaveCommand(MusicNotationEditorUI editorUI) {
        this.editorUI = editorUI;
    }

    @Override
    public void execute() {
        editorUI.saveComposition();
    }
}
