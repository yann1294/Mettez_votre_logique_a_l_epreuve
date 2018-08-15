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
import java.util.Arrays;

/*************************************************************
 * Classe relative au jeu Mastermind en mode duel.
 * Cette classe impl�mente l'interface ObservateurMastermind.
 * @author abrahamyann
 * @see ObserverMastermind
 *************************************************************/

public class MastermindModeDuel extends JPanel implements ObserverMastermind {
    private static final long serialVersionUID = 1L;

    /**
     * Param�tre du jeu Mastermind.
     */
    private int nbreCases,nbEssais,nbCouleursUtilisables;

    /**
     * Variable permettant d'effectuer des contr�les.
     */
    private int ligne=0,colonne=1,colonneCombinaisonSecrete=0,remplissageSolution=0,verifCombinaisonSecrete=0;

    /**
     * Tableau d'entier utilis� pour enregistrer la r�ponse du joueur.
     */
    private int [] tabReponseJoueur;

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
    private JPanel jpContainerGrilleDeJeuGauche=new JPanel(),jpContainerButtonCouleur=new JPanel(),
            jpContainerReponseJoueur=new JPanel(),jPanelContainerSolutionCombinaisonSecreteJoueur=new JPanel(),
            jPanelContainerSolutionCombinaisonSecreteOrdinateur=new JPanel(),jpContainerPartieGauche=new JPanel(),
            jpContainerPartieDroite=new JPanel(),jpContainerGrilleDeJeuDroite=new JPanel();

    /**
     * Tableau de JPanel utilis� pour r�aliser l'interface graphique.
     */
    private JPanel[] jpContainerSolutionGauche,jpContainerSolutionDroite;

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
            imgIconMastermindEmplacementVideSolutionCombinaisonSecreteJoueur=new ImageIcon("resources/MastermindEmplacementVideSolutionCombinaisonSecreteOrdinateur.png"),
            imgIconCouleurGris=new ImageIcon("resources/imgCouleurGris.png"),imgIconCouleurBleuFonce=new ImageIcon("resources/imgCouleurBleuFonce.png"),
            imgIconCouleurNoir=new ImageIcon("resources/imgCouleurNoir.png"),imgIconCouleurMarron=new ImageIcon("resources/imgCouleurMarron.png"),
            imgIconMastermindEmplacementVideSolutionCombinaisonSecreteOrdinateur=new ImageIcon("resources/MastermindEmplacementVideSolutionCombinaisonSecreteOrdinateur.png");

    /**
     * Tableau de JLabel � deux dimensions.
     */
    private JLabel [][] tabJLabelGrilleDeJeuGauche,tabJLabelGrilleDeJeuDroite;

    /**
     * Tableau de JLabel � une dimension.
     */
    private JLabel [] tabJLabelSolutionGauche,tabJLabelSolutionCombinaisonSecreteJoueur,tabJLabelSolutionCombinaisonSecreteOrdinateur,
            tabJLabelSolutionDroite;

    /**
     * JLabel de type informatif.
     */
    private JLabel jlPremiereInstruction= new JLabel("La combinaison secr�te a �t� g�n�r�e par l'ordinateur."),
            jlDeuxiemeInstruction=new JLabel("Veuillez choisir une combinaison secr�te."),
            jlReponseJoueur=new JLabel("Votre r�ponse :"),jlCombinaisonSecreteJoueur=new JLabel("Combinaison Secr�te Joueur :"),
            jlCombinaisonSecreteOrdinateur=new JLabel("Combinaison Secr�te Ordinateur :");

    /**
     * JButton utilis� pour r�aliser l'interface graphique.
     */
    private JButton jbValiderCombinaisonSecreteEtPropositionJoueur=new JButton("Valider"),
            jbEffacerCombinaisonSecreteEtPropositionJoueur=new JButton("Effacer"),
            jbCouleurVerte=new JButton(imgIconCouleurVerte),jbCouleurBleu=new JButton(imgIconCouleurBleu),
            jbCouleurOrange=new JButton(imgIconCouleurOrange),jbCouleurRouge=new JButton(imgIconCouleurRouge),
            jbCouleurJaune=new JButton(imgIconCouleurJaune),jbCouleurViolet=new JButton(imgIconCouleurViolet),
            jbValiderReponseJoueur=new JButton("Valider"),jbEffacerReponseJoueur=new JButton("Effacer"),
            jbPionRougeSolution=new JButton(imgIconMastermindPionRougeSolution),jbPionBlancSolution=new JButton(imgIconMastermindPionBlancSolution),
            jbCouleurGris=new JButton(imgIconCouleurGris),jbCouleurBleuFonce=new JButton(imgIconCouleurBleuFonce),
            jbCouleurNoir=new JButton(imgIconCouleurNoir),jbCouleurMarron=new JButton(imgIconCouleurMarron);

    /**
     * Police d'�criture : nom de la police, style et taille.
     */
    private Font police=new Font("Segoe UI Semilight",Font.PLAIN,14),policeSolution=new Font("Segoe UI Semilight",Font.BOLD,14);

    /**
     * Combinaison secr�te du joueur en mode duel.
     */
    private String propositionSecreteJoueurModeDuel="";

    /**
     * Proposition de l'ordinateur en mode duel.
     */
    private String propositionOrdinateurModeDuel="";

    /**
     * R�ponse du joueur en mode duel.
     */
    private String reponseJoueurModeDuel="";

    /**
     * R�ponse attendue par le joueur.
     */
    private String reponseAttendue="";

    /**
     * Combinaison secr�te g�n�r�e par l'ordinateur.
     */
    private String combinaisonSecreteOrdinateur="";

    /**
     * Proposition du joueur en mode duel.
     */
    private String propositionJoueurModeDuel="";

    /**
     * Mod�le de donn�es relatif au jeu Mastermind.
     * @see ModeleDonneesMastermind
     */
    private ModeleDonneesMastermind modelMastermind;

    /**
     * Controler relatif au jeu Mastermind.
     * @see controller.MastermindController
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
     * Param�tre de type bool�en indiquant si la combinaison secr�te du joueur a �t� valid�.
     */
    private boolean	combinaisonSecreteJoueurValide=false;

    /**
     * Param�tre de type bool�en permettant de s�lectionner la grille de jeu � mettre � jour.
     */
    private boolean	miseAJourAffichageModeDuel=false;

    /**
     * Param�tre de type bool�en indiquant si le mode d�veloppeur est activ� ou non.
     */
    private boolean	modeDeveloppeurActive;

    /**
     * Variable permettant la gestion des logs d'erreurs.
     */
    private static final Logger LOGGER=LogManager.getLogger();

