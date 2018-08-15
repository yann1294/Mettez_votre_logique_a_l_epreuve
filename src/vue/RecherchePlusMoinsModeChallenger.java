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

/****************************************************************
 * Classe relative au jeu RecherchePlusMoins en mode challenger.
 * Cette classe impl�mente l'interface Observateur.
 * @author abrahamyann
 * @see Observer
 ****************************************************************/

public class RecherchePlusMoinsModeChallenger extends JPanel implements Observer {

    private static final long serialVersionUID = 1L;

    /**
     * Un JLabel indiquant que la combinaison secr�te a �t� g�n�r�e par l'ordinateur.
     */
    private JLabel jlPremiereInstruction=new JLabel("La combinaison secr�te a �t� g�n�r�e par l'ordinateur.");

    /**
     * Un JLabel informant le joueur du nombre d'essais qu'il a pour trouver la combinaison secr�te, ainsi que le nombre
     * de cases de la combinaison secr�te.
     */
    private JLabel jlDeuxiemeInstruction;

    /**
     * Un JLabel indiquant que le joueur doit faire sa proposition.
     */
    private JLabel jlPropositionJoueur=new JLabel("Votre proposition :");

    /**
     * Un JButton permettant de valider la proposition du joueur.
     */
    private JButton jbValider=new JButton("Valider");

    /**
     * Un JFormattedTextField correspondant � la proposition du joueur.
     */
    private JFormattedTextField jftfPropositionJoueur;

    /**
     * Un JPanel contenant les composants relatifs � la proposition du joueur.
     */
    private JPanel jpContainer=new JPanel();

    /**
     * Un JPanel contenant le tableau o� sont affich�es les propositions du joueur et les r�ponses de l'ordinateur.
     */
    private JPanel jpContainerTableau=new JPanel();

    /**
     * Param�tre du jeu RecherchePlusMoins.
     */
    private int nbreCases, nbEssais;

    /**
     * Variable permettant d'effectuer des contr�les.
     */
    private int verificationJftf=0,rowIndex=0,columnIndex=0,min=0,max=10,verifCombinaisonSecrete=0;

    /**
     * Combinaison secr�te g�n�r�e par l'ordinateur.
     */
    private String combinaisonSecrete="";

    /**
     * Police d'�criture : nom de la police, style et taille.
     */
    private Font police=new Font("Segoe UI Semilight",Font.PLAIN,14);

    /**
     * Tableau o� sont affich�es les propositions du joueur et les r�ponses de l'ordinateur.
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
     * @see RecherchePlusMoinsControler
     */
    private RecherchePlusMoinsController controler;

    /**
     * Boite de dialogue permettant d'effectuer un choix en fin de partie.
     * @see BoiteDialogueFinDePartie
     */
    private BoiteDialogueFinDePartie jdFinDePartie;

    /**
     * Param�tre de type bool�en indiquant si le mode d�veloppeur est activ� ou non.
     */
    private boolean modeDeveloppeurActive;

    /**
     * Variable permettant la gestion des logs d'erreurs.
     */
    private static final Logger LOGGER=LogManager.getLogger();

