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

/***************************************************************
 * Classe relative au jeu RecherchePlusMoins en mode d�fenseur.
 * Cette classe impl�mente l'interface Observateur.
 * @author abrahamyann
 * @see Observer
 ****************************************************************/

public class RecherchePlusMoinsModeDefenseur extends JPanel implements Observer {

    private static final long serialVersionUID = 1L;

    /**
     * Un JLabel indiquant au joueur de saisir la combinaison secr�te.
     */
    private JLabel jlPremiereInstruction=new JLabel("Veuillez saisir la combinaison secr�te :");

    /**
     * Un JLabel indiquant le nombre d'essais qu'a l'ordinateur pour trouver la combinaison secr�te,
     * ainsi que le nombre de cases de la combinaison secr�te.
     */
    private JLabel jlDeuxiemeInstruction;

    /**
     * Un JLabel indiquant qu'une r�ponse est attendue de la part du joueur.
     * */
    private JLabel jlReponseJoueur=new JLabel("Votre r�ponse (+,-,=) :");

    /**
     * Un JButton permettant de valider la r�ponse du joueur.
     */
    private JButton jbValider=new JButton("Valider");

    /**
     * Un JButton permettant de valider la combinaison secr�te du joueur.
     */
    private JButton	jbValiderCombinaisonSecreteJoueur=new JButton("Valider");

    /**
     * Un JFormattedTextField relatif � la r�ponse du joueur.
     */
    private JFormattedTextField jftfReponseJoueur;

    /**
     * Un JFormattedTextField relatif � la combinaison secr�te du joueur.
     */
    private JFormattedTextField jftfCombinaisonSecreteJoueur;

    /**
     * Un JPanel contenant les composants relatifs � la r�ponse du joueur.
     */
    private JPanel jpContainer=new JPanel();

    /**
     * Un JPanel contenant le tableau o� sont affich�es les propositions de l'ordinateur et les r�ponses du joueur.
     */
    private JPanel jpContainerTableau=new JPanel();

    /**
     * Param�tre du jeu RecherchePlusMoins.
     */
    private int nbreCases, nbEssais;

    /**
     * Variable permettant d'effectuer des contr�les.
     */
    private int verificationJftf=0,verificationJftfCombinaisonSecreteJoueur=0,rowIndex=0,columnIndex=0,verifCombinaisonSecrete=0;

    /**
     * Police d'�criture : nom de la police, style et taille.
     */
    private Font police=new Font("Segoe UI Semilight",Font.PLAIN,14);

    /**
     * Tableau o� sont affich�es les propositions de l'ordinateur et les r�ponses du joueur.
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
     * Variable de type bool�enne permettant d'indiquer la fin de la partie.
     */
    private boolean finDePartie=false;

    /**
     * Proposition secr�te du joueur en mode d�fenseur.
     */
    private String propositionSecreteModeDefenseur="";

    /**
     * Proposition de l'ordinateur en mode d�fenseur.
     */
    private String propositionOrdinateurModeDefenseur="";

    /**
     * R�ponse attendue de la part du joueur. Cette variable a pour but de v�rifier que la r�ponse du joueur est correcte.
     */
    private String reponseAttendue="";

    /**
     * Variable permettant la gestion des logs d'erreurs.
     */
    private static final Logger LOGGER=LogManager.getLogger();

