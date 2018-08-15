package vue;

import DesignPattern.Observer;
import DesignPattern.ObserverMastermind;
import model.ModeleDonnees;
import model.ModeleDonneesMastermind;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/************************************************************************************************************
 * La classe Fenetre est la fen�tre principale de l'application. Elle correspond � la page d'accueil du jeu.
 * Elle est compos�e d'une barre de menu permettant d'acc�der aux jeux RecherchePlusMoins et Mastermind
 * dans les modes Challenger, D�fenseur et Duel, mais �galement aux param�tres de jeu et aux instructions.
 * Elle impl�mente les interfaces Observateur et ObservateurMastermind.
 * @author Abraham Yann
 *
 ************************************************************************************************************/
public class Fenetre extends JFrame implements Observer, ObserverMastermind {

    private static final long serialVersionUID = 1L;

    /**
     * JPanel principal de la classe.
     */
    private JPanel jpContainer = new JPanel();

    /**
     * Image de la page d'accueil.
     */
    private JLabel imageJeu = new JLabel(new ImageIcon("resources/Mastermind.jpg"));

    /**
     * Barre de menu.
     */
    private JMenuBar jmbMenuBar = new JMenuBar();

    /**
     * El�ment de la barre de menu.
     */
    private JMenu jmFichier = new JMenu("Fichier"), jmInstructions = new JMenu("Instructions"),
            jmJeuRecherchePlusMoins = new JMenu("Recherche +/-"), jmJeuMastermind = new JMenu("Mastermind"),
            jmParametres=new JMenu("Param�tres");
    /**
     * Champ permettant d'acc�der � la fonctionnalit� correspondante de l'application.
     */
    private JMenuItem jmiModeChallenger=new JMenuItem("Mode Challenger"), jmiModeDefenseur=new JMenuItem("Mode D�fenseur"),
            jmiModeDuel=new JMenuItem("Mode Duel"),jmi2ModeChallenger=new JMenuItem("Mode Challenger"),
            jmi2ModeDefenseur=new JMenuItem("Mode D�fenseur"),jmi2ModeDuel=new JMenuItem("Mode Duel"),
            jmiQuitter = new JMenuItem("Quitter"), jmiJeuRecherchePlusMoins = new JMenuItem("Recherche +/-"),
            jmiMastermind = new JMenuItem("Mastermind"),jmiParametres=new JMenuItem("Param�tres");
    /**
     * Mod�le de donn�es relatif au jeu RecherchePlusMoins.
     * @see ModeleDonnees
     */
    private ModeleDonnees model;

    /**
     * Mod�le de donn�es relatif au jeu Mastermind.
     * @see ModeleDonneesMastermind
     */
    private ModeleDonneesMastermind modelMastermind;

    /**
     * Objet li� au jeu correspondant.
     * @see RecherchePlusMoinsModeChallenger
     */
    private RecherchePlusMoinsModeChallenger jpRecherchePlusMoinsModeChallenger;

    /**
     * Objet li� au jeu correspondant.
     * @see RecherchePlusMoinsModeDefenseur
     */
    private RecherchePlusMoinsModeDefenseur jpRecherchePlusMoinsModeDefenseur;

    /**
     * Objet li� au jeu correspondant.
     * @see RecherchePlusMoinsModeDuel
     */
    private RecherchePlusMoinsModeDuel jpRecherchePlusMoinsModeDuel;

    /**
     * Objet li� au jeu correspondant.
     * @see MastermindModeChallenger
     */
    private MastermindModeChallenger jpMastermindModeChallenger;

    /**
     * Objet li� au jeu correspondant.
     * @see MastermindModeDefenseur
     */
    private MastermindModeDefenseur jpMastermindModeDefenseur;

    /**
     * Objet li� au jeu correspondant.
     * @see MastermindModeDuel
     */
    private MastermindModeDuel jpMastermindModeDuel;

    /**
     * Boite de dialogue permettant de changer les param�tres du jeu.
     * @see BoiteDialogueParametrage
     */
    private BoiteDialogueParametrage jdParametrage;

    /**
     * Flux d'entr�e permettant de lire le fichier resources/config.properties.
     */
    private InputStream input;

