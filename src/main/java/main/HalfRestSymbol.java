package main;
import java.awt.Graphics;

public class HalfRestSymbol extends MusicSymbol {
    public HalfRestSymbol(int x, int y) {
        super(10);
        this.position.x = x;
        this.position.y = y;
        setDuration(2.0); 
    }

    @Override
    protected void drawSymbol(Graphics g) {
        g.drawLine(position.x, position.y - 5, position.x + 20, position.y - 5);
        g.fillRect(position.x + 5, position.y - 10, 11, 6);
    }

    @Override
    protected MusicSymbol clone() {
        // Implement clone method for whole rest
        return new HalfRestSymbol(this.position.x, this.position.y);
    }
}