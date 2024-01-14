package ehei.ma.GestionProduct;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ProduitService {

    private Map<Long, Produit> produits = new HashMap<>();

    // Read
    public Produit getProduit(Long id) {
        return produits.get(id);
    }

    // Create
    public void ajouterProduit(Produit produit) throws ProduitExistException, IllegalArgumentException {
        verify(produit);
        produits.put(produit.getId(), produit);
    }

    private void verify(Produit nouveauProduit) throws ProduitExistException, IllegalArgumentException {
        for (Produit existantProduit : produits.values()) {
            if (existantProduit.getId().equals(nouveauProduit.getId()) || existantProduit.getNom().equals(nouveauProduit.getNom())) {
                throw new ProduitExistException("Un produit avec le même ID ou nom existe déjà.");
            }
        }

        if (nouveauProduit.getPrix() < 0 || nouveauProduit.getQuantite() < 0) {
            throw new IllegalArgumentException("Le prix et la quantité doivent être positifs.");
        }
    }
    
    
    // Update
    public void updateProduct(Produit produit) throws ProduitExistException {
        if (!produits.containsKey(produit.getId())) {
            throw new NoSuchElementException("Produit non trouvé pour l'ID : " + produit.getId());
        }
        verify(produit);
        produits.put(produit.getId(), produit);
    }

    
}
