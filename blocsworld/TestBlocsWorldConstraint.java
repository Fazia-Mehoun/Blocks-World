package blocsworld;

import modelling.Variable;
import modelling.Constraint;
import modelling.Implication;
import modelling.*;
import java.util.*;

public class TestBlocsWorldConstraint {
    /*
     * cette methode teste et affiche si une configuration donnée respecte les
     * contraintes de régularité
     */
    public static void main(String[] args) {
        int nbrBloc = 3;
        int nbrPile = 2;

        BlocsWorldConstraints contraintes = new BlocsWorldConstraints(nbrBloc, nbrPile);

        // Création d'une configuration pour tester
        Map<Variable, Object> configuration1 = new HashMap<>();
        configuration1.put(contraintes.getVariablesOnb().get(0), -1);
        configuration1.put(contraintes.getVariablesOnb().get(1), 0);
        configuration1.put(contraintes.getVariablesOnb().get(2), -2);
        configuration1.put(contraintes.getVariablesFixedb().get(0), true);
        configuration1.put(contraintes.getVariablesFixedb().get(1), false);
        configuration1.put(contraintes.getVariablesFixedb().get(2), false);
        configuration1.put(contraintes.getVariablesFreep().get(0), false);
        configuration1.put(contraintes.getVariablesFreep().get(1), false);

        System.out.println("Configuration 1 testée : " + configuration1);
        
        // recuperer les contraintes regulieres
        List<Constraint> contraintesRegulieres = contraintes.getConstraints();

        for (Constraint contrainte : contraintesRegulieres) {
            boolean isSatisfied = contrainte.isSatisfiedBy(configuration1);
            System.out.println("Contrainte : " + contrainte + " -> " + (isSatisfied ? "Satisfaite" : "Non satisfaite"));

        }
    }
}
