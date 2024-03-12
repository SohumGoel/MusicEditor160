package main;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

class WholeNoteSymbol extends MusicSymbol {
    public WholeNoteSymbol(int x, int y) {
        super(2);
        this.position.x = x;
        this.position.y = y;
    }

    @Override
    protected void drawSymbol(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.fill(new Ellipse2D.Double(this.position.x, this.position.y, 20, 15));
        g2d.setColor(getBackground());
        g2d.fill(new Ellipse2D.Double(this.position.x + 2, this.position.y + 2, 16, 11));
        g2d.dispose();
    }

    @Override
    protected MusicSymbol clone() {
        return new WholeNoteSymbol(this.position.x, this.position.y);
    }
}