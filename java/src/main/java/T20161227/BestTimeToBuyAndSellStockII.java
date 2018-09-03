package T20161227;

/**
 * Created by TomNg on 2016/12/27.
 */
public class BestTimeToBuyAndSellStockII {

    /**
     * @param prices: Given an integer array
     * @return: Maximum profit
     */
    public int maxProfit(int[] prices) {
        if (prices.length == 0)
            return 0;
        int result = 0;
        boolean empty = true;
        int buy = 0;
        int last = prices.length - 1;
        for (int i = 0; i < last; i++) {
            if (empty) {
                if (prices[i] < prices[i + 1]) {
                    buy = prices[i];
                    empty = false;
                }
            } else {
                if (prices[i] > prices[i + 1]) {
                    result += prices[i] - buy;
                    empty = true;
                }
            }
        }
        if (!empty) {
            result += prices[last] - buy;
        }
        return result;
    }
}
