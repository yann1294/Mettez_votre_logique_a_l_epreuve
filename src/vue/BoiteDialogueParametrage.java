package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Properties;

/***********************************************************************************************************************
 * Classe relative � la boite de dialogue permettant d'acc�der aux param�tres des jeux RecherchePlusMoins et Mastermind
 * tels que le nombre d'essais, le nombre de cases et l'option mode d�veloppeur pour les 2 jeux, et �galement le nombre
 * de couleurs utilisables pour le Mastermind.
 * @author abrahamyann
 ***********************************************************************************************************************/

public class BoiteDialogueParametrage extends JDialog {
    private static final long serialVersionUID = 1L;

    /**
     * JPanel principal de la classe.
     */
    private JPanel jpContainer=new JPanel();

    /**
     * JPanel contenant les composants relatifs aux param�tres du jeu RecherchePlusMoins.
     */
    private JPanel jpContainerRecherchePlusMoins=new JPanel();

    /**
     * JPanel contenant les composants relatifs aux param�tres du jeu Mastermind.
     */
    private JPanel jpContainerMastermind=new JPanel();

    /**
     * JPanel contenant le composant relatif au mode d�veloppeur commun aux jeux RecherchePlusMoins et Mastermind.
     */
    private JPanel jpContainerModeDeveloppeur=new JPanel();

    /**
     * JPanel contenant les JButton de validation ou d'annulation.
     */
    private JPanel jpContainerButton=new JPanel();

    /**
     * JLabel de type informatif.
     */
    private JLabel jlNbEssaisRecherchePlusMoins=new JLabel("Nombre d'essais :"),jlNbCasesRecherchePlusMoins=new JLabel("Nombre de cases :"),
            jlNbEssaisMastermind=new JLabel("Nombre d'essais :"),jlNbCasesMastermind=new JLabel("Nombre de cases :"),
            jlNbCouleursUtilisablesMastermind=new JLabel("Nombre de couleurs utilisables :");

    /**
     * JComboBox contenant une plage de valeurs pour un param�tre donn�.
     */
    private JComboBox jcbNbEssaisRecherchePlusMoins=new JComboBox(),jcbNbCasesRecherchePlusMoins=new JComboBox(),
            jcbNbEssaisMastermind=new JComboBox(),jcbNbCasesMastermind=new JComboBox(),
            jcbNbCouleursUtilisablesMastermind=new JComboBox();
    /**
     * JCheckBox permettant de s�lectionner le mode d�veloppeur.
     */
    private JCheckBox jcbModeDeveloppeur=new JCheckBox("Mode d�veloppeur");

    /**
     * JButton de validation.
     */
    private JButton jbOK=new JButton("OK");

    /**
     * JButton d'annulation.
     */
    private JButton jbAnnuler=new JButton("Annuler");

    /**
     * Objet permettant de charger le fichier resources/config.properties et �galement d'enregistrer dans ce fichier.
     */
    private Properties prop;

    /**
     * Flux d'entr�e permettant de lire le fichier resources/config.properties.
     */
    private InputStream input;

    /**
     * Flux de sortie permettant d'�crire dans le fichier resources/config.properties.
     */
    private OutputStream output;

    /**
     * Variable de type chaine de caract�res permettant de manipuler le fichier resources/config.properties.
     */
    private String strNbEssaisRecherchePlusMoins="",strNbCasesRecherchePlusMoins="",strNbEssaisMastermind="",
            strNbCasesMastermind="",strNbCouleursUtilisablesMastermind="";

    /**
     * Variable de type tableau de chaine de caract�res permettant de manipuler le fichier resources/config.properties.
     */
    private String [] tabNbEssaisRecherchePlusMoins, tabNbCasesRecherchePlusMoins,tabNbEssaisMastermind,
            tabNbCasesMastermind,tabNbCouleursUtilisablesMastermind;

    /**
     * Nombre de valeurs possibles dans le fichier resources/config.properties pour le param�tre correspondant.
     */
    private int choixNombreEssaisFichierConfigRecherchePlusMoins=4, choixNombreCasesFichierConfigRecherchePlusMoins=7,
            choixNombreCouleursUtilisablesFichierConfig=7;

