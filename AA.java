
/**
 * 静态块与静态成员依次执行于 classloader 之后
 *
 * 以下代码执行结果：
 *
 * 构造块1
 * 构造块2
 * 静态块3
 * 构造块1
 * 构造块2
 * 333
 * 构造块1
 * 构造块2
 * 444
 */

public class AA
{
    public static void main(String[] args)
    {
        System.out.println("333");
//        AA t =
                new AA();
        System.out.println("444");
    }
    public static AA t1 = new AA();

    public AA(){
        System.out.println("构造块2");
    }
    static
    {
        System.out.println("静态块3");
    }
    {
        System.out.println("构造块1");
    }
    public static AA t2 = new AA();
}