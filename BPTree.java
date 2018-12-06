import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import sun.awt.SunHints.Value;

/**
 * Implementation of a B+ tree to allow efficient access to
 * many different indexes of a large data set. 
 * BPTree objects are created for each type of index
 * needed by the program.  BPTrees provide an efficient
 * range search as compared to other types of data structures
 * due to the ability to perform log_m N lookups and
 * linear in-order traversals of the data items.
 * 
 * @author sapan (sapan@cs.wisc.edu)
 *
 * @param <K> key - expect a string that is the type of id for each item
 * @param <V> value - expect a user-defined type that stores all data for a food item
 */
public class BPTree<K extends Comparable<K>, V> implements BPTreeADT<K, V> {

    // Root of the tree
    private Node root;
    
    // Branching factor is the number of children nodes 
    // for internal nodes of the tree
    private int branchingFactor;
    
    
    /**
     * Public constructor
     * 
     * @param branchingFactor 
     */
    public BPTree(int branchingFactor) {
        if (branchingFactor <= 2) {
            throw new IllegalArgumentException(
               "Illegal branching factor: " + branchingFactor);
        }
        this.branchingFactor = branchingFactor;
        root = new LeafNode();
    }
    
    
    /*
     * (non-Javadoc)
     * @see BPTreeADT#insert(java.lang.Object, java.lang.Object)
     */
    @Override
    public void insert(K key, V value) {
        root.insert(key, value);
    }
    
    
    /*
     * (non-Javadoc)
     * @see BPTreeADT#rangeSearch(java.lang.Object, java.lang.String)
     */
    @Override
    public List<V> rangeSearch(K key, String comparator) {
    	return root.rangeSearch(key, comparator);
    	
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        Queue<List<Node>> queue = new LinkedList<List<Node>>();
        queue.add(Arrays.asList(root));
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            Queue<List<Node>> nextQueue = new LinkedList<List<Node>>();
            while (!queue.isEmpty()) {
                List<Node> nodes = queue.remove();
                sb.append('{');
                Iterator<Node> it = nodes.iterator();
                while (it.hasNext()) {
                    Node node = it.next();
                    sb.append(node.toString());
                    if (it.hasNext())
                        sb.append(", ");
                    if (node instanceof BPTree.InternalNode)
                        nextQueue.add(((InternalNode) node).children);
                }
                sb.append('}');
                if (!queue.isEmpty())
                    sb.append(", ");
                else {
                    sb.append('\n');
                }
            }
            queue = nextQueue;
        }
        return sb.toString();
    }
    
    
    /**
     * This abstract class represents any type of node in the tree
     * This class is a super class of the LeafNode and InternalNode types.
     * 
     * @author sapan
     */
    private abstract class Node {
        
        // List of keys
        List<K> keys;
        
        /**
         * Package constructor
         */
        Node() {
            keys = new ArrayList<K>();
        }
        
        /**
         * Inserts key and value in the appropriate leaf node 
         * and balances the tree if required by splitting
         *  
         * @param key
         * @param value
         */
        abstract void insert(K key, V value);

        /**
         * Gets the first leaf key of the tree
         * 
         * @return key
         */
        abstract K getFirstLeafKey();
        
        /**
         * Gets the new sibling created after splitting the node
         * 
         * @return Node
         */
        abstract Node split();
        
        /*
         * (non-Javadoc)
         * @see BPTree#rangeSearch(java.lang.Object, java.lang.String)
         */
        abstract List<V> rangeSearch(K key, String comparator);

        /**
         * 
         * @return boolean
         */
        abstract boolean isOverflow();
        
        public String toString() {
            return keys.toString();
        }
    
    } // End of abstract class Node
    
    /**
     * This class represents an internal node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations
     * required for internal (non-leaf) nodes.
     * 
     * @author sapan
     */
    private class InternalNode extends Node {

        // List of children nodes
        List<Node> children;
        
        /**
         * Package constructor
         */
        InternalNode() {
            super(); //remeber there is a keys array too
            children = new ArrayList<Node>();
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            return children.get(0).getFirstLeafKey();
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
            return children.size() > branchingFactor;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#insert(java.lang.Comparable, java.lang.Object)
         */
        void insert(K key, V value) {
          	int loc = Collections.binarySearch(keys, key); //gets location of key in sorted list
        	int childIndex = loc >= 0 ? loc +1: -loc - 1;
        	Node child = children.get(childIndex); //get child at child's index
        	child.insert(key, value); //try inserting value at child
        	if(child.isOverflow()) { //if the child is overflowed
        		Node sibling = child.split(); //split
        		
        		insertChild(sibling.getFirstLeafKey(), sibling);
        	}
        	if(root.isOverflow()) {
        		Node sibling = split();
        		
        		InternalNode newRoot = new InternalNode();
        		newRoot.keys.add(sibling.getFirstLeafKey());
        		newRoot.children.add(this);
        		newRoot.children.add(sibling);
        		root = newRoot;
        		
        	}
        	//so the issue is the child isn't getting over to the next child on the other side of the tree
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
        	
            int fromX = (keys.size() +1) /2, toY = keys.size(); 
            InternalNode sibling = new InternalNode();
            sibling.keys.addAll(keys.subList(fromX, toY));
            sibling.children.addAll(children.subList(fromX, toY + 1));
            
            keys.subList(fromX-1, toY).clear();
            children.subList(fromX, toY + 1).clear();
            
            return sibling;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(java.lang.Comparable, java.lang.String)
         */
        List<V> rangeSearch(K key, String comparator) {
        	List<V> temp = new ArrayList<V>();
        	for(int i = 0; i < children.size(); i++) {
        		temp.addAll(children.get(i).rangeSearch(key, comparator));
        	}
        	return temp;
        }
        
        
        void insertChild(K key, Node child) {
            int loc = Collections.binarySearch(keys, key); //find location of key in list
            int childIndex = loc >= 0 ? loc +1: -loc -1; //the index 
            //if(loc >= 0) { //if the location is 
            	//System.out.println("if was reached in insertChild insert w/ values: " + loc + " " +  childIndex  + " " + key);
            	//keys.set(childIndex, key);
         	   //children.set(childIndex, child);
            //} else {
            	//System.out.println("else was reached in insertChild insert w/ values: " + loc + " " +  childIndex  + " " + key);
         	   keys.add(childIndex, key);
         	   children.add(childIndex +1, child);
            //}
         }
    
    } // End of class InternalNode
    
    
    /**
     * This class represents a leaf node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations that
     * required for leaf nodes.
     * 
     * @author sapan
     */
    private class LeafNode extends Node {
        
        // List of values
        List<V> values;
        
        // Reference to the next leaf node
        LeafNode next;
        
        // Reference to the previous leaf node
        LeafNode previous;
        
        /**
         * Package constructor
         */
        LeafNode() {
            super(); //remember there is a Keys list too in this
          	values = new ArrayList<V>();
          	next = null;
          	previous = null;
        }
        
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            return keys.get(0);
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
        	
            return values.size() >= branchingFactor;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#insert(Comparable, Object)
         */
        void insert(K key, V value) {
        	//Collections.sort(keys);
            int loc = Collections.binarySearch(keys, key);
            //Collections.sort(keys);
            //int loc = keys.indexOf(key);
            // this finds the index of the key in the list "keys"
            // the list should be sorted in ascending order
            int valueIndex = loc >= 0 ? loc : -loc - 1;
            //if location in array >= 0, index is location, -loc -1;
           // if(loc >= 0) { //means that there is already the value in the thing
            //	System.out.println("if was reached in leafNode insert w/ values: " + loc + " " +  valueIndex  + " " + key);
            	//keys.set(valueIndex, key);
            	//values.set(valueIndex, value);
            //} else {
            	//System.out.println("else was reached in leafNode insert w/ values: " + loc + " " +  valueIndex  + " " + key);
            	keys.add(valueIndex, key);
            	values.add(valueIndex, value);
            //}
            if(root.isOverflow()) { //if there is a split
            	Node sibling = split(); //create a new sibling
            	InternalNode newRoot = new InternalNode(); //creates a new root (new parent)
            	newRoot.keys.add(sibling.getFirstLeafKey()); //add the sibling's keys to the new internal node
            	newRoot.children.add(this); //add this leafNode to the new internal node's children
            	newRoot.children.add(sibling); //add the new sibling to the new internal node's children
            	root = newRoot; //changes the root to newly split root
            }
        }
        
        /**
         * 
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
        	
            LeafNode sibling = new LeafNode(); //make a new leafnode
            int fromX = (keys.size() +1) / 2, toY = keys.size(); //sets bounds from the half point of keys to end of keys
            sibling.keys.addAll(keys.subList(fromX, toY)); //get the keys from half point to end of keys and adds them to siblings keys
            sibling.values.addAll(values.subList(fromX, toY)); //gets the values from half point to end of values and adds them to siblings values
            keys.subList(fromX, toY).clear(); //removes the keys from old nodes list
            values.subList(fromX, toY).clear(); //removes the values from old nodes list
            sibling.next = next; //changes order of leaf nodes
            if(sibling.next != null)
            
            next = sibling;
            return sibling; // returns new sibling leaf node
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(Comparable, String)
         */
        List<V> rangeSearch(K key, String comparator) {
        	List<V> temp = new ArrayList<V>();
        	 if (!comparator.contentEquals(">=") && 
        	            !comparator.contentEquals("==") && 
        	            !comparator.contentEquals("<=") )
        	            return temp;
        	 
        	 else{
        		 for(int i = 0; i < keys.size(); i++) {
        			 if(comparator.contentEquals(">=")) {
        				 if(!(key.compareTo(keys.get(i)) >= 1)) {
        					 temp.add(values.get(i));
        				 }
        			 }else if(comparator.contentEquals("==")) {
        				 if(key.compareTo(keys.get(i)) == 0) {
        					 temp.add(values.get(i));
        				 }
        			 }else if(comparator.contentEquals("<=")) {
        				 if(!(key.compareTo(keys.get(i)) <= -1)) {
        					 temp.add(values.get(i));
        				 }
        			 }
        		 }
        	 }
            return temp;
        }
        
        List<V> getValues() {
			return values;
        	
        }
        
    } // End of class LeafNode
    
    
    /**
     * Contains a basic test scenario for a BPTree instance.
     * It shows a simple example of the use of this class
     * and its related types.
     * 
     * @param args
     */
    public static void main(String[] args) {
        // create empty BPTree with branching factor of 3
        BPTreeADT<Double, Double> bpTree = new BPTree<>(3);

        // create a pseudo random number generator
        Random rnd1 = new Random();

        // some value to add to the BPTree
        Double[] dd = {0.0d, 0.2d, 0.5d, 0.8d, .9d, 1.0d};
        
        // build an ArrayList of those value and add t7 BPTree also
        // allows for comparing the contents of the ArrayList 
        // against the contents and functionality of the BPTree
        // does not ensure BPTree is implemented correctly
        // just that it functions as a data structure with
        // insert, rangeSearch, and toString() working.
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Double j = dd[rnd1.nextInt(6)];
            list.add(j);
            System.out.println(list.toString());
            bpTree.insert(j, j);
            System.out.println("\n\nTree structure:\n" + bpTree.toString());
        }
       List<Double> filteredValues = bpTree.rangeSearch(0.5d, "==");
        System.out.println("Filtered values: " + filteredValues.toString());
    }

} // End of class BPTree
