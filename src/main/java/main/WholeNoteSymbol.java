package main;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

class WholeNoteSymbol extends MusicSymbol {
    public WholeNoteSymbol() {
        super(2);
    }

    @Override
    protected void drawSymbol(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        this.xOffset = this.position.x;
        this.yOffset = this.position.y;

        g2d.fill(new Ellipse2D.Double(xOffset, yOffset, 20, 15));
        g2d.setColor(getBackground());
        g2d.fill(new Ellipse2D.Double(xOffset + 2, yOffset + 2, 16, 11));
        g2d.dispose();
    }

    @Override
    protected MusicSymbol clone() {
        WholeNoteSymbol clonedSymbol = new WholeNoteSymbol();
        clonedSymbol.setPosition(this.position.x, this.position.y); // Copy position
        return clonedSymbol;
    }
}