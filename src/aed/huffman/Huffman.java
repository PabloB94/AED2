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
    		for(int j = 0; j < code.length - 1; j++){
    			if(code [j] == '0'){
    				if(!this.huffmanTree.hasLeft(pos)) this.huffmanTree.insertLeft(pos, ' ');
    				pos = this.huffmanTree.left(pos);
    			}else{
    				if(!this.huffmanTree.hasRight(pos)) this.huffmanTree.insertRight(pos, ' ');
    				pos = this.huffmanTree.right(pos);
    			}
    		}		
    		if(code[code.length - 1] == '0'){
    			if(!this.huffmanTree.hasLeft(pos)) this.huffmanTree.insertLeft(pos, ch);
    		}else{
    			if(!this.huffmanTree.hasRight(pos)) this.huffmanTree.insertRight(pos, ch);
    		}
    	}
    }

    //////////////////////////////////////////////////////////////////////


    /**
     * Huffman encodes a text, returning a new text string
     * containing only characters '0' and '1'.
     */
    public String encode(String text) {
		char[] toEncode = text.toCharArray();
		String compressed = new String("");
		for(int i = 0; i < toEncode.length; i++){
			String code = findCharacterCode(toEncode[i], this.huffmanTree.root());
			if (code != null) compressed = compressed + code;
		}
		return compressed;
    }

    
    private String findCharacterCode(Character ch, Position<Character> pos) {
    	if(pos.element() != ch){
    		if(!this.huffmanTree.hasLeft(pos) && !this.huffmanTree.hasRight(pos)) return null;
    		if(this.huffmanTree.left(pos).element() == ch) return "0";
    		if(this.huffmanTree.right(pos).element() == ch) return "1";
    		String res;
    		if(this.huffmanTree.left(pos).element() == ' '){
    			res = findCharacterCode(ch, this.huffmanTree.left(pos));
    			if(res == null) return null;
    			return "0" + res;
    		}else{
    			res = findCharacterCode(ch, this.huffmanTree.right(pos));
    			if(res == null) return null;
    			return "1" + res;
    		}   		
    	}else return "";   				     
    }


    //////////////////////////////////////////////////////////////////////

    /**
     * Given the Huffman encoded text argument (a string of only
     * '0' and '1's), returns the corresponding normal text.
     */
    public String decode(String encodedText) {
		char[] toDecode = encodedText.toCharArray();
		String text = new String("");
		Position<Character> pos = this.huffmanTree.root();
		for(char a : toDecode){
			if(a == '0'){
				text = text + this.huffmanTree.left(pos).element();
				pos = this.huffmanTree.root();
			}else if(pos.element() != ' '){
				text = text + pos.element();
				pos = this.huffmanTree.root();
			}else{
				pos = this.huffmanTree.right(pos);
			}
		}
		return text;
    }
}



