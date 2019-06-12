package com.ddf.datastructure.queue;

import java.util.NoSuchElementException;
import java.util.Random;

/**
 * 使用数组实现队列
 *
 * 队列的特点，先进先出
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
     * @return
     */
    private E dequeue() {
        @SuppressWarnings("unchecked")
        E e = (E) items[takeIndex];
        items[takeIndex] = null;
        if (++takeIndex == items.length) {
            takeIndex = 0;
        }
        count --;
        return e;
    }

    /**
     * 取出队列的头部元素，如果元素为空，则抛出异常
     * 1. 每次取队列数据的时候先判断当前角标的元素是否为空，如果为空，表明队列中没有数据，抛出异常
     * 2. 取出当前角标元素后，要将当前角标元素置空，然后需要判断下一次取元素的角标是否已到达数组长度临界上限，比如数组长度为4，
     *    则，当前取到了[3]的位置，那么下一次就要取[4]的位置，然而数组长度一共就四个长度，直接使用会越界，为了循环使用，所以这个时候需要
     *    把下一次取元素的角标置为0
     *
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
