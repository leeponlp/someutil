package cn.leepon.demo;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import cn.leepon.util.Node;
import cn.leepon.util.NodeUtil;

public class NodeUtilTest {
	
	@Test
    public void demo1() {
        long start = System.currentTimeMillis();
        List<Node> nodeList = new ArrayList<Node>();
        Node node1 = new Node(1, "蔬菜", 0);
        Node node2 = new Node(2, "水产", 0);
        Node node3 = new Node(3, "畜牧", 0);
        Node node4 = new Node(4, "瓜类", 1);
        Node node5 = new Node(5, "叶类", 1);
        Node node6 = new Node(6, "丝瓜", 4);
        Node node7 = new Node(7, "黄瓜", 4);
        Node node8 = new Node(8, "白菜", 1);
        Node node9 = new Node(9, "虾", 2);
        Node node10 = new Node(10, "鱼", 2);
        Node node11 = new Node(11, "牛", 3);
         
        nodeList.add(node1);
        nodeList.add(node2);
        nodeList.add(node3);
        nodeList.add(node4);
        nodeList.add(node5);
        nodeList.add(node6);
        nodeList.add(node7);
        nodeList.add(node8);
        nodeList.add(node9);
        nodeList.add(node10);
        nodeList.add(node11);
         
        System.out.println(NodeUtil.getChildNodes(nodeList, 1));
        long end = System.currentTimeMillis();
        System.out.println("用时:" + (end - start) + "ms");
    }

}
