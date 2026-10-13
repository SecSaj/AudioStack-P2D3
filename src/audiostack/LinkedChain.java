package audiostack;

/**
 * Generic singly linked storage supplied by CS 2114 staff and adapted for this
 * slice.
 * <p>
 * Original public operations are preserved. The only added operation is
 * removeAt(int).
 * </p>
 * 
 * @param <T>
 *            element type
 */
public class LinkedChain<T>
{
    private Node<T> firstNode;
    private int numberOfEntries;

    /**
     * Creates an empty chain.
     */
    public LinkedChain()
    {
        firstNode = null;
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
        {
            Node<T> current = firstNode;
            while (current.next != null)
                current = current.next;
            current.next = newNode;
        }
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
            firstNode = firstNode.next;
            numberOfEntries--;
            return true;
        }
        Node<T> previous = firstNode;
        Node<T> current = firstNode.next;
        while (current != null)
        {
            if (anEntry.equals(current.data))
            {
                previous.next = current.next;
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
            throw new IndexOutOfBoundsException("Invalid index: " + index);
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
        numberOfEntries--;

        return removed.data;
    }


    /**
     * Removes all stored entries.
     */
    public void clear()
    {
        firstNode = null;
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
                "Index " + index + " out of bounds for size "
                    + numberOfEntries);
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
