package blocsworld;

import planning.Action;
import planning.BasicAction;

import java.util.*;
import modelling.Variable;
import modelling.BooleanVariable;
/*cette classe Génère les actions possibles pour déplacer des blocs dans un 
monde de bloc. Fournit un ensemble d'actions avec préconditions et effets.
 */
public class ActionBlocsWorld extends BlocsWorldConstraints {

    private Set<Action> actions;

    public ActionBlocsWorld(int nbrBlocs, int nbrPile) {
        super(nbrBlocs, nbrPile);
        this.actions = new HashSet<>();
        genererActions();
    }

    private void genererActions() {
        for (int b = 0; b < nbrBlocs; b++) {
            for (int bPrime = 0; bPrime < nbrBlocs; bPrime++) {
                if (b != bPrime) {
                    // Action 1: Déplacer b du dessus de b' au dessus de b''
                    for (int bDoublePrime = 0; bDoublePrime < nbrBlocs; bDoublePrime++) {
                        if (bDoublePrime != b && bDoublePrime != bPrime) {
                            actions.add(deplacementBlocSurBloc(b, bPrime, bDoublePrime));
                        }
                    }
                    // Action2: Déplacer b du dessus de b' vers une pile vide p
                    for (int p = 0; p < nbrPile; p++) {
                        actions.add(deplacementBlocSurPileVide(b, bPrime, p));
                    }
                }
            }
            // Action 3: Déplacer b d'une pile vers le dessus d'un bloc b'
            for (int bPrime = 0; bPrime < nbrBlocs; bPrime++) {
                if (b != bPrime) {
                    for (int p = 0; p < nbrPile; p++) {
                        actions.add(deplacementPileVersBloc(b, p, bPrime));
                    }
                }
            }
            // Action 4: Déplacer b d'une pile vers une autre pile vide p'
            for (int p = 0; p < nbrPile; p++) {
                for (int pPrime = 0; pPrime < nbrPile; pPrime++) {
                    if (p != pPrime) {
                        actions.add(deplacementPileVersPile(b, p, pPrime));
                    }
                }
            }
        }
    }

    // méthode pour deplacer le bloc b de dessus de b' vers b''
    private Action deplacementBlocSurBloc(int b, int bPrime, int bDoublePrime) {
        Variable onB = getVariablesOnb().get(b);
        BooleanVariable fixedB = getVariablesFixedb().get(b);
        BooleanVariable fixedBPrime = getVariablesFixedb().get(bPrime);
        BooleanVariable fixedBDoublePrime = getVariablesFixedb().get(bDoublePrime);

        Map<Variable, Object> preconditions = new HashMap<>();
        Map<Variable, Object> effets = new HashMap<>();

        preconditions.put(onB, bPrime);
        preconditions.put(fixedB, false);
        preconditions.put(fixedBDoublePrime, false);

        effets.put(onB, bDoublePrime);
        effets.put(fixedBPrime, false);
        effets.put(fixedBDoublePrime, true);

        return new BasicAction(preconditions, effets, 1);
    }

    // methode pour deplacer b qu'est sur le dessus de b' vers p
    private Action deplacementBlocSurPileVide(int b, int bPrime, int p) {
        Variable onB = getVariablesOnb().get(b);
        BooleanVariable fixedB = getVariablesFixedb().get(b);
        BooleanVariable fixedBPrime = getVariablesFixedb().get(bPrime);
        BooleanVariable freeP = getVariablesFreep().get(p);

        Map<Variable, Object> preconditions = new HashMap<>();
        Map<Variable, Object> effets = new HashMap<>();

        preconditions.put(onB, bPrime);
        preconditions.put(fixedB, false);
        preconditions.put(freeP, true);

        effets.put(onB, p);
        effets.put(fixedBPrime, false);
        effets.put(freeP, false);

        return new BasicAction(preconditions, effets, 1);
    }

    // cette methode de deplacement de bloc sue pile vers le dessus d un bloc
    private Action deplacementPileVersBloc(int b, int p, int bPrime) {
        Variable onB = getVariablesOnb().get(b);
        BooleanVariable fixedB = getVariablesFixedb().get(b);
        BooleanVariable fixedBPrime = getVariablesFixedb().get(bPrime);
        BooleanVariable freeP = getVariablesFreep().get(p);

        Map<Variable, Object> preconditions = new HashMap<>();
        Map<Variable, Object> effects = new HashMap<>();

        preconditions.put(onB, -(p + 1));
        preconditions.put(fixedB, false);
        preconditions.put(fixedBPrime, false);

        effects.put(onB, bPrime);
        effects.put(freeP, true);
        effects.put(fixedBPrime, true);

        return new BasicAction(preconditions, effects, 1);
    }

    // cette methode de deplacement bloc b sur pile vers autre pile
    private Action deplacementPileVersPile(int b, int p, int pPrime) {

        Variable onB = getVariablesOnb().get(b);
        BooleanVariable freeP = getVariablesFreep().get(p);
        BooleanVariable freePPrime = getVariablesFreep().get(pPrime);

        Map<Variable, Object> preconditions = new HashMap<>();
        Map<Variable, Object> effects = new HashMap<>();

        preconditions.put(onB, -(p + 1));
        preconditions.put(freeP, false);
        preconditions.put(freePPrime, true);

        effects.put(onB, -(pPrime + 1));
        effects.put(freeP, true);
        effects.put(freePPrime, false);

        return new BasicAction(preconditions, effects, 1);
    }

    public Set<Action> getActions() {
        return actions;
    }
}

