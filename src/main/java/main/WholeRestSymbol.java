package main;
import java.awt.Graphics;

public class WholeRestSymbol extends MusicSymbol {
    public WholeRestSymbol(int x, int y) {
        super(9);
        this.position.x = x;
        this.position.y = y;
        setDuration(4.0); 
    }

    @Override
    protected void drawSymbol(Graphics g) {
        g.drawLine(position.x, position.y - 5, position.x + 20, position.y - 5);
        g.fillRect(position.x + 5, position.y - 5, 11, 6);
    }

    @Override
    protected MusicSymbol clone() {
        // Implement clone method for whole rest
        return new WholeRestSymbol(this.position.x, this.position.y);
    }
}