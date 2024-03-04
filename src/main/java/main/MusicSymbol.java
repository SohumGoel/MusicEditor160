package main;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

    public abstract class MusicSymbol extends JPanel {
        protected int type;
        protected int xOffset;
        protected int yOffset;
        protected Point position = new Point();

        public MusicSymbol(int type) {
            this.type = type;
            setPreferredSize(new Dimension(50, 50)); // Adjust size as needed
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

        protected abstract void drawSymbol(Graphics g);
        protected abstract MusicSymbol clone();
    }

