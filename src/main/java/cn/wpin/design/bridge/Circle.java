package cn.wpin.design.bridge;

/**
 * 画圆形
 *
 * @author wangpin
 */
public class Circle extends AbstractDraw {

    private int radius;

    public Circle(int radius, DrawApi drawApi) {
        super(drawApi);
        this.radius = radius;

    }

    @Override
    public void draw() {
        drawApi.draw(radius,0,0);
    }
}
