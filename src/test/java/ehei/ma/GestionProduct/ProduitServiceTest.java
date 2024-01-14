package ehei.ma.GestionProduct;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ProduitServiceTest {

    private ProduitService produitService;

    @Before
    public void setUp() {
        produitService = new ProduitService();
    }

    @Test
    public void testAjouterProduit() {
        Produit produit = new Produit(1L, "ProduitTest", 10.0, 5);

        try {
            produitService.ajouterProduit(produit);
            Produit retrievedProduit = produitService.getProduit(1L);

            assertNotNull(retrievedProduit);
            assertEquals("ProduitTest", retrievedProduit.getNom());
            assertEquals(10.0, retrievedProduit.getPrix(), 0.001);
            assertEquals(5, retrievedProduit.getQuantite());
        } catch (Exception e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }

    @Test
    public void testAjouterProduitExistingProduct() {
        Produit produit1 = new Produit(1L, "Produit1", 10.0, 5);
        Produit produit2 = new Produit(2L, "Produit2", 15.0, 8);

        try {
            produitService.ajouterProduit(produit1);
            produitService.ajouterProduit(produit2);

            // Try to add a product with the same ID or name
            Produit existingProduct = new Produit(1L, "ProduitUpdated", 20.0, 3);
            produitService.ajouterProduit(existingProduct);

            fail("Expected ProduitExistException, but it did not occur.");
        } catch (ProduitExistException e) {
            assertEquals("Un produit avec le même ID ou nom existe déjà.", e.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAjouterProduitNegativePrice() throws Exception {
        Produit produit = new Produit(1L, "ProduitTest", -10.0, 5);
        produitService.ajouterProduit(produit);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAjouterProduitNegativeQuantity() throws Exception {
        Produit produit = new Produit(1L, "ProduitTest", 10.0, -5);
        produitService.ajouterProduit(produit);
    }

    @Test
    public void testUpdateProduct() {
        Produit produit = new Produit(1L, "ProduitTest", 10.0, 5);

        try {
            produitService.ajouterProduit(produit);

            // Update the product
            Produit updatedProduit = new Produit(1L, "ProduitUpdated", 15.0, 3);
            produitService.updateProduct(updatedProduit);

            Produit retrievedProduit = produitService.getProduit(1L);

            assertNotNull(retrievedProduit);
            assertEquals("ProduitUpdated", retrievedProduit.getNom());
            assertEquals(15.0, retrievedProduit.getPrix(), 0.001);
            assertEquals(3, retrievedProduit.getQuantite());
        } catch (Exception e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void testUpdateNonExistingProduct() throws Exception {
        Produit updatedProduit = new Produit(1L, "ProduitUpdated", 15.0, 3);
        produitService.updateProduct(updatedProduit);
    }

    @Test
    public void testDeleteProduct() {
        Produit produit = new Produit(1L, "ProduitTest", 10.0, 5);

        try {
            produitService.ajouterProduit(produit);
            assertNotNull(produitService.getProduit(1L));

            produitService.deleteProduct(1L);
            assertNull(produitService.getProduit(1L));
        } catch (Exception e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteNonExistingProduct() {
        try {
            assertNull(produitService.getProduit(1L));

            produitService.deleteProduct(1L);
            assertNull(produitService.getProduit(1L));
        } catch (Exception e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }
}
