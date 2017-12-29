package aed.compress;

import es.upm.aedlib.Position;
import es.upm.aedlib.tree.*;


public class AttachableLinkedBinaryTree<E> extends LinkedBinaryTree<E> implements AttachableBinaryTree<E> {

	public void attach(Position<E> pos, BinaryTree<E> leftTree, BinaryTree<E> rightTree) {
		Position<E> rootLeft = leftTree.root();
		Position<E> rootRight = rightTree.root();
		this.insertLeft(pos, rootLeft.element());
		this.insertRight(pos, rootRight.element());
		if(leftTree.hasLeft(rootLeft) || leftTree.hasRight(rootLeft)) clone(this.left(pos), leftTree, rootLeft);
		if(rightTree.hasLeft(rootRight) || rightTree.hasRight(rootRight)) clone(this.right(pos), rightTree, rootRight);		
	}
	
	private void clone(Position<E> pos, BinaryTree<E> tree, Position<E> root){
		if(tree.hasLeft(root)){
			this.insertLeft(pos, tree.left(root).element());
			clone(this.left(pos), tree, tree.left(root));
		}
		if(tree.hasRight(root)){
			this.insertRight(pos, tree.right(root).element());
			clone(this.right(pos), tree, tree.right(root));
		}		
	}

}
