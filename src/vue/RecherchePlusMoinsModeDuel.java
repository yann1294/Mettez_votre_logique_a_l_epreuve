package vue;

import DesignPattern.Observer;
import controller.RecherchePlusMoinsController;
import model.ModelTableau;
import model.ModeleDonnees;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;

/**********************************************************
 * Classe relative au jeu RecherchePlusMoins en mode duel.
 * Cette classe impl�mente l'interface Observateur.
 * @author abrahamyann
 * @see Observer
 **********************************************************/

public class RecherchePlusMoinsModeDuel extends JPanel implements Observer {


    private static final long serialVersionUID = 1L;

    /**
     * JLabel de type informatif.
     */
    private JLabel jlPremiereInstruction=new JLabel("La combinaison secr�te a �t� g�n�r�e par l'ordinateur."),
            jlDeuxiemeInstruction=new JLabel("Veuillez saisir votre combinaison secr�te :"),jlPropositionJoueur=new JLabel("Votre proposition :"),
            jlReponseJoueur=new JLabel("Votre r�ponse (+,-,=) :"),jlLegendeJoueur=new JLabel("Le joueur est en vert."),
            jlLegendeOrdinateur=new JLabel ("L'ordinateur est en rouge.");

    /**
     * Un JButton permettant de valider la combinaison secr�te du joueur.
     */
    private JButton jbValiderCombinaisonSecreteJoueur=new JButton("Valider");

    /**
     * Un JButton permettant de valider la proposition du joueur.
     */
    private JButton	jbValiderPropositionJoueur=new JButton("Valider");

    /**
     * Un JButton permettant de valider la r�ponse du joueur.
     */
    private JButton	jbValiderReponseJoueur=new JButton("Valider");

    /**
     * Un JFormattedTextField relatif � la combinaison secr�te du joueur.
     */
    private JFormattedTextField jftfCombinaisonSecreteJoueur;

    /**
     * Un JFormattedTextField correspondant � la proposition du joueur.
     */
    private JFormattedTextField jftfPropositionJoueur;

    /**
     * Un JFormattedTextField relatif � la r�ponse du joueur.
     */
    private JFormattedTextField jftfReponseJoueur;

    /**
     * Un JPanel contenant les composants relatifs � la combinaison secr�te du joueur.
     */
    private JPanel jpContainerCombinaisonSecreteJoueur=new JPanel();

    /**
     * Un JPanel contenant le tableau o� sont affich�es les propositions et les r�ponses du joueur et de l'ordinateur.
     */
    private JPanel jpContainerTableau=new JPanel();

    /**
     * Un JPanel contenant les composants relatifs aux propositions et aux r�ponses du joueur.
     */
    private JPanel jpContainerPropositionReponseJoueur=new JPanel();

    /**
     * Un JPanel contenant une l�gende.
     */
    private JPanel jpContainerLegendes=new JPanel();

    /**
     * Param�tre du jeu RecherchePlusMoins.
     */
    private int nbreCases, nbEssais;

    /**
     * Variable permettant d'effectuer des contr�les.
     */
    private int verificationJftf=0,verificationJftfCombinaisonSecreteJoueur=0,verificationJftfPropositionJoueur=0,
            rowIndex=0,columnIndex=0,min=0,max=10,verifCombinaisonSecrete=0,miseAJourAffichageModeDuel=0;

    /**
     * Combinaison secr�te g�n�r�e par l'ordinateur.
     */
    private String combinaisonSecreteOrdinateur="";

    /**
     * R�ponse attendue de la part du joueur. Cette variable a pour but de v�rifier que la r�ponse du joueur est correcte.
     */
    private String reponseAttendue="";

    /**
     * Proposition secr�te du joueur en mode duel.
     */
    private String propositionSecreteJoueurModeDuel="";

    /**
     * Proposition de l'ordinateur en mode duel.
     */
    private String propositionOrdinateurModeDuel="";

    /**
     * Police d'�criture : nom de la police, style et taille.
     */
    private Font police=new Font("Segoe UI Semilight",Font.PLAIN,14);

    /**
     * Tableau o� sont affich�es les propositions et les r�ponses du joueur et de l'ordinateur.
     */
    private JTable jtTableau;

    /**
     * Mod�le de donn�es li� au tableau.
     * @see ModelTableau
     */
    private ModelTableau modelTableau;

