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
        // Use symbol's position for drawing
        int drawX = this.position.x ; // Adjust the 10 to position the note correctly relative to its selection point
        int drawY = this.position.y ; // Adjust the 10 to position the note correctly on the staff line

        g2d.fill(new Ellipse2D.Double(drawX, drawY, 20, 15));
        g2d.setColor(getBackground());
        g2d.fill(new Ellipse2D.Double(drawX + 2, drawY + 2, 16, 11));
        g2d.dispose(); // Dispose of the graphics context copy
    }

    @Override
    protected MusicSymbol clone() {
        WholeNoteSymbol clonedSymbol = new WholeNoteSymbol();
        clonedSymbol.setPosition(this.position.x, this.position.y); // Copy position
        return clonedSymbol;
    }
}