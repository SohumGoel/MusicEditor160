package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

public abstract class MusicSymbol extends JPanel{

    protected int type;
    protected int xOffset;
    protected int yOffset;
    protected Point position = new Point();
    protected int midiPitch;
    protected double duration;

    public MusicSymbol(int type) {
        this.type = type;
        setPreferredSize(new Dimension(40, 40));
        setBorder(new MatteBorder(2, 2, 2, 2, Color.DARK_GRAY));
    }

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawSymbol(g);
    }

    public void setPosition(int x, int y) {
        this.position.setLocation(x, y);
        this.setBounds(x, y, getPreferredSize().width, getPreferredSize().height);
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

