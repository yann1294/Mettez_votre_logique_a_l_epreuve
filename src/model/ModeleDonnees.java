package model;

import DesignPattern.Observable;
import DesignPattern.Observer;

import java.util.ArrayList;

/***********************************************************************************
 * Pattern MVC - Classe relative au Mod�le de donn�es du jeu RecherchePlusMoins.
 * Le mod�le de donn�es va r�ceptionner les donn�es du controler, les analyser et
 * mettre � jour les observateurs. Cette classe impl�mente donc l'interface
 * Observable.
 * @author abrahamyann
 * @see Observable
 ************************************************************************************/

public class ModeleDonnees implements Observable {
    /**
     * ArrayList contenant la liste des observateurs.
     */
    private ArrayList<Observer> listeObservateur=new ArrayList<Observer>();


    /**
     * Variable de type chaine de caract�res relative au mode challenger.
     */
    private String propositionJoueurModeChallenger="", propositionSecreteModeChallenger="",reponseModeChallenger="";

    /**
     * Choix du joueur en fin de partie.
     */
    private String choixFinDePartie="";

    /**
     * Variable de type chaine de caract�res relative au mode d�fenseur.
     */
    private String propositionSecreteModeDefenseur="",reponseJoueurModeDefenseur="",propositionOrdinateurModeDefenseur="";

    /**
     * Variable relative au mode de jeu : 0 - Challenger, 1 - D�fenseur, 2 - Duel.
     */
    private int modeDeJeu=0;

    /**
     * Variable permettant d'effectuer des contr�les.
     */
    private int compteurReponseJoueurModeDefenseur=0;

    /**
     * Nombre d'essais.
     */
    private int nbEssais=0;

    /**
     * Bornes min.
     */
    private int[] bornesMin;

    /**
     * Bornes max.
     */
    private int[]bornesMax;

    /**
     * Variable de type chaine de caract�res relative au mode duel.
     */
    private String propositionSecreteOrdinateurModeDuel="",propositionSecreteJoueurModeDuel="",propositionJoueurModeDuel="",
            reponseOrdinateurModeDuel="",affichage="",reponseJoueurModeDuel="",propositionOrdinateurModeDuel="";

    /* ***************************************
     * M�thodes relatives au mode Challenger
     *****************************************/

    /**
     * M�thode relative au mode Challenger qui permet de r�cup�rer la proposition du joueur.
     * Suite � la proposition du joueur, l'ordinateur devra r�pondre.
     * @param propositionJoueur Proposition du joueur en mode challenger.
     */
    public void setPropositionJoueurModeChallenger(String propositionJoueur) {
        this.propositionJoueurModeChallenger=propositionJoueur;
        this.analysePropositionJoueurModeChallenger();
        this.updateObservateur();
    }

    /**
     * M�thode relative au mode Challenger qui permet de r�cup�rer la combinaison secr�te de l'ordinateur.
     * @param propositionSecrete Combinaison secr�te de l'ordinateur en mode challenger.
     */
    public void setPropositionSecreteModeChallenger(String propositionSecrete) {
        this.propositionSecreteModeChallenger=propositionSecrete;
    }

    /**
     * M�thode relative au mode Challenger qui permet d'analyser la proposition du joueur en la comparant
     * � la combinaison secr�te de l'ordinateur.
     */
    public void analysePropositionJoueurModeChallenger() {
        char[] tabReponse=new char [this.propositionSecreteModeChallenger.length()];
        reponseModeChallenger="";
        for (int i=0;i<this.propositionSecreteModeChallenger.length();i++) {
            if(this.propositionJoueurModeChallenger.charAt(i)==this.propositionSecreteModeChallenger.charAt(i)) {
                tabReponse[i]='=';
            }
            else if (this.propositionJoueurModeChallenger.charAt(i)<this.propositionSecreteModeChallenger.charAt(i)) {
                tabReponse[i]='+';
            }
            else {
                tabReponse[i]='-';
            }
            reponseModeChallenger+=String.valueOf(tabReponse[i]);
        }
    }

    /* ***************************************
     * M�thodes relatives au mode D�fenseur
     *****************************************/

