package main;
import java.awt.Font;
import java.awt.Graphics;
import java.io.Serializable;



class PianoSymbol extends MusicSymbol implements Serializable{
    private static final long serialVersionUID = 1L;

    public PianoSymbol(int x, int y) {
        super(5);
        this.position.x = x;
        this.position.y = y;
    }

    @Override
    protected void drawSymbol(Graphics g) {
        g.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 20));
        g.drawString("p", this.position.x, this.position.y);
    }

    @Override
    protected MusicSymbol clone() {
        return new PianoSymbol(this.position.x, this.position.y);
    }
}