    /**
     * Param�tre du jeu RecherchePlusMoins.
     */
    private int nbreCasesRecherchePlusMoins, nbEssaisRecherchePlusMoins;

    /**
     * Param�tre du jeu Mastermind.
     */
    private int nbreCasesMastermind,nbEssaisMastermind,nbCouleursUtilisablesMastermind;

    /**
     * Param�tre relatif aux jeux RecherchePlusMoins et Mastermind. Par d�faut, le mode d�veloppeur est d�sactiv�.
     */
    private boolean modeDeveloppeurActive;

    /**
     * Constructeur de la classe BoiteDialogueParametrage.
     * @param parent Composant parent. Cette variable sera null.
     * @param title Titre de la boite de dialogue.
     * @param modal Modalit� de la boite de dialogue. On optera pour une boite de dialogue modale.
     * @param nbEssaisRecherchePlusMoins Param�tre du jeu RecherchePlusMoins.
     * @param nbreCasesRecherchePlusMoins Param�tre du jeu RecherchePlusMoins.
     * @param nbEssaisMastermind Param�tre du jeu Mastermind.
     * @param nbreCasesMastermind Param�tre du jeu Mastermind.
     * @param nbCouleursUtilisablesMastermind Param�tre du jeu Mastermind.
     * @param modeDeveloppeurActive Param�tre relatif aux jeux RecherchePlusMoins et Mastermind.
     * 		  Par d�faut, le mode d�veloppeur est d�sactiv�.
     */
    public BoiteDialogueParametrage(JFrame parent, String title, boolean modal, int nbEssaisRecherchePlusMoins,
                                    int nbreCasesRecherchePlusMoins,int nbEssaisMastermind,int nbreCasesMastermind,int nbCouleursUtilisablesMastermind,
                                    boolean modeDeveloppeurActive){
        super(parent,title,modal);
        this.nbEssaisRecherchePlusMoins=nbEssaisRecherchePlusMoins;
        this.nbreCasesRecherchePlusMoins=nbreCasesRecherchePlusMoins;
        this.nbEssaisMastermind=nbEssaisMastermind;
        this.nbreCasesMastermind=nbreCasesMastermind;
        this.nbCouleursUtilisablesMastermind=nbCouleursUtilisablesMastermind;
        this.modeDeveloppeurActive=modeDeveloppeurActive;
        this.setSize(600,290);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.initComponent();
        this.showDialog(true);
    }

