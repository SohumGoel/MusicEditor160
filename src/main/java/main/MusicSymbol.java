package main;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.MatteBorder;


    public abstract class MusicSymbol extends JPanel {
        protected int type;
        protected int xOffset;
        protected int yOffset;
        protected Point position = new Point();

        public MusicSymbol(int type) {
            this.type = type;
            setPreferredSize(new Dimension(40, 40));
            setBorder(new MatteBorder(2, 2, 2, 2, Color.BLUE));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawSymbol(g);
        }

        public void setPosition(int x, int y) {
            this.position.setLocation(x, y);
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

