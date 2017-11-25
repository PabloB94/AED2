package aed.huffman;

import es.upm.aedlib.Position;
import es.upm.aedlib.tree.*;


/**
 * Defines metodos for Huffman encoding of text strings.
 */
public class Huffman {
    private LinkedBinaryTree<Character> huffmanTree;
    
    public Huffman(LinkedBinaryTree<Character> huffmanTree) {
      // NO CAMBIA ESTE METODO!!! Esta usado durante las pruebas
      this.huffmanTree = huffmanTree;
    }

    /**
     * Creates a Huffman tree (and stores it in the attribute huffmanTree).
     * The shape of the (binary) tree is
     * defined by the array of char-codes.
     */
    public Huffman(CharCode[] paths) {
    	this.huffmanTree = new LinkedBinaryTree<Character>();
    	this.huffmanTree.addRoot(' ');
    	for(int i = 0; i < paths.length; i++){
    		Character ch = paths[i].getCh();
    		char[] code = paths[i].getCode().toCharArray();
    		Position<Character> pos = this.huffmanTree.root();
    		for(int j = 0; j < code.length; j++){
    			if(code [j] == '0'){
    				if(!this.huffmanTree.hasLeft(pos)) this.huffmanTree.insertLeft(pos, ch);
    			}else if(code[j] == '1'){
    				if(!this.huffmanTree.hasRight(pos)) this.huffmanTree.insertRight(pos, ' ');
    				pos = this.huffmanTree.right(pos);
    			}
    		}
    	}
    }

    //////////////////////////////////////////////////////////////////////


    /**
     * Huffman encodes a text, returning a new text string
     * containing only characters '0' and '1'.
     */
    public String encode(String text) {
	// CAMBIA
	// llama el metodo findCharacterCode
	return "";
    }

    // CAMBIA e UTILIZA si quiereis
    private String findCharacterCode(Character ch, 
			     BinaryTree<Character> tree,
			     Position<Character> pos,
			     String path) {

	// Return null if character not found, and otherwise the
	// string of "0" and "1" that lead to the node with the character
        return null;			     
    }


    //////////////////////////////////////////////////////////////////////

    /**
     * Given the Huffman encoded text argument (a string of only
     * '0' and '1's), returns the corresponding normal text.
     */
    public String decode(String encodedText) {
	// CAMBIA
	return "";
    }
}



