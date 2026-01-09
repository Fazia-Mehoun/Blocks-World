package blocsworld;

import java.util.*;
import modelling.*;

public class BlocsWorldConstraints extends BlocsWorld {

  protected List<Constraint> constraints;

  public BlocsWorldConstraints(int nbrBlocs, int nbrPile) {
    super(nbrBlocs, nbrPile);
    constraints = new ArrayList<>();
    creerContraintes();
  }
 /*
 construire les contraintes binaires assurant la validité des configurations
 */
  public void creerContraintes() {
    // Contrainte1: Les blocs b et b' ne peuvent pas etre sur le meme bloc/pile
    for (int b = 0; b < nbrBlocs; b++) {
      for (int bPrime = b + 1; bPrime < nbrBlocs; bPrime++) {
        // appeller la classe DifferenceConstraint du modelling retourne
        // true si deux var ont des valeurs differente et false si les valeurs sont
        // identiques
        Constraint differenceConstraint = new DifferenceConstraint(variablesOnb.get(b), variablesOnb.get(bPrime));

        constraints.add(differenceConstraint);
      }
    }
    // Contrainte2:ici on va verifier si un bloc b est sur b' alors fixedb'=true
    for (int b = 0; b < nbrBlocs; b++) {
      for (int bPrime = 0; bPrime < nbrBlocs; bPrime++) {
        if (b != bPrime) { 

          Set<Object> domaineOnb = new HashSet<>();
          domaineOnb.add(bPrime); 

          Set<Object> domaineFixedb = new HashSet<>();
          domaineFixedb.add(true); 

          Implication fixedConstraint = new Implication(variablesOnb.get(b), domaineOnb, variablesFixedb.get(bPrime),
              domaineFixedb);
          constraints.add(fixedConstraint);
        }
      }
    }
    // Contrainte3: ici on va verifier si un bloc b est sur p alors freep=false
    for (int b = 0; b < nbrBlocs; b++) {
      for (int p = 0; p < nbrPile; p++) {
        // Si onb a la valeur -(p + 1)=> alors freep doit etre faux
        Set<Object> domaineOnb = new HashSet<>();
        domaineOnb.add(-(p + 1)); 
        Set<Object> domaineFreep = new HashSet<>();
        domaineFreep.add(false); 

        Implication freeConstraint = new Implication(variablesOnb.get(b), domaineOnb, variablesFreep.get(p),
            domaineFreep);
        constraints.add(freeConstraint);
      }
    }
  }

  public List<Constraint>getConstraints() {
    return constraints;
}
}