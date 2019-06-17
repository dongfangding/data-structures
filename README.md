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

