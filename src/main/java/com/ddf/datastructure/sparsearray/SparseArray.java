package com.ddf.datastructure.sparsearray;

import java.util.Random;

/**
 * 稀疏数组
 *
 * 当一个数组中大部分元素为0，或者为同一个值的数组时，可以使用稀疏数组来保存该数组。
 *
 * 稀疏数组的处理方法是:
 * 记录数组一共有几行几列，有多少个不同的值
 * 把具有不同值的元素的行列及值记录在一个小规模的数组中，从而缩小程序的规模
 *
 *
 *
 * @author dongfang.ding
 * @date 2019/6/11 14:01
 */
public class SparseArray {

    public static void main(String[] args) {

        // 定义一个原始的二维数组，大小为10 * 10
        int[][] sourceArr = new int[10][11];

        // 给二维数组赋值，使有效（非零）的数组占少数部分，这样数组转换为稀疏数组才有意义
        Random random = new Random();
        sourceArr[0][0] = random.nextInt(10);
        sourceArr[1][2] = random.nextInt(10);
        sourceArr[3][3] = random.nextInt(10);
        sourceArr[8][9] = random.nextInt(10);

        System.out.println("====================原始数组========================");
        printArr(sourceArr);
        System.out.println("====================原始数组========================");


        System.out.println("====================稀疏数组数组========================");
        int[][] sparseArr = toSparse(sourceArr);
        printArr(sparseArr);
        System.out.println("====================稀疏数组数组========================");


        System.out.println("====================还原稀疏数组========================");
        int[][] toSourceArr = toSource(sparseArr);
        printArr(toSourceArr);
        System.out.println("====================还原稀疏数组========================");

    }

    /**
     * 将一个原始数组转换为稀疏数组
     * 稀疏数组的固定为三列，行号为原始数组有效数组的个数+1，因为第一行第一列用来存储原始数组的行数，第一行第二列存储原始数组的列数，
     * 第一行第三列用来存储原始数组的有效个数，从第二行开始记录有效数据在原始数组中的有效坐标以及值是多少，所以稀疏数组的大小为[原始数字有效值的个数][3]
     * @param sourceArr 原始数组
     */
    public static int[][] toSparse(int[][] sourceArr) {
        // 先统计原始数组的有效个数
        int validNum = 0;
        // 获得原始二维数组的行长
        int rowLength = sourceArr.length;
        // 获得原始二维数组的列长
        int colLength = 0;
        for (int[] ts : sourceArr) {
            for (int t : ts) {
                colLength = ts.length;
                if (t != 0) {
                    validNum++;
                }
            }
        }
        System.out.printf("原始二维数组的大小为[%d][%d]", rowLength, colLength);
        System.out.println();

        // 统计出来有效个数后就可以确定稀疏数组的大小
        int[][] sparseArr = new int[validNum + 1][3];
        // 稀疏数组的0行0列存储原始二维数组的长度
        sparseArr[0][0] = rowLength;
        // 稀疏二维数组的0行1列存储原始二维数组的列长
        sparseArr[0][1] = colLength;
        // 稀疏二维数组的0行2列存储原始二维数组的有效个数
        sparseArr[0][2] = validNum;


        // 再次遍历原始二维数组，将有效数据放入稀疏数组中
        // 稀疏数组的第[0]行用来存储上述数据了，所以有效数据从第[1]列开始存储
        int row = 1;
        for (int i = 0; i < rowLength; i ++) {
            for (int j = 0; j < colLength; j ++) {
                if (sourceArr[i][j] != 0) {
                    // 第一列存储原始数组有效数据的行号
                    sparseArr[row][0] = i;
                    // 第二列存储原始数组有效数据的列
                    sparseArr[row][1] = j;
                    // 第三列存储原始数组的值
                    sparseArr[row][2] = sourceArr[i][j];
                    // 稀疏数组没存储一个有效值需要一行三列，所以存储一个行号就要+1
                    row ++;
                }
            }
        }
        return sparseArr;
    }


    /**
     * 打印二维数组
     * @param sourceArr
     */
    public static void printArr(int[][] sourceArr) {
        for (int[] ints : sourceArr) {
            for (int item : ints) {
                System.out.printf("%d\t", item);
            }
            System.out.println();
        }
    }

    /**
     * 将稀疏数组还原成
     * @param sparseArr
     * @return
     */
    public static int[][] toSource(int[][] sparseArr) {
        // 先还原原数组大小, 稀疏数组的第一行第一列为原始数组的行，第一行第二列为原始数组的列
        int[][] sourceArr = new int[sparseArr[0][0]][sparseArr[0][1]];

        // 稀疏数组从第[1]行开始为原始二维数组有效数据
        for (int i = 1; i < sparseArr.length; i ++) {
            // sparseArr[i][0]原始数组的行坐标，sparseArr[i][1]原始数组的列坐标，sparseArr[i][2]原始数组的值
            sourceArr[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }
        return sourceArr;
    }
}
