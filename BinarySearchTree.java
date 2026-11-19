import java.util.*;

/**
 * This BinarySearchTree object defines a reference based binary search tree
 * 
 * @author  
 * @version 
 */

public class BinarySearchTree<T extends Comparable<T>> implements BSTInterface<T>
{
    protected BinaryNode<T> root;      // reference to the root of this BST

    // Creates an empty Binary Search Tree object
    public BinarySearchTree()
    {
        root = null;
    }

    // Returns true if this BST is empty; otherwise, returns false.
    public boolean isEmpty()
    {
       if(root==null)
       {
            return true;
       }
       return false;
    }

    // Returns the number of elements in this BST.
    public int size()
    {
        return size(root);
    }

    private int size(BinaryNode<T> tree)
    {
        if (tree == null)
            return 0;
        return 1 + size(tree.getLeft()) + size(tree.getRight());
    }
    // Adds element to this BST. The tree retains its BST property.
    public void add (T element)
    {
        if(root==null)
        {
            root=new BinaryNode<T>(element);
        }
        else
        {
            boolean go = true;
            /*while(go)
            {
               if(current.getInfo().compareTo(element)<0)
               {
                if(current.getRight()==null)
                {
                    go = false;
                    current.setRight(new BinaryNode<T>(element));
                }
                current = current.getRight();
               }
               else if(current.getInfo().compareTo(element)>0)
               {
                if(current.getLeft()==null)
                {
                    go = false;
                    current.setLeft(new BinaryNode<T>(element));
                }
                current = current.getLeft();
               }
               
            }*/
           if(contains(element))
           {
            add(root, element);
           }
        }
    }
    private void add(BinaryNode<T> current, T element)
    {
        boolean left = false;
        {
            if(current.getInfo().compareTo(element)>0)
               {
                    left = true;
               }
            if(left)
            {
                if(current.getLeft()!=null)
                {
                    add(current.getLeft(),element);
                }
                else{
                    current.setLeft(new BinaryNode<T>(element));
                }
            }
            else
            {
                if(current.getRight()!=null)
                {
                    add(current.getRight(),element);
                }
                else{
                    current.setRight(new BinaryNode<T>(element));
                }
            }
        }

    }


    // Returns true if this BST contains an element e such that 
    // e.compareTo(element) == 0; otherwise, returns false.
    public boolean contains (T element)
    {
        List<T> list = inorderTraverse();
        for(int i = 0; i<size(); i++)
        {
            if(list.get(i).equals(element))
            {
                return true;
            }
        }
        return false;
    }

    // Returns a graphical representation of the tree
    public String toString()
    {
        return toString(root, 0);
    }

    private String toString(BinaryNode<T> tree, int level)
    {
        String str = "";
        if (tree != null)
        {
            str += toString(tree.getRight(), level + 1);
            for (int i = 1; i <= level; ++i)
                str = str + "| ";
            str += tree.getInfo().toString() + "\n";
            str += toString(tree.getLeft(), level + 1);
        }
        return str;
    }

    // Returns a list of elements from a preorder traversal
    public List<T> preorderTraverse()
    {
        List<T> trav = new ArrayList<T>();
        preorderTraverse(root, trav);
        return trav;
    }
    private List<T> preorderTraverse(BinaryNode<T> tree, List<T> trav)
    {
        if (tree != null)
        {
        trav.add(tree.getInfo());
        preorderTraverse(tree.getLeft(), trav);
        preorderTraverse(tree.getRight(), trav);
        }
        return trav;
    }

    // Returns a list of elements from an inorder traversal
   public List<T> inorderTraverse()
    {
        List<T> trav = new ArrayList<T>();
        inorderTraverse(root, trav);
        return trav;
    }
    private List<T> inorderTraverse(BinaryNode<T> tree, List<T> trav)
    {
        if (tree != null)
        {
        inorderTraverse(tree.getLeft(), trav);
        trav.add(tree.getInfo());
        inorderTraverse(tree.getRight(), trav);
        }
        return trav;
    }
    
    // Returns a list of elements from a postorder traversal
    public List<T> postorderTraverse()
    {
        List<T> trav = new ArrayList<T>();
        postorderTraverse(root, trav);
        return trav;
    }
    private List<T> postorderTraverse(BinaryNode<T> tree, List<T> trav)
    {
        if (tree != null)
        {
        postorderTraverse(tree.getLeft(), trav);
        postorderTraverse(tree.getRight(), trav);
        trav.add(tree.getInfo());
        }
        return trav;
    }

    
public void remove(T element)
{
    boolean go = true;
    BinaryNode<T> current = root;
    BinaryNode<T> parent = null;

    // Find the element
    while(current != null && go)
    {
        if(current.getInfo().compareTo(element) == 0)
        {
            go = false;
        }
        else
        {
            parent = current;

            if(current.getInfo().compareTo(element) < 0)
                current = current.getRight();
            else
                current = current.getLeft();
        }
    }

    if(current == null)
        return;
    if(current.getLeft() == null && current.getRight() == null)
    {
        if(parent == null)
            root = null;
        else if(parent.getLeft() == current)
            parent.setLeft(null);
        else
            parent.setRight(null);
    }

    else if(current.getLeft() != null && current.getRight() != null)
    {
        BinaryNode<T> saved = current.getLeft();
        BinaryNode<T> savedParent = current;
        while(saved.getRight() != null)
        {
            savedParent = saved;
            saved = saved.getRight();
        }
        current.setInfo(saved.getInfo());

        if(savedParent == current)
            savedParent.setLeft(saved.getLeft());
        else
            savedParent.setRight(saved.getLeft());
    }

    else
    {
        BinaryNode<T> saved;

        if(current.getLeft() != null)
            saved = current.getLeft();
        else
            saved = current.getRight();

        if(parent == null)
            root = saved;
        else if(parent.getLeft() == current)
            parent.setLeft(saved);
        else
            parent.setRight(saved);
    }
}
    // Restructures this BST to be optimally balanced
    
public void balance()
{
    List<T> list = inorderTraverse();
    root = balance(list, 0, list.size() - 1);
}

private BinaryNode<T> balance(List<T> list, int start, int end)
{
    if(start > end)
        return null;

    int middle = (start + end) / 2;

    BinaryNode<T> current = new BinaryNode<T>(list.get(middle));

    current.setLeft(balance(list, start, middle - 1));
    current.setRight(balance(list, middle + 1, end));

    return current;
}

}