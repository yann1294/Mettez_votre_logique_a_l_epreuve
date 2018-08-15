package vue;

import DesignPattern.ObserverMastermind;
import controller.MastermindController;
import model.ModeleDonneesMastermind;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/************************************************************
 * Classe relative au jeu Mastermind en mode challenger.
 * Cette classe impl�mente l'interface ObservateurMastermind.
 * @author abrahamyann
 * @see ObserverMastermind
 ************************************************************/

public class MastermindModeChallenger  extends JPanel implements ObserverMastermind {

    private static final long serialVersionUID = 1L;

    /**
     * Param�tre du jeu Mastermind.
     */
    private int nbreCases, nbEssais,nbCouleursUtilisables;

    /**
     * Variable permettant d'effectuer des contr�les.
     */
    private int ligne=0,colonne=1,verifCombinaisonSecrete=0;

    /**
     * Param�tre de type bool�en indiquant si le mode d�veloppeur est activ� ou non.
     */
    private boolean modeDeveloppeurActive;

    /**
     * GridLayout correspondant � l'ensemble de la grille de jeu.
     */
    private GridLayout gl;

    /**
     * GridLayout correspondant � la derni�re colonne de la grille de jeu.
     */
    private GridLayout glSolution;

    /**
     * JPanel utilis� pour r�aliser l'interface graphique.
     */
    private JPanel jpContainerGrilleDeJeu=new JPanel(),jpContainerButtonEffacerValider=new JPanel(),jpContainerButtonCouleur=new JPanel(),
            jPanelContainerSolutionCombinaisonSecreteOrdinateur=new JPanel();

    /**
     * Tableau de JPanel utilis� pour r�aliser l'interface graphique.
     */
    private JPanel[] jpContainerSolution;

    /**
     * Image utilis�e pour r�aliser l'interface graphique.
     */
    private ImageIcon imgIconMastermindEmplacementVide=new ImageIcon("resources/MastermindEmplacementVide.png"),
            imgIconMastermindEmplacementVideSolution=new ImageIcon("resources/MastermindEmplacementVideSolution.png"),
            imgIconCouleurVerte=new ImageIcon("resources/imgCouleurVerte.png"),imgIconCouleurBleu=new ImageIcon("resources/imgCouleurBleu.png"),
            imgIconCouleurOrange=new ImageIcon("resources/imgCouleurOrange.png"),imgIconCouleurRouge=new ImageIcon("resources/imgCouleurRouge.png"),
            imgIconCouleurJaune=new ImageIcon("resources/imgCouleurJaune.png"),imgIconCouleurViolet=new ImageIcon("resources/imgCouleurViolet.png"),
            imgIconMastermindPionRougeSolution=new ImageIcon("resources/MastermindPionRougeSolution.png"),
            imgIconMastermindPionBlancSolution=new ImageIcon("resources/MastermindPionBlancSolution.png"),
            imgIconMastermindEmplacementVideSolutionCombinaisonSecreteOrdinateur=new ImageIcon("resources/MastermindEmplacementVideSolutionCombinaisonSecreteOrdinateur.png"),
            imgIconCouleurGris=new ImageIcon("resources/imgCouleurGris.png"),imgIconCouleurBleuFonce=new ImageIcon("resources/imgCouleurBleuFonce.png"),
            imgIconCouleurNoir=new ImageIcon("resources/imgCouleurNoir.png"),imgIconCouleurMarron=new ImageIcon("resources/imgCouleurMarron.png");

    /**
     * Tableau de JLabel � deux dimensions.
     */
    private JLabel [][] tabJLabelGrilleDeJeu;

    /**
     * Tableau de JLabel � une dimension.
     */
    private JLabel [] tabJLabelSolution,tabJLabelSolutionCombinaisonSecreteOrdinateur;

    /**
     * JLabel de type informatif.
     */
    private JLabel jlPremiereInstruction=new JLabel("La combinaison secr�te a �t� g�n�r�e par l'ordinateur."),jlSolution=new JLabel("Solution :");

