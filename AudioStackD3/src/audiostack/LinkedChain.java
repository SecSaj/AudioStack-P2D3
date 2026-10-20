package audiostack;

/**
 * Generic singly linked storage supplied by CS 2114 staff and adapted for this
 * slice.
 * <p>
 * Original public operations are preserved. Added: a lastNode tail reference
 * for O(1) appends, removeAt(int), insert(int, T), removeEntry(T),
 * getFrequencyOf(T), getCurrentSize(), and toArray().
 * </p>
 * 
 * @param <T>
 *            element type
 */
public class LinkedChain<T>
{
    private Node<T> firstNode;
    private Node<T> lastNode;
    private int numberOfEntries;

    /**
     * Creates an empty chain.
     */
    public LinkedChain()
    {
        firstNode = null;
        lastNode = null;
        numberOfEntries = 0;
    }


    /**
     * Adds a nonnull entry to the front.
     * 
     * @param newEntry
     *            object to add
     * @return true after insertion
     * @throws IllegalArgumentException
     *             if entry is null
     */
    public boolean add(T newEntry)
    {
        requireNonNull(newEntry);
        firstNode = new Node<T>(newEntry, firstNode);
        // first entry in an empty chain is also the last
        if (lastNode == null)
        {
            lastNode = firstNode;
        }
        numberOfEntries++;
        return true;
    }


    /**
     * Adds a nonnull entry at the end, preserving append order.
     * 
     * @param newEntry
     *            object to append
     * @return true after insertion
     * @throws IllegalArgumentException
     *             if entry is null
     */
    public boolean addToEnd(T newEntry)
    {
        requireNonNull(newEntry);
        Node<T> newNode = new Node<T>(newEntry);
        if (firstNode == null)
            firstNode = newNode;
        else
            lastNode.next = newNode; // O(1): no walk to the end
        lastNode = newNode;
        numberOfEntries++;
        return true;
    }


    /**
     * Removes the first entry.
     * 
     * @return removed entry, or null if empty
     */
    public T remove()
    {
        if (firstNode == null)
            return null;
        T result = firstNode.data;
        firstNode = firstNode.next;
        // removed the only node, so the chain is empty
        if (firstNode == null)
        {
            lastNode = null;
        }
        numberOfEntries--;
        return result;
    }


