package cn.wpin.design.deck;

/**
 * 芒果
 *
 * @author wangpin
 */
public class Mango extends Condiment {

    private Beverage beverage;

    public Mango(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDesc() {
        //装饰
        return beverage.getDesc() + "，加芒果";
    }

    @Override
    public Double cost() {
        //装饰，加柠檬要多加3快
        return beverage.cost() + 3;
    }
}
