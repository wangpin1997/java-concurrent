package cn.wpin.design.deck;

/**
 * 测试装饰模式
 * @author wangpin
 */
public class Main {

    public static void main(String[] args) {
        //点一杯咖啡
        Beverage beverage =new Coffee();
        //先加一份芒果
        beverage=new Mango(beverage);
        //再加一杯柠檬
        beverage=new Lemon(beverage);
        System.out.println(beverage.getDesc()+"  :  "+beverage.cost());
    }
}
