package cn.wpin.design.deck;

/**
 * 绿茶
 *
 * @author wangpin
 */
public class GreenTea extends Beverage {

    private static String desc = "绿茶";
    private static Double cost = 11D;

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public Double cost() {
        return cost;
    }
}
