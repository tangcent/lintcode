class Solution:
    # @param {int[]} digits a number represented as an array of digits
    # @return {int[]} the result
    @staticmethod
    def plusOne(digits):
        index = len(digits) - 1
        digits[index] = digits[index] + 1
        while digits[index] == 10:
            digits[index] = 0
            if index > 0:
                index = index - 1
                digits[index] = digits[index] + 1
            else:
                digits.insert(0, 1)
                return digits
        return digits


if __name__ == '__main__':
    a = Solution()
    print(a.plusOne([1, 2, 3]))
    print(a.plusOne([9, 9, 9, 9, 9, 9]))
    print(a.plusOne([2, 2, 3, 9]))
