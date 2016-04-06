package cn.leepon.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 获取子节点
 */
public class NodeUtil {
	
	 private static List<Integer> returnList = new ArrayList<Integer>();
     
	    /**
	     * 根据父节点的ID获取所有子节点
	     * @param list 分类表
	     * @param typeId 传入的父节点ID
	     * @return String
	     */
	    public static String getChildNodes(List<Node> list, Integer typeId) {
	        if(list == null && typeId == null) return "";
	        for (Iterator<Node> iterator = list.iterator(); iterator.hasNext();) {
	            Node node = (Node) iterator.next();
	            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
	            if (node.getParentId()==0 && typeId==node.getId()) {
	                recursionFn(list, node);
	            }
	            // 二、遍历所有的父节点下的所有子节点
	            /*if (node.getParentId()==0) {
	                recursionFn(list, node);
	            }*/
	        }
	        return returnList.toString();
	    }
	     
	    private static void recursionFn(List<Node> list, Node node) {
	        List<Node> childList = getChildList(list, node);// 得到子节点列表
	        if (hasChild(list, node)) {// 判断是否有子节点
	            returnList.add(node.getId());
	            Iterator<Node> it = childList.iterator();
	            while (it.hasNext()) {
	                Node n = (Node) it.next();
	                recursionFn(list, n);
	            }
	        } else {
	            returnList.add(node.getId());
	        }
	    }
	     
	    // 得到子节点列表
	    private static List<Node> getChildList(List<Node> list, Node node) {
	        List<Node> nodeList = new ArrayList<Node>();
	        Iterator<Node> it = list.iterator();
	        while (it.hasNext()) {
	            Node n = (Node) it.next();
	            if (n.getParentId() == node.getId()) {
	                nodeList.add(n);
	            }
	        }
	        return nodeList;
	    }
	 
	    // 判断是否有子节点
	    private static boolean hasChild(List<Node> list, Node node) {
	        return getChildList(list, node).size() > 0 ? true : false;
	    }

}
