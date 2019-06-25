package com.ddf.datastructure.recursion;

/**
 * 迷宫问题
 *
 *
 * 约定
 * 1. 1用来代表墙体，代表路障，不通，2 代表最终找到的路径， 3 代表此路不通
 * 2. 先确定起始点路线和终止路线，从起点找到了终点，最终形成的路径即为最终的运动路径
 * 3. 最终通畅的路径和程序员设置的找路策略有关即：找路的上下左右的顺序相关，是先往上找，通了继续找，不通换个方向找，
 *      还是先往下找，不通换个方向然后在换
 *
 *
 * 迷宫表现如下
 * 1 1 1 1 1 1 1
 * 1 0 0 0 0 0 1
 * 1 0 0 0 0 0 1
 * 1 1 1 0 0 0 1
 * 1 0 0 0 0 0 1
 * 1 0 0 0 0 0 1
 * 1 0 0 0 0 0 1
 * 1 1 1 1 1 1 1
 *
 * @author dongfang.ding
 * @date 2019/6/17 15:50
 */
public class Maze {


    public static void main(String[] args) {
        int[][] maze = new int[8][7];
        for (int i = 0; i < 7; i++) {
            maze[0][i] = 1;
            maze[7][i] = 1;
        }

        for (int i = 0; i < 8; i++) {
            maze[i][0] = 1;
            maze[i][6] = 1;
        }

        // 手动设置除了围墙之外的路障
        maze[1][3] = 1;
        maze[2][5] = 1;
        maze[3][1] = 1;
        maze[3][2] = 1;
        maze[4][5] = 1;
        maze[5][4] = 1;


        for (int[] ints : maze) {
            for (int anInt : ints) {
                System.out.printf("%d\t", anInt);
            }
            System.out.println();
        }

        find(maze, 1, 1, 6, 5);

        System.out.println("=========================寻找路径之后============================");

        for (int[] ints : maze) {
            for (int anInt : ints) {
                System.out.printf("%d\t", anInt);
            }
            System.out.println();
        }
    }

    /**
     * 寻找路径的方法
     * @param map 迷宫地图，使用二维数组代替
     * @param startRow 起始点的行坐标,从0开始
     * @param startCell 起始点的列坐标，从0开始
     * @param endRow 终点的行坐标，从0开始
     * @param endCell 终点的列坐标，从0开始
     * @return
     */
    public static boolean find(int[][] map, int startRow, int startCell, int endRow, int endCell) {
        // 校验参数的合理性
        if (map == null || startRow < 0 || startRow > map.length - 1 || endRow < 0 || endRow > map.length - 1) {
            throw new IllegalArgumentException();
        }
        int cellNum = 0;
        for (int[] ints : map) {
            cellNum = ints.length;
            break;
        }
        if (startCell < 0 || startCell > cellNum - 1 || endCell < 0 || endCell > cellNum - 1) {
            throw new IllegalArgumentException();
        }

        // 2标识路径畅通，如果在终点路也畅通，则标识寻找路径完成，跳出回溯
        if (map[endRow][endCell] == 2) {
            return true;
        } else {
            // 如果坐标点的值为0，说明是没有走过的路，可以走
            if (map[startRow][startCell] == 0) {
                map[startRow][startCell] = 2;
                // 按照路径方向开始寻找,如果找到了返回true,则代表此路通常，否则返回false,返回false之后方法判断条件不成立，继续判断下一个else if
                // startRow - 1，往上找
                if (find(map, startRow - 1, startCell, endRow, endCell)) {
                    return true;
                }
                // startCell + 1 往右找
                else if (find(map, startRow, startCell + 1, endRow, endCell)) {
                    return true;
                }
                // startRow + 1， 往下找
                else if (find(map, startRow + 1, startCell, endRow, endCell)) {
                    return true;
                }
                // startCell -1 往左找
                else if (find(map, startRow, startCell - 1, endRow, endCell)) {
                    return true;
                }
                // 如果都找不到，说明找不到通往终点的路径
                else {
                    // 此路不通
                    map[startRow][startCell] = 3;
                    return false;
                }
            } else {
                // 如果不是0，只有1,2,3；而这都代表不需要再走或者路不通，所以直接return false即可
                return false;
            }
        }
    }
}
