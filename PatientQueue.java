public class PatientQueue {
    private Patient[] queue; // An array of queue objects used for the binary heap
    private int N; // # of Patients in the queue
    private int size; // Total size of the queue
    private PHashtable hashtable; // Used to efficiently access attributes of Patient objects 

    /*
     * Constructor: creates a new PatientQueue with a starting capacity 
     * of 11.
     */
    public PatientQueue() {
        queue = new Patient[11];
        N = 0;
        size = 11;
        hashtable = new PHashtable();
    }

    /*
     * Constructor: creates a new PatientQueue with a starting capacity of cap.
     */
    public PatientQueue(int cap) {
        queue = new Patient[cap];
        N = 0;
        size = cap;
        hashtable = new PHashtable();
    }

    /*
     * Insert patient p into the queue.
     */
    public void insert(Patient p) {
        if (N == size) {
            resize(2*size);
        }
        queue[N] = p;
        queue[N].setPosInQueue(N);
        swim(N);
        hashtable.put(p);
        N++;
    }

    // Restores the heap property by switching the element 
    // with its parent if necessary until the heap property
    // is restored
    private void swim(int index) {
        int parentIndex = (index -1)/2;
        while(index>0 && queue[index].compareTo(queue[parentIndex]) == 1) {
            Patient temp = queue[index];
            queue[index] = queue[parentIndex];
            queue[index].setPosInQueue(index);
            queue[parentIndex] = temp;
            queue[parentIndex].setPosInQueue(parentIndex);

            index = parentIndex;
            parentIndex = (index-1)/2;
        }
    }

    // Replaces the old queue with a new queue that contains all the 
    // same Patients in the same positions but adds more space. 
    private void resize(int newSize) {
        size = newSize;
        Patient[] newQueue = new Patient[newSize];
        for (int i = 0; i<N; i++) {
            newQueue[i] = queue[i];
        }
        queue = newQueue;
    }

    /*
     * Remove and return the next patient in the queue, which should be the person 
     * with the highest priority according to the compareTo method of Patient. Throw 
     * the exception if the queue is empty.
     */
    public Patient removeNext() throws EmptyQueueException {
        if (N== 0) {
            throw new EmptyQueueException();
        }
        Patient removed = queue[0];
        queue[0] = queue[N-1];
        queue[0].setPosInQueue(0);
        queue[N-1] = null;
        N--;
        sink(0);
        removed.setPosInQueue(-1);
        return removed;
    }

    /*
     * Return but do not remove the next patient in the queue, which should be the person with 
     * the highest priority according to the compareTo method of Patient. Throw the exception if 
     * the queue is empty.
     */
    Patient getNext() throws EmptyQueueException {
        if (N == 0) {
            throw new EmptyQueueException();
        }
        return queue[0];
    }


    // Restores the heap propety by switching the element at index with its 2 children node, 
    // if necessary, until the heap property is restored
    private void sink(int index) {
        while (2*index+1 < N) {
            int left = 2*index+1;
            int right = 2*index+2;
            int greaterChild = left; 
    
            if (right < N && queue[right] != null && (queue[left] == null || queue[right].compareTo(queue[left]) > 0)) {
                greaterChild = right;
            }
    
            if (queue[left] == null || (queue[greaterChild] != null && queue[index] != null && queue[index].compareTo(queue[greaterChild]) >= 0)) {
                break;
            }
    
            if (queue[index] != null && queue[greaterChild] != null) {
                Patient temp = queue[index];
                queue[index] = queue[greaterChild];
                queue[index].setPosInQueue(index);
                queue[greaterChild] = temp;
                queue[greaterChild].setPosInQueue(greaterChild);
                index = greaterChild;
            } else {
                break; 
            }
        }
    }
    
    // returns the number of elements in the queue
    public int size() {
        return N;
    }

    /*
     * Return true if the queue is empty and false if it isn’t.
     */
    public boolean isEmpty() {
        return queue[0] == null;
    }

    public Patient remove(String name) {
        Patient p = hashtable.remove(name);
        if (p == null) {
            return null; 
        }
        int removedIndex = p.posInQueue();
        Patient removed = queue[removedIndex];
        Patient lastElement = queue[N-1];
        queue[removedIndex] = lastElement;
        if (queue[removedIndex] != null) {
            queue[removedIndex].setPosInQueue(removedIndex);
        }
        queue[N-1] = null;
        int parentIndex = (removedIndex-1)/2;
        if (queue[removedIndex] != null && queue[parentIndex] != null) {
            if (queue[removedIndex].compareTo(queue[parentIndex]) > 0) {
                swim(removedIndex);
            } else if (queue[parentIndex].compareTo(queue[removedIndex]) > 0) {
                sink(removedIndex);
            }
        }
        N--;
        if (removed != null) {
            removed.setPosInQueue(-1);
        }
        return removed;
    }
    

    /*
     * Update the urgency level of the patient with the given name. The new urgency value is 
     * passed as parameter urgency. Return the updated patient or null if the patient does not 
     * exist in the table.
     */
    public Patient update(String name, int urgency) {
        Patient p = hashtable.get(name);
        
        int currUrgency = p.urgency();
        p.setUrgency(urgency);
        int elementPos = p.posInQueue();

        if (urgency > currUrgency) {
            swim(elementPos);
        }
        else if (urgency < currUrgency) {
            sink(elementPos);
        }
        return p;
    }


}

