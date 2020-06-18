package cn.wpin.design.bridge;

/**
 * 测试前街模式main方法
 *
 * @author wangpin
 */
public class Main {

    public static void main(String[] args) {
        //用绿笔画一个半径为10的圆
        AbstractDraw abstractDraw = new Circle(10, new GreenPen());
        //用红笔画一个长度10宽度为8的长方形
        AbstractDraw abstractDraw1 = new Rectangle(10, 8, new RedPen());
        abstractDraw.draw();
        abstractDraw1.draw();
    }
}
