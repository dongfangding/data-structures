# data-structures

### 介绍
数据结构学习源码，学习过程中参考尚硅谷的《尚硅谷-韩顺平图解Java数据结构和算法》视频,
但视频中有些内容为了便于理解，存在一些硬编码以及一些个人觉得可以优化的点，因此该项目中源码与视频中源码有较大出入；
下面会统计每个具体不一样地方做一些说明；

### 内容

#### 1. 稀疏数组
稀疏数组源码中的转换与还原的过程中，视频中数组大小是按照定义时的硬编码来做的，因此做了修改，尽量将转换与还原抽象出来，
只传入原二维数组即可；本来想把数组做成泛型的，但语法不支持new 泛型；

`com.ddf.datastructure.sparsearray.SparseArray`
```java
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
 * 具体思路请参考下方代码实现
 *
 * 一个完整的二维数组转换为稀疏数组后的表现形式为：
 *
 * 0 0 0 0 0      (规则)    [原二维数组的行的长度] [原二维数组的列的长度] [原二维数组的有效数据（如int数组，非0即为有效）个数]
 * 1 0 0 0 2    ======>>    [原二维数组第一个有效元素的行角标] [原二维数组第一个有效元素的列角标] [原二维数组在这个坐标中的值]
 * 0 0 0 0 3
 *
 *   ||
 *   || 表现如下
 *  \||/
 *  5 3 3
 *  1 0 1
 *  1 4 2
 *  2 4 3
 *
 *  从上也可以看出，稀疏数组的行的大小由原二维数组有效数据的个数决定，所以如果原二维数组中的有效个数占比较大的话，使用稀疏数组，反而会增加大小
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

```

#### 2. 队列
##### 2.1 数组实现队列
原视频中实现的数组队列为了实现对数组的循环使用，算法过于复杂和难以理解，因此这部分参考了java自身实现的源码，写了更加精简版的实现

