package DesignPattern;

/*****************************************************
 * Pattern Observer - Interface ObservableMastermind.
 * @author abrahamyann
 *****************************************************/

public interface ObservableMastermind {
    /**
     * M�thode permettant d'ajouter des observateurs � une liste d'observateur.
     * @param obs Un objet de type ObservateurMastermind. Dans le cadre du programme, une
     * classe qui impl�mente l'interface ObservateurMastermind.
     */
    public void addObserverMastermind(ObserverMastermind obs);

    /**
     * M�thode permettant de mettre � jour les observateurs.
     */
    public void updateObservateurMastermind();

    /**
     * M�thode permettant de r�initialiser la liste des observateurs.
     */
    public void delObservateurMastermind();

    /**
     * M�thode permettant de quitter l'application.
     */
    public void quitterApplicationObservateurMastermind();

    /**
     * M�thode permettant de retourner � la page d'accueil.
     */
    public void acceuilObservateurMastermind();

    /**
     * M�thode permettant de relancer le m�me jeu.
     */
    public void relancerPartieObservateurMastermind();
}
