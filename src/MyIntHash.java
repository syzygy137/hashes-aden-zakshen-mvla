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
		this.mode = mode;
		this.load_factor = load_factor;
		this.tableSize = tableSize;
		if (mode == MODE.LinkedList) {
			hashTableLL = new LinkedList[tableSize];
			initHashTable(hashTableLL);
		} else if (mode == MODE.Cuckoo){
			hashTable1 = new int[tableSize];
			hashTable2 = new int[tableSize];
			initHashTable(hashTable1);
			initHashTable(hashTable2);
		} else {
			hashTable1 = new int[tableSize];
			initHashTable(hashTable1);
		}
		
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
		
		this.mode = mode;
		this.load_factor = load_factor;
		this.tableSize = INITIAL_SIZE;
		if (mode == MODE.LinkedList) {
			hashTableLL = new LinkedList[tableSize];
			initHashTable(hashTableLL);
		} else if (mode == MODE.Cuckoo) {
			hashTable1 = new int[tableSize];
			hashTable2 = new int[tableSize];
			initHashTable(hashTable1);
			initHashTable(hashTable2);
		} else {
			hashTable1 = new int[tableSize];
			initHashTable(hashTable1);
		}
		
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
		for (int i = 0; i < hashTable.length; i++) {
			hashTable[i] = EMPTY;
		}
		size = 0;
	}
	
	/**
	 * Initializes the provided LinkedList<Integer>[] hashTable - setting all entries to null
	 *
	 * @param hashTable the hash table
	 */
	private void initHashTable(LinkedList<Integer>[] hashTable) {
		// TODO Part1: Write this method 
		for (int i = 0; i < hashTable.length; i++) {
			hashTable[i] = null;
		}
		size = 0;
	}
	
	/**
	 * Hash fx.  This is the hash function that translates the key into the index into the hash table.
	 *
	 * @param key the key
	 * @return the int
	 */
	private int hashFx(int key) {
		// TODO Part1: Write this method.
		return key % tableSize;
	}
	
	/**
	 * Hash fx for the cuckoo implementation
	 * @param key the key
	 * @return the int
	 */
	private int hashFx2(int key) {
		// TODO Part1: Write this method.
		return (key / tableSize) % tableSize;
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
		if (contains(key))
			return false;
		if (getCurrLoadFactor() > load_factor) {
			growHash();
		}
		switch (mode) {
			case Linear : return add_LP(key);
			case Quadratic : return add_QP(key);
			case LinkedList : return add_LL(key);
			case Cuckoo : return add_C(key);
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
			case Quadratic : return contains_QP(key);
			case LinkedList : return contains_LL(key);
			case Cuckoo : return contains_C(key);
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
			case Quadratic : return remove_QP(key);
			case LinkedList : return remove_LL(key);
			case Cuckoo : return remove_C(key);
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
		case Linear: growHash(hashTable1, newSize); break;
		case Quadratic: growHash(hashTable1, newSize); break;
		case LinkedList: growHash(hashTableLL, newSize); break;
		case Cuckoo : growHash(hashTable1, hashTable2, newSize); break;
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
		hashTable1 = new int[newSize];
		tableSize = newSize;
		initHashTable(hashTable1);
		for (int thing : table) {
			if (thing >= 0) {
				add(thing);
			}
		}
	}
	
	/**
	 * Grow Hash LL
	 * @param table the table
	 * @param newSize the new size
	 */
	private void growHash(LinkedList<Integer>[] table, int newSize) {
		// TODO Part2:  Write this method
		hashTableLL = new LinkedList[newSize];
		tableSize = newSize;
		initHashTable(hashTableLL);
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				for (int j = 0; j < table[i].size(); j++) {
					add(table[i].get(j));
				}
			}
		}
	}
	
	/**
	 * Grow Hash Cuckoo
	 * @param tables
	 * @param newSize the new size
	 */
	private void growHash(int[] table1, int[] table2, int newSize) {
		// TODO Part2:  Write this method
		hashTable1 = new int[newSize];
		hashTable2 = new int[newSize];
		tableSize = newSize;
		initHashTable(hashTable1);
		initHashTable(hashTable2);
		for (int i = 0; i < table1.length; i++) {
			if (table1[i] != EMPTY) {
				add(table1[i]);
			}
			if (table2[i] != EMPTY) {
				add(table2[i]);
			}
		}
	}
	
	/**
	 * Gets the new table size. Finds the next prime number
	 * that is greater than 2x the passed in size (startSize)
	 *
	 * @param startSize the start size
	 * @return the new table size
	 */
	private int getNewTableSize(int startSize) {
		int size;
		if (mode == MODE.Cuckoo) {
			size = startSize + 1000;
			while (!isPrime(size)) {
				size += 2;
			}
			return size;
		}
		size = startSize * 2 + 1;
		while (!isPrime(size)) {
			size += 2;
		}
		return size;
	}
	

	
	/**
	 * Checks if is prime.  
	 *
	 * @param size the size
	 * @return true, if is prime
	 */
	private boolean isPrime(int size) {
		// TODO Part2: Write this method
		if (size < 3) {
			return false;
		}
		for (int i = 2; i <= Math.sqrt(size); i++) {
			if (size % i == 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 *
	 * @param index the index
	 * @return the next index, wrapping around
	 */
	
	private int wrap(int index) {
		return (index >= tableSize - 1) ? 0 : index + 1;
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
		int index = hashFx(key);
		if (hashTable1[index] == EMPTY || hashTable1[index] == REMOVED) {
			hashTable1[index] = key;
			size++;
			return true;
		}
		for (int i = wrap(index); i != index && i < tableSize;) {
			if (hashTable1[i] == EMPTY || hashTable1[i] == REMOVED) {
				hashTable1[i] = key;
				size++;
				return true;
			}
			i = wrap(i);
		}
		return false;
	}
	
	/**
	 * Adds the key using the Quadratic probing strategy:
	 * 
	 * 1) Find the first empty slot quadratically, starting at the index from hashFx(key)
	 * 2) Update the hash table with the key
	 * 3) increment the size
	 * 
	 * If no empty slots are found, return false - this would indicate that the hash needs to grow...
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	private boolean add_QP(int key) {
		if (tableSize == size) {
			growHash(hashTable1, getNewTableSize(tableSize));
		}
		int index;
		for (int i = 0; i < tableSize / 2; i++) {
			index = key + i * i;
			index = hashFx(index);
			if (hashTable1[index] == EMPTY || hashTable1[index] == REMOVED) {
				hashTable1[index] = key;
				size++;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Adds the key using the LL strategy
	 * 
	 * If no empty slots are found, return false - this would indicate that the hash needs to grow...
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	private boolean add_LL(int key) {

		if (hashTableLL[hashFx(key)] == null) {
			hashTableLL[hashFx(key)] = new LinkedList<Integer>();
		}
		hashTableLL[hashFx(key)].add(key);
		size++;
		return true;
	}
	
	/**
	 * Adds the key using the Cuckoo strategy
	 * 
	 * If no empty slots are found, return false - this would indicate that the hash needs to grow...
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	private boolean add_C(int key) {
		System.out.println("Adding " + key);
		if (hashTable1[hashFx(key)] == EMPTY) {
			hashTable1[hashFx(key)] = key;
			System.out.println(hashFx(key));
			size++;
			return true;
		} else {
			while (place(key, true, key) == false) {
				System.out.println("growing");
				growHash(hashTable1, hashTable2, getNewTableSize(tableSize));
			}
			return true;
		}
	}
	
	
	/**
	 * Places the key
	 * 
	 *
	 * @param key, table, initial key
	 * @return true, if successful false if loop detected
	 */
	public boolean place(int key, boolean table1, int init) {
		if (table1) {
			if (hashTable1[hashFx(key)] == EMPTY) {
				hashTable1[hashFx(key)] = key;
				size++;
				return true;
			} else {
				int evictedKey = hashTable1[hashFx(key)];
				hashTable1[hashFx(key)] = key;
				return place(evictedKey, false, init);
			}
		} else {
			if (hashTable2[hashFx2(key)] == EMPTY) {
				hashTable2[hashFx2(key)] = key;
				size++;
				return true;
			} else {
				if (key == init) {
					return false;
				}
				int evictedKey = hashTable2[hashFx2(key)];
				hashTable2[hashFx2(key)] = key;
				return place(evictedKey, true, init);
			}
		}
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
		int index = hashFx(key);
		if (hashTable1[index] == key) {
			return true;
		}
		for (int i = wrap(index); i != index;) {
			if (hashTable1[i] == EMPTY) {
				break;
			}
			if (hashTable1[i] == key) {
				return true;
			}
			i = wrap(i);
		}

		return false;
	}
	
	/**
	 * Contains - uses the Quadratic Probing method to determine if the key exists in the hash
	 * 
	 * If no matches found after walking through the entire table, return false
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	private boolean contains_QP(int key) {
		// TODO Part1: Write this method.
		int index;
		for (int i = 0; i < tableSize / 2; i++) {
			index = key + i * i;
			index = hashFx(index);
			if (hashTable1[index] == EMPTY) {
				break;
			}
			if (hashTable1[index] == key) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * Contains - uses the LL method to determine if the key exists in the hash
	 * 
	 * If no matches found after walking through the entire table, return false
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	private boolean contains_LL(int key) {
		if (hashTableLL[hashFx(key)] == null)
			return false;
		for (int i = 0; i < hashTableLL[hashFx(key)].size(); i++) {
			if (hashTableLL[hashFx(key)].get(i) == key)
				return true;
		}

		return false;
	}
	
	/**
	 * Contains - uses the cuckoo method to determine if the key exists in the hash
	 * 
	 * If no matches found after walking through the entire table, return false
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	private boolean contains_C(int key) {
		return (hashTable1[hashFx(key)] == key || hashTable2[hashFx2(key)] == key);
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
		int index = hashFx(key);
		int next;
		if (hashTable1[index] == key) {
			next = hashTable1[wrap(index)];
			hashTable1[index] = (next == EMPTY) ? EMPTY : REMOVED;
			size--;
			return true;
		}
		for (int i = wrap(index); i != index;) {
			if (hashTable1[i] == key) {
				next = hashTable1[wrap(i)];
				hashTable1[i] = (next == EMPTY) ? EMPTY : REMOVED;
				size--;
				return true;
			}
			i = wrap(i);
		}
		return false;
	}
	
	/**
	 * Remove - uses the Quadratic Problem method to evict a key from the hash, if it exists
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	private boolean remove_QP(int key) {
		int index;
		for (int i = 0; i < tableSize / 2; i++) {
			index = key + i * i;
			index = hashFx(index);
			if (hashTable1[index] == EMPTY) {
				break;
			}
			if (hashTable1[index] == key) {
				hashTable1[index] = REMOVED;
				size--;
				return true;
			}
		}

		return false;
	}
	
	/**
	 * Remove - uses the LL method to remove a key from the hash, if it exists
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	private boolean remove_LL(int key) {
		LinkedList<Integer> LL = hashTableLL[hashFx(key)];
		for (int i = 0; i < LL.size(); i++) {
			if (LL.get(i) == key) {
				LL.remove(i);
				size--;
				if (LL.isEmpty()) {
					hashTableLL[hashFx(key)] = null;
				}
				return true;
			}
		}

		return false;
	}
	
	/**
	 * Remove - uses the Cuckoo method to remove a key from the hash, if it exists
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	private boolean remove_C(int key) {
		if (hashTable1[hashFx(key)] == key) {
			hashTable1[hashFx(key)] = EMPTY;
			size--;
			return true;
		}
		if (hashTable2[hashFx2(key)] == key) {
			hashTable2[hashFx2(key)] = EMPTY;
			size--;
			return true;
		}
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
		case Linear : return hashTable1[index];
		case Quadratic : return hashTable1[index];
		case LinkedList : 
			if (hashTableLL[index] == null) return null;
			return offset >= hashTableLL[index].size() ?  EMPTY : hashTableLL[index].get(offset);
		case Cuckoo :
			return offset == 0 ? hashTable1[index] : hashTable2[index];
		}
		return EMPTY;
	}
	
	/**
	 * Gets the number of elements in the Hash
	 *
	 * @return size
	 */
	public int size() {
		// TODO Part1: Write this method
		return size;
	}

	/**
	 * resets all entries of the hash to -1. This should reuse existing code!!
	 *
	 */
	public void clear() {
		// TODO Part1: Write this method
		if (mode == MODE.LinkedList) {
			initHashTable(hashTableLL);
		} else {
			initHashTable(hashTable1);
			if (mode == MODE.Cuckoo) {
				System.out.println("cuckoo");
				initHashTable(hashTable2);
			}
		}
	}

	/**
	 * Returns a boolean to indicate of the hash is empty
	 *
	 * @return ????
	 */
	public boolean isEmpty() {
		// TODO Part1: Write this method
		return (size == 0);
	}

	/**
	 * return the calculated loading based upon the number of entries, size and number
	 * of hashTables.
	 * @return a double representing the loading.
	 */
	public double getCurrLoadFactor() {
		// TODO: write this method
		return (mode == MODE.Cuckoo) ? (double)(size + 1) / (double)(tableSize * 2) : 
			(double)(size + 1) / (double)(tableSize);
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