`com.ddf.datastructure.queue.ArrayQueueDemo`
```java
package com.ddf.datastructure.queue;

import java.util.NoSuchElementException;
import java.util.Random;

/**
 * 使用数组实现队列
 *
 * 队列的特点，先进先出
 * 队列在实际使用过程中会存在阻塞队列和非阻塞队列等的实现
 * 因为队列是有长度限制的，那么在插入元素的时候，如果此时数据已经存满了，当前这个存入元素的动作是抛出异常还是返回false，还是阻塞等待是有不同的
 * 实现和应用场景的
 * 相反的来说，取数据的时候也是，如果队列中的元素为空，此时取数据的动作是抛出异常还是返回null，还是阻塞等待直到队列中有数据也是有不同的实现的
 * 但归根到底，他们对数据的存取形式都是先进先出
 *
 * 添加元素的主要逻辑：
 *      1. 将元素加入到当前数组的添加数据角标位置，然后将角标后移一位，为下次加入数据做好处理；每次成功加入数据，队列大小+1，
 *      2. 最后需要判断，如果加入元素后下一次的角标会越界，那么就要将角标重新置为0，这样可以循环使用数组；
 *
 * 弹出元素的主要逻辑
 *      1.取出代表当前取元素的角标位置的数据后，要将当前角标元素置空，然后需要判断下一次取元素的角标是否已到达数组长度临界上限，比如数组长度为4，
 *          则，当前取到了[3]的位置，那么下一次就要取[4]的位置，然而数组长度一共就四个长度，直接使用会越界，为了循环使用，所以这个时候需要
 *          把下一次取元素的角标置为0
 *
 * @author dongfang.ding
 * @date 2019/6/11 17:02
 */
public class ArrayQueueDemo {

    public static void main(String[] args) {
        ArrayQueue<Integer> queue = new ArrayQueue<>(3);
        Random random = new Random();
        System.out.println("==================正常存取==============");
        queue.add(random.nextInt(1000));
        queue.add(random.nextInt(1000));
        queue.add(random.nextInt(1000));
        System.out.println(queue);
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println("==================正常存取==============");
        System.out.println();
        System.out.println("==================取出与弹出交互==============");
        queue.add(random.nextInt(1000));
        queue.add(random.nextInt(1000));
        queue.add(random.nextInt(1000));
        System.out.println(queue);
        System.out.println(queue.remove());
        queue.add(random.nextInt(1000));
        System.out.println(queue);
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        queue.add(random.nextInt(1000));
        System.out.println(queue);
        System.out.println(queue.remove());
        System.out.println("==================取出与弹出交互==============");
        System.out.println();
        System.out.println("==================不报异常的存取==============");
        System.out.println("添加结果: " + queue.offer(random.nextInt(1000)));
        System.out.println("添加结果: " + queue.offer(random.nextInt(1000)));
        System.out.println("添加结果: " + queue.offer(random.nextInt(1000)));
        System.out.println("添加结果: " + queue.offer(random.nextInt(1000)));
        System.out.println("取出结果: " + queue.get());
        System.out.println("取出结果: " + queue.get());
        System.out.println("取出结果: " + queue.get());
        System.out.println("取出结果: " + queue.get());
        System.out.println("==================不报异常的存取==============");
    }
}


/**
 * 数组实现队列的类,非线程安全。这里参考了源码的部分含义，但是没有考虑复杂情况，只是一个简易版的实现
 */
class ArrayQueue<E> {
    /** 存放队列元素的数组 */
    private final Object[] items;

    /** 取队列数据的角标 */
    private int takeIndex;

    /** 存入队列时的角标 */
    private int putIndex;

    /** 队列中元素的大小 */
    private int count;

    public ArrayQueue(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException();
        }
        this.items = new Object[maxSize];
    }

    /**
     * 直接添加，如果队列已满则抛出异常
     * 1. 添加队列的时候如果当前队列中的值已达到数组上限，则抛出异常
     * 2. 将元素加入到当前数组的角标位置，然后将角标后移一位，为下次加入数据做好处理；每次成功加入数据，队列大小+1，
     * 3. 最后需要判断，如果加入元素后下一次的角标会越界，那么就要将角标重新置为0，这样可以循环使用数据；
     * @param e
     */
    public boolean add(E e) {
        // 如果队列已满，则抛出异常
        if (count == items.length) {
            throw new IllegalStateException("Queue full");
        }
        return enqueue(e);
    }


    /**
     * 将元素加入队列，与add不同的是，当队列满时不会抛出异常，而是返回false标识添加失败
     */
    public boolean offer(E e) {
        if (count == items.length) {
            return false;
        }
        return enqueue(e);
    }


    /**
     * 从队列中取出元素，如果没有元素，则返回null
     * @return
     */
    public E get() {
        if (items[takeIndex] == null) {
            return null;
        }
        return dequeue();
    }


    /**
     * 将元素加入到队列中
     * 1. 将元素加入到当前数组的添加数据角标位置，然后将角标后移一位，为下次加入数据做好处理；每次成功加入数据，队列大小+1，
     * 2. 最后需要判断，如果加入元素后下一次的角标会越界，那么就要将角标重新置为0，这样可以循环使用数组；
     * @param e
     */
    private boolean enqueue(E e) {
        // 将元素加入指定角标，然后角标后移一位
        items[putIndex++] = e;
        // 队列大小+1
        count ++;
        // 判断放入元素的角标是否已达到数组的最后一位，如果是最后一位则将放入元素的角标置为0，
        // 避免数组越界，以及可以重复利用数组，达到循环使用的目的
        if (putIndex == items.length) {
            putIndex = 0;
        }
        return true;
    }

    /**
     * 将元素从队列中弹出，先进先出
     * 取出代表当前取元素的角标位置的数据后，要将当前角标元素置空，然后需要判断下一次取元素的角标是否已到达数组长度临界上限，比如数组长度为4，
     * 则，当前取到了[3]的位置，那么下一次就要取[4]的位置，然而数组长度一共就四个长度，直接使用会越界，为了循环使用，所以这个时候需要
     * 把下一次取元素的角标置为0
     * @return
     */
    private E dequeue() {
        // 取出元素
        @SuppressWarnings("unchecked")
        E e = (E) items[takeIndex];
        // 将原位置的数据清空
        items[takeIndex] = null;
        // 判断下次取数据时的角标是否会越界，如果越界，重新指向[0]角标，形成对数组的环形复用
        if (++takeIndex == items.length) {
            takeIndex = 0;
        }
        // 队列大小-1
        count --;
        return e;
    }

    /**
     * 返回队列元素大小
     * @return
     */
    public int size() {
        return count;
    }

    /**
     * 取出队列的头部元素，如果元素为空，则抛出异常
     */
    public E remove() {
        if (items[takeIndex] == null) {
            throw new NoSuchElementException();
        }
        return dequeue();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Object item : items) {
            str.append(item).append(" ");
        }
        return str.toString();
    }
}

```


#### 3. 链表
原视频中实现链表的时候头部元素为自己硬编码定义了一个空，会预留一个空的节点，因此没有采用这种实现方式；
参考了java源码对链表的实现，只写了几个简单的方法

`com.ddf.datastructure.linkedlist.LinkedListDemo`

