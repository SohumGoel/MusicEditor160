package main;
import java.awt.Font;
import java.awt.Graphics;

class MPSymbol extends MusicSymbol{

    public MPSymbol(int x, int y) {
        super(7);
        this.position.x = x;
        this.position.y = y;
    }

    @Override
    protected void drawSymbol(Graphics g) {
        g.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 20));
        g.drawString("mp", this.position.x, this.position.y);
    }

    @Override
    protected MusicSymbol clone() {
        return new MPSymbol(this.position.x, this.position.y);
    }
}
