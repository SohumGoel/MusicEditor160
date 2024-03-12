package main;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
// Make even smaller? 8 eighth notes do not fit
class EighthNoteSymbol extends MusicSymbol {
    public EighthNoteSymbol(int x, int y) {
        super(5);
        this.position.x = x;
        this.position.y = y;
    }

    @Override
    protected void drawSymbol(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.fill(new Ellipse2D.Double(this.position.x, this.position.y, 15, 13));
        g2d.drawLine(this.position.x + 15, this.position.y + 7, this.position.x + 15, this.position.y - 10);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawLine(this.position.x + 15, this.position.y-9, this.position.x + 25, this.position.y-8);
        g2d.setStroke(new BasicStroke(1));
    }

    @Override
    protected MusicSymbol clone() {
        return new EighthNoteSymbol(this.position.x, this.position.y);
    }
}