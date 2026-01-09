package blocsworld;

import java.util.*;
import modelling.Variable;
import modelling.Constraint;
import modelling.UnaryConstraint;

public class CroissanteConstraints extends BlocsWorldConstraints {
 
    protected List<Constraint> constrainteCroissantes= new ArrayList<>();

    public CroissanteConstraints(int nbrBlocs, int nbrPile) {
        super(nbrBlocs, nbrPile);
        creerContraintesCroissantes();
    }

    /*
     * Cette méthode crée les contraintes pour s'assurer qu'un bloc ne peut être
     * placé que sur un autre bloc de numéro plus petit (ou directement sur la table).
     */
    public void creerContraintesCroissantes() {
        
        for (Variable b1 : getVariablesOnb()) {
            // Récupérer le numéro du bloc b1 
            int numeroB1 = Integer.parseInt(b1.getName().substring(2));  

            // on récupére le domaine initial du bloc `b1`
            Set<Object> domaineInitial = b1.getDomain();
    
            /*on crre un domaine restreint qui ne contient que les valeurs inférieures à numeroB1  ou des valeurs negatives*/
            Set<Object> domaineRestreint = new HashSet<>();
            for (Object valeur : domaineInitial) {
                if (valeur instanceof Integer) {
                    int val = (Integer) valeur;
                    if (val < numeroB1 || val < 0) {
                        domaineRestreint.add(val); 
                    }
                }
            }
    
            // on applique le UnaryConstraint pour restreindre le domaine de b1
            if (!domaineRestreint.isEmpty()) {
                UnaryConstraint contrainteCroissante = new UnaryConstraint(b1, domaineRestreint);
                constrainteCroissantes.add(contrainteCroissante);  
            }
        }
    }

    
    public List<Constraint> getContraintesCroissantes() {
        return constrainteCroissantes;
    }
}


