package main;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

class QuarterNoteSymbol extends MusicSymbol {
    public QuarterNoteSymbol() {
        super(4);
        
    }

    @Override
    protected void drawSymbol(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        this.xOffset = this.position.x;
        this.yOffset = this.position.y;
        g2d.fill(new Ellipse2D.Double(xOffset, yOffset, 20, 15));
        g2d.drawLine(xOffset + 20, yOffset + 7, xOffset + 20, yOffset - 10);
    }

    @Override
    protected MusicSymbol clone() {
        QuarterNoteSymbol clonedSymbol = new QuarterNoteSymbol();
        clonedSymbol.setPosition(this.position.x, this.position.y); // Copy position
        return clonedSymbol;
    }
}
