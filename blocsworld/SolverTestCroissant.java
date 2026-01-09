package blocsworld;
import cp.*;
import modelling.*;
import java.util.*;

public class SolverTestCroissant {
    public static void main(String[] args) {
       int nbrBlocs=4;
       int nbrPile=3;

       BlocsWorldConstraints mondeContraintes=new BlocsWorldConstraints(nbrBlocs, nbrPile);
       CroissanteConstraints mondeCroissant   =new CroissanteConstraints(nbrBlocs, nbrPile);
       
       
       //liste qui contient toutes les contraintes 
       Set<Constraint> allContraintes=new HashSet();
       allContraintes.addAll(mondeContraintes.getConstraints());
       allContraintes.addAll(mondeCroissant.getContraintesCroissantes());
     
       

       Map<Variable, Object> solution;
       List<Solver> solverList = new ArrayList<>();
    
       Set<Variable> variablesSet = new HashSet<>(mondeContraintes.getVariablesOnb());
       BacktrackSolver backtrackSolver = new BacktrackSolver(variablesSet, allContraintes);

       HeuristicMACSolver heuristicMACSolver = new HeuristicMACSolver(variablesSet, allContraintes,
                new DomainSizeVariableHeuristic(true), new RandomValueHeuristic(new Random()));
       MACSolver macSolver = new MACSolver(variablesSet, allContraintes);
        // On ajoute tous les solveurs à la liste
        solverList.add(backtrackSolver);
        solverList.add(heuristicMACSolver);
        solverList.add(macSolver);

        // Test de chaque solveur
        for (Solver solver : solverList) {
            long debut = System.currentTimeMillis();
            
            solution = solver.solve();
            long fin = System.currentTimeMillis();
            float temps = (fin - debut);
            System.out.println(
                    "----------->  " + solver.getClass().getSimpleName() + "  <--------- \ntemps d'exécution :  " + temps + " ms");
            System.out.println("solution trouvé : \n" + solution + " \n");

        }
    }
}
