[TOC]

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


#### 2. 队列
##### 2.1 数组实现队列
原视频中实现的数组队列为了实现对数组的循环使用，算法过于复杂和难以理解，因此这部分参考了java自身实现的源码，写了更加精简版的实现

`com.ddf.datastructure.queue.ArrayQueueDemo`


#### 3. 链表

##### 3.1 单向链表
原视频中使用的一个head节点，自己去创建了一个空的固定的头节点去实现；而自己的代码是通过`first`和`last`两个属性来确定头节点的，
每次添加都会更新头节点或者尾节点（视调用的方法决定）

`com.ddf.datastructure.linkedlist.SingletonLinkedListDemo`

##### 3.2 双向链表
双向链表直接参考了`java`的源码实现，只不过写的是简易版本，有一些方法并没有实现，主要写了添加和删除等方法

`com.ddf.datastructure.linkedlist.LinkedListDemo`