    /**
     * Mod�le de donn�es relatif au jeu RecherchePlusMoins.
     * @see ModeleDonnees
     */
    private ModeleDonnees model;

    /**
     * Controler relatif au jeu RecherchePlusMoins.
     * @see RecherchePlusMoinsController
     */
    private RecherchePlusMoinsController controler;

    /**
     * Boite de dialogue permettant d'effectuer un choix en fin de partie.
     * @see BoiteDialogueFinDePartie
     */
    private BoiteDialogueFinDePartie jdFinDePartie;

    /**
     * Variable permettant de g�rer l'affichage du tableau.
     * @see LabelRenderer
     */
    private LabelRenderer labelRenderer=new LabelRenderer();

    /**
     * Variable de type bool�enne permettant d'indiquer la fin de la partie.
     */
    private boolean finDePartie=false;

    /**
     * Param�tre de type bool�en indiquant si le mode d�veloppeur est activ� ou non.
     */
    private boolean	modeDeveloppeurActive;

    /**
     * Variable permettant la gestion des logs d'erreurs.
     */
    private static final Logger LOGGER=LogManager.getLogger();

    /**
     * Constructeur de la classe RecherchePlusMoinsModeDuel
     * @param nbCases Nombre de cases du jeu RecherchePlusMoins.
     * @param nbEssais Nombre d'essais du jeu RecherchePlusMoins.
     * @param modeDeveloppeurActive Param�tre de type bool�en indiquant si le mode d�veloppeur est activ� ou non.
     * @param model Mod�le de donn�es correspondant au jeu RecherchePlusMoins.
     * @see ModeleDonnees
     */
    public RecherchePlusMoinsModeDuel(int nbCases, int nbEssais, boolean modeDeveloppeurActive, ModeleDonnees model) {
        LOGGER.trace("Instanciation du jeu RecherchePlusMoins en mode Duel");
        this.setPreferredSize(new Dimension(1000,740));
        this.setBackground(Color.WHITE);
        this.nbreCases=nbCases;
        this.nbEssais=nbEssais;
        this.modeDeveloppeurActive=modeDeveloppeurActive;
        this.model=model;
        controler=new RecherchePlusMoinsController(model);
        this.generationCombinaisonSecrete();
        if(this.modeDeveloppeurActive==true) {
            jlPremiereInstruction.setText("La combinaison secr�te a �t� g�n�r�e par l'ordinateur (Solution : "
                    +combinaisonSecreteOrdinateur+")");
        }
        jlPremiereInstruction.setPreferredSize(new Dimension(1000,40));
        jlPremiereInstruction.setHorizontalAlignment(JLabel.CENTER);
        jlPremiereInstruction.setFont(police);
        jlDeuxiemeInstruction.setPreferredSize(new Dimension(250,40));
        jlDeuxiemeInstruction.setHorizontalAlignment(JLabel.CENTER);
        jlDeuxiemeInstruction.setFont(police);
        jlPropositionJoueur.setHorizontalAlignment(JLabel.CENTER);
        jlPropositionJoueur.setFont(police);
        jlReponseJoueur.setHorizontalAlignment(JLabel.CENTER);
        jlReponseJoueur.setFont(police);
        jlLegendeJoueur.setHorizontalAlignment(JLabel.CENTER);
        jlLegendeJoueur.setFont(police);
        jlLegendeJoueur.setForeground(Color.GREEN);
        jlLegendeOrdinateur.setHorizontalAlignment(JLabel.CENTER);
        jlLegendeOrdinateur.setFont(police);
        jlLegendeOrdinateur.setForeground(Color.RED);

        //Mise en place des JFormattedTextField suivant le nombre de cases choisies.
        try {
            switch(this.nbreCases) {
                case 4 :
                    MaskFormatter formatCombinaisonSecreteJoueur1=new MaskFormatter("####");
                    MaskFormatter formatPropositionJoueur1=new MaskFormatter("####");
                    MaskFormatter formatReponseJoueur1=new MaskFormatter("****");
                    jftfCombinaisonSecreteJoueur=new JFormattedTextField(formatCombinaisonSecreteJoueur1);
                    jftfCombinaisonSecreteJoueur.setPreferredSize(new Dimension(50,20));
                    jftfPropositionJoueur=new JFormattedTextField(formatPropositionJoueur1);
                    jftfPropositionJoueur.setPreferredSize(new Dimension(50,20));
                    jftfReponseJoueur=new JFormattedTextField(formatReponseJoueur1);
                    jftfReponseJoueur.setPreferredSize(new Dimension(50,20));
                    break;
                case 5 :
                    MaskFormatter formatCombinaisonSecreteJoueur2=new MaskFormatter("#####");
                    MaskFormatter formatPropositionJoueur2=new MaskFormatter("#####");
                    MaskFormatter formatReponseJoueur2=new MaskFormatter("*****");
                    jftfCombinaisonSecreteJoueur=new JFormattedTextField(formatCombinaisonSecreteJoueur2);
                    jftfCombinaisonSecreteJoueur.setPreferredSize(new Dimension(55,20));
                    jftfPropositionJoueur=new JFormattedTextField(formatPropositionJoueur2);
                    jftfPropositionJoueur.setPreferredSize(new Dimension(55,20));
                    jftfReponseJoueur=new JFormattedTextField(formatReponseJoueur2);
                    jftfReponseJoueur.setPreferredSize(new Dimension(55,20));
                    break;
                case 6 :
                    MaskFormatter formatCombinaisonSecreteJoueur3=new MaskFormatter("######");
                    MaskFormatter formatPropositionJoueur3=new MaskFormatter("######");
                    MaskFormatter formatReponseJoueur3=new MaskFormatter("******");
                    jftfCombinaisonSecreteJoueur=new JFormattedTextField(formatCombinaisonSecreteJoueur3);
                    jftfCombinaisonSecreteJoueur.setPreferredSize(new Dimension(60,20));
                    jftfPropositionJoueur=new JFormattedTextField(formatPropositionJoueur3);
                    jftfPropositionJoueur.setPreferredSize(new Dimension(60,20));
                    jftfReponseJoueur=new JFormattedTextField(formatReponseJoueur3);
                    jftfReponseJoueur.setPreferredSize(new Dimension(80,20));
                    break;
                case 7 :
                    MaskFormatter formatCombinaisonSecreteJoueur4=new MaskFormatter("#######");
                    MaskFormatter formatPropositionJoueur4=new MaskFormatter("#######");
                    MaskFormatter formatReponseJoueur4=new MaskFormatter("*******");
                    jftfCombinaisonSecreteJoueur=new JFormattedTextField(formatCombinaisonSecreteJoueur4);
                    jftfCombinaisonSecreteJoueur.setPreferredSize(new Dimension(65,20));
                    jftfPropositionJoueur=new JFormattedTextField(formatPropositionJoueur4);
                    jftfPropositionJoueur.setPreferredSize(new Dimension(65,20));
                    jftfReponseJoueur=new JFormattedTextField(formatReponseJoueur4);
                    jftfReponseJoueur.setPreferredSize(new Dimension(85,20));
                    break;
                case 8 :
                    MaskFormatter formatCombinaisonSecreteJoueur5=new MaskFormatter("########");
                    MaskFormatter formatPropositionJoueur5=new MaskFormatter("########");
                    MaskFormatter formatReponseJoueur5=new MaskFormatter("********");
                    jftfCombinaisonSecreteJoueur=new JFormattedTextField(formatCombinaisonSecreteJoueur5);
                    jftfCombinaisonSecreteJoueur.setPreferredSize(new Dimension(70,20));
                    jftfPropositionJoueur=new JFormattedTextField(formatPropositionJoueur5);
                    jftfPropositionJoueur.setPreferredSize(new Dimension(70,20));
                    jftfReponseJoueur=new JFormattedTextField(formatReponseJoueur5);
                    jftfReponseJoueur.setPreferredSize(new Dimension(90,20));
                    break;
                case 9 :
                    MaskFormatter formatCombinaisonSecreteJoueur6=new MaskFormatter("#########");
                    MaskFormatter formatPropositionJoueur6=new MaskFormatter("#########");
                    MaskFormatter formatReponseJoueur6=new MaskFormatter("*********");
                    jftfCombinaisonSecreteJoueur=new JFormattedTextField(formatCombinaisonSecreteJoueur6);
                    jftfCombinaisonSecreteJoueur.setPreferredSize(new Dimension(75,20));
                    jftfPropositionJoueur=new JFormattedTextField(formatPropositionJoueur6);
                    jftfPropositionJoueur.setPreferredSize(new Dimension(75,20));
                    jftfReponseJoueur=new JFormattedTextField(formatReponseJoueur6);
                    jftfReponseJoueur.setPreferredSize(new Dimension(95,20));
                    break;
                case 10 :
                    MaskFormatter formatCombinaisonSecreteJoueur7=new MaskFormatter("##########");
                    MaskFormatter formatPropositionJoueur7=new MaskFormatter("##########");
                    MaskFormatter formatReponseJoueur7=new MaskFormatter("**********");
                    jftfCombinaisonSecreteJoueur=new JFormattedTextField(formatCombinaisonSecreteJoueur7);
                    jftfCombinaisonSecreteJoueur.setPreferredSize(new Dimension(80,20));
                    jftfPropositionJoueur=new JFormattedTextField(formatPropositionJoueur7);
                    jftfPropositionJoueur.setPreferredSize(new Dimension(80,20));
                    jftfReponseJoueur=new JFormattedTextField(formatReponseJoueur7);
                    jftfReponseJoueur.setPreferredSize(new Dimension(100,20));
                    break;
                default:
                    LOGGER.error("Jeu RecherchePlusMoins en mode Duel - Erreur d'initialisation des JFormattedTextField");
            }
        } catch (ParseException e) {
            LOGGER.error("Jeu RecherchePlusMoins en mode Duel -"+e.getMessage());
        }

        jftfCombinaisonSecreteJoueur.setFont(police);
        jftfPropositionJoueur.setFont(police);
        jftfPropositionJoueur.setEnabled(false);
        jftfReponseJoueur.setFont(police);
        jftfReponseJoueur.setEnabled(false);

        //Mise en place d'un tableau � partir d'un mod�le de donn�es
        String[] title= {"Proposition","R�ponse"};
        Object[][] data= new Object[2*this.nbEssais][2];
        modelTableau=new ModelTableau(data,title);
        jtTableau=new JTable(modelTableau);
        labelRenderer.setModeDeJeu(2);
        jtTableau.getColumn("Proposition").setCellRenderer(labelRenderer);
        jtTableau.getColumn("R�ponse").setCellRenderer(labelRenderer);
        jpContainerCombinaisonSecreteJoueur.setPreferredSize(new Dimension(1000,40));
        jpContainerPropositionReponseJoueur.setPreferredSize(new Dimension(1000,40));
        jpContainerLegendes.setPreferredSize(new Dimension(1000,40));

        jbValiderCombinaisonSecreteJoueur.setEnabled(false);
        jbValiderPropositionJoueur.setEnabled(false);
        jbValiderReponseJoueur.setEnabled(false);
        jpContainerTableau.setBackground(Color.WHITE);

        //La taille du tableau varie suivant le nombre d'essais
        if(this.nbEssais==5)
            jpContainerTableau.setPreferredSize(new Dimension(350,183));
        else if (this.nbEssais==10)
            jpContainerTableau.setPreferredSize(new Dimension(350,343));
        else if (this.nbEssais==15)
            jpContainerTableau.setPreferredSize(new Dimension(350,343));
        else
            jpContainerTableau.setPreferredSize(new Dimension(350,343));


        jpContainerTableau.setLayout(new BorderLayout());
        jpContainerTableau.add(new JScrollPane(jtTableau),BorderLayout.CENTER);

        jpContainerCombinaisonSecreteJoueur.setBackground(Color.WHITE);
        jpContainerCombinaisonSecreteJoueur.add(jlDeuxiemeInstruction);
        jpContainerCombinaisonSecreteJoueur.add(jftfCombinaisonSecreteJoueur);
        jpContainerCombinaisonSecreteJoueur.add(jbValiderCombinaisonSecreteJoueur);

        jpContainerPropositionReponseJoueur.setBackground(Color.WHITE);
        jpContainerPropositionReponseJoueur.add(jlPropositionJoueur);
        jpContainerPropositionReponseJoueur.add(jftfPropositionJoueur);
        jpContainerPropositionReponseJoueur.add(jbValiderPropositionJoueur);
        jpContainerPropositionReponseJoueur.add(jlReponseJoueur);
        jpContainerPropositionReponseJoueur.add(jftfReponseJoueur);
        jpContainerPropositionReponseJoueur.add(jbValiderReponseJoueur);

        jpContainerLegendes.setBackground(Color.WHITE);
        jpContainerLegendes.add(jlLegendeJoueur);
        jpContainerLegendes.add(jlLegendeOrdinateur);

        this.add(jlPremiereInstruction);
        this.add(jpContainerCombinaisonSecreteJoueur);
        this.add(jpContainerPropositionReponseJoueur);
        this.add(jpContainerTableau);
        this.add(jpContainerLegendes);

        // D�finition des listeners

        //Les boutons Valider ne doivent �tre accessibles que lorsque les JFormattedTextField associ�es sont renseign�s
        jftfCombinaisonSecreteJoueur.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent arg0) {}
            public void keyPressed(KeyEvent e) {}

