package DesignPattern;

/******************************************************
 * Pattern Observer - Interface ObservateurMastermind.
 * @author abrahamyann
 ******************************************************/

public interface ObserverMastermind {
    /**
     * M�thode permettant de mettre � jour la ou les grilles de jeu
     * du Mastermind en fonction des propositions et des r�ponses de
     * l'ordinateur suivant le mode de jeu choisi.
     * @param reponse Cette variable peut correspondre aux propositions et
     * aux r�ponses de l'ordinateur suivant le mode de jeu choisi.
     */
    public void updateMastermind(String reponse);

    /**
     * M�thode permettant de quitter l'application.
     */
    public void quitterApplicationMastermind();

    /**
     * M�thode permettant de retourner � la page d'accueil.
     */
    public void acceuilObservateurMastermind();

    /**
     * M�thode permettant de relancer le m�me jeu.
     */
     public void relancerPartieMastermind();
/**
   public void updateObserverMastermind();

   public void quitterApplicationObserverMastermind();

   public void relancerPartieObserverMastermind();
     */
}
