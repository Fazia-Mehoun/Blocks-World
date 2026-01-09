package blocsworld;

import java.util.*;
import modelling.Variable;
import planning.*;

public class HeuristicBlocs implements Heuristic {
    
    //un objet de type Goal nous permet d acceder au conditions de but 
    private Goal goal;

    
    public HeuristicBlocs(Goal goal) {
        this.goal = goal;
    }

    @Override

/*le but de cette methode estimate est d 'estimer la distance entre l etat actuel
 * et l objectif final cad combien de bloc qui n'ont pas ecore atteint 
 * le emplacement but 
 */

    public double estimate(Map<Variable, Object> state) {

        int nonSatisfiedConditions = 0;

        
        if (goal instanceof BasicGoal) { //verifier que goal est de type BasicGoal
            
            BasicGoal basicGoal = (BasicGoal) goal;

            // Pour chaque condition dans notre  ensemble de GoalConditions on verifie si l etat en appartient 
            for (Map.Entry<Variable, Object> goalCondition : basicGoal.getGoalConditions().entrySet()) {
                Variable var = goalCondition.getKey(); 
                Object value = goalCondition.getValue(); 

                // Vérifie si l'état actuel satisfait la condition de l'objectif
                if (!state.containsKey(var) || !state.get(var).equals(value)) {
                    nonSatisfiedConditions++;  
                }
            }
        }

        //represente  le nombre de conditions non satisfaites
        return nonSatisfiedConditions;
    }
}