    /**
     * M�thode relative au mode D�fenseur qui permet de r�cup�rer la combinaison secr�te du joueur.
     * Apr�s r�cup�ration de la combinaison secr�te du joueur, l'ordinateur devra faire une proposition.
     * @param propositionSecrete Combinaison secr�te du joueur en mode d�fenseur.
     */
    public void setPropositionSecreteModeDefenseur(String propositionSecrete) {
        this.propositionSecreteModeDefenseur=propositionSecrete;
        //En cas de relance d'une partie
        reponseJoueurModeDefenseur="";
        compteurReponseJoueurModeDefenseur=0;
        propositionOrdinateurModeDefenseur="";
        bornesMin=new int[propositionSecreteModeDefenseur.length()];
        bornesMax=new int[propositionSecreteModeDefenseur.length()];
        for (int i=0;i<propositionSecreteModeDefenseur.length();i++) {
            bornesMin[i]=0;
            bornesMax[i]=9;
        }
        this.propositionOrdinateurModeDefenseur();
        this.updateObservateur();
    }

    /**
     * M�thode relative au mode D�fenseur qui permet de r�cup�rer la r�ponse du joueur.
     * Suite � la r�ponse du joueur, l'ordinateur devra faire une proposition.
     * @param reponseJoueur R�ponse du joueur en mode d�fenseur.
     */
    public void setReponseJoueurModeDefenseur(String reponseJoueur) {
        int verifReponseJoueurModeDefenseur=0;
        compteurReponseJoueurModeDefenseur++;
        this.reponseJoueurModeDefenseur=reponseJoueur;
        this.updateObservateur();
        for(int i=0;i<propositionSecreteModeDefenseur.length();i++) {
            if(reponseJoueurModeDefenseur.charAt(i)=='=') {
                verifReponseJoueurModeDefenseur++;
            }
        }

        if(verifReponseJoueurModeDefenseur!=propositionSecreteModeDefenseur.length()&&compteurReponseJoueurModeDefenseur!=nbEssais) {
            this.propositionOrdinateurModeDefenseur();
            this.updateObservateur();
        }
    }

    /**
     * M�thode relative au mode D�fenseur qui permet de d�terminer la proposition de l'ordinateur
     * en fonction de la r�ponse du joueur. La premi�re proposition de l'ordinateur est al�atoire
     * vu que le joueur n'a pas encore r�pondu. Par la suite, nous adoptons un raisonnement par
     * dichotomie.
     */
    public void propositionOrdinateurModeDefenseur() {
        int tabAnalyse[]=new int[this.propositionSecreteModeDefenseur.length()];
        int tabReponse[]=new int[this.propositionSecreteModeDefenseur.length()];

        if (reponseJoueurModeDefenseur.equals("")){
            for (int i=0;i<propositionSecreteModeDefenseur.length();i++) {
                propositionOrdinateurModeDefenseur+=String.valueOf((int)(Math.random()*10));
            }
        }
        else {
            for (int i=0;i<this.propositionSecreteModeDefenseur.length();i++) {
                tabAnalyse[i]=Integer.valueOf(String.valueOf(propositionOrdinateurModeDefenseur.charAt(i)));
                if(reponseJoueurModeDefenseur.charAt(i)=='-') {
                    bornesMax[i]=tabAnalyse[i]-1;
                    tabReponse[i]=(int)(bornesMin[i]+bornesMax[i])/2;
                }
                else if(reponseJoueurModeDefenseur.charAt(i)=='+') {
                    bornesMin[i]=tabAnalyse[i]+1;
                    if((bornesMin[i]+bornesMax[i])%2==1)
                        tabReponse[i]=(int)(bornesMin[i]+bornesMax[i])/2+1;
                    else {
                        tabReponse[i]=(int)(bornesMin[i]+bornesMax[i])/2;
                    }

                }
                else {
                    tabReponse[i]=tabAnalyse[i];
                }
            }

            propositionOrdinateurModeDefenseur="";

            for(int i=0;i<this.propositionSecreteModeDefenseur.length();i++) {
                propositionOrdinateurModeDefenseur+=String.valueOf(tabReponse[i]);
            }
            reponseJoueurModeDefenseur="";
        }
    }

    /* ***************************************
     * M�thodes relatives au mode Duel
     *****************************************/