```java
package com.ddf.datastructure.linkedlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 双向链表
 *
 * 链表的数据结构是由一个一个的节点组成，节点包含数据本身以及头节点和尾节点，头节点指向当前节点的上个元素，
 * 而尾节点指向当前元素的下一个节点，节点与节点之间通过头节点和尾节点的指针来串联起来；
 * 而如果一个节点中既维护了上个节点又维护了下个节点的数据，那么这个链表即为双向链表，如果只维护了尾链表，那么则为单向链表；
 * 所以从这也可以看出，链表是不支持快速访问的，但是如果需要在中间位置插入数据，只需要修改原来的两个节点即可；
 *
 * @author dongfang.ding
 * @date 2019/6/12 14:58
 */
public class LinkedListDemo {
    public static void main(String[] args) {
        SimpleLinkedList<Integer> linkedList = new SimpleLinkedList<>();
        Random random = new Random();
        linkedList.add(random.nextInt(5000));
        linkedList.add(random.nextInt(5000));
        linkedList.add(random.nextInt(5000));
        linkedList.add(random.nextInt(5000));
        System.out.println(linkedList);

        System.out.println("获得第一个元素: " + linkedList.getFirst());
        System.out.println("获得最后一个元素: " + linkedList.getLast());
        System.out.println("获得指定角标的元素0: [" + linkedList.get(0) + "]");
        System.out.println("获得指定角标的元素1: [" + linkedList.get(1) + "]");
        System.out.println("获得指定角标的元素2: [" + linkedList.get(2) + "]");
        System.out.println("获得指定角标的元素3: [" + linkedList.get(3) + "]");

    }
}

/**
 * 实现一个简单的双向链表
 */
class SimpleLinkedList<E> {

    /** 链表中的第一个节点对象,这样可以记录下来头部，方便知道从哪个节点开始遍历节点 */
    private Node<E> first;

    /** 链表中的最后一个节点对象 */
    private Node<E> last;

    private int size;


    public boolean add(E e) {
        // 每次插入新元素，新元素的头节点都指向上一次链表中的最后一个节点
        Node<E> newNode = new Node<>(last, e, null);
        // 如果头节点为空，则说明当前插入元素为头节点
        if (first == null) {
            first = newNode;
        } else {
            // 如果头节点不为空，说明链表中已有数据，则最后一个节点的next要指向新的最后一个节点
            last.next = newNode;
        }
        // 将新插入的节点指向最后一个节点
        last = newNode;
        size ++;
        return true;
    }

    /**
     * 获得指定插入顺序的元素，
     * 链表与数组这里的区别就很明显了，数组可以直接通过角标访问，而链表只能通过头节点遍历指定的index的次数;
     * @param index
     * @return
     */
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        /* 如下这种写法应当是比较容易理解的，从头节点开始循环，取第几个角标，则其实就是从头节点循环几次
        Node<E> node = first;
        for (int i = 0; i < index; i ++) {
            node = node.next;
        }
        */
        // 看了java自己实现的链表发现，上述写法其实有个优化点，如果链表过长的话，比如长度为18，而取角标16，从头开始循环需要循环16次，
        // 而如果我们从尾部开始循环的话，则只需要循环两次;所以可以简单的判断下当前角标在中间元素的左边还是右边，如果是左边，则从头
        // 开始循环，而如果在右边，则从尾部循环；

        // 首先判断当前角标位于二分的左边还是右边
        if (index < (size >> 1)) {
            // 如果角标小于一半，则从头开始循环
            Node<E> node = first;
            for (int i = 0; i < index; i ++) {
                node = node.next;
            }
            return node.item;
        } else {
            // 如果角标大于总长度的一半，则从尾部开始循环
            Node<E> node = last;
            for (int i = size - 1; i > index; i --) {
                node = node.pre;
            }
            return node.item;
        }
    }

    /**
     * 方便打印，重写了toString方法
     * @return
     */
    @Override
    public String toString() {
        List<E> list = new ArrayList<>();
        if (first == null) {
            return null;
        }
        Node<E> node = first;
        while (true) {
            list.add(node.item);
            if (node.next == null) {
                break;
            }
            node = node.next;
        }
        return list.toString();
    }

    /**
     * 节点对象
     * @param <E>
     */
    class Node<E> {
        /** 当前节点的上个节点的数据 */
        Node<E> pre;
        /** 当前节点数据 */
        E item;
        /** 当前节点的下个节点的数据 */
        Node<E> next;

        public Node(Node<E> preNode, E item, Node<E> next) {
            this.pre = preNode;
            this.item = item;
            this.next = next;
        }
    }

    public E getFirst() {
        return first.item;
    }

    public E getLast() {
        return last.item;
    }

    public int getSize() {
        return size;
    }


}
```