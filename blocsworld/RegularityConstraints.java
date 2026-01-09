package blocsworld;

import java.util.*;
import modelling.*;

public class RegularityConstraints extends BlocsWorldConstraints {

   
    protected List<Constraint> constrainteReguliere = new ArrayList<>();

    public RegularityConstraints(int numberOfBlocks, int numberOfPiles) {
        super(numberOfBlocks, numberOfPiles);
        creerContrainteReguliere();
    }

    /*Cette methode  a pour but de créer une configuration régulière 
    en maintenant un écart constant entre chaque deux bloc pour éviter l' effet circulaire .
     */
    public void creerContrainteReguliere() {

        List<Variable> blocsVariables = getVariablesOnb(); 

       
        for (Variable bloc1 : blocsVariables) {
            int numBloc1 = Integer.parseInt(bloc1.getName().substring(2));

            for (Variable bloc2 : blocsVariables) {
                int numBloc2 = Integer.parseInt(bloc2.getName().substring(2));

                if (numBloc1 != numBloc2) { 
                    
                    int ecartAttendu = Math.abs(numBloc1 - numBloc2);

                    for (Variable bloc3 : blocsVariables) {
                     
                        int numBloc3 = Integer.parseInt(bloc3.getName().substring(2));

                        if(numBloc1!=numBloc3 && numBloc2!=numBloc3){
                       
                        if (ecartAttendu == Math.abs(numBloc2-numBloc3)) {
                            Set<Object> DomaineB2 = new HashSet<>();

                            
                            for (BooleanVariable pile : getVariablesFreep()) {
                                DomaineB2.add(-(Integer.parseInt(pile.getName().substring(4))+1));
                            }

                            DomaineB2.add(numBloc3); 

                            /*  si le bloc1 est pose sur le bloc 2 Alors le bloc2  doit etre 
                            pose sur bloc3 (qui garde le meme ecart) ou quelque soit la pile*/    
                            Implication constraint = new Implication(bloc1, Set.of(numBloc2), bloc2, DomaineB2);
                         constrainteReguliere.add(constraint); 
                        }
                      }
                  }
                }
            }
        }
    }

    
    public List<Constraint> getContraintesRegulieres() {
        return constrainteReguliere;
    }
}