    /**
     * M�thode relative au mode Duel qui permet de r�cup�rer la combinaison secr�te de l'ordinateur.
     * @param propositionSecrete Combinaison secr�te de l'ordinateur en mode duel.
     */
    public void setPropositionSecreteOrdinateurModeDuel(String propositionSecrete) {
        this.propositionSecreteOrdinateurModeDuel=propositionSecrete;
        bornesMin=new int[propositionSecreteOrdinateurModeDuel.length()];
        bornesMax=new int[propositionSecreteOrdinateurModeDuel.length()];
        for (int i=0;i<propositionSecreteOrdinateurModeDuel.length();i++) {
            bornesMin[i]=0;
            bornesMax[i]=9;
        }
    }

    /**
     * M�thode relative au mode Duel qui permet de r�cup�rer la combinaison secr�te du joueur.
     * @param propositionSecrete Combinaison secr�te du joueur en mode duel.
     */
    public void setPropositionSecreteJoueurModeDuel(String propositionSecrete) {
        this.propositionSecreteJoueurModeDuel=propositionSecrete;
    }

    /**
     * M�thode relative au mode Duel qui permet de r�cup�rer la proposition du joueur.
     * Suite � la proposition du joueur, l'ordinateur devra r�pondre et �galement
     * faire une proposition.
     * @param propositionJoueur Proposition du joueur en mode duel.
     */
    public void setPropositionJoueurModeDuel(String propositionJoueur) {
        int verifReponseOrdinateurModeDuel=0;
        this.propositionJoueurModeDuel=propositionJoueur;
        affichage=this.propositionJoueurModeDuel;
        this.updateObservateur();
        this.analysePropositionJoueurModeDuel();
        affichage=reponseOrdinateurModeDuel;
        this.updateObservateur();

        for(int i=0;i<propositionSecreteOrdinateurModeDuel.length();i++) {
            if(reponseOrdinateurModeDuel.charAt(i)=='=') {
                verifReponseOrdinateurModeDuel++;
            }
        }
        if(verifReponseOrdinateurModeDuel!=propositionSecreteOrdinateurModeDuel.length()) {
            this.propositionOrdinateurModeDuel();
            affichage=propositionOrdinateurModeDuel;
            this.updateObservateur();
        }
    }

    /**
     * M�thode relative au mode Duel qui permet de r�cup�rer la r�ponse du joueur.
     * @param reponseJoueur R�ponse du joueur en mode duel.
     */
    public void setReponseJoueurModeDuel(String reponseJoueur) {
        this.reponseJoueurModeDuel=reponseJoueur;
        affichage=this.reponseJoueurModeDuel;
        this.updateObservateur();
    }

    /**
     * M�thode relative au mode Duel qui permet d'analyser la proposition du joueur en la comparant
     * � la combinaison secr�te de l'ordinateur.
     */
    public void analysePropositionJoueurModeDuel() {
        char[] tabReponse=new char [this.propositionSecreteOrdinateurModeDuel.length()];
        reponseOrdinateurModeDuel="";
        for (int i=0;i<this.propositionSecreteOrdinateurModeDuel.length();i++) {
            if(this.propositionJoueurModeDuel.charAt(i)==this.propositionSecreteOrdinateurModeDuel.charAt(i)) {
                tabReponse[i]='=';
            }
            else if (this.propositionJoueurModeDuel.charAt(i)<this.propositionSecreteOrdinateurModeDuel.charAt(i)) {
                tabReponse[i]='+';
            }
            else {
                tabReponse[i]='-';
            }
            reponseOrdinateurModeDuel+=String.valueOf(tabReponse[i]);
        }
    }

