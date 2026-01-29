/** An Integer tester created by Flik Enterprises. */
public class Flik {
    public static boolean isSameNumber(Integer a, Integer b) {
        return a == b;
    }
}
/*
  Java 对 -128 到 127 之间的 Integer 对象进行了缓存
  在这个范围内的 Integer.valueOf()（自动装箱时调用）会返回缓存的相同对象
  超出这个范围时，每次都会创建新的 Integer 对象
  == 比较的是对象引用，不是数值
 */