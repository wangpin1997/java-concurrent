1.桥梁模式

顶级父类：DrawApi，画图工具，有半径，长度，宽度参数
DrawApi的实现类，RedPen,GreenPen,BluePen 分别用不同的颜色画，继承自DrawApi
桥接抽象类 AbstractDraw，内部组合了一个DrawApi，且定义了个draw()方法
AbstractDraw的实现类，Circle，Rectangle，圆形，长方形，圆形在自己类中定义了半径
长方形在类中定义了长宽，不同的形状就只需要调用不同的类即可
然后通过main方法调用
```java
package cn.wpin.design.bridge;

public class Main {

    public static void main(String[] args) {
        //用绿笔画一个半径为10的圆
        AbstractDraw abstractDraw = new Circle(10, new GreenPen());
        //用红笔画一个长度10宽度为8的长方形
        AbstractDraw abstractDraw1 = new Rectangle(10, 8, new RedPen());
        abstractDraw.draw();
        abstractDraw1.draw();
    }
}
```