package controller;

import model.ModeleDonneesMastermind;

/*************************************************************************
 * Pattern MVC - Classe relative au Controler du jeu Mastermind.
 * Dans le cadre de ce programme, le but du Controler est de transf�rer
 * les donn�es saisies par l'utilisateur dans la vue au mod�le de donn�es
 * associ� au jeu Mastermind.
 * @author abrahamyann
 * @see ModeleDonneesMastermind
 *************************************************************************/

public class MastermindController {
    /**
     * Mod�le de donn�es relatif au jeu Mastermind.
     * @see ModeleDonneesMastermind
     */
    private ModeleDonneesMastermind modelMastermind;

    /**
     * Constructeur de la classe MastermindControler.
     * @param modelMastermind Mod�le de donn�es relatif au jeu Mastermind.
     * @see ModeleDonneesMastermind
     */
    public MastermindController(ModeleDonneesMastermind modelMastermind) {
        this.modelMastermind=modelMastermind;
    }

    /* ***************************************
     * M�thodes relatives au mode Challenger
     *****************************************/

    /**
     * M�thode relative au mode Challenger qui permet de transf�rer la combinaison secr�te de l'ordinateur au mod�le.
     * @param propositionSecrete Combinaison secr�te de l'ordinateur en mode challenger.
     */
    public void setPropositionSecreteOrdinateurModeChallenger(String propositionSecrete) {
        this.modelMastermind.setPropositionSecreteOrdinateurModeChallenger(propositionSecrete);
    }

    /**
     * M�thode relative au mode Challenger qui permet de transf�rer la proposition du joueur au mod�le.
     * @param propositionJoueur Proposition du joueur en mode challenger.
     */
    public void setPropositionJoueurModeChallenger(String propositionJoueur) {
        this.modelMastermind.setPropositionJoueurModeChallenger(propositionJoueur);
    }

    /* ***************************************
     * M�thodes relatives au mode D�fenseur
     *****************************************/

    /**
     * M�thode relative au mode D�fenseur qui permet de transf�rer la combinaison secr�te du joueur au mod�le.
     * @param propositionSecrete Combinaison secr�te du joueur en mode d�fenseur.
     */
    public void setPropositionSecreteJoueurModeDefenseur(String propositionSecrete) {
        this.modelMastermind.setPropositionSecreteJoueurModeDefenseur(propositionSecrete);
    }

    /**
     * M�thode relative au mode D�fenseur qui permet de transf�rer la r�ponse du joueur au mod�le.
     * @param reponseJoueur R�ponse du joueur en mode d�fenseur.
     */
    public void setReponseJoueurModeDefenseur(String reponseJoueur) {
        this.modelMastermind.setReponseJoueurModeDefenseur(reponseJoueur);
    }

    /* ***************************************
     * M�thodes relatives au mode Duel
     *****************************************/

    /**
     * M�thode relative au mode Duel qui permet de transf�rer la combinaison secr�te de l'ordinateur au mod�le.
     * @param propositionSecrete Combinaison secr�te de l'ordinateur en mode duel.
     */
    public void setPropositionSecreteOrdinateurModeDuel(String propositionSecrete) {
        this.modelMastermind.setPropositionSecreteOrdinateurModeDuel(propositionSecrete);
    }

    /**
     * M�thode relative au mode Duel qui permet de transf�rer la combinaison secr�te du joueur au mod�le.
     * @param propositionSecrete Combinaison secr�te du joueur en mode duel.
     */
    public void setPropositionSecreteJoueurModeDuel(String propositionSecrete) {
        this.modelMastermind.setPropositionSecreteJoueurModeDuel(propositionSecrete);
    }

    /**
     * M�thode relative au mode Duel qui permet de transf�rer la proposition du joueur au mod�le.
     * @param propositionJoueur Proposition du joueur en mode duel.
     */
    public void setPropositionJoueurModeDuel(String propositionJoueur) {
        this.modelMastermind.setPropositionJoueurModeDuel(propositionJoueur);
    }

    /**
     * M�thode relative au mode Duel qui permet de transf�rer la r�ponse du joueur au mod�le.
     * @param reponseJoueur R�ponse du joueur en mode duel.
     */
    public void setReponseJoueurModeDuel(String reponseJoueur) {
        this.modelMastermind.setReponseJoueurModeDuel(reponseJoueur);
    }

    /* ********************************************
     * M�thodes communes � tous les modes de jeu
     *********************************************/

    /**
     * M�thode commune � tous les modes de jeu qui permet de transf�rer le mode de jeu au mod�le.
     * @param modeDeJeu Variable relative au mode de jeu : 0 - Challenger, 1 - D�fenseur, 2 - Duel.
     */
    public void setModeDeJeu(int modeDeJeu) {
        this.modelMastermind.setModeDeJeu(modeDeJeu);
    }

    /**
     * M�thode commune � tous les modes de jeu qui permet de transf�rer le nombre d'essais au mod�le.
     * @param nbEssais Nombre d'essais.
     */
    public void setNbEssais(int nbEssais) {
        this.modelMastermind.setNbEssais(nbEssais);
    }

    /**
     * M�thode commune � tous les modes de jeu qui permet de transf�rer le nombre de cases au mod�le.
     * @param nbreCases Nombre de cases
     */
    public void setNbreCases(int nbreCases) {
        this.modelMastermind.setNbreCases(nbreCases);
    }

    /**
     * M�thode commune � tous les modes de jeu qui permet de transf�rer le nombre de couleurs utilisables au mod�le.
     * @param nbCouleursUtilisables Nombre de couleurs utilisables
     */
    public void setNbCouleursUtilisables(int nbCouleursUtilisables) {
        this.modelMastermind.setNbCouleursUtilisables(nbCouleursUtilisables);
    }

    /**
     * M�thode commune � tous les modes de jeu qui permet de transf�rer le choix du joueur en fin de partie au mod�le.
     * @param choixFinDePartie Choix du joueur en fin de partie.
     */
    public void setChoixFinDePartie(String choixFinDePartie) {
        this.modelMastermind.setChoixFinDePartie(choixFinDePartie);
    }
}
