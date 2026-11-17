package Estructuras;

public class Arreglo_Bidimensional {

    private int[][] bahias;

    public Arreglo_Bidimensional() {
        bahias = new int[5][4];
    }

    public void ocuparBahia(int dia, int bahia) {
        if (dia >= 0 && dia < 5 && bahia >= 0 && bahia < 4) {
            bahias[dia][bahia] = 1;
        }
    }

    public void liberarBahia(int dia, int bahia) {
        if (dia >= 0 && dia < 5 && bahia >= 0 && bahia < 4) {
            bahias[dia][bahia] = 0;
        }
    }

    public String mostrarMatriz() {
        StringBuilder sb = new StringBuilder("Dia/Bahia | B1 | B2 | B3 | B4\n");
        for (int i = 0; i < 5; i++) {
            sb.append("Dia ").append(i + 1).append("     | ");
            for (int j = 0; j < 4; j++) {
                sb.append(bahias[i][j]).append("  | ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
