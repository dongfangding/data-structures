package com.ddf.datastructure.stack;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

/**
 * 栈
 * 栈的英文为(stack)
 * 栈是一个先入后出(FILO-First In Last Out)的有序列表。
 * 栈(stack)是限制线性表中元素的插入和删除只能在线性表的同一端进行的一种特殊线性表。允许插入和删除的一端，为变化的一端，称为栈顶(Top)，另一端为固定的一端，称为栈底(Bottom)。
 * 根据栈的定义可知，最先放入栈中元素在栈底，最后放入的元素在栈顶，而删除元素刚好相反，最后放入的元素最先删除，最先放入的元素最后删除
 *
 * @author dongfang.ding
 * @date 2019/6/14 16:36
 */
public class ArrayStackDemo {
    public static void main(String[] args) {
        ArrayStack<String> stack = new ArrayStack<>(4);
        stack.push("aaa");
        stack.push("bbb");
        stack.push("ccc");
        stack.push("ddd");
        System.out.println(stack);
        System.out.println("弹出元素： [" + stack.pop() + "]");
        System.out.println("弹出元素： [" + stack.pop() + "]");
        System.out.println("弹出元素： [" + stack.pop() + "]");
        System.out.println("弹出元素： [" + stack.pop() + "]");
    }

}


/**
 * 通过数组来实现一个简单的栈, 数组需要指定大小，不提供扩容功能，非线程安全；
 *
 * java自己实现的栈是继承线程安全的集合类Vector，所以是线程安全的；这里自己写的只是简单说明栈的构成
 *
 * 如果基于链表实现，会比较麻烦，由于是先进后出，还需要反转链表
 *
 * @param <E>
 */
class ArrayStack<E> {

    /**
     * 栈中元素个数
     */
    private int size;

    private Object[] items;

    /**
     * 需要指定大小来构造栈，不提供扩容功能
     *
     * @param capacity
     */
    ArrayStack(int capacity) {
        items = new Object[capacity];
    }

    /**
     * 新加元素放在栈顶
     * @param e
     * @return
     */
    public boolean push(E e) {
        if (size == items.length) {
            throw new IllegalStateException("Stack full");
        }
        items[size] = e;
        size ++;
        return true;
    }

    /**
     * 从栈顶弹出一个元素，返回该元素并删除该元素在栈中的位置
     * @return
     */
    public E pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        // 直接返回数组最后一个元素即可
        @SuppressWarnings("unchecked")
        E e = (E) items[size - 1];
        // 删除元素
        items[size - 1] = null;
        // 个数 - 1
        size --;
        return e;
    }

    /**
     * 查看栈顶元素，不会删除栈顶元素
     * @return
     */
    public E peek() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        @SuppressWarnings("unchecked")
        E e = (E) items[items.length - 1];
        return e;
    }

    @Override
    public String toString() {
        List<E> list = new ArrayList<>();
        for (Object item : items) {
            list.add((E) item);
        }
        return list.toString();
    }

    /**
     * 是否为空
     * @return
     */
    public boolean empty() {
        return size == 0;
    }

    /**
     * 返回栈中元素个数
     * @return
     */
    public int size() {
        return size;
    }
}

