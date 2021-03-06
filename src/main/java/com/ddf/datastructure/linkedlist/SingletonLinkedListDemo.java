package com.ddf.datastructure.linkedlist;

import java.util.*;

/**
 * 单向链表的实现
 *
 * @author dongfang.ding
 * @date 2019/6/13 14:24
 */
public class SingletonLinkedListDemo {

    public static void main(String[] args) {
        SingletonLinkedList<Integer> linkedList = new SingletonLinkedList<>();
        Random random = new Random();
        linkedList.add(random.nextInt(1000));
        linkedList.add(random.nextInt(1000));
        linkedList.add(random.nextInt(1000));
        System.out.println("===========================测试链表基础功能========================");
        System.out.println("加入前链表内容： " + linkedList.toList());
        int temp = random .nextInt(1000);
        System.out.println("向[0]位加入元素： [" + temp + "]");
        linkedList.add(0, temp);
        System.out.println(linkedList.toList());
        System.out.println("当前链表大小： " + linkedList.size());
        temp = random.nextInt(1000);
        System.out.println("向[2]位加入元素： [" + temp + "]");
        linkedList.add(2, temp);
        System.out.println("last: " + linkedList.getLast());
        System.out.println(linkedList.toList());
        temp = random.nextInt(1000);
        System.out.println("向[4]位加入元素： [" + temp + "]");
        linkedList.add(4, temp);
        System.out.println(linkedList.toList());


        linkedList.add(random.nextInt(1000));
        linkedList.add(random.nextInt(1000));
        linkedList.add(random.nextInt(1000));
        linkedList.add(random.nextInt(1000));
        System.out.println(linkedList.toList());
        System.out.println("链表大小： " + linkedList.size());
        System.out.println("链表第一个元素: " + linkedList.getFirst());
        System.out.println("链表最后一个元素: " + linkedList.getLast());
        System.out.println("从链表中弹出元素: " + linkedList.removeFirst());
        System.out.println("从链表中弹出元素: " + linkedList.removeFirst());
        System.out.println(linkedList.toList());
        System.out.println("链表大小： " + linkedList.size());
        System.out.println("链表第一个元素: " + linkedList.getFirst());
        System.out.println("链表最后一个元素: " + linkedList.getLast());
        System.out.println("===========================测试链表基础功能========================");

        System.out.println();
        System.out.println("=====================测试链表反转=========================");
        SingletonLinkedList<String> convert = new SingletonLinkedList<>();
        convert.add("aaa");
        convert.add("bbb");
        convert.add("ccc");
        SingletonLinkedList<String> convert1 = convert.convert();
        System.out.println("原链表： " + convert.toList());
        System.out.println("反转后: " + convert1.toList());
        System.out.println("=====================测试链表反转=========================");
    }
}


class SingletonLinkedList<E> {

    /** 链表的第一个元素，默认为空，当加入第一个元素的时候first指向该元素 */
    private Node<E> first;
    /** 链表的最后一个元素，默认为空，每次加入元素last都指向新加入的元素，当第一次添加时first和last都指向该元素 */
    private Node<E> last;
    /** 链表中的元素个数 */
    private int size;

    /**
     * 向链表中加入元素
     * @param e
     * @return
     */
    public boolean add(E e) {
        // 加入的新元素为最后一个元素，所以next为null
        Node<E> newNode = new Node<>(e, null);
        // 第一次添加，first和last同时指向新节点
        if (linkToFirst(newNode)) {
            return true;
        }
        // 获得链表之前的最后一个元素
        Node<E> oldLast = last;
        // 如果链表之前的最后一个元素不为空，那么将之前的元素的next指向最新加入的元素
        if (oldLast != null) {
            oldLast.next = newNode;
        }
        // 将last指向最新加入的元素，为链表的最后一个元素
        last = newNode;
        size ++;
        return true;
    }

    private boolean linkToFirst(Node<E> node) {
        // 第一次添加，first和last同时指向新节点
        if (first == null) {
            first = node;
            last = node;
            size ++;
            return true;
        }
        return false;
    }

    /**
     * 向指定位置加入一个元素
     * @param index
     * @param e
     * @return
     */
    public boolean add(int index, E e) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        // 加入的新元素为最后一个元素，所以next为null
        Node<E> newNode = new Node<>(e, null);
        // 第一次添加，first和last同时指向新节点
        if (linkToFirst(newNode)) {
            return true;
        }
        Node<E> node = first;
        // 找到要插入位置的前一个元素，因为只维护了next属性，所以要找到前一个元素，将前一个元素的next指向新加节点
        for (int i = 0 ; i < index - 1; i ++) {
            node = node.next;
        }
        if (index == 0) {
            // 如果是加入到第一个角标，那么新元素的next就指向找到的节点
            newNode.next = node;
            // 将第一个节点指向新加入的元素
            first = newNode;
        } else {
            // 如果不是加入到第一个节点，那么新元素的next要指向原来元素的next
            newNode.next = node.next;
            // 找到元素的next指向最新的元素，这样就吧新元素加入到之前链表里找到的元素和该元素的下一个元素之间了
            node.next = newNode;
        }
        // 如果将元素加入了最后，则将last指向新的元素
        if (index == size) {
            last = newNode;
        }
        size ++;
        return true;
    }


    /**
     * 获得链表的最后一个元素，之后弹出该元素, 从头节点开始弹出元素,因为单向链表只维护了一个节点，在添加元素的时候就已经决定了
     * 是从头部开始循环还是尾部还是循环
     * @return
     */
    public E removeFirst() {
        if (size < 1) {
            throw new NoSuchElementException();
        }
        Node<E> node = first;
        Node<E> oldNext = node.next;
        if (oldNext == null) {
            // 如果头节点没有next，说明只有一个头节点，要把链表的first和last赋空
            first = null;
            last = null;
        } else {
            // 将最新头节点指向原头节点的next
            first = oldNext;
        }
        size --;
        return node.item;
    }


    /**
     * 翻转链表元素内容
     * 不知道有没有更好的方法，减少循环次数
     * @return
     */
    public SingletonLinkedList<E> convert() {

        if (size == 0) {
            return null;
        }

        // 如果只有一个元素，不存在反转，直接返回自身即可
        if (size == 1) {
            return this;
        }

        // 找到last前面的一个元素
        SingletonLinkedList<E> rtn = new SingletonLinkedList<>();
        int lastIndex = size;
        while (true) {
            Node<E> node = first;
            // 每次都循环到链表的最后一个元素，因为从first开始循环，所以初始值为1,如果三个元素，从first开始找，只要循环两次node.next即可
            for (int i = 1; i < lastIndex; i ++) {
                node = node.next;
            }
            rtn.add(node.item);
            // 找到一个之后，下次循环取的最后一个元素就是本次的上个节点，所以要少一次循环，以此类推，直到循环到first
            lastIndex --;
            if (lastIndex == 0) {
                break;
            }
        }
        return rtn;
    }



    class Node<E> {
        E item;
        Node<E> next;
        Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }
    }

    public List<E> toList() {
        List<E> list = new ArrayList<>();
        Node<E> node = first;
        while (true) {
            if (node == null) {
                return Collections.emptyList();
            }
            list.add(node.item);
            if (node.next == null) {
                break;
            }
            node = node.next;
        }
        return list;
    }

    public int size() {
        return size;
    }

    public E getFirst() {
        return first.item;
    }

    public E getLast() {
        return last.item;
    }
}
