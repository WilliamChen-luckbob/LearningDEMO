package com.wwstation.leetcode.easy26;

import java.util.StringJoiner;

/**
 * @author william
 * @description
 * @Date: 2021-07-29 18:20
 */
public class TestMain {
    /**
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode() {}
     * ListNode(int val) { this.val = val; }
     * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */

    public static void main(String[] args) {
        ListNode l1 = new ListNode(6);
        ListNode l2 = new ListNode(1, new ListNode(3, new ListNode(4)));
        ListNode listNode = mergeTwoLists(l1, l2);
        StringJoiner stringJoiner = new StringJoiner(",", "[", "]");
        while (listNode != null) {
            stringJoiner.add(String.valueOf(listNode.val));
            listNode = listNode.next;
        }
        System.out.println(stringJoiner.toString());
    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
       if (l1==null){
           return l2;
       }else if (l2==null){
           return l1;
       }else if( l1.val<l2.val) {
           l1.next=mergeTwoLists(l1.next,l2);
           return l1;
       }else {
           l2.next=mergeTwoLists(l1,l2.next);
           return l2;
       }
    }

}

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
