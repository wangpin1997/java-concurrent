package cn.wpin.design.bridge;

/**
 * 定义一个抽象类来桥接
 * @author wangpin
 */
public abstract class AbstractDraw {

    protected DrawApi drawApi;

    public AbstractDraw(DrawApi drawApi) {
        this.drawApi = drawApi;
    }

    public abstract void draw();
}