    /**
     * JButton utilis� pour r�aliser l'interface graphique.
     */
    private JButton jbEffacer=new JButton("Effacer la ligne"), jbValider=new JButton("Valider"),
            jbCouleurVerte=new JButton(imgIconCouleurVerte),jbCouleurBleu=new JButton(imgIconCouleurBleu),
            jbCouleurOrange=new JButton(imgIconCouleurOrange),jbCouleurRouge=new JButton(imgIconCouleurRouge),
            jbCouleurJaune=new JButton(imgIconCouleurJaune),jbCouleurViolet=new JButton(imgIconCouleurViolet),
            jbCouleurGris=new JButton(imgIconCouleurGris),jbCouleurBleuFonce=new JButton(imgIconCouleurBleuFonce),
            jbCouleurNoir=new JButton(imgIconCouleurNoir),jbCouleurMarron=new JButton(imgIconCouleurMarron);

    /**
     * Police d'�criture : nom de la police, style et taille.
     */
    private Font police=new Font("Segoe UI Semilight",Font.PLAIN,14),policeSolution=new Font("Segoe UI Semilight",Font.BOLD,14);

    /**
     * Combinaison secr�te g�n�r�e par l'ordinateur.
     */
    private String combinaisonSecreteOrdinateur="";

    /**
     * Proposition du joueur en mode challenger.
     */
    private String propositionJoueurModeChallenger="";

    /**
     * Mod�le de donn�es relatif au jeu Mastermind.
     * @see ModeleDonneesMastermind
     */
    private ModeleDonneesMastermind modelMastermind;

    /**
     * Controler relatif au jeu Mastermind.
     * @see MastermindController
     */
    private MastermindController controlerMastermind;

    /**
     * Boite de dialogue permettant d'effectuer un choix en fin de partie.
     * @see BoiteDialogueFinDePartie
     */
    private BoiteDialogueFinDePartie jdFinDePartie;

    /**
     * Variable de type bool�enne permettant d'indiquer la fin de la partie.
     */
    private boolean finDePartie=false;

    /**
     * Variable permettant la gestion des logs d'erreurs.
     */
    private static final Logger LOGGER=LogManager.getLogger();

