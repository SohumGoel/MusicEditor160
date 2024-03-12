package main;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

class EighthNoteSymbol extends MusicSymbol {
    public EighthNoteSymbol() {
        super(5);
    }

    @Override
    protected void drawSymbol(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        this.xOffset = this.position.x;
        this.yOffset = this.position.y;

        g2d.fill(new Ellipse2D.Double(xOffset, yOffset, 20, 15));
        g2d.drawLine(xOffset + 20, yOffset + 7, xOffset + 20, yOffset - 10);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawLine(xOffset + 20, yOffset-9, xOffset + 35, yOffset-8);
        g2d.setStroke(new BasicStroke(1));
    }

    @Override
    protected MusicSymbol clone() {
        EighthNoteSymbol clonedSymbol = new EighthNoteSymbol();
        clonedSymbol.setPosition(this.position.x, this.position.y); // Copy position
        return clonedSymbol;
    }
}