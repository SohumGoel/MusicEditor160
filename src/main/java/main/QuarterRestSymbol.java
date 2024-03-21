package main;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class QuarterRestSymbol extends MusicSymbol {
    
    public QuarterRestSymbol(int x, int y) {
        super(11);
        this.position.x = x;
        this.position.y = y;
        setDuration(1.0); 
    }

    @Override
    protected void drawSymbol(Graphics g) {
        Image quarterRestImage = Toolkit.getDefaultToolkit().getImage("src\\main\\java\\main\\quarter_rest.png");
        int width = quarterRestImage.getWidth(this);
        int height = quarterRestImage.getHeight(this);
        g.drawImage(quarterRestImage, this.position.x - width/2, this.position.y - height/2, this);
    }

    @Override
    protected MusicSymbol clone() {
        return new QuarterRestSymbol(this.position.x, this.position.y);
    }
}