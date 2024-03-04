package main;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

class TrebleClefSymbol extends MusicSymbol {
    public TrebleClefSymbol() {
        super(1);
    }

    @Override
    protected void drawSymbol(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        GeneralPath path = new GeneralPath();
        
        g2d.draw(path);
    }

    @Override
    protected MusicSymbol clone() {
        return new TrebleClefSymbol();
    }
}