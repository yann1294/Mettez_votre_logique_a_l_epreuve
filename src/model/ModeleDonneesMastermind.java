package model;

import DesignPattern.ObservableMastermind;
import DesignPattern.Observer;
import DesignPattern.ObserverMastermind;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/***********************************************************************************
 * Pattern MVC - Classe relative au Mod�le de donn�es du jeu Mastermind.
 * Le mod�le de donn�es va r�ceptionner les donn�es du controler, les analyser et
 * mettre � jour les observateurs. Cette classe impl�mente donc l'interface
 * ObservableMastermind.
 * @author abrahamyann
 * @see ObservableMastermind
 ***********************************************************************************/

public class ModeleDonneesMastermind implements ObservableMastermind {

    /**
     * ArrayList contenant la liste des observateurs.
     */
    private ArrayList<ObserverMastermind> listeObservateurMastermind=new ArrayList<ObserverMastermind>();

    /**
     * Liste chain�e contenant la liste de l'ensemble des combinaisons possibles.
     */
    private LinkedList<String> listePossibilitees;

    /**
     * Variable de type chaine de caract�res relative au mode challenger.
     */
    private String propositionSecreteOrdinateurModeChallenger="",propositionJoueurModeChallenger="",reponseOrdinateurModeChallenger="";

    /**
     * Choix du joueur en fin de partie.
     */
    private String choixFinDePartieMastermind="";

    /**
     * Variable de type chaine de caract�res relative au mode d�fenseur.
     */
    private String propositionSecreteJoueurModeDefenseur="",reponseJoueurModeDefenseur="",propositionOrdinateurModeDefenseur="";

    /**
     * Variable de type chaine de caract�res relative au mode duel.
     */
    private String propositionSecreteOrdinateurModeDuel="",propositionSecreteJoueurModeDuel="",propositionJoueurModeDuel="",
            propositionOrdinateurModeDuel="",reponseJoueurModeDuel="",reponseOrdinateurModeDuel="",affichage="";

    /**
     * Param�tre du jeu Mastermind.
     */
    private int nbEssaisMastermind,nbreCasesMastermind,nbCouleursUtilisablesMastermind;

    /**
     * Variable relative au mode de jeu : 0 - Challenger, 1 - D�fenseur, 2 - Duel.
     */
    private int modeDeJeuMastermind;

    /**
     * Variable permettant la gestion des logs d'erreurs.
     */
    private static final Logger LOGGER=LogManager.getLogger();

    /* ***************************************
     * M�thodes relatives au mode Challenger
     *****************************************/

    /**
     * M�thode relative au mode Challenger qui permet de r�cup�rer la combinaison secr�te de l'ordinateur.
     * @param propositionSecrete Combinaison secr�te de l'ordinateur en mode challenger.
     */
    public void setPropositionSecreteOrdinateurModeChallenger(String propositionSecrete) {
        this.propositionSecreteOrdinateurModeChallenger=propositionSecrete;
    }

    /**
     * M�thode relative au mode Challenger qui permet de r�cup�rer la proposition du joueur.
     * Suite � la proposition du joueur, l'ordinateur devra r�pondre.
     * @param propositionJoueur Proposition du joueur en mode challenger.
     */
    public void setPropositionJoueurModeChallenger(String propositionJoueur) {
        this.propositionJoueurModeChallenger=propositionJoueur;
        this.analysePropositionJoueurModeChallenger();
        this.updateObservateurMastermind();
    }

