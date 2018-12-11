package finalProject;
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
     * @param branchingFactor = for how many nodes are allowed in each branch
     */
    public BPTree(int branchingFactor) {
        if (branchingFactor <= 2) { //must have a branching factor 3 or above
            throw new IllegalArgumentException(
               "Illegal branching factor: " + branchingFactor);
        }
        this.branchingFactor = branchingFactor;
        root = new LeafNode();
    }
    
    
    /*
     * Inserts a value with the passed in key into the tree
     * @param K key = key for value
     * @param V value = value being inserted
     * (non-Javadoc)
     * @see BPTreeADT#insert(java.lang.Object, java.lang.Object)
     */
    @Override
    public void insert(K key, V value) {
        root.insert(key, value);
    }
    
    
    /*
     * Gets the values that satisfy the given range 
     * search arguments.
     * 
     * Value of comparator can be one of these: 
     * "<=", "==", ">="
     * 
     * Example:
     *     If given key = 2.5 and comparator = ">=":
     *         return all the values with the corresponding 
     *      keys >= 2.5
     *      
     * If key is null or not found, return empty list.
     * If comparator is null, empty, or not according
     * to required form, return empty list.
     * 
     * @param key to be searched
     * @param comparator is a string
     * @return list of values that are the result of the 
     * range search; if nothing found, return empty list
     * (non-Javadoc)
     * @see BPTreeADT#rangeSearch(java.lang.Object, java.lang.String)
     */
    @Override
    public List<V> rangeSearch(K key, String comparator) {
    	return root.rangeSearch(key, comparator);
    	
    }
    
    /*
     * returns a string method of the tree
     * This method was provided to us, so we didn't comment
     * @return string of tree, correctly formatted 
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
         * gets the first key in the first child
         * @return K key
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            return children.get(0).getFirstLeafKey();
        }
        
        /**
         * checks if the internal node has overflowed
         * @return true if the size of children is greater than the branching factor
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
            return children.size() > branchingFactor;
        }
        
        /**
         * inserts a key into the tree, will handle if the tree is overflowed
         * 
         * @see BPTree.Node#insert(java.lang.Comparable, java.lang.Object)
         */
        void insert(K key, V value) {
          	int loc = Collections.binarySearch(keys, key); //gets location of key in sorted list
        	int childIndex = loc >= 0 ? loc +1: -loc - 1;
        	Node child = children.get(childIndex); //get child at child's index
        	child.insert(key, value); //try inserting value at child
        	if(child.isOverflow()) { //if the child is overflowed
        		Node sibling = child.split(); //split
        		
        		insertChild(sibling.getFirstLeafKey(), sibling); //insert newly made sibling
        	}
        	if(root.isOverflow()) { //if the  internal node is overflowed
        		Node sibling = split(); //split
        		
        		InternalNode newRoot = new InternalNode(); 
        		newRoot.keys.add(sibling.getFirstLeafKey()); //add sibling children
        		newRoot.children.add(this); //add this internal node
        		newRoot.children.add(sibling); //add sibling
        		root = newRoot; //change parent
        		
        	}
        }
        
        /**
         * splits one internal node into two
         * @return new sibling node
         * @see BPTree.Node#split()
         */
        Node split() {
        	
            int fromX = (keys.size() +1) /2, toY = keys.size(); //get range from middle of list to end
            InternalNode sibling = new InternalNode(); //create new sibling
            sibling.keys.addAll(keys.subList(fromX, toY)); //get all keys from range and add them to new sibling
            sibling.children.addAll(children.subList(fromX, toY + 1)); //get all children from range and add them to new sibling
            
            keys.subList(fromX-1, toY).clear(); //delete them from old node
            children.subList(fromX, toY + 1).clear(); //delete them from old node
             
            return sibling; //return newly created node
        }
        
        /**
         * Gets the children of this node and checks them with comparator
         * @return list of sorted values
         * @see BPTree.Node#rangeSearch(java.lang.Comparable, java.lang.String)
         */
        List<V> rangeSearch(K key, String comparator) {
        	List<V> temp = new ArrayList<V>();
        	for(int i = 0; i < children.size(); i++) { //iterate through children
        		temp.addAll(children.get(i).rangeSearch(key, comparator)); //call rangesearch on each child
        	}
        	return temp;
        }
        
        /*
         * helper method for inserting children of internal nodes
         * @param key
         * @param child that is being split
         */
        void insertChild(K key, Node child) {
            int loc = Collections.binarySearch(keys, key); //find location of key in list
            int childIndex = loc >= 0 ? loc +1: -loc -1; //the index 
         	   keys.add(childIndex, key); //add this key at the found index
         	   children.add(childIndex +1, child); //add
            
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
         * Gets the first key in the children
         * @return K key
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            return keys.get(0);
        }
        
        /**
         * check if overflowed or not
         * @return boolean true if the children are overflowed, false if not
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
        	
            return values.size() >= branchingFactor; //true if the size of value array is greater than branching factor
        }
        
        /**
         * inserts a key and value into leaf node
         * @see BPTree.Node#insert(Comparable, Object)
         */
        void insert(K key, V value) {
            int loc = Collections.binarySearch(keys, key);
            // this finds the index of the key in the list "keys"
            // the list should be sorted in ascending order
            int valueIndex = loc >= 0 ? loc : -loc - 1;  //if location in array >= 0, index is location, -loc -1;
       
            	keys.add(valueIndex, key);
            	values.add(valueIndex, value);
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
         * splits the childNode into two nodes
         * @return new sibling node
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
         * searches the child nodes and compares the values with the comparator
         * @return list of filtered values
         * @see BPTree.Node#rangeSearch(Comparable, String)
         */
        List<V> rangeSearch(K key, String comparator) {
        	List<V> temp = new ArrayList<V>();
        	 if (!comparator.contentEquals(">=") &&  //if the comparator isn't any of these
        	            !comparator.contentEquals("==") && 
        	            !comparator.contentEquals("<=") )
        	            return temp; //return a new list
        	 
        	 else{
        		 for(int i = 0; i < keys.size(); i++) { //iterate through the keys
        			 if(comparator.contentEquals(">=")) { 
        				 if(!(key.compareTo(keys.get(i)) >= 1)) { //if the key is greater than or equal to other key
        					 temp.add(values.get(i)); //add it to temp 
        				 }
        			 }else if(comparator.contentEquals("==")) { //if the key is equal to the other key
        				 if(key.compareTo(keys.get(i)) == 0) {
        					 temp.add(values.get(i));
        				 }
        			 }else if(comparator.contentEquals("<=")) { //if the key is less than the other key
        				 if(!(key.compareTo(keys.get(i)) <= -1)) {
        					 temp.add(values.get(i));
        				 }
        			 }
        		 }
        	 }
            return temp;
        }
        
        /*
         * helper method that returns values in child
         * 
         * @return list of values
         */
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
