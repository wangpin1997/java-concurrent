package cn.wpin.design.deck;

/**
 * 饮料基类
 *
 * @author wangpin
 */
public abstract class Beverage {

    /**
     * 返回描述
     *
     * @return
     */
    public abstract String getDesc();

    /**
     * 返回价格
     *
     * @return
     */
    public abstract Double cost();
}