    /**
     * Constructeur de la classe RecherchePlusMoinsModeChallenger.
     * @param nbCases Nombre de cases du jeu RecherchePlusMoins.
     * @param nbEssais Nombre d'essais du jeu RecherchePlusMoins.
     * @param modeDeveloppeurActive Param�tre de type bool�en indiquant si le mode d�veloppeur est activ� ou non.
     * @param model Mod�le de donn�es correspondant au jeu RecherchePlusMoins.
     * @see ModeleDonnees
     */
    public RecherchePlusMoinsModeChallenger(int nbCases, int nbEssais,boolean modeDeveloppeurActive,ModeleDonnees model) {
        LOGGER.trace("Instanciation du jeu RecherchePlusMoins en mode Challenger");
        this.setPreferredSize(new Dimension(1000,740));
        this.setBackground(Color.WHITE);
        this.nbreCases=nbCases;
        this.nbEssais=nbEssais;
        this.modeDeveloppeurActive=modeDeveloppeurActive;
        this.model=model;
        controler=new RecherchePlusMoinsController(model);
        this.generationCombinaisonSecrete();
        if(this.modeDeveloppeurActive==true) {
            jlPremiereInstruction.setText("La combinaison secr�te a �t� g�n�r�e par l'ordinateur (Solution : "+combinaisonSecrete+")");
        }

        jlPremiereInstruction.setPreferredSize(new Dimension(1000,40));
        jlPremiereInstruction.setHorizontalAlignment(JLabel.CENTER);
        jlPremiereInstruction.setFont(police);
        jlDeuxiemeInstruction=new JLabel("Vous avez "+this.nbEssais+" essais pour trouver la combinaison secr�te de "+this.nbreCases+" cases.");
        jlDeuxiemeInstruction.setPreferredSize(new Dimension(1000,40));
        jlDeuxiemeInstruction.setHorizontalAlignment(JLabel.CENTER);
        jlDeuxiemeInstruction.setFont(police);
        jlPropositionJoueur.setHorizontalAlignment(JLabel.CENTER);
        jlPropositionJoueur.setFont(police);

        //Mise en place du JFormattedTextField suivant le nombre de cases choisies.
        try {
            switch (this.nbreCases) {
                case 4:
                    MaskFormatter formatPropositionJoueur1 = new MaskFormatter("####");
                    jftfPropositionJoueur = new JFormattedTextField(formatPropositionJoueur1);
                    jftfPropositionJoueur.setPreferredSize(new Dimension(50, 20));
                    break;
                case 5:
                    MaskFormatter formatPropositionJoueur2 = new MaskFormatter("#####");
                    jftfPropositionJoueur = new JFormattedTextField(formatPropositionJoueur2);
                    jftfPropositionJoueur.setPreferredSize(new Dimension(55, 20));
                    break;
                case 6:
                    MaskFormatter formatPropositionJoueur3 = new MaskFormatter("######");
                    jftfPropositionJoueur = new JFormattedTextField(formatPropositionJoueur3);
                    jftfPropositionJoueur.setPreferredSize(new Dimension(60, 20));
                    break;
                case 7:
                    MaskFormatter formatPropositionJoueur4 = new MaskFormatter("#######");
                    jftfPropositionJoueur = new JFormattedTextField(formatPropositionJoueur4);
                    jftfPropositionJoueur.setPreferredSize(new Dimension(65, 20));
                    break;
                case 8:
                    MaskFormatter formatPropositionJoueur5 = new MaskFormatter("########");
                    jftfPropositionJoueur = new JFormattedTextField(formatPropositionJoueur5);
                    jftfPropositionJoueur.setPreferredSize(new Dimension(70, 20));
                    break;
                case 9:
                    MaskFormatter formatPropositionJoueur6 = new MaskFormatter("#########");
                    jftfPropositionJoueur = new JFormattedTextField(formatPropositionJoueur6);
                    jftfPropositionJoueur.setPreferredSize(new Dimension(75, 20));
                    break;
                case 10:
                    MaskFormatter formatPropositionJoueur7 = new MaskFormatter("##########");
                    jftfPropositionJoueur = new JFormattedTextField(formatPropositionJoueur7);
                    jftfPropositionJoueur.setPreferredSize(new Dimension(80, 20));
                    break;
                default:
                    LOGGER.error("Jeu RecherchePlusMoins en mode Challenger - Erreur d'initialisation pour le JFormattedTextField");
            }

        } catch (ParseException e) {
            LOGGER.error("Jeu RecherchePlusMoins en mode Challenger -"+e.getMessage());
        }

        jftfPropositionJoueur.setFont(police);

        //Mise en place d'un tableau � partir d'un mod�le de donn�es
        String[] title= {"Proposition du joueur","R�ponse"};
        Object[][] data= new Object[this.nbEssais][2];
        modelTableau=new ModelTableau(data,title);
        jtTableau=new JTable(modelTableau);
        jtTableau.getColumn("Proposition du joueur").setCellRenderer(new LabelRenderer());
        jtTableau.getColumn("R�ponse").setCellRenderer(new LabelRenderer());
        jpContainer.setPreferredSize(new Dimension(1000,40));
        jpContainer.setBackground(Color.WHITE);
        jpContainer.add(jlPropositionJoueur);
        jpContainer.add(jftfPropositionJoueur);
        jbValider.setEnabled(false);
        jpContainer.add(jbValider);
        jpContainerTableau.setBackground(Color.WHITE);

        //La taille du tableau varie suivant le nombre d'essais
        if(this.nbEssais==5)
            jpContainerTableau.setPreferredSize(new Dimension(350,103));
        else if(this.nbEssais==10)
            jpContainerTableau.setPreferredSize(new Dimension(350,183));
        else if(this.nbEssais==15)
            jpContainerTableau.setPreferredSize(new Dimension(350,263));
        else
            jpContainerTableau.setPreferredSize(new Dimension(350,343));

        jpContainerTableau.setLayout(new BorderLayout());
        jpContainerTableau.add(new JScrollPane(jtTableau),BorderLayout.CENTER);
        this.add(jlPremiereInstruction);
        this.add(jlDeuxiemeInstruction);
        this.add(jpContainer);
        this.add(jpContainerTableau);

        // D�finition des listeners

        //Le bouton Valider ne doit �tre accessible que lorsque tous les chiffres sont renseign�s
        jftfPropositionJoueur.addKeyListener(new KeyListener() {

            public void keyTyped(KeyEvent arg0) {}
            public void keyPressed(KeyEvent e) {}

            public void keyReleased(KeyEvent event) {
                for (int i=0;i<nbreCases;i++) {
                    if(jftfPropositionJoueur.getText().charAt(i)!=' ') {
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
        jbValider.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                controler.setPropositionJoueurModeChallenger(jftfPropositionJoueur.getText());
                jftfPropositionJoueur.setText("");
                jftfPropositionJoueur.requestFocusInWindow();
                jbValider.setEnabled(false);
            }

        });

        this.model.addObservateur(this);

    }

    /**
     * Accesseur permettant de retourner un objet de type JFormattedTextField correspondant � la proposition du joueur
     * @return Un objet de type JFormattedTextField correspondant � la proposition du joueur.
     */
    public JFormattedTextField getJftfPropositionJoueur() {
        return jftfPropositionJoueur;
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
        ((AbstractTableModel)jtTableau.getModel()).setValueAt(propositionJoueur,rowIndex,columnIndex);
        ((AbstractTableModel)jtTableau.getModel()).fireTableCellUpdated(rowIndex, columnIndex);
        columnIndex++;
        ((AbstractTableModel)jtTableau.getModel()).setValueAt(reponse,rowIndex,columnIndex);
        ((AbstractTableModel)jtTableau.getModel()).fireTableCellUpdated(rowIndex, columnIndex);
        rowIndex++;
        columnIndex=0;
        this.gestionFinDePartie(reponse);
    }

    /**
     * Pattern Observer - M�thode non utilis�e dans cette classe.
     */
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
        LOGGER.trace("Jeu RecherchePlusMoins en mode Challenger - Partie relanc�e");
        for(int i=0;i<rowIndex;i++) {
            for (int j=0;j<2;j++) {
                ((AbstractTableModel)jtTableau.getModel()).setValueAt("", i, j);
                ((AbstractTableModel)jtTableau.getModel()).fireTableCellUpdated(i, j);
            }
        }
        verificationJftf=0;
        rowIndex=0;
        columnIndex=0;
        min=0;
        max=10;
        verifCombinaisonSecrete=0;
        combinaisonSecrete="";
        this.generationCombinaisonSecrete();
        if(this.modeDeveloppeurActive==true) {
            jlPremiereInstruction.setText("La combinaison secr�te a �t� g�n�r�e par l'ordinateur (Solution : "+combinaisonSecrete+")");
        }
    }
    /**
     * G�n�ration de la combinaison secr�te par l'ordinateur.
     */
    public void generationCombinaisonSecrete(){
        int nbreAleatoire;
        for (int i=0;i<this.nbreCases;i++) {
            nbreAleatoire=(int)(Math.random()*(max-min));
            combinaisonSecrete+=String.valueOf(nbreAleatoire);
        }
        LOGGER.debug("Jeu RecherchePlusMoins en mode Challenger - G�n�ration de la combinaison secr�te:"+combinaisonSecrete);
        controler.setModeDeJeu(0);
        controler.setPropositionSecreteModeChallenger(combinaisonSecrete);
    }
    /**
     * Gestion de la fin de la partie en fonction de la r�ponse de l'ordinateur.
     * @param reponse R�ponse de l'ordinateur.
     */
    public void gestionFinDePartie(String reponse) {

        verifCombinaisonSecrete=0;

        for (int i=0;i<reponse.length();i++) {
            if (reponse.charAt(i)=='=') {
                verifCombinaisonSecrete++;
            }
        }

        //En cas de victoire
        if(verifCombinaisonSecrete==nbreCases) {
            JOptionPane.showMessageDialog(null, "F�licitations!!! Vous avez trouv� la combinaison secr�te en moins de "+nbEssais+" essais.",
                    "Fin de Partie",JOptionPane.INFORMATION_MESSAGE);

        }

        //En cas de d�fa�te
        if (rowIndex==nbEssais && verifCombinaisonSecrete!=nbreCases) {
            JOptionPane.showMessageDialog(null, "Vous avez perdu! La combinaison secr�te �tait : "+combinaisonSecrete, "Fin de Partie",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        //En cas de d�fa�te ou de victoire
        if(rowIndex==nbEssais||verifCombinaisonSecrete==nbreCases) {
            LOGGER.trace("Jeu RecherchePlusMoins en mode Challenger - Fin de partie");
            jdFinDePartie =new BoiteDialogueFinDePartie(null,"Fin de Partie",true);
            controler.setChoixFinDePartie(jdFinDePartie.getChoixFinDePartie());
        }
    }
}
