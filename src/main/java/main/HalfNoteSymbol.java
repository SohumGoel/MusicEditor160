package main;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

class HalfNoteSymbol extends MusicSymbol {
    public HalfNoteSymbol(int x, int y) {
        super(3);
        this.position.x = x;
        this.position.y = y;
        setDuration(2.0); 

    }

    @Override
    protected void drawSymbol(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.draw(new Ellipse2D.Double(this.position.x, this.position.y, 15, 13));
        g2d.drawLine(this.position.x + 15, this.position.y + 7, this.position.x + 15, this.position.y - 10);
    }

    @Override
    protected MusicSymbol clone() {
        return new HalfNoteSymbol(this.position.x, this.position.y);
    }
}