    /**
     * M�thode relative au mode Challenger qui permet d'analyser la proposition du joueur en la comparant
     * � la combinaison secr�te de l'ordinateur.
     */
    private void analysePropositionJoueurModeChallenger() {

        //Analyse des boules bien plac�es (pions rouges) et mal plac�es (pions blancs). Pour faciliter le traitement, on va dire
        //que pions rouges �quivaut � 1, pions blancs � 2 et emplacement vide � 3.
        int[] tabReponse=new int[this.nbreCasesMastermind];
        char []tabAnalyse=new char[this.nbreCasesMastermind];
        tabAnalyse=propositionSecreteOrdinateurModeChallenger.toCharArray();
        for (int i=0;i<this.nbreCasesMastermind;i++) {
            tabReponse[i]=3;
        }
        reponseOrdinateurModeChallenger="";

        for (int i=0;i<this.nbreCasesMastermind;i++) {
            if(this.propositionJoueurModeChallenger.charAt(i)==tabAnalyse[i]) {
                tabReponse[i]=1;
                tabAnalyse[i]=' ';
            }
        }

        for (int i=0;i<this.nbreCasesMastermind;i++) {
            for(int j=0;j<this.nbreCasesMastermind;j++) {
                if(this.propositionJoueurModeChallenger.charAt(i)==tabAnalyse[j]&&tabReponse[i]!=1) {
                    tabReponse[i]=2;
                    tabAnalyse[j]=' ';
                    break;
                }
            }
        }


        //On r�ordonne le tableau d'entiers dans l'ordre num�rique puis on effectue la correspondance avec les couleurs dans l'ordre suivant :
        //pions rouges (si pr�sents), pions blancs (si pr�sents), et emplacement vide
        Arrays.sort(tabReponse);

        for (int i=0;i<this.nbreCasesMastermind;i++) {
            if(tabReponse[i]==1)
                reponseOrdinateurModeChallenger+="R";
            else if(tabReponse[i]==2)
                reponseOrdinateurModeChallenger+="B";
            else
                reponseOrdinateurModeChallenger+="V";
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
    public void setPropositionSecreteJoueurModeDefenseur(String propositionSecrete) {
        this.propositionSecreteJoueurModeDefenseur=propositionSecrete;
        LOGGER.debug("Jeu Mastermind en mode D�fenseur - Combinaison secr�te joueur Mod�le de donn�es :"+this.propositionSecreteJoueurModeDefenseur);
        this.initListePossibilitees();
        LOGGER.debug("Jeu Mastermind en mode D�fenseur - Taille de la liste cha�n�e :"+listePossibilitees.size());
        this.propositionOrdinateurModeDefenseur();
        this.updateObservateurMastermind();
    }

    /**
     * M�thode relative au mode D�fenseur qui permet de r�cup�rer la r�ponse du joueur.
     * Suite � la r�ponse du joueur, l'ordinateur devra faire une proposition.
     * @param reponseJoueur R�ponse du joueur en mode d�fenseur.
     */
    public void setReponseJoueurModeDefenseur(String reponseJoueur) {
        this.reponseJoueurModeDefenseur=reponseJoueur;
        this.propositionOrdinateurModeDefenseur();
        this.updateObservateurMastermind();
    }

    /**
     * M�thode relative au mode D�fenseur qui permet de d�terminer la proposition de l'ordinateur
     * en fonction de la r�ponse du joueur. La premi�re proposition de l'ordinateur correspondra
     * au premier �l�ment de la liste chain�e initiale qui comprend toutes les possibilit�s
     * vu que le joueur n'a pas encore r�pondu. Par la suite, suivant la r�ponse du joueur, la liste
     * chain�e sera r�duite au fur et � mesure jusqu'� ne comprendre qu'un seul �l�ment.
     * L'ordinateur proposera � chaque fois le premier �l�ment de la liste chain�e.
     */
    private void propositionOrdinateurModeDefenseur() {
        if(reponseJoueurModeDefenseur.equals("")) {
            propositionOrdinateurModeDefenseur=listePossibilitees.getFirst();
            LOGGER.debug("Jeu Mastermind en mode D�fenseur - Proposition de l'ordinateur en mode d�fenseur :"+propositionOrdinateurModeDefenseur);
        }
        else {
            LOGGER.debug("Jeu Mastermind en mode D�fenseur - Modele de donn�es r�ponse du joueur :"+reponseJoueurModeDefenseur);
            Iterator<String> itParcoursListe=listePossibilitees.iterator();
            String premierElementListe=this.listePossibilitees.getFirst();
            while(itParcoursListe.hasNext()) {
                String strElementListe=itParcoursListe.next();
                String resultatComparaison="";
                int[] tabComparaison=new int[this.nbreCasesMastermind];
                char []tabAnalyseListe=new char[this.nbreCasesMastermind];
                tabAnalyseListe=strElementListe.toCharArray();
                for (int i=0;i<this.nbreCasesMastermind;i++) {
                    tabComparaison[i]=3;
                }

                for (int i=0;i<this.nbreCasesMastermind;i++) {
                    if(premierElementListe.charAt(i)==tabAnalyseListe[i]) {
                        tabComparaison[i]=1;
                        tabAnalyseListe[i]=' ';
                    }
                }
                for (int i=0;i<this.nbreCasesMastermind;i++) {
                    for(int j=0;j<this.nbreCasesMastermind;j++) {
                        if(premierElementListe.charAt(i)==tabAnalyseListe[j]&&tabComparaison[i]!=1) {
                            tabComparaison[i]=2;
                            tabAnalyseListe[j]=' ';
                            break;
                        }
                    }
                }

                Arrays.sort(tabComparaison);
                for (int i=0;i<this.nbreCasesMastermind;i++) {
                    if(tabComparaison[i]==1)
                        resultatComparaison+="R";
                    else if(tabComparaison[i]==2)
                        resultatComparaison+="B";
                    else
                        resultatComparaison+="V";
                }
                if(!resultatComparaison.equals(reponseJoueurModeDefenseur)) {
                    itParcoursListe.remove();
                }

            }
            LOGGER.debug("Jeu Mastermind en mode D�fenseur - Taille liste cha�n�e r�actualis�e :"+listePossibilitees.size());
            LOGGER.debug("Jeu Mastermind en mode D�fenseur - Premier �l�ment r�actualis� :"+listePossibilitees.getFirst());
            reponseJoueurModeDefenseur="";
            propositionOrdinateurModeDefenseur=listePossibilitees.getFirst();
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
        LOGGER.debug("Jeu Mastermind en mode Duel - Combinaison Secr�te Ordinateur Mod�le de donn�es : "+this.propositionSecreteOrdinateurModeDuel);
    }

    /**
     * M�thode relative au mode Duel qui permet de r�cup�rer la combinaison secr�te du joueur.
     * @param propositionSecrete Combinaison secr�te du joueur en mode duel.
     */
    public void setPropositionSecreteJoueurModeDuel(String propositionSecrete) {
        this.propositionSecreteJoueurModeDuel=propositionSecrete;
        LOGGER.debug("Jeu Mastermind en mode Duel - Proposition Secr�te Joueur Mod�le de donn�es :"+this.propositionSecreteJoueurModeDuel);
        this.initListePossibilitees();
        LOGGER.debug("Jeu Mastermind en mode Duel - Taille de la liste cha�n�e :"+listePossibilitees.size());
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
        LOGGER.debug("Jeu Mastermind en mode Duel - Proposition Joueur Mod�le de donn�es :"+this.propositionJoueurModeDuel);
        this.analysePropositionJoueurModeDuel();
        affichage=reponseOrdinateurModeDuel;
        LOGGER.debug("Jeu Mastermind en mode Duel - R�ponse Ordinateur Mode Duel :"+affichage);
        this.updateObservateurMastermind();

        for(int i=0;i<this.nbreCasesMastermind;i++) {
            if(reponseOrdinateurModeDuel.charAt(i)=='R') {
                verifReponseOrdinateurModeDuel++;
            }
        }
        if(verifReponseOrdinateurModeDuel!=this.nbreCasesMastermind) {
            this.propositionOrdinateurModeDuel();
            affichage=propositionOrdinateurModeDuel;
            this.updateObservateurMastermind();
        }
    }

    /**
     * M�thode relative au mode Duel qui permet de r�cup�rer la r�ponse du joueur.
     * @param reponseJoueur R�ponse du joueur en mode duel.
     */
    public void setReponseJoueurModeDuel(String reponseJoueur) {
        this.reponseJoueurModeDuel=reponseJoueur;
    }

    /**
     * M�thode relative au mode Duel qui permet d'analyser la proposition du joueur en la comparant
     * � la combinaison secr�te de l'ordinateur.
     */
    private void analysePropositionJoueurModeDuel() {

        //Analyse des boules bien plac�es (pions rouges) et mal plac�es (pions blancs). Pour faciliter le traitement, on va dire
        //que pions rouges �quivaut � 1, pions blancs � 2 et emplacement vide � 3.
        int[] tabReponse=new int[this.nbreCasesMastermind];
        char []tabAnalyse=new char[this.nbreCasesMastermind];
        tabAnalyse=propositionSecreteOrdinateurModeDuel.toCharArray();
        for (int i=0;i<this.nbreCasesMastermind;i++) {
            tabReponse[i]=3;
        }
        reponseOrdinateurModeDuel="";

        for (int i=0;i<this.nbreCasesMastermind;i++) {
            if(this.propositionJoueurModeDuel.charAt(i)==tabAnalyse[i]) {
                tabReponse[i]=1;
                tabAnalyse[i]=' ';
            }
        }

        for (int i=0;i<this.nbreCasesMastermind;i++) {
            for(int j=0;j<this.nbreCasesMastermind;j++) {
                if(this.propositionJoueurModeDuel.charAt(i)==tabAnalyse[j]&&tabReponse[i]!=1) {
                    tabReponse[i]=2;
                    tabAnalyse[j]=' ';
                    break;
                }
            }
        }

        //On r�ordonne le tableau d'entiers dans l'ordre num�rique puis on effectue la correspondance avec les couleurs dans l'ordre suivant :
        //pions rouges (si pr�sents), pions blancs (si pr�sents), et emplacement vide
        Arrays.sort(tabReponse);

        for (int i=0;i<this.nbreCasesMastermind;i++) {
            if(tabReponse[i]==1)
                reponseOrdinateurModeDuel+="R";
            else if(tabReponse[i]==2)
                reponseOrdinateurModeDuel+="B";
            else
                reponseOrdinateurModeDuel+="V";
        }
    }

    /**
     * M�thode relative au mode Duel qui permet de d�terminer la proposition de l'ordinateur
     * en fonction de la r�ponse du joueur. La premi�re proposition de l'ordinateur correspondra
     * au premier �l�ment de la liste chain�e initiale qui comprend toutes les possibilit�s
     * vu que le joueur n'a pas encore r�pondu. Par la suite, suivant la r�ponse du joueur, la liste
     * chain�e sera r�duite au fur et � mesure jusqu'� ne comprendre qu'un seul �l�ment.
     * L'ordinateur proposera � chaque fois le premier �l�ment de la liste chain�e.
     */
    public void propositionOrdinateurModeDuel() {

        if(reponseJoueurModeDuel.equals("")) {
            propositionOrdinateurModeDuel=listePossibilitees.getFirst();
            LOGGER.debug("Jeu Mastermind en mode Duel - Proposition Ordinateur Mode Duel :"+propositionOrdinateurModeDuel);
        }
        else {
            LOGGER.debug("Jeu Mastermind en mode Duel - Modele de donn�es r�ponse du joueur :"+reponseJoueurModeDuel);
            Iterator<String> itParcoursListe=listePossibilitees.iterator();
            String premierElementListe=this.listePossibilitees.getFirst();
            while(itParcoursListe.hasNext()) {
                String strElementListe=itParcoursListe.next();
                String resultatComparaison="";
                int[] tabComparaison=new int[this.nbreCasesMastermind];
                char []tabAnalyseListe=new char[this.nbreCasesMastermind];
                tabAnalyseListe=strElementListe.toCharArray();
                for (int i=0;i<this.nbreCasesMastermind;i++) {
                    tabComparaison[i]=3;
                }

                for (int i=0;i<this.nbreCasesMastermind;i++) {
                    if(premierElementListe.charAt(i)==tabAnalyseListe[i]) {
                        tabComparaison[i]=1;
                        tabAnalyseListe[i]=' ';
                    }
                }
                for (int i=0;i<this.nbreCasesMastermind;i++) {
                    for(int j=0;j<this.nbreCasesMastermind;j++) {
                        if(premierElementListe.charAt(i)==tabAnalyseListe[j]&&tabComparaison[i]!=1) {
                            tabComparaison[i]=2;
                            tabAnalyseListe[j]=' ';
                            break;
                        }
                    }
                }

                Arrays.sort(tabComparaison);
                for (int i=0;i<this.nbreCasesMastermind;i++) {
                    if(tabComparaison[i]==1)
                        resultatComparaison+="R";
                    else if(tabComparaison[i]==2)
                        resultatComparaison+="B";
                    else
                        resultatComparaison+="V";
                }
                if(!resultatComparaison.equals(reponseJoueurModeDuel)) {
                    itParcoursListe.remove();
                }

            }
            LOGGER.debug("Jeu Mastermind en mode Duel - Taille liste cha�n�e r�actualis� :"+listePossibilitees.size());
            LOGGER.debug("Jeu Mastermind en mode Duel - Premier �l�ment r�actualis� :"+listePossibilitees.getFirst());
            reponseJoueurModeDuel="";
            propositionOrdinateurModeDuel=listePossibilitees.getFirst();
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
        this.modeDeJeuMastermind=modeDeJeu;
    }

    /**
     * Mutateur commun � tous les modes de jeu qui permet de modifier le nombre d'essais.
     * @param nbEssais Nombre d'essais.
     */
    public void setNbEssais(int nbEssais) {
        this.nbEssaisMastermind=nbEssais;
    }

    /**
     * Mutateur commun � tous les modes de jeu qui permet de modifier le nombre de cases.
     * @param nbreCases Nombre de cases.
     */
    public void setNbreCases(int nbreCases) {
        this.nbreCasesMastermind=nbreCases;
    }

    /**
     * Mutateur commun � tous les modes de jeu qui permet de modifier le nombre de couleurs utilisables.
     * @param nbCouleursUtilisables Nombre de couleurs utilisables.
     */
    public void setNbCouleursUtilisables(int nbCouleursUtilisables) {
        this.nbCouleursUtilisablesMastermind=nbCouleursUtilisables;
    }

    /**
     * M�thode commune � tous les modes de jeu qui permet de r�cup�rer le choix du joueur en fin de partie et
     * en fonction de cela, faire appel � la m�thode ad�quate correspondant au choix du joueur.
     * @param choixFinDePartie Choix du joueur en fin de partie.
     */
    public void setChoixFinDePartie(String choixFinDePartie) {
        this.choixFinDePartieMastermind=choixFinDePartie;
        if(this.choixFinDePartieMastermind.equals("Quitter l'application"))
            this.quitterApplicationObservateurMastermind();
        else if(this.choixFinDePartieMastermind.equals("Lancer un autre jeu"))
            this.acceuilObservateurMastermind();
        else {
            this.relancerPartieObservateurMastermind();
        }
    }

    /**
     * M�thode permettant d'initialiser la liste chain�e avec l'ensemble des combinaisons possibles.
     */
    public void initListePossibilitees() {
		/*On cr�e un objet LinkedList avec l'ensemble des possibilit�s. Dans le cas o� on a 4 cases et 6 couleurs utilisables,
		 l'objet LinkedList contiendra 1296 �l�ments. On s'assure bien que cette liste est initialis�e � chaque d�but de partie*/
        listePossibilitees=new LinkedList<String>();
        if(nbreCasesMastermind==4) {
            for(int i=0;i<this.nbCouleursUtilisablesMastermind;i++) {
                for(int j=0;j<this.nbCouleursUtilisablesMastermind;j++) {
                    for(int k=0;k<this.nbCouleursUtilisablesMastermind;k++) {
                        for(int l=0;l<this.nbCouleursUtilisablesMastermind;l++) {
                            listePossibilitees.add(String.valueOf(i)+String.valueOf(j)+String.valueOf(k)+String.valueOf(l));
                        }

                    }
                }
            }
        }
        else if(nbreCasesMastermind==5) {
            for(int i=0;i<this.nbCouleursUtilisablesMastermind;i++) {
                for(int j=0;j<this.nbCouleursUtilisablesMastermind;j++) {
                    for(int k=0;k<this.nbCouleursUtilisablesMastermind;k++) {
                        for(int l=0;l<this.nbCouleursUtilisablesMastermind;l++) {
                            for(int m=0;m<this.nbCouleursUtilisablesMastermind;m++) {
                                listePossibilitees.add(String.valueOf(i)+String.valueOf(j)+String.valueOf(k)+String.valueOf(l)+String.valueOf(m));
                            }
                        }

                    }
                }
            }
        }
        else {
            for(int i=0;i<this.nbCouleursUtilisablesMastermind;i++) {
                for(int j=0;j<this.nbCouleursUtilisablesMastermind;j++) {
                    for(int k=0;k<this.nbCouleursUtilisablesMastermind;k++) {
                        for(int l=0;l<this.nbCouleursUtilisablesMastermind;l++) {
                            for(int m=0;m<this.nbCouleursUtilisablesMastermind;m++) {
                                for(int n=0;n<this.nbCouleursUtilisablesMastermind;n++) {
                                    listePossibilitees.add(String.valueOf(i)+String.valueOf(j)+String.valueOf(k)+String.valueOf(l)+String.valueOf(m)+String.valueOf(n));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    /* ********************************
     * Mise � jour des observateurs
     **********************************/

    /**
     * M�thode permettant d'ajouter des observateurs � une liste d'observateur.
     *
     * @param obs Un objet de type ObservateurMastermind. Dans le cadre du programme, une
     *            classe qui impl�mente l'interface ObservateurMastermind.
     */
    @Override
    public void addObserverMastermind(ObserverMastermind obs) {
        listeObservateurMastermind.add(obs);
    }

    /**
     * M�thode permettant de mettre � jour les observateurs.
     */
    @Override
    public void updateObservateurMastermind() {
        for(ObserverMastermind obs:listeObservateurMastermind) {
            if(modeDeJeuMastermind==0)
                obs.updateMastermind(reponseOrdinateurModeChallenger);
            else if(modeDeJeuMastermind==1)
                obs.updateMastermind(propositionOrdinateurModeDefenseur);
            else
                obs.updateMastermind(affichage);
        }
    }

    /**
     * M�thode permettant de r�initialiser la liste des observateurs.
     */
    @Override
    public void delObservateurMastermind() {
        listeObservateurMastermind=new ArrayList<ObserverMastermind>();
    }

    /**
     * M�thode permettant de quitter l'application.
     */
    @Override
    public void quitterApplicationObservateurMastermind() {
        for (ObserverMastermind obs : listeObservateurMastermind) {
            obs.quitterApplicationMastermind();
        }
    }

    /**
     * M�thode permettant de retourner � la page d'accueil.
     */
    @Override
    public void acceuilObservateurMastermind() {
        for (ObserverMastermind obs : listeObservateurMastermind) {
            obs.acceuilObservateurMastermind();
        }
    }

    /**
     * M�thode permettant de relancer le m�me jeu.
     */
    @Override
    public void relancerPartieObservateurMastermind() {
        for (ObserverMastermind obs : listeObservateurMastermind) {
            this.propositionSecreteOrdinateurModeDuel="";
            this.propositionSecreteJoueurModeDuel="";
            this.reponseJoueurModeDuel="";
            this.propositionOrdinateurModeDuel="";
            obs.relancerPartieMastermind();
        }
    }


}
