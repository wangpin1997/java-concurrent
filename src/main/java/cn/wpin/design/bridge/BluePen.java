package cn.wpin.design.bridge;

/**
 * 蓝笔画图
 *
 * @author wangpin
 */
public class BluePen implements DrawApi {

    @Override
    public void draw(int radius, int x, int y) {
        System.out.println("blue pen draw !");
    }
}
