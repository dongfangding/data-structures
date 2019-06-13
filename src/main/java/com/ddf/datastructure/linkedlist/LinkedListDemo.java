package com.ddf.datastructure.linkedlist;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
        int temp = random.nextInt(5000);
        System.out.println("向[0]位加入元素： [" + temp + "]");
        linkedList.add(0, temp);
        temp = random.nextInt(5000);
        System.out.println("向[3]位加入元素： [" + temp + "]");
        linkedList.add(3, temp);
        System.out.println("加入后链表元素");
        System.out.println(linkedList);

        System.out.println("移除头元素: [" + linkedList.remove() + "]");
        System.out.println(linkedList);
        System.out.println("移除头元素: [" + linkedList.remove() + "]");
        System.out.println(linkedList);
        System.out.println("移除头元素: [" + linkedList.remove() + "]");
        System.out.println(linkedList);
        System.out.println("移除头元素: [" + linkedList.remove() + "]");
        System.out.println(linkedList);
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


    public boolean add(int index, E e) {
        if (index == size) {
            return add(e);
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        Node<E> node = node(index);
        Node<E> oldPre = node.pre;
        Node<E> newNode = new Node<>(oldPre, e, node);
        if (oldPre == null) {
            first = newNode;
        } else {
            oldPre.next = newNode;
        }
        node.pre = newNode;
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

        //
        return node(index).item;
    }

    /**
     * 获得指定位置的节点
     * @param index
     * @return
     */
    private Node<E> node(int index) {
        // 首先判断当前角标位于二分的左边还是右边
        if (index < (size >> 1)) {
            // 如果角标小于一半，则从头开始循环
            Node<E> node = first;
            for (int i = 0; i < index; i ++) {
                node = node.next;
            }
            return node;
        } else {
            // 如果角标大于总长度的一半，则从尾部开始循环
            Node<E> node = last;
            for (int i = size - 1; i > index; i --) {
                node = node.pre;
            }
            return node;
        }
    }

    /**
     * 移除头节点
     */
    public E remove() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        final Node<E> node = first;
        Node<E> oldNext = node.next;
        if (oldNext != null) {
            first = oldNext;
            oldNext.pre = null;
        } else {
            first = null;
        }
        size --;
        return node.item;
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
