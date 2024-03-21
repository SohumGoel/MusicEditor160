package main;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class EighthRestSymbol extends MusicSymbol {
    
    public EighthRestSymbol(int x, int y) {
        super(12);
        this.position.x = x;
        this.position.y = y;
        setDuration(0.5); 
    }

    @Override
    protected void drawSymbol(Graphics g) {
        Image eighthRestImage = Toolkit.getDefaultToolkit().getImage("src\\main\\java\\main\\eighth_rest.png");
        int width = eighthRestImage.getWidth(this);
        int height = eighthRestImage.getHeight(this);
        g.drawImage(eighthRestImage, this.position.x - width/2, this.position.y - height/2, this);
    }

    @Override
    protected MusicSymbol clone() {
        return new EighthRestSymbol(this.position.x, this.position.y);
    }
}