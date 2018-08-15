import model.ModeleDonnees;
import model.ModeleDonneesMastermind;
import vue.Fenetre;

import java.util.Scanner;

/*******************************
 * La classe Main du programme.
 * @author abrahamyann
 ********************************/

public class Main {

    public static void main(String[] args) {
        // Activation ou non du mode developer via la console.

        Scanner sc = new Scanner(System.in);
        String strModeDeveloppeurActiveConsole = "";

        boolean modeDeveloppeurActiveConsole=false;
        do {
            System.out.println("Souhaitez-vous activer le mode d�veloppeur(O pour oui/N pour non) ? :");
            strModeDeveloppeurActiveConsole=sc.nextLine();
        }
        while(!strModeDeveloppeurActiveConsole.equals("O")&&!strModeDeveloppeurActiveConsole.equals("N"));

        if(strModeDeveloppeurActiveConsole.equals("O"))
            modeDeveloppeurActiveConsole=true;
        else
            modeDeveloppeurActiveConsole=false;

        //Instanciation des mod�les de donn�es relatifs aux jeux RecherchePlusMoins et Mastermind.
        ModeleDonnees model=new ModeleDonnees();
        ModeleDonneesMastermind modelMastermind=new ModeleDonneesMastermind();

        //Instanciation de la fen�tre principale.
        new Fenetre(model,modelMastermind,modeDeveloppeurActiveConsole);
    }
}