    /**
     * Removes the first equal entry.
     * 
     * @param anEntry
     *            value to remove
     * @return whether an entry was removed
     */
    public boolean remove(T anEntry)
    {
        if (anEntry == null || firstNode == null)
            return false;
        if (anEntry.equals(firstNode.data))
        {
            remove();
            return true;
        }
        Node<T> previous = firstNode;
        Node<T> current = firstNode.next;
        while (current != null)
        {
            if (anEntry.equals(current.data))
            {
                previous.next = current.next;
                // removed the tail, so the node before it is the new tail
                if (current == lastNode)
                {
                    lastNode = previous;
                }
                numberOfEntries--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }


    /**
     * Removes an entry at the specified zero-based index.
     *
     * @param index
     *            the position of the entry to remove
     * @return the removed entry
     * @throws IndexOutOfBoundsException
     *             if index is invalid
     */
    public T removeAt(int index)
    {
        if (index < 0 || index >= numberOfEntries)
        {
            throw new IndexOutOfBoundsException(
                "Index: " + index + ", Size: " + numberOfEntries);
        }

        if (index == 0)
        {
            return remove();
        }

        Node<T> previous = firstNode;

        for (int i = 1; i < index; i++)
        {
            previous = previous.next;
        }

        Node<T> removed = previous.next;
        previous.next = removed.next;
        // removed the tail, so the node before it is the new tail
        if (removed == lastNode)
        {
            lastNode = previous;
        }
        numberOfEntries--;

        return removed.data;
    }


    /**
     * Removes all stored entries.
     */
    public void clear()
    {
        firstNode = null;
        lastNode = null;
        numberOfEntries = 0;
    }


    /**
     * Gets the entry at a zero-based index without removing it.
     * 
     * @param index
     *            index to retrieve
     * @return entry at index
     * @throws IndexOutOfBoundsException
     *             if index is invalid
     */
    public T get(int index)
    {
        if (index < 0 || index >= numberOfEntries)
            throw new IndexOutOfBoundsException(
                "Index: " + index + ", Size: " + numberOfEntries);
        Node<T> current = firstNode;
        for (int i = 0; i < index; i++)
            current = current.next;
        return current.data;
    }


    /**
     * Checks whether an equal entry is stored.
     * 
     * @param anEntry
     *            entry to look for
     * @return true if an equal entry exists
     */
    public boolean contains(T anEntry)
    {
        return count(anEntry) > 0;
    }


    /**
     * Counts occurrences equal to an entry.
     * 
     * @param anEntry
     *            value to count
     * @return number of matching entries; zero for null
     */
    public int count(T anEntry)
    {
        if (anEntry == null)
            return 0;
        int frequency = 0;
        Node<T> current = firstNode;
        while (current != null)
        {
            if (anEntry.equals(current.data))
                frequency++;
            current = current.next;
        }
        return frequency;
    }


    /**
     * Gets the number of stored entries.
     * 
     * @return entry count
     */
    public int size()
    {
        return numberOfEntries;
    }


    /**
     * Checks whether the chain contains no entries.
     * 
     * @return true if empty
     */
    public boolean isEmpty()
    {
        return numberOfEntries == 0;
    }


    /**
     * Copies entries into an adequately sized array in order.
     * 
     * @param values
     *            destination array
     * @return supplied array filled with entries
     * @throws IllegalArgumentException
     *             if array is null or too small
     */
    public T[] toArray(T[] values)
    {
        if (values == null)
            throw new IllegalArgumentException("Array must not be null");
        if (values.length < numberOfEntries)
            throw new IllegalArgumentException(
                "Array of length " + values.length + " is too small for "
                    + numberOfEntries + " entries");
        int index = 0;
        Node<T> current = firstNode;
        while (current != null)
        {
            values[index++] = current.data;
            current = current.next;
        }
        return values;
    }


    /**
     * Gets the number of stored entries. This is the spec name for size().
     *
     * @return entry count
     */
    public int getCurrentSize()
    {
        return numberOfEntries;
    }


    /**
     * Inserts a nonnull entry at a zero-based index. Index 0 puts it at the
     * front and index size() puts it at the end. Everything at or after the
     * index moves back one spot.
     *
     * @param index
     *            position for the new entry, 0 through size()
     * @param newEntry
     *            object to insert
     * @throws IllegalArgumentException
     *             if entry is null
     * @throws IndexOutOfBoundsException
     *             if index is below 0 or above size()
     */
    public void insert(int index, T newEntry)
    {
        requireNonNull(newEntry);
        if (index < 0 || index > numberOfEntries)
        {
            throw new IndexOutOfBoundsException(
                "Index: " + index + ", Size: " + numberOfEntries);
        }

        // front of the chain: the new node becomes the head
        if (index == 0)
        {
            add(newEntry);
            return;
        }
        // end of the chain: append in O(1) using lastNode
        if (index == numberOfEntries)
        {
            addToEnd(newEntry);
            return;
        }
        // walk to the node just before the index, then relink around it
        Node<T> previous = firstNode;
        for (int i = 1; i < index; i++)
        {
            previous = previous.next;
        }
        previous.next = new Node<T>(newEntry, previous.next);
        numberOfEntries++;
    }


    /**
     * Removes the first entry equal to the given one. It finds the index first
     * and then lets removeAt(int) do the unlinking, so the head and middle
     * cases are only coded once.
     *
     * @param anEntry
     *            value to remove
     * @return true if an entry was removed; false if not found, empty, or null
     */
    public boolean removeEntry(T anEntry)
    {
        if (anEntry == null)
        {
            return false;
        }
        int index = 0;
        Node<T> current = firstNode;
        while (current != null)
        {
            if (anEntry.equals(current.data))
            {
                removeAt(index);
                return true;
            }
            current = current.next;
            index++;
        }
        return false;
    }


    /**
     * Counts how many stored entries equal the given one. This is the spec
     * name for count(T).
     *
     * @param anEntry
     *            value to count
     * @return number of matching entries; zero for null or an empty chain
     */
    public int getFrequencyOf(T anEntry)
    {
        return count(anEntry);
    }


    /**
     * Copies every entry into a new Object array in stored order. Java cannot
     * make a real T[] at runtime, so this returns Object[] and callers only
     * use Object methods such as toString() on the elements.
     *
     * @return a new array holding the entries; length 0 if empty
     */
    public Object[] toArray()
    {
        Object[] result = new Object[numberOfEntries];
        int index = 0;
        Node<T> current = firstNode;
        while (current != null)
        {
            result[index++] = current.data;
            current = current.next;
        }
        return result;
    }


    /** @return a list of entries written inside square brackets */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("[");
        Node<T> current = firstNode;
        while (current != null)
        {
            sb.append(current.data);
            if (current.next != null)
                sb.append(", ");
            current = current.next;
        }
        return sb.append("]").toString();
    }


    /**
     * Rejects a null entry.
     * 
     * @param entry
     *            entry to validate
     * @throws IllegalArgumentException
     *             if null
     */
    private void requireNonNull(T entry)
    {
        if (entry == null)
            throw new IllegalArgumentException("Cannot add null to chain");
    }

    /**
     * Stores one entry and a reference to the next node.
     * 
     * @param <E>
     *            node element type
     */
    private static class Node<E>
    {
        private E data;
        private Node<E> next;

        /**
         * Creates a node with no successor.
         * 
         * @param dataPortion
         *            stored data
         */
        private Node(E dataPortion)
        {
            this(dataPortion, null);
        }


        /**
         * Creates a node with a supplied successor.
         * 
         * @param dataPortion
         *            stored data
         * @param nextNode
         *            successor node
         */
        private Node(E dataPortion, Node<E> nextNode)
        {
            data = dataPortion;
            next = nextNode;
        }
    }
}
