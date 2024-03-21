package main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

class WholeNoteSymbol extends MusicSymbol {

    public WholeNoteSymbol(int x, int y) {
        super(2);
        this.position.x = x;
        this.position.y = y;
        setDuration(4.0);
    }

    @Override
    protected void drawSymbol(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        drawWholeNote(g2d);
        drawLedgerLine(g2d);
        g2d.dispose();
    }

    private void drawWholeNote(Graphics2D g) {
        g.fill(new Ellipse2D.Double(this.position.x, this.position.y, 20, 15));
        g.setColor(getBackground());
        g.fill(new Ellipse2D.Double(this.position.x + 2, this.position.y + 2, 16, 11));
    }

    private void drawLedgerLine(Graphics g) {
        g.setColor(new Color(0,0,0));
        if(this.position.y == 84 || this.position.y == 122) 
            g.drawLine(this.position.x-7, this.position.y+7, this.position.x + 25, this.position.y+7);
    }

    @Override
    protected MusicSymbol clone() {
        return new WholeNoteSymbol(this.position.x, this.position.y);
    }
}