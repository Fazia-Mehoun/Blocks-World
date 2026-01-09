package blocsworld;

import java.util.*;
import modelling.Variable;
import modelling.BooleanVariable;

public class BlocsWorld {

    protected int nbrBlocs;
    protected int nbrPile;

     
    protected List<Variable> variablesOnb;
    protected List<BooleanVariable> variablesFixedb;
    protected List<BooleanVariable> variablesFreep;

    public BlocsWorld(int nbrBlocs, int nbrPile) {
        this.nbrBlocs = nbrBlocs;
        this.nbrPile = nbrPile;
        variablesOnb = new ArrayList<>();
        variablesFixedb = new ArrayList<>();
        variablesFreep = new ArrayList<>();
        creerVariable();
    }

    /*
     * dans cette classe on va creer l ensemble des variable pour le monde 
     * des bloc et on va les stocker dans des listes 
     */
    public void creerVariable() {

        // on va initialiser des variables onb
        for (int i = 0; i < nbrBlocs; i++) {
            String nomOnb = "On" + i;
            Set<Object> domaineOnb = new HashSet<>();

            // on construit le domaine de onb sauf lui meme
            for (int j = 0; j < nbrBlocs; j++) {
                if (i != j) {
                    domaineOnb.add(j);
                }
            } // Bloc pose sur une pile
            for (int p = 0; p < nbrPile; p++) {
                domaineOnb.add(-(p + 1)); //
            }

            // on ajoute la variable onb dans la liste
            variablesOnb.add(new Variable(nomOnb, domaineOnb));
        }

        // Initialiser des variables fixedb
        for (int i = 0; i < nbrBlocs; i++) {
            variablesFixedb.add(new BooleanVariable("fixed" + i));
        }

        // Initialiser des variables freep (piles libres)
        for (int i = 0; i < nbrPile; i++) {
            variablesFreep.add(new BooleanVariable("free" + i));
        }
    }

    public List<Variable> getVariablesOnb() {
        return variablesOnb;
    }

    public List<BooleanVariable> getVariablesFixedb() {
        return variablesFixedb;
    }

    public List<BooleanVariable> getVariablesFreep() {
        return variablesFreep;
    }
}
