package com.ddf.datastructure.linkedlist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 单向环形链表
 * <p>
 * 在单向链表的基础上，将链表的最后一个元素的next指向first，将整个链表元素形成一个闭环
 *
 *
 * 约瑟夫(Josephu)问题(丢手帕问题)
 *
 * 问题为：设编号为1，2，… n的n个人围坐一圈，约定编号为k（1<=k<=n）的人从1开始报数，数到m 的那个人出列，它的下一位又从1开始报数，数到m的那个人又出列，依次类推，直到所有人出列为止，由此产生一个出队编号的序列。
 *
 * 提示：用一个不带头结点的循环链表来处理Josephu
 * 问题：先构成一个有n个结点的单循环链表（单向环形链表），然后由k结点起从1开始计数，计到m时，对应结点从链表中删除，然后再从被删除结点的下一个结点又从1开始计数，直到最后一个结点从链表中删除算法结束。
 *
 * @author dongfang.ding
 * @date 2019/6/14 9:17
 */
public class CircleSingletonLinkedListDemo {

    public static void main(String[] args) {
        CircleSingletonLinkedList<String> list = new CircleSingletonLinkedList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.add("ddd");
        list.add("eee");
        System.out.println(list.toList());
        List<String> removeItemList = new ArrayList<>();
        // 构建一个单项链表环，从头节点开始数，每次计数到3，就删除当前节点，然后从被删除节点的下个节点再计数到3，再删除节点，直到所有节点都被删除
        list.remove(1, 3, removeItemList);
        System.out.println(removeItemList);

        System.out.println("==================================");
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.add("ddd");
        list.add("eee");
        while (list.getSize() > 0) {
            // 与上述不同的是，这个方法的实现是每当删除一个节点后返回数据，下次重新从头节点开始计数删除节点
            System.out.println(list.remove(1, 3));
        }
    }
}


/**
 * 构建单向环形链表
 * newNode
 */
class CircleSingletonLinkedList<E> {

    /**
     * 链表中的第一个元素
     */
    private Node<E> first;

    /**
     * 链表中的最后一个元素
     */
    private Node<E> last;

    /**
     * 链表中的元素个数
     */
    private int size;


    public boolean add(E e) {
        // 与单向链表不同的是，单向环形链表每次添加数据的next都指向first
        Node<E> newNode = new Node<>(e, first);
        if (first == null) {
            first = newNode;
            // 第一次添加first为空，所以需要重新给新节点的next指向first
        } else {
            Node<E> oldLast = last;
            oldLast.next = newNode;
        }
        size ++;
        last = newNode;
        newNode.next = first;
        return true;
    }

    /**
     * 用以解决约瑟夫问题的弹出，指定一个起始的角标，startIndex，每当循环num次，则弹出当前节点，然后重新构建环形链表；
     * 该方法每次弹出的时候，下次弹出队列，都是从头节点开始重新数的；
     * 该方法只弹提供弹出节点的实现，如果想要把节点全部弹出来，自己去while 循环链表的长度，一致调用该方法即可；
     * <pre>
     *     while (list.getSize() > 0) {
     *         System.out.println(list.remove(1, 4));
     *     }
     * </pre>
     *
     * @param startIndex startIndex从1开始为有效值
     * @param num
     */
    public E remove(int startIndex, int num) {
        // 允许num大于size,找一个轮回后重新开始找, 也允许startIndex > size，否则在startIndex不为1的情况下，循环删除最后只剩一个节点的时候
        // 会无法经过校验
        if (startIndex < 1 || num < 1) {
            throw new IllegalArgumentException();
        }
        Node<E> removeNode;
        if (size == 1) {
            removeNode = first;
        } else {
            // 从第一个节点开始循环确定起始节点位置
            Node<E> node = first;
            for (int i = 0; i < startIndex - 1; i ++) {
                // 循环startIndex次获得起始节点位置
                node = node.next;
            }

            // 用以计算从startIndex开始数num的辅助变量，每次数一次就加1，如果等于链表长度，从1开始重新数
            int temp = startIndex;
            // 把i的初始值设为1，少循环一次的原因是节点本身也算num的一部分，自己数一次，如果输2次，其实指针只要往后移动1位
            // i 的终止条件 （num - 1）少循环一次的愿意是是要找到要删除元素的前一个位置，单向链表只维护了next，所以找到要删除元素的前一个位置，
            // 才能将这个位置的元素指向要删除元素的next，这样才能把环重新连接起来;
            for (int i = 1; i < num - 1; i ++) {
                node = node.next;
                temp ++;
                if (temp == size) {
                    temp = 0;
                }
            }
            // 找到节点的next才是本次要删除的节点
            removeNode = node.next;
            // 获得要删除节点的下一个节点
            Node<E> nextNode = node.next.next;
            // 将找到节点的next指向删除节点的下个节点，重新完成闭环
            node.next = nextNode;
            // 如果删除的节点正好是first，重新给first赋值
            if (removeNode == first) {
                first = nextNode;
            }
        }
        size --;
        // 如果节点删除完了，要把first和last清空，整个链表就空了
        if (size == 0) {
            first = null;
            last = null;
        }
        return removeNode.item;
    }