    /**
     * Constructeur de la classe MastermindModeDuel.
     * @param nbCases Nombre de cases du jeu Mastermind.
     * @param nbEssais Nombre d'essais du jeu Mastermind.
     * @param nbCouleursUtilisables Nombre de couleurs utilisables du jeu Mastermind.
     * @param modeDeveloppeurActive Param�tre de type bool�en indiquant si le mode d�veloppeur est activ� ou non.
     * @param modelMastermind Mod�le de donn�es correspondant au jeu Mastermind.
     * @see ModeleDonneesMastermind
     */
    public MastermindModeDuel(int nbCases, int nbEssais,int nbCouleursUtilisables,boolean modeDeveloppeurActive,ModeleDonneesMastermind modelMastermind) {
        LOGGER.trace("Instanciation du jeu Mastermind en mode Duel");
        this.setPreferredSize(new Dimension(1000,740));
        this.setBackground(Color.WHITE);
        this.nbreCases=nbCases;
        this.nbEssais=nbEssais;
        this.nbCouleursUtilisables=nbCouleursUtilisables;
        this.modeDeveloppeurActive=modeDeveloppeurActive;
        this.modelMastermind=modelMastermind;
        this.controlerMastermind=new MastermindController(this.modelMastermind);
        this.generationCombinaisonSecreteOrdinateur();
        //Ce tableau sera utilis� pour enregistrer la r�ponse du joueur
        tabReponseJoueur=new int[this.nbreCases];
        for (int i=0;i<this.nbreCases;i++) {
            tabReponseJoueur[i]=3;
            reponseJoueurModeDuel+="V";
        }

        jlPremiereInstruction.setPreferredSize(new Dimension(1000,30));
        jlPremiereInstruction.setHorizontalAlignment(JLabel.CENTER);
        jlPremiereInstruction.setFont(police);

        jlDeuxiemeInstruction.setPreferredSize(new Dimension(1000,30));
        jlDeuxiemeInstruction.setHorizontalAlignment(JLabel.CENTER);
        jlDeuxiemeInstruction.setFont(police);

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
        jbValiderCombinaisonSecreteEtPropositionJoueur.setEnabled(false);
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
                LOGGER.error("Jeu Mastermind en mode Duel - Erreur lors de la mise en place de l'IHM pour les boutons li�s aux couleurs");
        }

        jpContainerButtonCouleur.add(jbValiderCombinaisonSecreteEtPropositionJoueur);
        jpContainerButtonCouleur.add(jbEffacerCombinaisonSecreteEtPropositionJoueur);

        jlReponseJoueur.setHorizontalAlignment(JLabel.CENTER);
        jlReponseJoueur.setFont(police);
        jbPionRougeSolution.setPreferredSize(new Dimension(14,14));
        jbPionRougeSolution.setEnabled(false);
        jbPionBlancSolution.setPreferredSize(new Dimension(14,14));
        jbPionBlancSolution.setEnabled(false);
        jbValiderReponseJoueur.setEnabled(false);
        jbEffacerReponseJoueur.setEnabled(false);
        jpContainerReponseJoueur.setPreferredSize(new Dimension(1000,40));
        jpContainerReponseJoueur.setBackground(Color.WHITE);
        jpContainerReponseJoueur.add(jlReponseJoueur);
        jpContainerReponseJoueur.add(jbPionRougeSolution);
        jpContainerReponseJoueur.add(jbPionBlancSolution);
        jpContainerReponseJoueur.add(jbValiderReponseJoueur);
        jpContainerReponseJoueur.add(jbEffacerReponseJoueur);

