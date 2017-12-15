package aed.compress;

import es.upm.aedlib.Position;
import es.upm.aedlib.Entry;
import es.upm.aedlib.tree.*;
import es.upm.aedlib.priorityqueue.*;


public class Huffman {
	private BinaryTree<Character> huffmanTree;


	public Huffman(String text) {
		this.huffmanTree = constructHuffmanTree(text);
	}

	private BinaryTree<Character> constructHuffmanTree(String text) {
        int[] charCode = countChars(text);
        PriorityQueue<Integer, AttachableLinkedBinaryTree<Character>> priQ = new SortedListPriorityQueue<Integer, AttachableLinkedBinaryTree<Character>>();
        for (int i = 0; i < charCode.length; i++) {
            if (charCode[i] > 0) {
                AttachableLinkedBinaryTree<Character> tree = new AttachableLinkedBinaryTree<Character>();
                tree.addRoot((char) i);
                priQ.enqueue(charCode[i], tree);
            }
        }
        if (priQ.isEmpty()){
        	return null;
        }

        {
            Entry<Integer, AttachableLinkedBinaryTree<Character>> leftTree = priQ.dequeue();
            Entry<Integer, AttachableLinkedBinaryTree<Character>> rightTree = priQ.dequeue();
            AttachableLinkedBinaryTree<Character> var = new AttachableLinkedBinaryTree<Character>();
            var.addRoot(' ');
            var.attach(var.root(), leftTree.getValue(), rightTree.getValue());
            priQ.enqueue((leftTree.getKey() + rightTree.getKey()), var);
        }while(priQ.size() > 1);
        return priQ.dequeue().getValue();
    }


	public static int[] countChars(String text) {
		int arr[] = new int[256];      
		for(int i = 0; i<text.length(); i++){      
			int num = text.charAt(i);
			arr[num]++;
		}
		return arr;
	}
}



