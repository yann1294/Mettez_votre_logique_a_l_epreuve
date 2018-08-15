package model;

import javax.swing.table.AbstractTableModel;

/****************************************************************************************************
 * Classe relative au Mod�le de donn�es du tableau utilis� dans le cadre du jeu RecherchePlusMoins.
 *  @author abrahamyann
 ****************************************************************************************************/

public class ModelTableau extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    /**
     * Donn�es du tableau.
     */
    private Object[][] data;

    /**
     * Titre des colonnes du tableau.
     */
    private String[] title;

    /**
     * Constructeur de la classe ModelTableau.
     * @param data Donn�es du tableau.
     * @param title Titre des colonnes du tableau.
     */
    public ModelTableau(Object[][] data, String[] title) {
        this.data = data;
        this.title = title;
    }

    /**
     * Returns the number of rows in the model. A
     * <code>JTable</code> uses this method to determine how many rows it
     * should display.  This method should be quick, as it
     * is called frequently during rendering.
     *
     * @return the number of rows in the model
     * @see #getColumnCount
     */
    @Override
    public int getRowCount() {
        return this.data.length;
    }

    /**
     * Returns the number of columns in the model. A
     * <code>JTable</code> uses this method to determine how many columns it
     * should create and display by default.
     *
     * @return the number of columns in the model
     * @see #getRowCount
     */
    @Override
    public int getColumnCount() {
        return this.title.length;
    }

    /**
     * Returns the value for the cell at <code>columnIndex</code> and
     * <code>rowIndex</code>.
     *
     * @param rowIndex    the row whose value is to be queried
     * @param columnIndex the column whose value is to be queried
     * @return the value Object at the specified cell
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return this.data[rowIndex][columnIndex];
    }
    /**
     * M�thode qui retourne le titre de la colonne � l'indice sp�cifi�.
     * @param col Colonne.
     * @return Le titre de la colonne � l'indice sp�cifi�.
     */
    public String getColumnName(int col) {
        return this.title[col];
    }

    /**
     * M�thode qui d�finit la valeur � l'emplacement sp�cifi�.
     * @param value Valeur souhait�e.
     * @param row Ligne.
     * @param col Colonne.
     */
    public void setValueAt(Object value, int row, int col) {
        this.data[row][col] = value;
    }

    /**
     * M�thode permettant de rendre les cellules du tableau �ditables ou non.
     * @param row Ligne.
     * @param col Colonne.
     * @return False. Dans le cadre du jeu RecherchePlusMoins, les cellules du
     * tableau ne seront pas �ditables.
     */
    public boolean isCellEditable(int row, int col) {
        return false;
    }
}
