package blocsworld;
import modelling.Variable;
import modelling.Constraint;
import modelling.Implication;
import modelling.*;
import java.util.*;
public class TestCroissante {
    public static void main(String[] args) {
        int nbrBloc = 3;
        int nbrPile = 2;

       
        CroissanteConstraints croissanteConstraints = new CroissanteConstraints(nbrBloc, nbrPile);

        //Creation de la configuration 
        Map<Variable, Object> configuration1 = new HashMap<>();
        configuration1.put(croissanteConstraints.getVariablesOnb().get(0), -1);
        configuration1.put(croissanteConstraints.getVariablesOnb().get(1), 0);
        configuration1.put(croissanteConstraints.getVariablesOnb().get(2), -2);
        configuration1.put(croissanteConstraints.getVariablesFixedb().get(0), true);
        configuration1.put(croissanteConstraints.getVariablesFixedb().get(1), true);
        configuration1.put(croissanteConstraints.getVariablesFixedb().get(2), true);
        configuration1.put(croissanteConstraints.getVariablesFreep().get(0), false);
        configuration1.put(croissanteConstraints.getVariablesFreep().get(1), false);

        
        System.out.println("Configuration 1 testée : " + configuration1);
        
        
        
         List<Constraint> contraintesCroissantes = croissanteConstraints.getContraintesCroissantes();
 
         
         for (Constraint contrainte : contraintesCroissantes) {
             
             boolean isSatisfied = contrainte.isSatisfiedBy(configuration1);
 
            
             System.out.println("Contrainte : " + contrainte + " -> " + (isSatisfied ? "Satisfaite" : "Non satisfaite"));
 
            
                 }
    }
}



    
    
    
    
    
    
    

