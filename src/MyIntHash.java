import java.util.LinkedList;

// TODO: Auto-generated Javadoc
/**
 * The Class MyIntHash.
 */
public class MyIntHash {
	
	/**
	 * The Enum MODE.
	 */
	enum MODE {Linear, Quadratic,  LinkedList,  Cuckoo};
	
	/** The Constant INITIAL_SIZE. */
	private final static int INITIAL_SIZE = 31;
	
	/** constant to indicate that the hash entry is empty */
	private final int EMPTY = -1;
	
	/** constant to indicate that the hash entry has been removed,
	 *  but is available for placing a new key
	 */
	private final int REMOVED = -2;
	
	/** The mode of operation. */
	private MODE mode = MODE.Linear;
	
	/** The physical table size. */
	private int tableSize;
	
	/** The size of the hash - the number of elements in the hash. */
	private int size;
	
	/** The load factor. When the hash utilization exceeds this factor, the hash will 
	 *  automatically grow.  */
	private double load_factor; 
	
	/** The hash table 1. */
	private int[] hashTable1;
	
	// The following variables will be defined but not used until later in the project..
	/** Multiplier for the number of tables */
	private int mult_factor;

	/** The hash table 2. */
	private int[] hashTable2;
	
	/** The hash table LL. */
	private LinkedList<Integer>[] hashTableLL;

	/** Loop limit for quadratic probing before growing the hash */
	private int max_QP_LOOP;

	/** constant to limit QP searching so that it doesn't overflow */
	private final int MAX_QP_OFFSET = 2<<15;
	
	/** constant to control printing of debug information */
	private final boolean DEBUG=true;

	
	/**
	 * Instantiates a new my int hash. For Part1 JUnit Testing, the load_factor will be set to 1.0
	 *
	 * @param mode the mode
	 * @param load_factor the load factor
	 * @param tableSize - the initial size of the hashTable
	 */
	public MyIntHash(MODE mode, double load_factor, int tableSize) {
		// TODO Part1: initialize table size, size, mode, and load_factor
		//             Instantiate hashTable1 and initialize it
	}

	/**
	 * Instantiates a new my int hash - using INITIAL_SIZE as the tableSize 
	 *
	 * @param mode the mode
	 * @param load_factor the load factor
	 */
	public MyIntHash(MODE mode, double load_factor) {

		// TODO Part1: initialize table size, size, mode, and load_factor
		//             Instantiate hashTable1 and initialize it
	}

	/**
	 * Initializes the provided int[] hashTable - setting all entries to -1
	 * Note that this function will be overloaded to initialize hash tables in other modes
	 * of operation. This method should also reset size to 0!
	 *
	 * @param hashTable the hash table
	 */
	private void initHashTable(int[] hashTable) {
		// TODO Part1: Write this method 
	}
	
	/**
	 * Hash fx.  This is the hash function that translates the key into the index into the hash table.
	 *
	 * @param key the key
	 * @return the int
	 */
	private int hashFx(int key) {
		// TODO Part1: Write this method.
		return -1;
	}
	
	/**
	 * Adds the key to the hash table. Note that this is a helper function that will call the 
	 * required add function based upon the operating mode. However, before calling the specific
	 * add function, determine if the hash should be resized; if so, grow the hash.
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	public boolean add(int key) {
		
		// TODO: Part2 - if adding this key would cause the the hash load to exceed the load_factor, grow the hash.
		//      Note that you cannot just use size in the numerator... 
		//      Write the code to implement this check and call growHash() if required (no parameters)
		
		switch (mode) {
			case Linear : return add_LP(key); 
			default : return false;
		}
	}
	
	/**
	 * Contains. Note that this is a helper function that will call the 
	 * required contains function based upon the operating mode
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	public boolean contains(int key) {
		switch (mode) {
			case Linear : return contains_LP(key); 
			default : return false;
		}
	}
	
	/**
	 * Remove. Note that this is a helper function that will call the 
	 * required remove function based upon the operating mode
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	public boolean remove(int key) {
		switch (mode) {
			case Linear : return remove_LP(key); 
			default : return false;
		}
	}
	
	/**
	 * Grow hash. Note that this is a helper function that will call the 
	 * required overloaded growHash function based upon the operating mode.
	 * It will get the new size of the table, and then grow the Hash. Linear case
	 * is provided as an example....
	 */
	private void growHash() {
		int newSize = getNewTableSize(tableSize);
		switch (mode) {
		case Linear: growHash(hashTable1,newSize); break;
		}
	}
	
