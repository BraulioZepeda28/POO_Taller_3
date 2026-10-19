import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class VistaSudoku extends Application {

    private TextField[][] casillasUI = new TextField[9][9];
    private Button btnFacil;
    private Button btnNormal;
    private Button btnDificil;

    @Override
    public void start(Stage ventanaPrincipal) {
        BorderPane panelPrincipal = new BorderPane();
        panelPrincipal.setStyle("-fx-background-color: #121212;");

        GridPane cuadricula = new GridPane();
        cuadricula.setAlignment(Pos.CENTER);
        cuadricula.setHgap(0);
        cuadricula.setVgap(0);

        for (int f = 0; f < 9; f++) {
            for (int c = 0; c < 9; c++) {
                TextField cajitaTexto = new TextField();
                cajitaTexto.setPrefSize(45, 45);
                cajitaTexto.setAlignment(Pos.CENTER);

                casillasUI[f][c] = cajitaTexto;
                cuadricula.add(cajitaTexto, c, f);
            }
        }

        btnFacil = new Button("Facil");
        btnNormal = new Button("Normal");
        btnDificil = new Button("Difícil");

        btnFacil.setPrefWidth(90);
        btnNormal.setPrefWidth(90);
        btnDificil.setPrefWidth(90);

        HBox panelBotones = new HBox(10);
        panelBotones.setAlignment(Pos.CENTER);
        panelBotones.getChildren().add(btnFacil);
        panelBotones.getChildren().add(btnNormal);
        panelBotones.getChildren().add(btnDificil);

        panelPrincipal.setCenter(cuadricula);
        panelPrincipal.setBottom(panelBotones);

        Sudoku modelo = new Sudoku();
        ControladorSudoku controlador = new ControladorSudoku(modelo, this);
        controlador.iniciarJuego();

        Scene escena = new Scene(panelPrincipal, 480, 520);
        ventanaPrincipal.setTitle("Taller 3 - Sudoku JavaFX");
        ventanaPrincipal.setScene(escena);
        ventanaPrincipal.show();
    }

    public void aplicarEstiloCasilla(int fila, int col, String colorFondo, String colorLetra) {
        TextField cajita = casillasUI[fila][col];

        int grosorArriba = 1;
        int grosorDerecha = 1;
        int grosorAbajo = 1;
        int grosorIzquierda = 1;

        if (fila == 2 || fila == 5) {
            grosorAbajo = 3;
        }
        if (col == 2 || col == 5) {
            grosorDerecha = 3;
        }
        if (fila == 0) {
            grosorArriba = 3;
        }
        if (fila == 8) {
            grosorAbajo = 3;
        }
        if (col == 0) {
            grosorIzquierda = 3;
        }
        if (col == 8) {
            grosorDerecha = 3;
        }

        String estiloBordes = "-fx-border-color: #121212; -fx-border-width: " + grosorArriba + "px " + grosorDerecha + "px "+ grosorAbajo + "px " + grosorIzquierda + "px; ";
        String estiloColores = "-fx-background-color: " + colorFondo + "; " + "-fx-text-fill: " + colorLetra + "; " + "-fx-font-weight: bold; -fx-font-size: 16px;";
        cajita.setStyle(estiloBordes + estiloColores);
    }

    public void marcarBotonSeleccionado(int numPlantilla) {
        String estiloNormal = "-fx-background-color: #333333; -fx-text-fill: white; -fx-font-weight: bold;";
        String estiloInvertido = "-fx-background-color: white; -fx-text-fill: black; -fx-font-weight: bold;";

        btnFacil.setStyle(estiloNormal);
        btnNormal.setStyle(estiloNormal);
        btnDificil.setStyle(estiloNormal);

        if (numPlantilla == 1) {
            btnFacil.setStyle(estiloInvertido);
        } else if (numPlantilla == 2) {
            btnNormal.setStyle(estiloInvertido);
        } else if (numPlantilla == 3) {
            btnDificil.setStyle(estiloInvertido);
        }
    }

    public void mostrarMensajeVictoria() {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle("Juego Terminado");
        alerta.setHeaderText(null);
        alerta.setContentText("Has completado el Sudoku correctamente");
        alerta.showAndWait();
    }

    public TextField getCasilla(int f, int c) {
        return casillasUI[f][c];
    }

    public Button getBtnFacil() {
        return btnFacil;
    }

    public Button getBtnNormal() {
        return btnNormal;
    }

    public Button getBtnDificil() {
        return btnDificil;
    }

    public static void main(String[] args) {
        launch(args);
    }
}