    /**
     * M�thode permettant de r�aliser l'interface graphique de la boite de dialogue mais �galement de lire
     * les donn�es du fichier resources/config.properties et d'enregistrer les param�tres souhait�s dans le
     * m�me fichier.
     */
    private void initComponent() {

        //Mise en place de l'interface graphique
        jpContainerRecherchePlusMoins.setPreferredSize(new Dimension(590,80));
        jpContainerRecherchePlusMoins.setBorder(BorderFactory.createTitledBorder("RecherchePlusMoins"));
        jpContainerRecherchePlusMoins.add(jlNbEssaisRecherchePlusMoins);
        jpContainerRecherchePlusMoins.add(jcbNbEssaisRecherchePlusMoins);
        jpContainerRecherchePlusMoins.add(jlNbCasesRecherchePlusMoins);
        jpContainerRecherchePlusMoins.add(jcbNbCasesRecherchePlusMoins);

        jpContainerMastermind.setPreferredSize(new Dimension(590,80));
        jpContainerMastermind.setBorder(BorderFactory.createTitledBorder("Mastermind"));
        jpContainerMastermind.add(jlNbEssaisMastermind);
        jpContainerMastermind.add(jcbNbEssaisMastermind);
        jpContainerMastermind.add(jlNbCasesMastermind);
        jpContainerMastermind.add(jcbNbCasesMastermind);
        jpContainerMastermind.add(jlNbCouleursUtilisablesMastermind);
        jpContainerMastermind.add(jcbNbCouleursUtilisablesMastermind);

        jpContainerModeDeveloppeur.setPreferredSize(new Dimension(600,40));
        jpContainerModeDeveloppeur.add(jcbModeDeveloppeur);

        jpContainerButton.setPreferredSize(new Dimension(600,40));
        jpContainerButton.add(jbOK);
        jpContainerButton.add(jbAnnuler);

        jpContainer.setPreferredSize(new Dimension(600,290));
        jpContainer.add(jpContainerRecherchePlusMoins);
        jpContainer.add(jpContainerMastermind);
        jpContainer.add(jpContainerModeDeveloppeur);
        jpContainer.add(jpContainerButton);

        this.setContentPane(jpContainer);

        //Import des donn�es du fichier config.properties
        prop=new Properties();
        input=null;

        try {
            input=new FileInputStream("resources/config.properties");
            prop.load(input);
            strNbEssaisRecherchePlusMoins=prop.getProperty("param.nbEssaisRecherchePlusMoins");
            tabNbEssaisRecherchePlusMoins=strNbEssaisRecherchePlusMoins.split(",");
            strNbCasesRecherchePlusMoins=prop.getProperty("param.nbreCasesRecherchePlusMoins");
            tabNbCasesRecherchePlusMoins=strNbCasesRecherchePlusMoins.split(",");

            strNbEssaisMastermind=prop.getProperty("param.nbEssaisMastermind");
            tabNbEssaisMastermind=strNbEssaisMastermind.split(",");
            strNbCasesMastermind=prop.getProperty("param.nbreCasesMastermind");
            tabNbCasesMastermind=strNbCasesMastermind.split(",");
            strNbCouleursUtilisablesMastermind=prop.getProperty("param.nbCouleursUtilisablesMastermind");
            tabNbCouleursUtilisablesMastermind=strNbCouleursUtilisablesMastermind.split(",");

            for (int i=0;i<choixNombreEssaisFichierConfigRecherchePlusMoins;i++) {
                jcbNbEssaisRecherchePlusMoins.addItem(tabNbEssaisRecherchePlusMoins[i]);
                if(i<choixNombreEssaisFichierConfigRecherchePlusMoins-1)
                    jcbNbEssaisMastermind.addItem(tabNbEssaisMastermind[i]);
            }

            for (int i=0;i<choixNombreCasesFichierConfigRecherchePlusMoins;i++) {
                jcbNbCasesRecherchePlusMoins.addItem(tabNbCasesRecherchePlusMoins[i]);
                if(i<3)
                    jcbNbCasesMastermind.addItem(tabNbCasesMastermind[i]);
            }

            for (int i=0;i<choixNombreCouleursUtilisablesFichierConfig;i++) {
                jcbNbCouleursUtilisablesMastermind.addItem(tabNbCouleursUtilisablesMastermind[i]);
            }

            jcbNbEssaisRecherchePlusMoins.setSelectedItem(prop.getProperty("param.nbEssaisActifRecherchePlusMoins"));
            jcbNbCasesRecherchePlusMoins.setSelectedItem(prop.getProperty("param.nbreCasesActifRecherchePlusMoins"));
            jcbNbEssaisMastermind.setSelectedItem(prop.getProperty("param.nbEssaisActifMastermind"));
            jcbNbCasesMastermind.setSelectedItem(prop.getProperty("param.nbreCasesActifMastermind"));
            jcbNbCouleursUtilisablesMastermind.setSelectedItem(prop.getProperty("param.nbCouleursUtilisablesActifMastermind"));

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


        if(modeDeveloppeurActive==false)
            jcbModeDeveloppeur.setSelected(false);
        else
            jcbModeDeveloppeur.setSelected(true);

        //D�finition des listeners

        //Lors de la validation, on enregistre les param�tres du joueur dans le fichier config.properties
        jbOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                prop=new Properties();
                input=null;
                output=null;

                try {
                    input=new FileInputStream("resources/config.properties");
                    prop.load(input);

                    //Traitement pour le jeu RecherchePlusMoins
                    nbreCasesRecherchePlusMoins=Integer.valueOf((String)jcbNbCasesRecherchePlusMoins.getSelectedItem());
                    nbEssaisRecherchePlusMoins=Integer.valueOf((String)jcbNbEssaisRecherchePlusMoins.getSelectedItem());
                    prop.setProperty("param.nbreCasesActifRecherchePlusMoins", (String)jcbNbCasesRecherchePlusMoins.getSelectedItem());
                    prop.setProperty("param.nbEssaisActifRecherchePlusMoins", (String)jcbNbEssaisRecherchePlusMoins.getSelectedItem());

                    //Traitement pour le jeu Mastermind
                    nbreCasesMastermind=Integer.valueOf((String)jcbNbCasesMastermind.getSelectedItem());
                    nbEssaisMastermind=Integer.valueOf((String)jcbNbEssaisMastermind.getSelectedItem());
                    nbCouleursUtilisablesMastermind=Integer.valueOf((String)jcbNbCouleursUtilisablesMastermind.getSelectedItem());

                    prop.setProperty("param.nbreCasesActifMastermind", (String)jcbNbCasesMastermind.getSelectedItem());
                    prop.setProperty("param.nbEssaisActifMastermind", (String)jcbNbEssaisMastermind.getSelectedItem());
                    prop.setProperty("param.nbCouleursUtilisablesActifMastermind",(String)jcbNbCouleursUtilisablesMastermind.getSelectedItem());
                    output=new FileOutputStream("resources/config.properties");
                    prop.store(output, "Fichier de configuration config.properties");

                } catch (IOException e1) {
                    e1.printStackTrace();
                } finally {
                    if (input != null) {
                        try {
                            output.close();
                            input.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }

                }

                //Traitement pour l'option Mode D�veloppeur
                if(jcbModeDeveloppeur.isSelected()) {
                    modeDeveloppeurActive=true;
                }
                else
                    modeDeveloppeurActive=false;

                showDialog(false);
            }

        });