    /**
     * Constructeur de la classe RecherchePlusMoinsModeDefenseur
     * @param nbCases Nombre de cases du jeu RecherchePlusMoins.
     * @param nbEssais Nombre d'essais du jeu RecherchePlusMoins.
     * @param modeDeveloppeurActive Param�tre de type bool�en indiquant si le mode d�veloppeur est activ� ou non.
     * @param model Mod�le de donn�es correspondant au jeu RecherchePlusMoins.
     * @see ModeleDonnees
     */
    public RecherchePlusMoinsModeDefenseur(int nbCases, int nbEssais, boolean modeDeveloppeurActive, ModeleDonnees model) {
        LOGGER.trace("Instanciation du jeu RecherchePlusMoins en mode D�fenseur");
        this.setPreferredSize(new Dimension(1000,740));
        this.setBackground(Color.WHITE);
        this.nbreCases=nbCases;
        this.nbEssais=nbEssais;
        this.model=model;
        controler=new RecherchePlusMoinsController(model);
        jlDeuxiemeInstruction=new JLabel("L'ordinateur a "+this.nbEssais+" essais pour trouver la combinaison secr�te de "+this.nbreCases+" cases.");
        jlPremiereInstruction.setPreferredSize(new Dimension(230,40));
        jlPremiereInstruction.setHorizontalAlignment(JLabel.CENTER);
        jlPremiereInstruction.setFont(police);
        jlDeuxiemeInstruction.setPreferredSize(new Dimension(1000,40));
        jlDeuxiemeInstruction.setHorizontalAlignment(JLabel.CENTER);
        jlDeuxiemeInstruction.setFont(police);
        jlReponseJoueur.setHorizontalAlignment(JLabel.CENTER);
        jlReponseJoueur.setFont(police);

        //Mise en place des JFormattedTextField suivant le nombre de cases choisies.
        try {
            switch(this.nbreCases) {
                case 4:
                    MaskFormatter formatCombinaisonSecreteJoueur1=new MaskFormatter("####");
                    MaskFormatter formatReponseJoueur1=new MaskFormatter("****");
                    jftfCombinaisonSecreteJoueur=new JFormattedTextField(formatCombinaisonSecreteJoueur1);
                    jftfCombinaisonSecreteJoueur.setPreferredSize(new Dimension(50,20));
                    jftfReponseJoueur=new JFormattedTextField(formatReponseJoueur1);
                    jftfReponseJoueur.setPreferredSize(new Dimension(50,20));
                    break;
                case 5:
                    MaskFormatter formatCombinaisonSecreteJoueur2=new MaskFormatter("#####");
                    MaskFormatter formatReponseJoueur2=new MaskFormatter("*****");
                    jftfCombinaisonSecreteJoueur=new JFormattedTextField(formatCombinaisonSecreteJoueur2);
                    jftfCombinaisonSecreteJoueur.setPreferredSize(new Dimension(55,20));
                    jftfReponseJoueur=new JFormattedTextField(formatReponseJoueur2);
                    jftfReponseJoueur.setPreferredSize(new Dimension(55,20));
                    break;
                case 6:
                    MaskFormatter formatCombinaisonSecreteJoueur3=new MaskFormatter("######");
                    MaskFormatter formatReponseJoueur3=new MaskFormatter("******");
                    jftfCombinaisonSecreteJoueur=new JFormattedTextField(formatCombinaisonSecreteJoueur3);
                    jftfCombinaisonSecreteJoueur.setPreferredSize(new Dimension(60,20));
                    jftfReponseJoueur=new JFormattedTextField(formatReponseJoueur3);
                    jftfReponseJoueur.setPreferredSize(new Dimension(80,20));
                    break;
                case 7:
                    MaskFormatter formatCombinaisonSecreteJoueur4=new MaskFormatter("#######");
                    MaskFormatter formatReponseJoueur4=new MaskFormatter("*******");
                    jftfCombinaisonSecreteJoueur=new JFormattedTextField(formatCombinaisonSecreteJoueur4);
                    jftfCombinaisonSecreteJoueur.setPreferredSize(new Dimension(65,20));
                    jftfReponseJoueur=new JFormattedTextField(formatReponseJoueur4);
                    jftfReponseJoueur.setPreferredSize(new Dimension(85,20));
                    break;
                case 8:
                    MaskFormatter formatCombinaisonSecreteJoueur5=new MaskFormatter("########");
                    MaskFormatter formatReponseJoueur5=new MaskFormatter("********");
                    jftfCombinaisonSecreteJoueur=new JFormattedTextField(formatCombinaisonSecreteJoueur5);
                    jftfCombinaisonSecreteJoueur.setPreferredSize(new Dimension(70,20));
                    jftfReponseJoueur=new JFormattedTextField(formatReponseJoueur5);
                    jftfReponseJoueur.setPreferredSize(new Dimension(90,20));
                    break;
                case 9:
                    MaskFormatter formatCombinaisonSecreteJoueur6=new MaskFormatter("#########");
                    MaskFormatter formatReponseJoueur6=new MaskFormatter("*********");
                    jftfCombinaisonSecreteJoueur=new JFormattedTextField(formatCombinaisonSecreteJoueur6);
                    jftfCombinaisonSecreteJoueur.setPreferredSize(new Dimension(75,20));
                    jftfReponseJoueur=new JFormattedTextField(formatReponseJoueur6);
                    jftfReponseJoueur.setPreferredSize(new Dimension(95,20));
                    break;
                case 10:
                    MaskFormatter formatCombinaisonSecreteJoueur7=new MaskFormatter("##########");
                    MaskFormatter formatReponseJoueur7=new MaskFormatter("**********");
                    jftfCombinaisonSecreteJoueur=new JFormattedTextField(formatCombinaisonSecreteJoueur7);
                    jftfCombinaisonSecreteJoueur.setPreferredSize(new Dimension(80,20));
                    jftfReponseJoueur=new JFormattedTextField(formatReponseJoueur7);
                    jftfReponseJoueur.setPreferredSize(new Dimension(100,20));
                    break;
                default:
                    LOGGER.error("Jeu RecherchePlusMoins en mode D�fenseur - Erreur d'initialisation des JFormattedTextField");
            }

        } catch (ParseException e) {
            LOGGER.error("Jeu RecherchePlusMoins en mode D�fenseur -"+e.getMessage());
        }

        jftfCombinaisonSecreteJoueur.setFont(police);
        jftfReponseJoueur.setFont(police);
        jftfReponseJoueur.setEnabled(false);

        //Mise en place d'un tableau � partir d'un mod�le de donn�es
        String[] title= {"Proposition de l'ordinateur","R�ponse"};
        Object[][] data= new Object[this.nbEssais][2];
        modelTableau=new ModelTableau(data,title);
        jtTableau=new JTable(modelTableau);
        jtTableau.getColumn("Proposition de l'ordinateur").setCellRenderer(new LabelRenderer());
        jtTableau.getColumn("R�ponse").setCellRenderer(new LabelRenderer());
        jpContainer.setPreferredSize(new Dimension(1000,40));
        jpContainer.setBackground(Color.WHITE);
        jpContainer.add(jlReponseJoueur);
        jpContainer.add(jftfReponseJoueur);
        jbValider.setEnabled(false);
        jbValiderCombinaisonSecreteJoueur.setEnabled(false);
        jpContainer.add(jbValider);
        jpContainerTableau.setBackground(Color.WHITE);

        //La taille du tableau varie suivant le nombre d'essais
        if(this.nbEssais==5)
            jpContainerTableau.setPreferredSize(new Dimension(350,103));
        else if (this.nbEssais==10)
            jpContainerTableau.setPreferredSize(new Dimension(350,183));
        else if (this.nbEssais==15)
            jpContainerTableau.setPreferredSize(new Dimension(350,263));
        else
            jpContainerTableau.setPreferredSize(new Dimension(350,343));

        jpContainerTableau.setLayout(new BorderLayout());
        jpContainerTableau.add(new JScrollPane(jtTableau),BorderLayout.CENTER);
        this.add(jlPremiereInstruction);
        this.add(jftfCombinaisonSecreteJoueur);
        this.add(jbValiderCombinaisonSecreteJoueur);
        this.add(jlDeuxiemeInstruction);
        this.add(jpContainer);
        this.add(jpContainerTableau);

        // D�finition des listeners

        //Les boutons Valider ne doivent �tre accessibles que lorsque tous les champs des JFormattedTextField sont renseign�s
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
                    jbValider.setEnabled(true);
                    verificationJftf=0;
                }
                else {
                    jbValider.setEnabled(false);
                    verificationJftf=0;
                }
            }

        });

        //Lors de la validation, les donn�es saisies doivent �tre transmises au contr�leur
        jbValiderCombinaisonSecreteJoueur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                jftfCombinaisonSecreteJoueur.setEnabled(false);
                jbValiderCombinaisonSecreteJoueur.setEnabled(false);
                propositionSecreteModeDefenseur=jftfCombinaisonSecreteJoueur.getText();
                controler.setModeDeJeu(1);
                controler.setNbEssais(nbEssais);
                controler.setPropositionSecreteModeDefenseur(propositionSecreteModeDefenseur);
                jftfReponseJoueur.requestFocusInWindow();
                jftfReponseJoueur.setEnabled(true);
            }
        });

        jbValider.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                reponseAttendue="";

                //On d�termine la r�ponse attendue.
                for (int i=0;i<propositionSecreteModeDefenseur.length();i++) {
                    if(propositionOrdinateurModeDefenseur.charAt(i)== propositionSecreteModeDefenseur.charAt(i)) {
                        reponseAttendue+=String.valueOf('=');
                    }
                    else if(propositionOrdinateurModeDefenseur.charAt(i)<propositionSecreteModeDefenseur.charAt(i)) {
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
                    jbValider.setEnabled(false);
                }
                else {
                    controler.setReponseJoueurModeDefenseur(jftfReponseJoueur.getText());
                    jftfReponseJoueur.setText("");
                    jbValider.setEnabled(false);
                }

                if(!finDePartie)
                    jftfReponseJoueur.requestFocusInWindow();
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
     * M�thode permettant de mettre � jour le tableau pour le jeu
     * RecherchePlusMoins en mode challenger et d�fenseur.
     *
     * @param propositionJoueur Proposition du joueur en mode challenger/Proposition de l'ordinateur en mode d�fenseur.
     * @param reponse           R�ponse de l'ordinateur en mode challenger/R�ponse du joueur en mode d�fenseur.
     */
    @Override
    public void update(String propositionJoueur, String reponse) {
        int previousRowIndex=0;
        previousRowIndex=rowIndex;
        this.propositionOrdinateurModeDefenseur=propositionJoueur;
        ((AbstractTableModel)jtTableau.getModel()).setValueAt(propositionJoueur,rowIndex,columnIndex);
        ((AbstractTableModel)jtTableau.getModel()).fireTableCellUpdated(rowIndex, columnIndex);

        if(reponse.equals("")) {
            rowIndex=previousRowIndex;
        }
        else {
            columnIndex++;
            ((AbstractTableModel)jtTableau.getModel()).setValueAt(reponse,rowIndex,columnIndex);
            ((AbstractTableModel)jtTableau.getModel()).fireTableCellUpdated(rowIndex, columnIndex);
            rowIndex++;
        }

        columnIndex=0;
        this.gestionFinDePartie(reponse);
    }

    /**
     * Pattern Observer - M�thode non utilis�e dans cette classe.
     */
    @Override
    public void updateDuel(String affichage) {

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
        LOGGER.trace("Jeu RecherchePlusMoins en mode D�fenseur - Partie relanc�e");
        for(int i=0;i<rowIndex;i++) {
            for (int j=0;j<2;j++) {
                ((AbstractTableModel)jtTableau.getModel()).setValueAt("", i, j);
                ((AbstractTableModel)jtTableau.getModel()).fireTableCellUpdated(i, j);
            }
        }
        verificationJftf=0;
        verificationJftfCombinaisonSecreteJoueur=0;
        rowIndex=0;
        columnIndex=0;
        verifCombinaisonSecrete=0;
        propositionSecreteModeDefenseur="";
        jftfCombinaisonSecreteJoueur.setText("");
        jftfCombinaisonSecreteJoueur.setEnabled(true);
        jftfCombinaisonSecreteJoueur.requestFocusInWindow();
        jbValiderCombinaisonSecreteJoueur.setEnabled(false);
        jftfReponseJoueur.setEnabled(false);
        jbValider.setEnabled(false);
    }

    /**
     * Gestion de la fin de la partie en fonction de la r�ponse du joueur.
     * @param reponse R�ponse du joueur.
     */
    public void gestionFinDePartie(String reponse) {
        //Gestion de la fin de la partie
        verifCombinaisonSecrete=0;


        for (int i=0;i<reponse.length();i++) {
            if (reponse.charAt(i)=='=') {
                verifCombinaisonSecrete++;
            }
        }

        //En cas de d�fa�te
        if(verifCombinaisonSecrete==nbreCases) {
            JOptionPane.showMessageDialog(null, "Vous avez perdu. L'ordinateur a trouv� la combinaison secr�te "+
                    "en moins de "+nbEssais+" essais.", "Fin de Partie",JOptionPane.INFORMATION_MESSAGE);

        }

        //En cas de victoire
        if (rowIndex==nbEssais && verifCombinaisonSecrete!=nbreCases) {
            JOptionPane.showMessageDialog(null, "Vous avez gagn�! L'ordinateur n'a pas trouv� la combinaison secr�te "+
                    "en moins de "+nbEssais+" essais.", "Fin de Partie",JOptionPane.INFORMATION_MESSAGE);
        }

        //En cas de d�fa�te ou de victoire
        if(rowIndex==nbEssais||verifCombinaisonSecrete==nbreCases) {
            LOGGER.trace("Jeu RecherchePlusMoins en mode D�fenseur - Fin de partie");
            finDePartie=true;
            jdFinDePartie =new BoiteDialogueFinDePartie(null,"Fin de Partie",true);
            controler.setChoixFinDePartie(jdFinDePartie.getChoixFinDePartie());
        }
    }
}