    /**
     * Constructeur de la classe MastermindModeChallenger.
     * @param nbCases Nombre de cases du jeu Mastermind.
     * @param nbEssais Nombre d'essais du jeu Mastermind.
     * @param nbCouleursUtilisables Nombre de couleurs utilisables du jeu Mastermind.
     * @param modeDeveloppeurActive Param�tre de type bool�en indiquant si le mode d�veloppeur est activ� ou non.
     * @param modelMastermind Mod�le de donn�es correspondant au jeu Mastermind.
     * @see ModeleDonneesMastermind
     */
    public MastermindModeChallenger(int nbCases, int nbEssais, int nbCouleursUtilisables,boolean modeDeveloppeurActive,ModeleDonneesMastermind modelMastermind) {
        LOGGER.trace("Instanciation du jeu Mastermind en mode Challenger");
        this.setPreferredSize(new Dimension(1000,740));
        this.setBackground(Color.WHITE);
        this.nbreCases=nbCases;
        this.nbEssais=nbEssais;
        this.nbCouleursUtilisables=nbCouleursUtilisables;
        this.modeDeveloppeurActive=modeDeveloppeurActive;
        this.modelMastermind=modelMastermind;
        this.controlerMastermind=new MastermindController(this.modelMastermind);
        this.generationCombinaisonSecreteOrdinateur();
        jlPremiereInstruction.setPreferredSize(new Dimension(1000,40));
        jlPremiereInstruction.setHorizontalAlignment(JLabel.CENTER);
        jlPremiereInstruction.setFont(police);

        jbValider.setEnabled(false);
        jpContainerButtonEffacerValider.setPreferredSize(new Dimension(1000,40));
        jpContainerButtonEffacerValider.setBackground(Color.WHITE);
        jpContainerButtonEffacerValider.add(jbValider);
        jpContainerButtonEffacerValider.add(jbEffacer);

        jbCouleurBleu.setPreferredSize(new Dimension(29,29));
        jbCouleurJaune.setPreferredSize(new Dimension(29,29));
        jbCouleurOrange.setPreferredSize(new Dimension(29,29));
        jbCouleurRouge.setPreferredSize(new Dimension(29,29));
        jbCouleurVerte.setPreferredSize(new Dimension(29,29));
        jbCouleurViolet.setPreferredSize(new Dimension(29,29));
        jbCouleurGris.setPreferredSize(new Dimension(29,29));
        jbCouleurBleuFonce.setPreferredSize(new Dimension(29,29));
        jbCouleurMarron.setPreferredSize(new Dimension(29,29));
        jbCouleurNoir.setPreferredSize(new Dimension(29,29));

        jpContainerButtonCouleur.setPreferredSize(new Dimension(1000,40));
        jpContainerButtonCouleur.setBackground(Color.WHITE);

        switch(this.nbCouleursUtilisables) {
            case 4 :
                jpContainerButtonCouleur.add(jbCouleurBleu);
                jpContainerButtonCouleur.add(jbCouleurJaune);
                jpContainerButtonCouleur.add(jbCouleurOrange);
                jpContainerButtonCouleur.add(jbCouleurRouge);
                break;
            case 5 :
                jpContainerButtonCouleur.add(jbCouleurBleu);
                jpContainerButtonCouleur.add(jbCouleurJaune);
                jpContainerButtonCouleur.add(jbCouleurOrange);
                jpContainerButtonCouleur.add(jbCouleurRouge);
                jpContainerButtonCouleur.add(jbCouleurVerte);
                break;
            case 6 :
                jpContainerButtonCouleur.add(jbCouleurBleu);
                jpContainerButtonCouleur.add(jbCouleurJaune);
                jpContainerButtonCouleur.add(jbCouleurOrange);
                jpContainerButtonCouleur.add(jbCouleurRouge);
                jpContainerButtonCouleur.add(jbCouleurVerte);
                jpContainerButtonCouleur.add(jbCouleurViolet);
                break;
            case 7 :
                jpContainerButtonCouleur.add(jbCouleurBleu);
                jpContainerButtonCouleur.add(jbCouleurJaune);
                jpContainerButtonCouleur.add(jbCouleurOrange);
                jpContainerButtonCouleur.add(jbCouleurRouge);
                jpContainerButtonCouleur.add(jbCouleurVerte);
                jpContainerButtonCouleur.add(jbCouleurViolet);
                jpContainerButtonCouleur.add(jbCouleurGris);
                break;
            case 8 :
                jpContainerButtonCouleur.add(jbCouleurBleu);
                jpContainerButtonCouleur.add(jbCouleurJaune);
                jpContainerButtonCouleur.add(jbCouleurOrange);
                jpContainerButtonCouleur.add(jbCouleurRouge);
                jpContainerButtonCouleur.add(jbCouleurVerte);
                jpContainerButtonCouleur.add(jbCouleurViolet);
                jpContainerButtonCouleur.add(jbCouleurGris);
                jpContainerButtonCouleur.add(jbCouleurBleuFonce);
                break;
            case 9 :
                jpContainerButtonCouleur.add(jbCouleurBleu);
                jpContainerButtonCouleur.add(jbCouleurJaune);
                jpContainerButtonCouleur.add(jbCouleurOrange);
                jpContainerButtonCouleur.add(jbCouleurRouge);
                jpContainerButtonCouleur.add(jbCouleurVerte);
                jpContainerButtonCouleur.add(jbCouleurViolet);
                jpContainerButtonCouleur.add(jbCouleurGris);
                jpContainerButtonCouleur.add(jbCouleurBleuFonce);
                jpContainerButtonCouleur.add(jbCouleurMarron);
                break;
            case 10 :
                jpContainerButtonCouleur.add(jbCouleurBleu);
                jpContainerButtonCouleur.add(jbCouleurJaune);
                jpContainerButtonCouleur.add(jbCouleurOrange);
                jpContainerButtonCouleur.add(jbCouleurRouge);
                jpContainerButtonCouleur.add(jbCouleurVerte);
                jpContainerButtonCouleur.add(jbCouleurViolet);
                jpContainerButtonCouleur.add(jbCouleurGris);
                jpContainerButtonCouleur.add(jbCouleurBleuFonce);
                jpContainerButtonCouleur.add(jbCouleurMarron);
                jpContainerButtonCouleur.add(jbCouleurNoir);
                break;
            default :
                LOGGER.error("Jeu Mastermind en mode Challenger - Erreur lors de la mise en place de l'IHM pour les boutons li�s aux couleurs");
        }

        jlSolution.setFont(policeSolution);
        jPanelContainerSolutionCombinaisonSecreteOrdinateur.setPreferredSize(new Dimension(1000,40));
        jPanelContainerSolutionCombinaisonSecreteOrdinateur.setBackground(Color.WHITE);
        jPanelContainerSolutionCombinaisonSecreteOrdinateur.add(jlSolution);
        tabJLabelSolutionCombinaisonSecreteOrdinateur=new JLabel[this.nbreCases];
        if(modeDeveloppeurActive==false) {
            for (int i=0;i<this.nbreCases;i++) {
                tabJLabelSolutionCombinaisonSecreteOrdinateur[i]=new JLabel();
                tabJLabelSolutionCombinaisonSecreteOrdinateur[i].setPreferredSize(new Dimension(29,29));
                tabJLabelSolutionCombinaisonSecreteOrdinateur[i].setIcon(imgIconMastermindEmplacementVideSolutionCombinaisonSecreteOrdinateur);
                jPanelContainerSolutionCombinaisonSecreteOrdinateur.add(tabJLabelSolutionCombinaisonSecreteOrdinateur[i]);
            }
        }
        else {
            this.affichageSolution();
        }

        this.add(jlPremiereInstruction);
        this.add(jpContainerButtonEffacerValider);
        this.add(jpContainerButtonCouleur);
        this.initialisationGrilleJeu();
        this.add(jpContainerGrilleDeJeu);
        this.add(jPanelContainerSolutionCombinaisonSecreteOrdinateur);

        this.modelMastermind.addObserverMastermind(this);


        // D�finition des listeners

        jbCouleurBleu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                updateGrilleJeu(ligne, colonne,imgIconCouleurBleu,"0");
                colonne++;
            }
        });

        jbCouleurJaune.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                updateGrilleJeu(ligne, colonne,imgIconCouleurJaune,"1");
                colonne++;
            }
        });

        jbCouleurOrange.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                updateGrilleJeu(ligne, colonne,imgIconCouleurOrange,"2");
                colonne++;
            }
        });

        jbCouleurRouge.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                updateGrilleJeu(ligne, colonne,imgIconCouleurRouge,"3");
                colonne++;
            }
        });

        jbCouleurVerte.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                updateGrilleJeu(ligne, colonne,imgIconCouleurVerte,"4");
                colonne++;
            }
        });

        jbCouleurViolet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                updateGrilleJeu(ligne, colonne,imgIconCouleurViolet,"5");
                colonne++;
            }
        });

        jbCouleurGris.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                updateGrilleJeu(ligne, colonne,imgIconCouleurGris,"6");
                colonne++;
            }
        });

        jbCouleurBleuFonce.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                updateGrilleJeu(ligne, colonne,imgIconCouleurBleuFonce,"7");
                colonne++;
            }
        });

        jbCouleurMarron.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                updateGrilleJeu(ligne, colonne,imgIconCouleurMarron,"8");
                colonne++;
            }
        });

        jbCouleurNoir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                updateGrilleJeu(ligne, colonne,imgIconCouleurNoir,"9");
                colonne++;
            }
        });

        jbValider.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                controlerMastermind.setPropositionJoueurModeChallenger(propositionJoueurModeChallenger);
                if(!finDePartie) {
                    propositionJoueurModeChallenger="";
                    ligne++;
                    colonne=1;
                    jbValider.setEnabled(false);
                }
                else
                    finDePartie=false;
            }
        });

        jbEffacer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                effacerLigneGrilleJeu(ligne, colonne, imgIconMastermindEmplacementVide);
                colonne=1;
            }
        });
    }

    /**
     * M�thode permettant d'initialiser la grille de jeu.
     */
    private void initialisationGrilleJeu() {
        gl=new GridLayout(this.nbEssais,this.nbreCases+2);
        jpContainerGrilleDeJeu.setLayout(gl);
        jpContainerGrilleDeJeu.setPreferredSize(new Dimension(30*(this.nbreCases+2),29*this.nbEssais));
        tabJLabelGrilleDeJeu=new JLabel[this.nbEssais][this.nbreCases+1];
        if(this.nbreCases==4)
            glSolution=new GridLayout(this.nbreCases/2,this.nbreCases/2);
        else if(this.nbreCases==5)
            glSolution=new GridLayout(this.nbreCases/2,this.nbreCases/2+1);
        else
            glSolution=new GridLayout(this.nbreCases/2-1,this.nbreCases/2);

        tabJLabelSolution= new JLabel[this.nbreCases];
        jpContainerSolution=new JPanel[this.nbEssais];

        /*
         * La grille de jeu est un JPanel organis� en GridLayout compos� d'un tableau de JLabel et d'un tableau de JPanel organis�
         * �galement en GridLayout.
         *
         */
        for (int i=0;i<this.nbEssais;i++) {
            for (int j=0;j<=this.nbreCases;j++) {
                if(j==0) {
                    tabJLabelGrilleDeJeu[i][j]=new JLabel(String.valueOf(i+1));
                    tabJLabelGrilleDeJeu[i][j].setOpaque(true);
                    tabJLabelGrilleDeJeu[i][j].setBackground(Color.LIGHT_GRAY);
                    tabJLabelGrilleDeJeu[i][j].setForeground(Color.BLACK);
                    tabJLabelGrilleDeJeu[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    tabJLabelGrilleDeJeu[i][j].setHorizontalAlignment(JLabel.CENTER);
                }
                else {
                    tabJLabelGrilleDeJeu[i][j]=new JLabel(imgIconMastermindEmplacementVide);
                }
            }
        }

        for (int i=0;i<this.nbEssais;i++) {
            jpContainerSolution[i]=new JPanel();
            jpContainerSolution[i].removeAll();
            jpContainerSolution[i].setPreferredSize(new Dimension(30,29));
            jpContainerSolution[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            jpContainerSolution[i].setLayout(glSolution);
            for (int k=0;k<this.nbreCases;k++) {
                tabJLabelSolution[k]=new JLabel(imgIconMastermindEmplacementVideSolution);
                jpContainerSolution[i].add(tabJLabelSolution[k]);
            }
            jpContainerSolution[i].revalidate();
            jpContainerSolution[i].repaint();
        }

        //L'organisation en GridLayout impose un remplissage ligne par ligne
        jpContainerGrilleDeJeu.removeAll();
        for (int i=0;i<this.nbEssais;i++) {
            for (int j=0;j<this.nbreCases+1;j++) {
                jpContainerGrilleDeJeu.add(tabJLabelGrilleDeJeu[i][j]);
            }
            jpContainerGrilleDeJeu.add(jpContainerSolution[i]);
        }
        jpContainerGrilleDeJeu.revalidate();
        jpContainerGrilleDeJeu.repaint();
    }


    /**
     * M�thode permettant de mettre � jour la grille de jeu selon la proposition du joueur.
     * @param lig Ligne de la grille de jeu.
     * @param col Colonne de la grille de jeu.
     * @param couleurChoisie Couleur choisie par le joueur.
     * @param codeCouleur Code couleur associ� � une couleur. Exemple : Bleu :"0", Jaune :"1",...,Noir :"9".
     */
    private void updateGrilleJeu(int lig, int col,ImageIcon couleurChoisie, String codeCouleur) {
        if(colonne<=this.nbreCases) {
            tabJLabelGrilleDeJeu[lig][col]=new JLabel(couleurChoisie);
            propositionJoueurModeChallenger+=codeCouleur;
            tabJLabelGrilleDeJeu[lig][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            //L'organisation en GridLayout impose un remplissage ligne par ligne
            jpContainerGrilleDeJeu.removeAll();
            for (int i=0;i<this.nbEssais;i++) {
                for (int j=0;j<this.nbreCases+1;j++) {
                    jpContainerGrilleDeJeu.add(tabJLabelGrilleDeJeu[i][j]);
                }
                jpContainerGrilleDeJeu.add(jpContainerSolution[i]);
            }
            jpContainerGrilleDeJeu.revalidate();
            jpContainerGrilleDeJeu.repaint();
            if(colonne==this.nbreCases) {
                jbValider.setEnabled(true);
            }
        }

        if(colonne>this.nbreCases) {
            JOptionPane.showMessageDialog(null, "La ligne est compl�te. Vous pouvez soit valider, soit effacer la ligne", "Message Informatif",
                    JOptionPane.WARNING_MESSAGE);
            colonne=this.nbreCases;
        }
    }

    /**
     * M�thode permettant d'effacer une ligne de la grille de jeu.
     * @param lig Ligne de la grille de jeu.
     * @param col Colonne de la grille de jeu.
     * @param emplacementVide Image correspondant � un emplacement vide.
     */
    private void effacerLigneGrilleJeu(int lig, int col,ImageIcon emplacementVide) {
        for(int i=1;i<col;i++) {
            tabJLabelGrilleDeJeu[lig][i]=new JLabel(emplacementVide);
        }
        propositionJoueurModeChallenger="";
        //L'organisation en GridLayout impose un remplissage ligne par ligne
        jpContainerGrilleDeJeu.removeAll();
        for (int i=0;i<this.nbEssais;i++) {
            for (int j=0;j<this.nbreCases+1;j++) {
                jpContainerGrilleDeJeu.add(tabJLabelGrilleDeJeu[i][j]);
            }
            jpContainerGrilleDeJeu.add(jpContainerSolution[i]);
        }
        jpContainerGrilleDeJeu.revalidate();
        jpContainerGrilleDeJeu.repaint();
        jbValider.setEnabled(false);

    }

    /**
     * G�n�ration de la combinaison secr�te par l'ordinateur. La combinaison secr�te est
     * une combinaison de chiffres cast�s en String, chaque chiffre correspondant � une couleur.
     */
    private void generationCombinaisonSecreteOrdinateur(){
        int nbreAleatoire;
        for (int i=0;i<this.nbreCases;i++) {
            nbreAleatoire=(int)(Math.random()*nbCouleursUtilisables);
            combinaisonSecreteOrdinateur+=String.valueOf(nbreAleatoire);
        }
        LOGGER.debug("Jeu Mastermind en mode Challenger - G�n�ration de la combinaison secr�te:"+combinaisonSecreteOrdinateur);
        controlerMastermind.setModeDeJeu(0);
        controlerMastermind.setNbEssais(this.nbEssais);
        controlerMastermind.setNbreCases(this.nbreCases);
        controlerMastermind.setPropositionSecreteOrdinateurModeChallenger(combinaisonSecreteOrdinateur);
    }

    /* ***********************************
     * Impl�mentation du pattern Observer
     *************************************/

    /**
     * Pattern Observer - M�thode permettant de mettre � jour la grille de jeu selon la r�ponse de l'ordinateur.
     * @param reponse R�ponse de l'ordinateur.
     */
    @Override
    public void updateMastermind(String reponse) {
       /*Pour une ligne donn�e, on met � jour le JPanel jpContainerSolution en suivant les �tapes habituelles pour un JPanel :
		On supprime les anciens composants, on ajoute les nouveaux, on revalide et on fait appel � la m�thode repaint()*/

        jpContainerSolution[ligne].removeAll();

        for (int i=0;i<this.nbreCases;i++) {
            if(reponse.charAt(i)=='R') {
                tabJLabelSolution[i]=new JLabel(imgIconMastermindPionRougeSolution);
                jpContainerSolution[ligne].add(tabJLabelSolution[i]);
            }
            else if(reponse.charAt(i)=='B') {
                tabJLabelSolution[i]=new JLabel(imgIconMastermindPionBlancSolution);
                jpContainerSolution[ligne].add(tabJLabelSolution[i]);
            }
            else {
                tabJLabelSolution[i]=new JLabel(imgIconMastermindEmplacementVideSolution);
                jpContainerSolution[ligne].add(tabJLabelSolution[i]);
            }
        }

        jpContainerSolution[ligne].revalidate();
        jpContainerSolution[ligne].repaint();

        //L'organisation en GridLayout impose un remplissage ligne par ligne
        jpContainerGrilleDeJeu.removeAll();
        for (int i=0;i<this.nbEssais;i++) {
            for (int j=0;j<this.nbreCases+1;j++) {
                jpContainerGrilleDeJeu.add(tabJLabelGrilleDeJeu[i][j]);
            }
            jpContainerGrilleDeJeu.add(jpContainerSolution[i]);
        }
        jpContainerGrilleDeJeu.revalidate();
        jpContainerGrilleDeJeu.repaint();
        this.gestionFinDePartie(reponse);
    }
    /**
     * Pattern Observer - M�thode permettant de relancer le m�me jeu : R�initialisation de l'IHM de la grille de jeu et
     * de la solution en bas de page ainsi que toutes les variables et reg�n�ration d'une nouvelle combinaison secr�te.
     */
    @Override
    public void relancerPartieMastermind() {
        LOGGER.trace("Jeu Mastermind en mode Challenger - Partie relanc�e");
        //R�initialisation de l'IHM : on refait appel � la fonction InitialisationGrilleJeu() et on r�initialise la solution en bas de page
        this.initialisationGrilleJeu();
        if(modeDeveloppeurActive==false) {
            jPanelContainerSolutionCombinaisonSecreteOrdinateur.removeAll();
            jPanelContainerSolutionCombinaisonSecreteOrdinateur.add(jlSolution);
            for (int i=0;i<this.nbreCases;i++) {
                tabJLabelSolutionCombinaisonSecreteOrdinateur[i]=new JLabel();
                tabJLabelSolutionCombinaisonSecreteOrdinateur[i].setPreferredSize(new Dimension(29,29));
                tabJLabelSolutionCombinaisonSecreteOrdinateur[i].setIcon(imgIconMastermindEmplacementVideSolutionCombinaisonSecreteOrdinateur);
                jPanelContainerSolutionCombinaisonSecreteOrdinateur.add(tabJLabelSolutionCombinaisonSecreteOrdinateur[i]);
            }
            jPanelContainerSolutionCombinaisonSecreteOrdinateur.revalidate();
            jPanelContainerSolutionCombinaisonSecreteOrdinateur.repaint();
        }

        ligne=0;
        colonne=1;
        verifCombinaisonSecrete=0;
        jbValider.setEnabled(false);
        propositionJoueurModeChallenger="";
        combinaisonSecreteOrdinateur="";
        this.generationCombinaisonSecreteOrdinateur();
        if(modeDeveloppeurActive==true)
            this.affichageSolution();
    }

    /**
     * Gestion de la fin de la partie en fonction de la r�ponse de l'ordinateur.
     * @param reponse R�ponse de l'ordinateur.
     */
    private void gestionFinDePartie(String reponse) {
        verifCombinaisonSecrete=0;
        for (int i=0;i<reponse.length();i++) {
            if(reponse.charAt(i)=='R')
                verifCombinaisonSecrete++;
        }

        //En cas de victoire
        if(verifCombinaisonSecrete==nbreCases) {
            JOptionPane.showMessageDialog(null, "F�licitations!!! Vous avez trouv� la combinaison secr�te en moins de "+nbEssais+" essais.",
                    "Fin de Partie",JOptionPane.INFORMATION_MESSAGE);
            this.affichageSolution();
        }

        //En cas de d�fa�te
        if (ligne==nbEssais-1 && verifCombinaisonSecrete!=nbreCases) {
            this.affichageSolution();
            JOptionPane.showMessageDialog(null, "Vous avez perdu! Pour information, la combinaison secr�te est affich�e en bas de la page.", "Fin de Partie",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        //En cas de d�fa�te ou de victoire
        if(ligne==nbEssais-1||verifCombinaisonSecrete==nbreCases) {
            LOGGER.trace("Jeu Mastermind en mode Challenger - Fin de partie");
            finDePartie=true;
            jdFinDePartie =new BoiteDialogueFinDePartie(null,"Fin de Partie",true);
            controlerMastermind.setChoixFinDePartie(jdFinDePartie.getChoixFinDePartie());
        }
    }
    /**
     * M�thode permettant d'afficher la combinaison secr�te g�n�r�e par l'ordinateur.
     */
    private void affichageSolution() {
        jPanelContainerSolutionCombinaisonSecreteOrdinateur.removeAll();
        jPanelContainerSolutionCombinaisonSecreteOrdinateur.add(jlSolution);
        for (int i=0;i<nbreCases;i++) {
            switch(combinaisonSecreteOrdinateur.charAt(i)) {
                case '0':
                    tabJLabelSolutionCombinaisonSecreteOrdinateur[i]=new JLabel(imgIconCouleurBleu);
                    break;
                case '1':
                    tabJLabelSolutionCombinaisonSecreteOrdinateur[i]=new JLabel(imgIconCouleurJaune);
                    break;
                case '2':
                    tabJLabelSolutionCombinaisonSecreteOrdinateur[i]=new JLabel(imgIconCouleurOrange);
                    break;
                case '3':
                    tabJLabelSolutionCombinaisonSecreteOrdinateur[i]=new JLabel(imgIconCouleurRouge);
                    break;
                case '4':
                    tabJLabelSolutionCombinaisonSecreteOrdinateur[i]=new JLabel(imgIconCouleurVerte);
                    break;
                case '5':
                    tabJLabelSolutionCombinaisonSecreteOrdinateur[i]=new JLabel(imgIconCouleurViolet);
                    break;
                case '6':
                    tabJLabelSolutionCombinaisonSecreteOrdinateur[i]=new JLabel(imgIconCouleurGris);
                    break;
                case '7':
                    tabJLabelSolutionCombinaisonSecreteOrdinateur[i]=new JLabel(imgIconCouleurBleuFonce);
                    break;
                case '8':
                    tabJLabelSolutionCombinaisonSecreteOrdinateur[i]=new JLabel(imgIconCouleurMarron);
                    break;
                case '9':
                    tabJLabelSolutionCombinaisonSecreteOrdinateur[i]=new JLabel(imgIconCouleurNoir);
                    break;
                default :
                    LOGGER.error("Jeu Mastermind en mode Challenger - Erreur de correspondance entre la combinaison secr�te et les couleurs");
            }

            tabJLabelSolutionCombinaisonSecreteOrdinateur[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            jPanelContainerSolutionCombinaisonSecreteOrdinateur.add(tabJLabelSolutionCombinaisonSecreteOrdinateur[i]);
        }
        jPanelContainerSolutionCombinaisonSecreteOrdinateur.revalidate();
        jPanelContainerSolutionCombinaisonSecreteOrdinateur.repaint();
    }

    /**
     * Pattern Observer - M�thode non utilis�e dans cette classe.
     */
    @Override
    public void quitterApplicationMastermind() {

    }

    /**
     * Pattern Observer - M�thode non utilis�e dans cette classe.
     */
    @Override
    public void acceuilObservateurMastermind() {

    }




}