    /**
     * Objet permettant de charger le fichier resources/config.properties.
     */
    private Properties prop;

    /**
     * Variable permettant la gestion des logs d'erreurs.
     */
    private static final Logger LOGGER=LogManager.getLogger();

    /**
     * Param�tre du jeu RecherchePlusMoins.
     */
    private int nbreCasesRecherchePlusMoins=4, nbEssaisRecherchePlusMoins=10;

    /**
     * Param�tre du jeu Mastermind.
     */
    private int nbreCasesMastermind=4,nbEssaisMastermind=10,nbCouleursUtilisablesMastermind=6;

    /**
     * Param�tre relatif aux jeux RecherchePlusMoins et Mastermind. Par d�faut, le mode d�veloppeur est d�sactiv�.
     */
    private boolean modeDeveloppeurActive=false;

    /**
     * Constructeur de la classe Fenetre.
     * @param model Mod�le de donn�es correspondant au jeu RecherchePlusMoins.
     * @param modelMastermind Mod�le de donn�es correspondant au jeu Mastermind.
     * @param modeDeveloppeurActiveConsole Param�tre de type bool�en indiquant si le mode d�veloppeur est activ� ou non.
     * @see ModeleDonnees
     * @see ModeleDonneesMastermind
     */
    public Fenetre(ModeleDonnees model,ModeleDonneesMastermind modelMastermind, boolean modeDeveloppeurActiveConsole) {
        LOGGER.trace("Instanciation de la fen�tre principale");
        this.setTitle("Mettez votre logique � l'�preuve");
        this.setSize(1000, 740);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setIconImage(new ImageIcon("resources/MastermindFormatIcone.png").getImage());
        imageJeu.setPreferredSize(new Dimension(600,637));
        jpContainer.setPreferredSize(new Dimension(1000,740));
        jpContainer.add(imageJeu);
        jpContainer.setBackground(Color.WHITE);
        this.setContentPane(jpContainer);
        this.model=model;
        this.model.addObservateur(this);
        this.modelMastermind=modelMastermind;
        this.modelMastermind.addObserverMastermind(this);
        this.modeDeveloppeurActive=modeDeveloppeurActiveConsole;
        LOGGER.trace("Initialisation des mod�les de donn�es");

        //On r�cup�re les donn�es enregistr�es dans le fichier config.properties
        prop=new Properties();
        input=null;

        try {
            input=new FileInputStream("resources/config.properties");
            prop.load(input);
            nbEssaisRecherchePlusMoins=Integer.valueOf(prop.getProperty("param.nbEssaisActifRecherchePlusMoins"));
            nbreCasesRecherchePlusMoins=Integer.valueOf(prop.getProperty("param.nbreCasesActifRecherchePlusMoins"));
            nbEssaisMastermind=Integer.valueOf(prop.getProperty("param.nbEssaisActifMastermind"));
            nbreCasesMastermind=Integer.valueOf(prop.getProperty("param.nbreCasesActifMastermind"));
            nbCouleursUtilisablesMastermind=Integer.valueOf(prop.getProperty("param.nbCouleursUtilisablesActifMastermind"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(input!=null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        this.initMenu();
        this.setVisible(true);
    }

    /**
     * M�thode permettant d'initialiser le menu de la fen�tre principale.
     * Un listener a �t� ajout� � chaque �l�ment du menu.
     * */
    public void initMenu() {
        LOGGER.trace("Initialisation du menu");

        // D�finition des mn�moniques
        jmFichier.setMnemonic('F');
        jmInstructions.setMnemonic('I');
        jmParametres.setMnemonic('P');

        // D�finition des acc�l�rateurs
        jmiQuitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));
        jmiParametres.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK));

        // Construction du menu
        jmJeuRecherchePlusMoins.add(jmiModeChallenger);
        jmJeuRecherchePlusMoins.add(jmiModeDefenseur);
        jmJeuRecherchePlusMoins.add(jmiModeDuel);
        jmJeuMastermind.add(jmi2ModeChallenger);
        jmJeuMastermind.add(jmi2ModeDefenseur);
        jmJeuMastermind.add(jmi2ModeDuel);
        jmFichier.add(jmJeuRecherchePlusMoins);
        jmFichier.add(jmJeuMastermind);
        jmFichier.addSeparator();
        jmFichier.add(jmiQuitter);
        jmParametres.add(jmiParametres);
        jmInstructions.add(jmiJeuRecherchePlusMoins);
        jmInstructions.add(jmiMastermind);
        jmbMenuBar.add(jmFichier);
        jmbMenuBar.add(jmParametres);
        jmbMenuBar.add(jmInstructions);
        this.setJMenuBar(jmbMenuBar);

        // D�finition des listeners
        jmiModeChallenger.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jpContainer.removeAll();
                jpContainer.setBackground(Color.WHITE);
                jpRecherchePlusMoinsModeChallenger=new RecherchePlusMoinsModeChallenger(nbreCasesRecherchePlusMoins,
                        nbEssaisRecherchePlusMoins,modeDeveloppeurActive,model);
                jpContainer.add(jpRecherchePlusMoinsModeChallenger);
                jpContainer.revalidate();
                jpContainer.repaint();
                jmParametres.setEnabled(false);

                /*
                 * Cette instruction permet de placer le curseur sur le JFormattedTextField voulu. Il faut imp�rativement placer
                 * cette instruction apr�s le .add
                 */
                jpRecherchePlusMoinsModeChallenger.getJftfPropositionJoueur().requestFocusInWindow();

                /* *******************************************************************************************************
                 *Ne pas oublier de r�initialiser le mod�le dans le cas o� on revient plusieurs fois � la page d'acceuil
                 *********************************************************************************************************/
                initModel();

            }
        });

        jmiModeDefenseur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jpContainer.removeAll();
                jpContainer.setBackground(Color.WHITE);
                jpRecherchePlusMoinsModeDefenseur=new RecherchePlusMoinsModeDefenseur(nbreCasesRecherchePlusMoins,
                        nbEssaisRecherchePlusMoins,modeDeveloppeurActive,model);
                jpContainer.add(jpRecherchePlusMoinsModeDefenseur);
                jpContainer.revalidate();
                jpContainer.repaint();

                jmParametres.setEnabled(false);

                /*
                 * Cette instruction permet de placer le curseur sur le JFormattedTextField voulu. Il faut imp�rativement placer
                 * cette instruction apr�s le .add
                 */
                jpRecherchePlusMoinsModeDefenseur.getJftfCombinaisonSecreteJoueur().requestFocusInWindow();

                /* *******************************************************************************************************
                 *Ne pas oublier de r�initialiser le mod�le dans le cas o� on revient plusieurs fois � la page d'acceuil
                 *********************************************************************************************************/
                initModel();

            }
        });

        jmiModeDuel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jpContainer.removeAll();
                jpContainer.setBackground(Color.WHITE);
                jpRecherchePlusMoinsModeDuel=new RecherchePlusMoinsModeDuel(nbreCasesRecherchePlusMoins,
                        nbEssaisRecherchePlusMoins,modeDeveloppeurActive,model);
                jpContainer.add(jpRecherchePlusMoinsModeDuel);
                jpContainer.revalidate();
                jpContainer.repaint();
                jmParametres.setEnabled(false);

                /*
                 * Cette instruction permet de placer le curseur sur le JFormattedTextField voulu. Il faut imp�rativement placer
                 * cette instruction apr�s le .add
                 */
                jpRecherchePlusMoinsModeDuel.getJftfCombinaisonSecreteJoueur().requestFocusInWindow();

                /* *******************************************************************************************************
                 *Ne pas oublier de r�initialiser le mod�le dans le cas o� on revient plusieurs fois � la page d'acceuil
                 *********************************************************************************************************/
                initModel();

            }
        });

        jmi2ModeChallenger.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jpContainer.removeAll();
                jpContainer.setBackground(Color.WHITE);
                jpMastermindModeChallenger=new MastermindModeChallenger(nbreCasesMastermind,nbEssaisMastermind,
                        nbCouleursUtilisablesMastermind,modeDeveloppeurActive,modelMastermind);
                jpContainer.add(jpMastermindModeChallenger);
                jpContainer.revalidate();
                jpContainer.repaint();
                jmParametres.setEnabled(false);

                /* *******************************************************************************************************
                 *Ne pas oublier de r�initialiser le mod�le dans le cas o� on revient plusieurs fois � la page d'acceuil
                 *********************************************************************************************************/
                initModel();

            }
        });

        jmi2ModeDefenseur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jpContainer.removeAll();
                jpContainer.setBackground(Color.WHITE);
                jpMastermindModeDefenseur=new MastermindModeDefenseur(nbreCasesMastermind,nbEssaisMastermind,
                        nbCouleursUtilisablesMastermind,modeDeveloppeurActive,modelMastermind);
                jpContainer.add(jpMastermindModeDefenseur);
                jpContainer.revalidate();
                jpContainer.repaint();
                jmParametres.setEnabled(false);

                /* *******************************************************************************************************
                 *Ne pas oublier de r�initialiser le mod�le dans le cas o� on revient plusieurs fois � la page d'acceuil
                 *********************************************************************************************************/
                initModel();

            }
        });

        jmi2ModeDuel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jpContainer.removeAll();
                jpContainer.setBackground(Color.WHITE);
                jpMastermindModeDuel=new MastermindModeDuel(nbreCasesMastermind,nbEssaisMastermind,
                        nbCouleursUtilisablesMastermind,modeDeveloppeurActive,modelMastermind);
                jpContainer.add(jpMastermindModeDuel);
                jpContainer.revalidate();
                jpContainer.repaint();
                jmParametres.setEnabled(false);

                /* *******************************************************************************************************
                 *Ne pas oublier de r�initialiser le mod�le dans le cas o� on revient plusieurs fois � la page d'acceuil
                 *********************************************************************************************************/
                initModel();

            }
        });


        jmiParametres.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                jdParametrage=new BoiteDialogueParametrage(null,"Param�tres des Jeux",true,nbEssaisRecherchePlusMoins,
                        nbreCasesRecherchePlusMoins,nbEssaisMastermind,nbreCasesMastermind,nbCouleursUtilisablesMastermind,
                        modeDeveloppeurActive);
                nbEssaisRecherchePlusMoins=jdParametrage.getNbEssaisRecherchePlusMoins();
                nbreCasesRecherchePlusMoins=jdParametrage.getNbreCasesRecherchePlusMoins();
                nbEssaisMastermind=jdParametrage.getNbEssaisMastermind();
                nbreCasesMastermind=jdParametrage.getNbreCasesMastermind();
                modeDeveloppeurActive=jdParametrage.getModeDeveloppeurActive();
                nbCouleursUtilisablesMastermind=jdParametrage.getNbCouleursUtilisablesMastermind();
                LOGGER.debug("Menu Param�tres - Nb essais RecherchePlusMoins :"+nbEssaisRecherchePlusMoins);
                LOGGER.debug("Menu Param�tres - Nb cases RecherchePlusMoins :"+nbreCasesRecherchePlusMoins);
                LOGGER.debug("Menu Param�tres - Nb essais Mastermind :"+nbEssaisMastermind);
                LOGGER.debug("Menu Param�tres - Nb cases Mastermind :"+nbreCasesMastermind);
                LOGGER.debug("Menu Param�tres - Nb couleurs utilisables Mastermind :"+nbCouleursUtilisablesMastermind);
                LOGGER.debug("Menu Param�tres - Etat du mode d�veloppeur :"+modeDeveloppeurActive);

            }
        });

        jmiJeuRecherchePlusMoins.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String strInstructionsJeuRecherchePlusMoins=
                        "Le but de ce jeu est de d�couvrir la combinaison � x chiffres de l'adversaire (le d�fenseur)."
                                + "\nPour ce faire, l'attaquant fait une proposition. Le d�fenseur indique pour chaque chiffre de "
                                + "\nla combinaison propos�e si le chiffre de sa combinaison est plus grand (+), plus petit (-) "
                                + "\nou si c'est le bon chiffre (=). Un mode duel o� attaquant et d�fenseur jouent tour � tour "
                                + "\nest �galement disponible.";
                JOptionPane.showMessageDialog(null, strInstructionsJeuRecherchePlusMoins, "Instructions Recherche +/-", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        jmiMastermind.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String strInstructionsJeuMastermind=
                        "Le but de ce jeu est de d�couvrir la combinaison � x couleurs de l'adversaire (le d�fenseur)."
                                + "\nPour ce faire, l'attaquant fait une proposition. Le d�fenseur indique pour chaque proposition"
                                + "\nle nombre de couleurs de la proposition qui apparaissent � la bonne place (� l'aide de pions rouges)"
                                + "\net � la mauvaise place (� l'aide de pions blancs) dans la combinaison secr�te.Un mode duel o� "
                                + "\nattaquant et d�fenseur jouent tour � tour est �galement disponible.";
                JOptionPane.showMessageDialog(null, strInstructionsJeuMastermind, "Instructions Mastermind", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        jmiQuitter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LOGGER.trace("Fin de l'application");
                System.exit(0);
            }
        });
    }

    /**
     * M�thode qui permet de r�initialiser les mod�les de donn�es relatifs aux jeux RecherchePlusMoins et Mastermind.
     */
    private void initModel() {
        this.model=new ModeleDonnees();
        this.model.addObservateur(this);
        this.modelMastermind=new ModeleDonneesMastermind();
        this.modelMastermind.addObserverMastermind(this);
        LOGGER.trace("R�initialisation des mod�les de donn�es");
    }

    /* ******************************************************************
     * Impl�mentation du pattern Observer pour le jeu RecherchePlusMoins
     * ******************************************************************/

    /**
     * Pattern Observer - M�thode non utilis�e dans la classe Fen�tre.
     */
    public void update(String propositionJoueur, String reponse) {}

    /**
     * Pattern Observer - M�thode non utilis�e dans la classe Fen�tre.
     */
    public void updateDuel(String affichage) {}

    /**
     * Pattern Observer - M�thode permettant de quitter l'application.
     */
    public void quitterApplication() {
        LOGGER.trace("Fin de l'application");
        System.exit(0);
    }

    /**
     * Pattern Observer - M�thode permettant de revenir � la page d'accueil.
     */
    public void acceuilObservateur() {
        jmParametres.setEnabled(true);
        jpContainer.removeAll();
        jpContainer.setBackground(Color.WHITE);
        jpContainer.add(imageJeu);
        jpContainer.revalidate();

        /* **************************************************************************************************************************
         * ATTENTION : Il faut imp�rativement utiliser la m�thode repaint() sinon des composants de l'ancien JPanel resteront visible
         ****************************************************************************************************************************/
        jpContainer.repaint();
        LOGGER.trace("Retour � l'accueil");
    }

    /**
     * Pattern Observer - M�thode non utilis�e dans la classe Fen�tre.
     */
    public void relancerPartie() {}

    /* **********************************************************
     * Impl�mentation du pattern Observer pour le jeu Mastermind
     ************************************************************/

    /**
     * Pattern Observer - M�thode non utilis�e dans la classe Fen�tre.
     */
    public void updateMastermind(String reponse) {}

    /**
     * Pattern Observer - M�thode permettant de quitter l'application.
     */
    public void quitterApplicationMastermind() {
        LOGGER.trace("Fin de l'application");
        System.exit(0);
    }

    /**
     * Pattern Observer - M�thode permettant de revenir � la page d'accueil.
     */
    public void acceuilObservateurMastermind() {
        jmParametres.setEnabled(true);
        jpContainer.removeAll();
        jpContainer.setBackground(Color.WHITE);
        jpContainer.add(imageJeu);
        jpContainer.revalidate();

        /* **************************************************************************************************************************
         * ATTENTION : Il faut imp�rativement utiliser la m�thode repaint() sinon des composants de l'ancien JPanel resteront visible
         ****************************************************************************************************************************/
        jpContainer.repaint();
        LOGGER.trace("Retour � l'accueil");
    }

    /**
     * Pattern Observer - M�thode non utilis�e dans la classe Fen�tre.
     */
    public void relancerPartieMastermind() {}
}


