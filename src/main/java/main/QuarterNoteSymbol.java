package main;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
// Make smaller?
class QuarterNoteSymbol extends MusicSymbol {
    public QuarterNoteSymbol(int x, int y) {
        super(4);
        this.position.x = x;
        this.position.y = y;
    }

    @Override
    protected void drawSymbol(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.fill(new Ellipse2D.Double(this.position.x, this.position.y, 15, 13));
        g2d.drawLine(this.position.x + 15, this.position.y + 7, this.position.x + 15, this.position.y - 10);
    }

    @Override
    protected MusicSymbol clone() {
        return new QuarterNoteSymbol(this.position.x, this.position.y);
    }
}
