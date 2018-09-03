package T20161227;

/**
 * Created by TomNg on 2016/12/27.
 */
public class BestTimeToBuyAndSellStock {

    /**
     * @param prices: Given an integer array
     * @return: Maximum profit
     */
    public int maxProfit(int[] prices) {
        if (prices.length == 0)
            return 0;
        int min = Integer.MIN_VALUE;
        int max = Integer.MAX_VALUE;
        for (int price : prices) {
            if (price < min) {
                min = price;
            }
            price = price - min;
            if (price > max) {
                max = price;
            }
        }
        return Integer.max(max - min, 0);
    }
}