	/**
	 * Grow hash. This the specific function that will grow the hash table in Linear or 
	 * Quadratic modes. This method will:
	 * 	1. save the current hash table, 
	 *  2. create a new version of hashTable1
	 *  3. update tableSize and size
	 *  4. add all valid entries from the old hash table into the new hash table
	 * 
	 * @param table the table
	 * @param newSize the new size
	 */
	private void growHash(int[] table, int newSize) {
		// TODO Part2:  Write this method
	}
	
	/**
	 * Gets the new table size. Finds the next prime number
	 * that is greater than 2x the passed in size (startSize)
	 *
	 * @param startSize the start size
	 * @return the new table size
	 */
	private int getNewTableSize(int startSize) {
		// TODO Part2: Write this method
		return -1;
	}
	
	/**
	 * Checks if is prime.  
	 *
	 * @param size the size
	 * @return true, if is prime
	 */
	private boolean isPrime(int size) {
		// TODO Part2: Write this method
		return false;
	}
	
	/**
	 * Adds the key using the Linear probing strategy:
	 * 
	 * 1) Find the first empty slot sequentially, starting at the index from hashFx(key)
	 * 2) Update the hash table with the key
	 * 3) increment the size
	 * 
	 * If no empty slots are found, return false - this would indicate that the hash needs to grow...
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	private boolean add_LP(int key) {
		// TODO Part1: Write this function
		return false;
	}
	
	/**
	 * Contains - uses the Linear Probing method to determine if the key exists in the hash
	 * A key condition is that there are no open spaces between any values with collisions, 
	 * independent of where they are stored.
	 * 
	 * Starting a the index from hashFx(key), sequentially search through the hash until:
	 * a) the key matches the value at the index --> return true
	 * b) there is no valid data at the current index --> return false
	 * 
	 * If no matches found after walking through the entire table, return false
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	private boolean contains_LP(int key) {
		// TODO Part1: Write this method.
		return false;
	}
	
	/**
	 * Remove - uses the Linear Problem method to evict a key from the hash, if it exists
	 * A key requirement of this function is that the evicted key cannot introduce an open space
	 * if there are subsequent values which had collisions...
	 * 
	 * 1) Identify if the key exists by walking sequentially through the hash table, starting at hashFx(key) 
	 *    - if not return false,
	 * 2) Once the key is found at an index, the hashTable entry at that index will be marked either
	 *    as REMOVED or as EMPTY. If the next incremental index is EMPTY, then the mark the removed entry as 
	 *    EMPTY; otherwise, mark it as removed. This is required to support the assumptions of the contains_LP method.
	 * 3) decrement size and return true.
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	private boolean remove_LP(int key) {
		// TODO Part2: Write this function
		return false;		
	}
		
	/**
	 * Gets the hash at. Returns the value of the hash at the specified index, and (if required by the operating mode) 
	 * the specified offset.
	 * 
	 * Use a switch statement to implement this code. This is FOR DEBUG AND TESTING PURPOSES ONLY
	 * 
	 * @param index the index
	 * @param offset the offset
	 * @return the value of the hash at the specified index,offset (if applicable) as an Integer (required for LL)
	 */
	Integer getHashAt(int index, int offset) {
		// TODO Part1: as you code this project, you will add different cases. 
		//             for now, complete the case for Linear Probing
		switch (mode) {
		//case Linear : return // What needs to go here??? write this and uncomment
		}
		return -1;
	}
	
	/**
	 * Gets the number of elements in the Hash
	 *
	 * @return size
	 */
	public int size() {
		// TODO Part1: Write this method
		return -1;
	}

	/**
	 * resets all entries of the hash to -1. This should reuse existing code!!
	 *
	 */
	public void clear() {
		// TODO Part1: Write this method
	}

	/**
	 * Returns a boolean to indicate of the hash is empty
	 *
	 * @return ????
	 */
	public boolean isEmpty() {
		// TODO Part1: Write this method
		return true;
	}

	/**
	 * return the calculated loading based upon the number of entries, size and number
	 * of hashTables.
	 * @return a double representing the loading.
	 */
	public double getCurrLoadFactor() {
		// TODO: write this method
		return 1.0;
	}

	/**
	 * Gets the load factor.
	 *
	 * @return the load factor
	 */
	public double getLoad_factor() {
		return load_factor;
	}

	/**
	 * Sets the load factor.
	 *
	 * @param load_factor the new load factor
	 */
	public void setLoad_factor(double load_factor) {
		this.load_factor = load_factor;
	}

	/**
	 * Gets the table size.
	 *
	 * @return the table size
	 */
	public int getTableSize() {
		return tableSize;
	}

}
