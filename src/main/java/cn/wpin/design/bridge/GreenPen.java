package cn.wpin.design.bridge;

/**
 * 绿笔画图
 * @author wangpin
 */
public class GreenPen implements DrawApi {

    @Override
    public void draw(int radius, int x, int y) {
        System.out.println("green pen draw !");
    }
}
