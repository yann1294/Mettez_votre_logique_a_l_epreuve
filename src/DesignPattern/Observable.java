package DesignPattern;

/*******************************************
 * Pattern Observer - Interface Observable.
 * @author abrahamyann
 *******************************************/

public interface Observable {
    /**
     * M�thode permettant d'ajouter des observateurs � une liste d'observateur.
     * @param obs Un objet de type Observateur. Dans le cadre du programme, une
     * classe qui impl�mente l'interface Observateur.
     */
    public void addObservateur(Observer obs);

    /**
     * M�thode permettant de mettre � jour les observateurs.
     */
    public void updateObservateur();

    /**
     * M�thode permettant de r�initialiser la liste des observateurs.
     */
    public void delObservateur();

    /**
     * M�thode permettant de quitter l'application.
     */
    public void quitterApplicationObservateur();

    /**
     * M�thode permettant de retourner � la page d'accueil.
     */
    public void acceuilObservateur();

    /**
     * M�thode permettant de relancer le m�me jeu.
     */
    public void relancerPartieObservateur();
}
