package cn.wpin.design.deck;

/**
 * 红茶
 *
 * @author wangpin
 */
public class BlcakTea extends Beverage {

    private static String desc = "红茶";
    private static Double cost = 10D;

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public Double cost() {
        return cost;
    }
}
