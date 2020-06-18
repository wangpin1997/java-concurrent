package cn.wpin.design.bridge;

/**
 * 画长方形
 *
 * @author wangpin
 */
public class Rectangle extends AbstractDraw {

    private int x;
    private int y;

    public Rectangle(int x, int y, DrawApi drawApi) {
        super(drawApi);
        this.x = x;
        this.y = y;

    }

    @Override
    public void draw() {
        drawApi.draw(0, x, y);
    }
}
