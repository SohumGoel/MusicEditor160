package main;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.geom.GeneralPath;

class TrebleClefSymbol extends MusicSymbol {
    public TrebleClefSymbol() {
        super(1);
    }

    @Override
    protected void drawSymbol(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
    
        GeneralPath path = new GeneralPath();
        // Start at the bottom of the clef
        path.moveTo(position.x, position.y + 35);
        
        // Draw the lower curve
        path.curveTo(position.x - 10, position.y + 35, position.x - 10, position.y + 15, position.x, position.y + 15);
        // Draw the center part of the symbol
        path.curveTo(position.x + 10, position.y + 15, position.x + 10, position.y + 50, position.x, position.y + 50);
        // Draw the upper curve that loops around
        path.curveTo(position.x - 10, position.y + 50, position.x - 10, position.y, position.x + 15, position.y);
        // More curves and lines would be needed here to complete the treble clef shape
        g2d.setStroke(new BasicStroke(3)); // Set the stroke width
        g2d.draw(path); // Draw the path
        
    }

    @Override
    protected MusicSymbol clone() {
        TrebleClefSymbol clonedSymbol = new TrebleClefSymbol();
        clonedSymbol.setPosition(this.position.x, this.position.y); // Copy position
        return clonedSymbol;    }
}