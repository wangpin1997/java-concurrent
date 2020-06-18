package cn.wpin.design.deck;

/**
 * 咖啡
 *
 * @author wangpin
 */
public class Coffee extends Beverage {

    private static String desc = "咖啡";
    private static Double cost = 12D;

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public Double cost() {
        return cost;
    }
}
