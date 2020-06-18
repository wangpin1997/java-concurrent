package cn.wpin.design.bridge;

/**
 * 红笔画图
 * @author wangpin
 */
public class RedPen implements DrawApi {

    @Override
    public void draw(int radius, int x, int y) {
        System.out.println("red pen draw !");
    }
}