    /**
     * M�thode relative au mode Duel qui permet de d�terminer la proposition de l'ordinateur
     * en fonction de la r�ponse du joueur. La premi�re proposition de l'ordinateur est al�atoire
     * vu que le joueur n'a pas encore r�pondu. Par la suite, nous adoptons un raisonnement par
     * dichotomie.
     */
    public void propositionOrdinateurModeDuel() {
        int tabAnalyse[]=new int[this.propositionSecreteOrdinateurModeDuel.length()];
        int tabReponse[]=new int[this.propositionSecreteOrdinateurModeDuel.length()];

        if (reponseJoueurModeDuel.equals("")){
            for (int i=0;i<propositionSecreteOrdinateurModeDuel.length();i++) {
                propositionOrdinateurModeDuel+=String.valueOf((int)(Math.random()*10));
            }
        }
        else {
            for (int i=0;i<propositionSecreteOrdinateurModeDuel.length();i++) {
                tabAnalyse[i]=Integer.valueOf(String.valueOf(propositionOrdinateurModeDuel.charAt(i)));
                if(reponseJoueurModeDuel.charAt(i)=='-') {
                    bornesMax[i]=tabAnalyse[i]-1;
                    tabReponse[i]=(int)(bornesMin[i]+bornesMax[i])/2;

                }
                else if(reponseJoueurModeDuel.charAt(i)=='+') {
                    bornesMin[i]=tabAnalyse[i]+1;
                    if((bornesMin[i]+bornesMax[i])%2==1)
                        tabReponse[i]=(int)(bornesMin[i]+bornesMax[i])/2+1;
                    else {
                        tabReponse[i]=(int)(bornesMin[i]+bornesMax[i])/2;
                    }
                }
                else {
                    tabReponse[i]=tabAnalyse[i];
                }
            }

            propositionOrdinateurModeDuel="";

            for(int i=0;i<propositionSecreteOrdinateurModeDuel.length();i++) {
                propositionOrdinateurModeDuel+=String.valueOf(tabReponse[i]);
            }
            reponseJoueurModeDuel="";
        }
    }
    /* ********************************************
     * M�thodes communes � tous les modes de jeu
     *********************************************/

    /**
     * Mutateur commun � tous les modes de jeu qui permet de modifier le mode de jeu.
     * @param modeDeJeu Variable relative au mode de jeu : 0 - Challenger, 1 - D�fenseur, 2 - Duel.
     */
    public void setModeDeJeu(int modeDeJeu) {
        this.modeDeJeu=modeDeJeu;
    }

    /**
     * Mutateur commun � tous les modes de jeu qui permet de modifier le nombre d'essais.
     * @param nbEssais Nombre d'essais.
     */
    public void setNbEssais(int nbEssais) {
        this.nbEssais=nbEssais;
    }

    /**
     * M�thode commune � tous les modes de jeu qui permet de r�cup�rer le choix du joueur en fin de partie et
     * en fonction de cela, faire appel � la m�thode ad�quate correspondant au choix du joueur.
     * @param choixFinDePartie Choix du joueur en fin de partie.
     */
    public void setChoixFinDePartie(String choixFinDePartie) {
        this.choixFinDePartie=choixFinDePartie;
        if(this.choixFinDePartie.equals("Quitter l'application"))
            this.quitterApplicationObservateur();
        else if(this.choixFinDePartie.equals("Lancer un autre jeu"))
            this.acceuilObservateur();
        else {
            this.relancerPartieObservateur();
        }
    }

    /* ********************************
     * Mise � jour des observateurs
     **********************************/

    /**
     * M�thode permettant d'ajouter des observateurs � une liste d'observateur.
     *
     * @param obs Un objet de type Observateur. Dans le cadre du programme, une
     *            classe qui impl�mente l'interface Observateur.
     */
    @Override
    public void addObservateur(Observer obs) {
        listeObservateur.add(obs);

    }

    /**
     * M�thode permettant de mettre � jour les observateurs.
     */
    @Override
    public void updateObservateur() {

        for (Observer observer : listeObservateur){
            if(modeDeJeu==0) {
                observer.update(propositionJoueurModeChallenger,reponseModeChallenger);
            }
            else if (modeDeJeu==1) {
                observer.update(propositionOrdinateurModeDefenseur,reponseJoueurModeDefenseur);
            }
            else
                observer.updateDuel(affichage);
        }

    }

    /**
     * M�thode permettant de r�initialiser la liste des observateurs.
     */
    @Override
    public void delObservateur() {
        listeObservateur = new ArrayList<Observer>();
    }

    /**
     * M�thode permettant de quitter l'application.
     */
    @Override
    public void quitterApplicationObservateur() {
        for (Observer observer : listeObservateur){
            observer.quitterApplication();
        }
    }

    /**
     * M�thode permettant de retourner � la page d'accueil.
     */
    @Override
    public void acceuilObservateur() {
        for (Observer obs : listeObservateur) {
            obs.acceuilObservateur();
        }
    }

    /**
     * M�thode permettant de relancer le m�me jeu.
     */
    @Override
    public void relancerPartieObservateur() {
        for (Observer obs : listeObservateur) {
            this.propositionSecreteOrdinateurModeDuel="";
            this.propositionSecreteJoueurModeDuel="";
            this.reponseJoueurModeDuel="";
            this.propositionOrdinateurModeDuel="";
            obs.relancerPartie();
        }
    }
}
