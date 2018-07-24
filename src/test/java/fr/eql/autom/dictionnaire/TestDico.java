package fr.eql.autom.dictionnaire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import fr.eql.autom.dictionnaire.exceptions.CategorieNonSupporteeException;
import fr.eql.autom.modele.entrees.Entree;
import fr.eql.autom.modele.proprietes.Categorie;

public class TestDico 
{
	/*
	@Test
	@DisplayName("La methode ajouterEntree doit permettre d'ajouter une entree non nominale")
	void test1() throws CategorieNonSupporteeException
	{
		IDictionnaire iDico = new Dictionnaire();
		Boolean result = iDico.ajouterEntree("Aller", Categorie.VERBE);
		assertTrue(result);
	}
	*/
	
	@ParameterizedTest
	@DisplayName("La methode ajouterEntree doit permettre d'ajouter une entree non nominale")
	@MethodSource("fournirEntreeNonNominale")
	void test1(String identite, Categorie categorie) throws CategorieNonSupporteeException
	{
		IDictionnaire iDico = new Dictionnaire();
		Boolean result = iDico.ajouterEntree(identite, categorie);
		assertTrue(result);
		Dictionnaire dico = (Dictionnaire) iDico;
		Map<String, Entree> mapEntree = dico.entrees;
		assertEquals(1, mapEntree.size());
		Entree entree = mapEntree.get(identite);
		assertNotNull(entree);
		assertEquals(identite, entree.getIdentite());
		assertEquals(categorie, entree.getCategorie());
	}
	
	static Stream<Arguments> fournirEntreeNonNominale()
	{
		return Stream.of(
				Arguments.of("Aller", Categorie.VERBE),
				Arguments.of("Devant", Categorie.ADV),
				Arguments.of("Rapide", Categorie.ADJ));
	}
	
	@Test
	@DisplayName("La méthode ajouterEntree doit retourner une erreur si la catégorie est nominale")
	void test2()throws CategorieNonSupporteeException
	{
		IDictionnaire iDico = new Dictionnaire();
		assertThrows(CategorieNonSupporteeException.class, () -> iDico.ajouterEntree("Toto", Categorie.NOM));
	}
	
	@Test
	@DisplayName("La méthode ajouterEntree ne doit pas créer d’entrée si une entrée de même nom existe déjà")
	void test3()throws CategorieNonSupporteeException
	{
		IDictionnaire iDico = new Dictionnaire();
		Dictionnaire dico = (Dictionnaire) iDico;
		dico.entrees.put("Aller", new Entree("Aller",Categorie.VERBE));
		boolean result = iDico.ajouterEntree("Aller", Categorie.VERBE);
		assertFalse(result);
		Map<String, Entree> mapEntree = dico.entrees;
		assertEquals(1, mapEntree.size());
	}
}
