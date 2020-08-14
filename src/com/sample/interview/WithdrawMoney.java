package com.sample.interview;

/**
 * Created by jiek on 2020/8/13.
 * <p>
 * Z国的货币系统包含面值1元、4元、16元、64元共计4种硬币，以及面值1024元的纸币。现在小Y使用1024元的纸币购买了一件价值为N的商品，请问最少他会收到多少硬币？
 * <p>
 * DP Dynamic Planning 动态规划
 * <ul>
 * <li>方式一：按最大面值先兑换，再将剩余的逐个减小面值进行兑换，各数相加即为总枚数。
 * 【可知每种面额找零数量。】
 * {效率最其次，时间复杂度 O(4),空间复杂度 O(1)}
 * <li>方式二：将找零数，按面额进行二进制移位，完成每种面额逐一兑换。
 * 【可知每种面额找零数量。】
 * {效率最高，时间复杂度 O(4),空间复杂度 O(1)}；
 * 缺点：无法满足非2的 N 次幂面值的兑换场景
 * <li>方式三：背包问题方案，按面额为步长，将前一步的数+1枚当前面额，即为当前找零数的找零总最小硬币数。
 * 【但此算法无法知道每种面额的找零数量。】
 * {效率最低，时间复杂度 O(4N),空间复杂度 O(N)}
 * </li>
 * </ul>
 */
public class WithdrawMoney {
    //货币面值种类，在此算法中必须按升序排列
    int moneyValues[] = {1, 4, 16, 64};
    //调试，打印日志
    boolean isDebug = true;

    public static void main(String[] args) throws Exception {
        System.out.println("Z国的货币系统包含面值1元、4元、16元、64元共计4种硬币，以及面值1024元的纸币。现在小Y使用1024元的纸币购买了一件价值为N的商品，请问最少他会收到多少硬币？");

//        支付单张面额
        int pay = 1 << 10;//1024
//        消费金额
        int spend = 1;

        WithdrawMoney withdrawMoney = new WithdrawMoney();


        withdrawMoney.pay(new PayMethod_1(pay, spend, withdrawMoney.moneyValues));//方式一：由大至小逐个币种兑换
        withdrawMoney.pay(new PayMethod_2(pay, spend, withdrawMoney.moneyValues));//方式二：二进制移位方式逐个币种兑换
        withdrawMoney.pay(new PayMethod_3(pay, spend, withdrawMoney.moneyValues));//方式三：背包问题方式求解

        System.out.println("---------------");

        /*long start = 0;
        for (int j = 0; j < 100; j++) {//测性能，进行反复实验次数循环
            start = System.currentTimeMillis();
            for (int i = 0; i <= pay; i++) {
                spend = i;
                withdrawMoney.pay(new PayMethod_1(pay, spend, withdrawMoney.moneyValues));//方式一：由大至小逐个币种兑换
//                withdrawMoney.pay(new PayMethod_2(pay, spend, withdrawMoney.moneyValues));//方式二：二进制移位方式逐个币种兑换
//                withdrawMoney.pay(new PayMethod_3(pay, spend, withdrawMoney.moneyValues));//方式三：背包问题方式求解
//                break;//
            }
            System.out.println("耗时：" + (System.currentTimeMillis() - start) + "\n");
        }*/
    }

