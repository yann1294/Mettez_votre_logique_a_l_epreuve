package DesignPattern;

/********************************************
 * Pattern Observer - Interface Observateur.
 * @author Andr� Monnier
 ********************************************/

public interface Observer {

    /**
     * M�thode permettant de mettre � jour le tableau pour le jeu
     * RecherchePlusMoins en mode challenger et d�fenseur.
     * @param propositionJoueur Proposition du joueur en mode challenger/Proposition de l'ordinateur en mode d�fenseur.
     * @param reponse R�ponse de l'ordinateur en mode challenger/R�ponse du joueur en mode d�fenseur.
     */
    public void update(String propositionJoueur, String reponse);

    /**
     * M�thode permettant de mettre � jour le tableau pour le jeu RecherchePlusMoins en
     * mode duel en fonction des propositions et des r�ponses du joueur et de l'ordinateur.
     * @param affichage Cette variable peut correspondre aux propositions/r�ponses du joueur
     * et de l'ordinateur.
     */
    public void updateDuel(String affichage);

    /**
     * M�thode permettant de quitter l'application.
     */
    public void quitterApplication();

    /**
     * M�thode permettant de retourner � la page d'accueil.
     */
    public void acceuilObservateur();

    /**
     * M�thode permettant de relancer le m�me jeu.
     */
    public void relancerPartie();
}