            public void keyReleased(KeyEvent event) {
                for (int i=0;i<nbreCases;i++) {
                    if(jftfCombinaisonSecreteJoueur.getText().charAt(i)!=' ') {
                        verificationJftfCombinaisonSecreteJoueur++;
                    }
                }
                if(verificationJftfCombinaisonSecreteJoueur==nbreCases) {
                    jbValiderCombinaisonSecreteJoueur.setEnabled(true);
                    verificationJftfCombinaisonSecreteJoueur=0;
                }
                else {
                    jbValiderCombinaisonSecreteJoueur.setEnabled(false);
                    verificationJftfCombinaisonSecreteJoueur=0;
                }
            }
        });

        jftfPropositionJoueur.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent arg0) {}
            public void keyPressed(KeyEvent e) {}

            public void keyReleased(KeyEvent event) {
                for (int i=0;i<nbreCases;i++) {
                    if(jftfPropositionJoueur.getText().charAt(i)!=' ') {
                        verificationJftfPropositionJoueur++;
                    }
                }
                if(verificationJftfPropositionJoueur==nbreCases) {
                    jbValiderPropositionJoueur.setEnabled(true);
                    verificationJftfPropositionJoueur=0;
                }
                else {
                    jbValiderPropositionJoueur.setEnabled(false);
                    verificationJftfPropositionJoueur=0;
                }
            }
        });

        jftfReponseJoueur.addKeyListener(new KeyListener() {

            public void keyTyped(KeyEvent arg0) {}
            public void keyPressed(KeyEvent e) {}

            public void keyReleased(KeyEvent event) {

                for (int i=0;i<nbreCases;i++) {
                    if(jftfReponseJoueur.getText().charAt(i)!='='&&jftfReponseJoueur.getText().charAt(i)!='+'&&
                            jftfReponseJoueur.getText().charAt(i)!='-'&&jftfReponseJoueur.getText().charAt(i)!=' ') {
                        JOptionPane.showMessageDialog(null, "Veuillez saisir l'un des carat�res suivants : +, - ou =", "Information", JOptionPane.INFORMATION_MESSAGE);
                        jftfReponseJoueur.setText("");
                    }

                    if(jftfReponseJoueur.getText().charAt(i)!=' ') {
                        verificationJftf++;
                    }
                }
                if(verificationJftf==nbreCases) {
                    jbValiderReponseJoueur.setEnabled(true);
                    verificationJftf=0;
                }
                else {
                    jbValiderReponseJoueur.setEnabled(false);
                    verificationJftf=0;
                }
            }

        });

        //Lors de la validation, les donn�es saisies doivent �tre transmises au contr�leur
        jbValiderCombinaisonSecreteJoueur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                jftfCombinaisonSecreteJoueur.setEnabled(false);
                jbValiderCombinaisonSecreteJoueur.setEnabled(false);
                propositionSecreteJoueurModeDuel=jftfCombinaisonSecreteJoueur.getText();
                controler.setPropositionSecreteJoueurModeDuel(propositionSecreteJoueurModeDuel);
                jftfPropositionJoueur.setEnabled(true);
                jftfPropositionJoueur.requestFocusInWindow();
            }

        });

        jbValiderPropositionJoueur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                jftfPropositionJoueur.setEnabled(false);
                jbValiderPropositionJoueur.setEnabled(false);
                controler.setPropositionJoueurModeDuel(jftfPropositionJoueur.getText());
                jftfPropositionJoueur.setText("");
                if(!finDePartie) {
                    jftfReponseJoueur.setEnabled(true);
                    jftfReponseJoueur.requestFocusInWindow();
                }
                else
                    finDePartie=false;
            }
        });

        jbValiderReponseJoueur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                boolean saisieJoueur=true;
                reponseAttendue="";

                //On d�termine la r�ponse attendue.
                for (int i=0;i<combinaisonSecreteOrdinateur.length();i++) {
                    if(propositionOrdinateurModeDuel.charAt(i)==propositionSecreteJoueurModeDuel.charAt(i)) {
                        reponseAttendue+=String.valueOf('=');
                    }
                    else if(propositionOrdinateurModeDuel.charAt(i)<propositionSecreteJoueurModeDuel.charAt(i)) {
                        reponseAttendue+=String.valueOf('+');
                    }
                    else {
                        reponseAttendue+=String.valueOf('-');
                    }
                }

				/*On contr�le la r�ponse de l'utilisateur par rapport � la r�ponse attendue. L'utilisateur doit imp�rativement
				/transmettre une r�ponse ad�quate � l'ordinateur.*/
                if(!jftfReponseJoueur.getText().equals(reponseAttendue)) {
                    JOptionPane.showMessageDialog(null,"Attention : votre r�ponse est erron�e. Veuillez saisir une autre r�ponse, svp.",
                            "Message d'avertissement", JOptionPane.WARNING_MESSAGE);
                    jftfReponseJoueur.setText("");
                    jbValiderReponseJoueur.setEnabled(false);
                    saisieJoueur=false;
                }
                else {
                    controler.setReponseJoueurModeDuel(jftfReponseJoueur.getText());
                    jftfReponseJoueur.setText("");
                    jftfReponseJoueur.setEnabled(false);
                    jbValiderReponseJoueur.setEnabled(false);
                }

                if(!finDePartie&&saisieJoueur==true) {
                    jftfPropositionJoueur.setEnabled(true);
                    jftfPropositionJoueur.requestFocusInWindow();
                }
                else
                    finDePartie=false;
            }

        });

        this.model.addObservateur(this);
    }

    /**
     * Accesseur permettant de retourner un objet de type JFormattedTextField correspondant � la combinaison secr�te du joueur.
     * @return Un objet de type JFormattedTextField correspondant � la combinaison secr�te du joueur.
     */
    public JFormattedTextField getJftfCombinaisonSecreteJoueur() {
        return jftfCombinaisonSecreteJoueur;
    }
    /* ***********************************
     * Impl�mentation du pattern Observer
     *************************************/

    /**
     * Pattern Observer - M�thode non utilis�e dans cette classe.
     */
    @Override
    public void update(String propositionJoueur, String reponse) {

    }

    /**
     * M�thode permettant de mettre � jour le tableau pour le jeu RecherchePlusMoins en
     * mode duel en fonction des propositions et des r�ponses du joueur et de l'ordinateur.
     *
     * @param affichage Cette variable peut correspondre aux propositions/r�ponses du joueur
     *                  et de l'ordinateur.
     */
    @Override
    public void updateDuel(String affichage) {
        if(miseAJourAffichageModeDuel==0) {
            ((AbstractTableModel)jtTableau.getModel()).setValueAt(affichage, rowIndex, columnIndex);
            ((AbstractTableModel)jtTableau.getModel()).fireTableCellUpdated(rowIndex, columnIndex);
            columnIndex++;
            miseAJourAffichageModeDuel++;
            if(rowIndex%2==1)
                propositionOrdinateurModeDuel=affichage;
        }
        else {
            ((AbstractTableModel)jtTableau.getModel()).setValueAt(affichage, rowIndex, columnIndex);
            ((AbstractTableModel)jtTableau.getModel()).fireTableCellUpdated(rowIndex, columnIndex);
            this.gestionFinDePartie(affichage);
            columnIndex=0;
            miseAJourAffichageModeDuel=0;
            if(!finDePartie) {
                rowIndex++;
            }
            else {
                rowIndex=0;
            }

        }
    }

    /**
     * Pattern Observer - M�thode non utilis�e dans cette classe.
     */
    @Override
    public void quitterApplication() {

    }

    /**
     * Pattern Observer - M�thode non utilis�e dans cette classe.
     */
    @Override
    public void acceuilObservateur() {

    }

    /**
     * M�thode permettant de relancer le m�me jeu.
     */
    @Override
    public void relancerPartie() {
        LOGGER.trace("Jeu RecherchePlusMoins en mode Duel - Partie relanc�e");
        for(int i=0;i<=rowIndex;i++) {
            for (int j=0;j<2;j++) {
                ((AbstractTableModel)jtTableau.getModel()).setValueAt("", i, j);
                ((AbstractTableModel)jtTableau.getModel()).fireTableCellUpdated(i, j);
            }
        }
        verificationJftf=0;
        verificationJftfCombinaisonSecreteJoueur=0;
        verificationJftfPropositionJoueur=0;
        rowIndex=0;
        columnIndex=0;
        min=0;
        max=10;
        verifCombinaisonSecrete=0;
        combinaisonSecreteOrdinateur="";
        jftfCombinaisonSecreteJoueur.setText("");
        jftfCombinaisonSecreteJoueur.setEnabled(true);
        jftfCombinaisonSecreteJoueur.requestFocusInWindow();
        jbValiderCombinaisonSecreteJoueur.setEnabled(false);
        jftfPropositionJoueur.setEnabled(false);
        jftfPropositionJoueur.setText("");
        jbValiderPropositionJoueur.setEnabled(false);
        jftfReponseJoueur.setEnabled(false);
        jftfReponseJoueur.setText("");
        jbValiderReponseJoueur.setEnabled(false);
        this.generationCombinaisonSecrete();
        if(this.modeDeveloppeurActive==true) {
            jlPremiereInstruction.setText("La combinaison secr�te a �t� g�n�r�e par l'ordinateur (Solution : "
                    +combinaisonSecreteOrdinateur+")");
        }
    }

    /**
     * G�n�ration de la combinaison secr�te par l'ordinateur
     */
    public void generationCombinaisonSecrete(){
        int nbreAleatoire;
        for (int i=0;i<this.nbreCases;i++) {
            nbreAleatoire=(int)(Math.random()*(max-min));
            combinaisonSecreteOrdinateur+=String.valueOf(nbreAleatoire);
        }
        LOGGER.debug("Jeu RecherchePlusMoins en mode Duel - G�n�ration de la combinaison secr�te:"+combinaisonSecreteOrdinateur);
        controler.setModeDeJeu(2);
        controler.setNbEssais(nbEssais);
        controler.setPropositionSecreteOrdinateurModeDuel(combinaisonSecreteOrdinateur);
    }

    /**
     * Gestion de la fin de la partie en fonction des r�ponses du joueur ou de l'ordinateur.
     * @param affichage R�ponse du joueur ou de l'ordinateur.
     */
    public void gestionFinDePartie(String affichage) {
        //Gestion de la fin de la partie
        verifCombinaisonSecrete=0;

        for (int i=0;i<affichage.length();i++) {
            if (affichage.charAt(i)=='=') {
                verifCombinaisonSecrete++;
            }
        }

        //En cas de victoire
        if(verifCombinaisonSecrete==nbreCases && rowIndex%2==0) {
            JOptionPane.showMessageDialog(null, "F�licitations!!! Vous avez trouv� la combinaison secr�te de l'ordinateur. ",
                    "Fin de Partie",JOptionPane.INFORMATION_MESSAGE);
        }

        //En cas de d�fa�te
        if(verifCombinaisonSecrete==nbreCases && rowIndex%2==1) {
            JOptionPane.showMessageDialog(null, "Vous avez perdu! L'ordinateur a trouv� en premier votre combinaison secr�te."
                            +"\n Pour information, la combinaison secr�te de l'ordinateur �tait : "+combinaisonSecreteOrdinateur,
                    "Fin de Partie",JOptionPane.INFORMATION_MESSAGE);

        }

        //En cas de match nul
        if(verifCombinaisonSecrete!=nbreCases&&rowIndex==2*nbEssais-1) {
            JOptionPane.showMessageDialog(null, "Match nul. Le nombre d'essai maximal a �t� d�pass�.",
                    "Fin de Partie",JOptionPane.INFORMATION_MESSAGE);
            LOGGER.trace("Jeu RecherchePlusMoins en mode Duel - Fin de partie");
            finDePartie=true;
            jdFinDePartie =new BoiteDialogueFinDePartie(null,"Fin de Partie",true);
            controler.setChoixFinDePartie(jdFinDePartie.getChoixFinDePartie());
        }

        //En cas de d�fa�te ou de victoire
        if(verifCombinaisonSecrete==nbreCases) {
            LOGGER.trace("Jeu RecherchePlusMoins en mode Duel - Fin de partie");
            finDePartie=true;
            jdFinDePartie =new BoiteDialogueFinDePartie(null,"Fin de Partie",true);
            controler.setChoixFinDePartie(jdFinDePartie.getChoixFinDePartie());
        }
    }
}
