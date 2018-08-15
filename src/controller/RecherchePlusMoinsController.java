package controller;

import model.ModeleDonnees;

/*************************************************************************
 * Pattern MVC - Classe relative au Controler du jeu RecherchePlusMoins.
 * Dans le cadre de ce programme, le but du Controler est de transf�rer
 * les donn�es saisies par l'utilisateur dans la vue au mod�le de donn�es
 * associ� au jeu RecherchePlusMoins.
 * @author abrahamyann
 * @see ModeleDonnees
 *************************************************************************/

public class RecherchePlusMoinsController {
    /**
     * Mod�le de donn�es relatif au jeu RecherchePlusMoins.
     * @see ModeleDonnees
     */
    private ModeleDonnees model;

    /**
     * Constructeur de la classe RecherchePlusMoinsControler.
     * @param model Mod�le de donn�es relatif au jeu RecherchePlusMoins.
     * @see ModeleDonnees
     */
    public RecherchePlusMoinsController(ModeleDonnees model) {
        this.model=model;
    }

    /* **************************************
     * M�thodes relatives au mode Challenger
     ****************************************/

    /**
     * M�thode relative au mode Challenger qui permet de transf�rer la proposition du joueur au mod�le.
     * @param propositionJoueur Proposition du joueur en mode challenger.
     */
    public void setPropositionJoueurModeChallenger(String propositionJoueur) {
        model.setPropositionJoueurModeChallenger(propositionJoueur);

    }

    /**
     * M�thode relative au mode Challenger qui permet de transf�rer la combinaison secr�te de l'ordinateur au mod�le.
     * @param propositionSecrete Combinaison secr�te de l'ordinateur en mode challenger.
     */
    public void setPropositionSecreteModeChallenger(String propositionSecrete) {
        model.setPropositionSecreteModeChallenger(propositionSecrete);
    }

    /* *************************************
     * M�thodes relatives au mode D�fenseur
     ***************************************/

    /**
     * M�thode relative au mode D�fenseur qui permet de transf�rer la combinaison secr�te du joueur au mod�le.
     * @param propositionSecrete Combinaison secr�te du joueur en mode d�fenseur.
     */
    public void setPropositionSecreteModeDefenseur(String propositionSecrete) {
        model.setPropositionSecreteModeDefenseur(propositionSecrete);
    }

    /**
     * M�thode relative au mode D�fenseur qui permet de transf�rer la r�ponse du joueur au mod�le.
     * @param reponseJoueur R�ponse du joueur en mode d�fenseur.
     */
    public void setReponseJoueurModeDefenseur(String reponseJoueur) {
        model.setReponseJoueurModeDefenseur(reponseJoueur);
    }

    /* ***************************************
     * M�thodes relatives au mode Duel
     *****************************************/

    /**
     * M�thode relative au mode Duel qui permet de transf�rer la combinaison secr�te de l'ordinateur au mod�le.
     * @param propositionSecrete Combinaison secr�te de l'ordinateur en mode duel.
     */
    public void setPropositionSecreteOrdinateurModeDuel(String propositionSecrete) {
        model.setPropositionSecreteOrdinateurModeDuel(propositionSecrete);
    }

    /**
     * M�thode relative au mode Duel qui permet de transf�rer la combinaison secr�te du joueur au mod�le.
     * @param propositionSecrete Combinaison secr�te du joueur en mode duel.
     */
    public void setPropositionSecreteJoueurModeDuel(String propositionSecrete) {
        model.setPropositionSecreteJoueurModeDuel(propositionSecrete);
    }

    /**
     * M�thode relative au mode Duel qui permet de transf�rer la proposition du joueur au mod�le.
     * @param propositionJoueur Proposition du joueur en mode duel.
     */
    public void setPropositionJoueurModeDuel(String propositionJoueur) {
        model.setPropositionJoueurModeDuel(propositionJoueur);
    }

    /**
     * M�thode relative au mode Duel qui permet de transf�rer la r�ponse du joueur au mod�le.
     * @param reponseJoueur R�ponse du joueur en mode duel.
     */
    public void setReponseJoueurModeDuel(String reponseJoueur) {
        model.setReponseJoueurModeDuel(reponseJoueur);
    }

    /* ********************************************
     * M�thodes communes � tous les modes de jeu
     *********************************************/

    /**
     * M�thode commune � tous les modes de jeu qui permet de transf�rer le mode de jeu au mod�le.
     * @param modeDeJeu Variable relative au mode de jeu : 0 - Challenger, 1 - D�fenseur, 2 - Duel.
     */
    public void setModeDeJeu(int modeDeJeu) {
        model.setModeDeJeu(modeDeJeu);
    }

    /**
     * M�thode commune � tous les modes de jeu qui permet de transf�rer le nombre d'essais au mod�le.
     * @param nbEssais Nombre d'essais.
     */
    public void setNbEssais(int nbEssais) {
        model.setNbEssais(nbEssais);
    }

    /**
     * M�thode commune � tous les modes de jeu qui permet de transf�rer le choix du joueur en fin de partie au mod�le.
     * @param choixFinDePartie Choix du joueur en fin de partie.
     */
    public void setChoixFinDePartie(String choixFinDePartie) {
        model.setChoixFinDePartie(choixFinDePartie);
    }
}
