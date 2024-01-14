package ehei.ma.GestionProduct;

import java.util.HashMap;
import java.util.Map;

public class ProduitService {

	 private Map<Long, Produit> produits = new HashMap<>();

	 // Read
	    public Produit getProduit(Long id) {
	        return produits.get(id);
	    }

	
}
