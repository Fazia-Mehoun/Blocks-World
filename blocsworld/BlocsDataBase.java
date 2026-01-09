
package blocsworld;

import java.util.*;
import modelling.BooleanVariable;

public class BlocsDataBase {

    private List<BooleanVariable> VariablesOnTables;
    private List<BooleanVariable> VariablesOnBB;
    private List<BooleanVariable> VariablesFixed;
    private List<BooleanVariable> VariablesFreep;

   
    private int nbrBlocs;
    private int nbrPile;

    public BlocsDataBase(int nbrBlocs, int nbrPile) {
        VariablesOnTables = new ArrayList<>();
        VariablesOnBB = new ArrayList<>();
        VariablesFixed = new ArrayList<>();
        VariablesFreep =new ArrayList<>();
        this.nbrBlocs = nbrBlocs;
        this.nbrPile = nbrPile;
    }

    // Créer les variables pour la base de données des blocs
    public void creerVariables() {

        // on initialise les variables onBB pour chaque couple de blocs différents {b, b'}
        for (int b = 0; b < nbrBlocs; b++) {
            for (int bPrime = 0; bPrime < nbrBlocs; bPrime++) {
                if (b != bPrime) {  
                    String nomOnBB = "on" + b + "_" + bPrime;
                    
                    VariablesOnBB.add(new BooleanVariable(nomOnBB));
                }
            }
        }

        // on initialise des variables onTable pour chaque bloc b dans chaque pile p
        for (int b = 0; b < nbrBlocs; b++) {
            for (int p = 0; p < nbrPile; p++) {
                String nomOnTable = "onTable" + b + "_" + p;
                
                VariablesOnTables.add(new BooleanVariable(nomOnTable));
            }
        }

        // on initialise des variables fixed pour chaque bloc b fixe
        for (int b = 0; b < nbrBlocs; b++) {
            VariablesFixed.add(new BooleanVariable("fixed" + b));
        }

        // on initialise des variables free pour chaque pile 
        for (int p = 0; p < nbrPile; p++) {
            VariablesFreep.add(new BooleanVariable("free" + p));
        }
    }

/* cette methode sert a extraire les variables booléennes
 correspondant à un état donné du monde
 */
    
 public Set<BooleanVariable> extraitreBoolVariable(List<List<Integer>> etat) {
    Set<BooleanVariable> booleanVariables =  new HashSet<>();
    int indicePile = 0;

  
    for (List<Integer> pile : etat) {
        extraireVariablesDansPile(pile, indicePile, booleanVariables);
        indicePile++;
    }

    return booleanVariables;
 }

 // Méthode pour extraire les variables d'une pile donnée
 private void extraireVariablesDansPile(List<Integer> pile, int indicePile, Set<BooleanVariable> booleanVariables) {
    if (pile.isEmpty()) {
        // Ajouter la pile vide a l ensemble des variables booleen
        booleanVariables.add(VariablesFreep.get(indicePile));
    } else {
       
        int blocBas = pile.get(0);
        int blocHaut = pile.get(pile.size() - 1);

        
        if (pile.size() > 1) {
            // Ajout de la variable fixed pour le bloc en bas de la pile
            booleanVariables.add(new BooleanVariable("fixed" + blocBas));

            // Ajout de la relation on entre le bloc du haut et le bloc en dessous
            booleanVariables.add(new BooleanVariable("on" + blocHaut + "_" + pile.get(pile.size() - 2)));
        }

        // Ajout de la relation onTable pour le bloc en bas de la pile
        booleanVariables.add(new BooleanVariable("onTable" + blocBas + "_" + indicePile));

        // Parcours des autres blocs dans la pile sauf le bas et le haut 
        for (int i = 1; i <= pile.size() - 2; i++) {
            booleanVariables.add(new BooleanVariable("fixed" + pile.get(i)));
            booleanVariables.add(new BooleanVariable("on" + pile.get(i) + "_" + pile.get(i - 1)));
        }
    }
}


   

   
    public List<BooleanVariable> getVariablesOnBB() {
        return VariablesOnBB;
    }

    public List<BooleanVariable> getVariablesOnTables() {
        return VariablesOnTables;
    }

    public List<BooleanVariable> getVariablesFixed() {
        return VariablesFixed;
    }

    public List<BooleanVariable> getVariablesFreep() {
        return VariablesFreep;
    }
}