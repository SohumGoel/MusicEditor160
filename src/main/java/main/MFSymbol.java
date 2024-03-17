package main;
import java.awt.Font;
import java.awt.Graphics;

class MFSymbol extends MusicSymbol {
    public MFSymbol(int x, int y) {
        super(8);
        this.position.x = x;
        this.position.y = y;
    }

    @Override
    protected void drawSymbol(Graphics g) {
        g.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 20));
        g.drawString("mf", this.position.x, this.position.y);
    }

    @Override
    protected MusicSymbol clone() {
        return new MFSymbol(this.position.x, this.position.y);
    }
}