    /**
     * 用以解决约瑟夫问题的弹出，指定一个起始的角标，startIndex，每当循环num次，则弹出当前节点，然后重新构建环形链表；
     * 与上面不同的是，该方法每次弹出一个元素后，下次重新计数是从被删除节点的下一个节点开始重新数的；
     * 该方法只弹提供弹出节点的实现，如果想要把节点全部弹出来，自己去while 循环链表的长度，一致调用该方法即可；
     * </pre>
     *
     * @param startIndex startIndex从1开始为有效值
     * @param num
     * @param removeItemList
     */
    public List<E> remove(int startIndex, int num, List<E> removeItemList) {
        // 允许num大于size,找一个轮回后重新开始找, 也允许startIndex > size，否则在startIndex不为1的情况下，循环删除最后只剩一个节点的时候
        // 会无法经过校验
        if (startIndex < 1 || num < 1 || removeItemList == null) {
            throw new IllegalArgumentException();
        }
        Node<E> removeNode;
        // 用以计算从startIndex开始数num的辅助变量，每次数一次就加1，如果等于链表长度，从1开始重新数
        if (size == 0) {
            return removeItemList;
        }
        int temp = startIndex;
        if (size == 1) {
            removeNode = first;
        } else {
            // 从第一个节点开始循环确定起始节点位置
            Node<E> node = first;

            // 重新定位从哪个节点开始计数
            for (int i = 0; i < startIndex - 1; i ++) {
                node = node.next;
            }

            // 把i的初始值设为1，少循环一次的原因是节点本身也算num的一部分，自己数一次，如果输2次，其实指针只要往后移动1位
            // i 的终止条件 （num - 1）少循环一次的愿意是是要找到要删除元素的前一个位置，单向链表只维护了next，所以找到要删除元素的前一个位置，
            // 才能将这个位置的元素指向要删除元素的next，这样才能把环重新连接起来;
            for (int i = 1; i < num - 1; i ++) {
                node = node.next;
                temp ++;
                if (temp >= size) {
                    temp = 0;
                }
            }
            // 找到节点的next才是本次要删除的节点
            removeNode = node.next;
            // 获得要删除节点的下一个节点
            Node<E> nextNode = node.next.next;
            // 将找到节点的next指向删除节点的下个节点，重新完成闭环
            node.next = nextNode;
            // 如果删除的节点正好是first，重新给first赋值
            if (removeNode == first) {
                first = nextNode;
            }
        }
        size --;
        // 如果节点删除完了，要把first和last清空，整个链表就空了
        if (size == 0) {
            first = null;
            last = null;
        }
        removeItemList.add(removeNode.item);
        remove(temp + 1, num, removeItemList);
        return removeItemList;
    }

    /**
     * 链表节点
     *
     * @param <E>
     */
    class Node<E> {
        E item;
        Node<E> next;

        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }
    }

    public List<E> toList() {
        if (size == 0) {
            return Collections.emptyList();
        }
        Node<E> node = first;
        List<E> list = new ArrayList<>();
        while (true) {
            list.add(node.item);
            if (node.next == first) {
                break;
            }
            node = node.next;
        }
        return list;
    }

    public Node<E> getFirst() {
        return first;
    }

    public Node<E> getLast() {
        return last;
    }

    public int getSize() {
        return size;
    }
}