    private int pay(IPayMoney payMoney) {
        if (payMoney != null) {
            try {
                long start = System.currentTimeMillis();

                if (isDebug) {
                    System.out.println("支付￥" + payMoney.getPay() + "元，共花掉 ￥" + payMoney.getSpend() + "元，问找回最少多少枚硬币？ " +
                            "方式：" + payMoney.getClass().getCanonicalName());
                }

                int pay = payMoney.pay();

                if (isDebug) {
                    System.out.println("方式 [" + payMoney.getClass().getCanonicalName() + "] 耗时："
                            + (System.currentTimeMillis() - start) + "\n");
                }
                return pay;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }


    /**
     * 购买物品，支付大面值货币，找回小面值货币，返回最少枚数
     */
    public interface IPayMoney {
        public int pay() throws Exception;

        /**
         * 获取支付金额
         *
         * @return
         */
        int getPay();

        /**
         * 获取消费金额
         *
         * @return
         */
        int getSpend();
    }

    static abstract class AbsPayMoney implements IPayMoney {
        int pay;
        int spend;
        int moneyValues[];

        /**
         * 构造支持找零算法
         *
         * @param pay         支付金额
         * @param spend       消费金额
         * @param moneyValues 货币面额种类
         */
        public AbsPayMoney(int pay, int spend, int[] moneyValues) {
            this.pay = pay;
            this.spend = spend;
            this.moneyValues = moneyValues;
        }

        @Override
        public int getPay() {
            return pay;
        }

        @Override
        public int getSpend() {
            return spend;
        }

        // the ranges of payment and expense that need to be detected 需要验证值域范围合法性
        protected void checkValue(int pay, int spend) throws Exception {
            if (pay < 0 || spend > pay || pay < 0)
                throw new Exception("值不合法：pay[" + pay + "] spend[" + spend + "]");
        }
    }

    /**
     * 方式三:背包问题方式
     */
    static class PayMethod_3 extends AbsPayMoney {
        public PayMethod_3(int pay, int spend, int[] moneyValues) {
            super(pay, spend, moneyValues);
        }

        /**
         * 方式三：背包问题方式
         * 能解决取值范围不满足2的N 次幂的规则场景。
         */
        @Override
        public int pay() throws Exception {
            checkValue(pay, spend);
            int remain = pay - spend;                            //购买后剩余的钱，找零总额

            int dp[] = new int[remain + 1];//创建找零+1个空间      //dp解法,类似背包问题，总的金额
            for (int i = 1; i <= remain; i++) {
                dp[i] = 0;//默认每个找零数的枚数设零
            }
            dp[0] = 0;                                           //初始为0
            for (int i = 0; i < moneyValues.length; i++) {
                for (int j = moneyValues[i]; j <= remain; j++) {
                    dp[j] = dp[j - moneyValues[i]] + 1;          //核心算法，dp下标为找零数，以当前面值数为步长，向前退一步长的值 +
                    // 当前面值硬币一枚。如当前找零为33时，即找33-16=17的找零数+1枚16元硬币，即是结果。
                }
            }
            System.out.println("找零[￥" + (pay - spend) + "]，共找回硬币最少枚数: " + dp[remain]);
            return dp[remain];
        }
    }

    /**
     * 方式二:二进制移位方式
     */
    static class PayMethod_2 extends AbsPayMoney {
        public PayMethod_2(int pay, int spend, int[] moneyValues) {
            super(pay, spend, moneyValues);
        }

        /**
         * 方式二：
         * 每一种硬币是1按位左移2的倍数次的数。
         * 所以此方式用位运算进行求解，性能略高于数值运算
         * <p>
         * 余额右移最大面值二进制幂位数后，就是最大面值的找零数量。
         * 如 1023 >> 2^6 (64元) 即为64元硬币找零个数
         * 依次处理即得结果。
         */
        @Override
        public int pay() throws Exception {

            checkValue(pay, spend);
//        找回的硬币数
            int coinCount = 0;
//        找回货币总额
            int remain = pay - spend;
            int count = 0;//当前面值找零枚数
            for (int i = moneyValues.length - 1; i >= 0; i--) {
                count = remain >> (2 * i);
                coinCount += count;
                remain = remain & ((1 << (2 * i)) - 1);
                System.out.println("找回 " + moneyValues[i] + " 元 " + count + " 枚");
                if (remain <= 0) {
                    break;
                }
            }
            System.out.println("找零[￥" + (pay - spend) + "]，共找回硬币最少枚数：" + coinCount);
            return coinCount;
        }
    }

    /**
     * 方式一：从大面额向小面额逐一兑换
     */
    static class PayMethod_1 extends AbsPayMoney {

        public PayMethod_1(int pay, int spend, int[] moneyValues) {
            super(pay, spend, moneyValues);
        }

        /**
         * 方式一：
         * 按最大可找硬币面值进行逐一找零。可用于任何面值数进行找零，如[0.1 0.5 1，2 5 10 20 50 100]面值进行找零
         * 找回剩余钱数除以可找最大面值硬币，即为当前最大面值硬币数。
         * 将所有面值找零数相加，即为最少找零硬币数。
         */
        @Override
        public int pay() throws Exception {
            checkValue(pay, spend);
//        找回的硬币数
            int coinCount = 0;
//        找回货币总额
            int remain = pay - spend;
            int count = 0;//当前面值找零枚数
            for (int i = moneyValues.length; i > 0; i--) {
                count = remain / moneyValues[i - 1];
                coinCount += count;
                remain = remain % moneyValues[i - 1];//大额找完后，剩余钱数
                System.out.println("找回 " + moneyValues[i - 1] + " 元 " + count + " 枚   ");
                if (remain <= 0) {
                    break;
                }
            }
            System.out.println("找零[￥" + (pay - spend) + "]，共找回硬币最少枚数：" + coinCount);
            return coinCount;
        }
    }
}
