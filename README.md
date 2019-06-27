[TOC]

## data-structures

### 介绍
数据结构学习源码，学习过程中参考尚硅谷的《尚硅谷-韩顺平图解Java数据结构和算法》视频,
但视频中有些内容为了便于理解，存在一些硬编码以及一些个人觉得可以优化的点，因此该项目中源码与视频中源码有较大出入；

### 内容

#### 1. 稀疏数组

`com.ddf.datastructure.sparsearray.SparseArray`


#### 2. 队列
##### 2.1 数组实现队列

`com.ddf.datastructure.queue.ArrayQueueDemo`


#### 3. 链表

##### 3.1 单向链表

`com.ddf.datastructure.linkedlist.SingletonLinkedListDemo`

##### 3.2 双向链表

`com.ddf.datastructure.linkedlist.LinkedListDemo`

##### 3.3 单向环形链表

`com.ddf.datastructure.linkedlist.CircleSingletonLinkedListDemo`

#### 4. 栈

##### 4.1 数组实现栈

`com.ddf.datastructure.stack.ArrayStackDemo`

##### 4.2 基于栈实现一个简单的计算器
表达式必须是后缀表达式，暂时未处理将中缀表达式转换为后缀表达式，这只是个简单演示，说明栈的应用

`com.ddf.datastructure.stack.Calculator`

#### 5. 递归

##### 5.1 迷宫问题
使用递归回溯算法来完成寻找迷宫的路径问题

`com.ddf.datastructure.recursion.Maze`

#### 6. 排序算法
> 关于排序算法的写法，按照韩顺平韩老师的思路去想是一个比较好的思路，首先关于各种算法的动态图解都有很多，但是真的一步到位写出来对于
算法基础的人来说还是比较困难的，韩老师提供了一个思路，就把数组自己先手动排序，第一次排序后是什么样子的，第二次排序后是什么样子的，
然后自己就按照结果写代码，变量的什么的都直接写死，比如循环第一次就直接写`int i = 0`， 循环第二次也直接写`int i = 1`，然后包括变量赋值
也是都是直接操作角标，然后找到规律了，再把写死的代码用变量代替掉，从简单到难写，这一点可以详细看下韩老师的视频，确实受益匪浅；

* 冒泡排序
* 选择排序
* 插入排序
* 希尔排序