        jbAnnuler.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showDialog(false);
            }

        });
    }

    /**
     * M�thode permettant de rendre visible la boite de dialogue.
     * @param affichage Variable de type bool�enne permettant d'indiquer
     * si la boite de dialogue doit �tre visible ou non.
     */
    private void showDialog(boolean affichage) {
        this.setVisible(affichage);
    }

    /**
     * Accesseur permettant de r�cup�rer le nombre de cases pour le jeu RecherchePlusMoins.
     * @return Le nombre de cases pour le jeu RecherchePlusMoins
     */
    public int getNbreCasesRecherchePlusMoins() {
        return nbreCasesRecherchePlusMoins;
    }

    /**
     * Accesseur permettant de r�cup�rer le nombre d'essais pour le jeu RecherchePlusMoins.
     * @return Le nombre d'essais pour le jeu RecherchePlusMoins.
     */
    public int getNbEssaisRecherchePlusMoins() {
        return nbEssaisRecherchePlusMoins;
    }

    /**
     * Accesseur permettant de r�cup�rer le nombre de cases pour le jeu Mastermind.
     * @return Le nombre de cases pour le jeu Mastermind.
     */
    public int getNbreCasesMastermind() {
        return nbreCasesMastermind;
    }

    /**
     * Accesseur permettant de r�cup�rer le nombre d'essais pour le jeu Mastermind.
     * @return Le nombre d'essais pour le jeu Mastermind.
     */
    public int getNbEssaisMastermind() {
        return nbEssaisMastermind;
    }

    /**
     * Accesseur permettant de r�cup�rer le nombre de couleurs utilisables pour le jeu Mastermind.
     * @return Le nombre de couleurs utilisables pour le jeu Mastermind.
     */
    public int getNbCouleursUtilisablesMastermind(){
        return nbCouleursUtilisablesMastermind;
    }

    /**
     * Accesseur permettant de r�cup�rer la variable indiquant si le mode d�veloppeur est activ� ou non.
     * @return La variable indiquant si le mode d�veloppeur est activ� ou non.
     */
    public boolean getModeDeveloppeurActive() {
        return modeDeveloppeurActive;
    }
}
