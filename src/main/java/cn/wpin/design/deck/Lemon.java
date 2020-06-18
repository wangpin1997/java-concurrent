package cn.wpin.design.deck;

/**
 * 柠檬
 *
 * @author wangpin
 */
public class Lemon extends Condiment {

    private Beverage beverage;

    public Lemon(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDesc() {
        //装饰
        return beverage.getDesc() + "，加柠檬";
    }

    @Override
    public Double cost() {
        //装饰，加柠檬要多加2快
        return beverage.cost() + 2;
    }
}
