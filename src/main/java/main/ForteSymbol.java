package main;
import java.awt.Font;
import java.awt.Graphics;

class ForteSymbol extends MusicSymbol{

    public ForteSymbol(int x, int y) {
        super(6);
        this.position.x = x;
        this.position.y = y;
    }

    @Override
    protected void drawSymbol(Graphics g) {
        g.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 20));
        g.drawString("f", this.position.x, this.position.y);
    }

    @Override
    protected MusicSymbol clone() {
        return new ForteSymbol(this.position.x, this.position.y);
    }
}
