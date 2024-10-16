public class PHashtable {
     Patient[] hashtable;
    private int N;
    private int M;
    
    /*
     * Constructor–creates an empty hashtable with a starting capacity of 11
     */
    public PHashtable() {
        hashtable = new Patient[11];
        M = 11;
        N = 0;
    }

    /*
     * Constructor–creates an empty hashtable with a starting capacity of cap
     */
    public PHashtable(int cap) {
        cap = findNearestPrime(cap);
        hashtable = new Patient[cap];
        M = cap;
        N = 0;
    }

    /*
     * Return the Patient with the given name.
     */
    public Patient get(String name) {
        int hash = hash(name);
        int i = 0;
        int curr = hash;
        int attempts = 0;

        while(hashtable[curr] != null && !hashtable[curr].name().equals(name)) {
            curr = (hash+i*i) % M;
            i++;
            attempts++;
            if (attempts > M) {
                return null;
            }
        }
        if (hashtable[curr] == null) {
            return null;
        }
        return hashtable[curr];
    }

    /*
     * Put Patient p into the table.
     */
    public void put(Patient p) {
        if ((double)N / M > 0.5) { 
            resize(M * 2);
        }
        int hash = hash(p.name());
        int i = 0;
        int curr = hash;
        while (hashtable[curr] != null) {
            if (hashtable[curr].name().equals(p.name())) {
                hashtable[curr] = p; 
                return; 
            }
            curr = (hash + i * i) % M; 
            i++;
        }
        hashtable[curr] = p; 
        N++; 
    }
    


    // Hash function used for this hashtable structure
    private int hash(String key) {
        return key.hashCode() & 0x7fffffff % M;
    }

    /*
     * Remove and return the Patient with the given name.
     */
    public Patient remove(String name) {
        if ((double)N/M < .125 && N/2 >= 11) {
            resize(M/2);
        }
        int hash = hash(name);
        int i = 0;
        int curr = hash;
        while(hashtable[curr] != null) {
            curr = (hash+i*i) % M;
            i++;
            if (hashtable[curr] != null && hashtable[curr].name().equals(name)) {
                Patient p = hashtable[curr];
                Patient emptyP = new Patient("", 0, 0); 
                hashtable[curr] = emptyP;
                N--;
                return p;
            }
        }
        return null;
    }

    /*
     * replaces the old table with a table of size 'size' and rehashes all the old 
     * values. Assures that the new table size is prime
     */
    private void resize(int size) {
        int newM;
        if (isPrime(size)) {
            newM = size;
        }
        else {
            newM = findNearestPrime(size);
        }
        M = newM;
        Patient newHashtable[] = new Patient[M];
        for (int i = 0; i< hashtable.length; i++) {
            if (hashtable[i] != null) {
                int hash = hash(hashtable[i].name()) % M;
                if (newHashtable[hash] == null) {
                    newHashtable[hash] = hashtable[i];
                }
                else {
                    int curr = hash;
                    int index = 0;
                    while (newHashtable[curr] != null) {
                        index++;
                        curr = (hash+index*index) % M;
                    }
                    newHashtable[curr] = hashtable[i];
                }
            }
        }
        hashtable = newHashtable;
    }


    // finds the nearest prime to the paramter given
    private int findNearestPrime(int num) {
        if (num % 2 == 0) {
            num++;
        }
        while (!isPrime(num)) {
            num += 2; 
        }
        return num;
    }
    
    // Checks if a number is prime
    private static boolean isPrime(int number) {
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    /*
     * Return the number of patients in the table.
     */
    public int size() {
        return N;
    }

    /*
     * returns the size of the table
     */
    public int tableSize() {
        return M;
    }
}
