package vue;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/****************************************************************************************************
 * Classe permettant de g�rer l'affichage du tableau utilis� dans le cadre du jeu RecherchePlusMoins
 * @author abrahamyann
 ****************************************************************************************************/
public class LabelRenderer extends JLabel implements TableCellRenderer {
    private static final long serialVersionUID = 1L;

    /**
     * Variable relative au mode de jeu : 0 - Challenger, 1 - D�fenseur, 2 - Duel.
     */
    private int modeDeJeu=0;

    /**
     * Red�finition de la m�thode de l'interface TableCellRenderer permettant de g�rer l'affichage du tableau :
     * la couleur verte sera associ�e au joueur et la couleur rouge � l'ordinateur.
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
                                                   int column) {
        this.setText((value!=null)?value.toString():"");
        this.setHorizontalAlignment(JLabel.CENTER);

        if(modeDeJeu==2) {
            if((row%2==0&&column==0)||(row%2==1&&column==1))
                this.setForeground(Color.GREEN);
            else
                this.setForeground(Color.RED);
        }
        return this;
    }

    /**
     * Mutateur permettant de modifier le mode de Jeu
     * @param modeDeJeu Variable relative au mode de jeu : 0 - Challenger, 1 - D�fenseur, 2 - Duel.
     */
    public void setModeDeJeu(int modeDeJeu) {
        this.modeDeJeu=modeDeJeu;
    }

}
