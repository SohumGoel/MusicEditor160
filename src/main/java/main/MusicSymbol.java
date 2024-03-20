package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import java.io.Serializable;
import java.io.Serializable;



public abstract class MusicSymbol extends JPanel implements Serializable{
    private static final long serialVersionUID = 1L;

    protected int type;
    protected int xOffset;
    protected int yOffset;
    protected Point position = new Point();

    protected int midiPitch; // MIDI pitch number, 60 = Middle C (C4)
    protected double duration; // Duration in beats, 4 = Whole note, 1 = Quarter note, etc.

    // Add getters and setters for these properties
    public int getMidiPitch() {
        return midiPitch;
    }

    public void setMidiPitch(int midiPitch) {
        this.midiPitch = midiPitch;
        if (type > 5) this.midiPitch = 0;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public MusicSymbol(int type) {
        this.type = type;
        setPreferredSize(new Dimension(40, 40));
        setBorder(new MatteBorder(2, 2, 2, 2, Color.DARK_GRAY));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawSymbol(g);
    }

    public void setPosition(int x, int y) {
        this.position.setLocation(x, y);
        this.setBounds(x, y, getPreferredSize().width, getPreferredSize().height); // Update bounds for accurate hit detection

    }

    public Point getPosition() {
        return this.position;
    }

    public int getType() {
        return this.type;
    }

    protected abstract void drawSymbol(Graphics g);
    protected abstract MusicSymbol clone();
}

