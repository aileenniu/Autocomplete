import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Collections;

public class HashListAutocomplete implements Autocompletor {

    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap;
    private int mySize; 

    public HashListAutocomplete(String[] terms, double[] weights) {
        if (terms == null || weights == null) {
			throw new NullPointerException("One or more arguments null");
		}

		if (terms.length != weights.length) {
			throw new IllegalArgumentException("terms and weights are not the same length");
		}
		initialize(terms,weights);
    }

    @Override
    public List<Term> topMatches(String prefix, int k) {
        // TODO Auto-generated method stub
        if (prefix.length()>MAX_PREFIX) {
            prefix = prefix.substring(0, MAX_PREFIX);
        }
        if (myMap.containsKey(prefix)) {
            List<Term> all = myMap.get(prefix);
            List<Term> list = all.subList(0, Math.min(k, all.size()));
            return list;
        }
        ArrayList<Term> al = new ArrayList<>();
        return al;
    }

    @Override
    public void initialize(String[] terms, double[] weights) {
        myMap = new HashMap<String, List<Term>>();
        int j = 0;
        for(String s : terms){
            mySize+=BYTES_PER_CHAR*s.length();
            mySize+=BYTES_PER_DOUBLE;
            for(int i = 0; i<=Math.min(MAX_PREFIX,s.length()); i++){
                if(!myMap.containsKey(s.substring(0, i))){
                    myMap.put(s.substring(0, i), new ArrayList<Term>());
                    mySize+=BYTES_PER_CHAR*(i-0);
                }
                myMap.get(s.substring(0, i)).add(new Term(s, weights[j]));
            }
            j++;
        }
        for(String s : myMap.keySet()){
            Collections.sort(myMap.get(s), Comparator.comparing(Term::getWeight).reversed());
        }
    }

    @Override
    public int sizeInBytes() {
        // TODO Auto-generated method stub
        /* 
        if (mySize == 0) {
            for (List<Term> list : myMap.values()) {
                for(Term t : list) {
                    mySize += BYTES_PER_DOUBLE + 
                            BYTES_PER_CHAR*t.getWord().length();	
                }
            }
            return mySize; 
        }
        else {
            return mySize;
        }
        */
        return mySize;
    }  
}