        //Mise en place de l'IHM pour la combinaison secr�te de l'ordinateur
        jlCombinaisonSecreteOrdinateur.setFont(policeSolution);
        jPanelContainerSolutionCombinaisonSecreteOrdinateur.setPreferredSize(new Dimension(470,40));
        jPanelContainerSolutionCombinaisonSecreteOrdinateur.setBackground(Color.WHITE);
        jPanelContainerSolutionCombinaisonSecreteOrdinateur.add(jlCombinaisonSecreteOrdinateur);
        tabJLabelSolutionCombinaisonSecreteOrdinateur=new JLabel[this.nbreCases];
        if(this.modeDeveloppeurActive==false) {
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

        //Mise en place de l'IHM pour la combinaison secr�te du joueur
        jlCombinaisonSecreteJoueur.setFont(policeSolution);
        jPanelContainerSolutionCombinaisonSecreteJoueur.setPreferredSize(new Dimension(470,40));
        jPanelContainerSolutionCombinaisonSecreteJoueur.setBackground(Color.WHITE);
        jPanelContainerSolutionCombinaisonSecreteJoueur.add(jlCombinaisonSecreteJoueur);
        tabJLabelSolutionCombinaisonSecreteJoueur=new JLabel[this.nbreCases];
        for (int i=0;i<this.nbreCases;i++) {
            tabJLabelSolutionCombinaisonSecreteJoueur[i]=new JLabel();
            tabJLabelSolutionCombinaisonSecreteJoueur[i].setPreferredSize(new Dimension(29,29));
            tabJLabelSolutionCombinaisonSecreteJoueur[i].setIcon(imgIconMastermindEmplacementVideSolutionCombinaisonSecreteJoueur);
            jPanelContainerSolutionCombinaisonSecreteJoueur.add(tabJLabelSolutionCombinaisonSecreteJoueur[i]);
        }
        this.initialisationGrilleJeuDroite();
        this.initialisationGrilleJeuGauche();

        //On va ajuster la taille des 2 JPanel principaux en fonction de la taille de la grille de jeu
        if (this.nbEssais==5) {
            jpContainerPartieGauche.setPreferredSize(new Dimension(480,220));
            jpContainerPartieDroite.setPreferredSize(new Dimension(480,220));
        }
        else if(this.nbEssais==10) {
            jpContainerPartieGauche.setPreferredSize(new Dimension(480,370));
            jpContainerPartieDroite.setPreferredSize(new Dimension(480,370));
        }
        else {
            jpContainerPartieGauche.setPreferredSize(new Dimension(480,510));
            jpContainerPartieDroite.setPreferredSize(new Dimension(480,510));
        }

		/*Le JPanel de gauche contiendra les propositions du joueur, les r�ponses de l'ordinateur
		et la combinaison secr�te de l'ordinateur.*/
        jpContainerPartieGauche.setBackground(Color.WHITE);
        jpContainerPartieGauche.setBorder(BorderFactory.createTitledBorder("Propositions Joueur"));
        jpContainerPartieGauche.add(jpContainerGrilleDeJeuGauche);
        jpContainerPartieGauche.add(jPanelContainerSolutionCombinaisonSecreteOrdinateur);

		/*Le JPanel de droite contiendra les propositions de l'ordinateur, les r�ponses du joueur
		et la combinaison secr�te du joueur.*/
        jpContainerPartieDroite.setBackground(Color.WHITE);
        jpContainerPartieDroite.setBorder(BorderFactory.createTitledBorder("Propositions Ordinateur"));
        jpContainerPartieDroite.add(jpContainerGrilleDeJeuDroite);
        jpContainerPartieDroite.add(jPanelContainerSolutionCombinaisonSecreteJoueur);

        this.add(jlPremiereInstruction);
        this.add(jlDeuxiemeInstruction);
        this.add(jpContainerButtonCouleur);
        this.add(jpContainerReponseJoueur);
        this.add(jpContainerPartieGauche);
        this.add(jpContainerPartieDroite);

        this.modelMastermind.addObserverMastermind(this);


        // D�finition des listeners

        jbCouleurBleu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if(!combinaisonSecreteJoueurValide) {
                    updateCombinaisonSecrete(colonneCombinaisonSecrete,imgIconCouleurBleu,"0");
                    colonneCombinaisonSecrete++;
                }
                else {
                    affichagePropositionJoueur(ligne, colonne,imgIconCouleurBleu,"0");
                    colonne++;
                }
            }
        });

        jbCouleurJaune.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if(!combinaisonSecreteJoueurValide) {
                    updateCombinaisonSecrete(colonneCombinaisonSecrete,imgIconCouleurJaune,"1");
                    colonneCombinaisonSecrete++;
                }
                else {
                    affichagePropositionJoueur(ligne, colonne,imgIconCouleurJaune,"1");
                    colonne++;
                }
            }
        });

        jbCouleurOrange.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if(!combinaisonSecreteJoueurValide) {
                    updateCombinaisonSecrete(colonneCombinaisonSecrete,imgIconCouleurOrange,"2");
                    colonneCombinaisonSecrete++;
                }
                else {
                    affichagePropositionJoueur(ligne, colonne,imgIconCouleurOrange,"2");
                    colonne++;
                }
            }
        });

        jbCouleurRouge.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if(!combinaisonSecreteJoueurValide) {
                    updateCombinaisonSecrete(colonneCombinaisonSecrete,imgIconCouleurRouge,"3");
                    colonneCombinaisonSecrete++;
                }
                else {
                    affichagePropositionJoueur(ligne, colonne,imgIconCouleurRouge,"3");
                    colonne++;
                }
            }
        });

        jbCouleurVerte.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if(!combinaisonSecreteJoueurValide) {
                    updateCombinaisonSecrete(colonneCombinaisonSecrete,imgIconCouleurVerte,"4");
                    colonneCombinaisonSecrete++;
                }
                else {
                    affichagePropositionJoueur(ligne, colonne,imgIconCouleurVerte,"4");
                    colonne++;
                }
            }
        });

        jbCouleurViolet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if(!combinaisonSecreteJoueurValide) {
                    updateCombinaisonSecrete(colonneCombinaisonSecrete,imgIconCouleurViolet,"5");
                    colonneCombinaisonSecrete++;
                }
                else {
                    affichagePropositionJoueur(ligne, colonne,imgIconCouleurViolet,"5");
                    colonne++;
                }
            }
        });

        jbCouleurGris.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if(!combinaisonSecreteJoueurValide) {
                    updateCombinaisonSecrete(colonneCombinaisonSecrete,imgIconCouleurGris,"6");
                    colonneCombinaisonSecrete++;
                }
                else {
                    affichagePropositionJoueur(ligne, colonne,imgIconCouleurGris,"6");
                    colonne++;
                }
            }
        });

        jbCouleurBleuFonce.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if(!combinaisonSecreteJoueurValide) {
                    updateCombinaisonSecrete(colonneCombinaisonSecrete,imgIconCouleurBleuFonce,"7");
                    colonneCombinaisonSecrete++;
                }
                else {
                    affichagePropositionJoueur(ligne, colonne,imgIconCouleurBleuFonce,"7");
                    colonne++;
                }
            }
        });

        jbCouleurMarron.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if(!combinaisonSecreteJoueurValide) {
                    updateCombinaisonSecrete(colonneCombinaisonSecrete,imgIconCouleurMarron,"8");
                    colonneCombinaisonSecrete++;
                }
                else {
                    affichagePropositionJoueur(ligne, colonne,imgIconCouleurMarron,"8");
                    colonne++;
                }
            }
        });

        jbCouleurNoir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if(!combinaisonSecreteJoueurValide) {
                    updateCombinaisonSecrete(colonneCombinaisonSecrete,imgIconCouleurNoir,"9");
                    colonneCombinaisonSecrete++;
                }
                else {
                    affichagePropositionJoueur(ligne, colonne,imgIconCouleurNoir,"9");
                    colonne++;
                }
            }
        });

        jbValiderCombinaisonSecreteEtPropositionJoueur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if(!combinaisonSecreteJoueurValide) {
                    jbValiderCombinaisonSecreteEtPropositionJoueur.setEnabled(false);
                    combinaisonSecreteJoueurValide=true;
                    JOptionPane.showMessageDialog(null, "C'est � vous de commencer en effectuant une premi�re proposition!", "Proposition Joueur", JOptionPane.INFORMATION_MESSAGE);
                    controlerMastermind.setPropositionSecreteJoueurModeDuel(propositionSecreteJoueurModeDuel);
                }
                else {
                    jbValiderCombinaisonSecreteEtPropositionJoueur.setEnabled(false);
                    jbEffacerCombinaisonSecreteEtPropositionJoueur.setEnabled(false);
                    jbCouleurBleu.setEnabled(false);
                    jbCouleurJaune.setEnabled(false);
                    jbCouleurOrange.setEnabled(false);
                    jbCouleurRouge.setEnabled(false);
                    jbCouleurVerte.setEnabled(false);
                    jbCouleurViolet.setEnabled(false);
                    jbCouleurGris.setEnabled(false);
                    jbCouleurBleuFonce.setEnabled(false);
                    jbCouleurMarron.setEnabled(false);
                    jbCouleurNoir.setEnabled(false);
                    jbPionRougeSolution.setEnabled(true);
                    jbPionBlancSolution.setEnabled(true);
                    jbValiderReponseJoueur.setEnabled(true);
                    jbEffacerReponseJoueur.setEnabled(true);
                    colonne=1;
                    controlerMastermind.setPropositionJoueurModeDuel(propositionJoueurModeDuel);
                    propositionJoueurModeDuel="";
                    if(finDePartie) {
                        finDePartie=false;
                    }
                }
            }
        });

        jbEffacerCombinaisonSecreteEtPropositionJoueur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if(!combinaisonSecreteJoueurValide) {
                    effacerCombinaisonSecrete(colonneCombinaisonSecrete,imgIconMastermindEmplacementVideSolutionCombinaisonSecreteJoueur);
                    colonneCombinaisonSecrete=0;
                }
                else {
                    effacerPropositionJoueur(ligne, colonne, imgIconMastermindEmplacementVide);
                    colonne=1;
                }
            }
        });

        jbPionRougeSolution.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                affichageReponseJoueur(imgIconMastermindPionRougeSolution,1);
            }
        });

        jbPionBlancSolution.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                affichageReponseJoueur(imgIconMastermindPionBlancSolution,2);
            }
        });

        jbValiderReponseJoueur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LOGGER.debug("Jeu Mastermind en mode Duel - R�ponse du joueur en mode duel :"+reponseJoueurModeDuel);
                calculReponseAttendue();
                LOGGER.debug("Jeu Mastermind en mode Duel - Reponse attendue :" + reponseAttendue);
                if(!reponseJoueurModeDuel.equals(reponseAttendue)) {
                    JOptionPane.showMessageDialog(null,"Attention : votre r�ponse est erron�e. Veuillez saisir une autre r�ponse, svp.",
                            "Message d'avertissement", JOptionPane.WARNING_MESSAGE);
                    effacerReponseJoueur();
                }
                else {
                    ligne++;
                    gestionFinDePartie(reponseJoueurModeDuel,'J');
                    if(!finDePartie) {
                        controlerMastermind.setReponseJoueurModeDuel(reponseJoueurModeDuel);
                        remplissageSolution=0;
                        reponseJoueurModeDuel="";
                        jbCouleurBleu.setEnabled(true);
                        jbCouleurJaune.setEnabled(true);
                        jbCouleurOrange.setEnabled(true);
                        jbCouleurRouge.setEnabled(true);
                        jbCouleurVerte.setEnabled(true);
                        jbCouleurViolet.setEnabled(true);
                        jbCouleurGris.setEnabled(true);
                        jbCouleurBleuFonce.setEnabled(true);
                        jbCouleurMarron.setEnabled(true);
                        jbCouleurNoir.setEnabled(true);
                        jbEffacerCombinaisonSecreteEtPropositionJoueur.setEnabled(true);
                        jbPionRougeSolution.setEnabled(false);
                        jbPionBlancSolution.setEnabled(false);
                        jbValiderReponseJoueur.setEnabled(false);
                        jbEffacerReponseJoueur.setEnabled(false);
                        for (int i=0;i<nbreCases;i++) {
                            tabReponseJoueur[i]=3;
                            reponseJoueurModeDuel+="V";
                        }
                    }
                    else
                        finDePartie=false;
                }
            }
        });

        jbEffacerReponseJoueur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                effacerReponseJoueur();
            }
        });

    }

    /**
     * M�thode permettant d'initialiser la grille de jeu gauche : propositions du joueur, r�ponses de l'ordinateur.
     */
    private void initialisationGrilleJeuGauche() {
        gl=new GridLayout(this.nbEssais,this.nbreCases+2);
        jpContainerGrilleDeJeuGauche.setLayout(gl);
        jpContainerGrilleDeJeuGauche.setPreferredSize(new Dimension(30*(this.nbreCases+2),29*this.nbEssais));
        jpContainerGrilleDeJeuGauche.setBackground(Color.WHITE);

        tabJLabelGrilleDeJeuGauche=new JLabel[this.nbEssais][this.nbreCases+1];
        if(this.nbreCases==4)
            glSolution=new GridLayout(this.nbreCases/2,this.nbreCases/2);
        else if(this.nbreCases==5)
            glSolution=new GridLayout(this.nbreCases/2,this.nbreCases/2+1);
        else
            glSolution=new GridLayout(this.nbreCases/2-1,this.nbreCases/2);

        tabJLabelSolutionGauche= new JLabel[this.nbreCases];
        jpContainerSolutionGauche=new JPanel[this.nbEssais];

        /*
         * La grille de jeu est un JPanel organis� en GridLayout compos� d'un tableau de JLabel et d'un tableau de JPanel organis�
         * �galement en GridLayout.
         *
         */
        for (int i=0;i<this.nbEssais;i++) {
            for (int j=0;j<=this.nbreCases;j++) {
                if(j==0) {
                    tabJLabelGrilleDeJeuGauche[i][j]=new JLabel(String.valueOf(i+1));
                    tabJLabelGrilleDeJeuGauche[i][j].setOpaque(true);
                    tabJLabelGrilleDeJeuGauche[i][j].setBackground(Color.LIGHT_GRAY);
                    tabJLabelGrilleDeJeuGauche[i][j].setForeground(Color.BLACK);
                    tabJLabelGrilleDeJeuGauche[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    tabJLabelGrilleDeJeuGauche[i][j].setHorizontalAlignment(JLabel.CENTER);
                }
                else {
                    tabJLabelGrilleDeJeuGauche[i][j]=new JLabel(imgIconMastermindEmplacementVide);
                }
            }
        }

        for (int i=0;i<this.nbEssais;i++) {
            jpContainerSolutionGauche[i]=new JPanel();
            jpContainerSolutionGauche[i].removeAll();
            jpContainerSolutionGauche[i].setPreferredSize(new Dimension(30,29));
            jpContainerSolutionGauche[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            jpContainerSolutionGauche[i].setLayout(glSolution);
            for (int k=0;k<this.nbreCases;k++) {
                tabJLabelSolutionGauche[k]=new JLabel(imgIconMastermindEmplacementVideSolution);
                jpContainerSolutionGauche[i].add(tabJLabelSolutionGauche[k]);
            }
            jpContainerSolutionGauche[i].revalidate();
            jpContainerSolutionGauche[i].repaint();
        }

        //L'organisation en GridLayout impose un remplissage ligne par ligne
        jpContainerGrilleDeJeuGauche.removeAll();
        for (int i=0;i<this.nbEssais;i++) {
            for (int j=0;j<this.nbreCases+1;j++) {
                jpContainerGrilleDeJeuGauche.add(tabJLabelGrilleDeJeuGauche[i][j]);
            }
            jpContainerGrilleDeJeuGauche.add(jpContainerSolutionGauche[i]);
        }

        jpContainerGrilleDeJeuGauche.revalidate();
        jpContainerGrilleDeJeuGauche.repaint();
    }

    /**
     * M�thode permettant d'initialiser la grille de jeu droite : propositions de l'ordinateur, r�ponses du joueur.
     *
     */
    private void initialisationGrilleJeuDroite() {
        gl=new GridLayout(this.nbEssais,this.nbreCases+2);
        jpContainerGrilleDeJeuDroite.setLayout(gl);
        jpContainerGrilleDeJeuDroite.setPreferredSize(new Dimension(30*(this.nbreCases+2),29*this.nbEssais));
        jpContainerGrilleDeJeuDroite.setBackground(Color.WHITE);

        tabJLabelGrilleDeJeuDroite=new JLabel[this.nbEssais][this.nbreCases+1];
        if(this.nbreCases==4)
            glSolution=new GridLayout(this.nbreCases/2,this.nbreCases/2);
        else if(this.nbreCases==5)
            glSolution=new GridLayout(this.nbreCases/2,this.nbreCases/2+1);
        else
            glSolution=new GridLayout(this.nbreCases/2-1,this.nbreCases/2);

        tabJLabelSolutionDroite= new JLabel[this.nbreCases];
        jpContainerSolutionDroite=new JPanel[this.nbEssais];

        /*
         * La grille de jeu est un JPanel organis� en GridLayout compos� d'un tableau de JLabel et d'un tableau de JPanel organis�
         * �galement en GridLayout.
         *
         */
        for (int i=0;i<this.nbEssais;i++) {
            for (int j=0;j<=this.nbreCases;j++) {
                if(j==0) {
                    tabJLabelGrilleDeJeuDroite[i][j]=new JLabel(String.valueOf(i+1));
                    tabJLabelGrilleDeJeuDroite[i][j].setOpaque(true);
                    tabJLabelGrilleDeJeuDroite[i][j].setBackground(Color.LIGHT_GRAY);
                    tabJLabelGrilleDeJeuDroite[i][j].setForeground(Color.BLACK);
                    tabJLabelGrilleDeJeuDroite[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    tabJLabelGrilleDeJeuDroite[i][j].setHorizontalAlignment(JLabel.CENTER);
                }
                else {
                    tabJLabelGrilleDeJeuDroite[i][j]=new JLabel(imgIconMastermindEmplacementVide);
                }
            }
        }

        for (int i=0;i<this.nbEssais;i++) {
            jpContainerSolutionDroite[i]=new JPanel();
            jpContainerSolutionDroite[i].removeAll();
            jpContainerSolutionDroite[i].setPreferredSize(new Dimension(30,29));
            jpContainerSolutionDroite[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            jpContainerSolutionDroite[i].setLayout(glSolution);
            for (int k=0;k<this.nbreCases;k++) {
                tabJLabelSolutionDroite[k]=new JLabel(imgIconMastermindEmplacementVideSolution);
                jpContainerSolutionDroite[i].add(tabJLabelSolutionDroite[k]);
            }
            jpContainerSolutionDroite[i].revalidate();
            jpContainerSolutionDroite[i].repaint();
        }

        //L'organisation en GridLayout impose un remplissage ligne par ligne
        jpContainerGrilleDeJeuDroite.removeAll();
        for (int i=0;i<this.nbEssais;i++) {
            for (int j=0;j<this.nbreCases+1;j++) {
                jpContainerGrilleDeJeuDroite.add(tabJLabelGrilleDeJeuDroite[i][j]);
            }
            jpContainerGrilleDeJeuDroite.add(jpContainerSolutionDroite[i]);
        }

        jpContainerGrilleDeJeuDroite.revalidate();
        jpContainerGrilleDeJeuDroite.repaint();
    }

    /**
     * G�n�ration de la combinaison secr�te par l'ordinateur. La combinaison secr�te est une combinaison de chiffres
     * cast�s en String, chaque chiffre correspondant � une couleur.
     */
    private void generationCombinaisonSecreteOrdinateur(){
        int nbreAleatoire;
        for (int i=0;i<this.nbreCases;i++) {
            nbreAleatoire=(int)(Math.random()*nbCouleursUtilisables);
            combinaisonSecreteOrdinateur+=String.valueOf(nbreAleatoire);
        }
        controlerMastermind.setModeDeJeu(2);
        controlerMastermind.setNbEssais(this.nbEssais);
        controlerMastermind.setNbreCases(this.nbreCases);
        controlerMastermind.setNbCouleursUtilisables(nbCouleursUtilisables);
        controlerMastermind.setPropositionSecreteOrdinateurModeDuel(combinaisonSecreteOrdinateur);
    }

    /**
     * M�thode permettant de mettre � jour la combinaison secr�te choisie par le joueur.
     * @param col Colonne de la combinaison secr�te.
     * @param couleurChoisie Couleur choisie par le joueur.
     * @param codeCouleur Code couleur associ� � une couleur. Exemple : Bleu :"0", Jaune :"1",...,Noir :"9".
     */
    private void updateCombinaisonSecrete(int col,ImageIcon couleurChoisie, String codeCouleur) {
        if(colonneCombinaisonSecrete<this.nbreCases) {
            propositionSecreteJoueurModeDuel+=codeCouleur;
            tabJLabelSolutionCombinaisonSecreteJoueur[col]=new JLabel(couleurChoisie);
            tabJLabelSolutionCombinaisonSecreteJoueur[col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            jPanelContainerSolutionCombinaisonSecreteJoueur.removeAll();
            jPanelContainerSolutionCombinaisonSecreteJoueur.add(jlCombinaisonSecreteJoueur);
            for (int i=0;i<this.nbreCases;i++) {
                jPanelContainerSolutionCombinaisonSecreteJoueur.add(tabJLabelSolutionCombinaisonSecreteJoueur[i]);
            }
            jPanelContainerSolutionCombinaisonSecreteJoueur.revalidate();
            jPanelContainerSolutionCombinaisonSecreteJoueur.repaint();
            if(colonneCombinaisonSecrete==this.nbreCases-1)
                jbValiderCombinaisonSecreteEtPropositionJoueur.setEnabled(true);
        }
        else if(colonneCombinaisonSecrete==this.nbreCases) {
            JOptionPane.showMessageDialog(null, "La ligne est compl�te. Vous pouvez soit valider, soit effacer la ligne", "Message Informatif",
                    JOptionPane.WARNING_MESSAGE);
            colonneCombinaisonSecrete=this.nbreCases-1;
        }
    }

    /**
     * M�thode permettant d'effacer la combinaison secr�te choisie par le joueur.
     * @param col Colonne de la combinaison secr�te.
     * @param emplacementVide Image correspondant � un emplacement vide.
     */
    private void effacerCombinaisonSecrete(int col,ImageIcon emplacementVide) {
        for (int i=0;i<col;i++) {
            tabJLabelSolutionCombinaisonSecreteJoueur[i]=new JLabel(emplacementVide);
        }
        jPanelContainerSolutionCombinaisonSecreteJoueur.removeAll();
        jPanelContainerSolutionCombinaisonSecreteJoueur.add(jlCombinaisonSecreteJoueur);
        for (int i=0;i<this.nbreCases;i++) {
            jPanelContainerSolutionCombinaisonSecreteJoueur.add(tabJLabelSolutionCombinaisonSecreteJoueur[i]);
        }
        jPanelContainerSolutionCombinaisonSecreteJoueur.revalidate();
        jPanelContainerSolutionCombinaisonSecreteJoueur.repaint();
        jbValiderCombinaisonSecreteEtPropositionJoueur.setEnabled(false);
        propositionSecreteJoueurModeDuel="";
    }

    /**
     * M�thode permettant de mettre � jour la grille de jeu gauche suite aux propositions du joueur.
     * @param lig Ligne de la grille de jeu gauche.
     * @param col Colonne de la grille de jeu gauche.
     * @param couleurChoisie Couleur choisie par le joueur.
     * @param codeCouleur Code couleur associ� � une couleur. Exemple : Bleu :"0", Jaune :"1",...,Noir :"9".
     */
    private void affichagePropositionJoueur(int lig, int col,ImageIcon couleurChoisie, String codeCouleur) {
        if(colonne<=this.nbreCases) {
            tabJLabelGrilleDeJeuGauche[lig][col]=new JLabel(couleurChoisie);
            propositionJoueurModeDuel+=codeCouleur;
            tabJLabelGrilleDeJeuGauche[lig][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            //L'organisation en GridLayout impose un remplissage ligne par ligne
            jpContainerGrilleDeJeuGauche.removeAll();
            for (int i=0;i<this.nbEssais;i++) {
                for (int j=0;j<this.nbreCases+1;j++) {
                    jpContainerGrilleDeJeuGauche.add(tabJLabelGrilleDeJeuGauche[i][j]);
                }
                jpContainerGrilleDeJeuGauche.add(jpContainerSolutionGauche[i]);
            }
            jpContainerGrilleDeJeuGauche.revalidate();
            jpContainerGrilleDeJeuGauche.repaint();
            if(colonne==this.nbreCases) {
                jbValiderCombinaisonSecreteEtPropositionJoueur.setEnabled(true);
            }
        }

        if(colonne>this.nbreCases) {
            JOptionPane.showMessageDialog(null, "La ligne est compl�te. Vous pouvez soit valider, soit effacer la ligne", "Message Informatif",
                    JOptionPane.WARNING_MESSAGE);
            colonne=this.nbreCases;
        }
    }

    /**
     * M�thode permettant d'effacer une ligne de la grille de jeu gauche.
     * @param lig Ligne de la grille de jeu gauche.
     * @param col Colonne de la grille de jeu gauche.
     * @param emplacementVide Image correspondant � un emplacement vide.
     */
    private void effacerPropositionJoueur(int lig, int col,ImageIcon emplacementVide) {
        for(int i=1;i<col;i++) {
            tabJLabelGrilleDeJeuGauche[lig][i]=new JLabel(emplacementVide);
        }
        propositionJoueurModeDuel="";
        //L'organisation en GridLayout impose un remplissage ligne par ligne
        jpContainerGrilleDeJeuGauche.removeAll();
        for (int i=0;i<this.nbEssais;i++) {
            for (int j=0;j<this.nbreCases+1;j++) {
                jpContainerGrilleDeJeuGauche.add(tabJLabelGrilleDeJeuGauche[i][j]);
            }
            jpContainerGrilleDeJeuGauche.add(jpContainerSolutionGauche[i]);
        }
        jpContainerGrilleDeJeuGauche.revalidate();
        jpContainerGrilleDeJeuGauche.repaint();
        jbValiderCombinaisonSecreteEtPropositionJoueur.setEnabled(false);

    }

    /**
     * M�thode permettant d'afficher la r�ponse du joueur sur la grille de jeu droite.
     * @param couleurChoisieReponse Couleur choisie par le joueur pour r�pondre : Rouge ou blanc.
     * @param codeCouleurReponse Code couleur associ� � une couleur. Exemple : Rouge:1, Blanc:2, Vide:3.
     */
    private void affichageReponseJoueur(ImageIcon couleurChoisieReponse, int codeCouleurReponse) {
        if(remplissageSolution<this.nbreCases) {
            jpContainerSolutionDroite[ligne].removeAll();
            reponseJoueurModeDuel="";
            tabReponseJoueur[remplissageSolution]=codeCouleurReponse;

            for(int i=0;i<remplissageSolution;i++) {
                jpContainerSolutionDroite[ligne].add(tabJLabelSolutionDroite[i]);
            }

            if(codeCouleurReponse==1) {
                tabJLabelSolutionDroite[remplissageSolution]=new JLabel(couleurChoisieReponse);
                jpContainerSolutionDroite[ligne].add(tabJLabelSolutionDroite[remplissageSolution]);
                remplissageSolution++;
            }
            else if(codeCouleurReponse==2) {
                tabJLabelSolutionDroite[remplissageSolution]=new JLabel(imgIconMastermindPionBlancSolution);
                jpContainerSolutionDroite[ligne].add(tabJLabelSolutionDroite[remplissageSolution]);
                remplissageSolution++;
            }

            for (int i=remplissageSolution;i<this.nbreCases;i++) {
                tabJLabelSolutionDroite[i]=new JLabel(imgIconMastermindEmplacementVideSolution);
                jpContainerSolutionDroite[ligne].add(tabJLabelSolutionDroite[i]);
            }
            jpContainerSolutionDroite[ligne].revalidate();
            jpContainerSolutionDroite[ligne].repaint();

            //On r�ordonne le tableau d'entiers dans l'ordre num�rique puis on effectue la correspondance avec les couleurs dans
            //l'ordre suivant : pions rouges (si pr�sents), pions blancs (si pr�sents), et emplacement vide
            Arrays.sort(tabReponseJoueur);

            for (int i=0;i<this.nbreCases;i++) {
                if(tabReponseJoueur[i]==1)
                    reponseJoueurModeDuel+="R";
                else if(tabReponseJoueur[i]==2)
                    reponseJoueurModeDuel+="B";
                else
                    reponseJoueurModeDuel+="V";
            }

        }
        else {
            JOptionPane.showMessageDialog(null, "Vous avez bien renseign�s tous les pions. Vous pouvez soit valider votre r�ponse, soit effacer.", "Message Informatif",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * M�thode permettant d'effacer la r�ponse du joueur sur la grille de jeu droite.
     */
    private void effacerReponseJoueur() {
        jpContainerSolutionDroite[ligne].removeAll();
        jpContainerSolutionDroite[ligne].revalidate();
        for (int i=0;i<nbreCases;i++) {
            tabJLabelSolutionDroite[i]=new JLabel(imgIconMastermindEmplacementVideSolution);
            jpContainerSolutionDroite[ligne].add(tabJLabelSolutionDroite[i]);
        }
        jpContainerSolutionDroite[ligne].repaint();
        remplissageSolution=0;
        reponseJoueurModeDuel="";
        for (int i=0;i<nbreCases;i++) {
            tabReponseJoueur[i]=3;
            reponseJoueurModeDuel+="V";
        }
    }

    /**
     * Cette m�thode permet de d�terminer la r�ponse attendue par le joueur.
     */
    private void calculReponseAttendue() {
        //Analyse des boules bien plac�es (pions rouges) et mal plac�es (pions blancs). Pour faciliter le traitement, on va dire
        //que pions rouges �quivaut � 1, pions blancs � 2 et emplacement vide � 3.
        int[] tabReponseAttendue=new int[this.nbreCases];
        char[] tabAnalyse=new char[this.nbreCases];
        tabAnalyse=this.propositionSecreteJoueurModeDuel.toCharArray();
        for (int i=0;i<this.nbreCases;i++) {
            tabReponseAttendue[i]=3;
        }
        reponseAttendue="";

        for (int i=0;i<this.nbreCases;i++) {
            if(this.propositionOrdinateurModeDuel.charAt(i)==tabAnalyse[i]) {
                tabReponseAttendue[i]=1;
                tabAnalyse[i]=' ';
            }
        }

        for (int i=0;i<this.nbreCases;i++) {
            for(int j=0;j<this.nbreCases;j++) {
                if(this.propositionOrdinateurModeDuel.charAt(i)==tabAnalyse[j]&&tabReponseAttendue[i]!=1) {
                    tabReponseAttendue[i]=2;
                    tabAnalyse[j]=' ';
                    break;
                }
            }
        }

        //On r�ordonne le tableau d'entiers dans l'ordre num�rique puis on effectue la correspondance avec les couleurs dans l'ordre suivant :
        //pions rouges (si pr�sents), pions blancs (si pr�sents), et emplacement vide
        Arrays.sort(tabReponseAttendue);

        for (int i=0;i<this.nbreCases;i++) {
            if(tabReponseAttendue[i]==1)
                reponseAttendue+="R";
            else if(tabReponseAttendue[i]==2)
                reponseAttendue+="B";
            else
                reponseAttendue+="V";
        }
    }

    /**
     * M�thode permettant d'afficher la combinaison secr�te g�n�r�e par l'ordinateur.
     */
    private void affichageSolution() {
        jPanelContainerSolutionCombinaisonSecreteOrdinateur.removeAll();
        jPanelContainerSolutionCombinaisonSecreteOrdinateur.add(jlCombinaisonSecreteOrdinateur);
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
                    LOGGER.error("Jeu Mastermind en mode Duel - Erreur de correspondance entre la combinaison secr�te de l'ordinateur et les couleurs");
            }
            tabJLabelSolutionCombinaisonSecreteOrdinateur[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            jPanelContainerSolutionCombinaisonSecreteOrdinateur.add(tabJLabelSolutionCombinaisonSecreteOrdinateur[i]);
        }
        jPanelContainerSolutionCombinaisonSecreteOrdinateur.revalidate();
        jPanelContainerSolutionCombinaisonSecreteOrdinateur.repaint();
    }

    /**
     * Gestion de la fin de la partie en fonction de la r�ponse du joueur ou de l'ordinateur.
     * @param reponse R�ponse du joueur ou de l'ordinateur.
     * @param identiteJoueur Identite de celui qui r�pond : joueur ou ordinateur.
     */
    private void gestionFinDePartie(String reponse, char identiteJoueur) {
        verifCombinaisonSecrete=0;
        for (int i=0;i<reponse.length();i++) {
            if(reponse.charAt(i)=='R')
                verifCombinaisonSecrete++;
        }

        //En cas de victoire
        if(verifCombinaisonSecrete==nbreCases&& identiteJoueur=='O') {
            this.affichageSolution();
            JOptionPane.showMessageDialog(null,"F�licitations!!! Vous avez trouv� la combinaison secr�te de l'ordinateur en premier. ",
                    "Fin de Partie",JOptionPane.INFORMATION_MESSAGE);
        }

        //En cas de d�fa�te
        if(verifCombinaisonSecrete==nbreCases&& identiteJoueur=='J') {
            this.affichageSolution();
            JOptionPane.showMessageDialog(null, "Vous avez perdu. L'ordinateur a trouv� en premier votre combinaison secr�te. "
                    + "\nPour information, la combinaison secr�te de l'ordinateur est affich�e.", "Fin de Partie",JOptionPane.INFORMATION_MESSAGE);
        }

        //En cas de match nul
        if (ligne==nbEssais && verifCombinaisonSecrete!=nbreCases) {
            this.affichageSolution();
            JOptionPane.showMessageDialog(null, "Match nul. Le nombre d'essai maximal a �t� d�pass�.\nPour information, la combinaison secr�te de l'ordinateur est "
                            + "affich�e.", "Fin de Partie",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        //En cas de d�fa�te, de victoire ou de match nul
        if (ligne==nbEssais||verifCombinaisonSecrete==nbreCases) {
            LOGGER.trace("Jeu Mastermind en mode Duel - Fin de partie");
            finDePartie=true;
            jdFinDePartie =new BoiteDialogueFinDePartie(null,"Fin de Partie",true);
            controlerMastermind.setChoixFinDePartie(jdFinDePartie.getChoixFinDePartie());
        }
    }

    /* ***********************************
     * Impl�mentation du pattern Observer
     *************************************/

    /**
     * Pattern Observer - M�thode non utilis�e dans cette classe.
     */
    public void quitterApplicationMastermind() {}

    /**
     * Pattern Observer - M�thode non utilis�e dans cette classe.
     */
    public void acceuilObservateurMastermind() {}

    /**
     * Pattern Observer - M�thode permettant de mettre � jour soit la grille de jeu gauche avec les r�ponses de l'ordinateur,
     * soit la grille de jeu droite avec les propositions de l'ordinateur.
     * @param affichage Cette variable peut correspondre aux propositions et aux r�ponses de l'ordinateur.
     */
    public void updateMastermind(String affichage) {

        //Dans un cas, on met � jour la grille de jeu gauche avec les r�ponses de l'ordinateur
        if(!miseAJourAffichageModeDuel) {
			/*Pour une ligne donn�e, on met � jour le JPanel jpContainerSolutionGauche en suivant les �tapes habituelles pour un JPanel :
			On supprime les anciens composants, on ajoute les nouveaux, on revalide et on fait appel � la m�thode repaint()*/
            jpContainerSolutionGauche[ligne].removeAll();
            for (int i=0;i<this.nbreCases;i++) {
                if(affichage.charAt(i)=='R') {
                    tabJLabelSolutionGauche[i]=new JLabel(imgIconMastermindPionRougeSolution);
                    jpContainerSolutionGauche[ligne].add(tabJLabelSolutionGauche[i]);
                }
                else if(affichage.charAt(i)=='B') {
                    tabJLabelSolutionGauche[i]=new JLabel(imgIconMastermindPionBlancSolution);
                    jpContainerSolutionGauche[ligne].add(tabJLabelSolutionGauche[i]);
                }
                else {
                    tabJLabelSolutionGauche[i]=new JLabel(imgIconMastermindEmplacementVideSolution);
                    jpContainerSolutionGauche[ligne].add(tabJLabelSolutionGauche[i]);
                }
            }
            jpContainerSolutionGauche[ligne].revalidate();
            jpContainerSolutionGauche[ligne].repaint();
            miseAJourAffichageModeDuel=true;
            this.gestionFinDePartie(affichage,'O');
        }

        //Dans l'autre cas, on met � jour la grille de jeu droite avec les propositions de l'ordinateur
        else {
            this.propositionOrdinateurModeDuel= affichage;
			/*On convertit la proposition de l'ordinateur en JLabel avec les images ad�quates en vue de l'affichage
			 sur la grille de jeu*/
            jpContainerGrilleDeJeuDroite.removeAll();
            for (int i=0;i<nbreCases;i++) {
                switch(affichage.charAt(i)) {
                    case '0':
                        tabJLabelGrilleDeJeuDroite[ligne][i+1]=new JLabel(imgIconCouleurBleu);
                        tabJLabelGrilleDeJeuDroite[ligne][i+1].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        break;
                    case '1':
                        tabJLabelGrilleDeJeuDroite[ligne][i+1]=new JLabel(imgIconCouleurJaune);
                        tabJLabelGrilleDeJeuDroite[ligne][i+1].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        break;
                    case '2':
                        tabJLabelGrilleDeJeuDroite[ligne][i+1]=new JLabel(imgIconCouleurOrange);
                        tabJLabelGrilleDeJeuDroite[ligne][i+1].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        break;
                    case '3':
                        tabJLabelGrilleDeJeuDroite[ligne][i+1]=new JLabel(imgIconCouleurRouge);
                        tabJLabelGrilleDeJeuDroite[ligne][i+1].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        break;
                    case '4':
                        tabJLabelGrilleDeJeuDroite[ligne][i+1]=new JLabel(imgIconCouleurVerte);
                        tabJLabelGrilleDeJeuDroite[ligne][i+1].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        break;
                    case '5':
                        tabJLabelGrilleDeJeuDroite[ligne][i+1]=new JLabel(imgIconCouleurViolet);
                        tabJLabelGrilleDeJeuDroite[ligne][i+1].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        break;
                    case '6':
                        tabJLabelGrilleDeJeuDroite[ligne][i+1]=new JLabel(imgIconCouleurGris);
                        tabJLabelGrilleDeJeuDroite[ligne][i+1].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        break;
                    case '7':
                        tabJLabelGrilleDeJeuDroite[ligne][i+1]=new JLabel(imgIconCouleurBleuFonce);
                        tabJLabelGrilleDeJeuDroite[ligne][i+1].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        break;
                    case '8':
                        tabJLabelGrilleDeJeuDroite[ligne][i+1]=new JLabel(imgIconCouleurMarron);
                        tabJLabelGrilleDeJeuDroite[ligne][i+1].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        break;
                    case '9':
                        tabJLabelGrilleDeJeuDroite[ligne][i+1]=new JLabel(imgIconCouleurNoir);
                        tabJLabelGrilleDeJeuDroite[ligne][i+1].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        break;
                    default :
                        LOGGER.error("Jeu Mastermind en mode Duel - Erreur de correspondance entre la proposition de l'ordinateur et les couleurs");
                }
            }
            for (int i=0;i<this.nbEssais;i++) {
                for (int j=0;j<this.nbreCases+1;j++) {
                    jpContainerGrilleDeJeuDroite.add(tabJLabelGrilleDeJeuDroite[i][j]);
                }
                jpContainerGrilleDeJeuDroite.add(jpContainerSolutionDroite[i]);
            }
            jpContainerGrilleDeJeuDroite.revalidate();
            jpContainerGrilleDeJeuDroite.repaint();
            miseAJourAffichageModeDuel=false;
        }
    }

    /**
     * Pattern Observer - M�thode permettant de relancer le m�me jeu : R�initialisation de l'IHM des grilles de jeu et
     * des combinaisons secr�tes en bas de page ainsi que toutes les variables, et reg�n�ration d'une nouvelle combinaison
     * secr�te pour l'ordinateur.
     */
    /**
     * M�thode permettant de relancer le m�me jeu.
     */
    @Override
    public void relancerPartieMastermind() {
        LOGGER.trace("Jeu Mastermind en mode Duel - Partie relanc�e");
        //R�initialisation de l'IHM des grilles de jeu
        this.initialisationGrilleJeuGauche();
        this.initialisationGrilleJeuDroite();

        //R�initialisation de la combinaison secr�te de l'ordinateur lorsque le mode d�veloppeur est d�sactiv�
        if(this.modeDeveloppeurActive==false) {
            jPanelContainerSolutionCombinaisonSecreteOrdinateur.removeAll();
            jPanelContainerSolutionCombinaisonSecreteOrdinateur.add(jlCombinaisonSecreteOrdinateur);
            for (int i=0;i<this.nbreCases;i++) {
                tabJLabelSolutionCombinaisonSecreteOrdinateur[i]=new JLabel();
                tabJLabelSolutionCombinaisonSecreteOrdinateur[i].setPreferredSize(new Dimension(29,29));
                tabJLabelSolutionCombinaisonSecreteOrdinateur[i].setIcon(imgIconMastermindEmplacementVideSolutionCombinaisonSecreteOrdinateur);
                jPanelContainerSolutionCombinaisonSecreteOrdinateur.add(tabJLabelSolutionCombinaisonSecreteOrdinateur[i]);
            }
            jPanelContainerSolutionCombinaisonSecreteOrdinateur.revalidate();
            jPanelContainerSolutionCombinaisonSecreteOrdinateur.repaint();
        }

        //R�initialisation de la combinaison secr�te du joueur
        jPanelContainerSolutionCombinaisonSecreteJoueur.removeAll();
        jPanelContainerSolutionCombinaisonSecreteJoueur.add(jlCombinaisonSecreteJoueur);
        for (int i=0;i<this.nbreCases;i++) {
            tabJLabelSolutionCombinaisonSecreteJoueur[i]=new JLabel();
            tabJLabelSolutionCombinaisonSecreteJoueur[i].setPreferredSize(new Dimension(29,29));
            tabJLabelSolutionCombinaisonSecreteJoueur[i].setIcon(imgIconMastermindEmplacementVideSolutionCombinaisonSecreteJoueur);
            jPanelContainerSolutionCombinaisonSecreteJoueur.add(tabJLabelSolutionCombinaisonSecreteJoueur[i]);
        }
        jPanelContainerSolutionCombinaisonSecreteJoueur.revalidate();
        jPanelContainerSolutionCombinaisonSecreteJoueur.repaint();

        jbCouleurBleu.setEnabled(true);
        jbCouleurJaune.setEnabled(true);
        jbCouleurOrange.setEnabled(true);
        jbCouleurRouge.setEnabled(true);
        jbCouleurVerte.setEnabled(true);
        jbCouleurViolet.setEnabled(true);
        jbCouleurGris.setEnabled(true);
        jbCouleurBleuFonce.setEnabled(true);
        jbCouleurMarron.setEnabled(true);
        jbCouleurNoir.setEnabled(true);
        jbValiderCombinaisonSecreteEtPropositionJoueur.setEnabled(false);
        jbEffacerCombinaisonSecreteEtPropositionJoueur.setEnabled(true);
        jbPionRougeSolution.setEnabled(false);
        jbPionBlancSolution.setEnabled(false);
        jbValiderReponseJoueur.setEnabled(false);
        jbEffacerReponseJoueur.setEnabled(false);
        reponseJoueurModeDuel="";
        for (int i=0;i<this.nbreCases;i++) {
            tabReponseJoueur[i]=3;
            reponseJoueurModeDuel+="V";
        }
        ligne=0;
        colonne=1;
        remplissageSolution=0;
        colonneCombinaisonSecrete=0;
        verifCombinaisonSecrete=0;
        reponseAttendue="";
        combinaisonSecreteOrdinateur="";
        propositionSecreteJoueurModeDuel="";
        propositionOrdinateurModeDuel="";
        propositionJoueurModeDuel="";
        combinaisonSecreteJoueurValide=false;
        miseAJourAffichageModeDuel=false;
        this.generationCombinaisonSecreteOrdinateur();
        //R�initialisation de la combinaison secr�te de l'ordinateur lorsque le mode d�veloppeur est activ�
        if(modeDeveloppeurActive==true)
            this.affichageSolution();

    }
}
