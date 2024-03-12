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
        this.xOffset = this.position.x;
        this.yOffset = this.position.y;
        g2d.draw(new Ellipse2D.Double(xOffset, yOffset, 20, 15));
        g2d.drawLine(xOffset + 20, yOffset + 7, xOffset + 20, yOffset - 10);
    }

    @Override
    protected MusicSymbol clone() {
        HalfNoteSymbol clonedSymbol = new HalfNoteSymbol();
        clonedSymbol.setPosition(this.position.x, this.position.y);
        return clonedSymbol;
    }
}
