package com.ddf.datastructure.stack;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 基于栈实现一个简单的计算器
 *
 * 1. 前缀表达式(波兰表达式)
 *      前缀表达式又称波兰式，前缀表达式的运算符位于操作数之前
 *      举例说明： (3+4)×5-6 对应的前缀表达式就是 - × + 3 4 5 6
 *      前缀表达式的计算机求值
 *
 *      从右至左扫描表达式，遇到数字时，将数字压入堆栈，遇到运算符时，弹出栈顶的两个数，用运算符对它们做相应的计算（栈顶元素 和 次顶元素），
 *      并将结果入栈；重复上述过程直到表达式最左端，最后运算得出的值即为表达式的结果
 *
 *      例如: (3+4)×5-6 对应的前缀表达式就是 - × + 3 4 5 6 , 针对前缀表达式求值步骤如下:
 *
 *      从右至左扫描，将6、5、4、3压入堆栈
 *      遇到+运算符，因此弹出3和4（3为栈顶元素，4为次顶元素），计算出3+4的值，得7，再将7入栈
 *      接下来是×运算符，因此弹出7和5，计算出7×5=35，将35入栈
 *      最后是-运算符，计算出35-6的值，即29，由此得出最终结果
 *
 * 2. 中缀表达式
 *
 *      中缀表达式就是常见的运算表达式，如(3+4)×5-6
 *
 *      中缀表达式的求值是我们人最熟悉的，但是对计算机来说却不好操作(前面我们讲的案例就能看的这个问题)，因此，在计算结果时，
 *      往往会将中缀表达式转成其它表达式来操作(一般转成后缀表达式.)
 *
 * 3. 后缀表达式(逆波兰表达式)
 *
 *      后缀表达式又称逆波兰表达式,与前缀表达式相似，只是运算符位于操作数之后
 *
 *      中举例说明： (3+4)×5-6 对应的后缀表达式就是 3 4 + 5 × 6 –
 *
 *      后缀表达式的计算机求值
 *
 *      从左至右扫描表达式，遇到数字时，将数字压入堆栈，遇到运算符时，弹出栈顶的两个数，用运算符对它们做相应的计算（次顶元素 和 栈顶元素），并将结果入栈；重复上述过程直到表达式最右端，最后运算得出的值即为表达式的结果
 *
 *      例如: (3+4)×5-6 对应的后缀表达式就是 3 4 + 5 × 6 - , 针对后缀表达式求值步骤如下:
 *
 *      从左至右扫描，将3和4压入堆栈；
 *      遇到+运算符，因此弹出4和3（4为栈顶元素，3为次顶元素），计算出3+4的值，得7，再将7入栈；
 *      将5入栈；
 *      接下来是×运算符，因此弹出5和7，计算出7×5=35，将35入栈；
 *      将6入栈；
 *      最后是-运算符，计算出35-6的值，即29，由此得出最终结果
 *
 * @author dongfang.ding
 * @date 2019/6/17 11:55
 */
public class Calculator {


    public static void main(String[] args) {
        System.out.println(suffixExpression("3 4 + 5 * 6 -"));
    }

    /**
     * 使用栈来完成基于后缀表达式的简单计算器（加减乘除）
     */
    public static long suffixExpression(String expression) {
        Stack<Long> element = new Stack<>();
        // 简单点来，假设已经是后缀表达式，而且有空格
        List<String> list = Arrays.asList(expression.split(" "));
        if (!list.isEmpty()) {
            for (String str : list) {
                // 如果是数字就压入栈
                if (str.matches("\\d+")) {
                    element.add(Long.parseLong(str));
                } else {
                    // 如果是运算符就弹出两个元素然后根据运算符进行计算，计算完成后将结果压入栈
                    Long num1 = element.pop();
                    Long num2 = element.pop();
                    switch (str) {
                        case "+":
                            element.add(num2 + num1);
                            break;
                        case "-":
                            element.add(num2 - num1);
                            break;
                        case "*":
                            element.add(num2 * num1);
                            break;
                        case "/":
                            element.add(num2 / num1 + num2 % num1);
                            break;
                        default:
                            throw new RuntimeException("暂不支持的运算符");
                    }
                }
            }
            // 当计算完最后一个运算符，则吧最终结果压入栈，此时栈中只有这一个元素了，也就是最终的运算结果
            return element.pop();
        }
        return 0L;
    }
}
