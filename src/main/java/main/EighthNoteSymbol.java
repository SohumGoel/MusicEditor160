package main;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

class EighthNoteSymbol extends MusicSymbol{
    
    public EighthNoteSymbol(int x, int y) {
        super(5);
        this.position.x = x;
        this.position.y = y;
        setDuration(0.5); 
    }

    @Override
    protected void drawSymbol(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.fill(new Ellipse2D.Double(this.position.x, this.position.y, 15, 13));
        drawLedgerLine(g2d);
        drawStem(g2d);
    }

    private void drawLedgerLine(Graphics2D g) {
        if(this.position.y == 84 || this.position.y == 122) 
            g.drawLine(this.position.x-7, this.position.y+6, this.position.x + 22, this.position.y+6);
    }

    private void drawStem(Graphics2D g) {
        if ((this.position.y == 15 && this.position.x == 10)) 
            drawUpStem(g);
        else if ((this.position.y > 46 && this.position.y < 120) || this.position.y > 166) 
            drawDownStem(g);
        else drawShortStem(g);
    }

    private void drawUpStem(Graphics2D g) {
        g.drawLine(this.position.x + 15, this.position.y + 7, this.position.x + 15, this.position.y - 10);
        g.setStroke(new BasicStroke(4));
        g.drawLine(this.position.x + 15, this.position.y-9, this.position.x + 25, this.position.y-8);
        g.setStroke(new BasicStroke(1));
    }

    private void drawDownStem(Graphics2D g) {
        g.drawLine(this.position.x + 15, this.position.y + 7, this.position.x + 15, this.position.y - 25);
        g.setStroke(new BasicStroke(5));
        g.drawLine(this.position.x + 15, this.position.y-24, this.position.x + 25, this.position.y-23);
        g.setStroke(new BasicStroke(1));
    }

    private void drawShortStem(Graphics2D g) {
        g.drawLine(this.position.x, this.position.y + 7, this.position.x, this.position.y + 36);
        g.setStroke(new BasicStroke(5));
        g.drawLine(this.position.x + 2, this.position.y + 36, this.position.x + 12, this.position.y + 34);
        g.setStroke(new BasicStroke(1));
    }

    @Override
    protected MusicSymbol clone() {
        return new EighthNoteSymbol(this.position.x, this.position.y);
    }
}