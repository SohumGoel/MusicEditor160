package main;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

class HalfNoteSymbol extends MusicSymbol {
    public HalfNoteSymbol() {
        super(3);
    }

    @Override
    protected void drawSymbol(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.draw(new Ellipse2D.Double(10, 10, 20, 15));
        g2d.drawLine(30, 17, 30, -20);
    }

    @Override
    protected MusicSymbol clone() {
        HalfNoteSymbol clonedSymbol = new HalfNoteSymbol();
        clonedSymbol.setPosition(this.position.x, this.position.y); // Copy position
        return clonedSymbol;
    }
}
