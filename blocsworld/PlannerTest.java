package blocsworld;

import planning.*;
import modelling.Variable;
import modelling.*;
import java.util.*;

/**
 * Cette classe permet de tester les plannificateurs dans le contexte de
 * BlocsWorld.
 */
public class PlannerTest {
    public static void main(String[] args) {

        
        ActionBlocsWorld monde = new ActionBlocsWorld(6, 3);

        Set<Action> actions = monde.getActions();

        
        Map<Variable, Object> etat_initial = new HashMap<>();

       //Ajouter les variable Onb
        etat_initial.put(monde.variablesOnb.get(0), -1);
        etat_initial.put(monde.variablesOnb.get(1), 0);
        etat_initial.put(monde.variablesOnb.get(2), 1);
        etat_initial.put(monde.variablesOnb.get(3), -2);
        etat_initial.put(monde.variablesOnb.get(4), -3);
        etat_initial.put(monde.variablesOnb.get(5), 4);

        // Ajouter les variables fixed 
        etat_initial.put(monde.variablesFixedb.get(0), true);
        etat_initial.put(monde.variablesFixedb.get(1), true);
        etat_initial.put(monde.variablesFixedb.get(2), false);
        etat_initial.put(monde.variablesFixedb.get(3), false);
        etat_initial.put(monde.variablesFixedb.get(4), true);
        etat_initial.put(monde.variablesFixedb.get(5), false);

        // Ajouter les variables free 
        etat_initial.put(monde.variablesFreep.get(0), false);
        etat_initial.put(monde.variablesFreep.get(1), false);
        etat_initial.put(monde.variablesFreep.get(2), false);

        // état initial
        System.out.println("État initial :");


        System.out.println("Variables 'On':");
        for (Map.Entry<Variable, Object> entry : etat_initial.entrySet()) {
            Variable var = entry.getKey();
            if (var.getName().startsWith("On")) {
                System.out.println("  " + var.getName() + " = " + entry.getValue());
            }
        }
        
        System.out.println("Variables 'Fixed':");
        for (Map.Entry<Variable, Object> entry : etat_initial.entrySet()) {
            Variable var = entry.getKey();
            if (var.getName().startsWith("fixed")) {
                System.out.println("  " + var.getName() + " = " + entry.getValue());
            }
        }
        
        System.out.println("Variables 'Free':");
        for (Map.Entry<Variable, Object> entry : etat_initial.entrySet()) {
            Variable var = entry.getKey();
            if (var.getName().startsWith("free")) {
                System.out.println("  " + var.getName() + " = " + entry.getValue());
            }
        }

        // Etat but
        Map<Variable, Object> goalConditions = new HashMap<>();

        goalConditions.put(monde.variablesOnb.get(0), -1);
        goalConditions.put(monde.variablesOnb.get(1), 0);
        goalConditions.put(monde.variablesOnb.get(2), 3);
        goalConditions.put(monde.variablesOnb.get(3), -2);
        goalConditions.put(monde.variablesOnb.get(4), -3);
        goalConditions.put(monde.variablesOnb.get(5), 4);

        goalConditions.put(monde.variablesFixedb.get(0), true);
        goalConditions.put(monde.variablesFixedb.get(1), false);
        goalConditions.put(monde.variablesFixedb.get(2), false);
        goalConditions.put(monde.variablesFixedb.get(3), true);
        goalConditions.put(monde.variablesFixedb.get(4), true);
        goalConditions.put(monde.variablesFixedb.get(5), false);

        goalConditions.put(monde.variablesFreep.get(0), false);
        goalConditions.put(monde.variablesFreep.get(1), false);
        goalConditions.put(monde.variablesFreep.get(2), false);

      
        Goal goal = new BasicGoal(goalConditions);

        Heuristic heuristic = new HeuristicBlocs(goal);
        DFSPlanner dFSPlanner = new DFSPlanner(etat_initial, actions, goal);
        BFSPlanner bFSPlanner = new BFSPlanner(etat_initial, actions, goal);
        DijkstraPlanner dijkstraPlanner = new DijkstraPlanner(etat_initial, actions, goal);
        AStarPlanner astarPlanner = new AStarPlanner(etat_initial, actions, goal, heuristic);
        // activer le comptage
        dFSPlanner.activateNodeCount(true);
        bFSPlanner.activateNodeCount(true);
        dijkstraPlanner.activateNodeCount(true);
        astarPlanner.activateNodeCount(true);

        // Mettre les planificateurs dans une liste
        List<Planner> planerList = new ArrayList<>();
        planerList.add(dFSPlanner);
        planerList.add(bFSPlanner);
        planerList.add(dijkstraPlanner);
        planerList.add(astarPlanner);

        for (Planner planner : planerList) {
            long debut = System.currentTimeMillis();
            List<Action> plan = planner.plan();
            long fin = System.currentTimeMillis();
            float temps = (fin - debut);

           
            System.out.println("\n====================");
            System.out.println("Planificateur : " + planner.getClass().getSimpleName());
            System.out.println("====================");

            // Afficher le temps d'exécution
            System.out.printf("Temps d'exécution : %.2f ms\n", temps);

            // Afficher le nombre de noeuds explorés selon le type de planificateur
            if (planner instanceof DFSPlanner) {
                System.out.println("Noeuds explorés dans DFS : " + ((DFSPlanner) planner).getCountnoeudsExplores());
            }
            if (planner instanceof BFSPlanner) {
                System.out.println("Noeuds explorés dans BFS : " + ((BFSPlanner) planner).getCountnoeudsExplores());
            }
            if (planner instanceof DijkstraPlanner) {
                System.out.println(
                        "Noeuds explorés dans Dijkstra : " + ((DijkstraPlanner) planner).getCountnoeudsExplores());
            }
            if (planner instanceof AStarPlanner) {
                System.out.println("Noeuds explorés dans A* : " + ((AStarPlanner) planner).getCountnoeudsExplores());
            }

            // Afficher le nombre d'actions dans le plan
            System.out.println("Nombre d'actions du plan : " + (plan != null ? plan.size() : 0));
            // Afficher les actions du plan si trouvé
            if (plan != null && !plan.isEmpty()) {
                System.out.println("Plan trouvé : ");
                for (int i = 0; i < plan.size(); i++) {
                    Action action = plan.get(i);
                    if (action instanceof BasicAction) {
                        BasicAction basicAction = (BasicAction) action;
                        System.out.println("  Action " + (i + 1) + " :");
                        System.out.println("    Préconditions : " + basicAction.getPrecondition());
                        System.out.println("    Effets : " + basicAction.getEffet());
                        System.out.println("    Coût : " + basicAction.getCost());
                        System.out.println(); 
                    } else {
                        System.out.println("  Action " + (i + 1) + " : (Action inconnue)");
                    }
                }
            } else {
                System.out.println("Aucun plan trouvé.");
            }

            
            System.out.println("------------------------------------------------\n");
        }
    }
}